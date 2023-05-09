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
		} else if (getType() instanceof EClass) {
			if (otherType.getType() instanceof EClass) {
				result = getType() == otherType.getType() || getType() == EcorePackage.eINSTANCE.getEObject()
						|| queryEnvironment.getEPackageProvider().getAllSubTypes((EClass)getType()).contains(
								otherType.getType());
			} else if (otherType.getType() instanceof Class<?>) {
				result = emfIsAssignableFrom(otherType);
			} else {
				result = javaIsAssignableFrom(otherType);
			}
		} else {
			result = javaIsAssignableFrom(otherType);
		}
		return result;
	}

	/**
	 * Tells if the given {@link IType} is assignable using {@link EClass}.
	 * 
	 * @param otherType
	 *            the {@link IType}
	 * @return <code>true</code> if the given {@link IType} is assignable using {@link EClass},
	 *         <code>false</code> otherwise
	 */
	private boolean emfIsAssignableFrom(IType otherType) {
		final boolean result;
		final Set<EClassifier> eClasses = queryEnvironment.getEPackageProvider().getEClassifiers(
				(Class<?>)otherType.getType());
		if (eClasses != null) {
			boolean compatible = false;
			for (EClassifier eCls : eClasses) {
				if (getType() == eCls || getType() == EcorePackage.eINSTANCE.getEObject() || queryEnvironment
						.getEPackageProvider().getAllSubTypes((EClass)getType()).contains(eCls)) {
					compatible = true;
					break;
				}
			}
			result = compatible;
		} else {
			result = javaIsAssignableFrom(otherType);
		}
		return result;
	}

	/**
	 * Tells if the given {@link IType} is assignable using {@link Class}.
	 * 
	 * @param otherType
	 *            the {@link IType}
	 * @return <code>true</code> if the given {@link IType} is assignable using {@link Class},
	 *         <code>false</code> otherwise
	 */
	private boolean javaIsAssignableFrom(IType otherType) {
		final boolean result;
		final Class<?> ourClass = queryEnvironment.getEPackageProvider().getClass(getType());
		final Class<?> otherClass;
		if (otherType instanceof EClassifierType) {
			otherClass = queryEnvironment.getEPackageProvider().getClass(((EClassifierType)otherType)
					.getType());
		} else if (otherType instanceof IJavaType) {
			otherClass = ((IJavaType)otherType).getType();
		} else {
			otherClass = null;
		}
		result = isAssignableFrom(ourClass, otherClass);
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
