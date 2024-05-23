/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.ide.evaluation.strategy.AcceleoWorkspaceWriterFactory;
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
import org.eclipse.acceleo.debug.ls.DSLDebugServer;
import org.eclipse.acceleo.debug.util.FrameVariable;
import org.eclipse.acceleo.debug.util.StackFrame;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.AQLUtils.AcceleoAQLResult;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.swt.widgets.Display;

/**
 * Debugger implementation for Acceleo.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
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
					evaluator = new AcceleoDebugEvaluator(queryEnvironment, newLine,
							getBreakPointsHitCounts());

					final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
					resolver.clearLoaders();
					resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
					resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(
							AcceleoParser.QUALIFIER_SEPARATOR, false));

					final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(model
							.getResourceSet().getURIConverter(), new AcceleoWorkspaceWriterFactory()) {
						@Override
						public IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, Charset charset,
								String lineDelimiter) throws IOException {
							consolePrint(uri.toString());
							return super.createWriterFor(uri, openMode, charset, lineDelimiter);
						}
					};
					AcceleoUtil.generate(evaluator, queryEnvironment, module, model, strategy,
							getDestination(), logURI);
					if (evaluator.getGenerationResult().getDiagnostic().getSeverity() != Diagnostic.OK) {
						printDiagnostic(evaluator.getGenerationResult().getDiagnostic(), "");
					}
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

		/**
		 * Gets the breakpoint hit counts map.
		 * 
		 * @return the breakpoint hit counts map
		 */
		private Map<URI, Integer> getBreakPointsHitCounts() {
			final Map<URI, Integer> res = new HashMap<>();

			for (Entry<URI, Map<String, String>> entry : getBreakpoints().entrySet()) {
				final String hitCondition = entry.getValue().get(
						DSLDebugServer.HIT_CONDITION_BREAKPOINT_ATTRIBUTE);
				if (hitCondition != null) {
					try {
						res.put(entry.getKey(), Integer.valueOf(hitCondition));
					} catch (NumberFormatException e) {
						consolePrint("hit condition is not an integer: " + hitCondition);
					}
				}
			}

			return res;
		}
	}

	/**
	 * Acceleo Debugger.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class AcceleoDebugEvaluator extends AcceleoEvaluator {

		/**
		 * The {@link Block} output variable name.
		 */
		private static final String BLOCK_OUTPUT_VARIABLE = "blockOutput";

		/**
		 * The stack of {@link Block} texts {@link List}.
		 */
		private Deque<List<String>> blockTextLists = new ArrayDeque<>();

		/**
		 * The mapping from an instruction {@link URI} to its hit count.
		 */
		private final Map<URI, Integer> breakPointHitCounts;

		/**
		 * Constructor.
		 * 
		 * @param queryEnvironment
		 *            the {@link IQualifiedNameQueryEnvironment}
		 * @param newLine
		 *            the new line {@link String}
		 * @param breakPointHitCounts
		 *            the break point hit count map
		 */
		AcceleoDebugEvaluator(IQualifiedNameQueryEnvironment queryEnvironment, String newLine,
				Map<URI, Integer> breakPointHitCounts) {
			super(queryEnvironment.getLookupEngine(), newLine);
			this.breakPointHitCounts = breakPointHitCounts;
		}

		/**
		 * Pushes a new {@link Block} text {@link List}.
		 */
		private void pushBlockTextList() {
			blockTextLists.addLast(new ArrayList<>());
		}

		/**
		 * Pops the last {@link Block} text {@link List}.
		 * 
		 * @return the last {@link Block} text {@link List}
		 */
		private List<String> popBlockTextList() {
			return blockTextLists.removeLast();
		}

		@Override
		public Object doSwitch(EObject eObject) {
			if (isTerminated()) {
				return null;
			}
			if (eObject instanceof Template || eObject instanceof Query) {
				pushStackFrame(Thread.currentThread().getId(), eObject);
			} else if (eObject instanceof Block) {
				pushBlockTextList();
			}
			try {
				if (isAcceleoInstruction(eObject)) {
					final StackFrame currentFrame = peekStackFrame(Thread.currentThread().getId());
					currentFrame.setInstruction(eObject);
					final LinkedHashMap<String, FrameVariable> variables = new LinkedHashMap<>();
					for (Entry<String, Object> entry : peekVariables().entrySet()) {
						variables.put(entry.getKey(), getFrameVariable(entry.getKey(), entry.getValue()));
					}
					currentFrame.setVariables(variables);
					currentFrame.getVariables().put(BLOCK_OUTPUT_VARIABLE, getFrameVariable(
							BLOCK_OUTPUT_VARIABLE, blockTextLists.peekLast()));
					if (!AcceleoDebugger.this.control(Thread.currentThread().getId(), eObject)) {
						Thread.currentThread().interrupt();
					}
				}
				return super.doSwitch(eObject);
			} finally {
				if (eObject instanceof Template || eObject instanceof Query) {
					popStackFrame(Thread.currentThread().getId());
				} else if (eObject instanceof Block) {
					popBlockTextList();
				}
			}
		}

		@Override
		protected List<String> createBlockTextsList(Block block) {
			return blockTextLists.peekLast();
		}

		/**
		 * Tells if we should break on the given {@link EObject instruction}.
		 * 
		 * @param instruction
		 *            the {@link EObject instruction}
		 * @return <code>true</code> if we should break on the given {@link EObject instruction},
		 *         <code>false</code> otherwise
		 */
		public boolean shouldBreak(EObject instruction) {
			boolean res;

			// check the condition expression
			final String conditionExpression = getBreakpointAttributes(instruction,
					DSLDebugServer.CONDITION_BREAKPOINT_ATTRIBUTE);
			if (conditionExpression != null) {
				final Object value = evaluateExpression(conditionExpression);
				res = value == null || (value instanceof Boolean && ((Boolean)value));
			} else {
				res = true;
			}

			// check hit count
			if (res) {
				final URI uri = EcoreUtil.getURI(instruction);
				final Integer count = breakPointHitCounts.get(uri);
				if (count != null && count > 0) {
					breakPointHitCounts.put(uri, count - 1);
					res = false;
				}
			}

			// check log expression
			if (res) {
				final String logExpression = getBreakpointAttributes(instruction,
						DSLDebugServer.LOG_MESSAGE_BREAKPOINT_ATTRIBUTE);
				if (logExpression != null) {
					final Object value = evaluateExpression(logExpression);
					if (value != null) {
						consolePrint(value.toString());
					}
					res = false;
				}
			}

			return res;
		}

		/**
		 * Parses and evaluate the given AQL expression in the current debug context.
		 * 
		 * @param expression
		 *            the AQL expression
		 * @return the evaluated {@link Object}
		 */
		private Object evaluateExpression(final String expression) {
			final Object res;

			final AcceleoAQLResult result = AQLUtils.parseWhileAqlExpression(expression.toString());
			if (result.getAstResult().getDiagnostic().getSeverity() == Diagnostic.ERROR) {
				consolePrint("parsing error: " + expression);
				printDiagnostic(result.getAstResult().getDiagnostic(), "");
				res = null;
			} else {
				final EvaluationResult value = evaluator.getAqlEngine().eval(result.getAstResult(),
						peekVariables());
				if (value.getDiagnostic().getSeverity() == Diagnostic.ERROR) {
					consolePrint("evaluation error: " + expression);
					printDiagnostic(result.getAstResult().getDiagnostic(), "");
					res = null;
				} else {
					res = value.getResult();
				}
			}

			return res;
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
	 * The JSON map of options.
	 */
	public static final String OPTIONS = "options";

	/**
	 * The option map type.
	 */
	public static final Type OPTION_MAP_TYPE = new TypeToken<LinkedHashMap<String, String>>() {
	}.getType();

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
	 * The {@link ResourceSet} for models.
	 */
	private ResourceSet resourceSetForModels;

	/**
	 * The destination {@link URI}.
	 */
	private URI destination;

	private Map<String, String> options;

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
	 * The new line {@link String}.
	 */
	private String newLine;

	/**
	 * The log {@link URI} if any, <code>null</code> otherwise.
	 */
	private URI logURI;

	/**
	 * The {@link AcceleoDebugEvaluator}.
	 */
	private AcceleoDebugEvaluator evaluator;

	/**
	 * The module {@link IFile}.
	 */
	private IResource moduleFile;

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

		final String optionString = (String)arguments.get(OPTIONS);
		if (optionString != null) {
			options = new Gson().fromJson(optionString, OPTION_MAP_TYPE);
		} else {
			options = new LinkedHashMap<>();
		}

		final String profileModel = (String)arguments.get(PROFILE_MODEL);
		if (profileModel != null) {
			profileModelURI = URI.createURI(profileModel);
		}
		final String profilerModelRepresentationString = (String)arguments.get(PROFILE_MODEL_REPRESENTATION);
		if (profilerModelRepresentationString != null) {
			profilerModelRepresentation = Representation.valueOf(profilerModelRepresentationString);
		}

		moduleFile = LSPEclipseUtils.findResourceFor((String)arguments.get(MODULE));
		final IProject project = moduleFile.getProject();
		final IQualifiedNameResolver resolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				AcceleoPlugin.getPlugin().getClass().getClassLoader(), project,
				AcceleoParser.QUALIFIER_SEPARATOR, false);

		newLine = options.getOrDefault(AcceleoUtil.NEW_LINE_OPTION, System.lineSeparator());
		logURI = AcceleoUtil.getlogURI(destination, options.get(AcceleoUtil.LOG_URI_OPTION));
		final ArrayList<Exception> exceptions = new ArrayList<>();
		resourceSetForModels = AQLUtils.createResourceSetForModels(exceptions, this, new ResourceSetImpl(),
				options);
		// TODO report exceptions
		model = resourceSetForModels.getResource(modelURI, true);
		queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(options, resolver, resourceSetForModels,
				false);

		for (String nsURI : new ArrayList<String>(EPackage.Registry.INSTANCE.keySet())) {
			registerEPackage(queryEnvironment, EPackage.Registry.INSTANCE.getEPackage(nsURI));
		}
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR,
				false));

		final java.net.URI moduleBinaryURI = resolver.getBinaryURI(java.net.URI.create(moduleURI.toString()));
		final String moduleQualifiedName = resolver.getQualifiedName(moduleBinaryURI);
		final Object resolved = resolver.resolve(moduleQualifiedName);
		if (resolved instanceof Module) {
			astResult = ((Module)resolved).getAst();
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
			final String workspaceModuleFilePath = moduleFile.getFullPath().toString();
			final URI startFileURI = URI.createPlatformResourceURI(workspaceModuleFilePath, true);
			profiler = ProfilerUtils.getProfiler(startFileURI.toString(), profilerModelRepresentation,
					ProfilerPackage.eINSTANCE.getProfilerFactory());
			noDebugEvaluator = new AcceleoProfilerEvaluator(queryEnvironment, newLine, profiler);
		} else {
			noDebugEvaluator = new AcceleoEvaluator(environment.getLookupEngine(), newLine);
			profiler = null;
		}

		final IQualifiedNameResolver resolver = environment.getLookupEngine().getResolver();
		resolver.clearLoaders();
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), noDebugEvaluator));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR,
				false));

		final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(modelResource
				.getResourceSet().getURIConverter(), new AcceleoWorkspaceWriterFactory()) {
			@Override
			public IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, Charset charset,
					String lineDelimiter) throws IOException {
				consolePrint(uri.toString());
				return super.createWriterFor(uri, openMode, charset, lineDelimiter);
			}
		};
		AcceleoUtil.generate(noDebugEvaluator, environment, module, modelResource, strategy, getDestination(),
				logURI);
		if (noDebugEvaluator.getGenerationResult().getDiagnostic().getSeverity() != Diagnostic.OK) {
			printDiagnostic(noDebugEvaluator.getGenerationResult().getDiagnostic(), "");
		}

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
		if (queryEnvironment != null && resourceSetForModels != null) {
			AQLUtils.cleanResourceSetForModels(this, resourceSetForModels);
			AcceleoUtil.cleanServices(queryEnvironment, resourceSetForModels);
		}
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
			final java.net.URI binaryURI = resolver.getBinaryURI(new java.net.URI("file://" + path));
			final String moduleQualifiedName = resolver.getQualifiedName(binaryURI);
			if (moduleQualifiedName != null) {
				final Object resolved = resolver.resolve(moduleQualifiedName);
				if (resolved instanceof Module) {
					moduleAstResult = ((Module)resolved).getAst();
				}
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final EObject res;
		if (moduleAstResult != null) {
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
		} else {
			res = null;
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
			final java.net.URI moduleSourceURI = resolver.getSourceURI(moduleQualifiedName);
			path = URIUtil.toFile(moduleSourceURI).toString();

			if (instruction instanceof AcceleoASTNode) {
				final int startLine = moduleAstResult.getStartLine((AcceleoASTNode)instruction);
				final int startColumn = moduleAstResult.getStartColumn((AcceleoASTNode)instruction);
				final int endLine = moduleAstResult.getEndLine((AcceleoASTNode)instruction);
				final int endColumn = moduleAstResult.getEndColumn((AcceleoASTNode)instruction);
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

	/**
	 * Prints the given {@link Diagnostic}.
	 * 
	 * @param stream
	 *            the {@link PrintStream}
	 * @param diagnostic
	 *            the {@link Diagnostic}
	 * @param indentation
	 *            the current indentation
	 */
	protected void printDiagnostic(Diagnostic diagnostic, String indentation) {
		String nextIndentation = indentation;
		if (diagnostic.getMessage() != null) {
			consolePrint(indentation);
			switch (diagnostic.getSeverity()) {
				case Diagnostic.INFO:
					consolePrint("INFO: ");
					break;

				case Diagnostic.WARNING:
					consolePrint("WARNING: ");
					break;

				case Diagnostic.ERROR:
					consolePrint("ERROR: ");
					break;
			}
			consolePrint(diagnostic.getMessage() + newLine);
			nextIndentation += "\t";
		}
		for (Diagnostic child : diagnostic.getChildren()) {
			printDiagnostic(child, nextIndentation);
		}
	}

	@Override
	public FrameVariable getFrameVariable(String name, Object value) {
		final FrameVariable res = new FrameVariable();

		res.setName(name);
		res.setValue(value);
		res.setReadOnly(true);

		return res;
	}

	@Override
	public boolean shouldBreak(EObject instruction) {
		final boolean res;

		if (super.shouldBreak(instruction)) {
			res = evaluator.shouldBreak(instruction);
		} else {
			res = false;
		}

		return res;
	}

}
