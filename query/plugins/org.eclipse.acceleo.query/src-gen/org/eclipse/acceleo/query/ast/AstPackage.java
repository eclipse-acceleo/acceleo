/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
 * @see org.eclipse.acceleo.query.ast.AstFactory
 * @model kind="package"
 * @generated
 */
public interface AstPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "ast";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/query/1.0";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "ast";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	AstPackage eINSTANCE = org.eclipse.acceleo.query.ast.impl.AstPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ExpressionImpl
	 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ExpressionImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>Expression</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Expression</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.VarRefImpl <em>Var Ref</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.VarRefImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getVarRef()
	 * @generated
	 */
	int VAR_REF = 1;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_REF__VARIABLE_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Var Ref</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_REF_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Var Ref</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_REF_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.FeatureAccessImpl
	 * <em>Feature Access</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.FeatureAccessImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getFeatureAccess()
	 * @generated
	 */
	int FEATURE_ACCESS = 2;

	/**
	 * The feature id for the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_ACCESS__FEATURE_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_ACCESS__TARGET = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Feature Access</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_ACCESS_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Feature Access</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_ACCESS_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.CallImpl <em>Call</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.CallImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCall()
	 * @generated
	 */
	int CALL = 3;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__SERVICE_NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__TYPE = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__ARGUMENTS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Call</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Call</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.LiteralImpl <em>Literal</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.LiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 4;

	/**
	 * The number of structural features of the '<em>Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Literal</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.IntegerLiteralImpl
	 * <em>Integer Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.IntegerLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getIntegerLiteral()
	 * @generated
	 */
	int INTEGER_LITERAL = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.RealLiteralImpl
	 * <em>Real Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.RealLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getRealLiteral()
	 * @generated
	 */
	int REAL_LITERAL = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REAL_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Real Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REAL_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Real Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REAL_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.StringLiteralImpl
	 * <em>String Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.StringLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getStringLiteral()
	 * @generated
	 */
	int STRING_LITERAL = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.BooleanLiteralImpl
	 * <em>Boolean Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.BooleanLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getBooleanLiteral()
	 * @generated
	 */
	int BOOLEAN_LITERAL = 8;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Boolean Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.EnumLiteralImpl
	 * <em>Enum Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.EnumLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getEnumLiteral()
	 * @generated
	 */
	int ENUM_LITERAL = 9;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUM_LITERAL__LITERAL = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enum Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUM_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Enum Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUM_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.TypeLiteralImpl
	 * <em>Type Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.TypeLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getTypeLiteral()
	 * @generated
	 */
	int TYPE_LITERAL = 10;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.CollectionTypeLiteralImpl
	 * <em>Collection Type Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.CollectionTypeLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCollectionTypeLiteral()
	 * @generated
	 */
	int COLLECTION_TYPE_LITERAL = 11;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TYPE_LITERAL__VALUE = TYPE_LITERAL__VALUE;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TYPE_LITERAL__ELEMENT_TYPE = TYPE_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Collection Type Literal</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TYPE_LITERAL_FEATURE_COUNT = TYPE_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Collection Type Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLLECTION_TYPE_LITERAL_OPERATION_COUNT = TYPE_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.LambdaImpl <em>Lambda</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.LambdaImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getLambda()
	 * @generated
	 */
	int LAMBDA = 12;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA__PARAMETERS = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA__EXPRESSION = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Evaluator</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA__EVALUATOR = LITERAL_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Lambda</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Eval</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA___EVAL__OBJECT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Lambda</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.NullLiteralImpl
	 * <em>Null Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.NullLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getNullLiteral()
	 * @generated
	 */
	int NULL_LITERAL = 13;

	/**
	 * The number of structural features of the '<em>Null Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Null Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.SetInExtensionLiteralImpl
	 * <em>Set In Extension Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.SetInExtensionLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getSetInExtensionLiteral()
	 * @generated
	 */
	int SET_IN_EXTENSION_LITERAL = 14;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_IN_EXTENSION_LITERAL__VALUES = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Set In Extension Literal</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_IN_EXTENSION_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Set In Extension Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_IN_EXTENSION_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.SequenceInExtensionLiteralImpl
	 * <em>Sequence In Extension Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.SequenceInExtensionLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getSequenceInExtensionLiteral()
	 * @generated
	 */
	int SEQUENCE_IN_EXTENSION_LITERAL = 15;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_IN_EXTENSION_LITERAL__VALUES = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sequence In Extension Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_IN_EXTENSION_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Sequence In Extension Literal</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEQUENCE_IN_EXTENSION_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.VariableDeclarationImpl
	 * <em>Variable Declaration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.VariableDeclarationImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getVariableDeclaration()
	 * @generated
	 */
	int VARIABLE_DECLARATION = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__EXPRESSION = 2;

	/**
	 * The number of structural features of the '<em>Variable Declaration</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Variable Declaration</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.Error <em>Error</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.Error
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getError()
	 * @generated
	 */
	int ERROR = 17;

	/**
	 * The number of structural features of the '<em>Error</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Error</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorExpressionImpl
	 * <em>Error Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorExpressionImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorExpression()
	 * @generated
	 */
	int ERROR_EXPRESSION = 18;

	/**
	 * The number of structural features of the '<em>Error Expression</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_FEATURE_COUNT = ERROR_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Error Expression</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_EXPRESSION_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorTypeLiteralImpl
	 * <em>Error Type Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorTypeLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorTypeLiteral()
	 * @generated
	 */
	int ERROR_TYPE_LITERAL = 19;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL__VALUE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL__SEGMENTS = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Error Type Literal</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Error Type Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorFeatureAccessOrCallImpl
	 * <em>Error Feature Access Or Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorFeatureAccessOrCallImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorFeatureAccessOrCall()
	 * @generated
	 */
	int ERROR_FEATURE_ACCESS_OR_CALL = 20;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_ACCESS_OR_CALL__TARGET = ERROR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Feature Access Or Call</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_ACCESS_OR_CALL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Error Feature Access Or Call</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_FEATURE_ACCESS_OR_CALL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorCollectionCallImpl
	 * <em>Error Collection Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorCollectionCallImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorCollectionCall()
	 * @generated
	 */
	int ERROR_COLLECTION_CALL = 21;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COLLECTION_CALL__TARGET = ERROR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Collection Call</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COLLECTION_CALL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Error Collection Call</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_COLLECTION_CALL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl
	 * <em>Error Variable Declaration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorVariableDeclaration()
	 * @generated
	 */
	int ERROR_VARIABLE_DECLARATION = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_DECLARATION__NAME = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_DECLARATION__TYPE = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_DECLARATION__EXPRESSION = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Variable Declaration</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_DECLARATION_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Variable Declaration</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_VARIABLE_DECLARATION_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.CallType <em>Call Type</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.CallType
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCallType()
	 * @generated
	 */
	int CALL_TYPE = 23;

	/**
	 * The meta object id for the '<em>Object Type</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see java.lang.Object
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getObjectType()
	 * @generated
	 */
	int OBJECT_TYPE = 24;

	/**
	 * The meta object id for the '<em>Evaluator</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.acceleo.query.parser.AstEvaluator
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getAstEvaluator()
	 * @generated
	 */
	int AST_EVALUATOR = 25;

	/**
	 * The meta object id for the '<em>Object Array</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getObjectArray()
	 * @generated
	 */
	int OBJECT_ARRAY = 26;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Expression <em>Expression</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.query.ast.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.VarRef <em>Var Ref</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Var Ref</em>'.
	 * @see org.eclipse.acceleo.query.ast.VarRef
	 * @generated
	 */
	EClass getVarRef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.VarRef#getVariableName
	 * <em>Variable Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Variable Name</em>'.
	 * @see org.eclipse.acceleo.query.ast.VarRef#getVariableName()
	 * @see #getVarRef()
	 * @generated
	 */
	EAttribute getVarRef_VariableName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.FeatureAccess
	 * <em>Feature Access</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Feature Access</em>'.
	 * @see org.eclipse.acceleo.query.ast.FeatureAccess
	 * @generated
	 */
	EClass getFeatureAccess();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.ast.FeatureAccess#getFeatureName <em>Feature Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Feature Name</em>'.
	 * @see org.eclipse.acceleo.query.ast.FeatureAccess#getFeatureName()
	 * @see #getFeatureAccess()
	 * @generated
	 */
	EAttribute getFeatureAccess_FeatureName();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.FeatureAccess#getTarget <em>Target</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.acceleo.query.ast.FeatureAccess#getTarget()
	 * @see #getFeatureAccess()
	 * @generated
	 */
	EReference getFeatureAccess_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Call <em>Call</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Call</em>'.
	 * @see org.eclipse.acceleo.query.ast.Call
	 * @generated
	 */
	EClass getCall();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.Call#getServiceName
	 * <em>Service Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Service Name</em>'.
	 * @see org.eclipse.acceleo.query.ast.Call#getServiceName()
	 * @see #getCall()
	 * @generated
	 */
	EAttribute getCall_ServiceName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.Call#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.query.ast.Call#getType()
	 * @see #getCall()
	 * @generated
	 */
	EAttribute getCall_Type();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.ast.Call#getArguments <em>Arguments</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.eclipse.acceleo.query.ast.Call#getArguments()
	 * @see #getCall()
	 * @generated
	 */
	EReference getCall_Arguments();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.IntegerLiteral
	 * <em>Integer Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Integer Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.IntegerLiteral
	 * @generated
	 */
	EClass getIntegerLiteral();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.ast.IntegerLiteral#getValue <em>Value</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.ast.IntegerLiteral#getValue()
	 * @see #getIntegerLiteral()
	 * @generated
	 */
	EAttribute getIntegerLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.RealLiteral
	 * <em>Real Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Real Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.RealLiteral
	 * @generated
	 */
	EClass getRealLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.RealLiteral#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.ast.RealLiteral#getValue()
	 * @see #getRealLiteral()
	 * @generated
	 */
	EAttribute getRealLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.StringLiteral
	 * <em>String Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>String Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.StringLiteral
	 * @generated
	 */
	EClass getStringLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.StringLiteral#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.ast.StringLiteral#getValue()
	 * @see #getStringLiteral()
	 * @generated
	 */
	EAttribute getStringLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.BooleanLiteral
	 * <em>Boolean Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Boolean Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.BooleanLiteral
	 * @generated
	 */
	EClass getBooleanLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.BooleanLiteral#isValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.ast.BooleanLiteral#isValue()
	 * @see #getBooleanLiteral()
	 * @generated
	 */
	EAttribute getBooleanLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.EnumLiteral
	 * <em>Enum Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Enum Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.EnumLiteral
	 * @generated
	 */
	EClass getEnumLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.query.ast.EnumLiteral#getLiteral
	 * <em>Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.EnumLiteral#getLiteral()
	 * @see #getEnumLiteral()
	 * @generated
	 */
	EReference getEnumLiteral_Literal();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.TypeLiteral
	 * <em>Type Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.TypeLiteral
	 * @generated
	 */
	EClass getTypeLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.TypeLiteral#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.ast.TypeLiteral#getValue()
	 * @see #getTypeLiteral()
	 * @generated
	 */
	EAttribute getTypeLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.CollectionTypeLiteral
	 * <em>Collection Type Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Collection Type Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.CollectionTypeLiteral
	 * @generated
	 */
	EClass getCollectionTypeLiteral();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.CollectionTypeLiteral#getElementType <em>Element Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Element Type</em>'.
	 * @see org.eclipse.acceleo.query.ast.CollectionTypeLiteral#getElementType()
	 * @see #getCollectionTypeLiteral()
	 * @generated
	 */
	EReference getCollectionTypeLiteral_ElementType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Lambda <em>Lambda</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Lambda</em>'.
	 * @see org.eclipse.acceleo.query.ast.Lambda
	 * @generated
	 */
	EClass getLambda();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.ast.Lambda#getParameters <em>Parameters</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.acceleo.query.ast.Lambda#getParameters()
	 * @see #getLambda()
	 * @generated
	 */
	EReference getLambda_Parameters();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Lambda#getExpression <em>Expression</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.query.ast.Lambda#getExpression()
	 * @see #getLambda()
	 * @generated
	 */
	EReference getLambda_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.Lambda#getEvaluator
	 * <em>Evaluator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Evaluator</em>'.
	 * @see org.eclipse.acceleo.query.ast.Lambda#getEvaluator()
	 * @see #getLambda()
	 * @generated
	 */
	EAttribute getLambda_Evaluator();

	/**
	 * Returns the meta object for the '{@link org.eclipse.acceleo.query.ast.Lambda#eval(java.lang.Object[])
	 * <em>Eval</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Eval</em>' operation.
	 * @see org.eclipse.acceleo.query.ast.Lambda#eval(java.lang.Object[])
	 * @generated
	 */
	EOperation getLambda__Eval__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.NullLiteral
	 * <em>Null Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Null Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.NullLiteral
	 * @generated
	 */
	EClass getNullLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.SetInExtensionLiteral
	 * <em>Set In Extension Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Set In Extension Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.SetInExtensionLiteral
	 * @generated
	 */
	EClass getSetInExtensionLiteral();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.ast.SetInExtensionLiteral#getValues <em>Values</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.acceleo.query.ast.SetInExtensionLiteral#getValues()
	 * @see #getSetInExtensionLiteral()
	 * @generated
	 */
	EReference getSetInExtensionLiteral_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral
	 * <em>Sequence In Extension Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sequence In Extension Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral
	 * @generated
	 */
	EClass getSequenceInExtensionLiteral();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral#getValues <em>Values</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral#getValues()
	 * @see #getSequenceInExtensionLiteral()
	 * @generated
	 */
	EReference getSequenceInExtensionLiteral_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.VariableDeclaration
	 * <em>Variable Declaration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable Declaration</em>'.
	 * @see org.eclipse.acceleo.query.ast.VariableDeclaration
	 * @generated
	 */
	EClass getVariableDeclaration();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.ast.VariableDeclaration#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.ast.VariableDeclaration#getName()
	 * @see #getVariableDeclaration()
	 * @generated
	 */
	EAttribute getVariableDeclaration_Name();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.VariableDeclaration#getType <em>Type</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.eclipse.acceleo.query.ast.VariableDeclaration#getType()
	 * @see #getVariableDeclaration()
	 * @generated
	 */
	EReference getVariableDeclaration_Type();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.ast.VariableDeclaration#getExpression <em>Expression</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.query.ast.VariableDeclaration#getExpression()
	 * @see #getVariableDeclaration()
	 * @generated
	 */
	EReference getVariableDeclaration_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Error <em>Error</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error</em>'.
	 * @see org.eclipse.acceleo.query.ast.Error
	 * @generated
	 */
	EClass getError();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorExpression
	 * <em>Error Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Expression</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorExpression
	 * @generated
	 */
	EClass getErrorExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorTypeLiteral
	 * <em>Error Type Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Type Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorTypeLiteral
	 * @generated
	 */
	EClass getErrorTypeLiteral();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.ast.ErrorTypeLiteral#getSegments <em>Segments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Segments</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorTypeLiteral#getSegments()
	 * @see #getErrorTypeLiteral()
	 * @generated
	 */
	EAttribute getErrorTypeLiteral_Segments();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall
	 * <em>Error Feature Access Or Call</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Feature Access Or Call</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall
	 * @generated
	 */
	EClass getErrorFeatureAccessOrCall();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall#getTarget()
	 * @see #getErrorFeatureAccessOrCall()
	 * @generated
	 */
	EReference getErrorFeatureAccessOrCall_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorCollectionCall
	 * <em>Error Collection Call</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Collection Call</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorCollectionCall
	 * @generated
	 */
	EClass getErrorCollectionCall();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.ErrorCollectionCall#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorCollectionCall#getTarget()
	 * @see #getErrorCollectionCall()
	 * @generated
	 */
	EReference getErrorCollectionCall_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorVariableDeclaration
	 * <em>Error Variable Declaration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Variable Declaration</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorVariableDeclaration
	 * @generated
	 */
	EClass getErrorVariableDeclaration();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.ast.CallType <em>Call Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Call Type</em>'.
	 * @see org.eclipse.acceleo.query.ast.CallType
	 * @generated
	 */
	EEnum getCallType();

	/**
	 * Returns the meta object for data type '{@link java.lang.Object <em>Object Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Object Type</em>'.
	 * @see java.lang.Object
	 * @model instanceClass="java.lang.Object"
	 * @generated
	 */
	EDataType getObjectType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.acceleo.query.parser.AstEvaluator
	 * <em>Evaluator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Evaluator</em>'.
	 * @see org.eclipse.acceleo.query.parser.AstEvaluator
	 * @model instanceClass="org.eclipse.acceleo.query.parser.AstEvaluator"
	 * @generated
	 */
	EDataType getAstEvaluator();

	/**
	 * Returns the meta object for data type '<em>Object Array</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Object Array</em>'.
	 * @model instanceClass="java.lang.Object[]"
	 * @generated
	 */
	EDataType getObjectArray();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AstFactory getAstFactory();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ExpressionImpl
		 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ExpressionImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.VarRefImpl
		 * <em>Var Ref</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.VarRefImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getVarRef()
		 * @generated
		 */
		EClass VAR_REF = eINSTANCE.getVarRef();

		/**
		 * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VAR_REF__VARIABLE_NAME = eINSTANCE.getVarRef_VariableName();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.FeatureAccessImpl
		 * <em>Feature Access</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.FeatureAccessImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getFeatureAccess()
		 * @generated
		 */
		EClass FEATURE_ACCESS = eINSTANCE.getFeatureAccess();

		/**
		 * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FEATURE_ACCESS__FEATURE_NAME = eINSTANCE.getFeatureAccess_FeatureName();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE_ACCESS__TARGET = eINSTANCE.getFeatureAccess_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.CallImpl <em>Call</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.CallImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCall()
		 * @generated
		 */
		EClass CALL = eINSTANCE.getCall();

		/**
		 * The meta object literal for the '<em><b>Service Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CALL__SERVICE_NAME = eINSTANCE.getCall_ServiceName();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CALL__TYPE = eINSTANCE.getCall_Type();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CALL__ARGUMENTS = eINSTANCE.getCall_Arguments();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.LiteralImpl
		 * <em>Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.LiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.IntegerLiteralImpl
		 * <em>Integer Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.IntegerLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getIntegerLiteral()
		 * @generated
		 */
		EClass INTEGER_LITERAL = eINSTANCE.getIntegerLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGER_LITERAL__VALUE = eINSTANCE.getIntegerLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.RealLiteralImpl
		 * <em>Real Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.RealLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getRealLiteral()
		 * @generated
		 */
		EClass REAL_LITERAL = eINSTANCE.getRealLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REAL_LITERAL__VALUE = eINSTANCE.getRealLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.StringLiteralImpl
		 * <em>String Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.StringLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getStringLiteral()
		 * @generated
		 */
		EClass STRING_LITERAL = eINSTANCE.getStringLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_LITERAL__VALUE = eINSTANCE.getStringLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.BooleanLiteralImpl
		 * <em>Boolean Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.BooleanLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getBooleanLiteral()
		 * @generated
		 */
		EClass BOOLEAN_LITERAL = eINSTANCE.getBooleanLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOLEAN_LITERAL__VALUE = eINSTANCE.getBooleanLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.EnumLiteralImpl
		 * <em>Enum Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.EnumLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getEnumLiteral()
		 * @generated
		 */
		EClass ENUM_LITERAL = eINSTANCE.getEnumLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ENUM_LITERAL__LITERAL = eINSTANCE.getEnumLiteral_Literal();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.TypeLiteralImpl
		 * <em>Type Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.TypeLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getTypeLiteral()
		 * @generated
		 */
		EClass TYPE_LITERAL = eINSTANCE.getTypeLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TYPE_LITERAL__VALUE = eINSTANCE.getTypeLiteral_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.ast.impl.CollectionTypeLiteralImpl
		 * <em>Collection Type Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.CollectionTypeLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCollectionTypeLiteral()
		 * @generated
		 */
		EClass COLLECTION_TYPE_LITERAL = eINSTANCE.getCollectionTypeLiteral();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COLLECTION_TYPE_LITERAL__ELEMENT_TYPE = eINSTANCE.getCollectionTypeLiteral_ElementType();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.LambdaImpl
		 * <em>Lambda</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.LambdaImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getLambda()
		 * @generated
		 */
		EClass LAMBDA = eINSTANCE.getLambda();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LAMBDA__PARAMETERS = eINSTANCE.getLambda_Parameters();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LAMBDA__EXPRESSION = eINSTANCE.getLambda_Expression();

		/**
		 * The meta object literal for the '<em><b>Evaluator</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LAMBDA__EVALUATOR = eINSTANCE.getLambda_Evaluator();

		/**
		 * The meta object literal for the '<em><b>Eval</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation LAMBDA___EVAL__OBJECT = eINSTANCE.getLambda__Eval__Object();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.NullLiteralImpl
		 * <em>Null Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.NullLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getNullLiteral()
		 * @generated
		 */
		EClass NULL_LITERAL = eINSTANCE.getNullLiteral();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.ast.impl.SetInExtensionLiteralImpl
		 * <em>Set In Extension Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.SetInExtensionLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getSetInExtensionLiteral()
		 * @generated
		 */
		EClass SET_IN_EXTENSION_LITERAL = eINSTANCE.getSetInExtensionLiteral();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SET_IN_EXTENSION_LITERAL__VALUES = eINSTANCE.getSetInExtensionLiteral_Values();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.ast.impl.SequenceInExtensionLiteralImpl
		 * <em>Sequence In Extension Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.SequenceInExtensionLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getSequenceInExtensionLiteral()
		 * @generated
		 */
		EClass SEQUENCE_IN_EXTENSION_LITERAL = eINSTANCE.getSequenceInExtensionLiteral();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SEQUENCE_IN_EXTENSION_LITERAL__VALUES = eINSTANCE.getSequenceInExtensionLiteral_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.VariableDeclarationImpl
		 * <em>Variable Declaration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.VariableDeclarationImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getVariableDeclaration()
		 * @generated
		 */
		EClass VARIABLE_DECLARATION = eINSTANCE.getVariableDeclaration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE_DECLARATION__NAME = eINSTANCE.getVariableDeclaration_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VARIABLE_DECLARATION__TYPE = eINSTANCE.getVariableDeclaration_Type();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VARIABLE_DECLARATION__EXPRESSION = eINSTANCE.getVariableDeclaration_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.Error <em>Error</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.Error
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getError()
		 * @generated
		 */
		EClass ERROR = eINSTANCE.getError();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorExpressionImpl
		 * <em>Error Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorExpressionImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorExpression()
		 * @generated
		 */
		EClass ERROR_EXPRESSION = eINSTANCE.getErrorExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorTypeLiteralImpl
		 * <em>Error Type Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorTypeLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorTypeLiteral()
		 * @generated
		 */
		EClass ERROR_TYPE_LITERAL = eINSTANCE.getErrorTypeLiteral();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TYPE_LITERAL__SEGMENTS = eINSTANCE.getErrorTypeLiteral_Segments();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.ast.impl.ErrorFeatureAccessOrCallImpl
		 * <em>Error Feature Access Or Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorFeatureAccessOrCallImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorFeatureAccessOrCall()
		 * @generated
		 */
		EClass ERROR_FEATURE_ACCESS_OR_CALL = eINSTANCE.getErrorFeatureAccessOrCall();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ERROR_FEATURE_ACCESS_OR_CALL__TARGET = eINSTANCE.getErrorFeatureAccessOrCall_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorCollectionCallImpl
		 * <em>Error Collection Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorCollectionCallImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorCollectionCall()
		 * @generated
		 */
		EClass ERROR_COLLECTION_CALL = eINSTANCE.getErrorCollectionCall();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ERROR_COLLECTION_CALL__TARGET = eINSTANCE.getErrorCollectionCall_Target();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl
		 * <em>Error Variable Declaration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorVariableDeclaration()
		 * @generated
		 */
		EClass ERROR_VARIABLE_DECLARATION = eINSTANCE.getErrorVariableDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.CallType <em>Call Type</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.CallType
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCallType()
		 * @generated
		 */
		EEnum CALL_TYPE = eINSTANCE.getCallType();

		/**
		 * The meta object literal for the '<em>Object Type</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.lang.Object
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getObjectType()
		 * @generated
		 */
		EDataType OBJECT_TYPE = eINSTANCE.getObjectType();

		/**
		 * The meta object literal for the '<em>Evaluator</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.parser.AstEvaluator
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getAstEvaluator()
		 * @generated
		 */
		EDataType AST_EVALUATOR = eINSTANCE.getAstEvaluator();

		/**
		 * The meta object literal for the '<em>Object Array</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getObjectArray()
		 * @generated
		 */
		EDataType OBJECT_ARRAY = eINSTANCE.getObjectArray();

	}

} // AstPackage