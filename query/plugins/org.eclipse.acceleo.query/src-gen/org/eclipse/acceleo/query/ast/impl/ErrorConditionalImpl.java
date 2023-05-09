/**
 *  Copyright (c) 2015, 2021 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast.impl;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Conditional</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl#getPredicate <em>Predicate</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl#getTrueBranch <em>True Branch</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorConditionalImpl#getFalseBranch <em>False
 * Branch</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorConditionalImpl extends ExpressionImpl implements ErrorConditional {
	/**
	 * The cached value of the '{@link #getPredicate() <em>Predicate</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPredicate()
	 * @generated
	 * @ordered
	 */
	protected Expression predicate;

	/**
	 * The cached value of the '{@link #getTrueBranch() <em>True Branch</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrueBranch()
	 * @generated
	 * @ordered
	 */
	protected Expression trueBranch;

	/**
	 * The cached value of the '{@link #getFalseBranch() <em>False Branch</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFalseBranch()
	 * @generated
	 * @ordered
	 */
	protected Expression falseBranch;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorConditionalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ERROR_CONDITIONAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getPredicate() {
		return predicate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPredicate(Expression newPredicate, NotificationChain msgs) {
		Expression oldPredicate = predicate;
		predicate = newPredicate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_CONDITIONAL__PREDICATE, oldPredicate, newPredicate);
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
	@Override
	public void setPredicate(Expression newPredicate) {
		if (newPredicate != predicate) {
			NotificationChain msgs = null;
			if (predicate != null)
				msgs = ((InternalEObject)predicate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_CONDITIONAL__PREDICATE, null, msgs);
			if (newPredicate != null)
				msgs = ((InternalEObject)newPredicate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_CONDITIONAL__PREDICATE, null, msgs);
			msgs = basicSetPredicate(newPredicate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_CONDITIONAL__PREDICATE,
					newPredicate, newPredicate));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getTrueBranch() {
		return trueBranch;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetTrueBranch(Expression newTrueBranch, NotificationChain msgs) {
		Expression oldTrueBranch = trueBranch;
		trueBranch = newTrueBranch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH, oldTrueBranch, newTrueBranch);
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
	@Override
	public void setTrueBranch(Expression newTrueBranch) {
		if (newTrueBranch != trueBranch) {
			NotificationChain msgs = null;
			if (trueBranch != null)
				msgs = ((InternalEObject)trueBranch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH, null, msgs);
			if (newTrueBranch != null)
				msgs = ((InternalEObject)newTrueBranch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH, null, msgs);
			msgs = basicSetTrueBranch(newTrueBranch, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH,
					newTrueBranch, newTrueBranch));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getFalseBranch() {
		return falseBranch;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFalseBranch(Expression newFalseBranch, NotificationChain msgs) {
		Expression oldFalseBranch = falseBranch;
		falseBranch = newFalseBranch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH, oldFalseBranch, newFalseBranch);
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
	@Override
	public void setFalseBranch(Expression newFalseBranch) {
		if (newFalseBranch != falseBranch) {
			NotificationChain msgs = null;
			if (falseBranch != null)
				msgs = ((InternalEObject)falseBranch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH, null, msgs);
			if (newFalseBranch != null)
				msgs = ((InternalEObject)newFalseBranch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH, null, msgs);
			msgs = basicSetFalseBranch(newFalseBranch, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH,
					newFalseBranch, newFalseBranch));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AstPackage.ERROR_CONDITIONAL__PREDICATE:
				return basicSetPredicate(null, msgs);
			case AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH:
				return basicSetTrueBranch(null, msgs);
			case AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH:
				return basicSetFalseBranch(null, msgs);
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
			case AstPackage.ERROR_CONDITIONAL__PREDICATE:
				return getPredicate();
			case AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH:
				return getTrueBranch();
			case AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH:
				return getFalseBranch();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AstPackage.ERROR_CONDITIONAL__PREDICATE:
				setPredicate((Expression)newValue);
				return;
			case AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH:
				setTrueBranch((Expression)newValue);
				return;
			case AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH:
				setFalseBranch((Expression)newValue);
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
			case AstPackage.ERROR_CONDITIONAL__PREDICATE:
				setPredicate((Expression)null);
				return;
			case AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH:
				setTrueBranch((Expression)null);
				return;
			case AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH:
				setFalseBranch((Expression)null);
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
			case AstPackage.ERROR_CONDITIONAL__PREDICATE:
				return predicate != null;
			case AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH:
				return trueBranch != null;
			case AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH:
				return falseBranch != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Conditional.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_CONDITIONAL__PREDICATE:
					return AstPackage.CONDITIONAL__PREDICATE;
				case AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH:
					return AstPackage.CONDITIONAL__TRUE_BRANCH;
				case AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH:
					return AstPackage.CONDITIONAL__FALSE_BRANCH;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Conditional.class) {
			switch (baseFeatureID) {
				case AstPackage.CONDITIONAL__PREDICATE:
					return AstPackage.ERROR_CONDITIONAL__PREDICATE;
				case AstPackage.CONDITIONAL__TRUE_BRANCH:
					return AstPackage.ERROR_CONDITIONAL__TRUE_BRANCH;
				case AstPackage.CONDITIONAL__FALSE_BRANCH:
					return AstPackage.ERROR_CONDITIONAL__FALSE_BRANCH;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // ErrorConditionalImpl
