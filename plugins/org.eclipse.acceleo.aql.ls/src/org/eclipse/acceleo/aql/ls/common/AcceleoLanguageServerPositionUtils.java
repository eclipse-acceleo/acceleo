/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.common;

import java.util.Objects;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

/**
 * Various utilities to make Acceleo APIs and LSP4J APIs work together, especially with {@link Position} and
 * {@link Range}.
 * 
 * @author Florent Latombe
 */
public final class AcceleoLanguageServerPositionUtils {

	/**
	 * New line.
	 */
	public static final String NEW_LINE = "\n";

	/**
	 * New line.
	 */
	public static final String WINDOWS_NEW_LINE = "\r\n";

	private AcceleoLanguageServerPositionUtils() {
		// Utility class.
	}

	/**
	 * Provides the start {@link Position} of an {@link AcceleoASTNode} in the given {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode} we want the start {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the start of {@code astNode} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getStartPositionOf(AcceleoASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getStartLine(astNode);
		int nodeStartColumn = inAcceleoAstResult.getStartColumn(astNode);
		if (nodeStartLine == -1 || nodeStartColumn == -1) {
			return null;
		} else {
			return new Position(nodeStartLine, nodeStartColumn);
		}
	}

	/**
	 * Provides the identifier start {@link Position} of an {@link AcceleoASTNode} in the given
	 * {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode} we want the start {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the identifier {@link Position} corresponding to the start of {@code astNode} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getIDentifierStartPositionOf(AcceleoASTNode astNode,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getIdentifierStartLine(astNode);
		int nodeStartColumn = inAcceleoAstResult.getIdentifierStartColumn(astNode);
		if (nodeStartLine == -1 || nodeStartColumn == -1) {
			return null;
		} else {
			return new Position(nodeStartLine, nodeStartColumn);
		}
	}

	/**
	 * Provides the end {@link Position} of an {@link AcceleoASTNode} in the given {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode} we want the end {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the end of {@code astNode} in {@code inAcceleoAstResult}.
	 */
	public static Position getEndPositionOf(AcceleoASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getEndLine(astNode);
		int nodeEndColumn = inAcceleoAstResult.getEndColumn(astNode);
		if (nodeEndLine == -1 || nodeEndColumn == -1) {
			return null;
		} else {
			return new Position(nodeEndLine, nodeEndColumn);
		}
	}

	/**
	 * Provides the identifier end {@link Position} of an {@link AcceleoASTNode} in the given
	 * {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode} we want the end {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the identifier {@link Position} corresponding to the end of {@code astNode} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getIdentifierEndPositionOf(AcceleoASTNode astNode,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getIdentifierEndLine(astNode);
		int nodeEndColumn = inAcceleoAstResult.getIdentifierEndColumn(astNode);
		if (nodeEndLine == -1 || nodeEndColumn == -1) {
			return null;
		} else {
			return new Position(nodeEndLine, nodeEndColumn);
		}
	}

	/**
	 * Provides the {@link Range} corresponding to an {@link AcceleoASTNode} in the given
	 * {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Range} corresponding to the position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to {@code astNode}.
	 */
	public static Range getRangeOf(AcceleoASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		return new Range(getStartPositionOf(astNode, inAcceleoAstResult), getEndPositionOf(astNode,
				inAcceleoAstResult));
	}

	/**
	 * Provides the identifier {@link Range} corresponding to an {@link AcceleoASTNode} in the given
	 * {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link AcceleoASTNode}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the identifier {@link Range} corresponding to the position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to {@code astNode}.
	 */
	public static Range getIdentifierRangeOf(AcceleoASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		return new Range(getIDentifierStartPositionOf(astNode, inAcceleoAstResult),
				getIdentifierEndPositionOf(astNode, inAcceleoAstResult));
	}

