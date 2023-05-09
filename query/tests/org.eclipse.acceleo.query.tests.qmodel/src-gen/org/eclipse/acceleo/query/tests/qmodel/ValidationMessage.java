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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Validation Message</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getSeverity <em>Severity</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getMessage <em>Message</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getStartPosition <em>Start Position
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getEndPosition <em>End Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getValidationMessage()
 * @model
 * @generated
 */
public interface ValidationMessage extends EObject {
	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Severity}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Severity
	 * @see #setSeverity(Severity)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getValidationMessage_Severity()
	 * @model required="true"
	 * @generated
	 */
	Severity getSeverity();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getSeverity
	 * <em>Severity</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Severity
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(Severity value);

	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getValidationMessage_Message()
	 * @model required="true"
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getMessage
	 * <em>Message</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Position</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start Position</em>' attribute.
	 * @see #setStartPosition(int)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getValidationMessage_StartPosition()
	 * @model required="true"
	 * @generated
	 */
	int getStartPosition();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getStartPosition
	 * <em>Start Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start Position</em>' attribute.
	 * @see #getStartPosition()
	 * @generated
	 */
	void setStartPosition(int value);

	/**
	 * Returns the value of the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Position</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End Position</em>' attribute.
	 * @see #setEndPosition(int)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getValidationMessage_EndPosition()
	 * @model required="true"
	 * @generated
	 */
	int getEndPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getEndPosition
	 * <em>End Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Position</em>' attribute.
	 * @see #getEndPosition()
	 * @generated
	 */
	void setEndPosition(int value);

} // ValidationMessage
