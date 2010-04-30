/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.internal.SaveablesList;

/**
 * This class will launch the refactoring action.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameAction implements IWorkbenchWindowActionDelegate {

	/**
	 * Action's name.
	 */
	private final String name = "Rename..."; //$NON-NLS-1$

	/**
	 * The current workbench window.
	 */
	private IWorkbenchWindow fWindow;

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
		final AcceleoEditor editor = (AcceleoEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		if (fWindow != null && allRessourceSaved(editor) && !editor.isDirty()) {
			final EObject obj = OpenDeclarationUtils.findDeclaration(editor);

			if (obj instanceof Template) {
				final AcceleoRenameTemplateRefactoring refactoring = new AcceleoRenameTemplateRefactoring();
				refactoring.setFileName(editor.getFile().getName());
				AcceleoPositionedTemplate.computePartialInput();
				AcceleoPositionedTemplate.setAcceleoEditor(editor);

				final AcceleoPositionedTemplate[] array = AcceleoPositionedTemplate.getInput();
				for (int i = 0; i < array.length; i++) {
					AcceleoPositionedTemplate positionedTemplate = array[i];
					if (((Template)obj).getName().equals(positionedTemplate.getTemplateName())) {
						refactoring.setTemplate(positionedTemplate);
					}
				}
				runWizard(new AcceleoRenameTemplateWizard(refactoring, name), fWindow.getShell(), name);
			} else if (obj instanceof Query) {
				final AcceleoRenameQueryRefactoring refactoring = new AcceleoRenameQueryRefactoring();
				refactoring.setFileName(editor.getFile().getName());
				AcceleoPositionedQuery.computePartialInput();
				AcceleoPositionedQuery.setAcceleoEditor(editor);

				final AcceleoPositionedQuery[] array = AcceleoPositionedQuery.getInput();
				if (array.length > 0) {
					refactoring.setQuery(array[0]);
					runWizard(new AcceleoRenameQueryWizard(refactoring, name), fWindow.getShell(), name);
				}
			} else if (obj instanceof Variable) {
				final AcceleoRenameVariableRefactoring refactoring = new AcceleoRenameVariableRefactoring();
				refactoring.setFileName(editor.getFile().getName());
				AcceleoPositionedVariable apv = new AcceleoPositionedVariable((Variable)obj, editor);

				refactoring.setVariable(apv);
				runWizard(new AcceleoRenameVariableWizard(refactoring, name), fWindow.getShell(), name);
			} else if (obj instanceof VariableExp) {
				final AcceleoRenameVariableRefactoring refactoring = new AcceleoRenameVariableRefactoring();
				AcceleoPositionedVariable apv = new AcceleoPositionedVariable((VariableExp)obj, editor);

				refactoring.setVariable(apv);
				runWizard(new AcceleoRenameVariableWizard(refactoring, name), fWindow.getShell(), name);
			} else {
				// by default, we will show the rename template refactoring.
				final AcceleoRenameTemplateRefactoring refactoring = new AcceleoRenameTemplateRefactoring();
				refactoring.setFileName(editor.getFile().getName());
				AcceleoPositionedTemplate.computePartialInput();
				AcceleoPositionedTemplate.setAcceleoEditor(editor);

				final AcceleoPositionedTemplate[] array = AcceleoPositionedTemplate.getInput();
				if (array.length > 0) {
					refactoring.setTemplate(array[0]);
					runWizard(new AcceleoRenameTemplateWizard(refactoring, name), fWindow.getShell(), name);
				}
			}
		}
	}

	/**
	 * Force the editor to save to perform the refactoring.
	 * 
	 * @param editor
	 *            The editor.
	 * @return If the editor has saved.
	 */
	private boolean allRessourceSaved(final AcceleoEditor editor) {
		if (editor.isDirty()) {
			SaveablesList modelManager = (SaveablesList)editor.getSite().getWorkbenchWindow().getService(
					ISaveablesLifecycleListener.class);
			Saveable[] saveableArray = editor.getSaveables();
			List<Saveable> list = new ArrayList<Saveable>();
			for (int i = 0; i < saveableArray.length; i++) {
				list.add(saveableArray[i]);
			}
			boolean canceled = modelManager.promptForSaving(list, fWindow, fWindow, true, false);
			return !canceled;
		} else {
			return true;
		}
	}

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
			// Do nothing
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
