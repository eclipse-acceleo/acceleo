/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.ide.Activator;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.debug.AbstractDSLDebugger;
import org.eclipse.acceleo.debug.DSLSource;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.util.StackFrame;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.swt.widgets.Display;

public class AcceleoDebugger extends AbstractDSLDebugger {

	/**
	 * Acceleo Debugger.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class AcceleoDebugEvaluator extends AcceleoEvaluator {

		/**
		 * Constructor.
		 * 
		 * @param environment
		 *            the {@link IllegalAccessError}
		 */
		AcceleoDebugEvaluator(IAcceleoEnvironment environment) {
			super(environment);
		}

		@Override
		public Object generate(ASTNode node, Map<String, Object> variables) {
			return super.generate(node, variables);
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
	 * The {@link IAcceleoEnvironment}.
	 */
	private IAcceleoEnvironment environment;

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
		final URI destination = URI.createURI((String)arguments.get(DESTINATION));
		environment = new AcceleoEnvironment(new DefaultGenerationStrategy(), destination);
		final IProject project = LSPEclipseUtils.findResourceFor((String)arguments.get(MODULE)).getProject();
		environment.setModuleResolver(Activator.getPlugin().createQualifiedNameResolver(environment
				.getQueryEnvironment(), project));
		try {
			astResult = environment.getModule(java.net.URI.create(moduleURI.toString()).toURL()).getAst();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final ResourceSet resourceSetForModels = new ResourceSetImpl();
		model = resourceSetForModels.getResource(modelURI, true);
	}

	/**
	 * Gets the content of the given {@link InputStream}.
	 * 
	 * @param stream
	 *            the {@link InputStream}
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
	 * @return a {@link CharSequence} of the content of the given {@link InputStream}
	 * @throws IOException
	 *             if the {@link InputStream} can't be read
	 */
	public String getContent(InputStream stream, String charsetName) throws IOException {
		final int len = 8192;
		StringBuilder res = new StringBuilder(len);
		if (len != 0) {
			try (InputStreamReader input = new InputStreamReader(new BufferedInputStream(stream),
					charsetName)) {
				char[] buffer = new char[len];
				int length = input.read(buffer);
				while (length != -1) {
					res.append(buffer, 0, length);
					length = input.read(buffer);
				}
				input.close();
			}
		}
		return res.toString();
	}

	@Override
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				final Module module = astResult.getModule();
				final long threadID = Thread.currentThread().getId();
				spawnRunningThread(threadID, Thread.currentThread().getName(), module);
				try {
					if (isNoDebug()) {
						generateNoDebug(environment, module, model);
					} else {
						evaluator = new AcceleoDebugEvaluator(environment);
						AcceleoUtil.generate(evaluator, environment, module, model);
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
							terminated();
						}
					});
				}

				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				if (workspace != null) {
					IContainer container = workspace.getRoot().getContainerForLocation(new Path(environment
							.getDestination().toFileString()));
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
		}, "Acceleo Debug Thread").start();
	}

	protected void generateNoDebug(IAcceleoEnvironment environment, Module module, Resource model) {
		AcceleoUtil.generate(new AcceleoEvaluator(environment), environment, module, model);
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
			moduleAstResult = environment.getModule(new URL("file://" + path)).getAst();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final EObject res;
		EObject instruction = moduleAstResult.getAstNode((int)line - 1, (int)column - 1);
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
			URL moduleSourceURL = environment.getModuleSourceURL(moduleAstResult.getModule());
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
}
