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

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.acceleo.traceability.minimal.MinimalEObjectImpl;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Model</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getModules <em>Modules</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getGeneratedFiles <em>Generated
 * Files</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl#getModelFiles <em>Model Files</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TraceabilityModelImpl extends MinimalEObjectImpl.Container implements TraceabilityModel {
	/**
	 * The cached value of the '{@link #getModules() <em>Modules</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModules()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleFile> modules;

	/**
	 * The cached value of the '{@link #getGeneratedFiles() <em>Generated Files</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getGeneratedFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<GeneratedFile> generatedFiles;

	/**
	 * The cached value of the '{@link #getModelFiles() <em>Model Files</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getModelFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<ModelFile> modelFiles;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TraceabilityModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.TRACEABILITY_MODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleFile> getModules() {
		if (modules == null) {
			modules = new EObjectContainmentEList<ModuleFile>(ModuleFile.class, this, TraceabilityPackage.TRACEABILITY_MODEL__MODULES);
		}
		return modules;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GeneratedFile> getGeneratedFiles() {
		if (generatedFiles == null) {
			generatedFiles = new EObjectContainmentEList<GeneratedFile>(GeneratedFile.class, this, TraceabilityPackage.TRACEABILITY_MODEL__GENERATED_FILES);
		}
		return generatedFiles;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModelFile> getModelFiles() {
		if (modelFiles == null) {
			modelFiles = new EObjectContainmentEList<ModelFile>(ModelFile.class, this, TraceabilityPackage.TRACEABILITY_MODEL__MODEL_FILES);
		}
		return modelFiles;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GeneratedFile getGeneratedFile(String filePath) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GeneratedFile getGeneratedFile(ModuleElement moduleElement) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFile getGenerationModule(String moduleURI) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFile getGenerationModule(Resource resource) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFile getInputModel(String modelURI) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFile getInputModel(Resource resource) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TraceabilityPackage.TRACEABILITY_MODEL__MODULES:
				return ((InternalEList<?>)getModules()).basicRemove(otherEnd, msgs);
			case TraceabilityPackage.TRACEABILITY_MODEL__GENERATED_FILES:
				return ((InternalEList<?>)getGeneratedFiles()).basicRemove(otherEnd, msgs);
			case TraceabilityPackage.TRACEABILITY_MODEL__MODEL_FILES:
				return ((InternalEList<?>)getModelFiles()).basicRemove(otherEnd, msgs);
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
			case TraceabilityPackage.TRACEABILITY_MODEL__MODULES:
				return getModules();
			case TraceabilityPackage.TRACEABILITY_MODEL__GENERATED_FILES:
				return getGeneratedFiles();
			case TraceabilityPackage.TRACEABILITY_MODEL__MODEL_FILES:
				return getModelFiles();
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
			case TraceabilityPackage.TRACEABILITY_MODEL__MODULES:
				getModules().clear();
				getModules().addAll((Collection<? extends ModuleFile>)newValue);
				return;
			case TraceabilityPackage.TRACEABILITY_MODEL__GENERATED_FILES:
				getGeneratedFiles().clear();
				getGeneratedFiles().addAll((Collection<? extends GeneratedFile>)newValue);
				return;
			case TraceabilityPackage.TRACEABILITY_MODEL__MODEL_FILES:
				getModelFiles().clear();
				getModelFiles().addAll((Collection<? extends ModelFile>)newValue);
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
			case TraceabilityPackage.TRACEABILITY_MODEL__MODULES:
				getModules().clear();
				return;
			case TraceabilityPackage.TRACEABILITY_MODEL__GENERATED_FILES:
				getGeneratedFiles().clear();
				return;
			case TraceabilityPackage.TRACEABILITY_MODEL__MODEL_FILES:
				getModelFiles().clear();
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
			case TraceabilityPackage.TRACEABILITY_MODEL__MODULES:
				return modules != null && !modules.isEmpty();
			case TraceabilityPackage.TRACEABILITY_MODEL__GENERATED_FILES:
				return generatedFiles != null && !generatedFiles.isEmpty();
			case TraceabilityPackage.TRACEABILITY_MODEL__MODEL_FILES:
				return modelFiles != null && !modelFiles.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // TraceabilityModelImpl
