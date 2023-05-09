/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.qmodel.impl;

import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Severity;
import org.eclipse.acceleo.query.tests.qmodel.ValidationMessage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Validation Message</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl#getSeverity <em>Severity</em>}
 * </li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl#getMessage <em>Message</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl#getStartPosition <em>Start
 * Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl#getEndPosition <em>End
 * Position</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValidationMessageImpl extends MinimalEObjectImpl.Container implements ValidationMessage {
	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final Severity SEVERITY_EDEFAULT = Severity.INFO;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected Severity severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ValidationMessageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return QmodelPackage.Literals.VALIDATION_MESSAGE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Severity getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSeverity(Severity newSeverity) {
		Severity oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QmodelPackage.VALIDATION_MESSAGE__SEVERITY,
					oldSeverity, severity));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QmodelPackage.VALIDATION_MESSAGE__MESSAGE,
					oldMessage, message));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					QmodelPackage.VALIDATION_MESSAGE__START_POSITION, oldStartPosition, startPosition));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					QmodelPackage.VALIDATION_MESSAGE__END_POSITION, oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case QmodelPackage.VALIDATION_MESSAGE__SEVERITY:
				return getSeverity();
			case QmodelPackage.VALIDATION_MESSAGE__MESSAGE:
				return getMessage();
			case QmodelPackage.VALIDATION_MESSAGE__START_POSITION:
				return getStartPosition();
			case QmodelPackage.VALIDATION_MESSAGE__END_POSITION:
				return getEndPosition();
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
			case QmodelPackage.VALIDATION_MESSAGE__SEVERITY:
				setSeverity((Severity)newValue);
				return;
			case QmodelPackage.VALIDATION_MESSAGE__MESSAGE:
				setMessage((String)newValue);
				return;
			case QmodelPackage.VALIDATION_MESSAGE__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case QmodelPackage.VALIDATION_MESSAGE__END_POSITION:
				setEndPosition((Integer)newValue);
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
			case QmodelPackage.VALIDATION_MESSAGE__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case QmodelPackage.VALIDATION_MESSAGE__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case QmodelPackage.VALIDATION_MESSAGE__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case QmodelPackage.VALIDATION_MESSAGE__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
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
			case QmodelPackage.VALIDATION_MESSAGE__SEVERITY:
				return severity != SEVERITY_EDEFAULT;
			case QmodelPackage.VALIDATION_MESSAGE__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case QmodelPackage.VALIDATION_MESSAGE__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case QmodelPackage.VALIDATION_MESSAGE__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
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
		result.append(" (severity: ");
		result.append(severity);
		result.append(", message: ");
		result.append(message);
		result.append(", startPosition: ");
		result.append(startPosition);
		result.append(", endPosition: ");
		result.append(endPosition);
		result.append(')');
		return result.toString();
	}

} // ValidationMessageImpl
