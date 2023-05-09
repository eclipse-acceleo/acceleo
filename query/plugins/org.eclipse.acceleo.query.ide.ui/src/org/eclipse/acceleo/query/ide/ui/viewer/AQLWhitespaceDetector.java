/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.ui.viewer;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

/**
 * AQL {@link IWhitespaceDetector}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLWhitespaceDetector implements IWhitespaceDetector {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.text.rules.IWhitespaceDetector#isWhitespace(char)
	 */
	public boolean isWhitespace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r';
	}
}
