/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.builders;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoModelManager;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoProjectState;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.builders.runner.CreateRunnableAcceleoOperation;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.JavaServicesUtils;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoParser;
import org.eclipse.acceleo.internal.parser.compiler.AcceleoProjectClasspathEntry;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
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
	 * The output folders to ignore.
	 */
	private Set<File> outputFolders = new LinkedHashSet<File>();

	/**
	 * The projects mapped by the builder.
	 */
	private Map<IJavaProject, org.eclipse.acceleo.internal.parser.compiler.AcceleoProject> mappedProjects = new HashMap<IJavaProject, org.eclipse.acceleo.internal.parser.compiler.AcceleoProject>();

	/**
	 * The state of the current Acceleo project.
	 */
	private AcceleoProjectState lastState;

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
		this.mappedProjects.clear();

		monitor.subTask(AcceleoUIMessages.getString(
				"AcceleoBuilder.StartingBuild", project.getName(), Integer.valueOf(0))); //$NON-NLS-1$
		long currentTimeMillis = System.currentTimeMillis();

		// Generate all Acceleo Java Services modules
		List<IFile> javaFiles = this.members(getProject(), "java"); //$NON-NLS-1$
		for (IFile iFile : javaFiles) {
			IJavaElement iJavaElement = JavaCore.create(iFile);
			if (iJavaElement instanceof ICompilationUnit) {
				ICompilationUnit iCompilationUnit = (ICompilationUnit)iJavaElement;
				if (JavaServicesUtils.isAcceleoJavaServicesClass(iCompilationUnit)) {
					JavaServicesUtils.generateAcceleoServicesModule(iCompilationUnit, monitor);
				}
			}
		}

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.ComputeAccessibleEcores", Long //$NON-NLS-1$
				.valueOf(System.currentTimeMillis() - currentTimeMillis)));
		IJavaProject javaProject = JavaCore.create(project);
		Set<AcceleoProjectClasspathEntry> entries = this.computeProjectClassPath(javaProject);

		File projectRoot = project.getLocation().toFile();
		org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
				projectRoot, entries);

		acceleoProject = this.computeProjectDependencies(acceleoProject, javaProject);

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.LoadAccessibleEcores", Long //$NON-NLS-1$
				.valueOf(System.currentTimeMillis() - currentTimeMillis)));
		// Check that all ".ecore" models in accessible projects have been loaded.
		AcceleoProject aProject = new AcceleoProject(project);
		List<IProject> accessibleProjects = new ArrayList<IProject>();
		accessibleProjects = aProject.getRecursivelyAccessibleProjects();
		for (IProject iProject : Lists.reverse(accessibleProjects)) {
			List<IFile> members = this.members(iProject, IAcceleoConstants.ECORE_FILE_EXTENSION);
			for (IFile iFile : members) {
				URI uri = URI.createPlatformResourceURI(iFile.getFullPath().toString(), true);
				AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
						AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
			}
		}

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.ComputeAccessibleAcceleoModules", Long //$NON-NLS-1$
				.valueOf(System.currentTimeMillis() - currentTimeMillis)));
		List<URI> accessibleOutputFiles = AcceleoProject.computeAcceleoModuleInRequiredPlugins(project);
		acceleoProject.addDependencies(Sets.newHashSet(accessibleOutputFiles));
		AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
		String resourceKind = settings.getResourceKind();
		boolean useBinaryResources = !AcceleoBuilderSettings.BUILD_XMI_RESOURCE.equals(resourceKind);
		boolean usePlatformResourcePath = AcceleoBuilderSettings.COMPILATION_PLATFORM_RESOURCE
				.equals(settings.getCompilationKind());
		boolean trimmedPositions = settings.isTrimmedPositions();

		Set<File> mainFiles = new LinkedHashSet<File>();

		this.lastState = this.getLastState(this.getProject());

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.CompilationStart", Long.valueOf(System //$NON-NLS-1$
				.currentTimeMillis() - currentTimeMillis)));

		if (kind == IncrementalProjectBuilder.FULL_BUILD) {
			// Full build -> build all
			mainFiles.addAll(buildAll(acceleoProject, project, useBinaryResources, usePlatformResourcePath,
					trimmedPositions, monitor));
		} else {
			if (this.lastState == null) {
				// No state -> build all
				mainFiles.addAll(buildAll(acceleoProject, project, useBinaryResources,
						usePlatformResourcePath, trimmedPositions, monitor));
			} else if (kind == IncrementalProjectBuilder.INCREMENTAL_BUILD
					|| kind == IncrementalProjectBuilder.AUTO_BUILD) {
				mainFiles.addAll(this.incrementalBuild(acceleoProject, project, useBinaryResources,
						usePlatformResourcePath, trimmedPositions, monitor));
			} else if (kind == IncrementalProjectBuilder.CLEAN_BUILD) {
				acceleoProject.clean();
				this.cleanAcceleoMarkers(project);
			}
		}

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.BuildFileNotCompiled", Long //$NON-NLS-1$
				.valueOf(System.currentTimeMillis() - currentTimeMillis)));
		// Ensure that we didn't forget to build a file out of the dependency graph of the file(s)
		// currently
		// built, this can occur if two files are not related at all and we force the build of only one of
		// those files.
		Set<File> fileNotCompiled = acceleoProject.getFileNotCompiled();
		for (File fileToBuild : fileNotCompiled) {
			AcceleoParser acceleoParser = new AcceleoParser(acceleoProject, useBinaryResources,
					usePlatformResourcePath, trimmedPositions);
			Set<File> builtFiles = acceleoParser.buildFile(fileToBuild, BasicMonitor.toMonitor(monitor));
			this.addAcceleoMarkers(builtFiles, acceleoParser);
			mainFiles.addAll(acceleoParser.getMainFiles());
		}

		// Launch the build of the MANIFEST.MF, Java launcher, build.acceleo etc.
		List<IFile> filesWithMainTag = new ArrayList<IFile>();
		for (File mainFile : mainFiles) {
			IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(mainFile.getAbsolutePath()));
			filesWithMainTag.add(workspaceFile);
		}
		if (filesWithMainTag.size() > 0) {
			monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.GeneratingAcceleoFiles", Long //$NON-NLS-1$
					.valueOf(System.currentTimeMillis() - currentTimeMillis)));
			CreateRunnableAcceleoOperation createRunnableAcceleoOperation = new CreateRunnableAcceleoOperation(
					new AcceleoProject(project), filesWithMainTag);
			createRunnableAcceleoOperation.run(monitor);
		}

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.RefreshingProjects", Long.valueOf(System //$NON-NLS-1$
				.currentTimeMillis() - currentTimeMillis)));
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

		monitor.subTask(AcceleoUIMessages.getString("AcceleoBuilder.GenerateBuildFiles", Long.valueOf(System //$NON-NLS-1$
				.currentTimeMillis() - currentTimeMillis)));
		generateAcceleoBuildFile(monitor);
		monitor.done();

		return accessibleProjects.toArray(new IProject[accessibleProjects.size()]);
	}

	/**
	 * Build all the files in the project.
	 * 
	 * @param acceleoProject
	 *            The Acceleo project
	 * @param project
	 *            The Eclipse IProject
	 * @param useBinaryResources
	 *            Indicates if we should use the binary resources serialization
	 * @param usePlatformResourcePath
	 *            Indicates if we should use platform:/resource paths
	 * @param trimPosition
	 *            Indicates that we will trim the positions from the emtl files.
	 * @param monitor
	 *            The progress monitor
	 * @return The files built
	 */
	private Set<File> buildAll(org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject,
			IProject project, boolean useBinaryResources, boolean usePlatformResourcePath,
			boolean trimPosition, IProgressMonitor monitor) {
		Set<File> filesBuilt = new LinkedHashSet<File>();

		this.clearLastState();

		// acceleoProject.clean();
		this.cleanAcceleoMarkers(project);
		AcceleoParser acceleoParser = new AcceleoParser(acceleoProject, useBinaryResources,
				usePlatformResourcePath, trimPosition);
		Set<File> builtFiles = acceleoParser.buildAll(BasicMonitor.toMonitor(monitor));
		for (File builtFile : builtFiles) {
			IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(builtFile.getAbsolutePath()));
			this.cleanAcceleoMarkers(workspaceFile);
		}
		this.addAcceleoMarkers(builtFiles, acceleoParser);
		filesBuilt.addAll(acceleoParser.getMainFiles());

		AcceleoProjectState state = new AcceleoProjectState();
		state.setProjectName(this.getProject().getName());
		state.setLastStructuralBuildTime(System.currentTimeMillis());
		this.recordNewState(state);

		return filesBuilt;
	}

	/**
	 * Launches an incremental build of the given project.
	 * 
	 * @param acceleoProject
	 *            The Acceleo project
	 * @param project
	 *            The Eclipse IProject
	 * @param useBinaryResources
	 *            Indicates if we should use binary resources serialization
	 * @param usePlatformResourcePath
	 *            Indicates if we should use platform:/resources paths
	 * @param trimPosition
	 *            Indicates that we will trim the positions from the emtl files.
	 * @param monitor
	 *            The progress monitor
	 * @return The list of the files built
	 * @throws CoreException
	 *             In case of problems during the serialization
	 */
	private Set<File> incrementalBuild(
			org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject, IProject project,
			boolean useBinaryResources, boolean usePlatformResourcePath, boolean trimPosition,
			IProgressMonitor monitor) throws CoreException {
		Set<File> filesBuilt = new LinkedHashSet<File>();

		this.clearLastState();

		List<IFile> deltaMembers = this.deltaMembers(getDelta(project), monitor);
		AcceleoParser acceleoParser = new AcceleoParser(acceleoProject, useBinaryResources,
				usePlatformResourcePath, trimPosition);
		Iterator<IFile> it = deltaMembers.iterator();
		while (it.hasNext() && !monitor.isCanceled()) {
			File fileToBuild = it.next().getLocation().toFile();
			Set<File> builtFiles = acceleoParser.buildFile(fileToBuild, BasicMonitor.toMonitor(monitor));
			for (File builtFile : builtFiles) {
				IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
						new Path(builtFile.getAbsolutePath()));
				this.cleanAcceleoMarkers(workspaceFile);
			}
			this.addAcceleoMarkers(builtFiles, acceleoParser);
			filesBuilt.addAll(acceleoParser.getMainFiles());
		}

		AcceleoProjectState state = new AcceleoProjectState();
		state.setProjectName(this.getProject().getName());
		state.setLastStructuralBuildTime(System.currentTimeMillis());
		this.recordNewState(state);

		return filesBuilt;
	}

	/**
	 * Adds the necessary markers on the given built files.
	 * 
	 * @param builtFiles
	 *            The files built by the parser.
	 * @param parser
	 *            The parser.
	 */
	private void addAcceleoMarkers(Set<File> builtFiles, AcceleoParser parser) {
		for (File builtFile : builtFiles) {
			try {
				IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
						new Path(builtFile.getAbsolutePath()));
				Collection<AcceleoParserInfo> infos = parser.getInfos(builtFile);
				for (AcceleoParserInfo info : infos) {
					AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.INFO_MARKER_ID, workspaceFile,
							info.getLine(), info.getPosBegin(), info.getPosEnd(), info.getMessage());
				}
				Collection<AcceleoParserWarning> warnings = parser.getWarnings(builtFile);
				for (AcceleoParserWarning warning : warnings) {
					AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.WARNING_MARKER_ID,
							workspaceFile, warning.getLine(), warning.getPosBegin(), warning.getPosEnd(),
							warning.getMessage());
				}
				Collection<AcceleoParserProblem> problems = parser.getProblems(builtFile);
				for (AcceleoParserProblem problem : problems) {
					AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.PROBLEM_MARKER_ID,
							workspaceFile, problem.getLine(), problem.getPosBegin(), problem.getPosEnd(),
							problem.getMessage());
				}
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}

		}
	}

	/**
	 * Cleans the Acceleo marker from the given resource.
	 * 
	 * @param resource
	 *            The resource.
	 */
	private void cleanAcceleoMarkers(IResource resource) {
		try {
			if (resource.exists() && resource.isAccessible()) {
				resource.deleteMarkers(AcceleoMarkerUtils.PROBLEM_MARKER_ID, true, IResource.DEPTH_INFINITE);
				resource.deleteMarkers(AcceleoMarkerUtils.WARNING_MARKER_ID, true, IResource.DEPTH_INFINITE);
				resource.deleteMarkers(AcceleoMarkerUtils.INFO_MARKER_ID, true, IResource.DEPTH_INFINITE);
				resource.deleteMarkers(AcceleoMarkerUtils.OVERRIDE_MARKER_ID, true, IResource.DEPTH_INFINITE);
				resource.deleteMarkers(AcceleoMarkerUtils.TASK_MARKER_ID, true, IResource.DEPTH_INFINITE);
			}
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}

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
	private org.eclipse.acceleo.internal.parser.compiler.AcceleoProject computeProjectDependencies(
			org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject,
			IJavaProject javaProject) {
		this.mappedProjects.put(javaProject, acceleoProject);
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

						org.eclipse.acceleo.internal.parser.compiler.AcceleoProject mappedProject = this.mappedProjects
								.get(requiredJavaProject);
						if (mappedProject != null) {
							acceleoProject.addProjectDependencies(Sets.newHashSet(mappedProject));
						} else {
							Set<AcceleoProjectClasspathEntry> entries = this
									.computeProjectClassPath(requiredJavaProject);
							org.eclipse.acceleo.internal.parser.compiler.AcceleoProject requiredAcceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
									projectRoot, entries);
							if (!acceleoProject.getProjectDependencies().contains(requiredAcceleoProject)) {
								acceleoProject
										.addProjectDependencies(Sets.newHashSet(requiredAcceleoProject));
								requiredAcceleoProject = this.computeProjectDependencies(
										requiredAcceleoProject, requiredJavaProject);
							}
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

						org.eclipse.acceleo.internal.parser.compiler.AcceleoProject mappedProject = this.mappedProjects
								.get(iJavaProject);
						if (requiring && mappedProject != null) {
							acceleoProject.addDependentProjects(Sets.newHashSet(mappedProject));
						} else if (requiring && mappedProject == null) {
							Set<AcceleoProjectClasspathEntry> entries = this
									.computeProjectClassPath(iJavaProject);
							org.eclipse.acceleo.internal.parser.compiler.AcceleoProject requiringAcceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
									iProject.getLocation().toFile(), entries);
							if (!acceleoProject.getDependentProjects().contains(requiringAcceleoProject)) {
								acceleoProject.addDependentProjects(Sets.newHashSet(requiringAcceleoProject));
								requiringAcceleoProject = this.computeProjectDependencies(
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
	 * Computes the classpath for the given java project.
	 * 
	 * @param javaProject
	 *            The Java project
	 * @return The classpath entries
	 */
	private Set<AcceleoProjectClasspathEntry> computeProjectClassPath(IJavaProject javaProject) {
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

							this.outputFolders.add(outputDirectory);
						}
					}
				}
			}
		} catch (JavaModelException e) {
			AcceleoUIActivator.log(e, true);
		}
		return classpathEntries;
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
		for (File outputFolder : this.outputFolders) {
			IPath path = new Path(outputFolder.getAbsolutePath());
			AcceleoBuilderUtils
					.members(filesOutput, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION, path);
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
				AcceleoCompileOperation compileOperation = new AcceleoCompileOperation(getProject(), files,
						false);
				compileOperation.run(monitor);
				generateAcceleoBuildFile(monitor);
			}
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
				for (File outputFolder : this.outputFolders) {
					IPath path = new Path(outputFolder.getAbsolutePath());
					AcceleoBuilderUtils.members(ecoreFiles, project, "ecore", path); //$NON-NLS-1$
				}
			}
		}
		for (IFile ecoreFile : Lists.reverse(ecoreFiles)) {
			URI uri = URI.createPlatformResourceURI(ecoreFile.getFullPath().toString(), true);
			AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
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
		for (File outputFolder : this.outputFolders) {
			IPath path = new Path(outputFolder.getAbsolutePath());
			if (path.segmentCount() >= 1) {
				AcceleoProject project = new AcceleoProject(getProject());
				List<IProject> dependencies = project.getRecursivelyAccessibleProjects();
				dependencies.remove(getProject());
				org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE
						.createAcceleoProject();
				List<String> pluginDependencies = acceleoProject.getPluginDependencies();
				for (IProject iProject : dependencies) {
					pluginDependencies.add(iProject.getName());
				}

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
				for (File outputFolder : this.outputFolders) {
					IPath path = new Path(outputFolder.getAbsolutePath());
					AcceleoBuilderUtils.members(deltaFilesOutput, getProject(),
							IAcceleoConstants.MTL_FILE_EXTENSION, path);
				}
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
		for (File outputFolder : this.outputFolders) {
			IPath path = new Path(outputFolder.getAbsolutePath());
			AcceleoBuilderUtils.members(otherTemplates, getProject(), IAcceleoConstants.MTL_FILE_EXTENSION,
					path);
		}
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
		IProject project = getProject();
		IJavaProject javaProject = JavaCore.create(project);
		File projectRoot = project.getLocation().toFile();
		Set<AcceleoProjectClasspathEntry> entries = this.computeProjectClassPath(javaProject);
		org.eclipse.acceleo.internal.parser.compiler.AcceleoProject acceleoProject = new org.eclipse.acceleo.internal.parser.compiler.AcceleoProject(
				projectRoot, entries);

		acceleoProject = this.computeProjectDependencies(acceleoProject, javaProject);
		acceleoProject.clean();
		this.cleanAcceleoMarkers(project);
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
				if (contentChanged(delta)
						&& (IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension()) || "MANIFEST.MF" //$NON-NLS-1$
						.equals(resource.getName())) || "plugin.xml".equals(resource.getName())) { //$NON-NLS-1$
					deltaFilesOutput.add((IFile)resource);
				}
			} else {
				boolean shouldConsider = true;
				for (File outputFolder : this.outputFolders) {
					if (outputFolder == null
							|| new Path(outputFolder.getAbsolutePath()).isPrefixOf(resource.getLocation())) {
						shouldConsider = false;
					}
				}
				if (shouldConsider) {
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
	 * return true if the delta indicates a change of file content.
	 * 
	 * @param delta
	 *            a resource delta.
	 * @return true if the delta indicates a change of file content.
	 */
	private boolean contentChanged(IResourceDelta delta) {
		if (delta.getKind() != IResourceDelta.REMOVED) {
			return (delta.getFlags() & IResourceDelta.CONTENT) != 0
					|| (delta.getFlags() & IResourceDelta.REPLACED) != 0;
		}
		return false;
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
				for (File outputFolder : this.outputFolders) {
					if (outputFolder == null
							|| !new Path(outputFolder.getAbsolutePath()).isPrefixOf(resource.getLocation())) {
						IResourceDelta[] children = delta.getAffectedChildren();
						for (int i = 0; i < children.length; i++) {
							deltaRemovedMembers(deltaFilesOutput, children[i], monitor);
						}
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
	 * @param container
	 *            The container to browse for files with the given extension.
	 * @param extension
	 *            The file extension to browse for.
	 * @return The List of files of the given extension contained by <code>container</code>.
	 * @throws CoreException
	 *             Thrown if we couldn't retrieve the children of <code>container</code>.
	 */
	private List<IFile> members(IContainer container, String extension) throws CoreException {
		List<IFile> output = new ArrayList<IFile>();
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile && extension.equals(((IFile)resource).getFileExtension())) {
						output.add((IFile)resource);
					} else if (resource instanceof IContainer) {
						output.addAll(members((IContainer)resource, extension));
					}
				}
			}
		}
		return output;
	}

	/**
	 * Record a new state for the current project.
	 * 
	 * @param state
	 *            The state to record
	 */
	private void recordNewState(AcceleoProjectState state) {
		AcceleoModelManager.getManager().setProjectState(this.getProject(), state);
	}

	/**
	 * Clears the last recorded state.
	 */
	private void clearLastState() {
		AcceleoModelManager.getManager().setProjectState(this.getProject(), null);
	}

	/**
	 * Returns the last saved state for the given project.
	 * 
	 * @param project
	 *            The given project
	 * @return The last saved state for the given project
	 */
	private AcceleoProjectState getLastState(IProject project) {
		return AcceleoModelManager.getManager().getLastBuiltState(project, new NullProgressMonitor());
	}
}
