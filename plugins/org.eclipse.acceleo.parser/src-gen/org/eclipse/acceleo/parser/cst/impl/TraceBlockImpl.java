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
import org.eclipse.acceleo.parser.cst.TraceBlock;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Trace Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TraceBlockImpl#getModelElement <em>Model Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TraceBlockImpl extends BlockImpl implements TraceBlock {
	/**
	 * The cached value of the '{@link #getModelElement() <em>Model Element</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelElement()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression modelElement;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TraceBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.TRACE_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getModelElement() {
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetModelElement(ModelExpression newModelElement, NotificationChain msgs) {
		ModelExpression oldModelElement = modelElement;
		modelElement = newModelElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.TRACE_BLOCK__MODEL_ELEMENT, oldModelElement, newModelElement);
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
	public void setModelElement(ModelExpression newModelElement) {
		if (newModelElement != modelElement) {
			NotificationChain msgs = null;
			if (modelElement != null)
				msgs = ((InternalEObject)modelElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.TRACE_BLOCK__MODEL_ELEMENT, null, msgs);
			if (newModelElement != null)
				msgs = ((InternalEObject)newModelElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.TRACE_BLOCK__MODEL_ELEMENT, null, msgs);
			msgs = basicSetModelElement(newModelElement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TRACE_BLOCK__MODEL_ELEMENT,
					newModelElement, newModelElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.TRACE_BLOCK__MODEL_ELEMENT:
				return basicSetModelElement(null, msgs);
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
			case CstPackage.TRACE_BLOCK__MODEL_ELEMENT:
				return getModelElement();
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
			case CstPackage.TRACE_BLOCK__MODEL_ELEMENT:
				setModelElement((ModelExpression)newValue);
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
			case CstPackage.TRACE_BLOCK__MODEL_ELEMENT:
				setModelElement((ModelExpression)null);
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
			case CstPackage.TRACE_BLOCK__MODEL_ELEMENT:
				return modelElement != null;
		}
		return super.eIsSet(featureID);
	}

} // TraceBlockImpl
