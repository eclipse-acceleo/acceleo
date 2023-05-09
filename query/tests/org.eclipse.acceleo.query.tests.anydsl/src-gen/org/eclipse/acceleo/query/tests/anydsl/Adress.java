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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Adress</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Adress#getZipCode <em>Zip Code</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Adress#getCity <em>City</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Adress#getCountry <em>Country</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getAdress()
 * @model
 * @generated
 */
public interface Adress extends EObject {
	/**
	 * Returns the value of the '<em><b>Zip Code</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Zip Code</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Zip Code</em>' attribute.
	 * @see #setZipCode(String)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getAdress_ZipCode()
	 * @model
	 * @generated
	 */
	String getZipCode();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Adress#getZipCode
	 * <em>Zip Code</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Zip Code</em>' attribute.
	 * @see #getZipCode()
	 * @generated
	 */
	void setZipCode(String value);

	/**
	 * Returns the value of the '<em><b>City</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>City</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>City</em>' attribute.
	 * @see #setCity(String)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getAdress_City()
	 * @model
	 * @generated
	 */
	String getCity();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Adress#getCity <em>City</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>City</em>' attribute.
	 * @see #getCity()
	 * @generated
	 */
	void setCity(String value);

	/**
	 * Returns the value of the '<em><b>Country</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Country</em>' attribute.
	 * @see #setCountry(Country)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getAdress_Country()
	 * @model dataType="org.eclipse.acceleo.query.tests.anydsl.CountryData"
	 * @generated
	 */
	Country getCountry();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Adress#getCountry
	 * <em>Country</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Country</em>' attribute.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(Country value);

} // Adress
