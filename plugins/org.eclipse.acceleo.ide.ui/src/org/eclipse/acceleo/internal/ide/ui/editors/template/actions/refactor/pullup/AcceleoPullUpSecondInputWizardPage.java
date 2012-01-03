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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class will create the second page of the refactoring wizard in which the user will select the
 * destination module.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpSecondInputWizardPage extends UserInputWizardPage {

	/**
	 * The text field to type the name of the destination module.
	 */
	protected Text textField;

	/**
	 * The treeviewer to show the destination folder.
	 */
	private TreeViewer viewer;

	/**
	 * The label of the text.
	 */
	private Label label;

	/**
	 * The constructor.
	 * 
	 * @param name
	 *            The name of the wizard page.
	 */
	public AcceleoPullUpSecondInputWizardPage(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		this.setControl(composite);
		composite.setLayout(new FormLayout());
		this.viewer = new TreeViewer(composite, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		this.viewer.setLabelProvider(new AcceleoPullUpTreeLabelProvider());
		this.viewer.setContentProvider(new AcceleoPullUpTreeContentProvider());
		this.viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Refactoring refactoring = getRefactoring();
				if (refactoring instanceof AcceleoPullUpRefactoring) {
					AcceleoPullUpRefactoring acceleoPullUpRefactoring = (AcceleoPullUpRefactoring)refactoring;
					ISelection selection = event.getSelection();
					if (selection instanceof IStructuredSelection) {
						IStructuredSelection structuredSelection = (IStructuredSelection)selection;
						Object element = structuredSelection.getFirstElement();
						if (element instanceof IFolder) {
							acceleoPullUpRefactoring.setContainer((IFolder)element);
						} else if (element instanceof IFile) {
							acceleoPullUpRefactoring.setContainer(((IFile)element).getParent());
							acceleoPullUpRefactoring.setFileName(((IFile)element).getName());
							textField.setText(((IFile)element).getName());
							setPageComplete(true);
						}
					}
				}
			}
		});

		this.label = new Label(composite, SWT.LEFT);
		this.label.setText(AcceleoUIMessages
				.getString("AcceleoEditorPullUpRefactoring.PullUpSecondInputWizardPageLabelName")); //$NON-NLS-1$

		final int gap = -5;

		FormData formDataLabel = new FormData();
		formDataLabel.bottom = new FormAttachment(100, gap);
		formDataLabel.left = new FormAttachment(0, 5);
		this.label.setLayoutData(formDataLabel);

		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		formData.right = new FormAttachment(100, gap);
		formData.bottom = new FormAttachment(this.label, 3 * gap);
		this.viewer.getControl().setLayoutData(formData);

		this.textField = new Text(composite, SWT.SINGLE);
		this.textField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				Refactoring refactoring = getRefactoring();
				if (refactoring instanceof AcceleoPullUpRefactoring) {
					AcceleoPullUpRefactoring acceleoPullUpRefactoring = (AcceleoPullUpRefactoring)refactoring;
					acceleoPullUpRefactoring.setFileName(textField.getText());
					setPageComplete(isPageComplete());
				}
			}
		});

		FormData formDataText = new FormData();
		formDataText.left = new FormAttachment(this.label, 0);
		formDataText.right = new FormAttachment(100, gap);
		formDataText.bottom = new FormAttachment(100, gap);
		this.textField.setLayoutData(formDataText);

		this.viewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizardPage#getRefactoring()
	 */
	@Override
	protected Refactoring getRefactoring() {
		// enhances visibility
		return super.getRefactoring();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		Refactoring refactoring = getRefactoring();
		if (refactoring instanceof AcceleoPullUpRefactoring) {
			AcceleoPullUpRefactoring acceleoPullUpRefactoring = (AcceleoPullUpRefactoring)refactoring;
			return acceleoPullUpRefactoring.getContainer() != null
					&& acceleoPullUpRefactoring.getFileName() != null;
		}
		return false;
	}

}
