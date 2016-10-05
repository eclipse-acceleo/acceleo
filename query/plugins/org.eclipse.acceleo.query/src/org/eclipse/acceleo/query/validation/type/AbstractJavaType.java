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

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Abstract implementation of {@link IJavaType}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractJavaType extends AbstractType implements IJavaType {

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 */
	public AbstractJavaType(IReadOnlyQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IType#isAssignableFrom(org.eclipse.acceleo.query.validation.type.IType)
	 */
	@Override
	public boolean isAssignableFrom(IType otherType) {
		final boolean result;

		if (otherType instanceof IJavaType) {
			result = isAssignableFrom(getType(), ((IJavaType)otherType).getType());
		} else if (otherType instanceof EClassifierType) {
			final Class<?> otherClass = queryEnvironment.getEPackageProvider().getClass(
					((EClassifierType)otherType).getType());
			if (otherClass != null) {
				result = isAssignableFrom(getType(), otherClass);
			} else {
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

}
