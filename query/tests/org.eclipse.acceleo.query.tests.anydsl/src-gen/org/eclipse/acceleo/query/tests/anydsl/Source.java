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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Source</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Source#getFoods <em>Foods</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Source#getOrigin <em>Origin</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getSource()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Source extends MultiNamedElement {
	/**
	 * Returns the value of the '<em><b>Foods</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food}. It is bidirectional and its opposite is '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#getSource <em>Source</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foods</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Foods</em>' reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getSource_Foods()
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Food> getFoods();

	/**
	 * Returns the value of the '<em><b>Origin</b></em>' attribute list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Country}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Origin</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getSource_Origin()
	 * @model dataType="org.eclipse.acceleo.query.tests.anydsl.CountryData"
	 * @generated
	 */
	EList<Country> getOrigin();

} // Source
