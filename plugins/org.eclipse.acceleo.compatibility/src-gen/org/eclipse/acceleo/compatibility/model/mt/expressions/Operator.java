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
package org.eclipse.acceleo.compatibility.model.mt.expressions;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Operator</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperator <em>Operator</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperands <em>Operands</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getOperator()
 * @model
 * @generated
 */
public interface Operator extends Expression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see #setOperator(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getOperator_Operator()
	 * @model required="true"
	 * @generated
	 */
	String getOperator();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperator <em>Operator</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Operator</em>' attribute.
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(String value);

	/**
	 * Returns the value of the '<em><b>Operands</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Expression}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Operands</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operands</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getOperator_Operands()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<Expression> getOperands();

} // Operator
