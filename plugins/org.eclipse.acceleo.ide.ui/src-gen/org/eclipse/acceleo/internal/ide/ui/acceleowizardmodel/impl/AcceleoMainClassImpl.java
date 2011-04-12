/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoMainClassImpl.java,v 1.2 2011/04/12 15:01:49 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import java.util.Collection;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Acceleo Main Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getBasePackage <em>Base Package</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getClassShortName <em>Class Short Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getModuleFileShortName <em>Module File Short Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getTemplateNames <em>Template Names</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getResolvedClassPath <em>Resolved Class Path</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoMainClassImpl#getPackages <em>Packages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AcceleoMainClassImpl extends EObjectImpl implements AcceleoMainClass {
	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected String projectName = PROJECT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getBasePackage() <em>Base Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasePackage()
	 * @generated
	 * @ordered
	 */
	protected static final String BASE_PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBasePackage() <em>Base Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasePackage()
	 * @generated
	 * @ordered
	 */
	protected String basePackage = BASE_PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassShortName() <em>Class Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassShortName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_SHORT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassShortName() <em>Class Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassShortName()
	 * @generated
	 * @ordered
	 */
	protected String classShortName = CLASS_SHORT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getModuleFileShortName() <em>Module File Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleFileShortName()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_FILE_SHORT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleFileShortName() <em>Module File Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleFileShortName()
	 * @generated
	 * @ordered
	 */
	protected String moduleFileShortName = MODULE_FILE_SHORT_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTemplateNames() <em>Template Names</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplateNames()
	 * @generated
	 * @ordered
	 */
	protected EList<String> templateNames;

	/**
	 * The cached value of the '{@link #getResolvedClassPath() <em>Resolved Class Path</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResolvedClassPath()
	 * @generated
	 * @ordered
	 */
	protected EList<String> resolvedClassPath;

	/**
	 * The cached value of the '{@link #getPackages() <em>Packages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<AcceleoPackage> packages;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AcceleoMainClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleowizardmodelPackage.Literals.ACCELEO_MAIN_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBasePackage() {
		return basePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBasePackage(String newBasePackage) {
		String oldBasePackage = basePackage;
		basePackage = newBasePackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__BASE_PACKAGE, oldBasePackage, basePackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassShortName() {
		return classShortName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassShortName(String newClassShortName) {
		String oldClassShortName = classShortName;
		classShortName = newClassShortName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME, oldClassShortName, classShortName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModuleFileShortName() {
		return moduleFileShortName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleFileShortName(String newModuleFileShortName) {
		String oldModuleFileShortName = moduleFileShortName;
		moduleFileShortName = newModuleFileShortName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME, oldModuleFileShortName, moduleFileShortName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getTemplateNames() {
		if (templateNames == null) {
			templateNames = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__TEMPLATE_NAMES);
		}
		return templateNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AcceleoPackage> getPackages() {
		if (packages == null) {
			packages = new EObjectContainmentEList<AcceleoPackage>(AcceleoPackage.class, this, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PACKAGES);
		}
		return packages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PACKAGES:
				return ((InternalEList<?>)getPackages()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getResolvedClassPath() {
		if (resolvedClassPath == null) {
			resolvedClassPath = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH);
		}
		return resolvedClassPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PROJECT_NAME:
				return getProjectName();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__BASE_PACKAGE:
				return getBasePackage();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME:
				return getClassShortName();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME:
				return getModuleFileShortName();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__TEMPLATE_NAMES:
				return getTemplateNames();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH:
				return getResolvedClassPath();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PACKAGES:
				return getPackages();
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
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__BASE_PACKAGE:
				setBasePackage((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME:
				setClassShortName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME:
				setModuleFileShortName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__TEMPLATE_NAMES:
				getTemplateNames().clear();
				getTemplateNames().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH:
				getResolvedClassPath().clear();
				getResolvedClassPath().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PACKAGES:
				getPackages().clear();
				getPackages().addAll((Collection<? extends AcceleoPackage>)newValue);
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
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__BASE_PACKAGE:
				setBasePackage(BASE_PACKAGE_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME:
				setClassShortName(CLASS_SHORT_NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME:
				setModuleFileShortName(MODULE_FILE_SHORT_NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__TEMPLATE_NAMES:
				getTemplateNames().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH:
				getResolvedClassPath().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PACKAGES:
				getPackages().clear();
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
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__BASE_PACKAGE:
				return BASE_PACKAGE_EDEFAULT == null ? basePackage != null : !BASE_PACKAGE_EDEFAULT.equals(basePackage);
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__CLASS_SHORT_NAME:
				return CLASS_SHORT_NAME_EDEFAULT == null ? classShortName != null : !CLASS_SHORT_NAME_EDEFAULT.equals(classShortName);
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__MODULE_FILE_SHORT_NAME:
				return MODULE_FILE_SHORT_NAME_EDEFAULT == null ? moduleFileShortName != null : !MODULE_FILE_SHORT_NAME_EDEFAULT.equals(moduleFileShortName);
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__TEMPLATE_NAMES:
				return templateNames != null && !templateNames.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__RESOLVED_CLASS_PATH:
				return resolvedClassPath != null && !resolvedClassPath.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS__PACKAGES:
				return packages != null && !packages.isEmpty();
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
		result.append(" (projectName: "); //$NON-NLS-1$
		result.append(projectName);
		result.append(", basePackage: "); //$NON-NLS-1$
		result.append(basePackage);
		result.append(", classShortName: "); //$NON-NLS-1$
		result.append(classShortName);
		result.append(", moduleFileShortName: "); //$NON-NLS-1$
		result.append(moduleFileShortName);
		result.append(", templateNames: "); //$NON-NLS-1$
		result.append(templateNames);
		result.append(", resolvedClassPath: "); //$NON-NLS-1$
		result.append(resolvedClassPath);
		result.append(')');
		return result.toString();
	}

} //AcceleoMainClassImpl
