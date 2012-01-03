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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;

/**
 * A wizard to create a new UI launcher (i.e an eclipse plug-in) for one or several Acceleo modules.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewProjectUIWizard extends Wizard implements INewWizard {

	/**
	 * Default prefix of the new UI launcher.
	 */
	public static final String MODULE_UI_NAME_PREFIX = "org.eclipse.acceleo.module."; //$NON-NLS-1$

	/**
	 * Default suffix of the new UI launcher.
	 */
	public static final String MODULE_UI_NAME_SUFFIX = ".ui"; //$NON-NLS-1$

	/**
	 * This is a new project wizard page.
	 */
	protected WizardNewAcceleoProjectCreationPage newProjectPage;

	/**
	 * The workspace selection when the wizard has been opened.
	 */
	private ISelection selection;

	/**
	 * This is a wizard page to select the Acceleo modules to be considered.
	 */
	private WizardNewProjectReferencePage projectReferencesPage;

	/**
	 * This is a wizard page used to configure the UI of the new module launcher.
	 */
	private AcceleoNewUIConfigurationPage uiConfigPage;

	/**
	 * New Acceleo UI project wizard page.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private class WizardNewAcceleoProjectCreationPage extends WizardNewProjectCreationPage {

		/**
		 * Constructor.
		 */
		public WizardNewAcceleoProjectCreationPage() {
			super(AcceleoUIMessages.getString("AcceleoNewProjectUIWizard.Module.Title")); //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
		 */
		@Override
		public boolean isPageComplete() {
			return validateProjectExists() || super.isPageComplete();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.dialogs.WizardNewProjectCreationPage#validatePage()
		 */
		@Override
		protected boolean validatePage() {
			return validateProjectExists() || super.validatePage();
		}

		/**
		 * Indicates if the specified project already exists.
		 * 
		 * @return true if the specified project already exists
		 */
		private boolean validateProjectExists() {
			IProject handle = getProjectHandle();
			if (handle.exists()) {
				setMessage(
						AcceleoUIMessages.getString("AcceleoNewProjectUIWizard.OverwriteProject"), IMessageProvider.WARNING); //$NON-NLS-1$
				return true;
			}
			return false;
		}
	}

	/**
	 * Constructor.
	 */
	public AcceleoNewProjectUIWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoNewProjectUIWizard.Title")); //$NON-NLS-1$
	}

	/**
	 * Gets the new project wizard page.
	 * 
	 * @return the new project wizard page
	 */
	public WizardNewProjectCreationPage getNewProjectPage() {
		return newProjectPage;
	}

	/**
	 * Gets the wizard page where the Acceleo modules have been selected.
	 * 
	 * @return the Acceleo modules wizard page
	 */
	public WizardNewProjectReferencePage getProjectReferencesPage() {
		return projectReferencesPage;
	}

	/**
	 * Gets the wizard page used to configure the UI of the new module launcher.
	 * 
	 * @return the UI page of the new module launcher
	 */
	public AcceleoNewUIConfigurationPage getConfigurationPage() {
		return uiConfigPage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection aSelection) {
		this.selection = aSelection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		IProject selectedProject = getSelectedProject();
		ImageDescriptor wizardImage = AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif"); //$NON-NLS-1$
		newProjectPage = new WizardNewAcceleoProjectCreationPage();
		String initialProjectName;
		if (selectedProject != null) {
			if (selectedProject.getName().endsWith(MODULE_UI_NAME_SUFFIX)) {
				initialProjectName = selectedProject.getName();
			} else {
				initialProjectName = selectedProject.getName() + MODULE_UI_NAME_SUFFIX;
			}
		} else {
			initialProjectName = MODULE_UI_NAME_PREFIX + '*' + MODULE_UI_NAME_SUFFIX;
		}
		newProjectPage.setInitialProjectName(initialProjectName);
		newProjectPage.setTitle(AcceleoUIMessages.getString("AcceleoNewProjectUIWizard.Title")); //$NON-NLS-1$
		newProjectPage.setDescription(AcceleoUIMessages.getString("AcceleoNewProjectUIWizard.Description")); //$NON-NLS-1$
		newProjectPage.setImageDescriptor(wizardImage);
		addPage(newProjectPage);
		projectReferencesPage = new WizardNewProjectReferencePage(AcceleoUIMessages
				.getString("AcceleoNewProjectUIWizard.Reference.Name")); //$NON-NLS-1$ 
		projectReferencesPage.setDescription(AcceleoUIMessages
				.getString("AcceleoNewProjectUIWizard.Reference.Description")); //$NON-NLS-1$
		projectReferencesPage.setImageDescriptor(wizardImage);
		addPage(projectReferencesPage);
		uiConfigPage = new AcceleoNewUIConfigurationPage(AcceleoUIMessages
				.getString("AcceleoNewProjectUIWizard.Config.Name"), initialProjectName); //$NON-NLS-1$
		addPage(uiConfigPage);
	}

	/**
	 * Gets the current selection in the workbench, and returns the corresponding project.
	 * 
	 * @return the selected project
	 */
	private IProject getSelectedProject() {
		IProject selectedProject = null;
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection aSelection = (IStructuredSelection)selection;
			if (aSelection.size() > 0) {
				Object element = aSelection.getFirstElement();
				if (element instanceof IAdaptable) {
					element = ((IAdaptable)element).getAdapter(IResource.class);
				}
				if (element instanceof IProject) {
					selectedProject = (IProject)element;
				} else if (element instanceof IResource) {
					selectedProject = ((IResource)element).getProject();
				}
			}
		}
		return selectedProject;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		IProject selectedProject = getSelectedProject();
		if (selectedProject != null
				&& ((Composite)projectReferencesPage.getControl()).getChildren().length > 1
				&& ((Composite)projectReferencesPage.getControl()).getChildren()[1] instanceof Table) {
			String selectedProjectName = selectedProject.getName();
			if (selectedProjectName.endsWith(MODULE_UI_NAME_SUFFIX)) {
				String newName = selectedProjectName.substring(0, selectedProjectName.length()
						- MODULE_UI_NAME_SUFFIX.length());
				if (ResourcesPlugin.getWorkspace().getRoot().getProject(newName).exists()) {
					selectedProjectName = newName;
				}
			}
			Table table = (Table)((Composite)projectReferencesPage.getControl()).getChildren()[1];
			TableItem[] children = table.getItems();
			for (int i = 0; i < children.length; i++) {
				TableItem item = children[i];
				Object data = item.getData();
				if (data instanceof IProject) {
					String dataName = ((IProject)data).getName();
					if (selectedProjectName.equals(dataName)) {
						item.setChecked(true);
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				CreateModuleUIData arg = new CreateModuleUIData(AcceleoNewProjectUIWizard.this);
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
						newProjectPage.getProjectName());
				IPath location = newProjectPage.getLocationPath();
				if (!project.exists()) {
					IProjectDescription desc = project.getWorkspace().newProjectDescription(
							newProjectPage.getProjectName());
					if (location != null
							&& ResourcesPlugin.getWorkspace().getRoot().getLocation().equals(location)) {
						location = null;
					}
					desc.setLocation(location);
					project.create(desc, monitor);
					project.open(monitor);
				} else if (!project.isOpen()) {
					project.open(monitor);
				}
				convert(project, arg, monitor);
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(create, null);
			return true;
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
			return false;
		}
	}

	/**
	 * Converts the given project to Acceleo Module UI project.
	 * 
	 * @param project
	 *            is the project to convert
	 * @param arg
	 *            is the class used to configure all the JET generations
	 * @param monitor
	 *            is the monitor
	 */
	protected void convert(IProject project, CreateModuleUIData arg, IProgressMonitor monitor) {
		String baseFolder = "/src/" + arg.getProjectName().replaceAll("\\.", "/"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		createDefaultImage(project, monitor);
		CreateModuleUIActivatorWriter activatorWriter = new CreateModuleUIActivatorWriter();
		String text = activatorWriter.generate(arg);
		IPath file = new Path(baseFolder + "/Activator.java"); //$NON-NLS-1$
		createFile(project, file, text, false, monitor);
		CreateModuleUIBuildWriter buildWriter = new CreateModuleUIBuildWriter();
		text = buildWriter.generate(arg);
		file = new Path("build.properties"); //$NON-NLS-1$
		createFile(project, file, text, true, monitor);
		CreateModuleUIClasspathWriter classpathWriter = new CreateModuleUIClasspathWriter();
		text = classpathWriter.generate(arg);
		file = new Path(".classpath"); //$NON-NLS-1$
		createFile(project, file, text, true, monitor);
		CreateModuleUIMANIFESTWriter manifestWriter = new CreateModuleUIMANIFESTWriter();
		text = manifestWriter.generate(arg);
		file = new Path("META-INF/MANIFEST.MF"); //$NON-NLS-1$
		createFile(project, file, text, true, monitor);
		CreateModuleUIProjectWriter projectWriter = new CreateModuleUIProjectWriter();
		text = projectWriter.generate(arg);
		file = new Path(".project"); //$NON-NLS-1$
		createFile(project, file, text, false, monitor);
		CreateModuleUISettingsWriter settingsWriter = new CreateModuleUISettingsWriter();
		text = settingsWriter.generate(arg);
		file = new Path("/.settings/org.eclipse.jdt.core.prefs"); //$NON-NLS-1$
		createFile(project, file, text, true, monitor);
		CreateModuleUIGenerateAllWriter generateAllWriter = new CreateModuleUIGenerateAllWriter();
		text = generateAllWriter.generate(arg);
		file = new Path(baseFolder + "/common/GenerateAll.java"); //$NON-NLS-1$
		createFile(project, file, text, false, monitor);
		CreateModuleUIPluginXMLWriter pluginXMLWriter = new CreateModuleUIPluginXMLWriter();
		text = pluginXMLWriter.generate(arg);
		file = new Path("plugin.xml"); //$NON-NLS-1$
		createFile(project, file, text, true, monitor);
		CreateModuleUIPopupMenuActionWriter actionPopupWriter = new CreateModuleUIPopupMenuActionWriter();
		text = actionPopupWriter.generate(arg);
		file = new Path(baseFolder
				+ "/popupMenus/AcceleoGenerate" + arg.getModuleNameWithoutSpaces() + "Action.java"); //$NON-NLS-1$ //$NON-NLS-2$
		createFile(project, file, text, false, monitor);
	}

	/**
	 * Creates a 'default.gif' image in the 'icons' folder of the project.
	 * 
	 * @param project
	 *            is a project
	 * @param monitor
	 *            is the monitor
	 */
	private void createDefaultImage(IProject project, IProgressMonitor monitor) {
		try {
			URL imageURL = Platform.getBundle(AcceleoUIActivator.PLUGIN_ID).getEntry(
					"icons/AcceleoEditor.gif"); //$NON-NLS-1$
			imageURL = FileLocator.toFileURL(imageURL);
			File imageFile = new File(imageURL.getFile());
			IFolder icons = project.getFolder(new Path("icons")); //$NON-NLS-1$
			if (!icons.exists()) {
				icons.create(true, true, monitor);
			}
			if (!icons.exists(new Path("default.gif"))) { //$NON-NLS-1$
				copyFile(imageFile, icons.getFile("default.gif").getLocation().toFile()); //$NON-NLS-1$
				icons.refreshLocal(1, monitor);
			}
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		}
	}

	/**
	 * Creates a file and its content.
	 * 
	 * @param project
	 *            is the project
	 * @param projectRelativePath
	 *            is the path of the file to create, relative to the project
	 * @param content
	 *            is the content of the new file
	 * @param firstTimeOnly
	 *            indicates if the file generation is ignored when the file already exists
	 * @param monitor
	 *            is the monitor
	 */
	private void createFile(IProject project, IPath projectRelativePath, String content,
			boolean firstTimeOnly, IProgressMonitor monitor) {
		try {
			IContainer container = project;
			String[] folders = projectRelativePath.removeLastSegments(1).segments();
			for (int i = 0; i < folders.length; i++) {
				container = container.getFolder(new Path(folders[i]));
				if (!container.exists()) {
					((IFolder)container).create(true, true, monitor);
				}
			}
			IFile file = container.getFile(new Path(projectRelativePath.lastSegment()));
			if (!file.exists() && file.getParent().exists()) {
				IResource[] members = file.getParent().members(IResource.FILE);
				for (int i = 0; i < members.length; i++) {
					if (members[i] instanceof IFile
							&& file.getName().toLowerCase().equals(members[i].getName().toLowerCase())) {
						file = (IFile)members[i];
						break;
					}
				}
			}
			String text = content;
			if (!firstTimeOnly && file.exists() && "java".equals(file.getFileExtension())) { //$NON-NLS-1$
				String jmergeFile = URI.createPlatformPluginURI(
						"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
				JControlModel model = new JControlModel();
				ASTFacadeHelper astFacadeHelper = new ASTFacadeHelper();
				model.initialize(astFacadeHelper, jmergeFile);
				if (model.canMerge()) {
					JMerger jMerger = new JMerger(model);
					jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForContents(text));
					try {
						jMerger.setTargetCompilationUnit(jMerger
								.createCompilationUnitForInputStream(new FileInputStream(file.getLocation()
										.toFile()))); // target=last generated code
						jMerger.merge();
						text = jMerger.getTargetCompilationUnit().getContents();
					} catch (FileNotFoundException e) {
						AcceleoUIActivator.getDefault().getLog().log(
								new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
					}
				} else {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, AcceleoUIMessages
									.getString("CreateRunnableAcceleoOperation.MergerUnusable"), null)); //$NON-NLS-1$
				}
			}
			if (!file.exists()
					|| (!firstTimeOnly && !text.equals(FileContent
							.getFileContent(file.getLocation().toFile()).toString()))) {
				ByteArrayInputStream javaStream = new ByteArrayInputStream(text.getBytes("UTF8")); //$NON-NLS-1$
				if (!file.exists()) {
					file.create(javaStream, true, monitor);
				} else if (!firstTimeOnly) {
					file.setContents(javaStream, true, false, monitor);
				}
			}
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (UnsupportedEncodingException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		}
	}

	/**
	 * Copy the given file.
	 * 
	 * @param sourceFile
	 *            is the file to copy
	 * @param targetFile
	 *            is the file to create
	 * @throws FileNotFoundException
	 *             the input file isn't found
	 * @throws IOException
	 *             if an I/O error occurs.
	 */
	private void copyFile(File sourceFile, File targetFile) throws FileNotFoundException, IOException {
		if (targetFile.exists()) {
			return;
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(sourceFile);
			out = new FileOutputStream(targetFile);

			final int length = 8 * 1024;
			byte[] buffer = new byte[length];
			int count = 0;
			do {
				out.write(buffer, 0, count);
				count = in.read(buffer, 0, buffer.length);
			} while (count != -1);
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}

}
