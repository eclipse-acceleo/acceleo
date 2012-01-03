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
package org.eclipse.acceleo.common.ui.internal.notification;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

/**
 * This class will represent a font.
 * 
 * @author <a href="mailto:emil.crumhorn@gmail.com">Emil Crumhorn</a>
 */
public class OneFont {

	/**
	 * Name of the font.
	 */
	private String name;

	/**
	 * Height of the font.
	 */
	private int height;

	/**
	 * Style of the font.
	 */
	private int style;

	/**
	 * The font.
	 */
	private Font font;

	/**
	 * Creates the font with the given name, height and style.
	 * 
	 * @param fontName
	 *            The name of the font.
	 * @param fontHeight
	 *            The height of the font.
	 * @param fontStyle
	 *            The style of the font.
	 */
	public OneFont(String fontName, int fontHeight, int fontStyle) {
		name = fontName;
		height = fontHeight;
		style = fontStyle;
		font = new Font(Display.getDefault(), fontName, fontHeight, fontStyle);
	}

	/**
	 * Creates the font from the given font data.
	 * 
	 * @param fd
	 *            The font data.
	 */
	public OneFont(FontData fd) {
		name = fd.getName();
		height = fd.getHeight();
		style = fd.getStyle();
		font = new Font(Display.getDefault(), fd);
	}

	/**
	 * Returns the name of the font.
	 * 
	 * @return The name of the font.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the height of the font.
	 * 
	 * @return The height of the font.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the style of the font.
	 * 
	 * @return The style of the font.
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * Sets the new font.
	 * 
	 * @param newFont
	 *            The font.
	 */
	public void setFont(Font newFont) {
		font = newFont;
	}

	/**
	 * Returns the font.
	 * 
	 * @return The font.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Indicates if the current font matches the given font data.
	 * 
	 * @param fd
	 *            The font data.
	 * @return <code>true</code> if the font matches the given font data, <code>false</code> otherwise.
	 */
	public boolean matches(FontData fd) {
		return fd.getName().equals(name) && fd.getHeight() == height && fd.getStyle() == style;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Font: " + name + " " + height + " " + style;
	}

}
