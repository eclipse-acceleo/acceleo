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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Query Validation Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getInterpreter <em>Interpreter
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getPossibleTypes <em>Possible Types
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getValidationMessages <em>
 * Validation Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryValidationResult()
 * @model
 * @generated
 */
public interface QueryValidationResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interpreter</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Interpreter</em>' attribute.
	 * @see #setInterpreter(String)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryValidationResult_Interpreter()
	 * @model
	 * @generated
	 */
	String getInterpreter();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getInterpreter
	 * <em>Interpreter</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Interpreter</em>' attribute.
	 * @see #getInterpreter()
	 * @generated
	 */
	void setInterpreter(String value);

	/**
	 * Returns the value of the '<em><b>Possible Types</b></em>' attribute list. The list contents are of type
	 * {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Possible Types</em>' attribute list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Possible Types</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryValidationResult_PossibleTypes()
	 * @model
	 * @generated
	 */
	EList<String> getPossibleTypes();

	/**
	 * Returns the value of the '<em><b>Validation Messages</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Messages</em>' reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Validation Messages</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryValidationResult_ValidationMessages()
	 * @model containment="true"
	 * @generated
	 */
	EList<ValidationMessage> getValidationMessages();

} // QueryValidationResult
