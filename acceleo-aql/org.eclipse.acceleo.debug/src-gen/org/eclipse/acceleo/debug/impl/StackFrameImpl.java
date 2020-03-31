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

import java.util.Collection;

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.RegisterGroup;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Stack Frame</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getContext <em>Context</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getVariables <em>Variables</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getChildFrame <em>Child Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getCurrentInstruction <em>Current
 * Instruction</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#isCanStepIntoCurrentInstruction <em>Can Step
 * Into Current Instruction</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getParentFrame <em>Parent Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.StackFrameImpl#getRegisterGroups <em>Register Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StackFrameImpl extends EObjectImpl implements StackFrame {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected EObject context;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> variables;

	/**
	 * The cached value of the '{@link #getChildFrame() <em>Child Frame</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChildFrame()
	 * @generated
	 * @ordered
	 */
	protected StackFrame childFrame;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCurrentInstruction() <em>Current Instruction</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCurrentInstruction()
	 * @generated
	 * @ordered
	 */
	protected EObject currentInstruction;

	/**
	 * The default value of the '{@link #isCanStepIntoCurrentInstruction() <em>Can Step Into Current
	 * Instruction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isCanStepIntoCurrentInstruction()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CAN_STEP_INTO_CURRENT_INSTRUCTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCanStepIntoCurrentInstruction() <em>Can Step Into Current
	 * Instruction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isCanStepIntoCurrentInstruction()
	 * @generated
	 * @ordered
	 */
	protected boolean canStepIntoCurrentInstruction = CAN_STEP_INTO_CURRENT_INSTRUCTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRegisterGroups() <em>Register Groups</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRegisterGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<RegisterGroup> registerGroups;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected StackFrameImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DebugPackage.Literals.STACK_FRAME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject getContext() {
		if (context != null && context.eIsProxy()) {
			InternalEObject oldContext = (InternalEObject)context;
			context = eResolveProxy(oldContext);
			if (context != oldContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DebugPackage.STACK_FRAME__CONTEXT, oldContext, context));
			}
		}
		return context;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject basicGetContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setContext(EObject newContext) {
		EObject oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.STACK_FRAME__CONTEXT,
					oldContext, context));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Variable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentWithInverseEList<Variable>(Variable.class, this,
					DebugPackage.STACK_FRAME__VARIABLES, DebugPackage.VARIABLE__FRAME);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame getChildFrame() {
		return childFrame;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetChildFrame(StackFrame newChildFrame, NotificationChain msgs) {
		StackFrame oldChildFrame = childFrame;
		childFrame = newChildFrame;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DebugPackage.STACK_FRAME__CHILD_FRAME, oldChildFrame, newChildFrame);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setChildFrame(StackFrame newChildFrame) {
		if (newChildFrame != childFrame) {
			NotificationChain msgs = null;
			if (childFrame != null)
				msgs = ((InternalEObject)childFrame).eInverseRemove(this,
						DebugPackage.STACK_FRAME__PARENT_FRAME, StackFrame.class, msgs);
			if (newChildFrame != null)
				msgs = ((InternalEObject)newChildFrame).eInverseAdd(this,
						DebugPackage.STACK_FRAME__PARENT_FRAME, StackFrame.class, msgs);
			msgs = basicSetChildFrame(newChildFrame, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.STACK_FRAME__CHILD_FRAME,
					newChildFrame, newChildFrame));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.STACK_FRAME__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject getCurrentInstruction() {
		if (currentInstruction != null && currentInstruction.eIsProxy()) {
			InternalEObject oldCurrentInstruction = (InternalEObject)currentInstruction;
			currentInstruction = eResolveProxy(oldCurrentInstruction);
			if (currentInstruction != oldCurrentInstruction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DebugPackage.STACK_FRAME__CURRENT_INSTRUCTION, oldCurrentInstruction,
							currentInstruction));
			}
		}
		return currentInstruction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject basicGetCurrentInstruction() {
		return currentInstruction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCurrentInstruction(EObject newCurrentInstruction) {
		EObject oldCurrentInstruction = currentInstruction;
		currentInstruction = newCurrentInstruction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DebugPackage.STACK_FRAME__CURRENT_INSTRUCTION, oldCurrentInstruction,
					currentInstruction));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isCanStepIntoCurrentInstruction() {
		return canStepIntoCurrentInstruction;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCanStepIntoCurrentInstruction(boolean newCanStepIntoCurrentInstruction) {
		boolean oldCanStepIntoCurrentInstruction = canStepIntoCurrentInstruction;
		canStepIntoCurrentInstruction = newCanStepIntoCurrentInstruction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DebugPackage.STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION,
					oldCanStepIntoCurrentInstruction, canStepIntoCurrentInstruction));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame getParentFrame() {
		if (eContainerFeatureID() != DebugPackage.STACK_FRAME__PARENT_FRAME)
			return null;
		return (StackFrame)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetParentFrame(StackFrame newParentFrame, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentFrame, DebugPackage.STACK_FRAME__PARENT_FRAME,
				msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setParentFrame(StackFrame newParentFrame) {
		if (newParentFrame != eInternalContainer()
				|| (eContainerFeatureID() != DebugPackage.STACK_FRAME__PARENT_FRAME
						&& newParentFrame != null)) {
			if (EcoreUtil.isAncestor(this, newParentFrame))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentFrame != null)
				msgs = ((InternalEObject)newParentFrame).eInverseAdd(this,
						DebugPackage.STACK_FRAME__CHILD_FRAME, StackFrame.class, msgs);
			msgs = basicSetParentFrame(newParentFrame, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.STACK_FRAME__PARENT_FRAME,
					newParentFrame, newParentFrame));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RegisterGroup> getRegisterGroups() {
		if (registerGroups == null) {
			registerGroups = new EObjectContainmentEList<RegisterGroup>(RegisterGroup.class, this,
					DebugPackage.STACK_FRAME__REGISTER_GROUPS);
		}
		return registerGroups;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DebugPackage.STACK_FRAME__VARIABLES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getVariables()).basicAdd(otherEnd,
						msgs);
			case DebugPackage.STACK_FRAME__CHILD_FRAME:
				if (childFrame != null)
					msgs = ((InternalEObject)childFrame).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
							- DebugPackage.STACK_FRAME__CHILD_FRAME, null, msgs);
				return basicSetChildFrame((StackFrame)otherEnd, msgs);
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentFrame((StackFrame)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DebugPackage.STACK_FRAME__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case DebugPackage.STACK_FRAME__CHILD_FRAME:
				return basicSetChildFrame(null, msgs);
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				return basicSetParentFrame(null, msgs);
			case DebugPackage.STACK_FRAME__REGISTER_GROUPS:
				return ((InternalEList<?>)getRegisterGroups()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				return eInternalContainer().eInverseRemove(this, DebugPackage.STACK_FRAME__CHILD_FRAME,
						StackFrame.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DebugPackage.STACK_FRAME__CONTEXT:
				if (resolve)
					return getContext();
				return basicGetContext();
			case DebugPackage.STACK_FRAME__VARIABLES:
				return getVariables();
			case DebugPackage.STACK_FRAME__CHILD_FRAME:
				return getChildFrame();
			case DebugPackage.STACK_FRAME__NAME:
				return getName();
			case DebugPackage.STACK_FRAME__CURRENT_INSTRUCTION:
				if (resolve)
					return getCurrentInstruction();
				return basicGetCurrentInstruction();
			case DebugPackage.STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION:
				return isCanStepIntoCurrentInstruction();
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				return getParentFrame();
			case DebugPackage.STACK_FRAME__REGISTER_GROUPS:
				return getRegisterGroups();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DebugPackage.STACK_FRAME__CONTEXT:
				setContext((EObject)newValue);
				return;
			case DebugPackage.STACK_FRAME__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Variable>)newValue);
				return;
			case DebugPackage.STACK_FRAME__CHILD_FRAME:
				setChildFrame((StackFrame)newValue);
				return;
			case DebugPackage.STACK_FRAME__NAME:
				setName((String)newValue);
				return;
			case DebugPackage.STACK_FRAME__CURRENT_INSTRUCTION:
				setCurrentInstruction((EObject)newValue);
				return;
			case DebugPackage.STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION:
				setCanStepIntoCurrentInstruction((Boolean)newValue);
				return;
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				setParentFrame((StackFrame)newValue);
				return;
			case DebugPackage.STACK_FRAME__REGISTER_GROUPS:
				getRegisterGroups().clear();
				getRegisterGroups().addAll((Collection<? extends RegisterGroup>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DebugPackage.STACK_FRAME__CONTEXT:
				setContext((EObject)null);
				return;
			case DebugPackage.STACK_FRAME__VARIABLES:
				getVariables().clear();
				return;
			case DebugPackage.STACK_FRAME__CHILD_FRAME:
				setChildFrame((StackFrame)null);
				return;
			case DebugPackage.STACK_FRAME__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DebugPackage.STACK_FRAME__CURRENT_INSTRUCTION:
				setCurrentInstruction((EObject)null);
				return;
			case DebugPackage.STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION:
				setCanStepIntoCurrentInstruction(CAN_STEP_INTO_CURRENT_INSTRUCTION_EDEFAULT);
				return;
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				setParentFrame((StackFrame)null);
				return;
			case DebugPackage.STACK_FRAME__REGISTER_GROUPS:
				getRegisterGroups().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DebugPackage.STACK_FRAME__CONTEXT:
				return context != null;
			case DebugPackage.STACK_FRAME__VARIABLES:
				return variables != null && !variables.isEmpty();
			case DebugPackage.STACK_FRAME__CHILD_FRAME:
				return childFrame != null;
			case DebugPackage.STACK_FRAME__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DebugPackage.STACK_FRAME__CURRENT_INSTRUCTION:
				return currentInstruction != null;
			case DebugPackage.STACK_FRAME__CAN_STEP_INTO_CURRENT_INSTRUCTION:
				return canStepIntoCurrentInstruction != CAN_STEP_INTO_CURRENT_INSTRUCTION_EDEFAULT;
			case DebugPackage.STACK_FRAME__PARENT_FRAME:
				return getParentFrame() != null;
			case DebugPackage.STACK_FRAME__REGISTER_GROUPS:
				return registerGroups != null && !registerGroups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", canStepIntoCurrentInstruction: ");
		result.append(canStepIntoCurrentInstruction);
		result.append(')');
		return result.toString();
	}

} // StackFrameImpl
