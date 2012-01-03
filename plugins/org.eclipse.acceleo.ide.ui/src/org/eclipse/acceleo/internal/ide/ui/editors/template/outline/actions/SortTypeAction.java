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
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;

/**
 * This action will sort the element of the treeviewer by the type of their first parameter.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class SortTypeAction extends Action {

	/**
	 * The tree viewer of the outline view.
	 */
	private final TreeViewer viewer;

	/**
	 * The sorter.
	 */
	private final AcceleoOutlineViewerTypeSorter sorter;

	/**
	 * The constructor.
	 * 
	 * @param treeViewer
	 *            The treeviewer of the outline view
	 */
	public SortTypeAction(TreeViewer treeViewer) {
		super(AcceleoUIMessages.getString("AcceleoEditorOutline.Actions.SortType"), SWT.TOGGLE); //$NON-NLS-1$
		this.viewer = treeViewer;
		this.sorter = new AcceleoOutlineViewerTypeSorter();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (this.viewer.getSorter() != null && this.viewer.getSorter() == this.sorter) {
			this.viewer.setSorter(null);
		} else if (this.viewer.getSorter() != null) {
			this.setChecked(false);
		} else {
			this.viewer.setSorter(this.sorter);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return AcceleoUIActivator.getImageDescriptor("icons/template-editor/outline/SortType.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return AcceleoUIMessages.getString("AcceleoEditorOutline.Actions.SortType"); //$NON-NLS-1$
	}

}
