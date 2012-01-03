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

import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the IfBlockItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class IfBlockItemProviderSpec extends IfBlockItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public IfBlockItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.IfBlockItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		if (((IfBlock)object).getIfExpr() != null) {
			return "[if/] " + ((IfBlock)object).getIfExpr().toString(); //$NON-NLS-1$
		}
		return super.getText(object);
	}
}
