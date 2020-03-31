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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Thread</b></em>'. <!-- end-user-doc
 * --> <!-- begin-model-doc --> Current {@link Thread threads} running in this {@link DebugerTarget}. <!--
 * end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.Thread#getBottomStackFrame <em>Bottom Stack Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.Thread#getTopStackFrame <em>Top Stack Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.Thread#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.Thread#getParent <em>Parent</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.Thread#getBreakpoints <em>Breakpoints</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.Thread#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getThread()
 * @model
 * @generated
 */
public interface Thread extends Contextual {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Bottom Stack Frame</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> The current bottom {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Bottom Stack Frame</em>' reference.
	 * @see #setBottomStackFrame(StackFrame)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getThread_BottomStackFrame()
	 * @model required="true"
	 * @generated
	 */
	StackFrame getBottomStackFrame();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Thread#getBottomStackFrame <em>Bottom Stack
	 * Frame</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Bottom Stack Frame</em>' containment reference.
	 * @see #getBottomStackFrame()
	 * @generated
	 */
	void setBottomStackFrame(StackFrame value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.debug.State}. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The current {@link Thread} of this {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>State</em>' attribute.
	 * @see org.eclipse.acceleo.debug.State
	 * @see #setState(State)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getThread_State()
	 * @model required="true"
	 * @generated
	 */
	State getState();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Thread#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>State</em>' attribute.
	 * @see org.eclipse.acceleo.debug.State
	 * @see #getState()
	 * @generated
	 */
	void setState(State value);

	/**
	 * Returns the value of the '<em><b>Top Stack Frame</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> The curren top {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Top Stack Frame</em>' reference.
	 * @see #setTopStackFrame(StackFrame)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getThread_TopStackFrame()
	 * @model required="true"
	 * @generated
	 */
	StackFrame getTopStackFrame();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Thread#getTopStackFrame <em>Top Stack
	 * Frame</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Top Stack Frame</em>' reference.
	 * @see #getTopStackFrame()
	 * @generated
	 */
	void setTopStackFrame(StackFrame value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> The {@link Thread} name. It must be unique across a {@link DebugTarget}.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getThread_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Thread#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Debug Target</b></em>' container reference. It is bidirectional and
	 * its opposite is '{@link org.eclipse.acceleo.debug.DebugTarget#getThreads <em>Threads</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The {@link DebugTarget} of this
	 * {@link StackFrame}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Debug Target</em>' container reference.
	 * @see #setDebugTarget(DebugTarget)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getThread_DebugTarget()
	 * @see org.eclipse.acceleo.debug.DebugTarget#getThreads
	 * @model opposite="threads" transient="false"
	 * @generated
	 */
	DebugTarget getDebugTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Thread#getDebugTarget <em>Debug Target</em>}'
	 * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Debug Target</em>' container reference.
	 * @see #getDebugTarget()
	 * @generated
	 */
	void setDebugTarget(DebugTarget value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute. The default value is <code>"0"</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The priority of the given
	 * thread. The meaning is debugger dependant. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(int)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getThread_Priority()
	 * @model default="0" required="true"
	 * @generated
	 */
	int getPriority();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Thread#getPriority <em>Priority</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(int value);

} // Thread
