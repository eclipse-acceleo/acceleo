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

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

/**
 * Various {@link String}-based utilities to make Acceleo APIs and LSP4J APIs work together.
 * 
 * @author Florent Latombe
 */
public final class AcceleoLanguageServerStringUtils {

	private AcceleoLanguageServerStringUtils() {
		// Utility class.
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
		int currentIndex = -1;
		char previousCharacter = Character.MIN_VALUE;
		char currentCharacter = Character.MIN_VALUE;
		do {
			previousCharacter = currentCharacter;
			if (previousCharacter == '\n') {
				currentLine++;
			}
			currentIndex++;
			currentCharacter = text.charAt(currentIndex);
		} while (currentLine != positionLine && currentIndex < text.length());

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
