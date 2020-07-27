/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.common;

import java.util.Objects;

import org.eclipse.acceleo.ASTNode;
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

	private AcceleoLanguageServerPositionUtils() {
		// Utility class.
	}

	/**
	 * Provides the start {@link Position} of an {@link ASTNode} in the given {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link ASTNode} we want the start {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the start of {@code astNode} in
	 *         {@code inAcceleoAstResult}.
	 */
	public static Position getStartPositionOf(ASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		int nodeStartLine = inAcceleoAstResult.getStartLine(astNode);
		int nodeStartColumn = inAcceleoAstResult.getStartColumn(astNode);
		if (nodeStartLine == -1 || nodeStartColumn == -1) {
			return null;
		} else {
			return new Position(nodeStartLine, nodeStartColumn);
		}
	}

	/**
	 * Provides the end {@link Position} of an {@link ASTNode} in the given {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link ASTNode} we want the end {@link Position} of.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Position} corresponding to the end of {@code astNode} in {@code inAcceleoAstResult}.
	 */
	public static Position getEndPositionOf(ASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		int nodeEndLine = inAcceleoAstResult.getEndLine(astNode);
		int nodeEndColumn = inAcceleoAstResult.getEndColumn(astNode);
		if (nodeEndLine == -1 || nodeEndColumn == -1) {
			return null;
		} else {
			return new Position(nodeEndLine, nodeEndColumn);
		}
	}

	/**
	 * Provides the {@link Range} corresponding to an {@link ASTNode} in the given {@link AcceleoAstResult}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link ASTNode}.
	 * @param inAcceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Range} corresponding to the position in the source contents of
	 *         {@code inAcceleoAstResult} corresponding to {@code astNode}.
	 */
	public static Range getRangeOf(ASTNode astNode, AcceleoAstResult inAcceleoAstResult) {
		return new Range(getStartPositionOf(astNode, inAcceleoAstResult), getEndPositionOf(astNode,
				inAcceleoAstResult));
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
		// FIXME: this provides the Position of the whole expression (including its contained expressions like
		// the arguments of a query call), whereas we probably want only the Position of the specific
		// expression term passed.
		// This is due to how AQL stores the positions of an Expression.
		int nodeStartLine = inAcceleoAstResult.getStartLine(aqlExpression);
		int nodeStartColumn = inAcceleoAstResult.getStartColumn(aqlExpression);
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
		// FIXME: this provides the Position of the whole expression (including its contained expressions like
		// the arguments of a query call), whereas we probably want only the Position of the specific
		// expression term passed.
		// This is due to how AQL stores the positions of an Expression.
		int nodeEndLine = inAcceleoAstResult.getEndLine(aqlExpression);
		int nodeEndColumn = inAcceleoAstResult.getEndColumn(aqlExpression);
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
		char currentCharacter;
		while (currentLine != positionLine && currentIndex < text.length()) {
			currentCharacter = text.charAt(currentIndex);
			if (currentCharacter == '\n') {
				currentLine++;
			}
			currentIndex++;
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

		int lineNumber = 0;
		int indexOfLastNewLine = -1;
		for (int currentIndex = 0; currentIndex < characterIndex; currentIndex++) {
			Character currentCharacter = text.charAt(currentIndex);
			if (currentCharacter == '\n') {
				lineNumber++;
				indexOfLastNewLine = currentIndex;
			}
		}
		int offset;
		if (lineNumber == 0) {
			// The text is only one line, so the offset is the same as the character index.
			offset = characterIndex;
		} else {
			// The text is on a line that starts at character index "indexOfLastNewLine", but the newline is a
			// character so we subtract 1.
			offset = characterIndex - indexOfLastNewLine - 1;
		}

		return new Position(lineNumber, offset);
	}
}
