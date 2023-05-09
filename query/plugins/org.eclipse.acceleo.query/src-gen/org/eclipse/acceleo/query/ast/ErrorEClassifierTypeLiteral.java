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
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Error EClassifier Type
 * Literal</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral#isMissingColon <em>Missing
 * Colon</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getErrorEClassifierTypeLiteral()
 * @model
 * @generated
 */
public interface ErrorEClassifierTypeLiteral extends org.eclipse.acceleo.query.ast.Error, EClassifierTypeLiteral {

	/**
	 * Returns the value of the '<em><b>Missing Colon</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Colon</em>' attribute.
	 * @see #setMissingColon(boolean)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getErrorEClassifierTypeLiteral_MissingColon()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral#isMissingColon
	 * <em>Missing Colon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Colon</em>' attribute.
	 * @see #isMissingColon()
	 * @generated
	 */
	void setMissingColon(boolean value);

} // ErrorEClassifierTypeLiteral
