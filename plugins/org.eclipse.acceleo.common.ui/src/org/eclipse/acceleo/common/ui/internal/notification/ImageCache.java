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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.acceleo.common.ui.AcceleoCommonUIPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

/**
 * Class for caching images.
 * 
 * @author <a href="mailto:emil.crumhorn@gmail.com">Emil Crumhorn</a>
 */
public final class ImageCache {

	/**
	 * The cache of the image.
	 */
	private static HashMap<String, Image> imageMap = new HashMap<String, Image>();

	/**
	 * What path to get to the "icons" directory without actually including it.
	 */
	private static final String ICON_ROOT_PATH = "/";

	/**
	 * The constructor.
	 */
	private ImageCache() {
		// Prevent instantiation.
	}

	/**
	 * Returns an image that is also cached if it has to be created and does not already exist in the cache.
	 * 
	 * @param fileName
	 *            Filename of image to fetch
	 * @return Image null if it could not be found
	 */
	public static Image getImage(String fileName) {
		String iconName = ICON_ROOT_PATH + fileName;
		Image image = imageMap.get(iconName);
		if (image == null) {
			image = createImage(iconName);
			imageMap.put(iconName, image);
		}
		return image;
	}

	/**
	 * creates the image, and tries really hard to do so.
	 * 
	 * @param fileName
	 *            The name of the image file.
	 * @return The loaded image.
	 */
	private static Image createImage(String fileName) {
		ClassLoader classLoader = ImageCache.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream(fileName);
		if (is == null) {
			// the old way didn't have leading slash, so if we can't find the image stream,
			// let's see if the old way works.
			is = classLoader.getResourceAsStream(fileName.substring(1));

			if (is == null) {
				is = classLoader.getResourceAsStream(fileName);
				if (is == null) {
					is = classLoader.getResourceAsStream(fileName.substring(1));
				}
			}
		}

		Image img = null;
		if (is != null) {
			img = new Image(Display.getDefault(), is);
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Bundle bundle = Platform.getBundle(AcceleoCommonUIPlugin.PLUGIN_ID);
			URL entry = bundle.getEntry(fileName);
			ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(entry);
			img = imageDescriptor.createImage();
		}

		return img;
	}

	/**
	 * Disposes ALL images that have been cached.
	 */
	public static void dispose() {
		Iterator<Image> e = imageMap.values().iterator();
		while (e.hasNext()) {
			e.next().dispose();
		}
	}
}
