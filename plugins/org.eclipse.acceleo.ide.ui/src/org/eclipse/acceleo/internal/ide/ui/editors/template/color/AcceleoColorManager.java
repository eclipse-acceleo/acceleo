/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * The color manager for the template editor. It is often used for syntax highlighting.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoColorManager {
	/** References the Acceleo UI preference store. */
	private IPreferenceStore preferenceStore = AcceleoUIActivator.getDefault().getPreferenceStore();

	/**
	 * Created colors.
	 */
	private Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

	/**
	 * It disposes color manager.
	 */
	public void dispose() {
		Iterator<Color> e = fColorTable.values().iterator();
		while (e.hasNext()) {
			e.next().dispose();
		}
	}

	/**
	 * Retrieves the SWT Color corresponding to the given Acceleo color constant.
	 * 
	 * @param color
	 *            The color we are to retrieve.
	 * @return The SWT Color corresponding to the given Acceleo constant.
	 */
	public Color getColor(AcceleoColor color) {
		return getColor(color.getPreferenceKey(), color.getDefault());
	}

	/**
	 * Returns the Color associated with the given key in Acceleo's preference store.
	 * 
	 * @param key
	 *            Key of the color we seek.
	 * @param defaultValue
	 *            Default value that is to be returned if there is no color associated to this key.
	 * @return The retrieved color.
	 */
	private Color getColor(String key, RGB defaultValue) {
		RGB rgbValue = getPreference(key, defaultValue);
		return getColor(rgbValue);
	}

	/**
	 * Retrieves the specified color preference from the Acceleo preference store.
	 * 
	 * @param key
	 *            Key of the color we need to retrieve.
	 * @param defaultValue
	 *            Default value that is to be returned if there is no color associated to this key.
	 * @return The retrieved preference, or the default value if no preference is associated to this key.
	 */
	private RGB getPreference(String key, RGB defaultValue) {
		if (key.length() > 0 && preferenceStore.contains(key)) {
			return PreferenceConverter.getColor(preferenceStore, key);
		}
		return defaultValue;
	}

	/**
	 * Gets the color for the given description (Red, Green, Blue).
	 * 
	 * @param rgb
	 *            is the description of color
	 * @return the color
	 */
	private Color getColor(RGB rgb) {
		Color color = fColorTable.get(rgb);
		if (color == null) {
			color = new Color(Display.getCurrent(), rgb);
			fColorTable.put(rgb, color);
		}
		return color;
	}

}
