/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl.internal.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.ui.PlatformUI;

/**
 * This action will be used to import the content of a CompleteOCL document within the expression section of
 * the interpreter view.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public final class ImportCompleteOCLResourceAction extends Action {
	/** The viewer in which to import a resource's content. */
	private TextViewer viewer;

	/**
	 * Instantiates the load resource action.
	 */
	public ImportCompleteOCLResourceAction(TextViewer viewer) {
		super("Import Complete OCL Resource", IAction.AS_PUSH_BUTTON);
		setToolTipText("Import the content of a CompleteOCL Resource into the expression area.");
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		new ImportCompleteOCLResourceDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), viewer)
				.open();
	}
}
