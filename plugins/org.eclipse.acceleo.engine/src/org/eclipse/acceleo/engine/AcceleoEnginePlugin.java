/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.engine.internal.utils.AcceleoDynamicTemplatesEclipseUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnginePlugin extends Plugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.engine"; //$NON-NLS-1$

	/** Name of the extension point's templates tag "path" atribute. */
	private static final String DYNAMIC_TEMPLATES_ATTRIBUTE_PATH = "path"; //$NON-NLS-1$

	/** Name of the extension point to parse for template locations. */
	private static final String DYNAMIC_TEMPLATES_EXTENSION_POINT = "org.eclipse.acceleo.engine.dynamic.templates"; //$NON-NLS-1$

	/** Name of the extension point's "templates" tag. */
	private static final String DYNAMIC_TEMPLATES_TAG_TEMPLATES = "templates"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static AcceleoEnginePlugin plugin;

	/** The registry listener that will be used to listen to dynamic templates changes. */
	private final DynamicTemplatesRegistryListener dynamicTemplatesListener = new DynamicTemplatesRegistryListener();

	/**
	 * Default constructor for the plugin.
	 */
	public AcceleoEnginePlugin() {
		plugin = this;
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static AcceleoEnginePlugin getDefault() {
		return plugin;
	}

	/**
	 * Trace an Exception in the error log.
	 * 
	 * @param e
	 *            Exception to log.
	 * @param blocker
	 *            <code>True</code> if the exception must be logged as error, <code>False</code> to log it as
	 *            a warning.
	 */
	public static void log(Exception e, boolean blocker) {
		if (e == null) {
			throw new NullPointerException(AcceleoEngineMessages
					.getString("AcceleoEnginePlugin.LogNullException")); //$NON-NLS-1$
		}

		if (getDefault() == null) {
			// We are out of eclipse. Prints the stack trace on standard error.
			// CHECKSTYLE:OFF
			e.printStackTrace();
			// CHECKSTYLE:ON
		} else if (e instanceof CoreException) {
			log(((CoreException)e).getStatus());
		} else if (e instanceof NullPointerException) {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, PLUGIN_ID, severity, AcceleoEngineMessages
					.getString("AcceleoEnginePlugin.ElementNotFound"), e)); //$NON-NLS-1$
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, PLUGIN_ID, severity, e.getMessage(), e));
		}
	}

	/**
	 * Puts the given status in the error log view.
	 * 
	 * @param status
	 *            Error Status.
	 */
	public static void log(IStatus status) {
		// Eclipse platform displays NullPointer on standard error instead of throwing it.
		// We'll handle this by throwing it ourselves.
		if (status == null) {
			throw new NullPointerException(AcceleoEngineMessages
					.getString("AcceleoEnginePlugin.LogNullStatus")); //$NON-NLS-1$
		}

		if (getDefault() != null) {
			getDefault().getLog().log(status);
		} else {
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
				errorMessage = AcceleoEngineMessages.getString("AcceleoEnginePlugin.UnexpectedException"); //$NON-NLS-1$
			}
			log(new Status(severity, PLUGIN_ID, errorMessage));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(dynamicTemplatesListener, DYNAMIC_TEMPLATES_EXTENSION_POINT);
		parseInitialContributions();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.removeListener(dynamicTemplatesListener);
		AcceleoDynamicTemplatesEclipseUtil.clearRegistry();
	}

	/**
	 * Though we have listeners on both provided extension points, there could have been contributions before
	 * this plugin got started. This will parse them.
	 */
	private void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();

		// Dynamic templates
		for (IExtension extension : registry.getExtensionPoint(DYNAMIC_TEMPLATES_EXTENSION_POINT)
				.getExtensions()) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			final List<String> pathes = new ArrayList<String>(configElements.length);
			for (IConfigurationElement elem : configElements) {
				if (DYNAMIC_TEMPLATES_TAG_TEMPLATES.equals(elem.getName())) {
					pathes.add(elem.getAttribute(DYNAMIC_TEMPLATES_ATTRIBUTE_PATH));
				}
			}
			final Bundle bundle = Platform.getBundle(extension.getContributor().getName());
			// If bundle is null, the bundle id is different than its name.
			if (bundle != null) {
				AcceleoDynamicTemplatesEclipseUtil.addExtendingBundle(bundle, pathes);
			}
		}
	}

	/**
	 * This listener will allow us to be aware of contribution changes against the dynamic templates extension
	 * point.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	final class DynamicTemplatesRegistryListener implements IRegistryEventListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
		 */
		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				final IConfigurationElement[] configElements = extension.getConfigurationElements();
				final List<String> pathes = new ArrayList<String>(configElements.length);
				for (IConfigurationElement elem : configElements) {
					if (DYNAMIC_TEMPLATES_TAG_TEMPLATES.equals(elem.getName())) {
						pathes.add(elem.getAttribute(DYNAMIC_TEMPLATES_ATTRIBUTE_PATH));
					}
				}
				final Bundle bundle = Platform.getBundle(extension.getContributor().getName());
				// If bundle is null, the bundle id is different than its name.
				if (bundle != null) {
					AcceleoDynamicTemplatesEclipseUtil.addExtendingBundle(bundle, pathes);
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
		 */
		public void added(IExtensionPoint[] extensionPoints) {
			// no need to listen to this event
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
		 */
		public void removed(IExtension[] extensions) {
			/*
			 * Etensions will be removed on the fly by AcceleoDynamicTemplatesEclipseUtil when trying to
			 * access uninstalled bundles
			 */
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
		 */
		public void removed(IExtensionPoint[] extensionPoints) {
			// no need to listen to this event
		}
	}
}
