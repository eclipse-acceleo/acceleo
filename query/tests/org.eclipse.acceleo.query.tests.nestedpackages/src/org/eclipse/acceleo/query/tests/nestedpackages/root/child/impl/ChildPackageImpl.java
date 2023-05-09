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
package org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl;

import org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildEClass;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildFactory;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildPackage;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.Grand_childPackage;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.Grand_childPackageImpl;

import org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ChildPackageImpl extends EPackageImpl implements ChildPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass childEClassEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ChildPackageImpl() {
		super(eNS_URI, ChildFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ChildPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ChildPackage init() {
		if (isInited) return (ChildPackage)EPackage.Registry.INSTANCE.getEPackage(ChildPackage.eNS_URI);

		// Obtain or create and register package
		ChildPackageImpl theChildPackage = (ChildPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ChildPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ChildPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI) : RootPackage.eINSTANCE);
		Grand_childPackageImpl theGrand_childPackage = (Grand_childPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(Grand_childPackage.eNS_URI) instanceof Grand_childPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(Grand_childPackage.eNS_URI) : Grand_childPackage.eINSTANCE);

		// Create package meta-data objects
		theChildPackage.createPackageContents();
		theRootPackage.createPackageContents();
		theGrand_childPackage.createPackageContents();

		// Initialize created meta-data
		theChildPackage.initializePackageContents();
		theRootPackage.initializePackageContents();
		theGrand_childPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theChildPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ChildPackage.eNS_URI, theChildPackage);
		return theChildPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChildEClass() {
		return childEClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChildFactory getChildFactory() {
		return (ChildFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		childEClassEClass = createEClass(CHILD_ECLASS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		Grand_childPackage theGrand_childPackage = (Grand_childPackage)EPackage.Registry.INSTANCE.getEPackage(Grand_childPackage.eNS_URI);
		RootPackage theRootPackage = (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theGrand_childPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		childEClassEClass.getESuperTypes().add(theRootPackage.getEntityInterface());

		// Initialize classes, features, and operations; add parameters
		initEClass(childEClassEClass, ChildEClass.class, "ChildEClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //ChildPackageImpl
