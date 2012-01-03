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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * The filter for the Acceleo result view. It is used to extract a subset of the workspace elements to show.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoResultFilesFilter extends ViewerFilter {

	/**
	 * The view.
	 */
	private AcceleoResultView view;

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            is the view
	 */
	public AcceleoResultFilesFilter(AcceleoResultView view) {
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parent, Object element) {
		if (element instanceof String) {
			return true;
		}
		Object resource = element;
		if (resource instanceof IJavaProject) {
			resource = ((IJavaProject)resource).getProject();
		}
		return !(resource instanceof IResource) || select((IResource)resource);
	}

	/**
	 * Indicates if the given resource must be shown.
	 * 
	 * @param resource
	 *            is the resource to select or not
	 * @return true if the given resource must be shown
	 */
	private boolean select(IResource resource) {
		if (view.getContent() != null) {
			IPath path = resource.getFullPath();
			for (TraceabilityTargetFile targetFile : view.getContent().getTargetFiles()) {
				if (path.isPrefixOf(targetFile.getTargetFileFullPath())) {
					return true;
				}
			}
		}
		return false;
	}

}
