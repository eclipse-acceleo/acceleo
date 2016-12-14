/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import java.util.Collection;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ParameterDocumentation;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Element Documentation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ModuleElementDocumentationImpl#getParameterDocumentation <em>Parameter Documentation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleElementDocumentationImpl extends DocumentationImpl implements
		ModuleElementDocumentation {
	/**
	 * The cached value of the '{@link #getParameterDocumentation() <em>Parameter Documentation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterDocumentation()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterDocumentation> parameterDocumentation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleElementDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.MODULE_ELEMENT_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ParameterDocumentation> getParameterDocumentation() {
		if (parameterDocumentation == null) {
			parameterDocumentation = new EObjectContainmentEList<ParameterDocumentation>(
					ParameterDocumentation.class,
					this,
					AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION);
		}
		return parameterDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
			return ((InternalEList<?>) getParameterDocumentation())
					.basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
			return getParameterDocumentation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
			getParameterDocumentation().clear();
			getParameterDocumentation().addAll(
					(Collection<? extends ParameterDocumentation>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
			getParameterDocumentation().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
			return parameterDocumentation != null
					&& !parameterDocumentation.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ModuleElementDocumentationImpl
