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
package org.eclipse.acceleo.common.preference;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

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

	/** Preference key for the forced deactivation of Acceleo's notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_FORCED_DEACTIVATION = "org.eclipse.acceleo.ui.notification.forced.deactivation"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's error notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_ERROR = "org.eclipse.acceleo.ui.notification.error"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's warning notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_WARNING = "org.eclipse.acceleo.ui.notification.warning"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's ok notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_OK = "org.eclipse.acceleo.ui.notification.ok"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's success notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_SUCCESS = "org.eclipse.acceleo.ui.notification.success"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's info notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_INFO = "org.eclipse.acceleo.ui.notification.info"; //$NON-NLS-1$

	/** Preference key for the activation of Acceleo's cancel notifications. */
	private static final String PREFERENCE_KEY_ENABLE_NOTIFICATIONS_CANCEL = "org.eclipse.acceleo.ui.notification.cancel"; //$NON-NLS-1$

	/** Default value for the profiler enablement. */
	private static final boolean DEFAULT_ENABLE_PROFILER = false;

	/** Default value for the traceability enablement. */
	private static final boolean DEFAULT_ENABLE_TRACEABILITY = false;

	/** Default value for the query cache enablement. */
	private static final boolean DEFAULT_ENABLE_QUERY_CACHE = true;

	/** Default value for the debug messages enablement. */
	private static final boolean DEFAULT_ENABLE_DEBUG_MESSAGES = true;

	/** Default value for the notifications enablement. */
	private static final boolean DEFAULT_ENABLE_NOTIFICATIONS = true;

	/** Default value for the notifications type enablement. */
	private static final boolean DEFAULT_ENABLE_NOTIFICATIONS_TYPE = true;

	/** Default value for the force disablement of the notifications. */
	private static final boolean DEFAULT_FORCE_DISABLE_NOTIFICATIONS = false;

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
	 * This will be called at plugin stopping in order to flush the preference scope.
	 * 
	 * @since 3.2
	 */
	public static void save() {
		try {
			PREFERENCES_SCOPE.flush();
		} catch (BackingStoreException e) {
			AcceleoCommonPlugin.log(e, true);
		}
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

	/**
	 * Switches the forced deactivation of the notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the forced deactivation of the notifications, <code>false</code>
	 *            to disable it.
	 * @since 3.2
	 */
	public static void switchForceDeactivationNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_FORCED_DEACTIVATION, state);
	}

	/**
	 * Returns whether the notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areNotificationsForcedDisabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_FORCED_DEACTIVATION,
				DEFAULT_FORCE_DISABLE_NOTIFICATIONS);
	}

	/**
	 * Switches the error notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the error notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchErrorNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_ERROR, state);
	}

	/**
	 * Returns whether the error notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the error notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areErrorNotificationsEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_ERROR,
				DEFAULT_ENABLE_NOTIFICATIONS_TYPE);
	}

	/**
	 * Switches the warning notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the warning notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchWarningNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_WARNING, state);
	}

	/**
	 * Returns whether the warning notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the warning notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areWarningNotificationsEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_WARNING,
				DEFAULT_ENABLE_NOTIFICATIONS_TYPE);
	}

	/**
	 * Switches the ok notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the ok notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchOKNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_OK, state);
	}

	/**
	 * Returns whether the ok notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the ok notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areOKNotificationsEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_OK,
				DEFAULT_ENABLE_NOTIFICATIONS_TYPE);
	}

	/**
	 * Switches the success notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the success notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchSuccessNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_SUCCESS, state);
	}

	/**
	 * Returns whether the success notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the success notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areSuccessNotificationsEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_SUCCESS,
				DEFAULT_ENABLE_NOTIFICATIONS_TYPE);
	}

	/**
	 * Switches the info notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the info notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchInfoNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_INFO, state);
	}

	/**
	 * Returns whether the info notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the info notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areInfoNotificationsEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_INFO,
				DEFAULT_ENABLE_NOTIFICATIONS_TYPE);
	}

	/**
	 * Switches the cancel notifications to <code>state</code>.
	 * 
	 * @param state
	 *            <code>true</code> to enable the cancel notifications, <code>false</code> to disable it.
	 * @since 3.2
	 */
	public static void switchCancelNotifications(boolean state) {
		PREFERENCES_SCOPE.putBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_CANCEL, state);
	}

	/**
	 * Returns whether the cancel notifications are enabled or not.
	 * 
	 * @return <code>true</code> if the cancel notifications are enabled, <code>false</code> otherwise.
	 * @since 3.2
	 */
	public static boolean areCancelNotificationsEnabled() {
		return PREFERENCES_SCOPE.getBoolean(PREFERENCE_KEY_ENABLE_NOTIFICATIONS_CANCEL,
				DEFAULT_ENABLE_NOTIFICATIONS_TYPE);
	}

	/**
	 * Returns the line delimiter of the Eclipse platform.
	 * 
	 * @return The line delimiter used by the Eclipse platform.
	 * @since 3.3
	 */
	public static String getLineSeparator() {
		IScopeContext scope = new InstanceScope();
		IEclipsePreferences node = scope.getNode(Platform.PI_RUNTIME);
		return node.get(Platform.PREF_LINE_SEPARATOR, System.getProperty("line.separator")); //$NON-NLS-1$
	}
}
