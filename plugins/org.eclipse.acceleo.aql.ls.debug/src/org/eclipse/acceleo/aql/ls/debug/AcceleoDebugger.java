/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.aql.profiler.IProfiler;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.acceleo.aql.profiler.ProfilerUtils;
import org.eclipse.acceleo.aql.profiler.ProfilerUtils.Representation;
import org.eclipse.acceleo.debug.AbstractDSLDebugger;
import org.eclipse.acceleo.debug.DSLSource;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.util.StackFrame;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.impl.ECrossReferenceAdapterCrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.impl.ResourceSetRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.swt.widgets.Display;

public class AcceleoDebugger extends AbstractDSLDebugger {

	/**
	 * The generate {@link Runnable}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class GenerateRunnable implements Runnable {

		@Override
		public void run() {
			final Module module = astResult.getModule();
			final long threadID = Thread.currentThread().getId();
			spawnRunningThread(threadID, Thread.currentThread().getName(), module);
			try {
				if (isNoDebug()) {
					generateNoDebug(queryEnvironment, module, model);
				} else {
					evaluator = new AcceleoDebugEvaluator(queryEnvironment);

					final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
					resolver.clearLoaders();
					resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
					resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(
							AcceleoParser.QUALIFIER_SEPARATOR));

					AcceleoUtil.generate(evaluator, queryEnvironment, module, model, getDestination());
				}
			} finally {
				// FIXME workaround: UI jobs are coming from core.debug even if the gen has finished,
				// which cause makes the JobManager to stall and prevents the termination of the LSP
				// process. By launching the termination in the UI thread in sync we allow the jobs to
				// finish first.
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						terminate(threadID);
						terminated(threadID);
						terminated();
					}
				});
			}

			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			if (workspace != null) {
				IContainer container = workspace.getRoot().getContainerForLocation(new Path(destination
						.toFileString()));
				if (container != null) {
					try {
						container.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Acceleo Debugger.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class AcceleoDebugEvaluator extends AcceleoEvaluator {

		/**
		 * Constructor.
		 * 
		 * @param queryEnvironment
		 *            the {@link IQualifiedNameQueryEnvironment}
		 */
		AcceleoDebugEvaluator(IQualifiedNameQueryEnvironment queryEnvironment) {
			super(queryEnvironment.getLookupEngine());
		}

