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
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AcceleoNewTemplatesWizardController;
import org.eclipse.acceleo.internal.ide.ui.wizards.newfile.CreateTemplateData;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;

/**
 * The class used to configure all the JET generations of the 'AcceleoNewProjectWizard' wizard. The purpose of
 * the wizard is to create a new Acceleo module (i.e an eclipse plug-in).
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CreateModuleData {

	/**
	 * New project name.
	 */
	private String projectName;

	/**
	 * New module name.
	 */
	private String moduleName;

	/**
	 * Creates new templates in the new project.
	 */
	private List<CreateTemplateData> templates;

	/**
	 * IDs of the required plug-ins.
	 */
	private List<String> pluginDependencies;

	/**
	 * Constructor.
	 * 
	 * @param wizard
	 *            is the wizard
	 */
	public CreateModuleData(AcceleoNewProjectWizard wizard) {
		projectName = wizard.getNewProjectPage().getProjectName();
		computeModuleName();
		templates = new ArrayList<CreateTemplateData>();
		pluginDependencies = new ArrayList<String>();
		computeMetamodelDependencies(wizard);
	}

	/**
	 * Initializes the module name by using the project name.
	 */
	private void computeModuleName() {
		moduleName = projectName;
		if (moduleName.startsWith(AcceleoNewProjectWizard.MODULE_NAME_PREFIX)) {
			moduleName = moduleName.substring(AcceleoNewProjectWizard.MODULE_NAME_PREFIX.length());
		} else {
			int i = moduleName.lastIndexOf('.');
			if (i > -1) {
				moduleName = moduleName.substring(i + 1);
			}
		}
		if (moduleName.length() > 0) {
			StringTokenizer st = new StringTokenizer(moduleName, "."); //$NON-NLS-1$
			moduleName = ""; //$NON-NLS-1$
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (token.length() > 0) {
					moduleName += Character.toUpperCase(token.charAt(0)) + token.substring(1);
				}
			}
		}
	}

	/**
	 * Computes plug-ins dependencies.
	 * 
	 * @param wizard
	 *            is the wizard
	 */
	private void computeMetamodelDependencies(AcceleoNewProjectWizard wizard) {
		List<String> metamodelDone = new ArrayList<String>();
		for (AcceleoNewTemplatesWizardController controller : wizard.getTemplatePage().getControllers()) {
			CreateTemplateData data = controller.getModel();
			String metamodelURIs = data.getTemplateMetamodel();
			StringTokenizer st = new StringTokenizer(metamodelURIs, ","); //$NON-NLS-1$
			while (st.hasMoreTokens()) {
				String metamodelURI = st.nextToken().trim();
				if (!metamodelDone.contains(metamodelURI)) {
					metamodelDone.add(metamodelURI);
					EPackage metamodel = ModelUtils.getEPackage(metamodelURI);
					if (metamodel != null) {
						computeMetamodelDependencies(metamodel);
					}
				}
			}
		}
	}

	/**
	 * Computes plug-in dependency with the given metamodel.
	 * 
	 * @param metamodel
	 *            is the metamodel
	 */
	private void computeMetamodelDependencies(EPackage metamodel) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint("org.eclipse.emf.ecore.generated_package"); //$NON-NLS-1$
		if (extensionPoint != null && extensionPoint.isValid() && extensionPoint.getExtensions().length > 0) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IExtension extension = extensions[i];
				IConfigurationElement[] members = extension.getConfigurationElements();
				for (int j = 0; j < members.length; j++) {
					IConfigurationElement member = members[j];
					String mURI = member.getAttribute("uri"); //$NON-NLS-1$
					if (mURI != null && mURI.equals(metamodel.getNsURI())) {
						if (!pluginDependencies.contains(member.getNamespaceIdentifier())) {
							pluginDependencies.add(member.getNamespaceIdentifier());
						}
					}
				}
			}
		}
	}

	/**
	 * Gets new project name.
	 * 
	 * @return the new project name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Gets new module name.
	 * 
	 * @return the new module name
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * Gets the templates to create.
	 * 
	 * @return the new templates configurations
	 */
	public List<CreateTemplateData> getTemplates() {
		return templates;
	}

	/**
	 * Gets the required plug-ins.
	 * 
	 * @return the IDs of the required plug-ins
	 */
	public List<String> getPluginDependencies() {
		return pluginDependencies;
	}

}
