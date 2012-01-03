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

import org.eclipse.acceleo.common.IAcceleoConstants;

/**
 * Common utilities to parse sequences.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class ParserUtils {

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private ParserUtils() {
		// prevents instantiation
	}

	/**
	 * Creates a Acceleo block analyzer. It uses the Acceleo delimiters '[' and ']'. <li>header only : [name
	 * ... header ... /]</li> <li>header + body : [name ... header ... ] ... body ... [/name]</li>
	 * 
	 * @param headerOnly
	 *            indicates if it parses the header only, the body is ignored
	 * @param name
	 *            is the name of the block
	 * @param inhibsHeader
	 *            are the ignored sequences on the header part
	 * @param inhibsBody
	 *            are the ignored sequences on the body part
	 * @return the Acceleo block analyzer
	 */
	public static SequenceBlock createAcceleoSequenceBlock(boolean headerOnly, String name,
			SequenceBlock[] inhibsHeader, SequenceBlock[] inhibsBody) {
		Sequence beginHeader = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, name);
		Sequence endHeaderOnly = new Sequence(IAcceleoConstants.DEFAULT_END);
		Sequence endHeaderBody = new Sequence(IAcceleoConstants.DEFAULT_END_BODY_CHAR,
				IAcceleoConstants.DEFAULT_END);
		Sequence endBody;
		if (headerOnly) {
			endBody = null;
		} else {
			endBody = new Sequence(IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.DEFAULT_END_BODY_CHAR,
					name, IAcceleoConstants.DEFAULT_END);
		}
		return new SequenceBlock(beginHeader, endHeaderOnly, endHeaderBody, inhibsHeader, endBody, null,
				inhibsBody);
	}

	/**
	 * To find the nearest sequence ('pElements') in the buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param pElements
	 *            are the candidates to search
	 * @param pElementsPositions
	 *            are the previous values computed for the candidates, it is modified in this method
	 * @return the index in the tables 'pElements' and in 'pElementsPositions' of the next element
	 */
	public static int getNextSequence(StringBuffer buffer, int posBegin, int posEnd, ISequence[] pElements,
			Region[] pElementsPositions) {
		assert pElements.length == pElementsPositions.length;
		for (int i = 0; i < pElements.length; i++) {
			if (pElementsPositions[i].b() != -1 && posBegin > pElementsPositions[i].b()) {
				ISequence pModuleElement = pElements[i];
				pElementsPositions[i] = pModuleElement.search(buffer, posBegin, posEnd);
			}
		}
		int positionMin = posEnd;
		int iPositionMin = -1;
		for (int i = 0; i < pElementsPositions.length; i++) {
			if ((pElementsPositions[i].b() > -1) && (pElementsPositions[i].b() < positionMin)) {
				iPositionMin = i;
				positionMin = pElementsPositions[i].b();
			}
		}
		if (iPositionMin > -1 && iPositionMin < pElements.length) {
			return iPositionMin;
		}
		return -1;
	}

	/**
	 * To find the nearest sequence ('pElements') in the buffer, and some blocks ('pInhibs') are ignored.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param pElements
	 *            are the candidates to search
	 * @param pElementsPositions
	 *            are the previous values computed for the candidates, it is modified in this method
	 * @param pInhibs
	 *            are the blocks to ignore
	 * @param pInhibsPositions
	 *            are the previous values computed for the ignored blocks, it is modified in this method
	 * @return the index in the tables 'pElements' and in 'pElementsPositions' of the next element
	 */
	public static int getNextSequence(StringBuffer buffer, int posBegin, int posEnd, ISequence[] pElements,
			Region[] pElementsPositions, SequenceBlock[] pInhibs, Region[] pInhibsPositions) {
		int currentPosBegin = posBegin;
		while (currentPosBegin > -1 && currentPosBegin < posEnd) {
			int i = getNextSequence(buffer, currentPosBegin, posEnd, pElements, pElementsPositions);
			int j = getNextSequence(buffer, currentPosBegin, posEnd, pInhibs, pInhibsPositions);
			if (j == -1 || i == -1 || pElementsPositions[i].b() < pInhibsPositions[j].b()) {
				return i;
			}
			SequenceBlock pInhib = pInhibs[j];
			currentPosBegin = pInhib.searchEndBodyAtBeginHeader(buffer, pInhibsPositions[j], posEnd).e();
		}
		return -1;
	}

	/**
	 * Reads eventually the following text if the given keyword matches. Spaces are ignored.
	 * 
	 * @param buffer
	 *            is the buffer to parse
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param keyword
	 *            is the keyword to read in the text at the current index
	 * @param wholeWord
	 *            indicates if the search mode is 'whole word'
	 * @return the ending index of the keyword, or the beginning index if it doesn't exist
	 */
	public static int shiftKeyword(StringBuffer buffer, int posBegin, int posEnd, String keyword,
			boolean wholeWord) {
		return shiftKeyword(buffer, posBegin, posEnd, keyword, false, wholeWord);
	}

	/**
	 * Reads eventually the following text if the given keyword matches. Spaces are ignored.
	 * 
	 * @param buffer
	 *            is the buffer to parse
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param keyword
	 *            is the keyword to read in the text at the current index
	 * @param ignoreCase
	 *            indicates if the search mode is 'ignore case'
	 * @param wholeWord
	 *            indicates if the search mode is 'whole word'
	 * @return the ending index of the keyword, or the beginning index if it doesn't exist
	 */
	public static int shiftKeyword(StringBuffer buffer, int posBegin, int posEnd, String keyword,
			boolean ignoreCase, boolean wholeWord) {
		if (keyword == null || keyword.length() == 0) {
			return posBegin;
		}
		int b = posBegin;
		while (b < posEnd && Character.isWhitespace(buffer.charAt(b))) {
			b++;
		}
		int result;
		if (b + keyword.length() <= posEnd) {
			int e = strictlyReadKeyword(buffer, b, keyword, ignoreCase);
			if ((b != e) && (e == posEnd || !wholeWord || !Character.isJavaIdentifierStart(buffer.charAt(e)))) {
				result = e;
			} else {
				result = posBegin;
			}
		} else {
			result = posBegin;
		}
		return result;
	}

	/**
	 * Reads strictly the following text if the given keyword matches.
	 * 
	 * @param buffer
	 *            is the buffer to parse
	 * @param posBegin
	 *            is the beginning index
	 * @param keyword
	 *            is the keyword to read in the text at the current index
	 * @param ignoreCase
	 *            indicates if the search mode is 'ignore case'
	 * @return the ending index of the keyword, or the beginning index if it doesn't exist
	 */
	private static int strictlyReadKeyword(StringBuffer buffer, int posBegin, String keyword,
			boolean ignoreCase) {
		int e = posBegin;
		for (int i = 0; i < keyword.length(); i++) {
			char keywordChar = keyword.charAt(i);
			char bufferChar = buffer.charAt(e);
			if (!similarCharacters(keywordChar, bufferChar, ignoreCase)) {
				return posBegin;
			}
			e++;
		}
		return e;
	}

	/**
	 * Indicates if the characters are similar.
	 * 
	 * @param c1
	 *            is the first character
	 * @param c2
	 *            is the second character
	 * @param ignoreCase
	 *            indicates if the case is ignored
	 * @return true if the characters are similar
	 */
	private static boolean similarCharacters(char c1, char c2, boolean ignoreCase) {
		if (ignoreCase) {
			return Character.toUpperCase(c1) == Character.toUpperCase(c2);
		}
		return c1 == c2;
	}

	/**
	 * Reads eventually the following identifier in the text.
	 * 
	 * @param buffer
	 *            is the buffer to parse
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the ending index of the identifier, or 'posBegin' if the following text isn't an identifier
	 */
	public static int shiftIdentifier(StringBuffer buffer, int posBegin, int posEnd) {
		int b = posBegin;
		while (b < posEnd && Character.isWhitespace(buffer.charAt(b))) {
			b++;
		}
		if (b < posEnd && Character.isJavaIdentifierStart(buffer.charAt(b))) {
			int e = b + 1;
			while (e < posEnd && Character.isJavaIdentifierPart(buffer.charAt(e))) {
				e++;
			}
			return e;
		}
		return posBegin;
	}

	/**
	 * Indicates if the given text is a valid identifier.
	 * 
	 * @param text
	 *            is the text to test
	 * @return true if the given text is a valid identifier
	 */
	public static boolean isIdentifier(String text) {
		if (text != null && text.length() > 0 && Character.isJavaIdentifierStart(text.charAt(0))) {
			int i = 1;
			while (i < text.length() && Character.isJavaIdentifierPart(text.charAt(i))) {
				i++;
			}
			return i == text.length();
		}
		return false;
	}

}
