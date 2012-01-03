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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>If Block</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.IfBlock#getIfExpr <em>If Expr</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.IfBlock#getElse <em>Else</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.IfBlock#getElseIf <em>Else If</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getIfBlock()
 * @model
 * @generated
 */
public interface IfBlock extends Block {
	/**
	 * Returns the value of the '<em><b>If Expr</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>If Expr</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>If Expr</em>' containment reference.
	 * @see #setIfExpr(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getIfBlock_IfExpr()
	 * @model containment="true" required="true"
	 * @generated
	 */
	OCLExpression getIfExpr();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.IfBlock#getIfExpr <em>If Expr</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>If Expr</em>' containment reference.
	 * @see #getIfExpr()
	 * @generated
	 */
	void setIfExpr(OCLExpression value);

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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getIfBlock_Else()
	 * @model containment="true"
	 * @generated
	 */
	Block getElse();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.IfBlock#getElse <em>Else</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Else</em>' containment reference.
	 * @see #getElse()
	 * @generated
	 */
	void setElse(Block value);

	/**
	 * Returns the value of the '<em><b>Else If</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.model.mtl.IfBlock}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else If</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Else If</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getIfBlock_ElseIf()
	 * @model containment="true"
	 * @generated
	 */
	EList<IfBlock> getElseIf();

} // IfBlock
