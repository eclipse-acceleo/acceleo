/**
 * Copyright (c) 2008, 2023 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Protected Area</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ProtectedArea#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.acceleo.ProtectedArea#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.ProtectedArea#getStartTagPrefix <em>Start Tag Prefix</em>}</li>
 * <li>{@link org.eclipse.acceleo.ProtectedArea#getEndTagPrefix <em>End Tag Prefix</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getProtectedArea()
 * @model
 * @generated
 */
public interface ProtectedArea extends Statement {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Id</em>' containment reference.
	 * @see #setId(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getProtectedArea_Id()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getId();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ProtectedArea#getId <em>Id</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Id</em>' containment reference.
	 * @see #getId()
	 * @generated
	 */
	void setId(Expression value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getProtectedArea_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ProtectedArea#getBody <em>Body</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

	/**
	 * Returns the value of the '<em><b>Start Tag Prefix</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start Tag Prefix</em>' containment reference.
	 * @see #setStartTagPrefix(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getProtectedArea_StartTagPrefix()
	 * @model containment="true"
	 * @generated
	 */
	Expression getStartTagPrefix();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ProtectedArea#getStartTagPrefix <em>Start Tag
	 * Prefix</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start Tag Prefix</em>' containment reference.
	 * @see #getStartTagPrefix()
	 * @generated
	 */
	void setStartTagPrefix(Expression value);

	/**
	 * Returns the value of the '<em><b>End Tag Prefix</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End Tag Prefix</em>' containment reference.
	 * @see #setEndTagPrefix(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getProtectedArea_EndTagPrefix()
	 * @model containment="true"
	 * @generated
	 */
	Expression getEndTagPrefix();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ProtectedArea#getEndTagPrefix <em>End Tag
	 * Prefix</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Tag Prefix</em>' containment reference.
	 * @see #getEndTagPrefix()
	 * @generated
	 */
	void setEndTagPrefix(Expression value);

} // ProtectedArea
