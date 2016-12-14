/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.Module#getMetamodels <em>Metamodels</em>}</li>
 *   <li>{@link org.eclipse.acceleo.Module#getExtends <em>Extends</em>}</li>
 *   <li>{@link org.eclipse.acceleo.Module#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.acceleo.Module#getModuleElements <em>Module Elements</em>}</li>
 *   <li>{@link org.eclipse.acceleo.Module#getStartHeaderPosition <em>Start Header Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.Module#getEndHeaderPosition <em>End Header Position</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getModule()
 * @model
 * @generated
 */
public interface Module extends NamedElement, DocumentedElement {
	/**
	 * Returns the value of the '<em><b>Metamodels</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.Metamodel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodels</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodels</em>' reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Metamodels()
	 * @model required="true"
	 * @generated
	 */
	EList<Metamodel> getMetamodels();

	/**
	 * Returns the value of the '<em><b>Extends</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extends</em>' attribute list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Extends()
	 * @model dataType="org.eclipse.acceleo.ModuleQualifiedName"
	 * @generated
	 */
	EList<String> getExtends();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' attribute list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Imports()
	 * @model dataType="org.eclipse.acceleo.ModuleQualifiedName"
	 * @generated
	 */
	EList<String> getImports();

	/**
	 * Returns the value of the '<em><b>Module Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.ModuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_ModuleElements()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ModuleElement> getModuleElements();

	/**
	 * Returns the value of the '<em><b>Start Header Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Header Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Header Position</em>' attribute.
	 * @see #setStartHeaderPosition(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_StartHeaderPosition()
	 * @model required="true"
	 * @generated
	 */
	int getStartHeaderPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getStartHeaderPosition <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Header Position</em>' attribute.
	 * @see #getStartHeaderPosition()
	 * @generated
	 */
	void setStartHeaderPosition(int value);

	/**
	 * Returns the value of the '<em><b>End Header Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Header Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Header Position</em>' attribute.
	 * @see #setEndHeaderPosition(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_EndHeaderPosition()
	 * @model required="true"
	 * @generated
	 */
	int getEndHeaderPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getEndHeaderPosition <em>End Header Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Header Position</em>' attribute.
	 * @see #getEndHeaderPosition()
	 * @generated
	 */
	void setEndHeaderPosition(int value);

} // Module
