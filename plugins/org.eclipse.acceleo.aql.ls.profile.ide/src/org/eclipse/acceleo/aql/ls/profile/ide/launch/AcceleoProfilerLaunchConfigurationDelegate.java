package org.eclipse.acceleo.aql.ls.profile.ide.launch;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;

import org.eclipse.acceleo.aql.ls.debug.AcceleoDebugger;
import org.eclipse.acceleo.aql.ls.profile.AcceleoProfiler;
import org.eclipse.acceleo.aql.ls.profile.ide.AcceleoProfilePlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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

public class AcceleoProfilerLaunchConfigurationDelegate extends DSPLaunchDelegate {

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
			final IFolder model = root.getFolder(new Path(wc.getAttribute(AcceleoDebugger.DESTINATION,
					(String)null)));
			final URI modelUri = model.getLocation().toFile().getAbsoluteFile().toURI();
			param.addProperty(AcceleoDebugger.DESTINATION, modelUri.toString());
			// TODO [profiler] use value from launch configuration
			param.addProperty(AcceleoProfiler.PROFILE_DESTINATION, model.getLocation().toFile()
					.getAbsoluteFile() + ".mtlp");
		}

		wc.setAttribute(DSPPlugin.ATTR_CUSTOM_LAUNCH_PARAMS, true);
		wc.setAttribute(DSPPlugin.ATTR_DSP_PARAM, new Gson().toJson(param));
		wc.setAttribute(DSPPlugin.ATTR_DSP_MODE, DSPPlugin.DSP_MODE_CONNECT);
		wc.setAttribute(DSPPlugin.ATTR_DSP_MONITOR_DEBUG_ADAPTER, true);
		wc.setAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, "localhost");
		wc.setAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, AcceleoProfilePlugin.PORT);

		super.launch(wc.doSave(), ILaunchManager.RUN_MODE, launch, monitor);
	}
}
