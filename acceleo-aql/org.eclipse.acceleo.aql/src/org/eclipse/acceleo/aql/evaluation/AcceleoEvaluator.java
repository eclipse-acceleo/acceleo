/*******************************************************************************
 * Copyright (c) 2016, 2017  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;

/**
 * This implementation of a switch dedicated to Acceleo elements will be used to evaluate their results as we
 * visit them.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluator extends AcceleoSwitch<Object> {
	/**
	 * The cases are not expected to ever return <code>null</code>; this is the placeholder for "no return
	 * value".
	 */
	private static final String EMPTY_RETURN = "";

	/** The current evaluation environment. */
	private IAcceleoEnvironment environment;

	/**
	 * The {@link IQueryEvaluationEngine} used to evaluate AQL expressions.
	 */
	private final IQueryEvaluationEngine aqlEngine;

	/**
	 * The variables stack.
	 */
	private final Deque<Map<String, Object>> variablesStack;

	/**
	 * The current destination {@link URI}.
	 */
	private URI destination;

	/**
	 * The current {@link GenerationResult}.
	 */
	private GenerationResult generationResult;

	/**
	 * Instantiates an evaluation switch given the acceleo environment to consider.
	 * 
	 * @param environment
	 *            The current evaluation environment.
	 */
	public AcceleoEvaluator(IAcceleoEnvironment environment) {
		this.environment = environment;
		final IQueryEnvironment env = environment.getQueryEnvironment();
		this.aqlEngine = QueryEvaluation.newEngine(env);
		this.variablesStack = new ArrayDeque<>();
	}

	/**
	 * Generates the given {@link Module} with the given variables.
	 * 
	 * @param module
	 *            the {@link Module} to generate
	 * @param variables
	 *            the variables
	 * @param dest
	 *            the destination {@link URI}
	 * @return the {@link GenerationResult} produced by the generation
	 */
	public GenerationResult generate(Module module, Map<String, Object> variables, URI dest) {
		generationResult = new GenerationResult();

		this.destination = dest;
		pushVariables(variables);
		try {
			doSwitch(module);
		} finally {
			popVariables();
		}

		return generationResult;
	}

	/**
	 * Pushes the given variables into the stack.
	 * 
	 * @param variables
	 *            the variables to push
	 */
	public void pushVariables(Map<String, Object> variables) {
		variablesStack.addLast(variables);
	}

	/**
	 * Peeks the last {@link #pushVariables(Map) pushed} variables from the stack.
	 * 
	 * @return the last {@link #pushVariables(Map) pushed} variables from the stack
	 */
	public Map<String, Object> peekVariables() {
		return variablesStack.peekLast();
	}

	/**
	 * Pops the last {@link #pushVariables(Map) pushed} variables from the stack.
	 * 
	 * @return the last {@link #pushVariables(Map) pushed} variables from the stack
	 */
	public Map<String, Object> popVariables() {
		return variablesStack.removeLast();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseExpression(org.eclipse.acceleo.Expression)
	 */
	@Override
	public Object caseExpression(Expression expression) {
		AstResult ast = expression.getAst();
		if (ast.getDiagnostic().getSeverity() == Diagnostic.ERROR) {
			// FIXME throw / log
		}

		// FIXME log evaluation problems
		return aqlEngine.eval(ast, variablesStack.peekLast()).getResult();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseExpressionStatement(org.eclipse.acceleo.ExpressionStatement)
	 */
	@Override
	public Object caseExpressionStatement(ExpressionStatement expressionStatement) {
		return doSwitch(expressionStatement.getExpression());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseModule(org.eclipse.acceleo.Module)
	 */
	@Override
	public Object caseModule(Module module) {
		StringBuilder builder = new StringBuilder();
		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template && ((Template)element).isMain()) {
				environment.pushImport(environment.getModuleQualifiedName(module), element);
				builder.append(doSwitch(element));
			}
		}
		// FIXME return the text generated by the templates? the list of generated files?
		return builder.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseTemplate(org.eclipse.acceleo.Template)
	 */
	@Override
	public Object caseTemplate(Template template) {
		final Object res;

		try {
			res = doSwitch(template.getBody());
		} finally {
			environment.popStack(template);
		}

		return res;
	}

	@Override
	public Object caseQuery(Query query) {
		final Object res;

		try {
			res = doSwitch(query.getBody());
		} finally {
			environment.popStack(query);
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseTextStatement(org.eclipse.acceleo.TextStatement)
	 */
	@Override
	public Object caseTextStatement(TextStatement textStatement) {
		return textStatement.getValue();
	}

	@Override
	public Object caseBlock(Block block) {
		for (Statement stmt : block.getStatements()) {
			final Object value = doSwitch(stmt);
			environment.write(toString(value));
		}

		return EMPTY_RETURN;
	}

	@Override
	public Object caseComment(Comment comment) {
		return "";
	}

	@Override
	public Object caseLetStatement(LetStatement letStatement) {
		final Object res;

		final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
		for (Binding binding : letStatement.getVariables()) {
			final String name = binding.getName();
			final Object value = doSwitch(binding.getInitExpression());
			variables.put(name, value);
		}

		pushVariables(variables);
		try {
			res = doSwitch(letStatement.getBody());
		} finally {
			popVariables();
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseFileStatement(org.eclipse.acceleo.FileStatement)
	 */
	@Override
	public Object caseFileStatement(FileStatement fileStatement) {
		Object uriObject = doSwitch(fileStatement.getUrl());
		if (uriObject == null) {
			// FIXME log properly
			return null;
		}
		final Object fileCharset;
		final OpenModeKind mode = fileStatement.getMode();
		if (fileStatement.getCharset() != null) {
			// TODO add syntax for charset
			final Object charsetValue = doSwitch(fileStatement.getCharset());
			if (charsetValue != null) {
				fileCharset = charsetValue;
			} else {
				// FIXME log ?
				fileCharset = "UTF-8";
			}
		} else {
			fileCharset = "UTF-8";
		}

		final URI uri = URI.createURI(toString(uriObject), true).resolve(destination);
		// FIXME line delimiter
		environment.openWriter(uri, mode, toString(fileCharset), "\n");
		generationResult.getGeneratedFiles().add(uri);
		try {
			doSwitch(fileStatement.getBody());
		} finally {
			environment.closeWriter();
		}

		return EMPTY_RETURN;
	}

	@Override
	public Object caseIfStatement(IfStatement ifStatement) {
		Object condition = doSwitch(ifStatement.getCondition());
		if (condition instanceof Boolean) {
			final Object result;
			if (Boolean.TRUE.equals(condition)) {
				result = doSwitch(ifStatement.getThen());
			} else {
				result = doSwitch(ifStatement.getElse());
			}
			return result;
		} else {
			// FIXME log, evaluate to "false", ... ?
			return EMPTY_RETURN;
		}
	}

	@Override
	public Object caseForStatement(ForStatement forStatement) {
		return super.caseForStatement(forStatement);
	}

	/**
	 * Converts the given {@link Object} to a {@link String}.
	 * 
	 * @param object
	 *            the {@link Object} to convert
	 * @return the {@link String} representation of the given {@link Object}
	 */
	private String toString(Object object) {
		final StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				buffer.append(toString(childrenIterator.next()));
			}
		} else if (object != null) {
			buffer.append(object.toString());
		}
		return buffer.toString();
	}

}
