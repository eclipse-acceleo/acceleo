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
package org.eclipse.acceleo.internal.ide.ui.wizards.project;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoBuilder;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoBuilderSettings;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoProjectUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;
import org.eclipse.jdt.launching.environments.IExecutionEnvironmentsManager;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSet;
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
	protected static final String RESOURCE_WORKING_SET_ID = "org.eclipse.ui.resourceWorkingSetPage"; //$NON-NLS-1$

	/**
	 * The "Java working set" ID.
	 */
	protected static final String JAVA_WORKING_SET_ID = "org.eclipse.jdt.ui.JavaWorkingSetPage"; //$NON-NLS-1$

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
	protected AcceleoProjectPage newProjectPage;

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

		// load the ecore models from the workspace to ensure that all models are available in the wizard
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iProject : projects) {
			try {
				if (iProject.isAccessible()) {
					List<IFile> members = this.members(iProject, IAcceleoConstants.ECORE_FILE_EXTENSION);
					for (IFile iFile : members) {
						Map<String, String> dynamicEcorePackagePaths = AcceleoPackageRegistry.INSTANCE
								.getDynamicEcorePackagePaths();
						Collection<String> values = dynamicEcorePackagePaths.values();
						boolean contains = values.contains(iFile.getFullPath().toString());
						if (!contains) {
							URI uri = URI.createPlatformResourceURI(iFile.getFullPath().toString(), true);
							AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
									AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
						}
					}
				}
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, false);
			}
		}
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
		newProjectPage = new AcceleoProjectPage(newProjectWizardName, getSelection());
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
					createProject(monitor);
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
	 * Creates the Acceleo project.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 */
	private void createProject(IProgressMonitor monitor) {
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

				boolean shouldGenerateModules = !(getContainer().getCurrentPage() instanceof WizardNewProjectCreationPage);
				convert(project, newProjectPage.getSelectedJVM(), newAcceleoModulesCreationPage
						.getAllModules(), shouldGenerateModules, monitor);

				IWorkingSet[] workingSets = newProjectPage.getSelectedWorkingSets();
				getWorkbench().getWorkingSetManager().addToWorkingSets(project, workingSets);

				project.build(IncrementalProjectBuilder.FULL_BUILD, AcceleoBuilder.BUILDER_ID,
						new HashMap<String, String>(), monitor);
			}
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Convert the empty project to an Acceleo project.
	 * 
	 * @param project
	 *            The newly created project.
	 * @param selectedJVM
	 *            The name of the selected JVM (J2SE-1.5 or JavaSE-1.6 recommended).
	 * @param allModules
	 *            The description of the module that need to be created.
	 * @param shouldGenerateModules
	 *            Indicates if we should generate the modules in the project or not. The wizard container to
	 *            display the progress monitor
	 * @param monitor
	 *            The monitor.
	 */
	public static void convert(IProject project, String selectedJVM, List<AcceleoModule> allModules,
			boolean shouldGenerateModules, IProgressMonitor monitor) {
		String generatorName = computeGeneratorName(project.getName());
		AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE.createAcceleoProject();
		acceleoProject.setName(project.getName());
		acceleoProject.setGeneratorName(generatorName);

		// Default JRE value
		acceleoProject.setJre(selectedJVM);
		if (acceleoProject.getJre() == null || acceleoProject.getJre().length() == 0) {
			acceleoProject.setJre("J2SE-1.5"); //$NON-NLS-1$			
		}

		if (shouldGenerateModules) {
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

		try {
			IProjectDescription description = project.getDescription();
			description.setNatureIds(new String[] {JavaCore.NATURE_ID,
					IBundleProjectDescription.PLUGIN_NATURE, IAcceleoConstants.ACCELEO_NATURE_ID, });
			project.setDescription(description, monitor);

			IJavaProject iJavaProject = JavaCore.create(project);

			// Compute the JRE
			List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
			IExecutionEnvironmentsManager executionEnvironmentsManager = JavaRuntime
					.getExecutionEnvironmentsManager();
			IExecutionEnvironment[] executionEnvironments = executionEnvironmentsManager
					.getExecutionEnvironments();
			for (IExecutionEnvironment iExecutionEnvironment : executionEnvironments) {
				if (acceleoProject.getJre().equals(iExecutionEnvironment.getId())) {
					entries.add(JavaCore.newContainerEntry(JavaRuntime
							.newJREContainerPath(iExecutionEnvironment)));
					break;
				}
			}

			// PDE Entry (will not be generated anymore)
			entries.add(JavaCore.newContainerEntry(new Path("org.eclipse.pde.core.requiredPlugins"))); //$NON-NLS-1$

			// Sets the input / output folders
			IFolder target = project.getFolder("src"); //$NON-NLS-1$
			if (!target.exists()) {
				target.create(true, true, monitor);
			}

			IFolder classes = project.getFolder("bin"); //$NON-NLS-1$
			if (!classes.exists()) {
				classes.create(true, true, monitor);
			}

			iJavaProject.setOutputLocation(classes.getFullPath(), monitor);
			IPackageFragmentRoot packageRoot = iJavaProject.getPackageFragmentRoot(target);
			entries.add(JavaCore.newSourceEntry(packageRoot.getPath(), new Path[] {}, new Path[] {}, classes
					.getFullPath()));

			iJavaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
			iJavaProject.open(monitor);

			AcceleoProjectUtils.generateFiles(acceleoProject, allModules, project, shouldGenerateModules,
					monitor);

			// Default settings
			AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
			settings.setCompilationKind(AcceleoBuilderSettings.COMPILATION_PLATFORM_RESOURCE);
			settings.setResourceKind(AcceleoBuilderSettings.BUILD_XMI_RESOURCE);
			settings.save();
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * Computes the name of the generator from the name of the project.
	 * 
	 * @param projectName
	 *            The project name
	 * @return The name of the generator
	 */
	private static String computeGeneratorName(String projectName) {
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
	 * Returns a list of existing member files (that validate the file extension) in this resource.
	 * 
	 * @param iContainer
	 *            The container to browse for files with the given extension.
	 * @param extension
	 *            The file extension to browse for.
	 * @return The List of files of the given extension contained by <code>container</code>.
	 * @throws CoreException
	 *             Thrown if we couldn't retrieve the children of <code>container</code>.
	 */
	private List<IFile> members(IContainer iContainer, String extension) throws CoreException {
		List<IFile> output = new ArrayList<IFile>();
		if (iContainer != null) {
			IResource[] children = iContainer.members();
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
