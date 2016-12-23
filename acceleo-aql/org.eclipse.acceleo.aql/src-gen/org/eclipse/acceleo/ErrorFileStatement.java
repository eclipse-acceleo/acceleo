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
 * A representation of the model object '<em><b>Error File Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorFileStatement#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorFileStatement#isMissingComma <em>Missing Comma</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorFileStatement#isMissingOpenMode <em>Missing Open Mode</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorFileStatement#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorFileStatement#isMissingEndHeader <em>Missing End Header</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorFileStatement#isMissingEnd <em>Missing End</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement()
 * @model
 * @generated
 */
public interface ErrorFileStatement extends org.eclipse.acceleo.Error, FileStatement {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingOpenParenthesis()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Comma</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Comma</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Comma</em>' attribute.
	 * @see #setMissingComma(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingComma()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingComma();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#isMissingComma <em>Missing Comma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Comma</em>' attribute.
	 * @see #isMissingComma()
	 * @generated
	 */
	void setMissingComma(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Open Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Open Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Open Mode</em>' attribute.
	 * @see #setMissingOpenMode(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingOpenMode()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingOpenMode();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#isMissingOpenMode <em>Missing Open Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Open Mode</em>' attribute.
	 * @see #isMissingOpenMode()
	 * @generated
	 */
	void setMissingOpenMode(boolean value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingCloseParenthesis()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #isMissingCloseParenthesis()
	 * @generated
	 */
	void setMissingCloseParenthesis(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing End Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Header</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing End Header</em>' attribute.
	 * @see #setMissingEndHeader(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingEndHeader()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#isMissingEndHeader <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #isMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(boolean value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingEnd()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#isMissingEnd <em>Missing End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing End</em>' attribute.
	 * @see #isMissingEnd()
	 * @generated
	 */
	void setMissingEnd(boolean value);

} // ErrorFileStatement
