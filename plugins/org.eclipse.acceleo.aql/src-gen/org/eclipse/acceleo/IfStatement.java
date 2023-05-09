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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>If Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.IfStatement#getCondition <em>Condition</em>}</li>
 * <li>{@link org.eclipse.acceleo.IfStatement#getThen <em>Then</em>}</li>
 * <li>{@link org.eclipse.acceleo.IfStatement#getElse <em>Else</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getIfStatement()
 * @model
 * @generated
 */
public interface IfStatement extends Statement {
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
	 * @see org.eclipse.acceleo.AcceleoPackage#getIfStatement_Condition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.IfStatement#getCondition <em>Condition</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * Returns the value of the '<em><b>Then</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Then</em>' containment reference.
	 * @see #setThen(Block)
	 * @see org.eclipse.acceleo.AcceleoPackage#getIfStatement_Then()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Block getThen();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.IfStatement#getThen <em>Then</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Then</em>' containment reference.
	 * @see #getThen()
	 * @generated
	 */
	void setThen(Block value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getIfStatement_Else()
	 * @model containment="true"
	 * @generated
	 */
	Block getElse();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.IfStatement#getElse <em>Else</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Else</em>' containment reference.
	 * @see #getElse()
	 * @generated
	 */
	void setElse(Block value);

} // IfStatement
