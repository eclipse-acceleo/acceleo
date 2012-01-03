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

import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Template</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.Template#getOverrides <em>Overrides </em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Template#getParameter <em>Parameter </em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Template#getGuard <em>Guard</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplate()
 * @model
 * @generated
 */
public interface Template extends Block, ModuleElement, DocumentedElement {
	/**
	 * Returns the value of the '<em><b>Overrides</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.model.mtl.Template}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overrides</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Overrides</em>' reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplate_Overrides()
	 * @model
	 * @generated
	 */
	EList<Template> getOverrides();

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.ocl.ecore.Variable}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplate_Parameter()
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
	 * @see #setGuard(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplate_Guard()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Template#getGuard <em>Guard</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Guard</em>' containment reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Main</b></em>' attribute. The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Main</em>' attribute.
	 * @see #setMain(boolean)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplate_Main()
	 * @model default="false"
	 * @generated
	 */
	boolean isMain();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Template#isMain <em>Main</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Main</em>' attribute.
	 * @see #isMain()
	 * @generated
	 */
	void setMain(boolean value);

	/**
	 * Returns the value of the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Post</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Post</em>' containment reference.
	 * @see #setPost(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplate_Post()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getPost();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Template#getPost <em>Post</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Post</em>' containment reference.
	 * @see #getPost()
	 * @generated
	 */
	void setPost(OCLExpression value);

} // Template
