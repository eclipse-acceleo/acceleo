/*******************************************************************************
 * Copyright (c) 2009, 2011 Emil Crumhorn.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Emil Crumhorn - initial API and implementation
 *     Stephane Begaudeau - improvements
 *******************************************************************************/
package org.eclipse.acceleo.common.ui.internal.notification;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * The cache of the color.
 * 
 * @author <a href="mailto:emil.crumhorn@gmail.com">Emil Crumhorn</a>
 */
public final class ColorCache {

	/**
	 * Black.
	 */
	public static final RGB BLACK = new RGB(0, 0, 0);

	/**
	 * White.
	 */
	public static final RGB WHITE = new RGB(255, 255, 255);

	/**
	 * Cache of the color.
	 */
	private static Map<RGB, Color> colorTable;

	/**
	 * The sole instance.
	 */
	private static ColorCache instance;

	static {
		colorTable = new HashMap<RGB, Color>();
		new ColorCache();
	}

	/**
	 * The constructor.
	 */
	private ColorCache() {
		instance = this;
	}

	/**
	 * Returns the sole instance.
	 * 
	 * @return The sole instance.
	 */
	public static ColorCache getInstance() {
		return instance;
	}

	/**
	 * Disposes of all colors. DO ONLY CALL THIS WHEN YOU ARE SHUTTING DOWN YOUR APPLICATION!
	 */
	public static void disposeColors() {
		Iterator<Color> e = colorTable.values().iterator();
		while (e.hasNext()) {
			e.next().dispose();
		}

		colorTable.clear();
	}

	/**
	 * Returns the white color.
	 * 
	 * @return The white color.
	 */
	public static Color getWhite() {
		final int max = 255;
		return getColorFromRGB(new RGB(max, max, max));
	}

	/**
	 * Returns the black color.
	 * 
	 * @return The black color.
	 */
	public static Color getBlack() {
		return getColorFromRGB(new RGB(0, 0, 0));
	}

	/**
	 * Returns the color from a given RGB value.
	 * 
	 * @param rgb
	 *            The RGB value.
	 * @return The color from a given RGB value.
	 */
	public static Color getColorFromRGB(RGB rgb) {
		Color color = colorTable.get(rgb);

		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

	/**
	 * Returns the color from a given RGB value.
	 * 
	 * @param r
	 *            The red value.
	 * @param g
	 *            The green value.
	 * @param b
	 *            The blue value.
	 * @return The color from a given RGB value.
	 */
	public static Color getColor(int r, int g, int b) {
		RGB rgb = new RGB(r, g, b);
		Color color = colorTable.get(rgb);

		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colorTable.put(rgb, color);
		}

		return color;
	}

}
