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
import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.ResourceSet;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage;
import org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl;
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
public class MtPackageImpl extends EPackageImpl implements MtPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass resourceSetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass resourceEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.compatibility.model.mt.MtPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MtPackageImpl() {
		super(eNS_URI, MtFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link MtPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MtPackage init() {
		if (isInited) {
			return (MtPackage)EPackage.Registry.INSTANCE.getEPackage(MtPackage.eNS_URI);
		}

		// Obtain or create and register package
		MtPackageImpl theMtPackage = (MtPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MtPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI)
				: new MtPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CorePackageImpl theCorePackage = (CorePackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(CorePackage.eNS_URI) instanceof CorePackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(CorePackage.eNS_URI) : CorePackage.eINSTANCE);
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI) instanceof ExpressionsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI)
				: ExpressionsPackage.eINSTANCE);
		StatementsPackageImpl theStatementsPackage = (StatementsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI) instanceof StatementsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI)
				: StatementsPackage.eINSTANCE);

		// Create package meta-data objects
		theMtPackage.createPackageContents();
		theCorePackage.createPackageContents();
		theExpressionsPackage.createPackageContents();
		theStatementsPackage.createPackageContents();

		// Initialize created meta-data
		theMtPackage.initializePackageContents();
		theCorePackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();
		theStatementsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMtPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MtPackage.eNS_URI, theMtPackage);
		return theMtPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getResourceSet() {
		return resourceSetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getResourceSet_Resources() {
		return (EReference)resourceSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getResource() {
		return resourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getResource_Name() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtFactory getMtFactory() {
		return (MtFactory)getEFactoryInstance();
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
		resourceSetEClass = createEClass(RESOURCE_SET);
		createEReference(resourceSetEClass, RESOURCE_SET__RESOURCES);

		resourceEClass = createEClass(MtPackage.RESOURCE);
		createEAttribute(resourceEClass, RESOURCE__NAME);
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

		// Obtain other dependent packages
		CorePackage theCorePackage = (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);
		ExpressionsPackage theExpressionsPackage = (ExpressionsPackage)EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI);
		StatementsPackage theStatementsPackage = (StatementsPackage)EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theCorePackage);
		getESubpackages().add(theExpressionsPackage);
		getESubpackages().add(theStatementsPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(resourceSetEClass, ResourceSet.class, "ResourceSet", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceSet_Resources(), this.getResource(), null, "resources", null, 0, -1, //$NON-NLS-1$
				ResourceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceEClass, Resource.class, "Resource", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResource_Name(), ecorePackage.getEString(), "name", null, 1, 1, Resource.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // MtPackageImpl
