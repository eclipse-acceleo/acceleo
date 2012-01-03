/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.cst;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Variable</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.Variable#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Variable#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getVariable()
 * @model
 * @generated
 * @since 3.0
 */
public interface Variable extends CSTNode {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getVariable_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Variable#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getVariable_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Variable#getType <em>Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Init Expression</b></em>' containment reference. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Init Expression</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Init Expression</em>' containment reference.
	 * @see #setInitExpression(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getVariable_InitExpression()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getInitExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Variable#getInitExpression
	 * <em>Init Expression</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Init Expression</em>' containment reference.
	 * @see #getInitExpression()
	 * @generated
	 */
	void setInitExpression(ModelExpression value);

} // Variable