	/**
	 * Provides the start {@link Position} of an {@link org.eclipse.acceleo.query.ast.Expression AQL
	 * Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlExpression
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.Expression} we want the start
	 *            {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the start of {@code aqlExpression} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getStartPositionOf(org.eclipse.acceleo.query.ast.Expression aqlExpression,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getStartLine(aqlExpression);
		int nodeStartColumn = inAcceleoAstResult.getStartColumn(aqlExpression);
		return new Position(nodeStartLine, nodeStartColumn);
	}

	/**
	 * Provides the identifier start {@link Position} of an {@link org.eclipse.acceleo.query.ast.Expression
	 * AQL Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlExpression
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.Expression} we want the start
	 *            {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the identifier start of {@code aqlExpression} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getIdentifierStartPositionOf(
			org.eclipse.acceleo.query.ast.Expression aqlExpression, AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getIdentifierStartLine(aqlExpression);
		int nodeStartColumn = inAcceleoAstResult.getIdentifierStartColumn(aqlExpression);
		return new Position(nodeStartLine, nodeStartColumn);
	}

	/**
	 * Provides the end {@link Position} of an {@link org.eclipse.acceleo.query.ast.Expression AQL Expression}
	 * in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlExpression
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.Expression} we want the end
	 *            {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the end of {@code aqlExpression} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getEndPositionOf(org.eclipse.acceleo.query.ast.Expression aqlExpression,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getEndLine(aqlExpression);
		int nodeEndColumn = inAcceleoAstResult.getEndColumn(aqlExpression);
		return new Position(nodeEndLine, nodeEndColumn);
	}

	/**
	 * Provides the identifier end {@link Position} of an {@link org.eclipse.acceleo.query.ast.Expression AQL
	 * Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlExpression
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.Expression} we want the end
	 *            {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the identifier end of {@code aqlExpression} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getIdentifierEndPositionOf(org.eclipse.acceleo.query.ast.Expression aqlExpression,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getIdentifierEndLine(aqlExpression);
		int nodeEndColumn = inAcceleoAstResult.getIdentifierEndColumn(aqlExpression);
		return new Position(nodeEndLine, nodeEndColumn);
	}

	/**
	 * Provides the {@link Range} corresponding to an {@link org.eclipse.acceleo.query.ast.Expression AQL
	 * Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlExpression
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.Expression}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Range} corresponding to the position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to {@code aqlExpression}.
	 */
	public static Range getRangeOf(org.eclipse.acceleo.query.ast.Expression aqlExpression,
			AcceleoAstResult inAcceleoAstResult) {
		return new Range(getStartPositionOf(aqlExpression, inAcceleoAstResult), getEndPositionOf(
				aqlExpression, inAcceleoAstResult));
	}

	/**
	 * Provides the {@link Range} corresponding to the identifier of an
	 * {@link org.eclipse.acceleo.query.ast.Expression AQL Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlExpression
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.Expression}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Range} corresponding to the position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to the identifier of an {@code aqlExpression}.
	 */
	public static Range getIdentifierRangeOf(org.eclipse.acceleo.query.ast.Expression aqlExpression,
			AcceleoAstResult inAcceleoAstResult) {
		return new Range(getIdentifierStartPositionOf(aqlExpression, inAcceleoAstResult),
				getIdentifierEndPositionOf(aqlExpression, inAcceleoAstResult));
	}

