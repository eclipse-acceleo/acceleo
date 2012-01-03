/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.impl;

import java.util.Collection;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Block</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.BlockImpl#getInit <em>Init </em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.BlockImpl#getBody <em>Body </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class BlockImpl extends TemplateExpressionImpl implements Block {
	/**
	 * The cached value of the '{@link #getInit() <em>Init</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getInit()
	 * @generated
	 * @ordered
	 */
	protected InitSection init;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected EList<OCLExpression> body;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InitSection getInit() {
		return init;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetInit(InitSection newInit, NotificationChain msgs) {
		InitSection oldInit = init;
		init = newInit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.BLOCK__INIT, oldInit, newInit);
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
	public void setInit(InitSection newInit) {
		if (newInit != init) {
			NotificationChain msgs = null;
			if (init != null)
				msgs = ((InternalEObject)init).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.BLOCK__INIT, null, msgs);
			if (newInit != null)
				msgs = ((InternalEObject)newInit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.BLOCK__INIT, null, msgs);
			msgs = basicSetInit(newInit, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.BLOCK__INIT, newInit, newInit));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<OCLExpression> getBody() {
		if (body == null) {
			body = new EObjectContainmentEList<OCLExpression>(OCLExpression.class, this,
					MtlPackage.BLOCK__BODY);
		}
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.BLOCK__INIT:
				return basicSetInit(null, msgs);
			case MtlPackage.BLOCK__BODY:
				return ((InternalEList<?>)getBody()).basicRemove(otherEnd, msgs);
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
			case MtlPackage.BLOCK__INIT:
				return getInit();
			case MtlPackage.BLOCK__BODY:
				return getBody();
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
			case MtlPackage.BLOCK__INIT:
				setInit((InitSection)newValue);
				return;
			case MtlPackage.BLOCK__BODY:
				getBody().clear();
				getBody().addAll((Collection<? extends OCLExpression>)newValue);
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
			case MtlPackage.BLOCK__INIT:
				setInit((InitSection)null);
				return;
			case MtlPackage.BLOCK__BODY:
				getBody().clear();
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
			case MtlPackage.BLOCK__INIT:
				return init != null;
			case MtlPackage.BLOCK__BODY:
				return body != null && !body.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // BlockImpl
