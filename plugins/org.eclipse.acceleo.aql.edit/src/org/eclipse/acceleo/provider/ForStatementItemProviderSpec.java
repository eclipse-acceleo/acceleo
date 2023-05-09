/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.provider;

import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.provider.utils.ASTUtils;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the ForStatementItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ForStatementItemProviderSpec extends ForStatementItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory the adapter factory
	 */
	public ForStatementItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.ForStatementItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		StringBuilder res = new StringBuilder();

		ForStatement forStatement = (ForStatement) object;
		if (forStatement.getBinding() != null) {
			res.append(forStatement.getBinding().getName());
			if (forStatement.getBinding().getType() != null) {
				res.append(" : "); //$NON-NLS-1$
				res.append(ASTUtils.serialize(forStatement.getBinding().getType()));
			}
			if (forStatement.getBinding().getInitExpression() != null) {
				res.append(" | "); //$NON-NLS-1$
				res.append(ASTUtils.serialize(forStatement.getBinding().getInitExpression()));
			}
		}

		return res.toString();
	}
}
