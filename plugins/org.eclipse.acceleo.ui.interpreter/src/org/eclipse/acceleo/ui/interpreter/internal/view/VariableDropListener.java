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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.acceleo.ui.interpreter.internal.view.actions.CreateVariableAction;
import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

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
			Widget targetItem = event.item;

			if (targetItem instanceof TreeItem) {
				Object targetVariable = targetItem.getData();
				while (!(targetVariable instanceof Variable)) {
					targetItem = ((TreeItem)targetItem).getParentItem();
					targetVariable = targetItem.getData();
				}
				addToVariable((Variable)targetVariable, selection);
			} else {
				createNewVariable(selection);
			}
		}
	}

	/**
	 * Adds the given selection to the values of <code>targetVariable</code>.
	 * 
	 * @param targetVariable
	 *            Variable into which we are to add values.
	 * @param selection
	 *            The values that are to be added to <code>targetVariable</code>.
	 */
	@SuppressWarnings("unchecked")
	private void addToVariable(Variable targetVariable, Collection<?> selection) {
		Collection<Object> newValue;

		final Object currentValue = targetVariable.getValue();
		if (currentValue instanceof Collection<?>) {
			newValue = (Collection<Object>)currentValue;
		} else {
			newValue = new ArrayList<Object>();
			if (currentValue != null) {
				newValue.add(currentValue);
			}
		}

		newValue.addAll(selection);

		// if there is but a single value, simply use it
		if (newValue.size() == 1) {
			targetVariable.setValue(newValue.iterator().next());
		} else {
			targetVariable.setValue(newValue);
		}

		viewer.refresh();
	}

	/**
	 * Creates a new variable to hold the given selection.
	 * 
	 * @param selection
	 *            The selection for which to create a Variable.
	 */
	private void createNewVariable(Collection<?> selection) {
		final Object variableValue;
		if (selection.size() == 1) {
			variableValue = selection.iterator().next();
		} else {
			variableValue = selection;
		}

		CreateVariableAction action = new CreateVariableAction(viewer, variableValue);
		action.run();
	}

	/**
	 * Tries and extract the selected objects from the drop event.
	 * 
	 * @param event
	 *            The drop event from which to extract a selection.
	 * @return The list containing the event's selection, an empty list if none.
	 */
	private Collection<?> getSelection(DropTargetEvent event) {
		if (event.data instanceof IStructuredSelection) {
			return ((IStructuredSelection)event.data).toList();
		}
		return Collections.emptyList();
	}
}
