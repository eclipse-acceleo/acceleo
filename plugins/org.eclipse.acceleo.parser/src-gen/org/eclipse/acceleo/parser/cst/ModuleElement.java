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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.ModuleElement#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ModuleElement#getVisibility <em>Visibility</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModuleElement()
 * @model abstract="true"
 * @generated
 * @since 3.0
 */
public interface ModuleElement extends CSTNode {
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
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModuleElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ModuleElement#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.parser.cst.VisibilityKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visibility</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.acceleo.parser.cst.VisibilityKind
	 * @see #setVisibility(VisibilityKind)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModuleElement_Visibility()
	 * @model required="true"
	 * @generated
	 */
	VisibilityKind getVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ModuleElement#getVisibility
	 * <em>Visibility</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.acceleo.parser.cst.VisibilityKind
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(VisibilityKind value);

} // ModuleElement
