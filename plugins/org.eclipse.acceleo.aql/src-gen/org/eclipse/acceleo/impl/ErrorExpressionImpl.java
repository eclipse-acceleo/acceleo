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
import org.eclipse.acceleo.ErrorExpression;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Expression</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorExpressionImpl#getAst <em>Ast</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorExpressionImpl#getAql <em>Aql</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorExpressionImpl extends MinimalEObjectImpl.Container implements ErrorExpression {
	/**
	 * The default value of the '{@link #getAst() <em>Ast</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAst()
	 * @generated
	 * @ordered
	 */
	protected static final AstResult AST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAst() <em>Ast</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAst()
	 * @generated
	 * @ordered
	 */
	protected AstResult ast = AST_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAql() <em>Aql</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAql()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.acceleo.query.ast.Expression aql;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AstResult getAst() {
		return ast;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAst(AstResult newAst) {
		AstResult oldAst = ast;
		ast = newAst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_EXPRESSION__AST,
					oldAst, ast));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public org.eclipse.acceleo.query.ast.Expression getAql() {
		return aql;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAql(org.eclipse.acceleo.query.ast.Expression newAql,
			NotificationChain msgs) {
		org.eclipse.acceleo.query.ast.Expression oldAql = aql;
		aql = newAql;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_EXPRESSION__AQL, oldAql, newAql);
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
	public void setAql(org.eclipse.acceleo.query.ast.Expression newAql) {
		if (newAql != aql) {
			NotificationChain msgs = null;
			if (aql != null)
				msgs = ((InternalEObject)aql).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_EXPRESSION__AQL, null, msgs);
			if (newAql != null)
				msgs = ((InternalEObject)newAql).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_EXPRESSION__AQL, null, msgs);
			msgs = basicSetAql(newAql, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_EXPRESSION__AQL,
					newAql, newAql));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_EXPRESSION__AQL:
				return basicSetAql(null, msgs);
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
			case AcceleoPackage.ERROR_EXPRESSION__AST:
				return getAst();
			case AcceleoPackage.ERROR_EXPRESSION__AQL:
				return getAql();
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
			case AcceleoPackage.ERROR_EXPRESSION__AST:
				setAst((AstResult)newValue);
				return;
			case AcceleoPackage.ERROR_EXPRESSION__AQL:
				setAql((org.eclipse.acceleo.query.ast.Expression)newValue);
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
			case AcceleoPackage.ERROR_EXPRESSION__AST:
				setAst(AST_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_EXPRESSION__AQL:
				setAql((org.eclipse.acceleo.query.ast.Expression)null);
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
			case AcceleoPackage.ERROR_EXPRESSION__AST:
				return AST_EDEFAULT == null ? ast != null : !AST_EDEFAULT.equals(ast);
			case AcceleoPackage.ERROR_EXPRESSION__AQL:
				return aql != null;
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
				case AcceleoPackage.ERROR_EXPRESSION__AST:
					return AcceleoPackage.EXPRESSION__AST;
				case AcceleoPackage.ERROR_EXPRESSION__AQL:
					return AcceleoPackage.EXPRESSION__AQL;
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
				case AcceleoPackage.EXPRESSION__AST:
					return AcceleoPackage.ERROR_EXPRESSION__AST;
				case AcceleoPackage.EXPRESSION__AQL:
					return AcceleoPackage.ERROR_EXPRESSION__AQL;
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
		result.append(" (ast: "); //$NON-NLS-1$
		result.append(ast);
		result.append(')');
		return result.toString();
	}

} // ErrorExpressionImpl
