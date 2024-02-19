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
package org.eclipse.acceleo.engine;

import org.eclipse.acceleo.engine.internal.utils.AcceleoDynamicTemplatesEclipseUtil;
import org.eclipse.acceleo.engine.internal.utils.AcceleoEngineRegistry;
import org.eclipse.acceleo.engine.internal.utils.AcceleoTraceabilityRegistryListenerUils;
import org.eclipse.acceleo.engine.internal.utils.DynamicTemplatesRegistryListener;
import org.eclipse.acceleo.engine.internal.utils.EngineRegistryListener;
import org.eclipse.acceleo.engine.internal.utils.TraceabilityRegistryListeners;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IExtensionRegistry;
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
public class AcceleoEnginePlugin extends Plugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.engine"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static AcceleoEnginePlugin plugin;

	/** The registry listener that will be used to listen to dynamic templates changes. */
	private final DynamicTemplatesRegistryListener dynamicTemplatesListener = new DynamicTemplatesRegistryListener();

	/** The registry listener that will be used to listen to engine creator changes. */
	private final EngineRegistryListener engineCreatorListener = new EngineRegistryListener();

	/** The registry listener that will be used to listen to traceability listener changes. */
	private final TraceabilityRegistryListeners traceabilityRegistryListener = new TraceabilityRegistryListeners();

	/**
	 * Default constructor for the plugin.
	 */
	public AcceleoEnginePlugin() {
		// Empty implementation
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
		plugin = this;
		super.start(context);
		parseExtensionPoints();
	}

	/**
	 * Parse extension points
	 */
	public void parseExtensionPoints() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(dynamicTemplatesListener,
				DynamicTemplatesRegistryListener.DYNAMIC_TEMPLATES_EXTENSION_POINT);
		registry.addListener(engineCreatorListener, EngineRegistryListener.ENGINE_CREATORS_EXTENSION_POINT);
		registry.addListener(traceabilityRegistryListener,
				TraceabilityRegistryListeners.TRACEABILITY_LISTENERS_EXTENSION_POINT);
		dynamicTemplatesListener.parseInitialContributions();
		engineCreatorListener.parseInitialContributions();
		traceabilityRegistryListener.parseInitialContributions();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.removeListener(dynamicTemplatesListener);
		registry.removeListener(engineCreatorListener);
		registry.removeListener(traceabilityRegistryListener);
		AcceleoDynamicTemplatesEclipseUtil.clearRegistry();
		AcceleoEngineRegistry.clearRegistry();
		AcceleoTraceabilityRegistryListenerUils.clearRegistry();
	}
}
