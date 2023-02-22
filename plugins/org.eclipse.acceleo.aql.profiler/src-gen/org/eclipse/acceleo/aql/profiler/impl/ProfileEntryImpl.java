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

import java.util.Collection;

import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Profile Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getDuration <em>Duration</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCallees <em>Callees</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCaller <em>Caller</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCount <em>Count</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getMonitored <em>Monitored</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getPercentage <em>Percentage</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCreationTime <em>Creation
 * Time</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProfileEntryImpl extends EObjectImpl implements ProfileEntry {
	/**
	 * The default value of the '{@link #getDuration() <em>Duration</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected static final long DURATION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getDuration() <em>Duration</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDuration()
	 * @generated
	 * @ordered
	 */
	protected long duration = DURATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCallees() <em>Callees</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCallees()
	 * @generated
	 * @ordered
	 */
	protected EList<ProfileEntry> callees;

	/**
	 * The default value of the '{@link #getCount() <em>Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected static final int COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCount() <em>Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected int count = COUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMonitored() <em>Monitored</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMonitored()
	 * @generated
	 * @ordered
	 */
	protected EObject monitored;

	/**
	 * The default value of the '{@link #getPercentage() <em>Percentage</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPercentage()
	 * @generated
	 * @ordered
	 */
	protected static final float PERCENTAGE_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getPercentage() <em>Percentage</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPercentage()
	 * @generated
	 * @ordered
	 */
	protected float percentage = PERCENTAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationTime() <em>Creation Time</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCreationTime()
	 * @generated
	 * @ordered
	 */
	protected static final long CREATION_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getCreationTime() <em>Creation Time</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCreationTime()
	 * @generated
	 * @ordered
	 */
	protected long creationTime = CREATION_TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProfileEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProfilerPackage.Literals.PROFILE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public long getDuration() {
		return duration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDuration(long newDuration) {
		long oldDuration = duration;
		duration = newDuration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__DURATION,
					oldDuration, duration));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ProfileEntry> getCallees() {
		if (callees == null) {
			callees = new EObjectContainmentWithInverseEList<ProfileEntry>(ProfileEntry.class, this,
					ProfilerPackage.PROFILE_ENTRY__CALLEES, ProfilerPackage.PROFILE_ENTRY__CALLER);
		}
		return callees;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ProfileEntry getCaller() {
		if (eContainerFeatureID() != ProfilerPackage.PROFILE_ENTRY__CALLER)
			return null;
		return (ProfileEntry)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCaller(ProfileEntry newCaller, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newCaller, ProfilerPackage.PROFILE_ENTRY__CALLER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setCaller(ProfileEntry newCaller) {
		if (newCaller != eInternalContainer()
				|| (eContainerFeatureID() != ProfilerPackage.PROFILE_ENTRY__CALLER && newCaller != null)) {
			if (EcoreUtil.isAncestor(this, newCaller))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newCaller != null)
				msgs = ((InternalEObject)newCaller).eInverseAdd(this, ProfilerPackage.PROFILE_ENTRY__CALLEES,
						ProfileEntry.class, msgs);
			msgs = basicSetCaller(newCaller, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__CALLER,
					newCaller, newCaller));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getCount() {
		return count;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setCount(int newCount) {
		int oldCount = count;
		count = newCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__COUNT,
					oldCount, count));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject getMonitored() {
		if (monitored != null && monitored.eIsProxy()) {
			InternalEObject oldMonitored = (InternalEObject)monitored;
			monitored = eResolveProxy(oldMonitored);
			if (monitored != oldMonitored) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ProfilerPackage.PROFILE_ENTRY__MONITORED, oldMonitored, monitored));
			}
		}
		return monitored;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject basicGetMonitored() {
		return monitored;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMonitored(EObject newMonitored) {
		EObject oldMonitored = monitored;
		monitored = newMonitored;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__MONITORED,
					oldMonitored, monitored));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public float getPercentage() {
		return percentage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setPercentage(float newPercentage) {
		float oldPercentage = percentage;
		percentage = newPercentage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__PERCENTAGE,
					oldPercentage, percentage));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public long getCreationTime() {
		return creationTime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setCreationTime(long newCreationTime) {
		long oldCreationTime = creationTime;
		creationTime = newCreationTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ProfilerPackage.PROFILE_ENTRY__CREATION_TIME, oldCreationTime, creationTime));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void start() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void stop() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
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
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCallees()).basicAdd(otherEnd,
						msgs);
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetCaller((ProfileEntry)otherEnd, msgs);
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
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				return ((InternalEList<?>)getCallees()).basicRemove(otherEnd, msgs);
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				return basicSetCaller(null, msgs);
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
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				return eInternalContainer().eInverseRemove(this, ProfilerPackage.PROFILE_ENTRY__CALLEES,
						ProfileEntry.class, msgs);
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
			case ProfilerPackage.PROFILE_ENTRY__DURATION:
				return getDuration();
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				return getCallees();
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				return getCaller();
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
				return getCount();
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				if (resolve)
					return getMonitored();
				return basicGetMonitored();
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				return getPercentage();
			case ProfilerPackage.PROFILE_ENTRY__CREATION_TIME:
				return getCreationTime();
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
			case ProfilerPackage.PROFILE_ENTRY__DURATION:
				setDuration((Long)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				getCallees().clear();
				getCallees().addAll((Collection<? extends ProfileEntry>)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				setCaller((ProfileEntry)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
				setCount((Integer)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				setMonitored((EObject)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				setPercentage((Float)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CREATION_TIME:
				setCreationTime((Long)newValue);
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
			case ProfilerPackage.PROFILE_ENTRY__DURATION:
				setDuration(DURATION_EDEFAULT);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				getCallees().clear();
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				setCaller((ProfileEntry)null);
				return;
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
				setCount(COUNT_EDEFAULT);
				return;
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				setMonitored((EObject)null);
				return;
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				setPercentage(PERCENTAGE_EDEFAULT);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CREATION_TIME:
				setCreationTime(CREATION_TIME_EDEFAULT);
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
			case ProfilerPackage.PROFILE_ENTRY__DURATION:
				return duration != DURATION_EDEFAULT;
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				return callees != null && !callees.isEmpty();
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				return getCaller() != null;
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
				return count != COUNT_EDEFAULT;
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				return monitored != null;
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				return percentage != PERCENTAGE_EDEFAULT;
			case ProfilerPackage.PROFILE_ENTRY__CREATION_TIME:
				return creationTime != CREATION_TIME_EDEFAULT;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (duration: ");
		result.append(duration);
		result.append(", count: ");
		result.append(count);
		result.append(", percentage: ");
		result.append(percentage);
		result.append(", creationTime: ");
		result.append(creationTime);
		result.append(')');
		return result.toString();
	}

} // ProfileEntryImpl
