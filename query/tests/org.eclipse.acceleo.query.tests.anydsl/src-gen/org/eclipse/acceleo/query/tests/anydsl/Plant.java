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
package org.eclipse.acceleo.query.tests.anydsl;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Plant</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Plant#getKind <em>Kind</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getPlant()
 * @model
 * @generated
 */
public interface Plant extends Source {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Kind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Kind
	 * @see #setKind(Kind)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getPlant_Kind()
	 * @model
	 * @generated
	 */
	Kind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Plant#getKind <em>Kind</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Kind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(Kind value);

} // Plant
