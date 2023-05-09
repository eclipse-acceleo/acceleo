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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Variable</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorVariable#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorVariable#getMissingColon <em>Missing Colon</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorVariable#getMissingType <em>Missing Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorVariable()
 * @model
 * @generated
 */
public interface ErrorVariable extends org.eclipse.acceleo.Error, Variable {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorVariable_MissingName()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorVariable#getMissingName <em>Missing Name</em>}'
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorVariable_MissingColon()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorVariable#getMissingColon <em>Missing
	 * Colon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorVariable_MissingType()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorVariable#getMissingType <em>Missing Type</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Type</em>' attribute.
	 * @see #getMissingType()
	 * @generated
	 */
	void setMissingType(int value);

} // ErrorVariable
