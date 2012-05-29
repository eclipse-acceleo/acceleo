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
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * To launch an Acceleo application in a Java standalone way.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoLaunchDelegateStandalone extends org.eclipse.jdt.launching.JavaLaunchDelegate {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.launching.JavaLaunchDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void launch(final ILaunchConfiguration configuration, String mode, final ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		super.launch(configuration, mode, launch, monitor);
		String target = getTargetPath(configuration);
		if (target.length() > 0) {
			IPath targetPath = new Path(target);
			final IContainer container;
			if (targetPath.segmentCount() == 1) {
				container = ResourcesPlugin.getWorkspace().getRoot().getProject(targetPath.lastSegment());
			} else if (targetPath.segmentCount() > 1) {
				container = ResourcesPlugin.getWorkspace().getRoot().getFolder(targetPath);
			} else {
				container = null;
			}
			if (container != null) {
				IWorkspaceRunnable operation = new IWorkspaceRunnable() {
					public void run(final IProgressMonitor progressMonitor) throws CoreException {
						try {
							while (!launch.isTerminated() && !progressMonitor.isCanceled()) {
								Thread.sleep(100);
							}
						} catch (InterruptedException e) {
							AcceleoUIActivator.log(e, true);
						}
						if (container.getProject() != null) {
							container.getProject().refreshLocal(IResource.DEPTH_INFINITE, progressMonitor);
						}
					}
				};
				ResourcesPlugin.getWorkspace().run(operation, monitor);
			}
		}
	}

	/**
	 * Returns the target folder specified by the given launch configuration, or an empty string if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the target folder or an empty string
	 */
	protected String getTargetPath(final ILaunchConfiguration configuration) {
		String target = ""; //$NON-NLS-1$
		try {
			target = configuration.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_TARGET_PATH, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			target = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return target;
	}

}
