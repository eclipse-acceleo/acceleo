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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Block</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Block#getStatements <em>Statements</em>}</li>
 * <li>{@link org.eclipse.acceleo.Block#isInlined <em>Inlined</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getBlock()
 * @model
 * @generated
 */
public interface Block extends AcceleoASTNode {
	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.Statement}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statements</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getBlock_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();

	/**
	 * Returns the value of the '<em><b>Inlined</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>Inlined</em>' attribute.
	 * @see #setInlined(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getBlock_Inlined()
	 * @model required="true"
	 * @generated
	 */
	boolean isInlined();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Block#isInlined <em>Inlined</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Inlined</em>' attribute.
	 * @see #isInlined()
	 * @generated
	 */
	void setInlined(boolean value);

} // Block
