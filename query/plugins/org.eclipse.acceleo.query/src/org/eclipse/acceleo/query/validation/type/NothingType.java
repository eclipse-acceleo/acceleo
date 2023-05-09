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

import org.eclipse.acceleo.query.runtime.impl.Nothing;

/**
 * Nothing validation type. Used when no type can be found.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class NothingType extends AbstractType implements IJavaType {

	/**
	 * The {@link Class}.
	 */
	private final String message;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public NothingType(String message) {
		this.message = message;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IType#getType()
	 */
	@Override
	public Class<?> getType() {
		return Nothing.class;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Nothing(" + message + ")";
	}

	/**
	 * Makes sure each nothing is unique. {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.AbstractType#hashCode()
	 */
	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	/**
	 * Makes sure each nothing is unique. {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.AbstractType#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return System.identityHashCode(this) == System.identityHashCode(obj);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IType#isAssignableFrom(org.eclipse.acceleo.query.validation.type.IType)
	 */
	@Override
	public boolean isAssignableFrom(IType type) {
		return false;
	}

}
