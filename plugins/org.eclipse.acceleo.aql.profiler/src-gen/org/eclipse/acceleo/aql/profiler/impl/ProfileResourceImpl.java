/*******************************************************************************
 * Copyright (c) 2008, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.impl;

import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileResource;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Profile Resource</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileResourceImpl#getEntry <em>Entry</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProfileResourceImpl extends EObjectImpl implements ProfileResource {
	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected ProfileEntry entry;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProfileResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProfilerPackage.Literals.PROFILE_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ProfileEntry getEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEntry(ProfileEntry newEntry, NotificationChain msgs) {
		ProfileEntry oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ProfilerPackage.PROFILE_RESOURCE__ENTRY, oldEntry, newEntry);
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
	@Override
	public void setEntry(ProfileEntry newEntry) {
		if (newEntry != entry) {
			NotificationChain msgs = null;
			if (entry != null)
				msgs = ((InternalEObject)entry).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- ProfilerPackage.PROFILE_RESOURCE__ENTRY, null, msgs);
			if (newEntry != null)
				msgs = ((InternalEObject)newEntry).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- ProfilerPackage.PROFILE_RESOURCE__ENTRY, null, msgs);
			msgs = basicSetEntry(newEntry, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_RESOURCE__ENTRY,
					newEntry, newEntry));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProfilerPackage.PROFILE_RESOURCE__ENTRY:
				return basicSetEntry(null, msgs);
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
			case ProfilerPackage.PROFILE_RESOURCE__ENTRY:
				return getEntry();
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
			case ProfilerPackage.PROFILE_RESOURCE__ENTRY:
				setEntry((ProfileEntry)newValue);
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
			case ProfilerPackage.PROFILE_RESOURCE__ENTRY:
				setEntry((ProfileEntry)null);
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
			case ProfilerPackage.PROFILE_RESOURCE__ENTRY:
				return entry != null;
		}
		return super.eIsSet(featureID);
	}

} // ProfileResourceImpl
