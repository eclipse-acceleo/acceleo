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
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The input page for the wizard of the rename text refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameTextInputWizardPage extends UserInputWizardPage {

	/**
	 * The text field for the new name of the variable.
	 */
	private Text fNameField;

	/**
	 * The constructor.
	 * 
	 * @param name
	 *            The name of the wizard page.
	 */
	public AcceleoRenameTextInputWizardPage(String name) {
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
		label.setText(AcceleoUIMessages.getString("AcceleoEditorRenameTextRefactoring.NewName")); //$NON-NLS-1$

		this.fNameField = createNameField(result);

		final AcceleoRenameTextRefactoring refactoring = this.getRenameTextRefactoring();
		fNameField.setText(refactoring.getText());

		fNameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				handleInputChanged();
			}
		});

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
	 * Returns the refactoring.
	 * 
	 * @return The refactoring
	 */
	private AcceleoRenameTextRefactoring getRenameTextRefactoring() {
		return (AcceleoRenameTextRefactoring)getRefactoring();
	}

	/**
	 * Handle changes in the text box.
	 */
	protected void handleInputChanged() {
		final RefactoringStatus status = new RefactoringStatus();
		final AcceleoRenameTextRefactoring refactoring = this.getRenameTextRefactoring();

		status.merge(refactoring.setNewName(this.fNameField.getText()));

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
