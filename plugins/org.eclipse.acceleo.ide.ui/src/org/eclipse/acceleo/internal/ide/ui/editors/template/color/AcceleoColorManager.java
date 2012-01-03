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
package org.eclipse.acceleo.internal.ide.ui.editors.template.color;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * This will manage the colors needed by the various parts of the Acceleo UI : syntax highlighting, Overrides
 * view...
 * <p>
 * This class is a singleton which will be cleared when the UI plugin is stopped.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoColorManager {
	/** Keeps track of the colors we created. */
	private static Map<RGB, Color> colors = new HashMap<RGB, Color>(10);

	/** Utility classes don't need to be instantiated. */
	private AcceleoColorManager() {
		// Hides default constructor.
	}

	/**
	 * Disposes the cached colors. This will be called from plugin.stop and shouldn't be called manually.
	 */
	public static void dispose() {
		Iterator<Color> e = colors.values().iterator();
		while (e.hasNext()) {
			e.next().dispose();
		}
		colors.clear();
	}

	/**
	 * Retrieves the SWT Color corresponding to the given Acceleo color constant.
	 * 
	 * @param color
	 *            The color we are to retrieve.
	 * @param preferenceLookupOrder
	 *            Order in which to look for plugin preferences.
	 * @return The SWT Color corresponding to the given Acceleo constant.
	 */
	public static Color getColor(AcceleoColor color, IEclipsePreferences[] preferenceLookupOrder) {
		return getColor(color.getPreferenceKey(), color.getDefault(), preferenceLookupOrder);
	}

	/**
	 * Retrieves the SWT Color corresponding to the given Acceleo color constant.
	 * 
	 * @param color
	 *            The color we are to retrieve.
	 * @return The SWT Color corresponding to the given Acceleo constant.
	 */
	public static Color getColor(AcceleoColor color) {
		return getColor(color, null);
	}

	/**
	 * Returns the Color associated with the given key in Acceleo's preference store.
	 * 
	 * @param key
	 *            Key of the color we seek.
	 * @param defaultValue
	 *            Default value that is to be returned if there is no color associated to this key.
	 * @param preferenceLookupOrder
	 *            Order in which to look for plugin preferences.
	 * @return The retrieved color.
	 */
	private static Color getColor(String key, RGB defaultValue, IEclipsePreferences[] preferenceLookupOrder) {
		RGB rgbValue = getPreference(key, defaultValue, preferenceLookupOrder);
		return getColor(rgbValue);
	}

	/**
	 * Retrieves the specified color preference from the Acceleo preference store.
	 * 
	 * @param key
	 *            Key of the color we need to retrieve.
	 * @param defaultValue
	 *            Default value that is to be returned if there is no color associated to this key.
	 * @param preferenceLookupOrder
	 *            Order in which to look for plugin preferences.
	 * @return The retrieved preference, or the default value if no preference is associated to this key.
	 */
	private static RGB getPreference(String key, RGB defaultValue, IEclipsePreferences[] preferenceLookupOrder) {
		if (key.length() > 0) {
			String rgbValue = AcceleoUIActivator.getPreferenceValue(key, preferenceLookupOrder);
			if (rgbValue != null) {
				return StringConverter.asRGB(rgbValue);
			}
		}
		return defaultValue;
	}

	/**
	 * Gets the color for the given RGB description. It will be created and cached if not already in cache.
	 * 
	 * @param rgb
	 *            RGB description of the color we seek.
	 * @return The created color.
	 */
	private static Color getColor(RGB rgb) {
		Color color = colors.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			colors.put(rgb, color);
		}
		return color;
	}

}
