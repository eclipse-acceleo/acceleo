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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Let Block</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.LetBlock#getElseLet <em>Else Let</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.LetBlock#getElse <em>Else</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.LetBlock#getLetExpr <em>Let Expr</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getLetBlock()
 * @model
 * @generated
 * @since 3.0
 */
public interface LetBlock extends Block {
	/**
	 * Returns the value of the '<em><b>Else Let</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.parser.cst.LetBlock}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else Let</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Else Let</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getLetBlock_ElseLet()
	 * @model containment="true"
	 * @generated
	 */
	EList<LetBlock> getElseLet();

	/**
	 * Returns the value of the '<em><b>Else</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Else</em>' containment reference.
	 * @see #setElse(Block)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getLetBlock_Else()
	 * @model containment="true"
	 * @generated
	 */
	Block getElse();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.LetBlock#getElse <em>Else</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Else</em>' containment reference.
	 * @see #getElse()
	 * @generated
	 */
	void setElse(Block value);

	/**
	 * Returns the value of the '<em><b>Let Variable</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Let Variable</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Let Variable</em>' containment reference.
	 * @see #setLetVariable(Variable)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getLetBlock_LetVariable()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getLetVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.LetBlock#getLetVariable
	 * <em>Let Variable</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Let Variable</em>' containment reference.
	 * @see #getLetVariable()
	 * @generated
	 */
	void setLetVariable(Variable value);

} // LetBlock
