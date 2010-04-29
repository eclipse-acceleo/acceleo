/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.builders;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.runner.CreateBuildAcceleoWriter;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.parser.AcceleoFile;
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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
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
	@SuppressWarnings("rawtypes")
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
		members(filesOutput, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION);
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
			registerAccessibleEcoreFiles();
			IFile[] files = filesOutput.toArray(new IFile[filesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, false);
			compileOperation.run(monitor);
			validateAcceleoBuildFile(monitor);
		}
	}

	/**
	 * Register the accessible workspace ecore files.
	 * 
	 * @throws CoreException
	 *             when an issue occurs
	 */
	private void registerAccessibleEcoreFiles() throws CoreException {
		// TODO JMU : Builder temporary fix
		// The full build is able to register all the ecore files of the workspace
		List<IFile> ecoreFiles = new ArrayList<IFile>();
		AcceleoProject acceleoProject = new AcceleoProject(getProject());
		for (IProject project : acceleoProject.getRecursivelyAccessibleProjects()) {
			if (project.isAccessible()) {
				members(ecoreFiles, project, "ecore"); //$NON-NLS-1$
			}
		}
		for (IFile ecoreFile : ecoreFiles) {
			ModelUtils.registerEcorePackages(ecoreFile.getFullPath().toString());
		}
	}

	/**
	 * It checks the build configuration of the Acceleo module. It creates the build.acceleo file if it
	 * doesn't exist.
	 * 
	 * @param monitor
	 *            is the monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void validateAcceleoBuildFile(IProgressMonitor monitor) throws CoreException {
		IFile buildProperties = getProject().getFile("build.properties"); //$NON-NLS-1$
		if (buildProperties.exists() && outputFolder != null && outputFolder.segmentCount() > 1) {
			IFile buildAcceleo = getProject().getFile("build.acceleo"); //$NON-NLS-1$
			AcceleoProject project = new AcceleoProject(getProject());
			List<IProject> dependencies = project.getRecursivelyAccessibleProjects();
			dependencies.remove(getProject());
			CreateBuildAcceleoWriter buildWriter = new CreateBuildAcceleoWriter();
			String buildText = buildWriter.generate(new Object[] {dependencies, });
			if (!buildAcceleo.exists()
					|| !buildText.equals(FileContent.getFileContent(buildAcceleo.getLocation().toFile())
							.toString())) {
				try {
					ByteArrayInputStream javaStream = new ByteArrayInputStream(buildText.getBytes("UTF8")); //$NON-NLS-1$
					if (!buildAcceleo.exists()) {
						buildAcceleo.create(javaStream, true, monitor);
					} else {
						buildAcceleo.setContents(javaStream, true, false, monitor);
					}
				} catch (UnsupportedEncodingException e) {
					throw new CoreException(new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e
							.getMessage(), e));
				}
			}
			if (FileContent.getFileContent(buildProperties.getLocation().toFile()).indexOf(
					buildAcceleo.getName()) == -1) {
				AcceleoUIActivator
						.getDefault()
						.getLog()
						.log(
								new Status(
										IStatus.ERROR,
										AcceleoUIActivator.PLUGIN_ID,
										AcceleoUIMessages
												.getString(
														"AcceleoBuilder.AcceleoBuildFileIssue", new Object[] {getProject().getName() }))); //$NON-NLS-1$
			}
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
				members(deltaFilesOutput, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION);
			} else {
				computeOtherFilesToBuild(deltaFilesOutput);
			}
		} else {
			computeOtherFilesToBuild(deltaFilesOutput);
		}
		if (deltaFilesOutput.size() > 0) {
			Collections.sort(deltaFilesOutput, new Comparator<IFile>() {
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
			registerAccessibleEcoreFiles();
			IFile[] files = deltaFilesOutput.toArray(new IFile[deltaFilesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, false);
			compileOperation.run(monitor);
			validateAcceleoBuildFile(monitor);
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
		AcceleoProject acceleoProject = new AcceleoProject(getProject());
		List<IFile> otherTemplates = new ArrayList<IFile>();
		members(otherTemplates, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION);
		List<Sequence> importSequencesToSearch = new ArrayList<Sequence>();
		for (int i = 0; i < deltaFiles.size(); i++) {
			IFile deltaFile = deltaFiles.get(i);
			if (IAcceleoConstants.MTL_FILE_EXTENSION.equals(deltaFile.getFileExtension())) {
				importSequencesToSearch.addAll(getImportSequencesToSearch(acceleoProject, deltaFile));
				otherTemplates.remove(deltaFile);
			}
		}
		List<IFile> otherTemplatesToBuild = getOtherTemplatesToBuild(acceleoProject, otherTemplates,
				importSequencesToSearch);
		while (otherTemplatesToBuild.size() > 0) {
			for (int i = 0; i < otherTemplatesToBuild.size(); i++) {
				IFile otherTemplateToBuild = otherTemplatesToBuild.get(i);
				otherTemplates.remove(otherTemplateToBuild);
				if (!deltaFiles.contains(otherTemplateToBuild)) {
					deltaFiles.add(otherTemplateToBuild);
					importSequencesToSearch.addAll(getImportSequencesToSearch(acceleoProject,
							otherTemplateToBuild));
				}
			}
			otherTemplatesToBuild = getOtherTemplatesToBuild(acceleoProject, otherTemplates,
					importSequencesToSearch);
		}
	}

	/**
	 * Gets all the possible sequences to search when we use the given file in other files : '[import myGen',
	 * '[import org::eclipse::myGen', 'extends myGen', 'extends org::eclipse::myGen'...
	 * 
	 * @param acceleoProject
	 *            is the project
	 * @param file
	 *            is the MTL file
	 * @return all the possible sequences to search for the given file
	 */
	private List<Sequence> getImportSequencesToSearch(AcceleoProject acceleoProject, IFile file) {
		List<Sequence> result = new ArrayList<Sequence>();
		String simpleModuleName = new Path(file.getName()).removeFileExtension().lastSegment();
		String[] tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT,
				simpleModuleName, };
		result.add(new Sequence(tokens));
		tokens = new String[] {IAcceleoConstants.EXTENDS, simpleModuleName, };
		result.add(new Sequence(tokens));
		String javaPackageName = acceleoProject.getPackageName(file);
		String fullModuleName = AcceleoFile.javaPackageToFullModuleName(javaPackageName, simpleModuleName);
		tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT, fullModuleName, };
		result.add(new Sequence(tokens));
		tokens = new String[] {IAcceleoConstants.EXTENDS, fullModuleName, };
		result.add(new Sequence(tokens));
		return result;
	}

	/**
	 * Gets the files that import the given dependencies.
	 * 
	 * @param acceleoProject
	 *            is the project
	 * @param otherTemplates
	 *            are the other templates that we can decide to build
	 * @param importSequencesToSearch
	 *            are the dependencies to detect in the "import" section of the other templates
	 * @return the other templates to build
	 */
	private List<IFile> getOtherTemplatesToBuild(AcceleoProject acceleoProject, List<IFile> otherTemplates,
			List<Sequence> importSequencesToSearch) {
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
		members(filesOutput, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION);
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
						&& (IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension()) || "MANIFEST.MF" //$NON-NLS-1$
						.equals(resource.getName()))) {
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
	 * Returns a list of existing member files (that validate the file extension) in this resource.
	 * 
	 * @param filesOutput
	 *            an output parameter to get all the files
	 * @param container
	 *            is the container to browse
	 * @param extension
	 *            is the extension
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void members(List<IFile> filesOutput, IContainer container, String extension)
			throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile && extension.equals(((IFile)resource).getFileExtension())) {
						filesOutput.add((IFile)resource);
					} else if (resource instanceof IContainer
							&& (outputFolder == null || !outputFolder.isPrefixOf(resource.getFullPath()))) {
						members(filesOutput, (IContainer)resource, extension);
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
