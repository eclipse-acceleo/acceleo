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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;

/**
 * This action will hide all the templates from the outline view.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class HideTemplatesAction extends Action {

	/**
	 * The tree viewer of the outline view.
	 */
	private final TreeViewer viewer;

	/**
	 * This filter will not allow queries.
	 */
	private final ViewerFilter filter;

	/**
	 * The constructor.
	 * 
	 * @param treeViewer
	 *            The treeviewer of the outline view
	 */
	public HideTemplatesAction(TreeViewer treeViewer) {
		super(AcceleoUIMessages.getString("AcceleoEditorOutline.Actions.HideTemplates"), SWT.TOGGLE); //$NON-NLS-1$
		this.viewer = treeViewer;
		this.filter = new ViewerFilter() {
			@Override
			public boolean select(Viewer tree, Object parentElement, Object element) {
				boolean result = true;
				if (element instanceof Template) {
					result = false;
				}
				return result;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		ViewerFilter[] filters = this.viewer.getFilters();
		boolean possessFilter = false;
		for (ViewerFilter viewerFilter : filters) {
			if (viewerFilter == this.filter) {
				possessFilter = true;
				break;
			}
		}

		if (possessFilter) {
			List<ViewerFilter> filterList = new ArrayList<ViewerFilter>();
			for (ViewerFilter viewerFilter : filters) {
				if (viewerFilter != this.filter) {
					filterList.add(viewerFilter);
				}
			}
			this.viewer.setFilters(filterList.toArray(new ViewerFilter[filterList.size()]));
		} else {
			this.viewer.addFilter(this.filter);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return AcceleoUIActivator.getImageDescriptor("icons/template-editor/outline/HideTemplates.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return AcceleoUIMessages.getString("AcceleoEditorOutline.Actions.HideTemplates"); //$NON-NLS-1$
	}

}
