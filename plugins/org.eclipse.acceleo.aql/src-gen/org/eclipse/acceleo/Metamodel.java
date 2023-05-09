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

import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Metamodel</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Metamodel#getReferencedPackage <em>Referenced Package</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getMetamodel()
 * @model
 * @generated
 */
public interface Metamodel extends AcceleoASTNode {
	/**
	 * Returns the value of the '<em><b>Referenced Package</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Package</em>' reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Referenced Package</em>' reference.
	 * @see #setReferencedPackage(EPackage)
	 * @see org.eclipse.acceleo.AcceleoPackage#getMetamodel_ReferencedPackage()
	 * @model required="true"
	 * @generated
	 */
	EPackage getReferencedPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Metamodel#getReferencedPackage <em>Referenced
	 * Package</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Referenced Package</em>' reference.
	 * @see #getReferencedPackage()
	 * @generated
	 */
	void setReferencedPackage(EPackage value);

} // Metamodel