		@Override
		public Object doSwitch(EObject eObject) {
			if (isTerminated()) {
				return null;
			}
			if (eObject instanceof Template || eObject instanceof Query) {
				pushStackFrame(Thread.currentThread().getId(), eObject);
			}
			try {
				if (isAcceleoInstruction(eObject)) {
					final StackFrame currentFrame = peekStackFrame(Thread.currentThread().getId());
					currentFrame.setInstruction(eObject);
					currentFrame.setVariables(peekVariables());
					if (!AcceleoDebugger.this.control(Thread.currentThread().getId(), eObject)) {
						Thread.currentThread().interrupt();
					}
				}
				return super.doSwitch(eObject);
			} finally {
				if (eObject instanceof Template || eObject instanceof Query) {
					popStackFrame(Thread.currentThread().getId());
				}
			}
		}

	}

	/**
	 * The module.
	 */
	public static final String MODULE = "module";

	/**
	 * The model.
	 */
	public static final String MODEL = "model";

	/**
	 * The destination.
	 */
	public static final String DESTINATION = "destination";

	/**
	 * The profile model
	 */
	public static final String PROFILE_MODEL = "profileModel";

	/**
	 * The profile model {@link Representation}.
	 */
	public static final String PROFILE_MODEL_REPRESENTATION = "modelRepresentation";

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The destination {@link URI}.
	 */
	private URI destination;

	/**
	 * The profile model {@link URI}.
	 */
	private URI profileModelURI;

	/**
	 * The profiler model {@link Representation}.
	 */
	private Representation profilerModelRepresentation;

	/**
	 * The {@link AcceleoAstResult}.
	 */
	private AcceleoAstResult astResult;

	/**
	 * The {@link Resource} containing the model.
	 */
	private Resource model;

	/**
	 * The {@link AcceleoDebugEvaluator}.
	 */
	private AcceleoDebugEvaluator evaluator;

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            the {@link org.eclipse.acceleo.debug.event.DSLDebugEventDispatcher dispatcher} for
	 *            asynchronous communication or the {@link org.eclipse.acceleo.debug.ide.DSLDebugTargetAdapter
	 *            target} for synchronous communication
	 */
	public AcceleoDebugger(IDSLDebugEventProcessor target) {
		super(target);
	}

	@Override
	public void initialize(boolean noDebug, Map<String, Object> arguments) {
		setNoDebug(noDebug);
		final URI moduleURI = URI.createURI((String)arguments.get(MODULE));
		final URI modelURI = URI.createURI((String)arguments.get(MODEL));
		destination = URI.createURI((String)arguments.get(DESTINATION));

		final ResourceSet resourceSetForModels = new ResourceSetImpl();
		model = resourceSetForModels.getResource(modelURI, true);

		final String profileModel = (String)arguments.get(PROFILE_MODEL);
		if (profileModel != null) {
			profileModelURI = URI.createFileURI(URI.decode(profileModel));
		}
		final String profilerModelRepresentationString = (String)arguments.get(PROFILE_MODEL_REPRESENTATION);
		if (profilerModelRepresentationString != null) {
			profilerModelRepresentation = Representation.valueOf(profilerModelRepresentationString);
		}

		final IProject project = LSPEclipseUtils.findResourceFor((String)arguments.get(MODULE)).getProject();
		final IQualifiedNameResolver resolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				AcceleoPlugin.getPlugin().getClass().getClassLoader(), project,
				AcceleoParser.QUALIFIER_SEPARATOR);

		final ECrossReferenceAdapterCrossReferenceProvider crossReferenceProvider = new ECrossReferenceAdapterCrossReferenceProvider(
				ECrossReferenceAdapter.getCrossReferenceAdapter(resourceSetForModels));
		final ResourceSetRootEObjectProvider rootProvider = new ResourceSetRootEObjectProvider(
				resourceSetForModels);
		queryEnvironment = org.eclipse.acceleo.query.runtime.Query
				.newQualifiedNameEnvironmentWithDefaultServices(resolver, crossReferenceProvider,
						rootProvider);

		for (String nsURI : new ArrayList<String>(EPackage.Registry.INSTANCE.keySet())) {
			registerEPackage(queryEnvironment, EPackage.Registry.INSTANCE.getEPackage(nsURI));
		}
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));

		try {
			final String moduleQualifiedName = resolver.getQualifiedName(java.net.URI.create(moduleURI
					.toString()).toURL());
			final Object resolved = resolver.resolve(moduleQualifiedName);
			if (resolved instanceof Module) {
				astResult = ((Module)resolved).getAst();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Registers the given {@link EPackage} in the given {@link IQualifiedNameQueryEnvironment} recursively.
	 * 
	 * @param environment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param ePackage
	 *            the {@link EPackage}
	 */
	private void registerEPackage(IQualifiedNameQueryEnvironment environment, EPackage ePackage) {
		environment.registerEPackage(ePackage);
		for (EPackage child : ePackage.getESubpackages()) {
			registerEPackage(environment, child);
		}
	}

	@Override
	public void start() {
		new Thread(new GenerateRunnable(), "Acceleo Debug Thread").start();
	}

	/**
	 * Generates without debug.
	 * 
	 * @param environment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param module
	 *            the {@link Module} to generate
	 * @param modelResource
	 *            the model {@link Resource}
	 */
	protected void generateNoDebug(IQualifiedNameQueryEnvironment environment, Module module,
			Resource modelResource) {
		final AcceleoEvaluator noDebugEvaluator;
		final IProfiler profiler;
		if (profileModelURI != null && profilerModelRepresentation != null) {
			profiler = ProfilerUtils.getProfiler(profilerModelRepresentation, ProfilerPackage.eINSTANCE
					.getProfilerFactory());
			noDebugEvaluator = new AcceleoProfilerEvaluator(queryEnvironment, profiler);
		} else {
			noDebugEvaluator = new AcceleoEvaluator(environment.getLookupEngine());
			profiler = null;
		}

		final IQualifiedNameResolver resolver = environment.getLookupEngine().getResolver();
		resolver.clearLoaders();
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), noDebugEvaluator));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));

		AcceleoUtil.generate(noDebugEvaluator, environment, module, modelResource, getDestination());

		if (profiler != null) {
			try {
				profiler.save(profileModelURI);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canStepInto(Long threadID, EObject instruction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateVariableValue(Long threadID, String variableName, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getVariableValue(Long threadID, String stackName, String variableName, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariableValue(Long threadID, String stackName, String variableName, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EObject getInstruction(String path, long line, long column) {
		AcceleoAstResult moduleAstResult = null;

		try {
			final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
			final String moduleQualifiedName = resolver.getQualifiedName(new URL("file://" + path));
			if (moduleQualifiedName != null) {
				final Object resolved = resolver.resolve(moduleQualifiedName);
				if (resolved instanceof Module) {
					moduleAstResult = ((Module)resolved).getAst();
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final EObject res;
		EObject instruction = moduleAstResult.getAstNode((int)line, (int)column);
		if (column == 0) {
			// The breakpoint has been set on the line so we select the containing Expression statement if
			// needed
			while (instruction != null) {
				if (isAcceleoInstruction(instruction)) {
					break;
				}
				instruction = instruction.eContainer();
			}
			res = instruction;
		} else {
			res = instruction;
		}
		return res;
	}

	/**
	 * Tells if the given {@link EObject} is an Acceleo instruction.
	 * 
	 * @param eObject
	 *            the {@link EObject}
	 * @return <code>true</code> if the given {@link EObject} is an Acceleo instruction, <code>false</code>
	 *         otherwise
	 */
	private boolean isAcceleoInstruction(EObject eObject) {
		return eObject instanceof Statement;
	}

	@Override
	public DSLSource getSource(EObject instruction) {
		DSLSource res = null;
		String path = null;
		Module module = getModule(instruction);
		if (module != null) {
			final AcceleoAstResult moduleAstResult = module.getAst();
			final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
			final String moduleQualifiedName = resolver.getQualifiedName(moduleAstResult.getModule());
			URL moduleSourceURL = resolver.getSourceURL(moduleQualifiedName);
			try {
				path = URIUtil.toFile(moduleSourceURL.toURI()).toString();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (instruction instanceof ASTNode) {
				final int startLine = moduleAstResult.getStartLine((ASTNode)instruction);
				final int startColumn = moduleAstResult.getStartColumn((ASTNode)instruction);
				final int endLine = moduleAstResult.getEndLine((ASTNode)instruction);
				final int endColumn = moduleAstResult.getEndColumn((ASTNode)instruction);
				res = new DSLSource(path, startLine + 1, startColumn + 1, endLine + 1, endColumn + 1);
			} else if (instruction instanceof Expression) {
				final int startLine = moduleAstResult.getStartLine((Expression)instruction);
				final int startColumn = moduleAstResult.getStartColumn((Expression)instruction);
				final int endLine = moduleAstResult.getEndLine((Expression)instruction);
				final int endColumn = moduleAstResult.getEndColumn((Expression)instruction);
				res = new DSLSource(path, startLine + 1, startColumn + 1, endLine + 1, endColumn + 1);
			} else if (instruction instanceof VariableDeclaration) {
				final int startLine = moduleAstResult.getStartLine((VariableDeclaration)instruction);
				final int startColumn = moduleAstResult.getStartColumn((VariableDeclaration)instruction);
				final int endLine = moduleAstResult.getEndLine((VariableDeclaration)instruction);
				final int endColumn = moduleAstResult.getEndColumn((VariableDeclaration)instruction);
				res = new DSLSource(path, startLine + 1, startColumn + 1, endLine + 1, endColumn + 1);
			} else {
				res = null;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link Module} of the given {@link EObject instruction}.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return the {@link Module} of the given {@link EObject instruction} if nay, <code>null</code> otherwise
	 */
	private Module getModule(EObject instruction) {
		Module res = null;

		EObject current = instruction;
		while (current != null) {
			if (current instanceof Module) {
				res = (Module)current;
				break;
			}
			current = current.eContainer();
		}

		return res;
	}

	/**
	 * Gets the destination {@link URI}.
	 * 
	 * @return the destination {@link URI}
	 */
	protected URI getDestination() {
		return destination;
	}

}
