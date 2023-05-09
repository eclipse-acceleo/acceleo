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
package org.eclipse.acceleo.query.tests.nestedpackages.root.child;

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
 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildFactory
 * @model kind="package"
 * @generated
 */
public interface ChildPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "child";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/aql/child";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "childpackage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ChildPackage eINSTANCE = org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildEClassImpl <em>EClass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildEClassImpl
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildPackageImpl#getChildEClass()
	 * @generated
	 */
	int CHILD_ECLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_ECLASS__NAME = RootPackage.ENTITY_INTERFACE__NAME;

	/**
	 * The number of structural features of the '<em>EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_ECLASS_FEATURE_COUNT = RootPackage.ENTITY_INTERFACE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_ECLASS_OPERATION_COUNT = RootPackage.ENTITY_INTERFACE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildEClass <em>EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EClass</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildEClass
	 * @generated
	 */
	EClass getChildEClass();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ChildFactory getChildFactory();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildEClassImpl <em>EClass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildEClassImpl
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.impl.ChildPackageImpl#getChildEClass()
		 * @generated
		 */
		EClass CHILD_ECLASS = eINSTANCE.getChildEClass();

	}

} //ChildPackage
