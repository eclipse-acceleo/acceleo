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
package org.eclipse.acceleo.ide.ui.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * A Acceleo project represents a view of a project resource in terms of Acceleo elements. Each Acceleo
 * project has a classpath, defining which folders contain source code and where templates are located. Each
 * Acceleo project also has an output location. The extension of a template is '.acceleo' and the extension of
 * an output file is '.emtl'.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoProject {

	/**
	 * Save the found URIs of the EMTL files in the plug-ins. The key is the bundle symbolic name and the
	 * values are the URIs of the bundle EMTL files.
	 */
	private static Map<String, List<URI>> bundle2outputFiles = new HashMap<String, List<URI>>();

	/**
	 * Indicates if the saved map is complete.
	 */
	private static boolean allBundlesOutputFilesFound;

	/**
	 * Indicates if the EMF registry has been initialized.
	 */
	private static boolean registryInitialized;

	/**
	 * The project.
	 */
	private IProject project;

	/**
	 * The folders that contain the Acceleo sources (A Acceleo source folder is a Java source folder).
	 */
	private List<IPath> sourceFolders;

	/**
	 * Output file cache for MANIFEST.MF dependencies.
	 */
	private List<URI> outputFilesWithManifest;

	/**
	 * The MANIFEST.MF file modification stamp. If the modification stamp doesn't change, we haven't to
	 * compute the MANIFEST.MF dependencies. We can use the variable 'outputFilesWithManifest' to get the
	 * dependencies while the MANIFEST.MF file doesn't change.
	 */
	private long manifestModificationStamp = -1;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            is the project
	 */
	public AcceleoProject(IProject project) {
		if (!registryInitialized) {
			registryInitialized = true;
			registerPackages();
		}
		this.project = project;
		this.sourceFolders = new ArrayList<IPath>();
		final IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries;
		try {
			entries = javaProject.getResolvedClasspath(true);
		} catch (JavaModelException e1) {
			entries = new IClasspathEntry[] {};
		}
		for (int i = 0; i < entries.length; i++) {
			IClasspathEntry entry = entries[i];
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				this.sourceFolders.add(entry.getPath());
			}
		}
	}

	/**
	 * Register the Acceleo metamodel NsURI.
	 */
	private static void registerPackages() {
		if (!EPackage.Registry.INSTANCE.containsKey(MtlPackage.eINSTANCE.getNsURI())) {
			EPackage.Registry.INSTANCE.put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
					org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
			EPackage.Registry.INSTANCE.put(org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE
					.getNsURI(), org.eclipse.ocl.expressions.ExpressionsPackage.eINSTANCE);
			EPackage.Registry.INSTANCE.put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
					getOCLStdLibPackage());
			EPackage.Registry.INSTANCE.put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
		}
	}

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 * @generated
	 */
	private static EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		return (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary().getBag());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AcceleoProject) {
			return ((AcceleoProject)obj).project.equals(project);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return project.getName().hashCode();
	}

	/**
	 * Returns a list of existing Acceleo files (Acceleo files only) in this resource.
	 * 
	 * @return all the Acceleo files
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	public List<IFile> getInputFiles() throws CoreException {
		List<IFile> filesInput = new ArrayList<IFile>();
		for (Iterator<IPath> itSourceFolders = sourceFolders.iterator(); itSourceFolders.hasNext();) {
			IPath sourceFolderPath = itSourceFolders.next();
			if (sourceFolderPath.segmentCount() > 1) {
				IFolder sourceFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(sourceFolderPath);
				if (sourceFolder != null && sourceFolder.isAccessible()) {
					computeInputFiles(filesInput, sourceFolder);
				}
			}
		}
		return filesInput;
	}

	/**
	 * Computes a list of existing Acceleo files (Acceleo files only) in this resource.
	 * 
	 * @param filesInput
	 *            an output parameter to get all the Acceleo files
	 * @param container
	 *            is the container to browse
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void computeInputFiles(List<IFile> filesInput, IContainer container) throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile
							&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)resource)
									.getFileExtension())) {
						filesInput.add((IFile)resource);
					} else if (resource instanceof IContainer) {
						computeInputFiles(filesInput, (IContainer)resource);
					}
				}
			}
		}
	}

	/**
	 * Gets the output file path (EMTL) for the given Acceleo file. For example : <br>
	 * '/MyProject/src/org/eclipse/acceleo/file.acceleo' becomes
	 * '/MyProject/bin/org/eclipse/acceleo/file.emtl' </br>
	 * 
	 * @param fileAcceleo
	 *            is the Acceleo file
	 * @return the output file path
	 */
	public IPath getOutputFilePath(IFile fileAcceleo) {
		IPath projectPath = project.getRawLocation();
		if (projectPath == null) {
			projectPath = project.getLocation();
		}
		IPath acceleoFilePath = fileAcceleo.getRawLocation();
		if (acceleoFilePath == null) {
			acceleoFilePath = fileAcceleo.getLocation();
		}

		IPath filePath = makeRelativeTo(acceleoFilePath, projectPath);
		IFolder folder = getOutputFolder(project);
		if (folder != null) {
			for (Iterator<IPath> itSourceFolders = sourceFolders.iterator(); itSourceFolders.hasNext();) {
				IPath sourcePath = makeRelativeTo(itSourceFolders.next(), project.getFullPath());
				if (sourcePath.isPrefixOf(filePath)) {
					IPath relativePath = filePath.removeFirstSegments(sourcePath.segmentCount());
					return folder.getFullPath().append(
							relativePath.removeFileExtension().addFileExtension(
									IAcceleoConstants.EMTL_FILE_EXTENSION));
				}
			}
		}
		return null;
	}

	/**
	 * Gets the input file path (Acceleo) for the given EMTL file path. For example : <br>
	 * '/MyProject/bin/org/eclipse/acceleo/file.emtl' becomes
	 * '/MyProject/src-gen/org/eclipse/acceleo/file.acceleo' </br>
	 * 
	 * @param fileEMTL
	 *            is the EMTL file path
	 * @return the input file, or null if the given file isn't valid
	 */
	public IPath getInputFilePath(IPath fileEMTL) {
		IFolder folder = getOutputFolder(project);
		if (folder != null && folder.getFullPath().isPrefixOf(fileEMTL)) {
			IPath relativePath = fileEMTL.removeFileExtension().addFileExtension(
					IAcceleoConstants.MTL_FILE_EXTENSION).removeFirstSegments(
					folder.getFullPath().segmentCount());
			for (Iterator<IPath> itSourceFolders = sourceFolders.iterator(); itSourceFolders.hasNext();) {
				IPath sourcePath = itSourceFolders.next().append(relativePath);
				if (ResourcesPlugin.getWorkspace().getRoot().exists(sourcePath)) {
					return sourcePath;
				}
			}
		}
		return null;
	}

	/**
	 * This is a helper method returning the resolved classpath for the project as a list of simple classpath
	 * entries.
	 * 
	 * @return the classpath entries
	 */
	public List<IPath> getResolvedClasspath() {
		List<IPath> result = new ArrayList<IPath>();
		final IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] entries;
		try {
			entries = javaProject.getResolvedClasspath(true);
		} catch (JavaModelException e1) {
			AcceleoUIActivator.getDefault().getLog().log(e1.getStatus());
			entries = new IClasspathEntry[] {};
		}
		for (int i = 0; i < entries.length; i++) {
			IClasspathEntry entry = entries[i];
			result.add(entry.getPath());
		}
		return result;
	}

	/**
	 * Gets the full name of the package which contains the Acceleo file. For example : <br>
	 * '/MyProject/src/org/eclipse/acceleo/file.acceleo' becomes 'org.eclipse.acceleo' </br>
	 * 
	 * @param fileAcceleo
	 *            is the Acceleo file
	 * @return the full name of the package, or an empty string if the file is not valid
	 */
	public String getPackageName(IFile fileAcceleo) {
		IPath projectPath = project.getRawLocation();
		if (projectPath == null) {
			projectPath = project.getLocation();
		}
		IPath acceleoFilePath = fileAcceleo.getRawLocation();
		if (acceleoFilePath == null) {
			acceleoFilePath = fileAcceleo.getLocation();
		}

		IPath filePath = makeRelativeTo(acceleoFilePath, projectPath);
		for (Iterator<IPath> itSourceFolders = sourceFolders.iterator(); itSourceFolders.hasNext();) {
			IPath sourcePath = makeRelativeTo(itSourceFolders.next(), project.getFullPath());
			if (sourcePath.isPrefixOf(filePath)) {
				StringBuffer name = new StringBuffer();
				String[] segments = filePath.removeFirstSegments(sourcePath.segmentCount())
						.removeLastSegments(1).segments();
				for (int i = 0; i < segments.length; i++) {
					if (i > 0) {
						name.append("."); //$NON-NLS-1$
					}
					name.append(segments[i]);
				}
				return name.toString();
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Gets the output folder of the project. For example : '/MyProject/bin'.
	 * 
	 * @param aProject
	 *            is a project of the workspace
	 * @return the output folder of the project, or null if it doesn't exist
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AcceleoRefactoringUtils#getOutputFolder(IProject
	 *      aProject)
	 */
	private static IFolder getOutputFolder(IProject aProject) {
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
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return null;
	}

	/**
	 * Gets all the output files (EMTL) of this project. It means the files of the project and not the files
	 * of the required plugins.
	 * 
	 * @return the URIs of the output files, there are only 'workspace' URIs...
	 */
	public List<URI> getOutputFiles() {
		List<URI> outputURIs = new ArrayList<URI>();
		computeAccessibleOutputFilesInFolder(outputURIs, getOutputFolder(project));
		return outputURIs;
	}

	/**
	 * Gets all the accessible output files (EMTL) of this project. It means the files of the project and the
	 * files of the required plugins.
	 * 
	 * @return the URIs of the output files, there are 'plugin' URIs and 'workspace' URIs...
	 */
	public List<URI> getAccessibleOutputFiles() {
		List<URI> outputURIs = new ArrayList<URI>();
		computeAccessibleOutputFilesInFolder(outputURIs, getOutputFolder(project));
		computeAccessibleOutputFilesWithPluginXML(outputURIs, project);
		computeAccessibleOutputFilesWithProjectDependencies(outputURIs, project);
		return outputURIs;
	}

	/**
	 * Computes the URIs of all the accessible output files (EMTL) in the given folder.
	 * 
	 * @param outputURIs
	 *            is an output parameter with all the URIs
	 * @param folder
	 *            is the folder to browse
	 */
	private static void computeAccessibleOutputFilesInFolder(List<URI> outputURIs, IContainer folder) {
		if (folder == null) {
			return;
		}
		try {
			IResource[] members = folder.members();
			for (int i = 0; i < members.length; i++) {
				IResource member = members[i];
				if (member instanceof IFile) {
					if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(((IFile)member).getFileExtension())) {
						URI uri = URI.createPlatformResourceURI(((IFile)member).getFullPath().toString(),
								false);
						if (!outputURIs.contains(uri)) {
							outputURIs.add(uri);
						}
					}
				} else if (member instanceof IContainer) {
					computeAccessibleOutputFilesInFolder(outputURIs, (IContainer)member);
				}
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Returns the URIs of the emtl files in the required plugin of the given project.
	 * 
	 * @param project
	 *            The project
	 * @return The URIs of the emtl files in the required plugin of the given project.
	 * @since 3.3
	 */
	public static List<URI> computeAcceleoModuleInRequiredPlugins(IProject project) {
		List<URI> uris = new ArrayList<URI>();
		AcceleoProject acceleoProject = new AcceleoProject(project);
		acceleoProject.computeAccessibleOutputFilesWithPluginXML(uris, project);
		return uris;
	}

	/**
	 * Computes the URIs of all the accessible output files (EMTL) in the dependencies of the current plug-in
	 * (project). It browses all the required plug-ins declared in the 'plugin.xml' file.
	 * 
	 * @param outputURIs
	 *            is an output parameter with all the URIs
	 * @param aProject
	 *            is the current plug-in
	 */
	private void computeAccessibleOutputFilesWithPluginXML(List<URI> outputURIs, IProject aProject) {
		IFile manifest = aProject.getFile(new Path("META-INF/MANIFEST.MF")); //$NON-NLS-1$
		if (outputFilesWithManifest == null
				|| (manifest.isAccessible() && manifest.getModificationStamp() != manifestModificationStamp)) {
			outputFilesWithManifest = new ArrayList<URI>();
			manifestModificationStamp = manifest.getModificationStamp();
			IPluginModelBase plugin = PluginRegistry.findModel(aProject);
			if (plugin != null && plugin.getBundleDescription() != null) {
				BundleDescription[] requiredPlugins = plugin.getBundleDescription().getResolvedRequires();
				for (int i = 0; i < requiredPlugins.length; i++) {
					String requiredSymbolicName = requiredPlugins[i].getSymbolicName();
					IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							requiredSymbolicName);
					if (requiredProject != null && requiredProject.isAccessible()) {
						computeAccessibleOutputFilesInFolder(outputFilesWithManifest,
								getOutputFolder(requiredProject));
					} else {
						computeAccessibleOutputFilesWithBundle(outputFilesWithManifest, Platform
								.getBundle(requiredSymbolicName));
					}
				}
			}
		}
		for (URI uri : outputFilesWithManifest) {
			if (!outputURIs.contains(uri)) {
				outputURIs.add(uri);
			}
		}
	}

	/**
	 * Computes the URIs of all the accessible output files (EMTL) in the given bundle. The bundle represents
	 * a plug-in. It browses all the entries of the bundle, to get the '.emtl' files.
	 * 
	 * @param outputURIs
	 *            is an output parameter with all the URIs
	 * @param bundle
	 *            is the current bundle
	 */
	private static void computeAccessibleOutputFilesWithBundle(List<URI> outputURIs, Bundle bundle) {
		if (bundle != null) {
			if (bundle.getState() == Bundle.RESOLVED || bundle.getState() == Bundle.ACTIVE
					|| bundle.getState() == Bundle.INSTALLED || bundle.getState() == Bundle.STARTING) {
				outputURIs.addAll(getOrCreatePlatformPluginSavedURIs(bundle));
			}
		}
	}

	/**
	 * Computes the URIs of all the accessible output files (EMTL) in the dependencies of the current project.
	 * It browses the resolved classpath of the java project, and keeps each entry of type
	 * 'IClasspathEntry.CPE_PROJECT'.
	 * 
	 * @param outputURIs
	 *            is an output parameter with all the URIs
	 * @param aProject
	 *            is the current project
	 */
	private void computeAccessibleOutputFilesWithProjectDependencies(List<URI> outputURIs, IProject aProject) {
		final IJavaProject javaProject = JavaCore.create(aProject);
		IClasspathEntry[] entries;
		try {
			entries = javaProject.getResolvedClasspath(true);
		} catch (JavaModelException e1) {
			AcceleoUIActivator.getDefault().getLog().log(e1.getStatus());
			entries = new IClasspathEntry[] {};
		}
		for (int i = 0; i < entries.length; i++) {
			IClasspathEntry entry = entries[i];
			if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
				IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
						entry.getPath().toString());
				if (requiredProject != null && requiredProject.exists()) {
					computeAccessibleOutputFilesInFolder(outputURIs, getOutputFolder(requiredProject));
				}
			}
		}
	}

	/**
	 * Gets all the accessible Acceleo projects of the workspace. It means that the current project is
	 * included.
	 * 
	 * @return the accessible Acceleo projects
	 */
	public List<AcceleoProject> getRecursivelyAccessibleAcceleoProjects() {
		List<AcceleoProject> result = new ArrayList<AcceleoProject>();
		for (IProject aProject : getRecursivelyAccessibleProjects()) {
			try {
				if (aProject.isAccessible() && aProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
					result.add(new AcceleoProject(aProject));
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return result;
	}

	/**
	 * Gets all the accessible projects of the workspace. It means that the current project is included.
	 * 
	 * @return the accessible projects
	 * @since 3.0
	 */
	public List<IProject> getRecursivelyAccessibleProjects() {
		List<IProject> result = new ArrayList<IProject>();
		computeAccessibleProjects(result, project);
		return result;
	}

	/**
	 * Computes all the accessible projects.
	 * 
	 * @param accessibleProjects
	 *            is the output list that will contain all the accessible projects
	 * @param current
	 *            is the current project
	 */
	private void computeAccessibleProjects(List<IProject> accessibleProjects, IProject current) {
		if (!accessibleProjects.contains(current)) {
			accessibleProjects.add(current);
			IPluginModelBase plugin = PluginRegistry.findModel(current);
			if (plugin != null && plugin.getBundleDescription() != null) {
				BundleDescription[] requiredPlugins = plugin.getBundleDescription().getResolvedRequires();
				for (int i = 0; i < requiredPlugins.length; i++) {
					String requiredSymbolicName = requiredPlugins[i].getSymbolicName();
					IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							requiredSymbolicName);
					if (requiredProject != null && requiredProject.isAccessible()) {
						computeAccessibleProjects(accessibleProjects, requiredProject);
					}
				}
			}
			final IJavaProject javaProject = JavaCore.create(current);
			IClasspathEntry[] entries;
			try {
				entries = javaProject.getResolvedClasspath(true);
			} catch (JavaModelException e1) {
				AcceleoUIActivator.getDefault().getLog().log(e1.getStatus());
				entries = new IClasspathEntry[] {};
			}
			for (int i = 0; i < entries.length; i++) {
				IClasspathEntry entry = entries[i];
				if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							entry.getPath().toString());
					if (requiredProject != null && requiredProject.exists()) {
						computeAccessibleProjects(accessibleProjects, requiredProject);
					}
				}
			}
		}
	}

	/**
	 * Gets in a single resource set all the accessible AST resources (EMTL). It means the files of the
	 * project and the files of the required plugins. You must unload the resources of the returned resource
	 * set by yourself.
	 * 
	 * @return a resource set which contains all the accessible resources
	 * @deprecated It's a long time operation, the monitor allows the operation to be canceled. Now you have
	 *             to use : loadAccessibleOutputFiles(IProgressMonitor monitor)
	 */
	@Deprecated
	public ResourceSet loadAccessibleOutputFiles() {
		return loadAccessibleOutputFiles(new NullProgressMonitor());
	}

	/**
	 * Gets in a single resource set all the accessible AST resources (EMTL). It means the files of the
	 * project and the files of the required plugins. You must unload the resources of the returned resource
	 * set by yourself.
	 * 
	 * @param monitor
	 *            is the monitor
	 * @return a resource set which contains all the accessible resources
	 * @since 3.0
	 */
	public ResourceSet loadAccessibleOutputFiles(IProgressMonitor monitor) {
		ResourceSet oResourceSet = new ResourceSetImpl();
		List<URI> outputURIs = getAccessibleOutputFiles();
		for (Iterator<URI> itOutputURIs = outputURIs.iterator(); itOutputURIs.hasNext()
				&& !monitor.isCanceled();) {
			URI oURI = itOutputURIs.next();
			try {
				ModelUtils.load(oURI, oResourceSet);
			} catch (WrappedException e) {
				// continue and do nothing because it occurs when the EMTL file has been deleted
			} catch (IOException e) {
				// continue and do nothing because it occurs when the EMTL file has been deleted
			}
		}
		return oResourceSet;
	}

	/**
	 * Gets in a single resource set all the not accessible AST resources (EMTL). It means all the EMTL files
	 * of the Eclipse instance (plugin and workspace) not in the project and not in the required plugins. You
	 * must unload the resources of the returned resource set by yourself.
	 * 
	 * @param monitor
	 *            is the monitor
	 * @return a resource set which contains all the not accessible resources
	 * @since 3.0
	 */
	public ResourceSet loadNotAccessibleOutputFiles(IProgressMonitor monitor) {
		return loadAllPlatformOutputFiles(this, monitor);
	}

	/**
	 * Gets in a single resource set all the EMTL files of the Eclipse instance, it means in the plug-ins and
	 * in the workspace. You must unload the resources of the returned resource set by yourself.
	 * 
	 * @param monitor
	 *            is the monitor
	 * @return a resource set which contains all the not accessible resources
	 * @since 3.0
	 */
	public static ResourceSet loadAllPlatformOutputFiles(IProgressMonitor monitor) {
		if (!registryInitialized) {
			registryInitialized = true;
			registerPackages();
		}
		return loadAllPlatformOutputFiles(null, monitor);
	}

	/**
	 * Gets in a single resource set all the EMTL files of the Eclipse instance, it means in the plug-ins and
	 * in the workspace. You must unload the resources of the returned resource set by yourself. We can ignore
	 * the accessible output files, it means all the EMTL files of the given project and its required plugins.
	 * 
	 * @param excludeAccessible
	 *            the accessible output files we want to exclude, null indicates that we don't want to ignore
	 *            anything
	 * @param monitor
	 *            is the monitor
	 * @return a resource set which contains all the not accessible resources
	 */
	private static ResourceSet loadAllPlatformOutputFiles(AcceleoProject excludeAccessible,
			IProgressMonitor monitor) {
		ResourceSet oResourceSet = new ResourceSetImpl();
		List<URI> outputURIs = new ArrayList<URI>();
		for (IProject aProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			try {
				if (aProject.isAccessible() && aProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
					computeAccessibleOutputFilesInFolder(outputURIs, getOutputFolder(aProject));
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		outputURIs.addAll(getAllPlatformPluginOutputFiles());
		List<URI> excludeURIs;
		if (excludeAccessible != null) {
			excludeURIs = excludeAccessible.getAccessibleOutputFiles();
		} else {
			excludeURIs = null;
		}
		for (Iterator<URI> itOutputURIs = outputURIs.iterator(); itOutputURIs.hasNext()
				&& !monitor.isCanceled();) {
			URI oURI = itOutputURIs.next();
			if (excludeURIs == null || !excludeURIs.contains(oURI)) {
				try {
					ModelUtils.load(oURI, oResourceSet);
				} catch (WrappedException e) {
					// continue and do nothing because it occurs when the EMTL file has been deleted
				} catch (IOException e) {
					// continue and do nothing because it occurs when the EMTL file has been deleted
				}
			}
		}
		return oResourceSet;
	}

	/**
	 * Gets the URIs of all the EMTL files in the workspace.
	 * 
	 * @return the URIs of all the EMTL files
	 * @since 3.0
	 */
	public static List<URI> getAllPlatformResourceOutputFiles() {
		List<URI> outputURIs = new ArrayList<URI>();
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject aProject : projects) {
			try {
				if (aProject.isAccessible() && aProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
					computeAccessibleOutputFilesInFolder(outputURIs, getOutputFolder(aProject));
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return outputURIs;
	}

	/**
	 * Returns the URIs of the EMTL files in the bundle dependencies of the given project.
	 * 
	 * @param project
	 *            The project
	 * @return The URIs of the EMTL files in the bundle dependencies of the given project.
	 * @since 3.3
	 */
	public static List<URI> getAccessiblePluginModules(IProject project) {
		List<URI> outputURIs = new ArrayList<URI>();
		List<String> bundles = new ArrayList<String>();

		IPluginModelBase plugin = PluginRegistry.findModel(project);
		if (plugin != null && plugin.getBundleDescription() != null) {
			BundleDescription[] requiredPlugins = plugin.getBundleDescription().getResolvedRequires();
			for (int i = 0; i < requiredPlugins.length; i++) {
				String requiredSymbolicName = requiredPlugins[i].getSymbolicName();
				bundles.add(requiredSymbolicName);
			}
		}

		for (String bundle : bundles) {
			outputURIs.addAll(bundle2outputFiles.get(bundle));
		}
		return outputURIs;
	}

	/**
	 * Gets the URIs of all the EMTL files in the plug-ins. It also creates the EMTL files if only the MTL
	 * files are available. The workspace files are ignored.
	 * 
	 * @return the URIs of all the EMTL files
	 * @since 3.0
	 */
	public static List<URI> getAllPlatformPluginOutputFiles() {
		List<URI> outputURIs = new ArrayList<URI>();
		if (!allBundlesOutputFilesFound) {
			Set<String> done = new CompactHashSet<String>();
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject aProject : projects) {
				try {
					if (aProject.isAccessible() && aProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
						done.add(aProject.getName());
					}
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
			IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
			for (IBundleGroupProvider provider : providers) {
				for (IBundleGroup group : provider.getBundleGroups()) {
					for (Bundle bundle : group.getBundles()) {
						String name = bundle.getSymbolicName();
						if (!done.contains(name)) {
							if (bundle.getState() == Bundle.RESOLVED || bundle.getState() == Bundle.ACTIVE
									|| bundle.getState() == Bundle.INSTALLED
									|| bundle.getState() == Bundle.STARTING) {
								done.add(name);
								outputURIs.addAll(getOrCreatePlatformPluginSavedURIs(bundle));
							}
						}
					}
				}
			}
			// Deprecated? Yes, but the following API give more results
			for (IPluginDescriptor descriptor : Platform.getPluginRegistry().getPluginDescriptors()) {
				String name = descriptor.getUniqueIdentifier();
				if (!done.contains(name)) {
					done.add(name);
					Bundle bundle = Platform.getBundle(name);
					if (bundle != null) {
						outputURIs.addAll(getOrCreatePlatformPluginSavedURIs(bundle));
					}
				}
			}
			allBundlesOutputFilesFound = true;
		} else {
			for (List<URI> values : bundle2outputFiles.values()) {
				outputURIs.addAll(values);
			}
		}
		return outputURIs;
	}

	/**
	 * Gets and saves the URIs of all the EMTL files in the plug-ins. The workspace files are ignored. We
	 * search the URIs only one time, because the bundles won't change. This method only saves the found URIs
	 * of the EMTL files for the given plug-in.
	 * 
	 * @param bundle
	 *            is the OSGI bundle of the plug-in
	 * @return the URIs of all the EMTL files in the plug-ins
	 */
	private static List<URI> getOrCreatePlatformPluginSavedURIs(Bundle bundle) {
		String name = bundle.getSymbolicName();
		List<URI> savedURIs = bundle2outputFiles.get(name);
		if (savedURIs == null) {
			savedURIs = new ArrayList<URI>();
			bundle2outputFiles.put(name, savedURIs);
			String required = (String)bundle.getHeaders().get(Constants.REQUIRE_BUNDLE);
			if (required != null && required.indexOf(AcceleoEnginePlugin.PLUGIN_ID) != -1) {
				computeSaveURIs(bundle, savedURIs);
			}
		}
		return savedURIs;
	}

	/**
	 * Computes the saved URIs of the given bundle.
	 * 
	 * @param bundle
	 *            the bundle to browse
	 * @param savedURIs
	 *            the EMTL files URIs to get, it is an input/output parameter
	 */
	@SuppressWarnings("unchecked")
	private static void computeSaveURIs(Bundle bundle, List<URI> savedURIs) {
		// The first time we would like to be sure to extract the MTL files of the plug-in jar.
		Enumeration<URL> entriesMTL = bundle.findEntries(
				"/", "*." + IAcceleoConstants.MTL_FILE_EXTENSION, true); //$NON-NLS-1$ //$NON-NLS-2$
		Enumeration<URL> entriesEMTL = bundle.findEntries(
				"/", "*." + IAcceleoConstants.EMTL_FILE_EXTENSION, true); //$NON-NLS-1$ //$NON-NLS-2$
		if (entriesEMTL != null && entriesEMTL.hasMoreElements()) {
			while (entriesEMTL.hasMoreElements()) {
				URL entry = entriesEMTL.nextElement();
				if (entry != null) {
					IPath path = new Path(entry.getPath());
					if (path.segmentCount() > 0) {
						savedURIs.add(URI.createPlatformPluginURI(new Path(bundle.getSymbolicName()).append(
								path).toString(), false));
					}
				}
			}
		}
	}

	/**
	 * Returns the list of source folders in the project.
	 * 
	 * @return The list of source folders in the project.
	 * @since 3.1
	 */
	public List<IPath> getSourceFolders() {
		// We create a copy to prevent problems.
		return new ArrayList<IPath>(this.sourceFolders);
	}

	/**
	 * Make relative to.
	 * 
	 * @param path1
	 *            The first path
	 * @param path2
	 *            the second path
	 * @return The first path relative to the second path.
	 * @since 3.1
	 */
	public static IPath makeRelativeTo(IPath path1, IPath path2) {
		IPath path = path1;

		// can't make relative if devices are not equal
		if (path1.getDevice() == path2.getDevice()
				|| (path1.getDevice() != null && path1.getDevice().equalsIgnoreCase(path2.getDevice()))) {
			int commonLength = path1.matchingFirstSegments(path2);
			final int differenceLength = path2.segmentCount() - commonLength;
			final int newSegmentLength = differenceLength + path1.segmentCount() - commonLength;
			if (newSegmentLength == 0) {
				return Path.EMPTY;
			}
			path = new Path(""); //$NON-NLS-1$
			String[] newSegments = new String[newSegmentLength];
			// add parent references for each segment different from the base
			Arrays.fill(newSegments, 0, differenceLength, ".."); //$NON-NLS-1$
			// append the segments of this path not in common with the base
			System.arraycopy(path1.segments(), commonLength, newSegments, differenceLength, newSegmentLength
					- differenceLength);
			for (String segment : newSegments) {
				path = path.append(new Path(segment));
			}
		}

		return path;
	}
}
