/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.qmodel;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>EObject Variable</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.EObjectVariable#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getEObjectVariable()
 * @model
 * @generated
 */
public interface EObjectVariable extends Variable {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(ModelElement)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getEObjectVariable_Value()
	 * @model required="true"
	 * @generated
	 */
	ModelElement getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.EObjectVariable#getValue
	 * <em>Value</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(ModelElement value);

} // EObjectVariable
