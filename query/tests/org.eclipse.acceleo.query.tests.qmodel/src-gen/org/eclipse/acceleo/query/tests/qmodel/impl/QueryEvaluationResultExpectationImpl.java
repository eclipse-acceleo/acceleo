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
package org.eclipse.acceleo.query.tests.qmodel.impl;

import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Query Evaluation Result Expectation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultExpectationImpl#getExpectedResult
 * <em>Expected Result</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QueryEvaluationResultExpectationImpl extends ExpectationImpl implements QueryEvaluationResultExpectation {
	/**
	 * The cached value of the '{@link #getExpectedResult() <em>Expected Result</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpectedResult()
	 * @generated
	 * @ordered
	 */
	protected QueryEvaluationResult expectedResult;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected QueryEvaluationResultExpectationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QueryEvaluationResult getExpectedResult() {
		return expectedResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpectedResult(QueryEvaluationResult newExpectedResult,
			NotificationChain msgs) {
		QueryEvaluationResult oldExpectedResult = expectedResult;
		expectedResult = newExpectedResult;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT, oldExpectedResult,
					newExpectedResult);
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
	public void setExpectedResult(QueryEvaluationResult newExpectedResult) {
		if (newExpectedResult != expectedResult) {
			NotificationChain msgs = null;
			if (expectedResult != null)
				msgs = ((InternalEObject)expectedResult).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT, null, msgs);
			if (newExpectedResult != null)
				msgs = ((InternalEObject)newExpectedResult).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT, null, msgs);
			msgs = basicSetExpectedResult(newExpectedResult, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT, newExpectedResult,
					newExpectedResult));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT:
				return basicSetExpectedResult(null, msgs);
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
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT:
				return getExpectedResult();
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
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT:
				setExpectedResult((QueryEvaluationResult)newValue);
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
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT:
				setExpectedResult((QueryEvaluationResult)null);
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
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT:
				return expectedResult != null;
		}
		return super.eIsSet(featureID);
	}

} // QueryEvaluationResultExpectationImpl
