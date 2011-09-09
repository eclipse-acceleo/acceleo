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

import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.jface.wizard.Wizard;

/**
 * This wizard will be used
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NewVariableWizard extends Wizard {
	/** If there was a variable selected in the viewer, this will hold a reference to it. */
	private final Variable selectedVariable;

	/**
	 * Instantiates The new variable wizard with no initial selection.
	 */
	public NewVariableWizard() {
		super();
		this.selectedVariable = null;
	}

	/**
	 * Instantiates the new variable wizard given the initially selected variable.
	 * 
	 * @param selectedVariable
	 *            The variable that was selected in the viewer when this wizard has been created.
	 */
	public NewVariableWizard(Variable selectedVariable) {
		super();
		this.selectedVariable = selectedVariable;
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
