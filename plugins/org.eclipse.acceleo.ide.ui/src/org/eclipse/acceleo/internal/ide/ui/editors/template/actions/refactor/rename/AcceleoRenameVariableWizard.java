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
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * This class represent the wizard that will realize the refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRenameVariableWizard extends RefactoringWizard {
	/**
	 * The constructor.
	 * 
	 * @param refactoring
	 *            The refactoring.
	 * @param name
	 *            The name of the wizard.
	 */
	public AcceleoRenameVariableWizard(AcceleoRenameVariableRefactoring refactoring, String name) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
	 */
	@Override
	protected void addUserInputPages() {
		addPage(new AcceleoRenameVariableInputWizardPage(AcceleoUIMessages
				.getString("AcceleoEditorRenameVariableRefactoring.RenameVariableInputWizardPage"))); //$NON-NLS-1$
	}
}
