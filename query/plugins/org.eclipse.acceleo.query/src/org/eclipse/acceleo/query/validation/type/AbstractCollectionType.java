/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

/**
 * Abstract collection type implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractCollectionType implements ICollectionType {

	/**
	 * The {@link IType}.
	 */
	private final IType type;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the {@link IType}
	 */
	public AbstractCollectionType(IType type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.ICollectionType#getCollectionType()
	 */
	public IType getCollectionType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getType().hashCode() ^ getCollectionType().hashCode();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass() && ((AbstractCollectionType)obj).getType().equals(getType())
				&& ((AbstractCollectionType)obj).getCollectionType().equals(getCollectionType());
	}

}