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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Template</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingVisibility <em>Missing Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingParameters <em>Missing Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingGuardOpenParenthesis <em>Missing Guard Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingGuardCloseParenthesis <em>Missing Guard Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingPostCloseParenthesis <em>Missing Post Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingEndHeader <em>Missing End Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorTemplate#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate()
 * @model
 * @generated
 */
public interface ErrorTemplate extends org.eclipse.acceleo.Error, Template {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingVisibility()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingVisibility <em>Missing
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingName()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingName <em>Missing Name</em>}'
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingOpenParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingOpenParenthesis <em>Missing
	 * Open Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingParameters()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingParameters();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingParameters <em>Missing
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingCloseParenthesis <em>Missing
	 * Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Close Parenthesis</em>' attribute.
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 */
	void setMissingCloseParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Guard Open Parenthesis</b></em>' attribute. The default value
	 * is <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Guard Open Parenthesis</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Guard Open Parenthesis</em>' attribute.
	 * @see #setMissingGuardOpenParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingGuardOpenParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingGuardOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingGuardOpenParenthesis
	 * <em>Missing Guard Open Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Guard Open Parenthesis</em>' attribute.
	 * @see #getMissingGuardOpenParenthesis()
	 * @generated
	 */
	void setMissingGuardOpenParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Guard Close Parenthesis</b></em>' attribute. The default value
	 * is <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Guard Close Parenthesis</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Guard Close Parenthesis</em>' attribute.
	 * @see #setMissingGuardCloseParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingGuardCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingGuardCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingGuardCloseParenthesis
	 * <em>Missing Guard Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Guard Close Parenthesis</em>' attribute.
	 * @see #getMissingGuardCloseParenthesis()
	 * @generated
	 */
	void setMissingGuardCloseParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing Post Close Parenthesis</b></em>' attribute. The default value
	 * is <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Post Close Parenthesis</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Post Close Parenthesis</em>' attribute.
	 * @see #setMissingPostCloseParenthesis(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingPostCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingPostCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingPostCloseParenthesis
	 * <em>Missing Post Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Post Close Parenthesis</em>' attribute.
	 * @see #getMissingPostCloseParenthesis()
	 * @generated
	 */
	void setMissingPostCloseParenthesis(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingEndHeader()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingEndHeader <em>Missing End
	 * Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorTemplate_MissingEnd()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorTemplate#getMissingEnd <em>Missing End</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End</em>' attribute.
	 * @see #getMissingEnd()
	 * @generated
	 */
	void setMissingEnd(int value);

} // ErrorTemplate
