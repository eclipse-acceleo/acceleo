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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Let Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.LetStatement#getVariables <em>Variables</em>}</li>
 * <li>{@link org.eclipse.acceleo.LetStatement#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getLetStatement()
 * @model
 * @generated
 */
public interface LetStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.Binding}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getLetStatement_Variables()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Binding> getVariables();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Block)
	 * @see org.eclipse.acceleo.AcceleoPackage#getLetStatement_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.LetStatement#getBody <em>Body</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

} // LetStatement
