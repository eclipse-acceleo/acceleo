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
package org.eclipse.acceleo.model.mtl.impl;

import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>For Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl#getLoopVariable <em>Loop Variable</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl#getIterSet <em>Iter Set</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl#getAfter <em>After</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ForBlockImpl#getGuard <em>Guard</em>}</li>
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
	protected OCLExpression iterSet;

	/**
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression before;

	/**
	 * The cached value of the '{@link #getEach() <em>Each</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEach()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression each;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression after;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression guard;

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
		return MtlPackage.Literals.FOR_BLOCK;
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
					MtlPackage.FOR_BLOCK__LOOP_VARIABLE, oldLoopVariable, newLoopVariable);
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
						- MtlPackage.FOR_BLOCK__LOOP_VARIABLE, null, msgs);
			if (newLoopVariable != null)
				msgs = ((InternalEObject)newLoopVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__LOOP_VARIABLE, null, msgs);
			msgs = basicSetLoopVariable(newLoopVariable, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.FOR_BLOCK__LOOP_VARIABLE,
					newLoopVariable, newLoopVariable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getIterSet() {
		return iterSet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetIterSet(OCLExpression newIterSet, NotificationChain msgs) {
		OCLExpression oldIterSet = iterSet;
		iterSet = newIterSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.FOR_BLOCK__ITER_SET, oldIterSet, newIterSet);
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
	public void setIterSet(OCLExpression newIterSet) {
		if (newIterSet != iterSet) {
			NotificationChain msgs = null;
			if (iterSet != null)
				msgs = ((InternalEObject)iterSet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__ITER_SET, null, msgs);
			if (newIterSet != null)
				msgs = ((InternalEObject)newIterSet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__ITER_SET, null, msgs);
			msgs = basicSetIterSet(newIterSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.FOR_BLOCK__ITER_SET, newIterSet,
					newIterSet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getBefore() {
		return before;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBefore(OCLExpression newBefore, NotificationChain msgs) {
		OCLExpression oldBefore = before;
		before = newBefore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.FOR_BLOCK__BEFORE, oldBefore, newBefore);
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
	public void setBefore(OCLExpression newBefore) {
		if (newBefore != before) {
			NotificationChain msgs = null;
			if (before != null)
				msgs = ((InternalEObject)before).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__BEFORE, null, msgs);
			if (newBefore != null)
				msgs = ((InternalEObject)newBefore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__BEFORE, null, msgs);
			msgs = basicSetBefore(newBefore, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.FOR_BLOCK__BEFORE, newBefore,
					newBefore));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getEach() {
		return each;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEach(OCLExpression newEach, NotificationChain msgs) {
		OCLExpression oldEach = each;
		each = newEach;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.FOR_BLOCK__EACH, oldEach, newEach);
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
	public void setEach(OCLExpression newEach) {
		if (newEach != each) {
			NotificationChain msgs = null;
			if (each != null)
				msgs = ((InternalEObject)each).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__EACH, null, msgs);
			if (newEach != null)
				msgs = ((InternalEObject)newEach).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__EACH, null, msgs);
			msgs = basicSetEach(newEach, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.FOR_BLOCK__EACH, newEach,
					newEach));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getAfter() {
		return after;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAfter(OCLExpression newAfter, NotificationChain msgs) {
		OCLExpression oldAfter = after;
		after = newAfter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.FOR_BLOCK__AFTER, oldAfter, newAfter);
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
	public void setAfter(OCLExpression newAfter) {
		if (newAfter != after) {
			NotificationChain msgs = null;
			if (after != null)
				msgs = ((InternalEObject)after).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__AFTER, null, msgs);
			if (newAfter != null)
				msgs = ((InternalEObject)newAfter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__AFTER, null, msgs);
			msgs = basicSetAfter(newAfter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.FOR_BLOCK__AFTER, newAfter,
					newAfter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGuard(OCLExpression newGuard, NotificationChain msgs) {
		OCLExpression oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.FOR_BLOCK__GUARD, oldGuard, newGuard);
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
	public void setGuard(OCLExpression newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.FOR_BLOCK__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.FOR_BLOCK__GUARD, newGuard,
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
			case MtlPackage.FOR_BLOCK__LOOP_VARIABLE:
				return basicSetLoopVariable(null, msgs);
			case MtlPackage.FOR_BLOCK__ITER_SET:
				return basicSetIterSet(null, msgs);
			case MtlPackage.FOR_BLOCK__BEFORE:
				return basicSetBefore(null, msgs);
			case MtlPackage.FOR_BLOCK__EACH:
				return basicSetEach(null, msgs);
			case MtlPackage.FOR_BLOCK__AFTER:
				return basicSetAfter(null, msgs);
			case MtlPackage.FOR_BLOCK__GUARD:
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
			case MtlPackage.FOR_BLOCK__LOOP_VARIABLE:
				return getLoopVariable();
			case MtlPackage.FOR_BLOCK__ITER_SET:
				return getIterSet();
			case MtlPackage.FOR_BLOCK__BEFORE:
				return getBefore();
			case MtlPackage.FOR_BLOCK__EACH:
				return getEach();
			case MtlPackage.FOR_BLOCK__AFTER:
				return getAfter();
			case MtlPackage.FOR_BLOCK__GUARD:
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
			case MtlPackage.FOR_BLOCK__LOOP_VARIABLE:
				setLoopVariable((Variable)newValue);
				return;
			case MtlPackage.FOR_BLOCK__ITER_SET:
				setIterSet((OCLExpression)newValue);
				return;
			case MtlPackage.FOR_BLOCK__BEFORE:
				setBefore((OCLExpression)newValue);
				return;
			case MtlPackage.FOR_BLOCK__EACH:
				setEach((OCLExpression)newValue);
				return;
			case MtlPackage.FOR_BLOCK__AFTER:
				setAfter((OCLExpression)newValue);
				return;
			case MtlPackage.FOR_BLOCK__GUARD:
				setGuard((OCLExpression)newValue);
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
			case MtlPackage.FOR_BLOCK__LOOP_VARIABLE:
				setLoopVariable((Variable)null);
				return;
			case MtlPackage.FOR_BLOCK__ITER_SET:
				setIterSet((OCLExpression)null);
				return;
			case MtlPackage.FOR_BLOCK__BEFORE:
				setBefore((OCLExpression)null);
				return;
			case MtlPackage.FOR_BLOCK__EACH:
				setEach((OCLExpression)null);
				return;
			case MtlPackage.FOR_BLOCK__AFTER:
				setAfter((OCLExpression)null);
				return;
			case MtlPackage.FOR_BLOCK__GUARD:
				setGuard((OCLExpression)null);
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
			case MtlPackage.FOR_BLOCK__LOOP_VARIABLE:
				return loopVariable != null;
			case MtlPackage.FOR_BLOCK__ITER_SET:
				return iterSet != null;
			case MtlPackage.FOR_BLOCK__BEFORE:
				return before != null;
			case MtlPackage.FOR_BLOCK__EACH:
				return each != null;
			case MtlPackage.FOR_BLOCK__AFTER:
				return after != null;
			case MtlPackage.FOR_BLOCK__GUARD:
				return guard != null;
		}
		return super.eIsSet(featureID);
	}

} // ForBlockImpl
