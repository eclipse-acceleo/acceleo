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
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Protected Area Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ProtectedAreaBlockImpl#getMarker <em>Marker</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ProtectedAreaBlockImpl extends BlockImpl implements ProtectedAreaBlock {
	/**
	 * The cached value of the '{@link #getMarker() <em>Marker</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMarker()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression marker;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProtectedAreaBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.PROTECTED_AREA_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getMarker() {
		return marker;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMarker(ModelExpression newMarker, NotificationChain msgs) {
		ModelExpression oldMarker = marker;
		marker = newMarker;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.PROTECTED_AREA_BLOCK__MARKER, oldMarker, newMarker);
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
	public void setMarker(ModelExpression newMarker) {
		if (newMarker != marker) {
			NotificationChain msgs = null;
			if (marker != null)
				msgs = ((InternalEObject)marker).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.PROTECTED_AREA_BLOCK__MARKER, null, msgs);
			if (newMarker != null)
				msgs = ((InternalEObject)newMarker).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.PROTECTED_AREA_BLOCK__MARKER, null, msgs);
			msgs = basicSetMarker(newMarker, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.PROTECTED_AREA_BLOCK__MARKER,
					newMarker, newMarker));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.PROTECTED_AREA_BLOCK__MARKER:
				return basicSetMarker(null, msgs);
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
			case CstPackage.PROTECTED_AREA_BLOCK__MARKER:
				return getMarker();
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
			case CstPackage.PROTECTED_AREA_BLOCK__MARKER:
				setMarker((ModelExpression)newValue);
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
			case CstPackage.PROTECTED_AREA_BLOCK__MARKER:
				setMarker((ModelExpression)null);
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
			case CstPackage.PROTECTED_AREA_BLOCK__MARKER:
				return marker != null;
		}
		return super.eIsSet(featureID);
	}

} // ProtectedAreaBlockImpl
