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

import org.eclipse.acceleo.traceability.*;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.Resource;
import org.eclipse.acceleo.traceability.TraceabilityFactory;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.acceleo.traceability.spec.GeneratedFileSpec;
import org.eclipse.acceleo.traceability.spec.GeneratedTextSpec;
import org.eclipse.acceleo.traceability.spec.InputElementSpec;
import org.eclipse.acceleo.traceability.spec.TraceabilityModelSpec;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class TraceabilityFactoryImpl extends EFactoryImpl implements TraceabilityFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static TraceabilityFactory init() {
		try {
			TraceabilityFactory theTraceabilityFactory = (TraceabilityFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/acceleo/traceability/1.0"); //$NON-NLS-1$ 
			if (theTraceabilityFactory != null) {
				return theTraceabilityFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TraceabilityFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TraceabilityFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TraceabilityPackage.TRACEABILITY_MODEL: return createTraceabilityModel();
			case TraceabilityPackage.RESOURCE: return createResource();
			case TraceabilityPackage.MODEL_FILE: return createModelFile();
			case TraceabilityPackage.MODULE_FILE: return createModuleFile();
			case TraceabilityPackage.GENERATED_FILE: return createGeneratedFile();
			case TraceabilityPackage.INPUT_ELEMENT: return createInputElement();
			case TraceabilityPackage.MODULE_ELEMENT: return createModuleElement();
			case TraceabilityPackage.GENERATED_TEXT: return createGeneratedText();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TraceabilityPackage.PATH:
				return createpathFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TraceabilityPackage.PATH:
				return convertpathToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public TraceabilityModel createTraceabilityModel() {
		TraceabilityModelImpl traceabilityModel = new TraceabilityModelSpec();
		return traceabilityModel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFile createModelFile() {
		ModelFileImpl modelFile = new ModelFileImpl();
		return modelFile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFile createModuleFile() {
		ModuleFileImpl moduleFile = new ModuleFileImpl();
		return moduleFile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public GeneratedFile createGeneratedFile() {
		GeneratedFileImpl generatedFile = new GeneratedFileSpec();
		return generatedFile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public InputElement createInputElement() {
		InputElementImpl inputElement = new InputElementSpec();
		return inputElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElement createModuleElement() {
		ModuleElementImpl moduleElement = new ModuleElementImpl();
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public GeneratedText createGeneratedText() {
		GeneratedTextImpl generatedText = new GeneratedTextSpec();
		return generatedText;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String createpathFromString(EDataType eDataType, String initialValue) {
		return (String)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertpathToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TraceabilityPackage getTraceabilityPackage() {
		return (TraceabilityPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TraceabilityPackage getPackage() {
		return TraceabilityPackage.eINSTANCE;
	}

} // TraceabilityFactoryImpl
