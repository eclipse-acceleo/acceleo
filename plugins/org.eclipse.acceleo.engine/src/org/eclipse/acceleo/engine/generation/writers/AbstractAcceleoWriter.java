/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
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
 * Base class of the writers that need be returned by the various generation strategies. Clients can override
 * {@link AcceleoWriterDecorator} instead.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.9
 */
public abstract class AbstractAcceleoWriter extends Writer {
	/** Default size to be used for new buffers. */
	protected static final int DEFAULT_BUFFER_SIZE = 1024;

	/** This will hold the system specific line separator ("\n" for unix, "\r\n" for dos, "\r" for mac, ...). */
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/** The buffer to which all calls will be delegated. */
	protected Writer delegate;

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
		delegate.write(cbuf, off, len);
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
		delegate.write(str, off, len);
	}
}
