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
 * <p>
 * Moved into this package from <code>org.eclipse.jface.internal.text.revisions</code>.
 * </p>
 * Checkstyle needs an author, and since somebody wrote this class...
 * 
 * @author <a href="mailto:some.body@ibm.com">Some Body</a>
 */
public abstract class AbstractSingleCharReader extends Reader {

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Reader#read()
	 */
	@Override
	public abstract int read() throws IOException;

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Reader#read(char[], int, int)
	 */
	// CHECKSTYLE:OFF it comes from the JDT, it works, it's not that dirty so let's not touch it
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int end = off + len;
		for (int i = off; i < end; i++) {
			int ch = read();
			if (ch == -1) {
				if (i == off) {
					return -1;
				}
				return i - off;
			}
			cbuf[i] = (char)ch;
		}
		return len;
	}

	// CHECKSTYLE:ON

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Reader#ready()
	 */
	@Override
	public boolean ready() throws IOException {
		return true;
	}

	/**
	 * Returns the readable content as string.
	 * 
	 * @return the readable content as string
	 * @exception IOException
	 *                in case reading fails
	 */
	public String getString() throws IOException {
		StringBuffer buf = new StringBuffer();
		int ch;
		while ((ch = read()) != -1) {
			buf.append((char)ch);
		}
		return buf.toString();
	}
}
