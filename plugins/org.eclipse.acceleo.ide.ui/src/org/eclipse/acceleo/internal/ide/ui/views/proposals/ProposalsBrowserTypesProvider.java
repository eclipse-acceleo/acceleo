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
package org.eclipse.acceleo.internal.ide.ui.views.proposals;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * The 'ProposalsBrowser' view is used to update the settings of the current 'pattern' completion proposal.
 * This Types content provider displays the metamodel types of the current template. This content provider
 * wraps an AdapterFactory and it delegates its JFace provider interfaces to corresponding adapter-implemented
 * item provider interfaces.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ProposalsBrowserTypesProvider extends AdapterFactoryContentProvider {

	/**
	 * The view.
	 */
	private ProposalsBrowser view;

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            is the view
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public ProposalsBrowserTypesProvider(ProposalsBrowser view, AdapterFactory adapterFactory) {
		super(adapterFactory);
		this.view = view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object object) {
		Object[] result;
		if (object instanceof String) {
			result = new Object[] {(String)object };
		} else if (object instanceof List<?>) {
			result = ((List<?>)object).toArray();
		} else if (object instanceof Object[]) {
			result = (Object[])object;
		} else {
			result = super.getElements(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof EClassHandler) {
			return view.hasSubType((EClassHandler)object);
		}
		return super.hasChildren(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof EClassHandler) {
			return view.createSubTypeHandlers((EClassHandler)object);
		}
		return super.getChildren(object);
	}

}
