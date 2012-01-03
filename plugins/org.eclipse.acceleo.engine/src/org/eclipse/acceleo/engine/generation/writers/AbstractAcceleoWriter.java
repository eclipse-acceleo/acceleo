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
package org.eclipse.acceleo.engine.generation.writers;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Base class of the writers that need be returned by the various generation strategies.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public abstract class AbstractAcceleoWriter extends Writer {
	/** Default size to be used for new buffers. */
	protected static final int DEFAULT_BUFFER_SIZE = 1024;

	/** This will hold the system specific line separator ("\n" for unix, "\r\n" for dos, "\r" for mac, ...). */
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/** DOS line separators. */
	protected static final String DOS_LINE_SEPARATOR = "\r\n"; //$NON-NLS-1$

	/** Unix line separators. */
	protected static final String UNIX_LINE_SEPARATOR = "\n"; //$NON-NLS-1$

	/** Mac line separators. */
	protected static final String MAC_LINE_SEPARATOR = "\r"; //$NON-NLS-1$

	/** The buffer to which all calls will be delegated. */
	protected Writer delegate;

	/** This will be maintained on-the-fly by all implementation when appending new lines. */
	protected StringBuffer currentIndentation;

	/** This will be <code>true</code> until a non-whitespace character is inserted after a line terminator. */
	protected boolean recordIndentation;

	/**
	 * Default constructor. This will delegate all calls to a StringWriter.
	 */
	public AbstractAcceleoWriter() {
		delegate = new StringWriter(DEFAULT_BUFFER_SIZE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Writer#close()
	 */
	@Override
	public void close() throws IOException {
		delegate.close();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Writer#flush()
	 */
	@Override
	public void flush() throws IOException {
		delegate.flush();
	}

	/**
	 * Returns the target file's path.
	 * 
	 * @return The target file's path.
	 */
	public abstract String getTargetPath();

	/**
	 * This will be used by the visitor to determine indentation of template invocations. It is meant to
	 * return the indentation of the last line that's been written by this implementation.
	 * 
	 * @return Indentation of the last line that's been written by this implementation.
	 */
	public String getCurrentLineIndentation() {
		if (currentIndentation != null) {
			return currentIndentation.toString();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * If you need to flush/reset the delegate writer, this is the place to do so.
	 */
	public void reinit() {
		// empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return delegate.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Writer#write(char[], int, int)
	 */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		write(new String(cbuf), off, len);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Writer#write(int)
	 */
	@Override
	public void write(int c) throws IOException {
		delegate.write(c);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Writer#write(java.lang.String, int, int)
	 */
	@Override
	public void write(String str, int off, int len) throws IOException {
		int newLineIndex = -1;
		if (str.contains(DOS_LINE_SEPARATOR)) {
			newLineIndex = str.lastIndexOf(DOS_LINE_SEPARATOR) + DOS_LINE_SEPARATOR.length();
		} else if (str.contains(UNIX_LINE_SEPARATOR)) {
			newLineIndex = str.lastIndexOf(UNIX_LINE_SEPARATOR) + UNIX_LINE_SEPARATOR.length();
		} else if (str.contains(MAC_LINE_SEPARATOR)) {
			newLineIndex = str.lastIndexOf(MAC_LINE_SEPARATOR) + MAC_LINE_SEPARATOR.length();
		}
		if (newLineIndex != -1) {
			currentIndentation = new StringBuffer();
			recordIndentation = true;
		} else if (currentIndentation == null) {
			currentIndentation = new StringBuffer();
			recordIndentation = true;
			newLineIndex = 0;
		} else if (recordIndentation) {
			newLineIndex = 0;
		}
		if (recordIndentation) {
			for (int i = newLineIndex; i < str.length(); i++) {
				if (Character.isWhitespace(str.charAt(i))) {
					currentIndentation.append(str.charAt(i));
				} else {
					recordIndentation = false;
					break;
				}
			}
		}
		delegate.write(str, off, len);
	}
}
