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
package org.eclipse.acceleo.internal.ide.ui.editors.template.rules;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * A rule to detect Acceleo keywords.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class KeywordRule implements ISequenceRule {

	/**
	 * The keyword name to detect.
	 */
	private final String keyword;

	/**
	 * Indicates if case is ignored.
	 */
	private boolean isIgnoreCase;

	/**
	 * Indicates if the whole word mode is enabled.
	 */
	private boolean wholeWord;

	/**
	 * A token to be returned by the rule.
	 */
	private final IToken token;

	/**
	 * Constructor.
	 * 
	 * @param keyword
	 *            is the keyword name to detect
	 */
	public KeywordRule(String keyword) {
		this(keyword, false, false, Token.UNDEFINED);
	}

	/**
	 * Constructor.
	 * 
	 * @param keyword
	 *            is the keyword name
	 * @param wholeWord
	 *            indicates if the whole word mode is enabled
	 * @param isIgnoreCase
	 *            indicates if case is ignored
	 * @param token
	 *            is the token to use for this rule
	 */
	public KeywordRule(String keyword, boolean wholeWord, boolean isIgnoreCase, IToken token) {
		this.keyword = keyword;
		this.isIgnoreCase = isIgnoreCase;
		this.token = token;
		this.wholeWord = wholeWord;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.rules.IPredicateRule#getSuccessToken()
	 */
	public IToken getSuccessToken() {
		return token;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner) {
		if (read(scanner) > 0) {
			return token;
		}
		return Token.UNDEFINED;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.rules.ISequenceRule#read(org.eclipse.jface.text.rules.ICharacterScanner)
	 */
	public int read(ICharacterScanner scanner) {
		int result = 0;
		if (keyword.length() > 0) {
			int shift = 0;
			boolean valid = true;
			if (wholeWord && !previousIsNotIdentifierPart(scanner)) {
				valid = false;
			}
			for (int i = 0; valid && i < keyword.length(); i++) {
				shift++;
				int c = scanner.read();
				int at = keyword.charAt(i);
				if (isIgnoreCase) {
					c = Character.toLowerCase(c);
					at = Character.toLowerCase(at);
				}
				if (c == ICharacterScanner.EOF || c != at) {
					valid = false;
				}
			}
			if (valid && (!wholeWord || nextIsNotIdentifierPart(scanner))) {
				result = keyword.length();
			}

			if (result != 0 && IAcceleoConstants.LITERAL_ESCAPE.equals(keyword)) {
				int tmpShift = shift;
				while (shift > 0) {
					scanner.unread();
					shift--;
				}
				// Read the previous character
				scanner.unread();
				int read = scanner.read();
				// Check if "\" is before "\'"
				if (read == '\\') {
					result = 0;
				} else if (shift < tmpShift) {
					while (shift < tmpShift) {
						scanner.read();
						shift++;
					}
				}
			}

			if (result != 0) {
				return result;
			}

			while (shift > 0) {
				scanner.unread();
				shift--;
			}
		}
		return 0;
	}

	/**
	 * Indicates if the character before the candidate isn't an identifier part.
	 * 
	 * @param scanner
	 *            is the scanner
	 * @return true if the character before the candidate isn't an identifier part
	 */
	protected boolean previousIsNotIdentifierPart(ICharacterScanner scanner) {
		if (scanner.getColumn() == 0) {
			return true;
		}
		scanner.unread();
		int c = scanner.read();
		return !Character.isJavaIdentifierPart(c) && c != ':';
	}

	/**
	 * Indicates if the character after the candidate isn't an identifier part.
	 * 
	 * @param scanner
	 *            is the scanner
	 * @return true if the character after the candidate isn't an identifier part
	 */
	protected boolean nextIsNotIdentifierPart(ICharacterScanner scanner) {
		int c = scanner.read();
		boolean result = (c == ICharacterScanner.EOF) || (!Character.isJavaIdentifierPart(c));
		scanner.unread();
		return result && c != ':';
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.rules.IPredicateRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner,
	 *      boolean)
	 */
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

}
