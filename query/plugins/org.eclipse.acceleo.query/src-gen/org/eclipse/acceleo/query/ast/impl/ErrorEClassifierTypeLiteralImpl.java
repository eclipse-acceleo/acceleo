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
import org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Error EClassifier Type
 * Literal</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEClassifierTypeLiteralImpl#getEPackageName <em>EPackage
 * Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEClassifierTypeLiteralImpl#getEClassifierName
 * <em>EClassifier Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEClassifierTypeLiteralImpl#isMissingColon <em>Missing
 * Colon</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorEClassifierTypeLiteralImpl extends ExpressionImpl implements ErrorEClassifierTypeLiteral {
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
	protected ErrorEClassifierTypeLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ERROR_ECLASSIFIER_TYPE_LITERAL;
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
					AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME, oldEPackageName, ePackageName));
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
					AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME, oldEClassifierName,
					eClassifierName));
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
					AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__MISSING_COLON, oldMissingColon, missingColon));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				return getEPackageName();
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				return getEClassifierName();
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__MISSING_COLON:
				return isMissingColon();
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
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				setEPackageName((String)newValue);
				return;
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				setEClassifierName((String)newValue);
				return;
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__MISSING_COLON:
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
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				setEPackageName(EPACKAGE_NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				setEClassifierName(ECLASSIFIER_NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__MISSING_COLON:
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
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
				return EPACKAGE_NAME_EDEFAULT == null ? ePackageName != null
						: !EPACKAGE_NAME_EDEFAULT.equals(ePackageName);
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
				return ECLASSIFIER_NAME_EDEFAULT == null ? eClassifierName != null
						: !ECLASSIFIER_NAME_EDEFAULT.equals(eClassifierName);
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__MISSING_COLON:
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
		if (baseClass == TypeLiteral.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == EClassifierTypeLiteral.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
					return AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME;
				case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
					return AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME;
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
		if (baseClass == TypeLiteral.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == EClassifierTypeLiteral.class) {
			switch (baseFeatureID) {
				case AstPackage.ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME:
					return AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME;
				case AstPackage.ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME:
					return AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME;
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
		result.append(", eClassifierName: ");
		result.append(eClassifierName);
		result.append(", missingColon: ");
		result.append(missingColon);
		result.append(')');
		return result.toString();
	}

} // ErrorEClassifierTypeLiteralImpl
