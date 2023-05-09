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
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Typed Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.TypedElementImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TypedElementImpl#getTypeAql <em>Type Aql</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypedElementImpl extends MinimalEObjectImpl.Container implements TypedElement {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.TYPED_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TYPED_ELEMENT__TYPE, oldType,
					type));
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
					AcceleoPackage.TYPED_ELEMENT__TYPE_AQL, oldTypeAql, newTypeAql);
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
						- AcceleoPackage.TYPED_ELEMENT__TYPE_AQL, null, msgs);
			if (newTypeAql != null)
				msgs = ((InternalEObject)newTypeAql).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TYPED_ELEMENT__TYPE_AQL, null, msgs);
			msgs = basicSetTypeAql(newTypeAql, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TYPED_ELEMENT__TYPE_AQL,
					newTypeAql, newTypeAql));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
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
			case AcceleoPackage.TYPED_ELEMENT__TYPE:
				return getType();
			case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
				return getTypeAql();
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
			case AcceleoPackage.TYPED_ELEMENT__TYPE:
				setType((AstResult)newValue);
				return;
			case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
				setTypeAql((Expression)newValue);
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
			case AcceleoPackage.TYPED_ELEMENT__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
				setTypeAql((Expression)null);
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
			case AcceleoPackage.TYPED_ELEMENT__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
				return typeAql != null;
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
		result.append(" (type: "); //$NON-NLS-1$
		result.append(type);
		result.append(')');
		return result.toString();
	}

} // TypedElementImpl
