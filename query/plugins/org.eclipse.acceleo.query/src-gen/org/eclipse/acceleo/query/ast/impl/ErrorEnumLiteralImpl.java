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
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Enum Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#getEPackageName <em>EPackage
 * Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#getEEnumName <em>EEnum Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#getEEnumLiteralName <em>EEnum Literal
 * Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#isMissingColon <em>Missing
 * Colon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorEnumLiteralImpl extends ExpressionImpl implements ErrorEnumLiteral {
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
	 * The default value of the '{@link #getEEnumName() <em>EEnum Name</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEEnumName()
	 * @generated
	 * @ordered
	 */
	protected static final String EENUM_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEEnumName() <em>EEnum Name</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEEnumName()
	 * @generated
	 * @ordered
	 */
	protected String eEnumName = EENUM_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEEnumLiteralName() <em>EEnum Literal Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEEnumLiteralName()
	 * @generated
	 * @ordered
	 */
	protected static final String EENUM_LITERAL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEEnumLiteralName() <em>EEnum Literal Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEEnumLiteralName()
	 * @generated
	 * @ordered
	 */
	protected String eEnumLiteralName = EENUM_LITERAL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingColon() <em>Missing Colon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isMissingColon()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_COLON_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingColon() <em>Missing Colon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isMissingColon()
	 * @generated
	 * @ordered
	 */
	protected boolean missingColon = MISSING_COLON_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorEnumLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ERROR_ENUM_LITERAL;
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
					AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME, oldEPackageName, ePackageName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getEEnumName() {
		return eEnumName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEEnumName(String newEEnumName) {
		String oldEEnumName = eEnumName;
		eEnumName = newEEnumName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME,
					oldEEnumName, eEnumName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getEEnumLiteralName() {
		return eEnumLiteralName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEEnumLiteralName(String newEEnumLiteralName) {
		String oldEEnumLiteralName = eEnumLiteralName;
		eEnumLiteralName = newEEnumLiteralName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME, oldEEnumLiteralName,
					eEnumLiteralName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isMissingColon() {
		return missingColon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingColon(boolean newMissingColon) {
		boolean oldMissingColon = missingColon;
		missingColon = newMissingColon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_ENUM_LITERAL__MISSING_COLON, oldMissingColon, missingColon));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME:
				return getEPackageName();
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME:
				return getEEnumName();
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME:
				return getEEnumLiteralName();
			case AstPackage.ERROR_ENUM_LITERAL__MISSING_COLON:
				return isMissingColon();
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
			case AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME:
				setEPackageName((String)newValue);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME:
				setEEnumName((String)newValue);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME:
				setEEnumLiteralName((String)newValue);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__MISSING_COLON:
				setMissingColon((Boolean)newValue);
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
			case AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME:
				setEPackageName(EPACKAGE_NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME:
				setEEnumName(EENUM_NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME:
				setEEnumLiteralName(EENUM_LITERAL_NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__MISSING_COLON:
				setMissingColon(MISSING_COLON_EDEFAULT);
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
			case AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME:
				return EPACKAGE_NAME_EDEFAULT == null ? ePackageName != null
						: !EPACKAGE_NAME_EDEFAULT.equals(ePackageName);
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME:
				return EENUM_NAME_EDEFAULT == null ? eEnumName != null
						: !EENUM_NAME_EDEFAULT.equals(eEnumName);
			case AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME:
				return EENUM_LITERAL_NAME_EDEFAULT == null ? eEnumLiteralName != null
						: !EENUM_LITERAL_NAME_EDEFAULT.equals(eEnumLiteralName);
			case AstPackage.ERROR_ENUM_LITERAL__MISSING_COLON:
				return missingColon != MISSING_COLON_EDEFAULT;
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
		if (baseClass == Literal.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == EnumLiteral.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME:
					return AstPackage.ENUM_LITERAL__EPACKAGE_NAME;
				case AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME:
					return AstPackage.ENUM_LITERAL__EENUM_NAME;
				case AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME:
					return AstPackage.ENUM_LITERAL__EENUM_LITERAL_NAME;
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
		if (baseClass == Literal.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == EnumLiteral.class) {
			switch (baseFeatureID) {
				case AstPackage.ENUM_LITERAL__EPACKAGE_NAME:
					return AstPackage.ERROR_ENUM_LITERAL__EPACKAGE_NAME;
				case AstPackage.ENUM_LITERAL__EENUM_NAME:
					return AstPackage.ERROR_ENUM_LITERAL__EENUM_NAME;
				case AstPackage.ENUM_LITERAL__EENUM_LITERAL_NAME:
					return AstPackage.ERROR_ENUM_LITERAL__EENUM_LITERAL_NAME;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", eEnumName: ");
		result.append(eEnumName);
		result.append(", eEnumLiteralName: ");
		result.append(eEnumLiteralName);
		result.append(", missingColon: ");
		result.append(missingColon);
		result.append(')');
		return result.toString();
	}

} // ErrorEnumLiteralImpl
