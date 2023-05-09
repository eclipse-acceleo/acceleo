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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>File Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.FileStatement#getMode <em>Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.FileStatement#getUrl <em>Url</em>}</li>
 * <li>{@link org.eclipse.acceleo.FileStatement#getCharset <em>Charset</em>}</li>
 * <li>{@link org.eclipse.acceleo.FileStatement#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getFileStatement()
 * @model
 * @generated
 */
public interface FileStatement extends Statement {
	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.OpenModeKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.acceleo.OpenModeKind
	 * @see #setMode(OpenModeKind)
	 * @see org.eclipse.acceleo.AcceleoPackage#getFileStatement_Mode()
	 * @model required="true"
	 * @generated
	 */
	OpenModeKind getMode();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.FileStatement#getMode <em>Mode</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.acceleo.OpenModeKind
	 * @see #getMode()
	 * @generated
	 */
	void setMode(OpenModeKind value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Url</em>' containment reference.
	 * @see #setUrl(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getFileStatement_Url()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.FileStatement#getUrl <em>Url</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Url</em>' containment reference.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(Expression value);

	/**
	 * Returns the value of the '<em><b>Charset</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Charset</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Charset</em>' containment reference.
	 * @see #setCharset(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getFileStatement_Charset()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCharset();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.FileStatement#getCharset <em>Charset</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Charset</em>' containment reference.
	 * @see #getCharset()
	 * @generated
	 */
	void setCharset(Expression value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getFileStatement_Body()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Block getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.FileStatement#getBody <em>Body</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(Block value);

} // FileStatement
