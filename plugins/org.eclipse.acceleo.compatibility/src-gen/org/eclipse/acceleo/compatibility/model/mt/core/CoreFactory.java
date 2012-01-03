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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage
 * @generated
 */
public interface CoreFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	CoreFactory eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.core.impl.CoreFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Template</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Template</em>'.
	 * @generated
	 */
	Template createTemplate();

	/**
	 * Returns a new object of class '<em>Script</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Script</em>'.
	 * @generated
	 */
	Script createScript();

	/**
	 * Returns a new object of class '<em>Script Descriptor</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Script Descriptor</em>'.
	 * @generated
	 */
	ScriptDescriptor createScriptDescriptor();

	/**
	 * Returns a new object of class '<em>File Path</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>File Path</em>'.
	 * @generated
	 */
	FilePath createFilePath();

	/**
	 * Returns a new object of class '<em>Metamodel</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Metamodel</em>'.
	 * @generated
	 */
	Metamodel createMetamodel();

	/**
	 * Returns a new object of class '<em>Service</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Service</em>'.
	 * @generated
	 */
	Service createService();

	/**
	 * Returns a new object of class '<em>Method</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Method</em>'.
	 * @generated
	 */
	Method createMethod();

	/**
	 * Returns a new object of class '<em>Parameter</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Parameter</em>'.
	 * @generated
	 */
	Parameter createParameter();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	CorePackage getCorePackage();

} // CoreFactory
