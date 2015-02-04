/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.ide.authoring;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Workspace-specific utilities.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class WorkspaceUtils {
	/**
	 * utility class.
	 */
	private WorkspaceUtils() {

	}

	/**
	 * This will try and resolve the given path against a workspace file. The path can either be relative to
	 * the workpace, an absolute path towards a workspace file or represent an uri of platform scheme.
	 * 
	 * @param path
	 *            The path we seek a file for. Cannot be <code>null</code>.
	 * @return The resolved file.
	 * @throws IOException
	 *             This will be throw if we cannot resolve a "platform://plugin" URI to an existing file.
	 */
	public static File getWorkspaceFile(String path) throws IOException {
		final String platformResourcePrefix = "platform:/resource/"; //$NON-NLS-1$
		final String platformPluginPrefix = "platform:/plugin/"; //$NON-NLS-1$

		final File soughtFile;
		if (path.startsWith(platformResourcePrefix)) {
			final IPath relativePath = new Path(path.substring(platformResourcePrefix.length()));
			final IFile soughtIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(relativePath);
			soughtFile = soughtIFile.getLocation().toFile();
		} else if (path.startsWith(platformPluginPrefix)) {
			final int bundleNameEnd = path.indexOf('/', platformPluginPrefix.length() + 1);
			final String bundleName = path.substring(platformPluginPrefix.length(), bundleNameEnd);
			Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				final URL bundleFileURL = bundle.getEntry(path.substring(bundleNameEnd));
				final URL fileURL = FileLocator.toFileURL(bundleFileURL);
				soughtFile = new File(fileURL.getFile());
			} else {
				/*
				 * Being here means that the bundle id is different than the bundle name. We could try and
				 * find the corresponding bundle in the PluginRegistry.getActiveModels(false) list, but is it
				 * worth the trouble? Most cases should be handled by the previous code and going through such
				 * a loop would be extremely CPU intensive.
				 */
				soughtFile = null;
			}
		} else {
			final IPath workspaceLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			IPath fullPath = new Path(path);
			if (workspaceLocation.isPrefixOf(fullPath)) {
				fullPath = fullPath.removeFirstSegments(workspaceLocation.segmentCount());
			}
			final IFile soughtIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath);
			soughtFile = soughtIFile.getLocation().toFile();
		}
		return soughtFile;
	}
}
