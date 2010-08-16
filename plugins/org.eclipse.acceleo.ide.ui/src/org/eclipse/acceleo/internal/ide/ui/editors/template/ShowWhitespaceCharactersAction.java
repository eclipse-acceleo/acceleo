/*******************************************************************************
 * Copyright (c) 2006, 2010 Wind River Systems, Inc., IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Anton Leherbauer (Wind River Systems) - initial API and implementation
 *     Stephane Begaudeau (Obeo) - Adapting for Acceleo needs
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * This action will add the whitespace painter to the acceleo editor.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class ShowWhitespaceCharactersAction implements IEditorActionDelegate {

	/**
	 * The painter.
	 **/
	private AcceleoWhitespaceCharactersPainter painter;

	/**
	 * The Acceleo editor.
	 */
	private AcceleoEditor acceleoEditor;

	/**
	 * Indicates if the button is checked.
	 */
	private boolean isChecked;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		togglePainterState();
	}

	/**
	 * Installs the painter on the editor.
	 */
	private void installPainter() {
		Assert.isTrue(this.painter == null);
		ITextViewer viewer = this.acceleoEditor.getAcceleoSourceViewer();
		if (viewer instanceof ITextViewerExtension2) {
			this.painter = new AcceleoWhitespaceCharactersPainter(acceleoEditor);
			((ITextViewerExtension2)viewer).addPainter(this.painter);
		}
	}

	/**
	 * Remove the painter from the current editor.
	 */
	private void uninstallPainter() {
		if (this.painter == null) {
			return;
		}
		ITextViewer viewer = this.acceleoEditor.getAcceleoSourceViewer();
		if (viewer instanceof ITextViewerExtension2) {
			((ITextViewerExtension2)viewer).removePainter(this.painter);
		}

		this.painter.deactivate(true);
		this.painter = null;
	}

	/**
	 * Toggles the painter state.
	 */
	private void togglePainterState() {
		if (!isChecked) {
			installPainter();
			this.isChecked = true;
		} else {
			uninstallPainter();
			this.isChecked = false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof AcceleoEditor) {
			this.acceleoEditor = (AcceleoEditor)targetEditor;
		}
	}
}
