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

import java.util.Collection;

import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module File</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.ModuleFileImpl#getModuleElements <em>Module Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleFileImpl extends ResourceImpl implements ModuleFile {
	/**
	 * The cached value of the '{@link #getModuleElements() <em>Module Elements</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getModuleElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleElement> moduleElements;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.MODULE_FILE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleElement> getModuleElements() {
		if (moduleElements == null) {
			moduleElements = new EObjectContainmentEList<ModuleElement>(ModuleElement.class, this, TraceabilityPackage.MODULE_FILE__MODULE_ELEMENTS);
		}
		return moduleElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_FILE__MODULE_ELEMENTS:
				return ((InternalEList<?>)getModuleElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_FILE__MODULE_ELEMENTS:
				return getModuleElements();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.MODULE_FILE__MODULE_ELEMENTS:
				getModuleElements().clear();
				getModuleElements().addAll((Collection<? extends ModuleElement>)newValue);
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
			case TraceabilityPackage.MODULE_FILE__MODULE_ELEMENTS:
				getModuleElements().clear();
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
			case TraceabilityPackage.MODULE_FILE__MODULE_ELEMENTS:
				return moduleElements != null && !moduleElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ModuleFileImpl
