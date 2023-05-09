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

import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Company;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.Source;
import org.eclipse.acceleo.query.tests.anydsl.World;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>World</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl#getCompanies <em>Companies</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl#getFoods <em>Foods</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl#getSources <em>Sources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorldImpl extends MinimalEObjectImpl.Container implements World {
	/**
	 * The cached value of the '{@link #getCompanies() <em>Companies</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCompanies()
	 * @generated
	 * @ordered
	 */
	protected EList<Company> companies;

	/**
	 * The cached value of the '{@link #getFoods() <em>Foods</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFoods()
	 * @generated
	 * @ordered
	 */
	protected EList<Food> foods;

	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected EList<Source> sources;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected WorldImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnydslPackage.Literals.WORLD;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Company> getCompanies() {
		if (companies == null) {
			companies = new EObjectContainmentEList<Company>(Company.class, this,
					AnydslPackage.WORLD__COMPANIES);
		}
		return companies;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Food> getFoods() {
		if (foods == null) {
			foods = new EObjectContainmentEList<Food>(Food.class, this, AnydslPackage.WORLD__FOODS);
		}
		return foods;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Source> getSources() {
		if (sources == null) {
			sources = new EObjectContainmentEList<Source>(Source.class, this, AnydslPackage.WORLD__SOURCES);
		}
		return sources;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnydslPackage.WORLD__COMPANIES:
				return ((InternalEList<?>)getCompanies()).basicRemove(otherEnd, msgs);
			case AnydslPackage.WORLD__FOODS:
				return ((InternalEList<?>)getFoods()).basicRemove(otherEnd, msgs);
			case AnydslPackage.WORLD__SOURCES:
				return ((InternalEList<?>)getSources()).basicRemove(otherEnd, msgs);
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
			case AnydslPackage.WORLD__COMPANIES:
				return getCompanies();
			case AnydslPackage.WORLD__FOODS:
				return getFoods();
			case AnydslPackage.WORLD__SOURCES:
				return getSources();
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
			case AnydslPackage.WORLD__COMPANIES:
				getCompanies().clear();
				getCompanies().addAll((Collection<? extends Company>)newValue);
				return;
			case AnydslPackage.WORLD__FOODS:
				getFoods().clear();
				getFoods().addAll((Collection<? extends Food>)newValue);
				return;
			case AnydslPackage.WORLD__SOURCES:
				getSources().clear();
				getSources().addAll((Collection<? extends Source>)newValue);
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
			case AnydslPackage.WORLD__COMPANIES:
				getCompanies().clear();
				return;
			case AnydslPackage.WORLD__FOODS:
				getFoods().clear();
				return;
			case AnydslPackage.WORLD__SOURCES:
				getSources().clear();
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
			case AnydslPackage.WORLD__COMPANIES:
				return companies != null && !companies.isEmpty();
			case AnydslPackage.WORLD__FOODS:
				return foods != null && !foods.isEmpty();
			case AnydslPackage.WORLD__SOURCES:
				return sources != null && !sources.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // WorldImpl
