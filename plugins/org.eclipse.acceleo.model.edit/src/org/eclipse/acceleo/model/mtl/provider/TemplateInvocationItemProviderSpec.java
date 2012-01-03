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

import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the TemplateInvocationItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TemplateInvocationItemProviderSpec extends TemplateInvocationItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public TemplateInvocationItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.TemplateInvocationItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		Object res = super.getImage(object);

		if (object != null && ((TemplateInvocation)object).getDefinition() != null) {
			if (((TemplateInvocation)object).getDefinition().isMain()) {
				res = overlayImage(object, getResourceLocator()
						.getImage("full/obj16/TemplateInvocation_main")); //$NON-NLS-1$			
			} else if (((TemplateInvocation)object).getDefinition().getVisibility() == VisibilityKind.PRIVATE) {
				res = overlayImage(object, getResourceLocator().getImage("full/obj16/Template_private")); //$NON-NLS-1$
			} else if (((TemplateInvocation)object).getDefinition().getVisibility() == VisibilityKind.PROTECTED) {
				res = overlayImage(object, getResourceLocator().getImage("full/obj16/Template_protected")); //$NON-NLS-1$
			}
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.TemplateInvocationItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		return object.toString();
	}
}
