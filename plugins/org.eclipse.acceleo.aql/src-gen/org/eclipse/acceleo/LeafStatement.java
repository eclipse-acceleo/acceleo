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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Leaf Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.LeafStatement#isNewLineNeeded <em>New Line Needed</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getLeafStatement()
 * @model
 * @generated
 */
public interface LeafStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>New Line Needed</em>' attribute.
	 * @see #setNewLineNeeded(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getLeafStatement_NewLineNeeded()
	 * @model required="true"
	 * @generated
	 */
	boolean isNewLineNeeded();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.LeafStatement#isNewLineNeeded <em>New Line
	 * Needed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>New Line Needed</em>' attribute.
	 * @see #isNewLineNeeded()
	 * @generated
	 */
	void setNewLineNeeded(boolean value);

} // LeafStatement
