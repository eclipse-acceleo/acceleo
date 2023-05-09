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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Let</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.Let#getBindings <em>Bindings</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.Let#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getLet()
 * @model
 * @generated
 */
public interface Let extends Expression {
	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.query.ast.Binding}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getLet_Bindings()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Binding> getBindings();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(Expression)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getLet_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.Let#getBody <em>Body</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Expression value);

} // Let
