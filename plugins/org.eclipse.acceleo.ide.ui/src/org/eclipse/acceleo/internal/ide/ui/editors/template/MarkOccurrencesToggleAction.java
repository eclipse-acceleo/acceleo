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

import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIPreferences;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * This action will activate / desactivate the mark occurrences process.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class MarkOccurrencesToggleAction implements IEditorActionDelegate, IPropertyChangeListener {

	/**
	 * The Acceleo editor.
	 */
	private AcceleoEditor acceleoEditor;

	/**
	 * The action.
	 */
	private IAction markOccurrencesAction;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		AcceleoUIPreferences.switchMarkOccurrences(action.isChecked());
		if (!action.isChecked()) {
			new AcceleoRemoveAnnotationJob(acceleoEditor).schedule();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {

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
			this.markOccurrencesAction = action;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (AcceleoUIPreferences.PREFERENCE_KEY_ENABLE_MARK_OCCURRENCES.equals(event.getProperty())) {
			markOccurrencesAction.setChecked(Boolean.valueOf(event.getNewValue().toString()).booleanValue());
		}
	}

}
