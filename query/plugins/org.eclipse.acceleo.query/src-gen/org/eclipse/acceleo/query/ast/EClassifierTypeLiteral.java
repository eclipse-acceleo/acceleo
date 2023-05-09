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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>EClassifier Type Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.EClassifierTypeLiteral#getEPackageName <em>EPackage
 * Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.EClassifierTypeLiteral#getEClassifierName <em>EClassifier
 * Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getEClassifierTypeLiteral()
 * @model
 * @generated
 */
public interface EClassifierTypeLiteral extends TypeLiteral {
	/**
	 * Returns the value of the '<em><b>EPackage Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>EPackage Name</em>' attribute.
	 * @see #setEPackageName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getEClassifierTypeLiteral_EPackageName()
	 * @model required="true"
	 * @generated
	 */
	String getEPackageName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.EClassifierTypeLiteral#getEPackageName
	 * <em>EPackage Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>EPackage Name</em>' attribute.
	 * @see #getEPackageName()
	 * @generated
	 */
	void setEPackageName(String value);

	/**
	 * Returns the value of the '<em><b>EClassifier Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>EClassifier Name</em>' attribute.
	 * @see #setEClassifierName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getEClassifierTypeLiteral_EClassifierName()
	 * @model required="true"
	 * @generated
	 */
	String getEClassifierName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.EClassifierTypeLiteral#getEClassifierName
	 * <em>EClassifier Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>EClassifier Name</em>' attribute.
	 * @see #getEClassifierName()
	 * @generated
	 */
	void setEClassifierName(String value);

} // EClassifierTypeLiteral
