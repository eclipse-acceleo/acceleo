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
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.Module#getInput <em>Input</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Module#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Module#getImports <em>Imports</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.Module#getOwnedModuleElement <em> Owned Module Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule()
 * @model
 * @generated
 */
public interface Module extends EPackage, DocumentedElement {
	/**
	 * Returns the value of the '<em><b>Input</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.model.mtl.TypedModel}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Input</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule_Input()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<TypedModel> getInput();

	/**
	 * Returns the value of the '<em><b>Extends</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.model.mtl.Module}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Extends</em>' reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule_Extends()
	 * @model
	 * @generated
	 */
	EList<Module> getExtends();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.model.mtl.Module}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Imports</em>' reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule_Imports()
	 * @model
	 * @generated
	 */
	EList<Module> getImports();

	/**
	 * Returns the value of the '<em><b>Owned Module Element</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.model.mtl.ModuleElement}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Module Element</em>' containment reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Owned Module Element</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule_OwnedModuleElement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ModuleElement> getOwnedModuleElement();

	/**
	 * Returns the value of the '<em><b>Start Header Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Header Position</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start Header Position</em>' attribute.
	 * @see #setStartHeaderPosition(int)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule_StartHeaderPosition()
	 * @model required="true"
	 * @generated
	 * @since 3.1
	 */
	int getStartHeaderPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Module#getStartHeaderPosition
	 * <em>Start Header Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start Header Position</em>' attribute.
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @since 3.1
	 */
	void setStartHeaderPosition(int value);

	/**
	 * Returns the value of the '<em><b>End Header Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Header Position</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End Header Position</em>' attribute.
	 * @see #setEndHeaderPosition(int)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModule_EndHeaderPosition()
	 * @model required="true"
	 * @generated
	 * @since 3.1
	 */
	int getEndHeaderPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Module#getEndHeaderPosition
	 * <em>End Header Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Header Position</em>' attribute.
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @since 3.1
	 */
	void setEndHeaderPosition(int value);

} // Module
