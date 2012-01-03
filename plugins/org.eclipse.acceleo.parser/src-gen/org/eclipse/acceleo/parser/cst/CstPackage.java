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
package org.eclipse.acceleo.parser.cst;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

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
 * @see org.eclipse.acceleo.parser.cst.CstFactory
 * @model kind="package"
 * @generated
 * @since 3.0
 */
public interface CstPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "cst"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/mtl/cst/3.0"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "cst"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	CstPackage eINSTANCE = org.eclipse.acceleo.parser.cst.impl.CstPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.CSTNode <em>CST Node</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.CSTNode
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getCSTNode()
	 * @generated
	 */
	int CST_NODE = 0;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CST_NODE__START_POSITION = 0;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CST_NODE__END_POSITION = 1;

	/**
	 * The number of structural features of the '<em>CST Node</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CST_NODE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleImpl <em>Module</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ModuleImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModule()
	 * @generated
	 */
	int MODULE = 1;

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
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__START_POSITION = EcorePackage.EPACKAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__END_POSITION = EcorePackage.EPACKAGE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Input</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__INPUT = EcorePackage.EPACKAGE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Owned Module Element</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__OWNED_MODULE_ELEMENT = EcorePackage.EPACKAGE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__EXTENDS = EcorePackage.EPACKAGE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__IMPORTS = EcorePackage.EPACKAGE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int MODULE__DOCUMENTATION = EcorePackage.EPACKAGE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Module</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_FEATURE_COUNT = EcorePackage.EPACKAGE_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleExtendsValueImpl
	 * <em>Module Extends Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ModuleExtendsValueImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModuleExtendsValue()
	 * @generated
	 */
	int MODULE_EXTENDS_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_EXTENDS_VALUE__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_EXTENDS_VALUE__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_EXTENDS_VALUE__NAME = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Module Extends Value</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_EXTENDS_VALUE_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleImportsValueImpl
	 * <em>Module Imports Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ModuleImportsValueImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModuleImportsValue()
	 * @generated
	 */
	int MODULE_IMPORTS_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_IMPORTS_VALUE__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_IMPORTS_VALUE__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_IMPORTS_VALUE__NAME = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Module Imports Value</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_IMPORTS_VALUE_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.TypedModelImpl
	 * <em>Typed Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.TypedModelImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTypedModel()
	 * @generated
	 */
	int TYPED_MODEL = 4;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_MODEL__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_MODEL__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Takes Types From</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_MODEL__TAKES_TYPES_FROM = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Model</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_MODEL_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl
	 * <em>Module Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModuleElement()
	 * @generated
	 */
	int MODULE_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__NAME = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__VISIBILITY = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Module Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.CommentImpl <em>Comment</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.CommentImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 6;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__START_POSITION = MODULE_ELEMENT__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__END_POSITION = MODULE_ELEMENT__END_POSITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__NAME = MODULE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__VISIBILITY = MODULE_ELEMENT__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__BODY = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.TemplateExpressionImpl
	 * <em>Template Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.TemplateExpressionImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTemplateExpression()
	 * @generated
	 */
	int TEMPLATE_EXPRESSION = 10;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The number of structural features of the '<em>Template Expression</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_EXPRESSION_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.BlockImpl <em>Block</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.BlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 13;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl <em>Template</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.TemplateImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTemplate()
	 * @generated
	 */
	int TEMPLATE = 7;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__NAME = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__VISIBILITY = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Overrides</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__OVERRIDES = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__PARAMETER = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__GUARD = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__POST = BLOCK_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Template</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.TemplateOverridesValueImpl
	 * <em>Template Overrides Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.TemplateOverridesValueImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTemplateOverridesValue()
	 * @generated
	 */
	int TEMPLATE_OVERRIDES_VALUE = 8;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_OVERRIDES_VALUE__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_OVERRIDES_VALUE__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_OVERRIDES_VALUE__NAME = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Template Overrides Value</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_OVERRIDES_VALUE_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.VariableImpl <em>Variable</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.VariableImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 9;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Init Expression</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__INIT_EXPRESSION = CST_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl
	 * <em>Model Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModelExpression()
	 * @generated
	 */
	int MODEL_EXPRESSION = 11;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION__START_POSITION = TEMPLATE_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION__END_POSITION = TEMPLATE_EXPRESSION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION__BODY = TEMPLATE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Before</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION__BEFORE = TEMPLATE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Each</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION__EACH = TEMPLATE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>After</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION__AFTER = TEMPLATE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Model Expression</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_EXPRESSION_FEATURE_COUNT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.TextExpressionImpl
	 * <em>Text Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.TextExpressionImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTextExpression()
	 * @generated
	 */
	int TEXT_EXPRESSION = 12;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION__START_POSITION = TEMPLATE_EXPRESSION__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION__END_POSITION = TEMPLATE_EXPRESSION__END_POSITION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION__VALUE = TEMPLATE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Expression</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_EXPRESSION_FEATURE_COUNT = TEMPLATE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.InitSectionImpl
	 * <em>Init Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.InitSectionImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getInitSection()
	 * @generated
	 */
	int INIT_SECTION = 14;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION__START_POSITION = CST_NODE__START_POSITION;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION__END_POSITION = CST_NODE__END_POSITION;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION__VARIABLE = CST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Init Section</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INIT_SECTION_FEATURE_COUNT = CST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ProtectedAreaBlockImpl
	 * <em>Protected Area Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ProtectedAreaBlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getProtectedAreaBlock()
	 * @generated
	 */
	int PROTECTED_AREA_BLOCK = 15;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl <em>For Block</em>}
	 * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.ForBlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getForBlock()
	 * @generated
	 */
	int FOR_BLOCK = 16;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.IfBlockImpl <em>If Block</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.IfBlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getIfBlock()
	 * @generated
	 */
	int IF_BLOCK = 17;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.LetBlockImpl <em>Let Block</em>}
	 * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.LetBlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getLetBlock()
	 * @generated
	 */
	int LET_BLOCK = 18;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.FileBlockImpl
	 * <em>File Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.FileBlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getFileBlock()
	 * @generated
	 */
	int FILE_BLOCK = 19;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.TraceBlockImpl
	 * <em>Trace Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.TraceBlockImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTraceBlock()
	 * @generated
	 */
	int TRACE_BLOCK = 20;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.MacroImpl <em>Macro</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.MacroImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getMacro()
	 * @generated
	 */
	int MACRO = 21;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__NAME = BLOCK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__VISIBILITY = BLOCK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__PARAMETER = BLOCK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO__TYPE = BLOCK_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Macro</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MACRO_FEATURE_COUNT = BLOCK_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.QueryImpl <em>Query</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.QueryImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getQuery()
	 * @generated
	 */
	int QUERY = 22;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__NAME = MODULE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__VISIBILITY = MODULE_ELEMENT__VISIBILITY;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__PARAMETER = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__TYPE = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__EXPRESSION = MODULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Query</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.impl.DocumentationImpl
	 * <em>Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.impl.DocumentationImpl
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getDocumentation()
	 * @generated
	 * @since 3.1
	 */
	int DOCUMENTATION = 23;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__NAME = COMMENT__NAME;

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
	 * The feature id for the '<em><b>Body</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION__BODY = COMMENT__BODY;

	/**
	 * The number of structural features of the '<em>Documentation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 * @since 3.1
	 */
	int DOCUMENTATION_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.VisibilityKind
	 * <em>Visibility Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.VisibilityKind
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getVisibilityKind()
	 * @generated
	 */
	int VISIBILITY_KIND = 24;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.parser.cst.OpenModeKind <em>Open Mode Kind</em>}
	 * ' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.parser.cst.OpenModeKind
	 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getOpenModeKind()
	 * @generated
	 */
	int OPEN_MODE_KIND = 25;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.CSTNode <em>CST Node</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>CST Node</em>'.
	 * @see org.eclipse.acceleo.parser.cst.CSTNode
	 * @generated
	 */
	EClass getCSTNode();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.CSTNode#getStartPosition <em>Start Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start Position</em>'.
	 * @see org.eclipse.acceleo.parser.cst.CSTNode#getStartPosition()
	 * @see #getCSTNode()
	 * @generated
	 */
	EAttribute getCSTNode_StartPosition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.CSTNode#getEndPosition <em>End Position</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Position</em>'.
	 * @see org.eclipse.acceleo.parser.cst.CSTNode#getEndPosition()
	 * @see #getCSTNode()
	 * @generated
	 */
	EAttribute getCSTNode_EndPosition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Module <em>Module</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Module
	 * @generated
	 */
	EClass getModule();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Module#getInput <em>Input</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Input</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Module#getInput()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Input();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Module#getOwnedModuleElement <em>Owned Module Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Owned Module Element</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Module#getOwnedModuleElement()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_OwnedModuleElement();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Module#getExtends <em>Extends</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Extends</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Module#getExtends()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Extends();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Module#getImports <em>Imports</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Imports</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Module#getImports()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Imports();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.Module#getDocumentation <em>Documentation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Documentation</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Module#getDocumentation()
	 * @see #getModule()
	 * @generated
	 * @since 3.1
	 */
	EReference getModule_Documentation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.ModuleExtendsValue
	 * <em>Module Extends Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Extends Value</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleExtendsValue
	 * @generated
	 */
	EClass getModuleExtendsValue();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.ModuleExtendsValue#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleExtendsValue#getName()
	 * @see #getModuleExtendsValue()
	 * @generated
	 */
	EAttribute getModuleExtendsValue_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.ModuleImportsValue
	 * <em>Module Imports Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Imports Value</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleImportsValue
	 * @generated
	 */
	EClass getModuleImportsValue();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.ModuleImportsValue#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleImportsValue#getName()
	 * @see #getModuleImportsValue()
	 * @generated
	 */
	EAttribute getModuleImportsValue_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.TypedModel
	 * <em>Typed Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Typed Model</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TypedModel
	 * @generated
	 */
	EClass getTypedModel();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.parser.cst.TypedModel#getTakesTypesFrom <em>Takes Types From</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Takes Types From</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TypedModel#getTakesTypesFrom()
	 * @see #getTypedModel()
	 * @generated
	 */
	EReference getTypedModel_TakesTypesFrom();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.ModuleElement
	 * <em>Module Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleElement
	 * @generated
	 */
	EClass getModuleElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.ModuleElement#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleElement#getName()
	 * @see #getModuleElement()
	 * @generated
	 */
	EAttribute getModuleElement_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.ModuleElement#getVisibility <em>Visibility</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModuleElement#getVisibility()
	 * @see #getModuleElement()
	 * @generated
	 */
	EAttribute getModuleElement_Visibility();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.Comment#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Comment#getBody()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Template <em>Template</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Template
	 * @generated
	 */
	EClass getTemplate();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Template#getOverrides <em>Overrides</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Overrides</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Template#getOverrides()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Overrides();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Template#getParameter <em>Parameter</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Template#getParameter()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Parameter();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.Template#getGuard <em>Guard</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Template#getGuard()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Guard();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.Template#getPost <em>Post</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Post</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Template#getPost()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Post();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.TemplateOverridesValue
	 * <em>Template Overrides Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template Overrides Value</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TemplateOverridesValue
	 * @generated
	 */
	EClass getTemplateOverridesValue();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.TemplateOverridesValue#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TemplateOverridesValue#getName()
	 * @see #getTemplateOverridesValue()
	 * @generated
	 */
	EAttribute getTemplateOverridesValue_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.Variable#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.Variable#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Variable#getType()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Type();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.Variable#getInitExpression <em>Init Expression</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Init Expression</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Variable#getInitExpression()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_InitExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.TemplateExpression
	 * <em>Template Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template Expression</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TemplateExpression
	 * @generated
	 */
	EClass getTemplateExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.ModelExpression
	 * <em>Model Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Model Expression</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModelExpression
	 * @generated
	 */
	EClass getModelExpression();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.ModelExpression#getBody <em>Body</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModelExpression#getBody()
	 * @see #getModelExpression()
	 * @generated
	 */
	EAttribute getModelExpression_Body();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ModelExpression#getBefore <em>Before</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Before</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModelExpression#getBefore()
	 * @see #getModelExpression()
	 * @generated
	 */
	EReference getModelExpression_Before();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ModelExpression#getEach <em>Each</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Each</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModelExpression#getEach()
	 * @see #getModelExpression()
	 * @generated
	 */
	EReference getModelExpression_Each();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ModelExpression#getAfter <em>After</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>After</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ModelExpression#getAfter()
	 * @see #getModelExpression()
	 * @generated
	 */
	EReference getModelExpression_After();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.TextExpression
	 * <em>Text Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Text Expression</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TextExpression
	 * @generated
	 */
	EClass getTextExpression();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.parser.cst.TextExpression#getValue <em>Value</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TextExpression#getValue()
	 * @see #getTextExpression()
	 * @generated
	 */
	EAttribute getTextExpression_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Block <em>Block</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.Block#getInit <em>Init</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Init</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Block#getInit()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Init();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Block#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Body</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Block#getBody()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.InitSection
	 * <em>Init Section</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Init Section</em>'.
	 * @see org.eclipse.acceleo.parser.cst.InitSection
	 * @generated
	 */
	EClass getInitSection();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.InitSection#getVariable <em>Variable</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Variable</em>'.
	 * @see org.eclipse.acceleo.parser.cst.InitSection#getVariable()
	 * @see #getInitSection()
	 * @generated
	 */
	EReference getInitSection_Variable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.ProtectedAreaBlock
	 * <em>Protected Area Block</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Protected Area Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ProtectedAreaBlock
	 * @generated
	 */
	EClass getProtectedAreaBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ProtectedAreaBlock#getMarker <em>Marker</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Marker</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ProtectedAreaBlock#getMarker()
	 * @see #getProtectedAreaBlock()
	 * @generated
	 */
	EReference getProtectedAreaBlock_Marker();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.ForBlock <em>For Block</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>For Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock
	 * @generated
	 */
	EClass getForBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ForBlock#getLoopVariable <em>Loop Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Loop Variable</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock#getLoopVariable()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_LoopVariable();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ForBlock#getIterSet <em>Iter Set</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Iter Set</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock#getIterSet()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_IterSet();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ForBlock#getBefore <em>Before</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Before</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock#getBefore()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_Before();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ForBlock#getEach <em>Each</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Each</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock#getEach()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_Each();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ForBlock#getAfter <em>After</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>After</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock#getAfter()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_After();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.ForBlock#getGuard <em>Guard</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see org.eclipse.acceleo.parser.cst.ForBlock#getGuard()
	 * @see #getForBlock()
	 * @generated
	 */
	EReference getForBlock_Guard();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.IfBlock <em>If Block</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>If Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.IfBlock
	 * @generated
	 */
	EClass getIfBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.IfBlock#getIfExpr <em>If Expr</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>If Expr</em>'.
	 * @see org.eclipse.acceleo.parser.cst.IfBlock#getIfExpr()
	 * @see #getIfBlock()
	 * @generated
	 */
	EReference getIfBlock_IfExpr();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.IfBlock#getElse <em>Else</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.acceleo.parser.cst.IfBlock#getElse()
	 * @see #getIfBlock()
	 * @generated
	 */
	EReference getIfBlock_Else();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.IfBlock#getElseIf <em>Else If</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else If</em>'.
	 * @see org.eclipse.acceleo.parser.cst.IfBlock#getElseIf()
	 * @see #getIfBlock()
	 * @generated
	 */
	EReference getIfBlock_ElseIf();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.LetBlock <em>Let Block</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Let Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.LetBlock
	 * @generated
	 */
	EClass getLetBlock();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.LetBlock#getElseLet <em>Else Let</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else Let</em>'.
	 * @see org.eclipse.acceleo.parser.cst.LetBlock#getElseLet()
	 * @see #getLetBlock()
	 * @generated
	 */
	EReference getLetBlock_ElseLet();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.LetBlock#getElse <em>Else</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.acceleo.parser.cst.LetBlock#getElse()
	 * @see #getLetBlock()
	 * @generated
	 */
	EReference getLetBlock_Else();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.LetBlock#getLetVariable <em>Let Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Let Variable</em>'.
	 * @see org.eclipse.acceleo.parser.cst.LetBlock#getLetVariable()
	 * @see #getLetBlock()
	 * @generated
	 */
	EReference getLetBlock_LetVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.FileBlock <em>File Block</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>File Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.FileBlock
	 * @generated
	 */
	EClass getFileBlock();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.FileBlock#getOpenMode
	 * <em>Open Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Open Mode</em>'.
	 * @see org.eclipse.acceleo.parser.cst.FileBlock#getOpenMode()
	 * @see #getFileBlock()
	 * @generated
	 */
	EAttribute getFileBlock_OpenMode();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.FileBlock#getFileUrl <em>File Url</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>File Url</em>'.
	 * @see org.eclipse.acceleo.parser.cst.FileBlock#getFileUrl()
	 * @see #getFileBlock()
	 * @generated
	 */
	EReference getFileBlock_FileUrl();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.FileBlock#getUniqId <em>Uniq Id</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Uniq Id</em>'.
	 * @see org.eclipse.acceleo.parser.cst.FileBlock#getUniqId()
	 * @see #getFileBlock()
	 * @generated
	 */
	EReference getFileBlock_UniqId();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.FileBlock#getCharset <em>Charset</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Charset</em>'.
	 * @see org.eclipse.acceleo.parser.cst.FileBlock#getCharset()
	 * @see #getFileBlock()
	 * @generated
	 */
	EReference getFileBlock_Charset();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.TraceBlock
	 * <em>Trace Block</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Trace Block</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TraceBlock
	 * @generated
	 */
	EClass getTraceBlock();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.TraceBlock#getModelElement <em>Model Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Model Element</em>'.
	 * @see org.eclipse.acceleo.parser.cst.TraceBlock#getModelElement()
	 * @see #getTraceBlock()
	 * @generated
	 */
	EReference getTraceBlock_ModelElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Macro <em>Macro</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Macro</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Macro
	 * @generated
	 */
	EClass getMacro();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Macro#getParameter <em>Parameter</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Macro#getParameter()
	 * @see #getMacro()
	 * @generated
	 */
	EReference getMacro_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.Macro#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Macro#getType()
	 * @see #getMacro()
	 * @generated
	 */
	EAttribute getMacro_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Query <em>Query</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Query
	 * @generated
	 */
	EClass getQuery();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.parser.cst.Query#getParameter <em>Parameter</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Query#getParameter()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.parser.cst.Query#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Query#getType()
	 * @see #getQuery()
	 * @generated
	 */
	EAttribute getQuery_Type();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.parser.cst.Query#getExpression <em>Expression</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Query#getExpression()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.parser.cst.Documentation
	 * <em>Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Documentation</em>'.
	 * @see org.eclipse.acceleo.parser.cst.Documentation
	 * @generated
	 * @since 3.1
	 */
	EClass getDocumentation();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.parser.cst.VisibilityKind
	 * <em>Visibility Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Visibility Kind</em>'.
	 * @see org.eclipse.acceleo.parser.cst.VisibilityKind
	 * @generated
	 */
	EEnum getVisibilityKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.parser.cst.OpenModeKind
	 * <em>Open Mode Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Open Mode Kind</em>'.
	 * @see org.eclipse.acceleo.parser.cst.OpenModeKind
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
	CstFactory getCstFactory();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.CSTNode <em>CST Node</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.CSTNode
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getCSTNode()
		 * @generated
		 */
		EClass CST_NODE = eINSTANCE.getCSTNode();

		/**
		 * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CST_NODE__START_POSITION = eINSTANCE.getCSTNode_StartPosition();

		/**
		 * The meta object literal for the '<em><b>End Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CST_NODE__END_POSITION = eINSTANCE.getCSTNode_EndPosition();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleImpl
		 * <em>Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ModuleImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModule()
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
		 * The meta object literal for the '<em><b>Owned Module Element</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__OWNED_MODULE_ELEMENT = eINSTANCE.getModule_OwnedModuleElement();

		/**
		 * The meta object literal for the '<em><b>Extends</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__EXTENDS = eINSTANCE.getModule_Extends();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__IMPORTS = eINSTANCE.getModule_Imports();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 * @since 3.1
		 */
		EReference MODULE__DOCUMENTATION = eINSTANCE.getModule_Documentation();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleExtendsValueImpl
		 * <em>Module Extends Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ModuleExtendsValueImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModuleExtendsValue()
		 * @generated
		 */
		EClass MODULE_EXTENDS_VALUE = eINSTANCE.getModuleExtendsValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_EXTENDS_VALUE__NAME = eINSTANCE.getModuleExtendsValue_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleImportsValueImpl
		 * <em>Module Imports Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ModuleImportsValueImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModuleImportsValue()
		 * @generated
		 */
		EClass MODULE_IMPORTS_VALUE = eINSTANCE.getModuleImportsValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_IMPORTS_VALUE__NAME = eINSTANCE.getModuleImportsValue_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.TypedModelImpl
		 * <em>Typed Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.TypedModelImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTypedModel()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl
		 * <em>Module Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModuleElement()
		 * @generated
		 */
		EClass MODULE_ELEMENT = eINSTANCE.getModuleElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_ELEMENT__NAME = eINSTANCE.getModuleElement_Name();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_ELEMENT__VISIBILITY = eINSTANCE.getModuleElement_Visibility();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.CommentImpl
		 * <em>Comment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.CommentImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COMMENT__BODY = eINSTANCE.getComment_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl
		 * <em>Template</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.TemplateImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTemplate()
		 * @generated
		 */
		EClass TEMPLATE = eINSTANCE.getTemplate();

		/**
		 * The meta object literal for the '<em><b>Overrides</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
		 * The meta object literal for the '<em><b>Post</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__POST = eINSTANCE.getTemplate_Post();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.parser.cst.impl.TemplateOverridesValueImpl
		 * <em>Template Overrides Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.TemplateOverridesValueImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTemplateOverridesValue()
		 * @generated
		 */
		EClass TEMPLATE_OVERRIDES_VALUE = eINSTANCE.getTemplateOverridesValue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE_OVERRIDES_VALUE__NAME = eINSTANCE.getTemplateOverridesValue_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.VariableImpl
		 * <em>Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.VariableImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__TYPE = eINSTANCE.getVariable_Type();

		/**
		 * The meta object literal for the '<em><b>Init Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VARIABLE__INIT_EXPRESSION = eINSTANCE.getVariable_InitExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.TemplateExpressionImpl
		 * <em>Template Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.TemplateExpressionImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTemplateExpression()
		 * @generated
		 */
		EClass TEMPLATE_EXPRESSION = eINSTANCE.getTemplateExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl
		 * <em>Model Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getModelExpression()
		 * @generated
		 */
		EClass MODEL_EXPRESSION = eINSTANCE.getModelExpression();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODEL_EXPRESSION__BODY = eINSTANCE.getModelExpression_Body();

		/**
		 * The meta object literal for the '<em><b>Before</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL_EXPRESSION__BEFORE = eINSTANCE.getModelExpression_Before();

		/**
		 * The meta object literal for the '<em><b>Each</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL_EXPRESSION__EACH = eINSTANCE.getModelExpression_Each();

		/**
		 * The meta object literal for the '<em><b>After</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL_EXPRESSION__AFTER = eINSTANCE.getModelExpression_After();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.TextExpressionImpl
		 * <em>Text Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.TextExpressionImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTextExpression()
		 * @generated
		 */
		EClass TEXT_EXPRESSION = eINSTANCE.getTextExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEXT_EXPRESSION__VALUE = eINSTANCE.getTextExpression_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.BlockImpl
		 * <em>Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.BlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getBlock()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.InitSectionImpl
		 * <em>Init Section</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.InitSectionImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getInitSection()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ProtectedAreaBlockImpl
		 * <em>Protected Area Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ProtectedAreaBlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getProtectedAreaBlock()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl
		 * <em>For Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.ForBlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getForBlock()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.IfBlockImpl
		 * <em>If Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.IfBlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getIfBlock()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.LetBlockImpl
		 * <em>Let Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.LetBlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getLetBlock()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.FileBlockImpl
		 * <em>File Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.FileBlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getFileBlock()
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
		 */
		EReference FILE_BLOCK__CHARSET = eINSTANCE.getFileBlock_Charset();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.TraceBlockImpl
		 * <em>Trace Block</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.TraceBlockImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getTraceBlock()
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
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.MacroImpl
		 * <em>Macro</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.MacroImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getMacro()
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
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MACRO__TYPE = eINSTANCE.getMacro_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.QueryImpl
		 * <em>Query</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.QueryImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getQuery()
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
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY__TYPE = eINSTANCE.getQuery_Type();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__EXPRESSION = eINSTANCE.getQuery_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.impl.DocumentationImpl
		 * <em>Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.impl.DocumentationImpl
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getDocumentation()
		 * @generated
		 * @since 3.1
		 */
		EClass DOCUMENTATION = eINSTANCE.getDocumentation();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.VisibilityKind
		 * <em>Visibility Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.VisibilityKind
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getVisibilityKind()
		 * @generated
		 */
		EEnum VISIBILITY_KIND = eINSTANCE.getVisibilityKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.parser.cst.OpenModeKind
		 * <em>Open Mode Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.parser.cst.OpenModeKind
		 * @see org.eclipse.acceleo.parser.cst.impl.CstPackageImpl#getOpenModeKind()
		 * @generated
		 */
		EEnum OPEN_MODE_KIND = eINSTANCE.getOpenModeKind();

	}

} // CstPackage
