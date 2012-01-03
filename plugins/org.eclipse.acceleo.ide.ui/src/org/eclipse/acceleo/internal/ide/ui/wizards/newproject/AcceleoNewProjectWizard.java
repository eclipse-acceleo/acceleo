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
import java.io.UnsupportedEncodingException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AcceleoNewTemplatesWizard;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

/**
 * A wizard to create a new Acceleo module (i.e an eclipse plug-in) in the workspace.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewProjectWizard extends AcceleoNewTemplatesWizard {

	/**
	 * Default prefix of the new module.
	 */
	public static final String MODULE_NAME_PREFIX = "org.eclipse.acceleo.module."; //$NON-NLS-1$

	/**
	 * This is a new project wizard page.
	 */
	protected WizardNewProjectCreationPage newProjectPage;

	/**
	 * Constructor.
	 */
	public AcceleoNewProjectWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Title")); //$NON-NLS-1$
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AcceleoNewTemplatesWizard#multipleTemplates()
	 */
	@Override
	protected boolean multipleTemplates() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AcceleoNewTemplatesWizard#addPages()
	 */
	@Override
	public void addPages() {
		newProjectPage = new WizardNewProjectCreationPage(AcceleoUIMessages
				.getString("AcceleoNewProjectWizard.Page.Name")); //$NON-NLS-1$
		newProjectPage.setInitialProjectName(MODULE_NAME_PREFIX + "sample"); //$NON-NLS-1$
		newProjectPage.setTitle(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Title")); //$NON-NLS-1$
		newProjectPage
				.setDescription(AcceleoUIMessages.getString("AcceleoNewProjectWizard.Page.Description")); //$NON-NLS-1$
		newProjectPage.setImageDescriptor(AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif")); //$NON-NLS-1$
		addPage(newProjectPage);
		super.addPages();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return newProjectPage.isPageComplete();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AcceleoNewTemplatesWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		IWorkspaceRunnable create = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				CreateModuleData arg = new CreateModuleData(AcceleoNewProjectWizard.this);
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
					convert(project, arg, monitor);
				}
				if (!project.isOpen()) {
					project.open(monitor);
				}
				AcceleoNewProjectWizard.super.performFinish();
				appendManifestExportedPackages(project, monitor);
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
	 * Converts the given project to Acceleo Module project.
	 * 
	 * @param project
	 *            is the project to convert
	 * @param arg
	 *            is the class used to configure all the JET generations
	 * @param monitor
	 *            is the monitor
	 */
	protected void convert(IProject project, CreateModuleData arg, IProgressMonitor monitor) {
		CreateModuleActivatorWriter activatorWriter = new CreateModuleActivatorWriter();
		String text = activatorWriter.generate(arg);
		IPath file = new Path("/src/" + arg.getProjectName().replaceAll("\\.", "/") + "/Activator.java"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		createFile(project, file, text, monitor);
		CreateModuleBuildWriter buildWriter = new CreateModuleBuildWriter();
		text = buildWriter.generate(arg);
		file = new Path("build.properties"); //$NON-NLS-1$
		createFile(project, file, text, monitor);
		CreateModuleClasspathWriter classpathWriter = new CreateModuleClasspathWriter();
		text = classpathWriter.generate(arg);
		file = new Path(".classpath"); //$NON-NLS-1$
		createFile(project, file, text, monitor);
		CreateModuleMANIFESTWriter manifestWriter = new CreateModuleMANIFESTWriter();
		text = manifestWriter.generate(arg);
		file = new Path("META-INF/MANIFEST.MF"); //$NON-NLS-1$
		createFile(project, file, text, monitor);
		CreateModuleProjectWriter projectWriter = new CreateModuleProjectWriter();
		text = projectWriter.generate(arg);
		file = new Path(".project"); //$NON-NLS-1$
		createFile(project, file, text, monitor);
		CreateModuleSettingsWriter settingsWriter = new CreateModuleSettingsWriter();
		text = settingsWriter.generate(arg);
		file = new Path("/.settings/org.eclipse.jdt.core.prefs"); //$NON-NLS-1$
		createFile(project, file, text, monitor);
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
	 * @param monitor
	 *            is the monitor
	 */
	private void createFile(IProject project, IPath projectRelativePath, String content,
			IProgressMonitor monitor) {
		try {
			ByteArrayInputStream javaStream = new ByteArrayInputStream(content.getBytes("UTF8")); //$NON-NLS-1$
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
			if (!file.exists()) {
				file.create(javaStream, true, monitor);
			} else {
				file.setContents(javaStream, true, false, monitor);
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
	 * It modifies the MANIFEST.MF file and appends the exported packages.
	 * 
	 * @param project
	 *            is the module project
	 * @param monitor
	 *            is the monitor
	 * @throws CoreException
	 *             if this method fails
	 */
	protected void appendManifestExportedPackages(IProject project, IProgressMonitor monitor)
			throws CoreException {
		StringBuffer exportedContent = new StringBuffer();
		computeExportedPackagesContent(exportedContent, "", project.getFolder(new Path("src"))); //$NON-NLS-1$ //$NON-NLS-2$
		exportedContent.append("\n"); //$NON-NLS-1$
		try {
			ByteArrayInputStream appendStream = new ByteArrayInputStream(exportedContent.toString().getBytes(
					"UTF8")); //$NON-NLS-1$
			project.getFile(new Path("META-INF/MANIFEST.MF")).appendContents(appendStream, true, false, //$NON-NLS-1$
					monitor);
		} catch (UnsupportedEncodingException e) {
			IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, IStatus.OK, e
					.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		}
	}

	/**
	 * Computes the text to add at the end of the MANIFEST.MF file.
	 * 
	 * @param exportedContent
	 *            is the current text to add at the end of the MANIFEST.MF file
	 * @param currentPackageName
	 *            is the package name of the current folder
	 * @param folder
	 *            is the current folder
	 * @throws CoreException
	 *             if this method fails
	 */
	private void computeExportedPackagesContent(StringBuffer exportedContent, String currentPackageName,
			IContainer folder) throws CoreException {
		IResource[] members = folder.members();
		for (int i = 0; i < members.length; i++) {
			if (members[i] instanceof IContainer) {
				String packageName = currentPackageName;
				if (packageName.length() == 0) {
					packageName = members[i].getName();
				} else {
					packageName += "." + members[i].getName(); //$NON-NLS-1$
				}
				if (hasTemplate((IContainer)members[i])) {
					if (exportedContent.length() == 0) {
						exportedContent.append("Export-Package:"); //$NON-NLS-1$
					} else {
						exportedContent.append(",\n"); //$NON-NLS-1$
					}
					exportedContent.append(" "); //$NON-NLS-1$
					exportedContent.append(packageName);
				}
				computeExportedPackagesContent(exportedContent, packageName, (IContainer)members[i]);
			}
		}
	}

	/**
	 * Indicates if the given folder contains one (or more) Acceleo file.
	 * 
	 * @param folder
	 *            is the folder to test
	 * @return true if the given folder contains one (or more) Acceleo file
	 * @throws CoreException
	 *             if this method fails
	 */
	private boolean hasTemplate(IContainer folder) throws CoreException {
		IResource[] members = folder.members();
		boolean hasTemplate = false;
		for (int i = 0; !hasTemplate && i < members.length; i++) {
			if (members[i] instanceof IFile
					&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)members[i]).getFileExtension())) {
				hasTemplate = true;
			}
		}
		return hasTemplate;
	}

}
