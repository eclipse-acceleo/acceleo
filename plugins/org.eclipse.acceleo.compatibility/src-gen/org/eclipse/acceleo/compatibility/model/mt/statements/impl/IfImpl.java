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
package org.eclipse.acceleo.compatibility.model.mt.statements.impl;

import java.util.Collection;

import org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.statements.If;
import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>If</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl#getCondition <em>Condition
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl#getThenStatements <em>Then
 * Statements</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl#getElseStatements <em>Else
 * Statements</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl#getElseIf <em>Else If</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class IfImpl extends ASTNodeImpl implements If {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Expression condition;

	/**
	 * The cached value of the '{@link #getThenStatements() <em>Then Statements</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getThenStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Statement> thenStatements;

	/**
	 * The cached value of the '{@link #getElseStatements() <em>Else Statements</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElseStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Statement> elseStatements;

	/**
	 * The cached value of the '{@link #getElseIf() <em>Else If</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElseIf()
	 * @generated
	 * @ordered
	 */
	protected EList<If> elseIf;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IfImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatementsPackage.Literals.IF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCondition(Expression newCondition, NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StatementsPackage.IF__CONDITION, oldCondition, newCondition);
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
	public void setCondition(Expression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- StatementsPackage.IF__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- StatementsPackage.IF__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatementsPackage.IF__CONDITION,
					newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Statement> getThenStatements() {
		if (thenStatements == null) {
			thenStatements = new EObjectContainmentEList<Statement>(Statement.class, this,
					StatementsPackage.IF__THEN_STATEMENTS);
		}
		return thenStatements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Statement> getElseStatements() {
		if (elseStatements == null) {
			elseStatements = new EObjectContainmentEList<Statement>(Statement.class, this,
					StatementsPackage.IF__ELSE_STATEMENTS);
		}
		return elseStatements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<If> getElseIf() {
		if (elseIf == null) {
			elseIf = new EObjectContainmentEList<If>(If.class, this, StatementsPackage.IF__ELSE_IF);
		}
		return elseIf;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatementsPackage.IF__CONDITION:
				return basicSetCondition(null, msgs);
			case StatementsPackage.IF__THEN_STATEMENTS:
				return ((InternalEList<?>)getThenStatements()).basicRemove(otherEnd, msgs);
			case StatementsPackage.IF__ELSE_STATEMENTS:
				return ((InternalEList<?>)getElseStatements()).basicRemove(otherEnd, msgs);
			case StatementsPackage.IF__ELSE_IF:
				return ((InternalEList<?>)getElseIf()).basicRemove(otherEnd, msgs);
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
			case StatementsPackage.IF__CONDITION:
				return getCondition();
			case StatementsPackage.IF__THEN_STATEMENTS:
				return getThenStatements();
			case StatementsPackage.IF__ELSE_STATEMENTS:
				return getElseStatements();
			case StatementsPackage.IF__ELSE_IF:
				return getElseIf();
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
			case StatementsPackage.IF__CONDITION:
				setCondition((Expression)newValue);
				return;
			case StatementsPackage.IF__THEN_STATEMENTS:
				getThenStatements().clear();
				getThenStatements().addAll((Collection<? extends Statement>)newValue);
				return;
			case StatementsPackage.IF__ELSE_STATEMENTS:
				getElseStatements().clear();
				getElseStatements().addAll((Collection<? extends Statement>)newValue);
				return;
			case StatementsPackage.IF__ELSE_IF:
				getElseIf().clear();
				getElseIf().addAll((Collection<? extends If>)newValue);
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
			case StatementsPackage.IF__CONDITION:
				setCondition((Expression)null);
				return;
			case StatementsPackage.IF__THEN_STATEMENTS:
				getThenStatements().clear();
				return;
			case StatementsPackage.IF__ELSE_STATEMENTS:
				getElseStatements().clear();
				return;
			case StatementsPackage.IF__ELSE_IF:
				getElseIf().clear();
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
			case StatementsPackage.IF__CONDITION:
				return condition != null;
			case StatementsPackage.IF__THEN_STATEMENTS:
				return thenStatements != null && !thenStatements.isEmpty();
			case StatementsPackage.IF__ELSE_STATEMENTS:
				return elseStatements != null && !elseStatements.isEmpty();
			case StatementsPackage.IF__ELSE_IF:
				return elseIf != null && !elseIf.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // IfImpl
