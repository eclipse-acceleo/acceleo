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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * A rule to detect an Acceleo keyword preceded and followed by the given delimiters (The sequences "[for (",
 * ") after ("... for example).
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class KeywordSequenceRule implements ISequenceRule {
	/** Matches any word. */
	private static final String ANY_WORD = "*"; //$NON-NLS-1$

	/** The keyword we need to detect. */
	private String keyword;

	/** The preceding delimiters if not only one. */
	private String[] precedingDelimiters;

	/** The preceding delimiter we need to detect. */
	private String precedingDelimiter;

	/** The following delimiters if not only one. */
	private String[] followingDelimiters;

	/** The following delimiter we need to detect. */
	private String followingDelimiter;

	/** A token to be returned by the rule. */
	private final IToken token;

	/**
	 * Constructor.
	 * 
	 * @param keyword
	 *            The keyword we need to detect.
	 * @param token
	 *            is the token to use for this rule
	 */
	public KeywordSequenceRule(String keyword, IToken token) {
		this.keyword = keyword;
		this.token = token;
	}

	/**
	 * Constructor.
	 * 
	 * @param precedingDelimiter
	 *            The delimiter that needs to precede the keyword. Can be <code>null</code>.
	 * @param keyword
	 *            The keyword we need to detect.
	 * @param followingDelimiter
	 *            The delimiter that needs to follow the keyword. Can be <code>null</code>.
	 * @param token
	 *            is the token to use for this rule
	 */
	public KeywordSequenceRule(String precedingDelimiter, String keyword, String followingDelimiter,
			IToken token) {
		this(keyword, token);
		this.precedingDelimiter = precedingDelimiter;
		this.followingDelimiter = followingDelimiter;
	}

	/**
	 * Constructor.
	 * 
	 * @param precedingDelimiters
	 *            The delimiters that needs to precede the keyword. Can be <code>null</code>.
	 * @param keyword
	 *            The keyword we need to detect.
	 * @param followingDelimiters
	 *            The delimiters that needs to follow the keyword. Can be <code>null</code>.
	 * @param token
	 *            is the token to use for this rule
	 */
	public KeywordSequenceRule(String[] precedingDelimiters, String keyword, String[] followingDelimiters,
			IToken token) {
		this(keyword, token);
		this.precedingDelimiters = precedingDelimiters;
		this.followingDelimiters = followingDelimiters;
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
		boolean result = true;
		int shift = 0;

		// Validate the preceding delimiters
		List<String> actualDelimiters = new ArrayList<String>();
		if (precedingDelimiters != null) {
			for (String preceding : precedingDelimiters) {
				actualDelimiters.add(preceding);
			}
		}
		if (precedingDelimiter != null) {
			actualDelimiters.add(precedingDelimiter);
		}
		Collections.reverse(actualDelimiters);
		for (String delim : actualDelimiters) {
			shift -= unreadWhitespace(scanner);
			if (ANY_WORD.equals(delim)) {
				int c = unreadChar(scanner);
				shift--;
				while (c != ICharacterScanner.EOF && Character.isJavaIdentifierPart(c)
						&& scanner.getColumn() > 0) {
					c = unreadChar(scanner);
					shift--;
				}
			} else {
				for (int i = 0; result && i < delim.length(); i++) {
					shift--;
					int c = unreadChar(scanner);
					int at = delim.charAt(delim.length() - i - 1);
					if (c == ICharacterScanner.EOF || c != at) {
						result = false;
					}
				}
			}
		}
		while (shift < 0) {
			scanner.read();
			shift++;
		}
		if (result) {
			shift += readWhitespace(scanner);
		}

		// Validate the keyword
		for (int i = 0; result && i < keyword.length(); i++) {
			shift++;
			int c = scanner.read();
			int at = keyword.charAt(i);
			if (c == ICharacterScanner.EOF || c != at) {
				result = false;
			}
		}

		// Then validate that our keyword is followed by the needed delimiter
		if (result) {
			actualDelimiters = new ArrayList<String>();
			if (followingDelimiters != null) {
				for (String preceding : followingDelimiters) {
					actualDelimiters.add(preceding);
				}
			}
			if (followingDelimiter != null) {
				actualDelimiters.add(followingDelimiter);
			}

			if (actualDelimiters.size() > 0 && !isWhitespace(actualDelimiters.get(0))) {
				shift += readWhitespace(scanner);
			}

			int followingShift = 0;
			for (String delim : actualDelimiters) {
				if (ANY_WORD.equals(delim)) {
					int c = scanner.read();
					followingShift++;
					while (c != ICharacterScanner.EOF && Character.isJavaIdentifierPart(c)
							&& scanner.getColumn() > 0) {
						c = scanner.read();
						followingShift++;
					}
				} else {
					for (int i = 0; result && i < delim.length(); i++) {
						int c = scanner.read();
						followingShift++;
						int at = delim.charAt(i);
						if (c == ICharacterScanner.EOF || c != at) {
							result = false;
						}
					}
				}
			}
			while (followingShift > 0) {
				scanner.unread();
				followingShift--;
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

	/**
	 * Try to read a whitespace by examining the characters available from the provided character scanner.
	 * 
	 * @param scanner
	 *            is the character scanner to be used by this rule
	 * @return the number of examined characters
	 */
	private int unreadWhitespace(ICharacterScanner scanner) {
		int c = unreadChar(scanner);
		int n = 0;
		while (c != ICharacterScanner.EOF && Character.isWhitespace(c)) {
			n++;
			c = unreadChar(scanner);
		}
		scanner.read();
		return n;
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
	 * This will be used in order to determine whether the given String is composed of whitespaces only.
	 * 
	 * @param string
	 *            String we are to check.
	 * @return <code>true</code> if <code>string</code> contains only whitespace, <code>false</code>
	 *         otherwise.
	 */
	private boolean isWhitespace(String string) {
		for (char c : string.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}
}
