/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class Grand_childFactoryImpl extends EFactoryImpl implements Grand_childFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Grand_childFactory init() {
		try {
			Grand_childFactory theGrand_childFactory = (Grand_childFactory)EPackage.Registry.INSTANCE.getEFactory(Grand_childPackage.eNS_URI);
			if (theGrand_childFactory != null) {
				return theGrand_childFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Grand_childFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Grand_childFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case Grand_childPackage.GRAND_CHILD_ECLASS: return createGrandChildEClass();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GrandChildEClass createGrandChildEClass() {
		GrandChildEClassImpl grandChildEClass = new GrandChildEClassImpl();
		return grandChildEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Grand_childPackage getGrand_childPackage() {
		return (Grand_childPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static Grand_childPackage getPackage() {
		return Grand_childPackage.eINSTANCE;
	}

} //Grand_childFactoryImpl
