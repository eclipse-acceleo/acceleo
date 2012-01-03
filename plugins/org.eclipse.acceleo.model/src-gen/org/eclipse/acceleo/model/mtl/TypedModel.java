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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Typed Model</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.TypedModel#getTakesTypesFrom <em>Takes Types From</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTypedModel()
 * @model
 * @generated
 */
public interface TypedModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Takes Types From</b></em>' reference list. The list contents are of
	 * type {@link org.eclipse.emf.ecore.EPackage}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Takes Types From</em>' reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Takes Types From</em>' reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTypedModel_TakesTypesFrom()
	 * @model required="true"
	 * @generated
	 */
	EList<EPackage> getTakesTypesFrom();

} // TypedModel
