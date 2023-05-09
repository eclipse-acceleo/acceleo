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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Chef</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Chef#getAdress <em>Adress</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Chef#getRecipes <em>Recipes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getChef()
 * @model
 * @generated
 */
public interface Chef extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Adress</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adress</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Adress</em>' containment reference.
	 * @see #setAdress(Adress)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getChef_Adress()
	 * @model containment="true"
	 * @generated
	 */
	Adress getAdress();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Chef#getAdress <em>Adress</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Adress</em>' containment reference.
	 * @see #getAdress()
	 * @generated
	 */
	void setAdress(Adress value);

	/**
	 * Returns the value of the '<em><b>Recipes</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.query.tests.anydsl.Recipe}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recipes</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Recipes</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getChef_Recipes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Recipe> getRecipes();

} // Chef
