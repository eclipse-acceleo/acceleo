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

/**
 * The constants used for the notifications.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public interface INotificationConstants {
	/**
	 * The preference key to change the width of the notification pop up. (default = 450px).
	 */
	String WIDTH_KEY = "org.eclipse.acceleo.common.ui.notification.width";

	/**
	 * The default width.
	 */
	int DEFAULT_WIDTH_VALUE = 450;

	/**
	 * The preference key to indicate if the fade out should be done automatically of the notification pop up.
	 * (default = true).
	 */
	String FADE_OUT_AUTO_KEY = "org.eclipse.acceleo.common.ui.notification.fade_out_auto";

	/**
	 * The preference key to change the fade out duration of the notification pop up. (default = 50ms).
	 */
	String FADE_OUT_TIMER_KEY = "org.eclipse.acceleo.common.ui.notification.fade_out_timer";

	/**
	 * The default fade out time.
	 */
	int DEFAULT_FADE_OUT_TIMER = 50;

	/**
	 * The preference key to change the border color of the notification pop up. (default = 40, 73, 97).
	 */
	String BORDER_COLOR_KEY = "org.eclipse.acceleo.common.ui.notification.border_color";

	/**
	 * The default border color.
	 */
	String DEFAULT_BORDER_COLOR = "40,73,97";

	/**
	 * The preference key to change the title color of the notification pop up. (default = 40, 73, 97).
	 */
	String TITLE_COLOR_KEY = "org.eclipse.acceleo.common.ui.notification.title_color";

	/**
	 * The default title color.
	 */
	String DEFAULT_TITLE_COLOR = DEFAULT_BORDER_COLOR;

	/**
	 * The preference key to change the message color of the notification pop up. (default = 40, 73, 97).
	 */
	String MESSAGE_COLOR_KEY = "org.eclipse.acceleo.common.ui.notification.message_color";

	/**
	 * The default message color.
	 */
	String DEFAULT_MESSAGE_COLOR = DEFAULT_BORDER_COLOR;

	/**
	 * The preference key to change the background top gradient color of the notification pop up. (default =
	 * 226, 239, 249).
	 */
	String BACKGROUND_TOP_COLOR_KEY = "org.eclipse.acceleo.common.ui.notification.background_color";

	/**
	 * The default background top color.
	 */
	String DEFAULT_BACKGROUND_TOP_COLOR = "226,239,249";

	/**
	 * The preference key to change the background bottom gradient color of the notification pop up. (default
	 * = 177, 211, 243).
	 */
	String BACKGROUND_BOTTOM_COLOR_KEY = "org.eclipse.acceleo.common.ui.notification.background_color";

	/**
	 * The default background bottom color.
	 */
	String DEFAULT_BACKGROUND_BOTTOM_COLOR = "177,211,243";
}
