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
import java.util.List;

import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * This will act as the content provider for the "variables" Tree Viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class VariableContentProvider extends AdapterFactoryContentProvider {
	/** Keeps a reference to this viewer's input. */
	private List<Variable> input;

	/**
	 * Instantiates this content provider given its adapter factory.
	 * 
	 * @param adapterFactory
	 *            The adapter factory for this content provider.
	 */
	public VariableContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		Object[] children = null;
		if (parentElement instanceof Variable) {
			Object variableValue = ((Variable)parentElement).getValue();
			if (variableValue instanceof Collection<?>) {
				children = ((Collection<?>)variableValue).toArray(new Object[((Collection<?>)variableValue)
						.size()]);
			} else {
				children = new Object[] {variableValue, };
			}
			return children;
		}
		return super.getChildren(parentElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>)inputElement).toArray(new Variable[((Collection<?>)inputElement).size()]);
		}
		return super.getElements(inputElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		for (Variable var : input) {
			if (var.getValue() == element) {
				return var;
			}
		}
		return super.getParent(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Variable) {
			return ((Variable)element).getValue() != null;
		}
		return super.hasChildren(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer aViewer, Object oldInput, Object newInput) {
		super.inputChanged(aViewer, oldInput, newInput);
		if (newInput instanceof List<?>) {
			input = (List<Variable>)newInput;
		} else if (newInput instanceof Variable) {
			input = new ArrayList<Variable>();
			input.add((Variable)newInput);
		} else {
			input = null;
		}
	}
}
