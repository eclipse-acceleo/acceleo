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
package org.eclipse.acceleo.query.ast.impl;

import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Lambda</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.LambdaImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.LambdaImpl#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.LambdaImpl#getEvaluator <em>Evaluator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LambdaImpl extends LiteralImpl implements Lambda {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableDeclaration> parameters;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression;

	/**
	 * The default value of the '{@link #getEvaluator() <em>Evaluator</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEvaluator()
	 * @generated
	 * @ordered
	 */
	protected static final AstEvaluator EVALUATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEvaluator() <em>Evaluator</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getEvaluator()
	 * @generated
	 * @ordered
	 */
	protected AstEvaluator evaluator = EVALUATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LambdaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.LAMBDA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<VariableDeclaration> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<VariableDeclaration>(VariableDeclaration.class, this,
					AstPackage.LAMBDA__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AstPackage.LAMBDA__EXPRESSION, oldExpression, newExpression);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.LAMBDA__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.LAMBDA__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.LAMBDA__EXPRESSION,
					newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AstEvaluator getEvaluator() {
		return evaluator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEvaluator(AstEvaluator newEvaluator) {
		AstEvaluator oldEvaluator = evaluator;
		evaluator = newEvaluator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.LAMBDA__EVALUATOR, oldEvaluator,
					evaluator));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Object eval(Object[] args) {
		// XXX : maybe we should use a gap generator pattern here but right now it doesn't seem worth it.
		// XXX : could we have
		Map<String, Object> variables = Maps.newHashMap();
		int argc = args.length;
		for (int i = 0; i < argc; i++) {
			variables.put(getParameters().get(i).getName(), args[i]);
		}
		// TODO should we return the EvaluationResult object from here?
		return this.getEvaluator().eval(variables, this.getExpression()).getResult();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AstPackage.LAMBDA__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case AstPackage.LAMBDA__EXPRESSION:
				return basicSetExpression(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstPackage.LAMBDA__PARAMETERS:
				return getParameters();
			case AstPackage.LAMBDA__EXPRESSION:
				return getExpression();
			case AstPackage.LAMBDA__EVALUATOR:
				return getEvaluator();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AstPackage.LAMBDA__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends VariableDeclaration>)newValue);
				return;
			case AstPackage.LAMBDA__EXPRESSION:
				setExpression((Expression)newValue);
				return;
			case AstPackage.LAMBDA__EVALUATOR:
				setEvaluator((AstEvaluator)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AstPackage.LAMBDA__PARAMETERS:
				getParameters().clear();
				return;
			case AstPackage.LAMBDA__EXPRESSION:
				setExpression((Expression)null);
				return;
			case AstPackage.LAMBDA__EVALUATOR:
				setEvaluator(EVALUATOR_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AstPackage.LAMBDA__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case AstPackage.LAMBDA__EXPRESSION:
				return expression != null;
			case AstPackage.LAMBDA__EVALUATOR:
				return EVALUATOR_EDEFAULT == null ? evaluator != null : !EVALUATOR_EDEFAULT.equals(evaluator);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AstPackage.LAMBDA___EVAL__OBJECT:
				return eval((Object[])arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (evaluator: ");
		result.append(evaluator);
		result.append(')');
		return result.toString();
	}

} // LambdaImpl
