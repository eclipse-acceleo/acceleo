/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.query.tests.nestedpackages.root.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder;
import org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity Holder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.query.tests.nestedpackages.root.impl.EntityHolderImpl#getEntityInterfaces <em>Entity Interfaces</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntityHolderImpl extends MinimalEObjectImpl.Container implements EntityHolder {
	/**
	 * The cached value of the '{@link #getEntityInterfaces() <em>Entity Interfaces</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityInterfaces()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityInterface> entityInterfaces;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityHolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RootPackage.Literals.ENTITY_HOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EntityInterface> getEntityInterfaces() {
		if (entityInterfaces == null) {
			entityInterfaces = new EObjectContainmentEList<EntityInterface>(EntityInterface.class, this, RootPackage.ENTITY_HOLDER__ENTITY_INTERFACES);
		}
		return entityInterfaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RootPackage.ENTITY_HOLDER__ENTITY_INTERFACES:
				return ((InternalEList<?>)getEntityInterfaces()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RootPackage.ENTITY_HOLDER__ENTITY_INTERFACES:
				return getEntityInterfaces();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RootPackage.ENTITY_HOLDER__ENTITY_INTERFACES:
				getEntityInterfaces().clear();
				getEntityInterfaces().addAll((Collection<? extends EntityInterface>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RootPackage.ENTITY_HOLDER__ENTITY_INTERFACES:
				getEntityInterfaces().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RootPackage.ENTITY_HOLDER__ENTITY_INTERFACES:
				return entityInterfaces != null && !entityInterfaces.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EntityHolderImpl
