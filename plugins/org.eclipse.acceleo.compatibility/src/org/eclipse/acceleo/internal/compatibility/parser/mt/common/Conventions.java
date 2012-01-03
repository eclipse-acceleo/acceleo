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

/**
 * Conventions to format text.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class Conventions {

	/**
	 * Private Constructor. Utility class should not have default or public constructor.
	 */
	private Conventions() {
		// Hides constructor
	}

	/**
	 * Transforms a multiline text into a literal.
	 * 
	 * @param value
	 *            is the multiline text
	 * @return the literal (single line)
	 */
	public static String formatString(String value) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
				case '\t':
					result.append("\\t"); //$NON-NLS-1$
					break;
				case '\n':
					result.append("\\n"); //$NON-NLS-1$
					break;
				case '\r':
					result.append("\\r"); //$NON-NLS-1$
					break;
				case '\"':
					result.append("\\\""); //$NON-NLS-1$
					break;
				case '\\':
					result.append("\\\\"); //$NON-NLS-1$
					break;
				default:
					result.append(c);
			}
		}
		return result.toString();
	}

	/**
	 * Transforms a literal into a multiline text.
	 * 
	 * @param value
	 *            is the literal (single line)
	 * @return the multiline text
	 */
	public static String unformatString(String value) {
		StringBuffer result = new StringBuffer();
		int i = 0;
		while (i < value.length()) {
			char c = value.charAt(i);
			if (c == '\\' && i + 1 < value.length()) {
				++i;
				c = value.charAt(i);
				switch (c) {
					case 't':
						result.append('\t');
						break;
					case 'n':
						result.append('\n');
						break;
					case 'r':
						result.append('\r');
						break;
					case '"':
						result.append('"');
						break;
					case '\\':
						result.append('\\');
						break;
					default:
						result.append('\\');
						result.append(c);
				}
			} else {
				result.append(c);
			}
			++i;
		}
		return result.toString();
	}

}
