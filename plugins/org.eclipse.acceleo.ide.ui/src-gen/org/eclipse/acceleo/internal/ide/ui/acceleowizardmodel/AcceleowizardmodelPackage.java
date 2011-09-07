/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleowizardmodelPackage.java,v 1.5 2011/04/19 13:28:36 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

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
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory
 * @model kind="package"
 * @generated
 */
@SuppressWarnings("hiding")
public interface AcceleowizardmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "acceleowizardmodel"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/ui/acceleowizardmodel/3.1"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "acceleowizardmodel"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	AcceleowizardmodelPackage eINSTANCE = org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl <em>Acceleo Project</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoProject()
	 * @generated
	 */
	int ACCELEO_PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT__NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generator Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT__GENERATOR_NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Acceleo Modules</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT__ACCELEO_MODULES = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Plugin Dependencies</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT__PLUGIN_DEPENDENCIES = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Exported Packages</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT__EXPORTED_PACKAGES = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Jre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT__JRE = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Acceleo Project</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PROJECT_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl <em>Acceleo UI Project</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoUIProject()
	 * @generated
	 */
	int ACCELEO_UI_PROJECT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Generator Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__GENERATOR_NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Plugins Dependencies</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Modules</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__MODULES = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Module Plugins</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__MODULE_PLUGINS = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Module Java Class</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Model Name Filter</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__MODEL_NAME_FILTER = EcorePackage.EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Target Folder Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH = EcorePackage.EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Acceleo UI Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_UI_PROJECT_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl <em>Acceleo Module</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoModule()
	 * @generated
	 */
	int ACCELEO_MODULE = 2;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__PROJECT_NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Folder</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__PARENT_FOLDER = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Metamodel UR Is</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__METAMODEL_UR_IS = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Module Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__MODULE_ELEMENT = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Generate Documentation</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__GENERATE_DOCUMENTATION = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Is Initialized</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__IS_INITIALIZED = EcorePackage.EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Initialization Kind</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__INITIALIZATION_KIND = EcorePackage.EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Initialization Path</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE__INITIALIZATION_PATH = EcorePackage.EOBJECT_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Acceleo Module</em>' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl <em>Acceleo Module Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoModuleElement()
	 * @generated
	 */
	int ACCELEO_MODULE_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_ELEMENT__NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Type</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_ELEMENT__KIND = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Main</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_ELEMENT__IS_MAIN = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Generate File</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_ELEMENT__GENERATE_FILE = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Acceleo Module Element</em>' class.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MODULE_ELEMENT_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl <em>Acceleo Main Class</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoMainClass()
	 * @generated
	 */
	int ACCELEO_MAIN_CLASS = 4;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__PROJECT_NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base Package</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__BASE_PACKAGE = EcorePackage.EOBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Class Short Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Module File Short Name</b></em>' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME = EcorePackage.EOBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Template Names</b></em>' attribute list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__TEMPLATE_NAMES = EcorePackage.EOBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Resolved Class Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH = EcorePackage.EOBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Packages</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS__PACKAGES = EcorePackage.EOBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Acceleo Main Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_MAIN_CLASS_FEATURE_COUNT = EcorePackage.EOBJECT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPackageImpl <em>Acceleo Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPackageImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoPackage()
	 * @generated
	 */
	int ACCELEO_PACKAGE = 5;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PACKAGE__CLASS = 0;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PACKAGE__PATH = 1;

	/**
	 * The number of structural features of the '<em>Acceleo Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_PACKAGE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomImpl <em>Acceleo Pom</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoPom()
	 * @generated
	 */
	int ACCELEO_POM = 6;

	/**
	 * The feature id for the '<em><b>Artifact Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM__ARTIFACT_ID = 0;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM__DEPENDENCIES = 1;

	/**
	 * The number of structural features of the '<em>Acceleo Pom</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomDependencyImpl <em>Acceleo Pom Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomDependencyImpl
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoPomDependency()
	 * @generated
	 */
	int ACCELEO_POM_DEPENDENCY = 7;

	/**
	 * The feature id for the '<em><b>Group Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM_DEPENDENCY__GROUP_ID = 0;

	/**
	 * The feature id for the '<em><b>Artifact Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM_DEPENDENCY__ARTIFACT_ID = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM_DEPENDENCY__VERSION = 2;

	/**
	 * The feature id for the '<em><b>System Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM_DEPENDENCY__SYSTEM_PATH = 3;

	/**
	 * The number of structural features of the '<em>Acceleo Pom Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACCELEO_POM_DEPENDENCY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind <em>Module Element Kind</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getModuleElementKind()
	 * @generated
	 */
	int MODULE_ELEMENT_KIND = 8;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject <em>Acceleo Project</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Project</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject
	 * @generated
	 */
	EClass getAcceleoProject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getName()
	 * @see #getAcceleoProject()
	 * @generated
	 */
	EAttribute getAcceleoProject_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getGeneratorName <em>Generator Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generator Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getGeneratorName()
	 * @see #getAcceleoProject()
	 * @generated
	 */
	EAttribute getAcceleoProject_GeneratorName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getAcceleoModules <em>Acceleo Modules</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Acceleo Modules</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getAcceleoModules()
	 * @see #getAcceleoProject()
	 * @generated
	 */
	EReference getAcceleoProject_AcceleoModules();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getPluginDependencies <em>Plugin Dependencies</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Plugin Dependencies</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getPluginDependencies()
	 * @see #getAcceleoProject()
	 * @generated
	 */
	EAttribute getAcceleoProject_PluginDependencies();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getExportedPackages <em>Exported Packages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Exported Packages</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getExportedPackages()
	 * @see #getAcceleoProject()
	 * @generated
	 */
	EAttribute getAcceleoProject_ExportedPackages();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getJre <em>Jre</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Jre</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getJre()
	 * @see #getAcceleoProject()
	 * @generated
	 */
	EAttribute getAcceleoProject_Jre();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject <em>Acceleo UI Project</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo UI Project</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject
	 * @generated
	 */
	EClass getAcceleoUIProject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getName()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getGeneratorName <em>Generator Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generator Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getGeneratorName()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_GeneratorName();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getPluginsDependencies <em>Plugins Dependencies</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Plugins Dependencies</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getPluginsDependencies()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_PluginsDependencies();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModules <em>Modules</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Modules</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModules()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_Modules();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModulePlugins <em>Module Plugins</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Module Plugins</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModulePlugins()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_ModulePlugins();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModuleJavaClass <em>Module Java Class</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Module Java Class</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModuleJavaClass()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_ModuleJavaClass();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModelNameFilter <em>Model Name Filter</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model Name Filter</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModelNameFilter()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_ModelNameFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getTargetFolderRelativePath <em>Target Folder Relative Path</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Folder Relative Path</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getTargetFolderRelativePath()
	 * @see #getAcceleoUIProject()
	 * @generated
	 */
	EAttribute getAcceleoUIProject_TargetFolderRelativePath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule <em>Acceleo Module</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Module</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule
	 * @generated
	 */
	EClass getAcceleoModule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getProjectName()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_ProjectName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getParentFolder <em>Parent Folder</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parent Folder</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getParentFolder()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_ParentFolder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getName()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_Name();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getMetamodelURIs <em>Metamodel UR Is</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Metamodel UR Is</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getMetamodelURIs()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_MetamodelURIs();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getModuleElement <em>Module Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Module Element</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getModuleElement()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EReference getAcceleoModule_ModuleElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#isGenerateDocumentation <em>Generate Documentation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate Documentation</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#isGenerateDocumentation()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_GenerateDocumentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#isIsInitialized <em>Is Initialized</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Initialized</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#isIsInitialized()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_IsInitialized();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getInitializationKind <em>Initialization Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initialization Kind</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getInitializationKind()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_InitializationKind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getInitializationPath <em>Initialization Path</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initialization Path</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getInitializationPath()
	 * @see #getAcceleoModule()
	 * @generated
	 */
	EAttribute getAcceleoModule_InitializationPath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement <em>Acceleo Module Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Module Element</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement
	 * @generated
	 */
	EClass getAcceleoModuleElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getName()
	 * @see #getAcceleoModuleElement()
	 * @generated
	 */
	EAttribute getAcceleoModuleElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter Type</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getParameterType()
	 * @see #getAcceleoModuleElement()
	 * @generated
	 */
	EAttribute getAcceleoModuleElement_ParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getKind()
	 * @see #getAcceleoModuleElement()
	 * @generated
	 */
	EAttribute getAcceleoModuleElement_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isIsMain <em>Is Main</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Main</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isIsMain()
	 * @see #getAcceleoModuleElement()
	 * @generated
	 */
	EAttribute getAcceleoModuleElement_IsMain();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isGenerateFile <em>Generate File</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generate File</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isGenerateFile()
	 * @see #getAcceleoModuleElement()
	 * @generated
	 */
	EAttribute getAcceleoModuleElement_GenerateFile();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass <em>Acceleo Main Class</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Main Class</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass
	 * @generated
	 */
	EClass getAcceleoMainClass();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getProjectName()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EAttribute getAcceleoMainClass_ProjectName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getBasePackage <em>Base Package</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Package</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getBasePackage()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EAttribute getAcceleoMainClass_BasePackage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getClassShortName <em>Class Short Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Short Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getClassShortName()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EAttribute getAcceleoMainClass_ClassShortName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getModuleFileShortName <em>Module File Short Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module File Short Name</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getModuleFileShortName()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EAttribute getAcceleoMainClass_ModuleFileShortName();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getTemplateNames <em>Template Names</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Template Names</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getTemplateNames()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EAttribute getAcceleoMainClass_TemplateNames();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getPackages <em>Packages</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Packages</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getPackages()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EReference getAcceleoMainClass_Packages();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage <em>Acceleo Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Package</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage
	 * @generated
	 */
	EClass getAcceleoPackage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage#getClass_()
	 * @see #getAcceleoPackage()
	 * @generated
	 */
	EAttribute getAcceleoPackage_Class();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage#getPath()
	 * @see #getAcceleoPackage()
	 * @generated
	 */
	EAttribute getAcceleoPackage_Path();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom <em>Acceleo Pom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Pom</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom
	 * @generated
	 */
	EClass getAcceleoPom();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom#getArtifactId <em>Artifact Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Id</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom#getArtifactId()
	 * @see #getAcceleoPom()
	 * @generated
	 */
	EAttribute getAcceleoPom_ArtifactId();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom#getDependencies()
	 * @see #getAcceleoPom()
	 * @generated
	 */
	EReference getAcceleoPom_Dependencies();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency <em>Acceleo Pom Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Acceleo Pom Dependency</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency
	 * @generated
	 */
	EClass getAcceleoPomDependency();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getGroupId <em>Group Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Group Id</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getGroupId()
	 * @see #getAcceleoPomDependency()
	 * @generated
	 */
	EAttribute getAcceleoPomDependency_GroupId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getArtifactId <em>Artifact Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Artifact Id</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getArtifactId()
	 * @see #getAcceleoPomDependency()
	 * @generated
	 */
	EAttribute getAcceleoPomDependency_ArtifactId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getVersion()
	 * @see #getAcceleoPomDependency()
	 * @generated
	 */
	EAttribute getAcceleoPomDependency_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getSystemPath <em>System Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>System Path</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency#getSystemPath()
	 * @see #getAcceleoPomDependency()
	 * @generated
	 */
	EAttribute getAcceleoPomDependency_SystemPath();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getResolvedClassPath <em>Resolved Class Path</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Resolved Class Path</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass#getResolvedClassPath()
	 * @see #getAcceleoMainClass()
	 * @generated
	 */
	EAttribute getAcceleoMainClass_ResolvedClassPath();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind <em>Module Element Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Module Element Kind</em>'.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind
	 * @generated
	 */
	EEnum getModuleElementKind();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AcceleowizardmodelFactory getAcceleowizardmodelFactory();

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
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl <em>Acceleo Project</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoProject()
		 * @generated
		 */
		EClass ACCELEO_PROJECT = eINSTANCE.getAcceleoProject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_PROJECT__NAME = eINSTANCE.getAcceleoProject_Name();

		/**
		 * The meta object literal for the '<em><b>Generator Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_PROJECT__GENERATOR_NAME = eINSTANCE.getAcceleoProject_GeneratorName();

		/**
		 * The meta object literal for the '<em><b>Acceleo Modules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCELEO_PROJECT__ACCELEO_MODULES = eINSTANCE.getAcceleoProject_AcceleoModules();

		/**
		 * The meta object literal for the '<em><b>Plugin Dependencies</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_PROJECT__PLUGIN_DEPENDENCIES = eINSTANCE.getAcceleoProject_PluginDependencies();

		/**
		 * The meta object literal for the '<em><b>Exported Packages</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_PROJECT__EXPORTED_PACKAGES = eINSTANCE.getAcceleoProject_ExportedPackages();

		/**
		 * The meta object literal for the '<em><b>Jre</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_PROJECT__JRE = eINSTANCE.getAcceleoProject_Jre();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl <em>Acceleo UI Project</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoUIProject()
		 * @generated
		 */
		EClass ACCELEO_UI_PROJECT = eINSTANCE.getAcceleoUIProject();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__NAME = eINSTANCE.getAcceleoUIProject_Name();

		/**
		 * The meta object literal for the '<em><b>Generator Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__GENERATOR_NAME = eINSTANCE.getAcceleoUIProject_GeneratorName();

		/**
		 * The meta object literal for the '<em><b>Plugins Dependencies</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES = eINSTANCE.getAcceleoUIProject_PluginsDependencies();

		/**
		 * The meta object literal for the '<em><b>Modules</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__MODULES = eINSTANCE.getAcceleoUIProject_Modules();

		/**
		 * The meta object literal for the '<em><b>Module Plugins</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__MODULE_PLUGINS = eINSTANCE.getAcceleoUIProject_ModulePlugins();

		/**
		 * The meta object literal for the '<em><b>Module Java Class</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS = eINSTANCE.getAcceleoUIProject_ModuleJavaClass();

		/**
		 * The meta object literal for the '<em><b>Model Name Filter</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__MODEL_NAME_FILTER = eINSTANCE.getAcceleoUIProject_ModelNameFilter();

		/**
		 * The meta object literal for the '<em><b>Target Folder Relative Path</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH = eINSTANCE.getAcceleoUIProject_TargetFolderRelativePath();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl <em>Acceleo Module</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoModule()
		 * @generated
		 */
		EClass ACCELEO_MODULE = eINSTANCE.getAcceleoModule();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__PROJECT_NAME = eINSTANCE.getAcceleoModule_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Parent Folder</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__PARENT_FOLDER = eINSTANCE.getAcceleoModule_ParentFolder();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__NAME = eINSTANCE.getAcceleoModule_Name();

		/**
		 * The meta object literal for the '<em><b>Metamodel UR Is</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__METAMODEL_UR_IS = eINSTANCE.getAcceleoModule_MetamodelURIs();

		/**
		 * The meta object literal for the '<em><b>Module Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCELEO_MODULE__MODULE_ELEMENT = eINSTANCE.getAcceleoModule_ModuleElement();

		/**
		 * The meta object literal for the '<em><b>Generate Documentation</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__GENERATE_DOCUMENTATION = eINSTANCE.getAcceleoModule_GenerateDocumentation();

		/**
		 * The meta object literal for the '<em><b>Is Initialized</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__IS_INITIALIZED = eINSTANCE.getAcceleoModule_IsInitialized();

		/**
		 * The meta object literal for the '<em><b>Initialization Kind</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__INITIALIZATION_KIND = eINSTANCE.getAcceleoModule_InitializationKind();

		/**
		 * The meta object literal for the '<em><b>Initialization Path</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE__INITIALIZATION_PATH = eINSTANCE.getAcceleoModule_InitializationPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl <em>Acceleo Module Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoModuleElement()
		 * @generated
		 */
		EClass ACCELEO_MODULE_ELEMENT = eINSTANCE.getAcceleoModuleElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_MODULE_ELEMENT__NAME = eINSTANCE.getAcceleoModuleElement_Name();

		/**
		 * The meta object literal for the '<em><b>Parameter Type</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE = eINSTANCE.getAcceleoModuleElement_ParameterType();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_MODULE_ELEMENT__KIND = eINSTANCE.getAcceleoModuleElement_Kind();

		/**
		 * The meta object literal for the '<em><b>Is Main</b></em>' attribute feature.
		 * <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_MODULE_ELEMENT__IS_MAIN = eINSTANCE.getAcceleoModuleElement_IsMain();

		/**
		 * The meta object literal for the '<em><b>Generate File</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MODULE_ELEMENT__GENERATE_FILE = eINSTANCE.getAcceleoModuleElement_GenerateFile();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl <em>Acceleo Main Class</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoMainClass()
		 * @generated
		 */
		EClass ACCELEO_MAIN_CLASS = eINSTANCE.getAcceleoMainClass();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MAIN_CLASS__PROJECT_NAME = eINSTANCE.getAcceleoMainClass_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Base Package</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MAIN_CLASS__BASE_PACKAGE = eINSTANCE.getAcceleoMainClass_BasePackage();

		/**
		 * The meta object literal for the '<em><b>Class Short Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME = eINSTANCE.getAcceleoMainClass_ClassShortName();

		/**
		 * The meta object literal for the '<em><b>Module File Short Name</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME = eINSTANCE.getAcceleoMainClass_ModuleFileShortName();

		/**
		 * The meta object literal for the '<em><b>Template Names</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MAIN_CLASS__TEMPLATE_NAMES = eINSTANCE.getAcceleoMainClass_TemplateNames();

		/**
		 * The meta object literal for the '<em><b>Packages</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ACCELEO_MAIN_CLASS__PACKAGES = eINSTANCE.getAcceleoMainClass_Packages();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPackageImpl <em>Acceleo Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPackageImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoPackage()
		 * @generated
		 */
		EClass ACCELEO_PACKAGE = eINSTANCE.getAcceleoPackage();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_PACKAGE__CLASS = eINSTANCE.getAcceleoPackage_Class();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_PACKAGE__PATH = eINSTANCE.getAcceleoPackage_Path();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomImpl <em>Acceleo Pom</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoPom()
		 * @generated
		 */
		EClass ACCELEO_POM = eINSTANCE.getAcceleoPom();

		/**
		 * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_POM__ARTIFACT_ID = eINSTANCE.getAcceleoPom_ArtifactId();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCELEO_POM__DEPENDENCIES = eINSTANCE.getAcceleoPom_Dependencies();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomDependencyImpl <em>Acceleo Pom Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoPomDependencyImpl
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getAcceleoPomDependency()
		 * @generated
		 */
		EClass ACCELEO_POM_DEPENDENCY = eINSTANCE.getAcceleoPomDependency();

		/**
		 * The meta object literal for the '<em><b>Group Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_POM_DEPENDENCY__GROUP_ID = eINSTANCE.getAcceleoPomDependency_GroupId();

		/**
		 * The meta object literal for the '<em><b>Artifact Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_POM_DEPENDENCY__ARTIFACT_ID = eINSTANCE.getAcceleoPomDependency_ArtifactId();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_POM_DEPENDENCY__VERSION = eINSTANCE.getAcceleoPomDependency_Version();

		/**
		 * The meta object literal for the '<em><b>System Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCELEO_POM_DEPENDENCY__SYSTEM_PATH = eINSTANCE.getAcceleoPomDependency_SystemPath();

		/**
		 * The meta object literal for the '<em><b>Resolved Class Path</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH = eINSTANCE.getAcceleoMainClass_ResolvedClassPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind <em>Module Element Kind</em>}' enum.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind
		 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelPackageImpl#getModuleElementKind()
		 * @generated
		 */
		EEnum MODULE_ELEMENT_KIND = eINSTANCE.getModuleElementKind();

	}

} // AcceleowizardmodelPackage
