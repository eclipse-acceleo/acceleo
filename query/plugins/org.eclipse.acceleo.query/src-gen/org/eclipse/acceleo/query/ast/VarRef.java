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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Var Ref</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.VarRef#getVariableName <em>Variable Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getVarRef()
 * @model
 * @generated
 */
public interface VarRef extends Expression {
	/**
	 * Returns the value of the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variable Name</em>' attribute.
	 * @see #setVariableName(String)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getVarRef_VariableName()
	 * @model
	 * @generated
	 */
	String getVariableName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.VarRef#getVariableName <em>Variable
	 * Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Variable Name</em>' attribute.
	 * @see #getVariableName()
	 * @generated
	 */
	void setVariableName(String value);

} // VarRef
