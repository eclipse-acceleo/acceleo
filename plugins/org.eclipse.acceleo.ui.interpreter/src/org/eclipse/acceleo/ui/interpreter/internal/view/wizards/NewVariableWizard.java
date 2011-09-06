/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.view.wizards;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.wizard.Wizard;

/**
 * This wizard will be used in order to allow users to create new variables and select their values.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NewVariableWizard extends Wizard {
	/** The editing domain from which this wizard can take values. */
	private final EditingDomain editingDomain;

	/**
	 * The wizard page on which users may (have) select(ed) one or more elements as values of the new
	 * variable.
	 */
	private ElementSelectionWizardPage elementSelectionPage;

	/**
	 * Instantiates this wizard given the editing domain from which this wizard can take values.
	 * 
	 * @param editingDomain
	 *            The editing domain from which this wizard can take values.
	 */
	public NewVariableWizard(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		elementSelectionPage = new ElementSelectionWizardPage(editingDomain);

		addPage(elementSelectionPage);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
