/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.validation.type;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

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
				// instances will be dynamic DynamicEObjectImpl
				// this suppose All dynamic instances of EClass are EObjects
				result = getType() == EObject.class && ((EClassifierType)otherType)
						.getType() instanceof EClass;
			}
		} else {
			result = false;
		}

		return result;
	}

}
