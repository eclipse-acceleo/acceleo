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
 * A rule to detect the name of an override.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class OverrideNameRule implements ISequenceRule {
	/** A token to be returned by the rule. */
	private final IToken token;

	/**
	 * Constructor.
	 * 
	 * @param token
	 *            is the token to use for this rule
	 */
	public OverrideNameRule(IToken token) {
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
		int column = scanner.getColumn();
		if (!validateLocation(scanner)) {
			return 0;
		}
		assert scanner.getColumn() == column;

		// We are after the overrides keyword. Everything up till the next white space is the template name.
		int shift = 0;
		int c = scanner.read();
		shift++;
		while (c != ICharacterScanner.EOF && (c == ':' || Character.isJavaIdentifierPart(c))) {
			c = scanner.read();
			shift++;
		}
		scanner.unread();
		shift--;

		return shift;
	}

	/**
	 * Validates that we are currently on a template/query/macro name, and that this name is preceded by the
	 * sequence &lt;blockType&gt; &lt;visibility&gt;.
	 * 
	 * @param scanner
	 *            is the scanner
	 * @return true if the previous words are valid
	 */
	private boolean validateLocation(ICharacterScanner scanner) {
		boolean valid = true;
		int shift = 0;

		int n = unreadOverrides(scanner);
		shift += n;

		valid = shift < 0;

		while (shift < 0) {
			scanner.read();
			shift++;
		}
		return valid;
	}

	/**
	 * Rewinds the scanner before the previous word if it correspond to the overrides keyword (
	 * {@link IAcceleoConstants#OVERRIDES}).
	 * 
	 * @param scanner
	 *            The current scanner.
	 * @return The number of examined characters (less than 0 if the word matches).
	 */
	private int unreadOverrides(ICharacterScanner scanner) {
		int shift = 0;

		// Ignore white spaces
		int c;
		do {
			c = unreadChar(scanner);
			shift--;
		} while (Character.isWhitespace(c));

		// Unread the preceding word
		String word = ""; //$NON-NLS-1$
		while (c != ICharacterScanner.EOF && Character.isJavaIdentifierPart(c)) {
			word = Character.toString((char)c) + word;
			c = unreadChar(scanner);
			shift--;
		}

		if (!IAcceleoConstants.OVERRIDES.equals(word)) {
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
