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

import java.io.Serializable;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Serializable Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.SerializableResult#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getSerializableResult()
 * @model
 * @generated
 */
public interface SerializableResult extends QueryEvaluationResult {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Serializable)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getSerializableResult_Value()
	 * @model dataType="org.eclipse.acceleo.query.tests.qmodel.AnySerializable" required="true"
	 * @generated
	 */
	Serializable getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.SerializableResult#getValue
	 * <em>Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Serializable value);

} // SerializableResult
