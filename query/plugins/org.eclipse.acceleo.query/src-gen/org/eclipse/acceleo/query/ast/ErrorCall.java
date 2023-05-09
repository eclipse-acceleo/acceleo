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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Collection Call</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.ErrorCall#isMissingEndParenthesis <em>Missing End
 * Parenthesis</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getErrorCall()
 * @model
 * @generated
 */
public interface ErrorCall extends org.eclipse.acceleo.query.ast.Error, Call {

	/**
	 * Returns the value of the '<em><b>Missing End Parenthesis</b></em>' attribute. The default value is
	 * <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Parenthesis</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End Parenthesis</em>' attribute.
	 * @see #setMissingEndParenthesis(boolean)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getErrorCall_MissingEndParenthesis()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isMissingEndParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.ErrorCall#isMissingEndParenthesis
	 * <em>Missing End Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Parenthesis</em>' attribute.
	 * @see #isMissingEndParenthesis()
	 * @generated
	 */
	void setMissingEndParenthesis(boolean value);

} // ErrorCollectionCall
