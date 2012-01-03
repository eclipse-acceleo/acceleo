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
package org.eclipse.acceleo.internal.ide.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * A tree content provider for file selection.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FileTreeContentProvider implements ITreeContentProvider {
	/**
	 * Should we show closed projects.
	 */
	private boolean showClosedProjects;

	/**
	 * File extension to filter.
	 */
	private String filterExtension;

	/**
	 * Constructor.
	 * 
	 * @param showClosedProjects
	 *            tell if we should show closed projects.
	 * @param filterExtension
	 *            the file extension to filter
	 */
	public FileTreeContentProvider(boolean showClosedProjects, String filterExtension) {
		this.showClosedProjects = showClosedProjects;
		this.filterExtension = filterExtension;
	}

	/**
	 * Constructor.
	 * 
	 * @param showClosedProjects
	 *            tell if we should show closed projects.
	 */
	public FileTreeContentProvider(boolean showClosedProjects) {
		this(showClosedProjects, null);
	}

	/**
	 * The visual part that is using this content provider is about to be disposed. Deallocate all allocated
	 * SWT resources.
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object element) {
		Object[] res = new Object[0];
		if (element instanceof IWorkspace) {
			// check if closed projects should be shown
			IProject[] allProjects = ((IWorkspace)element).getRoot().getProjects();
			if (showClosedProjects) {
				res = allProjects;
			} else {

				ArrayList<IProject> accessibleProjects = new ArrayList<IProject>();
				for (int i = 0; i < allProjects.length; i++) {
					if (allProjects[i].isOpen()) {
						accessibleProjects.add(allProjects[i]);
					}
				}
				res = accessibleProjects.toArray();
			}
		} else if (element instanceof IContainer) {
			IContainer container = (IContainer)element;
			if (container.isAccessible()) {
				try {
					List<IResource> children = new ArrayList<IResource>();
					IResource[] members = container.members();
					for (int i = 0; i < members.length; i++) {
						if (!members[i].isDerived() && !members[i].getName().startsWith(".") //$NON-NLS-1$
								&& (filterExtension == null || members[i] instanceof IContainer)) {
							children.add(members[i]);
						} else {
							if (!members[i].isDerived() && !members[i].getName().startsWith(".") //$NON-NLS-1$
									&& filterExtension.equals(members[i].getFullPath().getFileExtension())) {
								children.add(members[i]);
							}
						}
					}
					res = children.toArray();
				} catch (CoreException e) {
					// this should never happen because we call #isAccessible before invoking #members
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		if (element instanceof IResource) {
			return ((IResource)element).getParent();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * Specify whether or not to show closed projects in the tree viewer. Default is to show closed projects.
	 * 
	 * @param show
	 *            boolean if false, do not show closed projects in the tree
	 */
	public void showClosedProjects(boolean show) {
		showClosedProjects = show;
	}
}
