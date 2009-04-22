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
package org.eclipse.acceleo.ide.ui.launching.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.debug.core.AcceleoDebugger;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoDebugTarget;
import org.eclipse.acceleo.internal.ide.ui.launching.AcceleoLaunchOperation;
import org.eclipse.acceleo.internal.ide.ui.launching.IAcceleoLaunchConfigurationConstants;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

/**
 * Default Acceleo Plug-in launching strategy. It is used to launch an Acceleo application in the current
 * Eclipse thread.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoPluginLaunchingStrategy implements IAcceleoLaunchingStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @throws CoreException
	 * @see org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		IProject project = getProject(configuration);
		if (project != null) {
			AcceleoDebugger debugger = null;
			if ("debug".equals(mode)) { //$NON-NLS-1$
				debugger = new AcceleoDebugger(project);
				launch.addDebugTarget(new AcceleoDebugTarget(launch, debugger));
				AcceleoEvaluationVisitor.setDebug(debugger);
				debugger.start();
			}
			try {
				String model = getModelPath(configuration);
				String target = getTargetPath(configuration);
				String message;
				if (model.length() == 0) {
					message = AcceleoUIMessages.getString("AcceleoLaunchDelegate.MissingModel"); //$NON-NLS-1$
				} else if (target.length() == 0) {
					message = AcceleoUIMessages.getString("AcceleoLaunchDelegate.MissingTarget"); //$NON-NLS-1$
				} else {
					message = null;
				}
				if (message != null) {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message));
				} else {
					IPath targetPath = new Path(target);
					IContainer container;
					if (targetPath.segmentCount() == 1) {
						container = ResourcesPlugin.getWorkspace().getRoot().getProject(
								targetPath.lastSegment());
					} else if (targetPath.segmentCount() > 1) {
						container = ResourcesPlugin.getWorkspace().getRoot().getFolder(targetPath);
					} else {
						container = null;
					}
					if (container instanceof IFolder && !container.exists()) {
						((IFolder)container).create(true, true, monitor);
					} else if (container instanceof IProject && !container.exists()) {
						((IProject)container).create(monitor);
						((IProject)container).open(monitor);
						container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					}
					if (container != null) {
						String qualifiedName = getMainType(configuration);
						File targetFolder = container.getLocation().toFile();
						List<String> args = getArguments(configuration);
						launch(project, qualifiedName, model, targetFolder, args, monitor);
						container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					}
				}
			} finally {
				if ("debug".equals(mode)) { //$NON-NLS-1$
					AcceleoEvaluationVisitor.setDebug(null);
					debugger.end();
				}
			}
		}
	}

	/**
	 * Launches the given java class in the current Eclipse thread.
	 * 
	 * @param project
	 *            the project where the module is located.
	 * @param qualifiedName
	 *            the module Java name (the first character may be in upper case)
	 * @param model
	 *            the model
	 * @param targetFolder
	 *            the target folder
	 * @param args
	 *            the other arguments of the code generation
	 * @param monitor
	 *            the current monitor
	 */
	protected void launch(IProject project, String qualifiedName, String model, File targetFolder,
			List<String> args, IProgressMonitor monitor) {
		AcceleoLaunchOperation operation = new AcceleoLaunchOperation(project, qualifiedName, model,
				targetFolder, args);
		try {
			operation.run(monitor);
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
	}

	/**
	 * Returns the project whose name is specified by the given launch configuration, or <code>null</code> if
	 * none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the project, or null if it doesn't exist
	 */
	protected IProject getProject(ILaunchConfiguration configuration) {
		String projectName;
		try {
			projectName = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			projectName = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project != null && project.isAccessible()) {
			return project;
		} else {
			return null;
		}
	}

	/**
	 * Returns the main type specified by the given launch configuration, or an empty string if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the main type or an empty string
	 */
	protected String getMainType(final ILaunchConfiguration configuration) {
		String type = ""; //$NON-NLS-1$
		try {
			type = configuration.getAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			type = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return type;
	}

	/**
	 * Returns the model path specified by the given launch configuration, or an empty string if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the model path or an empty string
	 */
	protected String getModelPath(final ILaunchConfiguration configuration) {
		String model = ""; //$NON-NLS-1$
		try {
			model = configuration.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_MODEL_PATH, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			model = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return model;
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

	/**
	 * Returns the Acceleo application arguments (properties) specified by the given launch configuration, or
	 * an empty list if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the arguments or an empty list
	 */
	protected List<String> getArguments(final ILaunchConfiguration configuration) {
		String args = ""; //$NON-NLS-1$
		try {
			args = configuration.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_ARGUMENTS, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			args = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		List<String> result = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(args, "\n"); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			result.add(st.nextToken().trim());
		}
		return result;
	}

}
