/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.impl;

import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.acceleo.traceability.minimal.MinimalEObjectImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.ModuleElementImpl#getModuleElement <em>Module Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleElementImpl extends MinimalEObjectImpl.Container implements ModuleElement {
	/**
	 * The cached value of the '{@link #getModuleElement() <em>Module Element</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModuleElement()
	 * @generated
	 * @ordered
	 */
	protected EObject moduleElement;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.MODULE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getModuleElement() {
		if (moduleElement != null && moduleElement.eIsProxy()) {
			InternalEObject oldModuleElement = (InternalEObject)moduleElement;
			moduleElement = eResolveProxy(oldModuleElement);
			if (moduleElement != oldModuleElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.MODULE_ELEMENT__MODULE_ELEMENT, oldModuleElement, moduleElement));
			}
		}
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetModuleElement() {
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleElement(EObject newModuleElement) {
		EObject oldModuleElement = moduleElement;
		moduleElement = newModuleElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.MODULE_ELEMENT__MODULE_ELEMENT, oldModuleElement, moduleElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_ELEMENT__MODULE_ELEMENT:
				if (resolve) return getModuleElement();
				return basicGetModuleElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_ELEMENT__MODULE_ELEMENT:
				setModuleElement((EObject)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_ELEMENT__MODULE_ELEMENT:
				setModuleElement((EObject)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_ELEMENT__MODULE_ELEMENT:
				return moduleElement != null;
		}
		return super.eIsSet(featureID);
	}

} // ModuleElementImpl
