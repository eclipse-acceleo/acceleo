/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.Set;

import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Keeps track of <code>null</code> value {@link IType} for further resolution.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class NullValue {

	/**
	 * The <code>null</code> value possible {@link IType}.
	 */
	private final Set<IType> types;

	/**
	 * The upper {@link IType}.
	 */
	private IType type;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the <code>null</code> value possible {@link IType}
	 */
	public NullValue(Set<IType> types) {
		this.types = types;
	}

	/**
	 * @return the type
	 */
	public IType getType() {
		if (type == null) {
			type = types.iterator().next();
			for (IType t : types) {
				if (t.isAssignableFrom(type)) {
					type = t;
				}
			}
		}
		return type;
	}

}
