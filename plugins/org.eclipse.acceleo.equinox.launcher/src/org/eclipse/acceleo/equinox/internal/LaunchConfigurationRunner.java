/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.equinox.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.core.LaunchManager;

/**
 * A class launching a launch configuration.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public class LaunchConfigurationRunner {

	/**
	 * Start a launch configuration.
	 * 
	 * @param dotLaunchFilePath
	 *            the path of the .launch file in the workspace.
	 * @param monitor
	 *            a progress monitor.
	 * @throws CoreException
	 *             if anything goes wrong during the execution of the .launch.
	 */
	public void launch(String dotLaunchFilePath, IProgressMonitor monitor) throws CoreException {

		LaunchManager manager = new LaunchManager();
		try {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(dotLaunchFilePath));
			if (file != null && file.exists() && file.isAccessible()
					&& ILaunchConfiguration.LAUNCH_CONFIGURATION_FILE_EXTENSION.equals(file
							.getFileExtension())) {
				ILaunchConfiguration handle = manager.getLaunchConfiguration(file);
				handle.launch(ILaunchManager.RUN_MODE, monitor, true, false);
			} else {
				throw new RuntimeException("File:" + dotLaunchFilePath
						+ " not found or not having the .launch file extension.");
			}
		} finally {
			manager.shutdown();
		}
	}

}
