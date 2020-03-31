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
package org.eclipse.acceleo.debug.util;

import org.eclipse.acceleo.debug.Contextual;
import org.eclipse.acceleo.debug.CurrentSession;
import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.Register;
import org.eclipse.acceleo.debug.RegisterGroup;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage
 * @generated
 */
public class DebugSwitch<T> extends Switch<T> {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static DebugPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugSwitch() {
		if (modelPackage == null) {
			modelPackage = DebugPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param ePackage
	 *            the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DebugPackage.CONTEXTUAL: {
				Contextual contextual = (Contextual)theEObject;
				T result = caseContextual(contextual);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.DEBUG_TARGET: {
				DebugTarget debugTarget = (DebugTarget)theEObject;
				T result = caseDebugTarget(debugTarget);
				if (result == null)
					result = caseContextual(debugTarget);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.THREAD: {
				org.eclipse.acceleo.debug.Thread thread = (org.eclipse.acceleo.debug.Thread)theEObject;
				T result = caseThread(thread);
				if (result == null)
					result = caseContextual(thread);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.STACK_FRAME: {
				StackFrame stackFrame = (StackFrame)theEObject;
				T result = caseStackFrame(stackFrame);
				if (result == null)
					result = caseContextual(stackFrame);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.CURRENT_SESSION: {
				CurrentSession currentSession = (CurrentSession)theEObject;
				T result = caseCurrentSession(currentSession);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.REGISTER_GROUP: {
				RegisterGroup registerGroup = (RegisterGroup)theEObject;
				T result = caseRegisterGroup(registerGroup);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case DebugPackage.REGISTER: {
				Register register = (Register)theEObject;
				T result = caseRegister(register);
				if (result == null)
					result = caseVariable(register);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Contextual</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Contextual</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextual(Contextual object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Target</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDebugTarget(DebugTarget object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Thread</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Thread</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseThread(org.eclipse.acceleo.debug.Thread object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stack Frame</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stack Frame</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStackFrame(StackFrame object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Current Session</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Current Session</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCurrentSession(CurrentSession object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Register Group</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Register Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRegisterGroup(RegisterGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Register</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Register</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRegister(Register object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // DebugSwitch
