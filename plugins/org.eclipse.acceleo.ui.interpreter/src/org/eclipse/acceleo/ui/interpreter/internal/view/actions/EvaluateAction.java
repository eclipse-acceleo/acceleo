/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.view.actions;

import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.jface.action.Action;

/**
 * This action will be called by the interpreter view in order to evaluate an expression, compiling it if
 * necessary.
 * <p>
 * This action is available through the "M1 + M2 + D" (control + shift + D) keyboard shortcut.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class EvaluateAction extends Action {
	/** References the interpreter from which the action was triggered. */
	private InterpreterView interpreterView;

	/**
	 * Initializes the evaluation action given the interpreter from which it was triggered.
	 * 
	 * @param view
	 *            The interpreter view from which this evaluation was triggered.
	 */
	public void initialize(InterpreterView view) {
		interpreterView = view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (interpreterView != null) {
			interpreterView.compileExpression();
			interpreterView.evaluate();
		}
	}
}
