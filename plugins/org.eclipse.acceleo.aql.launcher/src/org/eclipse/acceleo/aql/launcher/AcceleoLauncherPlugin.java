/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.launcher;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Logger;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * Plugin and Activator classes for the bundle.
 * 
 * @author lgoubet
 */
public final class AcceleoLauncherPlugin extends EMFPlugin {

	/** Keep track of the singleton. */
	public static final AcceleoLauncherPlugin INSTANCE = new AcceleoLauncherPlugin();

	/** Keep track of the singleton. */
	private static Implementation plugin;

	/** Create the instance. */
	public AcceleoLauncherPlugin() {
		super(new ResourceLocator[] {});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * 
	 * @return the singleton instance.
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * 
	 * @return the singleton instance.
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	@Override
	public void log(Object logEntry) {
		Logger logger = getPluginLogger();
		if (logger == null) {
			if (logEntry instanceof Throwable) {
				((Throwable)logEntry).printStackTrace(System.err);
			} else {
				System.err.println(CLIUtils.getDecorator().red(logEntry.toString()));
			}
		} else {
			logger.log(logEntry);
		}
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 */
	public static class Implementation extends EclipsePlugin {

		/** Creates an instance. */
		public Implementation() {
			super();
			plugin = this;
		}

	}

}
