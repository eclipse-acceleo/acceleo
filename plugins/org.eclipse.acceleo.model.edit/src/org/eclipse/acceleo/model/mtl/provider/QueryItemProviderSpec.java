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
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the QueryItemProvider.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class QueryItemProviderSpec extends QueryItemProvider {

	/**
	 * The constructor.
	 * 
	 * @param adapterFactory
	 *            The adapter factory.
	 */
	public QueryItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.QueryInvocationItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		Object image = null;
		if (object instanceof Query) {
			Query query = (Query)object;
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
