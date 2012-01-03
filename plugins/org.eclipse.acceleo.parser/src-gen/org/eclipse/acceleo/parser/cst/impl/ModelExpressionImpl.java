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
package org.eclipse.acceleo.parser.cst.impl;

import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Model Expression</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModelExpressionImpl#getAfter <em>After</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModelExpressionImpl extends TemplateExpressionImpl implements ModelExpression {
	/**
	 * The default value of the '{@link #getBody() <em>Body</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected static final String BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected String body = BODY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression before;

	/**
	 * The cached value of the '{@link #getEach() <em>Each</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEach()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression each;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression after;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModelExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.MODEL_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBody(String newBody) {
		String oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODEL_EXPRESSION__BODY, oldBody,
					body));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getBefore() {
		return before;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBefore(ModelExpression newBefore, NotificationChain msgs) {
		ModelExpression oldBefore = before;
		before = newBefore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.MODEL_EXPRESSION__BEFORE, oldBefore, newBefore);
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
	public void setBefore(ModelExpression newBefore) {
		if (newBefore != before) {
			NotificationChain msgs = null;
			if (before != null)
				msgs = ((InternalEObject)before).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODEL_EXPRESSION__BEFORE, null, msgs);
			if (newBefore != null)
				msgs = ((InternalEObject)newBefore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODEL_EXPRESSION__BEFORE, null, msgs);
			msgs = basicSetBefore(newBefore, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODEL_EXPRESSION__BEFORE,
					newBefore, newBefore));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getEach() {
		return each;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEach(ModelExpression newEach, NotificationChain msgs) {
		ModelExpression oldEach = each;
		each = newEach;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.MODEL_EXPRESSION__EACH, oldEach, newEach);
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
	public void setEach(ModelExpression newEach) {
		if (newEach != each) {
			NotificationChain msgs = null;
			if (each != null)
				msgs = ((InternalEObject)each).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODEL_EXPRESSION__EACH, null, msgs);
			if (newEach != null)
				msgs = ((InternalEObject)newEach).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODEL_EXPRESSION__EACH, null, msgs);
			msgs = basicSetEach(newEach, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODEL_EXPRESSION__EACH, newEach,
					newEach));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getAfter() {
		return after;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAfter(ModelExpression newAfter, NotificationChain msgs) {
		ModelExpression oldAfter = after;
		after = newAfter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.MODEL_EXPRESSION__AFTER, oldAfter, newAfter);
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
	public void setAfter(ModelExpression newAfter) {
		if (newAfter != after) {
			NotificationChain msgs = null;
			if (after != null)
				msgs = ((InternalEObject)after).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODEL_EXPRESSION__AFTER, null, msgs);
			if (newAfter != null)
				msgs = ((InternalEObject)newAfter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODEL_EXPRESSION__AFTER, null, msgs);
			msgs = basicSetAfter(newAfter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODEL_EXPRESSION__AFTER,
					newAfter, newAfter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.MODEL_EXPRESSION__BEFORE:
				return basicSetBefore(null, msgs);
			case CstPackage.MODEL_EXPRESSION__EACH:
				return basicSetEach(null, msgs);
			case CstPackage.MODEL_EXPRESSION__AFTER:
				return basicSetAfter(null, msgs);
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
			case CstPackage.MODEL_EXPRESSION__BODY:
				return getBody();
			case CstPackage.MODEL_EXPRESSION__BEFORE:
				return getBefore();
			case CstPackage.MODEL_EXPRESSION__EACH:
				return getEach();
			case CstPackage.MODEL_EXPRESSION__AFTER:
				return getAfter();
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
			case CstPackage.MODEL_EXPRESSION__BODY:
				setBody((String)newValue);
				return;
			case CstPackage.MODEL_EXPRESSION__BEFORE:
				setBefore((ModelExpression)newValue);
				return;
			case CstPackage.MODEL_EXPRESSION__EACH:
				setEach((ModelExpression)newValue);
				return;
			case CstPackage.MODEL_EXPRESSION__AFTER:
				setAfter((ModelExpression)newValue);
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
			case CstPackage.MODEL_EXPRESSION__BODY:
				setBody(BODY_EDEFAULT);
				return;
			case CstPackage.MODEL_EXPRESSION__BEFORE:
				setBefore((ModelExpression)null);
				return;
			case CstPackage.MODEL_EXPRESSION__EACH:
				setEach((ModelExpression)null);
				return;
			case CstPackage.MODEL_EXPRESSION__AFTER:
				setAfter((ModelExpression)null);
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
			case CstPackage.MODEL_EXPRESSION__BODY:
				return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT.equals(body);
			case CstPackage.MODEL_EXPRESSION__BEFORE:
				return before != null;
			case CstPackage.MODEL_EXPRESSION__EACH:
				return each != null;
			case CstPackage.MODEL_EXPRESSION__AFTER:
				return after != null;
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (body: "); //$NON-NLS-1$
		result.append(body);
		result.append(')');
		return result.toString();
	}

} // ModelExpressionImpl
