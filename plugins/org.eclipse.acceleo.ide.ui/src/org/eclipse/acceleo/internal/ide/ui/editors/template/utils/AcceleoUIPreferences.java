/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.utils;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * This will allow for the manipulation of Acceleo preferences in the UI.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class AcceleoUIPreferences {
	/** Preference key for the activation of Acceleo's mark occurrences. */
	public static final String PREFERENCE_KEY_ENABLE_MARK_OCCURRENCES = "org.eclipse.acceleo.ui.mark.occurrences"; //$NON-NLS-1$

	/** Default value for the mark occurrences enablement. */
	private static final boolean DEFAULT_ENABLE_MARK_OCCURRENCES = false;

	/** Preferences scope for the Acceleo ui plugin. */
	private static final IEclipsePreferences PREFERENCES_SCOPE = new InstanceScope()
			.getNode(AcceleoUIActivator.PLUGIN_ID);

	/**
	 * The constructor.
	 */
	private AcceleoUIPreferences() {
		// prevent instantiation
	}

	/**
	 * Switches the mark occurrences to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the mark occurrences, <code>false</code> to disable it.
	 */
	public static void switchMarkOccurrences(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_MARK_OCCURRENCES, state);
	}

	/**
	 * Returns whether the mark occurrences is enabled or not.
	 * 
	 * @return <code>true</code> if the mark occurrences is enabled, <code>false</code> otherwise.
	 */
	public static boolean isMarkOccurrencesEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_MARK_OCCURRENCES,
				DEFAULT_ENABLE_MARK_OCCURRENCES);
	}

}
