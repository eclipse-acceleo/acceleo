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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * This class provide the tree structure to be shown in the ReferencesSearchViewPage.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferencesTreeContentProvider implements ITreeContentProvider {

	/**
	 * Comparator class for entry sorting.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	protected class EntryComparator implements Comparator<Object> {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object arg0, Object arg1) {
			int res = 0;
			if (arg0 instanceof ReferenceEntry && arg1 instanceof ReferenceEntry) {
				final int begin0 = ((ReferenceEntry)arg0).getRegion().getOffset();
				final int begin1 = ((ReferenceEntry)arg1).getRegion().getOffset();

				if (begin0 > begin1) {
					res = 1;
				} else if (begin0 < begin1) {
					res = -1;
				} else {
					res = 0;
				}
			} else if (arg0 instanceof IResource && arg1 instanceof IResource) {
				final IResource res0 = (IResource)arg0;
				final IResource res1 = (IResource)arg1;
				final String name0 = res0.getProject().toString() + res0.getProjectRelativePath().toString()
						+ res0.getName();
				final String name1 = res1.getProject().toString() + res1.getProjectRelativePath().toString()
						+ res1.getName();
				res = name0.compareTo(name1);
			} else {
				res = 0;
			}
			return res;
		}
	}

	/**
	 * An empty array for elements with no child.
	 */
	private static final Object[] EMPTY_ARR = new Object[0];

	/**
	 * The map connecting an element with there children.
	 */
	private Map<Object, Set<Object>> map;

	/**
	 * The result provided by the ReferencesSearchQuery.
	 */
	private ReferencesSearchResult result;

	/**
	 * The TreeViewer from the ReferencesSearchViewPage.
	 */
	private TreeViewer viewer;

	/**
	 * Constructor.
	 * 
	 * @param viewer
	 *            the tree view to use to display the result
	 */
	public ReferencesTreeContentProvider(TreeViewer viewer) {
		this.viewer = viewer;
	}

	/**
	 * Refresh the tree viewer.
	 */
	public void clear() {
		viewer.setInput(null);
		viewer.refresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		final Set<Object> children = map.get(parentElement);
		if (children == null) {
			return EMPTY_ARR;
		}
		return children.toArray();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		Object res = null;
		if (element instanceof ReferenceEntry) {
			final ReferenceEntry entry = (ReferenceEntry)element;
			res = entry.getTemplateFile();
		} else if (element instanceof IFile) {
			res = ((IFile)element).getParent();
		} else if (element instanceof IFolder) {
			res = ((IFolder)element).getProject();
		}
		return res;
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
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		viewer = null;
		result = null;
		map.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer theViewer, Object oldInput, Object newInput) {
		if (newInput instanceof ReferencesSearchResult) {
			initialize((ReferencesSearchResult)newInput);
		}
	}

	/**
	 * This method initialize the tree view according to the given input.
	 * 
	 * @param newInput
	 *            the new input on inputChanged
	 */
	protected synchronized void initialize(ReferencesSearchResult newInput) {
		result = newInput;
		map = new HashMap<Object, Set<Object>>();
		if (newInput != null) {
			final Object[] entries = newInput.getElements();
			for (int i = 0; i < entries.length; i++) {
				insert(entries[i], false);
			}
		}
	}

	/**
	 * Insert a new element into the tree view.
	 * 
	 * @param child
	 *            the element to insert
	 * @param refreshViewer
	 *            the tree viewer should be refreshed
	 */
	protected void insert(Object child, boolean refreshViewer) {
		Object localChild = child;
		Object parent = getParent(localChild);
		while (parent != null) {
			if (insertChild(parent, localChild)) {
				if (refreshViewer) {
					viewer.add(parent, localChild);
				}
			} else {
				if (refreshViewer) {
					viewer.refresh(parent);
				}
				return;
			}
			localChild = parent;
			parent = getParent(localChild);
		}
		if (insertChild(result, localChild)) {
			if (refreshViewer) {
				viewer.add(result, localChild);
			}
		}
	}

	/**
	 * Insert the given child to the given parent.
	 * 
	 * @param parent
	 *            the parent
	 * @param child
	 *            the child
	 * @return true if the insertion was done
	 */
	private boolean insertChild(Object parent, Object child) {
		Set<Object> children = map.get(parent);
		if (children == null) {
			children = new TreeSet<Object>(new EntryComparator());
			map.put(parent, children);
		}
		return children.add(child);
	}

	/**
	 * Refresh the tree viewer according to changes.
	 * 
	 * @param updatedElements
	 *            array of updated elements
	 */
	public synchronized void elementsChanged(Object[] updatedElements) {
		for (int i = 0; i < updatedElements.length; i++) {
			if (result.getMatchCount(updatedElements[i]) > 0) {
				insert(updatedElements[i], true);
			} else {
				remove(updatedElements[i], true);
			}
		}
	}

	/**
	 * Remove an element.
	 * 
	 * @param element
	 *            the element to remove
	 * @param refreshViewer
	 *            the tree viewer should be refreshed
	 */
	protected void remove(Object element, boolean refreshViewer) {
		// precondition here: fResult.getMatchCount(child) <= 0

		if (hasChildren(element)) {
			if (refreshViewer) {
				viewer.refresh(element);
			}
		} else {
			if (result.getMatchCount(element) == 0) {
				map.remove(element);
				final Object parent = getParent(element);
				if (parent != null) {
					removeFromSiblings(element, parent);
					remove(parent, refreshViewer);
				} else {
					removeFromSiblings(element, result);
					if (refreshViewer) {
						viewer.refresh();
					}
				}
			} else {
				if (refreshViewer) {
					viewer.refresh(element);
				}
			}
		}
	}

	/**
	 * Remove the given element from the chidlren of parent.
	 * 
	 * @param element
	 *            the element to remove
	 * @param parent
	 *            the parent node to use
	 */
	private void removeFromSiblings(Object element, Object parent) {
		final Set<Object> siblings = map.get(parent);
		if (siblings != null) {
			siblings.remove(element);
		}
	}
}
