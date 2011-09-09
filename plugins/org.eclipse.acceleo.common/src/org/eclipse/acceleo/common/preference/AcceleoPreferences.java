/*******************************************************************************
 * Copyright (c) 2006, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.preference;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * This will allow for the manipulation of Acceleo preferences.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public final class AcceleoPreferences {
	/** Preference key for the profiler enablement. */
	private static final String PREFERENCE_KEY_ENABLE_PROFILER = "org.eclipse.acceleo.profiler.enable"; //$NON-NLS-1$

	/** Preference key for the traceability enablement. */
	private static final String PREFERENCE_KEY_ENABLE_TRACEABILITY = "org.eclipse.acceleo.traceability.enable"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's query caches. */
	private static final String PREFERENCE_KEY_ENABLE_QUERY_CACHE = "org.eclipse.acceleo.query.cache.enable"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's debug messages. */
	private static final String PREFERENCE_KEY_ENABLE_DEBUG_MESSAGES = "org.eclipse.acceleo.debug.messages.enable"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS = "org.eclipse.acceleo.ui.notification"; //$NON-NLS-1$

	/** Default value for the profiler enablement. */
	private static final boolean DEFAULT_ENABLE_PROFILER = false;

	/** Default value for the traceability enablement. */
	private static final boolean DEFAULT_ENABLE_TRACEABILITY = false;

	/** Default value for the query cache enablement. */
	private static final boolean DEFAULT_ENABLE_QUERY_CACHE = true;

	/** Default value for the debug messages enablement. */
	private static final boolean DEFAULT_ENABLE_DEBUG_MESSAGES = true;

	/** Default value for the notifications enablement. */
	private static final boolean DEFAULT_ENABLE_NOTIFICATIONS = false;

	/** Preferences scope for the Acceleo common plugin. */
	private static final IEclipsePreferences PREFERENCES_SCOPE = new InstanceScope()
			.getNode(AcceleoCommonPlugin.PLUGIN_ID);

	/**
	 * Doesn't need to be instantiated.
	 */
	private AcceleoPreferences() {
		// Hides default constructor
	}

	/**
	 * Switches the traceability enablement to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the traceability, <code>false</code> to disable it.
	 */
	public static void switchTraceability(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_TRACEABILITY, state);
	}

	/**
	 * Returns whether the traceability is enabled or not.
	 * 
	 * @return <code>true</code> if the traceability is enabled, <code>false</code> otherwise.
	 */
	public static boolean isTraceabilityEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_TRACEABILITY, DEFAULT_ENABLE_TRACEABILITY);
	}

	/**
	 * Switches the query cache on and off.
	 * 
	 * @param state
	 *            <code>true</code> to enable the cache, <code>false</code> to disable it.
	 * @since 3.1
	 */
	public static void switchQueryCache(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_QUERY_CACHE, state);
	}

	/**
	 * Returns whether the query cache is enabled or not.
	 * 
	 * @return <code>true</code> if the query cache is enabled, <code>false</code> otherwise.
	 * @since 3.1
	 */
	public static boolean isQueryCacheEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_QUERY_CACHE, DEFAULT_ENABLE_QUERY_CACHE);
	}

	/**
	 * Switches the profiler on and off.
	 * 
	 * @param state
	 *            <code>true</code> to enable the profiler, <code>false</code> to disable it.
	 */
	public static void switchProfiler(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_PROFILER, state);
	}

	/**
	 * Returns whether the profiler is enabled or not.
	 * 
	 * @return <code>true</code> if the profiler is enabled, <code>false</code> otherwise.
	 */
	public static boolean isProfilerEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_PROFILER, DEFAULT_ENABLE_PROFILER);
	}

	/**
	 * Switches the debug messages on and off.
	 * 
	 * @param state
	 *            <code>true</code> to enable the debug messages, <code>false</code> to disable it.
	 * @since 3.1
	 */
	public static void switchDebugMessages(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_DEBUG_MESSAGES, state);
	}

	/**
	 * Returns whether the debug messages are enabled or not.
	 * 
	 * @return <code>true</code> if the debug messages are enabled, <code>false</code> otherwise.
	 * @since 3.1
	 */
	public static boolean isDebugMessagesEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_DEBUG_MESSAGES,
				DEFAULT_ENABLE_DEBUG_MESSAGES);
	}

	/**
	 * Switches the notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS, state);
	}

	/**
	 * Returns whether the notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areNotificationsEnabled() {
		return PREFERENCES_SCOPE
				.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS, DEFAULT_ENABLE_NOTIFICATIONS);
	}
}
