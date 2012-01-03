/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
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

import org.eclipse.acceleo.model.mtl.ModuleElementDocumentation;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ParameterDocumentation;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Element Documentation</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementDocumentationImpl#getParametersDocumentation
 * <em>Parameters Documentation</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleElementDocumentationImpl extends DocumentationImpl implements ModuleElementDocumentation {
	/**
	 * The cached value of the '{@link #getParametersDocumentation() <em>Parameters Documentation</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParametersDocumentation()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterDocumentation> parametersDocumentation;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleElementDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.MODULE_ELEMENT_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ParameterDocumentation> getParametersDocumentation() {
		if (parametersDocumentation == null) {
			parametersDocumentation = new EObjectContainmentEList<ParameterDocumentation>(
					ParameterDocumentation.class, this,
					MtlPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION);
		}
		return parametersDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION:
				return ((InternalEList<?>)getParametersDocumentation()).basicRemove(otherEnd, msgs);
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
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION:
				return getParametersDocumentation();
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
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION:
				getParametersDocumentation().clear();
				getParametersDocumentation().addAll((Collection<? extends ParameterDocumentation>)newValue);
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
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION:
				getParametersDocumentation().clear();
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
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION:
				return parametersDocumentation != null && !parametersDocumentation.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ModuleElementDocumentationImpl
