/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Documentation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getAuthor <em>Author</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getVersion <em>Version</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getSince <em>Since</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleDocumentation()
 * @model
 * @generated
 * @since 3.1
 */
public interface ModuleDocumentation extends Documentation {
	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleDocumentation_Author()
	 * @model
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getAuthor
	 * <em>Author</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleDocumentation_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getVersion
	 * <em>Version</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

	/**
	 * Returns the value of the '<em><b>Since</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Since</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Since</em>' attribute.
	 * @see #setSince(String)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleDocumentation_Since()
	 * @model
	 * @generated
	 */
	String getSince();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getSince
	 * <em>Since</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Since</em>' attribute.
	 * @see #getSince()
	 * @generated
	 */
	void setSince(String value);

} // ModuleDocumentation
