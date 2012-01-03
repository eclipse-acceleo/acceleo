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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Block</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.Block#getInit <em>Init</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Block#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getBlock()
 * @model
 * @generated
 * @since 3.0
 */
public interface Block extends TemplateExpression {
	/**
	 * Returns the value of the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Init</em>' containment reference.
	 * @see #setInit(InitSection)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getBlock_Init()
	 * @model containment="true"
	 * @generated
	 */
	InitSection getInit();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Block#getInit <em>Init</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Init</em>' containment reference.
	 * @see #getInit()
	 * @generated
	 */
	void setInit(InitSection value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.parser.cst.TemplateExpression}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getBlock_Body()
	 * @model containment="true"
	 * @generated
	 */
	EList<TemplateExpression> getBody();

} // Block
