/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.view;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.acceleo.ui.interpreter.AcceleoInterpreterPlugin;
import org.eclipse.acceleo.ui.interpreter.internal.IInterpreterConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.PlatformUI;

/**
 * This utility class provides the images for the interpreter plugin.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoInterpreterImages {
	/** The image registry containing <code>Image</code>s and the <code>ImageDescriptor</code>s. */
	private static ImageRegistry imageRegistry;

	/** Base URL for all "icon" images. This will be initialized to this bundle's "icons" folder. */
	private static URL BASE_URL = null;

	static {
		BASE_URL = AcceleoInterpreterPlugin.getDefault().getBundle()
				.getEntry(IInterpreterConstants.INTERPRETER_ICONS);
	}

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoInterpreterImages() {
		// Hides default constructor
	}

	/**
	 * Provides access to the image registry.
	 * 
	 * @return The image registry.
	 */
	public static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			initializeRegistry();
		}
		return imageRegistry;
	}

	/**
	 * Returns the image descriptor associated with the given key in this registry.
	 * 
	 * @param key
	 *            Key of the image descriptor we seek.
	 * @return The image descriptor associated with the given key in this registry.
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		return getImageRegistry().getDescriptor(key);
	}

	/**
	 * Initializes the image registry with all needed images.
	 */
	private static void initializeRegistry() {
		imageRegistry = new ImageRegistry(PlatformUI.getWorkbench().getDisplay());

		createImage(IInterpreterConstants.TOGGLE_VARIABLE_VISIBILITY_ICON,
				IInterpreterConstants.TOGGLE_VARIABLE_VISIBILITY_ICON);
		createImage(IInterpreterConstants.TOGGLE_REALTIME_ICON, IInterpreterConstants.TOGGLE_REALTIME_ICON);
	}

	/**
	 * Creates the given image and puts it in the registry for latter use.
	 * 
	 * @param key
	 *            Key that will allow us to retrieve the image in the registry.
	 * @param path
	 *            Path to the image that is to be put into the registry.
	 */
	private static void createImage(String key, String path) {
		ImageDescriptor image = ImageDescriptor.getMissingImageDescriptor();
		if (BASE_URL == null) {
			// FIXME log this, will have a "missing image" descriptor.
		} else {
			try {
				image = ImageDescriptor.createFromURL(new URL(BASE_URL, path));
			} catch (MalformedURLException e) {
				// FIXME log this, will have a "missing image" descriptor.
			}
		}
		imageRegistry.put(key, image);
	}
}
