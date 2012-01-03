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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringAction;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AcceleoRefactoringUtils;
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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ocl.ecore.LiteralExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

/**
 * This class will launch the refactoring action.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameAction extends AbstractRefactoringAction {

	/**
	 * The constructor.
	 */
	public AcceleoRenameAction() {
		super(false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringAction#launchRefactoring()
	 */
	@Override
	protected void launchRefactoring() {
		this.name = AcceleoUIMessages.getString("AcceleoEditorRenameRefactoring.RefactoringTitle"); //$NON-NLS-1$

		if (this.fWindow != null && AcceleoRefactoringUtils.allResourceSaved() && !this.editor.isDirty()) {
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
				ISelection iSelection = this.editor.getSelectionProvider().getSelection();
				if (iSelection instanceof ITextSelection && ((ITextSelection)iSelection).getLength() > 0) {
					// If we have a text selection, that is NOT a template, a query, a variable, a variable
					// exp or a module. Then we will try to rename all the occurrences of the text in the
					// given module element.
					this.launchRefactorRenameTextOccurrences((ITextSelection)iSelection);
				} else {
					// We launch the rename template refactoring by default.
					this.launchRefactorRenameTemplate();
				}
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
		IFile file = this.editor.getFile();
		if (file == null || !file.exists()) {
			return;
		}

		IFile javaFile = AcceleoRefactoringUtils.getJavaFileFromModuleFile(
				this.editor.getFile().getProject(), file);
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
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}

		final AcceleoRenameModuleRefactoring refactoring = new AcceleoRenameModuleRefactoring();
		refactoring.setModule(module);
		refactoring.setFile(file);
		refactoring.setProject(file.getProject());

		runWizard(new AcceleoRenameModuleWizard(refactoring, name), fWindow.getShell(), name);
	}

	/**
	 * Launch the refactoring of the text selection.
	 * 
	 * @param selection
	 *            The text selection
	 */
	private void launchRefactorRenameTextOccurrences(ITextSelection selection) {
		ASTNode astNode = this.editor.getContent().getASTNode(selection.getOffset() + selection.getLength(),
				selection.getOffset() + selection.getLength());
		if (selection.getLength() > 0 && astNode instanceof LiteralExp) {
			Template template = (Template)this.editor.getContent().getASTParent(astNode,
					org.eclipse.acceleo.model.mtl.Template.class);
			IDocument document = this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput());
			String text = document.get();
			if (template != null && template.getEndPosition() > -1
					&& template.getEndPosition() <= text.length()) {
				AcceleoRenameTextRefactoring refactoring = new AcceleoRenameTextRefactoring();
				refactoring.setParent(template);
				refactoring.setSourceContent(this.editor.getContent());
				refactoring.setSelection(selection);
				runWizard(new AcceleoRenameTextWizard(refactoring, name), fWindow.getShell(), name);
			}
		}
	}
}
