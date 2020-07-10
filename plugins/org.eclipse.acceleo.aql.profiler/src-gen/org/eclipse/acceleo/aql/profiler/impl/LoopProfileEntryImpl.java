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
package org.eclipse.acceleo.aql.profiler.impl;

import java.util.Collection;

import org.eclipse.acceleo.aql.profiler.LoopProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Loop Profile Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.LoopProfileEntryImpl#getLoopElements <em>Loop Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class LoopProfileEntryImpl extends ProfileEntryImpl implements LoopProfileEntry {
	/**
	 * The cached value of the '{@link #getLoopElements() <em>Loop Elements</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLoopElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ProfileEntry> loopElements;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LoopProfileEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProfilerPackage.Literals.LOOP_PROFILE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ProfileEntry> getLoopElements() {
		if (loopElements == null) {
			loopElements = new EObjectContainmentEList<ProfileEntry>(ProfileEntry.class, this,
					ProfilerPackage.LOOP_PROFILE_ENTRY__LOOP_ELEMENTS);
		}
		return loopElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProfilerPackage.LOOP_PROFILE_ENTRY__LOOP_ELEMENTS:
				return ((InternalEList<?>)getLoopElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProfilerPackage.LOOP_PROFILE_ENTRY__LOOP_ELEMENTS:
				return getLoopElements();
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
			case ProfilerPackage.LOOP_PROFILE_ENTRY__LOOP_ELEMENTS:
				getLoopElements().clear();
				getLoopElements().addAll((Collection<? extends ProfileEntry>)newValue);
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
			case ProfilerPackage.LOOP_PROFILE_ENTRY__LOOP_ELEMENTS:
				getLoopElements().clear();
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
			case ProfilerPackage.LOOP_PROFILE_ENTRY__LOOP_ELEMENTS:
				return loopElements != null && !loopElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // LoopProfileEntryImpl
