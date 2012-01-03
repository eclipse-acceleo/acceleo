/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.extlibrary;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Employee</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.tests.extlibrary.Employee#getManager <em>Manager</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.tests.extlibrary.ExtlibraryPackage#getEmployee()
 * @model
 * @generated
 */
public interface Employee extends Person {
	/**
	 * Returns the value of the '<em><b>Manager</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Manager</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Manager</em>' reference.
	 * @see #setManager(Employee)
	 * @see org.eclipse.acceleo.parser.tests.extlibrary.ExtlibraryPackage#getEmployee_Manager()
	 * @model
	 * @generated
	 */
	Employee getManager();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.tests.extlibrary.Employee#getManager
	 * <em>Manager</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Manager</em>' reference.
	 * @see #getManager()
	 * @generated
	 */
	void setManager(Employee value);

} // Employee
