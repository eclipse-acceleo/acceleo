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
package org.eclipse.acceleo.internal.ide.ui.builders.runner;

import java.util.List;

/**
 * The class used to configure the 'CreateRunnableJavaWriter' JET generation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CreateRunnableAcceleoContent {

	/**
	 * The name of the project.
	 */
	private String projectName;

	/**
	 * The base package of the class to create.
	 */
	private String basePackage;

	/**
	 * The name of the class.
	 */
	private String classShortName;

	/**
	 * The name of the module file.
	 */
	private String moduleFileShortName;

	/**
	 * The name of the templates.
	 */
	private List<String> templateNames;

	/**
	 * The meta-models packages to consider.
	 */
	private List<String> packages;

	/**
	 * The resolved classpath for the project as a list of simple classpath entries.
	 */
	private List<String> resolvedClasspath;

	/**
	 * Constructor.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @param basePackage
	 *            the base package of the class to create
	 * @param classShortName
	 *            the name of the class
	 * @param moduleFileShortName
	 *            the name of the module file
	 * @param templateNames
	 *            the name of the templates
	 * @param packages
	 *            the meta-models packages to consider
	 * @param resolvedClasspath
	 *            resolved classpath for the project as a list of simple classpath entries
	 */
	public CreateRunnableAcceleoContent(String projectName, String basePackage, String classShortName,
			String moduleFileShortName, List<String> templateNames, List<String> packages,
			List<String> resolvedClasspath) {
		super();
		this.projectName = projectName;
		this.basePackage = basePackage;
		this.classShortName = classShortName;
		this.moduleFileShortName = moduleFileShortName;
		this.templateNames = templateNames;
		this.packages = packages;
		this.resolvedClasspath = resolvedClasspath;
	}

	/**
	 * Gets the name of the project.
	 * 
	 * @return the name of the project
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Gets the base package of the class to create.
	 * 
	 * @return the base package of the class to create
	 */
	public String getBasePackage() {
		return basePackage;
	}

	/**
	 * Gets the name of the class.
	 * 
	 * @return the name of the class
	 */
	public String getClassShortName() {
		return classShortName;
	}

	/**
	 * Gets the name of the module file.
	 * 
	 * @return the name of the module file
	 */
	public String getModuleFileShortName() {
		return moduleFileShortName;
	}

	/**
	 * Gets the name of the templates.
	 * 
	 * @return the name of the templates
	 */
	public List<String> getTemplateNames() {
		return templateNames;
	}

	/**
	 * Gets the meta-models packages to consider.
	 * 
	 * @return the meta-models packages to consider
	 */
	public List<String> getPackages() {
		return packages;
	}

	/**
	 * Gets the resolved classpath for the project as a list of simple classpath entries.
	 * 
	 * @return the resolved classpath for the project as a list of simple classpath entries
	 */
	public List<String> getResolvedClasspath() {
		return resolvedClasspath;
	}
}
