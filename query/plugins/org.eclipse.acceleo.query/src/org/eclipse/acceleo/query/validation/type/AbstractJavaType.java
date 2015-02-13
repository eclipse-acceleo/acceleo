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
 * Abstract implementation of {@link IJavaType}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractJavaType implements IJavaType {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IType#isAssignableFrom(org.eclipse.acceleo.query.validation.type.IType)
	 */
	@Override
	public boolean isAssignableFrom(IType otherType) {
		final boolean result;

		if (otherType instanceof ClassType) {
			result = getType().isAssignableFrom(((ClassType)otherType).getType());
		} else if (otherType instanceof EClassifierType) {
			final Class<?> otherClass = ((EClassifierType)otherType).getType().getInstanceClass();
			if (otherClass != null) {
				result = getType().isAssignableFrom(otherClass);
			} else {
				result = false;
			}
		} else {
			result = false;
		}

		return result;
	}

}
