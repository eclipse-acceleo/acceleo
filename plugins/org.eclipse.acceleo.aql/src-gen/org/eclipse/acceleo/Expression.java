/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

import org.eclipse.acceleo.query.parser.AstResult;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Expression</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Expression#getAst <em>Ast</em>}</li>
 * <li>{@link org.eclipse.acceleo.Expression#getAql <em>Aql</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getExpression()
 * @model
 * @generated
 */
public interface Expression extends AcceleoASTNode {
	/**
	 * Returns the value of the '<em><b>Ast</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ast</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ast</em>' attribute.
	 * @see #setAst(AstResult)
	 * @see org.eclipse.acceleo.AcceleoPackage#getExpression_Ast()
	 * @model dataType="org.eclipse.acceleo.ASTResult" required="true"
	 * @generated
	 */
	AstResult getAst();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Expression#getAst <em>Ast</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Ast</em>' attribute.
	 * @see #getAst()
	 * @generated
	 */
	void setAst(AstResult value);

	/**
	 * Returns the value of the '<em><b>Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Aql</em>' containment reference.
	 * @see #setAql(org.eclipse.acceleo.query.ast.Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getExpression_Aql()
	 * @model containment="true" required="true"
	 * @generated
	 */
	org.eclipse.acceleo.query.ast.Expression getAql();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Expression#getAql <em>Aql</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Aql</em>' containment reference.
	 * @see #getAql()
	 * @generated
	 */
	void setAql(org.eclipse.acceleo.query.ast.Expression value);

} // Expression
