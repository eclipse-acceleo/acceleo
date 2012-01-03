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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Template</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.Template#getOverrides <em>Overrides</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Template#getParameter <em>Parameter</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Template#getGuard <em>Guard</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getTemplate()
 * @model
 * @generated
 * @since 3.0
 */
public interface Template extends Block, ModuleElement {
	/**
	 * Returns the value of the '<em><b>Overrides</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.parser.cst.TemplateOverridesValue}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overrides</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Overrides</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getTemplate_Overrides()
	 * @model containment="true"
	 * @generated
	 */
	EList<TemplateOverridesValue> getOverrides();

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.parser.cst.Variable}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getTemplate_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getParameter();

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Guard</em>' containment reference.
	 * @see #setGuard(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getTemplate_Guard()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Template#getGuard <em>Guard</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Guard</em>' containment reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Post</em>' containment reference.
	 * @see #setPost(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getTemplate_Post()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getPost();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Template#getPost <em>Post</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Post</em>' containment reference.
	 * @see #getPost()
	 * @generated
	 */
	void setPost(ModelExpression value);

} // Template
