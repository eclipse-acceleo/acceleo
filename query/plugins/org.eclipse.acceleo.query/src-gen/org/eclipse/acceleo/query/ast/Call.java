/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Call</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.Call#getServiceName <em>Service Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Call#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Call#getArguments <em>Arguments</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Call#isSuperCall <em>Super Call</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getCall()
 * @model
 * @generated
 */
public interface Call extends Expression {
	/**
	 * Returns the value of the '<em><b>Service Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Service Name</em>' attribute.
	 * @see #setServiceName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getCall_ServiceName()
	 * @model
	 * @generated
	 */
	String getServiceName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Call#getServiceName <em>Service Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Service Name</em>' attribute.
	 * @see #getServiceName()
	 * @generated
	 */
	void setServiceName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.ast.CallType}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.acceleo.query.ast.CallType
	 * @see #setType(CallType)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getCall_Type()
	 * @model
	 * @generated
	 */
	CallType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Call#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.acceleo.query.ast.CallType
	 * @see #getType()
	 * @generated
	 */
	void setType(CallType value);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.query.ast.Expression}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getCall_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getArguments();

	/**
	 * Returns the value of the '<em><b>Super Call</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Super Call</em>' attribute.
	 * @see #setSuperCall(boolean)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getCall_SuperCall()
	 * @model
	 * @generated
	 */
	boolean isSuperCall();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Call#isSuperCall <em>Super Call</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Super Call</em>' attribute.
	 * @see #isSuperCall()
	 * @generated
	 */
	void setSuperCall(boolean value);

} // Call
