/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
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

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getInput <em> Input</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getExtends <em> Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getImports <em> Imports</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getOwnedModuleElement <em>Owned Module
 * Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleImpl extends EPackageImpl implements Module {
	/**
	 * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected EList<TypedModel> input;

	/**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected EList<Module> extends_;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<Module> imports;

	/**
	 * The cached value of the '{@link #getOwnedModuleElement() <em>Owned Module Element</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwnedModuleElement()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleElement> ownedModuleElement;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.MODULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TypedModel> getInput() {
		if (input == null) {
			input = new EObjectContainmentEList<TypedModel>(TypedModel.class, this, MtlPackage.MODULE__INPUT);
		}
		return input;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Module> getExtends() {
		if (extends_ == null) {
			extends_ = new EObjectResolvingEList<Module>(Module.class, this, MtlPackage.MODULE__EXTENDS);
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Module> getImports() {
		if (imports == null) {
			imports = new EObjectResolvingEList<Module>(Module.class, this, MtlPackage.MODULE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ModuleElement> getOwnedModuleElement() {
		if (ownedModuleElement == null) {
			ownedModuleElement = new EObjectContainmentEList<ModuleElement>(ModuleElement.class, this,
					MtlPackage.MODULE__OWNED_MODULE_ELEMENT);
		}
		return ownedModuleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.MODULE__INPUT:
				return ((InternalEList<?>)getInput()).basicRemove(otherEnd, msgs);
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				return ((InternalEList<?>)getOwnedModuleElement()).basicRemove(otherEnd, msgs);
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
			case MtlPackage.MODULE__INPUT:
				return getInput();
			case MtlPackage.MODULE__EXTENDS:
				return getExtends();
			case MtlPackage.MODULE__IMPORTS:
				return getImports();
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				return getOwnedModuleElement();
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
			case MtlPackage.MODULE__INPUT:
				getInput().clear();
				getInput().addAll((Collection<? extends TypedModel>)newValue);
				return;
			case MtlPackage.MODULE__EXTENDS:
				getExtends().clear();
				getExtends().addAll((Collection<? extends Module>)newValue);
				return;
			case MtlPackage.MODULE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends Module>)newValue);
				return;
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				getOwnedModuleElement().clear();
				getOwnedModuleElement().addAll((Collection<? extends ModuleElement>)newValue);
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
			case MtlPackage.MODULE__INPUT:
				getInput().clear();
				return;
			case MtlPackage.MODULE__EXTENDS:
				getExtends().clear();
				return;
			case MtlPackage.MODULE__IMPORTS:
				getImports().clear();
				return;
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				getOwnedModuleElement().clear();
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
			case MtlPackage.MODULE__INPUT:
				return input != null && !input.isEmpty();
			case MtlPackage.MODULE__EXTENDS:
				return extends_ != null && !extends_.isEmpty();
			case MtlPackage.MODULE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				return ownedModuleElement != null && !ownedModuleElement.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ModuleImpl
