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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Animal</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Animal#getPart <em>Part</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getAnimal()
 * @model
 * @generated
 */
public interface Animal extends Source {
	/**
	 * Returns the value of the '<em><b>Part</b></em>' attribute list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Part}. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Part}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Part</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Part</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Part
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getAnimal_Part()
	 * @model
	 * @generated
	 */
	EList<Part> getPart();

} // Animal
