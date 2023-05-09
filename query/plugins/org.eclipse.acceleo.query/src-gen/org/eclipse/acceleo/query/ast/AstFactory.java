/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.ast.AstPackage
 * @generated
 */
public interface AstFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	AstFactory eINSTANCE = org.eclipse.acceleo.query.ast.impl.AstFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Var Ref</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Var Ref</em>'.
	 * @generated
	 */
	VarRef createVarRef();

	/**
	 * Returns a new object of class '<em>Call</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Call</em>'.
	 * @generated
	 */
	Call createCall();

	/**
	 * Returns a new object of class '<em>Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Literal</em>'.
	 * @generated
	 */
	Literal createLiteral();

	/**
	 * Returns a new object of class '<em>Integer Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Integer Literal</em>'.
	 * @generated
	 */
	IntegerLiteral createIntegerLiteral();

	/**
	 * Returns a new object of class '<em>Real Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Real Literal</em>'.
	 * @generated
	 */
	RealLiteral createRealLiteral();

	/**
	 * Returns a new object of class '<em>String Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>String Literal</em>'.
	 * @generated
	 */
	StringLiteral createStringLiteral();

	/**
	 * Returns a new object of class '<em>Boolean Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Boolean Literal</em>'.
	 * @generated
	 */
	BooleanLiteral createBooleanLiteral();

	/**
	 * Returns a new object of class '<em>Enum Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Enum Literal</em>'.
	 * @generated
	 */
	EnumLiteral createEnumLiteral();

	/**
	 * Returns a new object of class '<em>EClassifier Type Literal</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>EClassifier Type Literal</em>'.
	 * @generated
	 */
	EClassifierTypeLiteral createEClassifierTypeLiteral();

	/**
	 * Returns a new object of class '<em>Class Type Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Class Type Literal</em>'.
	 * @generated
	 */
	ClassTypeLiteral createClassTypeLiteral();

	/**
	 * Returns a new object of class '<em>Type Set Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Type Set Literal</em>'.
	 * @generated
	 */
	TypeSetLiteral createTypeSetLiteral();

	/**
	 * Returns a new object of class '<em>Collection Type Literal</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Collection Type Literal</em>'.
	 * @generated
	 */
	CollectionTypeLiteral createCollectionTypeLiteral();

	/**
	 * Returns a new object of class '<em>Lambda</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Lambda</em>'.
	 * @generated
	 */
	Lambda createLambda();

	/**
	 * Returns a new object of class '<em>Null Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Null Literal</em>'.
	 * @generated
	 */
	NullLiteral createNullLiteral();

	/**
	 * Returns a new object of class '<em>Set In Extension Literal</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Set In Extension Literal</em>'.
	 * @generated
	 */
	SetInExtensionLiteral createSetInExtensionLiteral();

	/**
	 * Returns a new object of class '<em>Sequence In Extension Literal</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Sequence In Extension Literal</em>'.
	 * @generated
	 */
	SequenceInExtensionLiteral createSequenceInExtensionLiteral();

	/**
	 * Returns a new object of class '<em>Variable Declaration</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Variable Declaration</em>'.
	 * @generated
	 */
	VariableDeclaration createVariableDeclaration();

	/**
	 * Returns a new object of class '<em>Error Expression</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Expression</em>'.
	 * @generated
	 */
	ErrorExpression createErrorExpression();

	/**
	 * Returns a new object of class '<em>Error Type Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Type Literal</em>'.
	 * @generated
	 */
	ErrorTypeLiteral createErrorTypeLiteral();

	/**
	 * Returns a new object of class '<em>Error EClassifier Type Literal</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error EClassifier Type Literal</em>'.
	 * @generated
	 */
	ErrorEClassifierTypeLiteral createErrorEClassifierTypeLiteral();

	/**
	 * Returns a new object of class '<em>Error Enum Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Enum Literal</em>'.
	 * @generated
	 */
	ErrorEnumLiteral createErrorEnumLiteral();

	/**
	 * Returns a new object of class '<em>Error Call</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Call</em>'.
	 * @generated
	 */
	ErrorCall createErrorCall();

	/**
	 * Returns a new object of class '<em>Error Variable Declaration</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Variable Declaration</em>'.
	 * @generated
	 */
	ErrorVariableDeclaration createErrorVariableDeclaration();

	/**
	 * Returns a new object of class '<em>Error String Literal</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error String Literal</em>'.
	 * @generated
	 */
	ErrorStringLiteral createErrorStringLiteral();

	/**
	 * Returns a new object of class '<em>Error Conditional</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Conditional</em>'.
	 * @generated
	 */
	ErrorConditional createErrorConditional();

	/**
	 * Returns a new object of class '<em>Binding</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Binding</em>'.
	 * @generated
	 */
	Binding createBinding();

	/**
	 * Returns a new object of class '<em>Error Binding</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Binding</em>'.
	 * @generated
	 */
	ErrorBinding createErrorBinding();

	/**
	 * Returns a new object of class '<em>Let</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Let</em>'.
	 * @generated
	 */
	Let createLet();

	/**
	 * Returns a new object of class '<em>Conditional</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Conditional</em>'.
	 * @generated
	 */
	Conditional createConditional();

	/**
	 * Returns a new object of class '<em>Or</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Or</em>'.
	 * @generated
	 */
	Or createOr();

	/**
	 * Returns a new object of class '<em>And</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>And</em>'.
	 * @generated
	 */
	And createAnd();

	/**
	 * Returns a new object of class '<em>Implies</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Implies</em>'.
	 * @generated
	 */
	Implies createImplies();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	AstPackage getAstPackage();

} // AstFactory
