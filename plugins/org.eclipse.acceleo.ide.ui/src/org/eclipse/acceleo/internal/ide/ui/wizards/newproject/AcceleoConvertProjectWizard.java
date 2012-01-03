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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.natures.AcceleoToggleNatureAction;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;

/**
 * Convert a project to an Acceleo generation module.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoConvertProjectWizard extends Wizard implements INewWizard {

	/**
	 * The workspace selection when the wizard has been opened.
	 */
	private ISelection selection;

	/**
	 * This is a wizard page to select the projects to be considered.
	 */
	private WizardNewProjectReferencePage projectReferencesPage;

	/**
	 * Constructor.
	 */
	public AcceleoConvertProjectWizard() {
		super();
		setWindowTitle(AcceleoUIMessages.getString("AcceleoConvertProjectWizard.Title")); //$NON-NLS-1$
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
		ImageDescriptor wizardImage = AcceleoUIActivator.getImageDescriptor("icons/AcceleoWizard.gif"); //$NON-NLS-1$
		projectReferencesPage = new WizardNewProjectReferencePage(AcceleoUIMessages
				.getString("AcceleoConvertProjectWizard.Reference.Name")); //$NON-NLS-1$ 
		projectReferencesPage.setDescription(AcceleoUIMessages
				.getString("AcceleoConvertProjectWizard.Reference.Description")); //$NON-NLS-1$
		projectReferencesPage.setImageDescriptor(wizardImage);
		addPage(projectReferencesPage);
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
				List<IProject> newProjects = new ArrayList<IProject>();
				IProject[] projects = projectReferencesPage.getReferencedProjects();
				for (int i = 0; i < projects.length; i++) {
					IProject iProject = projects[i];
					if (iProject.getNature(IAcceleoConstants.ACCELEO_NATURE_ID) == null) {
						newProjects.add(iProject);
					}
				}
				AcceleoToggleNatureAction action = new AcceleoToggleNatureAction();
				action.setSelection(new StructuredSelection(newProjects));
				try {
					ExecutionEvent event = new ExecutionEvent();
					action.execute(event);
				} catch (ExecutionException e) {
					AcceleoUIActivator.log(e, true);
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

}
