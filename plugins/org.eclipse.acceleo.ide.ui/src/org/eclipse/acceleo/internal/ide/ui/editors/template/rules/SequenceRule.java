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

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * Creates a rule for the given starting and ending sequence which, if detected, will return the specified
 * token. It can also return the specified token if the rule breaks on the end of the line, or if the EOF
 * character is read.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class SequenceRule implements ISequenceRule {

	/**
	 * The words of the sequence.
	 */
	protected String[] words;

	/**
	 * The token to be returned on success.
	 */
	protected IToken token;

	/**
	 * Constructor. A default token will be created.
	 * <p>
	 * <code>new Token(IDocument.DEFAULT_CONTENT_TYPE)</code>
	 * 
	 * @param words
	 *            are the words of the sequence
	 */
	public SequenceRule(String[] words) {
		this(words, new Token(IDocument.DEFAULT_CONTENT_TYPE));
	}

	/**
	 * Constructor.
	 * 
	 * @param words
	 *            are the words of the sequence
	 * @param token
	 *            is the token to create
	 */
	public SequenceRule(String[] words, IToken token) {
		this.words = words;
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
	 * @see org.eclipse.acceleo.ide.ui.editors.template.rules.ISequenceRule#read(org.eclipse.jface.text.rules.ICharacterScanner)
	 */
	public int read(ICharacterScanner scanner) {
		if (words == null || words.length == 0) {
			return 0;
		}
		int shift = 0;
		Boolean valid = null;
		for (int i = 0; i < words.length; i++) {
			int n = readWord(words[i], scanner);
			if (n == 0) {
				valid = Boolean.FALSE;
				break;
			}
			shift += n;
			if (i + 1 < words.length) {
				shift += readWhitespace(scanner);
			}
		}
		if (valid != null) {
			while (shift > 0) {
				scanner.unread();
				shift--;
			}
		}
		return shift;
	}

	/**
	 * Try to read the given word by examining the characters available from the provided character scanner.
	 * 
	 * @param word
	 *            is the word to detect
	 * @param scanner
	 *            is the character scanner to be used by this rule
	 * @return the number of examined characters
	 */
	private int readWord(String word, ICharacterScanner scanner) {
		for (int i = 0; i < word.length(); i++) {
			int c = scanner.read();
			boolean stop = c == ICharacterScanner.EOF || c != word.charAt(i);
			stop = stop
					|| (i + 1 == word.length() && Character.isJavaIdentifierPart(word.charAt(i)) && !nextIsNotIdentifierPart(scanner));
			if (stop) {
				int shift = i;
				while (shift >= 0) {
					scanner.unread();
					shift--;
				}
				return 0;
			}
		}
		return word.length();
	}

	/**
	 * Indicates if the character after the candidate isn't an identifier part.
	 * 
	 * @param scanner
	 *            is the scanner
	 * @return true if the character after the candidate isn't an identifier part
	 */
	private boolean nextIsNotIdentifierPart(ICharacterScanner scanner) {
		int c = scanner.read();
		boolean result = (c == ICharacterScanner.EOF) || (!Character.isJavaIdentifierPart(c));
		scanner.unread();
		return result;
	}

	/**
	 * Try to read a whitespace by examining the characters available from the provided character scanner.
	 * 
	 * @param scanner
	 *            is the character scanner to be used by this rule
	 * @return the number of examined characters
	 */
	private int readWhitespace(ICharacterScanner scanner) {
		int c = scanner.read();
		int n = 0;
		while (c != ICharacterScanner.EOF && Character.isWhitespace(c)) {
			n++;
			c = scanner.read();
		}
		scanner.unread();
		return n;
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
