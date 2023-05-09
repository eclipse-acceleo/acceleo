/*******************************************************************************
 * Copyright (c) 2016, 2023  Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.AcceleoASTNode;
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
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
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

	/**
	 * The {@link DateFormat} used to log lost {@link UserContent}.
	 */
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

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
	 * Tells if the current {@link Block} is {@link Block#isInlined() inlined}.
	 */
	private boolean inlinedBlock;

	/**
	 * The destination {@link URI}.
	 */
	private URI destination;

	/** This will hold the writer stack for the file blocks. */
	private final Deque<IAcceleoWriter> writers = new ArrayDeque<IAcceleoWriter>();

	/** This will hold the {@link Set} of encountered {@link ProtectedArea} IDs. */
	private final Deque<Set<String>> protectedAreaIDs = new ArrayDeque<Set<String>>();

	/** The current generation strategy. */
	private IAcceleoGenerationStrategy generationStrategy;

	/**
	 * The {@link GenerationResult}.
	 */
	private GenerationResult generationResult;

	/**
	 * Constructor.
	 * 
	 * @param other
	 *            the other {@link AcceleoEvaluator}.
	 */
	public AcceleoEvaluator(AcceleoEvaluator other) {
		this(other.lookupEngine);
		destination = other.destination;
		generationStrategy = other.generationStrategy;
		generationResult = other.generationResult;
	}

	/**
	 * Constructor.
	 * 
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 */
	public AcceleoEvaluator(IQualifiedNameLookupEngine lookupEngine) {
		this.aqlEngine = QueryEvaluation.newEngine((IQueryEnvironment)lookupEngine.getQueryEnvironment());
		this.lookupEngine = lookupEngine;
	}

	/**
	 * Generates the given {@link AcceleoASTNode} with the given variables.
	 * 
	 * @param node
	 *            the {@link AcceleoASTNode} to generate
	 * @param variables
	 *            the variables
	 * @param strategy
	 *            the IAcceleoGenerationStrategy
	 * @param destinationURI
	 *            the destination {@link URI}
	 * @return the generated {@link Object}, can be <code>null</code>
	 */
	public Object generate(AcceleoASTNode node, Map<String, Object> variables,
			IAcceleoGenerationStrategy strategy, URI destinationURI) {

		final Object res;

		destination = destinationURI;
		generationStrategy = strategy;

		final String savedLastLineOfLastStatement = lastLineOfLastStatement;
		final boolean savedInlinedBlock = inlinedBlock;
		lastLineOfLastStatement = "";
		inlinedBlock = false;
		if (generationResult == null) {
			generationResult = new GenerationResult();
		}

		pushVariables(variables);
		try {
			res = doSwitch(node);
		} finally {
			popVariables();
			lastLineOfLastStatement = savedLastLineOfLastStatement;
			inlinedBlock = savedInlinedBlock;
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
	 * @param block
	 *            the {@link Block} to indent
	 * @param indentation
	 *            the indentation to push
	 */
	protected void pushIndentation(Block block, String indentation) {
		if (block.isInlined()) {
			inlinedBlock = true;
			indentationStack.addLast("");
		} else {
			inlinedBlock = false;
			indentationStack.addLast(indentation);
		}
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
			generationResult.addDiagnostic(diagnostic);
		}

		final EvaluationResult evalResult = aqlEngine.eval(ast, peekVariables());
		if (evalResult.getDiagnostic().getSeverity() != Diagnostic.OK) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(evalResult.getDiagnostic().getSeverity(),
					ID, 0, "AQL evaluation issue", new Object[] {expression, new HashMap<String, Object>(
							peekVariables()) });
			diagnostic.addAll(evalResult.getDiagnostic());
			generationResult.addDiagnostic(diagnostic);
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

		final String indentation;
		if (inlinedBlock) {
			indentation = "";
		} else {
			if (lastLineOfLastStatement.isEmpty()) {
				indentation = peekIndentation();
			} else {
				indentation = lastLineOfLastStatement;
			}
		}
		// TODO replace all possible new lines with the right one
		String expressionValue = toString(doSwitch(expressionStatement.getExpression()));
		final boolean endsWithNewLine = expressionValue.endsWith(NEW_LINE);
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
				lastLineOfLastStatement = res.substring(lastIndexOfNewLine + NEW_LINE.length(), res.length());
			} else {
				lastLineOfLastStatement = lastLineOfLastStatement + res;
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

		pushIndentation(template.getBody(), lastLineOfLastStatement);
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
				if (!textStatement.getValue().isEmpty()) {
					res = peekIndentation() + textStatement.getValue() + NEW_LINE;
				} else {
					// empty text with new line at the beginning of a line is a no operation
					res = EMPTY_RESULT;
				}
			} else {
				res = textStatement.getValue() + NEW_LINE;
				lastLineOfLastStatement = "";
			}
		} else {
			if (lastLineOfLastStatement.isEmpty()) {
				res = peekIndentation() + textStatement.getValue();
				lastLineOfLastStatement = res;
			} else {
				res = textStatement.getValue();
				lastLineOfLastStatement = lastLineOfLastStatement + res;
			}
		}

		return res;
	}

	@Override
	public Object caseNewLineStatement(NewLineStatement newLineStatement) {
		final String res;

		if (newLineStatement.isIndentationNeeded()) {
			res = NEW_LINE + peekIndentation();
		} else {
			res = NEW_LINE;
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
		pushIndentation(letStatement.getBody(), lastLineOfLastStatement);
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
			generationResult.addDiagnostic(diagnostic);

			res = EMPTY_RESULT;
		} else {
			final OpenModeKind mode = fileStatement.getMode();
			final Charset charset = getCharset(fileStatement);
			final URI uri = URI.createURI(toString(uriObject), true).resolve(destination);
			try {
				// FIXME line delimiter
				openWriter(uri, mode, charset, NEW_LINE);
				lastLineOfLastStatement = "";
				pushIndentation(fileStatement.getBody(), lastLineOfLastStatement);
				try {
					final String content = (String)doSwitch(fileStatement.getBody());
					write(content);
				} finally {
					popIndentation();
					closeWriter(fileStatement);
				}
			} catch (IOException e) {
				final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0, e
						.getMessage(), new Object[] {fileStatement, new HashMap<String, Object>(
								peekVariables()) });
				generationResult.addDiagnostic(diagnostic);
			}

			res = EMPTY_RESULT;
		}

		return res;
	}

	/**
	 * Opens a writer for the given file {@link URI}.
	 * 
	 * @param uri
	 *            The {@link URI} for which we need a writer.
	 * @param openMode
	 *            The {@link OpenModeKind} in which to open the file.
	 * @param charset
	 *            The {@link Charset} for the target file.
	 * @param lineDelimiter
	 *            Line delimiter that should be used for that file.
	 * @throws IOException
	 *             if the {@link IAcceleoWriter} can't be opened
	 */
	private void openWriter(URI uri, OpenModeKind openMode, Charset charset, String lineDelimiter)
			throws IOException {
		final IAcceleoWriter writer = generationStrategy.createWriterFor(uri, openMode, charset,
				lineDelimiter);
		writers.addLast(writer);
		protectedAreaIDs.addLast(new HashSet<>());
		generationResult.getGeneratedFiles().add(writer.getTargetURI());
	}

	/**
	 * Closes the last {@link #openWriter(String, OpenModeKind, String, String) opened} writer.
	 * 
	 * @param fileStatement
	 *            the {@link FileStatement} closing the current {@link IAcceleoWriter}
	 * @throws IOException
	 *             if an {@link IAcceleoWriter} open, write, or close can't be performed
	 */
	private void closeWriter(FileStatement fileStatement) throws IOException {
		final IAcceleoGenerationStrategy strategy = getGenerationStrategy();
		writeLostFiles(fileStatement, strategy);

		final IAcceleoWriter writer = writers.removeLast();
		strategy.closeWriter(writer);
		protectedAreaIDs.removeLast();
	}

	/**
	 * Writes lost files for the given {@link FileStatement} using the given
	 * {@link IAcceleoGenerationStrategy}.
	 * 
	 * @param fileStatement
	 *            the {@link FileStatement} that ended
	 * @param strategy
	 *            the {@link IAcceleoGenerationStrategy}
	 * @throws IOException
	 *             if the {@link IAcceleoWriter} open, write, or close can't be performed
	 */
	private void writeLostFiles(FileStatement fileStatement, IAcceleoGenerationStrategy strategy)
			throws IOException {
		final URI targetURI = getTargetURI();
		final Charset targetCharset = getTargetCharset();
		final Map<String, List<String>> remainingProtectedAreas = strategy.consumeAllProtectedAreas(
				targetURI);
		for (Entry<String, List<String>> entry : remainingProtectedAreas.entrySet()) {
			String lostID = entry.getKey();
			final IAcceleoWriter lostWriter = strategy.createWriterForLostContent(targetURI, lostID,
					targetCharset, NEW_LINE);
			try {
				for (String lostContent : entry.getValue()) {
					final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ID, 0,
							"Lost file generated: " + lostWriter.getTargetURI(), new Object[] {fileStatement,
									new HashMap<String, Object>(peekVariables()) });
					generationResult.addDiagnostic(diagnostic);
					lostWriter.append(FORMAT.format(new Date()) + " - Lost user content " + lostID
							+ NEW_LINE);
					lostWriter.append(lostContent + NEW_LINE);
					generationResult.getLostFiles().add(lostWriter.getTargetURI());
				}
			} finally {
				strategy.closeWriter(lostWriter);
			}
		}
	}

	/**
	 * Writes the given {@link String} to the last {@link #openWriter(String, OpenModeKind, String, String)
	 * opened} writer.
	 * 
	 * @param text
	 *            the text to write
	 * @throws IOException
	 *             if the writer can't be written
	 */
	private void write(String text) throws IOException {
		IAcceleoWriter writer = writers.peekLast();
		writer.append(text);
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
					generationResult.addDiagnostic(diagnostic);
				}
				charset = defaultCharset;
			} else {
				final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ID, 0,
						"The Charset can't be null, fallback to UTF-8", new Object[] {fileStatement.getUrl(),
								new HashMap<String, Object>(peekVariables()) });
				generationResult.addDiagnostic(diagnostic);
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
			if (Boolean.TRUE.equals(condition)) {
				pushIndentation(ifStatement.getThen(), lastLineOfLastStatement);
				try {
					res = (String)doSwitch(ifStatement.getThen());
				} finally {
					popIndentation();
				}
			} else if (ifStatement.getElse() != null) {
				pushIndentation(ifStatement.getElse(), lastLineOfLastStatement);
				try {
					res = (String)doSwitch(ifStatement.getElse());
				} finally {
					popIndentation();
				}
			} else {
				res = EMPTY_RESULT;
			}
		} else {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"The expression must be evaluated to a boolean not: " + toString(condition),
					new Object[] {ifStatement.getCondition(), new HashMap<String, Object>(peekVariables()) });
			generationResult.addDiagnostic(diagnostic);
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
			generationResult.addDiagnostic(diagnostic);
		}
		if (!iteration.isEmpty()) {
			final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
			final String name = forStatement.getBinding().getName();
			pushVariables(variables);
			pushIndentation(forStatement.getBody(), lastLineOfLastStatement);
			try {
				// the first value is generated on its own
				// to insert separators
				final Object firstValue = iteration.remove(0);
				int index = 1;
				variables.put(name, firstValue);
				variables.put(name + AcceleoValidator.INDEX_SUFFIX, index++);
				builder.append(doSwitch(forStatement.getBody()));
				for (Object val : iteration) {
					variables.put(name, val);
					variables.put(name + AcceleoValidator.INDEX_SUFFIX, index++);
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseProtectedArea(org.eclipse.acceleo.ProtectedArea)
	 */
	@Override
	public Object caseProtectedArea(ProtectedArea protectedArea) {
		final String res;

		final Object idObject = doSwitch(protectedArea.getId());
		final String id = toString(idObject);
		if (id.isEmpty()) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"The protected area id can't be empty.", new Object[] {protectedArea.getId(),
							new HashMap<String, Object>(peekVariables()) });
			generationResult.addDiagnostic(diagnostic);
			res = EMPTY_RESULT;
		} else {
			res = getProtectedAreaContent(protectedArea, id);
		}

		return res;
	}

	/**
	 * Gets the {@link ProtectedArea} contents.
	 * 
	 * @param protectedArea
	 *            the {@link ProtectedArea}
	 * @param id
	 *            the ID of the {@link ProtectedArea}
	 * @return the {@link ProtectedArea} contents
	 */
	private String getProtectedAreaContent(ProtectedArea protectedArea, String id) {
		final StringBuilder res = new StringBuilder();

		checkProtectedAreaIdUniqueness(protectedArea, id);

		if (protectedArea.getStartTagPrefix() != null) {
			Object startTagPrefixObject = doSwitch(protectedArea.getStartTagPrefix());
			res.append(toString(startTagPrefixObject));
		}

		final URI uri = getTargetURI();
		final String protectedAreaContent = generationStrategy.consumeProtectedAreaContent(uri, id);
		if (protectedAreaContent != null) {
			res.append(protectedAreaContent);
		} else {
			pushIndentation(protectedArea.getBody(), lastLineOfLastStatement);
			try {
				res.append(IAcceleoGenerationStrategy.USER_CODE_START + " " + id + NEW_LINE
						+ peekIndentation());

				res.append(doSwitch(protectedArea.getBody()));

				if (lastLineOfLastStatement.isEmpty()) {
					res.append(peekIndentation());
				}
				if (protectedArea.getEndTagPrefix() != null) {
					Object endTagPrefixObject = doSwitch(protectedArea.getEndTagPrefix());
					res.append(toString(endTagPrefixObject));
				}
				res.append(IAcceleoGenerationStrategy.USER_CODE_END + NEW_LINE);
			} finally {
				popIndentation();
			}
		}
		lastLineOfLastStatement = "";

		return res.toString();
	}

	/**
	 * Gets the current target {@link URI}.
	 * 
	 * @return the current target {@link URI}
	 */
	private URI getTargetURI() {
		return writers.peekLast().getTargetURI();
	}

	/**
	 * Gets the current target {@link Charset}.
	 * 
	 * @return the current target {@link Charset}
	 */
	private Charset getTargetCharset() {
		return writers.peekLast().getCharset();
	}

	/**
	 * Checks the given {@link ProtectedArea} generated ID uniqueness. It logs an error is uniqueness is not
	 * preserved.
	 * 
	 * @param protectedArea
	 *            the {@link ProtectedArea}
	 * @param id
	 *            the generated ID
	 */
	private void checkProtectedAreaIdUniqueness(ProtectedArea protectedArea, String id) {
		if (!protectedAreaIDs.peekLast().add(id)) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"Duplicated protected area ID: " + id, new Object[] {protectedArea.getId(),
							new HashMap<String, Object>(peekVariables()) });
			generationResult.addDiagnostic(diagnostic);
		}
	}

	@Override
	public Object caseError(Error error) {
		final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
				"Acceleo parsing error see validation for more details", new Object[] {error });
		generationResult.addDiagnostic(diagnostic);
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

	/**
	 * Gets the destination {@link URI}.
	 * 
	 * @return the destination {@link URI}
	 */
	public URI getDestination() {
		return destination;
	}

	/**
	 * Gets the {@link IAcceleoGenerationStrategy}.
	 * 
	 * @return the {@link IAcceleoGenerationStrategy}
	 */
	public IAcceleoGenerationStrategy getGenerationStrategy() {
		return generationStrategy;
	}

	/**
	 * Gets the {@link GenerationResult}.
	 * 
	 * @return the {@link GenerationResult}
	 */
	public GenerationResult getGenerationResult() {
		return generationResult;
	}

}
