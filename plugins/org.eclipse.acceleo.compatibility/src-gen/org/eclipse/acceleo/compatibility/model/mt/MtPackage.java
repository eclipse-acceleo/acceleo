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
package org.eclipse.acceleo.compatibility.model.mt;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.MtFactory
 * @model kind="package"
 * @generated
 */
public interface MtPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "mt"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/mt/2.6.0"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "mt"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	MtPackage eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.impl.ResourceSetImpl
	 * <em>Resource Set</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.impl.ResourceSetImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl#getResourceSet()
	 * @generated
	 */
	int RESOURCE_SET = 0;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET__RESOURCES = 0;

	/**
	 * The number of structural features of the '<em>Resource Set</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_SET_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.Resource
	 * <em>Resource</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.Resource
	 * @see org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Resource</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.ResourceSet
	 * <em>Resource Set</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Resource Set</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.ResourceSet
	 * @generated
	 */
	EClass getResourceSet();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.ResourceSet#getResources <em>Resources</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.ResourceSet#getResources()
	 * @see #getResourceSet()
	 * @generated
	 */
	EReference getResourceSet_Resources();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.Resource
	 * <em>Resource</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.Resource#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.Resource#getName()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Name();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MtFactory getMtFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.impl.ResourceSetImpl <em>Resource Set</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.impl.ResourceSetImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl#getResourceSet()
		 * @generated
		 */
		EClass RESOURCE_SET = eINSTANCE.getResourceSet();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESOURCE_SET__RESOURCES = eINSTANCE.getResourceSet_Resources();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.compatibility.model.mt.Resource
		 * <em>Resource</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.Resource
		 * @see org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute RESOURCE__NAME = eINSTANCE.getResource_Name();

	}

} // MtPackage
