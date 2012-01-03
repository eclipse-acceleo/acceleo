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

import java.util.Collection;

import org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Operator</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.OperatorImpl#getOperator <em>
 * Operator </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.OperatorImpl#getOperands <em>
 * Operands </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class OperatorImpl extends ASTNodeImpl implements Operator {
	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final String OPERATOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected String operator = OPERATOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOperands() <em>Operands</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperands()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> operands;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected OperatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionsPackage.Literals.OPERATOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOperator(String newOperator) {
		String oldOperator = operator;
		operator = newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionsPackage.OPERATOR__OPERATOR,
					oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expression> getOperands() {
		if (operands == null) {
			operands = new EObjectContainmentEList<Expression>(Expression.class, this,
					ExpressionsPackage.OPERATOR__OPERANDS);
		}
		return operands;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionsPackage.OPERATOR__OPERANDS:
				return ((InternalEList<?>)getOperands()).basicRemove(otherEnd, msgs);
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
			case ExpressionsPackage.OPERATOR__OPERATOR:
				return getOperator();
			case ExpressionsPackage.OPERATOR__OPERANDS:
				return getOperands();
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
			case ExpressionsPackage.OPERATOR__OPERATOR:
				setOperator((String)newValue);
				return;
			case ExpressionsPackage.OPERATOR__OPERANDS:
				getOperands().clear();
				getOperands().addAll((Collection<? extends Expression>)newValue);
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
			case ExpressionsPackage.OPERATOR__OPERATOR:
				setOperator(OPERATOR_EDEFAULT);
				return;
			case ExpressionsPackage.OPERATOR__OPERANDS:
				getOperands().clear();
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
			case ExpressionsPackage.OPERATOR__OPERATOR:
				return OPERATOR_EDEFAULT == null ? operator != null : !OPERATOR_EDEFAULT.equals(operator);
			case ExpressionsPackage.OPERATOR__OPERANDS:
				return operands != null && !operands.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (operator: "); //$NON-NLS-1$
		result.append(operator);
		result.append(')');
		return result.toString();
	}

} // OperatorImpl
