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
package org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child;

import org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.Grand_childFactory
 * @model kind="package"
 * @generated
 */
public interface Grand_childPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "grand_child";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/aql/grand-child";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "grand_child_package";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Grand_childPackage eINSTANCE = org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.Grand_childPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.GrandChildEClassImpl <em>Grand Child EClass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.GrandChildEClassImpl
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.Grand_childPackageImpl#getGrandChildEClass()
	 * @generated
	 */
	int GRAND_CHILD_ECLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAND_CHILD_ECLASS__NAME = RootPackage.ENTITY_INTERFACE__NAME;

	/**
	 * The number of structural features of the '<em>Grand Child EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAND_CHILD_ECLASS_FEATURE_COUNT = RootPackage.ENTITY_INTERFACE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Grand Child EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAND_CHILD_ECLASS_OPERATION_COUNT = RootPackage.ENTITY_INTERFACE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.GrandChildEClass <em>Grand Child EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Grand Child EClass</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.GrandChildEClass
	 * @generated
	 */
	EClass getGrandChildEClass();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	Grand_childFactory getGrand_childFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.GrandChildEClassImpl <em>Grand Child EClass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.GrandChildEClassImpl
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.Grand_childPackageImpl#getGrandChildEClass()
		 * @generated
		 */
		EClass GRAND_CHILD_ECLASS = eINSTANCE.getGrandChildEClass();

	}

} //Grand_childPackage
