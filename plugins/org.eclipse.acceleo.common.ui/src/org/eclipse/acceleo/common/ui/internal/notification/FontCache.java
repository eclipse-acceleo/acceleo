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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Caching class for fonts. Also deals with re-creating fonts should they have been disposed when the caller
 * asks for a font.
 * 
 * @author <a href="mailto:emil.crumhorn@gmail.com">Emil Crumhorn</a>
 */
public final class FontCache {

	/**
	 * Existing fonts.
	 */
	private static List<OneFont> existing = new ArrayList<OneFont>();

	/**
	 * The constructor.
	 */
	private FontCache() {
		// Prevent instantiation
	}

	/**
	 * Disposes all fonts and clears out the cache. Never call this unless you are shutting down your
	 * code/client/etc.
	 */
	public static void disposeAll() {
		for (OneFont of : existing) {
			if (of.getFont() != null && !of.getFont().isDisposed()) {
				of.getFont().dispose();
			}
		}

		existing.clear();
	}

	/**
	 * Gets a font from existing FontData.
	 * 
	 * @param fd
	 *            FontData
	 * @return Font or null on error
	 */
	public static Font getFont(FontData fd) {
		boolean disposed = false;
		OneFont toRemove = null;
		for (OneFont of : existing) {
			if (of.matches(fd)) {
				if (of.getFont().isDisposed()) {
					disposed = true;
					toRemove = of;
					break;
				}

				return of.getFont();
			}
		}
		if (disposed) {
			existing.remove(toRemove);
		}

		OneFont of = new OneFont(fd);
		existing.add(of);
		return of.getFont();
	}

	public static int getCount() {
		return existing.size();
	}

	/**
	 * Gets a font from an existing fonts data.
	 * 
	 * @param font
	 *            Font
	 * @return Font or null on error
	 */
	public static Font getFont(Font font) {
		if (font != null && !font.isDisposed()) {
			FontData fd = font.getFontData()[0];
			return getFont(fd);
		}

		return null;
	}

	/**
	 * Gets a font for a given font name and style.
	 * 
	 * @param fontName
	 *            Name of font
	 * @param height
	 *            Height of font
	 * @param style
	 *            Style of font
	 * @return Font or null on error
	 */
	public static Font getFont(String fontName, int height, int style) {
		boolean disposed = false;
		OneFont toRemove = null;
		for (OneFont of : existing) {
			if (of.getName().equals(fontName) && of.getHeight() == height && of.getStyle() == style) {
				if (of.getFont().isDisposed()) {
					disposed = true;
					toRemove = of;
					break;
				}
				return of.getFont();
			}
		}
		if (disposed) {
			existing.remove(toRemove);
		}

		OneFont of = new OneFont(fontName, height, style);
		existing.add(of);
		return of.getFont();
	}

}
