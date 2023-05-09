/**
 *  Copyright (c) 2015, 2021 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Conditional</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.Conditional#getPredicate <em>Predicate</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Conditional#getTrueBranch <em>True Branch</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Conditional#getFalseBranch <em>False Branch</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getConditional()
 * @model
 * @generated
 */
public interface Conditional extends Expression {
	/**
	 * Returns the value of the '<em><b>Predicate</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Predicate</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Predicate</em>' containment reference.
	 * @see #setPredicate(Expression)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getConditional_Predicate()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getPredicate();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Conditional#getPredicate
	 * <em>Predicate</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Predicate</em>' containment reference.
	 * @see #getPredicate()
	 * @generated
	 */
	void setPredicate(Expression value);

	/**
	 * Returns the value of the '<em><b>True Branch</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>True Branch</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>True Branch</em>' containment reference.
	 * @see #setTrueBranch(Expression)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getConditional_TrueBranch()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getTrueBranch();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Conditional#getTrueBranch <em>True
	 * Branch</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>True Branch</em>' containment reference.
	 * @see #getTrueBranch()
	 * @generated
	 */
	void setTrueBranch(Expression value);

	/**
	 * Returns the value of the '<em><b>False Branch</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>False Branch</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>False Branch</em>' containment reference.
	 * @see #setFalseBranch(Expression)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getConditional_FalseBranch()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getFalseBranch();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Conditional#getFalseBranch <em>False
	 * Branch</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>False Branch</em>' containment reference.
	 * @see #getFalseBranch()
	 * @generated
	 */
	void setFalseBranch(Expression value);

} // Conditional
