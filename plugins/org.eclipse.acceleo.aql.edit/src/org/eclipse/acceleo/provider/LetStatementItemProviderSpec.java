/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.provider;

import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.provider.utils.ASTUtils;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the LetStatementItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class LetStatementItemProviderSpec extends LetStatementItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory the adapter factory
	 */
	public LetStatementItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.LetStatementItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		final String res;

		if (!((LetStatement) object).getVariables().isEmpty()) {
			Binding binding = ((LetStatement) object).getVariables().get(0);
			if (binding.getInitExpression() != null) {
				res = binding.getName() + " : " + ASTUtils.serialize(binding.getInitExpression());
			} else {
				res = binding.getName();
			}
		} else {
			res = super.getText(object);
		}

		return res;
	}
}
