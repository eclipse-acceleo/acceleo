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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.engine.utils.AcceleoEngineUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.debug.core.AcceleoDebugger;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoDebugTarget;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoProcess;
import org.eclipse.acceleo.internal.ide.ui.launching.IAcceleoLaunchConfigurationConstants;
import org.eclipse.acceleo.profiler.Profiler;
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
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

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
	 * @since 0.8
	 */
	@SuppressWarnings("deprecation")
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		final IProject project = getProject(configuration);
		if (project == null) {
			return;
		}

		AcceleoDebugger debugger = null;
		Profiler profiler = null;
		boolean profiling = configuration.getAttribute(
				IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_PROFILING, false);

		if ("debug".equals(mode)) { //$NON-NLS-1$
			debugger = new AcceleoDebugger(project);
			for (IDebugTarget target : launch.getDebugTargets()) {
				launch.removeDebugTarget(target);
			}
			launch.addDebugTarget(new AcceleoDebugTarget(launch, debugger));
			AcceleoEvaluationVisitor.setDebug(debugger);
			debugger.start();
		} else if (AcceleoPreferences.isProfilerEnabled() || profiling) {
			profiler = new Profiler();
			AcceleoEngineUtils.setProfiler(profiler);
			launch.addProcess(new AcceleoProcess(launch));
		} else {
			launch.addProcess(new AcceleoProcess(launch));
		}
		boolean traceability = computeTraceability(configuration);
		if (traceability) {
			switchTraceability(true);
		}
		try {
			final String model = getModelPath(configuration);
			final String target = getTargetPath(configuration);
			String message;
			if (model.length() == 0) {
				message = AcceleoUIMessages.getString("AcceleoLaunchDelegate.MissingModel", configuration //$NON-NLS-1$
						.getName());
			} else if (target.length() == 0) {
				message = AcceleoUIMessages.getString("AcceleoLaunchDelegate.MissingTarget", configuration //$NON-NLS-1$
						.getName());
			} else {
				message = null;
			}
			if (message != null) {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message));

				final String dialogMessage = message;
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						Shell parentShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						String dialogTitle = AcceleoUIMessages
								.getString("AcceleoPluginLaunchingStrategy.ErrorDialogTitle"); //$NON-NLS-1$
						String[] dialogButtonLabels = new String[] {AcceleoUIMessages
								.getString("AcceleoPluginLaunchingStrategy.ErrorDialogOkButton"), }; //$NON-NLS-1$
						MessageDialog messageDialog = new MessageDialog(parentShell, dialogTitle, null,
								dialogMessage, MessageDialog.ERROR, dialogButtonLabels, 0);
						messageDialog.open();
					}
				});
			} else {
				IPath targetPath = new Path(target);
				IContainer container;
				if (targetPath.segmentCount() == 1) {
					container = ResourcesPlugin.getWorkspace().getRoot().getProject(targetPath.lastSegment());
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
					final String qualifiedName = getMainType(configuration);
					File targetFolder = null;
					try {
						if (container.isVirtual()) {
							AcceleoUIActivator.log(AcceleoUIMessages
									.getString("AcceleoPluginLaunchStrategy.VirtualFolder"), true); //$NON-NLS-1$
						} else {
							targetFolder = container.getLocation().toFile();
						}
					} catch (NoSuchMethodError e) {
						// Eclipse 3.5 does not have "isVirtual"
						targetFolder = container.getLocation().toFile();
					}
					final List<String> args = getArguments(configuration);

					if (targetFolder != null) {
						launch(project, qualifiedName, model, targetFolder, args, monitor);
						container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
					}

				}
			}
		} finally {
			if (traceability) {
				switchTraceability(false);
			}
			if ("debug".equals(mode)) { //$NON-NLS-1$
				AcceleoEvaluationVisitor.setDebug(null);
				if (debugger != null) {
					debugger.end();
				}
			} else if (profiling) {
				saveProfileModel(configuration, AcceleoEvaluationVisitor.getProfiler(), monitor);
				AcceleoEvaluationVisitor.setProfile(null);
			}
		}
	}

	/**
	 * To switch the traceability status.
	 * 
	 * @param activate
	 *            indicates that we would like to compute the traceability information
	 * @since 3.0
	 */
	protected void switchTraceability(boolean activate) {
		AcceleoPreferences.switchTraceability(activate);
	}

	/**
	 * Save the profile model to the workspace.
	 * 
	 * @param configuration
	 *            the launch configuration
	 * @param profiler
	 *            the profiler instance
	 * @param monitor
	 *            the progress monitor
	 */
	private void saveProfileModel(ILaunchConfiguration configuration, Profiler profiler,
			IProgressMonitor monitor) {
		if (profiler != null) {
			try {
				String profileModelPath = getProfileModelPath(configuration);
				if (profileModelPath.length() != 0) {
					profiler.save(ResourcesPlugin.getWorkspace().getRoot().getLocation().append(
							profileModelPath).toString());
					try {
						ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(profileModelPath))
								.getParent().refreshLocal(1, monitor);
					} catch (CoreException e) {
						AcceleoUIActivator
								.getDefault()
								.getLog()
								.log(new Status(
										IStatus.WARNING,
										AcceleoUIActivator.PLUGIN_ID,
										AcceleoUIMessages
												.getString("AcceleoLaunchDelegate.UnableToRefreshProfileModelContainer"))); //$NON-NLS-1$
					}
				} else {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, AcceleoUIMessages
									.getString("AcceleoLaunchDelegate.MissingProfileModel"))); //$NON-NLS-1$
				}
			} catch (IOException e) {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, AcceleoUIMessages.getString(
								"AcceleoLaunchDelegate.ProfileModelError", new Object[] {e.getMessage(), }))); //$NON-NLS-1$
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
			project.getWorkspace().run(operation, monitor);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Returns the project whose name is specified by the given launch configuration, or <code>null</code> if
	 * none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the project, or null if it doesn't exist
	 * @since 0.8
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
		}
		return null;
	}

	/**
	 * Returns the main type specified by the given launch configuration, or an empty string if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the main type or an empty string
	 * @since 0.8
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
	 * @since 0.8
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
	 * Returns the profile model path specified by the given launch configuration, or an empty string if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the profile model path or an empty string
	 * @since 3.0
	 */
	protected String getProfileModelPath(final ILaunchConfiguration configuration) {
		String model = ""; //$NON-NLS-1$
		try {
			model = configuration.getAttribute(IAcceleoLaunchConfigurationConstants.ATTR_PROFILE_MODEL_PATH,
					""); //$NON-NLS-1$
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
	 * @since 0.8
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
	 * Returns the traceability status specified by the given launch configuration, or false if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return true if we would like to compute the traceability information
	 * @since 3.0
	 */
	protected boolean computeTraceability(final ILaunchConfiguration configuration) {
		boolean traceability = false;
		try {
			traceability = configuration.getAttribute(
					IAcceleoLaunchConfigurationConstants.ATTR_COMPUTE_TRACEABILITY, false);
		} catch (CoreException e) {
			traceability = false;
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return traceability;
	}

	/**
	 * Returns the Acceleo application arguments (properties) specified by the given launch configuration, or
	 * an empty list if none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the arguments or an empty list
	 * @since 0.8
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
