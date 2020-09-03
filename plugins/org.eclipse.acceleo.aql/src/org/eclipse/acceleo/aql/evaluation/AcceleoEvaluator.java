/*******************************************************************************
 * Copyright (c) 2016, 2020  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
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
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil.FilteredSettingsIterator;

/**
 * This implementation of a switch dedicated to Acceleo elements will be used to evaluate their results as we
 * visit them.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluator extends AcceleoSwitch<Object> {

	/**
	 * The plugin ID.
	 */
	private static final String ID = "org.eclipse.acceleo.aql";

	/**
	 * The empty result.
	 */
	private static final String EMPTY_RESULT = "";

	/**
	 * A new line.
	 */
	private static final String NEW_LINE = "\n";

	/** The current evaluation environment. */
	private final IAcceleoEnvironment environment;

	/**
	 * The {@link IQueryEvaluationEngine} used to evaluate AQL expressions.
	 */
	private final IQueryEvaluationEngine aqlEngine;

	/**
	 * The variables stack.
	 */
	private final Deque<Map<String, Object>> variablesStack = new ArrayDeque<Map<String, Object>>();

	/**
	 * The indentation stack.
	 */
	private final Deque<String> indentationStack = new ArrayDeque<String>();

	/**
	 * The last generated line of the last {@link Statement}.
	 */
	private String lastLineOfLastStatement;

	/**
	 * The {@link IQualifiedNameLookupEngine}.
	 */
	private final IQualifiedNameLookupEngine lookupEngine;

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the {@link IAcceleoEnvironment}
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 */
	public AcceleoEvaluator(IAcceleoEnvironment environment, IQualifiedNameLookupEngine lookupEngine) {
		this.environment = environment;
		final IQueryEnvironment queryEnvironment = environment.getQueryEnvironment();
		this.aqlEngine = QueryEvaluation.newEngine(queryEnvironment);
		this.lookupEngine = lookupEngine;
	}

	/**
	 * Generates the given {@link ASTNode} with the given variables.
	 * 
	 * @param node
	 *            the {@link ASTNode} to generate
	 * @param variables
	 *            the variables
	 * @return the generated {@link Object}, can be <code>null</code>
	 */
	public Object generate(ASTNode node, Map<String, Object> variables) {

		final Object res;

		lastLineOfLastStatement = "";
		pushVariables(variables);
		try {
			res = doSwitch(node);
		} finally {
			popVariables();
		}

		return res;
	}

	/**
	 * Pushes the given variables into the stack.
	 * 
	 * @param variables
	 *            the variables to push
	 */
	protected void pushVariables(Map<String, Object> variables) {
		variablesStack.addLast(variables);
	}

	/**
	 * Peeks the last {@link #pushVariables(Map) pushed} variables from the stack.
	 * 
	 * @return the last {@link #pushVariables(Map) pushed} variables from the stack
	 */
	protected Map<String, Object> peekVariables() {
		return variablesStack.peekLast();
	}

	/**
	 * Pops the last {@link #pushVariables(Map) pushed} variables from the stack.
	 * 
	 * @return the last {@link #pushVariables(Map) pushed} variables from the stack
	 */
	protected Map<String, Object> popVariables() {
		return variablesStack.removeLast();
	}

	/**
	 * Gets the variables stack.
	 * 
	 * @return the variables stack
	 */
	protected Deque<Map<String, Object>> getVariablesStack() {
		return variablesStack;
	}

	/**
	 * Pushes the given indentation into the stack.
	 * 
	 * @param indentation
	 *            the indentation to push
	 */
	protected void pushIndentation(String indentation) {
		indentationStack.addLast(indentation);
	}

	/**
	 * Peeks the last {@link #pushIndentation(Map) pushed} indentation from the stack.
	 * 
	 * @return the last {@link #pushIndentation(Map) pushed} indentation from the stack
	 */
	protected String peekIndentation() {
		return indentationStack.peekLast();
	}

	/**
	 * Pops the last {@link #pushIndentation(Map) pushed} indentation from the stack.
	 * 
	 * @return the last {@link #pushIndentation(Map) pushed} indentation from the stack
	 */
	protected String popIndentation() {
		return indentationStack.removeLast();
	}

	/**
	 * Gets the {@link IQueryEvaluationEngine}.
	 * 
	 * @return the {@link IQueryEvaluationEngine}
	 */
	protected IQueryEvaluationEngine getAqlEngine() {
		return aqlEngine;
	}

	/**
	 * Gets the {@link IAcceleoEnvironment}.
	 * 
	 * @return the {@link IAcceleoEnvironment}
	 */
	protected IAcceleoEnvironment getEnvironment() {
		return environment;
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
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"AQL parsing issue", new Object[] {expression });
			diagnostic.addAll(ast.getDiagnostic());
			environment.getGenerationResult().addDiagnostic(diagnostic);
		}

		final EvaluationResult evalResult = aqlEngine.eval(ast, peekVariables());
		if (evalResult.getDiagnostic().getSeverity() != Diagnostic.OK) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(evalResult.getDiagnostic().getSeverity(),
					ID, 0, "AQL evaluation issue", new Object[] {expression, new HashMap<String, Object>(
							peekVariables()) });
			diagnostic.addAll(evalResult.getDiagnostic());
			environment.getGenerationResult().addDiagnostic(diagnostic);
		}

		return evalResult.getResult();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseExpressionStatement(org.eclipse.acceleo.ExpressionStatement)
	 */
	@Override
	public String caseExpressionStatement(ExpressionStatement expressionStatement) {
		final String res;

		// TODO replace all possible new lines with the right one
		String expressionValue = toString(doSwitch(expressionStatement.getExpression()));
		final boolean endsWithNewLine = expressionValue.endsWith(NEW_LINE);
		final String indentation = peekIndentation();
		if (!indentation.isEmpty()) {
			expressionValue = expressionValue.replace(NEW_LINE, NEW_LINE + indentation);
			if (endsWithNewLine) {
				expressionValue = expressionValue.substring(0, expressionValue.length() - indentation
						.length());
			}
		}
		if (expressionStatement.isNewLineNeeded() && !endsWithNewLine) {
			if (lastLineOfLastStatement.isEmpty()) {
				res = indentation + expressionValue + NEW_LINE;
			} else {
				res = expressionValue + NEW_LINE;
			}
			lastLineOfLastStatement = "";
		} else {
			if (lastLineOfLastStatement.isEmpty()) {
				res = indentation + expressionValue;
			} else {
				res = expressionValue;
			}
			final int lastIndexOfNewLine = res.lastIndexOf(NEW_LINE);
			if (lastIndexOfNewLine != -1) {
				lastLineOfLastStatement = res.substring(res.lastIndexOf(NEW_LINE) + NEW_LINE.length(), res
						.length());
			} else {
				lastLineOfLastStatement = res;
			}
		}

		return res;
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
				final String start = lookupEngine.getResolver().getQualifiedName(module);
				lookupEngine.pushImportsContext(start, start);
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
	public String caseTemplate(Template template) {
		final String res;

		pushIndentation(lastLineOfLastStatement);
		try {
			final String templateText = (String)doSwitch(template.getBody());
			if (template.getPost() != null) {
				final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
				variables.put(AcceleoUtil.getTemplateImplicitVariableName(), templateText);
				pushVariables(variables);
				try {
					res = toString(doSwitch(template.getPost()));
				} finally {
					popVariables();
				}
			} else {
				res = templateText;
			}
		} finally {
			popIndentation();
		}

		return res;
	}

	@Override
	public Object caseQuery(Query query) {
		final Object res;

		res = doSwitch(query.getBody());

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseTextStatement(org.eclipse.acceleo.TextStatement)
	 */
	@Override
	public String caseTextStatement(TextStatement textStatement) {
		final String res;
		if (textStatement.isNewLineNeeded()) {
			if (lastLineOfLastStatement.isEmpty()) {
				res = peekIndentation() + textStatement.getValue() + NEW_LINE;
			} else {
				res = textStatement.getValue() + NEW_LINE;
			}
			lastLineOfLastStatement = "";
		} else {
			if (lastLineOfLastStatement.isEmpty()) {
				res = peekIndentation() + textStatement.getValue();
				lastLineOfLastStatement = res;
			} else {
				res = textStatement.getValue();
				lastLineOfLastStatement = peekIndentation() + res;
			}
		}

		return res;
	}

	@Override
	public String caseBlock(Block block) {
		final StringBuilder builder = new StringBuilder();

		for (Statement statement : block.getStatements()) {
			builder.append((String)doSwitch(statement));
		}

		return builder.toString();
	}

	@Override
	public String caseComment(Comment comment) {
		return EMPTY_RESULT;
	}

	@Override
	public String caseLetStatement(LetStatement letStatement) {
		final String res;

		final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
		for (Binding binding : letStatement.getVariables()) {
			final String name = binding.getName();
			final Object value = doSwitch(binding.getInitExpression());
			variables.put(name, value);
		}

		pushVariables(variables);
		pushIndentation(lastLineOfLastStatement);
		try {
			res = (String)doSwitch(letStatement.getBody());
		} finally {
			popIndentation();
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
	public String caseFileStatement(FileStatement fileStatement) {
		final String res;

		Object uriObject = doSwitch(fileStatement.getUrl());
		if (uriObject == null) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"The URL can't be null", new Object[] {fileStatement.getUrl(),
							new HashMap<String, Object>(peekVariables()) });
			environment.getGenerationResult().addDiagnostic(diagnostic);

			res = EMPTY_RESULT;
		} else {
			final OpenModeKind mode = fileStatement.getMode();
			final Charset charset = getCharset(fileStatement);
			final URI uri = URI.createURI(toString(uriObject), true).resolve(environment.getDestination());
			try {
				// FIXME line delimiter
				environment.openWriter(uri, mode, charset, NEW_LINE);
				lastLineOfLastStatement = "";
				pushIndentation(lastLineOfLastStatement);
				try {
					final String content = (String)doSwitch(fileStatement.getBody());
					environment.write(content);
				} finally {
					popIndentation();
					environment.closeWriter();
				}
			} catch (IOException e) {
				final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0, e
						.getMessage(), new Object[] {fileStatement, new HashMap<String, Object>(
								peekVariables()) });
				environment.getGenerationResult().addDiagnostic(diagnostic);
			}

			res = EMPTY_RESULT;
		}

		return res;
	}

	/**
	 * Gets the {@link Charset} of the given {@link FileStatement}.
	 * 
	 * @param fileStatement
	 *            the {@link FilteredSettingsIterator}
	 * @return the {@link Charset} of the given {@link FileStatement}
	 */
	private Charset getCharset(FileStatement fileStatement) {
		final Charset charset;
		if (fileStatement.getCharset() != null) {
			final Object charsetValue = doSwitch(fileStatement.getCharset());
			if (charsetValue != null) {
				final String charsetString = toString(charsetValue);
				Charset defaultCharset = StandardCharsets.UTF_8;
				try {
					defaultCharset = Charset.forName(charsetString);
				} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
					final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ID, 0, e
							.getMessage() + " fallback to UTF-8", new Object[] {fileStatement.getUrl(),
									new HashMap<String, Object>(peekVariables()) });
					environment.getGenerationResult().addDiagnostic(diagnostic);
				}
				charset = defaultCharset;
			} else {
				final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ID, 0,
						"The Charset can't be null, fallback to UTF-8", new Object[] {fileStatement.getUrl(),
								new HashMap<String, Object>(peekVariables()) });
				environment.getGenerationResult().addDiagnostic(diagnostic);
				charset = StandardCharsets.UTF_8;
			}
		} else {
			charset = StandardCharsets.UTF_8;
		}
		return charset;
	}

	@Override
	public String caseIfStatement(IfStatement ifStatement) {
		final String res;

		final Object condition = doSwitch(ifStatement.getCondition());
		if (condition instanceof Boolean) {
			pushIndentation(lastLineOfLastStatement);
			try {
				if (Boolean.TRUE.equals(condition)) {
					res = (String)doSwitch(ifStatement.getThen());
				} else if (ifStatement.getElse() != null) {
					res = (String)doSwitch(ifStatement.getElse());
				} else {
					res = EMPTY_RESULT;
				}
			} finally {
				popIndentation();
			}
		} else {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"The expression must be evaluated to a boolean not: " + toString(condition),
					new Object[] {ifStatement.getCondition(), new HashMap<String, Object>(peekVariables()) });
			environment.getGenerationResult().addDiagnostic(diagnostic);
			res = EMPTY_RESULT;
		}

		return res;
	}

	@Override
	public String caseForStatement(ForStatement forStatement) {
		final StringBuilder builder = new StringBuilder();

		final List<Object> iteration = new ArrayList<Object>();
		final Object value = doSwitch(forStatement.getBinding().getInitExpression());
		if (value instanceof Collection) {
			iteration.addAll((Collection<?>)value);
		} else if (value != null) {
			iteration.add(value);
		} else {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ID, 0,
					"The expression should not be null", new Object[] {forStatement.getBinding()
							.getInitExpression(), new HashMap<String, Object>(peekVariables()) });
			environment.getGenerationResult().addDiagnostic(diagnostic);
		}
		if (!iteration.isEmpty()) {
			final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
			final String name = forStatement.getBinding().getName();
			pushVariables(variables);
			pushIndentation(lastLineOfLastStatement);
			try {
				// the first value is generated on its own
				// to insert separators
				final Object firstValue = iteration.remove(0);
				variables.put(name, firstValue);
				builder.append(doSwitch(forStatement.getBody()));
				for (Object val : iteration) {
					variables.put(name, val);
					if (forStatement.getSeparator() != null) {
						builder.append(toString(doSwitch(forStatement.getSeparator())));
					}
					builder.append(doSwitch(forStatement.getBody()));
				}
			} finally {
				popIndentation();
				popVariables();
			}
		}

		return builder.toString();
	}

	@Override
	public Object caseProtectedArea(ProtectedArea object) {
		// TODO Auto-generated method stub
		return super.caseProtectedArea(object);
	}

	@Override
	public Object caseError(Error error) {
		final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
				"Acceleo parsing error see validation for more details", new Object[] {error });
		environment.getGenerationResult().addDiagnostic(diagnostic);
		return EMPTY_RESULT;
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
