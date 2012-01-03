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
package org.eclipse.acceleo.internal.ide.ui.debug.actions;

import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoLineBreakpoint;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * The breakpoint ruler action to set the condition. A breakpoint is valid only if the current model object
 * validates this condition.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class BreakpointSelectModelRulerAction extends AbstractBreakpointRulerAction {

	/**
	 * The constructor.
	 * 
	 * @param editor
	 *            is the editor
	 * @param rulerInfo
	 *            is the vertical ruler
	 */
	public BreakpointSelectModelRulerAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		setInfo(rulerInfo);
		setTextEditor(editor);
		setText(AcceleoUIMessages.getString("BreakpointSelectModelRulerAction.Text")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		IBreakpoint breakPoint = getBreakpoint();
		if (breakPoint != null && breakPoint.getMarker() != null) {
			try {
				ASTFragment astFragment = new ASTFragment(breakPoint.getMarker().getAttribute(
						AcceleoLineBreakpoint.AST_FRAGMENT, "")); //$NON-NLS-1$
				InputDialog dialog = new InputDialog(
						getTextEditor().getSite().getShell(),
						AcceleoUIMessages.getString("BreakpointSelectModelRulerAction.Text"), AcceleoUIMessages.getString("BreakpointSelectModelRulerAction.DialogMessage"), astFragment.getEObjectNameFilter(), null); //$NON-NLS-1$ //$NON-NLS-2$
				if (dialog.open() == Window.OK && dialog.getValue() != null) {
					astFragment.setEObjectNameFilter(dialog.getValue());
					breakPoint.getMarker().setAttribute(AcceleoLineBreakpoint.AST_FRAGMENT,
							astFragment.toString());
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.IUpdate#update()
	 */
	public void update() {
		setBreakpoint(determineBreakpoint());
		if (getBreakpoint() == null || !(getBreakpoint() instanceof AcceleoLineBreakpoint)) {
			setBreakpoint(null);
			setEnabled(false);
			return;
		}
		setEnabled(true);
	}

}
