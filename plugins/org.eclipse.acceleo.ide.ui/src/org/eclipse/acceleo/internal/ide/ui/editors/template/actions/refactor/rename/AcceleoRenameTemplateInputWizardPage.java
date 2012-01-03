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

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class will create the first page of the refactoring wizard in which the user will enter the new name
 * for the selected template.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameTemplateInputWizardPage extends UserInputWizardPage {

	/**
	 * The text field for the new name of the template.
	 */
	private Text fNameField;

	/**
	 * A combo box with the name of all the templates available and their positions.
	 */
	private ComboViewer fComboViewer;

	/**
	 * The constructor.
	 * 
	 * @param name
	 *            The name of the wizard page.
	 */
	public AcceleoRenameTemplateInputWizardPage(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		final Composite result = new Composite(parent, SWT.NONE);
		this.setControl(result);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		result.setLayout(layout);

		Label label = new Label(result, SWT.NONE);
		label.setText(AcceleoUIMessages.getString("AcceleoEditorRenameTemplateRefactoring.NewName")); //$NON-NLS-1$

		this.fNameField = createNameField(result);

		label = new Label(result, SWT.NONE);
		label.setText(AcceleoUIMessages
				.getString("AcceleoEditorRenameTemplateRefactoring.TemplatesAvailable")); //$NON-NLS-1$

		final Composite composite = new Composite(result, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.fComboViewer = this.createComboViewer(composite);
		this.fComboViewer.getCombo().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button referenceButton = new Button(result, SWT.CHECK);
		referenceButton.setText(AcceleoUIMessages
				.getString("AcceleoEditorRenameRefactoring.UpdateReferences")); //$NON-NLS-1$
		final GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		data.verticalIndent = 2;
		referenceButton.setLayoutData(data);

		final AcceleoRenameTemplateRefactoring refactoring = this.getRenameTemplateRefactoring();
		fNameField.setText(refactoring.getTemplate().getTemplateName());
		fComboViewer.getCombo().setText(refactoring.getTemplate().getTemplateName());

		if (refactoring.getTemplate() != null) {
			fComboViewer.setSelection(new StructuredSelection(refactoring.getTemplate()));
		} else if (AcceleoPositionedTemplate.getInput().length > 0) {
			fComboViewer.setSelection(new StructuredSelection(AcceleoPositionedTemplate.getInput()[0]));
		}

		fNameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				handleInputChanged();
			}
		});

		referenceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				refactoring.setUpdateReferences(referenceButton.getSelection());
			}
		});

		fComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleInputChanged();
			}
		});

		referenceButton.setSelection(true);

		fNameField.setFocus();
		fNameField.selectAll();
		handleInputChanged();
	}

	/**
	 * Creates the name field.
	 * 
	 * @param result
	 *            The composite in which we will create the name field.
	 * @return The name field.
	 */
	private Text createNameField(final Composite result) {
		final Text field = new Text(result, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return field;
	}

	/**
	 * Creates the combo box.
	 * 
	 * @param composite
	 *            The composite in which we will create the combo box.
	 * @return The combo box.
	 */
	private ComboViewer createComboViewer(final Composite composite) {
		final ComboViewer combo = new ComboViewer(composite, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);

		combo.setLabelProvider(new AcceleoPositionedTemplateLabelProvider());
		combo.setContentProvider(new ArrayContentProvider());

		// the input of AcceleoPositionedTemplate has been compute in the RenameTemplateAction class
		combo.setInput(AcceleoPositionedTemplate.getInput());

		return combo;
	}

	private AcceleoRenameTemplateRefactoring getRenameTemplateRefactoring() {
		return (AcceleoRenameTemplateRefactoring)getRefactoring();
	}

	/**
	 * Handle changes in the text box.
	 */
	protected void handleInputChanged() {
		final RefactoringStatus status = new RefactoringStatus();
		final AcceleoRenameTemplateRefactoring refactoring = this.getRenameTemplateRefactoring();

		status.merge(refactoring.setNewTemplateName(this.fNameField.getText()));

		final IStructuredSelection selection = (IStructuredSelection)this.fComboViewer.getSelection();
		if (!selection.isEmpty()) {
			final Object obj = selection.getFirstElement();
			if (obj instanceof AcceleoPositionedTemplate) {
				refactoring.setTemplate((AcceleoPositionedTemplate)obj);
			}
		}

		this.setPageComplete(!status.hasError());
		final int severity = status.getSeverity();
		final String message = status.getMessageMatchingSeverity(severity);
		if (severity >= RefactoringStatus.INFO) {
			setMessage(message, severity);
		} else {
			setMessage("", NONE); //$NON-NLS-1$
		}
	}
}
