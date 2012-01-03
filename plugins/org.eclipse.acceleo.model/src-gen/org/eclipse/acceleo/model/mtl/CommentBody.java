/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Comment Body</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.CommentBody#getStartPosition <em>Start Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.CommentBody#getEndPosition <em>End Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.CommentBody#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getCommentBody()
 * @model
 * @generated
 * @since 3.1
 */
public interface CommentBody extends EObject {
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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getCommentBody_StartPosition()
	 * @model
	 * @generated
	 */
	int getStartPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.CommentBody#getStartPosition
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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getCommentBody_EndPosition()
	 * @model
	 * @generated
	 */
	int getEndPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.CommentBody#getEndPosition
	 * <em>End Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Position</em>' attribute.
	 * @see #getEndPosition()
	 * @generated
	 */
	void setEndPosition(int value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getCommentBody_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.CommentBody#getValue <em>Value</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // CommentBody
