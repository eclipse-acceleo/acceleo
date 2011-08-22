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
package org.eclipse.acceleo.internal.ide.ui.wizards.project;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.acceleo.internal.ide.ui.wizards.module.example.AcceleoInitializationStrategyUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.osgi.framework.Bundle;

/**
 * The Acceleo project wizard.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	/**
	 * Default prefix of the new module.
	 */
	public static final String MODULE_NAME_PREFIX = "org.eclipse.acceleo.module."; //$NON-NLS-1$

	/**
	 * The initial project name.
	 */
	public static final String INITIAL_PROJECT_NAME = MODULE_NAME_PREFIX + "sample"; //$NON-NLS-1$

	/**
	 * The "Resource working set" ID.
	 */
	private static final String RESOURCE_WORKING_SET_ID = "org.eclipse.ui.resourceWorkingSetPage"; //$NON-NLS-1$

	/**
	 * The "Java working set" ID.
	 */
	private static final String JAVA_WORKING_SET_ID = "org.eclipse.jdt.ui.JavaWorkingSetPage"; //$NON-NLS-1$

	/**
	 * The configuration element.
	 */
	protected IConfigurationElement configurationElement;

	/**
	 * The current workbench.
	 */
	protected IWorkbench workbench;

	/**
	 * The current selection.
	 */
	protected IStructuredSelection selection;

	/**
	 * This is the first page of the new project wizard.
	 */
	protected WizardNewProjectCreationPage newProjectPage;

	/**
	 * This is the second page of the new project wizard.
	 */
	protected AcceleoModulesCreationPage newAcceleoModulesCreationPage;

	/**
	 * The container.
	 */
	private String container;

	/**
	 * The constructor.
	 */
	public AcceleoProjectWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Title")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench iWorkbench, IStructuredSelection iSelection) {
		this.workbench = iWorkbench;
		this.selection = iSelection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		// The first page with the project name, its default location and the working set selection
		String newProjectWizardName = AcceleoUIMessages.getString("AcceleoNewProjectWizard.Page.Name"); //$NON-NLS-1$
		newProjectPage = new WizardNewProjectCreationPage(newProjectWizardName) {
			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#createControl(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			public void createControl(org.eclipse.swt.widgets.Composite parent) {
				super.createControl(parent);
				createWorkingSetGroup((Composite)getControl(), getSelection(), new String[] {
						RESOURCE_WORKING_SET_ID, JAVA_WORKING_SET_ID, });
				Dialog.applyDialogFont(getControl());
			}
		};
		newProjectPage.setInitialProjectName(INITIAL_PROJECT_NAME);
		newProjectPage.setTitle(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Title")); //$NON-NLS-1$
		newProjectPage
				.setDescription(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Page.Description")); //$NON-NLS-1$
		newProjectPage.setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
		addPage(newProjectPage);

		newAcceleoModulesCreationPage = new AcceleoModulesCreationPage();
		addPage(newAcceleoModulesCreationPage);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		if (page instanceof WizardNewProjectCreationPage) {
			WizardNewProjectCreationPage newPage = (WizardNewProjectCreationPage)page;
			container = newPage.getProjectName();
			container = container + "/src/" + container.replaceAll("\\.", "/"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
			container = container + "/common"; //$NON-NLS-1$
			this.newAcceleoModulesCreationPage.setContainer(container);
		}
		return super.getNextPage(page);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		this.configurationElement = config;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		IWizardContainer iWizardContainer = this.getContainer();
		IWizardPage currentPage = iWizardContainer.getCurrentPage();
		if (currentPage instanceof WizardNewProjectCreationPage) {
			return newProjectPage.isPageComplete();
		}
		return newProjectPage.isPageComplete() && newAcceleoModulesCreationPage.isPageComplete();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#needsProgressMonitor()
	 */
	@Override
	public boolean needsProgressMonitor() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			IWizardContainer iWizardContainer = this.getContainer();

			IRunnableWithProgress projectCreation = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					try {
						IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
								newProjectPage.getProjectName());
						IPath location = newProjectPage.getLocationPath();
						if (!project.exists()) {
							IProjectDescription desc = project.getWorkspace().newProjectDescription(
									newProjectPage.getProjectName());
							if (ResourcesPlugin.getWorkspace().getRoot().getLocation().equals(location)) {
								location = null;
							}
							desc.setLocation(location);
							project.create(desc, monitor);
							project.open(monitor);
							convert(project, AcceleoProjectWizard.this, monitor);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}
				}
			};
			iWizardContainer.run(false, false, projectCreation);

			// Update the perspective.
			BasicNewProjectResourceWizard.updatePerspective(this.configurationElement);
			return true;
		} catch (InvocationTargetException e) {
			AcceleoUIActivator.log(e, true);
		} catch (InterruptedException e) {
			AcceleoUIActivator.log(e, true);
		}
		return false;
	}

	/**
	 * Convert the empty project to an Acceleo project.
	 * 
	 * @param project
	 *            The newly created project.
	 * @param wizard
	 *            The project wizard.
	 * @param monitor
	 *            The monitor.
	 */
	private void convert(IProject project, AcceleoProjectWizard wizard, IProgressMonitor monitor) {
		String projectName = wizard.newProjectPage.getProjectName();
		String generatorName = this.computeGeneratorName(projectName);
		AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE.createAcceleoProject();
		acceleoProject.setName(projectName);
		acceleoProject.setGeneratorName(generatorName);

		List<AcceleoModule> allModules = this.newAcceleoModulesCreationPage.getAllModules();
		IWizardContainer iWizardContainer = this.getContainer();
		IWizardPage currentPage = iWizardContainer.getCurrentPage();
		if (!(currentPage instanceof WizardNewProjectCreationPage)) {
			for (AcceleoModule acceleoModule : allModules) {
				String parentFolder = acceleoModule.getParentFolder();
				IProject moduleProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
						acceleoModule.getProjectName());
				if (moduleProject.exists() && moduleProject.isAccessible()
						&& acceleoModule.getModuleElement() != null
						&& acceleoModule.getModuleElement().isIsMain()) {
					IPath parentFolderPath = new Path(parentFolder);
					IFolder folder = moduleProject.getFolder(parentFolderPath.removeFirstSegments(1));
					acceleoProject.getExportedPackages().add(
							folder.getProjectRelativePath().removeFirstSegments(1).toString().replaceAll("/", //$NON-NLS-1$
									"\\.")); //$NON-NLS-1$
				}
				// Calculate project dependencies
				List<String> metamodelURIs = acceleoModule.getMetamodelURIs();
				for (String metamodelURI : metamodelURIs) {
					// Find the project containing this metamodel and add a dependency to it.
					EPackage ePackage = AcceleoPackageRegistry.INSTANCE.getEPackage(metamodelURI);
					if (ePackage != null && !(ePackage instanceof EcorePackage)) {
						Bundle bundle = AcceleoWorkspaceUtil.getBundle(ePackage.getClass());
						acceleoProject.getPluginDependencies().add(bundle.getSymbolicName());
					}
				}
			}
		}
		// Generate files
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
		AcceleoUIGenerator.getDefault().generateProjectSettings(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateProjectClasspath(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateProjectManifest(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateBuildProperties(acceleoProject, project);
		monitor.worked(10);
		AcceleoUIGenerator.getDefault().generateActivator(acceleoProject, project);

		if (currentPage instanceof WizardNewProjectCreationPage) {
			return;
		}
		for (AcceleoModule acceleoModule : allModules) {
			monitor.worked(10);
			String parentFolder = acceleoModule.getParentFolder();
			IProject moduleProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
					acceleoModule.getProjectName());
			if (moduleProject.exists() && moduleProject.isAccessible()) {
				IPath parentFolderPath = new Path(parentFolder);
				IFolder folder = moduleProject.getFolder(parentFolderPath.removeFirstSegments(1));
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
					IFile file = folder.getFile(acceleoModule.getName()
							+ "." + IAcceleoConstants.MTL_FILE_EXTENSION); //$NON-NLS-1$
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
	}

	/**
	 * Computes the name of the generator from the name of the project.
	 * 
	 * @param projectName
	 *            The project name
	 * @return The name of the generator
	 */
	private String computeGeneratorName(String projectName) {
		String generatorName = projectName;
		if (generatorName.startsWith(AcceleoProjectWizard.MODULE_NAME_PREFIX)) {
			generatorName = generatorName.substring(AcceleoProjectWizard.MODULE_NAME_PREFIX.length());
		} else {
			int i = generatorName.lastIndexOf('.');
			if (i > -1) {
				generatorName = generatorName.substring(i + 1);
			}
		}
		if (generatorName.length() > 0) {
			StringTokenizer st = new StringTokenizer(generatorName, "."); //$NON-NLS-1$
			generatorName = ""; //$NON-NLS-1$
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (token.length() > 0) {
					generatorName += Character.toUpperCase(token.charAt(0)) + token.substring(1);
				}
			}
		}
		return generatorName;
	}

	/**
	 * Returns the selection which was passed to <code>init</code>.
	 * 
	 * @return the selection
	 */
	public IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * Returns the workbench which was passed to <code>init</code>.
	 * 
	 * @return the workbench
	 */
	public IWorkbench getWorkbench() {
		return workbench;
	}
}
