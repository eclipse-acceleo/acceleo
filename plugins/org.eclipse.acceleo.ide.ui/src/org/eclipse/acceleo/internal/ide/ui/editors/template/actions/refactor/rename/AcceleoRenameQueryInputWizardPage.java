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
 * for the selected query.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameQueryInputWizardPage extends UserInputWizardPage {

	/**
	 * The text field for the new name of the query.
	 */
	private Text fNameField;

	/**
	 * A combo box with the name of all the queries available and their positions.
	 */
	private ComboViewer fComboViewer;

	/**
	 * The constructor.
	 * 
	 * @param name
	 *            The name of the wizard page.
	 */
	public AcceleoRenameQueryInputWizardPage(String name) {
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
		label.setText(AcceleoUIMessages.getString("AcceleoEditorRenameQueryRefactoring.NewName")); //$NON-NLS-1$

		this.fNameField = createNameField(result);

		label = new Label(result, SWT.NONE);
		label.setText(AcceleoUIMessages.getString("AcceleoEditorRenameQueryRefactoring.QueriesAvailable")); //$NON-NLS-1$

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

		final AcceleoRenameQueryRefactoring refactoring = this.getRenameQueryRefactoring();
		fNameField.setText(refactoring.getQuery().getQueryName());
		fComboViewer.getCombo().setText(refactoring.getQuery().getQueryName());

		if (refactoring.getQuery() != null) {
			fComboViewer.setSelection(new StructuredSelection(refactoring.getQuery()));
		} else if (AcceleoPositionedQuery.getInput().length > 0) {
			fComboViewer.setSelection(new StructuredSelection(AcceleoPositionedQuery.getInput()[0]));
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

		combo.setLabelProvider(new AcceleoPositionedQueryLabelProvider());
		combo.setContentProvider(new ArrayContentProvider());

		// the input of AcceleoPositionedQuery has been compute in the RenameAction class
		combo.setInput(AcceleoPositionedQuery.getInput());

		return combo;
	}

	private AcceleoRenameQueryRefactoring getRenameQueryRefactoring() {
		return (AcceleoRenameQueryRefactoring)getRefactoring();
	}

	/**
	 * Handle changes in the text box.
	 */
	protected void handleInputChanged() {
		final RefactoringStatus status = new RefactoringStatus();
		final AcceleoRenameQueryRefactoring refactoring = this.getRenameQueryRefactoring();

		status.merge(refactoring.setNewQueryName(this.fNameField.getText()));

		final IStructuredSelection selection = (IStructuredSelection)this.fComboViewer.getSelection();
		if (!selection.isEmpty()) {
			final Object obj = selection.getFirstElement();
			if (obj instanceof AcceleoPositionedQuery) {
				refactoring.setQuery((AcceleoPositionedQuery)obj);
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
