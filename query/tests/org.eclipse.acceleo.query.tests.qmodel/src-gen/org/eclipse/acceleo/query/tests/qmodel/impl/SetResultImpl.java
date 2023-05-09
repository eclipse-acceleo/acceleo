/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.tests.qmodel.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.SetResult;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Set Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.SetResultImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetResultImpl extends QueryEvaluationResultImpl implements SetResult {
	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<QueryEvaluationResult> values;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SetResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QmodelPackage.Literals.SET_RESULT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<QueryEvaluationResult> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<QueryEvaluationResult>(QueryEvaluationResult.class, this,
					QmodelPackage.SET_RESULT__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QmodelPackage.SET_RESULT__VALUES:
				return ((InternalEList<?>)getValues()).basicRemove(otherEnd, msgs);
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
			case QmodelPackage.SET_RESULT__VALUES:
				return getValues();
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
			case QmodelPackage.SET_RESULT__VALUES:
				getValues().clear();
				getValues().addAll((Collection<? extends QueryEvaluationResult>)newValue);
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
			case QmodelPackage.SET_RESULT__VALUES:
				getValues().clear();
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
			case QmodelPackage.SET_RESULT__VALUES:
				return values != null && !values.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // SetResultImpl
