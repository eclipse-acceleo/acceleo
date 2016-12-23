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
 * A representation of the model object '<em><b>Error Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorModule#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorModule#isMissingEPackage <em>Missing EPackage</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorModule#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorModule#isMissingEndHeader <em>Missing End Header</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule()
 * @model
 * @generated
 */
public interface ErrorModule extends org.eclipse.acceleo.Error, Module {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingOpenParenthesis()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingOpenParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Open Parenthesis</em>' attribute.
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 */
	void setMissingOpenParenthesis(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing EPackage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing EPackage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing EPackage</em>' attribute.
	 * @see #setMissingEPackage(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingEPackage()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#isMissingEPackage <em>Missing EPackage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing EPackage</em>' attribute.
	 * @see #isMissingEPackage()
	 * @generated
	 */
	void setMissingEPackage(boolean value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingCloseParenthesis()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingCloseParenthesis();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}' attribute.
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModule_MissingEndHeader()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModule#isMissingEndHeader <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #isMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(boolean value);

} // ErrorModule
