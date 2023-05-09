/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Variable</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorVariableImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorVariableImpl#getTypeAql <em>Type Aql</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorVariableImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorVariableImpl#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorVariableImpl#getMissingColon <em>Missing Colon</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorVariableImpl#getMissingType <em>Missing Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorVariableImpl extends MinimalEObjectImpl.Container implements ErrorVariable {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final AstResult TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AstResult type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTypeAql() <em>Type Aql</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTypeAql()
	 * @generated
	 * @ordered
	 */
	protected Expression typeAql;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingName() <em>Missing Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingName()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_NAME_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingName() <em>Missing Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingName()
	 * @generated
	 * @ordered
	 */
	protected int missingName = MISSING_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingColon() <em>Missing Colon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingColon()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_COLON_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingColon() <em>Missing Colon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingColon()
	 * @generated
	 * @ordered
	 */
	protected int missingColon = MISSING_COLON_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingType() <em>Missing Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingType()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_TYPE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingType() <em>Missing Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingType()
	 * @generated
	 * @ordered
	 */
	protected int missingType = MISSING_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AstResult getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(AstResult newType) {
		AstResult oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_VARIABLE__TYPE,
					oldType, type));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getTypeAql() {
		return typeAql;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetTypeAql(Expression newTypeAql, NotificationChain msgs) {
		Expression oldTypeAql = typeAql;
		typeAql = newTypeAql;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_VARIABLE__TYPE_AQL, oldTypeAql, newTypeAql);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTypeAql(Expression newTypeAql) {
		if (newTypeAql != typeAql) {
			NotificationChain msgs = null;
			if (typeAql != null)
				msgs = ((InternalEObject)typeAql).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_VARIABLE__TYPE_AQL, null, msgs);
			if (newTypeAql != null)
				msgs = ((InternalEObject)newTypeAql).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_VARIABLE__TYPE_AQL, null, msgs);
			msgs = basicSetTypeAql(newTypeAql, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_VARIABLE__TYPE_AQL,
					newTypeAql, newTypeAql));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_VARIABLE__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingName() {
		return missingName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingName(int newMissingName) {
		int oldMissingName = missingName;
		missingName = newMissingName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_VARIABLE__MISSING_NAME,
					oldMissingName, missingName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingColon() {
		return missingColon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingColon(int newMissingColon) {
		int oldMissingColon = missingColon;
		missingColon = newMissingColon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_VARIABLE__MISSING_COLON, oldMissingColon, missingColon));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingType() {
		return missingType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingType(int newMissingType) {
		int oldMissingType = missingType;
		missingType = newMissingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_VARIABLE__MISSING_TYPE,
					oldMissingType, missingType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
				return basicSetTypeAql(null, msgs);
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
			case AcceleoPackage.ERROR_VARIABLE__TYPE:
				return getType();
			case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
				return getTypeAql();
			case AcceleoPackage.ERROR_VARIABLE__NAME:
				return getName();
			case AcceleoPackage.ERROR_VARIABLE__MISSING_NAME:
				return getMissingName();
			case AcceleoPackage.ERROR_VARIABLE__MISSING_COLON:
				return getMissingColon();
			case AcceleoPackage.ERROR_VARIABLE__MISSING_TYPE:
				return getMissingType();
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
			case AcceleoPackage.ERROR_VARIABLE__TYPE:
				setType((AstResult)newValue);
				return;
			case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
				setTypeAql((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_VARIABLE__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_NAME:
				setMissingName((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_COLON:
				setMissingColon((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_TYPE:
				setMissingType((Integer)newValue);
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
			case AcceleoPackage.ERROR_VARIABLE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
				setTypeAql((Expression)null);
				return;
			case AcceleoPackage.ERROR_VARIABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_NAME:
				setMissingName(MISSING_NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_COLON:
				setMissingColon(MISSING_COLON_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_TYPE:
				setMissingType(MISSING_TYPE_EDEFAULT);
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
			case AcceleoPackage.ERROR_VARIABLE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
				return typeAql != null;
			case AcceleoPackage.ERROR_VARIABLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.ERROR_VARIABLE__MISSING_NAME:
				return missingName != MISSING_NAME_EDEFAULT;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_COLON:
				return missingColon != MISSING_COLON_EDEFAULT;
			case AcceleoPackage.ERROR_VARIABLE__MISSING_TYPE:
				return missingType != MISSING_TYPE_EDEFAULT;
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
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_VARIABLE__TYPE:
					return AcceleoPackage.TYPED_ELEMENT__TYPE;
				case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
					return AcceleoPackage.TYPED_ELEMENT__TYPE_AQL;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_VARIABLE__NAME:
					return AcceleoPackage.NAMED_ELEMENT__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == Variable.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.TYPED_ELEMENT__TYPE:
					return AcceleoPackage.ERROR_VARIABLE__TYPE;
				case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
					return AcceleoPackage.ERROR_VARIABLE__TYPE_AQL;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.ERROR_VARIABLE__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == Variable.class) {
			switch (baseFeatureID) {
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
		result.append(" (type: "); //$NON-NLS-1$
		result.append(type);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", missingName: "); //$NON-NLS-1$
		result.append(missingName);
		result.append(", missingColon: "); //$NON-NLS-1$
		result.append(missingColon);
		result.append(", missingType: "); //$NON-NLS-1$
		result.append(missingType);
		result.append(')');
		return result.toString();
	}

} // ErrorVariableImpl
