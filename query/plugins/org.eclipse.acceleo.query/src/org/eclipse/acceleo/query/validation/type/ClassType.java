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
 * {@link Class} validation type.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ClassType extends AbstractJavaType implements IJavaType {

	/**
	 * The {@link Class}.
	 */
	private final Class<?> type;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            the {@link Class}
	 */
	public ClassType(Class<?> type) {
		this.type = type;
	}

	/**
	 * Gets the {@link Class}.
	 * 
	 * @return the {@link Class}
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final String result;
		if (type != null) {
			result = type.getCanonicalName();
		} else {
			result = "null";
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.AbstractType#hashCode()
	 */
	@Override
	public int hashCode() {
		final int result;

		if (type != null) {
			result = type.hashCode();
		} else {
			result = 0;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.AbstractType#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		final boolean result;

		if (type != null) {
			result = obj.getClass() == getClass() && type.equals(((ClassType)obj).getType());
		} else {
			result = obj.getClass() == getClass() && ((ClassType)obj).getType() == null;
		}

		return result;
	}

}
