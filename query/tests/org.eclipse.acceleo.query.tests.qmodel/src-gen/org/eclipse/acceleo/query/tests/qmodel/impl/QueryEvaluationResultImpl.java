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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Query Evaluation Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultImpl#getInterpreter <em>
 * Interpreter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class QueryEvaluationResultImpl extends MinimalEObjectImpl.Container implements QueryEvaluationResult {
	/**
	 * The default value of the '{@link #getInterpreter() <em>Interpreter</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInterpreter()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERPRETER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInterpreter() <em>Interpreter</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getInterpreter()
	 * @generated
	 * @ordered
	 */
	protected String interpreter = INTERPRETER_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected QueryEvaluationResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QmodelPackage.Literals.QUERY_EVALUATION_RESULT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getInterpreter() {
		return interpreter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInterpreter(String newInterpreter) {
		String oldInterpreter = interpreter;
		interpreter = newInterpreter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					QmodelPackage.QUERY_EVALUATION_RESULT__INTERPRETER, oldInterpreter, interpreter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QmodelPackage.QUERY_EVALUATION_RESULT__INTERPRETER:
				return getInterpreter();
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
			case QmodelPackage.QUERY_EVALUATION_RESULT__INTERPRETER:
				setInterpreter((String)newValue);
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
			case QmodelPackage.QUERY_EVALUATION_RESULT__INTERPRETER:
				setInterpreter(INTERPRETER_EDEFAULT);
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
			case QmodelPackage.QUERY_EVALUATION_RESULT__INTERPRETER:
				return INTERPRETER_EDEFAULT == null ? interpreter != null : !INTERPRETER_EDEFAULT
						.equals(interpreter);
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
		result.append(" (interpreter: ");
		result.append(interpreter);
		result.append(')');
		return result.toString();
	}

} // QueryEvaluationResultImpl
