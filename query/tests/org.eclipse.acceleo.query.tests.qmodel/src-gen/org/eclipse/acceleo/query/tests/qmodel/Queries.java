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
package org.eclipse.acceleo.query.tests.qmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Queries</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Queries#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Queries#getQueries <em>Queries</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Queries#getModelElements <em>Model Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueries()
 * @model
 * @generated
 */
public interface Queries extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueries_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.Queries#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Queries</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.query.tests.qmodel.Query}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Queries</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Queries</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueries_Queries()
	 * @model containment="true"
	 * @generated
	 */
	EList<Query> getQueries();

	/**
	 * Returns the value of the '<em><b>Model Elements</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.acceleo.query.tests.qmodel.ModelElement}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Elements</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Model Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueries_ModelElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModelElement> getModelElements();

} // Queries
