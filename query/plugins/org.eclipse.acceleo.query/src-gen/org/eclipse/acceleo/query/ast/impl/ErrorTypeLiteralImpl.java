/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Type Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorTypeLiteralImpl#getValue <em>Value</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorTypeLiteralImpl#getSegments <em>Segments</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorTypeLiteralImpl extends MinimalEObjectImpl.Container implements ErrorTypeLiteral {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Object value = VALUE_EDEFAULT;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorTypeLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ERROR_TYPE_LITERAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValue(Object newValue) {
		Object oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_TYPE_LITERAL__VALUE,
					oldValue, value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getSegments() {
		if (segments == null) {
			segments = new EDataTypeUniqueEList<String>(String.class, this,
					AstPackage.ERROR_TYPE_LITERAL__SEGMENTS);
		}
		return segments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstPackage.ERROR_TYPE_LITERAL__VALUE:
				return getValue();
			case AstPackage.ERROR_TYPE_LITERAL__SEGMENTS:
				return getSegments();
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
			case AstPackage.ERROR_TYPE_LITERAL__VALUE:
				setValue(newValue);
				return;
			case AstPackage.ERROR_TYPE_LITERAL__SEGMENTS:
				getSegments().clear();
				getSegments().addAll((Collection<? extends String>)newValue);
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
			case AstPackage.ERROR_TYPE_LITERAL__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case AstPackage.ERROR_TYPE_LITERAL__SEGMENTS:
				getSegments().clear();
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
			case AstPackage.ERROR_TYPE_LITERAL__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case AstPackage.ERROR_TYPE_LITERAL__SEGMENTS:
				return segments != null && !segments.isEmpty();
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
		if (baseClass == Expression.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == Literal.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == TypeLiteral.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_TYPE_LITERAL__VALUE:
					return AstPackage.TYPE_LITERAL__VALUE;
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
		if (baseClass == Expression.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == Literal.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == TypeLiteral.class) {
			switch (baseFeatureID) {
				case AstPackage.TYPE_LITERAL__VALUE:
					return AstPackage.ERROR_TYPE_LITERAL__VALUE;
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
		result.append(" (value: ");
		result.append(value);
		result.append(", segments: ");
		result.append(segments);
		result.append(')');
		return result.toString();
	}

} // ErrorTypeLiteralImpl
