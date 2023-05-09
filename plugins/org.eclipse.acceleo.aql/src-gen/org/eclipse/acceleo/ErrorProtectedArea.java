/**
 * Copyright (c) 2008, 2023 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Protected Area</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingStartTagPrefixCloseParenthesis <em>Missing
 * Start Tag Prefix Close Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEndTagPrefixCloseParenthesis <em>Missing End
 * Tag Prefix Close Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEndHeader <em>Missing End Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea()
 * @model
 * @generated
 */
public interface ErrorProtectedArea extends org.eclipse.acceleo.Error, ProtectedArea {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea_MissingOpenParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingOpenParenthesis
	 * <em>Missing Open Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea_MissingCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingCloseParenthesis
	 * <em>Missing Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 */
	void setMissingCloseParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Start Tag Prefix Close Parenthesis</b></em>' attribute. The
	 * default value is <code>"-1"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Start Tag Prefix Close Parenthesis</em>' attribute.
	 * @see #setMissingStartTagPrefixCloseParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea_MissingStartTagPrefixCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingStartTagPrefixCloseParenthesis();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingStartTagPrefixCloseParenthesis <em>Missing
	 * Start Tag Prefix Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Start Tag Prefix Close Parenthesis</em>' attribute.
	 * @see #getMissingStartTagPrefixCloseParenthesis()
	 * @generated
	 */
	void setMissingStartTagPrefixCloseParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing End Tag Prefix Close Parenthesis</b></em>' attribute. The
	 * default value is <code>"-1"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End Tag Prefix Close Parenthesis</em>' attribute.
	 * @see #setMissingEndTagPrefixCloseParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea_MissingEndTagPrefixCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndTagPrefixCloseParenthesis();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEndTagPrefixCloseParenthesis <em>Missing End
	 * Tag Prefix Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Tag Prefix Close Parenthesis</em>' attribute.
	 * @see #getMissingEndTagPrefixCloseParenthesis()
	 * @generated
	 */
	void setMissingEndTagPrefixCloseParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing End Header</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Header</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End Header</em>' attribute.
	 * @see #setMissingEndHeader(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea_MissingEndHeader()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEndHeader <em>Missing
	 * End Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #getMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorProtectedArea_MissingEnd()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEnd <em>Missing
	 * End</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End</em>' attribute.
	 * @see #getMissingEnd()
	 * @generated
	 */
	void setMissingEnd(int value);

} // ErrorProtectedArea
