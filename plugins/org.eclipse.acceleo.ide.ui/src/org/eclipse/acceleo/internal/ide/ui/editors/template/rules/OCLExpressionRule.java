/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * A rule to detect an OCL expression following a certain kind of delimiter and preceding an ending delimiter.
 * <p>
 * Note: if the chosen "ending delimiter" is a closing parenthesis, this rule will end on an <u>unmatched</u>
 * closing parenthesis.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class OCLExpressionRule implements ISequenceRule {
	/** Words that must be located before this rule for it to match. */
	private final String[] previousWords;

	/** Character that must be located after this rule for it to match. */
	private final char endingDelimiter;

	/** A token to be returned by the rule. */
	private final IToken token;

	/**
	 * Constructor.
	 * 
	 * @param previousWords
	 *            Words that must be located before this rule for it to match.
	 * @param endingDelimiter
	 *            The character that must be located after this rule for it to match.
	 * @param token
	 *            Token this rule is to return.
	 */
	public OCLExpressionRule(String[] previousWords, char endingDelimiter, IToken token) {
		this.previousWords = previousWords;
		this.endingDelimiter = endingDelimiter;
		this.token = token;
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
	 * @see org.eclipse.jface.text.rules.IPredicateRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner,
	 *      boolean)
	 */
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.editors.template.rules.ISequenceRule#read(org.eclipse.jface.text.rules.ICharacterScanner)
	 */
	public int read(ICharacterScanner scanner) {
		int column = scanner.getColumn();
		if (!validatePreviousWords(scanner)) {
			return 0;
		}
		assert scanner.getColumn() == column;

		// Everything until the closing parenthesis is considered to be an expression
		boolean matchParenthesis = endingDelimiter == ')';

		boolean result = true;
		boolean readNext = true;
		int shift = 0;
		int openParenthesis = 0;
		while (result && readNext) {
			int c = scanner.read();
			if (matchParenthesis && c == '(') {
				openParenthesis++;
			} else if (matchParenthesis && c == ')') {
				if (openParenthesis > 0) {
					openParenthesis--;
				} else {
					readNext = false;
				}
			} else if (c == endingDelimiter || c == ']' || c == '\'' || c == ICharacterScanner.EOF) {
				readNext = false;
			}
			if (readNext) {
				shift++;
			} else {
				scanner.unread();
			}
		}

		if (!result) {
			while (shift > 0) {
				scanner.unread();
				shift--;
			}
		}
		return shift;
	}

	/**
	 * Indicates if the previous words are valid.
	 * 
	 * @param scanner
	 *            is the scanner
	 * @return true if the previous words are valid
	 */
	protected boolean validatePreviousWords(ICharacterScanner scanner) {
		boolean valid = true;
		int shift = 0;
		for (int i = previousWords.length - 1; valid && i >= 0; i--) {
			int n = unreadPreviousWord(scanner, previousWords[i]);
			if (n == 0) {
				valid = false;
			} else {
				shift += n;
			}
		}
		while (shift < 0) {
			scanner.read();
			shift++;
		}
		return valid;
	}

	/**
	 * Rewinds the scanner before the previous word.
	 * 
	 * @param scanner
	 *            is the current scanner
	 * @param previousWord
	 *            is the word to find in the previous characters
	 * @return the number of examined characters ( < 0 if the word matches)
	 */
	private int unreadPreviousWord(ICharacterScanner scanner, String previousWord) {
		assert previousWord.length() > 0;
		boolean valid = false;
		int shift = 0;
		if (scanner.getColumn() >= previousWord.length()) {
			int c;
			do {
				c = unreadChar(scanner);
				shift--;
			} while (Character.isWhitespace(c));
			if ("*".equals(previousWord)) { //$NON-NLS-1$
				while (Character.isJavaIdentifierPart(c) && scanner.getColumn() > 0) {
					valid = true;
					c = unreadChar(scanner);
					shift--;
				}
			} else {
				if (scanner.getColumn() >= previousWord.length() - 1) {
					valid = true;
					for (int i = previousWord.length() - 1; valid && i >= 0; i--) {
						if (previousWord.charAt(i) != c) {
							valid = false;
						} else if (i > 0) {
							c = unreadChar(scanner);
							shift--;
						}
					}
				}
			}
		}
		if (!valid) {
			while (shift < 0) {
				scanner.read();
				shift++;
			}
		}
		return shift;
	}

	/**
	 * Rewinds the scanner before the last read character, and returns this character.
	 * 
	 * @param scanner
	 *            is the current scanner
	 * @return the last read character
	 */
	private int unreadChar(ICharacterScanner scanner) {
		scanner.unread();
		int c = scanner.read();
		scanner.unread();
		return c;
	}
}
