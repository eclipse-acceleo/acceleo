/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.provider;

import java.util.Iterator;

import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.provider.utils.ASTUtils;
import org.eclipse.emf.common.notify.AdapterFactory;

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
		Iterator<Variable> eParameters = eTemplate.getParameters().iterator();
		while (eParameters.hasNext()) {
			Variable eVariable = eParameters.next();
			if (eVariable.getType() != null) {
				text.append(ASTUtils.serialize(eVariable.getType()));
			}
			if (eParameters.hasNext()) {
				text.append(", "); //$NON-NLS-1$
			}
		}
		text.append(")"); //$NON-NLS-1$
		return text.toString();
	}
}
