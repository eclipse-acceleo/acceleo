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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Binding</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorBinding#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorBinding#getMissingColon <em>Missing Colon</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorBinding#getMissingType <em>Missing Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbole <em>Missing Affectation
 * Symbole</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbolePosition <em>Missing Affectation
 * Symbole Position</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding()
 * @model
 * @generated
 */
public interface ErrorBinding extends org.eclipse.acceleo.Error, Binding {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingName()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#getMissingName <em>Missing Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Name</em>' attribute.
	 * @see #getMissingName()
	 * @generated
	 */
	void setMissingName(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingColon()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#getMissingColon <em>Missing Colon</em>}'
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingType()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#getMissingType <em>Missing Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Type</em>' attribute.
	 * @see #getMissingType()
	 * @generated
	 */
	void setMissingType(int value);

	/**
	 * Returns the value of the '<em><b>Missing Affectation Symbole</b></em>' attribute. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Missing Affectation Symbole</em>' attribute isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Affectation Symbole</em>' attribute.
	 * @see #setMissingAffectationSymbole(String)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingAffectationSymbole()
	 * @model
	 * @generated
	 */
	String getMissingAffectationSymbole();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbole <em>Missing
	 * Affectation Symbole</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Affectation Symbole</em>' attribute.
	 * @see #getMissingAffectationSymbole()
	 * @generated
	 */
	void setMissingAffectationSymbole(String value);

	/**
	 * Returns the value of the '<em><b>Missing Affectation Symbole Position</b></em>' attribute. The default
	 * value is <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Affectation Symbole Position</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Affectation Symbole Position</em>' attribute.
	 * @see #setMissingAffectationSymbolePosition(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorBinding_MissingAffectationSymbolePosition()
	 * @model default="-1"
	 * @generated
	 */
	int getMissingAffectationSymbolePosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbolePosition
	 * <em>Missing Affectation Symbole Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Affectation Symbole Position</em>' attribute.
	 * @see #getMissingAffectationSymbolePosition()
	 * @generated
	 */
	void setMissingAffectationSymbolePosition(int value);

} // ErrorBinding
