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
package org.eclipse.acceleo.internal.parser.compiler;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Region;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.emf.common.util.URI;

/**
 * Utility class used to define and manipulate Acceleo projects.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoProject {

	/**
	 * The root of the Acceleo project.
	 */
	private File projectRoot;

	/**
	 * The set of classpath entries.
	 */
	private Set<AcceleoProjectClasspathEntry> entries = new LinkedHashSet<AcceleoProjectClasspathEntry>();

	/**
	 * The dependencies of the project.
	 */
	private Set<URI> dependencies = new LinkedHashSet<URI>();

	/**
	 * The project dependencies of this project.
	 */
	private Set<AcceleoProject> projectDependencies = new LinkedHashSet<AcceleoProject>();

	/**
	 * The project depending on this.
	 */
	private Set<AcceleoProject> dependentProjects = new LinkedHashSet<AcceleoProject>();

	/**
	 * The cache of the modules in the project.
	 */
	private Set<File> acceleoModulesCache = new LinkedHashSet<File>();

	/**
	 * The constructor.
	 * 
	 * @param projectRoot
	 *            The directory containing the Acceleo project.
	 */
	public AcceleoProject(File projectRoot) {
		this(projectRoot, new LinkedHashSet<AcceleoProjectClasspathEntry>());
	}

	/**
	 * The constructor.
	 * 
	 * @param projectRoot
	 *            The directory containing the Acceleo project.
	 * @param entries
	 *            The classpath entries.
	 */
	public AcceleoProject(File projectRoot, Set<AcceleoProjectClasspathEntry> entries) {
		Preconditions.checkState(projectRoot.isDirectory());
		this.projectRoot = projectRoot;
		this.entries.addAll(entries);
	}

	/**
	 * Returns the set of files depending on the given file.
	 * 
	 * @param file
	 *            The file.
	 * @return The set of files depending on the given file.
	 */
	public Set<File> getFilesDependingOn(File file) {
		List<Sequence> sequencesToSearch = AcceleoParserUtils.getImportSequencesToSearch(this, file);
		return this.getFilesDependingOn(sequencesToSearch);
	}

	/**
	 * Returns the set of files depending on the given sequences. See
	 * {@link AcceleoParserUtils#getImportSequencesToSearch} to compute the sequences.
	 * 
	 * @param sequences
	 *            The sequences.
	 * @return The set of files depending on the given sequences. See
	 *         {@link AcceleoParserUtils#getImportSequencesToSearch} to compute the sequences.
	 */
	public Set<File> getFilesDependingOn(List<Sequence> sequences) {
		// Compute the files that are depending on "file".
		Set<File> filesToBuild = new LinkedHashSet<File>();

		// Look for files to build in the project
		Set<File> allAcceleoModules = this.getAllAcceleoModules();
		for (File acceleoModule : allAcceleoModules) {
			StringBuffer fileContent = FileContent.getFileContent(acceleoModule);
			for (Sequence sequence : sequences) {
				Region region = sequence.search(fileContent);
				if (region.b() > -1) {
					filesToBuild.add(acceleoModule);
				}
			}
		}
		return filesToBuild;
	}

	/**
	 * Returns the file matching the given uri dependency (a::b::c::d).
	 * 
	 * @param dependency
	 *            The uri of an Acceleo module.
	 * @return The file matching the given uri dependency (a::b::c::d).
	 */
	public File getFileDependency(String dependency) {
		File module = null;
		for (File acceleoModule : this.getAllAcceleoModules()) {
			// Find the dependencies in the current project first
			if (dependency.equals(this.getModuleQualifiedName(acceleoModule))) {
				module = acceleoModule;
				break;
			}
		}
		// If we are there, we didn't found a file with the qualified name given
		// Let's look, at a file with the name
		for (File acceleoModule : this.getAllAcceleoModules()) {
			String name = acceleoModule.getName();
			if (name.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
				name = name.substring(0, name.length()
						- ('.' + IAcceleoConstants.MTL_FILE_EXTENSION).length());
			}
			if (dependency.equals(name)) {
				module = acceleoModule;
				break;
			}
		}
		return module;
	}

	/**
	 * Returns the URI of the emtl matching the given URI dependency (a::b::c::d).
	 * 
	 * @param dependency
	 *            The dependency
	 * @return The URI of the emtl matching the given URI dependency (a::b::c::d).
	 */
	public URI getURIDependency(String dependency) {
		URI result = null;

		Iterator<URI> iterator = this.dependencies.iterator();
		while (iterator.hasNext()) {
			URI uri = iterator.next();
			if (uri.isPlatformPlugin()) {
				String uriStr = uri.toString();
				String[] segments = uri.segments();
				if (uriStr.startsWith("platform:/plugin/") && segments.length > 1 && "plugin".equals(segments[0])) { //$NON-NLS-1$ //$NON-NLS-2$
					String moduleQualifiedName = ""; //$NON-NLS-1$
					for (int cpt = 2; cpt < segments.length; cpt++) {
						String segment = segments[cpt];
						if (cpt > 2) {
							moduleQualifiedName = moduleQualifiedName + IAcceleoConstants.NAMESPACE_SEPARATOR;
						}
						moduleQualifiedName = moduleQualifiedName + segment;
					}
					if (moduleQualifiedName.endsWith('.' + IAcceleoConstants.EMTL_FILE_EXTENSION)) {
						moduleQualifiedName = moduleQualifiedName.substring(0, moduleQualifiedName
								.lastIndexOf('.'));
					}
					if (dependency.equals(moduleQualifiedName)) {
						result = uri;
						break;
					}
				}
			} else if (uri.isPlatformResource()) {
				String uriStr = uri.toString();
				String[] segments = uri.segments();
				if (uriStr.startsWith("platform:/resource/") && segments.length > 1 && "resource".equals(segments[0])) { //$NON-NLS-1$ //$NON-NLS-2$
					String moduleQualifiedName = ""; //$NON-NLS-1$
					for (int cpt = 2; cpt < segments.length; cpt++) {
						String segment = segments[cpt];
						moduleQualifiedName = moduleQualifiedName + IAcceleoConstants.NAMESPACE_SEPARATOR
								+ segment;
					}
					if (moduleQualifiedName.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
						moduleQualifiedName = moduleQualifiedName.substring(0, moduleQualifiedName
								.lastIndexOf('.'));
					}
					if (dependency.equals(moduleQualifiedName)) {
						result = uri;
						break;
					}
				}
			} else {
				Set<URI> allModules = AcceleoParserUtils.getAllModules(uri);
				Iterator<URI> moduleIterator = allModules.iterator();
				while (moduleIterator.hasNext()) {
					URI moduleURI = moduleIterator.next();
					String moduleName = AcceleoParserUtils.getModuleName(moduleURI);
					if (dependency.equals(moduleName)) {
						return moduleURI;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Adds the given classpath entries.
	 * 
	 * @param classpathEntries
	 *            The classpath entries.
	 */
	public void addClasspathEntries(Set<AcceleoProjectClasspathEntry> classpathEntries) {
		this.entries.addAll(classpathEntries);
	}

	/**
	 * Adds the given dependencies. The URI should be the URI of jar files or platform:/plugin module uris or
	 * platform:/resource module uri.
	 * 
	 * @param newDependencies
	 *            The dependencies
	 * @return <code>true</code> if the dependencies were added, <code>false</code> otherwise.
	 */
	public boolean addDependencies(Set<URI> newDependencies) {
		return this.dependencies.addAll(newDependencies);
	}

	public Set<URI> getDependencies() {
		return Collections.unmodifiableSet(this.dependencies);
	}

	/**
	 * Clears the dependencies.
	 */
	public void clearDependencies() {
		this.dependencies.clear();
	}

	/**
	 * Return the modules dependencies.
	 * 
	 * @return The modules dependencies.
	 */
	public Set<URI> getModulesDependencies() {
		return Collections.unmodifiableSet(this.dependencies);
	}

	/**
	 * Adds a project dependencies.
	 * 
	 * @param newProjectDependencies
	 *            The project that this project depends on.
	 * @return <code>true</code> if it was successfully added, <code>false</code> otherwise.
	 */
	public boolean addProjectDependencies(Set<AcceleoProject> newProjectDependencies) {
		boolean result = this.projectDependencies.addAll(newProjectDependencies);
		for (AcceleoProject projectDependency : newProjectDependencies) {
			if (!projectDependency.getDependentProjects().contains(this)) {
				projectDependency.addDependentProjects(Sets.newHashSet(this));
			}
		}
		return result;
	}

	/**
	 * Returns the project dependencies.
	 * 
	 * @return The project dependencies.
	 */
	public Set<AcceleoProject> getProjectDependencies() {
		return Collections.unmodifiableSet(this.projectDependencies);
	}

	/**
	 * Clears the project dependencies.
	 */
	public void clearProjectDependencies() {
		this.projectDependencies.clear();
	}

	/**
	 * Adds the projects depending on this project.
	 * 
	 * @param newDependentProjects
	 *            The projects depending on this project.
	 * @return <code>true</code> if the dependent project were successfully added, <code>false</code>
	 *         otherwise.
	 */
	public boolean addDependentProjects(Set<AcceleoProject> newDependentProjects) {
		boolean result = this.dependentProjects.addAll(newDependentProjects);
		for (AcceleoProject dependentProject : newDependentProjects) {
			if (!dependentProject.getProjectDependencies().contains(this)) {
				dependentProject.addProjectDependencies(Sets.newHashSet(this));
			}
		}
		return result;
	}

	/**
	 * Returns the project depending on this project.
	 * 
	 * @return The project depending on this project.
	 */
	public Set<AcceleoProject> getDependentProjects() {
		return Collections.unmodifiableSet(this.dependentProjects);
	}

	/**
	 * Clears the project depending on this project.
	 */
	public void clearDependentProjects() {
		this.dependentProjects.clear();
	}

	/**
	 * Return the package name of the given file.
	 * 
	 * @param file
	 *            The file.
	 * @return The package name of the given file.
	 */
	public String getPackageName(File file) {
		for (AcceleoProjectClasspathEntry entry : this.entries) {
			File inputDirectory = entry.getInputDirectory();
			if (file.getAbsolutePath().startsWith(inputDirectory.getAbsolutePath())) {
				String path = file.getAbsolutePath().substring(inputDirectory.getAbsolutePath().length());
				String packageName = path;
				if (packageName.endsWith(file.getName())) {
					packageName = packageName.substring(0, packageName.length() - file.getName().length());
				}
				String slash = "/"; //$NON-NLS-1$

				packageName = packageName.replace("\\", slash); //$NON-NLS-1$
				if (packageName.startsWith(slash)) {
					packageName = packageName.substring(1);
				}
				if (packageName.endsWith(slash)) {
					packageName = packageName.substring(0, packageName.length() - 1);
				}
				return packageName;
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns <code>true</code> if the file is the project, <code>false</code> otherwise.
	 * 
	 * @param file
	 *            The file.
	 * @return <code>true</code> if the file is the project, <code>false</code> otherwise.
	 */
	public boolean isInProject(File file) {
		return this.getAllAcceleoModules().contains(file);
	}

	/**
	 * Returns the output file for the given input file.
	 * 
	 * @param inputFile
	 *            The input file
	 * @return The output file for the given input file.
	 */
	public File getOutputFile(File inputFile) {
		for (AcceleoProjectClasspathEntry entry : this.entries) {
			File inputDirectory = entry.getInputDirectory();
			if (inputFile.getAbsolutePath().startsWith(inputDirectory.getAbsolutePath())) {
				String path = inputFile.getAbsolutePath()
						.substring(inputDirectory.getAbsolutePath().length());
				File outputDirectory = entry.getOutputDirectory();
				if (path.endsWith(IAcceleoConstants.MTL_FILE_EXTENSION)) {
					path = path.substring(0, path.length() - IAcceleoConstants.MTL_FILE_EXTENSION.length());
					path = path + IAcceleoConstants.EMTL_FILE_EXTENSION;
				}
				File outputFile = new File(outputDirectory, path);
				return outputFile;
			}
		}
		return null;
	}

	/**
	 * Returns the input file for the given output file.
	 * 
	 * @param outputFile
	 *            the output file.
	 * @return The input file for the given output file.
	 */
	public File getInputFile(File outputFile) {
		for (AcceleoProjectClasspathEntry entry : this.entries) {
			File outputDirectory = entry.getOutputDirectory();
			if (outputFile.getAbsolutePath().startsWith(outputDirectory.getAbsolutePath())) {
				String path = outputFile.getAbsolutePath().substring(
						outputDirectory.getAbsolutePath().length());
				File inputDirectory = entry.getInputDirectory();
				if (path.endsWith(IAcceleoConstants.EMTL_FILE_EXTENSION)) {
					path = path.substring(0, path.length() - IAcceleoConstants.EMTL_FILE_EXTENSION.length());
					path = path + IAcceleoConstants.MTL_FILE_EXTENSION;
				}
				File inputFile = new File(inputDirectory, path);
				return inputFile;
			}
		}
		return null;
	}

	/**
	 * Returns all the acceleo modules contained in the project.
	 * 
	 * @return All the acceleo modules contained in the project.
	 */
	public Set<File> getAllAcceleoModules() {
		Set<File> result = new LinkedHashSet<File>();

		boolean shouldUpdate = this.acceleoModulesCache.isEmpty();

		if (shouldUpdate) {
			for (AcceleoProjectClasspathEntry entry : this.entries) {
				File inputDirectory = entry.getInputDirectory();
				result.addAll(AcceleoProject
						.getChildren(inputDirectory, IAcceleoConstants.MTL_FILE_EXTENSION));
			}

			this.acceleoModulesCache.clear();
			this.acceleoModulesCache.addAll(result);
		} else {
			result.addAll(this.acceleoModulesCache);
		}
		return result;
	}

	/**
	 * Returns all the compiled acceleo modules contained in the project.
	 * 
	 * @return All the compiled acceleo modules contained in the project.
	 */
	public Set<File> getAllCompiledAcceleoModules() {
		Set<File> result = new LinkedHashSet<File>();
		for (AcceleoProjectClasspathEntry entry : this.entries) {
			File outputDirectory = entry.getOutputDirectory();
			result.addAll(AcceleoProject.getChildren(outputDirectory, IAcceleoConstants.EMTL_FILE_EXTENSION));
		}
		return result;
	}

	/**
	 * Returns the children of the given directory with the given extension.
	 * 
	 * @param directory
	 *            The directory.
	 * @param extension
	 *            The extension.
	 * @return The children of the given directory with the given extension.
	 */
	public static Set<File> getChildren(File directory, String extension) {
		Set<File> result = new LinkedHashSet<File>();
		File[] files = directory.listFiles();
		if (files != null) {
			for (File subfile : files) {
				if (subfile.isFile() && subfile.getName().endsWith("." + extension)) { //$NON-NLS-1$
					result.add(subfile);
				} else if (subfile.isDirectory()) {
					result.addAll(AcceleoProject.getChildren(subfile, extension));
				}
			}
		}
		return result;
	}

	/**
	 * Returns the file matching the given module name (a::b::c::d).
	 * 
	 * @param moduleName
	 *            The module name.
	 * @return The file matching the given module name.
	 */
	public File getFileFromModuleName(String moduleName) {
		File file = null;
		for (AcceleoProjectClasspathEntry entry : this.entries) {
			File inputDirectory = entry.getInputDirectory();
			Set<File> children = AcceleoProject.getChildren(inputDirectory,
					IAcceleoConstants.MTL_FILE_EXTENSION);
			for (File child : children) {
				if (moduleName.equals(this.getModuleQualifiedName(child))) {
					file = child;
					return file;
				}
			}
		}
		return file;
	}

	/**
	 * Returns the module qualified name for the given file.
	 * 
	 * @param file
	 *            The file
	 * @return The module qualifier name for the given file.
	 */
	public String getModuleQualifiedName(File file) {
		String packageName = this.getPackageName(file);
		packageName = packageName.replace("/", IAcceleoConstants.NAMESPACE_SEPARATOR); //$NON-NLS-1$
		String fileName = file.getName();
		if (fileName.endsWith('.' + IAcceleoConstants.MTL_FILE_EXTENSION)) {
			fileName = fileName.substring(0, fileName.length()
					- ('.' + IAcceleoConstants.MTL_FILE_EXTENSION).length());
		}
		packageName = packageName + IAcceleoConstants.NAMESPACE_SEPARATOR + fileName;
		return packageName;
	}

	/**
	 * Returns the file that have not been compiled in the given project.
	 * 
	 * @return the file that have not been compiled in the given project.
	 */
	public Set<File> getFileNotCompiled() {
		Set<File> notCompiled = new LinkedHashSet<File>();
		Set<File> allAcceleoModules = this.getAllAcceleoModules();
		Set<File> allCompiledAcceleoModules = this.getAllCompiledAcceleoModules();

		for (File file : allAcceleoModules) {
			File outputFile = this.getOutputFile(file);
			if (!allCompiledAcceleoModules.contains(outputFile)) {
				notCompiled.add(file);
			}
		}
		return notCompiled;

	}

	/**
	 * Cleans the project. All the emtls of the project are deleted. Do not delete the subfolders of the
	 * output project and do not delete anything except the emtl files.
	 */
	public void clean() {
		Set<File> allCompiledAcceleoModules = this.getAllCompiledAcceleoModules();
		for (File compiledModules : allCompiledAcceleoModules) {
			compiledModules.delete();
		}
	}

	/**
	 * Returns the root directory of the project.
	 * 
	 * @return the root directory of the project.
	 */
	public File getProjectRoot() {
		return this.projectRoot;
	}

	/* package */Set<AcceleoProjectClasspathEntry> getEntries() {
		return Collections.unmodifiableSet(entries);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof AcceleoProject) {
			AcceleoProject project = (AcceleoProject)arg0;
			return project.getProjectRoot().equals(this.projectRoot);
		}
		return super.equals(arg0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.projectRoot.hashCode();
	}

	@Override
	public String toString() {
		return "AP/" + this.projectRoot.getName(); //$NON-NLS-1$
	}
}
