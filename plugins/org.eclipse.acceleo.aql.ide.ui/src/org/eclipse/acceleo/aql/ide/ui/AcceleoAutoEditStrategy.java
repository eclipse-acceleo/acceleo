/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;

public class AcceleoAutoEditStrategy implements IAutoEditStrategy {

	private static final Set<String> BLOCK_STARTS = initBlockStarts();

	private static Set<String> initBlockStarts() {
		final Set<String> res = new HashSet<>();

		res.add(AcceleoParser.BLOCK_COMMENT_START);
		res.add(AcceleoParser.FILE_HEADER_START);
		res.add(AcceleoParser.FOR_HEADER_START);
		res.add(AcceleoParser.IF_HEADER_START);
		res.add(AcceleoParser.IF_ELSEIF);
		res.add(AcceleoParser.IF_ELSE);
		res.add(AcceleoParser.LET_HEADER_START);
		res.add(AcceleoParser.PROTECTED_AREA_HEADER_START);
		res.add(AcceleoParser.QUERY_START);
		res.add(AcceleoParser.TEMPLATE_HEADER_START);

		return res;
	}

	/**
	 * Returns the first offset greater than <code>offset</code> and smaller than <code>end</code> whose
	 * character is not a space or tab character. If no such offset is found, <code>end</code> is returned.
	 *
	 * @param document
	 *            the document to search in
	 * @param offset
	 *            the offset at which searching start
	 * @param end
	 *            the offset at which searching stops
	 * @return the offset in the specified range whose character is not a space or tab
	 * @exception BadLocationException
	 *                if position is an invalid range in the given document
	 */
	protected int findEndOfWhiteSpace(IDocument document, int offset, int end) throws BadLocationException {
		while (offset < end) {
			char c = document.getChar(offset);
			if (c != ' ' && c != '\t') {
				return offset;
			}
			offset++;
		}
		return end;
	}

	/**
	 * Copies the indentation of the previous line.
	 *
	 * @param document
	 *            the document to work on
	 * @param command
	 *            the command to deal with
	 */
	private void autoIndentAfterNewLine(IDocument document, DocumentCommand command) {

		if (command.offset == -1 || document.getLength() == 0)
			return;

		try {
			int position = (command.offset == document.getLength() ? command.offset - 1 : command.offset);
			int indentationSize = neededIndentation(document, position);

			StringBuilder buf = new StringBuilder(command.text);
			if (indentationSize > 0) {
				for (int i = 0; i < indentationSize; i++) {
					buf.append(" ");
				}
			} else {
				// find start of line
				IRegion info = document.getLineInformationOfOffset(position);
				int start = info.getOffset();

				// find white spaces
				int end = findEndOfWhiteSpace(document, start, command.offset);

				if (end > start) {
					// append to input
					buf.append(document.get(start, end - start));
				}
			}
			command.text = buf.toString();
		} catch (BadLocationException excp) {
			// stop work
		}
	}

	private int neededIndentation(IDocument document, int position) {
		int res = 0;

		try {
			final IRegion currentLineInfo = document.getLineInformationOfOffset(position);
			final String currentLine = document.get(currentLineInfo.getOffset(), currentLineInfo.getLength());
			if (currentLine.endsWith(AcceleoParser.NO_SLASH_END)) {
				for (String start : BLOCK_STARTS) {
					final int indexOfStart = currentLine.indexOf(start);
					if (indexOfStart > -1) {
						res = indexOfStart + 2; // plus the two spaces for indentation
						break;
					}
				}
			}
		} catch (BadLocationException e) {
			// nothing to do here
		}

		return res;
	}

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (command.length == 0 && command.text != null && TextUtilities.endsWith(document
				.getLegalLineDelimiters(), command.text) != -1) {
			if (onNewLine(document, command)) {
				autoIndentAfterNewLine(document, command);
			}
		}
	}

	/**
	 * Tells if the given {@link DocumentCommand} was triggered on a new line of the given {@link IDocument}.
	 * 
	 * @param document
	 *            the {@link IDocument}
	 * @param command
	 *            the {@link DocumentCommand}
	 * @return <code>true</code> if the given {@link DocumentCommand} was triggered on a new line of the given
	 *         {@link IDocument}, <code>false</code> otherwise
	 */
	private boolean onNewLine(IDocument document, DocumentCommand command) {
		boolean res;
		try {
			res = AcceleoParser.NEW_LINE.equals(document.get(command.offset, AcceleoParser.NEW_LINE
					.length()));
		} catch (BadLocationException e) {
			res = false;
		}

		return res;
	}

}
