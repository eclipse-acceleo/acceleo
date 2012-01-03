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
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.traceability.TraceabilityFactory
 * @model kind="package"
 * @generated
 */
public interface TraceabilityPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "traceability"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/traceability/1.0"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "traceability"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	TraceabilityPackage eINSTANCE = org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getTraceabilityModel()
	 * @generated
	 */
	int TRACEABILITY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Modules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACEABILITY_MODEL__MODULES = 0;

	/**
	 * The feature id for the '<em><b>Generated Files</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRACEABILITY_MODEL__GENERATED_FILES = 1;

	/**
	 * The feature id for the '<em><b>Model Files</b></em>' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACEABILITY_MODEL__MODEL_FILES = 2;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACEABILITY_MODEL_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.ResourceImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 1;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__PATH = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__CHARSET = 2;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.ModelFileImpl <em>Model File</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.ModelFileImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getModelFile()
	 * @generated
	 */
	int MODEL_FILE = 2;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FILE__PATH = RESOURCE__PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FILE__NAME = RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_FILE__CHARSET = RESOURCE__CHARSET;

	/**
	 * The feature id for the '<em><b>Input Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FILE__INPUT_ELEMENTS = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Model File</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FILE_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.ModuleFileImpl <em>Module File</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.ModuleFileImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getModuleFile()
	 * @generated
	 */
	int MODULE_FILE = 3;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILE__PATH = RESOURCE__PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILE__NAME = RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_FILE__CHARSET = RESOURCE__CHARSET;

	/**
	 * The feature id for the '<em><b>Module Elements</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_FILE__MODULE_ELEMENTS = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Module File</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILE_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl <em>Generated File</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.GeneratedFileImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getGeneratedFile()
	 * @generated
	 */
	int GENERATED_FILE = 4;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__PATH = RESOURCE__PATH;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__NAME = RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Charset</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__CHARSET = RESOURCE__CHARSET;

	/**
	 * The feature id for the '<em><b>Generated Regions</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__GENERATED_REGIONS = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source Elements</b></em>' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__SOURCE_ELEMENTS = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__NAME_REGIONS = RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>File Block</b></em>' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__FILE_BLOCK = RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE__LENGTH = RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Generated File</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_FILE_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.InputElementImpl <em>Input Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.InputElementImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getInputElement()
	 * @generated
	 */
	int INPUT_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Model Element</b></em>' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_ELEMENT__MODEL_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INPUT_ELEMENT__FEATURE = 1;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INPUT_ELEMENT__OPERATION = 2;

	/**
	 * The number of structural features of the '<em>Input Element</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.ModuleElementImpl <em>Module Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.ModuleElementImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getModuleElement()
	 * @generated
	 */
	int MODULE_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT__MODULE_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>Module Element</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link java.lang.Comparable <em>IComparable</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see java.lang.Comparable
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getIComparable()
	 * @generated
	 */
	int ICOMPARABLE = 8;

	/**
	 * The number of structural features of the '<em>IComparable</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICOMPARABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl <em>Generated Text</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.traceability.impl.GeneratedTextImpl
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getGeneratedText()
	 * @generated
	 */
	int GENERATED_TEXT = 7;

	/**
	 * The feature id for the '<em><b>Source Element</b></em>' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_TEXT__SOURCE_ELEMENT = ICOMPARABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_TEXT__MODULE_ELEMENT = ICOMPARABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output File</b></em>' container reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_TEXT__OUTPUT_FILE = ICOMPARABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_TEXT__START_OFFSET = ICOMPARABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_TEXT__END_OFFSET = ICOMPARABLE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Generated Text</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERATED_TEXT_FEATURE_COUNT = ICOMPARABLE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '<em>path</em>' data type.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getpath()
	 * @generated
	 */
	int PATH = 9;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.TraceabilityModel <em>Model</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.eclipse.acceleo.traceability.TraceabilityModel
	 * @generated
	 */
	EClass getTraceabilityModel();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.traceability.TraceabilityModel#getModules <em>Modules</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Modules</em>'.
	 * @see org.eclipse.acceleo.traceability.TraceabilityModel#getModules()
	 * @see #getTraceabilityModel()
	 * @generated
	 */
	EReference getTraceabilityModel_Modules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.traceability.TraceabilityModel#getGeneratedFiles <em>Generated Files</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Generated Files</em>'.
	 * @see org.eclipse.acceleo.traceability.TraceabilityModel#getGeneratedFiles()
	 * @see #getTraceabilityModel()
	 * @generated
	 */
	EReference getTraceabilityModel_GeneratedFiles();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.traceability.TraceabilityModel#getModelFiles <em>Model Files</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Model Files</em>'.
	 * @see org.eclipse.acceleo.traceability.TraceabilityModel#getModelFiles()
	 * @see #getTraceabilityModel()
	 * @generated
	 */
	EReference getTraceabilityModel_ModelFiles();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.eclipse.acceleo.traceability.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.traceability.Resource#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.eclipse.acceleo.traceability.Resource#getPath()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Path();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.traceability.Resource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.traceability.Resource#getName()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.traceability.Resource#getCharset <em>Charset</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Charset</em>'.
	 * @see org.eclipse.acceleo.traceability.Resource#getCharset()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Charset();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.ModelFile <em>Model File</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model File</em>'.
	 * @see org.eclipse.acceleo.traceability.ModelFile
	 * @generated
	 */
	EClass getModelFile();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.traceability.ModelFile#getInputElements <em>Input Elements</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Input Elements</em>'.
	 * @see org.eclipse.acceleo.traceability.ModelFile#getInputElements()
	 * @see #getModelFile()
	 * @generated
	 */
	EReference getModelFile_InputElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.ModuleFile <em>Module File</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module File</em>'.
	 * @see org.eclipse.acceleo.traceability.ModuleFile
	 * @generated
	 */
	EClass getModuleFile();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.traceability.ModuleFile#getModuleElements <em>Module Elements</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Module Elements</em>'.
	 * @see org.eclipse.acceleo.traceability.ModuleFile#getModuleElements()
	 * @see #getModuleFile()
	 * @generated
	 */
	EReference getModuleFile_ModuleElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.GeneratedFile <em>Generated File</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generated File</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile
	 * @generated
	 */
	EClass getGeneratedFile();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.traceability.GeneratedFile#getGeneratedRegions <em>Generated Regions</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Generated Regions</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile#getGeneratedRegions()
	 * @see #getGeneratedFile()
	 * @generated
	 */
	EReference getGeneratedFile_GeneratedRegions();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.acceleo.traceability.GeneratedFile#getSourceElements <em>Source Elements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Elements</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile#getSourceElements()
	 * @see #getGeneratedFile()
	 * @generated
	 */
	EReference getGeneratedFile_SourceElements();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.traceability.GeneratedFile#getNameRegions <em>Name Regions</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Name Regions</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile#getNameRegions()
	 * @see #getGeneratedFile()
	 * @generated
	 */
	EReference getGeneratedFile_NameRegions();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.traceability.GeneratedFile#getFileBlock <em>File Block</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>File Block</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile#getFileBlock()
	 * @see #getGeneratedFile()
	 * @generated
	 */
	EReference getGeneratedFile_FileBlock();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.traceability.GeneratedFile#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile#getLength()
	 * @see #getGeneratedFile()
	 * @generated
	 */
	EAttribute getGeneratedFile_Length();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.InputElement <em>Input Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input Element</em>'.
	 * @see org.eclipse.acceleo.traceability.InputElement
	 * @generated
	 */
	EClass getInputElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.traceability.InputElement#getModelElement <em>Model Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Model Element</em>'.
	 * @see org.eclipse.acceleo.traceability.InputElement#getModelElement()
	 * @see #getInputElement()
	 * @generated
	 */
	EReference getInputElement_ModelElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.traceability.InputElement#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.acceleo.traceability.InputElement#getFeature()
	 * @see #getInputElement()
	 * @generated
	 */
	EReference getInputElement_Feature();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.traceability.InputElement#getOperation <em>Operation</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Operation</em>'.
	 * @see org.eclipse.acceleo.traceability.InputElement#getOperation()
	 * @see #getInputElement()
	 * @generated
	 */
	EReference getInputElement_Operation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.ModuleElement <em>Module Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.traceability.ModuleElement
	 * @generated
	 */
	EClass getModuleElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.traceability.ModuleElement#getModuleElement <em>Module Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.traceability.ModuleElement#getModuleElement()
	 * @see #getModuleElement()
	 * @generated
	 */
	EReference getModuleElement_ModuleElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.traceability.GeneratedText <em>Generated Text</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generated Text</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedText
	 * @generated
	 */
	EClass getGeneratedText();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.traceability.GeneratedText#getSourceElement <em>Source Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Source Element</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedText#getSourceElement()
	 * @see #getGeneratedText()
	 * @generated
	 */
	EReference getGeneratedText_SourceElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.traceability.GeneratedText#getModuleElement <em>Module Element</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedText#getModuleElement()
	 * @see #getGeneratedText()
	 * @generated
	 */
	EReference getGeneratedText_ModuleElement();

	/**
	 * Returns the meta object for the container reference '
	 * {@link org.eclipse.acceleo.traceability.GeneratedText#getOutputFile <em>Output File</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Output File</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedText#getOutputFile()
	 * @see #getGeneratedText()
	 * @generated
	 */
	EReference getGeneratedText_OutputFile();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.traceability.GeneratedText#getStartOffset <em>Start Offset</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start Offset</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedText#getStartOffset()
	 * @see #getGeneratedText()
	 * @generated
	 */
	EAttribute getGeneratedText_StartOffset();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.traceability.GeneratedText#getEndOffset <em>End Offset</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Offset</em>'.
	 * @see org.eclipse.acceleo.traceability.GeneratedText#getEndOffset()
	 * @see #getGeneratedText()
	 * @generated
	 */
	EAttribute getGeneratedText_EndOffset();

	/**
	 * Returns the meta object for class '{@link java.lang.Comparable <em>IComparable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>IComparable</em>'.
	 * @see java.lang.Comparable
	 * @model instanceClass="java.lang.Comparable" typeParameters="T"
	 * @generated
	 */
	EClass getIComparable();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>path</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getpath();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TraceabilityFactory getTraceabilityFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl <em>Model</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityModelImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getTraceabilityModel()
		 * @generated
		 */
		EClass TRACEABILITY_MODEL = eINSTANCE.getTraceabilityModel();

		/**
		 * The meta object literal for the '<em><b>Modules</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRACEABILITY_MODEL__MODULES = eINSTANCE.getTraceabilityModel_Modules();

		/**
		 * The meta object literal for the '<em><b>Generated Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACEABILITY_MODEL__GENERATED_FILES = eINSTANCE.getTraceabilityModel_GeneratedFiles();

		/**
		 * The meta object literal for the '<em><b>Model Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACEABILITY_MODEL__MODEL_FILES = eINSTANCE.getTraceabilityModel_ModelFiles();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.ResourceImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__PATH = eINSTANCE.getResource_Path();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__NAME = eINSTANCE.getResource_Name();

		/**
		 * The meta object literal for the '<em><b>Charset</b></em>' attribute feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__CHARSET = eINSTANCE.getResource_Charset();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.ModelFileImpl <em>Model File</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.ModelFileImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getModelFile()
		 * @generated
		 */
		EClass MODEL_FILE = eINSTANCE.getModelFile();

		/**
		 * The meta object literal for the '<em><b>Input Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_FILE__INPUT_ELEMENTS = eINSTANCE.getModelFile_InputElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.ModuleFileImpl <em>Module File</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.ModuleFileImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getModuleFile()
		 * @generated
		 */
		EClass MODULE_FILE = eINSTANCE.getModuleFile();

		/**
		 * The meta object literal for the '<em><b>Module Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODULE_FILE__MODULE_ELEMENTS = eINSTANCE.getModuleFile_ModuleElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl <em>Generated File</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.GeneratedFileImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getGeneratedFile()
		 * @generated
		 */
		EClass GENERATED_FILE = eINSTANCE.getGeneratedFile();

		/**
		 * The meta object literal for the '<em><b>Generated Regions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERATED_FILE__GENERATED_REGIONS = eINSTANCE.getGeneratedFile_GeneratedRegions();

		/**
		 * The meta object literal for the '<em><b>Source Elements</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GENERATED_FILE__SOURCE_ELEMENTS = eINSTANCE.getGeneratedFile_SourceElements();

		/**
		 * The meta object literal for the '<em><b>Name Regions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERATED_FILE__NAME_REGIONS = eINSTANCE.getGeneratedFile_NameRegions();

		/**
		 * The meta object literal for the '<em><b>File Block</b></em>' reference feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERATED_FILE__FILE_BLOCK = eINSTANCE.getGeneratedFile_FileBlock();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERATED_FILE__LENGTH = eINSTANCE.getGeneratedFile_Length();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.InputElementImpl <em>Input Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.InputElementImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getInputElement()
		 * @generated
		 */
		EClass INPUT_ELEMENT = eINSTANCE.getInputElement();

		/**
		 * The meta object literal for the '<em><b>Model Element</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference INPUT_ELEMENT__MODEL_ELEMENT = eINSTANCE.getInputElement_ModelElement();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INPUT_ELEMENT__FEATURE = eINSTANCE.getInputElement_Feature();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' reference feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INPUT_ELEMENT__OPERATION = eINSTANCE.getInputElement_Operation();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.ModuleElementImpl <em>Module Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.ModuleElementImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getModuleElement()
		 * @generated
		 */
		EClass MODULE_ELEMENT = eINSTANCE.getModuleElement();

		/**
		 * The meta object literal for the '<em><b>Module Element</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODULE_ELEMENT__MODULE_ELEMENT = eINSTANCE.getModuleElement_ModuleElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl <em>Generated Text</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.traceability.impl.GeneratedTextImpl
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getGeneratedText()
		 * @generated
		 */
		EClass GENERATED_TEXT = eINSTANCE.getGeneratedText();

		/**
		 * The meta object literal for the '<em><b>Source Element</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GENERATED_TEXT__SOURCE_ELEMENT = eINSTANCE.getGeneratedText_SourceElement();

		/**
		 * The meta object literal for the '<em><b>Module Element</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GENERATED_TEXT__MODULE_ELEMENT = eINSTANCE.getGeneratedText_ModuleElement();

		/**
		 * The meta object literal for the '<em><b>Output File</b></em>' container reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GENERATED_TEXT__OUTPUT_FILE = eINSTANCE.getGeneratedText_OutputFile();

		/**
		 * The meta object literal for the '<em><b>Start Offset</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GENERATED_TEXT__START_OFFSET = eINSTANCE.getGeneratedText_StartOffset();

		/**
		 * The meta object literal for the '<em><b>End Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERATED_TEXT__END_OFFSET = eINSTANCE.getGeneratedText_EndOffset();

		/**
		 * The meta object literal for the '{@link java.lang.Comparable <em>IComparable</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see java.lang.Comparable
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getIComparable()
		 * @generated
		 */
		EClass ICOMPARABLE = eINSTANCE.getIComparable();

		/**
		 * The meta object literal for the '<em>path</em>' data type.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.acceleo.traceability.impl.TraceabilityPackageImpl#getpath()
		 * @generated
		 */
		EDataType PATH = eINSTANCE.getpath();

	}

} // TraceabilityPackage
