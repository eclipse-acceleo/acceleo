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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Enum Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.EnumLiteral#getEPackageName <em>EPackage Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.EnumLiteral#getEEnumName <em>EEnum Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.EnumLiteral#getEEnumLiteralName <em>EEnum Literal Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getEnumLiteral()
 * @model
 * @generated
 */
public interface EnumLiteral extends Literal {
	/**
	 * Returns the value of the '<em><b>EPackage Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>EPackage Name</em>' attribute.
	 * @see #setEPackageName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getEnumLiteral_EPackageName()
	 * @model required="true"
	 * @generated
	 */
	String getEPackageName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.EnumLiteral#getEPackageName <em>EPackage
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>EPackage Name</em>' attribute.
	 * @see #getEPackageName()
	 * @generated
	 */
	void setEPackageName(String value);

	/**
	 * Returns the value of the '<em><b>EEnum Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>EEnum Name</em>' attribute.
	 * @see #setEEnumName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getEnumLiteral_EEnumName()
	 * @model required="true"
	 * @generated
	 */
	String getEEnumName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.EnumLiteral#getEEnumName <em>EEnum
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>EEnum Name</em>' attribute.
	 * @see #getEEnumName()
	 * @generated
	 */
	void setEEnumName(String value);

	/**
	 * Returns the value of the '<em><b>EEnum Literal Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>EEnum Literal Name</em>' attribute.
	 * @see #setEEnumLiteralName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getEnumLiteral_EEnumLiteralName()
	 * @model required="true"
	 * @generated
	 */
	String getEEnumLiteralName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.EnumLiteral#getEEnumLiteralName <em>EEnum
	 * Literal Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>EEnum Literal Name</em>' attribute.
	 * @see #getEEnumLiteralName()
	 * @generated
	 */
	void setEEnumLiteralName(String value);

} // EnumLiteral
