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
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getDuration <em>Duration</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCallees <em>Callees</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCaller <em>Caller</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCount <em>Count</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getPercentage <em>Percentage</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getCreateTime <em>Create Time</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl#getMonitored <em>Monitored</em>}</li>
 * </ul>
 * </p>
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
	protected static final long COUNT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getCount() <em>Count</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected long count = COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPercentage() <em>Percentage</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPercentage()
	 * @generated
	 * @ordered
	 */
	protected static final double PERCENTAGE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPercentage() <em>Percentage</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPercentage()
	 * @generated
	 * @ordered
	 */
	protected double percentage = PERCENTAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreateTime() <em>Create Time</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getCreateTime()
	 * @generated
	 * @ordered
	 */
	protected static final long CREATE_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getCreateTime() <em>Create Time</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getCreateTime()
	 * @generated
	 * @ordered
	 */
	protected long createTime = CREATE_TIME_EDEFAULT;

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
	public long getDuration() {
		return duration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDuration(long newDuration) {
		long oldDuration = duration;
		duration = newDuration;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__DURATION,
					oldDuration, duration));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
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
	public ProfileEntry getCaller() {
		if (eContainerFeatureID != ProfilerPackage.PROFILE_ENTRY__CALLER) {
			return null;
		}
		return (ProfileEntry)eContainer();
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
	public void setCaller(ProfileEntry newCaller) {
		if (newCaller != eInternalContainer()
				|| (eContainerFeatureID != ProfilerPackage.PROFILE_ENTRY__CALLER && newCaller != null)) {
			if (EcoreUtil.isAncestor(this, newCaller)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newCaller != null) {
				msgs = ((InternalEObject)newCaller).eInverseAdd(this, ProfilerPackage.PROFILE_ENTRY__CALLEES,
						ProfileEntry.class, msgs);
			}
			msgs = basicSetCaller(newCaller, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__CALLER,
					newCaller, newCaller));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public long getCount() {
		return count;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCount(long newCount) {
		long oldCount = count;
		count = newCount;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__COUNT,
					oldCount, count));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPercentage(double newPercentage) {
		double oldPercentage = percentage;
		percentage = newPercentage;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__PERCENTAGE,
					oldPercentage, percentage));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCreateTime(long newCreateTime) {
		long oldCreateTime = createTime;
		createTime = newCreateTime;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__CREATE_TIME,
					oldCreateTime, createTime));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObject getMonitored() {
		if (monitored != null && monitored.eIsProxy()) {
			InternalEObject oldMonitored = (InternalEObject)monitored;
			monitored = eResolveProxy(oldMonitored);
			if (monitored != oldMonitored) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ProfilerPackage.PROFILE_ENTRY__MONITORED, oldMonitored, monitored));
				}
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
	public void setMonitored(EObject newMonitored) {
		EObject oldMonitored = monitored;
		monitored = newMonitored;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ProfilerPackage.PROFILE_ENTRY__MONITORED,
					oldMonitored, monitored));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
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
				if (eInternalContainer() != null) {
					msgs = eBasicRemoveFromContainer(msgs);
				}
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
		switch (eContainerFeatureID) {
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
				return new Long(getDuration());
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				return getCallees();
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				return getCaller();
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
				return new Long(getCount());
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				return new Double(getPercentage());
			case ProfilerPackage.PROFILE_ENTRY__CREATE_TIME:
				return new Long(getCreateTime());
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				if (resolve) {
					return getMonitored();
				}
				return basicGetMonitored();
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
				setDuration(((Long)newValue).longValue());
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				getCallees().clear();
				getCallees().addAll((Collection<? extends ProfileEntry>)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLER:
				setCaller((ProfileEntry)newValue);
				return;
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
				setCount(((Long)newValue).longValue());
				return;
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				setPercentage(((Double)newValue).doubleValue());
				return;
			case ProfilerPackage.PROFILE_ENTRY__CREATE_TIME:
				setCreateTime(((Long)newValue).longValue());
				return;
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				setMonitored((EObject)newValue);
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
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				setPercentage(PERCENTAGE_EDEFAULT);
				return;
			case ProfilerPackage.PROFILE_ENTRY__CREATE_TIME:
				setCreateTime(CREATE_TIME_EDEFAULT);
				return;
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				setMonitored((EObject)null);
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
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
				return percentage != PERCENTAGE_EDEFAULT;
			case ProfilerPackage.PROFILE_ENTRY__CREATE_TIME:
				return createTime != CREATE_TIME_EDEFAULT;
			case ProfilerPackage.PROFILE_ENTRY__MONITORED:
				return monitored != null;
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
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (duration: "); //$NON-NLS-1$
		result.append(duration);
		result.append(", count: "); //$NON-NLS-1$
		result.append(count);
		result.append(", percentage: "); //$NON-NLS-1$
		result.append(percentage);
		result.append(", createTime: "); //$NON-NLS-1$
		result.append(createTime);
		result.append(')');
		return result.toString();
	}

} // ProfileEntryImpl
