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
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Call Set</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallSetImpl#getCalls <em>Calls</em>}
 * </li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CallSetImpl extends ASTNodeImpl implements CallSet {
	/**
	 * The cached value of the '{@link #getCalls() <em>Calls</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCalls()
	 * @generated
	 * @ordered
	 */
	protected EList<Call> calls;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CallSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionsPackage.Literals.CALL_SET;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Call> getCalls() {
		if (calls == null) {
			calls = new EObjectContainmentEList<Call>(Call.class, this, ExpressionsPackage.CALL_SET__CALLS);
		}
		return calls;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionsPackage.CALL_SET__CALLS:
				return ((InternalEList<?>)getCalls()).basicRemove(otherEnd, msgs);
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
			case ExpressionsPackage.CALL_SET__CALLS:
				return getCalls();
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
			case ExpressionsPackage.CALL_SET__CALLS:
				getCalls().clear();
				getCalls().addAll((Collection<? extends Call>)newValue);
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
			case ExpressionsPackage.CALL_SET__CALLS:
				getCalls().clear();
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
			case ExpressionsPackage.CALL_SET__CALLS:
				return calls != null && !calls.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // CallSetImpl
