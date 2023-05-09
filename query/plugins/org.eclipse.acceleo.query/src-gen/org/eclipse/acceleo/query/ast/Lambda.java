/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Lambda</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.Lambda#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Lambda#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Lambda#getEvaluator <em>Evaluator</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getLambda()
 * @model
 * @generated
 */
public interface Lambda extends Literal {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.query.ast.VariableDeclaration}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' attribute list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getLambda_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableDeclaration> getParameters();

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getLambda_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Lambda#getExpression <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

} // Lambda
