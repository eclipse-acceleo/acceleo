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
package org.eclipse.acceleo.ui.interpreter.view;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * This will act as the content provider for the "result" Tree Viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ResultContentProvider extends AdapterFactoryContentProvider {
	/**
	 * Instantiates this content provider given its adapter factory.
	 * 
	 * @param adapterFactory
	 *            The adapter factory for this content provider.
	 */
	public ResultContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		Object[] elements = new Object[0];
		if (inputElement instanceof Collection<?>) {
			elements = ((Collection<?>)inputElement)
					.toArray(new Object[((Collection<?>)inputElement).size()]);
		} else {
			elements = super.getElements(inputElement);
			if (elements == null || elements.length == 0 && inputElement != null) {
				if (inputElement instanceof EObject) {
					elements = new Object[] {inputElement, };
				} else {
					elements = new Object[] {inputElement.toString(), };
				}
			}
		}
		return elements;
	}
}
