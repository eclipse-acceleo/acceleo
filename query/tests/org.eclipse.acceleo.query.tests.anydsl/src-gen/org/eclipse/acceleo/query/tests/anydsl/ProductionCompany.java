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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Production Company</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.ProductionCompany#getProducers <em>Producers</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getProductionCompany()
 * @model
 * @generated
 */
public interface ProductionCompany extends Company {
	/**
	 * Returns the value of the '<em><b>Producers</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.query.tests.anydsl.Producer}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Producers</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Producers</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getProductionCompany_Producers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Producer> getProducers();

} // ProductionCompany
