/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Collection Type Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.CollectionTypeLiteral#getElementType <em>Element Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getCollectionTypeLiteral()
 * @model
 * @generated
 */
public interface CollectionTypeLiteral extends ClassTypeLiteral {
	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Element Type</em>' containment reference.
	 * @see #setElementType(TypeLiteral)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getCollectionTypeLiteral_ElementType()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TypeLiteral getElementType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.CollectionTypeLiteral#getElementType
	 * <em>Element Type</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Element Type</em>' containment reference.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(TypeLiteral value);

} // CollectionTypeLiteral
