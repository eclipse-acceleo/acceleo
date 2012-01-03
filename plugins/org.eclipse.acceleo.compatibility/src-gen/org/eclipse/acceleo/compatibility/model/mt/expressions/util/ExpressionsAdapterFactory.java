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
package org.eclipse.acceleo.compatibility.model.mt.expressions.util;

import org.eclipse.acceleo.compatibility.model.mt.core.ASTNode;
import org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Literal;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Not;
import org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage
 * @generated
 */
public class ExpressionsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ExpressionsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExpressionsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ExpressionsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected ExpressionsSwitch<Adapter> modelSwitch = new ExpressionsSwitch<Adapter>() {
		@Override
		public Adapter caseExpression(Expression object) {
			return createExpressionAdapter();
		}

		@Override
		public Adapter caseCallSet(CallSet object) {
			return createCallSetAdapter();
		}

		@Override
		public Adapter caseCall(Call object) {
			return createCallAdapter();
		}

		@Override
		public Adapter caseNot(Not object) {
			return createNotAdapter();
		}

		@Override
		public Adapter caseOperator(Operator object) {
			return createOperatorAdapter();
		}

		@Override
		public Adapter caseParenthesis(Parenthesis object) {
			return createParenthesisAdapter();
		}

		@Override
		public Adapter caseLiteral(Literal object) {
			return createLiteralAdapter();
		}

		@Override
		public Adapter caseStringLiteral(StringLiteral object) {
			return createStringLiteralAdapter();
		}

		@Override
		public Adapter caseIntegerLiteral(IntegerLiteral object) {
			return createIntegerLiteralAdapter();
		}

		@Override
		public Adapter caseDoubleLiteral(DoubleLiteral object) {
			return createDoubleLiteralAdapter();
		}

		@Override
		public Adapter caseBooleanLiteral(BooleanLiteral object) {
			return createBooleanLiteralAdapter();
		}

		@Override
		public Adapter caseNullLiteral(NullLiteral object) {
			return createNullLiteralAdapter();
		}

		@Override
		public Adapter caseASTNode(ASTNode object) {
			return createASTNodeAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Expression <em>Expression</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet <em>Call Set</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet
	 * @generated
	 */
	public Adapter createCallSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call <em>Call</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Call
	 * @generated
	 */
	public Adapter createCallAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Not <em>Not</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Not
	 * @generated
	 */
	public Adapter createNotAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Operator <em>Operator</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Operator
	 * @generated
	 */
	public Adapter createOperatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis <em>Parenthesis</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis
	 * @generated
	 */
	public Adapter createParenthesisAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Literal <em>Literal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.Literal
	 * @generated
	 */
	public Adapter createLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral
	 * @generated
	 */
	public Adapter createStringLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral <em>Integer Literal</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral
	 * @generated
	 */
	public Adapter createIntegerLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral
	 * @generated
	 */
	public Adapter createDoubleLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral <em>Boolean Literal</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral
	 * @generated
	 */
	public Adapter createBooleanLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral <em>Null Literal</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral
	 * @generated
	 */
	public Adapter createNullLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode <em>AST Node</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ASTNode
	 * @generated
	 */
	public Adapter createASTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // ExpressionsAdapterFactory
