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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error File Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorFileStatement#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorFileStatement#getMissingComma <em>Missing Comma</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorFileStatement#getMissingOpenMode <em>Missing Open Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorFileStatement#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorFileStatement#getMissingEndHeader <em>Missing End Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorFileStatement#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement()
 * @model
 * @generated
 */
public interface ErrorFileStatement extends org.eclipse.acceleo.Error, FileStatement {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingOpenParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingOpenParenthesis
	 * <em>Missing Open Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Comma</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Comma</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Comma</em>' attribute.
	 * @see #setMissingComma(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingComma()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingComma();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingComma <em>Missing
	 * Comma</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Comma</em>' attribute.
	 * @see #getMissingComma()
	 * @generated
	 */
	void setMissingComma(int value);

	/**
	 * Returns the value of the '<em><b>Missing Open Mode</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Open Mode</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Open Mode</em>' attribute.
	 * @see #setMissingOpenMode(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingOpenMode()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingOpenMode();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingOpenMode <em>Missing
	 * Open Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Open Mode</em>' attribute.
	 * @see #getMissingOpenMode()
	 * @generated
	 */
	void setMissingOpenMode(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingCloseParenthesis
	 * <em>Missing Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 */
	void setMissingCloseParenthesis(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingEndHeader()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingEndHeader <em>Missing
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorFileStatement_MissingEnd()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingEnd <em>Missing
	 * End</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End</em>' attribute.
	 * @see #getMissingEnd()
	 * @generated
	 */
	void setMissingEnd(int value);

} // ErrorFileStatement
