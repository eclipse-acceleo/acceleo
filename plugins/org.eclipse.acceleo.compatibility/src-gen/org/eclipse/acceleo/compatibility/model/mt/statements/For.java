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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>For</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.For#getIterator <em>Iterator</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.For#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getFor()
 * @model
 * @generated
 */
public interface For extends Statement {
	/**
	 * Returns the value of the '<em><b>Iterator</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iterator</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Iterator</em>' containment reference.
	 * @see #setIterator(Expression)
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getFor_Iterator()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getIterator();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.statements.For#getIterator
	 * <em>Iterator</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Iterator</em>' containment reference.
	 * @see #getIterator()
	 * @generated
	 */
	void setIterator(Expression value);

	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Statements</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getFor_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();

} // For
