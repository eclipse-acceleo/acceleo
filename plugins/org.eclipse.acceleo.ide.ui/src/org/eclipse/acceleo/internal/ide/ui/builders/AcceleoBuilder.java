/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.builders;

import com.google.common.collect.Sets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProjectClasspathEntry;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
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
		IProject project = getProject();
		if (project == null || !project.isAccessible()) {
			return new IProject[] {};
		}

		File projectRoot = project.getLocation().toFile();
		org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
				projectRoot);

		IJavaProject javaProject = JavaCore.create(project);
		acceleoProject = AcceleoBuilder.computeProjectClassPath(acceleoProject, javaProject);
		acceleoProject = AcceleoBuilder.computeProjectDependencies(acceleoProject, javaProject);

		AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
		String resourceKind = settings.getResourceKind();
		boolean useBinaryResources = !AcceleoBuilderSettings.BUILD_XMI_RESOURCE.equals(resourceKind);

		if (kind == IncrementalProjectBuilder.INCREMENTAL_BUILD
				|| kind == IncrementalProjectBuilder.AUTO_BUILD) {
			List<IFile> deltaMembers = this.deltaMembers(getDelta(project), monitor);
			org.eclipse.acceleo.internal.parser.compiler.AcceleoParser acceleoParser = new org.eclipse.acceleo.internal.parser.compiler.AcceleoParser(
					acceleoProject, useBinaryResources);
			for (IFile iFile : deltaMembers) {
				File fileToBuild = iFile.getLocation().toFile();
				acceleoParser.buildFile(fileToBuild, BasicMonitor.toMonitor(monitor));
			}
		} else if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			acceleoProject.clean();
			org.eclipse.acceleo.internal.parser.compiler.AcceleoParser acceleoParser = new org.eclipse.acceleo.internal.parser.compiler.AcceleoParser(
					acceleoProject, useBinaryResources);
			acceleoParser.buildAll(BasicMonitor.toMonitor(monitor));
		} else if (kind == IncrementalProjectBuilder.CLEAN_BUILD) {
			acceleoProject.clean();
		}

		// Ensure that we didn't forget to build a file out of the dependency graph of the file(s) currently
		// built, this can occur if two files are not related at all and we force the build of only one of
		// those files.
		Set<File> fileNotCompiled = acceleoProject.getFileNotCompiled();
		for (File fileToBuild : fileNotCompiled) {
			org.eclipse.acceleo.internal.parser.compiler.AcceleoParser acceleoParser = new org.eclipse.acceleo.internal.parser.compiler.AcceleoParser(
					acceleoProject, useBinaryResources);
			acceleoParser.buildFile(fileToBuild, BasicMonitor.toMonitor(monitor));
		}

		// Refresh all the projects potentially containing files.
		Set<org.eclipse.acceleo.internal.parser.compiler.AcceleoProject> projectsToRefresh = Sets
				.newHashSet(acceleoProject);
		projectsToRefresh.addAll(acceleoProject.getProjectDependencies());
		projectsToRefresh.addAll(acceleoProject.getDependentProjects());
		for (org.eclipse.acceleo.internal.parser.compiler.AcceleoProject projectToRefresh : projectsToRefresh) {
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject iProject : projects) {
				if (iProject.isAccessible()
						&& projectToRefresh.getProjectRoot().equals(iProject.getLocation().toFile())) {
					iProject.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				}
			}
		}

		return null;
	}

	/**
	 * Computes the project dependencies of the given Acceleo project matching the given Java project.
	 * 
	 * @param acceleoProject
	 *            The Acceleo project.
	 * @param javaProject
	 *            The Java project.
	 * @return The Acceleo project with its dependencies resolved.
	 */
	private static org.eclipse.acceleo.internal.parser.compiler.AcceleoProject computeProjectDependencies(
			org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject,
			IJavaProject javaProject) {
		try {
			// Required projects
			String[] requiredProjectNames = javaProject.getRequiredProjectNames();
			for (String requiredProjectName : requiredProjectNames) {
				IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
						requiredProjectName);
				try {
					if (requiredProject.isAccessible() && requiredProject.hasNature(JavaCore.NATURE_ID)
							&& requiredProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
						IJavaProject requiredJavaProject = JavaCore.create(requiredProject);
						File projectRoot = requiredProject.getLocation().toFile();

						org.eclipse.acceleo.internal.parser.compiler.AcceleoProject requiredAcceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
								projectRoot);
						requiredAcceleoProject = AcceleoBuilder.computeProjectClassPath(
								requiredAcceleoProject, requiredJavaProject);
						if (!acceleoProject.getProjectDependencies().contains(requiredAcceleoProject)) {
							acceleoProject.addProjectDependencies(Sets.newHashSet(requiredAcceleoProject));
							requiredAcceleoProject = AcceleoBuilder.computeProjectDependencies(
									requiredAcceleoProject, requiredJavaProject);
						}

					}
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}

			// Requiring projects
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject iProject : projects) {
				try {
					if (iProject.isAccessible() && iProject.hasNature(JavaCore.NATURE_ID)
							&& iProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
						IJavaProject iJavaProject = JavaCore.create(iProject);
						boolean requiring = false;

						String[] projectNames = iJavaProject.getRequiredProjectNames();
						for (String projectName : projectNames) {
							IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
									projectName);
							if (acceleoProject.getProjectRoot().equals(project.getLocation().toFile())) {
								requiring = true;
							}
						}

						if (requiring) {
							org.eclipse.acceleo.internal.parser.compiler.AcceleoProject requiringAcceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
									iProject.getLocation().toFile());
							requiringAcceleoProject = AcceleoBuilder.computeProjectClassPath(
									requiringAcceleoProject, iJavaProject);
							if (!acceleoProject.getDependentProjects().contains(requiringAcceleoProject)) {
								acceleoProject.addDependentProjects(Sets.newHashSet(requiringAcceleoProject));
								requiringAcceleoProject = AcceleoBuilder.computeProjectDependencies(
										requiringAcceleoProject, iJavaProject);
							}
						}

					}
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		} catch (JavaModelException e) {
			AcceleoUIActivator.log(e, true);
		}
		return acceleoProject;
	}

	/**
	 * Computes the classpath for the given Acceleo project from the given java project.
	 * 
	 * @param acceleoProject
	 *            The Acceleo project
	 * @param javaProject
	 *            The Java project
	 * @return The Acceleo project matching the given Java project with its classpath set.
	 */
	private static org.eclipse.acceleo.internal.parser.compiler.AcceleoProject computeProjectClassPath(
			org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject,
			IJavaProject javaProject) {
		Set<AcceleoProjectClasspathEntry> classpathEntries = new LinkedHashSet<AcceleoProjectClasspathEntry>();

		// Compute the classpath of the acceleo project
		IClasspathEntry[] rawClasspath;
		try {
			rawClasspath = javaProject.getRawClasspath();
			for (IClasspathEntry iClasspathEntry : rawClasspath) {
				int entryKind = iClasspathEntry.getEntryKind();
				if (IClasspathEntry.CPE_SOURCE == entryKind) {
					// We have the source folders of the project.
					IPath inputFolderPath = iClasspathEntry.getPath();
					IPath outputFolderPath = iClasspathEntry.getOutputLocation();

					if (outputFolderPath == null) {
						outputFolderPath = javaProject.getOutputLocation();
					}

					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
							inputFolderPath.lastSegment());
					if (!(project != null && project.exists() && project.equals(javaProject.getProject()))) {
						IContainer inputContainer = ResourcesPlugin.getWorkspace().getRoot().getFolder(
								inputFolderPath);
						IContainer outputContainer = ResourcesPlugin.getWorkspace().getRoot().getFolder(
								outputFolderPath);

						if (inputContainer != null && outputContainer != null) {
							File inputDirectory = inputContainer.getLocation().toFile();
							File outputDirectory = outputContainer.getLocation().toFile();
							AcceleoProjectClasspathEntry entry = new AcceleoProjectClasspathEntry(
									inputDirectory, outputDirectory);
							classpathEntries.add(entry);
						}
					}
				}
			}
			acceleoProject.addClasspathEntries(classpathEntries);
		} catch (JavaModelException e) {
			AcceleoUIActivator.log(e, true);
		}
		return acceleoProject;
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
		AcceleoBuilderUtils.members(filesOutput, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION,
				outputFolder);
		if (filesOutput.size() > 0) {
			Collections.sort(filesOutput, new Comparator<IFile>() {
				public int compare(IFile arg0, IFile arg1) {
					long m0 = arg0.getLocation().toFile().lastModified();
					long m1 = arg1.getLocation().toFile().lastModified();
					if (m0 < m1) {
						return 1;
					}
					return -1;
				}
			});
			registerAccessibleEcoreFiles();
			IFile[] files = filesOutput.toArray(new IFile[filesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, false);
			compileOperation.run(monitor);
			generateAcceleoBuildFile(monitor);
		}
	}

	/**
	 * Register the accessible workspace ecore files.
	 * 
	 * @throws CoreException
	 *             when an issue occurs
	 */
	private void registerAccessibleEcoreFiles() throws CoreException {
		List<IFile> ecoreFiles = new ArrayList<IFile>();
		AcceleoProject acceleoProject = new AcceleoProject(getProject());
		for (IProject project : acceleoProject.getRecursivelyAccessibleProjects()) {
			if (project.isAccessible()) {
				AcceleoBuilderUtils.members(ecoreFiles, project, "ecore", outputFolder); //$NON-NLS-1$
			}
		}
		for (IFile ecoreFile : ecoreFiles) {
			AcceleoPackageRegistry.INSTANCE.registerEcorePackages(ecoreFile.getFullPath().toString(),
					AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
		}
	}

	/**
	 * It checks the build configuration of the Acceleo module. It creates the build.acceleo file, the
	 * build.xml file and the pom.xm file if they don't exist.
	 * 
	 * @param monitor
	 *            is the monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void generateAcceleoBuildFile(IProgressMonitor monitor) throws CoreException {
		IFile buildProperties = getProject().getFile("build.properties"); //$NON-NLS-1$
		if (outputFolder != null && outputFolder.segmentCount() >= 1) {
			IFile buildAcceleo = getProject().getFile("build.acceleo"); //$NON-NLS-1$
			AcceleoProject project = new AcceleoProject(getProject());
			List<IProject> dependencies = project.getRecursivelyAccessibleProjects();
			dependencies.remove(getProject());
			org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE
					.createAcceleoProject();
			List<String> pluginDependencies = acceleoProject.getPluginDependencies();
			for (IProject iProject : dependencies) {
				pluginDependencies.add(iProject.getName());
			}

			AcceleoUIGenerator.getDefault().generateBuildAcceleo(acceleoProject, buildAcceleo.getParent());

			if (buildProperties.exists()
					&& FileContent.getFileContent(buildProperties.getLocation().toFile()).indexOf(
							buildAcceleo.getName()) == -1) {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, AcceleoUIMessages.getString(
								"AcceleoBuilder.AcceleoBuildFileIssue", new Object[] {getProject() //$NON-NLS-1$
										.getName(), })));
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
		List<IFile> deltaFilesOutput = deltaMembers(delta, monitor);
		if (deltaFilesOutput.size() > 0) {
			boolean containsManifest = false;
			for (int i = 0; !containsManifest && i < deltaFilesOutput.size(); i++) {
				containsManifest = "MANIFEST.MF".equals(deltaFilesOutput.get(i).getName()); //$NON-NLS-1$
			}
			if (containsManifest) {
				deltaFilesOutput.clear();
				AcceleoBuilderUtils.members(deltaFilesOutput, getProject(),
						IAcceleoConstants.MTL_FILE_EXTENSION, outputFolder);
			} else {
				computeOtherFilesToBuild(deltaFilesOutput);
			}
		}
		if (deltaFilesOutput.size() > 0) {
			Collections.sort(deltaFilesOutput, new Comparator<IFile>() {
				public int compare(IFile arg0, IFile arg1) {
					long m0 = arg0.getLocation().toFile().lastModified();
					long m1 = arg1.getLocation().toFile().lastModified();
					if (m0 < m1) {
						return 1;
					}
					return -1;
				}
			});
			registerAccessibleEcoreFiles();
			IFile[] files = deltaFilesOutput.toArray(new IFile[deltaFilesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, false);
			compileOperation.run(monitor);
			generateAcceleoBuildFile(monitor);
		} else {
			List<IFile> deltaRemovedFilesOutput = new ArrayList<IFile>();
			deltaRemovedMembers(deltaRemovedFilesOutput, delta, monitor);
			if (deltaRemovedFilesOutput.size() > 0) {
				for (IFile removedFile : deltaRemovedFilesOutput) {
					if ("java".equals(removedFile.getFileExtension())) { //$NON-NLS-1$
						this.fullBuild(monitor);
						break;
					}
				}
			}
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
		AcceleoBuilderUtils.members(otherTemplates, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION,
				outputFolder);
		List<Sequence> importSequencesToSearch = new ArrayList<Sequence>();
		for (int i = 0; i < deltaFiles.size(); i++) {
			IFile deltaFile = deltaFiles.get(i);
			if (IAcceleoConstants.MTL_FILE_EXTENSION.equals(deltaFile.getFileExtension())) {
				importSequencesToSearch.addAll(AcceleoBuilderUtils.getImportSequencesToSearch(acceleoProject,
						deltaFile));
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
					importSequencesToSearch.addAll(AcceleoBuilderUtils.getImportSequencesToSearch(
							acceleoProject, otherTemplateToBuild));
				}
			}
			otherTemplatesToBuild = getOtherTemplatesToBuild(acceleoProject, otherTemplates,
					importSequencesToSearch);
		}
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
		AcceleoBuilderUtils.members(filesOutput, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION,
				outputFolder);
		if (filesOutput.size() > 0) {
			IFile[] files = filesOutput.toArray(new IFile[filesOutput.size()]);
			AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files, true);
			compileOperation.run(monitor);
		}
	}

	/**
	 * Computes a list of all the modified files (Acceleo files only).
	 * 
	 * @param delta
	 *            the resource delta represents changes in the state of a resource tree
	 * @param monitor
	 *            is the monitor
	 * @return The list of files involved in the resource delta.
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private List<IFile> deltaMembers(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		List<IFile> deltaFilesOutput = new ArrayList<IFile>();
		if (delta != null) {
			IResource resource = delta.getResource();
			if (resource instanceof IFile) {
				if (delta.getKind() == IResourceDelta.REMOVED
						&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension())) {
					removeOutputFile((IFile)resource, monitor);
				}
				if (delta.getKind() != IResourceDelta.REMOVED
						&& (IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension()) || "MANIFEST.MF" //$NON-NLS-1$
						.equals(resource.getName())) || "plugin.xml".equals(resource.getName())) { //$NON-NLS-1$
					deltaFilesOutput.add((IFile)resource);
				}
			} else {
				if (outputFolder == null || !outputFolder.isPrefixOf(resource.getFullPath())) {
					IResourceDelta[] children = delta.getAffectedChildren();
					for (int i = 0; i < children.length; i++) {
						deltaFilesOutput.addAll(deltaMembers(children[i], monitor));
					}
				}
			}
		}

		return deltaFilesOutput;
	}

	/**
	 * Computes a list of all the removed files.
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
	private void deltaRemovedMembers(List<IFile> deltaFilesOutput, IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
		if (delta != null) {
			IResource resource = delta.getResource();
			if (resource instanceof IFile) {
				if (delta.getKind() == IResourceDelta.REMOVED) {
					deltaFilesOutput.add((IFile)resource);
				}
			} else {
				if (outputFolder == null || !outputFolder.isPrefixOf(resource.getFullPath())) {
					IResourceDelta[] children = delta.getAffectedChildren();
					for (int i = 0; i < children.length; i++) {
						deltaRemovedMembers(deltaFilesOutput, children[i], monitor);
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
}
