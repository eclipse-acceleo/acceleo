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

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarker;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.swt.widgets.MessageBox;
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
	private final String name = AcceleoUIMessages
			.getString("AcceleoEditorRenameRefactoring.RefactoringTitle"); //$NON-NLS-1$

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

		MessageBox box = null;
		if (this.editor.getFile() == null) {
			box = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			box.setMessage(AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.NotInWorkspace")); //$NON-NLS-1$

		} else if (this.containsAcceleoError(this.editor.getFile())) {
			box = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			box.setMessage(AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.ErrorInFile", //$NON-NLS-1$
					this.editor.getFile().getName()));
		}

		if (box != null) {
			box.open();
			return;
		}

		if (fWindow != null && allResourceSaved() && !this.editor.isDirty()) {
			final EObject object = OpenDeclarationUtils.findResolvedDeclaration(this.editor);

			if (object instanceof Template) {
				this.launchRefactoringRenameTemplate((Template)object);
			} else if (object instanceof Query) {
				this.launchRefactoringRenameQuery((Query)object);
			} else if (object instanceof Variable) {
				this.launchRefactoringRenameVariable((Variable)object);
			} else if (object instanceof VariableExp) {
				this.launchRefactoringRenameVariable((VariableExp)object);
			} else if (object instanceof Module) {
				this.launchRefactoringRenameModule((Module)object);
			} else {
				// We launch the rename template refactoring by default.
				this.launchRefactorRenameTemplate();

				// If we want to launch the rename module refactoring by default :
				// Module mod = AcceleoRenameModuleUtils.getModuleFromFile(this.editor.getFile());
				// this.launchRefactoringRenameModule(mod);
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
				for (AcceleoPositionedTemplate acceleoPositionedTemplate : array) {
					if (template.getName().equals(acceleoPositionedTemplate.getTemplateName())
							&& checkEquals(template.getParameter(), acceleoPositionedTemplate.getTemplate()
									.getParameter())) {
						refactoring.setTemplate(acceleoPositionedTemplate);
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
			for (AcceleoPositionedQuery acceleoPositionedQuery : array) {
				if (query.getName().equals(acceleoPositionedQuery.getQueryName())
						&& checkEquals(query.getParameter(), acceleoPositionedQuery.getQuery().getParameter())) {
					refactoring.setQuery(acceleoPositionedQuery);
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
	 * Check if the two list of parameters are equal.
	 * 
	 * @param paramList1
	 *            The first list of parameter.
	 * @param paramList2
	 *            The second list of parameter.
	 * @return If the two list are equal.
	 */
	private boolean checkEquals(EList<Variable> paramList1, EList<Variable> paramList2) {
		boolean result = true;

		if (paramList1.size() == paramList2.size()) {
			for (int i = 0; i < paramList1.size(); i++) {
				final Variable var1 = paramList1.get(i);
				final Variable var2 = paramList2.get(i);
				if (var1.getName().equals(var2.getName())) {
					if ((var1.getType() != null && var2.getType() != null)
							&& !var1.getType().getName().equals(var2.getType().getName())) {
						result = false;
						break;
					}
				} else {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Launch the refactoring of a module.
	 * 
	 * @param module
	 *            The module to refactor.
	 */
	private void launchRefactoringRenameModule(final Module module) {
		IFile file = AcceleoRenameModuleUtils.getFileFromModule(this.editor.getFile().getProject(), module);
		if (file == null || !file.exists()) {
			return;
		}

		IFile javaFile = AcceleoRenameModuleUtils.getJavaFileFromModuleFile(this.editor.getFile()
				.getProject(), file);
		if (javaFile != null && javaFile.exists()) {
			try {
				IMarker[] markers = javaFile.findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true,
						IResource.DEPTH_INFINITE);
				if (markers.length > 0) {
					MessageBox box = new MessageBox(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell());
					box.setMessage(AcceleoUIMessages.getString(
							"AcceleoEditorRenameRefactoring.ErrorInFile", javaFile.getName())); //$NON-NLS-1$
					box.open();
					return;
				}
			} catch (CoreException e) {
				// do nothing
			}
		}

		final AcceleoRenameModuleRefactoring refactoring = new AcceleoRenameModuleRefactoring();
		refactoring.setModule(module);
		refactoring.setFile(file);
		refactoring.setProject(file.getProject());

		runWizard(new AcceleoRenameModuleWizard(refactoring, name), fWindow.getShell(), name);
	}

	/**
	 * Force the editor to save to perform the refactoring.
	 * 
	 * @return If the editor has saved.
	 */
	private boolean allResourceSaved() {
		final List<AcceleoEditor> dirtyEditorList = new ArrayList<AcceleoEditor>();
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			IEditorPart[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getDirtyEditors();
			for (IEditorPart iEditorPart : editors) {
				if (iEditorPart instanceof AcceleoEditor) {
					dirtyEditorList.add((AcceleoEditor)iEditorPart);
				}
			}
		}

		if (dirtyEditorList.size() > 0) {
			for (AcceleoEditor acceleoEditor : dirtyEditorList) {
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
			for (AcceleoEditor acceleoEditor : dirtyEditorList) {
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
	 * Check if there are any acceleo errors in the file.
	 * 
	 * @param file
	 *            The current file.
	 * @return true if there is an error.
	 */
	private boolean containsAcceleoError(final IFile file) {
		boolean result = false;
		try {
			IMarker[] markers = file
					.findMarkers(AcceleoMarker.PROBLEM_MARKER, true, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				result = true;
			}
		} catch (CoreException e) {
			result = true;
		}
		return result;
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
