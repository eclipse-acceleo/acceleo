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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class TraceabilityPackageImpl extends EPackageImpl implements TraceabilityPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass traceabilityModelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelFileEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moduleFileEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generatedFileEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inputElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moduleElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass generatedTextEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iComparableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pathEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TraceabilityPackageImpl() {
		super(eNS_URI, TraceabilityFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TraceabilityPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TraceabilityPackage init() {
		if (isInited) return (TraceabilityPackage)EPackage.Registry.INSTANCE.getEPackage(TraceabilityPackage.eNS_URI);

		// Obtain or create and register package
		TraceabilityPackageImpl theTraceabilityPackage = (TraceabilityPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TraceabilityPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TraceabilityPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTraceabilityPackage.createPackageContents();

		// Initialize created meta-data
		theTraceabilityPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTraceabilityPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TraceabilityPackage.eNS_URI, theTraceabilityPackage);
		return theTraceabilityPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTraceabilityModel() {
		return traceabilityModelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTraceabilityModel_Modules() {
		return (EReference)traceabilityModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTraceabilityModel_GeneratedFiles() {
		return (EReference)traceabilityModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTraceabilityModel_ModelFiles() {
		return (EReference)traceabilityModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResource() {
		return resourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResource_Path() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResource_Name() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResource_Charset() {
		return (EAttribute)resourceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelFile() {
		return modelFileEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelFile_InputElements() {
		return (EReference)modelFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModuleFile() {
		return moduleFileEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModuleFile_ModuleElements() {
		return (EReference)moduleFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGeneratedFile() {
		return generatedFileEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedFile_GeneratedRegions() {
		return (EReference)generatedFileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedFile_SourceElements() {
		return (EReference)generatedFileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedFile_NameRegions() {
		return (EReference)generatedFileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedFile_FileBlock() {
		return (EReference)generatedFileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeneratedFile_Length() {
		return (EAttribute)generatedFileEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInputElement() {
		return inputElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInputElement_ModelElement() {
		return (EReference)inputElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInputElement_Feature() {
		return (EReference)inputElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInputElement_Operation() {
		return (EReference)inputElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModuleElement() {
		return moduleElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModuleElement_ModuleElement() {
		return (EReference)moduleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGeneratedText() {
		return generatedTextEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedText_SourceElement() {
		return (EReference)generatedTextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedText_ModuleElement() {
		return (EReference)generatedTextEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGeneratedText_OutputFile() {
		return (EReference)generatedTextEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeneratedText_StartOffset() {
		return (EAttribute)generatedTextEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGeneratedText_EndOffset() {
		return (EAttribute)generatedTextEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIComparable() {
		return iComparableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getpath() {
		return pathEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TraceabilityFactory getTraceabilityFactory() {
		return (TraceabilityFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		traceabilityModelEClass = createEClass(TRACEABILITY_MODEL);
		createEReference(traceabilityModelEClass, TRACEABILITY_MODEL__MODULES);
		createEReference(traceabilityModelEClass, TRACEABILITY_MODEL__GENERATED_FILES);
		createEReference(traceabilityModelEClass, TRACEABILITY_MODEL__MODEL_FILES);

		resourceEClass = createEClass(RESOURCE);
		createEAttribute(resourceEClass, RESOURCE__PATH);
		createEAttribute(resourceEClass, RESOURCE__NAME);
		createEAttribute(resourceEClass, RESOURCE__CHARSET);

		modelFileEClass = createEClass(MODEL_FILE);
		createEReference(modelFileEClass, MODEL_FILE__INPUT_ELEMENTS);

		moduleFileEClass = createEClass(MODULE_FILE);
		createEReference(moduleFileEClass, MODULE_FILE__MODULE_ELEMENTS);

		generatedFileEClass = createEClass(GENERATED_FILE);
		createEReference(generatedFileEClass, GENERATED_FILE__GENERATED_REGIONS);
		createEReference(generatedFileEClass, GENERATED_FILE__SOURCE_ELEMENTS);
		createEReference(generatedFileEClass, GENERATED_FILE__NAME_REGIONS);
		createEReference(generatedFileEClass, GENERATED_FILE__FILE_BLOCK);
		createEAttribute(generatedFileEClass, GENERATED_FILE__LENGTH);

		inputElementEClass = createEClass(INPUT_ELEMENT);
		createEReference(inputElementEClass, INPUT_ELEMENT__MODEL_ELEMENT);
		createEReference(inputElementEClass, INPUT_ELEMENT__FEATURE);
		createEReference(inputElementEClass, INPUT_ELEMENT__OPERATION);

		moduleElementEClass = createEClass(MODULE_ELEMENT);
		createEReference(moduleElementEClass, MODULE_ELEMENT__MODULE_ELEMENT);

		generatedTextEClass = createEClass(GENERATED_TEXT);
		createEReference(generatedTextEClass, GENERATED_TEXT__SOURCE_ELEMENT);
		createEReference(generatedTextEClass, GENERATED_TEXT__MODULE_ELEMENT);
		createEReference(generatedTextEClass, GENERATED_TEXT__OUTPUT_FILE);
		createEAttribute(generatedTextEClass, GENERATED_TEXT__START_OFFSET);
		createEAttribute(generatedTextEClass, GENERATED_TEXT__END_OFFSET);

		iComparableEClass = createEClass(ICOMPARABLE);

		// Create data types
		pathEDataType = createEDataType(PATH);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters
		ETypeParameter iComparableEClass_T = addETypeParameter(iComparableEClass, "T"); //$NON-NLS-1$

		// Set bounds for type parameters

		// Add supertypes to classes
		modelFileEClass.getESuperTypes().add(this.getResource());
		moduleFileEClass.getESuperTypes().add(this.getResource());
		generatedFileEClass.getESuperTypes().add(this.getResource());
		EGenericType g1 = createEGenericType(this.getIComparable());
		EGenericType g2 = createEGenericType(this.getGeneratedText());
		g1.getETypeArguments().add(g2);
		generatedTextEClass.getEGenericSuperTypes().add(g1);

		// Initialize classes and features; add operations and parameters
		initEClass(traceabilityModelEClass, TraceabilityModel.class, "TraceabilityModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getTraceabilityModel_Modules(), this.getModuleFile(), null, "modules", null, 0, -1, TraceabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTraceabilityModel_GeneratedFiles(), this.getGeneratedFile(), null, "generatedFiles", null, 0, -1, TraceabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getTraceabilityModel_ModelFiles(), this.getModelFile(), null, "modelFiles", null, 0, -1, TraceabilityModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		EOperation op = addEOperation(traceabilityModelEClass, this.getGeneratedFile(), "getGeneratedFile", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theEcorePackage.getEString(), "filePath", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(traceabilityModelEClass, this.getGeneratedFile(), "getGeneratedFile", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getModuleElement(), "moduleElement", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(traceabilityModelEClass, this.getModuleFile(), "getGenerationModule", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theEcorePackage.getEString(), "moduleURI", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(traceabilityModelEClass, this.getModuleFile(), "getGenerationModule", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theEcorePackage.getEResource(), "resource", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(traceabilityModelEClass, this.getModelFile(), "getInputModel", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theEcorePackage.getEString(), "modelURI", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(traceabilityModelEClass, this.getModelFile(), "getInputModel", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, theEcorePackage.getEResource(), "resource", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getResource_Path(), this.getpath(), "path", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getResource_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getResource_Charset(), theEcorePackage.getEString(), "charset", null, 0, 1, Resource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(modelFileEClass, ModelFile.class, "ModelFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getModelFile_InputElements(), this.getInputElement(), null, "InputElements", null, 0, -1, ModelFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleFileEClass, ModuleFile.class, "ModuleFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getModuleFile_ModuleElements(), this.getModuleElement(), null, "moduleElements", null, 0, -1, ModuleFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(generatedFileEClass, GeneratedFile.class, "GeneratedFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getGeneratedFile_GeneratedRegions(), this.getGeneratedText(), this.getGeneratedText_OutputFile(), "generatedRegions", null, 0, -1, GeneratedFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneratedFile_SourceElements(), this.getInputElement(), null, "sourceElements", null, 0, -1, GeneratedFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneratedFile_NameRegions(), this.getGeneratedText(), null, "nameRegions", null, 0, -1, GeneratedFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneratedFile_FileBlock(), this.getModuleElement(), null, "fileBlock", null, 0, 1, GeneratedFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGeneratedFile_Length(), theEcorePackage.getEInt(), "length", null, 0, 1, GeneratedFile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(inputElementEClass, InputElement.class, "InputElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getInputElement_ModelElement(), ecorePackage.getEObject(), null, "modelElement", null, 0, 1, InputElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInputElement_Feature(), theEcorePackage.getEStructuralFeature(), null, "feature", null, 0, 1, InputElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getInputElement_Operation(), theEcorePackage.getEOperation(), null, "operation", null, 0, 1, InputElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleElementEClass, ModuleElement.class, "ModuleElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getModuleElement_ModuleElement(), theEcorePackage.getEObject(), null, "moduleElement", null, 0, 1, ModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(generatedTextEClass, GeneratedText.class, "GeneratedText", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getGeneratedText_SourceElement(), this.getInputElement(), null, "sourceElement", null, 0, 1, GeneratedText.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneratedText_ModuleElement(), this.getModuleElement(), null, "moduleElement", null, 0, 1, GeneratedText.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getGeneratedText_OutputFile(), this.getGeneratedFile(), this.getGeneratedFile_GeneratedRegions(), "outputFile", null, 0, 1, GeneratedText.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGeneratedText_StartOffset(), theEcorePackage.getEInt(), "startOffset", null, 0, 1, GeneratedText.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getGeneratedText_EndOffset(), theEcorePackage.getEInt(), "endOffset", null, 0, 1, GeneratedText.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		op = addEOperation(generatedTextEClass, theEcorePackage.getEInt(), "compareTo", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		addEParameter(op, this.getGeneratedText(), "other", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(iComparableEClass, Comparable.class, "IComparable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		op = addEOperation(iComparableEClass, theEcorePackage.getEInt(), "compareTo", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$
		g1 = createEGenericType(iComparableEClass_T);
		addEParameter(op, g1, "other", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		// Initialize data types
		initEDataType(pathEDataType, String.class, "path", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} // TraceabilityPackageImpl
