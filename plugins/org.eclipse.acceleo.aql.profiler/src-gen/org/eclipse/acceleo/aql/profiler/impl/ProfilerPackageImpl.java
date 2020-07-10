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

import org.eclipse.acceleo.aql.profiler.Internal;
import org.eclipse.acceleo.aql.profiler.LoopProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileResource;
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProfilerPackageImpl extends EPackageImpl implements ProfilerPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass profileEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass loopProfileEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass profileResourceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass internalEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProfilerPackageImpl() {
		super(eNS_URI, ProfilerFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends. Simple dependencies are satisfied by calling this method on all dependent packages before
	 * doing anything else. This method drives initialization for interdependent packages directly, in
	 * parallel with this package, itself.
	 * <p>
	 * Of this package and its interdependencies, all packages which have not yet been registered by their URI
	 * values are first created and registered. The packages are then initialized in two steps: meta-model
	 * objects for all of the packages are created before any are initialized, since one package's meta-model
	 * objects may refer to those of another.
	 * <p>
	 * Invocation of this method will not affect any packages that have already been initialized. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ProfilerPackage init() {
		if (isInited) {
			return (ProfilerPackage)EPackage.Registry.INSTANCE.getEPackage(ProfilerPackage.eNS_URI);
		}

		// Obtain or create and register package
		ProfilerPackageImpl theProfilerPackage = (ProfilerPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI) instanceof ProfilerPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI) : new ProfilerPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theProfilerPackage.createPackageContents();

		// Initialize created meta-data
		theProfilerPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProfilerPackage.freeze();

		return theProfilerPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProfileEntry() {
		return profileEntryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getProfileEntry_Duration() {
		return (EAttribute)profileEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProfileEntry_Callees() {
		return (EReference)profileEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProfileEntry_Caller() {
		return (EReference)profileEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getProfileEntry_Count() {
		return (EAttribute)profileEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getProfileEntry_Percentage() {
		return (EAttribute)profileEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getProfileEntry_CreateTime() {
		return (EAttribute)profileEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProfileEntry_Monitored() {
		return (EReference)profileEntryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getLoopProfileEntry() {
		return loopProfileEntryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getLoopProfileEntry_LoopElements() {
		return (EReference)loopProfileEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProfileResource() {
		return profileResourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProfileResource_Entries() {
		return (EReference)profileResourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInternal() {
		return internalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProfilerFactory getProfilerFactory() {
		return (ProfilerFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		profileEntryEClass = createEClass(PROFILE_ENTRY);
		createEAttribute(profileEntryEClass, PROFILE_ENTRY__DURATION);
		createEReference(profileEntryEClass, PROFILE_ENTRY__CALLEES);
		createEReference(profileEntryEClass, PROFILE_ENTRY__CALLER);
		createEAttribute(profileEntryEClass, PROFILE_ENTRY__COUNT);
		createEAttribute(profileEntryEClass, PROFILE_ENTRY__PERCENTAGE);
		createEAttribute(profileEntryEClass, PROFILE_ENTRY__CREATE_TIME);
		createEReference(profileEntryEClass, PROFILE_ENTRY__MONITORED);

		loopProfileEntryEClass = createEClass(LOOP_PROFILE_ENTRY);
		createEReference(loopProfileEntryEClass, LOOP_PROFILE_ENTRY__LOOP_ELEMENTS);

		profileResourceEClass = createEClass(PROFILE_RESOURCE);
		createEReference(profileResourceEClass, PROFILE_RESOURCE__ENTRIES);

		internalEClass = createEClass(INTERNAL);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		loopProfileEntryEClass.getESuperTypes().add(this.getProfileEntry());

		// Initialize classes and features; add operations and parameters
		initEClass(profileEntryEClass, ProfileEntry.class, "ProfileEntry", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProfileEntry_Duration(), ecorePackage.getELong(), "duration", "0", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProfileEntry_Callees(), this.getProfileEntry(), this.getProfileEntry_Caller(),
				"callees", null, 0, -1, ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProfileEntry_Caller(), this.getProfileEntry(), this.getProfileEntry_Callees(),
				"caller", null, 0, 1, ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProfileEntry_Count(), ecorePackage.getELong(), "count", "0", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProfileEntry_Percentage(), ecorePackage.getEDouble(), "percentage", "0", 0, 1, //$NON-NLS-1$ //$NON-NLS-2$
				ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProfileEntry_CreateTime(), ecorePackage.getELong(), "createTime", null, 0, 1, //$NON-NLS-1$
				ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProfileEntry_Monitored(), ecorePackage.getEObject(), null, "monitored", null, 0, 1, //$NON-NLS-1$
				ProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(profileEntryEClass, null, "start", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		addEOperation(profileEntryEClass, null, "stop", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(loopProfileEntryEClass, LoopProfileEntry.class, "LoopProfileEntry", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLoopProfileEntry_LoopElements(), this.getProfileEntry(), null,
				"loopElements", //$NON-NLS-1$
				null, 0, -1, LoopProfileEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(profileResourceEClass, ProfileResource.class, "ProfileResource", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProfileResource_Entries(), this.getProfileEntry(), null, "entries", null, 0, -1, //$NON-NLS-1$
				ProfileResource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(internalEClass, Internal.class, "Internal", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // ProfilerPackageImpl
