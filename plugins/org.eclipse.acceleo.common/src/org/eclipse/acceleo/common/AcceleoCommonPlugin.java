/*******************************************************************************
 * Copyright (c) 2009, 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import org.eclipse.acceleo.common.internal.utils.AcceleoLogger;
import org.eclipse.acceleo.common.internal.utils.AcceleoServicesEclipseUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.ClassLoadingCompanionProvider;
import org.eclipse.acceleo.common.internal.utils.workspace.ClassLoadingCompanionsRegistry;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoCommonPlugin extends Plugin {
	/**
	 * Name of the extension point to parse for class loading companions.
	 * 
	 * @since 3.6
	 */
	public static final String COMPANIONS_EXTENSION_POINT = "org.eclipse.acceleo.common.internal.classloadingcompanion"; //$NON-NLS-1$

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.common"; //$NON-NLS-1$

	/**
	 * Name of the extension point to parse for service classes.
	 * 
	 * @deprecated this extension point has been deleted
	 */
	@Deprecated
	public static final String SERVICES_EXTENSION_POINT = "org.eclipse.acceleo.common.java.services"; //$NON-NLS-1$

	/** Exact name of the "class" tag of the extension point. */
	private static final String CLASS_TAG_NAME = "class"; //$NON-NLS-1$

	/** Exact name of the "fileExtension" tag of the extension point. */
	private static final String FILE_EXTENSION_TAG_NAME = "fileExtension"; //$NON-NLS-1$

	/** Exact name of the "file" tag of the extension point. */
	private static final String FILE_TAG_NAME = "file"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static AcceleoCommonPlugin plugin;

	/** Keeps a reference to this bundle's context. */
	private BundleContext context;

	/** The registry listener that will be used to listen to Acceleo library connector changes. */
	private final AcceleoCompanionsRegistryListener companionsListener = new AcceleoCompanionsRegistryListener();

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
	 * @deprecated
	 */
	@Deprecated
	public static void log(Exception e, boolean blocker) {
		AcceleoLogger.log(e, blocker);
	}

	/**
	 * Puts the given status in the error log view.
	 * 
	 * @param status
	 *            Error Status.
	 * @deprecated
	 */
	@Deprecated
	public static void log(IStatus status) {
		AcceleoLogger.log(status);
	}

	/**
	 * Puts the given message in the error log view, as error or warning.
	 * 
	 * @param message
	 *            The message to put in the error log view.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 * @deprecated
	 */
	@Deprecated
	public static void log(String message, boolean blocker) {
		AcceleoLogger.log(message, blocker);
	}

	/**
	 * Traces an exception in the error log with the given log message.
	 * <p>
	 * This is a convenience method fully equivalent to using
	 * <code>log(new Status(int, PLUGIN_ID, message, cause)</code>.
	 * </p>
	 * 
	 * @param message
	 *            The message that is to be displayed in the error log view.
	 * @param cause
	 *            Exception that is to be logged.
	 * @param blocker
	 *            <code>True</code> if the exception must be logged as error, <code>False</code> to log it as
	 *            a warning.
	 * @since 0.8
	 * @deprecated
	 */
	@Deprecated
	public static void log(String message, Exception cause, boolean blocker) {
		AcceleoLogger.log(message, cause, blocker);
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		AcceleoWorkspaceUtil.INSTANCE.initialize();

		context = bundleContext;
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(companionsListener, COMPANIONS_EXTENSION_POINT);
		parseInitialContributions();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		try {
			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			registry.removeListener(companionsListener);

			AcceleoServicesEclipseUtil.clearRegistry();
			ClassLoadingCompanionsRegistry.INSTANCE.clearRegistry();
			AcceleoWorkspaceUtil.INSTANCE.dispose();
			AcceleoPreferences.save();
			plugin = null;
			context = null;

		} finally {
			super.stop(bundleContext);
		}
	}

	/**
	 * Though we have listeners on the provided extension points, there could have been contributions before
	 * this plugin got started and listeners installed. This will parse them.
	 */
	@SuppressWarnings("unchecked")
	private void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		// and the class loading companions?
	}

	/**
	 * This will allow us to be aware of changes of extension against the Acceleo library connector extension
	 * point.
	 */
	final class AcceleoCompanionsRegistryListener implements IRegistryEventListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
		 */
		@SuppressWarnings("unchecked")
		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					try {
						ClassLoadingCompanionsRegistry.INSTANCE
								.register((ClassLoadingCompanionProvider)service
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
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
		 */
		public void added(IExtensionPoint[] extensionPoints) {
			// no need to listen to this
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
		 */
		public void removed(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					ClassLoadingCompanionsRegistry.INSTANCE.unregister(service.getAttribute(CLASS_TAG_NAME));
				}
			}
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
