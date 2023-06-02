/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.wizard;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EClass;

public class ModuleConfiguration {

	/**
	 * The project name.
	 */
	private String projectName;

	/**
	 * The module name.
	 */
	private String moduleName;

	/**
	 * The {@link Set} of nsURIs.
	 */
	private final Set<String> nsURIs = new LinkedHashSet<>();

	/**
	 * The module element name.
	 */
	private String moduleElementName;

	/**
	 * The {@link EClass} of the module element to create: {@link Template} or {@link Query}.
	 */
	private EClass moduleElementEClass;

	/**
	 * The module element parameter type.
	 */
	private String moduleElementParameterType;

	/**
	 * <code>true</code> if we need to create documentation.
	 */
	private boolean generateDocumentation;

	/**
	 * <code>true</code> if we need to create a {@link FileStatement}.
	 */
	private boolean generateFile;

	/**
	 * <code>true</code> if we need to create a main template.
	 */
	private boolean mainTemplate;

	/**
	 * The initialization path if any, <code>null</code> otherwise.
	 */
	private String initializationPath;

	/**
	 * Tells if the content should be initialized.
	 */
	private boolean isInitialized;

	/**
	 * The parent folder.
	 */
	private String parentFolder;

	/**
	 * Gets the project name.
	 * 
	 * @return the project name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the project name.
	 * 
	 * @param projectName
	 *            the project name
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Gets the module name.
	 * 
	 * @return the module name
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Sets the module name.
	 * 
	 * @param moduleName
	 *            the module name
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * Gets the module element name.
	 * 
	 * @return the module element name
	 */
	public String getModuleElementName() {
		return moduleElementName;
	}

	/**
	 * Sets the module element name.
	 * 
	 * @param moduleElementName
	 *            the module element name
	 */
	public void setModuleElementName(String moduleElementName) {
		this.moduleElementName = moduleElementName;
	}

	/**
	 * Gets the module element {@link EClass}: {@link Template} or {@link Query}.
	 * 
	 * @return the module element {@link EClass}: {@link Template} or {@link Query}
	 */
	public EClass getModuleElementEClass() {
		return moduleElementEClass;
	}

	/**
	 * Sets the module element parameter {@link EClass}: {@link Template} or {@link Query}.
	 * 
	 * @param moduleElementEClass
	 *            the module element parameter {@link EClass}: {@link Template} or {@link Query}
	 */
	public void setModuleElementEClass(EClass moduleElementEClass) {
		this.moduleElementEClass = moduleElementEClass;
	}

	/**
	 * Tells if the documentation need to be generated.
	 * 
	 * @return <code>true</code> if the documentation need to be generated, <code>false</code> otherwise
	 */
	public boolean isGenerateDocumentation() {
		return generateDocumentation;
	}

	/**
	 * Sets if the documentation need to be generated.
	 * 
	 * @param generateDocumentation
	 *            <code>true</code> if the documentation need to be generated, <code>false</code> otherwise
	 */
	public void setGenerateDocumentation(boolean generateDocumentation) {
		this.generateDocumentation = generateDocumentation;
	}

	/**
	 * Tells if we need to create a {@link FileStatement}.
	 * 
	 * @return <code>true</code> if we need to create a {@link FileStatement}, <code>false</code> otherwise
	 */
	public boolean isGenerateFile() {
		return generateFile;
	}

	/**
	 * Sets if we need to create a {@link FileStatement}.
	 * 
	 * @param generateFile
	 *            <code>true</code> if we need to create a {@link FileStatement}, <code>false</code> otherwise
	 */
	public void setGenerateFile(boolean generateFile) {
		this.generateFile = generateFile;
	}

	/**
	 * Tells if we need to create a main template.
	 * 
	 * @return <code>true</code> if we need to create a main template, <code>false</code> otherwise
	 */
	public boolean isMainTemplate() {
		return mainTemplate;
	}

	/**
	 * Sets if we need to create a main template.
	 * 
	 * @param mainTemplate
	 *            <code>true</code> if we need to create a main template, <code>false</code> otherwise
	 */
	public void setMainTemplate(boolean mainTemplate) {
		this.mainTemplate = mainTemplate;
	}

	/**
	 * Gets the initialization path.
	 * 
	 * @return the initialization path if any, <code>null</code> otherwise
	 */
	public String getInitializationPath() {
		return initializationPath;
	}

	/**
	 * Sets the initialization path.
	 * 
	 * @param initializationPath
	 *            the initialization path
	 */
	public void setInitializationPath(String initializationPath) {
		this.initializationPath = initializationPath;
	}

	/**
	 * Gets the parent folder.
	 * 
	 * @return the parent folder
	 */
	public String getParentFolder() {
		return parentFolder;
	}

	/**
	 * Sets the parent folder.
	 * 
	 * @param parentFolder
	 *            the parent folder
	 */
	public void setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
	}

	/**
	 * Gets the {@link Set} of nsURIs.
	 * 
	 * @return the {@link Set} of nsURIs
	 */
	public Set<String> getNsURIs() {
		return nsURIs;
	}

	/**
	 * Gets the module element parameter type.
	 * 
	 * @return the module element parameter type if any, <code>null</code> otherwise
	 */
	public String getModuleElementParameterType() {
		return moduleElementParameterType;
	}

	/**
	 * Sets the module element parameter type.
	 * 
	 * @param moduleElementParameterType
	 *            the module element parameter type
	 */
	public void setModuleElementParameterType(String moduleElementParameterType) {
		this.moduleElementParameterType = moduleElementParameterType;
	}

	/**
	 * Tells if the content should be initialized.
	 * 
	 * @return <code>true</code> if the content should be initialized, <code>false</code> otherwise
	 */
	public boolean isIsInitialized() {
		return isInitialized;
	}

	/**
	 * Sets if the content should be initialized
	 * 
	 * @param isInitialized
	 *            <code>true</code> if the content should be initialized, <code>false</code> otherwise
	 */
	public void setIsInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	/**
	 * Gets the module {@link IFile} to generate.
	 * 
	 * @return the module {@link IFile} to generate
	 */
	public IFile getModuleFile() {
		final IFile res;

		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.exists() && project.isAccessible()) {
			IPath path = new Path(parentFolder);
			final IContainer container;
			if (path.segmentCount() > 1) {
				container = project.getFolder(path.removeFirstSegments(1));
			} else {
				container = project;
			}
			if (container.exists() && container.isAccessible()) {
				res = container.getFile(new Path(moduleName + '.' + AcceleoParser.MODULE_FILE_EXTENSION));
			} else {
				res = null;
			}
		} else {
			res = null;
		}

		return res;
	}

}
