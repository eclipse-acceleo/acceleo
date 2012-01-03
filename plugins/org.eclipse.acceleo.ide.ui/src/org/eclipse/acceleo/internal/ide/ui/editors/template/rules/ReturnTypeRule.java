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
 * A rule to detect the return type of a query.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ReturnTypeRule implements ISequenceRule {
	/** A token to be returned by the rule. */
	private final IToken token;

	/**
	 * Constructor.
	 * 
	 * @param token
	 *            is the token to use for this rule
	 */
	public ReturnTypeRule(IToken token) {
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

		// We are at the location of the return type. Everything up till the next white space is the type
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
	 * Validates that we are currently at the location of a query return type. This implies that the previous
	 * words sequence is &lt;)&gt; &lt;:&gt;.
	 * 
	 * @param scanner
	 *            is the scanner
	 * @return true if the previous words are valid
	 */
	private boolean validateLocation(ICharacterScanner scanner) {
		boolean valid = true;
		int shift = 0;

		// Ignore white spaces
		int c;
		do {
			c = unreadChar(scanner);
			shift--;
		} while (c != ICharacterScanner.EOF && Character.isWhitespace(c));

		for (int i = IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.length() - 1; valid && i >= 0; i--) {
			if (c == ICharacterScanner.EOF || c != IAcceleoConstants.VARIABLE_DECLARATION_SEPARATOR.charAt(i)) {
				valid = false;
			} else {
				c = unreadChar(scanner);
				shift--;
			}
		}

		// Ignore white spaces
		do {
			c = unreadChar(scanner);
			shift--;
		} while (c != ICharacterScanner.EOF && Character.isWhitespace(c));

		if (c != ')') {
			valid = false;
		}

		while (shift < 0) {
			scanner.read();
			shift++;
		}
		return valid;
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
