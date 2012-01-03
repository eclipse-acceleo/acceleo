/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.engine;

import java.util.NoSuchElementException;

// TODO this tokenizer doesn't accept delimiters with surrogates
/**
 * This custom implementation of a String Tokenizer will allow access to the start and end offsets of the last
 * returned token. We can't use the basic {@link java.util.StringTokenizer} as it doesn't allow this, and we
 * do not override it because it was designed to be unoverridable.
 * <p>
 * Delimiters are not considered to be tokens themselves.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class TraceabilityTokenizer {
	/** The String we are to tokenize. */
	private final String source;

	/** Delimiters around which to tokenize. */
	private final String delims;

	/** Offset at which we last found a delimiter in {@link #source}. */
	private int lastOffset;

	/** Starting offset of the next token will be computed beforehand. */
	private int nextOffset;

	/**
	 * This will be set to <code>true</code> whenever we've scanned ahead for the next token, yet haven't
	 * returned it yet.
	 */
	private boolean soughtNext;

	/**
	 * Default constructor.
	 * 
	 * @param source
	 *            String that we are to tokenize.
	 * @param delims
	 *            Delimiters around which to tokenize.
	 */
	public TraceabilityTokenizer(String source, String delims) {
		this.source = source;
		this.delims = delims;
	}

	/**
	 * Counts the number of tokens this tokenizer's string contains.
	 * 
	 * @return The number of tokens this tokenizer's string contains.
	 */
	public int countTokens() {
		int tokenOffset = 0;
		int tokenCount = 0;

		do {
			tokenOffset = skipDelimiters(tokenOffset);
			tokenOffset = scanToken(tokenOffset);
			tokenCount++;
		} while (skipDelimiters(tokenOffset) < source.length());

		return tokenCount;
	}

	/**
	 * Returns the offset of the last token's start.
	 * 
	 * @return The offset of the last token's start.
	 */
	public int getLastOffset() {
		return lastOffset;
	}

	/**
	 * Returns the offset of the next token's start.
	 * 
	 * @return The offset of the next token's start.
	 */
	public int getNextOffset() {
		return nextOffset;
	}

	/**
	 * This will return <code>true</code> if there is at least one token after the current.
	 * 
	 * @return <code>true</code> if there is at least one token after the current in {@link #source},
	 *         <code>false</code> otherwise.
	 */
	public boolean hasMoreTokens() {
		if (soughtNext) {
			return true;
		}

		boolean result = true;
		if (skipDelimiters(nextOffset) == source.length()) {
			result = false;
		} else {
			lastOffset = skipDelimiters(nextOffset);
			if (lastOffset == source.length()) {
				result = false;
			}
			nextOffset = scanToken(lastOffset);
			soughtNext = true;
		}

		return result;
	}

	/**
	 * Compute and returns the next token.
	 * 
	 * @return The next token found in this tokenizer's string.
	 * @throws NoSuchElementException
	 *             If there are no more tokens in this tokenizer's string.
	 */
	public String nextToken() throws NoSuchElementException {
		if (!soughtNext) {
			lastOffset = skipDelimiters(nextOffset);
			if (lastOffset == source.length()) {
				throw new NoSuchElementException();
			}
			nextOffset = scanToken(lastOffset);
		} else {
			soughtNext = false;
		}

		return new String(source.substring(lastOffset, nextOffset));
	}

	/**
	 * Skips ahead from <code>start</code> and returns the index of the next delimiter character encountered,
	 * or the tokenizer's string length if it contains no more token.
	 * 
	 * @param start
	 *            Offset from which to start searching.
	 * @return Index of the next delimiter in this tokenizer's string, the string's length if none.
	 */
	private int scanToken(int start) {
		int position = start;
		while (position < source.length()) {
			char c = source.charAt(position);
			if (delims.indexOf(c) >= 0) {
				break;
			}
			position++;
		}
		return position;
	}

	/**
	 * Skips ahead from <code>start</code> and returns the index of the first non-delimiter character
	 * encountered.
	 * 
	 * @param start
	 *            Offset from which to start searching.
	 * @return Index of the next non-delimiter index in this tokenizer's string.
	 */
	private int skipDelimiters(int start) {
		int position = start;
		while (position < source.length()) {
			char c = source.charAt(position);
			if (delims.indexOf(c) < 0) {
				break;
			}
			position++;
		}
		return position;
	}
}
