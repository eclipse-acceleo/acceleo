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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>EObject Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.EObjectResult#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getEObjectResult()
 * @model
 * @generated
 */
public interface EObjectResult extends QueryEvaluationResult {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(EObject)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getEObjectResult_Value()
	 * @model required="true"
	 * @generated
	 */
	EObject getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.EObjectResult#getValue
	 * <em>Value</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(EObject value);

} // EObjectResult
