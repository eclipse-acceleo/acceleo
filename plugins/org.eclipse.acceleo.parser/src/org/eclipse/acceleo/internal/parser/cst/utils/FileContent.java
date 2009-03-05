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
package org.eclipse.acceleo.internal.parser.cst.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utilities to read files.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class FileContent {

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private FileContent() {
		// prevents instantiation
	}

	/**
	 * Returns the content of the file.
	 * 
	 * @param file
	 *            is the file
	 * @return the content of the file
	 */
	public static StringBuffer getFileContent(File file) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			try {
				int size = 0;
				final int buffLength = 512;
				char[] buff = new char[buffLength];
				while ((size = in.read(buff)) >= 0) {
					buffer.append(buff, 0, size);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			// continue and return an empty string
		}
		return buffer;
	}

	/**
	 * Returns the number of the column for the given offset.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param index
	 *            is the offset in the buffer
	 * @return the column
	 */
	public static int columnNumber(final StringBuffer buffer, int index) {
		if (buffer != null && index < buffer.length()) {
			int column = 1;
			for (int i = index - 1; i >= 0; i--) {
				if (buffer.charAt(i) != '\n') {
					column++;
				} else {
					break;
				}
			}
			return column;
		} else {
			return 0;
		}
	}

	/**
	 * Returns the number of the line for the given offset.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param index
	 *            is the offset in the buffer
	 * @return the line
	 */
	public static int lineNumber(final StringBuffer buffer, int index) {
		if (buffer != null && index < buffer.length()) {
			int line = 1;
			for (int i = index - 1; i >= 0; i--) {
				if (buffer.charAt(i) == '\n') {
					line++;
				}
			}
			return line;
		} else {
			return 0;
		}
	}

}
