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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Register Group</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.RegisterGroup#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.RegisterGroup#getRegisters <em>Registers</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.debug.DebugPackage#getRegisterGroup()
 * @model
 * @generated
 */
public interface RegisterGroup extends EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> A name of this {@link RegisterGroup}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getRegisterGroup_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.RegisterGroup#getName <em>Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Registers</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.debug.Register}. It is bidirectional and its opposite is '
	 * {@link org.eclipse.acceleo.debug.Register#getRegisterGroup <em>Register Group</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> {@link Register Registers} grouped in
	 * this {@link RegisterGroup}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Registers</em>' containment reference list.
	 * @see org.eclipse.acceleo.debug.DebugPackage#getRegisterGroup_Registers()
	 * @see org.eclipse.acceleo.debug.Register#getRegisterGroup
	 * @model opposite="registerGroup" containment="true"
	 * @generated
	 */
	EList<Register> getRegisters();

} // RegisterGroup
