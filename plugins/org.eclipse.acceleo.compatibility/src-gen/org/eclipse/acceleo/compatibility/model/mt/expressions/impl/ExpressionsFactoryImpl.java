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
package org.eclipse.acceleo.compatibility.model.mt.expressions.impl;

import org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Not;
import org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ExpressionsFactoryImpl extends EFactoryImpl implements ExpressionsFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ExpressionsFactory init() {
		try {
			ExpressionsFactory theExpressionsFactory = (ExpressionsFactory)EPackage.Registry.INSTANCE
					.getEFactory("http://www.eclipse.org/acceleo/mt/2.6.0/expressions"); //$NON-NLS-1$
			if (theExpressionsFactory != null) {
				return theExpressionsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ExpressionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExpressionsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ExpressionsPackage.CALL_SET:
				return createCallSet();
			case ExpressionsPackage.CALL:
				return createCall();
			case ExpressionsPackage.NOT:
				return createNot();
			case ExpressionsPackage.OPERATOR:
				return createOperator();
			case ExpressionsPackage.PARENTHESIS:
				return createParenthesis();
			case ExpressionsPackage.STRING_LITERAL:
				return createStringLiteral();
			case ExpressionsPackage.INTEGER_LITERAL:
				return createIntegerLiteral();
			case ExpressionsPackage.DOUBLE_LITERAL:
				return createDoubleLiteral();
			case ExpressionsPackage.BOOLEAN_LITERAL:
				return createBooleanLiteral();
			case ExpressionsPackage.NULL_LITERAL:
				return createNullLiteral();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CallSet createCallSet() {
		CallSetImpl callSet = new CallSetImpl();
		return callSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Call createCall() {
		CallImpl call = new CallImpl();
		return call;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Not createNot() {
		NotImpl not = new NotImpl();
		return not;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Operator createOperator() {
		OperatorImpl operator = new OperatorImpl();
		return operator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Parenthesis createParenthesis() {
		ParenthesisImpl parenthesis = new ParenthesisImpl();
		return parenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StringLiteral createStringLiteral() {
		StringLiteralImpl stringLiteral = new StringLiteralImpl();
		return stringLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DoubleLiteral createDoubleLiteral() {
		DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
		return doubleLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BooleanLiteral createBooleanLiteral() {
		BooleanLiteralImpl booleanLiteral = new BooleanLiteralImpl();
		return booleanLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NullLiteral createNullLiteral() {
		NullLiteralImpl nullLiteral = new NullLiteralImpl();
		return nullLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExpressionsPackage getExpressionsPackage() {
		return (ExpressionsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ExpressionsPackage getPackage() {
		return ExpressionsPackage.eINSTANCE;
	}

} // ExpressionsFactoryImpl
