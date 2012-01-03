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
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.traceability.Resource#getPath <em>Path</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.Resource#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.Resource#getCharset <em>Charset</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getResource()
 * @model
 * @generated
 */
public interface Resource extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getResource_Path()
	 * @model dataType="org.eclipse.acceleo.traceability.path"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.Resource#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getResource_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.Resource#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Charset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Charset</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Charset</em>' attribute.
	 * @see #setCharset(String)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getResource_Charset()
	 * @model
	 * @generated
	 */
	String getCharset();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.Resource#getCharset <em>Charset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Charset</em>' attribute.
	 * @see #getCharset()
	 * @generated
	 */
	void setCharset(String value);

} // Resource
