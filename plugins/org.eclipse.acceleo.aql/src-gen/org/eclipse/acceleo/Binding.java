/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Binding</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Binding#getInitExpression <em>Init Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getBinding()
 * @model
 * @generated
 */
public interface Binding extends Variable {
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
	 * @see #setInitExpression(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getBinding_InitExpression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getInitExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Binding#getInitExpression <em>Init Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Init Expression</em>' containment reference.
	 * @see #getInitExpression()
	 * @generated
	 */
	void setInitExpression(Expression value);

} // Binding
