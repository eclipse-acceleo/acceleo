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
package org.eclipse.acceleo.compatibility.model.mt.core;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>AST Node</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getBegin <em>Begin</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getEnd <em>End</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getASTNode()
 * @model abstract="true"
 * @generated
 */
public interface ASTNode extends EObject {
	/**
	 * Returns the value of the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Begin</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Begin</em>' attribute.
	 * @see #setBegin(int)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getASTNode_Begin()
	 * @model
	 * @generated
	 */
	int getBegin();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getBegin
	 * <em>Begin</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Begin</em>' attribute.
	 * @see #getBegin()
	 * @generated
	 */
	void setBegin(int value);

	/**
	 * Returns the value of the '<em><b>End</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End</em>' attribute.
	 * @see #setEnd(int)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getASTNode_End()
	 * @model
	 * @generated
	 */
	int getEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getEnd
	 * <em>End</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End</em>' attribute.
	 * @see #getEnd()
	 * @generated
	 */
	void setEnd(int value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation"
	 * @generated
	 */
	Template getTemplate();

} // ASTNode
