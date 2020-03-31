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
package org.eclipse.acceleo.debug.impl;

import org.eclipse.acceleo.debug.Contextual;
import org.eclipse.acceleo.debug.CurrentSession;
import org.eclipse.acceleo.debug.DebugFactory;
import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.DebugTargetState;
import org.eclipse.acceleo.debug.Register;
import org.eclipse.acceleo.debug.RegisterGroup;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.State;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class DebugPackageImpl extends EPackageImpl implements DebugPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass contextualEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass debugTargetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass threadEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stackFrameEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass currentSessionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass registerGroupEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass registerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum debugTargetStateEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum stateEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType objectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.debug.DebugPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DebugPackageImpl() {
		super(eNS_URI, DebugFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link DebugPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DebugPackage init() {
		if (isInited)
			return (DebugPackage)EPackage.Registry.INSTANCE.getEPackage(DebugPackage.eNS_URI);

		// Obtain or create and register package
		DebugPackageImpl theDebugPackage = (DebugPackageImpl)(EPackage.Registry.INSTANCE.get(
				eNS_URI) instanceof DebugPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new DebugPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theDebugPackage.createPackageContents();

		// Initialize created meta-data
		theDebugPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDebugPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DebugPackage.eNS_URI, theDebugPackage);
		return theDebugPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextual() {
		return contextualEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextual_Context() {
		return (EReference)contextualEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDebugTarget() {
		return debugTargetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDebugTarget_Name() {
		return (EAttribute)debugTargetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDebugTarget_State() {
		return (EAttribute)debugTargetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDebugTarget_Threads() {
		return (EReference)debugTargetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getThread() {
		return threadEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getThread_BottomStackFrame() {
		return (EReference)threadEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getThread_State() {
		return (EAttribute)threadEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getThread_TopStackFrame() {
		return (EReference)threadEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getThread_Name() {
		return (EAttribute)threadEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getThread_DebugTarget() {
		return (EReference)threadEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getThread_Priority() {
		return (EAttribute)threadEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStackFrame() {
		return stackFrameEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStackFrame_Variables() {
		return (EReference)stackFrameEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStackFrame_ChildFrame() {
		return (EReference)stackFrameEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getStackFrame_Name() {
		return (EAttribute)stackFrameEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStackFrame_CurrentInstruction() {
		return (EReference)stackFrameEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getStackFrame_CanStepIntoCurrentInstruction() {
		return (EAttribute)stackFrameEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStackFrame_ParentFrame() {
		return (EReference)stackFrameEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStackFrame_RegisterGroups() {
		return (EReference)stackFrameEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_Value() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_ValueChanged() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVariable_Frame() {
		return (EReference)variableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_DeclarationType() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_SupportModifications() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCurrentSession() {
		return currentSessionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCurrentSession_DebugTargets() {
		return (EReference)currentSessionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRegisterGroup() {
		return registerGroupEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRegisterGroup_Name() {
		return (EAttribute)registerGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRegisterGroup_Registers() {
		return (EReference)registerGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRegister() {
		return registerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRegister_RegisterGroup() {
		return (EReference)registerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getDebugTargetState() {
		return debugTargetStateEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getState() {
		return stateEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getObject() {
		return objectEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugFactory getDebugFactory() {
		return (DebugFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		contextualEClass = createEClass(CONTEXTUAL);
		createEReference(contextualEClass, CONTEXTUAL__CONTEXT);

		debugTargetEClass = createEClass(DEBUG_TARGET);
		createEAttribute(debugTargetEClass, DEBUG_TARGET__NAME);
		createEAttribute(debugTargetEClass, DEBUG_TARGET__STATE);
		createEReference(debugTargetEClass, DEBUG_TARGET__THREADS);

		threadEClass = createEClass(THREAD);
		createEReference(threadEClass, THREAD__BOTTOM_STACK_FRAME);
		createEAttribute(threadEClass, THREAD__STATE);
		createEReference(threadEClass, THREAD__TOP_STACK_FRAME);
		createEAttribute(threadEClass, THREAD__NAME);
		createEReference(threadEClass, THREAD__DEBUG_TARGET);
		createEAttribute(threadEClass, THREAD__PRIORITY);

		stackFrameEClass = createEClass(STACK_FRAME);
		createEReference(stackFrameEClass, STACK_FRAME__VARIABLES);
		createEReference(stackFrameEClass, STACK_FRAME__CHILD_FRAME);
		createEAttribute(stackFrameEClass, STACK_FRAME__NAME);
		createEReference(stackFrameEClass, STACK_FRAME__CURRENT_INSTRUCTION);
		createEAttribute(stackFrameEClass, STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION);
		createEReference(stackFrameEClass, STACK_FRAME__PARENT_FRAME);
		createEReference(stackFrameEClass, STACK_FRAME__REGISTER_GROUPS);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);
		createEAttribute(variableEClass, VARIABLE__VALUE);
		createEAttribute(variableEClass, VARIABLE__VALUE_CHANGED);
		createEReference(variableEClass, VARIABLE__FRAME);
		createEAttribute(variableEClass, VARIABLE__DECLARATION_TYPE);
		createEAttribute(variableEClass, VARIABLE__SUPPORT_MODIFICATIONS);

		currentSessionEClass = createEClass(CURRENT_SESSION);
		createEReference(currentSessionEClass, CURRENT_SESSION__DEBUG_TARGETS);

		registerGroupEClass = createEClass(REGISTER_GROUP);
		createEAttribute(registerGroupEClass, REGISTER_GROUP__NAME);
		createEReference(registerGroupEClass, REGISTER_GROUP__REGISTERS);

		registerEClass = createEClass(REGISTER);
		createEReference(registerEClass, REGISTER__REGISTER_GROUP);

		// Create enums
		debugTargetStateEEnum = createEEnum(DEBUG_TARGET_STATE);
		stateEEnum = createEEnum(STATE);

		// Create data types
		objectEDataType = createEDataType(OBJECT);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		debugTargetEClass.getESuperTypes().add(this.getContextual());
		threadEClass.getESuperTypes().add(this.getContextual());
		stackFrameEClass.getESuperTypes().add(this.getContextual());
		registerEClass.getESuperTypes().add(this.getVariable());

		// Initialize classes and features; add operations and parameters
		initEClass(contextualEClass, Contextual.class, "Contextual", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextual_Context(), ecorePackage.getEObject(), null, "context", null, 1, 1,
				Contextual.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(debugTargetEClass, DebugTarget.class, "DebugTarget", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDebugTarget_Name(), ecorePackage.getEString(), "name", null, 1, 1,
				DebugTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDebugTarget_State(), this.getDebugTargetState(), "state", null, 1, 1,
				DebugTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDebugTarget_Threads(), this.getThread(), this.getThread_DebugTarget(), "threads",
				null, 0, -1, DebugTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(threadEClass, org.eclipse.acceleo.debug.Thread.class, "Thread", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getThread_BottomStackFrame(), this.getStackFrame(), null, "bottomStackFrame", null, 1,
				1, org.eclipse.acceleo.debug.Thread.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getThread_State(), this.getState(), "state", null, 1, 1,
				org.eclipse.acceleo.debug.Thread.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThread_TopStackFrame(), this.getStackFrame(), null, "topStackFrame", null, 1, 1,
				org.eclipse.acceleo.debug.Thread.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getThread_Name(), ecorePackage.getEString(), "name", null, 1, 1,
				org.eclipse.acceleo.debug.Thread.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getThread_DebugTarget(), this.getDebugTarget(), this.getDebugTarget_Threads(),
				"debugTarget", null, 1, 1, org.eclipse.acceleo.debug.Thread.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getThread_Priority(), ecorePackage.getEInt(), "priority", "0", 1, 1,
				org.eclipse.acceleo.debug.Thread.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stackFrameEClass, StackFrame.class, "StackFrame", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStackFrame_Variables(), this.getVariable(), this.getVariable_Frame(), "variables",
				null, 0, -1, StackFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStackFrame_ChildFrame(), this.getStackFrame(), this.getStackFrame_ParentFrame(),
				"childFrame", null, 0, 1, StackFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStackFrame_Name(), ecorePackage.getEString(), "name", null, 1, 1, StackFrame.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getStackFrame_CurrentInstruction(), ecorePackage.getEObject(), null,
				"currentInstruction", null, 0, 1, StackFrame.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getStackFrame_CanStepIntoCurrentInstruction(), ecorePackage.getEBoolean(),
				"canStepIntoCurrentInstruction", "false", 1, 1, StackFrame.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStackFrame_ParentFrame(), this.getStackFrame(), this.getStackFrame_ChildFrame(),
				"parentFrame", null, 0, 1, StackFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStackFrame_RegisterGroups(), this.getRegisterGroup(), null, "registerGroups", null,
				0, -1, StackFrame.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 1, 1, Variable.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getVariable_Value(), this.getObject(), "value", null, 0, 1, Variable.class,
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getVariable_ValueChanged(), ecorePackage.getEBoolean(), "valueChanged", "false", 1, 1,
				Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVariable_Frame(), this.getStackFrame(), this.getStackFrame_Variables(), "frame",
				null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariable_DeclarationType(), ecorePackage.getEString(), "declarationType", null, 1,
				1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariable_SupportModifications(), ecorePackage.getEBoolean(), "supportModifications",
				null, 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(currentSessionEClass, CurrentSession.class, "CurrentSession", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCurrentSession_DebugTargets(), this.getDebugTarget(), null, "debugTargets", null, 0,
				-1, CurrentSession.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(registerGroupEClass, RegisterGroup.class, "RegisterGroup", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRegisterGroup_Name(), ecorePackage.getEString(), "name", null, 1, 1,
				RegisterGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRegisterGroup_Registers(), this.getRegister(), this.getRegister_RegisterGroup(),
				"registers", null, 0, -1, RegisterGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(registerEClass, Register.class, "Register", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRegister_RegisterGroup(), this.getRegisterGroup(), this
				.getRegisterGroup_Registers(), "registerGroup", null, 0, 1, Register.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(debugTargetStateEEnum, DebugTargetState.class, "DebugTargetState");
		addEEnumLiteral(debugTargetStateEEnum, DebugTargetState.CONNECTED);
		addEEnumLiteral(debugTargetStateEEnum, DebugTargetState.DISCONNECTED);
		addEEnumLiteral(debugTargetStateEEnum, DebugTargetState.TERMINATING);
		addEEnumLiteral(debugTargetStateEEnum, DebugTargetState.TERMINATED);

		initEEnum(stateEEnum, State.class, "State");
		addEEnumLiteral(stateEEnum, State.RUNNING);
		addEEnumLiteral(stateEEnum, State.STEPPING_INTO);
		addEEnumLiteral(stateEEnum, State.STEPPING_OVER);
		addEEnumLiteral(stateEEnum, State.STEPPING_RETURN);
		addEEnumLiteral(stateEEnum, State.SUSPENDING);
		addEEnumLiteral(stateEEnum, State.SUSPENDED);
		addEEnumLiteral(stateEEnum, State.TERMINATING);
		addEEnumLiteral(stateEEnum, State.TERMINATED);

		// Initialize data types
		initEDataType(objectEDataType, Object.class, "Object", !IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // DebugPackageImpl
