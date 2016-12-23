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
 * A representation of the model object '<em><b>Error Metamodel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorMetamodel#isMissingEndQuote <em>Missing End Quote</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorMetamodel()
 * @model
 * @generated
 */
public interface ErrorMetamodel extends org.eclipse.acceleo.Error, Metamodel {
	/**
	 * Returns the value of the '<em><b>Missing End Quote</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Quote</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing End Quote</em>' attribute.
	 * @see #setMissingEndQuote(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorMetamodel_MissingEndQuote()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEndQuote();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorMetamodel#isMissingEndQuote <em>Missing End Quote</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing End Quote</em>' attribute.
	 * @see #isMissingEndQuote()
	 * @generated
	 */
	void setMissingEndQuote(boolean value);

} // ErrorMetamodel
