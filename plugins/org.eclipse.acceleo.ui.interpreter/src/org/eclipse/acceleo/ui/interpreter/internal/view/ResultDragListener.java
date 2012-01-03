/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.view;

import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * This listener will be registered against the "Result" TreeViewer in order to allow drag operations on that
 * viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ResultDragListener extends DragSourceAdapter {
	/** Keeps track of the elements selected at the time the user starts the drag operation. */
	protected ISelection selection;

	/** Keeps a reference towards the viewer against which this listener is registered. */
	private TreeViewer viewer;

	/**
	 * Creates a new drag listener for the given <code>viewer</code>.
	 * 
	 * @param viewer
	 *            The viewer against which this drag listener is registered.
	 */
	public ResultDragListener(TreeViewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragFinished(DragSourceEvent event) {
		selection = null;
		LocalTransfer.getInstance().javaToNative(null, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragSetData(DragSourceEvent event) {
		if (LocalTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = selection;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragStart(DragSourceEvent event) {
		selection = viewer.getSelection();
	}
}
