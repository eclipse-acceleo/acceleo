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
 * A representation of the model object '<em><b>Error Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorBinding#isMissingColon <em>Missing Colon</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorBinding#isMissingType <em>Missing Type</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbole <em>Missing Affectation Symbole</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding()
 * @model
 * @generated
 */
public interface ErrorBinding extends org.eclipse.acceleo.Error, Binding {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingColon()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#isMissingColon <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Colon</em>' attribute.
	 * @see #isMissingColon()
	 * @generated
	 */
	void setMissingColon(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Type</em>' attribute.
	 * @see #setMissingType(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingType()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#isMissingType <em>Missing Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Type</em>' attribute.
	 * @see #isMissingType()
	 * @generated
	 */
	void setMissingType(boolean value);

	/**
	 * Returns the value of the '<em><b>Missing Affectation Symbole</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Affectation Symbole</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing Affectation Symbole</em>' attribute.
	 * @see #setMissingAffectationSymbole(String)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingAffectationSymbole()
	 * @model
	 * @generated
	 */
	String getMissingAffectationSymbole();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbole <em>Missing Affectation Symbole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Affectation Symbole</em>' attribute.
	 * @see #getMissingAffectationSymbole()
	 * @generated
	 */
	void setMissingAffectationSymbole(String value);

} // ErrorBinding
