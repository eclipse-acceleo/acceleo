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
package org.eclipse.acceleo.compatibility.model.mt.core;

import org.eclipse.acceleo.compatibility.model.mt.Resource;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Metamodel</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Metamodel#getPackageClass <em>Package Class
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getMetamodel()
 * @model
 * @generated
 */
public interface Metamodel extends Resource {
	/**
	 * Returns the value of the '<em><b>Package Class</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Class</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Package Class</em>' attribute.
	 * @see #setPackageClass(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getMetamodel_PackageClass()
	 * @model
	 * @generated
	 */
	String getPackageClass();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Metamodel#getPackageClass
	 * <em>Package Class</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Package Class</em>' attribute.
	 * @see #getPackageClass()
	 * @generated
	 */
	void setPackageClass(String value);

} // Metamodel
