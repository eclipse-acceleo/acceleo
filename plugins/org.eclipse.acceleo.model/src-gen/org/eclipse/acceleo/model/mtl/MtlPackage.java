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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.utilities.UtilitiesPackage;

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
 * @see org.eclipse.acceleo.model.mtl.MtlFactory
 * @model kind="package"
 * @generated
 */
public interface MtlPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "mtl"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/mtl/3.0"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "mtl"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	MtlPackage eINSTANCE = org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleImpl <em>Module</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ModuleImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModule()
	 * @generated
	 */
	int MODULE = 0;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__EANNOTATIONS = EcorePackage.EPACKAGE__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__NAME = EcorePackage.EPACKAGE__NAME;

	/**
	 * The feature id for the '<em><b>Ns URI</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__NS_URI = EcorePackage.EPACKAGE__NS_URI;

	/**
	 * The feature id for the '<em><b>Ns Prefix</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__NS_PREFIX = EcorePackage.EPACKAGE__NS_PREFIX;

	/**
	 * The feature id for the '<em><b>EFactory Instance</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__EFACTORY_INSTANCE = EcorePackage.EPACKAGE__EFACTORY_INSTANCE;

	/**
	 * The feature id for the '<em><b>EClassifiers</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__ECLASSIFIERS = EcorePackage.EPACKAGE__ECLASSIFIERS;

	/**
	 * The feature id for the '<em><b>ESubpackages</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__ESUBPACKAGES = EcorePackage.EPACKAGE__ESUBPACKAGES;

	/**
	 * The feature id for the '<em><b>ESuper Package</b></em>' container reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__ESUPER_PACKAGE = EcorePackage.EPACKAGE__ESUPER_PACKAGE;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE__DOCUMENTATION = EcorePackage.EPACKAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE__DEPRECATED = EcorePackage.EPACKAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Input</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__INPUT = EcorePackage.EPACKAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__EXTENDS = EcorePackage.EPACKAGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__IMPORTS = EcorePackage.EPACKAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Owned Module Element</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__OWNED_MODULE_ELEMENT = EcorePackage.EPACKAGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Start Header Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE__START_HEADER_POSITION = EcorePackage.EPACKAGE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>End Header Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE__END_HEADER_POSITION = EcorePackage.EPACKAGE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Module</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_FEATURE_COUNT = EcorePackage.EPACKAGE_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl
	 * <em>Module Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModuleElement()
	 * @generated
	 */
	int MODULE_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__EANNOTATIONS = EcorePackage.ENAMED_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__NAME = EcorePackage.ENAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__START_POSITION = EcorePackage.ENAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__END_POSITION = EcorePackage.ENAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__VISIBILITY = EcorePackage.ENAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Module Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_FEATURE_COUNT = EcorePackage.ENAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.TemplateExpressionImpl
	 * <em>Template Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.TemplateExpressionImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTemplateExpression()
	 * @generated
	 */
	int TEMPLATE_EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__EANNOTATIONS = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__NAME = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__ORDERED = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__UNIQUE = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__LOWER_BOUND = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__UPPER_BOUND = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__MANY = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__REQUIRED = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__ETYPE = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__EGENERIC_TYPE = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__START_POSITION = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__END_POSITION = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION__END_POSITION;

	/**
	 * The number of structural features of the '<em>Template Expression</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION_FEATURE_COUNT = org.eclipse.ocl.ecore.EcorePackage.OCL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.BlockImpl <em>Block</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.BlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 3;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__EANNOTATIONS = TEMPLATE_EXPRESSION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__NAME = TEMPLATE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__ORDERED = TEMPLATE_EXPRESSION__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__UNIQUE = TEMPLATE_EXPRESSION__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__LOWER_BOUND = TEMPLATE_EXPRESSION__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__UPPER_BOUND = TEMPLATE_EXPRESSION__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__MANY = TEMPLATE_EXPRESSION__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__REQUIRED = TEMPLATE_EXPRESSION__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__ETYPE = TEMPLATE_EXPRESSION__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__EGENERIC_TYPE = TEMPLATE_EXPRESSION__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__START_POSITION = TEMPLATE_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__END_POSITION = TEMPLATE_EXPRESSION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__INIT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__BODY = TEMPLATE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_FEATURE_COUNT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.InitSectionImpl
	 * <em>Init Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.InitSectionImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getInitSection()
	 * @generated
	 */
	int INIT_SECTION = 4;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION__START_POSITION = UtilitiesPackage.AST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION__END_POSITION = UtilitiesPackage.AST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION__VARIABLE = UtilitiesPackage.AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Init Section</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION_FEATURE_COUNT = UtilitiesPackage.AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl <em>Template</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.TemplateImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTemplate()
	 * @generated
	 */
	int TEMPLATE = 5;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__VISIBILITY = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int TEMPLATE__DOCUMENTATION = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int TEMPLATE__DEPRECATED = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Overrides</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__OVERRIDES = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__PARAMETER = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__GUARD = BLOCK_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Main</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__MAIN = BLOCK_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__POST = BLOCK_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Template</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl
	 * <em>Template Invocation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTemplateInvocation()
	 * @generated
	 */
	int TEMPLATE_INVOCATION = 6;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__EANNOTATIONS = TEMPLATE_EXPRESSION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__NAME = TEMPLATE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__ORDERED = TEMPLATE_EXPRESSION__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__UNIQUE = TEMPLATE_EXPRESSION__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__LOWER_BOUND = TEMPLATE_EXPRESSION__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__UPPER_BOUND = TEMPLATE_EXPRESSION__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__MANY = TEMPLATE_EXPRESSION__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__REQUIRED = TEMPLATE_EXPRESSION__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__ETYPE = TEMPLATE_EXPRESSION__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__EGENERIC_TYPE = TEMPLATE_EXPRESSION__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__START_POSITION = TEMPLATE_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__END_POSITION = TEMPLATE_EXPRESSION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__DEFINITION = TEMPLATE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__ARGUMENT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Before</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__BEFORE = TEMPLATE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>After</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__AFTER = TEMPLATE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Each</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__EACH = TEMPLATE_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Super</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION__SUPER = TEMPLATE_EXPRESSION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Template Invocation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_INVOCATION_FEATURE_COUNT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.QueryImpl <em>Query</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.QueryImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getQuery()
	 * @generated
	 */
	int QUERY = 7;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__EANNOTATIONS = MODULE_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__NAME = MODULE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__START_POSITION = MODULE_ELEMENT__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__END_POSITION = MODULE_ELEMENT__END_POSITION;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__VISIBILITY = MODULE_ELEMENT__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int QUERY__DOCUMENTATION = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int QUERY__DEPRECATED = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__PARAMETER = MODULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__EXPRESSION = MODULE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__TYPE = MODULE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Query</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.QueryInvocationImpl
	 * <em>Query Invocation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.QueryInvocationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getQueryInvocation()
	 * @generated
	 */
	int QUERY_INVOCATION = 8;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__EANNOTATIONS = TEMPLATE_EXPRESSION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__NAME = TEMPLATE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__ORDERED = TEMPLATE_EXPRESSION__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__UNIQUE = TEMPLATE_EXPRESSION__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__LOWER_BOUND = TEMPLATE_EXPRESSION__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__UPPER_BOUND = TEMPLATE_EXPRESSION__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__MANY = TEMPLATE_EXPRESSION__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__REQUIRED = TEMPLATE_EXPRESSION__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__ETYPE = TEMPLATE_EXPRESSION__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__EGENERIC_TYPE = TEMPLATE_EXPRESSION__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__START_POSITION = TEMPLATE_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__END_POSITION = TEMPLATE_EXPRESSION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__DEFINITION = TEMPLATE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION__ARGUMENT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Query Invocation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_INVOCATION_FEATURE_COUNT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ProtectedAreaBlockImpl
	 * <em>Protected Area Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ProtectedAreaBlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getProtectedAreaBlock()
	 * @generated
	 */
	int PROTECTED_AREA_BLOCK = 9;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Marker</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK__MARKER = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Protected Area Block</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_BLOCK_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl <em>For Block</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ForBlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getForBlock()
	 * @generated
	 */
	int FOR_BLOCK = 10;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Loop Variable</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__LOOP_VARIABLE = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Iter Set</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__ITER_SET = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Before</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__BEFORE = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Each</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__EACH = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>After</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__AFTER = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK__GUARD = BLOCK_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>For Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_BLOCK_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.IfBlockImpl <em>If Block</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.IfBlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getIfBlock()
	 * @generated
	 */
	int IF_BLOCK = 11;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>If Expr</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__IF_EXPR = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__ELSE = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else If</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK__ELSE_IF = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_BLOCK_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.LetBlockImpl <em>Let Block</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.LetBlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getLetBlock()
	 * @generated
	 */
	int LET_BLOCK = 12;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Else Let</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__ELSE_LET = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__ELSE = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Let Variable</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK__LET_VARIABLE = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Let Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_BLOCK_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.FileBlockImpl
	 * <em>File Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.FileBlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getFileBlock()
	 * @generated
	 */
	int FILE_BLOCK = 13;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Open Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__OPEN_MODE = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>File Url</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__FILE_URL = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Uniq Id</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK__UNIQ_ID = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.0
	 */
	int FILE_BLOCK__CHARSET = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>File Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_BLOCK_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.TraceBlockImpl
	 * <em>Trace Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.TraceBlockImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTraceBlock()
	 * @generated
	 */
	int TRACE_BLOCK = 14;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Model Element</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK__MODEL_ELEMENT = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Trace Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACE_BLOCK_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.MacroImpl <em>Macro</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.MacroImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getMacro()
	 * @generated
	 */
	int MACRO = 15;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__EANNOTATIONS = BLOCK__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__NAME = BLOCK__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__ORDERED = BLOCK__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__UNIQUE = BLOCK__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__LOWER_BOUND = BLOCK__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__UPPER_BOUND = BLOCK__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__MANY = BLOCK__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__REQUIRED = BLOCK__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__ETYPE = BLOCK__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__EGENERIC_TYPE = BLOCK__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__START_POSITION = BLOCK__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__END_POSITION = BLOCK__END_POSITION;

	/**
	 * The feature id for the '<em><b>Init</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__INIT = BLOCK__INIT;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__BODY = BLOCK__BODY;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__VISIBILITY = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MACRO__DOCUMENTATION = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MACRO__DEPRECATED = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__PARAMETER = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__TYPE = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Macro</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.MacroInvocationImpl
	 * <em>Macro Invocation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.MacroInvocationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getMacroInvocation()
	 * @generated
	 */
	int MACRO_INVOCATION = 16;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__EANNOTATIONS = TEMPLATE_EXPRESSION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__NAME = TEMPLATE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Ordered</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__ORDERED = TEMPLATE_EXPRESSION__ORDERED;

	/**
	 * The feature id for the '<em><b>Unique</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__UNIQUE = TEMPLATE_EXPRESSION__UNIQUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__LOWER_BOUND = TEMPLATE_EXPRESSION__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__UPPER_BOUND = TEMPLATE_EXPRESSION__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__MANY = TEMPLATE_EXPRESSION__MANY;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__REQUIRED = TEMPLATE_EXPRESSION__REQUIRED;

	/**
	 * The feature id for the '<em><b>EType</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__ETYPE = TEMPLATE_EXPRESSION__ETYPE;

	/**
	 * The feature id for the '<em><b>EGeneric Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__EGENERIC_TYPE = TEMPLATE_EXPRESSION__EGENERIC_TYPE;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__START_POSITION = TEMPLATE_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__END_POSITION = TEMPLATE_EXPRESSION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__DEFINITION = TEMPLATE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION__ARGUMENT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Macro Invocation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_INVOCATION_FEATURE_COUNT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.TypedModelImpl
	 * <em>Typed Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.TypedModelImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTypedModel()
	 * @generated
	 */
	int TYPED_MODEL = 17;

	/**
	 * The feature id for the '<em><b>Takes Types From</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_MODEL__TAKES_TYPES_FROM = 0;

	/**
	 * The number of structural features of the '<em>Typed Model</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_MODEL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.CommentImpl <em>Comment</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.CommentImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getComment()
	 * @generated
	 * @since 3.1
	 */
	int COMMENT = 18;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT__EANNOTATIONS = MODULE_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT__NAME = MODULE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT__START_POSITION = MODULE_ELEMENT__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT__END_POSITION = MODULE_ELEMENT__END_POSITION;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT__VISIBILITY = MODULE_ELEMENT__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT__BODY = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.DocumentationImpl
	 * <em>Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.DocumentationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getDocumentation()
	 * @generated
	 * @since 3.1
	 */
	int DOCUMENTATION = 19;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__EANNOTATIONS = COMMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__NAME = COMMENT__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__START_POSITION = COMMENT__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__END_POSITION = COMMENT__END_POSITION;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__VISIBILITY = COMMENT__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__BODY = COMMENT__BODY;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' container reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__DOCUMENTED_ELEMENT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Documentation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.DocumentedElement
	 * <em>Documented Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.DocumentedElement
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getDocumentedElement()
	 * @generated
	 * @since 3.1
	 */
	int DOCUMENTED_ELEMENT = 20;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTED_ELEMENT__DOCUMENTATION = 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTED_ELEMENT__DEPRECATED = 1;

	/**
	 * The number of structural features of the '<em>Documented Element</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTED_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl
	 * <em>Comment Body</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getCommentBody()
	 * @generated
	 * @since 3.1
	 */
	int COMMENT_BODY = 21;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT_BODY__START_POSITION = 0;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT_BODY__END_POSITION = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT_BODY__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Comment Body</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int COMMENT_BODY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleDocumentationImpl
	 * <em>Module Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ModuleDocumentationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModuleDocumentation()
	 * @generated
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION = 22;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__EANNOTATIONS = DOCUMENTATION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__NAME = DOCUMENTATION__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__START_POSITION = DOCUMENTATION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__END_POSITION = DOCUMENTATION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__VISIBILITY = DOCUMENTATION__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__BODY = DOCUMENTATION__BODY;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' container reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT = DOCUMENTATION__DOCUMENTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__AUTHOR = DOCUMENTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__VERSION = DOCUMENTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Since</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION__SINCE = DOCUMENTATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Module Documentation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_DOCUMENTATION_FEATURE_COUNT = DOCUMENTATION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementDocumentationImpl
	 * <em>Module Element Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ModuleElementDocumentationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModuleElementDocumentation()
	 * @generated
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION = 23;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__EANNOTATIONS = DOCUMENTATION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__NAME = DOCUMENTATION__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__START_POSITION = DOCUMENTATION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__END_POSITION = DOCUMENTATION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__VISIBILITY = DOCUMENTATION__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__BODY = DOCUMENTATION__BODY;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' container reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT = DOCUMENTATION__DOCUMENTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Parameters Documentation</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION = DOCUMENTATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Module Element Documentation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE_ELEMENT_DOCUMENTATION_FEATURE_COUNT = DOCUMENTATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.impl.ParameterDocumentationImpl
	 * <em>Parameter Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.impl.ParameterDocumentationImpl
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getParameterDocumentation()
	 * @generated
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION = 24;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION__EANNOTATIONS = COMMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION__NAME = COMMENT__NAME;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION__START_POSITION = COMMENT__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION__END_POSITION = COMMENT__END_POSITION;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION__VISIBILITY = COMMENT__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION__BODY = COMMENT__BODY;

	/**
	 * The number of structural features of the '<em>Parameter Documentation</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int PARAMETER_DOCUMENTATION_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.VisibilityKind
	 * <em>Visibility Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.VisibilityKind
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getVisibilityKind()
	 * @generated
	 */
	int VISIBILITY_KIND = 25;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.model.mtl.OpenModeKind <em>Open Mode Kind</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.model.mtl.OpenModeKind
	 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getOpenModeKind()
	 * @generated
	 */
	int OPEN_MODE_KIND = 26;

	/**
	 * Returns the meta object for class ' {@link org.eclipse.acceleo.model.mtl.Module <em>Module</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module
	 * @generated
	 */
	EClass getModule();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Module#getInput <em>Input</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Input</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module#getInput()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Input();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.acceleo.model.mtl.Module#getExtends
	 * <em>Extends</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Extends</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module#getExtends()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Extends();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.acceleo.model.mtl.Module#getImports
	 * <em>Imports</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Imports</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module#getImports()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Imports();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Module#getOwnedModuleElement <em>Owned Module Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Owned Module Element</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module#getOwnedModuleElement()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_OwnedModuleElement();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.Module#getStartHeaderPosition <em>Start Header Position</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start Header Position</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module#getStartHeaderPosition()
	 * @see #getModule()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getModule_StartHeaderPosition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.Module#getEndHeaderPosition <em>End Header Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Header Position</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Module#getEndHeaderPosition()
	 * @see #getModule()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getModule_EndHeaderPosition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.ModuleElement
	 * <em>Module Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleElement
	 * @generated
	 */
	EClass getModuleElement();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.ModuleElement#getVisibility <em>Visibility</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleElement#getVisibility()
	 * @see #getModuleElement()
	 * @generated
	 */
	EAttribute getModuleElement_Visibility();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.TemplateExpression
	 * <em>Template Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template Expression</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateExpression
	 * @generated
	 */
	EClass getTemplateExpression();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.acceleo.model.mtl.Block <em>Block</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.Block#getInit <em>Init</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Init</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Block#getInit()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Init();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Block#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Body</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Block#getBody()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.InitSection
	 * <em>Init Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Init Section</em>'.
	 * @see org.eclipse.acceleo.model.mtl.InitSection
	 * @generated
	 */
	EClass getInitSection();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.InitSection#getVariable <em>Variable</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Variable</em>'.
	 * @see org.eclipse.acceleo.model.mtl.InitSection#getVariable()
	 * @see #getInitSection()
	 * @generated
	 */
	EReference getInitSection_Variable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.Template <em>Template</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Template
	 * @generated
	 */
	EClass getTemplate();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Template#getOverrides <em>Overrides</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Overrides</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Template#getOverrides()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Overrides();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Template#getParameter <em>Parameter</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Template#getParameter()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Parameter();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.Template#getGuard <em>Guard</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Template#getGuard()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Guard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.model.mtl.Template#isMain
	 * <em>Main</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Main</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Template#isMain()
	 * @see #getTemplate()
	 * @generated
	 */
	EAttribute getTemplate_Main();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.Template#getPost <em>Post</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Post</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Template#getPost()
	 * @see #getTemplate()
	 * @generated
	 * @since 3.0
	 */
	EReference getTemplate_Post();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation
	 * <em>Template Invocation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template Invocation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation
	 * @generated
	 */
	EClass getTemplateInvocation();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getDefinition <em>Definition</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation#getDefinition()
	 * @see #getTemplateInvocation()
	 * @generated
	 */
	EReference getTemplateInvocation_Definition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getArgument <em>Argument</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Argument</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation#getArgument()
	 * @see #getTemplateInvocation()
	 * @generated
	 */
	EReference getTemplateInvocation_Argument();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getBefore <em>Before</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Before</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation#getBefore()
	 * @see #getTemplateInvocation()
	 * @generated
	 */
	EReference getTemplateInvocation_Before();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getAfter <em>After</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>After</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation#getAfter()
	 * @see #getTemplateInvocation()
	 * @generated
	 */
	EReference getTemplateInvocation_After();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getEach <em>Each</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Each</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation#getEach()
	 * @see #getTemplateInvocation()
	 * @generated
	 */
	EReference getTemplateInvocation_Each();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.TemplateInvocation#isSuper <em>Super</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Super</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation#isSuper()
	 * @see #getTemplateInvocation()
	 * @generated
	 */
	EAttribute getTemplateInvocation_Super();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.acceleo.model.mtl.Query <em>Query</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Query
	 * @generated
	 */
	EClass getQuery();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Query#getParameter <em>Parameter</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Query#getParameter()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Parameter();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.Query#getExpression <em>Expression</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Query#getExpression()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Expression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.model.mtl.Query#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Query#getType()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.QueryInvocation
	 * <em>Query Invocation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query Invocation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.QueryInvocation
	 * @generated
	 */
	EClass getQueryInvocation();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.model.mtl.QueryInvocation#getDefinition <em>Definition</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.eclipse.acceleo.model.mtl.QueryInvocation#getDefinition()
	 * @see #getQueryInvocation()
	 * @generated
	 */
	EReference getQueryInvocation_Definition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.QueryInvocation#getArgument <em>Argument</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Argument</em>'.
	 * @see org.eclipse.acceleo.model.mtl.QueryInvocation#getArgument()
	 * @see #getQueryInvocation()
	 * @generated
	 */
	EReference getQueryInvocation_Argument();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.ProtectedAreaBlock
	 * <em>Protected Area Block</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Protected Area Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ProtectedAreaBlock
	 * @generated
	 */
	EClass getProtectedAreaBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ProtectedAreaBlock#getMarker <em>Marker</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Marker</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ProtectedAreaBlock#getMarker()
	 * @see #getProtectedAreaBlock()
	 * @generated
	 */
	EReference getProtectedAreaBlock_Marker();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.ForBlock <em>For Block</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>For Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock
	 * @generated
	 */
	EClass getForBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ForBlock#getLoopVariable <em>Loop Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference ' <em>Loop Variable</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock#getLoopVariable()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_LoopVariable();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ForBlock#getIterSet <em>Iter Set</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Iter Set</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock#getIterSet()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_IterSet();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ForBlock#getBefore <em>Before</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Before</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock#getBefore()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_Before();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ForBlock#getEach <em>Each</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Each</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock#getEach()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_Each();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ForBlock#getAfter <em>After</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>After</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock#getAfter()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_After();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.ForBlock#getGuard <em>Guard</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock#getGuard()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_Guard();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.IfBlock <em>If Block</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>If Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.IfBlock
	 * @generated
	 */
	EClass getIfBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.IfBlock#getIfExpr <em>If Expr</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>If Expr</em>'.
	 * @see org.eclipse.acceleo.model.mtl.IfBlock#getIfExpr()
	 * @see #getIfBlock()
	 * @generated
	 */
	EReference getIfBlock_IfExpr();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.IfBlock#getElse <em>Else</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.acceleo.model.mtl.IfBlock#getElse()
	 * @see #getIfBlock()
	 * @generated
	 */
	EReference getIfBlock_Else();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.IfBlock#getElseIf <em>Else If</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else If</em>'.
	 * @see org.eclipse.acceleo.model.mtl.IfBlock#getElseIf()
	 * @see #getIfBlock()
	 * @generated
	 */
	EReference getIfBlock_ElseIf();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.LetBlock <em>Let Block</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Let Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.LetBlock
	 * @generated
	 */
	EClass getLetBlock();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.LetBlock#getElseLet <em>Else Let</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else Let</em>'.
	 * @see org.eclipse.acceleo.model.mtl.LetBlock#getElseLet()
	 * @see #getLetBlock()
	 * @generated
	 */
	EReference getLetBlock_ElseLet();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.LetBlock#getElse <em>Else</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.acceleo.model.mtl.LetBlock#getElse()
	 * @see #getLetBlock()
	 * @generated
	 */
	EReference getLetBlock_Else();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.LetBlock#getLetVariable <em>Let Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference ' <em>Let Variable</em>'.
	 * @see org.eclipse.acceleo.model.mtl.LetBlock#getLetVariable()
	 * @see #getLetBlock()
	 * @generated
	 */
	EReference getLetBlock_LetVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.FileBlock <em>File Block</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>File Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.FileBlock
	 * @generated
	 */
	EClass getFileBlock();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.model.mtl.FileBlock#getOpenMode
	 * <em>Open Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Open Mode</em>'.
	 * @see org.eclipse.acceleo.model.mtl.FileBlock#getOpenMode()
	 * @see #getFileBlock()
	 * @generated
	 */
	EAttribute getFileBlock_OpenMode();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.FileBlock#getFileUrl <em>File Url</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>File Url</em>'.
	 * @see org.eclipse.acceleo.model.mtl.FileBlock#getFileUrl()
	 * @see #getFileBlock()
	 * @generated
	 */
	EReference getFileBlock_FileUrl();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.FileBlock#getUniqId <em>Uniq Id</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Uniq Id</em>'.
	 * @see org.eclipse.acceleo.model.mtl.FileBlock#getUniqId()
	 * @see #getFileBlock()
	 * @generated
	 */
	EReference getFileBlock_UniqId();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.FileBlock#getCharset <em>Charset</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Charset</em>'.
	 * @see org.eclipse.acceleo.model.mtl.FileBlock#getCharset()
	 * @see #getFileBlock()
	 * @generated
	 * @since 3.0
	 */
	EReference getFileBlock_Charset();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.TraceBlock
	 * <em>Trace Block</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Trace Block</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TraceBlock
	 * @generated
	 */
	EClass getTraceBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.TraceBlock#getModelElement <em>Model Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference ' <em>Model Element</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TraceBlock#getModelElement()
	 * @see #getTraceBlock()
	 * @generated
	 */
	EReference getTraceBlock_ModelElement();

	/**
	 * Returns the meta object for class ' {@link org.eclipse.acceleo.model.mtl.Macro <em>Macro</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Macro</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Macro
	 * @generated
	 */
	EClass getMacro();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.Macro#getParameter <em>Parameter</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Macro#getParameter()
	 * @see #getMacro()
	 * @generated
	 */
	EReference getMacro_Parameter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.model.mtl.Macro#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Macro#getType()
	 * @see #getMacro()
	 * @generated
	 */
	EReference getMacro_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.MacroInvocation
	 * <em>Macro Invocation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Macro Invocation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.MacroInvocation
	 * @generated
	 */
	EClass getMacroInvocation();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.model.mtl.MacroInvocation#getDefinition <em>Definition</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.eclipse.acceleo.model.mtl.MacroInvocation#getDefinition()
	 * @see #getMacroInvocation()
	 * @generated
	 */
	EReference getMacroInvocation_Definition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.MacroInvocation#getArgument <em>Argument</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list ' <em>Argument</em>'.
	 * @see org.eclipse.acceleo.model.mtl.MacroInvocation#getArgument()
	 * @see #getMacroInvocation()
	 * @generated
	 */
	EReference getMacroInvocation_Argument();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.TypedModel
	 * <em>Typed Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Typed Model</em>'.
	 * @see org.eclipse.acceleo.model.mtl.TypedModel
	 * @generated
	 */
	EClass getTypedModel();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.model.mtl.TypedModel#getTakesTypesFrom <em>Takes Types From</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Takes Types From</em> '.
	 * @see org.eclipse.acceleo.model.mtl.TypedModel#getTakesTypesFrom()
	 * @see #getTypedModel()
	 * @generated
	 */
	EReference getTypedModel_TakesTypesFrom();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Comment
	 * @generated
	 * @since 3.1
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.model.mtl.Comment#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Comment#getBody()
	 * @see #getComment()
	 * @generated
	 * @since 3.1
	 */
	EReference getComment_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.Documentation
	 * <em>Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Documentation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Documentation
	 * @generated
	 * @since 3.1
	 */
	EClass getDocumentation();

	/**
	 * Returns the meta object for the container reference '
	 * {@link org.eclipse.acceleo.model.mtl.Documentation#getDocumentedElement <em>Documented Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Documented Element</em>'.
	 * @see org.eclipse.acceleo.model.mtl.Documentation#getDocumentedElement()
	 * @see #getDocumentation()
	 * @generated
	 * @since 3.1
	 */
	EReference getDocumentation_DocumentedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.DocumentedElement
	 * <em>Documented Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Documented Element</em>'.
	 * @see org.eclipse.acceleo.model.mtl.DocumentedElement
	 * @generated
	 * @since 3.1
	 */
	EClass getDocumentedElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.model.mtl.DocumentedElement#getDocumentation <em>Documentation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Documentation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.DocumentedElement#getDocumentation()
	 * @see #getDocumentedElement()
	 * @generated
	 * @since 3.1
	 */
	EReference getDocumentedElement_Documentation();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.DocumentedElement#isDeprecated <em>Deprecated</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Deprecated</em>'.
	 * @see org.eclipse.acceleo.model.mtl.DocumentedElement#isDeprecated()
	 * @see #getDocumentedElement()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getDocumentedElement_Deprecated();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.CommentBody
	 * <em>Comment Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comment Body</em>'.
	 * @see org.eclipse.acceleo.model.mtl.CommentBody
	 * @generated
	 * @since 3.1
	 */
	EClass getCommentBody();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.CommentBody#getStartPosition <em>Start Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start Position</em>'.
	 * @see org.eclipse.acceleo.model.mtl.CommentBody#getStartPosition()
	 * @see #getCommentBody()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getCommentBody_StartPosition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.CommentBody#getEndPosition <em>End Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Position</em>'.
	 * @see org.eclipse.acceleo.model.mtl.CommentBody#getEndPosition()
	 * @see #getCommentBody()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getCommentBody_EndPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.model.mtl.CommentBody#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.model.mtl.CommentBody#getValue()
	 * @see #getCommentBody()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getCommentBody_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation
	 * <em>Module Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Documentation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleDocumentation
	 * @generated
	 * @since 3.1
	 */
	EClass getModuleDocumentation();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getAuthor <em>Author</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleDocumentation#getAuthor()
	 * @see #getModuleDocumentation()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getModuleDocumentation_Author();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getVersion <em>Version</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleDocumentation#getVersion()
	 * @see #getModuleDocumentation()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getModuleDocumentation_Version();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.model.mtl.ModuleDocumentation#getSince <em>Since</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Since</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleDocumentation#getSince()
	 * @see #getModuleDocumentation()
	 * @generated
	 * @since 3.1
	 */
	EAttribute getModuleDocumentation_Since();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.ModuleElementDocumentation
	 * <em>Module Element Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Element Documentation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleElementDocumentation
	 * @generated
	 * @since 3.1
	 */
	EClass getModuleElementDocumentation();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.model.mtl.ModuleElementDocumentation#getParametersDocumentation
	 * <em>Parameters Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters Documentation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ModuleElementDocumentation#getParametersDocumentation()
	 * @see #getModuleElementDocumentation()
	 * @generated
	 * @since 3.1
	 */
	EReference getModuleElementDocumentation_ParametersDocumentation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.model.mtl.ParameterDocumentation
	 * <em>Parameter Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parameter Documentation</em>'.
	 * @see org.eclipse.acceleo.model.mtl.ParameterDocumentation
	 * @generated
	 * @since 3.1
	 */
	EClass getParameterDocumentation();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.model.mtl.VisibilityKind
	 * <em>Visibility Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Visibility Kind</em>'.
	 * @see org.eclipse.acceleo.model.mtl.VisibilityKind
	 * @generated
	 */
	EEnum getVisibilityKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.model.mtl.OpenModeKind
	 * <em>Open Mode Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Open Mode Kind</em>'.
	 * @see org.eclipse.acceleo.model.mtl.OpenModeKind
	 * @generated
	 */
	EEnum getOpenModeKind();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MtlFactory getMtlFactory();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleImpl
		 * <em>Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ModuleImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModule()
		 * @generated
		 */
		EClass MODULE = eINSTANCE.getModule();

		/**
		 * The meta object literal for the '<em><b>Input</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__INPUT = eINSTANCE.getModule_Input();

		/**
		 * The meta object literal for the '<em><b>Extends</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__EXTENDS = eINSTANCE.getModule_Extends();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__IMPORTS = eINSTANCE.getModule_Imports();

		/**
		 * The meta object literal for the '<em><b>Owned Module Element</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__OWNED_MODULE_ELEMENT = eINSTANCE.getModule_OwnedModuleElement();

		/**
		 * The meta object literal for the '<em><b>Start Header Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute MODULE__START_HEADER_POSITION = eINSTANCE.getModule_StartHeaderPosition();

		/**
		 * The meta object literal for the '<em><b>End Header Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute MODULE__END_HEADER_POSITION = eINSTANCE.getModule_EndHeaderPosition();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl
		 * <em>Module Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModuleElement()
		 * @generated
		 */
		EClass MODULE_ELEMENT = eINSTANCE.getModuleElement();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_ELEMENT__VISIBILITY = eINSTANCE.getModuleElement_Visibility();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.TemplateExpressionImpl
		 * <em>Template Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.TemplateExpressionImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTemplateExpression()
		 * @generated
		 */
		EClass TEMPLATE_EXPRESSION = eINSTANCE.getTemplateExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.BlockImpl
		 * <em>Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.BlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getBlock()
		 * @generated
		 */
		EClass BLOCK = eINSTANCE.getBlock();

		/**
		 * The meta object literal for the '<em><b>Init</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK__INIT = eINSTANCE.getBlock_Init();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK__BODY = eINSTANCE.getBlock_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.InitSectionImpl
		 * <em>Init Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.InitSectionImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getInitSection()
		 * @generated
		 */
		EClass INIT_SECTION = eINSTANCE.getInitSection();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INIT_SECTION__VARIABLE = eINSTANCE.getInitSection_Variable();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl
		 * <em>Template</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.TemplateImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTemplate()
		 * @generated
		 */
		EClass TEMPLATE = eINSTANCE.getTemplate();

		/**
		 * The meta object literal for the '<em><b>Overrides</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__OVERRIDES = eINSTANCE.getTemplate_Overrides();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__PARAMETER = eINSTANCE.getTemplate_Parameter();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__GUARD = eINSTANCE.getTemplate_Guard();

		/**
		 * The meta object literal for the '<em><b>Main</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE__MAIN = eINSTANCE.getTemplate_Main();

		/**
		 * The meta object literal for the '<em><b>Post</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.0
		 */
		EReference TEMPLATE__POST = eINSTANCE.getTemplate_Post();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl
		 * <em>Template Invocation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTemplateInvocation()
		 * @generated
		 */
		EClass TEMPLATE_INVOCATION = eINSTANCE.getTemplateInvocation();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE_INVOCATION__DEFINITION = eINSTANCE.getTemplateInvocation_Definition();

		/**
		 * The meta object literal for the '<em><b>Argument</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE_INVOCATION__ARGUMENT = eINSTANCE.getTemplateInvocation_Argument();

		/**
		 * The meta object literal for the '<em><b>Before</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE_INVOCATION__BEFORE = eINSTANCE.getTemplateInvocation_Before();

		/**
		 * The meta object literal for the '<em><b>After</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE_INVOCATION__AFTER = eINSTANCE.getTemplateInvocation_After();

		/**
		 * The meta object literal for the '<em><b>Each</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE_INVOCATION__EACH = eINSTANCE.getTemplateInvocation_Each();

		/**
		 * The meta object literal for the '<em><b>Super</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE_INVOCATION__SUPER = eINSTANCE.getTemplateInvocation_Super();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.QueryImpl
		 * <em>Query</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.QueryImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getQuery()
		 * @generated
		 */
		EClass QUERY = eINSTANCE.getQuery();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__PARAMETER = eINSTANCE.getQuery_Parameter();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__EXPRESSION = eINSTANCE.getQuery_Expression();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__TYPE = eINSTANCE.getQuery_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.QueryInvocationImpl
		 * <em>Query Invocation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.QueryInvocationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getQueryInvocation()
		 * @generated
		 */
		EClass QUERY_INVOCATION = eINSTANCE.getQueryInvocation();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY_INVOCATION__DEFINITION = eINSTANCE.getQueryInvocation_Definition();

		/**
		 * The meta object literal for the '<em><b>Argument</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY_INVOCATION__ARGUMENT = eINSTANCE.getQueryInvocation_Argument();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.ProtectedAreaBlockImpl
		 * <em>Protected Area Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ProtectedAreaBlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getProtectedAreaBlock()
		 * @generated
		 */
		EClass PROTECTED_AREA_BLOCK = eINSTANCE.getProtectedAreaBlock();

		/**
		 * The meta object literal for the '<em><b>Marker</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROTECTED_AREA_BLOCK__MARKER = eINSTANCE.getProtectedAreaBlock_Marker();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl
		 * <em>For Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ForBlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getForBlock()
		 * @generated
		 */
		EClass FOR_BLOCK = eINSTANCE.getForBlock();

		/**
		 * The meta object literal for the '<em><b>Loop Variable</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_BLOCK__LOOP_VARIABLE = eINSTANCE.getForBlock_LoopVariable();

		/**
		 * The meta object literal for the '<em><b>Iter Set</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_BLOCK__ITER_SET = eINSTANCE.getForBlock_IterSet();

		/**
		 * The meta object literal for the '<em><b>Before</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_BLOCK__BEFORE = eINSTANCE.getForBlock_Before();

		/**
		 * The meta object literal for the '<em><b>Each</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_BLOCK__EACH = eINSTANCE.getForBlock_Each();

		/**
		 * The meta object literal for the '<em><b>After</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_BLOCK__AFTER = eINSTANCE.getForBlock_After();

		/**
		 * The meta object literal for the '<em><b>Guard</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_BLOCK__GUARD = eINSTANCE.getForBlock_Guard();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.IfBlockImpl
		 * <em>If Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.IfBlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getIfBlock()
		 * @generated
		 */
		EClass IF_BLOCK = eINSTANCE.getIfBlock();

		/**
		 * The meta object literal for the '<em><b>If Expr</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_BLOCK__IF_EXPR = eINSTANCE.getIfBlock_IfExpr();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_BLOCK__ELSE = eINSTANCE.getIfBlock_Else();

		/**
		 * The meta object literal for the '<em><b>Else If</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_BLOCK__ELSE_IF = eINSTANCE.getIfBlock_ElseIf();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.LetBlockImpl
		 * <em>Let Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.LetBlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getLetBlock()
		 * @generated
		 */
		EClass LET_BLOCK = eINSTANCE.getLetBlock();

		/**
		 * The meta object literal for the '<em><b>Else Let</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET_BLOCK__ELSE_LET = eINSTANCE.getLetBlock_ElseLet();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET_BLOCK__ELSE = eINSTANCE.getLetBlock_Else();

		/**
		 * The meta object literal for the '<em><b>Let Variable</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET_BLOCK__LET_VARIABLE = eINSTANCE.getLetBlock_LetVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.FileBlockImpl
		 * <em>File Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.FileBlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getFileBlock()
		 * @generated
		 */
		EClass FILE_BLOCK = eINSTANCE.getFileBlock();

		/**
		 * The meta object literal for the '<em><b>Open Mode</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FILE_BLOCK__OPEN_MODE = eINSTANCE.getFileBlock_OpenMode();

		/**
		 * The meta object literal for the '<em><b>File Url</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FILE_BLOCK__FILE_URL = eINSTANCE.getFileBlock_FileUrl();

		/**
		 * The meta object literal for the '<em><b>Uniq Id</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FILE_BLOCK__UNIQ_ID = eINSTANCE.getFileBlock_UniqId();

		/**
		 * The meta object literal for the '<em><b>Charset</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.0
		 */
		EReference FILE_BLOCK__CHARSET = eINSTANCE.getFileBlock_Charset();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.TraceBlockImpl
		 * <em>Trace Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.TraceBlockImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTraceBlock()
		 * @generated
		 */
		EClass TRACE_BLOCK = eINSTANCE.getTraceBlock();

		/**
		 * The meta object literal for the '<em><b>Model Element</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACE_BLOCK__MODEL_ELEMENT = eINSTANCE.getTraceBlock_ModelElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.MacroImpl
		 * <em>Macro</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.MacroImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getMacro()
		 * @generated
		 */
		EClass MACRO = eINSTANCE.getMacro();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MACRO__PARAMETER = eINSTANCE.getMacro_Parameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MACRO__TYPE = eINSTANCE.getMacro_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.MacroInvocationImpl
		 * <em>Macro Invocation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.MacroInvocationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getMacroInvocation()
		 * @generated
		 */
		EClass MACRO_INVOCATION = eINSTANCE.getMacroInvocation();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MACRO_INVOCATION__DEFINITION = eINSTANCE.getMacroInvocation_Definition();

		/**
		 * The meta object literal for the '<em><b>Argument</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MACRO_INVOCATION__ARGUMENT = eINSTANCE.getMacroInvocation_Argument();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.TypedModelImpl
		 * <em>Typed Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.TypedModelImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getTypedModel()
		 * @generated
		 */
		EClass TYPED_MODEL = eINSTANCE.getTypedModel();

		/**
		 * The meta object literal for the '<em><b>Takes Types From</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPED_MODEL__TAKES_TYPES_FROM = eINSTANCE.getTypedModel_TakesTypesFrom();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.CommentImpl
		 * <em>Comment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.CommentImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getComment()
		 * @generated
		 * @since 3.1
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EReference COMMENT__BODY = eINSTANCE.getComment_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.DocumentationImpl
		 * <em>Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.DocumentationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getDocumentation()
		 * @generated
		 * @since 3.1
		 */
		EClass DOCUMENTATION = eINSTANCE.getDocumentation();

		/**
		 * The meta object literal for the '<em><b>Documented Element</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EReference DOCUMENTATION__DOCUMENTED_ELEMENT = eINSTANCE.getDocumentation_DocumentedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.DocumentedElement
		 * <em>Documented Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.DocumentedElement
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getDocumentedElement()
		 * @generated
		 * @since 3.1
		 */
		EClass DOCUMENTED_ELEMENT = eINSTANCE.getDocumentedElement();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EReference DOCUMENTED_ELEMENT__DOCUMENTATION = eINSTANCE.getDocumentedElement_Documentation();

		/**
		 * The meta object literal for the '<em><b>Deprecated</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute DOCUMENTED_ELEMENT__DEPRECATED = eINSTANCE.getDocumentedElement_Deprecated();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl
		 * <em>Comment Body</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getCommentBody()
		 * @generated
		 * @since 3.1
		 */
		EClass COMMENT_BODY = eINSTANCE.getCommentBody();

		/**
		 * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute COMMENT_BODY__START_POSITION = eINSTANCE.getCommentBody_StartPosition();

		/**
		 * The meta object literal for the '<em><b>End Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute COMMENT_BODY__END_POSITION = eINSTANCE.getCommentBody_EndPosition();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute COMMENT_BODY__VALUE = eINSTANCE.getCommentBody_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.impl.ModuleDocumentationImpl
		 * <em>Module Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ModuleDocumentationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModuleDocumentation()
		 * @generated
		 * @since 3.1
		 */
		EClass MODULE_DOCUMENTATION = eINSTANCE.getModuleDocumentation();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute MODULE_DOCUMENTATION__AUTHOR = eINSTANCE.getModuleDocumentation_Author();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute MODULE_DOCUMENTATION__VERSION = eINSTANCE.getModuleDocumentation_Version();

		/**
		 * The meta object literal for the '<em><b>Since</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EAttribute MODULE_DOCUMENTATION__SINCE = eINSTANCE.getModuleDocumentation_Since();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.model.mtl.impl.ModuleElementDocumentationImpl
		 * <em>Module Element Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ModuleElementDocumentationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getModuleElementDocumentation()
		 * @generated
		 * @since 3.1
		 */
		EClass MODULE_ELEMENT_DOCUMENTATION = eINSTANCE.getModuleElementDocumentation();

		/**
		 * The meta object literal for the '<em><b>Parameters Documentation</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EReference MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION = eINSTANCE
				.getModuleElementDocumentation_ParametersDocumentation();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.model.mtl.impl.ParameterDocumentationImpl
		 * <em>Parameter Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.impl.ParameterDocumentationImpl
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getParameterDocumentation()
		 * @generated
		 * @since 3.1
		 */
		EClass PARAMETER_DOCUMENTATION = eINSTANCE.getParameterDocumentation();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.VisibilityKind
		 * <em>Visibility Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.VisibilityKind
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getVisibilityKind()
		 * @generated
		 */
		EEnum VISIBILITY_KIND = eINSTANCE.getVisibilityKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.model.mtl.OpenModeKind
		 * <em>Open Mode Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.model.mtl.OpenModeKind
		 * @see org.eclipse.acceleo.model.mtl.impl.MtlPackageImpl#getOpenModeKind()
		 * @generated
		 */
		EEnum OPEN_MODE_KIND = eINSTANCE.getOpenModeKind();

	}

} // MtlPackage
