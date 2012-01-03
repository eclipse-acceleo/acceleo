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
package org.eclipse.acceleo.ide.ui.popupMenus;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.ide.ui.wizards.newfile.AcceleoNewTemplatesWizardController;
import org.eclipse.acceleo.internal.ide.ui.wizards.newfile.CreateTemplateData;
import org.eclipse.acceleo.internal.ide.ui.wizards.newproject.AcceleoNewProjectWizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * An abstract action to create automatically a new Acceleo module from an old code generation module (MT,
 * Xpand).
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 3.0
 */
public abstract class AbstractMigrateProjectWizardAction implements IWorkbenchWindowActionDelegate {

	/**
	 * The wizard dialog width.
	 */
	private static final int SIZING_WIZARD_WIDTH = 500;

	/**
	 * The wizard dialog height.
	 */
	private static final int SIZING_WIZARD_HEIGHT = 500;

	/**
	 * The current selection.
	 */
	private ISelection currentSelection;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (currentSelection instanceof IStructuredSelection
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
			AcceleoNewProjectWizard wizard = new AcceleoNewProjectWizard() {
				@Override
				protected boolean multipleTemplates() {
					return false;
				}

				@Override
				public void addPages() {
					super.addPages();
					if (((IStructuredSelection)currentSelection).getFirstElement() instanceof IProject) {
						newProjectPage
								.setInitialProjectName(((IProject)((IStructuredSelection)currentSelection)
										.getFirstElement()).getName()
										+ ".migrated"); //$NON-NLS-1$
					}
				}
			};
			wizard.init(PlatformUI.getWorkbench(), (IStructuredSelection)currentSelection);
			Shell parent = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			Point defaultSize = dialog.getShell().getSize();
			dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, defaultSize.x),
					Math.max(SIZING_WIZARD_HEIGHT, defaultSize.y));
			List<IProject> projects = new ArrayList<IProject>();
			Object[] objects = ((IStructuredSelection)currentSelection).toArray();
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] instanceof IProject) {
					projects.add((IProject)objects[i]);
				}
			}
			try {
				if (wizard.getTemplatePage().getControllers().size() > 0) {
					AcceleoNewTemplatesWizardController first = wizard.getTemplatePage().getControllers()
							.get(0);
					CreateTemplateData data = first.getModel();
					data.setTemplateShortName("main"); //$NON-NLS-1$
					data.setTemplateHasFileBlock(false);
					data.setTemplateIsInitialized(false);
					browseTemplates(projects.toArray(new IProject[projects.size()]));
					String metamodelURIs = computeMetamodelURIs();
					data.setTemplateMetamodel(metamodelURIs);
					first.initView(false);
					if (dialog.open() == Window.OK && wizard.getTemplatePage().getControllers().size() > 0) {
						first = wizard.getTemplatePage().getControllers().get(0);
						data = first.getModel();
						IPath baseFolder = new Path(data.getTemplateContainer());
						IPath mainTemplate = baseFolder.append(data.getTemplateShortName()).addFileExtension(
								IAcceleoConstants.MTL_FILE_EXTENSION);
						generateMTL(baseFolder, mainTemplate);
					}
				}
			} catch (CoreException e) {
				AcceleoCommonPlugin.log(e.getStatus());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		currentSelection = selection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
	}

	/**
	 * Browse the old template files of the selected projects.
	 * 
	 * @param projects
	 *            are the projects that contain the template to migrate
	 * @throws CoreException
	 *             when a workspace issue occurs
	 */
	protected abstract void browseTemplates(IProject[] projects) throws CoreException;

	/**
	 * Generate the output MTL files.
	 * 
	 * @param baseFolder
	 *            is the target folder
	 * @param mainTemplate
	 *            is the main template path in the workspace
	 */
	protected abstract void generateMTL(IPath baseFolder, IPath mainTemplate);

	/**
	 * Computes the metamodel URIs.
	 * 
	 * @return the URIs separated by the comma character
	 */
	protected abstract String computeMetamodelURIs();

}
