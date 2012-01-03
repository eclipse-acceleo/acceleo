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

import java.util.Collection;

import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Model File</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.ModelFileImpl#getInputElements <em>Input Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModelFileImpl extends ResourceImpl implements ModelFile {
	/**
	 * The cached value of the '{@link #getInputElements() <em>Input Elements</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getInputElements()
	 * @generated
	 * @ordered
	 */
	protected EList<InputElement> inputElements;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.MODEL_FILE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InputElement> getInputElements() {
		if (inputElements == null) {
			inputElements = new EObjectContainmentEList<InputElement>(InputElement.class, this, TraceabilityPackage.MODEL_FILE__INPUT_ELEMENTS);
		}
		return inputElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TraceabilityPackage.MODEL_FILE__INPUT_ELEMENTS:
				return ((InternalEList<?>)getInputElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.MODEL_FILE__INPUT_ELEMENTS:
				return getInputElements();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.MODEL_FILE__INPUT_ELEMENTS:
				getInputElements().clear();
				getInputElements().addAll((Collection<? extends InputElement>)newValue);
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
			case TraceabilityPackage.MODEL_FILE__INPUT_ELEMENTS:
				getInputElements().clear();
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
			case TraceabilityPackage.MODEL_FILE__INPUT_ELEMENTS:
				return inputElements != null && !inputElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ModelFileImpl
