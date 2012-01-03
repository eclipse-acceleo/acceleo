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
package org.eclipse.acceleo.internal.ide.ui.editors.template.scanner;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * Determines whether a given character is to be considered whitespace in the current context.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoWhitespaceDetector implements IWhitespaceDetector {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.rules.IWhitespaceDetector#isWhitespace(char)
	 */
	public boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}

}
