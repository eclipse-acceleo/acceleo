/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.cst;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLReflection;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.internal.parser.IAcceleoParserProblemsConstants;
import org.eclipse.acceleo.internal.parser.cst.utils.ParserUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;
import org.eclipse.acceleo.internal.parser.documentation.utils.DocumentationUtils;
import org.eclipse.acceleo.internal.parser.documentation.utils.DocumentationUtils.CommentType;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.Documentation;
import org.eclipse.acceleo.parser.cst.InitSection;
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * The main class of the CST creator. Creates a CST model from a Acceleo file. You just have to launch the
 * 'parse' method...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CSTParser {
	/**
	 * A Set of all Acceleo keywords.
	 */
	protected static final List<String> ACCELEO_KEYWORDS = Arrays.asList(new String[] {
			IAcceleoConstants.AFTER, IAcceleoConstants.BEFORE, IAcceleoConstants.COMMENT,
			IAcceleoConstants.ELSE, IAcceleoConstants.ELSE_IF, IAcceleoConstants.ELSE_LET,
			IAcceleoConstants.ENCODING, IAcceleoConstants.EXTENDS, IAcceleoConstants.FILE,
			IAcceleoConstants.FOR, IAcceleoConstants.IF, IAcceleoConstants.IMPORT, IAcceleoConstants.LET,
			IAcceleoConstants.MACRO, IAcceleoConstants.MODULE, IAcceleoConstants.OVERRIDES,
			IAcceleoConstants.POST, IAcceleoConstants.PROTECTED_AREA, IAcceleoConstants.QUERY,
			IAcceleoConstants.SELF, IAcceleoConstants.SUPER, IAcceleoConstants.TEMPLATE,
			IAcceleoConstants.TRACE, IAcceleoConstants.VISIBILITY_KIND_PRIVATE,
			IAcceleoConstants.VISIBILITY_KIND_PROTECTED, IAcceleoConstants.VISIBILITY_KIND_PUBLIC, });

	/** Key of the error message that should be logged when the parser misses a character. */
	private static final String MISSING_CHARACTER_KEY = "Parser.MissingCharacter"; //$NON-NLS-1$

	/** Key of the error message that should be logged when a statement header isn't closed. */
	private static final String INVALID_STMT_HEADER = "CSTParser.InvalidStatementHeader"; //$NON-NLS-1$

	/** Key of the error message that should be logged when a statement isn't closed. */
	private static final String INVALID_STMT = "CSTParser.InvalidStatement"; //$NON-NLS-1$

	/**
	 * The source buffer to parse.
	 */
	protected final AcceleoSourceBuffer source;

	/**
	 * To parse a 'literal' block in the text.
	 */
	protected final SequenceBlock pLiteral;

	/**
	 * To parse a 'comment' block in the text.
	 */
	protected final SequenceBlock pComment;

	/**
	 * To parse a 'parenthesis' block in the text.
	 */
	protected final SequenceBlock pParenthesis;

	/**
	 * To parse a 'brackets' block in the text.
	 */
	protected final SequenceBlock pBrackets;

	/**
	 * To parse a 'comma' sequence in the text.
	 */
	protected final Sequence pComma;

	/**
	 * To parse a 'semicolon' sequence in the text.
	 */
	protected final Sequence pSemicolon;

	/**
	 * To parse a 'pipe' sequence in the text.
	 */
	protected final Sequence pPipe;

	/**
	 * To parse a 'guard' sequence in the text.
	 */
	protected final Sequence pGuard;

	/**
	 * To parse a postcondition sequence in the text.
	 */
	protected final Sequence pPost;

	/**
	 * To parse a 'module' block in the text.
	 */
	protected final SequenceBlock pModule;

	/**
	 * To parse an 'import' block in the text.
	 */
	protected final SequenceBlock pImport;

	/**
	 * To parse a 'template' block in the text.
	 */
	protected final SequenceBlock pTemplate;

	/**
	 * To parse a 'macro' block in the text.
	 */
	protected final SequenceBlock pMacro;

	/**
	 * To parse a 'query' block in the text.
	 */
	protected final SequenceBlock pQuery;

	/**
	 * To parse a 'documentation' block in the text.
	 */
	protected final SequenceBlock pDocumentation;

	/**
	 * To parse a block statement in the text : 'for', 'if'...
	 */
	protected final CSTParserBlock pBlock;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            is the source buffer to parse
	 */
	public CSTParser(AcceleoSourceBuffer source) {
		this.source = source;
		Sequence literalEscape = new Sequence(IAcceleoConstants.LITERAL_ESCAPE);
		pLiteral = new SequenceBlock(new Sequence(IAcceleoConstants.LITERAL_BEGIN), new Sequence(
				IAcceleoConstants.LITERAL_END), literalEscape, false, null);
		pComment = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.COMMENT, null, null);
		pParenthesis = new SequenceBlock(new Sequence(IAcceleoConstants.PARENTHESIS_BEGIN), new Sequence(
				IAcceleoConstants.PARENTHESIS_END), null, true, new SequenceBlock[] {pLiteral });
		pBrackets = new SequenceBlock(new Sequence(IAcceleoConstants.BRACKETS_BEGIN), new Sequence(
				IAcceleoConstants.BRACKETS_END), null, true, new SequenceBlock[] {pLiteral });
		pComma = new Sequence(IAcceleoConstants.COMMA_SEPARATOR);
		pSemicolon = new Sequence(IAcceleoConstants.SEMICOLON_SEPARATOR);
		pPipe = new Sequence(IAcceleoConstants.PIPE_SEPARATOR);
		pGuard = new Sequence(IAcceleoConstants.GUARD);
		pPost = new Sequence(IAcceleoConstants.POST);
		pModule = ParserUtils.createAcceleoSequenceBlock(true, IAcceleoConstants.MODULE,
				new SequenceBlock[] {pLiteral }, null);
		pImport = ParserUtils.createAcceleoSequenceBlock(true, IAcceleoConstants.IMPORT,
				new SequenceBlock[] {pLiteral }, null);
		pTemplate = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.TEMPLATE,
				new SequenceBlock[] {pLiteral, pParenthesis }, new SequenceBlock[] {pComment });
		pMacro = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.MACRO, new SequenceBlock[] {
				pLiteral, pParenthesis, }, new SequenceBlock[] {pComment });
		pQuery = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.QUERY, new SequenceBlock[] {
				pLiteral, pParenthesis, }, new SequenceBlock[] {pComment });
		pDocumentation = ParserUtils.createAcceleoSequenceBlock(true, IAcceleoConstants.DOCUMENTATION_BEGIN,
				null, null);
		pBlock = new CSTParserBlock(this);
	}

	/**
	 * Gets the source buffer to parse.
	 * 
	 * @return the source buffer
	 */
	public AcceleoSourceBuffer getSource() {
		return source;
	}

	/**
	 * Gets the block parser, for the statement : 'for', 'if'...
	 * 
	 * @return the block parser
	 */
	public CSTParserBlock getPBlock() {
		return pBlock;
	}

	/**
	 * To store the position in the CST node.
	 * 
	 * @param eCSTNode
	 *            is the node to update
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 */
	protected void setPositions(CSTNode eCSTNode, int posBegin, int posEnd) {
		if (eCSTNode instanceof TextExpression || eCSTNode instanceof Block) {
			eCSTNode.setStartPosition(posBegin);
			eCSTNode.setEndPosition(posEnd);
		} else {
			int[] pos = source.trim(posBegin, posEnd);
			if (pos[0] == pos[1] && posBegin != posEnd) {
				eCSTNode.setStartPosition(posBegin);
				eCSTNode.setEndPosition(posEnd);
			} else {
				eCSTNode.setStartPosition(pos[0]);
				eCSTNode.setEndPosition(pos[1]);
			}
		}
	}

	/**
	 * Creates a CST model for the file.
	 * 
	 * @return the root object of the CST model
	 */
	public Module parse() {
		if (source.getBuffer() == null || source.getBuffer().length() == 0) {
			logProblem(AcceleoParserMessages.getString("CSTParser.EmptyBuffer"), 0, -1); //$NON-NLS-1$
		} else {
			Region bH = pModule.searchBeginHeader(source.getBuffer(), 0, source.getBuffer().length());
			if (bH.b() == -1) {
				logProblem(AcceleoParserMessages.getString("CSTParser.MissingModule"), 0, -1); //$NON-NLS-1$
			} else {
				Region eH = pModule.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, source.getBuffer()
						.length());
				if (eH.b() == -1) {
					logProblem(AcceleoParserMessages.getString("CSTParser.MissingModuleEnd"), bH.b(), bH.e()); //$NON-NLS-1$
				} else {
					Module eModule = CstFactory.eINSTANCE.createModule();
					setPositions(eModule, bH.b(), source.getBuffer().length());
					List<Comment> commentsBeforeModule = parseBeforeModule(eModule);
					computeModuleDocumentation(commentsBeforeModule, eModule);
					parseModuleHeader(bH.e(), eH.b(), eModule);
					parseModuleBody(eH.e(), source.getBuffer().length(), eModule);

					checkModuleImports(eModule);
					checkModuleExtends(eModule);
					return eModule;
				}
			}
		}
		return null;
	}

	/**
	 * Checks the imported module for duplicates.
	 * 
	 * @param eModule
	 *            The module
	 */
	private void checkModuleImports(Module eModule) {
		List<String> importedValues = new ArrayList<String>();

		EList<ModuleImportsValue> imports = eModule.getImports();
		for (ModuleImportsValue moduleImportsValue : imports) {
			if (importedValues.contains(moduleImportsValue.getName())) {
				logWarning(AcceleoParserMessages.getString(
						"CST2ASTConverterWithResolver.ModuleAlreadyImports", moduleImportsValue.getName()), //$NON-NLS-1$
						moduleImportsValue.getStartPosition(), moduleImportsValue.getEndPosition());
			} else {
				importedValues.add(moduleImportsValue.getName());
			}
		}
	}

	/**
	 * Checks extended modules value for duplicates.
	 * 
	 * @param eModule
	 *            The module
	 */
	private void checkModuleExtends(Module eModule) {
		List<String> extendedValues = new ArrayList<String>();

		EList<ModuleExtendsValue> extendedModules = eModule.getExtends();
		for (ModuleExtendsValue moduleExtendsValue : extendedModules) {
			if (extendedValues.contains(moduleExtendsValue.getName())) {
				logWarning(AcceleoParserMessages.getString(
						"CST2ASTConverterWithResolver.ModuleAlreadyExtends", moduleExtendsValue.getName()), //$NON-NLS-1$
						moduleExtendsValue.getStartPosition(), moduleExtendsValue.getEndPosition());
			} else {
				extendedValues.add(moduleExtendsValue.getName());
			}
		}
	}

	/**
	 * Find the documentation of the module in the list of all the documentation placed before the module's
	 * header.
	 * 
	 * @param commentsBeforeModule
	 *            The list of the comments before the header.
	 * @param eModule
	 *            The module which maay be modified to include its documentation
	 */
	private void computeModuleDocumentation(List<Comment> commentsBeforeModule, Module eModule) {
		if (commentsBeforeModule.size() > 0) {
			Comment comment = commentsBeforeModule.get(commentsBeforeModule.size() - 1);
			if (comment instanceof Documentation) {
				Documentation documentation = (Documentation)comment;

				Sequence bSequence = new Sequence(IAcceleoConstants.DEFAULT_BEGIN,
						IAcceleoConstants.DOCUMENTATION_BEGIN, IAcceleoConstants.ENCODING,
						IAcceleoConstants.VARIABLE_INIT_SEPARATOR);
				StringBuffer documentationBody = new StringBuffer(documentation.getBody());
				Region b = bSequence.search(documentationBody);

				// If we found an ending position for the encoding area of '-1', it means that this
				// documentation block does not have any encoding, therefore we can use it as the module
				// documentation
				if (b.e() == -1) {
					eModule.setDocumentation(documentation);
				}
			}
		}
	}

	/**
	 * Parse the text before the module to determine the module documentation.
	 * 
	 * @param posBegin
	 *            The beginning of the documentation.
	 * @param eModule
	 *            The module
	 * @return The documentation or <code>null</code> if none.
	 */
	public Documentation parseModuleDocumentation(int posBegin, Module eModule) {
		if (source.getBuffer() == null || source.getBuffer().length() == 0) {
			logProblem(AcceleoParserMessages.getString("CSTParser.EmptyBuffer"), 0, -1); //$NON-NLS-1$
		} else {
			Region bH = pModule.searchBeginHeader(source.getBuffer(), 0, source.getBuffer().length());
			if (bH.b() == -1) {
				logProblem(AcceleoParserMessages.getString("CSTParser.MissingModule"), 0, -1); //$NON-NLS-1$
			} else {
				Region eH = pModule.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, source.getBuffer()
						.length());
				if (eH.b() == -1) {
					logProblem(AcceleoParserMessages.getString("CSTParser.MissingModuleEnd"), bH.b(), bH.e()); //$NON-NLS-1$
				} else {
					setPositions(eModule, bH.b(), source.getBuffer().length());
					List<Comment> commentsBeforeModule = parseBeforeModule(eModule);
					computeModuleDocumentation(commentsBeforeModule, eModule);
					return eModule.getDocumentation();
				}
			}
		}
		return null;
	}

	/**
	 * Reads the text before the module declaration to find the starting comment of documentation and to log
	 * an error if there is something else before the module.
	 * 
	 * @param eModule
	 *            The module.
	 * @return The list of all comments before the module header.
	 */
	private List<Comment> parseBeforeModule(Module eModule) {
		int currentPosition = 0;
		List<Comment> commentBeforeModuleHeader = new ArrayList<Comment>();
		while (currentPosition < eModule.getStartPosition() && currentPosition != -1) {
			Region docBH = pDocumentation.searchBeginHeader(source.getBuffer(), currentPosition, eModule
					.getStartPosition());
			Region comBH = pComment.searchBeginHeader(source.getBuffer(), currentPosition, eModule
					.getStartPosition());

			if (docBH.b() == -1 && comBH.b() == -1) {
				parseWhitespaceArea(currentPosition, eModule.getStartPosition());
				currentPosition = -1;
			} else if (docBH.b() != -1 && comBH.b() != -1) {
				if (docBH.b() < comBH.b()) {
					// We found a documentation block first
					Documentation eDocumentation = parseDocumentationBeforeModule(docBH, eModule
							.getStartPosition());
					if (eDocumentation != null) {
						commentBeforeModuleHeader.add(eDocumentation);
						parseWhitespaceArea(currentPosition, eDocumentation.getStartPosition());
						currentPosition = eDocumentation.getEndPosition();
					} else {
						// never
						currentPosition = source.getBuffer().indexOf(IAcceleoConstants.DEFAULT_BEGIN,
								docBH.e() + 1);
					}
				} else if (docBH.b() > comBH.b()) {
					// We found a comment block first
					Comment eComment = parseCommentBeforeModule(comBH, eModule.getStartPosition());
					if (eComment != null) {
						commentBeforeModuleHeader.add(eComment);
						parseWhitespaceArea(currentPosition, eComment.getStartPosition());
						currentPosition = eComment.getEndPosition();
					} else {
						// never
						currentPosition = source.getBuffer().indexOf(IAcceleoConstants.DEFAULT_BEGIN,
								comBH.e() + 1);
					}
				}
			} else if (docBH.b() != -1 && comBH.b() == -1) {
				// We found only a documentation block
				Documentation eDocumentation = parseDocumentationBeforeModule(docBH, eModule
						.getStartPosition());
				if (eDocumentation != null) {
					commentBeforeModuleHeader.add(eDocumentation);
					parseWhitespaceArea(currentPosition, eDocumentation.getStartPosition());
					currentPosition = eDocumentation.getEndPosition();
				} else {
					currentPosition = source.getBuffer().indexOf(IAcceleoConstants.DEFAULT_BEGIN,
							docBH.e() + 1);
				}
			} else if (docBH.b() == -1 && comBH.b() != -1) {
				// We found only a comment block
				Comment eComment = parseCommentBeforeModule(comBH, eModule.getStartPosition());
				if (eComment != null) {
					commentBeforeModuleHeader.add(eComment);
					parseWhitespaceArea(currentPosition, eComment.getStartPosition());
					currentPosition = eComment.getEndPosition();
				} else {
					currentPosition = source.getBuffer().indexOf(IAcceleoConstants.DEFAULT_BEGIN,
							comBH.e() + 1);
				}
			}
		}
		return commentBeforeModuleHeader;
	}

	/**
	 * Parses the text between the starting comment/documentation blocks and the module to see if there is
	 * anything except whitespace, if yes we log an error.
	 * 
	 * @param startPosition
	 *            The ending position of the supposed whitespace area
	 * @param endPosition
	 *            The beginning position of the supposed whitespace area
	 */
	private void parseWhitespaceArea(int startPosition, int endPosition) {
		String substring = source.getBuffer().substring(startPosition, endPosition);

		// This regex will detect everything that is not a whitespace
		Pattern p = Pattern.compile("\\S"); //$NON-NLS-1$
		Matcher matcher = p.matcher(substring);
		boolean found = matcher.find();

		if (found) {
			this.logWarning(AcceleoParserMessages
					.getString("AcceleoParser.Warning.TextBetweenCommentAndModule"), startPosition, //$NON-NLS-1$
					endPosition);

		}
	}

	/**
	 * Parses the comment block before the module. This method will return a Documentation node in order to
	 * manipulate the starting comment has the documentation of the module without having to force users to
	 * use the new documentation system.
	 * 
	 * @param beginHeader
	 *            The begin header
	 * @param moduleStartPosition
	 *            The start position of the module
	 * @return The documentation parsed
	 */
	private Comment parseCommentBeforeModule(Region beginHeader, int moduleStartPosition) {
		Comment comment;
		Region eH = pComment.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader,
				moduleStartPosition);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.COMMENT),
					beginHeader.b(), beginHeader.e());
			comment = null;
		} else {
			if (eH.getSequence() == pComment.getEndHeaderBody()) {
				Comment eComment = CstFactory.eINSTANCE.createComment();
				setPositions(eComment, beginHeader.b(), eH.e());

				eComment.setBody(source.getBuffer().substring(beginHeader.e(), eH.b()));
				DocumentationUtils.parseToDoFixMe(this.source, eComment.getStartPosition(), eComment
						.getEndPosition(), CommentType.COMMENT_IN_HEADER);

				comment = eComment;
			} else {
				Region eB = pComment.searchEndBodyAtEndHeader(source.getBuffer(), eH, moduleStartPosition);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.COMMENT),
							beginHeader.b(), beginHeader.e());
					comment = null;
				} else {
					Comment eComment = CstFactory.eINSTANCE.createComment();
					setPositions(eComment, beginHeader.b(), eB.e());

					eComment.setBody(source.getBuffer().substring(eH.e(), eB.b()));
					DocumentationUtils.parseToDoFixMe(this.source, eComment.getStartPosition(), eComment
							.getEndPosition(), CommentType.COMMENT_WITH_END_HEADER);

					comment = eComment;
				}
			}
		}
		return comment;
	}

	/**
	 * Parses the documentation block before the module.
	 * 
	 * @param beginHeader
	 *            The begin header
	 * @param moduleStartPosition
	 *            The start position of the module
	 * @return The documentation parsed
	 */
	private Documentation parseDocumentationBeforeModule(Region beginHeader, int moduleStartPosition) {
		Documentation eDocumentation;
		Region eH = pDocumentation.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader,
				moduleStartPosition);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER,
					IAcceleoConstants.DOCUMENTATION_BEGIN), beginHeader.b(), beginHeader.e());
			eDocumentation = null;
		} else {
			if (eH.getSequence() == pDocumentation.getEndHeaderBody()) {
				eDocumentation = CstFactory.eINSTANCE.createDocumentation();
				setPositions(eDocumentation, beginHeader.b(), eH.e());

				String body = parseDocumentationBody(source.getBuffer().substring(beginHeader.e(), eH.b()));
				eDocumentation.setBody(body);

				DocumentationUtils.parseToDoFixMe(this.source, eDocumentation.getStartPosition(),
						eDocumentation.getEndPosition(), CommentType.DOCUMENTATION);
			} else {
				logProblem(AcceleoParserMessages.getString(INVALID_STMT,
						IAcceleoConstants.DOCUMENTATION_BEGIN), beginHeader.b(), beginHeader.e());
				eDocumentation = null;
			}

		}
		return eDocumentation;
	}

	/**
	 * Parses the body of the documentation and suppress all the '*'.
	 * 
	 * @param documentationBody
	 *            the body of the documentation
	 * @return the documentation text
	 */
	private String parseDocumentationBody(String documentationBody) {
		final String lineseparator = "line.separator"; //$NON-NLS-1$

		StringBuffer buffer = new StringBuffer();

		String str = ""; //$NON-NLS-1$
		BufferedReader reader = new BufferedReader(new StringReader(documentationBody));
		try {
			while ((str = reader.readLine()) != null) {
				if (str.trim().startsWith(IAcceleoConstants.DOCUMENTATION_NEW_LINE)) {
					str = str.substring(str.indexOf(IAcceleoConstants.DOCUMENTATION_NEW_LINE) + 1)
							+ System.getProperty(lineseparator);
					buffer.append(str.trim() + System.getProperty(lineseparator));
				} else if (str.endsWith(IAcceleoConstants.DOCUMENTATION_NEW_LINE)) {
					str = str.substring(0, str.lastIndexOf(IAcceleoConstants.DOCUMENTATION_NEW_LINE))
							+ System.getProperty(lineseparator);
					buffer.append(str.trim() + System.getProperty(lineseparator));
				} else {
					buffer.append(str + System.getProperty(lineseparator));
				}
			}
		} catch (IOException e) {
			// do nothing
		}

		return buffer.toString();
	}

	/**
	 * Reads the information of the 'module' header in the text, and modifies the properties of the current
	 * CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseModuleHeader(int posBegin, int posEnd, Module eModule) {
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
		if (bH.b() == -1) {
			logProblem(AcceleoParserMessages.getString("CSTParser.MissingMetamodel"), posBegin, posEnd); //$NON-NLS-1$
			String name = source.getBuffer().substring(posBegin, posEnd).trim();
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(AcceleoParserMessages.getString("CSTParser.InvalidModuleName"), posBegin, posEnd); //$NON-NLS-1$
			}
			eModule.setName(name);
			if (source.getFile() != null && !checkModuleDefinition(name, source.getFile())) {
				logProblem(
						AcceleoParserMessages.getString("CSTParser.InvalidModuleDefinition", name), posBegin, //$NON-NLS-1$
						posEnd);
			}
		} else {
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				parseModuleHeaderTypedModels(bH.e(), eH.b(), eModule);
				int eExtend = parseModuleHeaderExtends(eH.e(), posEnd, eModule);
				if (source.getBuffer().substring(eExtend, posEnd).trim().length() > 0) {
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, eExtend, posEnd);
				}
			}
			String name = source.getBuffer().substring(posBegin, bH.b()).trim();
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(
						AcceleoParserMessages.getString("CSTParser.InvalidModuleName", name), posBegin, bH.b()); //$NON-NLS-1$
			}
			eModule.setName(name);
			if (source.getFile() != null && !checkModuleDefinition(name, source.getFile())) {
				logProblem(
						AcceleoParserMessages.getString("CSTParser.InvalidModuleDefinition", name), posBegin, bH.b()); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Validates the name of the module. It must be defined in its own file.
	 * 
	 * @param name
	 *            is the module name
	 * @param file
	 *            is the file that contains the module name
	 * @return true if the module name is valid
	 */
	private boolean checkModuleDefinition(String name, File file) {
		StringTokenizer st = new StringTokenizer(name + "." + IAcceleoConstants.MTL_FILE_EXTENSION, "::"); //$NON-NLS-1$ //$NON-NLS-2$
		String[] segments = new String[st.countTokens()];
		for (int i = 0; i < segments.length; i++) {
			segments[i] = st.nextToken();
		}
		boolean result = true;
		File current = file;
		for (int i = segments.length - 1; result && i >= 0; i--) {
			if (current == null || current.getName() == null || !current.getName().equals(segments[i])) {
				result = false;
			} else {
				current = current.getParentFile();
			}
		}
		return result;
	}

	/**
	 * Creates new typed models in the CST model by reading the following text between the given delimiters. A
	 * comma separator is considered between each typed model.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the module of the CST model
	 */
	private void parseModuleHeaderTypedModels(int posBegin, int posEnd, Module eModule) {
		int currentPos = posBegin;
		String substring = source.getBuffer().substring(posBegin, posEnd);
		if ("".equals(substring.trim())) { //$NON-NLS-1$
			logProblem(AcceleoParserMessages.getString("CSTParser.MissingMetamodel"), eModule //$NON-NLS-1$
					.getStartPosition(), eModule.getEndPosition());
		}

		while (currentPos != posEnd) {
			Region comma = pComma.search(source.getBuffer(), currentPos, posEnd, null,
					new SequenceBlock[] {pLiteral });
			int e;
			if (comma.b() == -1) {
				e = posEnd;
			} else {
				e = comma.b();
			}
			TypedModel typedModel = checkEPackages(currentPos, e);
			eModule.getInput().add(typedModel);
			if (comma.b() == -1) {
				currentPos = posEnd;
			} else {
				currentPos = comma.e();
			}
		}
	}

	/**
	 * Checks EPackage existence.
	 * 
	 * @param currentPos
	 *            the current position
	 * @param endPos
	 *            the end position
	 * @return the created TypedModel
	 */
	private TypedModel checkEPackages(int currentPos, int endPos) {
		TypedModel res = CstFactory.eINSTANCE.createTypedModel();
		setPositions(res, currentPos, endPos);

		String ePackageKey = source.getBuffer().substring(currentPos, endPos).trim();
		EPackage ePackage = ModelUtils.getEPackage(ePackageKey);
		List<EPackage> ePackages = new ArrayList<EPackage>();
		if (ePackage == null && ePackageKey.startsWith(IAcceleoConstants.LITERAL_BEGIN)
				&& ePackageKey.endsWith(IAcceleoConstants.LITERAL_END)) {
			ePackageKey = ePackageKey.substring(IAcceleoConstants.LITERAL_BEGIN.length(), ePackageKey
					.length()
					- IAcceleoConstants.LITERAL_END.length());
			ePackage = ModelUtils.getEPackage(ePackageKey);
			if (ePackage == null) {
				List<String> ePackageKeys = new ArrayList<String>();
				try {
					ePackageKeys.addAll(AcceleoPackageRegistry.INSTANCE.registerEcorePackages(ePackageKey,
							AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET));

				} catch (WrappedException ex) {
					// swallow exception
				}
				for (String key : ePackageKeys) {
					final EPackage ePkg = ModelUtils.getEPackage(key);
					if (ePkg != null) {
						ePackages.add(ePkg);
					}
				}
				if (ePackages.size() == 0) {
					logProblem(
							AcceleoParserMessages.getString("CSTParser.MetamodelNotFound"), currentPos, endPos); //$NON-NLS-1$
				}
			} else {
				ePackages.add(ePackage);
			}
		} else if (ePackage != null) {
			ePackages.add(ePackage);
		}
		for (EPackage ePkg : ePackages) {
			res.getTakesTypesFrom().add(ePkg);
			res.getTakesTypesFrom().addAll(getAllSubpackages(ePkg));
		}

		return res;
	}

	/**
	 * Reads eventually the extends clause in the module header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseModuleHeaderExtends(int posBegin, int posEnd, Module eModule) {
		int bExtend = ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.EXTENDS, true);
		boolean errorFound = false;
		if (bExtend != posBegin) {
			while (bExtend != posEnd && !errorFound) {
				Region comma = pComma.search(source.getBuffer(), bExtend, posEnd, null,
						new SequenceBlock[] {pLiteral });
				if (comma.b() == -1) {
					ModuleExtendsValue eModuleExtendsValue = CstFactory.eINSTANCE.createModuleExtendsValue();
					String trimmedText = source.getBuffer().substring(bExtend, posEnd).trim();
					int indexOfSpace = trimmedText.indexOf(" "); //$NON-NLS-1$
					if (indexOfSpace == -1) {
						eModuleExtendsValue.setName(trimmedText);
						eModule.getExtends().add(eModuleExtendsValue);
						setPositions(eModuleExtendsValue, bExtend, posEnd);
						bExtend = posEnd;
					} else {
						int indexOfSpaceInTrimmedText = source.getBuffer().indexOf(trimmedText, bExtend)
								+ indexOfSpace;
						eModuleExtendsValue.setName(source.getBuffer().substring(bExtend,
								indexOfSpaceInTrimmedText).trim());
						eModule.getExtends().add(eModuleExtendsValue);
						setPositions(eModuleExtendsValue, bExtend, indexOfSpaceInTrimmedText);
						bExtend = indexOfSpaceInTrimmedText;
						errorFound = true;
					}
					if (eModuleExtendsValue.getName() != null
							&& !eModuleExtendsValue.getName().contains(IAcceleoConstants.NAMESPACE_SEPARATOR)) {
						this.logWarning(AcceleoParserMessages.getString("CSTParser.UnqualifiedExtend"), //$NON-NLS-1$
								eModuleExtendsValue.getStartPosition(), eModuleExtendsValue.getEndPosition());
					}
				} else {
					ModuleExtendsValue eModuleExtendsValue = CstFactory.eINSTANCE.createModuleExtendsValue();
					setPositions(eModuleExtendsValue, bExtend, comma.b());
					eModuleExtendsValue.setName(source.getBuffer().substring(bExtend, comma.b()).trim());
					eModule.getExtends().add(eModuleExtendsValue);
					bExtend = comma.e();
					if (eModuleExtendsValue.getName() != null
							&& !eModuleExtendsValue.getName().contains(IAcceleoConstants.NAMESPACE_SEPARATOR)) {
						this.logWarning(AcceleoParserMessages.getString("CSTParser.UnqualifiedExtend"), //$NON-NLS-1$
								eModuleExtendsValue.getStartPosition(), eModuleExtendsValue.getEndPosition());
					}
				}
			}
		}
		return bExtend;
	}

	/**
	 * Gets recursively the sub packages of a package.
	 * 
	 * @param ePackage
	 *            is the root package to visit
	 * @return all the sub packages
	 */
	private Collection<EPackage> getAllSubpackages(EPackage ePackage) {
		Collection<EPackage> result = new ArrayList<EPackage>();
		for (Iterator<EPackage> iterator = ePackage.getESubpackages().iterator(); iterator.hasNext();) {
			EPackage eSubpackage = iterator.next();
			result.add(eSubpackage);
			result.addAll(getAllSubpackages(eSubpackage));
		}
		return result;
	}

	/**
	 * Reads the information of the 'module' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseModuleBody(int posBegin, int posEnd, Module eModule) {
		int currentPosBegin = posBegin;
		SequenceBlock[] pModuleElements = new SequenceBlock[] {pComment, pImport, pTemplate, pMacro, pQuery,
				pDocumentation, };
		Region[] positions = Region.createPositions(pModuleElements.length);
		while (currentPosBegin > -1 && currentPosBegin < posEnd) {
			int i = ParserUtils.getNextSequence(source.getBuffer(), currentPosBegin, posEnd, pModuleElements,
					positions);
			if (i == -1) {
				if (source.getBuffer().substring(currentPosBegin, posEnd).trim().length() > 0) {
					logProblem(
							AcceleoParserMessages.getString("CSTParser.InvalidModel"), currentPosBegin, posEnd); //$NON-NLS-1$
				}
				currentPosBegin = -1;
			} else {
				SequenceBlock pModuleElement = pModuleElements[i];
				Region bH = positions[i];
				if (source.getBuffer().substring(currentPosBegin, bH.b()).trim().length() > 0) {
					logProblem(
							AcceleoParserMessages.getString("CSTParser.InvalidModuleElement"), currentPosBegin, bH.b()); //$NON-NLS-1$
				}
				if (pModuleElement == pComment) {
					currentPosBegin = parseCommentEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pImport) {
					currentPosBegin = parseImportEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pTemplate) {
					currentPosBegin = parseTemplateEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pMacro) {
					currentPosBegin = parseMacroEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pQuery) {
					currentPosBegin = parseQueryEnding(bH, posEnd, eModule);
				} else if (pModuleElement == pDocumentation) {
					currentPosBegin = parseDocumentationEnding(bH, posEnd, eModule);
				} else {
					currentPosBegin = -1; // never
				}
			}
		}
	}

	/**
	 * Reads the following text as a new 'documentation' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseDocumentationEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pDocumentation.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER,
					IAcceleoConstants.DOCUMENTATION_BEGIN), beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			Documentation eDocumentation = CstFactory.eINSTANCE.createDocumentation();
			setPositions(eDocumentation, beginHeader.b(), eH.e());

			String body = parseDocumentationBody(source.getBuffer().substring(beginHeader.e(), eH.b()));
			eDocumentation.setBody(body);

			DocumentationUtils.parseToDoFixMe(this.source, eDocumentation.getStartPosition(), eDocumentation
					.getEndPosition(), CommentType.DOCUMENTATION);

			eModule.getOwnedModuleElement().add(eDocumentation);
			posBegin = eH.e();
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'comment' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseCommentEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pComment.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.COMMENT),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pComment.getEndHeaderBody()) {
				Comment eComment = CstFactory.eINSTANCE.createComment();
				setPositions(eComment, beginHeader.b(), eH.e());

				eComment.setBody(source.getBuffer().substring(beginHeader.e(), eH.b()));

				DocumentationUtils.parseToDoFixMe(this.source, eComment.getStartPosition(), eComment
						.getEndPosition(), CommentType.COMMENT_IN_HEADER);

				eModule.getOwnedModuleElement().add(eComment);

				posBegin = eH.e();
			} else {
				Region eB = pComment.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.COMMENT),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					Comment eComment = CstFactory.eINSTANCE.createComment();
					setPositions(eComment, beginHeader.b(), eB.e());

					eComment.setBody(source.getBuffer().substring(eH.e(), eB.b()));

					DocumentationUtils.parseToDoFixMe(this.source, eComment.getStartPosition(), eComment
							.getEndPosition(), CommentType.COMMENT_WITH_END_HEADER);

					eModule.getOwnedModuleElement().add(eComment);

					posBegin = eB.e();
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'import' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseImportEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pImport.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.IMPORT),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			ModuleImportsValue eModuleImportsValue = CstFactory.eINSTANCE.createModuleImportsValue();
			setPositions(eModuleImportsValue, beginHeader.e(), eH.b());
			eModuleImportsValue.setName(source.getBuffer().substring(beginHeader.e(), eH.b()).trim());

			if (eModuleImportsValue.getName() != null
					&& !eModuleImportsValue.getName().contains(IAcceleoConstants.NAMESPACE_SEPARATOR)) {
				this.logWarning(AcceleoParserMessages.getString("CSTParser.UnqualifiedImport"), beginHeader //$NON-NLS-1$
						.e(), eH.b());
			}

			eModule.getImports().add(eModuleImportsValue);
			posBegin = eH.e();
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'template' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseTemplateEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pTemplate.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.TEMPLATE),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pTemplate.getEndHeaderBody()) {
				posBegin = eH.e();
				Template eTemplate = CstFactory.eINSTANCE.createTemplate();
				setPositions(eTemplate, beginHeader.b(), eH.e());
				eModule.getOwnedModuleElement().add(eTemplate);
				parseTemplateHeader(beginHeader.e(), eH.b(), eTemplate);
			} else {
				Region eB = pTemplate.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.TEMPLATE),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					Template eTemplate = CstFactory.eINSTANCE.createTemplate();
					setPositions(eTemplate, beginHeader.b(), eB.e());
					eModule.getOwnedModuleElement().add(eTemplate);
					parseTemplateHeader(beginHeader.e(), eH.b(), eTemplate);
					parseTemplateBody(eH.e(), eB.b(), eTemplate);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'macro' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseMacroEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pMacro.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.MACRO),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pMacro.getEndHeaderBody()) {
				posBegin = eH.e();
				Macro eMacro = CstFactory.eINSTANCE.createMacro();
				setPositions(eMacro, beginHeader.b(), eH.e());
				eModule.getOwnedModuleElement().add(eMacro);
				parseMacroHeader(beginHeader.e(), eH.b(), eMacro);
			} else {
				Region eB = pMacro.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_STMT, IAcceleoConstants.MACRO),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					Macro eMacro = CstFactory.eINSTANCE.createMacro();
					setPositions(eMacro, beginHeader.b(), eB.e());
					eModule.getOwnedModuleElement().add(eMacro);
					parseMacroHeader(beginHeader.e(), eH.b(), eMacro);
					parseMacroBody(eH.e(), eB.b(), eMacro);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'query' (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModule
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the element to parse
	 */
	private int parseQueryEnding(Region beginHeader, int posEnd, Module eModule) {
		int posBegin;
		Region eH = pQuery.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_STMT_HEADER, IAcceleoConstants.QUERY),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pQuery.getEndHeaderBody()) {
				posBegin = eH.e();
				Query eQuery = CstFactory.eINSTANCE.createQuery();
				setPositions(eQuery, beginHeader.b(), eH.e());
				eModule.getOwnedModuleElement().add(eQuery);
				parseQueryHeader(beginHeader.e(), eH.b(), eQuery);
			} else {
				Region eB = pQuery.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(
							AcceleoParserMessages.getString("CSTParser.InvalidQuery"), beginHeader.b(), beginHeader.e()); //$NON-NLS-1$
					posBegin = -1;
				} else {
					logProblem(
							AcceleoParserMessages.getString("CSTParser.InvalidQuery"), beginHeader.b(), eB.e()); //$NON-NLS-1$
					posBegin = eB.e();
					Query eQuery = CstFactory.eINSTANCE.createQuery();
					setPositions(eQuery, beginHeader.b(), eB.e());
					eModule.getOwnedModuleElement().add(eQuery);
					parseQueryHeader(beginHeader.e(), eH.b(), eQuery);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'template' header in the text, and modifies the properties of the current
	 * CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTemplate
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseTemplateHeader(int posBegin, int posEnd, Template eTemplate) {
		int posShift = shiftVisibility(posBegin, posEnd, eTemplate);
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posShift, posEnd);
		if (bH.b() == -1) {
			logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posShift, posEnd);
			String name = source.getBuffer().substring(posShift, posEnd).trim();
			if (!ParserUtils.isIdentifier(name)) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, posEnd);
			}
			eTemplate.setName(name);
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(
						AcceleoParserMessages.getString("CSTParser.InvalidTemplateName", name), posShift, posEnd); //$NON-NLS-1$
			}
		} else {
			String name = source.getBuffer().substring(posShift, bH.b()).trim();
			if (!ParserUtils.isIdentifier(name)) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, bH.b());
			}
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(
						AcceleoParserMessages.getString("CSTParser.InvalidTemplateName", name), posShift, bH.b()); //$NON-NLS-1$
			}
			eTemplate.setName(name);
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				// never
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				Variable[] eVariables = createVariablesCommaSeparator(bH.e(), eH.b());
				for (int i = 0; eVariables != null && i < eVariables.length; i++) {
					Variable eVariable = eVariables[i];
					if (eVariable != null) {
						eTemplate.getParameter().add(eVariable);
					}
				}
				int currentPosBegin = eH.e();
				int currentPosEnd = parseTemplateHeaderIndexOfGuardOrPostOrBrackets(currentPosBegin, posEnd);
				currentPosBegin = parseTemplateHeaderOverrides(currentPosBegin, currentPosEnd, eTemplate);
				currentPosEnd = posEnd;
				currentPosBegin = parseTemplateHeaderGuard(currentPosBegin, currentPosEnd, posEnd, eTemplate);
				currentPosBegin = parseTemplateHeaderPost(currentPosBegin, currentPosEnd, posEnd, eTemplate);
				currentPosBegin = shiftInitSectionBody(currentPosBegin, currentPosEnd, eTemplate);
				if (source.getBuffer().substring(currentPosBegin, posEnd).trim().length() > 0) {
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, currentPosBegin, posEnd);
				}
				if (eTemplate.getParameter().isEmpty()) {
					logWarning(
							AcceleoParserMessages.getString("CSTParser.MissingParameters"), eTemplate.getStartPosition(), eTemplate.getEndPosition()); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Gets the index of the next guard or the index of the next brackets.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the next guard or the index of the next brackets
	 */
	private int parseTemplateHeaderIndexOfGuardOrPostOrBrackets(int posBegin, int posEnd) {
		int currentPosEnd = posEnd;
		Region guard = pGuard.search(source.getBuffer(), posBegin, currentPosEnd, null,
				new SequenceBlock[] {pLiteral });
		if (guard.b() > -1) {
			currentPosEnd = guard.b();
		} else {
			Region post = pPost.search(source.getBuffer(), posBegin, currentPosEnd, null,
					new SequenceBlock[] {pLiteral });
			if (post.b() > -1) {
				currentPosEnd = post.b();
			} else {
				Region bracket = pBrackets.search(source.getBuffer(), posBegin, currentPosEnd, null,
						new SequenceBlock[] {pLiteral });
				if (bracket.b() > -1) {
					currentPosEnd = bracket.b();
				}
			}
		}
		return currentPosEnd;
	}

	/**
	 * Reads eventually the overrides clause in the template header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTemplate
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseTemplateHeaderOverrides(int posBegin, int posEnd, Template eTemplate) {
		int currentPosBegin = posBegin;
		int bOverride = ParserUtils.shiftKeyword(source.getBuffer(), currentPosBegin, posEnd,
				IAcceleoConstants.OVERRIDES, true);
		if (bOverride != currentPosBegin) {
			currentPosBegin = bOverride;
			while (currentPosBegin != posEnd) {
				Region comma = pComma.search(source.getBuffer(), currentPosBegin, posEnd, null,
						new SequenceBlock[] {pLiteral });
				if (comma.b() == -1) {
					TemplateOverridesValue eTemplateOverridesValue = CstFactory.eINSTANCE
							.createTemplateOverridesValue();
					setPositions(eTemplateOverridesValue, currentPosBegin, posEnd);
					eTemplateOverridesValue.setName(source.getBuffer().substring(currentPosBegin, posEnd)
							.trim());
					eTemplate.getOverrides().add(eTemplateOverridesValue);
					currentPosBegin = posEnd;
				} else {
					TemplateOverridesValue eTemplateOverridesValue = CstFactory.eINSTANCE
							.createTemplateOverridesValue();
					setPositions(eTemplateOverridesValue, currentPosBegin, comma.b());
					eTemplateOverridesValue.setName(source.getBuffer().substring(currentPosBegin, comma.b())
							.trim());
					eTemplate.getOverrides().add(eTemplateOverridesValue);
					currentPosBegin = comma.e();
				}
			}
		}
		return currentPosBegin;
	}

	/**
	 * Reads eventually the guard clause in the template header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param headerPosEnd
	 *            is the ending index of the header
	 * @param eTemplate
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseTemplateHeaderGuard(int posBegin, int posEnd, int headerPosEnd, Template eTemplate) {
		int currentPos = posBegin;
		int bGuard = ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd,
				IAcceleoConstants.GUARD, false);
		if (bGuard != currentPos) {
			currentPos = bGuard;
			if (ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd,
					IAcceleoConstants.PARENTHESIS_BEGIN, false) == currentPos) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, currentPos,
						posEnd);
				currentPos = headerPosEnd;
			} else {
				Region bHParenthesis = pParenthesis.searchBeginHeader(source.getBuffer(), currentPos, posEnd);
				Region eHParenthesis = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(),
						bHParenthesis, posEnd);
				if (eHParenthesis.b() == -1) {
					// never
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED,
							bHParenthesis.b(), bHParenthesis.e());
					currentPos = headerPosEnd;
				} else {
					ModelExpression eGuard = CstFactory.eINSTANCE.createModelExpression();
					setPositions(eGuard, bHParenthesis.e(), eHParenthesis.b());
					eTemplate.setGuard(eGuard);
					pBlock.parseExpressionHeader(bHParenthesis.e(), eHParenthesis.b(), eGuard);
					currentPos = eHParenthesis.e();
				}
			}
		}
		return currentPos;
	}

	/**
	 * Reads eventually the post clause in the template header. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param headerPosEnd
	 *            is the ending index of the header
	 * @param eTemplate
	 *            is the current node of the CST
	 * @return the ending index of the clause, or the beginning index if it doesn't exist
	 */
	private int parseTemplateHeaderPost(int posBegin, int posEnd, int headerPosEnd, Template eTemplate) {
		int currentPos = posBegin;
		int bPost = ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd, IAcceleoConstants.POST,
				false);
		if (bPost != currentPos) {
			currentPos = bPost;
			if (ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd,
					IAcceleoConstants.PARENTHESIS_BEGIN, false) == currentPos) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, currentPos,
						posEnd);
				currentPos = headerPosEnd;
			} else {
				Region bHParenthesis = pParenthesis.searchBeginHeader(source.getBuffer(), currentPos, posEnd);
				Region eHParenthesis = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(),
						bHParenthesis, posEnd);
				if (eHParenthesis.b() == -1) {
					// never
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED,
							bHParenthesis.b(), bHParenthesis.e());
					currentPos = headerPosEnd;
				} else {
					ModelExpression ePost = CstFactory.eINSTANCE.createModelExpression();
					setPositions(ePost, bHParenthesis.e(), eHParenthesis.b());
					eTemplate.setPost(ePost);
					pBlock.parseExpressionHeader(bHParenthesis.e(), eHParenthesis.b(), ePost);
					currentPos = eHParenthesis.e();
				}
			}
		}
		return currentPos;
	}

	/**
	 * Reads eventually the following visibility in the text. Spaces are ignored.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModuleElement
	 *            is the current node of the CST
	 * @return the ending index of the keyword, or the beginning index if it doesn't exist
	 */
	private int shiftVisibility(int posBegin, int posEnd, ModuleElement eModuleElement) {
		int posShift = ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.VISIBILITY_KIND_PRIVATE, true);
		int b = posBegin;
		if (b != posShift) {
			eModuleElement.setVisibility(VisibilityKind.PRIVATE);
		} else {
			posShift = ParserUtils.shiftKeyword(source.getBuffer(), posShift, posEnd,
					IAcceleoConstants.VISIBILITY_KIND_PROTECTED, true);
			if (b != posShift) {
				eModuleElement.setVisibility(VisibilityKind.PROTECTED);
			} else {
				posShift = ParserUtils.shiftKeyword(source.getBuffer(), posShift, posEnd,
						IAcceleoConstants.VISIBILITY_KIND_PUBLIC, true);
				if (b != posShift) {
					eModuleElement.setVisibility(VisibilityKind.PUBLIC);
				} else {
					this.logWarning(AcceleoParserMessages.getString("CSTParser.MissingVisibility"), //$NON-NLS-1$
							eModuleElement.getStartPosition(), eModuleElement.getStartPosition());
				}
			}
		}
		return posShift;
	}

	/**
	 * Reads eventually the following text as a new 'init section' (header and body) in the CST model.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            is the current object of the CST model, it will be modified in this method
	 * @return the ending index of the init section, or the beginning index if it doesn't exist
	 */
	protected int shiftInitSectionBody(int posBegin, int posEnd, Block eBlock) {
		if (ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd, IAcceleoConstants.BRACKETS_BEGIN,
				false) != posBegin) {
			Region bHBrackets = pBrackets.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
			Region eHBrackets = pBrackets
					.searchEndHeaderAtBeginHeader(source.getBuffer(), bHBrackets, posEnd);
			int posResult;
			if (eHBrackets.b() == -1) {
				logProblem(
						AcceleoParserMessages.getString("CSTParser.MissingClosingBracket"), bHBrackets.b(), bHBrackets.e()); //$NON-NLS-1$
				posResult = posEnd;
			} else {
				InitSection eInitSection = CstFactory.eINSTANCE.createInitSection();
				setPositions(eInitSection, bHBrackets.b(), eHBrackets.e());
				eBlock.setInit(eInitSection);
				boolean semicolonFound = shiftInitSectionBodyCreatesVariables(bHBrackets.e(), eHBrackets.b(),
						eInitSection);
				if (!semicolonFound) {
					logProblem(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.SEMICOLON_SEPARATOR), bHBrackets.e(), eHBrackets.b());
				}
				posResult = eHBrackets.e();
			}
			return posResult;
		}
		return posBegin;
	}

	/**
	 * Creates new variables in the CST model by reading the text in the init section. A semicolon separator
	 * is considered between each variable.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eInitSection
	 *            is the current node of the CST model
	 * @return true if one (or more) semicolon is found
	 */
	private boolean shiftInitSectionBodyCreatesVariables(int posBegin, int posEnd, InitSection eInitSection) {
		List<Region> positions = pSemicolon.split(source.getBuffer(), posBegin, posEnd, true, null, null);
		boolean semicolonFound = false;
		for (int i = 0; i < positions.size(); i++) {
			Region variablePos = positions.get(i);
			String text = source.getBuffer().substring(variablePos.b(), variablePos.e()).trim();
			if (text.equals(IAcceleoConstants.SEMICOLON_SEPARATOR)) {
				if (!semicolonFound) {
					semicolonFound = true;
				}
			} else if (text.length() > 0) {
				Variable eVariable = createVariable(variablePos.b(), variablePos.e());
				if (eVariable != null) {
					eInitSection.getVariable().add(eVariable);
				}
			}
		}
		return semicolonFound;
	}

	/**
	 * Creates new variables in the CST model by reading the following text between the given delimiters. A
	 * comma separator is considered between each variable.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the new variables
	 */
	protected Variable[] createVariablesCommaSeparator(int posBegin, int posEnd) {
		List<Region> positions = pComma.split(source.getBuffer(), posBegin, posEnd, false, null, null);
		Variable[] eVariables = new Variable[positions.size()];
		List<String> variableNames = new ArrayList<String>();
		for (int i = 0; i < eVariables.length; i++) {
			Region variablePos = positions.get(i);
			Variable eVariable = createVariable(variablePos.b(), variablePos.e());
			eVariables[i] = eVariable;
			if (eVariable != null) {
				if (!variableNames.contains(eVariable.getName())) {
					variableNames.add(eVariable.getName());
				} else {
					logProblem(
							AcceleoParserMessages.getString(
									"CSTParser.DuplicatedVariable", new Object[] {eVariable.getName() }), variablePos.b(), variablePos.e()); //$NON-NLS-1$
				}
			}
		}
		return eVariables;
	}

	/**
	 * Creates a new variable in the CST model by reading the following text between the given delimiters.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the new variable
	 */
	public Variable createVariable(int posBegin, int posEnd) {
		String variableBuffer = source.getBuffer().substring(posBegin, posEnd);
		Variable eVariable;
		int bDot = variableBuffer.indexOf(IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR);
		if (bDot == -1) {
			logProblem(
					AcceleoParserMessages.getString("CSTParser.InvalidVariable", variableBuffer.trim()), posBegin, //$NON-NLS-1$
					posEnd);
			eVariable = null;
		} else {
			String name = variableBuffer.substring(0, bDot);
			String type = variableBuffer.substring(bDot
					+ IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length());
			eVariable = CstFactory.eINSTANCE.createVariable();
			setPositions(eVariable, posBegin, posEnd);
			int bInit = variableBuffer.indexOf(IAcceleoConstants.VARIABLE_INIT_SEPARATOR, bDot
					+ IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length());
			if (bInit != -1) {
				type = variableBuffer.substring(bDot
						+ IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length(), bInit);
				String initExpression = variableBuffer.substring(bInit
						+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length());
				ModelExpression eInitExpression = CstFactory.eINSTANCE.createModelExpression();
				setPositions(eInitExpression, posBegin + bInit
						+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd);
				eVariable.setInitExpression(eInitExpression);
				eInitExpression.setBody(initExpression);
				pBlock.parseExpressionHeader(posBegin + bInit
						+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd, eInitExpression);
			}

			eVariable.setName(name.trim());
			if (!ParserUtils.isIdentifier(name.trim())) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name.trim(), posBegin,
						posEnd);
			}
			if (ACCELEO_KEYWORDS.contains(name.trim())
					|| AcceleoOCLReflection.getReservedKeywords().contains(name.trim())) {
				logWarning(AcceleoParserMessages.getString("CSTParser.InvalidVariableName", name), posBegin, //$NON-NLS-1$
						posEnd);
			}

			eVariable.setType(type.trim());
		}
		return eVariable;
	}

	/**
	 * Gets the current module for the given object of the CST model.
	 * 
	 * @param eObject
	 *            is an object of the CST model
	 * @return the current module
	 */
	protected Module getModule(EObject eObject) {
		EObject current = eObject;
		while (current != null) {
			if (current instanceof Module) {
				return (Module)current;
			}
			current = current.eContainer();
		}
		return null;
	}

	/**
	 * Reads the information of the 'template' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTemplate
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseTemplateBody(int posBegin, int posEnd, Template eTemplate) {
		pBlock.parse(posBegin, posEnd, eTemplate);
	}

	/**
	 * Reads the information of the 'query' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eQuery
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseQueryHeader(int posBegin, int posEnd, Query eQuery) {
		int posShift = shiftVisibility(posBegin, posEnd, eQuery);
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posShift, posEnd);
		if (bH.b() == -1) {
			logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posShift, posEnd);
			String name = source.getBuffer().substring(posShift, posEnd).trim();
			if (!ParserUtils.isIdentifier(name)) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, posEnd);
			}
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(AcceleoParserMessages.getString("CSTParser.InvalidQueryName", name), posShift, //$NON-NLS-1$
						posEnd);
			}
			eQuery.setName(name);
		} else {
			String name = source.getBuffer().substring(posShift, bH.b()).trim();
			if (!ParserUtils.isIdentifier(name)) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, bH.b());
			}
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(AcceleoParserMessages.getString("CSTParser.InvalidQueryName", name), posShift, bH //$NON-NLS-1$
						.b());
			}
			eQuery.setName(name);
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				// never
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				Variable[] eVariables = createVariablesCommaSeparator(bH.e(), eH.b());
				for (int i = 0; eVariables != null && i < eVariables.length; i++) {
					Variable eVariable = eVariables[i];
					if (eVariable != null) {
						eQuery.getParameter().add(eVariable);
					}
				}
				int currentPosBegin = eH.e();
				int eDot = ParserUtils.shiftKeyword(source.getBuffer(), currentPosBegin, posEnd,
						IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, false);
				if (eDot == currentPosBegin) {
					logProblem(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR), currentPosBegin, posEnd);
				}
				String bodyText = source.getBuffer().substring(eDot, posEnd);
				int bInit = bodyText.indexOf(IAcceleoConstants.VARIABLE_INIT_SEPARATOR);
				if (bInit != -1) {
					String type = bodyText.substring(0, bInit).trim();
					eQuery.setType(type);
					String initExpression = bodyText.substring(bInit
							+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length());
					ModelExpression eExpression = CstFactory.eINSTANCE.createModelExpression();
					setPositions(eExpression, eDot + bInit
							+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd);
					eQuery.setExpression(eExpression);
					eExpression.setBody(initExpression);
					pBlock.parseExpressionHeader(eDot + bInit
							+ IAcceleoConstants.VARIABLE_INIT_SEPARATOR.length(), posEnd, eExpression);
				} else {
					logProblem(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.VARIABLE_INIT_SEPARATOR), currentPosBegin, posEnd);
				}
				if (eQuery.getParameter().isEmpty()) {
					logWarning(
							AcceleoParserMessages.getString("CSTParser.MissingParameters"), eQuery.getStartPosition(), eQuery.getEndPosition()); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Reads the information of the 'macro' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eMacro
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseMacroHeader(int posBegin, int posEnd, Macro eMacro) {
		int posShift = shiftVisibility(posBegin, posEnd, eMacro);
		Region bH = pParenthesis.searchBeginHeader(source.getBuffer(), posShift, posEnd);
		if (bH.b() == -1) {
			logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posShift, posEnd);
			String name = source.getBuffer().substring(posShift, posEnd).trim();
			if (!ParserUtils.isIdentifier(name)) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, posEnd);
			}
			eMacro.setName(name);
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(AcceleoParserMessages.getString("CSTParser.InvalidMacroName", name), posShift, //$NON-NLS-1$
						posEnd);
			}
		} else {
			String name = source.getBuffer().substring(posShift, bH.b()).trim();
			if (!ParserUtils.isIdentifier(name)) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name, posShift, bH.b());
			}
			eMacro.setName(name);
			if (ACCELEO_KEYWORDS.contains(name) || AcceleoOCLReflection.getReservedKeywords().contains(name)) {
				logWarning(AcceleoParserMessages.getString("CSTParser.InvalidMacroName", name), posShift, //$NON-NLS-1$
						bH.b());
			}
			Region eH = pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				// never
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				Variable[] eVariables = createVariablesCommaSeparator(bH.e(), eH.b());
				for (int i = 0; eVariables != null && i < eVariables.length; i++) {
					Variable eVariable = eVariables[i];
					if (eVariable != null) {
						eMacro.getParameter().add(eVariable);
					}
				}
				int currentPosBegin = eH.e();
				int eDot = ParserUtils.shiftKeyword(source.getBuffer(), currentPosBegin, posEnd,
						IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR, false);
				if (eDot == currentPosBegin) {
					logProblem(AcceleoParserMessages.getString(MISSING_CHARACTER_KEY,
							IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR), currentPosBegin, posEnd);
				}
				String type = source.getBuffer().substring(eDot, posEnd).trim();
				eMacro.setType(type);
			}
		}
	}

	/**
	 * Reads the information of the 'macro' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eMacro
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseMacroBody(int posBegin, int posEnd, Macro eMacro) {
		pBlock.parse(posBegin, posEnd, eMacro);
	}

	/**
	 * To log a new problem.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	protected void logProblem(String message, int posBegin, int posEnd) {
		source.logProblem(message, posBegin, posEnd);
	}

	/**
	 * To log a new warning.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	protected void logWarning(String message, int posBegin, int posEnd) {
		source.logWarning(message, posBegin, posEnd);
	}

	/**
	 * To log a new information.
	 * 
	 * @param message
	 *            is the message
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	@SuppressWarnings("unused")
	protected void logInfo(String message, int posBegin, int posEnd) {
		source.logInfo(message, posBegin, posEnd);
	}

}
