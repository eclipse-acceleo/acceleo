/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.launch;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;

import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.lsp4e.debug.DSPPlugin;
import org.eclipse.lsp4e.debug.launcher.DSPLaunchDelegate;

public class AcceleoLaunchConfigurationDelegate extends DSPLaunchDelegate {

	/**
	 * The launch ID.
	 */
	public static final String ID = "org.eclipse.acceleo.aql.ls.debug.ide.launchConfigurationType";

	@SuppressWarnings("restriction")
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		final ILaunchConfigurationWorkingCopy wc = configuration.getWorkingCopy();
		final JsonObject param = new JsonObject();

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (wc.hasAttribute(AcceleoDebugger.MODULE)) {
			final IFile module = root.getFile(new Path(wc.getAttribute(AcceleoDebugger.MODULE,
					(String)null)));
			final URI moduleUri = module.getLocation().toFile().getAbsoluteFile().toURI();
			param.addProperty(AcceleoDebugger.MODULE, moduleUri.toString());
		}
		if (wc.hasAttribute(AcceleoDebugger.MODEL)) {
			final IFile model = root.getFile(new Path(wc.getAttribute(AcceleoDebugger.MODEL, (String)null)));
			final URI modelUri = model.getLocation().toFile().getAbsoluteFile().toURI();
			param.addProperty(AcceleoDebugger.MODEL, modelUri.toString());
		}
		if (wc.hasAttribute(AcceleoDebugger.DESTINATION)) {
			final Path destinationPath = new Path(wc.getAttribute(AcceleoDebugger.DESTINATION, (String)null));
			IResource destination = root.findMember(destinationPath);
			if (destination instanceof IFile) {
				destination = destination.getParent();
			} else if (destination == null) {
				destination = root.getFolder(destinationPath);
				if (!destination.exists()) {
					((IFolder)destination).create(true, true, monitor);
				}
			}
			final URI destinationUri = destination.getLocation().toFile().getAbsoluteFile().toURI();
			param.addProperty(AcceleoDebugger.DESTINATION, destinationUri.toString());
		}
		if (ILaunchManager.PROFILE_MODE.equals(mode)) {
			if (wc.hasAttribute(AcceleoDebugger.PROFILE_MODEL)) {
				final IFile profileModel = root.getFile(new Path(wc.getAttribute(
						AcceleoDebugger.PROFILE_MODEL, (String)null)));
				param.addProperty(AcceleoDebugger.PROFILE_MODEL, profileModel.getLocation().toFile()
						.getAbsoluteFile().toString());
			}
			if (wc.hasAttribute(AcceleoDebugger.PROFILE_MODEL_REPRESENTATION)) {
				param.addProperty(AcceleoDebugger.PROFILE_MODEL_REPRESENTATION, wc.getAttribute(
						AcceleoDebugger.PROFILE_MODEL_REPRESENTATION, (String)null));
			}
		}

		wc.setAttribute(DSPPlugin.ATTR_CUSTOM_LAUNCH_PARAMS, true);
		wc.setAttribute(DSPPlugin.ATTR_DSP_PARAM, new Gson().toJson(param));
		wc.setAttribute(DSPPlugin.ATTR_DSP_MODE, DSPPlugin.DSP_MODE_CONNECT);
		wc.setAttribute(DSPPlugin.ATTR_DSP_MONITOR_DEBUG_ADAPTER, true);
		wc.setAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, "localhost"); // TODO configure
		wc.setAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, AcceleoDebugPlugin.PORT); // TODO configure

		if (ILaunchManager.PROFILE_MODE.equals(mode)) {
			// FIXME lsp4e doesn't profile mode at the moment.
			super.launch(wc.doSave(), ILaunchManager.RUN_MODE, launch, monitor);
		} else {
			super.launch(wc.doSave(), mode, launch, monitor);
		}
	}

}
