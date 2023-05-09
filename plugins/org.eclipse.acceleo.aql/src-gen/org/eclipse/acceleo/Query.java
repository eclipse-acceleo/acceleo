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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Query</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Query#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.Query#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.Query#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getQuery()
 * @model
 * @generated
 */
public interface Query extends ModuleElement, DocumentedElement, NamedElement, TypedElement {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.Variable}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getQuery_Parameters()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Variable> getParameters();

	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.VisibilityKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visibility</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.acceleo.VisibilityKind
	 * @see #setVisibility(VisibilityKind)
	 * @see org.eclipse.acceleo.AcceleoPackage#getQuery_Visibility()
	 * @model required="true"
	 * @generated
	 */
	VisibilityKind getVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Query#getVisibility <em>Visibility</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.acceleo.VisibilityKind
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(VisibilityKind value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getQuery_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Query#getBody <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Expression value);

} // Query
