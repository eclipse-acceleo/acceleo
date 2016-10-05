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

import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * Class responsible for interacting with the workspace.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public class WorkspaceHandler {
	/**
	 * Child filename used to detect the fact a folder is an Eclipse Project.
	 */
	private static final String DOT_PROJECT = ".project";

	/**
	 * Clear the workspace of every project without deleting the project content files.
	 * 
	 * @param monitor
	 *            a progress monitor to keep track of the progress.
	 */
	public void clearWorkspace(Monitor monitor) {
		IWorkspaceRoot root = EcorePlugin.getWorkspaceRoot();
		if (root != null) {
			for (IProject prj : root.getProjects()) {
				try {
					prj.delete(false, true, BasicMonitor.toIProgressMonitor(monitor));
				} catch (CoreException e) {
					AcceleoEquinoxLauncherPlugin.INSTANCE.log(e);
				}
			}
		}

	}

	/**
	 * Recursively search projects and import those in the workspace. This method stops at the first project
	 * found and does not search for projects nested into another project.
	 * 
	 * @param rootFolder
	 *            a pathname used to denote the folder.
	 * @param monitor
	 *            a progress monitor to keep track of the progress.
	 */
	public void importProjectsInWorkspace(String rootFolder, Monitor monitor) {
		File folder = new File(rootFolder);
		if (!hasProjectDescriptor(folder)) {

			File[] children = folder.listFiles();

			if (children != null) {
				recursiveImport(children, monitor);
			}
		} else {
			importExistingProject(folder, monitor);
		}

	}

	/**
	 * Search for project in the given files and their childrens and import any Eclipse project which is
	 * found.
	 * 
	 * @param children
	 *            files to search in.
	 * @param monitor
	 *            a progress monitor to keep track of the progress.
	 */
	private void recursiveImport(File[] children, Monitor monitor) {
		for (File child : children) {
			if (hasProjectDescriptor(child)) {
				importExistingProject(child, monitor);
			} else if (child.listFiles() != null) {
				recursiveImport(child.listFiles(), monitor);
			}
		}
	}

	/**
	 * Import an existing project in the workspace.
	 * 
	 * @param folder
	 *            the folder corresponding to the project location.
	 * @param monitor
	 *            a progress monitor to keep track of the progress.
	 */
	private void importExistingProject(File folder, Monitor monitor) {
		IWorkspaceRoot root = EcorePlugin.getWorkspaceRoot();
		if (root != null) {
			try {
				Path projectDescriptionFile = new Path(folder.getAbsolutePath() + "/.project");
				monitor.subTask("Importing project based on file : " + projectDescriptionFile.toOSString());
				IProjectDescription desc = ResourcesPlugin.getWorkspace().loadProjectDescription(
						projectDescriptionFile);
				IProject prj = root.getProject(desc.getName());
				prj.create(desc, BasicMonitor.toIProgressMonitor(monitor));
				prj.open(BasicMonitor.toIProgressMonitor(monitor));
			} catch (CoreException e) {
				AcceleoEquinoxLauncherPlugin.INSTANCE.log(e);
			}
		}

	}

	/**
	 * return true if the given folder is an Eclipse project.
	 * 
	 * @param folder
	 *            any folder.
	 * @return true if the given folder is an Eclipse project.
	 */
	private boolean hasProjectDescriptor(File folder) {

		String[] projectDescriptorFile = folder.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return DOT_PROJECT.equals(name);
			}
		});
		return projectDescriptorFile != null && projectDescriptorFile.length > 0;
	}
}
