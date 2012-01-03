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
package org.eclipse.acceleo.ide.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.acceleo.internal.ide.ui.notifications.AcceleoLogListener;
import org.eclipse.acceleo.internal.traceability.AcceleoTraceabilityPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoUIActivator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.ide.ui"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	private static AcceleoUIActivator plugin;

	/**
	 * The log listener.
	 */
	private AcceleoLogListener listener = new AcceleoLogListener();

	/**
	 * The images.
	 */
	private Map<String, Image> imageMap = new HashMap<String, Image>();

	/**
	 * The constructor.
	 */
	public AcceleoUIActivator() {
		// Default constructor
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		AcceleoCommonPlugin.getDefault().getLog().addLogListener(listener);
		AcceleoEnginePlugin.getDefault().getLog().addLogListener(listener);
		AcceleoUIActivator.getDefault().getLog().addLogListener(listener);
		AcceleoTraceabilityPlugin.getDefault().getLog().addLogListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		AcceleoCommonPlugin.getDefault().getLog().removeLogListener(listener);
		AcceleoEnginePlugin.getDefault().getLog().removeLogListener(listener);
		AcceleoUIActivator.getDefault().getLog().removeLogListener(listener);
		AcceleoTraceabilityPlugin.getDefault().getLog().removeLogListener(listener);

		plugin = null;

		Iterator<Image> imageIterator = imageMap.values().iterator();
		while (imageIterator.hasNext()) {
			Image image = imageIterator.next();
			image.dispose();
		}
		imageMap.clear();
		AcceleoColorManager.dispose();
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static AcceleoUIActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns the value of the given preference in the Acceleo UI.
	 * 
	 * @param key
	 *            Key of the preference we seek to retrieve.
	 * @return The value of the given preference.
	 * @since 3.1
	 */
	public static String getPreferenceValue(String key) {
		IEclipsePreferences defaultScope = new DefaultScope().getNode(PLUGIN_ID);
		IEclipsePreferences instanceScope = new InstanceScope().getNode(PLUGIN_ID);
		IEclipsePreferences[] lookupOrder = new IEclipsePreferences[] {instanceScope, defaultScope, };

		return getPreferenceValue(key, lookupOrder);
	}

	/**
	 * Returns the value of the given preference in the Acceleo UI.
	 * 
	 * @param key
	 *            Key of the preference we seek to retrieve.
	 * @param lookupOrder
	 *            Order in which to look for preferences.
	 * @return The value of the given preference.
	 * @since 3.1
	 */
	public static String getPreferenceValue(String key, IEclipsePreferences[] lookupOrder) {
		final String preferenceValue;
		if (lookupOrder != null && lookupOrder.length > 0) {
			preferenceValue = Platform.getPreferencesService().get(key, null, lookupOrder);
		} else {
			preferenceValue = getPreferenceValue(key);
		}

		return preferenceValue;
	}

	/**
	 * Returns an image at the given plug-in relative path.
	 * 
	 * @param path
	 *            is a plug-in relative path
	 * @return the image
	 */
	public Image getImage(String path) {
		Image result = imageMap.get(path);
		if (result == null) {
			ImageDescriptor descriptor = getImageDescriptor(path);
			if (descriptor != null) {
				result = descriptor.createImage();
				imageMap.put(path, result);
			}
		}
		return result;
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

	/**
	 * Logs the given exception as error or warning.
	 * 
	 * @param exception
	 *            The exception to log.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 * @since 3.0
	 */
	public static void log(Exception exception, boolean blocker) {
		int severity = IStatus.WARNING;
		if (blocker) {
			severity = IStatus.ERROR;
		}
		ILog log = getDefault().getLog();
		log.log(new Status(severity, PLUGIN_ID, exception.getMessage(), exception));
	}

	/**
	 * Puts the given status in the error log view.
	 * 
	 * @param status
	 *            Error Status.
	 * @since 3.1
	 */
	public static void log(IStatus status) {
		// Eclipse platform displays NullPointer on standard error instead of throwing it.
		// We'll handle this by throwing it ourselves.
		if (status == null) {
			throw new NullPointerException(AcceleoEngineMessages
					.getString("AcceleoUIActivator.LogNullStatus")); //$NON-NLS-1$
		}

		if (getDefault() != null) {
			getDefault().getLog().log(status);
		} else {
			// We are out of eclipse. Prints the message on standard error.
			// CHECKSTYLE:OFF
			System.err.println(status.getMessage());
			status.getException().printStackTrace();
			// CHECKSTYLE:ON
		}
	}

	/**
	 * Puts the given message in the error log view, as error or warning.
	 * 
	 * @param message
	 *            The message to put in the error log view.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 * @since 3.1
	 */
	public static void log(String message, boolean blocker) {
		if (getDefault() == null) {
			// We are out of eclipse. Prints the message on standard error.
			// CHECKSTYLE:OFF
			System.err.println(message);
			// CHECKSTYLE:ON
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			String errorMessage = message;
			if (errorMessage == null || "".equals(errorMessage)) { //$NON-NLS-1$
				errorMessage = AcceleoEngineMessages.getString("AcceleoUIActivator.UnexpectedException"); //$NON-NLS-1$
			}
			log(new Status(severity, PLUGIN_ID, errorMessage));
		}
	}
}
