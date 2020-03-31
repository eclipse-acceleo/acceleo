/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage
 * @generated
 */
public interface DebugFactory extends EFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015, 2020 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	DebugFactory eINSTANCE = org.eclipse.acceleo.debug.impl.DebugFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Target</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Target</em>'.
	 * @generated
	 */
	DebugTarget createDebugTarget();

	/**
	 * Returns a new object of class '<em>Thread</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Thread</em>'.
	 * @generated
	 */
	Thread createThread();

	/**
	 * Returns a new object of class '<em>Stack Frame</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Stack Frame</em>'.
	 * @generated
	 */
	StackFrame createStackFrame();

	/**
	 * Returns a new object of class '<em>Variable</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Variable</em>'.
	 * @generated
	 */
	Variable createVariable();

	/**
	 * Returns a new object of class '<em>Current Session</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Current Session</em>'.
	 * @generated
	 */
	CurrentSession createCurrentSession();

	/**
	 * Returns a new object of class '<em>Register Group</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Register Group</em>'.
	 * @generated
	 */
	RegisterGroup createRegisterGroup();

	/**
	 * Returns a new object of class '<em>Register</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Register</em>'.
	 * @generated
	 */
	Register createRegister();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	DebugPackage getDebugPackage();

} // DebugFactory
