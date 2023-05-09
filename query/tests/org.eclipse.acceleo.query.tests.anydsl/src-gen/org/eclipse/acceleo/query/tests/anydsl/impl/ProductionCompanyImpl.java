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
import org.eclipse.acceleo.query.tests.anydsl.Producer;
import org.eclipse.acceleo.query.tests.anydsl.ProductionCompany;
import org.eclipse.acceleo.query.tests.anydsl.World;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Production Company</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl#getAdress <em>Adress</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl#getWorld <em>World</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl#getProducers <em>Producers
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProductionCompanyImpl extends MinimalEObjectImpl.Container implements ProductionCompany {
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
	 * The cached value of the '{@link #getWorld() <em>World</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getWorld()
	 * @generated
	 * @ordered
	 */
	protected World world;

	/**
	 * The cached value of the '{@link #getProducers() <em>Producers</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProducers()
	 * @generated
	 * @ordered
	 */
	protected EList<Producer> producers;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProductionCompanyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnydslPackage.Literals.PRODUCTION_COMPANY;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.PRODUCTION_COMPANY__NAME,
					oldName, name));
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
					AnydslPackage.PRODUCTION_COMPANY__ADRESS, oldAdress, newAdress);
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
						- AnydslPackage.PRODUCTION_COMPANY__ADRESS, null, msgs);
			if (newAdress != null)
				msgs = ((InternalEObject)newAdress).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AnydslPackage.PRODUCTION_COMPANY__ADRESS, null, msgs);
			msgs = basicSetAdress(newAdress, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.PRODUCTION_COMPANY__ADRESS,
					newAdress, newAdress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public World getWorld() {
		if (world != null && world.eIsProxy()) {
			InternalEObject oldWorld = (InternalEObject)world;
			world = (World)eResolveProxy(oldWorld);
			if (world != oldWorld) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AnydslPackage.PRODUCTION_COMPANY__WORLD, oldWorld, world));
			}
		}
		return world;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public World basicGetWorld() {
		return world;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setWorld(World newWorld) {
		World oldWorld = world;
		world = newWorld;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnydslPackage.PRODUCTION_COMPANY__WORLD,
					oldWorld, world));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Producer> getProducers() {
		if (producers == null) {
			producers = new EObjectContainmentEList<Producer>(Producer.class, this,
					AnydslPackage.PRODUCTION_COMPANY__PRODUCERS);
		}
		return producers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnydslPackage.PRODUCTION_COMPANY__ADRESS:
				return basicSetAdress(null, msgs);
			case AnydslPackage.PRODUCTION_COMPANY__PRODUCERS:
				return ((InternalEList<?>)getProducers()).basicRemove(otherEnd, msgs);
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
			case AnydslPackage.PRODUCTION_COMPANY__NAME:
				return getName();
			case AnydslPackage.PRODUCTION_COMPANY__ADRESS:
				return getAdress();
			case AnydslPackage.PRODUCTION_COMPANY__WORLD:
				if (resolve)
					return getWorld();
				return basicGetWorld();
			case AnydslPackage.PRODUCTION_COMPANY__PRODUCERS:
				return getProducers();
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
			case AnydslPackage.PRODUCTION_COMPANY__NAME:
				setName((String)newValue);
				return;
			case AnydslPackage.PRODUCTION_COMPANY__ADRESS:
				setAdress((Adress)newValue);
				return;
			case AnydslPackage.PRODUCTION_COMPANY__WORLD:
				setWorld((World)newValue);
				return;
			case AnydslPackage.PRODUCTION_COMPANY__PRODUCERS:
				getProducers().clear();
				getProducers().addAll((Collection<? extends Producer>)newValue);
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
			case AnydslPackage.PRODUCTION_COMPANY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AnydslPackage.PRODUCTION_COMPANY__ADRESS:
				setAdress((Adress)null);
				return;
			case AnydslPackage.PRODUCTION_COMPANY__WORLD:
				setWorld((World)null);
				return;
			case AnydslPackage.PRODUCTION_COMPANY__PRODUCERS:
				getProducers().clear();
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
			case AnydslPackage.PRODUCTION_COMPANY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AnydslPackage.PRODUCTION_COMPANY__ADRESS:
				return adress != null;
			case AnydslPackage.PRODUCTION_COMPANY__WORLD:
				return world != null;
			case AnydslPackage.PRODUCTION_COMPANY__PRODUCERS:
				return producers != null && !producers.isEmpty();
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

} // ProductionCompanyImpl
