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
package org.eclipse.acceleo.traceability.impl;

import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.acceleo.traceability.minimal.MinimalEObjectImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Input Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.InputElementImpl#getModelElement <em>Model Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.InputElementImpl#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.InputElementImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InputElementImpl extends MinimalEObjectImpl.Container implements InputElement {
	/**
	 * The cached value of the '{@link #getModelElement() <em>Model Element</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelElement()
	 * @generated
	 * @ordered
	 */
	protected EObject modelElement;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature feature;

	/**
	 * The cached value of the '{@link #getOperation() <em>Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected EOperation operation;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InputElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.INPUT_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getModelElement() {
		if (modelElement != null && modelElement.eIsProxy()) {
			InternalEObject oldModelElement = (InternalEObject)modelElement;
			modelElement = eResolveProxy(oldModelElement);
			if (modelElement != oldModelElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.INPUT_ELEMENT__MODEL_ELEMENT, oldModelElement, modelElement));
			}
		}
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetModelElement() {
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelElement(EObject newModelElement) {
		EObject oldModelElement = modelElement;
		modelElement = newModelElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.INPUT_ELEMENT__MODEL_ELEMENT, oldModelElement, modelElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature getFeature() {
		if (feature != null && feature.eIsProxy()) {
			InternalEObject oldFeature = (InternalEObject)feature;
			feature = (EStructuralFeature)eResolveProxy(oldFeature);
			if (feature != oldFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.INPUT_ELEMENT__FEATURE, oldFeature, feature));
			}
		}
		return feature;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeature(EStructuralFeature newFeature) {
		EStructuralFeature oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.INPUT_ELEMENT__FEATURE, oldFeature, feature));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getOperation() {
		if (operation != null && operation.eIsProxy()) {
			InternalEObject oldOperation = (InternalEObject)operation;
			operation = (EOperation)eResolveProxy(oldOperation);
			if (operation != oldOperation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.INPUT_ELEMENT__OPERATION, oldOperation, operation));
			}
		}
		return operation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation basicGetOperation() {
		return operation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperation(EOperation newOperation) {
		EOperation oldOperation = operation;
		operation = newOperation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.INPUT_ELEMENT__OPERATION, oldOperation, operation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.INPUT_ELEMENT__MODEL_ELEMENT:
				if (resolve) return getModelElement();
				return basicGetModelElement();
			case TraceabilityPackage.INPUT_ELEMENT__FEATURE:
				if (resolve) return getFeature();
				return basicGetFeature();
			case TraceabilityPackage.INPUT_ELEMENT__OPERATION:
				if (resolve) return getOperation();
				return basicGetOperation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.INPUT_ELEMENT__MODEL_ELEMENT:
				setModelElement((EObject)newValue);
				return;
			case TraceabilityPackage.INPUT_ELEMENT__FEATURE:
				setFeature((EStructuralFeature)newValue);
				return;
			case TraceabilityPackage.INPUT_ELEMENT__OPERATION:
				setOperation((EOperation)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.INPUT_ELEMENT__MODEL_ELEMENT:
				setModelElement((EObject)null);
				return;
			case TraceabilityPackage.INPUT_ELEMENT__FEATURE:
				setFeature((EStructuralFeature)null);
				return;
			case TraceabilityPackage.INPUT_ELEMENT__OPERATION:
				setOperation((EOperation)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.INPUT_ELEMENT__MODEL_ELEMENT:
				return modelElement != null;
			case TraceabilityPackage.INPUT_ELEMENT__FEATURE:
				return feature != null;
			case TraceabilityPackage.INPUT_ELEMENT__OPERATION:
				return operation != null;
		}
		return super.eIsSet(featureID);
	}

} // InputElementImpl
