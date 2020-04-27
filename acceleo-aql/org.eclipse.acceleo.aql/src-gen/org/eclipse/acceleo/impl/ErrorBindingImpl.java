/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getInitExpression <em>Init Expression</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getMissingName <em>Missing Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getMissingColon <em>Missing Colon</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getMissingType <em>Missing Type</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getMissingAffectationSymbole <em>Missing Affectation Symbole</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getMissingAffectationSymbolePosition <em>Missing Affectation Symbole Position</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorBindingImpl extends MinimalEObjectImpl.Container implements ErrorBinding {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final AstResult TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AstResult type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInitExpression() <em>Init Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression initExpression;

	/**
	 * The default value of the '{@link #getMissingName() <em>Missing Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingName()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_NAME_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingName() <em>Missing Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingName()
	 * @generated
	 * @ordered
	 */
	protected int missingName = MISSING_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingColon() <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingColon()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_COLON_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingColon() <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingColon()
	 * @generated
	 * @ordered
	 */
	protected int missingColon = MISSING_COLON_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingType() <em>Missing Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingType()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_TYPE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingType() <em>Missing Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingType()
	 * @generated
	 * @ordered
	 */
	protected int missingType = MISSING_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingAffectationSymbole() <em>Missing Affectation Symbole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingAffectationSymbole()
	 * @generated
	 * @ordered
	 */
	protected static final String MISSING_AFFECTATION_SYMBOLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMissingAffectationSymbole() <em>Missing Affectation Symbole</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingAffectationSymbole()
	 * @generated
	 * @ordered
	 */
	protected String missingAffectationSymbole = MISSING_AFFECTATION_SYMBOLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingAffectationSymbolePosition() <em>Missing Affectation Symbole Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingAffectationSymbolePosition()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_AFFECTATION_SYMBOLE_POSITION_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingAffectationSymbolePosition() <em>Missing Affectation Symbole Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMissingAffectationSymbolePosition()
	 * @generated
	 * @ordered
	 */
	protected int missingAffectationSymbolePosition = MISSING_AFFECTATION_SYMBOLE_POSITION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AstResult getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(AstResult newType) {
		AstResult oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__TYPE, oldType,
					type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getInitExpression() {
		return initExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitExpression(Expression newInitExpression, NotificationChain msgs) {
		Expression oldInitExpression = initExpression;
		initExpression = newInitExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION, oldInitExpression, newInitExpression);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitExpression(Expression newInitExpression) {
		if (newInitExpression != initExpression) {
			NotificationChain msgs = null;
			if (initExpression != null)
				msgs = ((InternalEObject)initExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION, null, msgs);
			if (newInitExpression != null)
				msgs = ((InternalEObject)newInitExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION, null, msgs);
			msgs = basicSetInitExpression(newInitExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION, newInitExpression, newInitExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMissingName() {
		return missingName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMissingName(int newMissingName) {
		int oldMissingName = missingName;
		missingName = newMissingName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__MISSING_NAME,
					oldMissingName, missingName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMissingColon() {
		return missingColon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMissingColon(int newMissingColon) {
		int oldMissingColon = missingColon;
		missingColon = newMissingColon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__MISSING_COLON,
					oldMissingColon, missingColon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMissingType() {
		return missingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMissingType(int newMissingType) {
		int oldMissingType = missingType;
		missingType = newMissingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__MISSING_TYPE,
					oldMissingType, missingType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMissingAffectationSymbole() {
		return missingAffectationSymbole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMissingAffectationSymbole(String newMissingAffectationSymbole) {
		String oldMissingAffectationSymbole = missingAffectationSymbole;
		missingAffectationSymbole = newMissingAffectationSymbole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE, oldMissingAffectationSymbole,
					missingAffectationSymbole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMissingAffectationSymbolePosition() {
		return missingAffectationSymbolePosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMissingAffectationSymbolePosition(int newMissingAffectationSymbolePosition) {
		int oldMissingAffectationSymbolePosition = missingAffectationSymbolePosition;
		missingAffectationSymbolePosition = newMissingAffectationSymbolePosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION,
					oldMissingAffectationSymbolePosition, missingAffectationSymbolePosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				return basicSetInitExpression(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.ERROR_BINDING__TYPE:
				return getType();
			case AcceleoPackage.ERROR_BINDING__NAME:
				return getName();
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				return getInitExpression();
			case AcceleoPackage.ERROR_BINDING__MISSING_NAME:
				return getMissingName();
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				return getMissingColon();
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				return getMissingType();
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				return getMissingAffectationSymbole();
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION:
				return getMissingAffectationSymbolePosition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AcceleoPackage.ERROR_BINDING__TYPE:
				setType((AstResult)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				setInitExpression((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_NAME:
				setMissingName((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				setMissingColon((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				setMissingType((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				setMissingAffectationSymbole((String)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION:
				setMissingAffectationSymbolePosition((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AcceleoPackage.ERROR_BINDING__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				setInitExpression((Expression)null);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_NAME:
				setMissingName(MISSING_NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				setMissingColon(MISSING_COLON_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				setMissingType(MISSING_TYPE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				setMissingAffectationSymbole(MISSING_AFFECTATION_SYMBOLE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION:
				setMissingAffectationSymbolePosition(MISSING_AFFECTATION_SYMBOLE_POSITION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AcceleoPackage.ERROR_BINDING__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case AcceleoPackage.ERROR_BINDING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				return initExpression != null;
			case AcceleoPackage.ERROR_BINDING__MISSING_NAME:
				return missingName != MISSING_NAME_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				return missingColon != MISSING_COLON_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				return missingType != MISSING_TYPE_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				return MISSING_AFFECTATION_SYMBOLE_EDEFAULT == null ? missingAffectationSymbole != null
						: !MISSING_AFFECTATION_SYMBOLE_EDEFAULT.equals(missingAffectationSymbole);
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION:
				return missingAffectationSymbolePosition != MISSING_AFFECTATION_SYMBOLE_POSITION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_BINDING__TYPE:
					return AcceleoPackage.TYPED_ELEMENT__TYPE;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_BINDING__NAME:
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
		if (baseClass == Binding.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
					return AcceleoPackage.BINDING__INIT_EXPRESSION;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.TYPED_ELEMENT__TYPE:
					return AcceleoPackage.ERROR_BINDING__TYPE;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.ERROR_BINDING__NAME;
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
		if (baseClass == Binding.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.BINDING__INIT_EXPRESSION:
					return AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		result.append(", missingAffectationSymbole: "); //$NON-NLS-1$
		result.append(missingAffectationSymbole);
		result.append(", missingAffectationSymbolePosition: "); //$NON-NLS-1$
		result.append(missingAffectationSymbolePosition);
		result.append(')');
		return result.toString();
	}

} //ErrorBindingImpl
