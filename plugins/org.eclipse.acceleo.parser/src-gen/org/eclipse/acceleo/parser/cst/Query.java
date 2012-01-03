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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Query</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.Query#getParameter <em>Parameter</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Query#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Query#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getQuery()
 * @model
 * @generated
 * @since 3.0
 */
public interface Query extends ModuleElement {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.parser.cst.Variable}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getQuery_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getParameter();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getQuery_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Query#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getQuery_Expression()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Query#getExpression <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(ModelExpression value);

} // Query
