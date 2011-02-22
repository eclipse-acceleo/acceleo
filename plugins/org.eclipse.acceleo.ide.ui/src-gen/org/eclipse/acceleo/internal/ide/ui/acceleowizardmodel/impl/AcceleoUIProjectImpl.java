/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoUIProjectImpl.java,v 1.1 2011/02/22 08:40:08 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import java.util.Collection;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Acceleo UI Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getGeneratorName <em>Generator Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getPluginsDependencies <em>Plugins Dependencies</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getModules <em>Modules</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getModulePlugins <em>Module Plugins</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getModuleJavaClass <em>Module Java Class</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getModelNameFilter <em>Model Name Filter</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoUIProjectImpl#getTargetFolderRelativePath <em>Target Folder Relative Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AcceleoUIProjectImpl extends EObjectImpl implements AcceleoUIProject {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getGeneratorName() <em>Generator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneratorName()
	 * @generated
	 * @ordered
	 */
	protected static final String GENERATOR_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGeneratorName() <em>Generator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneratorName()
	 * @generated
	 * @ordered
	 */
	protected String generatorName = GENERATOR_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPluginsDependencies() <em>Plugins Dependencies</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginsDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<String> pluginsDependencies;

	/**
	 * The cached value of the '{@link #getModules() <em>Modules</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModules()
	 * @generated
	 * @ordered
	 */
	protected EList<String> modules;

	/**
	 * The cached value of the '{@link #getModulePlugins() <em>Module Plugins</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModulePlugins()
	 * @generated
	 * @ordered
	 */
	protected EList<String> modulePlugins;

	/**
	 * The cached value of the '{@link #getModuleJavaClass() <em>Module Java Class</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleJavaClass()
	 * @generated
	 * @ordered
	 */
	protected EList<String> moduleJavaClass;

	/**
	 * The default value of the '{@link #getModelNameFilter() <em>Model Name Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelNameFilter()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_NAME_FILTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelNameFilter() <em>Model Name Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelNameFilter()
	 * @generated
	 * @ordered
	 */
	protected String modelNameFilter = MODEL_NAME_FILTER_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetFolderRelativePath() <em>Target Folder Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetFolderRelativePath()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_FOLDER_RELATIVE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetFolderRelativePath() <em>Target Folder Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetFolderRelativePath()
	 * @generated
	 * @ordered
	 */
	protected String targetFolderRelativePath = TARGET_FOLDER_RELATIVE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AcceleoUIProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleowizardmodelPackage.Literals.ACCELEO_UI_PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGeneratorName() {
		return generatorName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGeneratorName(String newGeneratorName) {
		String oldGeneratorName = generatorName;
		generatorName = newGeneratorName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__GENERATOR_NAME, oldGeneratorName, generatorName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPluginsDependencies() {
		if (pluginsDependencies == null) {
			pluginsDependencies = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES);
		}
		return pluginsDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getModules() {
		if (modules == null) {
			modules = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULES);
		}
		return modules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getModulePlugins() {
		if (modulePlugins == null) {
			modulePlugins = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_PLUGINS);
		}
		return modulePlugins;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getModuleJavaClass() {
		if (moduleJavaClass == null) {
			moduleJavaClass = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS);
		}
		return moduleJavaClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModelNameFilter() {
		return modelNameFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelNameFilter(String newModelNameFilter) {
		String oldModelNameFilter = modelNameFilter;
		modelNameFilter = newModelNameFilter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODEL_NAME_FILTER, oldModelNameFilter, modelNameFilter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTargetFolderRelativePath() {
		return targetFolderRelativePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetFolderRelativePath(String newTargetFolderRelativePath) {
		String oldTargetFolderRelativePath = targetFolderRelativePath;
		targetFolderRelativePath = newTargetFolderRelativePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH, oldTargetFolderRelativePath, targetFolderRelativePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__NAME:
				return getName();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__GENERATOR_NAME:
				return getGeneratorName();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES:
				return getPluginsDependencies();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULES:
				return getModules();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_PLUGINS:
				return getModulePlugins();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS:
				return getModuleJavaClass();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODEL_NAME_FILTER:
				return getModelNameFilter();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH:
				return getTargetFolderRelativePath();
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
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__NAME:
				setName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__GENERATOR_NAME:
				setGeneratorName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES:
				getPluginsDependencies().clear();
				getPluginsDependencies().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULES:
				getModules().clear();
				getModules().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_PLUGINS:
				getModulePlugins().clear();
				getModulePlugins().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS:
				getModuleJavaClass().clear();
				getModuleJavaClass().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODEL_NAME_FILTER:
				setModelNameFilter((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH:
				setTargetFolderRelativePath((String)newValue);
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
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__GENERATOR_NAME:
				setGeneratorName(GENERATOR_NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES:
				getPluginsDependencies().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULES:
				getModules().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_PLUGINS:
				getModulePlugins().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS:
				getModuleJavaClass().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODEL_NAME_FILTER:
				setModelNameFilter(MODEL_NAME_FILTER_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH:
				setTargetFolderRelativePath(TARGET_FOLDER_RELATIVE_PATH_EDEFAULT);
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
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__GENERATOR_NAME:
				return GENERATOR_NAME_EDEFAULT == null ? generatorName != null : !GENERATOR_NAME_EDEFAULT.equals(generatorName);
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__PLUGINS_DEPENDENCIES:
				return pluginsDependencies != null && !pluginsDependencies.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULES:
				return modules != null && !modules.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_PLUGINS:
				return modulePlugins != null && !modulePlugins.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODULE_JAVA_CLASS:
				return moduleJavaClass != null && !moduleJavaClass.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__MODEL_NAME_FILTER:
				return MODEL_NAME_FILTER_EDEFAULT == null ? modelNameFilter != null : !MODEL_NAME_FILTER_EDEFAULT.equals(modelNameFilter);
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT__TARGET_FOLDER_RELATIVE_PATH:
				return TARGET_FOLDER_RELATIVE_PATH_EDEFAULT == null ? targetFolderRelativePath != null : !TARGET_FOLDER_RELATIVE_PATH_EDEFAULT.equals(targetFolderRelativePath);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", generatorName: "); //$NON-NLS-1$
		result.append(generatorName);
		result.append(", pluginsDependencies: "); //$NON-NLS-1$
		result.append(pluginsDependencies);
		result.append(", modules: "); //$NON-NLS-1$
		result.append(modules);
		result.append(", modulePlugins: "); //$NON-NLS-1$
		result.append(modulePlugins);
		result.append(", moduleJavaClass: "); //$NON-NLS-1$
		result.append(moduleJavaClass);
		result.append(", modelNameFilter: "); //$NON-NLS-1$
		result.append(modelNameFilter);
		result.append(", targetFolderRelativePath: "); //$NON-NLS-1$
		result.append(targetFolderRelativePath);
		result.append(')');
		return result.toString();
	}

} //AcceleoUIProjectImpl
