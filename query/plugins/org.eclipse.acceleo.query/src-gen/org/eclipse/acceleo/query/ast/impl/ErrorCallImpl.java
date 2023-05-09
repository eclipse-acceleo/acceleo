/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Collection Call</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl#getServiceName <em>Service Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl#getArguments <em>Arguments</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl#isSuperCall <em>Super Call</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorCallImpl#isMissingEndParenthesis <em>Missing End
 * Parenthesis</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorCallImpl extends ExpressionImpl implements ErrorCall {
	/**
	 * The default value of the '{@link #getServiceName() <em>Service Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getServiceName()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVICE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getServiceName() <em>Service Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getServiceName()
	 * @generated
	 * @ordered
	 */
	protected String serviceName = SERVICE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final CallType TYPE_EDEFAULT = CallType.CALLSERVICE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected CallType type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> arguments;

	/**
	 * The default value of the '{@link #isSuperCall() <em>Super Call</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isSuperCall()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUPER_CALL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSuperCall() <em>Super Call</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isSuperCall()
	 * @generated
	 * @ordered
	 */
	protected boolean superCall = SUPER_CALL_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingEndParenthesis() <em>Missing End Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isMissingEndParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_END_PARENTHESIS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingEndParenthesis() <em>Missing End Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isMissingEndParenthesis()
	 * @generated
	 * @ordered
	 */
	protected boolean missingEndParenthesis = MISSING_END_PARENTHESIS_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ERROR_CALL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setServiceName(String newServiceName) {
		String oldServiceName = serviceName;
		serviceName = newServiceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_CALL__SERVICE_NAME,
					oldServiceName, serviceName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CallType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(CallType newType) {
		CallType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_CALL__TYPE, oldType,
					type));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Expression> getArguments() {
		if (arguments == null) {
			arguments = new EObjectContainmentEList<Expression>(Expression.class, this,
					AstPackage.ERROR_CALL__ARGUMENTS);
		}
		return arguments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isSuperCall() {
		return superCall;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSuperCall(boolean newSuperCall) {
		boolean oldSuperCall = superCall;
		superCall = newSuperCall;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_CALL__SUPER_CALL,
					oldSuperCall, superCall));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isMissingEndParenthesis() {
		return missingEndParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEndParenthesis(boolean newMissingEndParenthesis) {
		boolean oldMissingEndParenthesis = missingEndParenthesis;
		missingEndParenthesis = newMissingEndParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_CALL__MISSING_END_PARENTHESIS, oldMissingEndParenthesis,
					missingEndParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AstPackage.ERROR_CALL__ARGUMENTS:
				return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
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
			case AstPackage.ERROR_CALL__SERVICE_NAME:
				return getServiceName();
			case AstPackage.ERROR_CALL__TYPE:
				return getType();
			case AstPackage.ERROR_CALL__ARGUMENTS:
				return getArguments();
			case AstPackage.ERROR_CALL__SUPER_CALL:
				return isSuperCall();
			case AstPackage.ERROR_CALL__MISSING_END_PARENTHESIS:
				return isMissingEndParenthesis();
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
			case AstPackage.ERROR_CALL__SERVICE_NAME:
				setServiceName((String)newValue);
				return;
			case AstPackage.ERROR_CALL__TYPE:
				setType((CallType)newValue);
				return;
			case AstPackage.ERROR_CALL__ARGUMENTS:
				getArguments().clear();
				getArguments().addAll((Collection<? extends Expression>)newValue);
				return;
			case AstPackage.ERROR_CALL__SUPER_CALL:
				setSuperCall((Boolean)newValue);
				return;
			case AstPackage.ERROR_CALL__MISSING_END_PARENTHESIS:
				setMissingEndParenthesis((Boolean)newValue);
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
			case AstPackage.ERROR_CALL__SERVICE_NAME:
				setServiceName(SERVICE_NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_CALL__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AstPackage.ERROR_CALL__ARGUMENTS:
				getArguments().clear();
				return;
			case AstPackage.ERROR_CALL__SUPER_CALL:
				setSuperCall(SUPER_CALL_EDEFAULT);
				return;
			case AstPackage.ERROR_CALL__MISSING_END_PARENTHESIS:
				setMissingEndParenthesis(MISSING_END_PARENTHESIS_EDEFAULT);
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
			case AstPackage.ERROR_CALL__SERVICE_NAME:
				return SERVICE_NAME_EDEFAULT == null ? serviceName != null
						: !SERVICE_NAME_EDEFAULT.equals(serviceName);
			case AstPackage.ERROR_CALL__TYPE:
				return type != TYPE_EDEFAULT;
			case AstPackage.ERROR_CALL__ARGUMENTS:
				return arguments != null && !arguments.isEmpty();
			case AstPackage.ERROR_CALL__SUPER_CALL:
				return superCall != SUPER_CALL_EDEFAULT;
			case AstPackage.ERROR_CALL__MISSING_END_PARENTHESIS:
				return missingEndParenthesis != MISSING_END_PARENTHESIS_EDEFAULT;
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
		if (baseClass == Call.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_CALL__SERVICE_NAME:
					return AstPackage.CALL__SERVICE_NAME;
				case AstPackage.ERROR_CALL__TYPE:
					return AstPackage.CALL__TYPE;
				case AstPackage.ERROR_CALL__ARGUMENTS:
					return AstPackage.CALL__ARGUMENTS;
				case AstPackage.ERROR_CALL__SUPER_CALL:
					return AstPackage.CALL__SUPER_CALL;
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
		if (baseClass == Call.class) {
			switch (baseFeatureID) {
				case AstPackage.CALL__SERVICE_NAME:
					return AstPackage.ERROR_CALL__SERVICE_NAME;
				case AstPackage.CALL__TYPE:
					return AstPackage.ERROR_CALL__TYPE;
				case AstPackage.CALL__ARGUMENTS:
					return AstPackage.ERROR_CALL__ARGUMENTS;
				case AstPackage.CALL__SUPER_CALL:
					return AstPackage.ERROR_CALL__SUPER_CALL;
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
		result.append(" (serviceName: ");
		result.append(serviceName);
		result.append(", type: ");
		result.append(type);
		result.append(", superCall: ");
		result.append(superCall);
		result.append(", missingEndParenthesis: ");
		result.append(missingEndParenthesis);
		result.append(')');
		return result.toString();
	}

} // ErrorCollectionCallImpl
