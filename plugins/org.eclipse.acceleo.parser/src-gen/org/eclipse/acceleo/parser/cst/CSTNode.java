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
package org.eclipse.acceleo.parser.cst;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>CST Node</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.CSTNode#getStartPosition <em>Start Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.CSTNode#getEndPosition <em>End Position</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getCSTNode()
 * @model interface="true" abstract="true"
 * @generated
 * @since 3.0
 */
public interface CSTNode extends EObject {
	/**
	 * Returns the value of the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Position</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start Position</em>' attribute.
	 * @see #setStartPosition(int)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getCSTNode_StartPosition()
	 * @model
	 * @generated
	 */
	int getStartPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.CSTNode#getStartPosition
	 * <em>Start Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start Position</em>' attribute.
	 * @see #getStartPosition()
	 * @generated
	 */
	void setStartPosition(int value);

	/**
	 * Returns the value of the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Position</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End Position</em>' attribute.
	 * @see #setEndPosition(int)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getCSTNode_EndPosition()
	 * @model
	 * @generated
	 */
	int getEndPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.CSTNode#getEndPosition
	 * <em>End Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Position</em>' attribute.
	 * @see #getEndPosition()
	 * @generated
	 */
	void setEndPosition(int value);

} // CSTNode
