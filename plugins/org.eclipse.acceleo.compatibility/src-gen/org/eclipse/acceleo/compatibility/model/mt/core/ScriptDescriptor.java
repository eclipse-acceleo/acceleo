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
package org.eclipse.acceleo.compatibility.model.mt.core;

import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Script Descriptor</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getDescription <em>Description
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getFile <em>File</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getPost <em>Post</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScriptDescriptor()
 * @model
 * @generated
 */
public interface ScriptDescriptor extends ASTNode {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScriptDescriptor_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScriptDescriptor_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getType
	 * <em>Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScriptDescriptor_Description()
	 * @model required="true"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getDescription
	 * <em>Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>File</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>File</em>' containment reference.
	 * @see #setFile(FilePath)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScriptDescriptor_File()
	 * @model containment="true"
	 * @generated
	 */
	FilePath getFile();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getFile
	 * <em>File</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>File</em>' containment reference.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(FilePath value);

	/**
	 * Returns the value of the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Post</em>' containment reference.
	 * @see #setPost(Expression)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScriptDescriptor_Post()
	 * @model containment="true"
	 * @generated
	 */
	Expression getPost();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getPost
	 * <em>Post</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Post</em>' containment reference.
	 * @see #getPost()
	 * @generated
	 */
	void setPost(Expression value);

} // ScriptDescriptor
