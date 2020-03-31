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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class DebugFactoryImpl extends EFactoryImpl implements DebugFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static DebugFactory init() {
		try {
			DebugFactory theDebugFactory = (DebugFactory)EPackage.Registry.INSTANCE.getEFactory(
					DebugPackage.eNS_URI);
			if (theDebugFactory != null) {
				return theDebugFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DebugFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DebugPackage.DEBUG_TARGET:
				return createDebugTarget();
			case DebugPackage.THREAD:
				return createThread();
			case DebugPackage.STACK_FRAME:
				return createStackFrame();
			case DebugPackage.VARIABLE:
				return createVariable();
			case DebugPackage.CURRENT_SESSION:
				return createCurrentSession();
			case DebugPackage.REGISTER_GROUP:
				return createRegisterGroup();
			case DebugPackage.REGISTER:
				return createRegister();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DebugPackage.DEBUG_TARGET_STATE:
				return createDebugTargetStateFromString(eDataType, initialValue);
			case DebugPackage.STATE:
				return createStateFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DebugPackage.DEBUG_TARGET_STATE:
				return convertDebugTargetStateToString(eDataType, instanceValue);
			case DebugPackage.STATE:
				return convertStateToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugTarget createDebugTarget() {
		DebugTargetImpl debugTarget = new DebugTargetImpl();
		return debugTarget;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public org.eclipse.acceleo.debug.Thread createThread() {
		ThreadImpl thread = new ThreadImpl();
		return thread;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame createStackFrame() {
		StackFrameImpl stackFrame = new StackFrameImpl();
		return stackFrame;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CurrentSession createCurrentSession() {
		CurrentSessionImpl currentSession = new CurrentSessionImpl();
		return currentSession;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RegisterGroup createRegisterGroup() {
		RegisterGroupImpl registerGroup = new RegisterGroupImpl();
		return registerGroup;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Register createRegister() {
		RegisterImpl register = new RegisterImpl();
		return register;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugTargetState createDebugTargetStateFromString(EDataType eDataType, String initialValue) {
		DebugTargetState result = DebugTargetState.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertDebugTargetStateToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State createStateFromString(EDataType eDataType, String initialValue) {
		State result = State.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertStateToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugPackage getDebugPackage() {
		return (DebugPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DebugPackage getPackage() {
		return DebugPackage.eINSTANCE;
	}

} // DebugFactoryImpl
