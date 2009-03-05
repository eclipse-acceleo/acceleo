/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import org.eclipse.acceleo.common.internal.utils.AcceleoServicesEclipseUtil;
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
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoCommonPlugin extends Plugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.common"; //$NON-NLS-1$

	/** Name of the extension point to parse for service classes. */
	public static final String SERVICES_EXTENSION_POINT = "org.eclipse.acceleo.common.java.services"; //$NON-NLS-1$

	/** Exact name of the "class" tag of the extension point. */
	private static final String CLASS_TAG_NAME = "class"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static AcceleoCommonPlugin plugin;

	/** The registry listener that will be used to listen to Acceleo service changes. */
	private final AcceleoServicesRegistryListener servicesListener = new AcceleoServicesRegistryListener();

	/** Keeps a reference to this bundle's context. */
	private BundleContext context;

	/**
	 * Default constructor for the plugin.
	 */
	public AcceleoCommonPlugin() {
		plugin = this;
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static AcceleoCommonPlugin getDefault() {
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
			throw new NullPointerException(AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.LogNullException")); //$NON-NLS-1$
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
			log(new Status(severity, PLUGIN_ID, severity, AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.ElementNotFound"), e)); //$NON-NLS-1$
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, PLUGIN_ID, severity, AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.JavaException"), e)); //$NON-NLS-1$
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
			throw new NullPointerException(AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.LogNullStatus")); //$NON-NLS-1$
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
				errorMessage = AcceleoCommonMessages.getString("AcceleoCommonPlugin.UnexpectedException"); //$NON-NLS-1$
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
	public void start(final BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		context = bundleContext;
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(servicesListener, SERVICES_EXTENSION_POINT);
		parseInitialContributions();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		plugin = null;
		context = null;
		super.stop(bundleContext);
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.removeListener(servicesListener);
		AcceleoServicesEclipseUtil.clearRegistry();
	}

	/**
	 * Though we have listeners on both provided extension points, there could have been contributions before
	 * this plugin got started. This will parse them.
	 */
	private void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		for (IExtension extension : registry.getExtensionPoint(SERVICES_EXTENSION_POINT).getExtensions()) {
			for (IConfigurationElement service : extension.getConfigurationElements()) {
				try {
					AcceleoServicesEclipseUtil.addService(service.createExecutableExtension(CLASS_TAG_NAME));
				} catch (CoreException e) {
					log(e, false);
				}
			}
		}
	}

	/**
	 * Returns this bundle's context.
	 * 
	 * @return This bundle's context.
	 */
	public BundleContext getContext() {
		return context;
	}

	/**
	 * This will allow us to be aware of changes of extension against the Acceleo services extension point.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	final class AcceleoServicesRegistryListener implements IRegistryEventListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
		 */
		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					try {
						AcceleoServicesEclipseUtil.addService(service
								.createExecutableExtension(CLASS_TAG_NAME));
					} catch (CoreException e) {
						log(e, false);
					}
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
		 */
		public void removed(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					AcceleoServicesEclipseUtil.removeService(service.getAttribute(CLASS_TAG_NAME));
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
		 */
		public void added(IExtensionPoint[] extensionPoints) {
			// no need to listen to this
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
