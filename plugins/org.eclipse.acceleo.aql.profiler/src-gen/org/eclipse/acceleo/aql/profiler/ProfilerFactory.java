/*******************************************************************************
 * Copyright (c) 2008, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage
 * @generated
 */
public interface ProfilerFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ProfilerFactory eINSTANCE = org.eclipse.acceleo.aql.profiler.impl.ProfilerFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Profile Entry</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Profile Entry</em>'.
	 * @generated
	 */
	ProfileEntry createProfileEntry();

	/**
	 * Returns a new object of class '<em>Profile Resource</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Profile Resource</em>'.
	 * @generated
	 */
	ProfileResource createProfileResource();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProfilerPackage getProfilerPackage();

} // ProfilerFactory
