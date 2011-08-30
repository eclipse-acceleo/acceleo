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
package org.eclipse.acceleo.common.ui.notification;

import org.eclipse.acceleo.common.ui.internal.notification.ImageCache;
import org.eclipse.swt.graphics.Image;

/**
 * The notification type.
 * 
 * @author <a href="mailto:emil.crumhorn@gmail.com">Emil Crumhorn</a>
 */
public enum NotificationType {
	/**
	 * Error notification type.
	 */
	ERROR(ImageCache.getImage("icons/notification/cancel.png")),

	/**
	 * Warning notification type.
	 */
	WARNING(ImageCache.getImage("icons/notification/warning.png")),

	/**
	 * OK notification type.
	 */
	OK(ImageCache.getImage("icons/notification/accepted.png")),

	/**
	 * Success notification type.
	 */
	SUCCESS(ImageCache.getImage("icons/notification/success.png")),

	/**
	 * Information notification type.
	 */
	INFO(ImageCache.getImage("icons/notification/lightbulb.png")),

	/**
	 * Cancel notification type.
	 */
	CANCEL(ImageCache.getImage("icons/notification/cancel.png"));

	/**
	 * The image used in the notification.
	 */
	private Image image;

	/**
	 * Creates a notification type from the given image.
	 * 
	 * @param img
	 *            The image.
	 */
	private NotificationType(Image img) {
		image = img;
	}

	/**
	 * Returns the image.
	 * 
	 * @return The image.
	 */
	public Image getImage() {
		return image;
	}
}
