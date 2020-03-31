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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Variable</b></em>'. <!-- end-user-doc
 * --> <!-- begin-model-doc --> A {@link Variable} represents a visible data structure in the
 * {@link StackFrame}. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.Variable#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.Variable#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> The {@link Variable} name. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Variable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> The {@link Variable} value. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable_Value()
	 * @model dataType="org.eclipse.acceleo.debug.Object" transient="true"
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Variable#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the '<em><b>Value Changed</b></em>' attribute. The default value is
	 * <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Changed</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value Changed</em>' attribute.
	 * @see #setValueChanged(boolean)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable_ValueChanged()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isValueChanged();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Variable#isValueChanged <em>Value
	 * Changed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Value Changed</em>' attribute.
	 * @see #isValueChanged()
	 * @generated
	 */
	void setValueChanged(boolean value);

	/**
	 * Returns the value of the '<em><b>Frame</b></em>' container reference. It is bidirectional and its
	 * opposite is '{@link org.eclipse.acceleo.debug.StackFrame#getVariables <em>Variables</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frame</em>' container reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Frame</em>' container reference.
	 * @see #setFrame(StackFrame)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable_Frame()
	 * @see org.eclipse.acceleo.debug.StackFrame#getVariables
	 * @model opposite="variables" transient="false"
	 * @generated
	 */
	StackFrame getFrame();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Variable#getFrame <em>Frame</em>}' container
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Frame</em>' container reference.
	 * @see #getFrame()
	 * @generated
	 */
	void setFrame(StackFrame value);

	/**
	 * Returns the value of the '<em><b>Declaration Type</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> The {@link Variable} declaration type. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Declaration Type</em>' attribute.
	 * @see #setDeclarationType(String)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable_DeclarationType()
	 * @model required="true"
	 * @generated
	 */
	String getDeclarationType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Variable#getDeclarationType <em>Declaration
	 * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Declaration Type</em>' attribute.
	 * @see #getDeclarationType()
	 * @generated
	 */
	void setDeclarationType(String value);

	/**
	 * Returns the value of the '<em><b>Support Modifications</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc --> <!-- begin-model-doc --> Tells if the {@link Variable#getValue() value} can be
	 * changed <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Support Modifications</em>' attribute.
	 * @see #setSupportModifications(boolean)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getVariable_SupportModifications()
	 * @model required="true"
	 * @generated
	 */
	boolean isSupportModifications();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Variable#isSupportModifications <em>Support
	 * Modifications</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Support Modifications</em>' attribute.
	 * @see #isSupportModifications()
	 * @generated
	 */
	void setSupportModifications(boolean value);

} // Variable
