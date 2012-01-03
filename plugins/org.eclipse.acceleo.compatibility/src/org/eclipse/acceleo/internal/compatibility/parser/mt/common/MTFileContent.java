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
package org.eclipse.acceleo.internal.compatibility.parser.mt.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.acceleo.common.IAcceleoConstants;

/**
 * Utilities to read '.mt' files.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class MTFileContent {

	/**
	 * Extension of the concrete syntax file.
	 */
	public static final String MT_FILE_EXTENSION = "mt"; //$NON-NLS-1$

	/**
	 * Encoding specification start marker, used in MT templates to indicate to Acceleo in which encoding the
	 * file should be loaded.
	 */
	private static final String ENCODING_START = "encoding="; //$NON-NLS-1$

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private MTFileContent() {
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
				if (reader != null) {
					reader.close();
				}
				if (input != null) {
					input.close();
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
	private static String getEncoding(StringBuffer buffer) {
		String startMarker = "<%--"; //$NON-NLS-1$
		String endMarker = "--%>"; //$NON-NLS-1$
		String result = doGetEncoding(buffer, startMarker, endMarker);
		if (result == null) {
			startMarker = "[%--"; //$NON-NLS-1$
			endMarker = "--%]"; //$NON-NLS-1$
			result = doGetEncoding(buffer, startMarker, endMarker);
		}
		return result;
	}

	/**
	 * Look for an encoding specification in a buffer using start and end markers.
	 * 
	 * @param buffer
	 *            buffer in which we want to look for an encoding code.
	 * @param startMarker
	 *            start marker of the language.
	 * @param endMarker
	 *            end marker of the language.
	 * @return the found encoding code or null.
	 */
	private static String doGetEncoding(StringBuffer buffer, String startMarker, String endMarker) {
		int start = buffer.indexOf(startMarker + ENCODING_START);
		if (start != -1) {
			int end = buffer.indexOf(endMarker, start);
			if (end != -1) {
				String encoding = buffer.substring(start + (startMarker + ENCODING_START).length(), end);
				return encoding.trim().toUpperCase();
			}
		}
		return null;
	}

}
