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

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getInitExpression <em>Init Expression</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#isMissingColon <em>Missing Colon</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#isMissingType <em>Missing Type</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorBindingImpl#getMissingAffectationSymbole <em>Missing Affectation Symbole</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorBindingImpl extends MinimalEObjectImpl.Container implements ErrorBinding {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClassifier type;

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
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected int endPosition = END_POSITION_EDEFAULT;

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
	 * The default value of the '{@link #isMissingColon() <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingColon()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_COLON_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingColon() <em>Missing Colon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingColon()
	 * @generated
	 * @ordered
	 */
	protected boolean missingColon = MISSING_COLON_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingType() <em>Missing Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingType()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_TYPE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingType() <em>Missing Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingType()
	 * @generated
	 * @ordered
	 */
	protected boolean missingType = MISSING_TYPE_EDEFAULT;

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
	public EClassifier getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EClassifier)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AcceleoPackage.ERROR_BINDING__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EClassifier newType) {
		EClassifier oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__TYPE,
					oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_BINDING__START_POSITION, oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndPosition(int newEndPosition) {
		int oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_BINDING__END_POSITION,
					oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	public boolean isMissingColon() {
		return missingColon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingColon(boolean newMissingColon) {
		boolean oldMissingColon = missingColon;
		missingColon = newMissingColon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_BINDING__MISSING_COLON, oldMissingColon, missingColon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingType() {
		return missingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingType(boolean newMissingType) {
		boolean oldMissingType = missingType;
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
	public String getMissingAffectationSymbole() {
		return missingAffectationSymbole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
				if (resolve)
					return getType();
				return basicGetType();
			case AcceleoPackage.ERROR_BINDING__NAME:
				return getName();
			case AcceleoPackage.ERROR_BINDING__START_POSITION:
				return getStartPosition();
			case AcceleoPackage.ERROR_BINDING__END_POSITION:
				return getEndPosition();
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				return getInitExpression();
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				return isMissingColon();
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				return isMissingType();
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				return getMissingAffectationSymbole();
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
				setType((EClassifier)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__END_POSITION:
				setEndPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				setInitExpression((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				setMissingColon((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				setMissingType((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				setMissingAffectationSymbole((String)newValue);
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
				setType((EClassifier)null);
				return;
			case AcceleoPackage.ERROR_BINDING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				setInitExpression((Expression)null);
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
				return type != null;
			case AcceleoPackage.ERROR_BINDING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.ERROR_BINDING__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__INIT_EXPRESSION:
				return initExpression != null;
			case AcceleoPackage.ERROR_BINDING__MISSING_COLON:
				return missingColon != MISSING_COLON_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__MISSING_TYPE:
				return missingType != MISSING_TYPE_EDEFAULT;
			case AcceleoPackage.ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE:
				return MISSING_AFFECTATION_SYMBOLE_EDEFAULT == null ? missingAffectationSymbole != null
						: !MISSING_AFFECTATION_SYMBOLE_EDEFAULT.equals(missingAffectationSymbole);
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
		if (baseClass == ASTNode.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_BINDING__START_POSITION:
					return AcceleoPackage.AST_NODE__START_POSITION;
				case AcceleoPackage.ERROR_BINDING__END_POSITION:
					return AcceleoPackage.AST_NODE__END_POSITION;
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
		if (baseClass == ASTNode.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.AST_NODE__START_POSITION:
					return AcceleoPackage.ERROR_BINDING__START_POSITION;
				case AcceleoPackage.AST_NODE__END_POSITION:
					return AcceleoPackage.ERROR_BINDING__END_POSITION;
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", startPosition: "); //$NON-NLS-1$
		result.append(startPosition);
		result.append(", endPosition: "); //$NON-NLS-1$
		result.append(endPosition);
		result.append(", missingColon: "); //$NON-NLS-1$
		result.append(missingColon);
		result.append(", missingType: "); //$NON-NLS-1$
		result.append(missingType);
		result.append(", missingAffectationSymbole: "); //$NON-NLS-1$
		result.append(missingAffectationSymbole);
		result.append(')');
		return result.toString();
	}

} //ErrorBindingImpl
