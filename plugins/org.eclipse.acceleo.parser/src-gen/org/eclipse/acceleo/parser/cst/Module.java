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
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.Module#getInput <em>Input</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Module#getOwnedModuleElement <em>Owned Module Element</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Module#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.Module#getImports <em>Imports</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModule()
 * @model
 * @generated
 * @since 3.0
 */
public interface Module extends EPackage, CSTNode {
	/**
	 * Returns the value of the '<em><b>Input</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.parser.cst.TypedModel}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Input</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModule_Input()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TypedModel> getInput();

	/**
	 * Returns the value of the '<em><b>Owned Module Element</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.parser.cst.ModuleElement}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Module Element</em>' containment reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Owned Module Element</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModule_OwnedModuleElement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ModuleElement> getOwnedModuleElement();

	/**
	 * Returns the value of the '<em><b>Extends</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.parser.cst.ModuleExtendsValue}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Extends</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModule_Extends()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleExtendsValue> getExtends();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.parser.cst.ModuleImportsValue}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Imports</em>' containment reference list.
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModule_Imports()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleImportsValue> getImports();

	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Documentation</em>' containment reference.
	 * @see #setDocumentation(Documentation)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModule_Documentation()
	 * @model containment="true"
	 * @generated
	 * @since 3.1
	 */
	Documentation getDocumentation();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.Module#getDocumentation
	 * <em>Documentation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Documentation</em>' containment reference.
	 * @see #getDocumentation()
	 * @generated
	 * @since 3.1
	 */
	void setDocumentation(Documentation value);

} // Module
