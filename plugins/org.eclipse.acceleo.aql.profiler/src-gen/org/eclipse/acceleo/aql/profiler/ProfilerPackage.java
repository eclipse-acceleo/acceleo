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
package org.eclipse.acceleo.aql.profiler;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.aql.profiler.ProfilerFactory
 * @model kind="package"
 * @generated
 * @since 4.0
 */
public interface ProfilerPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "profiler"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/profiler/4.0"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "profiler"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ProfilerPackage eINSTANCE = org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl <em>Profile
	 * Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getProfileEntry()
	 * @generated
	 */
	int PROFILE_ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__DURATION = 0;

	/**
	 * The feature id for the '<em><b>Callees</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__CALLEES = 1;

	/**
	 * The feature id for the '<em><b>Caller</b></em>' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__CALLER = 2;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__COUNT = 3;

	/**
	 * The feature id for the '<em><b>Percentage</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__PERCENTAGE = 4;

	/**
	 * The feature id for the '<em><b>Create Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__CREATE_TIME = 5;

	/**
	 * The feature id for the '<em><b>Monitored</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY__MONITORED = 6;

	/**
	 * The number of structural features of the '<em>Profile Entry</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_ENTRY_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.aql.profiler.impl.LoopProfileEntryImpl <em>Loop
	 * Profile Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.impl.LoopProfileEntryImpl
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getLoopProfileEntry()
	 * @generated
	 */
	int LOOP_PROFILE_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__DURATION = PROFILE_ENTRY__DURATION;

	/**
	 * The feature id for the '<em><b>Callees</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__CALLEES = PROFILE_ENTRY__CALLEES;

	/**
	 * The feature id for the '<em><b>Caller</b></em>' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__CALLER = PROFILE_ENTRY__CALLER;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__COUNT = PROFILE_ENTRY__COUNT;

	/**
	 * The feature id for the '<em><b>Percentage</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__PERCENTAGE = PROFILE_ENTRY__PERCENTAGE;

	/**
	 * The feature id for the '<em><b>Create Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__CREATE_TIME = PROFILE_ENTRY__CREATE_TIME;

	/**
	 * The feature id for the '<em><b>Monitored</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__MONITORED = PROFILE_ENTRY__MONITORED;

	/**
	 * The feature id for the '<em><b>Loop Elements</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY__LOOP_ELEMENTS = PROFILE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Loop Profile Entry</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LOOP_PROFILE_ENTRY_FEATURE_COUNT = PROFILE_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.aql.profiler.impl.ProfileResourceImpl
	 * <em>Profile Resource</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfileResourceImpl
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getProfileResource()
	 * @generated
	 */
	int PROFILE_RESOURCE = 2;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_RESOURCE__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Profile Resource</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROFILE_RESOURCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.aql.profiler.impl.InternalImpl
	 * <em>Internal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.aql.profiler.impl.InternalImpl
	 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getInternal()
	 * @generated
	 */
	int INTERNAL = 3;

	/**
	 * The number of structural features of the '<em>Internal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTERNAL_FEATURE_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry <em>Profile
	 * Entry</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Profile Entry</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry
	 * @generated
	 */
	EClass getProfileEntry();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getDuration <em>Duration</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getDuration()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EAttribute getProfileEntry_Duration();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCallees <em>Callees</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Callees</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getCallees()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EReference getProfileEntry_Callees();

	/**
	 * Returns the meta object for the container reference '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCaller <em>Caller</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Caller</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getCaller()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EReference getProfileEntry_Caller();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCount <em>Count</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getCount()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EAttribute getProfileEntry_Count();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getPercentage <em>Percentage</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Percentage</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getPercentage()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EAttribute getProfileEntry_Percentage();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getCreateTime <em>Create Time</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Create Time</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getCreateTime()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EAttribute getProfileEntry_CreateTime();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileEntry#getMonitored <em>Monitored</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Monitored</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileEntry#getMonitored()
	 * @see #getProfileEntry()
	 * @generated
	 */
	EReference getProfileEntry_Monitored();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.aql.profiler.LoopProfileEntry <em>Loop
	 * Profile Entry</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Loop Profile Entry</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.LoopProfileEntry
	 * @generated
	 */
	EClass getLoopProfileEntry();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.aql.profiler.LoopProfileEntry#getLoopElements <em>Loop Elements</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Loop Elements</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.LoopProfileEntry#getLoopElements()
	 * @see #getLoopProfileEntry()
	 * @generated
	 */
	EReference getLoopProfileEntry_LoopElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.aql.profiler.ProfileResource <em>Profile
	 * Resource</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Profile Resource</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileResource
	 * @generated
	 */
	EClass getProfileResource();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.aql.profiler.ProfileResource#getEntries <em>Entries</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.ProfileResource#getEntries()
	 * @see #getProfileResource()
	 * @generated
	 */
	EReference getProfileResource_Entries();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.aql.profiler.Internal
	 * <em>Internal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Internal</em>'.
	 * @see org.eclipse.acceleo.aql.profiler.Internal
	 * @generated
	 */
	EClass getInternal();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProfilerFactory getProfilerFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl
		 * <em>Profile Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.aql.profiler.impl.ProfileEntryImpl
		 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getProfileEntry()
		 * @generated
		 */
		EClass PROFILE_ENTRY = eINSTANCE.getProfileEntry();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROFILE_ENTRY__DURATION = eINSTANCE.getProfileEntry_Duration();

		/**
		 * The meta object literal for the '<em><b>Callees</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROFILE_ENTRY__CALLEES = eINSTANCE.getProfileEntry_Callees();

		/**
		 * The meta object literal for the '<em><b>Caller</b></em>' container reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROFILE_ENTRY__CALLER = eINSTANCE.getProfileEntry_Caller();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROFILE_ENTRY__COUNT = eINSTANCE.getProfileEntry_Count();

		/**
		 * The meta object literal for the '<em><b>Percentage</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROFILE_ENTRY__PERCENTAGE = eINSTANCE.getProfileEntry_Percentage();

		/**
		 * The meta object literal for the '<em><b>Create Time</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PROFILE_ENTRY__CREATE_TIME = eINSTANCE.getProfileEntry_CreateTime();

		/**
		 * The meta object literal for the '<em><b>Monitored</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROFILE_ENTRY__MONITORED = eINSTANCE.getProfileEntry_Monitored();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.aql.profiler.impl.LoopProfileEntryImpl
		 * <em>Loop Profile Entry</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.aql.profiler.impl.LoopProfileEntryImpl
		 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getLoopProfileEntry()
		 * @generated
		 */
		EClass LOOP_PROFILE_ENTRY = eINSTANCE.getLoopProfileEntry();

		/**
		 * The meta object literal for the '<em><b>Loop Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LOOP_PROFILE_ENTRY__LOOP_ELEMENTS = eINSTANCE.getLoopProfileEntry_LoopElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.aql.profiler.impl.ProfileResourceImpl
		 * <em>Profile Resource</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.aql.profiler.impl.ProfileResourceImpl
		 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getProfileResource()
		 * @generated
		 */
		EClass PROFILE_RESOURCE = eINSTANCE.getProfileResource();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROFILE_RESOURCE__ENTRIES = eINSTANCE.getProfileResource_Entries();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.aql.profiler.impl.InternalImpl
		 * <em>Internal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.aql.profiler.impl.InternalImpl
		 * @see org.eclipse.acceleo.aql.profiler.impl.ProfilerPackageImpl#getInternal()
		 * @generated
		 */
		EClass INTERNAL = eINSTANCE.getInternal();

	}

} // ProfilerPackage
