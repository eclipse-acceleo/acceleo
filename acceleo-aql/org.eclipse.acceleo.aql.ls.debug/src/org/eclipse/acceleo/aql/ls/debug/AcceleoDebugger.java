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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.debug.AbstractDSLDebugger;
import org.eclipse.acceleo.debug.DSLSource;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class AcceleoDebugger extends AbstractDSLDebugger {

	/**
	 * Acceleo Debugger.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class AcceleoDebugEvaluator extends AcceleoEvaluator {

		/**
		 * The variables stack.
		 */
		private final Deque<EObject> contextStack = new ArrayDeque<EObject>();

		/**
		 * The variables stack (duplicated reference to super.variablesStack).
		 */
		private final Deque<Map<String, Object>> variablesStack;

		/**
		 * The current instruction.
		 */
		private EObject currentInstruction;

		/**
		 * Constructor.
		 * 
		 * @param environment
		 *            the {@link IllegalAccessError}
		 */
		AcceleoDebugEvaluator(IAcceleoEnvironment environment) {
			super(environment);
			variablesStack = getVariablesStack();
		}

		@Override
		public Object generate(ASTNode node, Map<String, Object> variables) {
			currentInstruction = node;
			return super.generate(node, variables);
		}

		@Override
		public Object doSwitch(EObject eObject) {
			currentInstruction = eObject;
			if (!AcceleoDebugger.this.control(Thread.currentThread().getId(), eObject)) {
				Thread.currentThread().interrupt();
			}
			return super.doSwitch(eObject);
		}

		@Override
		protected void pushVariables(Map<String, Object> variables) {
			super.pushVariables(variables);
			if (currentInstruction instanceof Expression) {
				contextStack.addLast(getContainingStatement((Expression)currentInstruction));
			} else {
				contextStack.addLast(currentInstruction);
			}
		}

		/**
		 * Gets the containing {@link Statement}.
		 * 
		 * @param expression
		 *            the {@link Expression}
		 * @return the containing {@link Statement} if any, <code>null</code> otherwise
		 */
		private EObject getContainingStatement(Expression expression) {
			Statement res = null;

			EObject current = expression.eContainer();
			while (current != null) {
				if (current instanceof Statement) {
					res = (Statement)current;
					break;
				}
				current = current.eContainer();
			}

			return res;
		}

		@Override
		protected Map<String, Object> popVariables() {
			contextStack.removeLast();
			return super.popVariables();
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
		final AcceleoParser parser = new AcceleoParser(environment.getQueryEnvironment());
		// TODO should directly ask the module from the environment
		final ResourceSet resourceSetForModels = new ResourceSetImpl();
		try (InputStream is = resourceSetForModels.getURIConverter().createInputStream(moduleURI)) {
			// TODO namespace
			astResult = parser.parse(getContent(is, "UTF-8"), "org::todo");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
						AcceleoUtil.generate(new AcceleoEvaluator(environment), environment, module, model);
					} else {
						evaluator = new AcceleoDebugEvaluator(environment);
						AcceleoUtil.generate(evaluator, environment, module, model);
					}
				} finally {
					terminate(threadID);
					terminated();
				}
			}
		}, "Acceleo Debug Thread").start();
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
	public List<EObject> getStackFrame(Long threadID) {
		final List<EObject> res = new ArrayList<EObject>();

		// TODO multi threading by peeking the write evaluator ?
		final Iterator<EObject> instructionIterator = evaluator.contextStack.iterator();
		while (instructionIterator.hasNext()) {
			res.add(instructionIterator.next());
		}

		return res;
	}

	@Override
	public List<Map<String, Object>> getStackVariables(Long threadID) {
		final List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();

		// TODO multi threading by peeking the write evaluator ?
		final Iterator<Map<String, Object>> variablesIterator = evaluator.variablesStack.iterator();
		while (variablesIterator.hasNext()) {
			res.add(variablesIterator.next());
		}

		return res;
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
	public EObject getNextInstruction(Long threadID, EObject currentInstruction, Stepping stepping) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EObject getInstruction(String path, long line, long column) {
		// TODO get the astResult from the right module
		final AcceleoAstResult moduleAstResult = astResult;

		return moduleAstResult.getAstNode((int)line - 1, (int)column - 1);
	}

	@Override
	public EObject getCurrentInstruction(Long threadID) {
		// TODO multi threading

		return evaluator.currentInstruction;
	}

	@Override
	public DSLSource getSource(EObject instruction) {
		// TODO get the astResult from the right module
		final AcceleoAstResult moduleAstResult = astResult;
		// TODO get the path from the right module
		final String path = "/home/development/bin/eclipse-2019-12-Acceleo-query/runtime-New_configuration/test/src/test/test.mtl";

		final DSLSource res;
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

		return res;
	}

}
