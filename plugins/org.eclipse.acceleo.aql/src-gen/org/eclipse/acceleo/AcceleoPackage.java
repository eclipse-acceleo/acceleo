/**
 * Copyright (c) 2008, 2024 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.AcceleoFactory
 * @model kind="package"
 * @generated
 */
public interface AcceleoPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "acceleo"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/4.0"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "acceleo"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	AcceleoPackage eINSTANCE = org.eclipse.acceleo.impl.AcceleoPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.NamedElementImpl <em>Named Element</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.NamedElementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ModuleImpl <em>Module</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ModuleImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModule()
	 * @generated
	 */
	int MODULE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__DOCUMENTATION = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__DEPRECATED = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Metamodels</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__METAMODELS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__EXTENDS = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__IMPORTS = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Module Elements</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__MODULE_ELEMENTS = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Start Header Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__START_HEADER_POSITION = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>End Header Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__END_HEADER_POSITION = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Ast</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__AST = NAMED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Encoding</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__ENCODING = NAMED_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Module</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The number of operations of the '<em>Module</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.AcceleoASTNode <em>AST Node</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.AcceleoASTNode
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getAcceleoASTNode()
	 * @generated
	 */
	int ACCELEO_AST_NODE = 22;

	/**
	 * The number of structural features of the '<em>AST Node</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACCELEO_AST_NODE_FEATURE_COUNT = AstPackage.AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>AST Node</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACCELEO_AST_NODE_OPERATION_COUNT = AstPackage.AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.Error <em>Error</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.Error
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getError()
	 * @generated
	 */
	int ERROR = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorModuleImpl <em>Error Module</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorModuleImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModule()
	 * @generated
	 */
	int ERROR_MODULE = 1;

	/**
	 * The number of structural features of the '<em>Error</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Error</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__NAME = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__DOCUMENTATION = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__DEPRECATED = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Metamodels</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__METAMODELS = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__EXTENDS = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__IMPORTS = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Module Elements</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__MODULE_ELEMENTS = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Start Header Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__START_HEADER_POSITION = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>End Header Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__END_HEADER_POSITION = ERROR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Ast</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__AST = ERROR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Encoding</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__ENCODING = ERROR_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Missing EPackage</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__MISSING_EPACKAGE = ERROR_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 14;

	/**
	 * The number of structural features of the '<em>Error Module</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_FEATURE_COUNT = ERROR_FEATURE_COUNT + 15;

	/**
	 * The number of operations of the '<em>Error Module</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.MetamodelImpl <em>Metamodel</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.MetamodelImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getMetamodel()
	 * @generated
	 */
	int METAMODEL = 2;

	/**
	 * The feature id for the '<em><b>Referenced Package</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METAMODEL__REFERENCED_PACKAGE = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metamodel</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METAMODEL_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metamodel</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METAMODEL_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorMetamodelImpl <em>Error
	 * Metamodel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorMetamodelImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorMetamodel()
	 * @generated
	 */
	int ERROR_METAMODEL = 3;

	/**
	 * The feature id for the '<em><b>Referenced Package</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_METAMODEL__REFERENCED_PACKAGE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fragment</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_METAMODEL__FRAGMENT = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Missing End Quote</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_METAMODEL__MISSING_END_QUOTE = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Metamodel</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_METAMODEL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Metamodel</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_METAMODEL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ModuleReferenceImpl <em>Module
	 * Reference</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ModuleReferenceImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleReference()
	 * @generated
	 */
	int MODULE_REFERENCE = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ImportImpl <em>Import</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ImportImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getImport()
	 * @generated
	 */
	int IMPORT = 4;

	/**
	 * The feature id for the '<em><b>Module</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT__MODULE = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Import</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Import</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPORT_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorImportImpl <em>Error Import</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorImportImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorImport()
	 * @generated
	 */
	int ERROR_IMPORT = 5;

	/**
	 * The feature id for the '<em><b>Module</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IMPORT__MODULE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IMPORT__MISSING_END = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Error Import</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IMPORT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Error Import</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IMPORT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_REFERENCE__QUALIFIED_NAME = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Module Reference</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_REFERENCE_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Module Reference</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_REFERENCE_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorModuleReferenceImpl <em>Error Module
	 * Reference</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorModuleReferenceImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModuleReference()
	 * @generated
	 */
	int ERROR_MODULE_REFERENCE = 7;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_REFERENCE__QUALIFIED_NAME = ERROR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Module Reference</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_REFERENCE_FEATURE_COUNT = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Error Module Reference</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_REFERENCE_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ModuleElementImpl <em>Module Element</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ModuleElementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleElement()
	 * @generated
	 */
	int MODULE_ELEMENT = 8;

	/**
	 * The number of structural features of the '<em>Module Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Module Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.CommentImpl <em>Comment</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.CommentImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 11;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__MULTI_LINES = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__BODY = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Comment</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_OPERATION_COUNT = MODULE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.BlockCommentImpl <em>Block Comment</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.BlockCommentImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getBlockComment()
	 * @generated
	 */
	int BLOCK_COMMENT = 9;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_COMMENT__MULTI_LINES = COMMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_COMMENT__BODY = COMMENT__BODY;

	/**
	 * The number of structural features of the '<em>Block Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_COMMENT_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Block Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_COMMENT_OPERATION_COUNT = COMMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorCommentImpl <em>Error Comment</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorCommentImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorComment()
	 * @generated
	 */
	int ERROR_COMMENT = 12;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COMMENT__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COMMENT__BODY = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Missing Space</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COMMENT__MISSING_SPACE = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COMMENT__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Error Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COMMENT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Error Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COMMENT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorBlockCommentImpl <em>Error Block
	 * Comment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorBlockCommentImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorBlockComment()
	 * @generated
	 */
	int ERROR_BLOCK_COMMENT = 10;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BLOCK_COMMENT__MULTI_LINES = ERROR_COMMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BLOCK_COMMENT__BODY = ERROR_COMMENT__BODY;

	/**
	 * The feature id for the '<em><b>Missing Space</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BLOCK_COMMENT__MISSING_SPACE = ERROR_COMMENT__MISSING_SPACE;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BLOCK_COMMENT__MISSING_END_HEADER = ERROR_COMMENT__MISSING_END_HEADER;

	/**
	 * The number of structural features of the '<em>Error Block Comment</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BLOCK_COMMENT_FEATURE_COUNT = ERROR_COMMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Error Block Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BLOCK_COMMENT_OPERATION_COUNT = ERROR_COMMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.CommentBodyImpl <em>Comment Body</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.CommentBodyImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getCommentBody()
	 * @generated
	 */
	int COMMENT_BODY = 13;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_BODY__VALUE = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comment Body</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_BODY_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Comment Body</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_BODY_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.Documentation <em>Documentation</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.Documentation
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getDocumentation()
	 * @generated
	 */
	int DOCUMENTATION = 14;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION__MULTI_LINES = COMMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION__BODY = COMMENT__BODY;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION__DOCUMENTED_ELEMENT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Documentation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Documentation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTATION_OPERATION_COUNT = COMMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ModuleDocumentationImpl <em>Module
	 * Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ModuleDocumentationImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleDocumentation()
	 * @generated
	 */
	int MODULE_DOCUMENTATION = 15;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION__MULTI_LINES = DOCUMENTATION__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION__BODY = DOCUMENTATION__BODY;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT = DOCUMENTATION__DOCUMENTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION__AUTHOR = DOCUMENTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION__VERSION = DOCUMENTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Since</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION__SINCE = DOCUMENTATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Module Documentation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION_FEATURE_COUNT = DOCUMENTATION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Module Documentation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_DOCUMENTATION_OPERATION_COUNT = DOCUMENTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl <em>Error
	 * Module Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModuleDocumentation()
	 * @generated
	 */
	int ERROR_MODULE_DOCUMENTATION = 16;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__BODY = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__AUTHOR = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__VERSION = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Since</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__SINCE = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Error Module Documentation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION_FEATURE_COUNT = ERROR_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Error Module Documentation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_DOCUMENTATION_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ModuleElementDocumentationImpl <em>Module
	 * Element Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ModuleElementDocumentationImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleElementDocumentation()
	 * @generated
	 */
	int MODULE_ELEMENT_DOCUMENTATION = 17;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_DOCUMENTATION__MULTI_LINES = DOCUMENTATION__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_DOCUMENTATION__BODY = DOCUMENTATION__BODY;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT = DOCUMENTATION__DOCUMENTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Parameter Documentation</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION = DOCUMENTATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Module Element Documentation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_DOCUMENTATION_FEATURE_COUNT = DOCUMENTATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Module Element Documentation</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_DOCUMENTATION_OPERATION_COUNT = DOCUMENTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl
	 * <em>Error Module Element Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModuleElementDocumentation()
	 * @generated
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION = 18;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Documented Element</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameter Documentation</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Error Module Element Documentation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION_FEATURE_COUNT = ERROR_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Error Module Element Documentation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MODULE_ELEMENT_DOCUMENTATION_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ParameterDocumentationImpl <em>Parameter
	 * Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ParameterDocumentationImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getParameterDocumentation()
	 * @generated
	 */
	int PARAMETER_DOCUMENTATION = 19;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_DOCUMENTATION__MULTI_LINES = COMMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_DOCUMENTATION__BODY = COMMENT__BODY;

	/**
	 * The number of structural features of the '<em>Parameter Documentation</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_DOCUMENTATION_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Parameter Documentation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_DOCUMENTATION_OPERATION_COUNT = COMMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.DocumentedElement <em>Documented Element</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.DocumentedElement
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getDocumentedElement()
	 * @generated
	 */
	int DOCUMENTED_ELEMENT = 20;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED_ELEMENT__DOCUMENTATION = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED_ELEMENT__DEPRECATED = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Documented Element</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED_ELEMENT_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Documented Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOCUMENTED_ELEMENT_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.BlockImpl <em>Block</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.BlockImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 24;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__STATEMENTS = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Inlined</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK__INLINED = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Block</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Block</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BLOCK_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.TypedElementImpl <em>Typed Element</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.TypedElementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 25;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE_AQL = 1;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Typed Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.TemplateImpl <em>Template</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.TemplateImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getTemplate()
	 * @generated
	 */
	int TEMPLATE = 26;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__DOCUMENTATION = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__DEPRECATED = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__NAME = MODULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__PARAMETERS = MODULE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__GUARD = MODULE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__POST = MODULE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Main</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__MAIN = MODULE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__VISIBILITY = MODULE_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__BODY = MODULE_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Template</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The number of operations of the '<em>Template</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_OPERATION_COUNT = MODULE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorTemplateImpl <em>Error Template</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorTemplateImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorTemplate()
	 * @generated
	 */
	int ERROR_TEMPLATE = 27;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__DOCUMENTATION = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__DEPRECATED = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__NAME = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__PARAMETERS = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__GUARD = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__POST = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Main</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MAIN = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__VISIBILITY = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__BODY = ERROR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Missing Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_VISIBILITY = ERROR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Missing Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_NAME = ERROR_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Missing Parameters</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_PARAMETERS = ERROR_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Missing Guard Open Parenthesis</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Missing Guard Close Parenthesis</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Missing Post Close Parenthesis</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE__MISSING_END = ERROR_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>Error Template</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE_FEATURE_COUNT = ERROR_FEATURE_COUNT + 19;

	/**
	 * The number of operations of the '<em>Error Template</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TEMPLATE_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.QueryImpl <em>Query</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.QueryImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getQuery()
	 * @generated
	 */
	int QUERY = 28;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__DOCUMENTATION = MODULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__DEPRECATED = MODULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__NAME = MODULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__TYPE = MODULE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__TYPE_AQL = MODULE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__PARAMETERS = MODULE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__VISIBILITY = MODULE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__BODY = MODULE_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Query</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_FEATURE_COUNT = MODULE_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Query</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_OPERATION_COUNT = MODULE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorQueryImpl <em>Error Query</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorQueryImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorQuery()
	 * @generated
	 */
	int ERROR_QUERY = 29;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__DOCUMENTATION = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deprecated</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__DEPRECATED = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__NAME = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__TYPE = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__TYPE_AQL = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__PARAMETERS = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__VISIBILITY = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__BODY = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Missing Visibility</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_VISIBILITY = ERROR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Missing Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_NAME = ERROR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Missing Parameters</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_PARAMETERS = ERROR_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Missing Colon</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_COLON = ERROR_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Missing Type</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_TYPE = ERROR_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Missing Equal</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_EQUAL = ERROR_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY__MISSING_END = ERROR_FEATURE_COUNT + 16;

	/**
	 * The number of structural features of the '<em>Error Query</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY_FEATURE_COUNT = ERROR_FEATURE_COUNT + 17;

	/**
	 * The number of operations of the '<em>Error Query</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_QUERY_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ExpressionImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 30;

	/**
	 * The feature id for the '<em><b>Ast</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__AST = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__AQL = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Expression</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Expression</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorExpressionImpl <em>Error
	 * Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorExpressionImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorExpression()
	 * @generated
	 */
	int ERROR_EXPRESSION = 31;

	/**
	 * The feature id for the '<em><b>Ast</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION__AST = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION__AQL = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Error Expression</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_FEATURE_COUNT = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Error Expression</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.VariableImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 32;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE = TYPED_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE_AQL = TYPED_ELEMENT__TYPE_AQL;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Variable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorVariableImpl <em>Error Variable</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorVariableImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorVariable()
	 * @generated
	 */
	int ERROR_VARIABLE = 33;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE__TYPE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE__TYPE_AQL = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE__NAME = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Missing Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE__MISSING_NAME = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Missing Colon</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE__MISSING_COLON = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing Type</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE__MISSING_TYPE = ERROR_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Error Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_FEATURE_COUNT = ERROR_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Error Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.BindingImpl <em>Binding</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.BindingImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getBinding()
	 * @generated
	 */
	int BINDING = 34;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__TYPE = VARIABLE__TYPE;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__TYPE_AQL = VARIABLE__TYPE_AQL;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Init Expression</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__INIT_EXPRESSION = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Binding</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Binding</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorBindingImpl <em>Error Binding</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorBindingImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorBinding()
	 * @generated
	 */
	int ERROR_BINDING = 35;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__TYPE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__TYPE_AQL = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__NAME = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Init Expression</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__INIT_EXPRESSION = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Missing Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__MISSING_NAME = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing Colon</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__MISSING_COLON = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Missing Type</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__MISSING_TYPE = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Missing Affectation Symbole</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Missing Affectation Symbole Position</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION = ERROR_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Error Binding</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING_FEATURE_COUNT = ERROR_FEATURE_COUNT + 9;

	/**
	 * The number of operations of the '<em>Error Binding</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.Statement <em>Statement</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.Statement
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getStatement()
	 * @generated
	 */
	int STATEMENT = 36;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATEMENT__MULTI_LINES = ACCELEO_AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATEMENT_FEATURE_COUNT = ACCELEO_AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Statement</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATEMENT_OPERATION_COUNT = ACCELEO_AST_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.LeafStatementImpl <em>Leaf Statement</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.LeafStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getLeafStatement()
	 * @generated
	 */
	int LEAF_STATEMENT = 37;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAF_STATEMENT__MULTI_LINES = STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAF_STATEMENT__NEW_LINE_NEEDED = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Leaf Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAF_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Leaf Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAF_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ExpressionStatementImpl <em>Expression
	 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ExpressionStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getExpressionStatement()
	 * @generated
	 */
	int EXPRESSION_STATEMENT = 38;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT__MULTI_LINES = LEAF_STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT__NEW_LINE_NEEDED = LEAF_STATEMENT__NEW_LINE_NEEDED;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT__EXPRESSION = LEAF_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT_FEATURE_COUNT = LEAF_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Expression Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT_OPERATION_COUNT = LEAF_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorExpressionStatementImpl <em>Error
	 * Expression Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorExpressionStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorExpressionStatement()
	 * @generated
	 */
	int ERROR_EXPRESSION_STATEMENT = 39;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_STATEMENT__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_STATEMENT__EXPRESSION = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Error Expression Statement</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_STATEMENT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Error Expression Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_STATEMENT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ProtectedAreaImpl <em>Protected Area</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ProtectedAreaImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getProtectedArea()
	 * @generated
	 */
	int PROTECTED_AREA = 40;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA__MULTI_LINES = STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA__ID = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA__BODY = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Start Tag Prefix</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA__START_TAG_PREFIX = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>End Tag Prefix</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA__END_TAG_PREFIX = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Protected Area</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Protected Area</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PROTECTED_AREA_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl <em>Error Protected
	 * Area</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorProtectedAreaImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorProtectedArea()
	 * @generated
	 */
	int ERROR_PROTECTED_AREA = 41;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__ID = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__BODY = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start Tag Prefix</b></em>' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__START_TAG_PREFIX = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>End Tag Prefix</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__END_TAG_PREFIX = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Missing Start Tag Prefix Close Parenthesis</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Missing End Tag Prefix Close Parenthesis</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA__MISSING_END = ERROR_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Error Protected Area</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA_FEATURE_COUNT = ERROR_FEATURE_COUNT + 11;

	/**
	 * The number of operations of the '<em>Error Protected Area</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_PROTECTED_AREA_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ForStatementImpl <em>For Statement</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ForStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getForStatement()
	 * @generated
	 */
	int FOR_STATEMENT = 42;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__MULTI_LINES = STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__BINDING = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__SEPARATOR = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__BODY = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>For Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>For Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorForStatementImpl <em>Error For
	 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorForStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorForStatement()
	 * @generated
	 */
	int ERROR_FOR_STATEMENT = 43;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__BINDING = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Separator</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__SEPARATOR = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__BODY = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing Binding</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MISSING_BINDING = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Missing Separator Close Parenthesis</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MISSING_SEPARATOR_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT__MISSING_END = ERROR_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Error For Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 10;

	/**
	 * The number of operations of the '<em>Error For Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FOR_STATEMENT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.IfStatementImpl <em>If Statement</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.IfStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getIfStatement()
	 * @generated
	 */
	int IF_STATEMENT = 44;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__MULTI_LINES = STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__CONDITION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__THEN = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__ELSE = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>If Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl <em>Error If
	 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorIfStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorIfStatement()
	 * @generated
	 */
	int ERROR_IF_STATEMENT = 45;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__CONDITION = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Then</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__THEN = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__ELSE = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT__MISSING_END = ERROR_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Error If Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Error If Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_IF_STATEMENT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.LetStatementImpl <em>Let Statement</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.LetStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getLetStatement()
	 * @generated
	 */
	int LET_STATEMENT = 46;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_STATEMENT__MULTI_LINES = STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_STATEMENT__VARIABLES = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_STATEMENT__BODY = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Let Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Let Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl <em>Error Let
	 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorLetStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorLetStatement()
	 * @generated
	 */
	int ERROR_LET_STATEMENT = 47;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT__VARIABLES = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT__BODY = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Missing Bindings</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT__MISSING_BINDINGS = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT__MISSING_END = ERROR_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Error Let Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Error Let Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_LET_STATEMENT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.FileStatementImpl <em>File Statement</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.FileStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getFileStatement()
	 * @generated
	 */
	int FILE_STATEMENT = 48;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT__MULTI_LINES = STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT__MODE = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Url</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT__URL = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT__CHARSET = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT__BODY = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>File Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>File Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl <em>Error File
	 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorFileStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorFileStatement()
	 * @generated
	 */
	int ERROR_FILE_STATEMENT = 49;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MODE = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Url</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__URL = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__CHARSET = ERROR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__BODY = ERROR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Missing Open Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS = ERROR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Missing Comma</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MISSING_COMMA = ERROR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Missing Open Mode</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MISSING_OPEN_MODE = ERROR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Missing Close Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS = ERROR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Missing End Header</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MISSING_END_HEADER = ERROR_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Missing End</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT__MISSING_END = ERROR_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Error File Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT_FEATURE_COUNT = ERROR_FEATURE_COUNT + 11;

	/**
	 * The number of operations of the '<em>Error File Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FILE_STATEMENT_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.TextStatementImpl <em>Text Statement</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.TextStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getTextStatement()
	 * @generated
	 */
	int TEXT_STATEMENT = 50;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_STATEMENT__MULTI_LINES = LEAF_STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_STATEMENT__NEW_LINE_NEEDED = LEAF_STATEMENT__NEW_LINE_NEEDED;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_STATEMENT__VALUE = LEAF_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_STATEMENT_FEATURE_COUNT = LEAF_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Text Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_STATEMENT_OPERATION_COUNT = LEAF_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.NewLineStatementImpl <em>New Line
	 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.NewLineStatementImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getNewLineStatement()
	 * @generated
	 */
	int NEW_LINE_STATEMENT = 51;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEW_LINE_STATEMENT__MULTI_LINES = TEXT_STATEMENT__MULTI_LINES;

	/**
	 * The feature id for the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEW_LINE_STATEMENT__NEW_LINE_NEEDED = TEXT_STATEMENT__NEW_LINE_NEEDED;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEW_LINE_STATEMENT__VALUE = TEXT_STATEMENT__VALUE;

	/**
	 * The feature id for the '<em><b>Indentation Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEW_LINE_STATEMENT__INDENTATION_NEEDED = TEXT_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>New Line Statement</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEW_LINE_STATEMENT_FEATURE_COUNT = TEXT_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>New Line Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NEW_LINE_STATEMENT_OPERATION_COUNT = TEXT_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.impl.ErrorMarginImpl <em>Error Margin</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.impl.ErrorMarginImpl
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorMargin()
	 * @generated
	 */
	int ERROR_MARGIN = 52;

	/**
	 * The feature id for the '<em><b>Multi Lines</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MARGIN__MULTI_LINES = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>New Line Needed</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MARGIN__NEW_LINE_NEEDED = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MARGIN__VALUE = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Margin</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MARGIN_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Margin</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_MARGIN_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.VisibilityKind <em>Visibility Kind</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.VisibilityKind
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getVisibilityKind()
	 * @generated
	 */
	int VISIBILITY_KIND = 53;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.OpenModeKind <em>Open Mode Kind</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.OpenModeKind
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getOpenModeKind()
	 * @generated
	 */
	int OPEN_MODE_KIND = 54;

	/**
	 * The meta object id for the '<em>AST Result</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.acceleo.query.parser.AstResult
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getASTResult()
	 * @generated
	 */
	int AST_RESULT = 55;

	/**
	 * The meta object id for the '<em>Module Qualified Name</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleQualifiedName()
	 * @generated
	 */
	int MODULE_QUALIFIED_NAME = 56;

	/**
	 * The meta object id for the '<em>Ast Result</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.acceleo.aql.parser.AcceleoAstResult
	 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getAcceleoAstResult()
	 * @generated
	 */
	int ACCELEO_AST_RESULT = 57;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Module <em>Module</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module</em>'.
	 * @see org.eclipse.acceleo.Module
	 * @generated
	 */
	EClass getModule();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.acceleo.Module#getMetamodels
	 * <em>Metamodels</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Metamodels</em>'.
	 * @see org.eclipse.acceleo.Module#getMetamodels()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Metamodels();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Module#getExtends
	 * <em>Extends</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Extends</em>'.
	 * @see org.eclipse.acceleo.Module#getExtends()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Extends();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.Module#getImports <em>Imports</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Imports</em>'.
	 * @see org.eclipse.acceleo.Module#getImports()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_Imports();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.Module#getModuleElements <em>Module Elements</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Module Elements</em>'.
	 * @see org.eclipse.acceleo.Module#getModuleElements()
	 * @see #getModule()
	 * @generated
	 */
	EReference getModule_ModuleElements();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Module#getStartHeaderPosition
	 * <em>Start Header Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start Header Position</em>'.
	 * @see org.eclipse.acceleo.Module#getStartHeaderPosition()
	 * @see #getModule()
	 * @generated
	 */
	EAttribute getModule_StartHeaderPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Module#getEndHeaderPosition
	 * <em>End Header Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Header Position</em>'.
	 * @see org.eclipse.acceleo.Module#getEndHeaderPosition()
	 * @see #getModule()
	 * @generated
	 */
	EAttribute getModule_EndHeaderPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Module#getAst <em>Ast</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ast</em>'.
	 * @see org.eclipse.acceleo.Module#getAst()
	 * @see #getModule()
	 * @generated
	 */
	EAttribute getModule_Ast();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Module#getEncoding
	 * <em>Encoding</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Encoding</em>'.
	 * @see org.eclipse.acceleo.Module#getEncoding()
	 * @see #getModule()
	 * @generated
	 */
	EAttribute getModule_Encoding();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorModule <em>Error Module</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Module</em>'.
	 * @see org.eclipse.acceleo.ErrorModule
	 * @generated
	 */
	EClass getErrorModule();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorModule#getMissingOpenParenthesis <em>Missing Open Parenthesis</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorModule#getMissingOpenParenthesis()
	 * @see #getErrorModule()
	 * @generated
	 */
	EAttribute getErrorModule_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorModule#getMissingEPackage
	 * <em>Missing EPackage</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing EPackage</em>'.
	 * @see org.eclipse.acceleo.ErrorModule#getMissingEPackage()
	 * @see #getErrorModule()
	 * @generated
	 */
	EAttribute getErrorModule_MissingEPackage();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorModule#getMissingCloseParenthesis <em>Missing Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorModule#getMissingCloseParenthesis()
	 * @see #getErrorModule()
	 * @generated
	 */
	EAttribute getErrorModule_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorModule#getMissingEndHeader
	 * <em>Missing End Header</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorModule#getMissingEndHeader()
	 * @see #getErrorModule()
	 * @generated
	 */
	EAttribute getErrorModule_MissingEndHeader();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Metamodel <em>Metamodel</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Metamodel</em>'.
	 * @see org.eclipse.acceleo.Metamodel
	 * @generated
	 */
	EClass getMetamodel();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.Metamodel#getReferencedPackage
	 * <em>Referenced Package</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Referenced Package</em>'.
	 * @see org.eclipse.acceleo.Metamodel#getReferencedPackage()
	 * @see #getMetamodel()
	 * @generated
	 */
	EReference getMetamodel_ReferencedPackage();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorMetamodel <em>Error
	 * Metamodel</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Metamodel</em>'.
	 * @see org.eclipse.acceleo.ErrorMetamodel
	 * @generated
	 */
	EClass getErrorMetamodel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorMetamodel#getFragment
	 * <em>Fragment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Fragment</em>'.
	 * @see org.eclipse.acceleo.ErrorMetamodel#getFragment()
	 * @see #getErrorMetamodel()
	 * @generated
	 */
	EAttribute getErrorMetamodel_Fragment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorMetamodel#getMissingEndQuote
	 * <em>Missing End Quote</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Quote</em>'.
	 * @see org.eclipse.acceleo.ErrorMetamodel#getMissingEndQuote()
	 * @see #getErrorMetamodel()
	 * @generated
	 */
	EAttribute getErrorMetamodel_MissingEndQuote();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Import <em>Import</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Import</em>'.
	 * @see org.eclipse.acceleo.Import
	 * @generated
	 */
	EClass getImport();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Import#getModule
	 * <em>Module</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Module</em>'.
	 * @see org.eclipse.acceleo.Import#getModule()
	 * @see #getImport()
	 * @generated
	 */
	EReference getImport_Module();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorImport <em>Error Import</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Import</em>'.
	 * @see org.eclipse.acceleo.ErrorImport
	 * @generated
	 */
	EClass getErrorImport();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorImport#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorImport#getMissingEnd()
	 * @see #getErrorImport()
	 * @generated
	 */
	EAttribute getErrorImport_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ModuleReference <em>Module
	 * Reference</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Reference</em>'.
	 * @see org.eclipse.acceleo.ModuleReference
	 * @generated
	 */
	EClass getModuleReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ModuleReference#getQualifiedName
	 * <em>Qualified Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Qualified Name</em>'.
	 * @see org.eclipse.acceleo.ModuleReference#getQualifiedName()
	 * @see #getModuleReference()
	 * @generated
	 */
	EAttribute getModuleReference_QualifiedName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorModuleReference <em>Error Module
	 * Reference</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Module Reference</em>'.
	 * @see org.eclipse.acceleo.ErrorModuleReference
	 * @generated
	 */
	EClass getErrorModuleReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ModuleElement <em>Module Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.ModuleElement
	 * @generated
	 */
	EClass getModuleElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Comment <em>Comment</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.acceleo.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Comment#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.Comment#getBody()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.BlockComment <em>Block Comment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block Comment</em>'.
	 * @see org.eclipse.acceleo.BlockComment
	 * @generated
	 */
	EClass getBlockComment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorBlockComment <em>Error Block
	 * Comment</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Block Comment</em>'.
	 * @see org.eclipse.acceleo.ErrorBlockComment
	 * @generated
	 */
	EClass getErrorBlockComment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorComment <em>Error Comment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Comment</em>'.
	 * @see org.eclipse.acceleo.ErrorComment
	 * @generated
	 */
	EClass getErrorComment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorComment#getMissingSpace
	 * <em>Missing Space</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Space</em>'.
	 * @see org.eclipse.acceleo.ErrorComment#getMissingSpace()
	 * @see #getErrorComment()
	 * @generated
	 */
	EAttribute getErrorComment_MissingSpace();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorComment#getMissingEndHeader
	 * <em>Missing End Header</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorComment#getMissingEndHeader()
	 * @see #getErrorComment()
	 * @generated
	 */
	EAttribute getErrorComment_MissingEndHeader();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.CommentBody <em>Comment Body</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comment Body</em>'.
	 * @see org.eclipse.acceleo.CommentBody
	 * @generated
	 */
	EClass getCommentBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.CommentBody#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.CommentBody#getValue()
	 * @see #getCommentBody()
	 * @generated
	 */
	EAttribute getCommentBody_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Documentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Documentation</em>'.
	 * @see org.eclipse.acceleo.Documentation
	 * @generated
	 */
	EClass getDocumentation();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.acceleo.Documentation#getDocumentedElement <em>Documented Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Documented Element</em>'.
	 * @see org.eclipse.acceleo.Documentation#getDocumentedElement()
	 * @see #getDocumentation()
	 * @generated
	 */
	EReference getDocumentation_DocumentedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ModuleDocumentation <em>Module
	 * Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Documentation</em>'.
	 * @see org.eclipse.acceleo.ModuleDocumentation
	 * @generated
	 */
	EClass getModuleDocumentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ModuleDocumentation#getAuthor
	 * <em>Author</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.eclipse.acceleo.ModuleDocumentation#getAuthor()
	 * @see #getModuleDocumentation()
	 * @generated
	 */
	EAttribute getModuleDocumentation_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ModuleDocumentation#getVersion
	 * <em>Version</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.acceleo.ModuleDocumentation#getVersion()
	 * @see #getModuleDocumentation()
	 * @generated
	 */
	EAttribute getModuleDocumentation_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ModuleDocumentation#getSince
	 * <em>Since</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Since</em>'.
	 * @see org.eclipse.acceleo.ModuleDocumentation#getSince()
	 * @see #getModuleDocumentation()
	 * @generated
	 */
	EAttribute getModuleDocumentation_Since();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorModuleDocumentation <em>Error Module
	 * Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Module Documentation</em>'.
	 * @see org.eclipse.acceleo.ErrorModuleDocumentation
	 * @generated
	 */
	EClass getErrorModuleDocumentation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorModuleDocumentation#getMissingEndHeader <em>Missing End Header</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorModuleDocumentation#getMissingEndHeader()
	 * @see #getErrorModuleDocumentation()
	 * @generated
	 */
	EAttribute getErrorModuleDocumentation_MissingEndHeader();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ModuleElementDocumentation <em>Module
	 * Element Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module Element Documentation</em>'.
	 * @see org.eclipse.acceleo.ModuleElementDocumentation
	 * @generated
	 */
	EClass getModuleElementDocumentation();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.ModuleElementDocumentation#getParameterDocumentation <em>Parameter
	 * Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameter Documentation</em>'.
	 * @see org.eclipse.acceleo.ModuleElementDocumentation#getParameterDocumentation()
	 * @see #getModuleElementDocumentation()
	 * @generated
	 */
	EReference getModuleElementDocumentation_ParameterDocumentation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorModuleElementDocumentation <em>Error
	 * Module Element Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Module Element Documentation</em>'.
	 * @see org.eclipse.acceleo.ErrorModuleElementDocumentation
	 * @generated
	 */
	EClass getErrorModuleElementDocumentation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorModuleElementDocumentation#getMissingEndHeader <em>Missing End
	 * Header</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorModuleElementDocumentation#getMissingEndHeader()
	 * @see #getErrorModuleElementDocumentation()
	 * @generated
	 */
	EAttribute getErrorModuleElementDocumentation_MissingEndHeader();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ParameterDocumentation <em>Parameter
	 * Documentation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parameter Documentation</em>'.
	 * @see org.eclipse.acceleo.ParameterDocumentation
	 * @generated
	 */
	EClass getParameterDocumentation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.DocumentedElement <em>Documented
	 * Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Documented Element</em>'.
	 * @see org.eclipse.acceleo.DocumentedElement
	 * @generated
	 */
	EClass getDocumentedElement();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.acceleo.DocumentedElement#getDocumentation <em>Documentation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Documentation</em>'.
	 * @see org.eclipse.acceleo.DocumentedElement#getDocumentation()
	 * @see #getDocumentedElement()
	 * @generated
	 */
	EReference getDocumentedElement_Documentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.DocumentedElement#isDeprecated
	 * <em>Deprecated</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Deprecated</em>'.
	 * @see org.eclipse.acceleo.DocumentedElement#isDeprecated()
	 * @see #getDocumentedElement()
	 * @generated
	 */
	EAttribute getDocumentedElement_Deprecated();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.acceleo.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.NamedElement#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.AcceleoASTNode <em>AST Node</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>AST Node</em>'.
	 * @see org.eclipse.acceleo.AcceleoASTNode
	 * @generated
	 */
	EClass getAcceleoASTNode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Error <em>Error</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error</em>'.
	 * @see org.eclipse.acceleo.Error
	 * @generated
	 */
	EClass getError();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Block <em>Block</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Block</em>'.
	 * @see org.eclipse.acceleo.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.Block#getStatements <em>Statements</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.acceleo.Block#getStatements()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Statements();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Block#isInlined
	 * <em>Inlined</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Inlined</em>'.
	 * @see org.eclipse.acceleo.Block#isInlined()
	 * @see #getBlock()
	 * @generated
	 */
	EAttribute getBlock_Inlined();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.eclipse.acceleo.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.TypedElement#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EAttribute getTypedElement_Type();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.TypedElement#getTypeAql <em>Type Aql</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type Aql</em>'.
	 * @see org.eclipse.acceleo.TypedElement#getTypeAql()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_TypeAql();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Template <em>Template</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template</em>'.
	 * @see org.eclipse.acceleo.Template
	 * @generated
	 */
	EClass getTemplate();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.Template#getParameters <em>Parameters</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.acceleo.Template#getParameters()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Parameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Template#getGuard
	 * <em>Guard</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Guard</em>'.
	 * @see org.eclipse.acceleo.Template#getGuard()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Guard();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Template#getPost
	 * <em>Post</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Post</em>'.
	 * @see org.eclipse.acceleo.Template#getPost()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Post();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Template#isMain <em>Main</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Main</em>'.
	 * @see org.eclipse.acceleo.Template#isMain()
	 * @see #getTemplate()
	 * @generated
	 */
	EAttribute getTemplate_Main();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Template#getVisibility
	 * <em>Visibility</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.acceleo.Template#getVisibility()
	 * @see #getTemplate()
	 * @generated
	 */
	EAttribute getTemplate_Visibility();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Template#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.Template#getBody()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorTemplate <em>Error Template</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Template</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate
	 * @generated
	 */
	EClass getErrorTemplate();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingVisibility <em>Missing Visibility</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Visibility</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingVisibility()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingVisibility();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorTemplate#getMissingName
	 * <em>Missing Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Name</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingName()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingOpenParenthesis <em>Missing Open
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingOpenParenthesis()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingParameters <em>Missing Parameters</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Parameters</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingParameters()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingParameters();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingCloseParenthesis <em>Missing Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingCloseParenthesis()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingGuardOpenParenthesis <em>Missing Guard Open
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Guard Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingGuardOpenParenthesis()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingGuardOpenParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingGuardCloseParenthesis <em>Missing Guard Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Guard Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingGuardCloseParenthesis()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingGuardCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorTemplate#getMissingPostCloseParenthesis <em>Missing Post Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Post Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingPostCloseParenthesis()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingPostCloseParenthesis();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorTemplate#getMissingEndHeader
	 * <em>Missing End Header</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingEndHeader()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingEndHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorTemplate#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorTemplate#getMissingEnd()
	 * @see #getErrorTemplate()
	 * @generated
	 */
	EAttribute getErrorTemplate_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Query <em>Query</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.acceleo.Query
	 * @generated
	 */
	EClass getQuery();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.Query#getParameters <em>Parameters</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.acceleo.Query#getParameters()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Query#getVisibility
	 * <em>Visibility</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Visibility</em>'.
	 * @see org.eclipse.acceleo.Query#getVisibility()
	 * @see #getQuery()
	 * @generated
	 */
	EAttribute getQuery_Visibility();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Query#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.Query#getBody()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorQuery <em>Error Query</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Query</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery
	 * @generated
	 */
	EClass getErrorQuery();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingVisibility
	 * <em>Missing Visibility</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Visibility</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingVisibility()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingVisibility();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingName
	 * <em>Missing Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Name</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingName()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingName();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorQuery#getMissingOpenParenthesis <em>Missing Open Parenthesis</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingOpenParenthesis()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingParameters
	 * <em>Missing Parameters</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Parameters</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingParameters()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingParameters();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorQuery#getMissingCloseParenthesis <em>Missing Close Parenthesis</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingCloseParenthesis()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingColon
	 * <em>Missing Colon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Colon</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingColon()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingColon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingType
	 * <em>Missing Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Type</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingType()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingEqual
	 * <em>Missing Equal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Equal</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingEqual()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingEqual();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorQuery#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorQuery#getMissingEnd()
	 * @see #getErrorQuery()
	 * @generated
	 */
	EAttribute getErrorQuery_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Expression <em>Expression</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Expression#getAst <em>Ast</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ast</em>'.
	 * @see org.eclipse.acceleo.Expression#getAst()
	 * @see #getExpression()
	 * @generated
	 */
	EAttribute getExpression_Ast();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.Expression#getAql
	 * <em>Aql</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Aql</em>'.
	 * @see org.eclipse.acceleo.Expression#getAql()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_Aql();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorExpression <em>Error
	 * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Expression</em>'.
	 * @see org.eclipse.acceleo.ErrorExpression
	 * @generated
	 */
	EClass getErrorExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Variable <em>Variable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.acceleo.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorVariable <em>Error Variable</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Variable</em>'.
	 * @see org.eclipse.acceleo.ErrorVariable
	 * @generated
	 */
	EClass getErrorVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorVariable#getMissingName
	 * <em>Missing Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Name</em>'.
	 * @see org.eclipse.acceleo.ErrorVariable#getMissingName()
	 * @see #getErrorVariable()
	 * @generated
	 */
	EAttribute getErrorVariable_MissingName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorVariable#getMissingColon
	 * <em>Missing Colon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Colon</em>'.
	 * @see org.eclipse.acceleo.ErrorVariable#getMissingColon()
	 * @see #getErrorVariable()
	 * @generated
	 */
	EAttribute getErrorVariable_MissingColon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorVariable#getMissingType
	 * <em>Missing Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Type</em>'.
	 * @see org.eclipse.acceleo.ErrorVariable#getMissingType()
	 * @see #getErrorVariable()
	 * @generated
	 */
	EAttribute getErrorVariable_MissingType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Binding <em>Binding</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Binding</em>'.
	 * @see org.eclipse.acceleo.Binding
	 * @generated
	 */
	EClass getBinding();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.Binding#getInitExpression <em>Init Expression</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Init Expression</em>'.
	 * @see org.eclipse.acceleo.Binding#getInitExpression()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_InitExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorBinding <em>Error Binding</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Binding</em>'.
	 * @see org.eclipse.acceleo.ErrorBinding
	 * @generated
	 */
	EClass getErrorBinding();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorBinding#getMissingName
	 * <em>Missing Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Name</em>'.
	 * @see org.eclipse.acceleo.ErrorBinding#getMissingName()
	 * @see #getErrorBinding()
	 * @generated
	 */
	EAttribute getErrorBinding_MissingName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorBinding#getMissingColon
	 * <em>Missing Colon</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Colon</em>'.
	 * @see org.eclipse.acceleo.ErrorBinding#getMissingColon()
	 * @see #getErrorBinding()
	 * @generated
	 */
	EAttribute getErrorBinding_MissingColon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorBinding#getMissingType
	 * <em>Missing Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Type</em>'.
	 * @see org.eclipse.acceleo.ErrorBinding#getMissingType()
	 * @see #getErrorBinding()
	 * @generated
	 */
	EAttribute getErrorBinding_MissingType();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbole <em>Missing Affectation
	 * Symbole</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Affectation Symbole</em>'.
	 * @see org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbole()
	 * @see #getErrorBinding()
	 * @generated
	 */
	EAttribute getErrorBinding_MissingAffectationSymbole();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbolePosition <em>Missing Affectation
	 * Symbole Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Affectation Symbole Position</em>'.
	 * @see org.eclipse.acceleo.ErrorBinding#getMissingAffectationSymbolePosition()
	 * @see #getErrorBinding()
	 * @generated
	 */
	EAttribute getErrorBinding_MissingAffectationSymbolePosition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.Statement <em>Statement</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Statement</em>'.
	 * @see org.eclipse.acceleo.Statement
	 * @generated
	 */
	EClass getStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.Statement#isMultiLines <em>Multi
	 * Lines</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Multi Lines</em>'.
	 * @see org.eclipse.acceleo.Statement#isMultiLines()
	 * @see #getStatement()
	 * @generated
	 */
	EAttribute getStatement_MultiLines();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.LeafStatement <em>Leaf Statement</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Leaf Statement</em>'.
	 * @see org.eclipse.acceleo.LeafStatement
	 * @generated
	 */
	EClass getLeafStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.LeafStatement#isNewLineNeeded
	 * <em>New Line Needed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>New Line Needed</em>'.
	 * @see org.eclipse.acceleo.LeafStatement#isNewLineNeeded()
	 * @see #getLeafStatement()
	 * @generated
	 */
	EAttribute getLeafStatement_NewLineNeeded();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ExpressionStatement <em>Expression
	 * Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression Statement</em>'.
	 * @see org.eclipse.acceleo.ExpressionStatement
	 * @generated
	 */
	EClass getExpressionStatement();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.ExpressionStatement#getExpression <em>Expression</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.ExpressionStatement#getExpression()
	 * @see #getExpressionStatement()
	 * @generated
	 */
	EReference getExpressionStatement_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorExpressionStatement <em>Error
	 * Expression Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Expression Statement</em>'.
	 * @see org.eclipse.acceleo.ErrorExpressionStatement
	 * @generated
	 */
	EClass getErrorExpressionStatement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorExpressionStatement#getMissingEndHeader <em>Missing End Header</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorExpressionStatement#getMissingEndHeader()
	 * @see #getErrorExpressionStatement()
	 * @generated
	 */
	EAttribute getErrorExpressionStatement_MissingEndHeader();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ProtectedArea <em>Protected Area</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Protected Area</em>'.
	 * @see org.eclipse.acceleo.ProtectedArea
	 * @generated
	 */
	EClass getProtectedArea();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.ProtectedArea#getId
	 * <em>Id</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Id</em>'.
	 * @see org.eclipse.acceleo.ProtectedArea#getId()
	 * @see #getProtectedArea()
	 * @generated
	 */
	EReference getProtectedArea_Id();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.ProtectedArea#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.ProtectedArea#getBody()
	 * @see #getProtectedArea()
	 * @generated
	 */
	EReference getProtectedArea_Body();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.ProtectedArea#getStartTagPrefix <em>Start Tag Prefix</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Start Tag Prefix</em>'.
	 * @see org.eclipse.acceleo.ProtectedArea#getStartTagPrefix()
	 * @see #getProtectedArea()
	 * @generated
	 */
	EReference getProtectedArea_StartTagPrefix();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.ProtectedArea#getEndTagPrefix <em>End Tag Prefix</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>End Tag Prefix</em>'.
	 * @see org.eclipse.acceleo.ProtectedArea#getEndTagPrefix()
	 * @see #getProtectedArea()
	 * @generated
	 */
	EReference getProtectedArea_EndTagPrefix();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorProtectedArea <em>Error Protected
	 * Area</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Protected Area</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea
	 * @generated
	 */
	EClass getErrorProtectedArea();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingOpenParenthesis <em>Missing Open
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea#getMissingOpenParenthesis()
	 * @see #getErrorProtectedArea()
	 * @generated
	 */
	EAttribute getErrorProtectedArea_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingCloseParenthesis <em>Missing Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea#getMissingCloseParenthesis()
	 * @see #getErrorProtectedArea()
	 * @generated
	 */
	EAttribute getErrorProtectedArea_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingStartTagPrefixCloseParenthesis <em>Missing
	 * Start Tag Prefix Close Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Start Tag Prefix Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea#getMissingStartTagPrefixCloseParenthesis()
	 * @see #getErrorProtectedArea()
	 * @generated
	 */
	EAttribute getErrorProtectedArea_MissingStartTagPrefixCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEndTagPrefixCloseParenthesis <em>Missing End
	 * Tag Prefix Close Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Tag Prefix Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea#getMissingEndTagPrefixCloseParenthesis()
	 * @see #getErrorProtectedArea()
	 * @generated
	 */
	EAttribute getErrorProtectedArea_MissingEndTagPrefixCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEndHeader <em>Missing End Header</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea#getMissingEndHeader()
	 * @see #getErrorProtectedArea()
	 * @generated
	 */
	EAttribute getErrorProtectedArea_MissingEndHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorProtectedArea#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorProtectedArea#getMissingEnd()
	 * @see #getErrorProtectedArea()
	 * @generated
	 */
	EAttribute getErrorProtectedArea_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ForStatement <em>For Statement</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>For Statement</em>'.
	 * @see org.eclipse.acceleo.ForStatement
	 * @generated
	 */
	EClass getForStatement();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.ForStatement#getBinding <em>Binding</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Binding</em>'.
	 * @see org.eclipse.acceleo.ForStatement#getBinding()
	 * @see #getForStatement()
	 * @generated
	 */
	EReference getForStatement_Binding();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.ForStatement#getSeparator <em>Separator</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Separator</em>'.
	 * @see org.eclipse.acceleo.ForStatement#getSeparator()
	 * @see #getForStatement()
	 * @generated
	 */
	EReference getForStatement_Separator();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.ForStatement#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.ForStatement#getBody()
	 * @see #getForStatement()
	 * @generated
	 */
	EReference getForStatement_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorForStatement <em>Error For
	 * Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error For Statement</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement
	 * @generated
	 */
	EClass getErrorForStatement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorForStatement#getMissingOpenParenthesis <em>Missing Open
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement#getMissingOpenParenthesis()
	 * @see #getErrorForStatement()
	 * @generated
	 */
	EAttribute getErrorForStatement_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorForStatement#getMissingBinding <em>Missing Binding</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Binding</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement#getMissingBinding()
	 * @see #getErrorForStatement()
	 * @generated
	 */
	EAttribute getErrorForStatement_MissingBinding();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorForStatement#getMissingCloseParenthesis <em>Missing Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement#getMissingCloseParenthesis()
	 * @see #getErrorForStatement()
	 * @generated
	 */
	EAttribute getErrorForStatement_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorForStatement#getMissingSeparatorCloseParenthesis <em>Missing Separator
	 * Close Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Separator Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement#getMissingSeparatorCloseParenthesis()
	 * @see #getErrorForStatement()
	 * @generated
	 */
	EAttribute getErrorForStatement_MissingSeparatorCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorForStatement#getMissingEndHeader <em>Missing End Header</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement#getMissingEndHeader()
	 * @see #getErrorForStatement()
	 * @generated
	 */
	EAttribute getErrorForStatement_MissingEndHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorForStatement#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorForStatement#getMissingEnd()
	 * @see #getErrorForStatement()
	 * @generated
	 */
	EAttribute getErrorForStatement_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.IfStatement <em>If Statement</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>If Statement</em>'.
	 * @see org.eclipse.acceleo.IfStatement
	 * @generated
	 */
	EClass getIfStatement();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.IfStatement#getCondition <em>Condition</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.acceleo.IfStatement#getCondition()
	 * @see #getIfStatement()
	 * @generated
	 */
	EReference getIfStatement_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.IfStatement#getThen
	 * <em>Then</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Then</em>'.
	 * @see org.eclipse.acceleo.IfStatement#getThen()
	 * @see #getIfStatement()
	 * @generated
	 */
	EReference getIfStatement_Then();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.IfStatement#getElse
	 * <em>Else</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.acceleo.IfStatement#getElse()
	 * @see #getIfStatement()
	 * @generated
	 */
	EReference getIfStatement_Else();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorIfStatement <em>Error If
	 * Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error If Statement</em>'.
	 * @see org.eclipse.acceleo.ErrorIfStatement
	 * @generated
	 */
	EClass getErrorIfStatement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorIfStatement#getMissingOpenParenthesis <em>Missing Open
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorIfStatement#getMissingOpenParenthesis()
	 * @see #getErrorIfStatement()
	 * @generated
	 */
	EAttribute getErrorIfStatement_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorIfStatement#getMissingCloseParenthesis <em>Missing Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorIfStatement#getMissingCloseParenthesis()
	 * @see #getErrorIfStatement()
	 * @generated
	 */
	EAttribute getErrorIfStatement_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorIfStatement#getMissingEndHeader <em>Missing End Header</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorIfStatement#getMissingEndHeader()
	 * @see #getErrorIfStatement()
	 * @generated
	 */
	EAttribute getErrorIfStatement_MissingEndHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorIfStatement#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorIfStatement#getMissingEnd()
	 * @see #getErrorIfStatement()
	 * @generated
	 */
	EAttribute getErrorIfStatement_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.LetStatement <em>Let Statement</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Let Statement</em>'.
	 * @see org.eclipse.acceleo.LetStatement
	 * @generated
	 */
	EClass getLetStatement();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.LetStatement#getVariables <em>Variables</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see org.eclipse.acceleo.LetStatement#getVariables()
	 * @see #getLetStatement()
	 * @generated
	 */
	EReference getLetStatement_Variables();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.LetStatement#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.LetStatement#getBody()
	 * @see #getLetStatement()
	 * @generated
	 */
	EReference getLetStatement_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorLetStatement <em>Error Let
	 * Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Let Statement</em>'.
	 * @see org.eclipse.acceleo.ErrorLetStatement
	 * @generated
	 */
	EClass getErrorLetStatement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorLetStatement#getMissingBindings <em>Missing Bindings</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Bindings</em>'.
	 * @see org.eclipse.acceleo.ErrorLetStatement#getMissingBindings()
	 * @see #getErrorLetStatement()
	 * @generated
	 */
	EAttribute getErrorLetStatement_MissingBindings();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorLetStatement#getMissingEndHeader <em>Missing End Header</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorLetStatement#getMissingEndHeader()
	 * @see #getErrorLetStatement()
	 * @generated
	 */
	EAttribute getErrorLetStatement_MissingEndHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorLetStatement#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorLetStatement#getMissingEnd()
	 * @see #getErrorLetStatement()
	 * @generated
	 */
	EAttribute getErrorLetStatement_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.FileStatement <em>File Statement</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>File Statement</em>'.
	 * @see org.eclipse.acceleo.FileStatement
	 * @generated
	 */
	EClass getFileStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.FileStatement#getMode
	 * <em>Mode</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.acceleo.FileStatement#getMode()
	 * @see #getFileStatement()
	 * @generated
	 */
	EAttribute getFileStatement_Mode();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.FileStatement#getUrl
	 * <em>Url</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Url</em>'.
	 * @see org.eclipse.acceleo.FileStatement#getUrl()
	 * @see #getFileStatement()
	 * @generated
	 */
	EReference getFileStatement_Url();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.FileStatement#getCharset <em>Charset</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Charset</em>'.
	 * @see org.eclipse.acceleo.FileStatement#getCharset()
	 * @see #getFileStatement()
	 * @generated
	 */
	EReference getFileStatement_Charset();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.FileStatement#getBody
	 * <em>Body</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.FileStatement#getBody()
	 * @see #getFileStatement()
	 * @generated
	 */
	EReference getFileStatement_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorFileStatement <em>Error File
	 * Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error File Statement</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement
	 * @generated
	 */
	EClass getErrorFileStatement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingOpenParenthesis <em>Missing Open
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement#getMissingOpenParenthesis()
	 * @see #getErrorFileStatement()
	 * @generated
	 */
	EAttribute getErrorFileStatement_MissingOpenParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingComma <em>Missing Comma</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Comma</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement#getMissingComma()
	 * @see #getErrorFileStatement()
	 * @generated
	 */
	EAttribute getErrorFileStatement_MissingComma();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingOpenMode <em>Missing Open Mode</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Open Mode</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement#getMissingOpenMode()
	 * @see #getErrorFileStatement()
	 * @generated
	 */
	EAttribute getErrorFileStatement_MissingOpenMode();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingCloseParenthesis <em>Missing Close
	 * Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Close Parenthesis</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement#getMissingCloseParenthesis()
	 * @see #getErrorFileStatement()
	 * @generated
	 */
	EAttribute getErrorFileStatement_MissingCloseParenthesis();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingEndHeader <em>Missing End Header</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Header</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement#getMissingEndHeader()
	 * @see #getErrorFileStatement()
	 * @generated
	 */
	EAttribute getErrorFileStatement_MissingEndHeader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ErrorFileStatement#getMissingEnd
	 * <em>Missing End</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End</em>'.
	 * @see org.eclipse.acceleo.ErrorFileStatement#getMissingEnd()
	 * @see #getErrorFileStatement()
	 * @generated
	 */
	EAttribute getErrorFileStatement_MissingEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.TextStatement <em>Text Statement</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Text Statement</em>'.
	 * @see org.eclipse.acceleo.TextStatement
	 * @generated
	 */
	EClass getTextStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.TextStatement#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.TextStatement#getValue()
	 * @see #getTextStatement()
	 * @generated
	 */
	EAttribute getTextStatement_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.NewLineStatement <em>New Line
	 * Statement</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>New Line Statement</em>'.
	 * @see org.eclipse.acceleo.NewLineStatement
	 * @generated
	 */
	EClass getNewLineStatement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.NewLineStatement#isIndentationNeeded <em>Indentation Needed</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Indentation Needed</em>'.
	 * @see org.eclipse.acceleo.NewLineStatement#isIndentationNeeded()
	 * @see #getNewLineStatement()
	 * @generated
	 */
	EAttribute getNewLineStatement_IndentationNeeded();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ErrorMargin <em>Error Margin</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Margin</em>'.
	 * @see org.eclipse.acceleo.ErrorMargin
	 * @generated
	 */
	EClass getErrorMargin();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.VisibilityKind <em>Visibility Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Visibility Kind</em>'.
	 * @see org.eclipse.acceleo.VisibilityKind
	 * @generated
	 */
	EEnum getVisibilityKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.OpenModeKind <em>Open Mode Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Open Mode Kind</em>'.
	 * @see org.eclipse.acceleo.OpenModeKind
	 * @generated
	 */
	EEnum getOpenModeKind();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.acceleo.query.parser.AstResult <em>AST
	 * Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>AST Result</em>'.
	 * @see org.eclipse.acceleo.query.parser.AstResult
	 * @model instanceClass="org.eclipse.acceleo.query.parser.AstResult"
	 * @generated
	 */
	EDataType getASTResult();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Module Qualified Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Module Qualified Name</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getModuleQualifiedName();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.acceleo.aql.parser.AcceleoAstResult <em>Ast
	 * Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Ast Result</em>'.
	 * @see org.eclipse.acceleo.aql.parser.AcceleoAstResult
	 * @model instanceClass="org.eclipse.acceleo.aql.parser.AcceleoAstResult"
	 * @generated
	 */
	EDataType getAcceleoAstResult();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AcceleoFactory getAcceleoFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ModuleImpl <em>Module</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ModuleImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModule()
		 * @generated
		 */
		EClass MODULE = eINSTANCE.getModule();

		/**
		 * The meta object literal for the '<em><b>Metamodels</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__METAMODELS = eINSTANCE.getModule_Metamodels();

		/**
		 * The meta object literal for the '<em><b>Extends</b></em>' containment reference feature. <!--
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
		 * The meta object literal for the '<em><b>Module Elements</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE__MODULE_ELEMENTS = eINSTANCE.getModule_ModuleElements();

		/**
		 * The meta object literal for the '<em><b>Start Header Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE__START_HEADER_POSITION = eINSTANCE.getModule_StartHeaderPosition();

		/**
		 * The meta object literal for the '<em><b>End Header Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE__END_HEADER_POSITION = eINSTANCE.getModule_EndHeaderPosition();

		/**
		 * The meta object literal for the '<em><b>Ast</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE__AST = eINSTANCE.getModule_Ast();

		/**
		 * The meta object literal for the '<em><b>Encoding</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE__ENCODING = eINSTANCE.getModule_Encoding();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorModuleImpl <em>Error
		 * Module</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorModuleImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModule()
		 * @generated
		 */
		EClass ERROR_MODULE = eINSTANCE.getErrorModule();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_MODULE__MISSING_OPEN_PARENTHESIS = eINSTANCE.getErrorModule_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing EPackage</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_MODULE__MISSING_EPACKAGE = eINSTANCE.getErrorModule_MissingEPackage();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_MODULE__MISSING_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorModule_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_MODULE__MISSING_END_HEADER = eINSTANCE.getErrorModule_MissingEndHeader();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.MetamodelImpl <em>Metamodel</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.MetamodelImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getMetamodel()
		 * @generated
		 */
		EClass METAMODEL = eINSTANCE.getMetamodel();

		/**
		 * The meta object literal for the '<em><b>Referenced Package</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference METAMODEL__REFERENCED_PACKAGE = eINSTANCE.getMetamodel_ReferencedPackage();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorMetamodelImpl <em>Error
		 * Metamodel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorMetamodelImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorMetamodel()
		 * @generated
		 */
		EClass ERROR_METAMODEL = eINSTANCE.getErrorMetamodel();

		/**
		 * The meta object literal for the '<em><b>Fragment</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_METAMODEL__FRAGMENT = eINSTANCE.getErrorMetamodel_Fragment();

		/**
		 * The meta object literal for the '<em><b>Missing End Quote</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_METAMODEL__MISSING_END_QUOTE = eINSTANCE.getErrorMetamodel_MissingEndQuote();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ImportImpl <em>Import</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ImportImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getImport()
		 * @generated
		 */
		EClass IMPORT = eINSTANCE.getImport();

		/**
		 * The meta object literal for the '<em><b>Module</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IMPORT__MODULE = eINSTANCE.getImport_Module();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorImportImpl <em>Error
		 * Import</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorImportImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorImport()
		 * @generated
		 */
		EClass ERROR_IMPORT = eINSTANCE.getErrorImport();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_IMPORT__MISSING_END = eINSTANCE.getErrorImport_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ModuleReferenceImpl <em>Module
		 * Reference</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ModuleReferenceImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleReference()
		 * @generated
		 */
		EClass MODULE_REFERENCE = eINSTANCE.getModuleReference();

		/**
		 * The meta object literal for the '<em><b>Qualified Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_REFERENCE__QUALIFIED_NAME = eINSTANCE.getModuleReference_QualifiedName();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorModuleReferenceImpl <em>Error
		 * Module Reference</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorModuleReferenceImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModuleReference()
		 * @generated
		 */
		EClass ERROR_MODULE_REFERENCE = eINSTANCE.getErrorModuleReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ModuleElementImpl <em>Module
		 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ModuleElementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleElement()
		 * @generated
		 */
		EClass MODULE_ELEMENT = eINSTANCE.getModuleElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.CommentImpl <em>Comment</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.CommentImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMMENT__BODY = eINSTANCE.getComment_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.BlockCommentImpl <em>Block
		 * Comment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.BlockCommentImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getBlockComment()
		 * @generated
		 */
		EClass BLOCK_COMMENT = eINSTANCE.getBlockComment();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorBlockCommentImpl <em>Error
		 * Block Comment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorBlockCommentImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorBlockComment()
		 * @generated
		 */
		EClass ERROR_BLOCK_COMMENT = eINSTANCE.getErrorBlockComment();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorCommentImpl <em>Error
		 * Comment</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorCommentImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorComment()
		 * @generated
		 */
		EClass ERROR_COMMENT = eINSTANCE.getErrorComment();

		/**
		 * The meta object literal for the '<em><b>Missing Space</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_COMMENT__MISSING_SPACE = eINSTANCE.getErrorComment_MissingSpace();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_COMMENT__MISSING_END_HEADER = eINSTANCE.getErrorComment_MissingEndHeader();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.CommentBodyImpl <em>Comment
		 * Body</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.CommentBodyImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getCommentBody()
		 * @generated
		 */
		EClass COMMENT_BODY = eINSTANCE.getCommentBody();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COMMENT_BODY__VALUE = eINSTANCE.getCommentBody_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.Documentation <em>Documentation</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.Documentation
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getDocumentation()
		 * @generated
		 */
		EClass DOCUMENTATION = eINSTANCE.getDocumentation();

		/**
		 * The meta object literal for the '<em><b>Documented Element</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DOCUMENTATION__DOCUMENTED_ELEMENT = eINSTANCE.getDocumentation_DocumentedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ModuleDocumentationImpl <em>Module
		 * Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ModuleDocumentationImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleDocumentation()
		 * @generated
		 */
		EClass MODULE_DOCUMENTATION = eINSTANCE.getModuleDocumentation();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_DOCUMENTATION__AUTHOR = eINSTANCE.getModuleDocumentation_Author();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_DOCUMENTATION__VERSION = eINSTANCE.getModuleDocumentation_Version();

		/**
		 * The meta object literal for the '<em><b>Since</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE_DOCUMENTATION__SINCE = eINSTANCE.getModuleDocumentation_Since();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl
		 * <em>Error Module Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModuleDocumentation()
		 * @generated
		 */
		EClass ERROR_MODULE_DOCUMENTATION = eINSTANCE.getErrorModuleDocumentation();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER = eINSTANCE
				.getErrorModuleDocumentation_MissingEndHeader();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ModuleElementDocumentationImpl
		 * <em>Module Element Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ModuleElementDocumentationImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleElementDocumentation()
		 * @generated
		 */
		EClass MODULE_ELEMENT_DOCUMENTATION = eINSTANCE.getModuleElementDocumentation();

		/**
		 * The meta object literal for the '<em><b>Parameter Documentation</b></em>' containment reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION = eINSTANCE
				.getModuleElementDocumentation_ParameterDocumentation();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl <em>Error Module Element
		 * Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorModuleElementDocumentation()
		 * @generated
		 */
		EClass ERROR_MODULE_ELEMENT_DOCUMENTATION = eINSTANCE.getErrorModuleElementDocumentation();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER = eINSTANCE
				.getErrorModuleElementDocumentation_MissingEndHeader();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ParameterDocumentationImpl
		 * <em>Parameter Documentation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ParameterDocumentationImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getParameterDocumentation()
		 * @generated
		 */
		EClass PARAMETER_DOCUMENTATION = eINSTANCE.getParameterDocumentation();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.DocumentedElement <em>Documented
		 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.DocumentedElement
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getDocumentedElement()
		 * @generated
		 */
		EClass DOCUMENTED_ELEMENT = eINSTANCE.getDocumentedElement();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DOCUMENTED_ELEMENT__DOCUMENTATION = eINSTANCE.getDocumentedElement_Documentation();

		/**
		 * The meta object literal for the '<em><b>Deprecated</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DOCUMENTED_ELEMENT__DEPRECATED = eINSTANCE.getDocumentedElement_Deprecated();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.NamedElementImpl <em>Named
		 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.NamedElementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.AcceleoASTNode <em>AST Node</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.AcceleoASTNode
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getAcceleoASTNode()
		 * @generated
		 */
		EClass ACCELEO_AST_NODE = eINSTANCE.getAcceleoASTNode();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.Error <em>Error</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.Error
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getError()
		 * @generated
		 */
		EClass ERROR = eINSTANCE.getError();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.BlockImpl <em>Block</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.BlockImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getBlock()
		 * @generated
		 */
		EClass BLOCK = eINSTANCE.getBlock();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BLOCK__STATEMENTS = eINSTANCE.getBlock_Statements();

		/**
		 * The meta object literal for the '<em><b>Inlined</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BLOCK__INLINED = eINSTANCE.getBlock_Inlined();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.TypedElementImpl <em>Typed
		 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.TypedElementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

		/**
		 * The meta object literal for the '<em><b>Type Aql</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPED_ELEMENT__TYPE_AQL = eINSTANCE.getTypedElement_TypeAql();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.TemplateImpl <em>Template</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.TemplateImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getTemplate()
		 * @generated
		 */
		EClass TEMPLATE = eINSTANCE.getTemplate();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__PARAMETERS = eINSTANCE.getTemplate_Parameters();

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
		 * The meta object literal for the '<em><b>Main</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE__MAIN = eINSTANCE.getTemplate_Main();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE__VISIBILITY = eINSTANCE.getTemplate_Visibility();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__BODY = eINSTANCE.getTemplate_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorTemplateImpl <em>Error
		 * Template</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorTemplateImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorTemplate()
		 * @generated
		 */
		EClass ERROR_TEMPLATE = eINSTANCE.getErrorTemplate();

		/**
		 * The meta object literal for the '<em><b>Missing Visibility</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_VISIBILITY = eINSTANCE.getErrorTemplate_MissingVisibility();

		/**
		 * The meta object literal for the '<em><b>Missing Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_NAME = eINSTANCE.getErrorTemplate_MissingName();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS = eINSTANCE
				.getErrorTemplate_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Parameters</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_PARAMETERS = eINSTANCE.getErrorTemplate_MissingParameters();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorTemplate_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Guard Open Parenthesis</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS = eINSTANCE
				.getErrorTemplate_MissingGuardOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Guard Close Parenthesis</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorTemplate_MissingGuardCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Post Close Parenthesis</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorTemplate_MissingPostCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_END_HEADER = eINSTANCE.getErrorTemplate_MissingEndHeader();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TEMPLATE__MISSING_END = eINSTANCE.getErrorTemplate_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.QueryImpl <em>Query</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.QueryImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getQuery()
		 * @generated
		 */
		EClass QUERY = eINSTANCE.getQuery();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__PARAMETERS = eINSTANCE.getQuery_Parameters();

		/**
		 * The meta object literal for the '<em><b>Visibility</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY__VISIBILITY = eINSTANCE.getQuery_Visibility();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__BODY = eINSTANCE.getQuery_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorQueryImpl <em>Error
		 * Query</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorQueryImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorQuery()
		 * @generated
		 */
		EClass ERROR_QUERY = eINSTANCE.getErrorQuery();

		/**
		 * The meta object literal for the '<em><b>Missing Visibility</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_VISIBILITY = eINSTANCE.getErrorQuery_MissingVisibility();

		/**
		 * The meta object literal for the '<em><b>Missing Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_NAME = eINSTANCE.getErrorQuery_MissingName();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_OPEN_PARENTHESIS = eINSTANCE.getErrorQuery_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Parameters</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_PARAMETERS = eINSTANCE.getErrorQuery_MissingParameters();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_CLOSE_PARENTHESIS = eINSTANCE.getErrorQuery_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Colon</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_COLON = eINSTANCE.getErrorQuery_MissingColon();

		/**
		 * The meta object literal for the '<em><b>Missing Type</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_TYPE = eINSTANCE.getErrorQuery_MissingType();

		/**
		 * The meta object literal for the '<em><b>Missing Equal</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_EQUAL = eINSTANCE.getErrorQuery_MissingEqual();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_QUERY__MISSING_END = eINSTANCE.getErrorQuery_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ExpressionImpl
		 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ExpressionImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '<em><b>Ast</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute EXPRESSION__AST = eINSTANCE.getExpression_Ast();

		/**
		 * The meta object literal for the '<em><b>Aql</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPRESSION__AQL = eINSTANCE.getExpression_Aql();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorExpressionImpl <em>Error
		 * Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorExpressionImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorExpression()
		 * @generated
		 */
		EClass ERROR_EXPRESSION = eINSTANCE.getErrorExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.VariableImpl <em>Variable</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.VariableImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorVariableImpl <em>Error
		 * Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorVariableImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorVariable()
		 * @generated
		 */
		EClass ERROR_VARIABLE = eINSTANCE.getErrorVariable();

		/**
		 * The meta object literal for the '<em><b>Missing Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_VARIABLE__MISSING_NAME = eINSTANCE.getErrorVariable_MissingName();

		/**
		 * The meta object literal for the '<em><b>Missing Colon</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_VARIABLE__MISSING_COLON = eINSTANCE.getErrorVariable_MissingColon();

		/**
		 * The meta object literal for the '<em><b>Missing Type</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_VARIABLE__MISSING_TYPE = eINSTANCE.getErrorVariable_MissingType();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.BindingImpl <em>Binding</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.BindingImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getBinding()
		 * @generated
		 */
		EClass BINDING = eINSTANCE.getBinding();

		/**
		 * The meta object literal for the '<em><b>Init Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINDING__INIT_EXPRESSION = eINSTANCE.getBinding_InitExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorBindingImpl <em>Error
		 * Binding</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorBindingImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorBinding()
		 * @generated
		 */
		EClass ERROR_BINDING = eINSTANCE.getErrorBinding();

		/**
		 * The meta object literal for the '<em><b>Missing Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_BINDING__MISSING_NAME = eINSTANCE.getErrorBinding_MissingName();

		/**
		 * The meta object literal for the '<em><b>Missing Colon</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_BINDING__MISSING_COLON = eINSTANCE.getErrorBinding_MissingColon();

		/**
		 * The meta object literal for the '<em><b>Missing Type</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_BINDING__MISSING_TYPE = eINSTANCE.getErrorBinding_MissingType();

		/**
		 * The meta object literal for the '<em><b>Missing Affectation Symbole</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE = eINSTANCE
				.getErrorBinding_MissingAffectationSymbole();

		/**
		 * The meta object literal for the '<em><b>Missing Affectation Symbole Position</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION = eINSTANCE
				.getErrorBinding_MissingAffectationSymbolePosition();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.Statement <em>Statement</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.Statement
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getStatement()
		 * @generated
		 */
		EClass STATEMENT = eINSTANCE.getStatement();

		/**
		 * The meta object literal for the '<em><b>Multi Lines</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STATEMENT__MULTI_LINES = eINSTANCE.getStatement_MultiLines();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.LeafStatementImpl <em>Leaf
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.LeafStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getLeafStatement()
		 * @generated
		 */
		EClass LEAF_STATEMENT = eINSTANCE.getLeafStatement();

		/**
		 * The meta object literal for the '<em><b>New Line Needed</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LEAF_STATEMENT__NEW_LINE_NEEDED = eINSTANCE.getLeafStatement_NewLineNeeded();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ExpressionStatementImpl
		 * <em>Expression Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ExpressionStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getExpressionStatement()
		 * @generated
		 */
		EClass EXPRESSION_STATEMENT = eINSTANCE.getExpressionStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EXPRESSION_STATEMENT__EXPRESSION = eINSTANCE.getExpressionStatement_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorExpressionStatementImpl
		 * <em>Error Expression Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorExpressionStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorExpressionStatement()
		 * @generated
		 */
		EClass ERROR_EXPRESSION_STATEMENT = eINSTANCE.getErrorExpressionStatement();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER = eINSTANCE
				.getErrorExpressionStatement_MissingEndHeader();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ProtectedAreaImpl <em>Protected
		 * Area</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ProtectedAreaImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getProtectedArea()
		 * @generated
		 */
		EClass PROTECTED_AREA = eINSTANCE.getProtectedArea();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROTECTED_AREA__ID = eINSTANCE.getProtectedArea_Id();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROTECTED_AREA__BODY = eINSTANCE.getProtectedArea_Body();

		/**
		 * The meta object literal for the '<em><b>Start Tag Prefix</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROTECTED_AREA__START_TAG_PREFIX = eINSTANCE.getProtectedArea_StartTagPrefix();

		/**
		 * The meta object literal for the '<em><b>End Tag Prefix</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PROTECTED_AREA__END_TAG_PREFIX = eINSTANCE.getProtectedArea_EndTagPrefix();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl <em>Error
		 * Protected Area</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorProtectedAreaImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorProtectedArea()
		 * @generated
		 */
		EClass ERROR_PROTECTED_AREA = eINSTANCE.getErrorProtectedArea();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS = eINSTANCE
				.getErrorProtectedArea_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorProtectedArea_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Start Tag Prefix Close Parenthesis</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorProtectedArea_MissingStartTagPrefixCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Tag Prefix Close Parenthesis</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorProtectedArea_MissingEndTagPrefixCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_PROTECTED_AREA__MISSING_END_HEADER = eINSTANCE
				.getErrorProtectedArea_MissingEndHeader();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_PROTECTED_AREA__MISSING_END = eINSTANCE.getErrorProtectedArea_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ForStatementImpl <em>For
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ForStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getForStatement()
		 * @generated
		 */
		EClass FOR_STATEMENT = eINSTANCE.getForStatement();

		/**
		 * The meta object literal for the '<em><b>Binding</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_STATEMENT__BINDING = eINSTANCE.getForStatement_Binding();

		/**
		 * The meta object literal for the '<em><b>Separator</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_STATEMENT__SEPARATOR = eINSTANCE.getForStatement_Separator();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR_STATEMENT__BODY = eINSTANCE.getForStatement_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorForStatementImpl <em>Error
		 * For Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorForStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorForStatement()
		 * @generated
		 */
		EClass ERROR_FOR_STATEMENT = eINSTANCE.getErrorForStatement();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FOR_STATEMENT__MISSING_OPEN_PARENTHESIS = eINSTANCE
				.getErrorForStatement_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Binding</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FOR_STATEMENT__MISSING_BINDING = eINSTANCE.getErrorForStatement_MissingBinding();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FOR_STATEMENT__MISSING_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorForStatement_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Separator Close Parenthesis</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FOR_STATEMENT__MISSING_SEPARATOR_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorForStatement_MissingSeparatorCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FOR_STATEMENT__MISSING_END_HEADER = eINSTANCE
				.getErrorForStatement_MissingEndHeader();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FOR_STATEMENT__MISSING_END = eINSTANCE.getErrorForStatement_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.IfStatementImpl <em>If
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.IfStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getIfStatement()
		 * @generated
		 */
		EClass IF_STATEMENT = eINSTANCE.getIfStatement();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_STATEMENT__CONDITION = eINSTANCE.getIfStatement_Condition();

		/**
		 * The meta object literal for the '<em><b>Then</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_STATEMENT__THEN = eINSTANCE.getIfStatement_Then();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF_STATEMENT__ELSE = eINSTANCE.getIfStatement_Else();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl <em>Error If
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorIfStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorIfStatement()
		 * @generated
		 */
		EClass ERROR_IF_STATEMENT = eINSTANCE.getErrorIfStatement();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS = eINSTANCE
				.getErrorIfStatement_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorIfStatement_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_IF_STATEMENT__MISSING_END_HEADER = eINSTANCE.getErrorIfStatement_MissingEndHeader();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_IF_STATEMENT__MISSING_END = eINSTANCE.getErrorIfStatement_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.LetStatementImpl <em>Let
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.LetStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getLetStatement()
		 * @generated
		 */
		EClass LET_STATEMENT = eINSTANCE.getLetStatement();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET_STATEMENT__VARIABLES = eINSTANCE.getLetStatement_Variables();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET_STATEMENT__BODY = eINSTANCE.getLetStatement_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl <em>Error
		 * Let Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorLetStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorLetStatement()
		 * @generated
		 */
		EClass ERROR_LET_STATEMENT = eINSTANCE.getErrorLetStatement();

		/**
		 * The meta object literal for the '<em><b>Missing Bindings</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_LET_STATEMENT__MISSING_BINDINGS = eINSTANCE.getErrorLetStatement_MissingBindings();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_LET_STATEMENT__MISSING_END_HEADER = eINSTANCE
				.getErrorLetStatement_MissingEndHeader();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_LET_STATEMENT__MISSING_END = eINSTANCE.getErrorLetStatement_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.FileStatementImpl <em>File
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.FileStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getFileStatement()
		 * @generated
		 */
		EClass FILE_STATEMENT = eINSTANCE.getFileStatement();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FILE_STATEMENT__MODE = eINSTANCE.getFileStatement_Mode();

		/**
		 * The meta object literal for the '<em><b>Url</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FILE_STATEMENT__URL = eINSTANCE.getFileStatement_Url();

		/**
		 * The meta object literal for the '<em><b>Charset</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FILE_STATEMENT__CHARSET = eINSTANCE.getFileStatement_Charset();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FILE_STATEMENT__BODY = eINSTANCE.getFileStatement_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl <em>Error
		 * File Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorFileStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorFileStatement()
		 * @generated
		 */
		EClass ERROR_FILE_STATEMENT = eINSTANCE.getErrorFileStatement();

		/**
		 * The meta object literal for the '<em><b>Missing Open Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS = eINSTANCE
				.getErrorFileStatement_MissingOpenParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing Comma</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FILE_STATEMENT__MISSING_COMMA = eINSTANCE.getErrorFileStatement_MissingComma();

		/**
		 * The meta object literal for the '<em><b>Missing Open Mode</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FILE_STATEMENT__MISSING_OPEN_MODE = eINSTANCE
				.getErrorFileStatement_MissingOpenMode();

		/**
		 * The meta object literal for the '<em><b>Missing Close Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS = eINSTANCE
				.getErrorFileStatement_MissingCloseParenthesis();

		/**
		 * The meta object literal for the '<em><b>Missing End Header</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FILE_STATEMENT__MISSING_END_HEADER = eINSTANCE
				.getErrorFileStatement_MissingEndHeader();

		/**
		 * The meta object literal for the '<em><b>Missing End</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_FILE_STATEMENT__MISSING_END = eINSTANCE.getErrorFileStatement_MissingEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.TextStatementImpl <em>Text
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.TextStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getTextStatement()
		 * @generated
		 */
		EClass TEXT_STATEMENT = eINSTANCE.getTextStatement();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEXT_STATEMENT__VALUE = eINSTANCE.getTextStatement_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.NewLineStatementImpl <em>New Line
		 * Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.NewLineStatementImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getNewLineStatement()
		 * @generated
		 */
		EClass NEW_LINE_STATEMENT = eINSTANCE.getNewLineStatement();

		/**
		 * The meta object literal for the '<em><b>Indentation Needed</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NEW_LINE_STATEMENT__INDENTATION_NEEDED = eINSTANCE.getNewLineStatement_IndentationNeeded();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.impl.ErrorMarginImpl <em>Error
		 * Margin</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.impl.ErrorMarginImpl
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getErrorMargin()
		 * @generated
		 */
		EClass ERROR_MARGIN = eINSTANCE.getErrorMargin();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.VisibilityKind <em>Visibility
		 * Kind</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.VisibilityKind
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getVisibilityKind()
		 * @generated
		 */
		EEnum VISIBILITY_KIND = eINSTANCE.getVisibilityKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.OpenModeKind <em>Open Mode Kind</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.OpenModeKind
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getOpenModeKind()
		 * @generated
		 */
		EEnum OPEN_MODE_KIND = eINSTANCE.getOpenModeKind();

		/**
		 * The meta object literal for the '<em>AST Result</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.parser.AstResult
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getASTResult()
		 * @generated
		 */
		EDataType AST_RESULT = eINSTANCE.getASTResult();

		/**
		 * The meta object literal for the '<em>Module Qualified Name</em>' data type. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getModuleQualifiedName()
		 * @generated
		 */
		EDataType MODULE_QUALIFIED_NAME = eINSTANCE.getModuleQualifiedName();

		/**
		 * The meta object literal for the '<em>Ast Result</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.aql.parser.AcceleoAstResult
		 * @see org.eclipse.acceleo.impl.AcceleoPackageImpl#getAcceleoAstResult()
		 * @generated
		 */
		EDataType ACCELEO_AST_RESULT = eINSTANCE.getAcceleoAstResult();

	}

} // AcceleoPackage
