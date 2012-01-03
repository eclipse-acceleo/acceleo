/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.interpreter;


/**
 * This implementation of a source content will allow us to maintain a gap between the text and the CST.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoInterpreterSourceContent extends org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent {
	/**
	 * This is the gap between the text as entered by the user and the text that is actually getting parsed.
	 */
	private int gap;

	/**
	 * Increases visibility of the default constructor.
	 */
	public AcceleoInterpreterSourceContent() {
		// Increases visibility of the default constructor.
	}

	/**
	 * Returns the offset gap between the displayed text and the actual parsed expression.
	 * 
	 * @return The offset gap between the displayed text and the actual parsed expression.
	 */
	public int getGap() {
		return gap;
	}

	/**
	 * Sets the new value of the offset gap.
	 * 
	 * @param newGap
	 *            The new offset gap.
	 */
	public void setGap(int newGap) {
		gap = newGap;
	}
}
