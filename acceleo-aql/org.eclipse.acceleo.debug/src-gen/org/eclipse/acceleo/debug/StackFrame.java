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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stack Frame</b></em>'. <!--
 * end-user-doc --> <!-- begin-model-doc --> A {@link StackFrame} represents an execution context in a
 * {@link Thread}. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getVariables <em>Variables</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getChildFrame <em>Child Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getState <em>State</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getParent <em>Parent</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction <em>Current Instruction</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getParentFrame <em>Parent Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.StackFrame#getRegisterGroups <em>Register Groups</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame()
 * @model
 * @generated
 */
public interface StackFrame extends Contextual {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.debug.Variable}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * <!-- begin-model-doc --> {@link Variable Variables} accessible in this {@link StackFrame}. <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * Returns the value of the '<em><b>Child Frame</b></em>' containment reference. It is bidirectional and
	 * its opposite is '{@link org.eclipse.acceleo.debug.StackFrame#getParentFrame <em>Parent Frame</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The current child
	 * {@link StackFrame} of this {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Child Frame</em>' containment reference.
	 * @see #setChildFrame(StackFrame)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_ChildFrame()
	 * @see org.eclipse.acceleo.debug.StackFrame#getParentFrame
	 * @model opposite="parentFrame" containment="true"
	 * @generated
	 */
	StackFrame getChildFrame();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.StackFrame#getChildFrame <em>Child
	 * Frame</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Child Frame</em>' containment reference.
	 * @see #getChildFrame()
	 * @generated
	 */
	void setChildFrame(StackFrame value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> The {@link StackFrame} name. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.StackFrame#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Current Instruction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> The {@link EObject} representing the current instruction in
	 * this {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Current Instruction</em>' reference.
	 * @see #setCurrentInstruction(EObject)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_CurrentInstruction()
	 * @model
	 * @generated
	 */
	EObject getCurrentInstruction();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction <em>Current
	 * Instruction</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Current Instruction</em>' reference.
	 * @see #getCurrentInstruction()
	 * @generated
	 */
	void setCurrentInstruction(EObject value);

	/**
	 * Returns the value of the '<em><b>Can Step Into Current Instruction</b></em>' attribute. The default
	 * value is <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can Step Into Current Instruction</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Can Step Into Current Instruction</em>' attribute.
	 * @see #setCanStepIntoCurrentInstruction(boolean)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_CanStepIntoCurrentInstruction()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isCanStepIntoCurrentInstruction();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.StackFrame#isCanStepIntoCurrentInstruction
	 * <em>Can Step Into Current Instruction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Can Step Into Current Instruction</em>' attribute.
	 * @see #isCanStepIntoCurrentInstruction()
	 * @generated
	 */
	void setCanStepIntoCurrentInstruction(boolean value);

	/**
	 * Returns the value of the '<em><b>Parent Frame</b></em>' container reference. It is bidirectional and
	 * its opposite is '{@link org.eclipse.acceleo.debug.StackFrame#getChildFrame <em>Child Frame</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The current parent
	 * {@link StackFrame} of this {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Parent Frame</em>' container reference.
	 * @see #setParentFrame(StackFrame)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_ParentFrame()
	 * @see org.eclipse.acceleo.debug.StackFrame#getChildFrame
	 * @model opposite="childFrame" transient="false"
	 * @generated
	 */
	StackFrame getParentFrame();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.StackFrame#getParentFrame <em>Parent
	 * Frame</em>}' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Parent Frame</em>' container reference.
	 * @see #getParentFrame()
	 * @generated
	 */
	void setParentFrame(StackFrame value);

	/**
	 * Returns the value of the '<em><b>Register Groups</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.debug.RegisterGroup}. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> {@link RegisterGroup Register groups} accessible in this
	 * {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Register Groups</em>' containment reference list.
	 * @see org.eclipse.acceleo.debug.DebugPackage#getStackFrame_RegisterGroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<RegisterGroup> getRegisterGroups();

} // StackFrame
