/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.ui.launch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugPlugin;
import org.eclipse.acceleo.aql.ls.debug.ide.launch.AcceleoLaunchConfigurationDelegate;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchGroup;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;

public class AcceleoLaunchShortcut implements ILaunchShortcut2 {

	@Override
	public void launch(ISelection selection, String mode) {
		launch(getLaunchableResource(selection), mode);
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		launch(getLaunchableResource(editor), mode);
	}

	/**
	 * Launches the given {@link IResource} in the given mode.
	 * 
	 * @param resource
	 *            the {@link IResource}
	 * @param mode
	 *            the mode
	 */
	private void launch(IResource resource, String mode) {
		final ILaunchConfiguration[] configurations = getLaunchConfigurations(resource);
		if (configurations != null) {
			for (ILaunchConfiguration config : configurations) {
				try {
					// TODO other monitor ?
					config.launch(mode, new NullProgressMonitor());
					return;
				} catch (CoreException e) {
					AcceleoDebugPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoDebugPlugin.ID,
							"couldn't launch module " + resource.getFullPath(), e));
				}
			}
			createConfiguration(resource, mode);
		} else {
			// not an Acceleo 4 module with main template
		}
	}

	/**
	 * Creates a {@link ILaunchConfiguration} for the given {@link IResource}.
	 * 
	 * @param resource
	 *            the {@link IResource} to launch
	 * @param mode
	 *            the launch mode
	 */
	protected void createConfiguration(IResource resource, String mode) {
		try {
			final ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType configType = launchManager.getLaunchConfigurationType(
					AcceleoLaunchConfigurationDelegate.ID);
			final ILaunchConfigurationWorkingCopy wc = configType.newInstance(null, launchManager
					.generateLaunchConfigurationName(resource.getName()));
			wc.setAttribute(AcceleoDebugger.MODULE, resource.getFullPath().toString());
			wc.setMappedResources(new IResource[] {resource });
			final ILaunchConfiguration config = wc.doSave();
			IStructuredSelection selection;
			if (config == null) {
				selection = new StructuredSelection();
			} else {
				selection = new StructuredSelection(config);
			}
			final ILaunchGroup launchGroup = DebugUITools.getLaunchGroup(config, mode);
			DebugUITools.openLaunchConfigurationDialogOnGroup(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), selection, launchGroup.getIdentifier());
		} catch (CoreException e) {
			AcceleoDebugPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoDebugPlugin.ID,
					"couldn't create launch configuration for module " + resource.getFullPath(), e));
		}
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		return getLaunchConfigurations(getLaunchableResource(selection));
	}

	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		return getLaunchConfigurations(getLaunchableResource(editorpart));
	}

	/**
	 * Gets the {@link ILaunchConfiguration} for the given {@link IResource}.
	 * 
	 * @param resource
	 *            the {@link IResource}
	 * @return the {@link ILaunchConfiguration} for the given {@link IResource} if any, <code>null</code>
	 *         otherwise
	 */
	private ILaunchConfiguration[] getLaunchConfigurations(IResource resource) {
		final ILaunchConfiguration[] res;

		if (AcceleoPlugin.isAcceleoMain(resource)) {
			final List<ILaunchConfiguration> configurations = new ArrayList<ILaunchConfiguration>();
			final ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType configType = launchManager.getLaunchConfigurationType(
					AcceleoLaunchConfigurationDelegate.ID);
			try {
				final ILaunchConfiguration[] launchConfigurations = launchManager.getLaunchConfigurations(
						configType);
				final String module = resource.getFullPath().toString();
				for (ILaunchConfiguration launchConfig : launchConfigurations) {
					if (launchConfig.hasAttribute(AcceleoDebugger.MODULE)) {
						final String launchConfigModule = launchConfig.getAttribute(AcceleoDebugger.MODULE,
								(String)null);
						if (module.equals(launchConfigModule)) {
							configurations.add(launchConfig);
						}
					}
				}
			} catch (CoreException e) {
				AcceleoDebugPlugin.getPlugin().log(new Status(IStatus.ERROR, AcceleoDebugPlugin.ID,
						"couldn't get launch configuration for module " + resource.getFullPath(), e));
			}
			res = configurations.toArray(new ILaunchConfiguration[configurations.size()]);
		} else {
			res = null;
		}
		return res;
	}

	@Override
	public IResource getLaunchableResource(ISelection selection) {
		IResource res = null;

		if (selection instanceof IStructuredSelection) {
			for (final Object element : ((IStructuredSelection)selection).toArray()) {
				if (element instanceof IFile) {
					res = (IResource)element;
					break;
				}
			}
		}

		return res;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return ResourceUtil.getFile(editorpart.getEditorInput());
	}

}
