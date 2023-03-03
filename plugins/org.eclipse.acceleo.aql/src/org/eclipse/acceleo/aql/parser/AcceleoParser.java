/*******************************************************************************
 * Copyright (c) 2016, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.ErrorComment;
import org.eclipse.acceleo.ErrorExpressionStatement;
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.ErrorForStatement;
import org.eclipse.acceleo.ErrorIfStatement;
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.ErrorLetStatement;
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.ErrorModule;
import org.eclipse.acceleo.ErrorModuleDocumentation;
import org.eclipse.acceleo.ErrorModuleElementDocumentation;
import org.eclipse.acceleo.ErrorModuleReference;
import org.eclipse.acceleo.ErrorProtectedArea;
import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.ErrorTemplate;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LeafStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.eclipse.acceleo.query.parser.QueryParser;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * Acceleo parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoParser {

	/**
	 * The acceleo URI protocol.
	 */
	public static final String ACCELEOENV_URI_PROTOCOL = "acceleoenv::";

	/**
	 * The module file extension.
	 */
	public static final String MODULE_FILE_EXTENSION = "mtl";

	/**
	 * New line.
	 */
	public static final String NEW_LINE = "\n";

	/**
	 * In line end delimiter.
	 */
	public static final String NO_SLASH_END = "]";

	/**
	 * In line end delimiter.
	 */
	public static final String SLASH_END = "/]";

	/**
	 * End of {@link TextStatement}.
	 */
	public static final String TEXT_END = "[";

	/**
	 * Empty {@link ExpressionStatement}.
	 */
	public static final String EMPTY_EXPRESSION_STATEMENT = TEXT_END + SLASH_END;

	/**
	 * Start of {@link BlockComment}.
	 */
	public static final String BLOCK_COMMENT_START = "[comment]";

	/**
	 * End of {@link BlockComment}.
	 */
	public static final String BLOCK_COMMENT_END = "[/comment]";

	/**
	 * Start of {@link Comment}.
	 */
	public static final String COMMENT_START = "[comment ";

	/**
	 * End of {@link Comment}.
	 */
	public static final String COMMENT_END = SLASH_END;

	/**
	 * Start of {@link Module} header.
	 */
	public static final String MODULE_HEADER_START = "[module ";

	/**
	 * End of {@link Module} header.
	 */
	public static final String MODULE_HEADER_END = SLASH_END;

	/**
	 * Open parenthesis.
	 */
	public static final String OPEN_PARENTHESIS = "(";

	/**
	 * Close parenthesis.
	 */
	public static final String CLOSE_PARENTHESIS = ")";

	/**
	 * A comma.
	 */
	public static final String COMMA = ",";

	/**
	 * A colon.
	 */
	public static final String COLON = ":";

	/**
	 * A qualifier separator.
	 */
	public static final String QUALIFIER_SEPARATOR = "::";

	/**
	 * An equal.
	 */
	public static final String EQUAL = "=";

	/**
	 * A pipe.
	 */
	public static final String PIPE = "|";

	/**
	 * A quote.
	 */
	public static final String QUOTE = "'";

	/**
	 * Start of {@link Template} header.
	 */
	public static final String TEMPLATE_HEADER_START = "[template ";

	/**
	 * Start of {@link Template} header.
	 */
	public static final String TEMPLATE_HEADER_END = NO_SLASH_END;

	/**
	 * Guard of {@link Template}.
	 */
	public static final String TEMPLATE_GUARD = "?";

	/**
	 * Post of {@link Template}.
	 */
	public static final String TEMPLATE_POST = "post(";

	/**
	 * End block prefix.
	 */
	public static final String END_BLOCK_PREFIX = "[/";

	/**
	 * End of {@link Template}.
	 */
	public static final String TEMPLATE_END = END_BLOCK_PREFIX + "template]";

	/**
	 * Start of {@link ExpressionStatement}.
	 */
	public static final String EXPRESSION_STATEMENT_START = "[";

	/**
	 * End of {@link ExpressionStatement}.
	 */
	public static final String EXPRESSION_STATEMENT_END = SLASH_END;

	/**
	 * Start of {@link ProtectedArea} header.
	 */
	public static final String PROTECTED_AREA_HEADER_START = "[protected ";

	/**
	 * End of {@link ProtectedArea} header.
	 */
	public static final String PROTECTED_AREA_HEADER_END = NO_SLASH_END;

	/**
	 * {@link ProtectedArea} start tag prefix.
	 */
	public static final String PROTECTED_AREA_START_TAG_PREFIX = "startTagPrefix(";

	/**
	 * {@link ProtectedArea} end tag prefix.
	 */
	public static final String PROTECTED_AREA_END_TAG_PREFIX = "endTagPrefix(";

	/**
	 * End of {@link ProtectedArea}.
	 */
	public static final String PROTECTED_AREA_END = END_BLOCK_PREFIX + "protected]";

	/**
	 * Start of {@link FileStatement} header.
	 */
	public static final String FILE_HEADER_START = "[file ";

	/**
	 * End of {@link FileStatement} header.
	 */
	public static final String FILE_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link FileStatement}.
	 */
	public static final String FILE_END = END_BLOCK_PREFIX + "file]";

	/**
	 * Start of {@link LetStatement} header.
	 */
	public static final String LET_HEADER_START = "[let ";

	/**
	 * End of {@link LetStatement} header.
	 */
	public static final String LET_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link LetStatement}.
	 */
	public static final String LET_END = END_BLOCK_PREFIX + "let]";

	/**
	 * Start of {@link ForStatement} header.
	 */
	public static final String FOR_HEADER_START = "[for ";

	/**
	 * Start of {@link ForStatement} header.
	 */
	public static final String FOR_SEPARATOR = "separator(";

	/**
	 * End of {@link ForStatement} header.
	 */
	public static final String FOR_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link ForStatement}.
	 */
	public static final String FOR_END = END_BLOCK_PREFIX + "for]";

	/**
	 * Start of {@link IfStatement} header.
	 */
	public static final String IF_HEADER_START = "[if ";

	/**
	 * End of {@link IfStatement} header.
	 */
	public static final String IF_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link IfStatement}.
	 */
	public static final String IF_ELSEIF = "[elseif ";

	/**
	 * End of {@link IfStatement}.
	 */
	public static final String IF_ELSE = "[else]";

	/**
	 * End of {@link IfStatement}.
	 */
	public static final String IF_END = END_BLOCK_PREFIX + "if]";

	/**
	 * Start of {@link Query}.
	 */
	public static final String QUERY_START = "[query ";

	/**
	 * End of {@link Query}.
	 */
	public static final String QUERY_END = SLASH_END;

	/**
	 * Start of {@link Import}.
	 */
	public static final String IMPORT_START = "[import ";

	/**
	 * End of {@link Import}.
	 */
	public static final String IMPORT_END = SLASH_END;

	/**
	 * Start of {@link Documentation}.
	 */
	public static final String DOCUMENTATION_START = "[**";

	/**
	 * End of {@link Documentation}.
	 */
	public static final String DOCUMENTATION_END = SLASH_END;

	/**
	 * Main tag.
	 */
	public static final String MAIN_TAG = "@main";

	/**
	 * Author tag.
	 */
	public static final String AUTHOR_TAG = "@author ";

	/**
	 * Version tag.
	 */
	public static final String VERSION_TAG = "@version ";

	/**
	 * Since tag.
	 */
	public static final String SINCE_TAG = "@since ";

	/**
	 * Param tag.
	 */
	public static final String PARAM_TAG = "@param ";

	/**
	 * Extends key work.
	 */
	public static final String EXTENDS = "extends ";

	/**
	 * The mandatory indentation for not inlined {@link Block}.
	 */
	public static final int INDENTATION = 2;

	/**
	 * The {@link Positions}.
	 */
	private Positions positions;

	/**
	 * The parser currentPosition.
	 */
	private int currentPosition;

	/**
	 * The line number at a given position.
	 */
	private int[] lines;

	/**
	 * The column number at a given position.
	 */
	private int[] columns;

	/**
	 * The source text.
	 */
	private String text;

	/**
	 * The {@link List} of {@link Error}.
	 */
	private List<Error> errors;

	/**
	 * Parses the given {@link InputStream}.
	 * 
	 * @param source
	 *            the {@link InputStream}
	 * @param charset
	 *            the {@link Charset}
	 * @param moduleQualifiedName
	 *            the qualified name of the {@link Module} (e.g. "path::to::module").
	 * @return the parsed {@link AstResult}
	 * @throws IOException
	 *             if the {@link InputStream} can't be read
	 */
	public AcceleoAstResult parse(InputStream source, Charset charset, String moduleQualifiedName)
			throws IOException {
		return parse(AcceleoUtil.getContent(source, charset.name()), moduleQualifiedName);
	}

	/**
	 * Parses the given source text.
	 * 
	 * @param source
	 *            the source text
	 * @param qualifiedName
	 *            the qualified name of the {@link Module} (e.g. "path::to::module").
	 * @return the parsed {@link AstResult}
	 */
	public AcceleoAstResult parse(String source, String qualifiedName) {
		this.currentPosition = 0;
		this.lines = new int[source.length() + 1];
		this.columns = new int[source.length() + 1];
		this.positions = new Positions();
		this.text = source;
		computeLinesAndColumns(text);
		this.errors = new ArrayList<Error>();

		final List<Comment> comments = parseCommentsOrModuleDocumentations();
		final Module module = parseModule(comments);

		final Resource containerEmfResource = new XMIResourceImpl(URI.createURI(ACCELEOENV_URI_PROTOCOL
				+ qualifiedName));
		containerEmfResource.getContents().add(module);

		return new AcceleoAstResult(module, positions, errors);
	}

	/**
	 * Computes the lines and columns at given position.
	 * 
	 * @param str
	 *            the source
	 */
	// TODO do it on the fly to prevent reading the input twice
	private void computeLinesAndColumns(String str) {
		int currentLine = 0;
		int currentColumn = 0;
		for (int i = 0; i < str.length(); i++) {
			lines[i] = currentLine;
			columns[i] = currentColumn;

			char currentCharacter = str.charAt(i);
			if (currentCharacter == '\n') {
				currentLine++;
				currentColumn = 0;
			} else {
				currentColumn++;
			}
		}
		// User cursor may be at the position right after the last character of the String.
		lines[str.length()] = currentLine;
		columns[str.length()] = currentColumn;
	}

	/**
	 * Sets the identifier start and identifer end positions for the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @param start
	 *            the start position
	 * @param end
	 *            the end position
	 */
	private void setIdentifierPositions(ASTNode node, int start, int end) {
		positions.setIdentifierStartPositions(node, start);
		positions.setIdentifierStartLines(node, lines[start]);
		positions.setIdentifierStartColumns(node, columns[start]);
		positions.setIdentifierEndPositions(node, end);
		positions.setIdentifierEndLines(node, lines[end]);
		positions.setIdentifierEndColumns(node, columns[end]);
	}

	/**
	 * Sets the start and end positions for the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @param start
	 *            the start position
	 * @param end
	 *            the end position
	 */
	private void setPositions(ASTNode node, int start, int end) {
		positions.setStartPositions(node, start);
		positions.setStartLines(node, lines[start]);
		positions.setStartColumns(node, columns[start]);
		positions.setEndPositions(node, end);
		positions.setEndLines(node, lines[end]);
		positions.setEndColumns(node, columns[end]);
	}

	/**
	 * Parses a {@link List} of {@link Comment} and {@link ModuleDocumentation}.
	 * 
	 * @return the created {@link List} of {@link Comment} and {@link ModuleDocumentation}
	 */
	protected List<Comment> parseCommentsOrModuleDocumentations() {
		final List<Comment> comments = new ArrayList<Comment>();
		Comment comment = parseComment();
		ModuleDocumentation documentation = parseModuleDocumentation();
		while (comment != null || documentation != null) {
			if (comment != null) {
				comments.add(comment);
			}
			if (documentation != null) {
				comments.add(documentation);
			}
			skipSpaces();
			comment = parseComment();
			documentation = parseModuleDocumentation();
		}
		return comments;
	}

	/**
	 * Skips {@link Character#isWhitespace(char) white spaces}.
	 */
	protected void skipSpaces() {
		while (currentPosition < text.length() && Character.isWhitespace(text.charAt(currentPosition))) {
			currentPosition++;
		}
	}

	/**
	 * Reads the given {@link String}.
	 * 
	 * @param str
	 *            the {@link String} to read
	 * @return <code>true</code> if the given {@link String} is at the current position, <code>false</code>
	 *         otherwise
	 */
	protected boolean readString(String str) {
		final boolean res = text.startsWith(str, currentPosition);

		if (res) {
			currentPosition += str.length();
		}

		return res;
	}

	/**
	 * {@link AcceleoParser#readString(String) Reads} the given {@link String}.
	 * 
	 * @param str
	 *            the {@link String} to read
	 * @return <code>-1</code> if {@link AcceleoParser#readString(String) read} the given {@link String}
	 *         succeed, the current position otherwise
	 */
	protected int readMissingString(String str) {
		final int res;

		if (readString(str)) {
			res = -1;
		} else {
			res = currentPosition;
		}

		return res;
	}

	/**
	 * Parses and identifier.
	 * 
	 * @return the identifier if any recognized, <code>null</code> otherwise
	 */
	protected String parseIdentifier() {
		final String res;

		if (currentPosition < text.length() && Character.isJavaIdentifierStart(text.charAt(
				currentPosition))) {
			final int identifierStart = currentPosition;
			currentPosition++;
			while (currentPosition < text.length() && Character.isJavaIdentifierPart(text.charAt(
					currentPosition))) {
				currentPosition++;
			}
			res = text.substring(identifierStart, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Comment} or a {@link BlockComment}.
	 * 
	 * @return the created {@link Comment} or {@link BlockComment} if any recognized, <code>null</code>
	 *         otherwise
	 */
	protected Comment parseComment() {
		final Comment res;
		if (text.startsWith(BLOCK_COMMENT_START, currentPosition)) {
			res = createComment(BLOCK_COMMENT_START, BLOCK_COMMENT_END, AcceleoPackage.eINSTANCE
					.getBlockComment(), AcceleoPackage.eINSTANCE.getErrorBlockComment());
		} else if (text.startsWith(COMMENT_START, currentPosition)) {
			res = createComment(COMMENT_START, COMMENT_END, AcceleoPackage.eINSTANCE.getComment(),
					AcceleoPackage.eINSTANCE.getErrorComment());
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Creates the {@link Comment} or {@link ErrorComment}.
	 * 
	 * @param startTag
	 *            the start tag
	 * @param endTag
	 *            the end tag
	 * @param commentClass
	 *            the {@link Comment} {@link EClass}
	 * @param errorCommentClass
	 *            the {@link ErrorComment} {@link EClass}
	 * @return the {@link Comment} or {@link ErrorComment}
	 */
	private Comment createComment(String startTag, String endTag, EClass commentClass,
			EClass errorCommentClass) {
		final Comment res;
		final int startPosition = currentPosition;
		final int startOfCommentBody = currentPosition + startTag.length();
		int endOfCommentBody = getNext(endTag);
		if (endOfCommentBody < 0) {
			endOfCommentBody = text.length();
			res = (Comment)EcoreUtil.create(errorCommentClass);
			setPositions(res, startPosition, endOfCommentBody);
			((ErrorComment)res).setMissingEndHeader(endOfCommentBody);
			errors.add((Error)res);
			currentPosition = endOfCommentBody;
		} else {
			res = (Comment)EcoreUtil.create(commentClass);
			setPositions(res, startPosition, endOfCommentBody + endTag.length());
			currentPosition = endOfCommentBody + endTag.length();
		}

		final CommentBody commentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory().createCommentBody();
		commentBody.setValue(text.substring(startOfCommentBody, endOfCommentBody));
		setPositions(commentBody, startOfCommentBody, endOfCommentBody);

		res.setBody(commentBody);
		return res;
	}

	/**
	 * Parses a {@link ModuleDocumentation}.
	 * 
	 * @return the created {@link ModuleDocumentation} if any recognized, <code>null</code> otherwise
	 */
	protected ModuleDocumentation parseModuleDocumentation() {
		final ModuleDocumentation res;

		if (text.startsWith(DOCUMENTATION_START, currentPosition)) {
			final int commentStartPositon = currentPosition;
			currentPosition += DOCUMENTATION_START.length();
			final int startPosition = currentPosition;
			int endPosition = getNext(DOCUMENTATION_END);
			if (endPosition < 0) {
				endPosition = text.length();
				currentPosition = endPosition;
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorModuleDocumentation();
				((ErrorModuleDocumentation)res).setMissingEndHeader(endPosition);
				errors.add((Error)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModuleDocumentation();
				currentPosition = endPosition + DOCUMENTATION_END.length();
			}
			final String docString = text.substring(startPosition, endPosition);
			final CommentBody commentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory().createCommentBody();
			setPositions(commentBody, startPosition, endPosition);
			commentBody.setValue(docString);
			res.setBody(commentBody);
			final int authorPosition = docString.indexOf(AUTHOR_TAG);
			if (authorPosition >= 0) {
				final int authorStart = authorPosition + AUTHOR_TAG.length();
				int authorEnd = docString.indexOf(NEW_LINE, authorStart);
				res.setAuthor(docString.substring(authorStart, authorEnd));
			}
			final int versionPosition = docString.indexOf(VERSION_TAG);
			if (versionPosition >= 0) {
				final int versionStart = versionPosition + VERSION_TAG.length();
				int versionEnd = docString.indexOf(NEW_LINE, versionStart);
				res.setVersion(docString.substring(versionStart, versionEnd));
			}
			final int sincePosition = docString.indexOf(SINCE_TAG);
			if (sincePosition >= 0) {
				final int sinceStart = sincePosition + SINCE_TAG.length();
				int sinceEnd = docString.indexOf(NEW_LINE, sinceStart);
				if (sinceEnd >= 0) {
					res.setSince(docString.substring(sinceStart, sinceEnd));
				} else {
					res.setSince(docString.substring(sinceStart, docString.length()));
				}
			}
			setPositions(res, commentStartPositon, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link ModuleElementDocumentation}.
	 * 
	 * @return the created {@link ModuleElementDocumentation} if any recognized, <code>null</code> otherwise
	 */
	protected ModuleElementDocumentation parseModuleElementDocumentation() {
		final ModuleElementDocumentation res;

		if (text.startsWith(DOCUMENTATION_START, currentPosition)) {
			final int commentStartPositon = currentPosition;
			currentPosition += DOCUMENTATION_START.length();
			final int startPosition = currentPosition;
			int endPosition = getNext(DOCUMENTATION_END);
			if (endPosition < 0) {
				endPosition = text.length();
				currentPosition = endPosition;
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorModuleElementDocumentation();
				((ErrorModuleElementDocumentation)res).setMissingEndHeader(endPosition);
				errors.add((Error)res);
			} else {
				currentPosition = endPosition + DOCUMENTATION_END.length();
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModuleElementDocumentation();
			}
			final String docString = text.substring(startPosition, endPosition);
			final CommentBody commentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory().createCommentBody();
			setPositions(commentBody, currentPosition, endPosition);
			commentBody.setValue(docString);
			res.setBody(commentBody);
			int paramPosition = docString.indexOf(PARAM_TAG);
			while (paramPosition >= 0) {
				final ParameterDocumentation paramDoc = AcceleoPackage.eINSTANCE.getAcceleoFactory()
						.createParameterDocumentation();
				final int paramStart = paramPosition + PARAM_TAG.length();
				int paramEnd = docString.indexOf(NEW_LINE, paramStart);
				if (paramEnd < 0) {
					paramPosition = -1;
					paramEnd = docString.length();
				} else {
					paramPosition = docString.indexOf(PARAM_TAG, paramEnd);
				}
				setPositions(paramDoc, paramStart + startPosition, paramEnd + startPosition);
				final CommentBody paramBody = AcceleoPackage.eINSTANCE.getAcceleoFactory()
						.createCommentBody();
				paramBody.setValue(docString.substring(paramStart, paramEnd));
				setPositions(paramBody, paramStart + startPosition, paramEnd + startPosition);
				paramDoc.setBody(paramBody);
				res.getParameterDocumentation().add(paramDoc);
			}
			setPositions(res, commentStartPositon, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Module}.
	 * 
	 * @param comments
	 *            the {@link List} of {@link Comment}
	 * @return the created {@link Module}
	 */
	protected Module parseModule(List<Comment> comments) {
		final Module res;

		if (text.startsWith(MODULE_HEADER_START, currentPosition)) {
			// Start of the whole module, including comments and documentation that may be before the
			// '[module' declaration.
			final int startPosition = Stream.concat(Stream.of(currentPosition), comments.stream().map(
					positions::getStartPositions)).min(Integer::compareTo).get();
			// Start of the header of the module, i.e. where the '[module' declaration is located.
			final int startHeaderPosition = currentPosition;
			currentPosition += MODULE_HEADER_START.length();
			skipSpaces();

			final int identifierStartPosition = currentPosition;
			final String name = parseIdentifier();
			final int identifierEndPosition = currentPosition;
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final List<Metamodel> metamodels = new ArrayList<Metamodel>();
			Metamodel metamodel = parseMetamodel();
			int missingEPackage = -1;
			if (metamodel == null) {
				missingEPackage = currentPosition;
			}
			while (metamodel != null) {
				metamodels.add(metamodel);
				skipSpaces();
				if (readString(COMMA)) {
					skipSpaces();
					metamodel = parseMetamodel();
					if (metamodel == null) {
						missingEPackage = currentPosition;
					} else {
						missingEPackage = -1;
					}
				} else {
					break;
				}
			}
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final ModuleReference extendedModule;
			if (readString(EXTENDS)) {
				skipSpaces();
				extendedModule = parseModuleReference();
				skipSpaces();
			} else {
				extendedModule = null;
			}
			final int missingEndHeader = readMissingString(MODULE_HEADER_END);
			final int endHeaderPosition = currentPosition;
			skipSpaces();
			final List<Import> imports = new ArrayList<Import>();
			Import imported = parseImport();
			while (imported != null) {
				imports.add(imported);
				skipSpaces();
				imported = parseImport();
			}
			final List<ModuleElement> moduleElements = parseModuleElements();
			final int endPosition = currentPosition;
			final boolean missingParenthesis = missingOpenParenthesis != -1 || missingCloseParenthesis != -1;
			if (missingParenthesis || missingEPackage != -1 || missingEndHeader != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorModule();
				((ErrorModule)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorModule)res).setMissingEPackage(missingEPackage);
				((ErrorModule)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorModule)res).setMissingEndHeader(missingEndHeader);
				errors.add((ErrorModule)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModule();
			}
			setIdentifierPositions(res, identifierStartPosition, identifierEndPosition);
			setPositions(res, startPosition, endPosition);
			res.setStartHeaderPosition(startHeaderPosition);
			res.setEndHeaderPosition(endHeaderPosition);
			res.getModuleElements().addAll(comments);
			res.setDocumentation(getLastDocumentation(comments));
			res.setName(name);
			res.getMetamodels().addAll(metamodels);
			res.getImports().addAll(imports);
			res.setExtends(extendedModule);
			res.getModuleElements().addAll(moduleElements);
		} else {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorModule();
			errors.add((ErrorModule)res);
		}

		return res;
	}

	/**
	 * Parses an import {@link ModuleReference}.
	 * 
	 * @return the create {@link ModuleReference} if any is recognized, <code>null</code> otherwise
	 */
	protected Import parseImport() {
		final Import res;

		if (text.startsWith(IMPORT_START, currentPosition)) {
			final int startPosition = currentPosition;
			currentPosition += IMPORT_START.length();
			skipSpaces();
			final ModuleReference moduleReference = parseModuleReference();
			skipSpaces();
			final int missingEnd = readMissingString(IMPORT_END);
			if (missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorImport();
				((ErrorImport)res).setMissingEnd(missingEnd);
				errors.add((ErrorImport)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createImport();
			}
			res.setModule(moduleReference);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link ModuleReference}.
	 * 
	 * @return the recognized {@link ModuleReference} if any, <code>null</code> otherwise
	 */
	protected ModuleReference parseModuleReference() {
		final ModuleReference res;

		final int startPosition = currentPosition;
		final String moduleQualifiedName = parseModuleQualifiedName();
		if (moduleQualifiedName == null) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorModuleReference();
			errors.add((ErrorModuleReference)res);
		} else {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModuleReference();
		}
		res.setQualifiedName(moduleQualifiedName);
		setPositions(res, startPosition, currentPosition);

		return res;
	}

	/**
	 * Parses module qualified name.
	 * 
	 * @return the recognized module qualified name if any, <code>null</code> otherwise
	 */
	protected String parseModuleQualifiedName() {
		final StringBuilder builder = new StringBuilder();

		String segment = parseIdentifier();
		while (segment != null) {
			builder.append(segment);
			if (readString(QUALIFIER_SEPARATOR)) {
				builder.append(QUALIFIER_SEPARATOR);
				segment = parseIdentifier();
			} else {
				break;
			}
		}

		final String res;
		if (builder.length() != 0) {
			res = builder.toString();
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the last {@link Documentation} from the given {@link List} of {@link Comment}.
	 * 
	 * @param comments
	 *            the {@link List} of {@link Comment}
	 * @return the last {@link Documentation} from the given {@link List} of {@link Comment}
	 */
	protected Documentation getLastDocumentation(List<Comment> comments) {
		for (int i = comments.size() - 1; i >= 0; i--) {
			Comment current = comments.get(i);
			if (current instanceof Documentation) {
				return (Documentation)current;
			}
		}

		return null;
	}

	/**
	 * Parses a {@link List} of {@link ModuleElement}.
	 * 
	 * @return the created {@link List} of {@link ModuleElement}
	 */
	protected List<ModuleElement> parseModuleElements() {
		final List<ModuleElement> res = new ArrayList<ModuleElement>();

		ModuleElement moduleElement;
		do {
			skipSpaces();
			final List<Comment> comments = parseCommentsOrModuleElementDocumentations();
			res.addAll(comments);
			final Documentation documentation = getLastDocumentation(comments);
			final Template template = parseTemplate(documentation, hasMain(comments));
			if (template != null) {
				moduleElement = template;
			} else {
				moduleElement = parseQuery(documentation);
			}
			if (moduleElement != null) {
				res.add(moduleElement);
			}
		} while (moduleElement != null);

		return res;
	}

	/**
	 * Tells if the given {@link List} of {@link Comment} contains a {@value #MAIN_TAG}.
	 * 
	 * @param comments
	 *            the {@link List} of {@link Comment}
	 * @return <code>true</code> if the given {@link List} of {@link Comment} contains a {@value #MAIN_TAG},
	 *         <code>false</code> otherwise
	 */
	private boolean hasMain(List<Comment> comments) {
		for (Comment comment : comments) {
			if (comment.getBody().getValue().contains(MAIN_TAG)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Parses a {@link List} of {@link Comment} and {@link ModuleElementDocumentation}.
	 * 
	 * @return the created {@link List} of {@link Comment} and {@link ModuleElementDocumentation}
	 */
	protected List<Comment> parseCommentsOrModuleElementDocumentations() {
		final List<Comment> comments = new ArrayList<Comment>();
		Comment comment = parseComment();
		ModuleElementDocumentation documentation = parseModuleElementDocumentation();
		while (comment != null || documentation != null) {
			if (comment != null) {
				comments.add(comment);
			}
			if (documentation != null) {
				comments.add(documentation);
			}
			skipSpaces();
			comment = parseComment();
			documentation = parseModuleElementDocumentation();
		}
		return comments;
	}

	/**
	 * Parses a {@link Query}.
	 * 
	 * @param documentation
	 *            the {@link Documentation} for the {@link Query} if any, <code>null</code> otherwise
	 * @return the created {@link Query} if any recognized, <code>null</code> otherwise
	 */
	protected Query parseQuery(Documentation documentation) {
		final Query res;

		if (text.startsWith(QUERY_START, currentPosition)) {
			final int startPosition = currentPosition;
			currentPosition += QUERY_START.length();
			skipSpaces();
			final VisibilityKind visibility = parseVisibility();
			final int missingVisibility;
			if (visibility == null) {
				missingVisibility = currentPosition;
			} else {
				missingVisibility = -1;
			}
			skipSpaces();
			final int identifierStartPosition = currentPosition;
			final String name = parseIdentifier();
			final int identifierEndPosition = currentPosition;
			final int missingName;
			if (name == null) {
				missingName = currentPosition;
			} else {
				missingName = -1;
			}
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final List<Variable> parameters = parseParameters();
			final int missingParameters;
			if (parameters.isEmpty()) {
				missingParameters = currentPosition;
			} else {
				missingParameters = -1;
			}
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final int missingColon = readMissingString(COLON);
			skipSpaces();
			final int typeEndLimit = getAqlExpressionEndLimit(EQUAL, QUERY_END);
			final AstResult type = parseWhileAqlTypeLiteral(text.substring(currentPosition, typeEndLimit));
			type.addAllPositonsTo(positions, currentPosition, lines[currentPosition],
					columns[currentPosition]);
			currentPosition += type.getEndPosition(type.getAst());
			final int missingType;
			if (type.getStartPosition(type.getAst()) == type.getEndPosition(type.getAst())) {
				missingType = currentPosition;
			} else {
				missingType = -1;
			}
			skipSpaces();
			final int missingEqual = readMissingString(EQUAL);
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(QUERY_END, QUERY_END);
			final Expression body = parseExpression(expressionEndLimit);
			skipSpaces();
			final int missingEnd = readMissingString(QUERY_END);
			final boolean missingValue = missingVisibility != -1 || missingName != -1 || missingType != -1
					|| parameters.isEmpty();
			final boolean missingParenthesis = missingOpenParenthesis != -1 || missingParameters != -1
					|| missingCloseParenthesis != -1;
			final boolean missingSymbols = missingColon != -1 || missingEqual != -1 || missingEnd != -1;
			if (missingVisibility != -1 || missingValue || missingParenthesis || missingSymbols) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorQuery();
				((ErrorQuery)res).setMissingVisibility(missingVisibility);
				((ErrorQuery)res).setMissingName(missingName);
				((ErrorQuery)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorQuery)res).setMissingParameters(missingParameters);
				((ErrorQuery)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorQuery)res).setMissingColon(missingColon);
				((ErrorQuery)res).setMissingType(missingType);
				((ErrorQuery)res).setMissingEqual(missingEqual);
				((ErrorQuery)res).setMissingEnd(missingEnd);
				errors.add((ErrorQuery)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createQuery();
			}
			res.setDocumentation(documentation);
			res.setVisibility(visibility);
			res.setName(name);
			res.getParameters().addAll(parameters);
			if (type != null) {
				res.setType(type);
				res.setTypeAql(type.getAst());
			}
			res.setBody(body);
			setIdentifierPositions(res, identifierStartPosition, identifierEndPosition);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link List} of at least one {@link Variable}.
	 * 
	 * @return the {@link List} of at least one {@link Variable}
	 */
	protected List<Variable> parseParameters() {
		final List<Variable> res = new ArrayList<Variable>();

		Variable variable = parseVariable();
		while (variable != null) {
			res.add(variable);
			skipSpaces();
			if (readString(COMMA)) {
				skipSpaces();
				variable = parseVariable();
			} else {
				skipSpaces();
				break;
			}
		}

		return res;
	}

	/**
	 * Parses a {@link Variable}.
	 * 
	 * @return the created {@link Variable} if any is recognized, <code>null</code> otherwise
	 */
	protected Variable parseVariable() {
		final Variable res;

		final int startPosition = currentPosition;
		final int identifierStartPosition = currentPosition;
		final String name = parseIdentifier();
		final int identifierEndPosition = currentPosition;
		final int missingName;
		if (name == null) {
			missingName = currentPosition;
		} else {
			missingName = -1;
		}
		skipSpaces();
		final int missingColon = readMissingString(COLON);
		skipSpaces();
		final int typeEndLimit = getAqlExpressionEndLimit(COMMA, CLOSE_PARENTHESIS);
		final AstResult type = parseWhileAqlTypeLiteral(text.substring(currentPosition, typeEndLimit));
		type.addAllPositonsTo(positions, currentPosition, lines[currentPosition], columns[currentPosition]);
		currentPosition += type.getEndPosition(type.getAst());
		final int missingType;
		if (type.getStartPosition(type.getAst()) == type.getEndPosition(type.getAst())) {
			missingType = currentPosition;
		} else {
			missingType = -1;
		}

		if (missingName == -1) {
			if (missingType != -1 || missingColon != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorVariable();
				((ErrorVariable)res).setMissingName(missingName);
				((ErrorVariable)res).setMissingColon(missingColon);
				((ErrorVariable)res).setMissingType(missingType);
				errors.add((ErrorVariable)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createVariable();
			}
			res.setName(name);
			if (type != null) {
				res.setType(type);
				res.setTypeAql(type.getAst());
			}
			setIdentifierPositions(res, identifierStartPosition, identifierEndPosition);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Template}.
	 * 
	 * @param documentation
	 *            the {@link Documentation} for the {@link Template} if any, <code>null</code> otherwise
	 * @param isMain
	 *            tells if the parsed {@link Template} should be a {@link Template#isMain() main}
	 *            {@link Template}.
	 * @return the created {@link Template} if any recognized, <code>null</code> otherwise
	 */
	// CHECKSTYLE:OFF
	protected Template parseTemplate(Documentation documentation, boolean isMain) {
		final Template res;

		if (text.startsWith(TEMPLATE_HEADER_START, currentPosition)) {
			final int startPosition = currentPosition;
			final int significantTextColumn = columns[startPosition] + INDENTATION;
			final int headerStartLine = lines[startPosition];
			currentPosition += TEMPLATE_HEADER_START.length();
			skipSpaces();
			final VisibilityKind visibility = parseVisibility();
			final int missingVisibility;
			if (visibility == null) {
				missingVisibility = currentPosition;
			} else {
				missingVisibility = -1;
			}
			skipSpaces();
			final int identifierStartPosition = currentPosition;
			final String name = parseIdentifier();
			final int identifierEndPosition = currentPosition;
			final int missingName;
			if (name == null) {
				missingName = currentPosition;
			} else {
				missingName = -1;
			}
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final List<Variable> parameters = parseParameters();
			final int missingParameters;
			if (parameters.isEmpty()) {
				missingParameters = currentPosition;
			} else {
				missingParameters = -1;
			}
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final int missingGuardOpenParenthesis;
			final int missingGuardCloseParenthesis;
			final Expression guardExpression;
			if (text.startsWith(TEMPLATE_GUARD, currentPosition)) {
				currentPosition += TEMPLATE_GUARD.length();
				skipSpaces();
				missingGuardOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
				skipSpaces();
				final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
						TEMPLATE_HEADER_END);
				guardExpression = parseExpression(expressionEndLimit);
				skipSpaces();
				missingGuardCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
				skipSpaces();
			} else {
				guardExpression = null;
				missingGuardOpenParenthesis = -1;
				missingGuardCloseParenthesis = -1;
			}
			final int missingPostCloseParenthesis;
			final Expression postExpression;
			if (text.startsWith(TEMPLATE_POST, currentPosition)) {
				currentPosition += TEMPLATE_POST.length();
				skipSpaces();
				final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
						TEMPLATE_HEADER_END);
				postExpression = parseExpression(expressionEndLimit);
				skipSpaces();
				missingPostCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
				skipSpaces();
			} else {
				missingPostCloseParenthesis = -1;
				postExpression = null;
			}
			final int missingEndHeader = readMissingString(TEMPLATE_HEADER_END);
			final Block body = parseBlock(headerStartLine, significantTextColumn, TEMPLATE_END);
			final int missingEnd = readMissingString(TEMPLATE_END);
			final boolean missingValue = missingVisibility != -1 || missingName != -1 || parameters.isEmpty();
			final boolean missingGuardParenthesis = missingGuardOpenParenthesis != -1
					|| missingGuardCloseParenthesis != -1;
			final boolean missingParenthesis = missingOpenParenthesis != -1 || missingParameters != -1
					|| missingCloseParenthesis != -1 || missingGuardParenthesis
					|| missingPostCloseParenthesis != -1;
			if (missingValue || missingParenthesis || missingEndHeader != -1 || missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorTemplate();
				((ErrorTemplate)res).setMissingVisibility(missingVisibility);
				((ErrorTemplate)res).setMissingName(missingName);
				((ErrorTemplate)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorTemplate)res).setMissingParameters(missingParameters);
				((ErrorTemplate)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorTemplate)res).setMissingGuardOpenParenthesis(missingGuardOpenParenthesis);
				((ErrorTemplate)res).setMissingGuardCloseParenthesis(missingGuardCloseParenthesis);
				((ErrorTemplate)res).setMissingPostCloseParenthesis(missingPostCloseParenthesis);
				((ErrorTemplate)res).setMissingEndHeader(missingEndHeader);
				((ErrorTemplate)res).setMissingEnd(missingEnd);
				errors.add((ErrorTemplate)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTemplate();
			}
			res.setMain(isMain);
			res.setDocumentation(documentation);
			res.setVisibility(visibility);
			res.setName(name);
			res.getParameters().addAll(parameters);
			res.setGuard(guardExpression);
			res.setPost(postExpression);
			res.setBody(body);
			setIdentifierPositions(res, identifierStartPosition, identifierEndPosition);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	// CHECKSTYLE:ON

	/**
	 * Parses a {@link Block}.
	 * 
	 * @param headerStartLine
	 *            the header start line
	 * @param significantTextColumn
	 *            the column where the text starts to be significant for {@link TextStatement}
	 * @param endBlocks
	 *            the end of block strings
	 * @return the created {@link Block}
	 */
	protected Block parseBlock(int headerStartLine, int significantTextColumn, String... endBlocks) {
		final Block res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();

		final int startPosition = currentPosition;
		boolean inlined = !text.startsWith(NEW_LINE, currentPosition)
				&& headerStartLine == lines[currentPosition];
		skipNewLine();
		int beforeStatementPosition = currentPosition;
		Statement statement = parseStatement(inlined, significantTextColumn);
		int afterStatementPosition = currentPosition;
		boolean onlyCommentsFromStart = true;
		LeafStatement lastLeafStatement = null;
		endOfBlock: while (beforeStatementPosition != afterStatementPosition) {
			if (statement != null) {
				if (statement instanceof Comment) {
					if (onlyCommentsFromStart) {
						inlined = !text.startsWith(NEW_LINE, currentPosition)
								&& headerStartLine == lines[currentPosition];
						// we skip the new line after a start of block header
						skipNewLine();
					}
					if (lastLeafStatement != null) {
						lastLeafStatement.setNewLineNeeded(lastLeafStatement.isNewLineNeeded() || (!inlined
								&& text.startsWith(NEW_LINE, currentPosition)));
					}
				} else if (statement instanceof LeafStatement) {
					lastLeafStatement = (LeafStatement)statement;
					onlyCommentsFromStart = false;
				} else {
					lastLeafStatement = null;
					onlyCommentsFromStart = false;
				}
				res.getStatements().add(statement);
			}
			for (String endOfBlock : endBlocks) {
				if (text.startsWith(endOfBlock, currentPosition)) {
					break endOfBlock;
				}
			}
			beforeStatementPosition = currentPosition;
			statement = parseStatement(inlined, significantTextColumn);
			afterStatementPosition = currentPosition;
		}
		res.setInlined(inlined);
		setPositions(res, startPosition, currentPosition);

		return res;
	}

	/**
	 * Skips the new line at current position if any, no operation otherwise.
	 */
	private void skipNewLine() {
		if (text.startsWith(NEW_LINE, currentPosition)) {
			currentPosition += NEW_LINE.length();
		}
	}

	/**
	 * Parses a {@link Statement}.
	 * 
	 * @param inlined
	 *            <code>true</code> if the current {@link Block} is inlined, <code>false</code> otherwise
	 * @param significantTextColumn
	 *            the column where the text starts to be significant for {@link TextStatement}
	 * @return the created {@link Statement} if any is recognize, <code>null</code> otherwise
	 */
	protected Statement parseStatement(boolean inlined, int significantTextColumn) {
		Statement res = null;

		// CHECKSTYLE:OFF
		final FileStatement file = parseFileStatement();
		if (file != null) {
			res = file;
		} else {
			final ForStatement forStatement = parseForStatement();
			if (forStatement != null) {
				res = forStatement;
			} else {
				final IfStatement ifStatement = parseIfStatement(IF_HEADER_START);
				if (ifStatement != null) {
					res = ifStatement;
				} else {
					final LetStatement letStatement = parseLetStatement();
					if (letStatement != null) {
						res = letStatement;
					} else {
						final ProtectedArea protectedArea = parseProtectedArea();
						if (protectedArea != null) {
							res = protectedArea;
						} else {
							final Comment comment = parseComment();
							if (comment != null) {
								res = comment;
							} else {
								final ExpressionStatement expressionStatement = parseExpressionStatement(
										inlined);
								if (expressionStatement != null) {
									res = expressionStatement;
								} else {
									final TextStatement text = parseTextStatement(inlined,
											significantTextColumn);
									if (text != null) {
										res = text;
									} else {
										res = null;
									}
								}
							}
						}
					}
				}
			}
		}
		// CHECKSTYLE:ON

		return res;
	}

	/**
	 * Parses a {@link IfStatement}.
	 * 
	 * @param startTag
	 *            the starting tag
	 * @return the created {@link IfStatement} if any is recognized, <code>null</code> otherwise
	 */
	protected IfStatement parseIfStatement(String startTag) {
		final IfStatement res;

		if (text.startsWith(startTag, currentPosition)) {
			final int startPosition = currentPosition;
			final int thenSignificantTextColumn = columns[startPosition] + INDENTATION;
			final int thenHeaderStartLine = lines[startPosition];
			currentPosition += startTag.length();
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS, IF_HEADER_END);
			final Expression condition = parseExpression(expressionEndLimit);
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final int missingEndHeader = readMissingString(IF_HEADER_END);
			final Block thenblock = parseBlock(thenHeaderStartLine, thenSignificantTextColumn, IF_END,
					IF_ELSEIF, IF_ELSE);
			final IfStatement elseIf = parseIfStatement(IF_ELSEIF);
			final boolean parseEndIf = elseIf == null;
			final Block elseBlock;
			if (elseIf != null) {
				elseBlock = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();
				elseBlock.getStatements().add(elseIf);
			} else if (readString(IF_ELSE)) {
				final int elseStartPosition = currentPosition - IF_ELSE.length();
				final int elseSignificantTextColumn = columns[elseStartPosition] + INDENTATION;
				final int headerStartLine = lines[elseStartPosition];
				elseBlock = parseBlock(headerStartLine, elseSignificantTextColumn, IF_END);
			} else {
				elseBlock = null;
			}
			final int missingEnd;
			if (parseEndIf) {
				missingEnd = readMissingString(IF_END);
			} else {
				missingEnd = -1;
			}
			if (missingOpenParenthesis != -1 || missingCloseParenthesis != -1 || missingEndHeader != -1
					|| missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorIfStatement();
				((ErrorIfStatement)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorIfStatement)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorIfStatement)res).setMissingEndHeader(missingEndHeader);
				((ErrorIfStatement)res).setMissingEnd(missingEnd);
				errors.add((ErrorIfStatement)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createIfStatement();
			}
			res.setCondition(condition);
			res.setThen(thenblock);
			res.setElse(elseBlock);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link ForStatement}.
	 * 
	 * @return the created {@link ForStatement} if any is recognized, <code>null</code> otherwise
	 */
	protected ForStatement parseForStatement() {
		final ForStatement res;

		if (text.startsWith(FOR_HEADER_START, currentPosition)) {
			final int startPosition = currentPosition;
			final int significantTextColumn = columns[startPosition] + INDENTATION;
			final int headerStartLine = lines[startPosition];
			currentPosition += FOR_HEADER_START.length();
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final int bindingEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS, FOR_HEADER_END);
			final Binding binding = parseBinding(PIPE, bindingEndLimit);
			final int missingBinding;
			if (binding == null) {
				missingBinding = currentPosition;
			} else {
				missingBinding = -1;
			}
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final Expression separator;
			final int missingSeparatorCloseParenthesis;
			if (readString(FOR_SEPARATOR)) {
				skipSpaces();
				final int separatorEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS, FOR_HEADER_END);
				separator = parseExpression(separatorEndLimit);
				skipSpaces();
				missingSeparatorCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
				skipSpaces();
			} else {
				separator = null;
				missingSeparatorCloseParenthesis = -1;
			}
			final int missingEndHeader = readMissingString(FOR_HEADER_END);
			final Block body = parseBlock(headerStartLine, significantTextColumn, FOR_END);
			final int missingEnd = readMissingString(FOR_END);
			final boolean missingParenthesis = missingOpenParenthesis != -1 || missingBinding != -1
					|| missingCloseParenthesis != -1 || missingSeparatorCloseParenthesis != -1;
			if (missingParenthesis || missingEndHeader != -1 || missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorForStatement();
				((ErrorForStatement)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorForStatement)res).setMissingBinding(missingBinding);
				((ErrorForStatement)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorForStatement)res).setMissingSeparatorCloseParenthesis(
						missingSeparatorCloseParenthesis);
				((ErrorForStatement)res).setMissingEndHeader(missingEndHeader);
				((ErrorForStatement)res).setMissingEnd(missingEnd);
				errors.add((ErrorForStatement)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createForStatement();
			}
			res.setBinding(binding);
			res.setSeparator(separator);
			res.setBody(body);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link FileStatement}.
	 * 
	 * @return the created {@link FileStatement} if any is recognized, <code>null</code> otherwise
	 */
	protected FileStatement parseFileStatement() {
		final FileStatement res;

		if (text.startsWith(FILE_HEADER_START, currentPosition)) {
			final int startPosition = currentPosition;
			final int significantTextColumn = columns[startPosition] + INDENTATION;
			final int headerStartLine = lines[startPosition];
			currentPosition += FILE_HEADER_START.length();
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final int urlExpressionEndLimit = getAqlExpressionEndLimit(COMMA, FILE_HEADER_END);
			final Expression url = parseExpression(urlExpressionEndLimit);
			skipSpaces();
			final int missingComma = readMissingString(COMMA);
			skipSpaces();
			final OpenModeKind openMode = parseOpenModeKind();
			final int missingOpenMode;
			if (openMode == null) {
				missingOpenMode = currentPosition;
				final int position = getNext(COMMA, CLOSE_PARENTHESIS);
				if (position >= 0) {
					currentPosition = position;
				}
			} else {
				missingOpenMode = -1;
			}
			final Expression charset;
			if (readString(COMMA)) {
				skipSpaces();
				final int charsetExpressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
						FILE_HEADER_END);
				charset = parseExpression(charsetExpressionEndLimit);
			} else {
				charset = null;
			}
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final int missingEndHeader = readMissingString(FILE_HEADER_END);
			final Block body = parseBlock(headerStartLine, significantTextColumn, FILE_END);
			final int missingEnd = readMissingString(FILE_END);
			final boolean missingSymbole = missingOpenParenthesis != -1 || missingCloseParenthesis != -1
					|| missingComma != -1;
			if (missingOpenMode != -1 || missingSymbole || missingEndHeader != -1 || missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorFileStatement();
				((ErrorFileStatement)res).setMissingOpenMode(missingOpenMode);
				((ErrorFileStatement)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorFileStatement)res).setMissingComma(missingComma);
				((ErrorFileStatement)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorFileStatement)res).setMissingEndHeader(missingEndHeader);
				((ErrorFileStatement)res).setMissingEnd(missingEnd);
				errors.add((ErrorFileStatement)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createFileStatement();
			}
			res.setUrl(url);
			res.setCharset(charset);
			res.setMode(openMode);
			res.setBody(body);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses {@link OpenModeKind}.
	 * 
	 * @return the {@link OpenModeKind} if any is recognized, <code>null</code> otherwise
	 */
	protected OpenModeKind parseOpenModeKind() {
		OpenModeKind res = null;

		for (OpenModeKind openMode : OpenModeKind.VALUES) {
			if (text.startsWith(openMode.getName(), currentPosition)) {
				res = openMode;
				currentPosition += openMode.getName().length();
				break;
			}
		}

		return res;
	}

	/**
	 * Parses a {@link LetStatement}.
	 * 
	 * @return the created {@link LetStatement} if any is recognized, <code>null</code> otherwise
	 */
	protected LetStatement parseLetStatement() {
		final LetStatement res;

		if (text.startsWith(LET_HEADER_START, currentPosition)) {
			final int startPosition = currentPosition;
			final int significantTextColumn = columns[startPosition] + INDENTATION;
			final int headerStartLine = lines[startPosition];
			currentPosition += LET_HEADER_START.length();
			skipSpaces();
			final List<Binding> bindings = parseBindings(EQUAL, LET_HEADER_END);
			final int missingBindings;
			if (bindings.isEmpty()) {
				missingBindings = currentPosition;
			} else {
				missingBindings = -1;
			}
			skipSpaces();
			final int missingEndHeader = readMissingString(LET_HEADER_END);
			final Block body = parseBlock(headerStartLine, significantTextColumn, LET_END);
			final int missingEnd = readMissingString(LET_END);
			if (missingBindings != -1 || missingEndHeader != -1 || missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorLetStatement();
				((ErrorLetStatement)res).setMissingBindings(missingBindings);
				((ErrorLetStatement)res).setMissingEndHeader(missingEndHeader);
				((ErrorLetStatement)res).setMissingEnd(missingEnd);
				errors.add((ErrorLetStatement)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createLetStatement();
			}
			res.getVariables().addAll(bindings);
			res.setBody(body);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Binding}.
	 * 
	 * @param affectationSymbol
	 *            the affectation symbol
	 * @param endLimit
	 *            the end limit of the binding
	 * @return the created {@link Binding} if any is recognized, <code>null</code> otherwise
	 */
	protected Binding parseBinding(String affectationSymbol, int endLimit) {
		final Binding res;

		final int startPosition = currentPosition;
		final int identifierStartPosition = currentPosition;
		final String name = parseIdentifier();
		final int identifierEndPosition = currentPosition;
		final int missingName;
		if (name == null) {
			missingName = currentPosition;
		} else {
			missingName = -1;
		}
		skipSpaces();
		int missingType;
		AstResult type;
		int missingColon = currentPosition;
		if (readString(COLON)) {
			skipSpaces();
			final int typeEndLimit = Math.min(getAqlExpressionEndLimit(affectationSymbol, COMMA), endLimit);
			type = parseWhileAqlTypeLiteral(text.substring(currentPosition, typeEndLimit));
			type.addAllPositonsTo(positions, currentPosition, lines[currentPosition],
					columns[currentPosition]);
			currentPosition += type.getEndPosition(type.getAst());
			if (type.getStartPosition(type.getAst()) == type.getEndPosition(type.getAst())) {
				missingType = currentPosition;
			} else {
				missingType = -1;
			}
			missingColon = -1;
		} else {
			skipSpaces();
			final int typeEndLimit = Math.min(getAqlExpressionEndLimit(affectationSymbol, COMMA), endLimit);
			type = parseWhileAqlTypeLiteral(text.substring(currentPosition, typeEndLimit));
			type.addAllPositonsTo(positions, currentPosition, lines[currentPosition],
					columns[currentPosition]);
			currentPosition += type.getEndPosition(type.getAst());
			if (type.getStartPosition(type.getAst()) == type.getEndPosition(type.getAst())) {
				missingColon = -1;
				type = null;
			}
			missingType = -1;
		}
		skipSpaces();
		int missingAffectationSymbol = readMissingString(affectationSymbol);
		if (missingColon != -1 && missingAffectationSymbol != -1) {
			final int typeEndLimit = Math.min(getAqlExpressionEndLimit(affectationSymbol, COMMA), endLimit);
			type = parseWhileAqlTypeLiteral(text.substring(currentPosition, typeEndLimit));
			type.addAllPositonsTo(positions, currentPosition, lines[currentPosition],
					columns[currentPosition]);
			currentPosition += type.getEndPosition(type.getAst());
			if (type.getStartPosition(type.getAst()) == type.getEndPosition(type.getAst())) {
				missingType = currentPosition;
			} else {
				missingType = -1;
			}
			skipSpaces();
			missingAffectationSymbol = readMissingString(affectationSymbol);
		}
		skipSpaces();
		final Expression expression = parseExpression(endLimit);
		if (missingName == -1) {
			final boolean hasErrorExpression = expression.getAst()
					.getAst() instanceof org.eclipse.acceleo.query.ast.Error;
			if (missingColon != -1 || missingType != -1 || missingAffectationSymbol != -1
					|| hasErrorExpression) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorBinding();
				((ErrorBinding)res).setMissingName(missingName);
				((ErrorBinding)res).setMissingColon(missingColon);
				((ErrorBinding)res).setMissingType(missingType);
				if (missingAffectationSymbol != -1) {
					((ErrorBinding)res).setMissingAffectationSymbole(affectationSymbol);
					((ErrorBinding)res).setMissingAffectationSymbolePosition(missingAffectationSymbol);
				}
				errors.add((ErrorBinding)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBinding();
			}
			res.setName(name);
			if (type != null) {
				res.setType(type);
				res.setTypeAql(type.getAst());
			}
			res.setInitExpression(expression);
			setIdentifierPositions(res, identifierStartPosition, identifierEndPosition);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link List} of at least one {@link Binding}.
	 * 
	 * @param affectationSymbol
	 *            the affectation symbol
	 * @param endTag
	 *            the end tag
	 * @return the {@link List} of at least one {@link Binding}
	 */
	protected List<Binding> parseBindings(String affectationSymbol, String endTag) {
		final List<Binding> res = new ArrayList<Binding>();

		int bindingEndLimit = getAqlExpressionEndLimit(COMMA, endTag);
		Binding binding = parseBinding(affectationSymbol, bindingEndLimit);
		while (binding != null) {
			res.add(binding);
			skipSpaces();
			if (readString(COMMA)) {
				skipSpaces();
				bindingEndLimit = getAqlExpressionEndLimit(COMMA, endTag);
				binding = parseBinding(affectationSymbol, bindingEndLimit);
			} else {
				skipSpaces();
				break;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link EPackage} with the given {@link EPackage#getName() name}.
	 * 
	 * @param ePackageName
	 *            the {@link EPackage#getName() name}
	 * @return the {@link EPackage} with the given {@link EPackage#getName() name} if any, <code>null</code>
	 *         otherwise
	 */
	protected EPackage getEPackage(String ePackageName) {
		EPackage res = null;

		for (Object object : EPackage.Registry.INSTANCE.values()) {
			if (object instanceof EPackage) {
				if (ePackageName.equals(((EPackage)object).getName())) {
					res = (EPackage)object;
					break;
				}
			} else if (object instanceof EPackage.Descriptor) {
				// TODO this code load all EPackages
				final EPackage ePackage = ((EPackage.Descriptor)object).getEPackage();
				if (ePackageName.equals(ePackage.getName())) {
					res = ePackage;
				}
			}
		}

		return res;
	}

	/**
	 * Parses a {@link ProtectedArea}.
	 * 
	 * @return the created {@link ProtectedArea} if any is recognized, <code>null</code> otherwise
	 */
	protected ProtectedArea parseProtectedArea() {
		final ProtectedArea res;

		if (text.startsWith(PROTECTED_AREA_HEADER_START, currentPosition)) {
			final int startPosition = currentPosition;
			final int significantTextColumn = columns[startPosition] + INDENTATION;
			final int headerStartLine = lines[startPosition];
			currentPosition += PROTECTED_AREA_HEADER_START.length();
			skipSpaces();
			final int missingOpenParenthesis = readMissingString(OPEN_PARENTHESIS);
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
					PROTECTED_AREA_HEADER_END);
			final Expression id = parseExpression(expressionEndLimit);
			skipSpaces();
			final int missingCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
			skipSpaces();
			final Expression startTagPrefix;
			final int missingStartTagPrefixCloseParenthesis;
			if (readString(PROTECTED_AREA_START_TAG_PREFIX)) {
				skipSpaces();
				final int startTagPrefixEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
						PROTECTED_AREA_HEADER_END);
				startTagPrefix = parseExpression(startTagPrefixEndLimit);
				skipSpaces();
				missingStartTagPrefixCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
				skipSpaces();
			} else {
				startTagPrefix = null;
				missingStartTagPrefixCloseParenthesis = -1;
			}
			final Expression endTagPrefix;
			final int missingEndTagPrefixCloseParenthesis;
			if (readString(PROTECTED_AREA_END_TAG_PREFIX)) {
				skipSpaces();
				final int startTagPrefixEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
						PROTECTED_AREA_HEADER_END);
				endTagPrefix = parseExpression(startTagPrefixEndLimit);
				skipSpaces();
				missingEndTagPrefixCloseParenthesis = readMissingString(CLOSE_PARENTHESIS);
				skipSpaces();
			} else {
				endTagPrefix = null;
				missingEndTagPrefixCloseParenthesis = -1;
			}
			final int missingEndHeader = readMissingString(PROTECTED_AREA_HEADER_END);
			final Block body = parseBlock(headerStartLine, significantTextColumn, PROTECTED_AREA_END);
			final int missingEnd = readMissingString(PROTECTED_AREA_END);
			if (missingOpenParenthesis != -1 || missingCloseParenthesis != -1
					|| missingStartTagPrefixCloseParenthesis != -1
					|| missingEndTagPrefixCloseParenthesis != -1 || missingEndHeader != -1
					|| missingEnd != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorProtectedArea();
				((ErrorProtectedArea)res).setMissingOpenParenthesis(missingOpenParenthesis);
				((ErrorProtectedArea)res).setMissingCloseParenthesis(missingCloseParenthesis);
				((ErrorProtectedArea)res).setMissingStartTagPrefixCloseParenthesis(
						missingStartTagPrefixCloseParenthesis);
				((ErrorProtectedArea)res).setMissingEndTagPrefixCloseParenthesis(
						missingEndTagPrefixCloseParenthesis);
				((ErrorProtectedArea)res).setMissingEndHeader(missingEndHeader);
				((ErrorProtectedArea)res).setMissingEnd(missingEnd);
				errors.add((ErrorProtectedArea)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createProtectedArea();
			}
			res.setId(id);
			res.setStartTagPrefix(startTagPrefix);
			res.setEndTagPrefix(endTagPrefix);
			res.setId(id);
			res.setBody(body);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses an {@link ExpressionStatement}.
	 * 
	 * @param inlined
	 *            <code>true</code> if the current {@link Block} is inlined, <code>false</code> otherwise
	 * @return the created {@link ExpressionStatement} if any is recognized, <code>null</code> otherwise
	 */
	protected ExpressionStatement parseExpressionStatement(boolean inlined) {
		final ExpressionStatement res;

		if (text.startsWith(EMPTY_EXPRESSION_STATEMENT, currentPosition) || !text.startsWith(END_BLOCK_PREFIX,
				currentPosition) && !text.startsWith(IF_ELSE, currentPosition) && text.startsWith(
						EXPRESSION_STATEMENT_START, currentPosition)) {
			final int startPosition = currentPosition;
			currentPosition += EXPRESSION_STATEMENT_START.length();
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(EXPRESSION_STATEMENT_END,
					EXPRESSION_STATEMENT_END);
			final Expression expression = parseExpression(expressionEndLimit);
			skipSpaces();
			final int missingEndHeader = readMissingString(EXPRESSION_STATEMENT_END);
			if (missingEndHeader != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorExpressionStatement();
				((ErrorExpressionStatement)res).setMissingEndHeader(missingEndHeader);
				errors.add((ErrorExpressionStatement)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpressionStatement();
				res.setNewLineNeeded(!inlined && text.startsWith(NEW_LINE, currentPosition));
			}
			res.setExpression(expression);
			if (res.isNewLineNeeded()) {
				currentPosition += NEW_LINE.length();
			}
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses an {@link Expression}.
	 * 
	 * @param endLimit
	 *            the end limit of the expression
	 * @return the created {@link Expression}
	 */
	protected Expression parseExpression(int endLimit) {
		final Expression res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpression();

		final AstResult astResult = parseWhileAqlExpression(text.substring(currentPosition, endLimit));
		final int startPosition = currentPosition;
		res.setAst(astResult);
		res.setAql(astResult.getAst());
		final int endPosition = currentPosition + astResult.getEndPosition(astResult.getAst());
		setPositions(res, startPosition, endPosition);
		currentPosition = endPosition;
		astResult.addAllPositonsTo(positions, startPosition, lines[startPosition], columns[startPosition]);

		return res;
	}

	/**
	 * Parses {@link VisibilityKind}.
	 * 
	 * @return the {@link VisibilityKind} if any is recognized, <code>null</code> otherwise
	 */
	protected VisibilityKind parseVisibility() {
		VisibilityKind res = null;

		for (VisibilityKind visibility : VisibilityKind.VALUES) {
			if (text.startsWith(visibility.getName(), currentPosition)) {
				res = visibility;
				currentPosition += visibility.getName().length();
				break;
			}
		}

		return res;
	}

	/**
	 * Parses {@link Metamodel}.
	 * 
	 * @return the {@link Metamodel} if any recognized, <code>null</code> otherwise
	 */
	protected Metamodel parseMetamodel() {
		final Metamodel res;

		if (readString(QUOTE)) {
			final int startPosition = currentPosition;
			int nextQuote = getNext(QUOTE, CLOSE_PARENTHESIS, MODULE_HEADER_END);
			if (nextQuote < 0) {
				nextQuote = text.length();
			}
			final String nsURI = text.substring(currentPosition, nextQuote);
			currentPosition = nextQuote;
			final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
			final int missingEndQuote = readMissingString(QUOTE);
			if (ePackage == null || missingEndQuote != -1) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorMetamodel();
				((ErrorMetamodel)res).setMissingEndQuote(missingEndQuote);
				errors.add((ErrorMetamodel)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createMetamodel();
			}
			res.setReferencedPackage(ePackage);
			setPositions(res, startPosition, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the position of the next token.
	 * 
	 * @param tokens
	 *            tokens
	 * @return the position of the next token if any found, <code>-1</code> otherwise
	 */
	protected int getNext(String... tokens) {
		int res = -1;

		for (String token : tokens) {
			final int position = text.indexOf(token, currentPosition);
			if (position >= 0) {
				res = position;
				break;
			}
		}

		return res;
	}

	/**
	 * Parses {@link TextStatement}.
	 * 
	 * @param inlined
	 *            <code>true</code> if the current {@link Block} is inlined, <code>false</code> otherwise
	 * @param significantTextColumn
	 *            the column where the text starts to be significant for {@link TextStatement}
	 * @return the created {@link TextStatement} if any, <code>null</code> otherwise
	 */
	protected TextStatement parseTextStatement(boolean inlined, int significantTextColumn) {
		final TextStatement res;

		int endOfText = getNext(TEXT_END);
		if (endOfText < 0) {
			endOfText = text.length();
		}
		if (currentPosition != endOfText) {
			if (inlined) {
				// raw copy of the text
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTextStatement();
				setPositions(res, currentPosition, endOfText);
				res.setValue(text.substring(currentPosition, endOfText));
				res.setNewLineNeeded(false);
				currentPosition = endOfText;
			} else {
				res = getSignificantTextStatement(significantTextColumn, endOfText);
			}
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the significant {@link TextStatement} at the given column and until the end of text.
	 * 
	 * @param significantTextColumn
	 *            the significant text column
	 * @param endOfText
	 *            the end of text
	 * @return the significant {@link TextStatement} at the given column and until the end of text.
	 */
	private TextStatement getSignificantTextStatement(int significantTextColumn, int endOfText) {
		final TextStatement res;

		int localStartOfText = currentPosition;
		if (columns[localStartOfText] == 0 && text.startsWith(NEW_LINE, localStartOfText)) {
			final NewLineStatement newLineStatement = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createNewLineStatement();
			newLineStatement.setIndentationNeeded(false);
			newLineStatement.setNewLineNeeded(true);
			newLineStatement.setValue("");
			currentPosition += NEW_LINE.length();
			setPositions(newLineStatement, localStartOfText, currentPosition);
			res = newLineStatement;
		} else {
			res = getNonEmptyLineTextStatement(significantTextColumn, endOfText);
		}

		return res;
	}

	/**
	 * Gets the significant {@link TextStatement} for indented text.
	 * 
	 * @param significantTextColumn
	 *            the significant text column
	 * @param endOfText
	 *            the end of text
	 * @return the significant {@link TextStatement} for indented text
	 */
	private TextStatement getNonEmptyLineTextStatement(int significantTextColumn, int endOfText) {
		final TextStatement res;

		int localStartOfText = currentPosition;
		if (text.startsWith(NEW_LINE, localStartOfText)) {
			if (columns[localStartOfText] > significantTextColumn) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTextStatement();
				res.setNewLineNeeded(true);
				res.setValue("");
				currentPosition += NEW_LINE.length();
				setPositions(res, localStartOfText, currentPosition);
			} else {
				currentPosition = currentPosition + NEW_LINE.length(); // skip the new line
				res = getNonEmptyLineNonEmptyTextTextStatement(significantTextColumn, endOfText);
			}
		} else {
			res = getNonEmptyLineNonEmptyTextTextStatement(significantTextColumn, endOfText);
		}

		return res;
	}

	/**
	 * Gets the non empty line {@link TextStatement}.
	 * 
	 * @param significantTextColumn
	 *            the significant text column
	 * @param endOfText
	 *            the end of text
	 * @return the non empty line {@link TextStatement}
	 */
	private TextStatement getNonEmptyLineNonEmptyTextTextStatement(int significantTextColumn, int endOfText) {
		final TextStatement res;

		int localStartOfText = currentPosition;
		while (localStartOfText < endOfText && columns[localStartOfText] < significantTextColumn) {
			localStartOfText++;
		}
		if (text.startsWith(NEW_LINE, localStartOfText)
				&& columns[localStartOfText] == significantTextColumn) {
			final NewLineStatement newLineStatement = AcceleoPackage.eINSTANCE.getAcceleoFactory()
					.createNewLineStatement();
			newLineStatement.setIndentationNeeded(true);
			newLineStatement.setNewLineNeeded(true);
			newLineStatement.setValue("");
			currentPosition = localStartOfText + NEW_LINE.length();
			setPositions(newLineStatement, localStartOfText, currentPosition);
			res = newLineStatement;
		} else if (localStartOfText < endOfText) {
			int localEndOfText = localStartOfText;
			while (localEndOfText < endOfText && columns[localEndOfText] >= significantTextColumn) {
				localEndOfText++;
			}
			final boolean needNewLine;
			if (columns[localEndOfText] == 0) {
				localEndOfText = localEndOfText - NEW_LINE.length(); // remove the new line
				needNewLine = true;
			} else {
				needNewLine = false;
			}
			final boolean isEmptyLine = columns[localStartOfText] == significantTextColumn && needNewLine;
			if (localStartOfText < localEndOfText || isEmptyLine) {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTextStatement();
				res.setValue(text.substring(localStartOfText, localEndOfText));
				res.setNewLineNeeded(needNewLine);
				if (needNewLine) {
					localEndOfText += NEW_LINE.length();
				}
				setPositions(res, localStartOfText, localEndOfText);
				currentPosition = localEndOfText;
			} else {
				res = null;
				currentPosition = localEndOfText;
			}
		} else {
			res = null;
			currentPosition = endOfText;
		}

		return res;
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	protected AstResult parseWhileAqlExpression(String expression) {
		final AstResult result;

		if (expression != null && expression.length() > 0) {
			AstBuilderListener astBuilder = new AstBuilderListener();
			CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
			QueryLexer lexer = new QueryLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			lexer.removeErrorListeners();
			lexer.addErrorListener(astBuilder.getErrorListener());
			TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
			QueryParser parser = new QueryParser(tokens);
			parser.addParseListener(astBuilder);
			parser.removeErrorListeners();
			parser.addErrorListener(astBuilder.getErrorListener());
			// parser.setTrace(true);
			parser.expression();
			result = astBuilder.getAstResult();
		} else {
			org.eclipse.acceleo.query.ast.ErrorExpression errorExpression = (org.eclipse.acceleo.query.ast.ErrorExpression)EcoreUtil
					.create(AstPackage.eINSTANCE.getErrorExpression());
			List<org.eclipse.acceleo.query.ast.Error> aqlErrors = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			aqlErrors.add(errorExpression);
			final Positions aqlPositions = new Positions();
			if (expression != null) {
				aqlPositions.setIdentifierStartPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierStartLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierStartColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setIdentifierEndColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setStartColumns(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndPositions(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndLines(errorExpression, Integer.valueOf(0));
				aqlPositions.setEndColumns(errorExpression, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"missing expression", new Object[] {errorExpression }));
			result = new AstResult(errorExpression, aqlPositions, aqlErrors, diagnostic);
		}

		return result;
	}

	/**
	 * Parses while matching an AQL expression.
	 * 
	 * @param expression
	 *            the expression to parse
	 * @return the corresponding {@link AstResult}
	 */
	protected AstResult parseWhileAqlTypeLiteral(String expression) {
		final AstResult result;

		if (expression != null && expression.length() > 0) {
			AstBuilderListener astBuilder = new AstBuilderListener();
			CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
			QueryLexer lexer = new QueryLexer(input);
			lexer.setTokenFactory(new CommonTokenFactory(true));
			lexer.removeErrorListeners();
			lexer.addErrorListener(astBuilder.getErrorListener());
			TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
			QueryParser parser = new QueryParser(tokens);
			parser.addParseListener(astBuilder);
			parser.removeErrorListeners();
			parser.addErrorListener(astBuilder.getErrorListener());
			// parser.setTrace(true);
			parser.typeLiteral();
			result = astBuilder.getAstResult();
		} else {
			ErrorTypeLiteral errorTypeLiteral = (ErrorTypeLiteral)EcoreUtil.create(AstPackage.eINSTANCE
					.getErrorTypeLiteral());
			List<org.eclipse.acceleo.query.ast.Error> errs = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			errs.add(errorTypeLiteral);
			final Positions aqlPositions = new Positions();
			if (expression != null) {
				aqlPositions.setIdentifierStartPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierStartLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierStartColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setIdentifierEndColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setStartColumns(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndPositions(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndLines(errorTypeLiteral, Integer.valueOf(0));
				aqlPositions.setEndColumns(errorTypeLiteral, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"missing type literal", new Object[] {errorTypeLiteral }));
			result = new AstResult(errorTypeLiteral, aqlPositions, errs, diagnostic);
		}

		return result;
	}

	/**
	 * Gets the end AQL expression limit according to the given end delimiter and the given end tag.
	 * 
	 * @param endDelimiter
	 *            the end delimiter
	 * @param endTag
	 *            the end tag
	 * @return the end AQL expression limit according to the given end delimiter and the given end tag if any
	 *         found, <code>-1</code> otherwise
	 */
	protected int getAqlExpressionEndLimit(String endDelimiter, String endTag) {
		int res = currentPosition;

		int parenthesisDepth = 0;
		int curlyBracketDepth = 0;
		while (res < text.length()) {
			if ((text.startsWith(endTag, res) || endLimitReached(endDelimiter, res)) && isProperlyParenthesed(
					parenthesisDepth, curlyBracketDepth)) {
				break;
			}
			switch (text.charAt(res)) {
				case '\'':
					// skip string literal
					boolean isEscaped = false;
					res++;
					endStrinLiteral: while (res < text.length()) {
						switch (text.charAt(res)) {
							case '\\':
								isEscaped = !isEscaped;
								res++;
								break;
							case '\'':
								if (!isEscaped) {
									res++;
									break endStrinLiteral;
								}
								res++;
								isEscaped = false;
								break;
							default:
								res++;
								isEscaped = false;
								break;
						}
					}
					break;
				case '{':
					curlyBracketDepth++;
					res++;
					break;
				case '}':
					curlyBracketDepth--;
					res++;
					break;
				case '(':
					parenthesisDepth++;
					res++;
					break;
				case ')':
					parenthesisDepth--;
					res++;
					break;
				default:
					res++;
					break;
			}
		}

		return res;
	}

	/**
	 * Tells if the end limit for the search has been reached.
	 * 
	 * @param endDelimiter
	 *            the end delimiter
	 * @param position
	 *            the current position
	 * @return <code>true</code> if the end limit for the search has been reached, <code>false</code>
	 *         otherwise
	 */
	private boolean endLimitReached(String endDelimiter, int position) {
		return text.startsWith(endDelimiter, position) || text.startsWith(TEXT_END, position);
	}

	/**
	 * Tells if the expression is properly parenthesed so far.
	 * 
	 * @param parenthesisDepth
	 *            the parenthesis depth
	 * @param curlyBracketDepth
	 *            the curly bracket depth
	 * @return <code>true</code> if the expression is properly parenthesed so far, <code>false</code>
	 *         otherwise
	 */
	private boolean isProperlyParenthesed(int parenthesisDepth, int curlyBracketDepth) {
		return parenthesisDepth == 0 && curlyBracketDepth == 0;
	}
}
