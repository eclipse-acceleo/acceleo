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
package org.eclipse.acceleo.query.tests.anydsl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Multi Named Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getMultiNamedElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface MultiNamedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute list. The list contents are of type
	 * {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getMultiNamedElement_Name()
	 * @model dataType="org.eclipse.acceleo.query.tests.anydsl.SingleString"
	 * @generated
	 */
	EList<String> getName();

} // MultiNamedElement
