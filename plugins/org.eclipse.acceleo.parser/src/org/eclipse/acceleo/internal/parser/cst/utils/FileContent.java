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
package org.eclipse.acceleo.internal.parser.cst.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.acceleo.common.IAcceleoConstants;

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
		StringBuffer buffer = getEncodedFileContent(file, null);
		if (file.getName() != null && file.getName().endsWith(IAcceleoConstants.MTL_FILE_EXTENSION)) {
			String encoding = getEncoding(buffer);
			if (encoding != null) {
				buffer = getEncodedFileContent(file, encoding);
			}
		}
		return buffer;
	}

	/**
	 * Return the content of the file using the specified encoding.
	 * 
	 * @param file
	 *            is the file
	 * @param encodingCode
	 *            encodingCode to use in order to read the file
	 * @return the content of the file
	 */
	private static StringBuffer getEncodedFileContent(File file, String encodingCode) {
		StringBuffer buffer = new StringBuffer();
		FileInputStream input = null;
		InputStreamReader reader = null;
		try {
			input = new FileInputStream(file);
			if (encodingCode != null) {
				reader = new InputStreamReader(input, encodingCode);
			} else {
				reader = new InputStreamReader(input);
			}
			int size = 0;
			final int buffLength = 8192;
			char[] buff = new char[buffLength];
			while ((size = reader.read(buff)) >= 0) {
				buffer.append(buff, 0, size);
			}
		} catch (IOException e) {
			// continue and return an empty string
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				// same punition
			}
		}
		return buffer;
	}

	/**
	 * Gets the encoding of the current buffer, or null if the encoding tag doesn't exist.
	 * 
	 * @param buffer
	 *            buffer in which we want to look for an encoding code
	 * @return the found encoding code or null
	 */
	public static String getEncoding(StringBuffer buffer) {
		String result = null;

		Sequence bSequence = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.COMMENT,
				IAcceleoConstants.ENCODING, IAcceleoConstants.VARIABLE_INIT_SEPARATOR);
		result = getEncoding(buffer, bSequence);
		// if we didn't find any encoding in a comment block, we will look for an encoding in a documentation
		// block. Going through the whole file twice to find the encoding may not be the most efficient
		// solution
		if (result == null) {
			bSequence = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.DOCUMENTATION_BEGIN,
					IAcceleoConstants.ENCODING, IAcceleoConstants.VARIABLE_INIT_SEPARATOR);
			result = getEncoding(buffer, bSequence);
		}
		return result;
	}

	/**
	 * Gets the encoding of the current buffer, or null if the encoding tag doesn't exist.
	 * 
	 * @param buffer
	 *            buffer in which we want to look for an encoding code
	 * @param bSequence
	 *            The sequence used to search the encoding (This will determine if we will search the encoding
	 *            in the starting comment block or in the starting documentation block)
	 * @return the found encoding code or null
	 */
	private static String getEncoding(StringBuffer buffer, Sequence bSequence) {
		Region b = bSequence.search(buffer);
		if (b.e() != -1) {
			int bEncoding = b.e();
			while (bEncoding < buffer.length() && Character.isWhitespace(buffer.charAt(bEncoding))) {
				bEncoding++;
			}
			if (bEncoding < buffer.length()
					&& (buffer.charAt(bEncoding) == '-' || Character.isJavaIdentifierStart(buffer
							.charAt(bEncoding)))) {
				int eEncoding = bEncoding + 1;
				while (eEncoding < buffer.length()
						&& (buffer.charAt(eEncoding) == '-' || Character.isJavaIdentifierPart(buffer
								.charAt(eEncoding)))) {
					eEncoding++;
				}
				return buffer.substring(bEncoding, eEncoding).trim();
			}
		}
		return null;
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
		}
		return 0;
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
		}
		return 0;
	}

}
