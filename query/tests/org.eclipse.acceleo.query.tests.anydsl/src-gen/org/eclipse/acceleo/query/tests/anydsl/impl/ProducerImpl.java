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

import java.util.Collection;

import org.eclipse.acceleo.query.tests.anydsl.Adress;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Company;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.Producer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Producer</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl#getAdress <em>Adress</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl#getCompany <em>Company</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl#getFoods <em>Foods</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProducerImpl extends MinimalEObjectImpl.Container implements Producer {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAdress() <em>Adress</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAdress()
	 * @generated
	 * @ordered
	 */
	protected Adress adress;

	/**
	 * The cached value of the '{@link #getCompany() <em>Company</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCompany()
	 * @generated
	 * @ordered
	 */
	protected Company company;

	/**
	 * The cached value of the '{@link #getFoods() <em>Foods</em>}' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFoods()
	 * @generated
	 * @ordered
	 */
	protected EList<Food> foods;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProducerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnydslPackage.Literals.PRODUCER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.PRODUCER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Adress getAdress() {
		return adress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAdress(Adress newAdress, NotificationChain msgs) {
		Adress oldAdress = adress;
		adress = newAdress;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AnydslPackage.PRODUCER__ADRESS, oldAdress, newAdress);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAdress(Adress newAdress) {
		if (newAdress != adress) {
			NotificationChain msgs = null;
			if (adress != null)
				msgs = ((InternalEObject)adress).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AnydslPackage.PRODUCER__ADRESS, null, msgs);
			if (newAdress != null)
				msgs = ((InternalEObject)newAdress).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AnydslPackage.PRODUCER__ADRESS, null, msgs);
			msgs = basicSetAdress(newAdress, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.PRODUCER__ADRESS, newAdress,
					newAdress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Company getCompany() {
		if (company != null && company.eIsProxy()) {
			InternalEObject oldCompany = (InternalEObject)company;
			company = (Company)eResolveProxy(oldCompany);
			if (company != oldCompany) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AnydslPackage.PRODUCER__COMPANY, oldCompany, company));
			}
		}
		return company;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Company basicGetCompany() {
		return company;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCompany(Company newCompany) {
		Company oldCompany = company;
		company = newCompany;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.PRODUCER__COMPANY,
					oldCompany, company));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Food> getFoods() {
		if (foods == null) {
			foods = new EObjectWithInverseResolvingEList.ManyInverse<Food>(Food.class, this,
					AnydslPackage.PRODUCER__FOODS, AnydslPackage.FOOD__PRODUCERS);
		}
		return foods;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnydslPackage.PRODUCER__FOODS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getFoods())
						.basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnydslPackage.PRODUCER__ADRESS:
				return basicSetAdress(null, msgs);
			case AnydslPackage.PRODUCER__FOODS:
				return ((InternalEList<?>)getFoods()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AnydslPackage.PRODUCER__NAME:
				return getName();
			case AnydslPackage.PRODUCER__ADRESS:
				return getAdress();
			case AnydslPackage.PRODUCER__COMPANY:
				if (resolve)
					return getCompany();
				return basicGetCompany();
			case AnydslPackage.PRODUCER__FOODS:
				return getFoods();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AnydslPackage.PRODUCER__NAME:
				setName((String)newValue);
				return;
			case AnydslPackage.PRODUCER__ADRESS:
				setAdress((Adress)newValue);
				return;
			case AnydslPackage.PRODUCER__COMPANY:
				setCompany((Company)newValue);
				return;
			case AnydslPackage.PRODUCER__FOODS:
				getFoods().clear();
				getFoods().addAll((Collection<? extends Food>)newValue);
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
			case AnydslPackage.PRODUCER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AnydslPackage.PRODUCER__ADRESS:
				setAdress((Adress)null);
				return;
			case AnydslPackage.PRODUCER__COMPANY:
				setCompany((Company)null);
				return;
			case AnydslPackage.PRODUCER__FOODS:
				getFoods().clear();
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
			case AnydslPackage.PRODUCER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AnydslPackage.PRODUCER__ADRESS:
				return adress != null;
			case AnydslPackage.PRODUCER__COMPANY:
				return company != null;
			case AnydslPackage.PRODUCER__FOODS:
				return foods != null && !foods.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // ProducerImpl
