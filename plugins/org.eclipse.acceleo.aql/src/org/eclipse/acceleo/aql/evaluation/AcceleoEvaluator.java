/*******************************************************************************
 * Copyright (c) 2016, 2026 Obeo.
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.query.runtime.impl.NullValue;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.FilteredSettingsIterator;

/**
 * This implementation of a switch dedicated to Acceleo elements will be used to evaluate their results as we
 * visit them.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluator extends AcceleoSwitch<Object> {

	/**
	 * The indentation context.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	protected static class IndentationContext {
		/**
		 * The current indentation.
		 */
		private final String indentation;

		/**
		 * Tells if the current {@link Block} is {@link Block#isInlined() inlined}.
		 */
		private final boolean inlinedBlock;

		/**
		 * Constructor.
		 * 
		 * @param indentation
		 *            the indentation
		 * @param inlinedBlock
		 *            tells if the current {@link Block} is {@link Block#isInlined() inlined}
		 */
		private IndentationContext(String indentation, boolean inlinedBlock) {
			this.indentation = indentation;
			this.inlinedBlock = inlinedBlock;
		}

	}

	/**
	 * The plugin ID.
	 */
	private static final String ID = "org.eclipse.acceleo.aql";

	/**
	 * The empty result.
	 */
	private static final String EMPTY_RESULT = "";

	/**
	 * The {@link DateFormat} used to log lost {@link UserContent}.
	 */
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * The new line {@link Pattern}.
	 */
	private static final Pattern NEW_LINE_PATTERN = Pattern.compile("(\\r\\n)|(\\n)|(\\r)");

	/**
	 * The {@link IQueryEvaluationEngine} used to evaluate AQL expressions.
	 */
	private final IQueryEvaluationEngine aqlEngine;

	/**
	 * The variables stack.
	 */
	private final Deque<Map<String, Object>> variablesStack = new ArrayDeque<Map<String, Object>>();

	/**
	 * The user code stack, mapping from ID to user code.
	 */
	private final Deque<Map<String, String>> protectedAreaContentStack = new ArrayDeque<>();

	/**
	 * The last generated line of the last {@link Statement}.
	 */
	private String lastLineOfLastStatement;

	/**
	 * The {@link IQualifiedNameLookupEngine}.
	 */
	private final IQualifiedNameLookupEngine lookupEngine;

	/**
	 * The {@link IndentationContext} stack.
	 */
	private Deque<IndentationContext> indentationContextStack = new ArrayDeque<IndentationContext>();

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
	 * The new line {@link String}.
	 */
	private String newLine;

	/**
	 * The progress {@link Monitor}.
	 */
	private Monitor monitor;

	/**
	 * Constructor.
	 * 
	 * @param lookupEngine
	 *            the {@link IQualifiedNameLookupEngine}
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AcceleoEvaluator(IQualifiedNameLookupEngine lookupEngine, String newLine) {
		this.aqlEngine = QueryEvaluation.newEngine((IQueryEnvironment)lookupEngine.getQueryEnvironment());
		this.lookupEngine = lookupEngine;
		this.newLine = newLine;
	}

	@Override
	public Object doSwitch(EObject eObject) {
		if (getMonitor().isCanceled()) {
			throw new AcceleoEvaluationCancelledException("Generation canceled");
		}
		return super.doSwitch(eObject);
	}

	/**
	 * Gets the new line {@link String}.
	 * 
	 * @return the new line {@link String}
	 */
	public String getNewLine() {
		return newLine;
	}

	/**
	 * Gets the progress {@link Monitor}.
	 * 
	 * @return the progress {@link Monitor}
	 */
	public Monitor getMonitor() {
		return monitor;
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
	 * @param monitor
	 *            the progress {@link Monitor}
	 * @return the generated {@link Object}, can be <code>null</code>
	 */
	public Object generate(AcceleoASTNode node, Map<String, Object> variables,
			IAcceleoGenerationStrategy strategy, URI destinationURI, Monitor monitor) {

		final Object res;

		if (monitor == null) {
			this.monitor = new BasicMonitor();
		} else {
			this.monitor = monitor;
		}

		destination = destinationURI;
		generationStrategy = strategy;

		if (generationResult == null) {
			generationResult = new GenerationResult();
		}

		final String savedLastLineOfLastStatement = lastLineOfLastStatement;
		lastLineOfLastStatement = "";
		indentationContextStack.addLast(new IndentationContext(lastLineOfLastStatement, false));
		pushVariables(variables);
		try {
			res = doSwitch(node);
		} finally {
			popVariables();
			indentationContextStack.removeLast();
			lastLineOfLastStatement = savedLastLineOfLastStatement;
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
	 * Peeks the last {@link #pushIndentationContext(Block, String) pushed} indentation context.
	 * 
	 * @return the last {@link #pushIndentationContext(Block, String) pushed} indentation context
	 */
	protected IndentationContext peekIndentationContext() {
		return indentationContextStack.peekLast();
	}

	/**
	 * Pushes the given indentation into the stack.
	 * 
	 * @param block
	 *            the {@link Block} to indent
	 * @param indentation
	 *            the indentation to push
	 */
	protected void pushIndentationContext(Block block, String indentation) {
		final IndentationContext newIndentationContext;

		if (block.isInlined()) {
			newIndentationContext = new IndentationContext("", true);
		} else {
			newIndentationContext = new IndentationContext(indentation, false);
		}

		indentationContextStack.addLast(newIndentationContext);
	}

	/**
	 * Pops the current {@link IndentationContext};
	 * 
	 * @return the current {@link IndentationContext}
	 */
	protected IndentationContext popIndentationContext() {
		return indentationContextStack.removeLast();
	}

	/**
	 * Pushes a new protected area contents {@link Map} into the stack.
	 */
	protected void pushProtectedAreaContent() {
		protectedAreaContentStack.addLast(new HashMap<>());
	}

	/**
	 * Puts the given protected area contents in the current {@link #pushUserCode() pushed Map} in the stack.
	 */
	protected void putProtectedAreaContent(String id, String useCode) {
		protectedAreaContentStack.peekLast().put(id, useCode);
	}

	/**
	 * Pops the last {@link #pushProtectedAreaContent() pushed Map} protected area contents from the stack.
	 * 
	 * @return the last {@link #pushProtectedAreaContent() pushed Map} protected area contents from the stack
	 */
	protected Map<String, String> popProtectedAreaContent() {
		return protectedAreaContentStack.removeLast();
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

		final Object res;
		if (evalResult.getResult() == null) {
			Set<IType> types = new LinkedHashSet<>();
			types.add(evalResult.getNullType());
			res = new NullValue(types);
		} else {
			res = evalResult.getResult();
		}

		return res;
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
		if (peekIndentationContext().inlinedBlock) {
			indentation = "";
		} else {
			indentation = getIndentation();
		}
		String expressionValue = toString(doSwitch(expressionStatement.getExpression()));
		if (!expressionValue.isEmpty()) {
			final boolean endsWithNewLine = expressionValue.endsWith(newLine);
			if (!indentation.isEmpty()) {
				expressionValue = NEW_LINE_PATTERN.matcher(expressionValue).replaceAll(newLine + Matcher
						.quoteReplacement(indentation));
				if (endsWithNewLine) {
					expressionValue = expressionValue.substring(0, expressionValue.length() - indentation
							.length());
				}
			} else {
				expressionValue = NEW_LINE_PATTERN.matcher(expressionValue).replaceAll(newLine);
			}
			if (expressionStatement.isNewLineNeeded() && !endsWithNewLine) {
				if (lastLineOfLastStatement.isEmpty()) {
					res = peekIndentationContext().indentation + expressionValue + newLine;
				} else {
					res = expressionValue + newLine;
				}
				lastLineOfLastStatement = "";
			} else {
				if (lastLineOfLastStatement.isEmpty()) {
					res = peekIndentationContext().indentation + expressionValue;
				} else {
					res = expressionValue;
				}
				final int lastIndexOfNewLine = res.lastIndexOf(newLine);
				if (lastIndexOfNewLine != -1) {
					lastLineOfLastStatement = res.substring(lastIndexOfNewLine + newLine.length(), res
							.length());
				} else {
					lastLineOfLastStatement = lastLineOfLastStatement + res;
				}
			}
		} else {
			if (lastLineOfLastStatement.isEmpty() || !expressionStatement.isNewLineNeeded()) {
				// empty text with new line at the beginning of a line is a no operation
				// see NewLineStatement
				res = EMPTY_RESULT;
			} else {
				res = newLine;
				lastLineOfLastStatement = "";
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
				final String start = module.getQualifiedName();
				lookupEngine.pushImportsContext(start, start);
				try {
					doSwitch(element);
				} finally {
					lookupEngine.popContext(start);
				}
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

		pushIndentationContext(template.getBody(), lastLineOfLastStatement);
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
			popIndentationContext();
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
					res = peekIndentationContext().indentation + textStatement.getValue() + newLine;
					lastLineOfLastStatement = "";
				} else {
					// empty text with new line at the beginning of a line is a no operation
					// see NewLineStatement
					res = EMPTY_RESULT;
				}
			} else {
				res = textStatement.getValue() + newLine;
				lastLineOfLastStatement = "";
			}
		} else {
			if (lastLineOfLastStatement.isEmpty()) {
				res = peekIndentationContext().indentation + textStatement.getValue();
				lastLineOfLastStatement = res;
			} else {
				res = textStatement.getValue();
				lastLineOfLastStatement = lastLineOfLastStatement + res;
			}
		}

		return res;
	}

	@Override
	public String caseNewLineStatement(NewLineStatement newLineStatement) {
		final String res;

		if (newLineStatement.isIndentationNeeded() && lastLineOfLastStatement.isEmpty()) {
			res = peekIndentationContext().indentation + newLine;
		} else {
			res = newLine;
		}
		lastLineOfLastStatement = "";

		return res;
	}

	@Override
	public String caseBlock(Block block) {
		final List<String> texts = createBlockTextsList(block);

		boolean lastIsEmptyBlock = false;
		String lastRemovedIndentation = null;
		for (Statement statement : block.getStatements()) {
			final String text = (String)doSwitch(statement);
			if (!text.isEmpty()) {
				if (lastIsEmptyBlock) {
					final String lastText;
					if (!texts.isEmpty()) {
						lastText = texts.remove(texts.size() - 1);
					} else {
						lastText = EMPTY_RESULT;
					}
					final int lastNewLineIndex = lastText.lastIndexOf(newLine);
					final String prefix;
					if (lastNewLineIndex >= 0) {
						prefix = lastText.substring(0, lastNewLineIndex + newLine.length());
					} else {
						prefix = EMPTY_RESULT;
					}
					final String suffix;
					if (text.startsWith(newLine)) {
						suffix = text.substring(newLine.length());
					} else {
						if (lastRemovedIndentation != null) {
							suffix = lastRemovedIndentation + text;
							lastRemovedIndentation = null;
						} else {
							suffix = text;
						}
					}

					final String newText = prefix + suffix;
					if (!newText.isEmpty()) {
						texts.add(newText);
					}
				} else {
					texts.add(text);
				}
				lastIsEmptyBlock = false;
			} else {
				lastIsEmptyBlock = statement.isMultiLines();
				if (lastIsEmptyBlock) {
					final String lastText;
					if (!texts.isEmpty()) {
						lastText = texts.remove(texts.size() - 1);
					} else {
						lastText = EMPTY_RESULT;
					}
					final int lastNewLineIndex = lastText.lastIndexOf(newLine);
					final String newText;
					if (lastNewLineIndex >= 0) {
						final int index = lastNewLineIndex + newLine.length();
						newText = lastText.substring(0, index);
						lastRemovedIndentation = lastText.substring(index, lastText.length());
					} else {
						newText = EMPTY_RESULT;
						lastRemovedIndentation = lastText;
					}
					if (!newText.isEmpty()) {
						texts.add(newText);
					}
				}
			}
		}

		final StringBuilder builder = new StringBuilder();
		for (String text : texts) {
			builder.append(text);
		}
		return builder.toString();
	}

	/**
	 * Creates the {@link List} of {@link String} for the given {@link Block}.
	 * 
	 * @param block
	 *            the {@link Block}
	 * @return the created {@link List} of {@link String} for the given {@link Block}
	 */
	protected List<String> createBlockTextsList(Block block) {
		return new ArrayList<>();
	}

	@Override
	public String caseComment(Comment comment) {
		return EMPTY_RESULT;
	}

	@Override
	public String caseLetStatement(LetStatement letStatement) {
		final String builder;

		final Map<String, Object> variables = new HashMap<String, Object>(peekVariables());
		pushVariables(variables);
		for (Binding binding : letStatement.getVariables()) {
			final String name = binding.getName();
			final Object value = doSwitch(binding.getInitExpression());
			variables.put(name, value);
		}

		final String lastLine = lastLineOfLastStatement;
		pushIndentationContext(letStatement.getBody(), getIndentation());
		try {
			builder = (String)doSwitch(letStatement.getBody());
		} finally {
			popIndentationContext();
			popVariables();
		}

		final String res;
		if (letStatement.getBody().isInlined()) {
			res = addIndentationPrefix(lastLine, builder.toString());
		} else {
			res = builder.toString();
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
			try {
				final URI uri = URI.createURI(toString(uriObject), true).resolve(destination);
				getMonitor().subTask("Generating " + uri);
				openWriter(uri, mode, charset, newLine);
				lastLineOfLastStatement = "";
				pushIndentationContext(fileStatement.getBody(), lastLineOfLastStatement);
				pushProtectedAreaContent();
				String content = EMPTY_RESULT;
				try {
					content = (String)doSwitch(fileStatement.getBody());
				} finally {
					for (Entry<String, String> entry : popProtectedAreaContent().entrySet()) {
						final String protectedAreaContentsRegex = IAcceleoGenerationStrategy.USER_CODE_START
								+ " " + Pattern.quote(entry.getKey()) + newLine + "((?!"
								+ IAcceleoGenerationStrategy.USER_CODE_END + ").|" + newLine + ")*"
								+ IAcceleoGenerationStrategy.USER_CODE_END + newLine;
						content = content.replaceFirst(protectedAreaContentsRegex, Matcher.quoteReplacement(
								entry.getValue()));
					}
					write(content);
					popIndentationContext();
					closeWriter(fileStatement);
				}
			} catch (IOException e) {
				final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0, e
						.getMessage(), new Object[] {fileStatement, new HashMap<String, Object>(
								peekVariables()) });
				generationResult.addDiagnostic(diagnostic);
			} catch (IllegalArgumentException e) {
				final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0, e.getMessage()
						+ " " + destination + " " + uriObject, new Object[] {fileStatement,
								new HashMap<String, Object>(peekVariables()) });
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
					targetCharset, newLine);
			try {
				for (String lostContent : entry.getValue()) {
					final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.WARNING, ID, 0,
							"Lost file generated: " + lostWriter.getTargetURI(), new Object[] {fileStatement,
									new HashMap<String, Object>(peekVariables()) });
					generationResult.addDiagnostic(diagnostic);
					lostWriter.append(FORMAT.format(new Date()) + " - Lost user content " + lostID + newLine);
					lostWriter.append(lostContent + newLine);
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
			if (charsetValue != null && charsetValue.getClass() != NullValue.class) {
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
				final String lastLine = lastLineOfLastStatement;
				pushIndentationContext(ifStatement.getThen(), getIndentation());
				final String text;
				try {
					text = (String)doSwitch(ifStatement.getThen());
				} finally {
					popIndentationContext();
				}
				if (ifStatement.getThen().isInlined()) {
					res = addIndentationPrefix(lastLine, text);
				} else {
					res = text;
				}
			} else if (ifStatement.getElse() != null) {
				final String lastLine = lastLineOfLastStatement;
				pushIndentationContext(ifStatement.getElse(), getIndentation());
				final String text;
				try {
					text = (String)doSwitch(ifStatement.getElse());
				} finally {
					popIndentationContext();
				}
				if (ifStatement.getElse().isInlined()) {
					res = addIndentationPrefix(lastLine, text);
				} else {
					res = text;
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

		final String lastLine = lastLineOfLastStatement;
		final List<Object> iteration = new ArrayList<Object>();
		final Object value = doSwitch(forStatement.getBinding().getInitExpression());
		if (value instanceof Collection) {
			iteration.addAll((Collection<?>)value);
		} else if (value != null && value.getClass() != NullValue.class) {
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
			pushIndentationContext(forStatement.getBody(), getIndentation());
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
				popIndentationContext();
				popVariables();
			}
		}

		final String res;
		if (forStatement.getBody().isInlined()) {
			res = addIndentationPrefix(lastLine, builder.toString());
		} else {
			res = builder.toString();
		}

		return res;
	}

	/**
	 * Add the indentation prefix to the given text if the given last line is {@link Block#isInlined()
	 * inlined}.
	 * 
	 * @param block
	 *            the {@link Block}
	 * @param text
	 *            the text
	 * @return the indentation prefix to the given text if the given last line is {@link Block#isInlined()
	 *         inlined}
	 */
	private String addIndentationPrefix(String lastLine, String text) {
		final String res;

		if (lastLine.isEmpty() && !text.isEmpty()) {
			res = peekIndentationContext().indentation + text;
		} else {
			res = text;
		}

		return res;
	}

	/**
	 * Gets the current indentation.
	 * 
	 * @return the current indentation
	 */
	private String getIndentation() {
		final String res;

		if (lastLineOfLastStatement.isEmpty()) {
			res = peekIndentationContext().indentation;
		} else {
			res = lastLineOfLastStatement;
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseProtectedArea(org.eclipse.acceleo.ProtectedArea)
	 */
	@Override
	public String caseProtectedArea(ProtectedArea protectedArea) {
		final String res;

		final Object idObject = doSwitch(protectedArea.getId());
		final String id = toString(idObject);
		if (id.isEmpty()) {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"The protected area id can't be empty.", new Object[] {protectedArea.getId(),
							new HashMap<String, Object>(peekVariables()) });
			generationResult.addDiagnostic(diagnostic);
			res = EMPTY_RESULT;
		} else if (!protectedAreaIDs.isEmpty()) {
			// if we are in a FleStatement
			res = getProtectedAreaContent(protectedArea, id);
		} else {
			final BasicDiagnostic diagnostic = new BasicDiagnostic(Diagnostic.ERROR, ID, 0,
					"The protected area can't be generated outside a file block.", new Object[] {
							protectedArea, new HashMap<String, Object>(peekVariables()) });
			generationResult.addDiagnostic(diagnostic);
			res = EMPTY_RESULT;
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

		final String lastLine = lastLineOfLastStatement;

		if (protectedArea.getStartTagPrefix() != null) {
			final Object startTagPrefixObject = doSwitch(protectedArea.getStartTagPrefix());
			final String prefix = toString(startTagPrefixObject);
			res.append(prefix);
		}

		final URI uri = getTargetURI();
		final String protectedAreaContent = generationStrategy.consumeProtectedAreaContent(uri, id);
		if (protectedAreaContent != null) {
			putProtectedAreaContent(id, protectedAreaContent);
			// We just mark the protected area, the contents will be set at the end of the FileStatement.
			res.append(IAcceleoGenerationStrategy.USER_CODE_START + " " + id + newLine);
			res.append(IAcceleoGenerationStrategy.USER_CODE_END + newLine);
			lastLineOfLastStatement = "";
		} else {
			pushIndentationContext(protectedArea.getBody(), getIndentation());
			try {
				res.append(IAcceleoGenerationStrategy.USER_CODE_START + " " + id + newLine);
				lastLineOfLastStatement = "";

				final String text = (String)doSwitch(protectedArea.getBody());
				res.append(text);

				final StringBuilder endTag = new StringBuilder();
				if (protectedArea.getEndTagPrefix() != null) {
					Object endTagPrefixObject = doSwitch(protectedArea.getEndTagPrefix());
					final String prefix = toString(endTagPrefixObject);
					endTag.append(prefix);
				}
				endTag.append(IAcceleoGenerationStrategy.USER_CODE_END + newLine);
				res.append(addIndentationPrefix(lastLineOfLastStatement, endTag.toString()));
			} finally {
				popIndentationContext();
			}
		}
		lastLineOfLastStatement = "";

		return addIndentationPrefix(lastLine, res.toString());
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
	public String caseError(Error error) {
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
		} else if (object != null && object.getClass() != NullValue.class) {
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
		if (generationResult == null) {
			generationResult = new GenerationResult();
		}
		return generationResult;
	}

}