	/**
	 * Provides the start {@link Position} of an {@link org.eclipse.acceleo.query.ast.VariableDeclaration} in
	 * the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlVariableDeclaration
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.VariableDeclaration} we want the
	 *            start {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the start of {@code aqlVariableDeclaration} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getStartPositionOf(
			org.eclipse.acceleo.query.ast.VariableDeclaration aqlVariableDeclaration,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getStartLine(aqlVariableDeclaration);
		int nodeStartColumn = inAcceleoAstResult.getStartColumn(aqlVariableDeclaration);
		return new Position(nodeStartLine, nodeStartColumn);
	}

	/**
	 * Provides the identifier start {@link Position} of an
	 * {@link org.eclipse.acceleo.query.ast.VariableDeclaration} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlVariableDeclaration
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.VariableDeclaration} we want the
	 *            start {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the identifier start of {@code aqlVariableDeclaration} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getIdentifierStartPositionOf(
			org.eclipse.acceleo.query.ast.VariableDeclaration aqlVariableDeclaration,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getIdentifierStartLine(aqlVariableDeclaration);
		int nodeStartColumn = inAcceleoAstResult.getIdentifierStartColumn(aqlVariableDeclaration);
		return new Position(nodeStartLine, nodeStartColumn);
	}

	/**
	 * Provides the end {@link Position} of an {@link org.eclipse.acceleo.query.ast.VariableDeclaration AQL
	 * Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlVariableDeclaration
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.VariableDeclaration} we want the
	 *            end {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the end of {@code aqlVariableDeclaration} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getEndPositionOf(
			org.eclipse.acceleo.query.ast.VariableDeclaration aqlVariableDeclaration,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getEndLine(aqlVariableDeclaration);
		int nodeEndColumn = inAcceleoAstResult.getEndColumn(aqlVariableDeclaration);
		return new Position(nodeEndLine, nodeEndColumn);
	}

	/**
	 * Provides the identifier end {@link Position} of an
	 * {@link org.eclipse.acceleo.query.ast.VariableDeclaration AQL Expression} in the given
	 * {@link AcceleoAstResult}.
	 * 
	 * @param aqlVariableDeclaration
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.VariableDeclaration} we want the
	 *            end {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the identifier end of {@code aqlVariableDeclaration} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getIdentifierEndPositionOf(
			org.eclipse.acceleo.query.ast.VariableDeclaration aqlVariableDeclaration,
			AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getIdentifierEndLine(aqlVariableDeclaration);
		int nodeEndColumn = inAcceleoAstResult.getIdentifierEndColumn(aqlVariableDeclaration);
		return new Position(nodeEndLine, nodeEndColumn);
	}

	/**
	 * Provides the {@link Range} corresponding to an {@link org.eclipse.acceleo.query.ast.VariableDeclaration
	 * AQL Expression} in the given {@link AcceleoAstResult}.
	 * 
	 * @param aqlVariableDeclaration
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.VariableDeclaration}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Range} corresponding to the position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to {@code aqlVariableDeclaration}.
	 */
	public static Range getRangeOf(org.eclipse.acceleo.query.ast.VariableDeclaration aqlVariableDeclaration,
			AcceleoAstResult inAcceleoAstResult) {
		return new Range(getStartPositionOf(aqlVariableDeclaration, inAcceleoAstResult), getEndPositionOf(
				aqlVariableDeclaration, inAcceleoAstResult));
	}

	/**
	 * Provides the {@link Range} corresponding to the identifier of an
	 * {@link org.eclipse.acceleo.query.ast.VariableDeclaration AQL Expression} in the given
	 * {@link AcceleoAstResult}.
	 * 
	 * @param aqlVariableDeclaration
	 *            the (non-{@code null}) {@link org.eclipse.acceleo.query.ast.VariableDeclaration}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Range} corresponding to the identifier position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to {@code aqlVariableDeclaration}.
	 */
	public static Range getIdentifierRangeOf(
			org.eclipse.acceleo.query.ast.VariableDeclaration aqlVariableDeclaration,
			AcceleoAstResult inAcceleoAstResult) {
		return new Range(getIdentifierStartPositionOf(aqlVariableDeclaration, inAcceleoAstResult),
				getIdentifierEndPositionOf(aqlVariableDeclaration, inAcceleoAstResult));
	}

