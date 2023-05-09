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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Metamodel</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorMetamodel#getFragment <em>Fragment</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorMetamodel#getMissingEndQuote <em>Missing End Quote</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorMetamodel()
 * @model
 * @generated
 */
public interface ErrorMetamodel extends org.eclipse.acceleo.Error, Metamodel {
	/**
	 * Returns the value of the '<em><b>Fragment</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Fragment</em>' attribute.
	 * @see #setFragment(String)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorMetamodel_Fragment()
	 * @model
	 * @generated
	 */
	String getFragment();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorMetamodel#getFragment <em>Fragment</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Fragment</em>' attribute.
	 * @see #getFragment()
	 * @generated
	 */
	void setFragment(String value);

	/**
	 * Returns the value of the '<em><b>Missing End Quote</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Quote</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End Quote</em>' attribute.
	 * @see #setMissingEndQuote(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorMetamodel_MissingEndQuote()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndQuote();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorMetamodel#getMissingEndQuote <em>Missing End
	 * Quote</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Quote</em>' attribute.
	 * @see #getMissingEndQuote()
	 * @generated
	 */
	void setMissingEndQuote(int value);

} // ErrorMetamodel
