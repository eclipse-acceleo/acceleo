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
package org.eclipse.acceleo.compatibility.model.mt.statements;

import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>If</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getCondition <em>Condition</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getThenStatements <em>Then Statements
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getElseStatements <em>Else Statements
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getElseIf <em>Else If</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getIf()
 * @model
 * @generated
 */
public interface If extends Statement {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Expression)
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getIf_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getCondition
	 * <em>Condition</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * Returns the value of the '<em><b>Then Statements</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then Statements</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Then Statements</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getIf_ThenStatements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getThenStatements();

	/**
	 * Returns the value of the '<em><b>Else Statements</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else Statements</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Else Statements</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getIf_ElseStatements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getElseStatements();

	/**
	 * Returns the value of the '<em><b>Else If</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.compatibility.model.mt.statements.If}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else If</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Else If</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getIf_ElseIf()
	 * @model containment="true"
	 * @generated
	 */
	EList<If> getElseIf();

} // If