	/**
	 * Replaces, in a {@link String}, the contents between two {@link Position positions}.
	 * 
	 * @param inText
	 *            the (non-{@code null}) {@link String} in which we replace some of the contents.
	 * @param startPosition
	 *            the (non-{@code null}) {@link Position} that indicates the beginning of the contents to
	 *            replace in {@code inText}.
	 * @param endPosition
	 *            the (non-{@code null}) {@link Position} that indicates the end of the contents to replace in
	 *            {@code inText}.
	 * @param withText
	 *            the (non-{@code null}) {@link String} to insert at {@code startPosition}.
	 * @return the modified {@link String}.
	 */
	public static String replace(String inText, Position startPosition, Position endPosition,
			String withText) {
		int startIndex = getCorrespondingCharacterIndex(startPosition, inText);
		int endIndex = getCorrespondingCharacterIndex(endPosition, inText);

		StringBuilder stringBuilder = new StringBuilder(inText);
		stringBuilder.replace(startIndex, endIndex, withText);
		return stringBuilder.toString();
	}

	/**
	 * Retrieves, in a {@link String text}, the character index corresponding to a {@link Position}.
	 * 
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @param text
	 *            the (non-{@code null}) {@link String}.
	 * @return the character index corresponding to {@code position} in {@code text}.
	 *         {@link IllegalArgumentException} if {@code position} does not exist in {@code text}.
	 */
	public static int getCorrespondingCharacterIndex(Position position, String text) {
		int positionLine = position.getLine();
		int positionOffset = position.getCharacter();

		int currentLine = 0;
		int currentIndex = 0;
		while (currentLine != positionLine && currentIndex < text.length()) {
			final int newLineLength = startsWithNewLine(text.substring(currentIndex));
			if (newLineLength != 0) {
				currentLine++;
				currentIndex += newLineLength;
			} else {
				currentIndex++;
			}
		}
		if (currentLine == positionLine) {
			return currentIndex + positionOffset;
		} else {
			// We reached the end of the text without finding the line designated by the position.
			throw new IllegalArgumentException("Could not find line " + positionLine
					+ " in the given text. This should never happen.");
		}
	}

	/**
	 * Tells if the given text starts with a new line and return if length.
	 * 
	 * @param text
	 *            the text
	 * @return the length of the new line if the given text starts with a new line, <code>0</code> otherwise
	 */
	private static int startsWithNewLine(String text) {
		final int res;

		if (text.startsWith(WINDOWS_NEW_LINE)) {
			res = WINDOWS_NEW_LINE.length();
		} else if (text.startsWith(NEW_LINE)) {
			res = NEW_LINE.length();
		} else {
			res = 0;
		}

		return res;
	}

	/**
	 * Creates the {@link Range} identified by the begin and end character indices in the given {@link String
	 * text}.
	 * 
	 * @param beginCharacterIndex
	 *            the (positive) begin character index.
	 * @param endCharacterIndex
	 *            the (positive) end character index.
	 * @param text
	 *            the (non-{@code null}) {@link String text}.
	 * @return the corresponding {@link Range}.
	 */
	public static Range getCorrespondingRange(int beginCharacterIndex, int endCharacterIndex, String text) {
		Position beginPosition = getCorrespondingPosition(beginCharacterIndex, text);
		Position endPosition = getCorrespondingPosition(endCharacterIndex, text);
		return new Range(beginPosition, endPosition);
	}

	/**
	 * Creates the {@link Position} corresponding to the index of a character in a {@link String text}.
	 * 
	 * @param characterIndex
	 *            the (positive) character index.
	 * @param text
	 *            the (non-{@code null}) {@link String text}.
	 * @return the corresponding {@link Position}.
	 */
	public static Position getCorrespondingPosition(int characterIndex, String text) {
		Objects.requireNonNull(text);

		int currentLine = 0;
		int currentColumn = 0;
		int i = 0;
		while (i < characterIndex) {
			final int newLineLength = startsWithNewLine(text.substring(i));
			if (newLineLength != 0) {
				currentLine++;
				currentColumn = 0;
				i += newLineLength;
			} else {
				currentColumn++;
				i++;
			}
		}

		return new Position(currentLine, currentColumn);
	}
}
