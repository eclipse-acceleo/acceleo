/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter;

import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterRegistry;
import org.eclipse.acceleo.ui.interpreter.internal.language.LanguageInterpreterRegistryListener;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class InterpreterPlugin extends AbstractUIPlugin {
	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.ui.interpreter"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static InterpreterPlugin plugin;

	/** The registry listener that will be used to listen to language interpreter changes. */
	private final LanguageInterpreterRegistryListener interpreterListener = new LanguageInterpreterRegistryListener();

	/**
	 * Default constructor for the plugin.
	 */
	public InterpreterPlugin() {
		// Empty implementation
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static InterpreterPlugin getDefault() {
		return plugin;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		plugin = this;
		super.start(context);

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(interpreterListener,
				LanguageInterpreterRegistryListener.LANGUAGE_INTERPRETER_EXTENSION_POINT_ID);

		interpreterListener.parseInitialContributions();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.removeListener(interpreterListener);

		LanguageInterpreterRegistry.clearRegistry();
	}
}
