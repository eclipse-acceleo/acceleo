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

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Module Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.ModuleElement#getVisibility <em>Visibility</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleElement()
 * @model abstract="true"
 * @generated
 */
public interface ModuleElement extends ENamedElement, ASTNode {
	/**
	 * Returns the value of the '<em><b>Visibility</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.model.mtl.VisibilityKind}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visibility</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.acceleo.model.mtl.VisibilityKind
	 * @see #setVisibility(VisibilityKind)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleElement_Visibility()
	 * @model required="true"
	 * @generated
	 */
	VisibilityKind getVisibility();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ModuleElement#getVisibility
	 * <em>Visibility</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Visibility</em>' attribute.
	 * @see org.eclipse.acceleo.model.mtl.VisibilityKind
	 * @see #getVisibility()
	 * @generated
	 */
	void setVisibility(VisibilityKind value);

} // ModuleElement
