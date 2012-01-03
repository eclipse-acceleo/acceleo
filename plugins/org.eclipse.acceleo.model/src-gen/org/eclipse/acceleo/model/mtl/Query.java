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
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Query</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.Query#getParameter <em>Parameter </em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Query#getExpression <em>Expression </em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Query#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQuery()
 * @model
 * @generated
 */
public interface Query extends ModuleElement, DocumentedElement {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.ocl.ecore.Variable}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQuery_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getParameter();

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQuery_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	OCLExpression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Query#getExpression <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClassifier)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQuery_Type()
	 * @model required="true"
	 * @generated
	 */
	EClassifier getType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Query#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClassifier value);

} // Query
