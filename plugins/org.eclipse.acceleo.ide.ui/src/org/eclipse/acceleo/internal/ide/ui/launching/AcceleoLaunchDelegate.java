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
package org.eclipse.acceleo.internal.ide.ui.launching;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.debug.core.AcceleoDebugger;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoDebugTarget;
import org.eclipse.acceleo.internal.ide.ui.launching.strategy.AcceleoJavaLaunchingStrategy;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.osgi.framework.Bundle;

/**
 * To launch an Acceleo application in a Java standalone way, or in a full Eclipse way. This last way is currently
 * required for debugging an Acceleo file. You can also debug an Acceleo application by launching it in a Java
 * standalone way, but you will debug the Acceleo Java sources...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoLaunchDelegate extends AcceleoLaunchDelegateStandalone {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.launching.JavaLaunchDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(final ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		IAcceleoLaunchingStrategy strategy = getLaunchingStrategy(configuration);
		if (strategy instanceof AcceleoJavaLaunchingStrategy) {
			super.launch(configuration, mode, launch, monitor);
		} else {
			launchInCurrentThread(configuration, mode, launch, strategy, monitor);
		}
	}

	/**
	 * Launches the given configuration in the current Eclipse thread. This thread is currently required for
	 * debugging an Acceleo file.
	 * 
	 * @param configuration
	 *            the configuration to launch
	 * @param mode
	 *            the mode in which to launch, one of the mode constants defined by ILaunchManager - RUN_MODE
	 *            or DEBUG_MODE
	 * @param launch
	 *            the launch object to contribute processes and debug targets to
	 * @param strategy
	 *            the launching strategy (used to define a specific way of launching an Acceleo generation)
	 * @param monitor
	 *            is the progress monitor
	 * @throws CoreException
	 *             when an issue occurs
	 */
	private void launchInCurrentThread(final ILaunchConfiguration configuration, String mode, ILaunch launch,
			IAcceleoLaunchingStrategy strategy, IProgressMonitor monitor) throws CoreException {
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
					URI modelURI = URI.createPlatformResourceURI(model, false);
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
						List<String> args = getArguments(configuration);
						AcceleoLaunchOperation operation = new AcceleoLaunchOperation(project,
								getMainType(configuration), modelURI, container.getLocation().toFile(), args,
								strategy);
						try {
							operation.run(monitor);
						} catch (CoreException e) {
							AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
						}
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
	 * Returns the launching strategy specified by the given launch configuration, or <code>null</code> if
	 * none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the launching strategy, or null if none
	 */
	private IAcceleoLaunchingStrategy getLaunchingStrategy(final ILaunchConfiguration configuration) {
		String description;
		try {
			description = configuration.getAttribute(
					IAcceleoLaunchConfigurationConstants.ATTR_LAUNCHING_STRATEGY_DESCRIPTION, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			description = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return getLaunchingStrategy(description);
	}

	/**
	 * Gets the launching strategy that matches the given description. It is used to define a specific way of
	 * launching an Acceleo generation.
	 * 
	 * @param launchingID
	 *            is the description of the strategy to get in the current Eclipse instance
	 * @return the launching strategy that matches the given description, or null if it doesn't exist
	 */
	@SuppressWarnings("unchecked")
	private IAcceleoLaunchingStrategy getLaunchingStrategy(String launchingID) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint(IAcceleoLaunchingStrategy.LAUNCHING_STRATEGY_EXTENSION_ID);
		if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IExtension extension = extensions[i];
				IConfigurationElement[] members = extension.getConfigurationElements();
				for (int j = 0; j < members.length; j++) {
					IConfigurationElement member = members[j];
					String description = member.getAttribute("description"); //$NON-NLS-1$
					String strategyClass = member.getAttribute("class"); //$NON-NLS-1$
					if (strategyClass != null && description != null && description.equals(launchingID)) {
						try {
							Bundle bundle = Platform.getBundle(member.getNamespaceIdentifier());
							Class<IAcceleoLaunchingStrategy> c = bundle.loadClass(strategyClass);
							return c.newInstance();
						} catch (ClassNotFoundException e) {
							IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK,
									e.getMessage(), e);
							AcceleoUIActivator.getDefault().getLog().log(status);
						} catch (InstantiationException e) {
							IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK,
									e.getMessage(), e);
							AcceleoUIActivator.getDefault().getLog().log(status);
						} catch (IllegalAccessException e) {
							IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK,
									e.getMessage(), e);
							AcceleoUIActivator.getDefault().getLog().log(status);
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the project whose name is specified by the given launch configuration, or <code>null</code> if
	 * none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the project, or null if it doesn't exist
	 */
	private IProject getProject(ILaunchConfiguration configuration) {
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
	private String getMainType(final ILaunchConfiguration configuration) {
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
	private String getModelPath(final ILaunchConfiguration configuration) {
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
	 * Returns the Acceleo application arguments (properties) specified by the given launch configuration, or an
	 * empty list if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the arguments or an empty list
	 */
	private List<String> getArguments(final ILaunchConfiguration configuration) {
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
