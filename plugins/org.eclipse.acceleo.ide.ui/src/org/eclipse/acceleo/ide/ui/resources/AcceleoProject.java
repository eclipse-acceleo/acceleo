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
package org.eclipse.acceleo.ide.ui.resources;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;

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
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof AcceleoProject) {
			return ((AcceleoProject)obj).project.equals(project);
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
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
		IPath filePath = fileAcceleo.getFullPath();
		IFolder folder = getOutputFolder(project);
		if (folder != null) {
			for (Iterator<IPath> itSourceFolders = sourceFolders.iterator(); itSourceFolders.hasNext();) {
				IPath sourcePath = itSourceFolders.next();
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
		IPath filePath = fileAcceleo.getFullPath();
		for (Iterator<IPath> itSourceFolders = sourceFolders.iterator(); itSourceFolders.hasNext();) {
			IPath sourcePath = itSourceFolders.next();
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
	private void computeAccessibleOutputFilesInFolder(List<URI> outputURIs, IContainer folder) {
		if (folder != null) {
			try {
				IResource[] members = folder.members();
				for (int i = 0; i < members.length; i++) {
					IResource member = members[i];
					if (member instanceof IFile) {
						if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(((IFile)member).getFileExtension())) {
							outputURIs.add(URI.createPlatformResourceURI(((IFile)member).getFullPath()
									.toString(), false));
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
			if (plugin != null) {
				BundleDescription[] requiredPlugins = plugin.getBundleDescription().getResolvedRequires();
				for (int i = 0; i < requiredPlugins.length; i++) {
					String requiredSymbolicName = requiredPlugins[i].getSymbolicName();
					IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							requiredSymbolicName);
					if (requiredProject != null && requiredProject.isAccessible()) {
						computeAccessibleOutputFilesInFolder(outputFilesWithManifest,
								getOutputFolder(requiredProject));
					} else if (Platform.getBundle(requiredSymbolicName) != null) {
						computeAccessibleOutputFilesWithBundle(outputFilesWithManifest, Platform
								.getBundle(requiredSymbolicName));
					}
				}
			}
		}
		outputURIs.addAll(outputFilesWithManifest);
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
	@SuppressWarnings("unchecked")
	private void computeAccessibleOutputFilesWithBundle(List<URI> outputURIs, Bundle bundle) {
		Enumeration<URL> entries = bundle
				.findEntries("/", "*." + IAcceleoConstants.EMTL_FILE_EXTENSION, true); //$NON-NLS-1$ //$NON-NLS-2$
		if (entries != null) {
			while (entries.hasMoreElements()) {
				URL entry = entries.nextElement();
				if (entry != null) {
					IPath path = new Path(entry.getPath());
					if (path.segmentCount() > 0) {
						outputURIs.add(URI.createPlatformPluginURI(path.toString(), false));
					}
				}
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
	 * Gets all the accessible Acceleo projects. It means that the current project is included.
	 * 
	 * @return the accessible Acceleo projects
	 */
	public List<AcceleoProject> getRecursivelyAccessibleAcceleoProjects() {
		List<AcceleoProject> result = new ArrayList<AcceleoProject>();
		computeAccessibleAcceleoProjects(result, this);
		return result;
	}

	/**
	 * Computes all the accessible Acceleo projects.
	 * 
	 * @param accessibleAcceleoProjects
	 *            is the output list that will contain all the accessible projects
	 * @param current
	 *            is the current Acceleo project
	 */
	private void computeAccessibleAcceleoProjects(List<AcceleoProject> accessibleAcceleoProjects,
			AcceleoProject current) {
		if (!accessibleAcceleoProjects.contains(current)) {
			accessibleAcceleoProjects.add(current);
			IPluginModelBase plugin = PluginRegistry.findModel(current.project);
			if (plugin != null) {
				BundleDescription[] requiredPlugins = plugin.getBundleDescription().getResolvedRequires();
				for (int i = 0; i < requiredPlugins.length; i++) {
					String requiredSymbolicName = requiredPlugins[i].getSymbolicName();
					IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							requiredSymbolicName);
					if (requiredProject != null && requiredProject.isAccessible()) {
						AcceleoProject requiredAcceleoProject = new AcceleoProject(requiredProject);
						computeAccessibleAcceleoProjects(accessibleAcceleoProjects, requiredAcceleoProject);
					}
				}
			}
			final IJavaProject javaProject = JavaCore.create(current.project);
			IClasspathEntry[] entries;
			try {
				entries = javaProject.getResolvedClasspath(true);
			} catch (JavaModelException e1) {
				entries = new IClasspathEntry[] {};
			}
			for (int i = 0; i < entries.length; i++) {
				IClasspathEntry entry = entries[i];
				if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					IProject requiredProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							entry.getPath().toString());
					if (requiredProject != null && requiredProject.exists()) {
						AcceleoProject requiredAcceleoProject = new AcceleoProject(requiredProject);
						computeAccessibleAcceleoProjects(accessibleAcceleoProjects, requiredAcceleoProject);
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
	 */
	public ResourceSet loadAccessibleOutputFiles() {
		ResourceSet oResourceSet = new ResourceSetImpl();
		List<URI> outputURIs = getAccessibleOutputFiles();
		for (Iterator<URI> itOutputURIs = outputURIs.iterator(); itOutputURIs.hasNext();) {
			URI oURI = itOutputURIs.next();
			try {
				ModelUtils.load(oURI, oResourceSet);
			} catch (IOException e) {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
			}
		}
		return oResourceSet;
	}

}
