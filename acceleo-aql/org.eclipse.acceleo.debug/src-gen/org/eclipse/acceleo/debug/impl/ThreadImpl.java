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

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.State;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Thread</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.impl.ThreadImpl#getBottomStackFrame <em>Bottom Stack
 * Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.ThreadImpl#getTopStackFrame <em>Top Stack Frame</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.ThreadImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.ThreadImpl#getParent <em>Parent</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.ThreadImpl#getBreakpoints <em>Breakpoints</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.ThreadImpl#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ThreadImpl extends EObjectImpl implements org.eclipse.acceleo.debug.Thread {
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
	 * The cached value of the '{@link #getBottomStackFrame() <em>Bottom Stack Frame</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBottomStackFrame()
	 * @generated
	 * @ordered
	 */
	protected StackFrame bottomStackFrame;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final State STATE_EDEFAULT = State.RUNNING;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected State state = STATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTopStackFrame() <em>Top Stack Frame</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTopStackFrame()
	 * @generated
	 * @ordered
	 */
	protected StackFrame topStackFrame;

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
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIORITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected int priority = PRIORITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ThreadImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DebugPackage.Literals.THREAD;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame getBottomStackFrame() {
		return bottomStackFrame;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBottomStackFrame(StackFrame newBottomStackFrame,
			NotificationChain msgs) {
		StackFrame oldBottomStackFrame = bottomStackFrame;
		bottomStackFrame = newBottomStackFrame;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DebugPackage.THREAD__BOTTOM_STACK_FRAME, oldBottomStackFrame, newBottomStackFrame);
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
	public void setBottomStackFrame(StackFrame newBottomStackFrame) {
		if (newBottomStackFrame != bottomStackFrame) {
			NotificationChain msgs = null;
			if (bottomStackFrame != null)
				msgs = ((InternalEObject)bottomStackFrame).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- DebugPackage.THREAD__BOTTOM_STACK_FRAME, null, msgs);
			if (newBottomStackFrame != null)
				msgs = ((InternalEObject)newBottomStackFrame).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- DebugPackage.THREAD__BOTTOM_STACK_FRAME, null, msgs);
			msgs = basicSetBottomStackFrame(newBottomStackFrame, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__BOTTOM_STACK_FRAME,
					newBottomStackFrame, newBottomStackFrame));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setState(State newState) {
		State oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__STATE, oldState,
					state));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame getTopStackFrame() {
		if (topStackFrame != null && topStackFrame.eIsProxy()) {
			InternalEObject oldTopStackFrame = (InternalEObject)topStackFrame;
			topStackFrame = (StackFrame)eResolveProxy(oldTopStackFrame);
			if (topStackFrame != oldTopStackFrame) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							DebugPackage.THREAD__TOP_STACK_FRAME, oldTopStackFrame, topStackFrame));
			}
		}
		return topStackFrame;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame basicGetTopStackFrame() {
		return topStackFrame;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTopStackFrame(StackFrame newTopStackFrame) {
		StackFrame oldTopStackFrame = topStackFrame;
		topStackFrame = newTopStackFrame;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__TOP_STACK_FRAME,
					oldTopStackFrame, topStackFrame));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DebugTarget getDebugTarget() {
		if (eContainerFeatureID() != DebugPackage.THREAD__DEBUG_TARGET)
			return null;
		return (DebugTarget)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetDebugTarget(DebugTarget newDebugTarget, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDebugTarget, DebugPackage.THREAD__DEBUG_TARGET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDebugTarget(DebugTarget newDebugTarget) {
		if (newDebugTarget != eInternalContainer()
				|| (eContainerFeatureID() != DebugPackage.THREAD__DEBUG_TARGET && newDebugTarget != null)) {
			if (EcoreUtil.isAncestor(this, newDebugTarget))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDebugTarget != null)
				msgs = ((InternalEObject)newDebugTarget).eInverseAdd(this, DebugPackage.DEBUG_TARGET__THREADS,
						DebugTarget.class, msgs);
			msgs = basicSetDebugTarget(newDebugTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__DEBUG_TARGET,
					newDebugTarget, newDebugTarget));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DebugPackage.THREAD__CONTEXT,
							oldContext, context));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__CONTEXT, oldContext,
					context));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPriority(int newPriority) {
		int oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.THREAD__PRIORITY, oldPriority,
					priority));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DebugPackage.THREAD__DEBUG_TARGET:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDebugTarget((DebugTarget)otherEnd, msgs);
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
			case DebugPackage.THREAD__BOTTOM_STACK_FRAME:
				return basicSetBottomStackFrame(null, msgs);
			case DebugPackage.THREAD__DEBUG_TARGET:
				return basicSetDebugTarget(null, msgs);
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
			case DebugPackage.THREAD__DEBUG_TARGET:
				return eInternalContainer().eInverseRemove(this, DebugPackage.DEBUG_TARGET__THREADS,
						DebugTarget.class, msgs);
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
			case DebugPackage.THREAD__CONTEXT:
				if (resolve)
					return getContext();
				return basicGetContext();
			case DebugPackage.THREAD__BOTTOM_STACK_FRAME:
				return getBottomStackFrame();
			case DebugPackage.THREAD__STATE:
				return getState();
			case DebugPackage.THREAD__TOP_STACK_FRAME:
				if (resolve)
					return getTopStackFrame();
				return basicGetTopStackFrame();
			case DebugPackage.THREAD__NAME:
				return getName();
			case DebugPackage.THREAD__DEBUG_TARGET:
				return getDebugTarget();
			case DebugPackage.THREAD__PRIORITY:
				return getPriority();
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
			case DebugPackage.THREAD__CONTEXT:
				setContext((EObject)newValue);
				return;
			case DebugPackage.THREAD__BOTTOM_STACK_FRAME:
				setBottomStackFrame((StackFrame)newValue);
				return;
			case DebugPackage.THREAD__STATE:
				setState((State)newValue);
				return;
			case DebugPackage.THREAD__TOP_STACK_FRAME:
				setTopStackFrame((StackFrame)newValue);
				return;
			case DebugPackage.THREAD__NAME:
				setName((String)newValue);
				return;
			case DebugPackage.THREAD__DEBUG_TARGET:
				setDebugTarget((DebugTarget)newValue);
				return;
			case DebugPackage.THREAD__PRIORITY:
				setPriority((Integer)newValue);
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
			case DebugPackage.THREAD__CONTEXT:
				setContext((EObject)null);
				return;
			case DebugPackage.THREAD__BOTTOM_STACK_FRAME:
				setBottomStackFrame((StackFrame)null);
				return;
			case DebugPackage.THREAD__STATE:
				setState(STATE_EDEFAULT);
				return;
			case DebugPackage.THREAD__TOP_STACK_FRAME:
				setTopStackFrame((StackFrame)null);
				return;
			case DebugPackage.THREAD__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DebugPackage.THREAD__DEBUG_TARGET:
				setDebugTarget((DebugTarget)null);
				return;
			case DebugPackage.THREAD__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
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
			case DebugPackage.THREAD__CONTEXT:
				return context != null;
			case DebugPackage.THREAD__BOTTOM_STACK_FRAME:
				return bottomStackFrame != null;
			case DebugPackage.THREAD__STATE:
				return state != STATE_EDEFAULT;
			case DebugPackage.THREAD__TOP_STACK_FRAME:
				return topStackFrame != null;
			case DebugPackage.THREAD__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DebugPackage.THREAD__DEBUG_TARGET:
				return getDebugTarget() != null;
			case DebugPackage.THREAD__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
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
		result.append(" (state: ");
		result.append(state);
		result.append(", name: ");
		result.append(name);
		result.append(", priority: ");
		result.append(priority);
		result.append(')');
		return result.toString();
	}

} // ThreadImpl
