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
package org.eclipse.acceleo.ide.ui.launching.strategy;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * An internal extension point is defined to specify multiple launching strategies. It is used to define a
 * specific way of launching an Acceleo generation. The extension point "org.eclipse.acceleo.ide.ui.launching"
 * requires a fully qualified name of a Java class implementing this interface.
 * 
 * @see AcceleoPluginLaunchingStrategy
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface IAcceleoLaunchingStrategy {

	/**
	 * The identifier of the internal extension point specifying the implementation to use for launching
	 * strategy. It is used to define a specific way of launching an Acceleo generation.
	 */
	String LAUNCHING_STRATEGY_EXTENSION_ID = "org.eclipse.acceleo.ide.ui.launching"; //$NON-NLS-1$

	/**
	 * Launches the given configuration in the current Eclipse thread.
	 * 
	 * @param configuration
	 *            the configuration to launch
	 * @param mode
	 *            the mode in which to launch, one of the mode constants defined by ILaunchManager - RUN_MODE
	 *            or DEBUG_MODE
	 * @param launch
	 *            the launch object to contribute processes and debug targets to
	 * @param monitor
	 *            is the progress monitor
	 * @throws CoreException
	 *             when an issue occurs
	 * @since 0.8
	 */
	void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException;

}
