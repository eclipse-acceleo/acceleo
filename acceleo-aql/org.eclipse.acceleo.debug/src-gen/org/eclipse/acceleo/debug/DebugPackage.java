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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.debug.DebugFactory
 * @model kind="package"
 * @generated
 */
public interface DebugPackage extends EPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "debug";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/acceleo/debug";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "debug";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	DebugPackage eINSTANCE = org.eclipse.acceleo.debug.impl.DebugPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.Contextual <em>Contextual</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.Contextual
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getContextual()
	 * @generated
	 */
	int CONTEXTUAL = 0;

	/**
	 * The feature id for the '<em><b>Context</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXTUAL__CONTEXT = 0;

	/**
	 * The number of structural features of the '<em>Contextual</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXTUAL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.DebugTargetImpl <em>Target</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.DebugTargetImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getDebugTarget()
	 * @generated
	 */
	int DEBUG_TARGET = 1;

	/**
	 * The feature id for the '<em><b>Context</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEBUG_TARGET__CONTEXT = CONTEXTUAL__CONTEXT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEBUG_TARGET__NAME = CONTEXTUAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEBUG_TARGET__STATE = CONTEXTUAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Threads</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEBUG_TARGET__THREADS = CONTEXTUAL_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Target</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEBUG_TARGET_FEATURE_COUNT = CONTEXTUAL_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.ThreadImpl <em>Thread</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.ThreadImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getThread()
	 * @generated
	 */
	int THREAD = 2;

	/**
	 * The feature id for the '<em><b>Context</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__CONTEXT = CONTEXTUAL__CONTEXT;

	/**
	 * The feature id for the '<em><b>Bottom Stack Frame</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__BOTTOM_STACK_FRAME = CONTEXTUAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__STATE = CONTEXTUAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Top Stack Frame</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__TOP_STACK_FRAME = CONTEXTUAL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__NAME = CONTEXTUAL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Debug Target</b></em>' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__DEBUG_TARGET = CONTEXTUAL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD__PRIORITY = CONTEXTUAL_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Thread</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int THREAD_FEATURE_COUNT = CONTEXTUAL_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.StackFrameImpl <em>Stack Frame</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.StackFrameImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getStackFrame()
	 * @generated
	 */
	int STACK_FRAME = 3;

	/**
	 * The feature id for the '<em><b>Context</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__CONTEXT = CONTEXTUAL__CONTEXT;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__VARIABLES = CONTEXTUAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child Frame</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__CHILD_FRAME = CONTEXTUAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__NAME = CONTEXTUAL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Current Instruction</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__CURRENT_INSTRUCTION = CONTEXTUAL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Can Step Into Current Instruction</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION = CONTEXTUAL_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parent Frame</b></em>' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__PARENT_FRAME = CONTEXTUAL_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Register Groups</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME__REGISTER_GROUPS = CONTEXTUAL_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Stack Frame</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STACK_FRAME_FEATURE_COUNT = CONTEXTUAL_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.VariableImpl <em>Variable</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.VariableImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value Changed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__VALUE_CHANGED = 2;

	/**
	 * The feature id for the '<em><b>Frame</b></em>' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__FRAME = 3;

	/**
	 * The feature id for the '<em><b>Declaration Type</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__DECLARATION_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Support Modifications</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__SUPPORT_MODIFICATIONS = 5;

	/**
	 * The number of structural features of the '<em>Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.CurrentSessionImpl <em>Current
	 * Session</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.CurrentSessionImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getCurrentSession()
	 * @generated
	 */
	int CURRENT_SESSION = 5;

	/**
	 * The feature id for the '<em><b>Debug Targets</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CURRENT_SESSION__DEBUG_TARGETS = 0;

	/**
	 * The number of structural features of the '<em>Current Session</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CURRENT_SESSION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.RegisterGroupImpl <em>Register
	 * Group</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.RegisterGroupImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getRegisterGroup()
	 * @generated
	 */
	int REGISTER_GROUP = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER_GROUP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Registers</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER_GROUP__REGISTERS = 1;

	/**
	 * The number of structural features of the '<em>Register Group</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER_GROUP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.impl.RegisterImpl <em>Register</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.impl.RegisterImpl
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getRegister()
	 * @generated
	 */
	int REGISTER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__VALUE = VARIABLE__VALUE;

	/**
	 * The feature id for the '<em><b>Value Changed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__VALUE_CHANGED = VARIABLE__VALUE_CHANGED;

	/**
	 * The feature id for the '<em><b>Frame</b></em>' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__FRAME = VARIABLE__FRAME;

	/**
	 * The feature id for the '<em><b>Declaration Type</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__DECLARATION_TYPE = VARIABLE__DECLARATION_TYPE;

	/**
	 * The feature id for the '<em><b>Support Modifications</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__SUPPORT_MODIFICATIONS = VARIABLE__SUPPORT_MODIFICATIONS;

	/**
	 * The feature id for the '<em><b>Register Group</b></em>' container reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER__REGISTER_GROUP = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Register</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REGISTER_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.DebugTargetState <em>Target State</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.DebugTargetState
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getDebugTargetState()
	 * @generated
	 */
	int DEBUG_TARGET_STATE = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.debug.State <em>State</em>}' enum. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.debug.State
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getState()
	 * @generated
	 */
	int STATE = 9;

	/**
	 * The meta object id for the '<em>Object</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see java.lang.Object
	 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getObject()
	 * @generated
	 */
	int OBJECT = 10;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.Contextual <em>Contextual</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Contextual</em>'.
	 * @see org.eclipse.acceleo.debug.Contextual
	 * @generated
	 */
	EClass getContextual();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.debug.Contextual#getContext
	 * <em>Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Context</em>'.
	 * @see org.eclipse.acceleo.debug.Contextual#getContext()
	 * @see #getContextual()
	 * @generated
	 */
	EReference getContextual_Context();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.DebugTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Target</em>'.
	 * @see org.eclipse.acceleo.debug.DebugTarget
	 * @generated
	 */
	EClass getDebugTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.DebugTarget#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.debug.DebugTarget#getName()
	 * @see #getDebugTarget()
	 * @generated
	 */
	EAttribute getDebugTarget_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.DebugTarget#getState
	 * <em>State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see org.eclipse.acceleo.debug.DebugTarget#getState()
	 * @see #getDebugTarget()
	 * @generated
	 */
	EAttribute getDebugTarget_State();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.debug.DebugTarget#getThreads <em>Threads</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Threads</em>'.
	 * @see org.eclipse.acceleo.debug.DebugTarget#getThreads()
	 * @see #getDebugTarget()
	 * @generated
	 */
	EReference getDebugTarget_Threads();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.Thread <em>Thread</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Thread</em>'.
	 * @see org.eclipse.acceleo.debug.Thread
	 * @generated
	 */
	EClass getThread();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.debug.Thread#getBottomStackFrame <em>Bottom Stack Frame</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Bottom Stack Frame</em>'.
	 * @see org.eclipse.acceleo.debug.Thread#getBottomStackFrame()
	 * @see #getThread()
	 * @generated
	 */
	EReference getThread_BottomStackFrame();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Thread#getState
	 * <em>State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see org.eclipse.acceleo.debug.Thread#getState()
	 * @see #getThread()
	 * @generated
	 */
	EAttribute getThread_State();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.debug.Thread#getTopStackFrame
	 * <em>Top Stack Frame</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Top Stack Frame</em>'.
	 * @see org.eclipse.acceleo.debug.Thread#getTopStackFrame()
	 * @see #getThread()
	 * @generated
	 */
	EReference getThread_TopStackFrame();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Thread#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.debug.Thread#getName()
	 * @see #getThread()
	 * @generated
	 */
	EAttribute getThread_Name();

	/**
	 * Returns the meta object for the container reference
	 * '{@link org.eclipse.acceleo.debug.Thread#getDebugTarget <em>Debug Target</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Debug Target</em>'.
	 * @see org.eclipse.acceleo.debug.Thread#getDebugTarget()
	 * @see #getThread()
	 * @generated
	 */
	EReference getThread_DebugTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Thread#getPriority
	 * <em>Priority</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.acceleo.debug.Thread#getPriority()
	 * @see #getThread()
	 * @generated
	 */
	EAttribute getThread_Priority();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.StackFrame <em>Stack Frame</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Stack Frame</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame
	 * @generated
	 */
	EClass getStackFrame();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.debug.StackFrame#getVariables <em>Variables</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#getVariables()
	 * @see #getStackFrame()
	 * @generated
	 */
	EReference getStackFrame_Variables();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.debug.StackFrame#getChildFrame <em>Child Frame</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Child Frame</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#getChildFrame()
	 * @see #getStackFrame()
	 * @generated
	 */
	EReference getStackFrame_ChildFrame();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.StackFrame#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#getName()
	 * @see #getStackFrame()
	 * @generated
	 */
	EAttribute getStackFrame_Name();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction <em>Current Instruction</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Current Instruction</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction()
	 * @see #getStackFrame()
	 * @generated
	 */
	EReference getStackFrame_CurrentInstruction();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.debug.StackFrame#isCanStepIntoCurrentInstruction <em>Can Step Into Current
	 * Instruction</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Can Step Into Current Instruction</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#isCanStepIntoCurrentInstruction()
	 * @see #getStackFrame()
	 * @generated
	 */
	EAttribute getStackFrame_CanStepIntoCurrentInstruction();

	/**
	 * Returns the meta object for the container reference
	 * '{@link org.eclipse.acceleo.debug.StackFrame#getParentFrame <em>Parent Frame</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Parent Frame</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#getParentFrame()
	 * @see #getStackFrame()
	 * @generated
	 */
	EReference getStackFrame_ParentFrame();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.debug.StackFrame#getRegisterGroups <em>Register Groups</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Register Groups</em>'.
	 * @see org.eclipse.acceleo.debug.StackFrame#getRegisterGroups()
	 * @see #getStackFrame()
	 * @generated
	 */
	EReference getStackFrame_RegisterGroups();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.Variable <em>Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.acceleo.debug.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Variable#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.debug.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Variable#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.debug.Variable#getValue()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Variable#isValueChanged
	 * <em>Value Changed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value Changed</em>'.
	 * @see org.eclipse.acceleo.debug.Variable#isValueChanged()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_ValueChanged();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.acceleo.debug.Variable#getFrame
	 * <em>Frame</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Frame</em>'.
	 * @see org.eclipse.acceleo.debug.Variable#getFrame()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Frame();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.Variable#getDeclarationType
	 * <em>Declaration Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Declaration Type</em>'.
	 * @see org.eclipse.acceleo.debug.Variable#getDeclarationType()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_DeclarationType();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.debug.Variable#isSupportModifications <em>Support Modifications</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Support Modifications</em>'.
	 * @see org.eclipse.acceleo.debug.Variable#isSupportModifications()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_SupportModifications();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.CurrentSession <em>Current
	 * Session</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Current Session</em>'.
	 * @see org.eclipse.acceleo.debug.CurrentSession
	 * @generated
	 */
	EClass getCurrentSession();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.debug.CurrentSession#getDebugTargets <em>Debug Targets</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Debug Targets</em>'.
	 * @see org.eclipse.acceleo.debug.CurrentSession#getDebugTargets()
	 * @see #getCurrentSession()
	 * @generated
	 */
	EReference getCurrentSession_DebugTargets();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.RegisterGroup <em>Register
	 * Group</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Register Group</em>'.
	 * @see org.eclipse.acceleo.debug.RegisterGroup
	 * @generated
	 */
	EClass getRegisterGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.debug.RegisterGroup#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.debug.RegisterGroup#getName()
	 * @see #getRegisterGroup()
	 * @generated
	 */
	EAttribute getRegisterGroup_Name();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.debug.RegisterGroup#getRegisters <em>Registers</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Registers</em>'.
	 * @see org.eclipse.acceleo.debug.RegisterGroup#getRegisters()
	 * @see #getRegisterGroup()
	 * @generated
	 */
	EReference getRegisterGroup_Registers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.debug.Register <em>Register</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Register</em>'.
	 * @see org.eclipse.acceleo.debug.Register
	 * @generated
	 */
	EClass getRegister();

	/**
	 * Returns the meta object for the container reference
	 * '{@link org.eclipse.acceleo.debug.Register#getRegisterGroup <em>Register Group</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Register Group</em>'.
	 * @see org.eclipse.acceleo.debug.Register#getRegisterGroup()
	 * @see #getRegister()
	 * @generated
	 */
	EReference getRegister_RegisterGroup();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.debug.DebugTargetState <em>Target
	 * State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Target State</em>'.
	 * @see org.eclipse.acceleo.debug.DebugTargetState
	 * @generated
	 */
	EEnum getDebugTargetState();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.debug.State <em>State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>State</em>'.
	 * @see org.eclipse.acceleo.debug.State
	 * @generated
	 */
	EEnum getState();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>Object</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Object</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object" serializeable="false"
	 * @generated
	 */
	EDataType getObject();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DebugFactory getDebugFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.Contextual <em>Contextual</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.Contextual
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getContextual()
		 * @generated
		 */
		EClass CONTEXTUAL = eINSTANCE.getContextual();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXTUAL__CONTEXT = eINSTANCE.getContextual_Context();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.DebugTargetImpl
		 * <em>Target</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.DebugTargetImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getDebugTarget()
		 * @generated
		 */
		EClass DEBUG_TARGET = eINSTANCE.getDebugTarget();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEBUG_TARGET__NAME = eINSTANCE.getDebugTarget_Name();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DEBUG_TARGET__STATE = eINSTANCE.getDebugTarget_State();

		/**
		 * The meta object literal for the '<em><b>Threads</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DEBUG_TARGET__THREADS = eINSTANCE.getDebugTarget_Threads();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.ThreadImpl <em>Thread</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.ThreadImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getThread()
		 * @generated
		 */
		EClass THREAD = eINSTANCE.getThread();

		/**
		 * The meta object literal for the '<em><b>Bottom Stack Frame</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference THREAD__BOTTOM_STACK_FRAME = eINSTANCE.getThread_BottomStackFrame();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute THREAD__STATE = eINSTANCE.getThread_State();

		/**
		 * The meta object literal for the '<em><b>Top Stack Frame</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference THREAD__TOP_STACK_FRAME = eINSTANCE.getThread_TopStackFrame();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute THREAD__NAME = eINSTANCE.getThread_Name();

		/**
		 * The meta object literal for the '<em><b>Debug Target</b></em>' container reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference THREAD__DEBUG_TARGET = eINSTANCE.getThread_DebugTarget();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute THREAD__PRIORITY = eINSTANCE.getThread_Priority();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.StackFrameImpl <em>Stack
		 * Frame</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.StackFrameImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getStackFrame()
		 * @generated
		 */
		EClass STACK_FRAME = eINSTANCE.getStackFrame();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STACK_FRAME__VARIABLES = eINSTANCE.getStackFrame_Variables();

		/**
		 * The meta object literal for the '<em><b>Child Frame</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STACK_FRAME__CHILD_FRAME = eINSTANCE.getStackFrame_ChildFrame();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STACK_FRAME__NAME = eINSTANCE.getStackFrame_Name();

		/**
		 * The meta object literal for the '<em><b>Current Instruction</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STACK_FRAME__CURRENT_INSTRUCTION = eINSTANCE.getStackFrame_CurrentInstruction();

		/**
		 * The meta object literal for the '<em><b>Can Step Into Current Instruction</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION = eINSTANCE
				.getStackFrame_CanStepIntoCurrentInstruction();

		/**
		 * The meta object literal for the '<em><b>Parent Frame</b></em>' container reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STACK_FRAME__PARENT_FRAME = eINSTANCE.getStackFrame_ParentFrame();

		/**
		 * The meta object literal for the '<em><b>Register Groups</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STACK_FRAME__REGISTER_GROUPS = eINSTANCE.getStackFrame_RegisterGroups();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.VariableImpl
		 * <em>Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.VariableImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__VALUE = eINSTANCE.getVariable_Value();

		/**
		 * The meta object literal for the '<em><b>Value Changed</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__VALUE_CHANGED = eINSTANCE.getVariable_ValueChanged();

		/**
		 * The meta object literal for the '<em><b>Frame</b></em>' container reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VARIABLE__FRAME = eINSTANCE.getVariable_Frame();

		/**
		 * The meta object literal for the '<em><b>Declaration Type</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__DECLARATION_TYPE = eINSTANCE.getVariable_DeclarationType();

		/**
		 * The meta object literal for the '<em><b>Support Modifications</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__SUPPORT_MODIFICATIONS = eINSTANCE.getVariable_SupportModifications();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.CurrentSessionImpl
		 * <em>Current Session</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.CurrentSessionImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getCurrentSession()
		 * @generated
		 */
		EClass CURRENT_SESSION = eINSTANCE.getCurrentSession();

		/**
		 * The meta object literal for the '<em><b>Debug Targets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CURRENT_SESSION__DEBUG_TARGETS = eINSTANCE.getCurrentSession_DebugTargets();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.RegisterGroupImpl
		 * <em>Register Group</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.RegisterGroupImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getRegisterGroup()
		 * @generated
		 */
		EClass REGISTER_GROUP = eINSTANCE.getRegisterGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REGISTER_GROUP__NAME = eINSTANCE.getRegisterGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Registers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REGISTER_GROUP__REGISTERS = eINSTANCE.getRegisterGroup_Registers();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.impl.RegisterImpl
		 * <em>Register</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.impl.RegisterImpl
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getRegister()
		 * @generated
		 */
		EClass REGISTER = eINSTANCE.getRegister();

		/**
		 * The meta object literal for the '<em><b>Register Group</b></em>' container reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REGISTER__REGISTER_GROUP = eINSTANCE.getRegister_RegisterGroup();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.DebugTargetState <em>Target
		 * State</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.DebugTargetState
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getDebugTargetState()
		 * @generated
		 */
		EEnum DEBUG_TARGET_STATE = eINSTANCE.getDebugTargetState();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.debug.State <em>State</em>}' enum. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.debug.State
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getState()
		 * @generated
		 */
		EEnum STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em>Object</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.lang.Object
		 * @see org.eclipse.acceleo.debug.impl.DebugPackageImpl#getObject()
		 * @generated
		 */
		EDataType OBJECT = eINSTANCE.getObject();

	}

} // DebugPackage
