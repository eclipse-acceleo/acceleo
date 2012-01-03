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
package org.eclipse.acceleo.compatibility.model.mt.impl;

import org.eclipse.acceleo.compatibility.model.mt.MtFactory;
import org.eclipse.acceleo.compatibility.model.mt.MtPackage;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
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
public class MtFactoryImpl extends EFactoryImpl implements MtFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static MtFactory init() {
		try {
			MtFactory theMtFactory = (MtFactory)EPackage.Registry.INSTANCE
					.getEFactory("http://www.eclipse.org/acceleo/mt/2.6.0"); //$NON-NLS-1$
			if (theMtFactory != null) {
				return theMtFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MtFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtFactoryImpl() {
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
			case MtPackage.RESOURCE_SET:
				return createResourceSet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ResourceSet createResourceSet() {
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		return resourceSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtPackage getMtPackage() {
		return (MtPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MtPackage getPackage() {
		return MtPackage.eINSTANCE;
	}

} // MtFactoryImpl
