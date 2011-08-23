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
package org.eclipse.acceleo.ui.interpreter.internal.view;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;

/**
 * This listener will be registered against the "Variables" TreeViewer in order to allow drop operations on
 * that viewer from workspace selections.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class VariableDropListener extends DropTargetAdapter {
	/** Keeps a reference towards the viewer against which this listener is registered. */
	private TreeViewer viewer;

	/**
	 * Creates a new drop listener for the given <code>viewer</code>.
	 * 
	 * @param viewer
	 *            The viewer against which this drop listener is registered.
	 */
	public VariableDropListener(TreeViewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DropTargetAdapter#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void drop(DropTargetEvent event) {
		Collection<?> selection = getSelection(event);
		if (!selection.isEmpty()) {
			Object target = getDropTarget(event);

			if (target == null) {
				createNewVariable(selection);
			}
		}
	}

	/**
	 * Creates a new variable to hold the given selection.
	 * 
	 * @param selection
	 *            The selection for which to create a Variable.
	 */
	private void createNewVariable(Collection<?> selection) {
		if (selection.size() == 1) {
			Object variableValue = selection.iterator().next();

			// Do we have a name that can be used for this variable?
			if (variableValue instanceof ENamedElement) {
				final String variableName = ((ENamedElement)variableValue).getName();

			}
		}
	}

	/**
	 * Tries and extract the selected objects from the drop event.
	 * 
	 * @param event
	 *            The drop event from which to extract a selection.
	 * @return The list containing the event's selection, an empty list if none.
	 */
	public Collection<?> getSelection(DropTargetEvent event) {
		if (event.data instanceof IStructuredSelection) {
			return ((IStructuredSelection)event.data).toList();
		}
		return Collections.emptyList();
	}

	/**
	 * Tries and extract the target Object of the given drop event.
	 * 
	 * @param event
	 *            The drop event from which to extract a target.
	 * @return The target of this drop event, <code>null</code> if none.
	 */
	public Object getDropTarget(DropTargetEvent event) {
		if (event.item != null) {
			return event.item.getData();
		}
		return null;
	}
}
