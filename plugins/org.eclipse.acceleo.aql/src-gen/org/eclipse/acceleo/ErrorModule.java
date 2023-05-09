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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Module</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorModule#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorModule#getMissingEPackage <em>Missing EPackage</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorModule#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorModule#getMissingEndHeader <em>Missing End Header</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule()
 * @model
 * @generated
 */
public interface ErrorModule extends org.eclipse.acceleo.Error, org.eclipse.acceleo.Module {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingOpenParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#getMissingOpenParenthesis <em>Missing
	 * Open Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(int value);

	/**
	 * Returns the value of the '<em><b>Missing EPackage</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing EPackage</em>' attribute isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing EPackage</em>' attribute.
	 * @see #setMissingEPackage(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingEPackage()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#getMissingEPackage <em>Missing
	 * EPackage</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing EPackage</em>' attribute.
	 * @see #getMissingEPackage()
	 * @generated
	 */
	void setMissingEPackage(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingCloseParenthesis()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#getMissingCloseParenthesis <em>Missing
	 * Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingEndHeader()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#getMissingEndHeader <em>Missing End
	 * Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #getMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(int value);

} // ErrorModule
