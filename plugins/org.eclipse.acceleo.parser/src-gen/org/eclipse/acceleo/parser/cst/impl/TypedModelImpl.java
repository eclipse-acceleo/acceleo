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

import java.util.Collection;

import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Typed Model</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TypedModelImpl#getStartPosition <em>Start Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TypedModelImpl#getEndPosition <em>End Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TypedModelImpl#getTakesTypesFrom <em>Takes Types From</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TypedModelImpl extends EObjectImpl implements TypedModel {
	/**
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndPosition() <em>End Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected int endPosition = END_POSITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTakesTypesFrom() <em>Takes Types From</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTakesTypesFrom()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> takesTypesFrom;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypedModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.TYPED_MODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TYPED_MODEL__START_POSITION,
					oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEndPosition(int newEndPosition) {
		int oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TYPED_MODEL__END_POSITION,
					oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<EPackage> getTakesTypesFrom() {
		if (takesTypesFrom == null) {
			takesTypesFrom = new EObjectResolvingEList<EPackage>(EPackage.class, this,
					CstPackage.TYPED_MODEL__TAKES_TYPES_FROM);
		}
		return takesTypesFrom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CstPackage.TYPED_MODEL__START_POSITION:
				return new Integer(getStartPosition());
			case CstPackage.TYPED_MODEL__END_POSITION:
				return new Integer(getEndPosition());
			case CstPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				return getTakesTypesFrom();
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
			case CstPackage.TYPED_MODEL__START_POSITION:
				setStartPosition(((Integer)newValue).intValue());
				return;
			case CstPackage.TYPED_MODEL__END_POSITION:
				setEndPosition(((Integer)newValue).intValue());
				return;
			case CstPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				getTakesTypesFrom().clear();
				getTakesTypesFrom().addAll((Collection<? extends EPackage>)newValue);
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
			case CstPackage.TYPED_MODEL__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case CstPackage.TYPED_MODEL__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case CstPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				getTakesTypesFrom().clear();
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
			case CstPackage.TYPED_MODEL__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case CstPackage.TYPED_MODEL__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case CstPackage.TYPED_MODEL__TAKES_TYPES_FROM:
				return takesTypesFrom != null && !takesTypesFrom.isEmpty();
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
		result.append(" (startPosition: "); //$NON-NLS-1$
		result.append(startPosition);
		result.append(", endPosition: "); //$NON-NLS-1$
		result.append(endPosition);
		result.append(')');
		return result.toString();
	}

} // TypedModelImpl
