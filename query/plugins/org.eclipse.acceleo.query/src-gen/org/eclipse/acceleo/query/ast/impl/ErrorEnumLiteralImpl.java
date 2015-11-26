/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Enum Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#getLiteral <em>Literal</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#getSegments <em>Segments</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorEnumLiteralImpl#isMissingColon <em>Missing Colon</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorEnumLiteralImpl extends ExpressionImpl implements ErrorEnumLiteral {
	/**
	 * The cached value of the '{@link #getLiteral() <em>Literal</em>}' reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected EEnumLiteral literal;

	/**
	 * The cached value of the '{@link #getSegments() <em>Segments</em>}' attribute list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getSegments()
	 * @generated
	 * @ordered
	 */
	protected EList<String> segments;

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
	public EEnumLiteral getLiteral() {
		if (literal != null && literal.eIsProxy()) {
			InternalEObject oldLiteral = (InternalEObject)literal;
			literal = (EEnumLiteral)eResolveProxy(oldLiteral);
			if (literal != oldLiteral) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AstPackage.ERROR_ENUM_LITERAL__LITERAL, oldLiteral, literal));
			}
		}
		return literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnumLiteral basicGetLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLiteral(EEnumLiteral newLiteral) {
		EEnumLiteral oldLiteral = literal;
		literal = newLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_ENUM_LITERAL__LITERAL,
					oldLiteral, literal));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getSegments() {
		if (segments == null) {
			segments = new EDataTypeUniqueEList<String>(String.class, this,
					AstPackage.ERROR_ENUM_LITERAL__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isMissingColon() {
		return missingColon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
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
			case AstPackage.ERROR_ENUM_LITERAL__LITERAL:
				if (resolve)
					return getLiteral();
				return basicGetLiteral();
			case AstPackage.ERROR_ENUM_LITERAL__SEGMENTS:
				return getSegments();
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
			case AstPackage.ERROR_ENUM_LITERAL__LITERAL:
				setLiteral((EEnumLiteral)newValue);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__SEGMENTS:
				getSegments().clear();
				getSegments().addAll((Collection<? extends String>)newValue);
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
			case AstPackage.ERROR_ENUM_LITERAL__LITERAL:
				setLiteral((EEnumLiteral)null);
				return;
			case AstPackage.ERROR_ENUM_LITERAL__SEGMENTS:
				getSegments().clear();
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
			case AstPackage.ERROR_ENUM_LITERAL__LITERAL:
				return literal != null;
			case AstPackage.ERROR_ENUM_LITERAL__SEGMENTS:
				return segments != null && !segments.isEmpty();
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
				case AstPackage.ERROR_ENUM_LITERAL__LITERAL:
					return AstPackage.ENUM_LITERAL__LITERAL;
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
				case AstPackage.ENUM_LITERAL__LITERAL:
					return AstPackage.ERROR_ENUM_LITERAL__LITERAL;
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (segments: ");
		result.append(segments);
		result.append(", missingColon: ");
		result.append(missingColon);
		result.append(')');
		return result.toString();
	}

} // ErrorEnumLiteralImpl
