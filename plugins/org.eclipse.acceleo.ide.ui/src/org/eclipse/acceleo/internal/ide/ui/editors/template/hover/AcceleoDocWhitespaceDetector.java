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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * Determines if a character is a whitespace in the documentation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocWhitespaceDetector implements IWhitespaceDetector {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.rules.IWhitespaceDetector#isWhitespace(char)
	 */
	public boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}

}
