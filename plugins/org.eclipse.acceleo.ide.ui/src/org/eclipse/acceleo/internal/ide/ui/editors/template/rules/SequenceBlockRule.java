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
public class SequenceBlockRule implements ISequenceRule {

	/**
	 * The pattern's start sequence.
	 */
	protected ISequenceRule begin;

	/**
	 * The pattern's end sequence.
	 */
	protected ISequenceRule end;

	/**
	 * The recursive mode.
	 */
	protected boolean recursive;

	/**
	 * Indicates whether end of line terminates the pattern.
	 */
	protected boolean isEOL;

	/**
	 * Indicates whether end of file terminates the pattern.
	 */
	protected boolean isEOF;

	/**
	 * The pattern's escape character.
	 */
	protected ISequenceRule spec;

	/**
	 * Ignored sub-blocks.
	 */
	protected SequenceBlockRule[] ignoredBlocks;

	/**
	 * The token to be returned on success.
	 */
	protected IToken token;

	/**
	 * Constructor.
	 * 
	 * @param begin
	 *            is the pattern's start sequence
	 * @param end
	 *            is the pattern's end sequence
	 * @param spec
	 *            is the pattern's escape character
	 * @param token
	 *            is the token to be returned on success
	 */
	public SequenceBlockRule(ISequenceRule begin, ISequenceRule end, ISequenceRule spec, IToken token) {
		this.begin = begin;
		this.end = end;
		this.recursive = false;
		this.isEOL = false;
		this.isEOF = false;
		this.spec = spec;
		this.ignoredBlocks = new SequenceBlockRule[] {};
		this.token = token;
	}

	/**
	 * Constructor.
	 * 
	 * @param begin
	 *            is the pattern's start sequence
	 * @param end
	 *            is the pattern's end sequence
	 * @param ignoredBlocks
	 *            are the ignored sub-blocks
	 * @param token
	 *            is the token to be returned on success
	 */
	public SequenceBlockRule(ISequenceRule begin, ISequenceRule end, SequenceBlockRule[] ignoredBlocks,
			IToken token) {
		this.begin = begin;
		this.end = end;
		this.recursive = true;
		this.isEOL = false;
		this.isEOF = false;
		this.spec = null;
		this.ignoredBlocks = ignoredBlocks;
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
		if (read(begin, scanner) == 0) {
			return 0;
		}
		int shift = 0;
		int opened = 0;
		Boolean valid = null;
		while (valid == null) {
			int n = read(spec, scanner);
			if (n == 0) {
				n = read(end, scanner);
				if (n > 0) {
					if (!recursive || opened == 0) {
						valid = Boolean.TRUE;
					} else {
						opened--;
					}
				} else {
					n = read(begin, scanner);
					if (n > 0) {
						opened++;
					} else {
						for (int i = 0; i < ignoredBlocks.length && n == 0; i++) {
							SequenceBlockRule ignoredBlock = ignoredBlocks[i];
							n = ignoredBlock.read(scanner);
						}
					}
					if (n == 0) {
						n = 1;
						int c = scanner.read();
						valid = readEOF(c);
					}
				}
			}
			shift += n;
		}
		if (!valid.booleanValue()) {
			while (shift > 0) {
				scanner.unread();
				shift--;
			}
		}
		return shift;
	}

	/**
	 * Evaluates the rule by examining the characters available from the provided character scanner.
	 * 
	 * @param sequence
	 *            is the current rule
	 * @param scanner
	 *            is the character scanner to be used by this rule
	 * @return the number of examined characters
	 */
	private int read(ISequenceRule sequence, ICharacterScanner scanner) {
		if (sequence != null) {
			return sequence.read(scanner);
		}
		return 0;
	}

	/**
	 * Indicates if the given character validates the end of the rule, when an EOF or an EOL is read. It
	 * depends of 'isEOL' and 'isEOF' values.
	 * 
	 * @param c
	 *            is a character
	 * @return Boolean.TRUE if the token is validated by reading an EOL/EOF, Boolean.FALSE if it isn't valid,
	 *         null if the choice isn't done
	 */
	private Boolean readEOF(int c) {
		Boolean valid = null;
		if (c == '\n') {
			if (isEOL) {
				valid = Boolean.TRUE;
			}
		} else if (c == ICharacterScanner.EOF) {
			if (isEOF) {
				valid = Boolean.TRUE;
			} else {
				valid = Boolean.FALSE;
			}
		}
		return valid;
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
