/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Query</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorQuery#isMissingVisibility <em>Missing Visibility</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorQuery#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorQuery#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorQuery#isMissingColon <em>Missing Colon</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorQuery#isMissingEqual <em>Missing Equal</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorQuery#isMissingEnd <em>Missing End</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery()
 * @model
 * @generated
 */
public interface ErrorQuery extends org.eclipse.acceleo.Error, Query {
	/**
	 * Returns the value of the '<em><b>Missing Visibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Visibility</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Visibility</em>' attribute.
	 * @see #setMissingVisibility(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingVisibility()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#isMissingVisibility <em>Missing Visibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Visibility</em>' attribute.
	 * @see #isMissingVisibility()
	 * @generated
	 */
	void setMissingVisibility(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Open Parenthesis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Open Parenthesis</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #setMissingOpenParenthesis(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingOpenParenthesis()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Close Parenthesis</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Close Parenthesis</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #setMissingCloseParenthesis(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingCloseParenthesis()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #isMissingCloseParenthesis()
	 * @generated
	 */
	void setMissingCloseParenthesis(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Colon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Colon</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Colon</em>' attribute.
	 * @see #setMissingColon(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingColon()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#isMissingColon <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Colon</em>' attribute.
	 * @see #isMissingColon()
	 * @generated
	 */
	void setMissingColon(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Equal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Equal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Equal</em>' attribute.
	 * @see #setMissingEqual(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingEqual()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEqual();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#isMissingEqual <em>Missing Equal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Equal</em>' attribute.
	 * @see #isMissingEqual()
	 * @generated
	 */
	void setMissingEqual(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing End</em>' attribute.
	 * @see #setMissingEnd(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingEnd()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#isMissingEnd <em>Missing End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing End</em>' attribute.
	 * @see #isMissingEnd()
	 * @generated
	 */
	void setMissingEnd(boolean value);

} // ErrorQuery
