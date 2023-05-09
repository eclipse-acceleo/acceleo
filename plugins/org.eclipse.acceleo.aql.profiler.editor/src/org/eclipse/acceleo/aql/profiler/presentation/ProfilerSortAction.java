/*******************************************************************************
 * Copyright (c) 2008, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.presentation;

import org.eclipse.acceleo.aql.profiler.editor.AcceleoProfilerEditorMessages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Sort Action class for the profiler view.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProfilerSortAction extends Action {
	/** Action Image descriptor. */
	private static final ImageDescriptor SORT_IMG_DESC = AbstractUIPlugin.imageDescriptorFromPlugin(
			"org.eclipse.acceleo.aql.profiler.editor", "icons/actions/durationSort.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	/** The current sort status of this action. */
	private final ProfilerSortStatus sortStatus;

	/**
	 * The selection tree viewer.
	 */
	private final TreeViewer selectionViewer;

	/**
	 * Constructor.
	 * 
	 * @param sortStatus
	 *            the sort status
	 * @param selectionViewer
	 *            the selection viewer
	 */
	public ProfilerSortAction(ProfilerSortStatus sortStatus, TreeViewer selectionViewer) {
		super(AcceleoProfilerEditorMessages.getString("ProfilerSortAction.text"), SWT.TOGGLE); //$NON-NLS-1$
		setImageDescriptor(SORT_IMG_DESC);
		this.sortStatus = sortStatus;
		this.selectionViewer = selectionViewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (sortStatus.getSortOrder() == ProfilerSortStatus.SORT_BY_CREATION_TIME) {
			sortStatus.setSortOrder(ProfilerSortStatus.SORT_BY_TIME);
		} else {
			sortStatus.setSortOrder(ProfilerSortStatus.SORT_BY_CREATION_TIME);
		}
		selectionViewer.refresh();
	}
}
