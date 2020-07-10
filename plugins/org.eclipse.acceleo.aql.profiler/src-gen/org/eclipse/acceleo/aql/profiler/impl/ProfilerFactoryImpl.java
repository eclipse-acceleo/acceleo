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

import org.eclipse.acceleo.aql.profiler.LoopProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileResource;
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.acceleo.aql.profiler.impl.spec.LoopProfileEntrySpec;
import org.eclipse.acceleo.aql.profiler.impl.spec.ProfileEntrySpec;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProfilerFactoryImpl extends EFactoryImpl implements ProfilerFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ProfilerFactory init() {
		try {
			ProfilerFactory theProfilerFactory = (ProfilerFactory)EPackage.Registry.INSTANCE.getEFactory(
					ProfilerPackage.eNS_URI);
			if (theProfilerFactory != null) {
				return theProfilerFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProfilerFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProfilerFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ProfilerPackage.PROFILE_ENTRY:
				return createProfileEntry();
			case ProfilerPackage.LOOP_PROFILE_ENTRY:
				return createLoopProfileEntry();
			case ProfilerPackage.PROFILE_RESOURCE:
				return createProfileResource();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ProfileEntry createProfileEntry() {
		ProfileEntryImpl profileEntry = new ProfileEntrySpec();
		return profileEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public LoopProfileEntry createLoopProfileEntry() {
		LoopProfileEntryImpl loopProfileEntry = new LoopProfileEntrySpec();
		return loopProfileEntry;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProfileResource createProfileResource() {
		ProfileResourceImpl profileResource = new ProfileResourceImpl();
		return profileResource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProfilerPackage getProfilerPackage() {
		return (ProfilerPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProfilerPackage getPackage() {
		return ProfilerPackage.eINSTANCE;
	}

} // ProfilerFactoryImpl
