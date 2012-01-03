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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>File Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.FileBlock#getOpenMode <em>Open Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.FileBlock#getFileUrl <em>File Url</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.FileBlock#getUniqId <em>Uniq Id</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.FileBlock#getCharset <em>Charset</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getFileBlock()
 * @model
 * @generated
 */
public interface FileBlock extends Block {
	/**
	 * Returns the value of the '<em><b>Open Mode</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.model.mtl.OpenModeKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open Mode</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Open Mode</em>' attribute.
	 * @see org.eclipse.acceleo.model.mtl.OpenModeKind
	 * @see #setOpenMode(OpenModeKind)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getFileBlock_OpenMode()
	 * @model required="true"
	 * @generated
	 */
	OpenModeKind getOpenMode();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.FileBlock#getOpenMode <em>Open Mode</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Open Mode</em>' attribute.
	 * @see org.eclipse.acceleo.model.mtl.OpenModeKind
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
	 * @see #setFileUrl(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getFileBlock_FileUrl()
	 * @model containment="true" required="true"
	 * @generated
	 */
	OCLExpression getFileUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.FileBlock#getFileUrl <em>File Url</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>File Url</em>' containment reference.
	 * @see #getFileUrl()
	 * @generated
	 */
	void setFileUrl(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Uniq Id</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uniq Id</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Uniq Id</em>' containment reference.
	 * @see #setUniqId(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getFileBlock_UniqId()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getUniqId();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.FileBlock#getUniqId <em>Uniq Id</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Uniq Id</em>' containment reference.
	 * @see #getUniqId()
	 * @generated
	 */
	void setUniqId(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Charset</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Charset</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Charset</em>' containment reference.
	 * @see #setCharset(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getFileBlock_Charset()
	 * @model containment="true"
	 * @since 3.0
	 * @generated
	 */
	OCLExpression getCharset();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.FileBlock#getCharset <em>Charset</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Charset</em>' containment reference.
	 * @see #getCharset()
	 * @since 3.0
	 * @generated
	 */
	void setCharset(OCLExpression value);

} // FileBlock
