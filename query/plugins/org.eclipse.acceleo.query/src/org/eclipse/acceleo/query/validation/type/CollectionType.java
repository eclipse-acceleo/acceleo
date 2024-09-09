/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

import java.util.Collection;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Abstract collection type implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CollectionType extends AbstractJavaType implements ICollectionType {

	/**
	 * The {@link IType}.
	 */
	private final IType type;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link IType}
	 */
	public CollectionType(IReadOnlyQueryEnvironment queryEnvironment, IType type) {
		super(queryEnvironment);
		this.type = type;
	}

	public IType getCollectionType() {
		return type;
	}

	@Override
	public boolean isAssignableFrom(IType otherType) {
		final boolean res;

		if (super.isAssignableFrom(otherType)) {
			if (otherType instanceof ICollectionType) {
				res = getCollectionType().isAssignableFrom(((ICollectionType)otherType).getCollectionType());
			} else {
				// not enough information, assume everything is OK.
				res = true;
			}
		} else {
			res = false;
		}

		return res;
	}

	@Override
	public int hashCode() {
		return getType().hashCode() ^ getCollectionType().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass() && ((CollectionType)obj).getType().equals(getType())
				&& ((CollectionType)obj).getCollectionType().equals(getCollectionType());
	}

	@Override
	public Class<?> getType() {
		return Collection.class;
	}

	@Override
	public String toString() {
		return "Collection(" + getCollectionType().toString() + ")";
	}

}
