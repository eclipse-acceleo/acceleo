/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleowizardmodelPackageImpl.java,v 1.5 2011/04/19 13:28:36 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AcceleowizardmodelPackageImpl extends EPackageImpl implements AcceleowizardmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoUIProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoModuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoModuleElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoMainClassEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoPackageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoPomEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass acceleoPomDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum moduleElementKindEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AcceleowizardmodelPackageImpl() {
		super(eNS_URI, AcceleowizardmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link AcceleowizardmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AcceleowizardmodelPackage init() {
		if (isInited) return (AcceleowizardmodelPackage)EPackage.Registry.INSTANCE.getEPackage(AcceleowizardmodelPackage.eNS_URI);

		// Obtain or create and register package
		AcceleowizardmodelPackageImpl theAcceleowizardmodelPackage = (AcceleowizardmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AcceleowizardmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AcceleowizardmodelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theAcceleowizardmodelPackage.createPackageContents();

		// Initialize created meta-data
		theAcceleowizardmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAcceleowizardmodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AcceleowizardmodelPackage.eNS_URI, theAcceleowizardmodelPackage);
		return theAcceleowizardmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoProject() {
		return acceleoProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoProject_Name() {
		return (EAttribute)acceleoProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoProject_GeneratorName() {
		return (EAttribute)acceleoProjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceleoProject_AcceleoModules() {
		return (EReference)acceleoProjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoProject_PluginDependencies() {
		return (EAttribute)acceleoProjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoProject_ExportedPackages() {
		return (EAttribute)acceleoProjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoProject_Jre() {
		return (EAttribute)acceleoProjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoUIProject() {
		return acceleoUIProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_Name() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_GeneratorName() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_PluginsDependencies() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_Modules() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_ModulePlugins() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_ModuleJavaClass() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_ModelNameFilter() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoUIProject_TargetFolderRelativePath() {
		return (EAttribute)acceleoUIProjectEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoModule() {
		return acceleoModuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_ProjectName() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_ParentFolder() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_Name() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_MetamodelURIs() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceleoModule_ModuleElement() {
		return (EReference)acceleoModuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_GenerateDocumentation() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_IsInitialized() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_InitializationKind() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModule_InitializationPath() {
		return (EAttribute)acceleoModuleEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoModuleElement() {
		return acceleoModuleElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModuleElement_Name() {
		return (EAttribute)acceleoModuleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModuleElement_ParameterType() {
		return (EAttribute)acceleoModuleElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModuleElement_Kind() {
		return (EAttribute)acceleoModuleElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModuleElement_IsMain() {
		return (EAttribute)acceleoModuleElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoModuleElement_GenerateFile() {
		return (EAttribute)acceleoModuleElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoMainClass() {
		return acceleoMainClassEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoMainClass_ProjectName() {
		return (EAttribute)acceleoMainClassEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoMainClass_BasePackage() {
		return (EAttribute)acceleoMainClassEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoMainClass_ClassShortName() {
		return (EAttribute)acceleoMainClassEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoMainClass_ModuleFileShortName() {
		return (EAttribute)acceleoMainClassEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoMainClass_TemplateNames() {
		return (EAttribute)acceleoMainClassEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceleoMainClass_Packages() {
		return (EReference)acceleoMainClassEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoPackage() {
		return acceleoPackageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPackage_Class() {
		return (EAttribute)acceleoPackageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPackage_Path() {
		return (EAttribute)acceleoPackageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoPom() {
		return acceleoPomEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPom_ArtifactId() {
		return (EAttribute)acceleoPomEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAcceleoPom_Dependencies() {
		return (EReference)acceleoPomEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAcceleoPomDependency() {
		return acceleoPomDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPomDependency_GroupId() {
		return (EAttribute)acceleoPomDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPomDependency_ArtifactId() {
		return (EAttribute)acceleoPomDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPomDependency_Version() {
		return (EAttribute)acceleoPomDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoPomDependency_SystemPath() {
		return (EAttribute)acceleoPomDependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAcceleoMainClass_ResolvedClassPath() {
		return (EAttribute)acceleoMainClassEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getModuleElementKind() {
		return moduleElementKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleowizardmodelFactory getAcceleowizardmodelFactory() {
		return (AcceleowizardmodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		acceleoProjectEClass = createEClass(ACCELEO_PROJECT);
		createEAttribute(acceleoProjectEClass, ACCELEO_PROJECT__NAME);
		createEAttribute(acceleoProjectEClass, ACCELEO_PROJECT__GENERATOR_NAME);
		createEReference(acceleoProjectEClass, ACCELEO_PROJECT__ACCELEO_MODULES);
		createEAttribute(acceleoProjectEClass, ACCELEO_PROJECT__PLUGIN_DEPENDENCIES);
		createEAttribute(acceleoProjectEClass, ACCELEO_PROJECT__EXPORTED_PACKAGES);
		createEAttribute(acceleoProjectEClass, ACCELEO_PROJECT__JRE);

		acceleoUIProjectEClass = createEClass(ACCELEO_UI_PROJECT);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__NAME);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__GENERATOR_NAME);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__MODULES);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__MODULE_PLUGINS);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__MODEL_NAME_FILTER);
		createEAttribute(acceleoUIProjectEClass, ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH);

		acceleoModuleEClass = createEClass(ACCELEO_MODULE);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__PROJECT_NAME);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__PARENT_FOLDER);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__NAME);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__METAMODEL_UR_IS);
		createEReference(acceleoModuleEClass, ACCELEO_MODULE__MODULE_ELEMENT);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__GENERATE_DOCUMENTATION);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__IS_INITIALIZED);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__INITIALIZATION_KIND);
		createEAttribute(acceleoModuleEClass, ACCELEO_MODULE__INITIALIZATION_PATH);

		acceleoModuleElementEClass = createEClass(ACCELEO_MODULE_ELEMENT);
		createEAttribute(acceleoModuleElementEClass, ACCELEO_MODULE_ELEMENT__NAME);
		createEAttribute(acceleoModuleElementEClass, ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE);
		createEAttribute(acceleoModuleElementEClass, ACCELEO_MODULE_ELEMENT__KIND);
		createEAttribute(acceleoModuleElementEClass, ACCELEO_MODULE_ELEMENT__IS_MAIN);
		createEAttribute(acceleoModuleElementEClass, ACCELEO_MODULE_ELEMENT__GENERATE_FILE);

		acceleoMainClassEClass = createEClass(ACCELEO_MAIN_CLASS);
		createEAttribute(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__PROJECT_NAME);
		createEAttribute(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__BASE_PACKAGE);
		createEAttribute(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME);
		createEAttribute(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME);
		createEAttribute(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__TEMPLATE_NAMES);
		createEAttribute(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH);
		createEReference(acceleoMainClassEClass, ACCELEO_MAIN_CLASS__PACKAGES);

		acceleoPackageEClass = createEClass(ACCELEO_PACKAGE);
		createEAttribute(acceleoPackageEClass, ACCELEO_PACKAGE__CLASS);
		createEAttribute(acceleoPackageEClass, ACCELEO_PACKAGE__PATH);

		acceleoPomEClass = createEClass(ACCELEO_POM);
		createEAttribute(acceleoPomEClass, ACCELEO_POM__ARTIFACT_ID);
		createEReference(acceleoPomEClass, ACCELEO_POM__DEPENDENCIES);

		acceleoPomDependencyEClass = createEClass(ACCELEO_POM_DEPENDENCY);
		createEAttribute(acceleoPomDependencyEClass, ACCELEO_POM_DEPENDENCY__GROUP_ID);
		createEAttribute(acceleoPomDependencyEClass, ACCELEO_POM_DEPENDENCY__ARTIFACT_ID);
		createEAttribute(acceleoPomDependencyEClass, ACCELEO_POM_DEPENDENCY__VERSION);
		createEAttribute(acceleoPomDependencyEClass, ACCELEO_POM_DEPENDENCY__SYSTEM_PATH);

		// Create enums
		moduleElementKindEEnum = createEEnum(MODULE_ELEMENT_KIND);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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

		// Set bounds for type parameters

		// Add supertypes to classes
		acceleoProjectEClass.getESuperTypes().add(theEcorePackage.getEObject());
		acceleoUIProjectEClass.getESuperTypes().add(theEcorePackage.getEObject());
		acceleoModuleEClass.getESuperTypes().add(theEcorePackage.getEObject());
		acceleoModuleElementEClass.getESuperTypes().add(theEcorePackage.getEObject());
		acceleoMainClassEClass.getESuperTypes().add(theEcorePackage.getEObject());

		// Initialize classes and features; add operations and parameters
		initEClass(acceleoProjectEClass, AcceleoProject.class, "AcceleoProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoProject_Name(), ecorePackage.getEString(), "name", null, 1, 1, AcceleoProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoProject_GeneratorName(), ecorePackage.getEString(), "generatorName", "", 1, 1, AcceleoProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getAcceleoProject_AcceleoModules(), this.getAcceleoModule(), null, "acceleoModules", null, 0, -1, AcceleoProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoProject_PluginDependencies(), theEcorePackage.getEString(), "pluginDependencies", null, 0, -1, AcceleoProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoProject_ExportedPackages(), ecorePackage.getEString(), "exportedPackages", null, 0, -1, AcceleoProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoProject_Jre(), theEcorePackage.getEString(), "jre", null, 0, 1, AcceleoProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceleoUIProjectEClass, AcceleoUIProject.class, "AcceleoUIProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_Name(), ecorePackage.getEString(), "name", null, 1, 1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_GeneratorName(), ecorePackage.getEString(), "generatorName", null, 1, 1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_PluginsDependencies(), ecorePackage.getEString(), "pluginsDependencies", null, 0, -1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_Modules(), ecorePackage.getEString(), "modules", null, 0, -1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_ModulePlugins(), ecorePackage.getEString(), "modulePlugins", null, 0, -1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_ModuleJavaClass(), ecorePackage.getEString(), "moduleJavaClass", null, 0, -1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_ModelNameFilter(), ecorePackage.getEString(), "modelNameFilter", null, 1, 1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoUIProject_TargetFolderRelativePath(), ecorePackage.getEString(), "targetFolderRelativePath", null, 1, 1, AcceleoUIProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceleoModuleEClass, AcceleoModule.class, "AcceleoModule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_ProjectName(), ecorePackage.getEString(), "projectName", null, 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_ParentFolder(), ecorePackage.getEString(), "parentFolder", null, 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_Name(), ecorePackage.getEString(), "name", null, 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_MetamodelURIs(), ecorePackage.getEString(), "metamodelURIs", null, 1, -1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAcceleoModule_ModuleElement(), this.getAcceleoModuleElement(), null, "moduleElement", null, 0, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_GenerateDocumentation(), ecorePackage.getEBoolean(), "generateDocumentation", null, 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_IsInitialized(), ecorePackage.getEBoolean(), "isInitialized", "false", 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getAcceleoModule_InitializationKind(), ecorePackage.getEString(), "initializationKind", null, 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModule_InitializationPath(), ecorePackage.getEString(), "initializationPath", null, 1, 1, AcceleoModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceleoModuleElementEClass, AcceleoModuleElement.class, "AcceleoModuleElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoModuleElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, AcceleoModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModuleElement_ParameterType(), ecorePackage.getEString(), "parameterType", null, 1, 1, AcceleoModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModuleElement_Kind(), this.getModuleElementKind(), "kind", null, 1, 1, AcceleoModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoModuleElement_IsMain(), ecorePackage.getEBoolean(), "isMain", "false", 1, 1, AcceleoModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getAcceleoModuleElement_GenerateFile(), ecorePackage.getEBoolean(), "generateFile", "false", 1, 1, AcceleoModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(acceleoMainClassEClass, AcceleoMainClass.class, "AcceleoMainClass", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoMainClass_ProjectName(), ecorePackage.getEString(), "projectName", null, 1, 1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoMainClass_BasePackage(), ecorePackage.getEString(), "basePackage", null, 1, 1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoMainClass_ClassShortName(), ecorePackage.getEString(), "classShortName", null, 1, 1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoMainClass_ModuleFileShortName(), ecorePackage.getEString(), "moduleFileShortName", null, 1, 1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoMainClass_TemplateNames(), ecorePackage.getEString(), "templateNames", null, 1, -1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoMainClass_ResolvedClassPath(), ecorePackage.getEString(), "resolvedClassPath", null, 1, -1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAcceleoMainClass_Packages(), this.getAcceleoPackage(), null, "packages", null, 1, -1, AcceleoMainClass.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceleoPackageEClass, AcceleoPackage.class, "AcceleoPackage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoPackage_Class(), theEcorePackage.getEString(), "class", null, 1, 1, AcceleoPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoPackage_Path(), theEcorePackage.getEString(), "path", null, 1, 1, AcceleoPackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceleoPomEClass, AcceleoPom.class, "AcceleoPom", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoPom_ArtifactId(), theEcorePackage.getEString(), "artifactId", null, 1, 1, AcceleoPom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getAcceleoPom_Dependencies(), this.getAcceleoPomDependency(), null, "dependencies", null, 0, -1, AcceleoPom.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(acceleoPomDependencyEClass, AcceleoPomDependency.class, "AcceleoPomDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAcceleoPomDependency_GroupId(), theEcorePackage.getEString(), "groupId", null, 1, 1, AcceleoPomDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoPomDependency_ArtifactId(), theEcorePackage.getEString(), "artifactId", null, 1, 1, AcceleoPomDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoPomDependency_Version(), theEcorePackage.getEString(), "version", null, 1, 1, AcceleoPomDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAcceleoPomDependency_SystemPath(), theEcorePackage.getEString(), "systemPath", null, 1, 1, AcceleoPomDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(moduleElementKindEEnum, ModuleElementKind.class, "ModuleElementKind"); //$NON-NLS-1$
		addEEnumLiteral(moduleElementKindEEnum, ModuleElementKind.TEMPLATE);
		addEEnumLiteral(moduleElementKindEEnum, ModuleElementKind.QUERY);

		// Create resource
		createResource(eNS_URI);
	}

} //AcceleowizardmodelPackageImpl
