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
package org.eclipse.acceleo.internal.ide.ui.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * An ANT task to compile the Acceleo templates in a background task. Compilation errors are thrown when it is
 * necessary. The ID of the ANT task is 'acceleoCompiler'.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoBuilderTask extends Task {

	/**
	 * The project to build.
	 */
	private IProject project;

	/**
	 * The Java output folder of the project.
	 */
	private IFolder bin;

	/**
	 * The target folder where to put the EMTL files.
	 */
	private File target;

	/**
	 * Sets the project to build.
	 * 
	 * @param projectPath
	 *            is the absolute path of the project to build
	 */
	public void setProject(String projectPath) {
		String projectName;
		if (projectPath != null) {
			projectName = new Path(projectPath).lastSegment();
		} else {
			projectName = null;
		}
		if (projectName != null
				&& ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).isAccessible()) {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			this.bin = getOutputFolder(project);
		} else {
			this.project = null;
			this.bin = null;
		}

	}

	/**
	 * Sets the target folder where to put the EMTL files.
	 * 
	 * @param targetPath
	 *            is the absolute path of the target folder
	 */
	public void setTarget(String targetPath) {
		File folder = new File(targetPath);
		if (folder.exists() && folder.isDirectory()) {
			target = folder;
		} else {
			target = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		try {
			executeAndThrowMessages();
		} catch (BuildException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage()));
			throw e;
		}
	}

	/**
	 * Let the task do its work and throw the compilation messages.
	 * 
	 * @exception BuildException
	 *                if something goes wrong with the build
	 */
	private void executeAndThrowMessages() throws BuildException {
		if (project == null) {
			throw new BuildException(AcceleoUIMessages.getString("AcceleoBuilderTask.ProjectIssue"));
		} else if (bin == null) {
			throw new BuildException(AcceleoUIMessages.getString("AcceleoBuilderTask.BinIssue"));
		} else if (target == null) {
			throw new BuildException(AcceleoUIMessages.getString("AcceleoBuilderTask.TargetIssue"));
		}
		try {
			String messages = fullBuild();
			Project p = new Project();
			Copy copy = new Copy();
			copy.setProject(p);
			copy.setTodir(target);
			FileSet fs = new FileSet();
			fs.setProject(p);
			fs.setDir(bin.getLocation().toFile());
			fs.setIncludes("**/*.emtl"); //$NON-NLS-1$
			fs.setExcludes("**/*.class"); //$NON-NLS-1$
			copy.addFileset(fs);
			copy.execute();
			if (messages.length() > 0) {
				throw new BuildException(messages);
			}
		} catch (CoreException e) {
			throw new BuildException(e);
		}
	}

	/**
	 * It does a full build.
	 * 
	 * @return the compilation messages, or an empty string if there isn't any syntax problem
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private String fullBuild() throws CoreException {
		List<IFile> filesOutput = new ArrayList<IFile>();
		members(filesOutput, project);
		if (filesOutput.size() > 0) {
			Collections.sort(filesOutput, new Comparator<IFile>() {
				public int compare(IFile arg0, IFile arg1) {
					long m0 = arg0.getLocation().toFile().lastModified();
					long m1 = arg1.getLocation().toFile().lastModified();
					if (m0 < m1) {
						return 1;
					} else {
						return -1;
					}
				}
			});
			IFile[] files = filesOutput.toArray(new IFile[filesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(project, files, false);
			compileOperation.run(new NullProgressMonitor());
			return compileOperation.getMessages();
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * Returns a list of existing member files (Acceleo files only) in this resource.
	 * 
	 * @param filesOutput
	 *            an output parameter to get all the Acceleo files
	 * @param container
	 *            is the container to browse
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void members(List<IFile> filesOutput, IContainer container) throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile
							&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)resource)
									.getFileExtension())) {
						filesOutput.add((IFile)resource);
					} else if (resource instanceof IContainer
							&& (bin == null || !bin.getFullPath().isPrefixOf(resource.getFullPath()))) {
						members(filesOutput, (IContainer)resource);
					}
				}
			}
		}
	}

	/**
	 * Gets the output folder of the project. For example : '/MyProject/bin'.
	 * 
	 * @param aProject
	 *            is a project of the workspace
	 * @return the output folder of the project, or null if it doesn't exist
	 */
	private IFolder getOutputFolder(IProject aProject) {
		final IJavaProject javaProject = JavaCore.create(aProject);
		try {
			IPath output = javaProject.getOutputLocation();
			if (output != null && output.segmentCount() > 1) {
				IFolder folder = aProject.getWorkspace().getRoot().getFolder(output);
				if (folder.isAccessible()) {
					return folder;
				}
			}
		} catch (JavaModelException e) {
			// continue
		}
		return null;
	}

}
