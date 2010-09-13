/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - Small modification in order to adapt it to Acceleo
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;

/**
 * The indentation strategy of the Acceleo documentation. Just like in the JDT every carriage return in the
 * Acceleo documentation will create a '*' at the beginning of the new line. This class is directly inspired
 * by the JavaDocAutoIndentStrategy class from the JDT which does exactly the same thing but with different
 * options.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	/**
	 * The constructor.
	 */
	public AcceleoDocAutoIndentStrategy() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy#customizeDocumentCommand(org.eclipse.jface.text.IDocument,
	 *      org.eclipse.jface.text.DocumentCommand)
	 */
	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (command.text != null) {
			if (command.length == 0) {
				String[] lineDelimiters = document.getLegalLineDelimiters();
				int index = TextUtilities.endsWith(lineDelimiters, command.text);
				if (index > -1 && lineDelimiters[index].equals(command.text)) {
					indentAfterNewLine(document, command);
				} else if (index > -1) {
					return;
				}
			}

			if (IAcceleoConstants.DEFAULT_END.equals(command.text)) {
				indentAfterCommentEnd(document, command);
				return;
			}
		}
	}

	/**
	 * Copies the indentation of the previous line and adds a star. If the documentation just started on this
	 * line add standard method tags and close the Javadoc.
	 * 
	 * @param d
	 *            the document to work on
	 * @param c
	 *            the command to deal with
	 */
	private void indentAfterNewLine(IDocument d, DocumentCommand c) {

		int offset = c.offset;
		if (offset == -1 || d.getLength() == 0) {
			return;
		}

		try {
			int p = -1;
			if (offset == d.getLength()) {
				p = offset - 1;
			} else {
				p = offset;
			}
			IRegion line = d.getLineInformationOfOffset(p);

			int lineOffset = line.getOffset();
			int firstNonWS = findEndOfWhiteSpace(d, lineOffset, offset);
			Assert.isTrue(firstNonWS >= lineOffset, AcceleoUIMessages
					.getString("AcceleoDocAutoIndentStrategy.InvalidIndentation")); //$NON-NLS-1$

			StringBuffer buf = new StringBuffer(c.text);
			IRegion prefix = findPrefixRange(d, line);
			String indentation = d.get(prefix.getOffset(), prefix.getLength());
			int lengthToAdd = Math.min(offset - prefix.getOffset(), prefix.getLength());

			buf.append(indentation.substring(0, lengthToAdd));

			if (firstNonWS < offset) {
				if (d.getChar(firstNonWS) == '[') {
					// documentation started on this line
					buf.append(" * "); //$NON-NLS-1$

					if (isNewComment(d, offset)) {
						c.shiftsCaret = false;
						c.caretOffset = c.offset + buf.length();
						String lineDelimiter = TextUtilities.getDefaultLineDelimiter(d);

						int eolOffset = lineOffset + line.getLength();
						int replacementLength = eolOffset - p;
						String restOfLine = d.get(p, replacementLength);
						String endTag = lineDelimiter + indentation
								+ IAcceleoConstants.DOCUMENTATION_NEW_LINE
								+ IAcceleoConstants.DEFAULT_END_BODY_CHAR + IAcceleoConstants.DEFAULT_END;

						// we need to close the comment before computing
						// the correct tags in order to get the method
						d.replace(offset, replacementLength, endTag);
						buf.append(restOfLine);
					}
				}
			}

			// move the caret behind the prefix, even if we do not have to insert it.
			if (lengthToAdd < prefix.getLength()) {
				c.caretOffset = offset + prefix.getLength() - lengthToAdd;
			}
			c.text = buf.toString();

		} catch (BadLocationException excp) {
			// stop work
		}
	}

	/**
	 * Returns the range of the Javadoc prefix on the given line in <code>document</code>. The prefix greedily
	 * matches the following regex pattern: <code>\w*\*\w*</code>, that is, any number of whitespace
	 * characters, followed by an asterisk ('*'), followed by any number of whitespace characters.
	 * 
	 * @param document
	 *            the document to which <code>line</code> refers
	 * @param line
	 *            the line from which to extract the prefix range
	 * @return an <code>IRegion</code> describing the range of the prefix on the given line
	 * @throws BadLocationException
	 *             if accessing the document fails
	 */
	private IRegion findPrefixRange(IDocument document, IRegion line) throws BadLocationException {
		int lineOffset = line.getOffset();
		int lineEnd = lineOffset + line.getLength();
		int indentEnd = findEndOfWhiteSpace(document, lineOffset, lineEnd);
		if (indentEnd < lineEnd && document.getChar(indentEnd) == '*') {
			indentEnd++;
			while (indentEnd < lineEnd && document.getChar(indentEnd) == ' ') {
				indentEnd++;
			}
		}
		return new Region(lineOffset, indentEnd - lineOffset);
	}

	/**
	 * Guesses if the command operates within a newly created documentation block or not. If in doubt, it will
	 * assume that the documentation is new.
	 * 
	 * @param document
	 *            the document
	 * @param commandOffset
	 *            the command offset
	 * @return <code>true</code> if the comment should be closed, <code>false</code> if not
	 */
	private boolean isNewComment(IDocument document, int commandOffset) {
		boolean result = false;
		try {
			int lineIndex = document.getLineOfOffset(commandOffset) + 1;
			if (lineIndex >= document.getNumberOfLines()) {
				result = true;
			}

			IRegion line = document.getLineInformation(lineIndex);
			ITypedRegion partition = TextUtilities.getPartition(document,
					AcceleoPartitionScanner.ACCELEO_DOCUMENTATION, commandOffset, false);
			int partitionEnd = partition.getOffset() + partition.getLength();
			if (line.getOffset() >= partitionEnd) {
				result = false;
			}

			if (document.getLength() == partitionEnd) {
				result = true; // partition goes to end of document - probably a new comment
			}

			String comment = document.get(partition.getOffset(), partition.getLength());
			if (comment.indexOf(IAcceleoConstants.DEFAULT_BEGIN + IAcceleoConstants.DOCUMENTATION_BEGIN, 2) != -1) {
				result = true; // enclosed another comment -> probably a new comment
			}

		} catch (BadLocationException e) {
			result = false;
		}
		return result;
	}

	/**
	 * Unindents a typed slash (']') if it forms the end of a comment.
	 * 
	 * @param d
	 *            the document
	 * @param c
	 *            the command
	 */
	private void indentAfterCommentEnd(IDocument d, DocumentCommand c) {
		// 3 = ' /]'
		if (c.offset < 3 || d.getLength() == 0) {
			return;
		}
		try {
			if ("* ".equals(d.get(c.offset - 3, 3))) { //$NON-NLS-1$
				// modify document command
				c.length++;
				c.offset--;
			}
		} catch (BadLocationException excp) {
			// stop work
		}
	}

}
