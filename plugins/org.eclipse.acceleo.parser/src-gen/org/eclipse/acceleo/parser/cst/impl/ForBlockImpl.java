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
package org.eclipse.acceleo.parser.cst.impl;

import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>For Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl#getLoopVariable <em>Loop Variable</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl#getIterSet <em>Iter Set</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl#getAfter <em>After</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ForBlockImpl#getGuard <em>Guard</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ForBlockImpl extends BlockImpl implements ForBlock {
	/**
	 * The cached value of the '{@link #getLoopVariable() <em>Loop Variable</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLoopVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable loopVariable;

	/**
	 * The cached value of the '{@link #getIterSet() <em>Iter Set</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIterSet()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression iterSet;

	/**
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression before;

	/**
	 * The cached value of the '{@link #getEach() <em>Each</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEach()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression each;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression after;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression guard;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ForBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.FOR_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Variable getLoopVariable() {
		return loopVariable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLoopVariable(Variable newLoopVariable, NotificationChain msgs) {
		Variable oldLoopVariable = loopVariable;
		loopVariable = newLoopVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FOR_BLOCK__LOOP_VARIABLE, oldLoopVariable, newLoopVariable);
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
	public void setLoopVariable(Variable newLoopVariable) {
		if (newLoopVariable != loopVariable) {
			NotificationChain msgs = null;
			if (loopVariable != null)
				msgs = ((InternalEObject)loopVariable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__LOOP_VARIABLE, null, msgs);
			if (newLoopVariable != null)
				msgs = ((InternalEObject)newLoopVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__LOOP_VARIABLE, null, msgs);
			msgs = basicSetLoopVariable(newLoopVariable, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FOR_BLOCK__LOOP_VARIABLE,
					newLoopVariable, newLoopVariable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getIterSet() {
		return iterSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetIterSet(ModelExpression newIterSet, NotificationChain msgs) {
		ModelExpression oldIterSet = iterSet;
		iterSet = newIterSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FOR_BLOCK__ITER_SET, oldIterSet, newIterSet);
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
	public void setIterSet(ModelExpression newIterSet) {
		if (newIterSet != iterSet) {
			NotificationChain msgs = null;
			if (iterSet != null)
				msgs = ((InternalEObject)iterSet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__ITER_SET, null, msgs);
			if (newIterSet != null)
				msgs = ((InternalEObject)newIterSet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__ITER_SET, null, msgs);
			msgs = basicSetIterSet(newIterSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FOR_BLOCK__ITER_SET, newIterSet,
					newIterSet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getBefore() {
		return before;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBefore(ModelExpression newBefore, NotificationChain msgs) {
		ModelExpression oldBefore = before;
		before = newBefore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FOR_BLOCK__BEFORE, oldBefore, newBefore);
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
	public void setBefore(ModelExpression newBefore) {
		if (newBefore != before) {
			NotificationChain msgs = null;
			if (before != null)
				msgs = ((InternalEObject)before).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__BEFORE, null, msgs);
			if (newBefore != null)
				msgs = ((InternalEObject)newBefore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__BEFORE, null, msgs);
			msgs = basicSetBefore(newBefore, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FOR_BLOCK__BEFORE, newBefore,
					newBefore));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getEach() {
		return each;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEach(ModelExpression newEach, NotificationChain msgs) {
		ModelExpression oldEach = each;
		each = newEach;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FOR_BLOCK__EACH, oldEach, newEach);
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
	public void setEach(ModelExpression newEach) {
		if (newEach != each) {
			NotificationChain msgs = null;
			if (each != null)
				msgs = ((InternalEObject)each).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__EACH, null, msgs);
			if (newEach != null)
				msgs = ((InternalEObject)newEach).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__EACH, null, msgs);
			msgs = basicSetEach(newEach, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FOR_BLOCK__EACH, newEach,
					newEach));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getAfter() {
		return after;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAfter(ModelExpression newAfter, NotificationChain msgs) {
		ModelExpression oldAfter = after;
		after = newAfter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FOR_BLOCK__AFTER, oldAfter, newAfter);
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
	public void setAfter(ModelExpression newAfter) {
		if (newAfter != after) {
			NotificationChain msgs = null;
			if (after != null)
				msgs = ((InternalEObject)after).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__AFTER, null, msgs);
			if (newAfter != null)
				msgs = ((InternalEObject)newAfter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__AFTER, null, msgs);
			msgs = basicSetAfter(newAfter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FOR_BLOCK__AFTER, newAfter,
					newAfter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGuard(ModelExpression newGuard, NotificationChain msgs) {
		ModelExpression oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FOR_BLOCK__GUARD, oldGuard, newGuard);
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
	public void setGuard(ModelExpression newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FOR_BLOCK__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FOR_BLOCK__GUARD, newGuard,
					newGuard));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.FOR_BLOCK__LOOP_VARIABLE:
				return basicSetLoopVariable(null, msgs);
			case CstPackage.FOR_BLOCK__ITER_SET:
				return basicSetIterSet(null, msgs);
			case CstPackage.FOR_BLOCK__BEFORE:
				return basicSetBefore(null, msgs);
			case CstPackage.FOR_BLOCK__EACH:
				return basicSetEach(null, msgs);
			case CstPackage.FOR_BLOCK__AFTER:
				return basicSetAfter(null, msgs);
			case CstPackage.FOR_BLOCK__GUARD:
				return basicSetGuard(null, msgs);
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
			case CstPackage.FOR_BLOCK__LOOP_VARIABLE:
				return getLoopVariable();
			case CstPackage.FOR_BLOCK__ITER_SET:
				return getIterSet();
			case CstPackage.FOR_BLOCK__BEFORE:
				return getBefore();
			case CstPackage.FOR_BLOCK__EACH:
				return getEach();
			case CstPackage.FOR_BLOCK__AFTER:
				return getAfter();
			case CstPackage.FOR_BLOCK__GUARD:
				return getGuard();
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
			case CstPackage.FOR_BLOCK__LOOP_VARIABLE:
				setLoopVariable((Variable)newValue);
				return;
			case CstPackage.FOR_BLOCK__ITER_SET:
				setIterSet((ModelExpression)newValue);
				return;
			case CstPackage.FOR_BLOCK__BEFORE:
				setBefore((ModelExpression)newValue);
				return;
			case CstPackage.FOR_BLOCK__EACH:
				setEach((ModelExpression)newValue);
				return;
			case CstPackage.FOR_BLOCK__AFTER:
				setAfter((ModelExpression)newValue);
				return;
			case CstPackage.FOR_BLOCK__GUARD:
				setGuard((ModelExpression)newValue);
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
			case CstPackage.FOR_BLOCK__LOOP_VARIABLE:
				setLoopVariable((Variable)null);
				return;
			case CstPackage.FOR_BLOCK__ITER_SET:
				setIterSet((ModelExpression)null);
				return;
			case CstPackage.FOR_BLOCK__BEFORE:
				setBefore((ModelExpression)null);
				return;
			case CstPackage.FOR_BLOCK__EACH:
				setEach((ModelExpression)null);
				return;
			case CstPackage.FOR_BLOCK__AFTER:
				setAfter((ModelExpression)null);
				return;
			case CstPackage.FOR_BLOCK__GUARD:
				setGuard((ModelExpression)null);
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
			case CstPackage.FOR_BLOCK__LOOP_VARIABLE:
				return loopVariable != null;
			case CstPackage.FOR_BLOCK__ITER_SET:
				return iterSet != null;
			case CstPackage.FOR_BLOCK__BEFORE:
				return before != null;
			case CstPackage.FOR_BLOCK__EACH:
				return each != null;
			case CstPackage.FOR_BLOCK__AFTER:
				return after != null;
			case CstPackage.FOR_BLOCK__GUARD:
				return guard != null;
		}
		return super.eIsSet(featureID);
	}

} // ForBlockImpl
