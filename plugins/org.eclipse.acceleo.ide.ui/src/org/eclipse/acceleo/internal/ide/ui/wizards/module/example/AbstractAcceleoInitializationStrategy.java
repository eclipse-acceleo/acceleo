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
package org.eclipse.acceleo.internal.ide.ui.wizards.module.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IFile;

/**
 * To initialize automatically an Acceleo module file from another M2T file, by copying and modifying the text
 * of the example into the new module.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public abstract class AbstractAcceleoInitializationStrategy implements IAcceleoInitializationStrategy {
	/**
	 * Constant to declare a complex pattern variable in the text to read.
	 */
	protected static final String VAR_ALL = "*"; //$NON-NLS-1$

	/**
	 * Constant to declare a simple identifier pattern variable in the text to read. It is also used to get a
	 * variable value in the target text. $1 to get the first variable (stored by * or $), $2 the second... An
	 * identifier matches the following regular expression : [a-zA-Z_\\.]+
	 */
	protected static final String VAR = "$"; //$NON-NLS-1$

	/**
	 * Constant to declare that the text to read is available in a single line.
	 */
	protected static final String END_LINE = "\\n"; //$NON-NLS-1$

	/**
	 * The module element kind.
	 */
	protected String elementKind;

	/**
	 * Indicates if the template has a file block.
	 */
	protected boolean templateHasFileBlock;

	/**
	 * Indicates if the template is main.
	 */
	protected boolean templateIsMain;

	/**
	 * Indicates if the documentation should be generated.
	 */
	protected boolean shouldGenerateDocumentation;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceMetamodelURI()
	 */
	public boolean forceMetamodelURI() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceMetamodelType()
	 */
	public boolean forceMetamodelType() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceHasFile()
	 */
	public boolean forceHasFile() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceHasMain()
	 */
	public boolean forceHasMain() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#configure(java.lang.String,
	 *      boolean, boolean, boolean)
	 */
	public void configure(String moduleElementKind, boolean hasFileBlock, boolean isMain,
			boolean generateDocumentation) {
		this.elementKind = moduleElementKind;
		this.templateHasFileBlock = hasFileBlock;
		this.templateIsMain = isMain;
		this.shouldGenerateDocumentation = generateDocumentation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getContent(org.eclipse.core.resources.IFile,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getContent(IFile exampleFile, String moduleName, List<String> metamodelURI,
			String metamodelFileType) {
		StringBuffer buffer = new StringBuffer(""); //$NON-NLS-1$
		if (exampleFile != null && exampleFile.exists()) {
			StringBuffer text = readExampleContent(exampleFile);
			modifyM2TContent(text, moduleName);
			buffer.append(text);
		}
		return buffer.toString();
	}

	/**
	 * Reads the text of the example file.
	 * 
	 * @param exampleFile
	 *            is the example file
	 * @return the example file text
	 */
	protected StringBuffer readExampleContent(IFile exampleFile) {
		StringBuffer text = FileContent.getFileContent(exampleFile.getLocation().toFile());
		char[] chars = new char[text.length()];
		text.getChars(0, text.length(), chars, 0);
		text = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '[') {
				text.append("['['/]"); //$NON-NLS-1$
			} else if (c == ']') {
				text.append("[']'/]"); //$NON-NLS-1$
			} else {
				text.append(c);
			}
		}
		return text;
	}

	/**
	 * Abstract method to define the migration of the other M2T technology.
	 * 
	 * @param text
	 *            is the initial buffer of the selected example
	 * @param moduleName
	 *            is the module name
	 */
	protected abstract void modifyM2TContent(StringBuffer text, String moduleName);

	/**
	 * Utility method to replace in the given buffer each substring that matches the given initial string with
	 * the given replacement.
	 * 
	 * @param text
	 *            is the buffer to modify
	 * @param initialString
	 *            is the string to search, it cans contain VAR_ALL, VAR_ID, or END_LINE to store specific
	 *            values
	 * @param replacingString
	 *            is the replacement string, it cans contain $1, $2, $? to get the specific values
	 */
	protected void replaceAll(StringBuffer text, String initialString, String replacingString) {
		int offset = 0;
		while (offset > -1) {
			offset = replaceNext(text, initialString, offset, replacingString);
		}
	}

	/**
	 * Utility method to replace in the given buffer the next substring that matches the given initial string
	 * with the given replacement.
	 * 
	 * @param text
	 *            is the buffer to modify
	 * @param initialString
	 *            is the string to search, it cans contain VAR_ALL, VAR_ID, or END_LINE to store specific
	 *            values
	 * @param offset
	 *            is the beginning offset in the text
	 * @param replacingString
	 *            is the replacement string, it cans contain $1, $2, $? to get the specific values
	 * @return the ending index of the next occurrence, or -1 if it doesn't exist
	 */
	protected int replaceNext(StringBuffer text, String initialString, int offset, String replacingString) {
		if (offset == -1) {
			return -1;
		}
		int result = -1;
		String stringToSearch = initialString;
		int replaceStart = -1;
		int replaceEnd = -1;
		int globalSearch = offset;
		boolean endsWithNewLine = stringToSearch.endsWith(END_LINE);
		if (endsWithNewLine) {
			stringToSearch = stringToSearch.substring(0, stringToSearch.length() - END_LINE.length());
		}
		String[] initialStrings = tokenize(stringToSearch, VAR_ALL + VAR, true, true);
		boolean jumpIdentifierOnly = false;
		while (result == -1 && globalSearch > -1 && globalSearch < text.length()) {
			int e = globalSearch;
			List<String> vars = new ArrayList<String>();
			for (int i = 0; i < initialStrings.length && e < text.length(); i++) {
				if (VAR_ALL.equals(initialStrings[i])) {
					jumpIdentifierOnly = false;
				} else if (VAR.equals(initialStrings[i])) {
					jumpIdentifierOnly = true;
				} else {
					int[] pos = indexOf(
							text,
							tokenize(initialStrings[i], " \t\r\n", false, false), e, endsWithNewLine, jumpIdentifierOnly); //$NON-NLS-1$
					if (pos != null) {
						int b = pos[0];
						if (replaceStart == -1) {
							replaceStart = b;
						}
						if (e > globalSearch) {
							vars.add(text.substring(e, b));
						}
						e = pos[1];
						if (i + 1 < initialStrings.length) {
							while (e < text.length() && Character.isWhitespace(text.charAt(e))) {
								e++;
							}
						} else {
							replaceEnd = e;
						}
						if (i + 1 < initialStrings.length && e >= text.length()) {
							globalSearch = -1;
						}
					} else {
						if (replaceStart > -1) {
							globalSearch = replaceStart + 1;
						} else {
							globalSearch = -1;
						}
						replaceStart = -1;
						replaceEnd = -1;
						vars.clear();
						break;
					}
				}
			}
			if (replaceStart > -1 && replaceEnd > -1) {
				String newString = replacingString;
				int i = 1;
				for (Iterator<String> it = vars.iterator(); it.hasNext();) {
					String var = it.next();
					newString = newString.replace(VAR + i, var);
					i++;
				}
				text.replace(replaceStart, replaceEnd, newString);
				result = replaceStart + newString.length();
			}
		}
		return result;
	}

	/**
	 * Constructs the string tokenizer values for the specified string.
	 * 
	 * @param str
	 *            a string to be parsed
	 * @param delim
	 *            the delimiters
	 * @param trim
	 *            indicates if we trim each token
	 * @param returnDelims
	 *            flag indicating whether to return the delimiters as tokens
	 * @return the tokens
	 */
	private String[] tokenize(String str, String delim, boolean trim, boolean returnDelims) {
		StringTokenizer st = new StringTokenizer(str, delim, returnDelims);
		String[] result = new String[st.countTokens()];
		for (int i = 0; i < result.length; i++) {
			if (trim) {
				result[i] = st.nextToken().trim();
			} else {
				result[i] = st.nextToken();
			}
		}
		return result;
	}

	/**
	 * Gets the beginning index and the ending index of the given string sequence, ignoring inner spaces.
	 * 
	 * @param text
	 *            is the buffer
	 * @param sequence
	 *            are the strings to search, it doesn't contain VAR_ALL, VAR_ID, and END_LINE
	 * @param offset
	 *            is the beginning offset in the text
	 * @param endsWithNewLine
	 *            indicates if we stop to search after the first \n character
	 * @param jumpIdentifierOnly
	 *            indicates if we stop to search after a none identifier part character
	 * @return the beginning and the ending index of the next occurrence (return.length == 2), or null if it
	 *         doesn't exist
	 */
	private int[] indexOf(StringBuffer text, String[] sequence, int offset, boolean endsWithNewLine,
			boolean jumpIdentifierOnly) {
		if (offset > -1 && sequence.length > 0) {
			int searchStart = -1;
			int searchEnd = -1;
			int globalSearch = offset;
			while (globalSearch > -1 && globalSearch < text.length()) {
				int e = globalSearch;
				for (int i = 0; i < sequence.length && e < text.length(); i++) {
					String token = sequence[i];
					if (searchStart != -1 && endsWithNewLine && text.charAt(e) == '\n') {
						globalSearch = e + 1;
						searchStart = -1;
						searchEnd = -1;
						break;
					}
					if (token.length() > 0 && e + token.length() <= text.length()
							&& token.equals(text.substring(e, e + token.length()))) {
						int b = e;
						if (searchStart == -1) {
							searchStart = b;
						}
						e = b + token.length();
						if (i + 1 < sequence.length) {
							while (e < text.length() && Character.isWhitespace(text.charAt(e))
									&& !(endsWithNewLine && text.charAt(e) == '\n')) {
								e++;
							}
						} else {
							searchEnd = e;
						}
						if (i + 1 < sequence.length && e >= text.length()) {
							globalSearch = -1;
						}
					} else {
						if (searchStart > -1 && "\"".equals(sequence[0])) { //$NON-NLS-1$
							globalSearch = -1;
						} else if (searchStart > -1) {
							globalSearch = searchStart + 1;
						} else {
							if (jumpIdentifierOnly && !Character.isJavaIdentifierPart(text.charAt(e))
									&& text.charAt(e) != '.') {
								globalSearch = -1;
							} else {
								globalSearch++;
							}
						}
						searchStart = -1;
						searchEnd = -1;
						break;
					}
				}
				if (searchStart > -1 && searchEnd > -1) {
					return new int[] {searchStart, searchEnd };
				}
			}
		}
		return null;
	}
}
