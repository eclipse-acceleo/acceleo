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
package org.eclipse.acceleo.model.mtl.provider;

import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the QueryInvocationItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryInvocationItemProviderSpec extends QueryInvocationItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public QueryInvocationItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.QueryInvocationItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		return object.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.QueryInvocationItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		Object image = null;
		if (object instanceof QueryInvocation && ((QueryInvocation)object).getDefinition() != null) {
			Query query = ((QueryInvocation)object).getDefinition();
			if (query.getVisibility() == VisibilityKind.PRIVATE) {
				image = overlayImage(object, getResourceLocator().getImage("full/obj16/Query_private")); //$NON-NLS-1$
			} else if (query.getVisibility() == VisibilityKind.PROTECTED) {
				image = overlayImage(object, getResourceLocator().getImage("full/obj16/Query_protected")); //$NON-NLS-1$
			} else if (query.getVisibility() == VisibilityKind.PUBLIC) {
				image = overlayImage(object, getResourceLocator().getImage("full/obj16/Query")); //$NON-NLS-1$
			}
		}

		if (image == null) {
			image = super.getImage(object);
		}

		return image;
	}
}
