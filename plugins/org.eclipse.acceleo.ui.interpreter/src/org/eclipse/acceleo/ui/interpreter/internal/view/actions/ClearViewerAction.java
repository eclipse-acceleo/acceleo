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

import org.eclipse.acceleo.ui.interpreter.internal.IInterpreterConstants;
import org.eclipse.acceleo.ui.interpreter.internal.InterpreterImages;
import org.eclipse.acceleo.ui.interpreter.internal.InterpreterMessages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * This action will be added to the result section's tool bar and will allow users to clear all previous
 * evaluation results.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ClearViewerAction extends Action {
	/** The tooltip we'll show for this action. */
	private static final String TOOLTIP_TEXT = InterpreterMessages
			.getString("interpreter.action.clear.tooltip"); //$NON-NLS-1$

	/** The viewer that should be cleared through this action. */
	private final Viewer viewer;

	/**
	 * Instantiates the "clear" action given the viewer it should operate on.
	 * 
	 * @param viewer
	 *            The viewer that should be cleared through this action.
	 */
	public ClearViewerAction(Viewer viewer) {
		super(null, IAction.AS_PUSH_BUTTON);
		setToolTipText(TOOLTIP_TEXT);
		setImageDescriptor(InterpreterImages.getImageDescriptor(IInterpreterConstants.BUTTON_CLEAR_ICON));
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (viewer.getControl().isDisposed()) {
			return;
		}
		if (viewer instanceof TextViewer) {
			((TextViewer)viewer).getTextWidget().setText(""); //$NON-NLS-1$
		} else {
			viewer.setInput(null);
		}
	}
}
