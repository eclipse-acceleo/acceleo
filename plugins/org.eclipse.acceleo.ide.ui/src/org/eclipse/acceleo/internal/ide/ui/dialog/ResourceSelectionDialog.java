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
package org.eclipse.acceleo.internal.ide.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ISelectionValidator;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * A dialog for file selection. It looks like ContainerSelectionDialog Eclipse dialog.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ResourceSelectionDialog extends SelectionDialog {
	/**
	 * An empty string constant.
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * The validation message.
	 */
	protected Label statusMessage;

	/**
	 * For validating the selection.
	 */
	protected ISelectionValidator validator;

	/**
	 * The widget group.
	 */
	protected TreeSelectionComposite selection;

	/**
	 * The root resource to populate the viewer with.
	 */
	private IResource initialSelection;

	/**
	 * The content provider of the tree view.
	 */
	private ITreeContentProvider contentProvider;

	/**
	 * Creates a resource selection dialog rooted at the given resource. All selections are considered valid.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param initialSelection
	 *            the initial selection in the tree
	 * @param message
	 *            the message to be displayed at the top of this dialog, or <code>null</code> to display a
	 *            default message
	 */
	public ResourceSelectionDialog(Shell parentShell, IResource initialSelection, String message) {
		super(parentShell);
		this.initialSelection = initialSelection;
		if (message != null) {
			setMessage(message);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite area = (Composite)super.createDialogArea(parent);

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				if (statusMessage != null && validator != null) {
					String errorMsg = validator.isValid(selection.getResourceFullPath());
					if (errorMsg == null || errorMsg.equals(EMPTY_STRING)) {
						statusMessage.setText(EMPTY_STRING);
						getOkButton().setEnabled(true);
					} else {
						statusMessage.setText(errorMsg);
						getOkButton().setEnabled(false);
					}
				}
			}
		};

		selection = new TreeSelectionComposite(area, contentProvider, listener, true, getMessage());

		if (initialSelection != null) {
			selection.setSelectedResource(initialSelection);
		}

		statusMessage = new Label(area, SWT.WRAP);
		statusMessage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		statusMessage.setText(" \n "); //$NON-NLS-1$
		statusMessage.setFont(parent.getFont());

		return dialogArea;
	}

	/**
	 * The <code>FileSelectionDialog</code> implementation of this <code>Dialog</code> method builds a list of
	 * the selected resource for later retrieval by the client and closes this dialog. {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {

		List<IPath> chosenResourcePathList = new ArrayList<IPath>();
		IPath returnValue = selection.getResourceFullPath();
		if (returnValue != null) {
			chosenResourcePathList.add(returnValue);
		}
		setResult(chosenResourcePathList);
		super.okPressed();
	}

	/**
	 * Sets the validator to use.
	 * 
	 * @param validator
	 *            A selection validator
	 */
	public void setValidator(ISelectionValidator validator) {
		this.validator = validator;
	}

	public void setContentProvider(ITreeContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}
}
