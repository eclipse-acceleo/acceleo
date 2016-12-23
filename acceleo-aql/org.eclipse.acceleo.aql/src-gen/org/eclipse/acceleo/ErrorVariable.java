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
 * A representation of the model object '<em><b>Error Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorVariable#isMissingColon <em>Missing Colon</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorVariable()
 * @model
 * @generated
 */
public interface ErrorVariable extends org.eclipse.acceleo.Error, Variable {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorVariable_MissingColon()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingColon();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorVariable#isMissingColon <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing Colon</em>' attribute.
	 * @see #isMissingColon()
	 * @generated
	 */
	void setMissingColon(boolean value);

} // ErrorVariable
