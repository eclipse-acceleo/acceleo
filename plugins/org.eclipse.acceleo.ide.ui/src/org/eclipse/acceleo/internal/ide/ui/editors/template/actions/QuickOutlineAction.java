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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * This action will display a quick outline view within a styled text popup menu.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QuickOutlineAction extends Action implements IWorkbenchWindowActionDelegate {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.show.outline"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.show.outline"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		// No disposal needed here
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		// no initialization required
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		super.run();
		IInformationPresenter presenter = getCurrentEditor().getQuickOutlinePresenter();
		presenter.showInformation();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// We don't need to react to selection changes
	}

	/**
	 * Returns the currently active editor if it's an AcceleoEditor.
	 * 
	 * @return The currently active editor if it's an AcceleoEditor, <code>null</code> otherwise.
	 */
	protected AcceleoEditor getCurrentEditor() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null
				&& window.getActivePage().getActiveEditor() instanceof AcceleoEditor) {
			return (AcceleoEditor)window.getActivePage().getActiveEditor();
		}
		return null;
	}
}
