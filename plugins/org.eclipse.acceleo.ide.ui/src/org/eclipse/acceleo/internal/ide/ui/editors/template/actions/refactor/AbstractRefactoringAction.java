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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * This is the base for all refactoring action.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public abstract class AbstractRefactoringAction implements IWorkbenchWindowActionDelegate {
	/**
	 * Action's name.
	 */
	protected String name;

	/**
	 * The current workbench window.
	 */
	protected IWorkbenchWindow fWindow;

	/**
	 * the acceleo editor.
	 */
	protected AcceleoEditor editor;

	/**
	 * Indicates if we tolerate errors in the file or not.
	 */
	protected boolean tolerateError;

	/**
	 * The constructor.
	 */
	public AbstractRefactoringAction() {
		this.tolerateError = false;
	}

	/**
	 * the constructor.
	 * 
	 * @param withError
	 *            Indicates if our refactoring can occurs with errors in the current file or not
	 */
	public AbstractRefactoringAction(final boolean withError) {
		this.tolerateError = withError;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.fWindow = window;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(final IAction action) {
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() == null
				|| PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() == null
				|| !(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor() instanceof AcceleoEditor)) {
			return;
		}

		this.editor = (AcceleoEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();

		MessageBox box = null;
		if (this.editor.getFile() == null) {
			box = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			box.setMessage(AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.NotInWorkspace")); //$NON-NLS-1$

		} else if (!this.tolerateError && AcceleoRefactoringUtils.containsAcceleoError(this.editor.getFile())) {
			box = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			box.setMessage(AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.ErrorInFile", //$NON-NLS-1$
					this.editor.getFile().getName()));
		}

		if (box != null) {
			box.open();
			return;
		}

		this.launchRefactoring();
	}

	/**
	 * This method is in charge of the refactoring.
	 */
	protected abstract void launchRefactoring();

	/**
	 * Launch the wizard.
	 * 
	 * @param wizard
	 *            The wizard to launch.
	 * @param parent
	 *            The parent shell.
	 * @param dialogTitle
	 *            The dialog title.
	 */
	public void runWizard(final RefactoringWizard wizard, final Shell parent, final String dialogTitle) {
		try {
			final RefactoringWizardOpenOperation operation = new RefactoringWizardOpenOperation(wizard);
			operation.run(parent, dialogTitle);
		} catch (InterruptedException exception) {
			AcceleoUIActivator.log(exception, true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(final IAction action, final ISelection selection) {
		// Nothing to do here, yeah \o/
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		// Nothing to do here, yeah \o/
	}

}
