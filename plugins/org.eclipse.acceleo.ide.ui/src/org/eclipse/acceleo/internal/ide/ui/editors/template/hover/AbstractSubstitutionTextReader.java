/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - minor modifications for checkstyle
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import java.io.IOException;
import java.io.Reader;

/**
 * Reads the text contents from a reader and computes for each character a potential substitution. The
 * substitution may eat more characters than only the one passed into the computation routine.
 * <p>
 * Moved into this package from <code>org.eclipse.jface.internal.text.revisions</code>.
 * </p>
 * Checkstyle needs an author, and since somebody wrote this class...
 * 
 * @author <a href="mailto:some.body@ibm.com">Some Body</a>
 */
public abstract class AbstractSubstitutionTextReader extends AbstractSingleCharReader {

	/**
	 * The line separator.
	 */
	protected static final String LINE_DELIM = System.getProperty("line.separator", "\n"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Indicates if the last character was a whitespace.
	 */
	protected boolean fWasWhiteSpace;

	/**
	 * The reader.
	 */
	private Reader fReader;

	/**
	 * The character after the whitespace.
	 */
	private int fCharAfterWhiteSpace;

	/**
	 * Tells whether white space characters are skipped.
	 */
	private boolean fSkipWhiteSpace = true;

	/**
	 * Indicates if we should read from the buffer.
	 */
	private boolean fReadFromBuffer;

	/**
	 * The buffer.
	 */
	private StringBuffer fBuffer;

	/**
	 * The index.
	 */
	private int fIndex;

	/**
	 * The constructor.
	 * 
	 * @param reader
	 *            the reader
	 */
	protected AbstractSubstitutionTextReader(Reader reader) {
		fReader = reader;
		fBuffer = new StringBuffer();
		fIndex = 0;
		fReadFromBuffer = false;
		fCharAfterWhiteSpace = -1;
		fWasWhiteSpace = true;
	}

	/**
	 * Computes the substitution for the given character and if necessary subsequent characters.
	 * Implementation should use <code>nextChar</code> to read subsequent characters.
	 * 
	 * @param c
	 *            the character to be substituted
	 * @return the substitution for <code>c</code>
	 * @throws IOException
	 *             in case computing the substitution fails
	 */
	protected abstract String computeSubstitution(int c) throws IOException;

	/**
	 * Returns the internal reader.
	 * 
	 * @return the internal reader
	 */
	protected Reader getReader() {
		return fReader;
	}

	/**
	 * Returns the next character.
	 * 
	 * @return the next character
	 * @throws IOException
	 *             in case reading the character fails
	 */
	// CHECKSTYLE:OFF it comes from the JDT, it works, it's not that dirty so let's not touch it
	protected int nextChar() throws IOException {
		fReadFromBuffer = (fBuffer.length() > 0);
		if (fReadFromBuffer) {
			char ch = fBuffer.charAt(fIndex++);
			if (fIndex >= fBuffer.length()) {
				fBuffer.setLength(0);
				fIndex = 0;
			}
			return ch;
		}

		int ch = fCharAfterWhiteSpace;
		if (ch == -1) {
			ch = fReader.read();
		}
		if (fSkipWhiteSpace && Character.isWhitespace((char)ch)) {
			do {
				ch = fReader.read();
			} while (Character.isWhitespace((char)ch));
			if (ch != -1) {
				fCharAfterWhiteSpace = ch;
				return ' ';
			}
		} else {
			fCharAfterWhiteSpace = -1;
		}
		return ch;
	}

	// CHECKSTYLE:ON

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AbstractSingleCharReader#read()
	 */
	@Override
	public int read() throws IOException {
		int c;
		do {

			c = nextChar();
			while (!fReadFromBuffer) {
				String s = computeSubstitution(c);
				if (s == null) {
					break;
				}
				if (s.length() > 0) {
					fBuffer.insert(0, s);
				}
				c = nextChar();
			}

		} while (fSkipWhiteSpace && fWasWhiteSpace && (c == ' '));
		// CHECKSTYLE:OFF it comes from the JDT, it works, it's not that dirty so let's not touch it
		fWasWhiteSpace = (c == ' ' || c == '\r' || c == '\n');
		// CHECKSTYLE:ON
		return c;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AbstractSingleCharReader#ready()
	 */
	@Override
	public boolean ready() throws IOException {
		return fReader.ready();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Reader#close()
	 */
	@Override
	public void close() throws IOException {
		fReader.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Reader#reset()
	 */
	@Override
	public void reset() throws IOException {
		fReader.reset();
		fWasWhiteSpace = true;
		fCharAfterWhiteSpace = -1;
		fBuffer.setLength(0);
		fIndex = 0;
	}

	/**
	 * Sets the skipping whitespace state.
	 * 
	 * @param state
	 *            The skipping whitespace state
	 */
	protected final void setSkipWhitespace(boolean state) {
		fSkipWhiteSpace = state;
	}

	/**
	 * Indicates if we are skipping the whitespace.
	 * 
	 * @return true if we are skipping the whitespace, false otherwise.
	 */
	protected final boolean isSkippingWhitespace() {
		return fSkipWhiteSpace;
	}
}
