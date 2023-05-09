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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Query</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingVisibility <em>Missing Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingOpenParenthesis <em>Missing Open Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingParameters <em>Missing Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingColon <em>Missing Colon</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingType <em>Missing Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingEqual <em>Missing Equal</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorQuery#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery()
 * @model
 * @generated
 */
public interface ErrorQuery extends org.eclipse.acceleo.Error, Query {
	/**
	 * Returns the value of the '<em><b>Missing Visibility</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Visibility</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Visibility</em>' attribute.
	 * @see #setMissingVisibility(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingVisibility()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingVisibility <em>Missing
	 * Visibility</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Visibility</em>' attribute.
	 * @see #getMissingVisibility()
	 * @generated
	 */
	void setMissingVisibility(int value);

	/**
	 * Returns the value of the '<em><b>Missing Name</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Name</em>' attribute.
	 * @see #setMissingName(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingName()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingName <em>Missing Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Name</em>' attribute.
	 * @see #getMissingName()
	 * @generated
	 */
	void setMissingName(int value);

	/**
	 * Returns the value of the '<em><b>Missing Open Parenthesis</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Open Parenthesis</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #setMissingOpenParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingOpenParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingOpenParenthesis <em>Missing Open
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Parameters</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Parameters</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Parameters</em>' attribute.
	 * @see #setMissingParameters(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingParameters()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingParameters();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingParameters <em>Missing
	 * Parameters</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Parameters</em>' attribute.
	 * @see #getMissingParameters()
	 * @generated
	 */
	void setMissingParameters(int value);

	/**
	 * Returns the value of the '<em><b>Missing Close Parenthesis</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Close Parenthesis</em>' attribute isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #setMissingCloseParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingCloseParenthesis <em>Missing
	 * Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 */
	void setMissingCloseParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Colon</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Colon</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Colon</em>' attribute.
	 * @see #setMissingColon(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingColon()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingColon <em>Missing Colon</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Colon</em>' attribute.
	 * @see #getMissingColon()
	 * @generated
	 */
	void setMissingColon(int value);

	/**
	 * Returns the value of the '<em><b>Missing Type</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Type</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Type</em>' attribute.
	 * @see #setMissingType(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingType()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingType <em>Missing Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Type</em>' attribute.
	 * @see #getMissingType()
	 * @generated
	 */
	void setMissingType(int value);

	/**
	 * Returns the value of the '<em><b>Missing Equal</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Equal</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Equal</em>' attribute.
	 * @see #setMissingEqual(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingEqual()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEqual();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingEqual <em>Missing Equal</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Equal</em>' attribute.
	 * @see #getMissingEqual()
	 * @generated
	 */
	void setMissingEqual(int value);

	/**
	 * Returns the value of the '<em><b>Missing End</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End</em>' attribute.
	 * @see #setMissingEnd(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorQuery_MissingEnd()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorQuery#getMissingEnd <em>Missing End</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End</em>' attribute.
	 * @see #getMissingEnd()
	 * @generated
	 */
	void setMissingEnd(int value);

} // ErrorQuery
