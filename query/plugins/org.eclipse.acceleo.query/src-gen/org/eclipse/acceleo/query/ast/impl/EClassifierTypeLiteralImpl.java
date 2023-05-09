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
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>EClassifier Type Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.EClassifierTypeLiteralImpl#getEPackageName <em>EPackage
 * Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.EClassifierTypeLiteralImpl#getEClassifierName <em>EClassifier
 * Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EClassifierTypeLiteralImpl extends LiteralImpl implements EClassifierTypeLiteral {
	/**
	 * The default value of the '{@link #getEPackageName() <em>EPackage Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String EPACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEPackageName() <em>EPackage Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEPackageName()
	 * @generated
	 * @ordered
	 */
	protected String ePackageName = EPACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEClassifierName() <em>EClassifier Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEClassifierName()
	 * @generated
	 * @ordered
	 */
	protected static final String ECLASSIFIER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEClassifierName() <em>EClassifier Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEClassifierName()
	 * @generated
	 * @ordered
	 */
	protected String eClassifierName = ECLASSIFIER_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EClassifierTypeLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ECLASSIFIER_TYPE_LITERAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getEPackageName() {
		return ePackageName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEPackageName(String newEPackageName) {
		String oldEPackageName = ePackageName;
		ePackageName = newEPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME, oldEPackageName, ePackageName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getEClassifierName() {
		return eClassifierName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEClassifierName(String newEClassifierName) {
		String oldEClassifierName = eClassifierName;
		eClassifierName = newEClassifierName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME, oldEClassifierName,
					eClassifierName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				return getEPackageName();
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				return getEClassifierName();
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
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				setEPackageName((String)newValue);
				return;
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				setEClassifierName((String)newValue);
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
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				setEPackageName(EPACKAGE_NAME_EDEFAULT);
				return;
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				setEClassifierName(ECLASSIFIER_NAME_EDEFAULT);
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
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				return EPACKAGE_NAME_EDEFAULT == null ? ePackageName != null
						: !EPACKAGE_NAME_EDEFAULT.equals(ePackageName);
			case AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				return ECLASSIFIER_NAME_EDEFAULT == null ? eClassifierName != null
						: !ECLASSIFIER_NAME_EDEFAULT.equals(eClassifierName);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (ePackageName: ");
		result.append(ePackageName);
		result.append(", eClassifierName: ");
		result.append(eClassifierName);
		result.append(')');
		return result.toString();
	}

} // EClassifierTypeLiteralImpl
