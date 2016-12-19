/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.eclipse.acceleo.query.parser.QueryParser;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Acceleo parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoParser {

	/**
	 * In line end delimiter.
	 */
	private static final String NO_SLASH_END = "]";

	/**
	 * In line end delimiter.
	 */
	private static final String SLASH_END = "/]";

	/**
	 * End of {@link TextStatement}.
	 */
	private static final String TEXT_END = "[";

	/**
	 * Start of {@link Comment}.
	 */
	private static final String COMMENT_START = "[comment ";

	/**
	 * End of {@link Comment}.
	 */
	private static final String COMMENT_END = NO_SLASH_END;

	/**
	 * Start of {@link Module} header.
	 */
	private static final String MODULE_HEADER_START = "[module ";

	/**
	 * End of {@link Module} header.
	 */
	private static final String MODULE_HEADER_END = SLASH_END;

	/**
	 * Open parenthesis.
	 */
	private static final String OPEN_PARENTHESIS = "(";

	/**
	 * Close parenthesis.
	 */
	private static final String CLOSE_PARENTHESIS = ")";

	/**
	 * A comma.
	 */
	private static final String COMMA = ",";

	/**
	 * A semicolon.
	 */
	private static final String SEMICOLON = ":";

	/**
	 * A qualifier separator.
	 */
	private static final String QUALIFIER_SEPARATOR = "::";

	/**
	 * An equal.
	 */
	private static final String EQUAL = "=";

	/**
	 * A pipe.
	 */
	private static final String PIPE = "|";

	/**
	 * A quote.
	 */
	private static final String QUOTE = "'";

	/**
	 * Start of {@link Template} header.
	 */
	private static final String TEMPLATE_HEADER_START = "[template ";

	/**
	 * Start of {@link Template} header.
	 */
	private static final String TEMPLATE_HEADER_END = NO_SLASH_END;

	/**
	 * Post of {@link Template}.
	 */
	private static final String TEMPLATE_POST = "post(";

	/**
	 * End of {@link Template}.
	 */
	private static final String TEMPLATE_END = "[/template]";

	/**
	 * Start of {@link ExpressionStatement}.
	 */
	private static final String EXPRESSION_STATEMENT_START = "[";

	/**
	 * End of {@link ExpressionStatement}.
	 */
	private static final String EXPRESSION_STATEMENT_END = SLASH_END;

	/**
	 * Start of {@link ProtectedArea} header.
	 */
	private static final String PROTECTED_AREA_HEADER_START = "[protected ";

	/**
	 * End of {@link ProtectedArea} header.
	 */
	private static final String PROTECTED_AREA_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link ProtectedArea}.
	 */
	private static final String PROTECTED_AREA_END = "[/protected]";

	/**
	 * Start of {@link FileStatement} header.
	 */
	private static final String FILE_HEADER_START = "[file ";

	/**
	 * End of {@link FileStatement} header.
	 */
	private static final String FILE_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link FileStatement}.
	 */
	private static final String FILE_END = "[/file]";

	/**
	 * Start of {@link LetStatement} header.
	 */
	private static final String LET_HEADER_START = "[let ";

	/**
	 * End of {@link LetStatement} header.
	 */
	private static final String LET_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link LetStatement}.
	 */
	private static final String LET_END = "[/let]";

	/**
	 * Start of {@link ForStatement} header.
	 */
	private static final String FOR_HEADER_START = "[for ";

	/**
	 * End of {@link ForStatement} header.
	 */
	private static final String FOR_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link ForStatement}.
	 */
	private static final String FOR_END = "[/for]";

	/**
	 * Start of {@link IfStatement} header.
	 */
	private static final String IF_HEADER_START = "[if ";

	/**
	 * End of {@link IfStatement} header.
	 */
	private static final String IF_HEADER_END = NO_SLASH_END;

	/**
	 * End of {@link IfStatement}.
	 */
	private static final String IF_ELSEIF = "[elseif ";

	/**
	 * End of {@link IfStatement}.
	 */
	private static final String IF_ELSE = "[else]";

	/**
	 * End of {@link IfStatement}.
	 */
	private static final String IF_END = "[/if]";

	/**
	 * Start of {@link Query}.
	 */
	private static final String QUERY_START = "[query ";

	/**
	 * End of {@link Query}.
	 */
	private static final String QUERY_END = SLASH_END;

	/**
	 * Start of {@link Query}.
	 */
	private static final String IMPORT_START = "[import ";

	/**
	 * End of {@link Query}.
	 */
	private static final String IMPORT_END = SLASH_END;

	/**
	 * Extends key work.
	 */
	private static final String EXTENDS = "extends ";

	/**
	 * The {@link IQueryEnvironment}.
	 */
	private final IQueryEnvironment queryEnvironment;

	/**
	 * The parser currentPosition.
	 */
	private int currentPosition;

	/**
	 * The source text.
	 */
	private String text;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 */
	public AcceleoParser(IQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * Parses the given source text.
	 * 
	 * @param source
	 *            the source text
	 * @return the created {@link Module} if any is recognized, <code>null</code> otherwise
	 */
	public Module parse(String source) {
		this.text = source;

		final List<Comment> comments = parseComments();

		return parseModule(comments);
	}

	/**
	 * Parses a {@link List} of {@link Comment}.
	 * 
	 * @return the created {@link List} of {@link Comment}
	 */
	protected List<Comment> parseComments() {
		final List<Comment> comments = new ArrayList<Comment>();
		skipSpaces();
		Comment comment = parseComment();
		while (comment != null) {
			comments.add(comment);
			skipSpaces();
			comment = parseComment();
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
	 * Parses and identifier.
	 * 
	 * @return the identifier if any recognized, <code>null</code> otherwise
	 */
	protected String parseIdentifier() {
		final String res;

		if (currentPosition <= text.length() && Character.isJavaIdentifierStart(text.charAt(currentPosition))) {
			final int identifierStart = currentPosition;
			currentPosition++;
			while (currentPosition <= text.length()
					&& Character.isJavaIdentifierPart(text.charAt(currentPosition))) {
				currentPosition++;
			}
			res = text.substring(identifierStart, currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Comment}.
	 * 
	 * @return the created {@link Comment} if any recognized, <code>null</code> otherwise
	 */
	protected Comment parseComment() {
		final Comment res;
		if (text.startsWith(COMMENT_START, currentPosition)) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createComment();
			int endOfComment = text.indexOf(COMMENT_END, currentPosition + COMMENT_START.length());
			if (endOfComment < 0) {
				endOfComment = text.length();
			}
			res.setStartPosition(currentPosition);
			res.setEndPosition(endOfComment);

			final CommentBody commentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory().createCommentBody();
			commentBody.setValue(text.substring(currentPosition, endOfComment));
			commentBody.setStartPosition(currentPosition + COMMENT_START.length());
			commentBody.setEndPosition(endOfComment);

			res.setBody(commentBody);
			currentPosition = endOfComment + COMMENT_END.length();
		} else {
			// TODO Documentation
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Module}.
	 * 
	 * @param comments
	 *            the {@link List} of {@link Comment}
	 * @return the created {@link Module} if any recognized, <code>null</code> otherwise
	 */
	protected Module parseModule(List<Comment> comments) {
		final Module res;

		if (text.startsWith(MODULE_HEADER_START, currentPosition)) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModule();
			res.getModuleElements().addAll(comments);
			res.setDocumentation(getLastDocumentation(comments));
			res.setStartHeaderPosition(currentPosition);
			currentPosition += MODULE_HEADER_START.length();
			skipSpaces();

			final String name = parseIdentifier();
			if (name == null) {
				// TODO missing identifier
			} else {
				res.setName(name);
			}
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			skipSpaces();
			Metamodel metamodel = parseMetamodel();
			while (metamodel != null) {
				res.getMetamodels().add(metamodel);
				skipSpaces();
				if (readString(COMMA)) {
					skipSpaces();
					metamodel = parseMetamodel();
					if (metamodel == null) {
						// TODO missing EPackage
					}
				} else {
					break;
				}
			}
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (readString(EXTENDS)) {
				skipSpaces();
				ModuleReference extended = parseModuleReference();
				if (extended == null) {
					// missing ModuleReference
				}
				res.getExtends().add(extended);
				skipSpaces();
				while (readString(COMMA)) {
					skipSpaces();
					extended = parseModuleReference();
					if (extended == null) {
						// missing ModuleReference
					}
					res.getExtends().add(extended);
					skipSpaces();
				}
			}
			if (!readString(MODULE_HEADER_END)) {
				// TODO missing MODULE_HEADER_END
			}
			res.setEndHeaderPosition(currentPosition);
			skipSpaces();
			ModuleReference imported = parseImport();
			while (imported != null) {
				res.getImports().add(imported);
				imported = parseImport();
			}
			final List<ModuleElement> moduleElements = parseModuleElements();
			res.getModuleElements().addAll(moduleElements);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses an import {@link ModuleReference}.
	 * 
	 * @return the create {@link ModuleReference} if any is recognized, <code>null</code> otherwise
	 */
	protected ModuleReference parseImport() {
		final ModuleReference res;

		if (text.startsWith(IMPORT_START, currentPosition)) {
			currentPosition += IMPORT_START.length();
			skipSpaces();
			res = parseModuleReference();
			skipSpaces();
			if (!readString(IMPORT_END)) {
				// TODO missing IMPORT_END
			}
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
			// TODO missing moduleQualifiedName
			res = null;
		} else {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModuleReference();
			res.setStartPosition(startPosition);
			res.setUrl(moduleQualifiedName);
			res.setEndPosition(currentPosition);
		}

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
			final List<Comment> comments = parseComments();
			res.addAll(comments);
			final Documentation documentation = getLastDocumentation(comments);
			final Template template = parseTemplate(documentation);
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
	 * Parses a {@link Query}.
	 * 
	 * @param documentation
	 *            the {@link Documentation} for the {@link Query} if any, <code>null</code> otherwise
	 * @return the created {@link Query} if any recognized, <code>null</code> otherwise
	 */
	protected Query parseQuery(Documentation documentation) {
		final Query res;

		if (text.startsWith(QUERY_START, currentPosition)) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createQuery();
			res.setStartPosition(currentPosition);
			currentPosition += QUERY_START.length();
			res.setDocumentation(documentation);
			skipSpaces();
			final VisibilityKind visibility = parseVisibility();
			res.setVisibility(visibility);
			skipSpaces();
			final String name = parseIdentifier();
			res.setName(name);
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			final List<Variable> parameters = parseVariables();
			res.getParameters().addAll(parameters);
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (!readString(SEMICOLON)) {
				// TODO missing SEMICOLON
			}
			skipSpaces();
			final EClassifier type = parseEClassifier();
			if (type == null) {
				// TODO missing type
			}
			res.setType(type);
			skipSpaces();
			if (!readString(EQUAL)) {
				// TODO missing EQUAL
			}
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(QUERY_END, QUERY_END);
			final Expression body = parseExpression(expressionEndLimit);
			res.setBody(body);
			skipSpaces();
			if (!readString(QUERY_END)) {
				// TODO missing QUERY_END
			}
			res.setEndPosition(currentPosition);
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
	protected List<Variable> parseVariables() {
		final List<Variable> res = new ArrayList<Variable>();

		Variable variable = parseVariable();
		while (variable != null) {
			res.add(variable);
			skipSpaces();
			if (readString(COMMA)) {
				skipSpaces();
				variable = parseVariable();
			} else {
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
		final String name = parseIdentifier();
		if (name == null) {
			// TODO missing identifier
		}
		skipSpaces();
		if (!readString(SEMICOLON)) {
			// TODO missing SEMICOLON
		}
		skipSpaces();
		final EClassifier type = parseEClassifier();
		if (type == null) {
			// TODO missing type
		}

		if (name != null && type != null) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createVariable();
			res.setStartPosition(startPosition);
			res.setName(name);
			res.setType(type);
			res.setEndPosition(currentPosition);
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
	 * @return the created {@link Template} if any recognized, <code>null</code> otherwise
	 */
	protected Template parseTemplate(Documentation documentation) {
		final Template res;

		if (text.startsWith(TEMPLATE_HEADER_START, currentPosition)) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTemplate();
			res.setStartPosition(currentPosition);
			currentPosition += TEMPLATE_HEADER_START.length();
			res.setDocumentation(documentation);
			skipSpaces();
			final VisibilityKind visibility = parseVisibility();
			res.setVisibility(visibility);
			skipSpaces();
			final String name = parseIdentifier();
			res.setName(name);
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			skipSpaces();
			final List<Variable> parameters = parseVariables();
			res.getParameters().addAll(parameters);
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (text.startsWith(TEMPLATE_POST, currentPosition)) {
				final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
						TEMPLATE_HEADER_END);
				final Expression postExpression = parseExpression(expressionEndLimit);
				res.setPost(postExpression);
				if (!readString(CLOSE_PARENTHESIS)) {
					// TODO missing CLOSE_PARENTHESIS
				}
				skipSpaces();
			}
			if (!readString(TEMPLATE_HEADER_END)) {
				// TODO missing TEMPLATE_HEADER_END
			}
			final Block body = parseBlock(TEMPLATE_END);
			res.setBody(body);
			if (!readString(TEMPLATE_END)) {
				// TODO missing TEMPLATE_END
			}
			res.setEndPosition(currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link Block} .
	 * 
	 * @param endBlocks
	 *            the end of block strings
	 * @return the created {@link Block}
	 */
	protected Block parseBlock(String... endBlocks) {
		final Block res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();

		res.setStartPosition(currentPosition);
		Statement statement = parseStatement();
		endOfBlock: while (statement != null) {
			res.getStatements().add(statement);
			for (String endOfBlock : endBlocks) {
				if (text.startsWith(endOfBlock, currentPosition)) {
					break endOfBlock;
				}
			}
			statement = parseStatement();
		}
		res.setEndPosition(currentPosition);

		return res;
	}

	/**
	 * Parses a {@link Statement}.
	 * 
	 * @return the created {@link Statement} if any is recognize, <code>null</code> otherwise
	 */
	protected Statement parseStatement() {
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
							final ExpressionStatement expressionStatement = parseExpressionStatement();
							if (expressionStatement != null) {
								res = expressionStatement;
							} else {
								final TextStatement text = parseTextStatement();
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
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createIfStatement();
			res.setStartPosition(currentPosition);
			currentPosition += startTag.length();
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS, IF_HEADER_END);
			final Expression condition = parseExpression(expressionEndLimit);
			res.setCondition(condition);
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (!readString(IF_HEADER_END)) {
				// TODO missing IF_HEADER_END
			}
			final Block thenblock = parseBlock(IF_END, IF_ELSEIF, IF_ELSE);
			res.setThen(thenblock);
			final IfStatement elseIf = parseIfStatement(IF_ELSEIF);
			if (elseIf != null) {
				final Block elseBlock = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();
				elseBlock.getStatements().add(elseIf);
				res.setElse(elseBlock);
			} else if (readString(IF_ELSE)) {
				final Block elseBlock = parseBlock(IF_END);
				res.setElse(elseBlock);
			}
			if (!readString(IF_END)) {
				// TODO missing IF_END
			}
			res.setEndPosition(currentPosition);
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
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createForStatement();
			res.setStartPosition(currentPosition);
			currentPosition += FOR_HEADER_START.length();
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			final int bindingEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS, FOR_HEADER_END);
			final Binding binding = parseBinding(PIPE, bindingEndLimit);
			res.setBinding(binding);
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (!readString(FOR_HEADER_END)) {
				// TODO missing FOR_HEADER_END
			}
			final Block body = parseBlock(FOR_END);
			res.setBody(body);
			if (!readString(FOR_END)) {
				// TODO missing FOR_END
			}
			res.setEndPosition(currentPosition);
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
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createFileStatement();
			res.setStartPosition(currentPosition);
			currentPosition += FILE_HEADER_START.length();
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(COMMA, FILE_HEADER_END);
			final Expression url = parseExpression(expressionEndLimit);
			res.setUrl(url);
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (!readString(FILE_HEADER_END)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			final Block body = parseBlock(FILE_END);
			res.setBody(body);
			if (!readString(FILE_END)) {
				// TODO missing FILE_END
			}
			res.setEndPosition(currentPosition);
		} else {
			res = null;
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
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createLetStatement();
			res.setStartPosition(currentPosition);
			currentPosition += LET_HEADER_START.length();
			int bindingEndLimit = getAqlExpressionEndLimit(COMMA, LET_HEADER_END);
			Binding binding = parseBinding(EQUAL, bindingEndLimit);
			res.getVariables().add(binding);
			skipSpaces();
			while (readString(COMMA)) {
				skipSpaces();
				bindingEndLimit = getAqlExpressionEndLimit(COMMA, LET_HEADER_END);
				binding = parseBinding(EQUAL, bindingEndLimit);
				res.getVariables().add(binding);
				skipSpaces();
			}
			skipSpaces();
			if (!readString(LET_HEADER_END)) {
				// TODO missing LET_HEADER_END
			}
			final Block body = parseBlock(LET_END);
			res.setBody(body);
			if (!readString(LET_END)) {
				// TODO missing LET_END
			}
			res.setEndPosition(currentPosition);
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
		final Binding res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBinding();

		res.setStartPosition(currentPosition);
		final String name = parseIdentifier();
		if (name == null) {
			// TODO missing name
		}
		res.setName(name);
		skipSpaces();
		if (readString(SEMICOLON)) {
			skipSpaces();
			final EClassifier type = parseEClassifier();
			res.setType(type);
		}
		skipSpaces();
		if (!readString(affectationSymbol)) {
			// TODO missing affectationSymbol
		}
		skipSpaces();
		final Expression expression = parseExpression(endLimit);
		res.setInitExpression(expression);
		res.setEndPosition(currentPosition);

		return res;
	}

	/**
	 * Parses a {@link EClassifier}.
	 * 
	 * @return the recognized {@link EClassifier} if any, <code>null</code> otherwise
	 */
	protected EClassifier parseEClassifier() {
		final EClassifier res;

		final String ePackageName = parseIdentifier();
		if (!readString(QUALIFIER_SEPARATOR)) {
			// TODO missing QUALIFIER_SEPARATOR
		}
		final String eClassifierName = parseIdentifier();

		if (ePackageName != null && eClassifierName != null) {
			final EPackage ePkg = getEPackage(ePackageName);
			if (ePkg != null) {
				res = ePkg.getEClassifier(eClassifierName);
			} else {
				res = null;
			}
		} else {
			res = null;
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
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createProtectedArea();
			res.setStartPosition(currentPosition);
			currentPosition += PROTECTED_AREA_HEADER_START.length();
			skipSpaces();
			if (!readString(OPEN_PARENTHESIS)) {
				// TODO missing OPEN_PARENTHESIS
			}
			skipSpaces();
			final int expressionEndLimit = getAqlExpressionEndLimit(CLOSE_PARENTHESIS,
					PROTECTED_AREA_HEADER_END);
			final Expression id = parseExpression(expressionEndLimit);
			res.setId(id);
			skipSpaces();
			if (!readString(CLOSE_PARENTHESIS)) {
				// TODO missing CLOSE_PARENTHESIS
			}
			skipSpaces();
			if (!readString(PROTECTED_AREA_HEADER_END)) {
				// TODO missing PROTECTED_AREA_HEADER_END
			}
			final Block body = parseBlock(PROTECTED_AREA_END);
			res.setBody(body);
			if (!readString(PROTECTED_AREA_END)) {
				// TODO missing PROTECTED_AREA_END
			}
			res.setEndPosition(currentPosition);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses an {@link ExpressionStatement}.
	 * 
	 * @return the created {@link ExpressionStatement} if any is recognized, <code>null</code> otherwise
	 */
	protected ExpressionStatement parseExpressionStatement() {
		final ExpressionStatement res;

		if (text.startsWith(EXPRESSION_STATEMENT_START, currentPosition)) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpressionStatement();
			res.setStartPosition(currentPosition);
			currentPosition += EXPRESSION_STATEMENT_START.length();
			final int expressionEndLimit = getAqlExpressionEndLimit(EXPRESSION_STATEMENT_END,
					EXPRESSION_STATEMENT_END);
			final Expression expression = parseExpression(expressionEndLimit);
			res.setExpression(expression);
			if (!readString(EXPRESSION_STATEMENT_END)) {
				// TODO missing EXPRESSION_STATEMENT_END
			}
			res.setEndPosition(currentPosition);
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
		res.setStartPosition(currentPosition);
		res.setAst(astResult);
		final int endPosition = currentPosition + astResult.getEndPosition(astResult.getAst());
		res.setEndPosition(endPosition);
		currentPosition = endPosition;

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
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createMetamodel();
			res.setStartPosition(currentPosition);
			int nextQuote = text.indexOf(QUOTE, currentPosition);
			if (nextQuote < 0) {
				nextQuote = text.length();
			}
			final String nsURI = text.substring(currentPosition, nextQuote);
			currentPosition = nextQuote;
			final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
			res.setReferencedPackage(ePackage);
			if (!readString(QUOTE)) {
				// TODO missing QUOTE
			}
			res.setEndPosition(currentPosition);
		} else {
			// TODO missing QUOTE
			res = null;
		}

		return res;
	}

	/**
	 * Parses {@link TextStatement}.
	 * 
	 * @return the created {@link TextStatement} if ant, <code>null</code> otherwise
	 */
	protected TextStatement parseTextStatement() {
		final TextStatement res;

		int endOfText = text.indexOf(TEXT_END, currentPosition);
		if (endOfText < 0) {
			endOfText = text.length();
		}
		if (currentPosition != endOfText) {
			res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTextStatement();
			res.setStartPosition(currentPosition);
			res.setEndPosition(endOfText);
			res.setValue(text.substring(currentPosition, endOfText));
			currentPosition = endOfText;
		} else {
			res = null;
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
		final IQueryBuilderEngine.AstResult result;

		if (expression != null && expression.length() > 0) {
			AstBuilderListener astBuilder = new AstBuilderListener(queryEnvironment);
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
			ErrorExpression errorExpression = (ErrorExpression)EcoreUtil.create(AstPackage.eINSTANCE
					.getErrorExpression());
			List<org.eclipse.acceleo.query.ast.Error> errors = new ArrayList<org.eclipse.acceleo.query.ast.Error>(
					1);
			errors.add(errorExpression);
			final Map<Object, Integer> positions = new HashMap<Object, Integer>();
			if (expression != null) {
				positions.put(errorExpression, Integer.valueOf(0));
			}
			final BasicDiagnostic diagnostic = new BasicDiagnostic();
			diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, AstBuilderListener.PLUGIN_ID, 0,
					"null or empty string.", new Object[] {errorExpression }));
			result = new AstResult(errorExpression, positions, positions, errors, diagnostic);
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
		while (currentPosition < text.length()) {
			switch (text.charAt(res)) {
				case '\'':
					// skip string literal
					boolean isEscaped = false;
					res++;
					endStrinLiteral: while (currentPosition < text.length()) {
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
			if (text.startsWith(endTag, res) || parenthesisDepth == 0 && text.startsWith(endDelimiter, res)) {
				break;
			}
		}

		return res;
	}
}
