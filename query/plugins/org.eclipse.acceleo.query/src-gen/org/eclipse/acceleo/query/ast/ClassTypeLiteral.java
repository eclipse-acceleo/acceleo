/**
 *  Copyright (c) 2015, 2021 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Class Type Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.ClassTypeLiteral#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getClassTypeLiteral()
 * @model
 * @generated
 */
public interface ClassTypeLiteral extends TypeLiteral {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Class)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getClassTypeLiteral_Value()
	 * @model dataType="org.eclipse.acceleo.query.ast.JavaClass" required="true"
	 * @generated
	 */
	Class getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.ClassTypeLiteral#getValue <em>Value</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Class value);

} // ClassTypeLiteral
