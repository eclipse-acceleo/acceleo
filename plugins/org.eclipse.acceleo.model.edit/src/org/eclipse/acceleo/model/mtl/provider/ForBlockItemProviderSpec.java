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

import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * Specializes the ForBlockItemProvider implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ForBlockItemProviderSpec extends ForBlockItemProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public ForBlockItemProviderSpec(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.model.mtl.provider.ForBlockItemProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object object) {
		StringBuilder res = new StringBuilder();
		ForBlock forBlock = (ForBlock)object;
		res.append("[for/] "); //$NON-NLS-1$
		if (forBlock.getLoopVariable() != null) {
			res.append(forBlock.getLoopVariable().getName());
			res.append(" : "); //$NON-NLS-1$
			res.append(forBlock.getLoopVariable().getType().getEPackage().getName());
			res.append("::"); //$NON-NLS-1$
			res.append(forBlock.getLoopVariable().getType().getName());
			if (forBlock.getIterSet() != null) {
				res.append(" | "); //$NON-NLS-1$
				res.append(forBlock.getIterSet());
				if (forBlock.getGuard() != null) {
					res.append(" ? ("); //$NON-NLS-1$
					res.append(forBlock.getGuard());
					res.append(")"); //$NON-NLS-1$
				}
			}
		}
		return res.toString();
	}
}
