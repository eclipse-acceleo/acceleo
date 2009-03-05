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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * The builder compiles the Acceleo templates in a background task. Compilation errors are put in the problems
 * view when it is necessary.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoBuilder extends IncrementalProjectBuilder {

	/**
	 * The builder ID.
	 */
	public static final String BUILDER_ID = "org.eclipse.acceleo.ide.ui.acceleoBuilder"; //$NON-NLS-1$

	/**
	 * The output folder to ignore.
	 */
	private IPath outputFolder;

	/**
	 * Constructor.
	 */
	public AcceleoBuilder() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected IProject[] build(int kind, Map arguments, IProgressMonitor monitor) throws CoreException {
		if (getProject() == null || !getProject().isAccessible()) {
			return new IProject[] {};
		} else {
			outputFolder = getOutputFolder(getProject());
			try {
				if (kind == FULL_BUILD) {
					clean(monitor);
					fullBuild(monitor);
				} else {
					IResourceDelta delta = getDelta(getProject());
					if (delta == null) {
						clean(monitor);
						fullBuild(monitor);
					} else {
						incrementalBuild(delta, monitor);
					}
				}
			} catch (OperationCanceledException e) {
				// continue
			} finally {
				outputFolder = null;
			}
			return null;
		}
	}

	/**
	 * It does a full build.
	 * 
	 * @param monitor
	 *            is the progress monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	protected void fullBuild(IProgressMonitor monitor) throws CoreException {
		List<IFile> filesOutput = new ArrayList<IFile>();
		members(filesOutput, getProject());
		if (filesOutput.size() > 0) {
			IFile[] files = filesOutput.toArray(new IFile[filesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, false);
			compileOperation.run(monitor);
		}
	}

	/**
	 * It does an incremental build.
	 * 
	 * @param delta
	 *            is the resource delta
	 * @param monitor
	 *            is the progress monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		List<IFile> deltaFilesOutput = new ArrayList<IFile>();
		deltaMembers(deltaFilesOutput, delta, monitor);
		if (deltaFilesOutput.size() > 0) {
			boolean containsManifest = false;
			for (int i = 0; !containsManifest && i < deltaFilesOutput.size(); i++) {
				containsManifest = "MANIFEST.MF".equals(deltaFilesOutput.get(i).getName()); //$NON-NLS-1$
			}
			if (containsManifest) {
				deltaFilesOutput.clear();
				members(deltaFilesOutput, getProject());
			} else {
				computeOtherFilesToBuild(deltaFilesOutput);
			}
			IFile[] files = deltaFilesOutput.toArray(new IFile[deltaFilesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, false);
			compileOperation.run(monitor);
		}
	}

	/**
	 * Gets also the files that depend of the templates to build.
	 * 
	 * @param deltaFiles
	 *            is an output parameter to get all the templates to build
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void computeOtherFilesToBuild(List<IFile> deltaFiles) throws CoreException {
		List<IFile> otherTemplates = new ArrayList<IFile>();
		members(otherTemplates, getProject());
		List<Sequence> importSequencesToSearch = new ArrayList<Sequence>();
		for (int i = 0; i < deltaFiles.size(); i++) {
			IFile deltaFile = deltaFiles.get(i);
			if (IAcceleoConstants.MTL_FILE_EXTENSION.equals(deltaFile.getFileExtension())) {
				String[] tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT,
						new Path(deltaFile.getName()).removeFileExtension().lastSegment(),};
				importSequencesToSearch.add(new Sequence(tokens));
				otherTemplates.remove(deltaFile);
			}
		}
		List<IFile> otherTemplatesToBuild = getOtherTemplatesToBuild(otherTemplates, importSequencesToSearch);
		while (otherTemplatesToBuild.size() > 0) {
			for (int i = 0; i < otherTemplatesToBuild.size(); i++) {
				IFile otherTemplateToBuild = otherTemplatesToBuild.get(i);
				otherTemplates.remove(otherTemplateToBuild);
				if (!deltaFiles.contains(otherTemplateToBuild)) {
					deltaFiles.add(otherTemplateToBuild);
					String[] tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN,
							IAcceleoConstants.IMPORT,
							new Path(otherTemplateToBuild.getName()).removeFileExtension().lastSegment(),};
					Sequence importSequence = new Sequence(tokens);
					importSequencesToSearch.add(importSequence);
				}
			}
			otherTemplatesToBuild = getOtherTemplatesToBuild(otherTemplates, importSequencesToSearch);
		}
	}

	/**
	 * Gets the files that import the given dependencies.
	 * 
	 * @param otherTemplates
	 *            are the other templates that we can decide to build
	 * @param importSequencesToSearch
	 *            are the dependencies to detect in the "import" section of the other templates
	 * @return the other templates to build
	 */
	private List<IFile> getOtherTemplatesToBuild(List<IFile> otherTemplates,
			List<Sequence> importSequencesToSearch) {
		AcceleoProject acceleoProject = new AcceleoProject(getProject());
		List<IFile> result = new ArrayList<IFile>();
		for (int i = 0; i < otherTemplates.size(); i++) {
			IFile otherTemplate = otherTemplates.get(i);
			IPath outputPath = acceleoProject.getOutputFilePath(otherTemplate);
			if (outputPath != null && !getProject().getFile(outputPath.removeFirstSegments(1)).exists()) {
				result.add(otherTemplate);
			} else {
				StringBuffer otherTemplateContent = FileContent.getFileContent(otherTemplate.getLocation()
						.toFile());
				for (int j = 0; j < importSequencesToSearch.size(); j++) {
					Sequence importSequence = importSequencesToSearch.get(j);
					if (importSequence.search(otherTemplateContent).b() > -1) {
						result.add(otherTemplate);
					}
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
		List<IFile> filesOutput = new ArrayList<IFile>();
		members(filesOutput, getProject());
		if (filesOutput.size() > 0) {
			IFile[] files = filesOutput.toArray(new IFile[filesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, true);
			compileOperation.run(monitor);
		}
	}

	/**
	 * Computes a list of all the modified files (Acceleo files only).
	 * 
	 * @param deltaFilesOutput
	 *            an output parameter to get all the modified files
	 * @param delta
	 *            the resource delta represents changes in the state of a resource tree
	 * @param monitor
	 *            is the monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void deltaMembers(List<IFile> deltaFilesOutput, IResourceDelta delta, IProgressMonitor monitor)
			throws CoreException {
		if (delta != null) {
			IResource resource = delta.getResource();
			if (resource instanceof IFile) {
				if (delta.getKind() == IResourceDelta.REMOVED
						&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension())) {
					removeOutputFile((IFile)resource, monitor);
				}
				if (delta.getKind() != IResourceDelta.REMOVED
						&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension())) {
					deltaFilesOutput.add((IFile)resource);
				}
			} else {
				if (outputFolder == null || !outputFolder.isPrefixOf(resource.getFullPath())) {
					IResourceDelta[] children = delta.getAffectedChildren();
					for (int i = 0; i < children.length; i++) {
						deltaMembers(deltaFilesOutput, children[i], monitor);
					}
				}
			}
		}
	}

	/**
	 * Removes the output file that corresponding to the input file.
	 * 
	 * @param inputFile
	 *            is the input file ('.acceleo')
	 * @param monitor
	 *            is the monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void removeOutputFile(IFile inputFile, IProgressMonitor monitor) throws CoreException {
		AcceleoProject acceleoProject = new AcceleoProject(getProject());
		IPath outputPath = acceleoProject.getOutputFilePath(inputFile);
		IResource outputFile = ResourcesPlugin.getWorkspace().getRoot().findMember(outputPath);
		if (outputFile instanceof IFile && outputFile.isAccessible()) {
			outputFile.delete(true, monitor);
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
							&& (outputFolder == null || !outputFolder.isPrefixOf(resource.getFullPath()))) {
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
	private IPath getOutputFolder(IProject aProject) {
		final IJavaProject javaProject = JavaCore.create(aProject);
		try {
			IPath output = javaProject.getOutputLocation();
			if (output != null && output.segmentCount() > 1) {
				IFolder folder = aProject.getWorkspace().getRoot().getFolder(output);
				if (folder.isAccessible()) {
					return folder.getFullPath();
				}
			}
		} catch (JavaModelException e) {
			// continue
		}
		return null;
	}

}
