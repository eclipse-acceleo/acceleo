/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.util.Objects;

import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IPosition;

/**
 * {@link IPosition} implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class Position implements IPosition {

	/**
	 * The line number.
	 */
	private final int line;

	/**
	 * The column number.
	 */
	private final int column;

	/**
	 * The position offset.
	 */
	private final int position;

	/**
	 * Constructor.
	 * 
	 * @param line
	 *            the line number
	 * @param column
	 *            the column number
	 * @param position
	 *            the position offset
	 */
	public Position(int line, int column, int position) {
		this.line = line;
		this.column = column;
		this.position = position;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public int getPosition() {
		return position;
	}

	/**
	 * Creates the {@link IPosition} corresponding to the index of a character in a {@link String text}.
	 * 
	 * @param characterIndex
	 *            the (positive) character index.
	 * @param text
	 *            the (non-{@code null}) {@link String text}.
	 * @return the corresponding {@link IPosition}.
	 */
	public static IPosition getCorrespondingPosition(int characterIndex, String text) {
		Objects.requireNonNull(text);

		int currentLine = 0;
		int currentColumn = 0;
		int i = 0;
		while (i < characterIndex) {
			final int textLength = text.length();
			final int newLineLength = newLineAt(text, textLength, i);
			if (newLineLength != 0) {
				currentLine++;
				currentColumn = 0;
				i += newLineLength;
			} else {
				currentColumn++;
				i++;
			}
		}

		return new Position(currentLine, currentColumn, characterIndex);
	}

	/**
	 * Tells if the given text has a new line at the given index and return the new line length if found.
	 * 
	 * @param text
	 *            the text
	 * @param textLength
	 *            the text length
	 * @param index
	 *            the index
	 * @return the length of the new line if the given text has a new line at the given index, <code>0</code>
	 *         otherwise
	 */
	private static int newLineAt(String text, int textLength, int index) {
		final int res;

		if (index < textLength) {
			if (text.charAt(index) == '\r' && index + 1 < textLength && text.charAt(index + 1) == '\n') {
				res = 2;
			} else if (text.charAt(index) == '\n' || text.charAt(index) == '\r') {
				res = 1;
			} else {
				res = 0;
			}
		} else {
			res = 0;
		}

		return res;
	}

}
