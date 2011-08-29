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
package org.eclipse.acceleo.ui.interpreter.internal.view.actions;

import org.eclipse.acceleo.ui.interpreter.internal.IInterpreterConstants;
import org.eclipse.acceleo.ui.interpreter.internal.view.AcceleoInterpreterImages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;

/**
 * This action will be displayed on the interpreter view's toolbar. It will make it possible to show or hide
 * the variable part of said view.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ToggleVariableVisibilityAction extends Action {
	/** The tooltip we'll show for this action. */
	private static final String TOOLTIP_TEXT = "Show variables";

	/** The form that will have to be re-laid out when changing visibilities. */
	private Form form;

	/**
	 * Keeps a reference towards the right column of our form. (The Composite that is to be show/hidden by
	 * this action.)
	 */
	private Composite rightColumn;

	/**
	 * Instantiates our action given the {@link Composite} that is to be hidden or shown, and the form
	 * containing this Composite.
	 * 
	 * @param form
	 *            The interpreter form.
	 * @param variableContainer
	 *            The Variable Section's container (right column of the form).
	 */
	public ToggleVariableVisibilityAction(Form interpreterForm, Composite variableContainer) {
		super(null, IAction.AS_CHECK_BOX);
		setImageDescriptor(AcceleoInterpreterImages
				.getImageDescriptor(IInterpreterConstants.TOGGLE_VARIABLE_VISIBILITY_ICON));
		setToolTipText(TOOLTIP_TEXT);
		this.form = interpreterForm;
		this.rightColumn = variableContainer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (rightColumn != null && !rightColumn.isDisposed()
				&& rightColumn.getLayoutData() instanceof GridData) {
			boolean hide = rightColumn.getVisible();
			rightColumn.setVisible(!hide);
			((GridData)rightColumn.getLayoutData()).exclude = hide;
			form.layout();
		}
	}
}
