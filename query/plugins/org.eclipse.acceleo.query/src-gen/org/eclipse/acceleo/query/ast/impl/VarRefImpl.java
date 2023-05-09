/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast.impl;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Var Ref</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.VarRefImpl#getVariableName <em>Variable Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VarRefImpl extends ExpressionImpl implements VarRef {
	/**
	 * The default value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVariableName()
	 * @generated
	 * @ordered
	 */
	protected static final String VARIABLE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVariableName() <em>Variable Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVariableName()
	 * @generated
	 * @ordered
	 */
	protected String variableName = VARIABLE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VarRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.VAR_REF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVariableName() {
		return variableName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVariableName(String newVariableName) {
		String oldVariableName = variableName;
		variableName = newVariableName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.VAR_REF__VARIABLE_NAME,
					oldVariableName, variableName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AstPackage.VAR_REF__VARIABLE_NAME:
				return getVariableName();
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
			case AstPackage.VAR_REF__VARIABLE_NAME:
				setVariableName((String)newValue);
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
			case AstPackage.VAR_REF__VARIABLE_NAME:
				setVariableName(VARIABLE_NAME_EDEFAULT);
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
			case AstPackage.VAR_REF__VARIABLE_NAME:
				return VARIABLE_NAME_EDEFAULT == null ? variableName != null
						: !VARIABLE_NAME_EDEFAULT.equals(variableName);
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
		result.append(" (variableName: ");
		result.append(variableName);
		result.append(')');
		return result.toString();
	}

} // VarRefImpl
