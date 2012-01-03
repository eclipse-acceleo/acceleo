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
package org.eclipse.acceleo.parser.cst;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>File Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.FileBlock#getOpenMode <em>Open Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.FileBlock#getFileUrl <em>File Url</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.FileBlock#getUniqId <em>Uniq Id</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.FileBlock#getCharset <em>Charset</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getFileBlock()
 * @model
 * @generated
 * @since 3.0
 */
public interface FileBlock extends Block {
	/**
	 * Returns the value of the '<em><b>Open Mode</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.parser.cst.OpenModeKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open Mode</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Open Mode</em>' attribute.
	 * @see org.eclipse.acceleo.parser.cst.OpenModeKind
	 * @see #setOpenMode(OpenModeKind)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getFileBlock_OpenMode()
	 * @model required="true"
	 * @generated
	 */
	OpenModeKind getOpenMode();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.FileBlock#getOpenMode <em>Open Mode</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Open Mode</em>' attribute.
	 * @see org.eclipse.acceleo.parser.cst.OpenModeKind
	 * @see #getOpenMode()
	 * @generated
	 */
	void setOpenMode(OpenModeKind value);

	/**
	 * Returns the value of the '<em><b>File Url</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Url</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>File Url</em>' containment reference.
	 * @see #setFileUrl(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getFileBlock_FileUrl()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ModelExpression getFileUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.FileBlock#getFileUrl <em>File Url</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>File Url</em>' containment reference.
	 * @see #getFileUrl()
	 * @generated
	 */
	void setFileUrl(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Uniq Id</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uniq Id</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Uniq Id</em>' containment reference.
	 * @see #setUniqId(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getFileBlock_UniqId()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getUniqId();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.FileBlock#getUniqId <em>Uniq Id</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Uniq Id</em>' containment reference.
	 * @see #getUniqId()
	 * @generated
	 */
	void setUniqId(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Charset</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Charset</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Charset</em>' containment reference.
	 * @see #setCharset(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getFileBlock_Charset()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getCharset();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.FileBlock#getCharset <em>Charset</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Charset</em>' containment reference.
	 * @see #getCharset()
	 * @generated
	 */
	void setCharset(ModelExpression value);

} // FileBlock
