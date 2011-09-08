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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ui.interpreter.internal.InterpreterMessages;
import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

/**
 * This action can be used in order to add a new Boolean value to a given variable.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class NewBooleanValueAction extends Action {
	/** Keeps a reference to the variable viewer. */
	protected final TreeViewer variableViewer;

	/** Variable to which we are adding a value */
	private final Variable variable;

	/**
	 * Instantiates the "new value > Boolean" action given the variable to which we are adding a value.
	 * 
	 * @param viewer
	 *            The variable viewer.
	 * @param variable
	 *            The variable to which we are adding a value.
	 */
	public NewBooleanValueAction(TreeViewer variableViewer, Variable variable) {
		super(InterpreterMessages.getString("interpreter.action.newvalue.boolean.name")); //$NON-NLS-1$
		this.variableViewer = variableViewer;
		this.variable = variable;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		InputDialog dialog = new InputDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
				InterpreterMessages.getString("interpreter.action.newvalue.boolean.popup.title"), //$NON-NLS-1$
				InterpreterMessages.getString("interpreter.action.newvalue.boolean.popup.message") + ':', "", //$NON-NLS-1$ //$NON-NLS-2$
				new BooleanValueValidator());
		int result = dialog.open();
		if (result == Window.OK) {
			Object value = variable.getValue();
			Object addedValue = Boolean.valueOf(dialog.getValue());
			if (value != null && !(value instanceof List<?>)) {
				List<Object> newValue = new ArrayList<Object>();
				newValue.add(value);
				newValue.add(addedValue);
				variable.setValue(newValue);
			} else if (value instanceof List<?>) {
				((List<Object>)value).add(addedValue);
			} else {
				variable.setValue(addedValue);
			}
			variableViewer.expandToLevel(new TreePath(new Object[] {variable, addedValue, }), 0);
			variableViewer.refresh();
		}
	}

	/**
	 * This validator will check that the entered String is a Boolean.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	protected class BooleanValueValidator implements IInputValidator {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
		 */
		public String isValid(String newText) {
			String errorMessage = null;
			if (newText == null || newText.length() == 0) {
				errorMessage = InterpreterMessages
						.getString("interpreter.action.newvalue.boolean.popup.error.novalue"); //$NON-NLS-1$
			} else if (!isBoolean(newText)) {
				errorMessage = InterpreterMessages.getString(
						"interpreter.action.newvalue.boolean.popup.error.invalid", newText); //$NON-NLS-1$
			}
			return errorMessage;
		}

		/**
		 * Returns <code>true</code> if the given String can be parsed as a Boolean.
		 * 
		 * @param value
		 *            Value we need to try and parse as a Boolean.
		 * @return <code>true</code> if the given <code>value</code> can be parsed as a Boolean,
		 *         <code>false</code> otherwise.
		 */
		private boolean isBoolean(String value) {
			return value.equalsIgnoreCase(Boolean.TRUE.toString())
					|| value.equalsIgnoreCase(Boolean.FALSE.toString());
		}
	}
}
