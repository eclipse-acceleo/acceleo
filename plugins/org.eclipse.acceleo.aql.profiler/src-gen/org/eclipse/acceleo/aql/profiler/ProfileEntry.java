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
package org.eclipse.acceleo.aql.profiler;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Profile Entry</b></em>'. <!--
 * end-user-doc --> <!-- begin-model-doc --> This element describe the node of a profiling tree. To be useful
 * after creation the method {@link ProfileEntry#start()} must be called when the profiler process starts.
 * Then method {@link ProfileEntry#stop()} must be called at the end of the profiled process. <!--
 * end-model-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getDuration <em>Duration</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCallees <em>Callees</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCaller <em>Caller</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCount <em>Count</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getMonitored <em>Monitored</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getPercentage <em>Percentage</em>}</li>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCreationTime <em>Creation Time</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry()
 * @model
 * @generated
 */
public interface ProfileEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Duration</b></em>' attribute. The default value is <code>"0"</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The established time between the
	 * call to {@link ProfileEntry#start()} and the call to {@link ProfileEntry#stop()}. <!-- end-model-doc
	 * -->
	 * 
	 * @return the value of the '<em>Duration</em>' attribute.
	 * @see #setDuration(long)
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_Duration()
	 * @model default="0"
	 * @generated
	 */
	long getDuration();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getDuration
	 * <em>Duration</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Duration</em>' attribute.
	 * @see #getDuration()
	 * @generated
	 */
	void setDuration(long value);

	/**
	 * Returns the value of the '<em><b>Callees</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.aql.profiler.ProfileEntry}. It is bidirectional and its opposite is
	 * '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCaller <em>Caller</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc --> <!-- begin-model-doc --> The list of {@link ProfileEntry} created between the
	 * call to {@link ProfileEntry#start()} and the call to {@link ProfileEntry#stop()} of <code>this</code>
	 * object. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Callees</em>' containment reference list.
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_Callees()
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getCaller
	 * @model opposite="caller" containment="true"
	 * @generated
	 */
	EList<ProfileEntry> getCallees();

	/**
	 * Returns the value of the '<em><b>Caller</b></em>' container reference. It is bidirectional and its
	 * opposite is '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCallees <em>Callees</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The parent {@link ProfileEntry}. <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>Caller</em>' container reference.
	 * @see #setCaller(ProfileEntry)
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_Caller()
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getCallees
	 * @model opposite="callees" unsettable="true" transient="false"
	 * @generated
	 */
	ProfileEntry getCaller();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCaller <em>Caller</em>}'
	 * container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Caller</em>' container reference.
	 * @see #getCaller()
	 * @generated
	 */
	void setCaller(ProfileEntry value);

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute. The default value is <code>"0"</code>. <!--
	 * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The number of times this
	 * {@link ProfileEntry} has been {@link ProfileEntry#start() used}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Count</em>' attribute.
	 * @see #setCount(int)
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_Count()
	 * @model default="0"
	 * @generated
	 */
	int getCount();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCount <em>Count</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Count</em>' attribute.
	 * @see #getCount()
	 * @generated
	 */
	void setCount(int value);

	/**
	 * Returns the value of the '<em><b>Monitored</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> The {@link EObject} representing the profiled process. <!--
	 * end-model-doc -->
	 * 
	 * @return the value of the '<em>Monitored</em>' reference.
	 * @see #setMonitored(EObject)
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_Monitored()
	 * @model required="true"
	 * @generated
	 */
	EObject getMonitored();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getMonitored
	 * <em>Monitored</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Monitored</em>' reference.
	 * @see #getMonitored()
	 * @generated
	 */
	void setMonitored(EObject value);

	/**
	 * Returns the value of the '<em><b>Percentage</b></em>' attribute. The default value is <code>"0"</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The percentage of time spent in
	 * this {@link ProfileEntry} based on the root {@link ProfileEntry} {@link ProfileEntry#getDuration()
	 * duration} . <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Percentage</em>' attribute.
	 * @see #setPercentage(float)
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_Percentage()
	 * @model default="0"
	 * @generated
	 */
	float getPercentage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getPercentage
	 * <em>Percentage</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Percentage</em>' attribute.
	 * @see #getPercentage()
	 * @generated
	 */
	void setPercentage(float value);

	/**
	 * Returns the value of the '<em><b>Creation Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc --> <!-- begin-model-doc --> The creation time of this {@link ProfileEntry}. It's managed
	 * by the {@link ProfileEntry} itself. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Creation Time</em>' attribute.
	 * @see #setCreationTime(long)
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileEntry_CreationTime()
	 * @model
	 * @generated
	 */
	long getCreationTime();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCreationTime
	 * <em>Creation Time</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Creation Time</em>' attribute.
	 * @see #getCreationTime()
	 * @generated
	 */
	void setCreationTime(long value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	void start();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	void stop();

} // ProfileEntry
