/*******************************************************************************
 * Copyright (c) 2020 Obeo.
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

import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.debug.ide.AcceleoDebugPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
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
		param.addProperty(AcceleoDebugger.MODULE, wc.getAttribute(AcceleoDebugger.MODULE, (String)null));
		param.addProperty(AcceleoDebugger.MODEL, wc.getAttribute(AcceleoDebugger.MODEL, (String)null));
		param.addProperty(AcceleoDebugger.DESTINATION, wc.getAttribute(AcceleoDebugger.DESTINATION,
				(String)null));

		wc.setAttribute(DSPPlugin.ATTR_CUSTOM_LAUNCH_PARAMS, true);
		wc.setAttribute(DSPPlugin.ATTR_DSP_PARAM, new Gson().toJson(param));
		wc.setAttribute(DSPPlugin.ATTR_DSP_MODE, DSPPlugin.DSP_MODE_CONNECT);
		wc.setAttribute(DSPPlugin.ATTR_DSP_MONITOR_DEBUG_ADAPTER, true);
		wc.setAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, "localhost"); // TODO configure
		wc.setAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, AcceleoDebugPlugin.PORT); // TODO configure

		super.launch(wc.doSave(), mode, launch, monitor);
	}

}
