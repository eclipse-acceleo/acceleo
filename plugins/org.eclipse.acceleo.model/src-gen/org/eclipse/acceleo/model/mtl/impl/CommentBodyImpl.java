/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.impl;

import org.eclipse.acceleo.model.mtl.CommentBody;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Comment Body</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl#getStartPosition <em>Start Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl#getEndPosition <em>End Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.CommentBodyImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CommentBodyImpl extends EObjectImpl implements CommentBody {
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
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CommentBodyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.COMMENT_BODY;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.COMMENT_BODY__START_POSITION,
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.COMMENT_BODY__END_POSITION,
					oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.COMMENT_BODY__VALUE, oldValue,
					value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MtlPackage.COMMENT_BODY__START_POSITION:
				return getStartPosition();
			case MtlPackage.COMMENT_BODY__END_POSITION:
				return getEndPosition();
			case MtlPackage.COMMENT_BODY__VALUE:
				return getValue();
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
			case MtlPackage.COMMENT_BODY__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case MtlPackage.COMMENT_BODY__END_POSITION:
				setEndPosition((Integer)newValue);
				return;
			case MtlPackage.COMMENT_BODY__VALUE:
				setValue((String)newValue);
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
			case MtlPackage.COMMENT_BODY__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case MtlPackage.COMMENT_BODY__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case MtlPackage.COMMENT_BODY__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case MtlPackage.COMMENT_BODY__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case MtlPackage.COMMENT_BODY__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case MtlPackage.COMMENT_BODY__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(", value: "); //$NON-NLS-1$
		result.append(value);
		result.append(')');
		return result.toString();
	}

} // CommentBodyImpl
