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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Register</b></em>'. <!-- end-user-doc
 * --> <!-- begin-model-doc --> A {@link Variable} representing a register. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.Register#getRegisterGroup <em>Register Group</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getRegister()
 * @model
 * @generated
 */
public interface Register extends Variable {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Register Group</b></em>' container reference. It is bidirectional and
	 * its opposite is '{@link org.eclipse.acceleo.debug.RegisterGroup#getRegisters <em>Registers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Register Group</em>' container reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Register Group</em>' container reference.
	 * @see #setRegisterGroup(RegisterGroup)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getRegister_RegisterGroup()
	 * @see org.eclipse.acceleo.debug.RegisterGroup#getRegisters
	 * @model opposite="registers" transient="false"
	 * @generated
	 */
	RegisterGroup getRegisterGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Register#getRegisterGroup <em>Register
	 * Group</em>}' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Register Group</em>' container reference.
	 * @see #getRegisterGroup()
	 * @generated
	 */
	void setRegisterGroup(RegisterGroup value);

} // Register
