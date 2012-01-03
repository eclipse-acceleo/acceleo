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

import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Trace Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TraceBlockImpl#getModelElement <em>Model Element</em>}</li>
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
	protected OCLExpression modelElement;

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
		return MtlPackage.Literals.TRACE_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getModelElement() {
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetModelElement(OCLExpression newModelElement, NotificationChain msgs) {
		OCLExpression oldModelElement = modelElement;
		modelElement = newModelElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.TRACE_BLOCK__MODEL_ELEMENT, oldModelElement, newModelElement);
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
	public void setModelElement(OCLExpression newModelElement) {
		if (newModelElement != modelElement) {
			NotificationChain msgs = null;
			if (modelElement != null)
				msgs = ((InternalEObject)modelElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TRACE_BLOCK__MODEL_ELEMENT, null, msgs);
			if (newModelElement != null)
				msgs = ((InternalEObject)newModelElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TRACE_BLOCK__MODEL_ELEMENT, null, msgs);
			msgs = basicSetModelElement(newModelElement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TRACE_BLOCK__MODEL_ELEMENT,
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
			case MtlPackage.TRACE_BLOCK__MODEL_ELEMENT:
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
			case MtlPackage.TRACE_BLOCK__MODEL_ELEMENT:
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
			case MtlPackage.TRACE_BLOCK__MODEL_ELEMENT:
				setModelElement((OCLExpression)newValue);
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
			case MtlPackage.TRACE_BLOCK__MODEL_ELEMENT:
				setModelElement((OCLExpression)null);
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
			case MtlPackage.TRACE_BLOCK__MODEL_ELEMENT:
				return modelElement != null;
		}
		return super.eIsSet(featureID);
	}

} // TraceBlockImpl
