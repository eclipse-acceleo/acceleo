/*******************************************************************************
 *  Copyright (c) 2023, 2025 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class AcceleoUIPlugin extends AbstractUIPlugin {

	/**
	 * The delete image key.
	 */
	public static final String DELETE_IMG_KEY = "delete";

	/**
	 * The add image key.
	 */
	public static final String ADD_IMG_KEY = "add";

	/**
	 * The Acceleo editor key.
	 */
	public static final String ACCELEO_EDITOR = "AcceleoEditor";

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.aql.ide.ui"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static AcceleoUIPlugin plugin;

	/**
	 * The {@link EclipseAcceleoLanguageServerContext}.
	 */
	private EclipseAcceleoLanguageServerContext serviceContext;

	/**
	 * The constructor.
	 */
	public AcceleoUIPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		serviceContext = new EclipseAcceleoLanguageServerContext(ResourcesPlugin.getWorkspace());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		serviceContext.dispose();
		serviceContext = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Gets the {@link EclipseAcceleoLanguageServerContext}.
	 * 
	 * @return the {@link EclipseAcceleoLanguageServerContext}
	 */
	public EclipseAcceleoLanguageServerContext getServiceContext() {
		return serviceContext;
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static AcceleoUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path.
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		try {
			reg.put(ADD_IMG_KEY, ImageDescriptor.createFromURL(new URL("platform:/plugin/" + PLUGIN_ID
					+ "/icons/add.gif")));
			reg.put(DELETE_IMG_KEY, ImageDescriptor.createFromURL(new URL("platform:/plugin/" + PLUGIN_ID
					+ "/icons/delete.gif")));
			reg.put(ACCELEO_EDITOR, ImageDescriptor.createFromURL(new URL("platform:/plugin/" + PLUGIN_ID
					+ "/icons/AcceleoEditor.gif")));
		} catch (MalformedURLException e) {
			getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		}
	}
}
