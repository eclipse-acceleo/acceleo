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
package org.eclipse.acceleo.query.tests.nestedpackages.root;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.RootFactory
 * @model kind="package"
 * @generated
 */
public interface RootPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "root";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/aql/root";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rootpackage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RootPackage eINSTANCE = org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface <em>Entity Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl#getEntityInterface()
	 * @generated
	 */
	int ENTITY_INTERFACE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_INTERFACE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Entity Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_INTERFACE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Entity Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_INTERFACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootEClassImpl <em>EClass</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootEClassImpl
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl#getRootEClass()
	 * @generated
	 */
	int ROOT_ECLASS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECLASS__NAME = ENTITY_INTERFACE__NAME;

	/**
	 * The number of structural features of the '<em>EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECLASS_FEATURE_COUNT = ENTITY_INTERFACE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>EClass</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_ECLASS_OPERATION_COUNT = ENTITY_INTERFACE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.impl.EntityHolderImpl <em>Entity Holder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.EntityHolderImpl
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl#getEntityHolder()
	 * @generated
	 */
	int ENTITY_HOLDER = 1;

	/**
	 * The feature id for the '<em><b>Entity Interfaces</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_HOLDER__ENTITY_INTERFACES = 0;

	/**
	 * The number of structural features of the '<em>Entity Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_HOLDER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Entity Holder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_HOLDER_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.RootEClass <em>EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EClass</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.RootEClass
	 * @generated
	 */
	EClass getRootEClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder <em>Entity Holder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Holder</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder
	 * @generated
	 */
	EClass getEntityHolder();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder#getEntityInterfaces <em>Entity Interfaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entity Interfaces</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder#getEntityInterfaces()
	 * @see #getEntityHolder()
	 * @generated
	 */
	EReference getEntityHolder_EntityInterfaces();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface <em>Entity Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Interface</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface
	 * @generated
	 */
	EClass getEntityInterface();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface#getName()
	 * @see #getEntityInterface()
	 * @generated
	 */
	EAttribute getEntityInterface_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RootFactory getRootFactory();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootEClassImpl <em>EClass</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootEClassImpl
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl#getRootEClass()
		 * @generated
		 */
		EClass ROOT_ECLASS = eINSTANCE.getRootEClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.impl.EntityHolderImpl <em>Entity Holder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.EntityHolderImpl
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl#getEntityHolder()
		 * @generated
		 */
		EClass ENTITY_HOLDER = eINSTANCE.getEntityHolder();

		/**
		 * The meta object literal for the '<em><b>Entity Interfaces</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_HOLDER__ENTITY_INTERFACES = eINSTANCE.getEntityHolder_EntityInterfaces();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface <em>Entity Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface
		 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.impl.RootPackageImpl#getEntityInterface()
		 * @generated
		 */
		EClass ENTITY_INTERFACE = eINSTANCE.getEntityInterface();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY_INTERFACE__NAME = eINSTANCE.getEntityInterface_Name();

	}

} //RootPackage
