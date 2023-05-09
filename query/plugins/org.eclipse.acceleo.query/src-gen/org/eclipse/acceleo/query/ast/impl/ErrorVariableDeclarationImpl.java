/**
 *  Copyright (c) 2015, 2023 Obeo.
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
import org.eclipse.acceleo.query.ast.Declaration;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Variable Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.ErrorVariableDeclarationImpl#getExpression
 * <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorVariableDeclarationImpl extends ExpressionImpl implements ErrorVariableDeclaration {
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
	 * The cached value of the '{@link #getType() <em>Type</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected TypeLiteral type;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorVariableDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.ERROR_VARIABLE_DECLARATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_VARIABLE_DECLARATION__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TypeLiteral getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetType(TypeLiteral newType, NotificationChain msgs) {
		TypeLiteral oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_VARIABLE_DECLARATION__TYPE, oldType, newType);
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
	public void setType(TypeLiteral newType) {
		if (newType != type) {
			NotificationChain msgs = null;
			if (type != null)
				msgs = ((InternalEObject)type).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_VARIABLE_DECLARATION__TYPE, null, msgs);
			if (newType != null)
				msgs = ((InternalEObject)newType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.ERROR_VARIABLE_DECLARATION__TYPE, null, msgs);
			msgs = basicSetType(newType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AstPackage.ERROR_VARIABLE_DECLARATION__TYPE,
					newType, newType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getExpression() {
		if (expression != null && expression.eIsProxy()) {
			InternalEObject oldExpression = (InternalEObject)expression;
			expression = (Expression)eResolveProxy(oldExpression);
			if (expression != oldExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION, oldExpression, expression));
			}
		}
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression basicGetExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setExpression(Expression newExpression) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION, oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AstPackage.ERROR_VARIABLE_DECLARATION__TYPE:
				return basicSetType(null, msgs);
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
			case AstPackage.ERROR_VARIABLE_DECLARATION__NAME:
				return getName();
			case AstPackage.ERROR_VARIABLE_DECLARATION__TYPE:
				return getType();
			case AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION:
				if (resolve)
					return getExpression();
				return basicGetExpression();
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
			case AstPackage.ERROR_VARIABLE_DECLARATION__NAME:
				setName((String)newValue);
				return;
			case AstPackage.ERROR_VARIABLE_DECLARATION__TYPE:
				setType((TypeLiteral)newValue);
				return;
			case AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION:
				setExpression((Expression)newValue);
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
			case AstPackage.ERROR_VARIABLE_DECLARATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AstPackage.ERROR_VARIABLE_DECLARATION__TYPE:
				setType((TypeLiteral)null);
				return;
			case AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION:
				setExpression((Expression)null);
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
			case AstPackage.ERROR_VARIABLE_DECLARATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AstPackage.ERROR_VARIABLE_DECLARATION__TYPE:
				return type != null;
			case AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION:
				return expression != null;
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
		if (baseClass == Declaration.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_VARIABLE_DECLARATION__NAME:
					return AstPackage.DECLARATION__NAME;
				case AstPackage.ERROR_VARIABLE_DECLARATION__TYPE:
					return AstPackage.DECLARATION__TYPE;
				default:
					return -1;
			}
		}
		if (baseClass == VariableDeclaration.class) {
			switch (derivedFeatureID) {
				case AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION:
					return AstPackage.VARIABLE_DECLARATION__EXPRESSION;
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
		if (baseClass == Declaration.class) {
			switch (baseFeatureID) {
				case AstPackage.DECLARATION__NAME:
					return AstPackage.ERROR_VARIABLE_DECLARATION__NAME;
				case AstPackage.DECLARATION__TYPE:
					return AstPackage.ERROR_VARIABLE_DECLARATION__TYPE;
				default:
					return -1;
			}
		}
		if (baseClass == VariableDeclaration.class) {
			switch (baseFeatureID) {
				case AstPackage.VARIABLE_DECLARATION__EXPRESSION:
					return AstPackage.ERROR_VARIABLE_DECLARATION__EXPRESSION;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // ErrorVariableDeclarationImpl
