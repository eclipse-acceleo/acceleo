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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * {@link EClassifier} validation type.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EClassifierType extends AbstractType {

	/**
	 * The {@link EClassifier}.
	 */
	private final EClassifier type;

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link EClassifier}
	 */
	public EClassifierType(IReadOnlyQueryEnvironment queryEnvironment, EClassifier type) {
		this.queryEnvironment = queryEnvironment;
		this.type = type;
	}

	/**
	 * Gets the {@link EClassifier}.
	 * 
	 * @return the {@link EClassifier}
	 */
	public EClassifier getType() {
		return type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.validation.type.IType#isAssignableFrom(org.eclipse.acceleo.query.validation.type.IType)
	 */
	@Override
	public boolean isAssignableFrom(IType otherType) {
		final boolean result;

		if (!queryEnvironment.getEPackageProvider().isRegistered(type)) {
			// This should not happen since the EClassifier must be registered for parsing
			result = false;
		} else if (getType() instanceof EClass && otherType.getType() instanceof EClass) {
			result = getType() == otherType.getType()
					|| getType() == EcorePackage.eINSTANCE.getEObject()
					|| queryEnvironment.getEPackageProvider().getAllSubTypes((EClass)getType()).contains(
							otherType.getType());
		} else {
			final Class<?> ourClass = queryEnvironment.getEPackageProvider().getClass(getType());
			final Class<?> otherClass;
			if (otherType instanceof EClassifierType) {
				otherClass = queryEnvironment.getEPackageProvider().getClass(
						((EClassifierType)otherType).getType());
			} else if (otherType instanceof IJavaType) {
				otherClass = ((IJavaType)otherType).getType();
			} else {
				otherClass = null;
			}
			result = isAssignableFrom(ourClass, otherClass);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EClassifier=" + type.getName();
	}

}
