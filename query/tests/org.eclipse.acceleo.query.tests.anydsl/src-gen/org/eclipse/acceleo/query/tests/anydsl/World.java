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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>World</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.World#getCompanies <em>Companies</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.World#getFoods <em>Foods</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.World#getSources <em>Sources</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getWorld()
 * @model annotation="http://www.obeo.fr/dsl/dnc/archetype archetype='MomentInterval'"
 * @generated
 */
public interface World extends EObject {
	/**
	 * Returns the value of the '<em><b>Companies</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.query.tests.anydsl.Company}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Companies</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Companies</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getWorld_Companies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Company> getCompanies();

	/**
	 * Returns the value of the '<em><b>Foods</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.query.tests.anydsl.Food}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foods</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Foods</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getWorld_Foods()
	 * @model containment="true"
	 * @generated
	 */
	EList<Food> getFoods();

	/**
	 * Returns the value of the '<em><b>Sources</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.query.tests.anydsl.Source}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sources</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sources</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getWorld_Sources()
	 * @model containment="true"
	 * @generated
	 */
	EList<Source> getSources();

} // World
