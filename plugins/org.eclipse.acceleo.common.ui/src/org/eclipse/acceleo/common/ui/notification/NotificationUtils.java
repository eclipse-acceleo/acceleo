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
package org.eclipse.acceleo.common.ui.notification;

import org.eclipse.acceleo.common.ui.AcceleoCommonUIPlugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.graphics.RGB;

/**
 * Utility class for the notification pop up.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class NotificationUtils {

	/**
	 * The constructor.
	 */
	private NotificationUtils() {
		// Prevent instantiation
	}

	/**
	 * Returns the default preferences.
	 * 
	 * @return The default preferences.
	 */
	public static IEclipsePreferences getDefaultPreferences() {
		IEclipsePreferences preferences = new InstanceScope().getNode(AcceleoCommonUIPlugin.PLUGIN_ID);
		return preferences;
	}

	/**
	 * Sets the width of the notification in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences
	 * @param width
	 *            The width
	 */
	public static void setNotificationWidth(IEclipsePreferences preferences, int width) {
		preferences.putInt(INotificationConstants.WIDTH_KEY, width);
	}

	/**
	 * Returns the width of the notification from the preferences.
	 * 
	 * @param preferences
	 *            The preferences
	 * @return The width of the notification from the preferences.
	 */
	public static int getNotificationWidth(IEclipsePreferences preferences) {
		return preferences.getInt(INotificationConstants.WIDTH_KEY,
				INotificationConstants.DEFAULT_WIDTH_VALUE);
	}

	/**
	 * Sets if the fade out should occurred automatically in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences
	 * @param active
	 *            <code>true</code> if the notification should fade out automatically, <code>false</code>
	 *            otherwise.
	 */
	public static void setNotificationFadeOutAuto(IEclipsePreferences preferences, boolean active) {
		preferences.putBoolean(INotificationConstants.FADE_OUT_AUTO_KEY, active);
	}

	/**
	 * Indicates if the notification should fade out automatically.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return <code>true</code> if the notification should fade out automatically, <code>false</code>
	 *         otherwise.
	 */
	public static boolean getNotificationFadeOutAuto(IEclipsePreferences preferences) {
		return preferences.getBoolean(INotificationConstants.FADE_OUT_AUTO_KEY, true);
	}

	/**
	 * Sets the notification fade out time in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences
	 * @param time
	 *            The fade out time
	 */
	public static void setNotificationFadeOutTimer(IEclipsePreferences preferences, int time) {
		preferences.putInt(INotificationConstants.FADE_OUT_TIMER_KEY, time);
	}

	/**
	 * Returns the notification fade out time from the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return The notification fade out time from the given preferences.
	 */
	public static int getNotificationFadeOutTimer(IEclipsePreferences preferences) {
		return preferences.getInt(INotificationConstants.FADE_OUT_TIMER_KEY,
				INotificationConstants.DEFAULT_FADE_OUT_TIMER);
	}

	/**
	 * Sets the notification border color in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @param color
	 *            The color of the border.
	 */
	public static void setNotificationBorderColor(IEclipsePreferences preferences, RGB color) {
		preferences.put(INotificationConstants.BORDER_COLOR_KEY, StringConverter.asString(color));
	}

	/**
	 * Returns the color of the border of the notification.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return The color of the border of the notification.
	 */
	public static RGB getNotificationBorderColor(IEclipsePreferences preferences) {
		return StringConverter.asRGB(preferences.get(INotificationConstants.BORDER_COLOR_KEY,
				INotificationConstants.DEFAULT_BORDER_COLOR));
	}

	/**
	 * Sets the notification title color in the given preferences.
	 * 
	 * @param preferences
	 *            the preferences.
	 * @param color
	 *            the color of the title of the notification.
	 */
	public static void setNotificationTitleColor(IEclipsePreferences preferences, RGB color) {
		preferences.put(INotificationConstants.TITLE_COLOR_KEY, StringConverter.asString(color));
	}

	/**
	 * Returns the notification title color from the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return The notification title color from the given preferences.
	 */
	public static RGB getNotificationTitleColor(IEclipsePreferences preferences) {
		return StringConverter.asRGB(preferences.get(INotificationConstants.TITLE_COLOR_KEY,
				INotificationConstants.DEFAULT_TITLE_COLOR));
	}

	/**
	 * Sets the notification message Color in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @param color
	 *            The color.
	 */
	public static void setNotificationMessageColor(IEclipsePreferences preferences, RGB color) {
		preferences.put(INotificationConstants.MESSAGE_COLOR_KEY, StringConverter.asString(color));
	}

	/**
	 * Returns the notification message color from the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return The notification message color from the given preferences.
	 */
	public static RGB getNotificationMessageColor(IEclipsePreferences preferences) {
		return StringConverter.asRGB(preferences.get(INotificationConstants.MESSAGE_COLOR_KEY,
				INotificationConstants.DEFAULT_MESSAGE_COLOR));
	}

	/**
	 * Sets the notification background top color in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @param color
	 *            The color.
	 */
	public static void setNotificationBackgroundTopColor(IEclipsePreferences preferences, RGB color) {
		preferences.put(INotificationConstants.BACKGROUND_TOP_COLOR_KEY, StringConverter.asString(color));
	}

	/**
	 * Returns the notification background top color from the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return The notification background top color from the given preferences.
	 */
	public static RGB getNotificationBackgroundTopColor(IEclipsePreferences preferences) {
		return StringConverter.asRGB(preferences.get(INotificationConstants.BACKGROUND_TOP_COLOR_KEY,
				INotificationConstants.DEFAULT_BACKGROUND_TOP_COLOR));
	}

	/**
	 * Sets the notification background bottom color in the given preferences.
	 * 
	 * @param preferences
	 *            The preferences
	 * @param color
	 *            The color.
	 */
	public static void setNotificationBackgroundBottomColor(IEclipsePreferences preferences, RGB color) {
		preferences.put(INotificationConstants.BACKGROUND_BOTTOM_COLOR_KEY, StringConverter.asString(color));
	}

	/**
	 * Returns the notification background bottom color from the given preferences.
	 * 
	 * @param preferences
	 *            The preferences.
	 * @return The notification background bottom color from the given preferences.
	 */
	public static RGB getNotificationBackgroundBottomColor(IEclipsePreferences preferences) {
		return StringConverter.asRGB(preferences.get(INotificationConstants.BACKGROUND_BOTTOM_COLOR_KEY,
				INotificationConstants.DEFAULT_BACKGROUND_BOTTOM_COLOR));
	}
}
