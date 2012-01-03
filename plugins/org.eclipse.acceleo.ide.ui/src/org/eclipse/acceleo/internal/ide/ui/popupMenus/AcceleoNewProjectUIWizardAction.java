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
package org.eclipse.acceleo.internal.ide.ui.popupMenus;

import org.eclipse.acceleo.internal.ide.ui.wizards.newproject.AcceleoNewProjectUIWizard;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * An action to create a new UI launcher (i.e an eclipse plug-in) for one Acceleo module.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoNewProjectUIWizardAction implements IWorkbenchWindowActionDelegate {

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
			AcceleoNewProjectUIWizard wizard = new AcceleoNewProjectUIWizard();
			wizard.init(PlatformUI.getWorkbench(), (IStructuredSelection)currentSelection);
			Shell parent = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			WizardDialog dialog = new WizardDialog(parent, wizard);
			dialog.create();
			Point defaultSize = dialog.getShell().getSize();
			dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, defaultSize.x),
					Math.max(SIZING_WIZARD_HEIGHT, defaultSize.y));
			dialog.open();
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
}
