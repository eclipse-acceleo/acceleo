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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Feature</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.Feature#getExpression <em>Expression</em>}
 * </li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getFeature()
 * @model
 * @generated
 */
public interface Feature extends Statement {
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
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#getFeature_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Feature#getExpression <em>Expression</em>}
	 * ' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

} // Feature
