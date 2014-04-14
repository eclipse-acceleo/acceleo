/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui.internal.wizards.newproject;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.acceleo.unit.ui.internal.util.AUnitProjectHelper;
import org.eclipse.acceleo.unit.ui.internal.wizards.AUnitMessages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * The AUnitProjectWizard.java AUnitProjectWizard.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class AUnitProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	/**
	 * Default prefix of the new module.
	 */
	public static final String MODULE_NAME_PREFIX = "org.eclipse.acceleo.unit."; //$NON-NLS-1$

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
	protected AUnitProjectPage newProjectPage;

	/**
	 * The constructor.
	 */
	public AUnitProjectWizard() {
		super();
		setWindowTitle(AUnitMessages.getString("AUnitProjectPage.Title")); //$NON-NLS-1$
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
		newProjectPage = new AUnitProjectPage(selection);
		this.addPage(newProjectPage);
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
			// BasicNewProjectResourceWizard.updatePerspective(this.configurationElement);
			return true;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		// TODO Auto-generated method stub

	}

	/**
	 * Create the new project.
	 * 
	 * @param monitor
	 *            the monitor.
	 */
	private void createProject(IProgressMonitor monitor) {

		IProject targetProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
				newProjectPage.getAcceleoTargetPath().getText());

		AUnitProjectHelper createProjectHelper = new AUnitProjectHelper(targetProject, newProjectPage
				.getProjectName(), newProjectPage.getLocationPath(), monitor);

		createProjectHelper.create();
	}

}
