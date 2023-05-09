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

import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.provider.utils.ASTUtils;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the ExpressionItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ExpressionItemProviderSpec extends ExpressionItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory the adapter factory
	 */
	public ExpressionItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	@Override
	public String getText(Object object) {
		final String res;

		if (object instanceof Expression) {
			res = ASTUtils.serialize((Expression) object);
		} else {
			res = super.getText(object);
		}

		return res;
	}
}
