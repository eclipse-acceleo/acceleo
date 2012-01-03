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

import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Template</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getImports <em>Imports</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getScripts <em>Scripts</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getBeginTag <em>Begin Tag</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getEndTag <em>End Tag</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getTemplate()
 * @model
 * @generated
 */
public interface Template extends Resource {
	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.compatibility.model.mt.Resource}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Imports</em>' reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getTemplate_Imports()
	 * @model
	 * @generated
	 */
	EList<Resource> getImports();

	/**
	 * Returns the value of the '<em><b>Scripts</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.compatibility.model.mt.core.Script}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scripts</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Scripts</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getTemplate_Scripts()
	 * @model containment="true"
	 * @generated
	 */
	EList<Script> getScripts();

	/**
	 * Returns the value of the '<em><b>Begin Tag</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Begin Tag</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Begin Tag</em>' attribute.
	 * @see #setBeginTag(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getTemplate_BeginTag()
	 * @model required="true"
	 * @generated
	 */
	String getBeginTag();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getBeginTag
	 * <em>Begin Tag</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Begin Tag</em>' attribute.
	 * @see #getBeginTag()
	 * @generated
	 */
	void setBeginTag(String value);

	/**
	 * Returns the value of the '<em><b>End Tag</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Tag</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End Tag</em>' attribute.
	 * @see #setEndTag(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getTemplate_EndTag()
	 * @model required="true"
	 * @generated
	 */
	String getEndTag();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getEndTag
	 * <em>End Tag</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Tag</em>' attribute.
	 * @see #getEndTag()
	 * @generated
	 */
	void setEndTag(String value);

} // Template
