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
import org.eclipse.acceleo.debug.Register;
import org.eclipse.acceleo.debug.RegisterGroup;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Register</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.impl.RegisterImpl#getRegisterGroup <em>Register Group</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RegisterImpl extends VariableImpl implements Register {
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
	protected RegisterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DebugPackage.Literals.REGISTER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RegisterGroup getRegisterGroup() {
		if (eContainerFeatureID() != DebugPackage.REGISTER__REGISTER_GROUP)
			return null;
		return (RegisterGroup)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetRegisterGroup(RegisterGroup newRegisterGroup, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newRegisterGroup, DebugPackage.REGISTER__REGISTER_GROUP,
				msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setRegisterGroup(RegisterGroup newRegisterGroup) {
		if (newRegisterGroup != eInternalContainer()
				|| (eContainerFeatureID() != DebugPackage.REGISTER__REGISTER_GROUP
						&& newRegisterGroup != null)) {
			if (EcoreUtil.isAncestor(this, newRegisterGroup))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRegisterGroup != null)
				msgs = ((InternalEObject)newRegisterGroup).eInverseAdd(this,
						DebugPackage.REGISTER_GROUP__REGISTERS, RegisterGroup.class, msgs);
			msgs = basicSetRegisterGroup(newRegisterGroup, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.REGISTER__REGISTER_GROUP,
					newRegisterGroup, newRegisterGroup));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DebugPackage.REGISTER__REGISTER_GROUP:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetRegisterGroup((RegisterGroup)otherEnd, msgs);
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
			case DebugPackage.REGISTER__REGISTER_GROUP:
				return basicSetRegisterGroup(null, msgs);
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
			case DebugPackage.REGISTER__REGISTER_GROUP:
				return eInternalContainer().eInverseRemove(this, DebugPackage.REGISTER_GROUP__REGISTERS,
						RegisterGroup.class, msgs);
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
			case DebugPackage.REGISTER__REGISTER_GROUP:
				return getRegisterGroup();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DebugPackage.REGISTER__REGISTER_GROUP:
				setRegisterGroup((RegisterGroup)newValue);
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
			case DebugPackage.REGISTER__REGISTER_GROUP:
				setRegisterGroup((RegisterGroup)null);
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
			case DebugPackage.REGISTER__REGISTER_GROUP:
				return getRegisterGroup() != null;
		}
		return super.eIsSet(featureID);
	}

} // RegisterImpl
