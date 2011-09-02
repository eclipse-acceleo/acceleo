/*******************************************************************************
 * Copyright (c) 2011 Obeo and others.
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
import org.eclipse.acceleo.ui.interpreter.internal.InterpreterImages;
import org.eclipse.acceleo.ui.interpreter.internal.InterpreterMessages;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

/**
 * This action will allow us to link the current language interpreter with the currently focused editor.
 * <p>
 * It is up to the language to determine what to do with the editor, and some might very well ignore it
 * altogether.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ToggleLinkWithEditorAction extends Action {
	/** The tooltip we'll show for this action. */
	private static final String TOOLTIP_TEXT = InterpreterMessages
			.getString("intepreter.action.link.tooltip"); //$NON-NLS-1$

	/** Keeps a reference to the interpreter view. */
	private InterpreterView view;

	/**
	 * Instantiates the Link with editor toggle given the interpreter view it should operate on.
	 * 
	 * @param view
	 *            The interpreter view it should operate on.
	 */
	public ToggleLinkWithEditorAction(InterpreterView view) {
		super(null, IAction.AS_CHECK_BOX);
		setToolTipText(TOOLTIP_TEXT);
		setImageDescriptor(InterpreterImages
				.getImageDescriptor(IInterpreterConstants.LINK_WITH_EDITOR_TOGGLE_ICON));
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		super.run();
	}
}
