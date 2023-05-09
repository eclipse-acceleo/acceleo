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
package org.eclipse.acceleo.query.tests.anydsl.impl;

import org.eclipse.acceleo.query.tests.anydsl.Adress;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Country;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Adress</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl#getZipCode <em>Zip Code</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl#getCity <em>City</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl#getCountry <em>Country</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AdressImpl extends MinimalEObjectImpl.Container implements Adress {
	/**
	 * The default value of the '{@link #getZipCode() <em>Zip Code</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getZipCode()
	 * @generated
	 * @ordered
	 */
	protected static final String ZIP_CODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getZipCode() <em>Zip Code</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getZipCode()
	 * @generated
	 * @ordered
	 */
	protected String zipCode = ZIP_CODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCity() <em>City</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getCity()
	 * @generated
	 * @ordered
	 */
	protected static final String CITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCity() <em>City</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getCity()
	 * @generated
	 * @ordered
	 */
	protected String city = CITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getCountry() <em>Country</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
	protected static final Country COUNTRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCountry() <em>Country</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
	protected Country country = COUNTRY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AdressImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnydslPackage.Literals.ADRESS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setZipCode(String newZipCode) {
		String oldZipCode = zipCode;
		zipCode = newZipCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.ADRESS__ZIP_CODE, oldZipCode,
					zipCode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getCity() {
		return city;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCity(String newCity) {
		String oldCity = city;
		city = newCity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.ADRESS__CITY, oldCity, city));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCountry(Country newCountry) {
		Country oldCountry = country;
		country = newCountry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.ADRESS__COUNTRY, oldCountry,
					country));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnydslPackage.ADRESS__ZIP_CODE:
				return getZipCode();
			case AnydslPackage.ADRESS__CITY:
				return getCity();
			case AnydslPackage.ADRESS__COUNTRY:
				return getCountry();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AnydslPackage.ADRESS__ZIP_CODE:
				setZipCode((String)newValue);
				return;
			case AnydslPackage.ADRESS__CITY:
				setCity((String)newValue);
				return;
			case AnydslPackage.ADRESS__COUNTRY:
				setCountry((Country)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AnydslPackage.ADRESS__ZIP_CODE:
				setZipCode(ZIP_CODE_EDEFAULT);
				return;
			case AnydslPackage.ADRESS__CITY:
				setCity(CITY_EDEFAULT);
				return;
			case AnydslPackage.ADRESS__COUNTRY:
				setCountry(COUNTRY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AnydslPackage.ADRESS__ZIP_CODE:
				return ZIP_CODE_EDEFAULT == null ? zipCode != null : !ZIP_CODE_EDEFAULT.equals(zipCode);
			case AnydslPackage.ADRESS__CITY:
				return CITY_EDEFAULT == null ? city != null : !CITY_EDEFAULT.equals(city);
			case AnydslPackage.ADRESS__COUNTRY:
				return COUNTRY_EDEFAULT == null ? country != null : !COUNTRY_EDEFAULT.equals(country);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (zipCode: ");
		result.append(zipCode);
		result.append(", city: ");
		result.append(city);
		result.append(", country: ");
		result.append(country);
		result.append(')');
		return result.toString();
	}

} // AdressImpl
