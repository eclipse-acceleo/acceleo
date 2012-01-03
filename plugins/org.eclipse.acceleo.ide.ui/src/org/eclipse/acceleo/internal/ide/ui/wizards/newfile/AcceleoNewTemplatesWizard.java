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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IPageChangingListener;
import org.eclipse.jface.dialogs.PageChangingEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This is a 'new' wizard for Acceleo templates. Its role is to create a new Acceleo file resource in the
 * provided container. If the container resource (a folder or a project) is selected in the workspace when the
 * wizard is opened, it will accept it as the target container. The wizard creates one file with the extension
 * "mtl".
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewTemplatesWizard extends Wizard implements INewWizard {

	/**
	 * The workspace selection when the wizard has been opened.
	 */
	private ISelection selection;

	/**
	 * A listener which is notified when the current page of a wizard is changing.
	 */
	private IPageChangingListener pageChangingListener;

	/**
	 * Templates page.
	 */
	private AcceleoNewTemplatesWizardPage templatePage;

	/**
	 * Constructor.
	 */
	public AcceleoNewTemplatesWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoNewTemplatesWizard.Title")); //$NON-NLS-1$
	}

	/**
	 * Get templates pages. There is one page for each template to create.
	 * 
	 * @return templates pages
	 */
	public AcceleoNewTemplatesWizardPage getTemplatePage() {
		return templatePage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#createPageControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPageControls(Composite pageContainer) {
		super.createPageControls(pageContainer);
		if (pageChangingListener == null && getContainer() instanceof WizardDialog) {
			WizardDialog dialog = (WizardDialog)getContainer();
			pageChangingListener = new IPageChangingListener() {
				public void handlePageChanging(PageChangingEvent event) {
					if (event.getCurrentPage() instanceof WizardPage
							&& event.getTargetPage() instanceof AcceleoNewTemplatesWizardPage) {
						WizardPage current = (WizardPage)event.getCurrentPage();
						AcceleoNewTemplatesWizardPage target = (AcceleoNewTemplatesWizardPage)event
								.getTargetPage();
						target.handleNewTemplatePageChanging(current);
					}
				}
			};
			dialog.addPageChangingListener(pageChangingListener);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#dispose()
	 */
	@Override
	public void dispose() {
		if (pageChangingListener != null && getContainer() instanceof WizardDialog) {
			WizardDialog dialog = (WizardDialog)getContainer();
			dialog.removePageChangingListener(pageChangingListener);
		}
		super.dispose();
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
		IContainer firstContainer = null;
		if (selection != null && !selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection aSelection = (IStructuredSelection)selection;
			if (aSelection.size() > 0) {
				Object element = aSelection.getFirstElement();
				if (element instanceof IAdaptable) {
					element = ((IAdaptable)element).getAdapter(IResource.class);
				}
				if (element instanceof IContainer) {
					firstContainer = (IContainer)element;
				} else if (element instanceof IResource) {
					firstContainer = ((IResource)element).getParent();
				}
			}
		}
		String container = ""; //$NON-NLS-1$
		if (firstContainer != null) {
			container = firstContainer.getFullPath().toString();
		}
		templatePage = new AcceleoNewTemplatesWizardPage(container, multipleTemplates());
		addPage(templatePage);
	}

	/**
	 * Indicates if multiple templates can be defined. If true, the template list is shown on the left of the
	 * page.
	 * 
	 * @return true if multiple templates can be defined
	 */
	protected boolean multipleTemplates() {
		return false;
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
				for (AcceleoNewTemplatesWizardController controller : templatePage.getControllers()) {
					createTemplate(controller.getModel(), monitor);
				}
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
	 * Creates a template using the given configuration.
	 * 
	 * @param data
	 *            is the new template configuration
	 * @param monitor
	 *            is the monitor
	 */
	protected void createTemplate(CreateTemplateData data, IProgressMonitor monitor) {
		data.createExampleStrategy();
		if (data.getTemplateContainer().length() > 0 && data.getTemplateShortName().length() > 0) {
			IPath templatePath = new Path(data.getTemplateContainer()).append(data.getTemplateShortName())
					.addFileExtension(IAcceleoConstants.MTL_FILE_EXTENSION);
			try {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
						templatePath.segment(0));
				if (project.isAccessible()) {
					monitor.beginTask(AcceleoUIMessages.getString(
							"AcceleoNewTemplatesWizard.Task.CreateTemplate", templatePath.lastSegment()), 2); //$NON-NLS-1$
					IPath projectRelativePath = templatePath.removeFirstSegments(1);
					ByteArrayInputStream javaStream = new ByteArrayInputStream(data.getTemplateContent()
							.getBytes("UTF8")); //$NON-NLS-1$
					IContainer container = project;
					String[] folders = projectRelativePath.removeLastSegments(1).segments();
					for (int i = 0; i < folders.length; i++) {
						container = container.getFolder(new Path(folders[i]));
						if (!container.exists()) {
							((IFolder)container).create(true, true, monitor);
						}
					}
					final IFile file = container.getFile(new Path(projectRelativePath.lastSegment()));
					if (!file.exists()) {
						file.create(javaStream, true, monitor);
					} else {
						file.setContents(javaStream, true, false, monitor);
					}
					monitor.worked(1);
					monitor.setTaskName(AcceleoUIMessages
							.getString("AcceleoNewTemplatesWizard.Task.OpenTemplate")); //$NON-NLS-1$
					getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							IWorkbenchPage aPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage();
							try {
								IDE.openEditor(aPage, file, true);
							} catch (PartInitException e) {
								// continue
								AcceleoUIActivator.log(e, true);
							}
						}
					});
					monitor.worked(1);
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
	}
}
