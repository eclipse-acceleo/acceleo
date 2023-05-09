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
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Collection Type Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.impl.CollectionTypeLiteralImpl#getElementType <em>Element
 * Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CollectionTypeLiteralImpl extends ClassTypeLiteralImpl implements CollectionTypeLiteral {
	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected TypeLiteral elementType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CollectionTypeLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.COLLECTION_TYPE_LITERAL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TypeLiteral getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetElementType(TypeLiteral newElementType, NotificationChain msgs) {
		TypeLiteral oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE, oldElementType, newElementType);
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
	public void setElementType(TypeLiteral newElementType) {
		if (newElementType != elementType) {
			NotificationChain msgs = null;
			if (elementType != null)
				msgs = ((InternalEObject)elementType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE, null, msgs);
			if (newElementType != null)
				msgs = ((InternalEObject)newElementType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE, null, msgs);
			msgs = basicSetElementType(newElementType, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE, newElementType, newElementType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE:
				return basicSetElementType(null, msgs);
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
			case AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE:
				return getElementType();
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
			case AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE:
				setElementType((TypeLiteral)newValue);
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
			case AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE:
				setElementType((TypeLiteral)null);
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
			case AstPackage.COLLECTION_TYPE_LITERAL__ELEMENT_TYPE:
				return elementType != null;
		}
		return super.eIsSet(featureID);
	}

} // CollectionTypeLiteralImpl
