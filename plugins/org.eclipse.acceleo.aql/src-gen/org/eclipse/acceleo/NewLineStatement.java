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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>New Line Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.NewLineStatement#isIndentationNeeded <em>Indentation Needed</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getNewLineStatement()
 * @model
 * @generated
 */
public interface NewLineStatement extends TextStatement {
	/**
	 * Returns the value of the '<em><b>Indentation Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Indentation Needed</em>' attribute.
	 * @see #setIndentationNeeded(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getNewLineStatement_IndentationNeeded()
	 * @model required="true"
	 * @generated
	 */
	boolean isIndentationNeeded();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.NewLineStatement#isIndentationNeeded <em>Indentation
	 * Needed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Indentation Needed</em>' attribute.
	 * @see #isIndentationNeeded()
	 * @generated
	 */
	void setIndentationNeeded(boolean value);

} // NewLineStatement
