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
 * This Patterns content provider displays the proposals Patterns available in the workbench. This content
 * provider wraps an AdapterFactory and it delegates its JFace provider interfaces to corresponding
 * adapter-implemented item provider interfaces.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ProposalsBrowserPatternsProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public ProposalsBrowserPatternsProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
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

}
