/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.CallImpl <em>Call</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.CallImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCall()
	 * @generated
	 */
	int CALL = 2;

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
	int LITERAL = 3;

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
	int INTEGER_LITERAL = 4;

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
	int REAL_LITERAL = 5;

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
	int STRING_LITERAL = 6;

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
	int BOOLEAN_LITERAL = 7;

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
	int ENUM_LITERAL = 8;

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
	int TYPE_LITERAL = 9;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.TypeSetLiteralImpl
	 * <em>Type Set Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.TypeSetLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getTypeSetLiteral()
	 * @generated
	 */
	int TYPE_SET_LITERAL = 10;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SET_LITERAL__VALUE = TYPE_LITERAL__VALUE;

	/**
	 * The feature id for the '<em><b>Types</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SET_LITERAL__TYPES = TYPE_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Set Literal</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SET_LITERAL_FEATURE_COUNT = TYPE_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Set Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SET_LITERAL_OPERATION_COUNT = TYPE_LITERAL_OPERATION_COUNT + 0;

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
	 * The number of structural features of the '<em>Lambda</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Lambda</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LAMBDA_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 0;

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
	int ERROR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Error</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

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
	 * The feature id for the '<em><b>Missing Colon</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL__MISSING_COLON = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Type Literal</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Type Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_TYPE_LITERAL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl
	 * <em>Error Enum Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorEnumLiteral()
	 * @generated
	 */
	int ERROR_ENUM_LITERAL = 20;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_ENUM_LITERAL__LITERAL = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_ENUM_LITERAL__SEGMENTS = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Missing Colon</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_ENUM_LITERAL__MISSING_COLON = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Enum Literal</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_ENUM_LITERAL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Enum Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_ENUM_LITERAL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl
	 * <em>Error Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorCallImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorCall()
	 * @generated
	 */
	int ERROR_CALL = 21;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CALL__SERVICE_NAME = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CALL__TYPE = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CALL__ARGUMENTS = ERROR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Missing End Parenthesis</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CALL__MISSING_END_PARENTHESIS = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Error Call</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CALL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Error Call</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CALL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

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
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorStringLiteralImpl
	 * <em>Error String Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorStringLiteralImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorStringLiteral()
	 * @generated
	 */
	int ERROR_STRING_LITERAL = 23;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_STRING_LITERAL__VALUE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error String Literal</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_STRING_LITERAL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Error String Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_STRING_LITERAL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl
	 * <em>Error Conditional</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorConditional()
	 * @generated
	 */
	int ERROR_CONDITIONAL = 24;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CONDITIONAL__PREDICATE = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>True Branch</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CONDITIONAL__TRUE_BRANCH = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>False Branch</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CONDITIONAL__FALSE_BRANCH = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Conditional</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CONDITIONAL_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Conditional</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_CONDITIONAL_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.BindingImpl <em>Binding</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.BindingImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getBinding()
	 * @generated
	 */
	int BINDING = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Binding</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Binding</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINDING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorBindingImpl
	 * <em>Error Binding</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ErrorBindingImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorBinding()
	 * @generated
	 */
	int ERROR_BINDING = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__NAME = ERROR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__TYPE = ERROR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING__VALUE = ERROR_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Error Binding</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING_FEATURE_COUNT = ERROR_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Error Binding</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_BINDING_OPERATION_COUNT = ERROR_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.LetImpl <em>Let</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.LetImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getLet()
	 * @generated
	 */
	int LET = 27;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET__BINDINGS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET__BODY = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Let</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Let</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LET_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ConditionalImpl
	 * <em>Conditional</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ConditionalImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getConditional()
	 * @generated
	 */
	int CONDITIONAL = 28;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL__PREDICATE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>True Branch</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL__TRUE_BRANCH = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>False Branch</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL__FALSE_BRANCH = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Conditional</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Conditional</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.OrImpl <em>Or</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.OrImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getOr()
	 * @generated
	 */
	int OR = 29;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR__SERVICE_NAME = CALL__SERVICE_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR__TYPE = CALL__TYPE;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR__ARGUMENTS = CALL__ARGUMENTS;

	/**
	 * The number of structural features of the '<em>Or</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR_FEATURE_COUNT = CALL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Or</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OR_OPERATION_COUNT = CALL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.AndImpl <em>And</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.AndImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getAnd()
	 * @generated
	 */
	int AND = 30;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND__SERVICE_NAME = CALL__SERVICE_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND__TYPE = CALL__TYPE;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND__ARGUMENTS = CALL__ARGUMENTS;

	/**
	 * The number of structural features of the '<em>And</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND_FEATURE_COUNT = CALL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>And</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AND_OPERATION_COUNT = CALL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.impl.ImpliesImpl <em>Implies</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.impl.ImpliesImpl
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getImplies()
	 * @generated
	 */
	int IMPLIES = 31;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPLIES__SERVICE_NAME = CALL__SERVICE_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPLIES__TYPE = CALL__TYPE;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPLIES__ARGUMENTS = CALL__ARGUMENTS;

	/**
	 * The number of structural features of the '<em>Implies</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPLIES_FEATURE_COUNT = CALL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Implies</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IMPLIES_OPERATION_COUNT = CALL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.ast.CallType <em>Call Type</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.ast.CallType
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getCallType()
	 * @generated
	 */
	int CALL_TYPE = 32;

	/**
	 * The meta object id for the '<em>Object Type</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see java.lang.Object
	 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getObjectType()
	 * @generated
	 */
	int OBJECT_TYPE = 33;

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
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.TypeSetLiteral
	 * <em>Type Set Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Set Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.TypeSetLiteral
	 * @generated
	 */
	EClass getTypeSetLiteral();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.ast.TypeSetLiteral#getTypes <em>Types</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Types</em>'.
	 * @see org.eclipse.acceleo.query.ast.TypeSetLiteral#getTypes()
	 * @see #getTypeSetLiteral()
	 * @generated
	 */
	EReference getTypeSetLiteral_Types();

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
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.ast.ErrorTypeLiteral#isMissingColon <em>Missing Colon</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Colon</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorTypeLiteral#isMissingColon()
	 * @see #getErrorTypeLiteral()
	 * @generated
	 */
	EAttribute getErrorTypeLiteral_MissingColon();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorEnumLiteral
	 * <em>Error Enum Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Enum Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorEnumLiteral
	 * @generated
	 */
	EClass getErrorEnumLiteral();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.ast.ErrorEnumLiteral#getSegments <em>Segments</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Segments</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorEnumLiteral#getSegments()
	 * @see #getErrorEnumLiteral()
	 * @generated
	 */
	EAttribute getErrorEnumLiteral_Segments();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.ast.ErrorEnumLiteral#isMissingColon <em>Missing Colon</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing Colon</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorEnumLiteral#isMissingColon()
	 * @see #getErrorEnumLiteral()
	 * @generated
	 */
	EAttribute getErrorEnumLiteral_MissingColon();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorCall <em>Error Call</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Call</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorCall
	 * @generated
	 */
	EClass getErrorCall();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.ast.ErrorCall#isMissingEndParenthesis
	 * <em>Missing End Parenthesis</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Missing End Parenthesis</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorCall#isMissingEndParenthesis()
	 * @see #getErrorCall()
	 * @generated
	 */
	EAttribute getErrorCall_MissingEndParenthesis();

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
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorStringLiteral
	 * <em>Error String Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error String Literal</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorStringLiteral
	 * @generated
	 */
	EClass getErrorStringLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorConditional
	 * <em>Error Conditional</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Conditional</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorConditional
	 * @generated
	 */
	EClass getErrorConditional();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Binding <em>Binding</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Binding</em>'.
	 * @see org.eclipse.acceleo.query.ast.Binding
	 * @generated
	 */
	EClass getBinding();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.ast.Binding#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.ast.Binding#getName()
	 * @see #getBinding()
	 * @generated
	 */
	EAttribute getBinding_Name();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Binding#getType <em>Type</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.eclipse.acceleo.query.ast.Binding#getType()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_Type();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Binding#getValue <em>Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.ast.Binding#getValue()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.ErrorBinding
	 * <em>Error Binding</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Binding</em>'.
	 * @see org.eclipse.acceleo.query.ast.ErrorBinding
	 * @generated
	 */
	EClass getErrorBinding();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Let <em>Let</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Let</em>'.
	 * @see org.eclipse.acceleo.query.ast.Let
	 * @generated
	 */
	EClass getLet();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.ast.Let#getBindings <em>Bindings</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see org.eclipse.acceleo.query.ast.Let#getBindings()
	 * @see #getLet()
	 * @generated
	 */
	EReference getLet_Bindings();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Let#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.acceleo.query.ast.Let#getBody()
	 * @see #getLet()
	 * @generated
	 */
	EReference getLet_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Conditional
	 * <em>Conditional</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Conditional</em>'.
	 * @see org.eclipse.acceleo.query.ast.Conditional
	 * @generated
	 */
	EClass getConditional();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Conditional#getPredicate <em>Predicate</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Predicate</em>'.
	 * @see org.eclipse.acceleo.query.ast.Conditional#getPredicate()
	 * @see #getConditional()
	 * @generated
	 */
	EReference getConditional_Predicate();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Conditional#getTrueBranch <em>True Branch</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>True Branch</em>'.
	 * @see org.eclipse.acceleo.query.ast.Conditional#getTrueBranch()
	 * @see #getConditional()
	 * @generated
	 */
	EReference getConditional_TrueBranch();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.ast.Conditional#getFalseBranch <em>False Branch</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>False Branch</em>'.
	 * @see org.eclipse.acceleo.query.ast.Conditional#getFalseBranch()
	 * @see #getConditional()
	 * @generated
	 */
	EReference getConditional_FalseBranch();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Or <em>Or</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Or</em>'.
	 * @see org.eclipse.acceleo.query.ast.Or
	 * @generated
	 */
	EClass getOr();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.And <em>And</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>And</em>'.
	 * @see org.eclipse.acceleo.query.ast.And
	 * @generated
	 */
	EClass getAnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.ast.Implies <em>Implies</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Implies</em>'.
	 * @see org.eclipse.acceleo.query.ast.Implies
	 * @generated
	 */
	EClass getImplies();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.TypeSetLiteralImpl
		 * <em>Type Set Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.TypeSetLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getTypeSetLiteral()
		 * @generated
		 */
		EClass TYPE_SET_LITERAL = eINSTANCE.getTypeSetLiteral();

		/**
		 * The meta object literal for the '<em><b>Types</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE_SET_LITERAL__TYPES = eINSTANCE.getTypeSetLiteral_Types();

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
		 * The meta object literal for the '<em><b>Missing Colon</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_TYPE_LITERAL__MISSING_COLON = eINSTANCE.getErrorTypeLiteral_MissingColon();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl
		 * <em>Error Enum Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorEnumLiteral()
		 * @generated
		 */
		EClass ERROR_ENUM_LITERAL = eINSTANCE.getErrorEnumLiteral();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_ENUM_LITERAL__SEGMENTS = eINSTANCE.getErrorEnumLiteral_Segments();

		/**
		 * The meta object literal for the '<em><b>Missing Colon</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_ENUM_LITERAL__MISSING_COLON = eINSTANCE.getErrorEnumLiteral_MissingColon();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl
		 * <em>Error Call</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorCallImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorCall()
		 * @generated
		 */
		EClass ERROR_CALL = eINSTANCE.getErrorCall();

		/**
		 * The meta object literal for the '<em><b>Missing End Parenthesis</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_CALL__MISSING_END_PARENTHESIS = eINSTANCE.getErrorCall_MissingEndParenthesis();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorStringLiteralImpl
		 * <em>Error String Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorStringLiteralImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorStringLiteral()
		 * @generated
		 */
		EClass ERROR_STRING_LITERAL = eINSTANCE.getErrorStringLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl
		 * <em>Error Conditional</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorConditional()
		 * @generated
		 */
		EClass ERROR_CONDITIONAL = eINSTANCE.getErrorConditional();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.BindingImpl
		 * <em>Binding</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.BindingImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getBinding()
		 * @generated
		 */
		EClass BINDING = eINSTANCE.getBinding();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BINDING__NAME = eINSTANCE.getBinding_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINDING__TYPE = eINSTANCE.getBinding_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINDING__VALUE = eINSTANCE.getBinding_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ErrorBindingImpl
		 * <em>Error Binding</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ErrorBindingImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getErrorBinding()
		 * @generated
		 */
		EClass ERROR_BINDING = eINSTANCE.getErrorBinding();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.LetImpl <em>Let</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.LetImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getLet()
		 * @generated
		 */
		EClass LET = eINSTANCE.getLet();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET__BINDINGS = eINSTANCE.getLet_Bindings();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LET__BODY = eINSTANCE.getLet_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ConditionalImpl
		 * <em>Conditional</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ConditionalImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getConditional()
		 * @generated
		 */
		EClass CONDITIONAL = eINSTANCE.getConditional();

		/**
		 * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONDITIONAL__PREDICATE = eINSTANCE.getConditional_Predicate();

		/**
		 * The meta object literal for the '<em><b>True Branch</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONDITIONAL__TRUE_BRANCH = eINSTANCE.getConditional_TrueBranch();

		/**
		 * The meta object literal for the '<em><b>False Branch</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONDITIONAL__FALSE_BRANCH = eINSTANCE.getConditional_FalseBranch();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.OrImpl <em>Or</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.OrImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getOr()
		 * @generated
		 */
		EClass OR = eINSTANCE.getOr();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.AndImpl <em>And</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.AndImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getAnd()
		 * @generated
		 */
		EClass AND = eINSTANCE.getAnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.ast.impl.ImpliesImpl
		 * <em>Implies</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.ast.impl.ImpliesImpl
		 * @see org.eclipse.acceleo.query.ast.impl.AstPackageImpl#getImplies()
		 * @generated
		 */
		EClass IMPLIES = eINSTANCE.getImplies();

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

	}

} // AstPackage
