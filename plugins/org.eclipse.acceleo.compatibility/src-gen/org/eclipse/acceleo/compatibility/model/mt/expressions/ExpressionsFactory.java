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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage
 * @generated
 */
public interface ExpressionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	ExpressionsFactory eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Call Set</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Call Set</em>'.
	 * @generated
	 */
	CallSet createCallSet();

	/**
	 * Returns a new object of class '<em>Call</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Call</em>'.
	 * @generated
	 */
	Call createCall();

	/**
	 * Returns a new object of class '<em>Not</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Not</em>'.
	 * @generated
	 */
	Not createNot();

	/**
	 * Returns a new object of class '<em>Operator</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Operator</em>'.
	 * @generated
	 */
	Operator createOperator();

	/**
	 * Returns a new object of class '<em>Parenthesis</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Parenthesis</em>'.
	 * @generated
	 */
	Parenthesis createParenthesis();

	/**
	 * Returns a new object of class '<em>String Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>String Literal</em>'.
	 * @generated
	 */
	StringLiteral createStringLiteral();

	/**
	 * Returns a new object of class '<em>Integer Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Integer Literal</em>'.
	 * @generated
	 */
	IntegerLiteral createIntegerLiteral();

	/**
	 * Returns a new object of class '<em>Double Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Double Literal</em>'.
	 * @generated
	 */
	DoubleLiteral createDoubleLiteral();

	/**
	 * Returns a new object of class '<em>Boolean Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Boolean Literal</em>'.
	 * @generated
	 */
	BooleanLiteral createBooleanLiteral();

	/**
	 * Returns a new object of class '<em>Null Literal</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Null Literal</em>'.
	 * @generated
	 */
	NullLiteral createNullLiteral();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExpressionsPackage getExpressionsPackage();

} // ExpressionsFactory
