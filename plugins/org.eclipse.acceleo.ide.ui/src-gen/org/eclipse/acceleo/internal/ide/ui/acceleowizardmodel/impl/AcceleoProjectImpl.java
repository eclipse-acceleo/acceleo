/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoProjectImpl.java,v 1.2 2011/02/25 12:47:28 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import java.util.Collection;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
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
 * An implementation of the model object '<em><b>Acceleo Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl#getGeneratorName <em>Generator Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl#getAcceleoModules <em>Acceleo Modules</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl#getPluginDependencies <em>Plugin Dependencies</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl#getExportedPackages <em>Exported Packages</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoProjectImpl#getJre <em>Jre</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AcceleoProjectImpl extends EObjectImpl implements AcceleoProject {
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
	protected static final String GENERATOR_NAME_EDEFAULT = ""; //$NON-NLS-1$

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
	 * The cached value of the '{@link #getAcceleoModules() <em>Acceleo Modules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAcceleoModules()
	 * @generated
	 * @ordered
	 */
	protected EList<AcceleoModule> acceleoModules;

	/**
	 * The cached value of the '{@link #getPluginDependencies() <em>Plugin Dependencies</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<String> pluginDependencies;

	/**
	 * The cached value of the '{@link #getExportedPackages() <em>Exported Packages</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<String> exportedPackages;

	/**
	 * The default value of the '{@link #getJre() <em>Jre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJre()
	 * @generated
	 * @ordered
	 */
	protected static final String JRE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJre() <em>Jre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJre()
	 * @generated
	 * @ordered
	 */
	protected String jre = JRE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AcceleoProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleowizardmodelPackage.Literals.ACCELEO_PROJECT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_PROJECT__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_PROJECT__GENERATOR_NAME, oldGeneratorName, generatorName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AcceleoModule> getAcceleoModules() {
		if (acceleoModules == null) {
			acceleoModules = new EObjectContainmentEList<AcceleoModule>(AcceleoModule.class, this, AcceleowizardmodelPackage.ACCELEO_PROJECT__ACCELEO_MODULES);
		}
		return acceleoModules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPluginDependencies() {
		if (pluginDependencies == null) {
			pluginDependencies = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_PROJECT__PLUGIN_DEPENDENCIES);
		}
		return pluginDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getExportedPackages() {
		if (exportedPackages == null) {
			exportedPackages = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_PROJECT__EXPORTED_PACKAGES);
		}
		return exportedPackages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJre() {
		return jre;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJre(String newJre) {
		String oldJre = jre;
		jre = newJre;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_PROJECT__JRE, oldJre, jre));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__ACCELEO_MODULES:
				return ((InternalEList<?>)getAcceleoModules()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__NAME:
				return getName();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__GENERATOR_NAME:
				return getGeneratorName();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__ACCELEO_MODULES:
				return getAcceleoModules();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__PLUGIN_DEPENDENCIES:
				return getPluginDependencies();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__EXPORTED_PACKAGES:
				return getExportedPackages();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__JRE:
				return getJre();
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
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__NAME:
				setName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__GENERATOR_NAME:
				setGeneratorName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__ACCELEO_MODULES:
				getAcceleoModules().clear();
				getAcceleoModules().addAll((Collection<? extends AcceleoModule>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__PLUGIN_DEPENDENCIES:
				getPluginDependencies().clear();
				getPluginDependencies().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__EXPORTED_PACKAGES:
				getExportedPackages().clear();
				getExportedPackages().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__JRE:
				setJre((String)newValue);
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
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__GENERATOR_NAME:
				setGeneratorName(GENERATOR_NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__ACCELEO_MODULES:
				getAcceleoModules().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__PLUGIN_DEPENDENCIES:
				getPluginDependencies().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__EXPORTED_PACKAGES:
				getExportedPackages().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__JRE:
				setJre(JRE_EDEFAULT);
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
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__GENERATOR_NAME:
				return GENERATOR_NAME_EDEFAULT == null ? generatorName != null : !GENERATOR_NAME_EDEFAULT.equals(generatorName);
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__ACCELEO_MODULES:
				return acceleoModules != null && !acceleoModules.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__PLUGIN_DEPENDENCIES:
				return pluginDependencies != null && !pluginDependencies.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__EXPORTED_PACKAGES:
				return exportedPackages != null && !exportedPackages.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_PROJECT__JRE:
				return JRE_EDEFAULT == null ? jre != null : !JRE_EDEFAULT.equals(jre);
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
		result.append(", pluginDependencies: "); //$NON-NLS-1$
		result.append(pluginDependencies);
		result.append(", exportedPackages: "); //$NON-NLS-1$
		result.append(exportedPackages);
		result.append(", jre: "); //$NON-NLS-1$
		result.append(jre);
		result.append(')');
		return result.toString();
	}

} //AcceleoProjectImpl
