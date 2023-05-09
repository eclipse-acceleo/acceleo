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
package org.eclipse.acceleo.query.tests.nestedpackages.root.impl;

import org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder;
import org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootEClass;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootFactory;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildPackage;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.Grand_childPackage;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.Grand_childPackageImpl;

import org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RootPackageImpl extends EPackageImpl implements RootPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootEClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityHolderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityInterfaceEClass = null;

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
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RootPackageImpl() {
		super(eNS_URI, RootFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RootPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RootPackage init() {
		if (isInited) return (RootPackage)EPackage.Registry.INSTANCE.getEPackage(RootPackage.eNS_URI);

		// Obtain or create and register package
		RootPackageImpl theRootPackage = (RootPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof RootPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new RootPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ChildPackageImpl theChildPackage = (ChildPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ChildPackage.eNS_URI) instanceof ChildPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ChildPackage.eNS_URI) : ChildPackage.eINSTANCE);
		Grand_childPackageImpl theGrand_childPackage = (Grand_childPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(Grand_childPackage.eNS_URI) instanceof Grand_childPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(Grand_childPackage.eNS_URI) : Grand_childPackage.eINSTANCE);

		// Create package meta-data objects
		theRootPackage.createPackageContents();
		theChildPackage.createPackageContents();
		theGrand_childPackage.createPackageContents();

		// Initialize created meta-data
		theRootPackage.initializePackageContents();
		theChildPackage.initializePackageContents();
		theGrand_childPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRootPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RootPackage.eNS_URI, theRootPackage);
		return theRootPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRootEClass() {
		return rootEClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityHolder() {
		return entityHolderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityHolder_EntityInterfaces() {
		return (EReference)entityHolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityInterface() {
		return entityInterfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEntityInterface_Name() {
		return (EAttribute)entityInterfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootFactory getRootFactory() {
		return (RootFactory)getEFactoryInstance();
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
		rootEClassEClass = createEClass(ROOT_ECLASS);

		entityHolderEClass = createEClass(ENTITY_HOLDER);
		createEReference(entityHolderEClass, ENTITY_HOLDER__ENTITY_INTERFACES);

		entityInterfaceEClass = createEClass(ENTITY_INTERFACE);
		createEAttribute(entityInterfaceEClass, ENTITY_INTERFACE__NAME);
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
		ChildPackage theChildPackage = (ChildPackage)EPackage.Registry.INSTANCE.getEPackage(ChildPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theChildPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		rootEClassEClass.getESuperTypes().add(this.getEntityInterface());

		// Initialize classes, features, and operations; add parameters
		initEClass(rootEClassEClass, RootEClass.class, "RootEClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(entityHolderEClass, EntityHolder.class, "EntityHolder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityHolder_EntityInterfaces(), this.getEntityInterface(), null, "entityInterfaces", null, 0, -1, EntityHolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityInterfaceEClass, EntityInterface.class, "EntityInterface", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEntityInterface_Name(), ecorePackage.getEString(), "name", null, 0, 1, EntityInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //RootPackageImpl
