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
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Query Validation Result Expectation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation#getExpectedResult <em>
 * Expected Result</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryValidationResultExpectation()
 * @model
 * @generated
 */
public interface QueryValidationResultExpectation extends Expectation {
	/**
	 * Returns the value of the '<em><b>Expected Result</b></em>' containment reference. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Expected Result</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expected Result</em>' containment reference.
	 * @see #setExpectedResult(QueryValidationResult)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryValidationResultExpectation_ExpectedResult()
	 * @model containment="true" required="true"
	 * @generated
	 */
	QueryValidationResult getExpectedResult();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation#getExpectedResult
	 * <em>Expected Result</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Expected Result</em>' containment reference.
	 * @see #getExpectedResult()
	 * @generated
	 */
	void setExpectedResult(QueryValidationResult value);

} // QueryValidationResultExpectation
