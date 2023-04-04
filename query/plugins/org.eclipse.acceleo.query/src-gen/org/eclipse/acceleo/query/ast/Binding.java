/**
 *  Copyright (c) 2015, 2023 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Binding</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.Binding#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Binding#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getBinding()
 * @model
 * @generated
 */
public interface Binding extends Declaration {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(Expression)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getBinding_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Binding#getValue <em>Value</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Expression value);

} // Binding
