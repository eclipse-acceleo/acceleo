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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.ASTNode;
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
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

/**
 * This implementation of a switch dedicated to Acceleo elements will be used to evaluate their results as we
 * visit them.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluator extends AcceleoSwitch<Object> {

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
	 * Generates the given {@link ASTNode} with the given variables.
	 * 
	 * @param node
	 *            the {@link ASTNode} to generate
	 * @param variables
	 *            the variables
	 * @param listeners
	 *            the {@link List} of {@link IAcceleoEvaluationListener}
	 */
	public void generate(ASTNode node, Map<String, Object> variables) {
		pushVariables(variables);
		try {
			doSwitch(node);
		} finally {
			popVariables();
		}
	}

	@Override
	public Object doSwitch(EObject eObject) {
		final Object res;

		for (IAcceleoEvaluationListener listener : environment.getEvaluationListeners()) {
			listener.startEvaluation((ASTNode)eObject, environment, peekVariables());
		}

		res = super.doSwitch(eObject);

		for (IAcceleoEvaluationListener listener : environment.getEvaluationListeners()) {
			listener.endEvaluation((ASTNode)eObject, environment, peekVariables(), res);
		}

		return res;
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
		final EvaluationResult evalResult = aqlEngine.eval(ast, peekVariables());

		return evalResult.getResult();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseExpressionStatement(org.eclipse.acceleo.ExpressionStatement)
	 */
	@Override
	public String caseExpressionStatement(ExpressionStatement expressionStatement) {
		return toString(doSwitch(expressionStatement.getExpression()));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseModule(org.eclipse.acceleo.Module)
	 */
	@Override
	public Void caseModule(Module module) {

		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template && ((Template)element).isMain()) {
				environment.pushImport(environment.getModuleQualifiedName(module), element);
				doSwitch(element);
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseTemplate(org.eclipse.acceleo.Template)
	 */
	@Override
	public Void caseTemplate(Template template) {
		try {
			doSwitch(template.getBody());
		} finally {
			environment.popStack(template);
		}

		return null;
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
	public String caseTextStatement(TextStatement textStatement) {
		return textStatement.getValue();
	}

	@Override
	public Void caseBlock(Block block) {
		for (Statement stmt : block.getStatements()) {
			doSwitch(stmt);
		}

		return null;
	}

	@Override
	public Void caseComment(Comment comment) {
		return null;
	}

	@Override
	public Void caseLetStatement(LetStatement letStatement) {
		final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
		for (Binding binding : letStatement.getVariables()) {
			final String name = binding.getName();
			final Object value = doSwitch(binding.getInitExpression());
			variables.put(name, value);
		}

		pushVariables(variables);
		try {
			doSwitch(letStatement.getBody());
		} finally {
			popVariables();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseFileStatement(org.eclipse.acceleo.FileStatement)
	 */
	@Override
	public Void caseFileStatement(FileStatement fileStatement) {
		final Object uriObject = doSwitch(fileStatement.getUrl());
		if (uriObject == null) {
			// FIXME log properly
		} else {
			final Expression charset = fileStatement.getCharset();
			if (charset != null) {
				doSwitch(charset);
			}
			doSwitch(fileStatement.getBody());
		}

		return null;
	}

	@Override
	public Void caseIfStatement(IfStatement ifStatement) {
		final Object condition = doSwitch(ifStatement.getCondition());
		if (condition instanceof Boolean) {
			if (Boolean.TRUE.equals(condition)) {
				doSwitch(ifStatement.getThen());
			} else {
				doSwitch(ifStatement.getElse());
			}
		} else {
			// FIXME log, evaluate to "false", ... ?
		}

		return null;
	}

	@Override
	public Void caseForStatement(ForStatement forStatement) {
		final List<Object> iteration = new ArrayList<Object>();
		final Object value = doSwitch(forStatement.getBinding().getInitExpression());
		if (value instanceof Collection) {
			iteration.addAll((Collection<?>)value);
		} else if (value != null) {
			iteration.add(value);
		} else {
			// FIXME log null value ?
		}
		final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
		final String name = forStatement.getBinding().getName();
		pushVariables(variables);
		try {
			for (Object val : iteration) {
				variables.put(name, val);
				doSwitch(forStatement.getBody());
			}
		} finally {
			popVariables();
		}

		return null;
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
