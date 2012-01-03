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
package org.eclipse.acceleo.internal.ide.ui.wizards.newproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;

/**
 * The class used to configure all the JET generations of the 'AcceleoNewProjectUIWizard' wizard. The purpose
 * of the wizard is to create a new UI launcher (i.e an eclipse plug-in) for one or several Acceleo modules.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CreateModuleUIData {

	/**
	 * New UI project name.
	 */
	private String projectName;

	/**
	 * Module name.
	 */
	private String moduleName;

	/**
	 * IDs of the required plug-ins.
	 */
	private List<String> pluginDependencies;

	/**
	 * Acceleo templates (EMTL files).
	 */
	private List<String> moduleTemplates;

	/**
	 * Acceleo templates plug-ins.
	 * <p>
	 * <code>assert moduleTemplatesPlugins.size() == moduleTemplates.size()</code>
	 * </p>
	 */
	private List<String> moduleTemplatesPlugins;

	/**
	 * Java classes corresponding to the Acceleo templates.
	 * <p>
	 * <code>assert moduleJavaClasses.size() == moduleTemplates.size()</code>
	 * </p>
	 */
	private List<String> moduleJavaClasses;

	/**
	 * The code generation (popup action...) is only available for the file which name validates the filter.
	 */
	private String modelNameFilter;

	/**
	 * It contains 'Java' code. It defines the way to access the target folder from the model file.
	 * <p>
	 * <code> IContainer target = model.getProject(); </code>
	 * </p>
	 */
	private String targetFolderAccess;

	/**
	 * Mapping between the Java class and the project name.
	 */
	private Map<String, String> javaClass2ProjectName = new HashMap<String, String>();

	/**
	 * Constructor.
	 * 
	 * @param wizard
	 *            is the wizard
	 */
	public CreateModuleUIData(AcceleoNewProjectUIWizard wizard) {
		projectName = wizard.getNewProjectPage().getProjectName();
		moduleName = wizard.getConfigurationPage().getModuleName();
		pluginDependencies = new ArrayList<String>();
		moduleTemplates = new ArrayList<String>();
		moduleTemplatesPlugins = new ArrayList<String>();
		moduleJavaClasses = new ArrayList<String>();
		computeTemplatesConfiguration(wizard.getProjectReferencesPage());
		modelNameFilter = wizard.getConfigurationPage().getModelNameFilter();
		targetFolderAccess = wizard.getConfigurationPage().getTargetFolderAccess();
	}

	/**
	 * Initializes the plug-ins dependencies and the API which runs the code generation by visiting each
	 * project reference.
	 * 
	 * @param page
	 *            is the standard project reference page
	 */
	private void computeTemplatesConfiguration(WizardNewProjectReferencePage page) {
		if (page.getReferencedProjects() != null) {
			IProject[] projects = page.getReferencedProjects();
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				pluginDependencies.add(project.getName());
				AcceleoProject acceleoProject = new AcceleoProject(project);
				computeModuleJavaClasses(acceleoProject, project);
			}
		}
	}

	/**
	 * Initializes the API which runs the code generation by visiting each Acceleo file of the given project.
	 * It adds a new entry point for the code generation if a java file is attached to the Acceleo file.
	 * 
	 * @param acceleoProject
	 *            is the Acceleo project
	 * @param container
	 *            is the current container of the project
	 */
	private void computeModuleJavaClasses(AcceleoProject acceleoProject, IContainer container) {
		if (container != null) {
			try {
				IResource[] members = container.members();
				for (int i = 0; i < members.length; i++) {
					IResource member = members[i];
					if (member instanceof IFile) {
						IFile fileAcceleo = (IFile)member;
						IPath javaFileName = new Path(Character.toUpperCase(fileAcceleo.getName().charAt(0))
								+ fileAcceleo.getName().substring(1)).removeFileExtension().addFileExtension(
								"java"); //$NON-NLS-1$
						if (IAcceleoConstants.MTL_FILE_EXTENSION.equals(fileAcceleo.getFileExtension())
								&& fileAcceleo.getParent().getFile(javaFileName).exists()) {
							String templatePath = '/'
									+ acceleoProject.getPackageName(fileAcceleo).replaceAll("\\.", "/") //$NON-NLS-1$ //$NON-NLS-2$
									+ '/'
									+ new Path(fileAcceleo.getName()).removeFileExtension().addFileExtension(
											IAcceleoConstants.EMTL_FILE_EXTENSION).lastSegment();
							moduleTemplates.add(templatePath);
							moduleTemplatesPlugins.add(container.getProject().getName());
							String javaClassName = acceleoProject.getPackageName(fileAcceleo) + "." //$NON-NLS-1$
									+ javaFileName.removeFileExtension().lastSegment();
							moduleJavaClasses.add(javaClassName);
							javaClass2ProjectName.put(javaClassName, container.getProject().getName());
						}
					} else if (member instanceof IContainer) {
						computeModuleJavaClasses(acceleoProject, (IContainer)member);
					}
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(
						new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
			}
		}
	}

	/**
	 * Gets the new UI project name.
	 * 
	 * @return the new UI project name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Gets the module name.
	 * 
	 * @return the module name
	 */
	public String getModuleNameWithSpaces() {
		return moduleName;
	}

	/**
	 * Gets the module name without spaces characters.
	 * 
	 * @return the module name without spaces characters
	 */
	public String getModuleNameWithoutSpaces() {
		return moduleName.replaceAll(" ", "").replaceAll("\t", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * Gets required plug-ins IDs.
	 * 
	 * @return required plug-ins IDs
	 */
	public List<String> getPluginDependencies() {
		return pluginDependencies;
	}

	/**
	 * Acceleo templates (EMTL files).
	 * 
	 * @return the templates
	 */
	public List<String> getModuleTemplates() {
		return moduleTemplates;
	}

	/**
	 * Acceleo templates plug-ins.
	 * <p>
	 * <code>assert getModuleTemplatesPlugins().size() == getModuleTemplates().size()</code>
	 * </p>
	 * 
	 * @return the templates plug-ins
	 */
	public List<String> getModuleTemplatesPlugins() {
		return moduleTemplatesPlugins;
	}

	/**
	 * Gets Java classes corresponding to the Acceleo templates.
	 * <p>
	 * <code>assert getModuleJavaClasses().size() == getModuleTemplates().size()</code>
	 * </p>
	 * 
	 * @return Java classes names
	 */
	public List<String> getModuleJavaClasses() {
		return moduleJavaClasses;
	}

	/**
	 * Gets the model name filter. The code generation (popup action...) is only available for the file which
	 * name validates the filter.
	 * 
	 * @return the model name filter
	 */
	public String getModelNameFilter() {
		return modelNameFilter;
	}

	/**
	 * Gets the 'Java' code that defines the way to access the target folder from the model file.
	 * <p>
	 * <code> IContainer target = model.getProject(); </code>
	 * </p>
	 * 
	 * @return the 'Java' code to access the target folder
	 */
	public String getTargetFolderAccess() {
		return targetFolderAccess;
	}

	/**
	 * Returns the project name containing the java class.
	 * 
	 * @param className
	 *            The class name
	 * @return The project name containing the java class.
	 */
	public String getProjectFromClass(String className) {
		return this.javaClass2ProjectName.get(className);
	}
}
