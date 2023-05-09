/**
 * Copyright (c) 2008, 2023 Obeo.
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
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Protected Area</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ProtectedAreaImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ProtectedAreaImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ProtectedAreaImpl#getStartTagPrefix <em>Start Tag Prefix</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ProtectedAreaImpl#getEndTagPrefix <em>End Tag Prefix</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProtectedAreaImpl extends MinimalEObjectImpl.Container implements ProtectedArea {
	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Expression id;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

	/**
	 * The cached value of the '{@link #getStartTagPrefix() <em>Start Tag Prefix</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartTagPrefix()
	 * @generated
	 * @ordered
	 */
	protected Expression startTagPrefix;

	/**
	 * The cached value of the '{@link #getEndTagPrefix() <em>End Tag Prefix</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndTagPrefix()
	 * @generated
	 * @ordered
	 */
	protected Expression endTagPrefix;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProtectedAreaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.PROTECTED_AREA;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetId(Expression newId, NotificationChain msgs) {
		Expression oldId = id;
		id = newId;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.PROTECTED_AREA__ID, oldId, newId);
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
	public void setId(Expression newId) {
		if (newId != id) {
			NotificationChain msgs = null;
			if (id != null)
				msgs = ((InternalEObject)id).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__ID, null, msgs);
			if (newId != null)
				msgs = ((InternalEObject)newId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__ID, null, msgs);
			msgs = basicSetId(newId, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.PROTECTED_AREA__ID, newId,
					newId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Block getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBody(Block newBody, NotificationChain msgs) {
		Block oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.PROTECTED_AREA__BODY, oldBody, newBody);
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
	public void setBody(Block newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.PROTECTED_AREA__BODY,
					newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getStartTagPrefix() {
		return startTagPrefix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetStartTagPrefix(Expression newStartTagPrefix, NotificationChain msgs) {
		Expression oldStartTagPrefix = startTagPrefix;
		startTagPrefix = newStartTagPrefix;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX, oldStartTagPrefix, newStartTagPrefix);
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
	public void setStartTagPrefix(Expression newStartTagPrefix) {
		if (newStartTagPrefix != startTagPrefix) {
			NotificationChain msgs = null;
			if (startTagPrefix != null)
				msgs = ((InternalEObject)startTagPrefix).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX, null, msgs);
			if (newStartTagPrefix != null)
				msgs = ((InternalEObject)newStartTagPrefix).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX, null, msgs);
			msgs = basicSetStartTagPrefix(newStartTagPrefix, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX, newStartTagPrefix, newStartTagPrefix));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getEndTagPrefix() {
		return endTagPrefix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEndTagPrefix(Expression newEndTagPrefix, NotificationChain msgs) {
		Expression oldEndTagPrefix = endTagPrefix;
		endTagPrefix = newEndTagPrefix;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX, oldEndTagPrefix, newEndTagPrefix);
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
	public void setEndTagPrefix(Expression newEndTagPrefix) {
		if (newEndTagPrefix != endTagPrefix) {
			NotificationChain msgs = null;
			if (endTagPrefix != null)
				msgs = ((InternalEObject)endTagPrefix).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX, null, msgs);
			if (newEndTagPrefix != null)
				msgs = ((InternalEObject)newEndTagPrefix).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX, null, msgs);
			msgs = basicSetEndTagPrefix(newEndTagPrefix, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX, newEndTagPrefix, newEndTagPrefix));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.PROTECTED_AREA__ID:
				return basicSetId(null, msgs);
			case AcceleoPackage.PROTECTED_AREA__BODY:
				return basicSetBody(null, msgs);
			case AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX:
				return basicSetStartTagPrefix(null, msgs);
			case AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX:
				return basicSetEndTagPrefix(null, msgs);
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
			case AcceleoPackage.PROTECTED_AREA__ID:
				return getId();
			case AcceleoPackage.PROTECTED_AREA__BODY:
				return getBody();
			case AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX:
				return getStartTagPrefix();
			case AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX:
				return getEndTagPrefix();
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
			case AcceleoPackage.PROTECTED_AREA__ID:
				setId((Expression)newValue);
				return;
			case AcceleoPackage.PROTECTED_AREA__BODY:
				setBody((Block)newValue);
				return;
			case AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX:
				setStartTagPrefix((Expression)newValue);
				return;
			case AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX:
				setEndTagPrefix((Expression)newValue);
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
			case AcceleoPackage.PROTECTED_AREA__ID:
				setId((Expression)null);
				return;
			case AcceleoPackage.PROTECTED_AREA__BODY:
				setBody((Block)null);
				return;
			case AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX:
				setStartTagPrefix((Expression)null);
				return;
			case AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX:
				setEndTagPrefix((Expression)null);
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
			case AcceleoPackage.PROTECTED_AREA__ID:
				return id != null;
			case AcceleoPackage.PROTECTED_AREA__BODY:
				return body != null;
			case AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX:
				return startTagPrefix != null;
			case AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX:
				return endTagPrefix != null;
		}
		return super.eIsSet(featureID);
	}

} // ProtectedAreaImpl
