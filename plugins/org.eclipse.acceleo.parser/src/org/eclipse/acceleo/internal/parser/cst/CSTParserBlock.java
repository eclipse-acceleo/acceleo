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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLReflection;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.internal.parser.IAcceleoParserProblemsConstants;
import org.eclipse.acceleo.internal.parser.cst.utils.ISequence;
import org.eclipse.acceleo.internal.parser.cst.utils.ParserUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.internal.parser.cst.utils.SequenceBlock;
import org.eclipse.acceleo.internal.parser.documentation.utils.DocumentationUtils;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.OpenModeKind;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TraceBlock;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Creates a CST node by parsing a text representing a Acceleo statement : 'for', 'if'...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CSTParserBlock {
	/** Key of the error message that should be logged when a block header isn't closed. */
	private static final String INVALID_BLOCK_HEADER = "CSTParserBlock.InvalidBlockHeader"; //$NON-NLS-1$

	/** Key of the error message that should be logged when a block isn't closed. */
	private static final String INVALID_BLOCK = "CSTParserBlock.InvalidBlock"; //$NON-NLS-1$

	/**
	 * The main parser.
	 */
	protected CSTParser pAcceleo;

	/**
	 * To parse a 'trace' block in the text.
	 */
	protected SequenceBlock pTrace;

	/**
	 * To parse a 'file' block in the text.
	 */
	protected SequenceBlock pFile;

	/**
	 * To parse a 'for' block in the text.
	 */
	protected SequenceBlock pFor;

	/**
	 * To parse a 'if' block in the text.
	 */
	protected SequenceBlock pIf;

	/**
	 * To parse a 'else if' block in the text.
	 */
	protected SequenceBlock pElseIf;

	/**
	 * To parse a 'else' sequence in the text.
	 */
	protected Sequence pElse;

	/**
	 * To parse a 'let' block in the text.
	 */
	protected SequenceBlock pLet;

	/**
	 * To parse a 'else let' block in the text.
	 */
	protected SequenceBlock pElseLet;

	/**
	 * To parse a 'protected area' block in the text.
	 */
	protected SequenceBlock pProtectedArea;

	/**
	 * To parse an OCL expression in the text.
	 */
	protected SequenceBlock pExpression;

	/**
	 * The source buffer to parse.
	 */
	protected AcceleoSourceBuffer source;

	/**
	 * Constructor.
	 * 
	 * @param pAcceleo
	 *            is the main parser
	 */
	public CSTParserBlock(CSTParser pAcceleo) {
		super();
		this.pAcceleo = pAcceleo;
		this.source = pAcceleo.source;
		pTrace = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.TRACE,
				new SequenceBlock[] {pAcceleo.pLiteral }, new SequenceBlock[] {pAcceleo.pComment });
		pFile = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.FILE,
				new SequenceBlock[] {pAcceleo.pLiteral }, new SequenceBlock[] {pAcceleo.pComment });
		pFor = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.FOR, new SequenceBlock[] {
				pAcceleo.pLiteral, pAcceleo.pParenthesis, }, new SequenceBlock[] {pAcceleo.pComment, });
		pIf = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.IF,
				new SequenceBlock[] {pAcceleo.pLiteral }, new SequenceBlock[] {pAcceleo.pComment });
		Sequence pElseIfBeginHeader = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE_IF);
		Sequence pElseIfEndHeader = new Sequence(IAcceleoConstants.DEFAULT_END);
		pElseIf = new SequenceBlock(pElseIfBeginHeader, pElseIfEndHeader, null, false,
				new SequenceBlock[] {pAcceleo.pLiteral });
		pElse = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.ELSE,
				IAcceleoConstants.DEFAULT_END);
		pLet = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.LET,
				new SequenceBlock[] {pAcceleo.pLiteral }, new SequenceBlock[] {pAcceleo.pComment });
		Sequence pElseLetBeginHeader = new Sequence(IAcceleoConstants.DEFAULT_BEGIN,
				IAcceleoConstants.ELSE_LET);
		Sequence pElseLetEndHeader = new Sequence(IAcceleoConstants.DEFAULT_END);
		pElseLet = new SequenceBlock(pElseLetBeginHeader, pElseLetEndHeader, null, false,
				new SequenceBlock[] {pAcceleo.pLiteral });
		pProtectedArea = ParserUtils.createAcceleoSequenceBlock(false, IAcceleoConstants.PROTECTED_AREA,
				new SequenceBlock[] {pAcceleo.pLiteral }, new SequenceBlock[] {pAcceleo.pComment });
		Sequence pBegin = new Sequence(IAcceleoConstants.DEFAULT_BEGIN);
		Sequence pEnd = new Sequence(IAcceleoConstants.DEFAULT_END);
		SequenceBlock pBeginEnd = new SequenceBlock(pBegin, pEnd, null, true,
				new SequenceBlock[] {pAcceleo.pLiteral });
		pExpression = new SequenceBlock(new Sequence(IAcceleoConstants.INVOCATION_BEGIN), new Sequence(
				IAcceleoConstants.INVOCATION_END), null, false, new SequenceBlock[] {pAcceleo.pLiteral,
				pBeginEnd, });
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
	private void setPositions(CSTNode eCSTNode, int posBegin, int posEnd) {
		pAcceleo.setPositions(eCSTNode, posBegin, posEnd);
	}

	/**
	 * Reads the text and creates a new 'block' in the CST model. It's the main method. This method is able to
	 * choose the instance of the new block to create. The given eBlock is the parent of the new block.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new block to create
	 */
	public void parse(int posBegin, int posEnd, Block eBlock) {
		SequenceBlock[] pBlockElements = new SequenceBlock[] {pAcceleo.pComment, pFile, pFor, pIf, pLet,
				pTrace, pProtectedArea, pExpression, };
		int currentPosBegin = posBegin;
		Region[] positions = Region.createPositions(pBlockElements.length);
		while (currentPosBegin > -1 && currentPosBegin < posEnd) {
			int i = ParserUtils.getNextSequence(source.getBuffer(), currentPosBegin, posEnd, pBlockElements,
					positions);
			if (i == -1) {
				parseText(currentPosBegin, posEnd, eBlock);
				currentPosBegin = -1;
			} else {
				SequenceBlock pBlockElement = pBlockElements[i];
				Region bH = positions[i];
				parseText(currentPosBegin, bH.b(), eBlock);
				if (pBlockElement == pAcceleo.pComment) {
					currentPosBegin = parseCommentEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pFile) {
					currentPosBegin = parseFileEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pFor) {
					currentPosBegin = parseForEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pIf) {
					currentPosBegin = parseIfEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pLet) {
					currentPosBegin = parseLetEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pTrace) {
					currentPosBegin = parseTraceEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pProtectedArea) {
					currentPosBegin = parseProtectedAreaEnding(bH, posEnd, eBlock);
				} else if (pBlockElement == pExpression) {
					currentPosBegin = parseExpressionEnding(bH, posEnd, eBlock);
				} else {
					currentPosBegin = -1; // never
				}
			}
		}
	}

	/**
	 * Reads the following text as a new 'comment' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'comment' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseCommentEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pAcceleo.pComment.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.COMMENT),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pAcceleo.pComment.getEndHeaderBody()) {
				Comment eComment = CstFactory.eINSTANCE.createComment();
				setPositions(eComment, beginHeader.b(), eH.e());
				eComment.setBody(source.getBuffer().substring(beginHeader.e(), eH.b()));
				eBlock.getBody().add(eComment);
				posBegin = eH.e();

				DocumentationUtils.checkKeyword(eComment.getBody(), IAcceleoConstants.TAG_TODO, source,
						beginHeader.e(), eH.b());
				DocumentationUtils.checkKeyword(eComment.getBody(), IAcceleoConstants.TAG_FIXME, source,
						beginHeader.e(), eH.b());
			} else {
				Region eB = pAcceleo.pComment.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK, IAcceleoConstants.COMMENT),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					Comment eComment = CstFactory.eINSTANCE.createComment();
					setPositions(eComment, beginHeader.b(), eB.e());
					eComment.setBody(source.getBuffer().substring(eH.e(), eB.b()));
					eBlock.getBody().add(eComment);
					posBegin = eB.e();
					DocumentationUtils.checkKeyword(eComment.getBody(), IAcceleoConstants.TAG_TODO, source,
							eH.e(), eB.b());
					DocumentationUtils.checkKeyword(eComment.getBody(), IAcceleoConstants.TAG_FIXME, source,
							eH.e(), eB.b());
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the following text as a new 'file' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'file' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseFileEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pFile.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.FILE),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pFile.getEndHeaderBody()) {
				posBegin = eH.e();
				FileBlock eFile = CstFactory.eINSTANCE.createFileBlock();
				setPositions(eFile, beginHeader.b(), eH.e());
				eBlock.getBody().add(eFile);
				parseFileHeader(beginHeader.e(), eH.b(), eFile);
			} else {
				Region eB = pFile.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK, IAcceleoConstants.FILE),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					FileBlock eFile = CstFactory.eINSTANCE.createFileBlock();
					setPositions(eFile, beginHeader.b(), eB.e());
					eBlock.getBody().add(eFile);
					parseFileHeader(beginHeader.e(), eH.b(), eFile);
					parseFileBody(eH.e(), eB.b(), eFile);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'file' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eFile
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseFileHeader(int posBegin, int posEnd, FileBlock eFile) {
		if (ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.PARENTHESIS_BEGIN, false) == posBegin) {
			logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posBegin, posEnd);
		} else {
			Region bH = pAcceleo.pParenthesis.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
			Region eH = pAcceleo.pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				// File URL
				parseFileHeaderParenthesis(bH, eH, eFile);
			}
		}
	}

	/**
	 * Reads the information between parenthesis of the 'file' header and modifies the properties of the
	 * current CST node.
	 * 
	 * @param bH
	 *            is the beginning index
	 * @param eH
	 *            is the ending index
	 * @param eFile
	 *            is the current object of the CST model, it will be modified in this method
	 */
	private void parseFileHeaderParenthesis(Region bH, Region eH, FileBlock eFile) {
		int b = bH.e();
		int e;
		Region comma = pAcceleo.pComma.search(source.getBuffer(), b, eH.b(), null, new SequenceBlock[] {
				pAcceleo.pParenthesis, pAcceleo.pComment, pAcceleo.pLiteral, });
		if (comma.b() == -1) {
			e = eH.b();
		} else {
			e = comma.b();
		}
		ModelExpression eFileURL = CstFactory.eINSTANCE.createModelExpression();
		setPositions(eFileURL, b, e);
		eFile.setFileUrl(eFileURL);
		parseExpressionHeader(b, e, eFileURL);

		if (eFileURL != null && ("".equals(eFileURL.getBody()) || "''".equals(eFileURL.getBody()))) { //$NON-NLS-1$ //$NON-NLS-2$
			logProblem(AcceleoParserMessages.getString("CSTParserBlock.EmptyFileName"), eFileURL //$NON-NLS-1$
					.getStartPosition(), eFileURL.getEndPosition());
		}

		if (comma.b() != -1) {
			boolean openModeFound = false;
			b = ParserUtils.shiftKeyword(source.getBuffer(), comma.e(), eH.b(), "true", true); //$NON-NLS-1$
			if (b != comma.e()) {
				eFile.setOpenMode(OpenModeKind.APPEND);
				openModeFound = true;
			} else {
				b = ParserUtils.shiftKeyword(source.getBuffer(), comma.e(), eH.b(), "false", true); //$NON-NLS-1$
				if (b != comma.e()) {
					eFile.setOpenMode(OpenModeKind.OVER_WRITE);
					openModeFound = true;
				}
			}
			if (!openModeFound) {
				logProblem(
						AcceleoParserMessages.getString("CSTParserBlock.MissingFileMode"), comma.b(), eH.b()); //$NON-NLS-1$
			} else {
				// For now, simply ignore the specification's "unique ID"
				int eComma = ParserUtils.shiftKeyword(source.getBuffer(), b, eH.b(),
						IAcceleoConstants.COMMA_SEPARATOR, false);
				if (eComma != b) {
					b = eComma;
					ModelExpression eFileCharset = CstFactory.eINSTANCE.createModelExpression();
					setPositions(eFileCharset, b, eH.b());
					eFile.setCharset(eFileCharset);
					parseExpressionHeader(b, eH.b(), eFileCharset);
				} else if (source.getBuffer().substring(b, eH.b()).trim().length() > 0) {
					logProblem(AcceleoParserMessages.getString(
							"Parser.MissingCharacter", IAcceleoConstants.COMMA_SEPARATOR), b, eH.b()); //$NON-NLS-1$
				}
			}
		} else {
			logProblem(AcceleoParserMessages.getString(
					"Parser.MissingCharacter", IAcceleoConstants.COMMA_SEPARATOR), eH.b(), eH.b()); //$NON-NLS-1$
		}
	}

	/**
	 * Reads the information of the 'file' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eFile
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseFileBody(int posBegin, int posEnd, FileBlock eFile) {
		parse(posBegin, posEnd, eFile);
	}

	/**
	 * Reads the following text as a new 'for' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'for' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseForEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pFor.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.FOR),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pFor.getEndHeaderBody()) {
				posBegin = eH.e();
				ForBlock eFor = CstFactory.eINSTANCE.createForBlock();
				setPositions(eFor, beginHeader.b(), eH.e());
				eBlock.getBody().add(eFor);
				parseForHeader(beginHeader.e(), eH.b(), eFor);
			} else {
				Region eB = pFor.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK, IAcceleoConstants.FOR),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					ForBlock eFor = CstFactory.eINSTANCE.createForBlock();
					setPositions(eFor, beginHeader.b(), eB.e());
					eBlock.getBody().add(eFor);
					parseForHeader(beginHeader.e(), eH.b(), eFor);
					parseForBody(eH.e(), eB.b(), eFor);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'for' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eFor
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseForHeader(int posBegin, int posEnd, ForBlock eFor) {
		if (ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.PARENTHESIS_BEGIN, false) == posBegin) {
			logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posBegin, posEnd);
		} else {
			Region bH = pAcceleo.pParenthesis.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
			Region eH = pAcceleo.pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				// File URL
				int b = bH.e();
				int e = eH.b();
				Region pipe = pAcceleo.pPipe.search(source.getBuffer(), b, eH.b());
				if (pipe.b() != -1) {
					Variable eVariable = pAcceleo.createVariable(b, pipe.b());
					eFor.setLoopVariable(eVariable);
					b = pipe.e();
				}
				ModelExpression eIterSet = CstFactory.eINSTANCE.createModelExpression();
				setPositions(eIterSet, b, e);
				eFor.setIterSet(eIterSet);
				parseExpressionHeader(b, e, eIterSet);
				int currentPosBegin = eH.e();
				currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd,
						IAcceleoConstants.BEFORE, true, eFor, CstPackage.eINSTANCE.getForBlock_Before());
				currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd,
						IAcceleoConstants.SEPARATOR, true, eFor, CstPackage.eINSTANCE.getForBlock_Each());
				currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd,
						IAcceleoConstants.AFTER, true, eFor, CstPackage.eINSTANCE.getForBlock_After());
				currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd,
						IAcceleoConstants.GUARD, false, eFor, CstPackage.eINSTANCE.getForBlock_Guard());
				currentPosBegin = pAcceleo.shiftInitSectionBody(currentPosBegin, posEnd, eFor);
				if (source.getBuffer().substring(currentPosBegin, posEnd).trim().length() > 0) {
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, currentPosBegin, posEnd);
				}
			}
		}
	}

	/**
	 * Reads eventually the following text as a new prefixed 'OCLExpression' in the CST model.
	 * <p>
	 * <li>'<b>prefix</b>' '(' <b>eStructuralFeature</b> ')'</li>
	 * <li>'before' '(' OCLExpresion ')'</li>
	 * <li>'separator' '(' OCLExpresion ')'</li>
	 * <li>'after' '(' OCLExpresion ')'</li>
	 * <li>'?' '(' OCLExpresion ')'</li>
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param prefix
	 *            is the prefix of the expression : 'before', 'after'...
	 * @param wholeWord
	 *            indicates if the search mode is 'whole word'
	 * @param eCSTNode
	 *            is the current object of the CST model, it will be modified in this method
	 * @param eStructuralFeature
	 *            is the reference to set
	 * @return the ending index of the expression, or the beginning index if it doesn't exist
	 */
	private int shiftPrefixedOCLExpression(int posBegin, int posEnd, String prefix, boolean wholeWord,
			CSTNode eCSTNode, EStructuralFeature eStructuralFeature) {
		int currentPos = posBegin;
		int b = ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd, prefix, wholeWord);
		if (b != currentPos) {
			currentPos = b;
			if (ParserUtils.shiftKeyword(source.getBuffer(), currentPos, posEnd,
					IAcceleoConstants.PARENTHESIS_BEGIN, false) == currentPos) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, currentPos,
						posEnd);
				currentPos = posEnd;
			} else {
				Region bHParenthesis = pAcceleo.pParenthesis.searchBeginHeader(source.getBuffer(),
						currentPos, posEnd);
				Region eHParenthesis = pAcceleo.pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(),
						bHParenthesis, posEnd);
				if (eHParenthesis.b() == -1) {
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED,
							bHParenthesis.b(), bHParenthesis.e());
					currentPos = posEnd;
				} else {
					ModelExpression eModelExpression = CstFactory.eINSTANCE.createModelExpression();
					setPositions(eModelExpression, bHParenthesis.e(), eHParenthesis.b());
					eCSTNode.eSet(eStructuralFeature, eModelExpression);
					parseExpressionHeader(bHParenthesis.e(), eHParenthesis.b(), eModelExpression);
					currentPos = eHParenthesis.e();
				}
			}
		}
		return currentPos;
	}

	/**
	 * Reads the information of the 'for' body in the text, and modifies the children of the current CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eFor
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseForBody(int posBegin, int posEnd, ForBlock eFor) {
		parse(posBegin, posEnd, eFor);
	}

	/**
	 * Reads the following text as a new 'if' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'if' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseIfEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pIf.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.IF),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pIf.getEndHeaderBody()) {
				posBegin = eH.e();
				IfBlock eIf = CstFactory.eINSTANCE.createIfBlock();
				setPositions(eIf, beginHeader.b(), eH.e());
				eBlock.getBody().add(eIf);
				parseIfHeader(beginHeader.e(), eH.b(), eIf);
			} else {
				Region eB = pIf.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK, IAcceleoConstants.IF),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					IfBlock eIf = CstFactory.eINSTANCE.createIfBlock();
					setPositions(eIf, beginHeader.b(), eB.e());
					eBlock.getBody().add(eIf);
					parseIfHeader(beginHeader.e(), eH.b(), eIf);
					parseIfBody(eH.e(), eB.b(), eIf);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'if' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eIf
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseIfHeader(int posBegin, int posEnd, IfBlock eIf) {
		ModelExpression eModelExpression = CstFactory.eINSTANCE.createModelExpression();
		setPositions(eModelExpression, posBegin, posEnd);
		eIf.setIfExpr(eModelExpression);
		parseExpressionHeader(posBegin, posEnd, eModelExpression);
	}

	/**
	 * Reads the information of the 'if' body in the text, and modifies the children of the current CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eIf
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseIfBody(int posBegin, int posEnd, IfBlock eIf) {
		ISequence[] pElements = new ISequence[] {pElseIf, pElse };
		SequenceBlock[] pInhibs = new SequenceBlock[] {pAcceleo.pComment, pIf };
		Region[] pElementsPositions = Region.createPositions(pElements.length);
		Region[] pInhibsPositions = Region.createPositions(pInhibs.length);
		int i = ParserUtils.getNextSequence(source.getBuffer(), posBegin, posEnd, pElements,
				pElementsPositions, pInhibs, pInhibsPositions);
		int eThen;
		if (i == -1) {
			eThen = posEnd;
		} else {
			eThen = pElementsPositions[i].b();
		}
		parse(posBegin, eThen, eIf);
		while (i != -1) {
			ISequence pElement = pElements[i];
			if (pElement == pElse) {
				Block eElse = CstFactory.eINSTANCE.createBlock();
				setPositions(eElse, pElementsPositions[i].b(), posEnd);
				eIf.setElse(eElse);
				parse(pElementsPositions[i].e(), posEnd, eElse);
				i = -1;
			} else if (pElement == pElseIf) {
				Region eH = pElseIf.searchEndHeaderAtBeginHeader(source.getBuffer(), pElementsPositions[i],
						posEnd);
				if (eH.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER,
							IAcceleoConstants.ELSE_IF), pElementsPositions[i].b(), pElementsPositions[i].e());
					i = -1;
				} else {
					int bElseIf = pElementsPositions[i].b();
					IfBlock eElseIfBlock = CstFactory.eINSTANCE.createIfBlock();
					// setPositions in the next lines
					eIf.getElseIf().add(eElseIfBlock);
					parseIfHeader(pElementsPositions[i].e(), eH.b(), eElseIfBlock);
					i = ParserUtils.getNextSequence(source.getBuffer(), eH.e(), posEnd, pElements,
							pElementsPositions, pInhibs, pInhibsPositions);
					int eElseIf;
					if (i == -1) {
						eElseIf = posEnd;
					} else {
						eElseIf = pElementsPositions[i].b();
					}
					setPositions(eElseIfBlock, bElseIf, eElseIf);
					parseIfBody(eH.e(), eElseIf, eElseIfBlock);
				}
			} else {
				i = -1; // never
			}
		}
	}

	/**
	 * Reads the following text as a new 'let' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'let' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseLetEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pLet.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.LET),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pLet.getEndHeaderBody()) {
				posBegin = eH.e();
				LetBlock eLet = CstFactory.eINSTANCE.createLetBlock();
				setPositions(eLet, beginHeader.b(), eH.e());
				eBlock.getBody().add(eLet);
				parseLetHeader(beginHeader.e(), eH.b(), eLet);
			} else {
				Region eB = pLet.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK, IAcceleoConstants.LET),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					LetBlock eLet = CstFactory.eINSTANCE.createLetBlock();
					setPositions(eLet, beginHeader.b(), eB.e());
					eBlock.getBody().add(eLet);
					parseLetHeader(beginHeader.e(), eH.b(), eLet);
					parseLetBody(eH.e(), eB.b(), eLet);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'let' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eLet
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseLetHeader(int posBegin, int posEnd, LetBlock eLet) {
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
				eVariable.setName(name.trim());
				if (!ParserUtils.isIdentifier(name.trim())) {
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_NAME_NOT_VALID + name.trim(), posBegin,
							posEnd);
				}
				if (CSTParser.ACCELEO_KEYWORDS.contains(name.trim())
						|| AcceleoOCLReflection.getReservedKeywords().contains(name.trim())) {
					pAcceleo.logWarning(AcceleoParserMessages
							.getString("CSTParser.InvalidVariableName", name), posBegin, //$NON-NLS-1$
							posEnd);
				}

				eVariable.setType(type.trim());
			} else {
				logProblem(AcceleoParserMessages.getString("CSTParser.InvalidLetVariable", variableBuffer //$NON-NLS-1$
						.trim()), posBegin, posEnd);
				eVariable = null;
			}

		}
		if (eVariable != null) {
			eLet.setLetVariable(eVariable);
		}
	}

	/**
	 * Reads the information of the 'let' body in the text, and modifies the children of the current CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eLet
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseLetBody(int posBegin, int posEnd, LetBlock eLet) {
		ISequence[] pElements = new ISequence[] {pElseLet, pElse };
		SequenceBlock[] pInhibs = new SequenceBlock[] {pAcceleo.pComment, pLet, pIf };
		Region[] pElementsPositions = Region.createPositions(pElements.length);
		Region[] pInhibsPositions = Region.createPositions(pInhibs.length);
		int i = ParserUtils.getNextSequence(source.getBuffer(), posBegin, posEnd, pElements,
				pElementsPositions, pInhibs, pInhibsPositions);
		int eThen;
		if (i == -1) {
			eThen = posEnd;
		} else {
			eThen = pElementsPositions[i].b();
		}
		parse(posBegin, eThen, eLet);
		while (i != -1) {
			ISequence pElement = pElements[i];
			if (pElement == pElse) {
				Block eElse = CstFactory.eINSTANCE.createBlock();
				setPositions(eElse, pElementsPositions[i].b(), posEnd);
				eLet.setElse(eElse);
				parse(pElementsPositions[i].e(), posEnd, eElse);
				i = -1;
			} else if (pElement == pElseLet) {
				Region eH = pElseLet.searchEndHeaderAtBeginHeader(source.getBuffer(), pElementsPositions[i],
						posEnd);
				if (eH.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER,
							IAcceleoConstants.ELSE_LET), pElementsPositions[i].b(), pElementsPositions[i].e());
					i = -1;
				} else {
					int bElseLet = pElementsPositions[i].b();
					LetBlock eElseLetBlock = CstFactory.eINSTANCE.createLetBlock();
					// setPositions in the next lines
					eLet.getElseLet().add(eElseLetBlock);
					parseLetHeader(pElementsPositions[i].e(), eH.b(), eElseLetBlock);
					i = ParserUtils.getNextSequence(source.getBuffer(), eH.e(), posEnd, pElements,
							pElementsPositions, pInhibs, pInhibsPositions);
					int eElseLet;
					if (i == -1) {
						eElseLet = posEnd;
					} else {
						eElseLet = pElementsPositions[i].b();
					}
					setPositions(eElseLetBlock, bElseLet, eElseLet);
					parseLetBody(eH.e(), eElseLet, eElseLetBlock);
				}
			} else {
				i = -1; // never
			}
		}
	}

	/**
	 * Reads the following text as a new 'trace' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'trace' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseTraceEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pTrace.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.TRACE),
					beginHeader.b(), beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pTrace.getEndHeaderBody()) {
				posBegin = eH.e();
				TraceBlock eTrace = CstFactory.eINSTANCE.createTraceBlock();
				setPositions(eTrace, beginHeader.b(), eH.e());
				eBlock.getBody().add(eTrace);
				parseTraceHeader(beginHeader.e(), eH.b(), eTrace);
			} else {
				Region eB = pTrace.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK, IAcceleoConstants.TRACE),
							beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					TraceBlock eTrace = CstFactory.eINSTANCE.createTraceBlock();
					setPositions(eTrace, beginHeader.b(), eB.e());
					eBlock.getBody().add(eTrace);
					parseTraceHeader(beginHeader.e(), eH.b(), eTrace);
					parseTraceBody(eH.e(), eB.b(), eTrace);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'trace' header in the text, and modifies the properties of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTrace
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseTraceHeader(int posBegin, int posEnd, TraceBlock eTrace) {
		ModelExpression eModelExpression = CstFactory.eINSTANCE.createModelExpression();
		setPositions(eModelExpression, posBegin, posEnd);
		eTrace.setModelElement(eModelExpression);
		parseExpressionHeader(posBegin, posEnd, eModelExpression);
	}

	/**
	 * Reads the information of the 'trace' body in the text, and modifies the children of the current CST
	 * node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eTrace
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseTraceBody(int posBegin, int posEnd, TraceBlock eTrace) {
		parse(posBegin, posEnd, eTrace);
	}

	/**
	 * Reads the following text as a new 'protected area' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'protected area' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseProtectedAreaEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pProtectedArea.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(AcceleoParserMessages
					.getString(INVALID_BLOCK_HEADER, IAcceleoConstants.PROTECTED_AREA), beginHeader.b(),
					beginHeader.e());
			posBegin = -1;
		} else {
			if (eH.getSequence() == pProtectedArea.getEndHeaderBody()) {
				posBegin = eH.e();
				ProtectedAreaBlock eProtectedArea = CstFactory.eINSTANCE.createProtectedAreaBlock();
				setPositions(eProtectedArea, beginHeader.b(), eH.e());
				eBlock.getBody().add(eProtectedArea);
				parseProtectedAreaHeader(beginHeader.e(), eH.b(), eProtectedArea);
			} else {
				Region eB = pProtectedArea.searchEndBodyAtEndHeader(source.getBuffer(), eH, posEnd);
				if (eB.b() == -1) {
					logProblem(AcceleoParserMessages.getString(INVALID_BLOCK,
							IAcceleoConstants.PROTECTED_AREA), beginHeader.b(), beginHeader.e());
					posBegin = -1;
				} else {
					posBegin = eB.e();
					ProtectedAreaBlock eProtectedArea = CstFactory.eINSTANCE.createProtectedAreaBlock();
					setPositions(eProtectedArea, beginHeader.b(), eB.e());
					eBlock.getBody().add(eProtectedArea);
					parseProtectedAreaHeader(beginHeader.e(), eH.b(), eProtectedArea);
					parseProtectedAreaBody(eH.e(), eB.b(), eProtectedArea);
				}
			}
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'protected area' header in the text, and modifies the properties of the
	 * current CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eProtectedArea
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseProtectedAreaHeader(int posBegin, int posEnd, ProtectedAreaBlock eProtectedArea) {
		if (ParserUtils.shiftKeyword(source.getBuffer(), posBegin, posEnd,
				IAcceleoConstants.PARENTHESIS_BEGIN, false) == posBegin) {
			logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_ARE_REQUIRED, posBegin, posEnd);
		} else {
			Region bH = pAcceleo.pParenthesis.searchBeginHeader(source.getBuffer(), posBegin, posEnd);
			Region eH = pAcceleo.pParenthesis.searchEndHeaderAtBeginHeader(source.getBuffer(), bH, posEnd);
			if (eH.b() == -1) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_PARENTHESIS_NOT_TERMINATED, bH.b(), bH.e());
			} else {
				ModelExpression eModelExpression = CstFactory.eINSTANCE.createModelExpression();
				setPositions(eModelExpression, bH.e(), eH.b());
				eProtectedArea.setMarker(eModelExpression);
				parseExpressionHeader(bH.e(), eH.b(), eModelExpression);
				if (source.getBuffer().substring(eH.e(), posEnd).trim().length() > 0) {
					logProblem(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, eH.e(), posEnd);
				}
			}
		}
	}

	/**
	 * Reads the information of the 'protected area' body in the text, and modifies the children of the
	 * current CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eProtectedArea
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseProtectedAreaBody(int posBegin, int posEnd, ProtectedAreaBlock eProtectedArea) {
		parse(posBegin, posEnd, eProtectedArea);
	}

	/**
	 * Reads the following text as a new 'expression' block (header and body) in the CST model.
	 * 
	 * @param beginHeader
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new 'expression' block to create
	 * @return the ending index of the element to parse
	 */
	private int parseExpressionEnding(Region beginHeader, int posEnd, Block eBlock) {
		int posBegin;
		Region eH = pExpression.searchEndHeaderAtBeginHeader(source.getBuffer(), beginHeader, posEnd);
		if (eH.b() == -1) {
			logProblem(
					AcceleoParserMessages.getString("CSTParserBlock.InvalidInvocation"), beginHeader.b(), beginHeader.e()); //$NON-NLS-1$
			posBegin = -1;
		} else {
			posBegin = eH.e();
			ModelExpression eModelExpression = CstFactory.eINSTANCE.createModelExpression();
			setPositions(eModelExpression, beginHeader.e(), eH.b());
			eBlock.getBody().add(eModelExpression);
			parseExpressionHeader(beginHeader.e(), eH.b(), eModelExpression);
		}
		return posBegin;
	}

	/**
	 * Reads the information of the 'expression' header in the text, and modifies the properties of the
	 * current CST node.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eModelExpression
	 *            is the current object of the CST model, it will be modified in this method
	 */
	public void parseExpressionHeader(int posBegin, int posEnd, ModelExpression eModelExpression) {
		Sequence pBefore = new Sequence(IAcceleoConstants.BEFORE, IAcceleoConstants.PARENTHESIS_BEGIN);
		Sequence pEach = new Sequence(IAcceleoConstants.SEPARATOR, IAcceleoConstants.PARENTHESIS_BEGIN);
		Sequence pAfter = new Sequence(IAcceleoConstants.AFTER, IAcceleoConstants.PARENTHESIS_BEGIN);
		Sequence[] pElements = new Sequence[] {pBefore, pEach, pAfter };
		Region[] pElementsPositions = Region.createPositions(pElements.length);
		SequenceBlock[] pInhibs = new SequenceBlock[] {pAcceleo.pLiteral, pAcceleo.pParenthesis, };
		Region[] pInhibsPositions = Region.createPositions(pInhibs.length);
		int i = ParserUtils.getNextSequence(source.getBuffer(), posBegin, posEnd, pElements,
				pElementsPositions, pInhibs, pInhibsPositions);
		int bBeforeEachAfter;
		if (i != -1 && pElementsPositions[i].b() != -1) {
			bBeforeEachAfter = pElementsPositions[i].b();
		} else {
			bBeforeEachAfter = posEnd;
		}
		eModelExpression.setBody(source.getBuffer().substring(posBegin, bBeforeEachAfter).trim());
		if (bBeforeEachAfter < posEnd) {
			int currentPosBegin = bBeforeEachAfter;
			currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd, IAcceleoConstants.BEFORE,
					true, eModelExpression, CstPackage.eINSTANCE.getModelExpression_Before());
			currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd,
					IAcceleoConstants.SEPARATOR, true, eModelExpression, CstPackage.eINSTANCE
							.getModelExpression_Each());
			currentPosBegin = shiftPrefixedOCLExpression(currentPosBegin, posEnd, IAcceleoConstants.AFTER,
					true, eModelExpression, CstPackage.eINSTANCE.getModelExpression_After());
			if (source.getBuffer().substring(currentPosBegin, posEnd).trim().length() > 0) {
				logProblem(IAcceleoParserProblemsConstants.SYNTAX_TEXT_NOT_VALID, currentPosBegin, posEnd);
			}
		}
	}

	/**
	 * Reads the following text as a new 'TextExpression' in the CST model.
	 * 
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param eBlock
	 *            will be the parent of the new object to create
	 */
	private void parseText(int posBegin, int posEnd, Block eBlock) {
		if (source.getBuffer().substring(posBegin, posEnd).length() > 0) {
			TextExpression eText = CstFactory.eINSTANCE.createTextExpression();
			setPositions(eText, posBegin, posEnd);
			eText.setValue(source.getBuffer().substring(posBegin, posEnd));
			eBlock.getBody().add(eText);
		}
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
	private void logProblem(String message, int posBegin, int posEnd) {
		source.logProblem(message, posBegin, posEnd);
	}

}
