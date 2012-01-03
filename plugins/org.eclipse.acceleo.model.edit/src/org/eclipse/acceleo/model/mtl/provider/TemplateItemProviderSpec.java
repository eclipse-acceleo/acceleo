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

import java.util.Iterator;

import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.ocl.ecore.Variable;

/**
 * Specializes the TemplateInvocationItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TemplateItemProviderSpec extends TemplateItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public TemplateItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.TemplateItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		Template eTemplate = (Template)object;
		StringBuffer text = new StringBuffer();
		text.append("template "); //$NON-NLS-1$
		text.append(eTemplate.getName());
		text.append("("); //$NON-NLS-1$
		for (Iterator<Variable> eParameters = eTemplate.getParameter().iterator(); eParameters.hasNext();) {
			Variable eVariable = eParameters.next();
			if (eVariable.getType() != null) {
				text.append(eVariable.getType().getName());
			}
			if (eParameters.hasNext()) {
				text.append(", "); //$NON-NLS-1$
			}
		}
		text.append(")"); //$NON-NLS-1$
		return text.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.TemplateInvocationItemProvider#getImage(java.lang.Object)
	 */
	@Override
	public Object getImage(Object object) {
		Object res = super.getImage(object);

		if (object instanceof Template) {
			if (((Template)object).isMain()) {
				res = overlayImage(object, getResourceLocator().getImage("full/obj16/Template_main")); //$NON-NLS-1$
			} else if (((Template)object).getVisibility() == VisibilityKind.PRIVATE) {
				res = overlayImage(object, getResourceLocator().getImage("full/obj16/Template_private")); //$NON-NLS-1$
			} else if (((Template)object).getVisibility() == VisibilityKind.PROTECTED) {
				res = overlayImage(object, getResourceLocator().getImage("full/obj16/Template_protected")); //$NON-NLS-1$
			}
		}
		return res;
	}
}
