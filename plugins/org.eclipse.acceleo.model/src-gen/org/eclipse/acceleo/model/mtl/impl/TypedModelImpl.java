/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.impl;

import java.util.Collection;

import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Typed Model</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TypedModelImpl#getTakesTypesFrom <em>Takes Types From</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TypedModelImpl extends EObjectImpl implements TypedModel {
	/**
	 * The cached value of the '{@link #getTakesTypesFrom() <em>Takes Types From</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTakesTypesFrom()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> takesTypesFrom;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypedModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.TYPED_MODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EPackage> getTakesTypesFrom() {
		if (takesTypesFrom == null) {
			takesTypesFrom = new EObjectResolvingEList<EPackage>(EPackage.class, this,
					MtlPackage.TYPED_MODEL__TAKES_TYPES_FROM);
		}
		return takesTypesFrom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MtlPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				return getTakesTypesFrom();
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
			case MtlPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				getTakesTypesFrom().clear();
				getTakesTypesFrom().addAll((Collection<? extends EPackage>)newValue);
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
			case MtlPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				getTakesTypesFrom().clear();
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
			case MtlPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				return takesTypesFrom != null && !takesTypesFrom.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // TypedModelImpl
