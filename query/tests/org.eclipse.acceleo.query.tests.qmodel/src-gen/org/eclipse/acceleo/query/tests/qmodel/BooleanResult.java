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
package org.eclipse.acceleo.query.tests.qmodel;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Boolean Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.BooleanResult#isValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getBooleanResult()
 * @model
 * @generated
 */
public interface BooleanResult extends QueryEvaluationResult {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(boolean)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getBooleanResult_Value()
	 * @model required="true"
	 * @generated
	 */
	boolean isValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.BooleanResult#isValue
	 * <em>Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #isValue()
	 * @generated
	 */
	void setValue(boolean value);

} // BooleanResult
