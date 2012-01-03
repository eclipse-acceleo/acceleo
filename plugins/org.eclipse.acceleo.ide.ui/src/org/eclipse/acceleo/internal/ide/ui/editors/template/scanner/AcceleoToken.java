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
package org.eclipse.acceleo.internal.ide.ui.editors.template.scanner;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;

/**
 * This implementation of a Token will allow the modification of its foreground.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoToken extends Token {
	/** Preference key of this token's foreground. */
	private String colorKey;

	/**
	 * Instantiates our token given its data and the preference key of its color.
	 * 
	 * @param data
	 *            The data attached to the newly created token.
	 * @param colorKey
	 *            Preference key of this token's foreground.
	 */
	public AcceleoToken(TextAttribute data, String colorKey) {
		super(data);
		this.colorKey = colorKey;
	}

	/**
	 * Returns this token foreground color's preference key.
	 * 
	 * @return This token foreground color's preference key.
	 */
	public String getColorKey() {
		return colorKey;
	}

	/**
	 * Update this token's foreground color.
	 * 
	 * @param foreground
	 *            The new foreground color of this token.
	 */
	public void update(Color foreground) {
		TextAttribute oldAttribute = (TextAttribute)getData();
		Color oldColor = oldAttribute.getForeground();
		if (!oldColor.equals(foreground)) {
			TextAttribute newAttribute = new TextAttribute(foreground, oldAttribute.getBackground(),
					oldAttribute.getStyle());
			setData(newAttribute);
		}
	}
}
