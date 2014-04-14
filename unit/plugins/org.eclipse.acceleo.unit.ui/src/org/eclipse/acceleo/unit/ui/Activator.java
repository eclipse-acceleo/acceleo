/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The PLUGIN_ID attribute.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.unit.ui"; //$NON-NLS-1$

	/**
	 * The plugin attribute.
	 */
	private static Activator plugin;

	/**
	 * The image map.
	 */
	private Map<String, Image> imageMap = new HashMap<String, Image>();

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns a local image at the given plug-in relative path.
	 * 
	 * @param path
	 *            is a plug-in relative path
	 * @return the image
	 */
	public Image getImage(String path) {
		Image result = getImage(PLUGIN_ID, path);
		return result;
	}

	/**
	 * Returns an image at the given plug-in relative path.
	 * 
	 * @param pluginId
	 *            the plugin id
	 * @param path
	 *            is a plug-in relative path
	 * @return the image
	 */
	public Image getImage(String pluginId, String path) {
		Image image = imageMap.get(pluginId + "#" + path); //$NON-NLS-1$
		if (image == null) {
			ImageDescriptor imageDescriptor = imageDescriptorFromPlugin(pluginId, path);
			if (imageDescriptor != null) {
				image = imageDescriptor.createImage();
				imageMap.put(pluginId + "#" + path, image); //$NON-NLS-1$
			}
		}
		return image;
	}
}
