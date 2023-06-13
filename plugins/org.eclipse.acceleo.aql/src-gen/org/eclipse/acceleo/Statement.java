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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Statement</b></em>'. <!-- end-user-doc
 * -->
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getStatement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Statement extends AcceleoASTNode {

	/**
	 * Returns the value of the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Multi Lines</em>' attribute.
	 * @see #setMultiLines(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getStatement_MultiLines()
	 * @model required="true"
	 * @generated
	 */
	boolean isMultiLines();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Statement#isMultiLines <em>Multi Lines</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Multi Lines</em>' attribute.
	 * @see #isMultiLines()
	 * @generated
	 */
	void setMultiLines(boolean value);
} // Statement
