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
import java.util.Iterator;
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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.SaveablesLifecycleEvent;

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
	 * the acceleo editor.
	 */
	private AcceleoEditor editor;

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

		if (fWindow != null && allRessourceSaved() && !this.editor.isDirty()) {
			final EObject object = OpenDeclarationUtils.findDeclaration(this.editor);

			if (object instanceof Template) {
				this.launchRefactoringRenameTemplate((Template)object);
			} else if (object instanceof Query) {
				this.launchRefactoringRenameQuery((Query)object);
			} else if (object instanceof Variable) {
				this.launchRefactoringRenameVariable((Variable)object);
			} else if (object instanceof VariableExp) {
				this.launchRefactoringRenameVariable((VariableExp)object);
			} else {
				// if there is no element selected we will show the rename template refactoring.
				this.launchRefactorRenameTemplate();
			}
		}
	}

	/**
	 * Launch the refactoring of a template.
	 */
	private void launchRefactorRenameTemplate() {
		this.launchRefactoringRenameTemplate(null);
	}

	/**
	 * Launch the refactoring of a template.
	 * 
	 * @param template
	 *            The template to refactor.
	 */
	private void launchRefactoringRenameTemplate(final Template template) {
		final AcceleoRenameTemplateRefactoring refactoring = new AcceleoRenameTemplateRefactoring();
		refactoring.setFileName(this.editor.getFile().getName());
		AcceleoPositionedTemplate.setAcceleoEditor(this.editor);
		AcceleoPositionedTemplate.computePartialInput(template);

		final AcceleoPositionedTemplate[] array = AcceleoPositionedTemplate.getInput();
		if (array.length > 0) {
			refactoring.setTemplate(array[0]);
			if (template != null) {
				for (int i = 0; i < array.length; i++) {
					AcceleoPositionedTemplate positionedTemplate = array[i];
					if (template.getName().equals(positionedTemplate.getTemplateName())) {
						refactoring.setTemplate(positionedTemplate);
					}
				}
			}
			runWizard(new AcceleoRenameTemplateWizard(refactoring, name), fWindow.getShell(), name);
		}
	}

	/**
	 * Launch the refactoring of a query.
	 * 
	 * @param query
	 *            the query to refactor.
	 */
	private void launchRefactoringRenameQuery(final Query query) {
		final AcceleoRenameQueryRefactoring refactoring = new AcceleoRenameQueryRefactoring();
		refactoring.setFileName(this.editor.getFile().getName());
		AcceleoPositionedQuery.setAcceleoEditor(this.editor);
		AcceleoPositionedQuery.computePartialInput(query);

		final AcceleoPositionedQuery[] array = AcceleoPositionedQuery.getInput();
		if (array.length > 0) {
			refactoring.setQuery(array[0]);
			for (int i = 0; i < array.length; i++) {
				AcceleoPositionedQuery positionedQuery = array[i];
				if (query.getName().equals(positionedQuery.getQueryName())) {
					refactoring.setQuery(positionedQuery);
				}
			}
			runWizard(new AcceleoRenameQueryWizard(refactoring, name), fWindow.getShell(), name);
		}
	}

	/**
	 * Launch the refactoring of a variable.
	 * 
	 * @param variable
	 *            The variable to refactor.
	 */
	private void launchRefactoringRenameVariable(final Variable variable) {
		final AcceleoRenameVariableRefactoring refactoring = new AcceleoRenameVariableRefactoring();
		refactoring.setFileName(this.editor.getFile().getName());
		AcceleoPositionedVariable apv = new AcceleoPositionedVariable(variable, this.editor);

		refactoring.setVariable(apv);
		runWizard(new AcceleoRenameVariableWizard(refactoring, name), fWindow.getShell(), name);
	}

	/**
	 * Launch the refactoring of a variable.
	 * 
	 * @param variable
	 *            The variable to refactor.
	 */
	private void launchRefactoringRenameVariable(final VariableExp variable) {
		final AcceleoRenameVariableRefactoring refactoring = new AcceleoRenameVariableRefactoring();
		AcceleoPositionedVariable apv = new AcceleoPositionedVariable(variable, editor);

		refactoring.setVariable(apv);
		runWizard(new AcceleoRenameVariableWizard(refactoring, name), fWindow.getShell(), name);
	}

	/**
	 * Force the editor to save to perform the refactoring.
	 * 
	 * @return If the editor has saved.
	 */
	private boolean allRessourceSaved() {
		final List<AcceleoEditor> dirtyEditorList = new ArrayList<AcceleoEditor>();
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			IEditorPart[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getDirtyEditors();
			for (int i = 0; i < editors.length; i++) {
				if (editors[i] instanceof AcceleoEditor) {
					dirtyEditorList.add((AcceleoEditor)editors[i]);
				}
			}
		}

		if (dirtyEditorList.size() > 0) {
			for (Iterator<AcceleoEditor> iterator = dirtyEditorList.iterator(); iterator.hasNext();) {
				AcceleoEditor acceleoEditor = (AcceleoEditor)iterator.next();
				ISaveablesLifecycleListener modelManager = (ISaveablesLifecycleListener)acceleoEditor
						.getSite().getWorkbenchWindow().getService(ISaveablesLifecycleListener.class);
				Saveable[] saveableArray = acceleoEditor.getSaveables();
				List<Saveable> list = new ArrayList<Saveable>();
				for (int i = 0; i < saveableArray.length; i++) {
					list.add(saveableArray[i]);
				}

				// Fires a "pre close" event so that the editors prompts us to save the dirty files.
				// None will really be closed.
				SaveablesLifecycleEvent event = new SaveablesLifecycleEvent(acceleoEditor,
						SaveablesLifecycleEvent.PRE_CLOSE, saveableArray, false);
				modelManager.handleLifecycleEvent(event);
			}

			boolean allSaved = true;
			for (Iterator<AcceleoEditor> iterator = dirtyEditorList.iterator(); iterator.hasNext();) {
				AcceleoEditor acceleoEditor = (AcceleoEditor)iterator.next();
				if (acceleoEditor.isDirty()) {
					allSaved = false;
					break;
				}
			}

			return allSaved;
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
