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
import java.util.List;

import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This will act as the content provider for the "variables" Tree Viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class VariableContentProvider implements ITreeContentProvider {
	/** Keeps a reference to this viewer's input. */
	private List<Variable> input;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// Nothing to dispose
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof List<?>) {
			input = (List<Variable>)newInput;
		} else if (newInput instanceof Variable) {
			input = new ArrayList<Variable>();
			input.add((Variable)newInput);
		} else {
			input = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List<?>) {
			return ((List<?>)inputElement).toArray(new Variable[((List<?>)inputElement).size()]);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Variable) {
			return new Object[] {((Variable)parentElement).getValue(), };
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		for (Variable var : input) {
			if (var.getValue() == element) {
				return var;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return element instanceof Variable && ((Variable)element).getValue() != null;
	}
}
