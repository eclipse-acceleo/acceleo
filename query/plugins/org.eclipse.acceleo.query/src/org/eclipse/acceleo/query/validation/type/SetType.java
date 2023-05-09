/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Set validation type.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SetType extends AbstractCollectionType {

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link IType}
	 */
	public SetType(IReadOnlyQueryEnvironment queryEnvironment, IType type) {
		super(queryEnvironment, type);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IType#getType()
	 */
	@Override
	public Class<?> getType() {
		return Set.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Set(" + getCollectionType().toString() + ")";
	}

}
