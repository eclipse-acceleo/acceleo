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
package org.eclipse.acceleo.internal.ide.ui.resource;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.acceleo.internal.ide.ui.wizards.module.example.AcceleoInitializationStrategyUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * Utility class to help the creation of an Acceleo project.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public final class AcceleoProjectUtils {

	/**
	 * The constructor.
	 */
	private AcceleoProjectUtils() {
		// hides the constructor.
	}

	/**
	 * Generates the files from the information in the wizard.
	 * 
	 * @param acceleoProject
	 *            The Acceleo Project
	 * @param allModules
	 *            The modules to create
	 * @param project
	 *            The project
	 * @param generateModules
	 *            Indicates if we should create the project only or if we should create the modules too.
	 * @param monitor
	 *            The progress monitor
	 * @return The latest file created
	 */
	public static IFile generateFiles(AcceleoProject acceleoProject, List<AcceleoModule> allModules,
			IProject project, boolean generateModules, IProgressMonitor monitor) {
		try {
			// Prepare Ant folder
			IFolder antTasksFolder = project.getFolder("tasks"); //$NON-NLS-1$
			if (!antTasksFolder.exists()) {
				antTasksFolder.create(true, false, monitor);
			}
			IProjectDescription description = project.getDescription();
			String[] natureIds = new String[] {IAcceleoConstants.ACCELEO_NATURE_ID,
					IAcceleoConstants.PLUGIN_NATURE_ID, IAcceleoConstants.JAVA_NATURE_ID, };
			description.setNatureIds(natureIds);
			project.setDescription(description, monitor);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
		monitor.beginTask(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Monitor"), 100); //$NON-NLS-1$
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateProjectManifest(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateBuildAcceleo(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateBuildProperties(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateActivator(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateDotProject(acceleoProject, project);
		try {
			project.close(monitor);
			project.open(monitor);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}

		if (!generateModules) {
			return null;
		}
		IFile file = null;

		for (AcceleoModule acceleoModule : allModules) {
			monitor.worked(10);
			String parentFolder = acceleoModule.getParentFolder();
			IProject moduleProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
					acceleoModule.getProjectName());
			if (moduleProject.exists() && moduleProject.isAccessible()) {
				IPath parentFolderPath = new Path(parentFolder);
				IFolder folder = moduleProject.getFolder(parentFolderPath.removeFirstSegments(1));
				file = folder.getFile(acceleoModule.getName() + "." + IAcceleoConstants.MTL_FILE_EXTENSION); //$NON-NLS-1$
				AcceleoUIGenerator.getDefault().generateAcceleoModule(acceleoModule, folder);
				if (acceleoModule.isIsInitialized()) {
					String initializationKind = acceleoModule.getInitializationKind();
					IAcceleoInitializationStrategy strategy = null;
					List<IAcceleoInitializationStrategy> initializationStrategy = AcceleoInitializationStrategyUtils
							.getInitializationStrategy();
					for (IAcceleoInitializationStrategy iAcceleoInitializationStrategy : initializationStrategy) {
						if (iAcceleoInitializationStrategy.getDescription() != null
								&& iAcceleoInitializationStrategy.getDescription().equals(initializationKind)) {
							strategy = iAcceleoInitializationStrategy;
							break;
						}
					}

					IFile exampleFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
							new Path(acceleoModule.getInitializationPath()));
					String moduleElementKind = IAcceleoInitializationStrategy.TEMPLATE_KIND;
					if (acceleoModule.getModuleElement().getKind().equals(ModuleElementKind.QUERY)) {
						moduleElementKind = IAcceleoInitializationStrategy.QUERY_KIND;
					}
					if (strategy != null && file.exists()) {
						try {
							strategy.configure(moduleElementKind, acceleoModule.getModuleElement()
									.isGenerateFile(), acceleoModule.getModuleElement().isIsMain(),
									acceleoModule.isGenerateDocumentation());
							String content = strategy.getContent(exampleFile, acceleoModule.getName(),
									acceleoModule.getMetamodelURIs(), acceleoModule.getModuleElement()
											.getParameterType());
							ByteArrayInputStream javaStream = new ByteArrayInputStream(content
									.getBytes("UTF8")); //$NON-NLS-1$
							file.setContents(javaStream, true, false, new NullProgressMonitor());
						} catch (CoreException e) {
							AcceleoUIActivator.log(e, true);
						} catch (UnsupportedEncodingException e) {
							AcceleoUIActivator.log(e, true);
						}
					}
				}
			}
		}

		return file;
	}
}
