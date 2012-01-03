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
package org.eclipse.acceleo.compatibility.model.mt.expressions;

import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
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
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory
 * @model kind="package"
 * @generated
 */
public interface ExpressionsPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "expressions"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/mt/2.6.0/expressions"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "expressions"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ExpressionsPackage eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Expression
	 * <em>Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Expression
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__BEGIN = CorePackage.AST_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__END = CorePackage.AST_NODE__END;

	/**
	 * The number of structural features of the '<em>Expression</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = CorePackage.AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallSetImpl <em>Call Set</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallSetImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getCallSet()
	 * @generated
	 */
	int CALL_SET = 1;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_SET__BEGIN = EXPRESSION__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_SET__END = EXPRESSION__END;

	/**
	 * The feature id for the '<em><b>Calls</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_SET__CALLS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Call Set</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_SET_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl <em>Call</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getCall()
	 * @generated
	 */
	int CALL = 2;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__BEGIN = CorePackage.AST_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__END = CorePackage.AST_NODE__END;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__NAME = CorePackage.AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Prefix</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__PREFIX = CorePackage.AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__ARGUMENTS = CorePackage.AST_NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL__FILTER = CorePackage.AST_NODE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Call</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CALL_FEATURE_COUNT = CorePackage.AST_NODE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NotImpl
	 * <em>Not</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NotImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getNot()
	 * @generated
	 */
	int NOT = 3;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NOT__BEGIN = EXPRESSION__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NOT__END = EXPRESSION__END;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NOT__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Not</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NOT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.OperatorImpl <em>Operator</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.OperatorImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 4;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATOR__BEGIN = EXPRESSION__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATOR__END = EXPRESSION__END;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATOR__OPERATOR = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operands</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATOR__OPERANDS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Operator</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OPERATOR_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ParenthesisImpl
	 * <em>Parenthesis</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ParenthesisImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getParenthesis()
	 * @generated
	 */
	int PARENTHESIS = 5;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARENTHESIS__BEGIN = EXPRESSION__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARENTHESIS__END = EXPRESSION__END;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARENTHESIS__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parenthesis</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARENTHESIS_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Literal
	 * <em>Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Literal
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 6;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL__BEGIN = EXPRESSION__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL__END = EXPRESSION__END;

	/**
	 * The number of structural features of the '<em>Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.StringLiteralImpl
	 * <em>String Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.StringLiteralImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getStringLiteral()
	 * @generated
	 */
	int STRING_LITERAL = 7;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__BEGIN = LITERAL__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__END = LITERAL__END;

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
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.IntegerLiteralImpl
	 * <em>Integer Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.IntegerLiteralImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getIntegerLiteral()
	 * @generated
	 */
	int INTEGER_LITERAL = 8;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__BEGIN = LITERAL__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__END = LITERAL__END;

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
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.DoubleLiteralImpl
	 * <em>Double Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.DoubleLiteralImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getDoubleLiteral()
	 * @generated
	 */
	int DOUBLE_LITERAL = 9;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__BEGIN = LITERAL__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__END = LITERAL__END;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.BooleanLiteralImpl
	 * <em>Boolean Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.BooleanLiteralImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getBooleanLiteral()
	 * @generated
	 */
	int BOOLEAN_LITERAL = 10;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL__BEGIN = LITERAL__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL__END = LITERAL__END;

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
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NullLiteralImpl
	 * <em>Null Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NullLiteralImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getNullLiteral()
	 * @generated
	 */
	int NULL_LITERAL = 11;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL__BEGIN = LITERAL__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL__END = LITERAL__END;

	/**
	 * The number of structural features of the '<em>Null Literal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 0;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Expression <em>Expression</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet <em>Call Set</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Call Set</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet
	 * @generated
	 */
	EClass getCallSet();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet#getCalls <em>Calls</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Calls</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet#getCalls()
	 * @see #getCallSet()
	 * @generated
	 */
	EReference getCallSet_Calls();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call
	 * <em>Call</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Call</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Call
	 * @generated
	 */
	EClass getCall();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getName()
	 * @see #getCall()
	 * @generated
	 */
	EAttribute getCall_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getPrefix <em>Prefix</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Prefix</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getPrefix()
	 * @see #getCall()
	 * @generated
	 */
	EAttribute getCall_Prefix();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getArguments()
	 * @see #getCall()
	 * @generated
	 */
	EReference getCall_Arguments();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getFilter <em>Filter</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getFilter()
	 * @see #getCall()
	 * @generated
	 */
	EReference getCall_Filter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Not
	 * <em>Not</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Not</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Not
	 * @generated
	 */
	EClass getNot();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Not#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Not#getExpression()
	 * @see #getNot()
	 * @generated
	 */
	EReference getNot_Expression();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator <em>Operator</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Operator</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Operator
	 * @generated
	 */
	EClass getOperator();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperator()
	 * @see #getOperator()
	 * @generated
	 */
	EAttribute getOperator_Operator();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperands <em>Operands</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Operands</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Operator#getOperands()
	 * @see #getOperator()
	 * @generated
	 */
	EReference getOperator_Operands();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis <em>Parenthesis</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parenthesis</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis
	 * @generated
	 */
	EClass getParenthesis();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis#getExpression()
	 * @see #getParenthesis()
	 * @generated
	 */
	EReference getParenthesis_Expression();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Literal <em>Literal</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>String Literal</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral
	 * @generated
	 */
	EClass getStringLiteral();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral#getValue()
	 * @see #getStringLiteral()
	 * @generated
	 */
	EAttribute getStringLiteral_Value();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral <em>Integer Literal</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Integer Literal</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral
	 * @generated
	 */
	EClass getIntegerLiteral();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral#getValue()
	 * @see #getIntegerLiteral()
	 * @generated
	 */
	EAttribute getIntegerLiteral_Value();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Double Literal</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral
	 * @generated
	 */
	EClass getDoubleLiteral();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral#getValue()
	 * @see #getDoubleLiteral()
	 * @generated
	 */
	EAttribute getDoubleLiteral_Value();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral <em>Boolean Literal</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Boolean Literal</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral
	 * @generated
	 */
	EClass getBooleanLiteral();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral#isValue()
	 * @see #getBooleanLiteral()
	 * @generated
	 */
	EAttribute getBooleanLiteral_Value();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral <em>Null Literal</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Null Literal</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral
	 * @generated
	 */
	EClass getNullLiteral();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExpressionsFactory getExpressionsFactory();

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
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Expression <em>Expression</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Expression
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallSetImpl <em>Call Set</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallSetImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getCallSet()
		 * @generated
		 */
		EClass CALL_SET = eINSTANCE.getCallSet();

		/**
		 * The meta object literal for the '<em><b>Calls</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CALL_SET__CALLS = eINSTANCE.getCallSet_Calls();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl <em>Call</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getCall()
		 * @generated
		 */
		EClass CALL = eINSTANCE.getCall();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CALL__NAME = eINSTANCE.getCall_Name();

		/**
		 * The meta object literal for the '<em><b>Prefix</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CALL__PREFIX = eINSTANCE.getCall_Prefix();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CALL__ARGUMENTS = eINSTANCE.getCall_Arguments();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CALL__FILTER = eINSTANCE.getCall_Filter();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NotImpl <em>Not</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NotImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getNot()
		 * @generated
		 */
		EClass NOT = eINSTANCE.getNot();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference NOT__EXPRESSION = eINSTANCE.getNot_Expression();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.OperatorImpl <em>Operator</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.OperatorImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getOperator()
		 * @generated
		 */
		EClass OPERATOR = eINSTANCE.getOperator();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OPERATOR__OPERATOR = eINSTANCE.getOperator_Operator();

		/**
		 * The meta object literal for the '<em><b>Operands</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference OPERATOR__OPERANDS = eINSTANCE.getOperator_Operands();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ParenthesisImpl
		 * <em>Parenthesis</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ParenthesisImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getParenthesis()
		 * @generated
		 */
		EClass PARENTHESIS = eINSTANCE.getParenthesis();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PARENTHESIS__EXPRESSION = eINSTANCE.getParenthesis_Expression();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Literal <em>Literal</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Literal
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.StringLiteralImpl
		 * <em>String Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.StringLiteralImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getStringLiteral()
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
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.IntegerLiteralImpl
		 * <em>Integer Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.IntegerLiteralImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getIntegerLiteral()
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
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.DoubleLiteralImpl
		 * <em>Double Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.DoubleLiteralImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getDoubleLiteral()
		 * @generated
		 */
		EClass DOUBLE_LITERAL = eINSTANCE.getDoubleLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DOUBLE_LITERAL__VALUE = eINSTANCE.getDoubleLiteral_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.BooleanLiteralImpl
		 * <em>Boolean Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.BooleanLiteralImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getBooleanLiteral()
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
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NullLiteralImpl
		 * <em>Null Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.NullLiteralImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl#getNullLiteral()
		 * @generated
		 */
		EClass NULL_LITERAL = eINSTANCE.getNullLiteral();

	}

} // ExpressionsPackage
