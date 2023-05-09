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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>For Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ForStatement#getBinding <em>Binding</em>}</li>
 * <li>{@link org.eclipse.acceleo.ForStatement#getSeparator <em>Separator</em>}</li>
 * <li>{@link org.eclipse.acceleo.ForStatement#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getForStatement()
 * @model
 * @generated
 */
public interface ForStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Binding</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binding</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Binding</em>' containment reference.
	 * @see #setBinding(Binding)
	 * @see org.eclipse.acceleo.AcceleoPackage#getForStatement_Binding()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Binding getBinding();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ForStatement#getBinding <em>Binding</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Binding</em>' containment reference.
	 * @see #getBinding()
	 * @generated
	 */
	void setBinding(Binding value);

	/**
	 * Returns the value of the '<em><b>Separator</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Separator</em>' containment reference.
	 * @see #setSeparator(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getForStatement_Separator()
	 * @model containment="true"
	 * @generated
	 */
	Expression getSeparator();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ForStatement#getSeparator <em>Separator</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Separator</em>' containment reference.
	 * @see #getSeparator()
	 * @generated
	 */
	void setSeparator(Expression value);

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Block)
	 * @see org.eclipse.acceleo.AcceleoPackage#getForStatement_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ForStatement#getBody <em>Body</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

} // ForStatement
