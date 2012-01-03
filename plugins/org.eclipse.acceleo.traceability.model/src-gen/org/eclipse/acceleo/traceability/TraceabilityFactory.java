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
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage
 * @generated
 */
public interface TraceabilityFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	TraceabilityFactory eINSTANCE = org.eclipse.acceleo.traceability.impl.TraceabilityFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	TraceabilityModel createTraceabilityModel();

	/**
	 * Returns a new object of class '<em>Resource</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource</em>'.
	 * @generated
	 */
	Resource createResource();

	/**
	 * Returns a new object of class '<em>Model File</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Model File</em>'.
	 * @generated
	 */
	ModelFile createModelFile();

	/**
	 * Returns a new object of class '<em>Module File</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Module File</em>'.
	 * @generated
	 */
	ModuleFile createModuleFile();

	/**
	 * Returns a new object of class '<em>Generated File</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Generated File</em>'.
	 * @generated
	 */
	GeneratedFile createGeneratedFile();

	/**
	 * Returns a new object of class '<em>Input Element</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Input Element</em>'.
	 * @generated
	 */
	InputElement createInputElement();

	/**
	 * Returns a new object of class '<em>Module Element</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Element</em>'.
	 * @generated
	 */
	ModuleElement createModuleElement();

	/**
	 * Returns a new object of class '<em>Generated Text</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>Generated Text</em>'.
	 * @generated
	 */
	GeneratedText createGeneratedText();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TraceabilityPackage getTraceabilityPackage();

} // TraceabilityFactory
