/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoModuleImpl.java,v 1.2 2011/02/23 15:35:39 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import java.util.Collection;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Acceleo Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getParentFolder <em>Parent Folder</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getMetamodelURIs <em>Metamodel UR Is</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getModuleElement <em>Module Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#isGenerateDocumentation <em>Generate Documentation</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#isIsInitialized <em>Is Initialized</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getInitializationKind <em>Initialization Kind</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleImpl#getInitializationPath <em>Initialization Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AcceleoModuleImpl extends EObjectImpl implements AcceleoModule {
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
	 * The default value of the '{@link #getParentFolder() <em>Parent Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentFolder() <em>Parent Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentFolder()
	 * @generated
	 * @ordered
	 */
	protected String parentFolder = PARENT_FOLDER_EDEFAULT;

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
	 * The cached value of the '{@link #getMetamodelURIs() <em>Metamodel UR Is</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelURIs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> metamodelURIs;

	/**
	 * The cached value of the '{@link #getModuleElement() <em>Module Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleElement()
	 * @generated
	 * @ordered
	 */
	protected AcceleoModuleElement moduleElement;

	/**
	 * The default value of the '{@link #isGenerateDocumentation() <em>Generate Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDocumentation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_DOCUMENTATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateDocumentation() <em>Generate Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDocumentation()
	 * @generated
	 * @ordered
	 */
	protected boolean generateDocumentation = GENERATE_DOCUMENTATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsInitialized() <em>Is Initialized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsInitialized()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INITIALIZED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsInitialized() <em>Is Initialized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsInitialized()
	 * @generated
	 * @ordered
	 */
	protected boolean isInitialized = IS_INITIALIZED_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitializationKind() <em>Initialization Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializationKind()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIALIZATION_KIND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitializationKind() <em>Initialization Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializationKind()
	 * @generated
	 * @ordered
	 */
	protected String initializationKind = INITIALIZATION_KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitializationPath() <em>Initialization Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializationPath()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIALIZATION_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitializationPath() <em>Initialization Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitializationPath()
	 * @generated
	 * @ordered
	 */
	protected String initializationPath = INITIALIZATION_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AcceleoModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleowizardmodelPackage.Literals.ACCELEO_MODULE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getParentFolder() {
		return parentFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentFolder(String newParentFolder) {
		String oldParentFolder = parentFolder;
		parentFolder = newParentFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__PARENT_FOLDER, oldParentFolder, parentFolder));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getMetamodelURIs() {
		if (metamodelURIs == null) {
			metamodelURIs = new EDataTypeUniqueEList<String>(String.class, this, AcceleowizardmodelPackage.ACCELEO_MODULE__METAMODEL_UR_IS);
		}
		return metamodelURIs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoModuleElement getModuleElement() {
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModuleElement(AcceleoModuleElement newModuleElement, NotificationChain msgs) {
		AcceleoModuleElement oldModuleElement = moduleElement;
		moduleElement = newModuleElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT, oldModuleElement, newModuleElement);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleElement(AcceleoModuleElement newModuleElement) {
		if (newModuleElement != moduleElement) {
			NotificationChain msgs = null;
			if (moduleElement != null)
				msgs = ((InternalEObject)moduleElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT, null, msgs);
			if (newModuleElement != null)
				msgs = ((InternalEObject)newModuleElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT, null, msgs);
			msgs = basicSetModuleElement(newModuleElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT, newModuleElement, newModuleElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateDocumentation() {
		return generateDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateDocumentation(boolean newGenerateDocumentation) {
		boolean oldGenerateDocumentation = generateDocumentation;
		generateDocumentation = newGenerateDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__GENERATE_DOCUMENTATION, oldGenerateDocumentation, generateDocumentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsInitialized() {
		return isInitialized;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsInitialized(boolean newIsInitialized) {
		boolean oldIsInitialized = isInitialized;
		isInitialized = newIsInitialized;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__IS_INITIALIZED, oldIsInitialized, isInitialized));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitializationKind() {
		return initializationKind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitializationKind(String newInitializationKind) {
		String oldInitializationKind = initializationKind;
		initializationKind = newInitializationKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_KIND, oldInitializationKind, initializationKind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitializationPath() {
		return initializationPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitializationPath(String newInitializationPath) {
		String oldInitializationPath = initializationPath;
		initializationPath = newInitializationPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_PATH, oldInitializationPath, initializationPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT:
				return basicSetModuleElement(null, msgs);
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
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PROJECT_NAME:
				return getProjectName();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PARENT_FOLDER:
				return getParentFolder();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__NAME:
				return getName();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__METAMODEL_UR_IS:
				return getMetamodelURIs();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT:
				return getModuleElement();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__GENERATE_DOCUMENTATION:
				return isGenerateDocumentation();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__IS_INITIALIZED:
				return isIsInitialized();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_KIND:
				return getInitializationKind();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_PATH:
				return getInitializationPath();
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
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PARENT_FOLDER:
				setParentFolder((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__NAME:
				setName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__METAMODEL_UR_IS:
				getMetamodelURIs().clear();
				getMetamodelURIs().addAll((Collection<? extends String>)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT:
				setModuleElement((AcceleoModuleElement)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__GENERATE_DOCUMENTATION:
				setGenerateDocumentation((Boolean)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__IS_INITIALIZED:
				setIsInitialized((Boolean)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_KIND:
				setInitializationKind((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_PATH:
				setInitializationPath((String)newValue);
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
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PARENT_FOLDER:
				setParentFolder(PARENT_FOLDER_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__METAMODEL_UR_IS:
				getMetamodelURIs().clear();
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT:
				setModuleElement((AcceleoModuleElement)null);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__GENERATE_DOCUMENTATION:
				setGenerateDocumentation(GENERATE_DOCUMENTATION_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__IS_INITIALIZED:
				setIsInitialized(IS_INITIALIZED_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_KIND:
				setInitializationKind(INITIALIZATION_KIND_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_PATH:
				setInitializationPath(INITIALIZATION_PATH_EDEFAULT);
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
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case AcceleowizardmodelPackage.ACCELEO_MODULE__PARENT_FOLDER:
				return PARENT_FOLDER_EDEFAULT == null ? parentFolder != null : !PARENT_FOLDER_EDEFAULT.equals(parentFolder);
			case AcceleowizardmodelPackage.ACCELEO_MODULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleowizardmodelPackage.ACCELEO_MODULE__METAMODEL_UR_IS:
				return metamodelURIs != null && !metamodelURIs.isEmpty();
			case AcceleowizardmodelPackage.ACCELEO_MODULE__MODULE_ELEMENT:
				return moduleElement != null;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__GENERATE_DOCUMENTATION:
				return generateDocumentation != GENERATE_DOCUMENTATION_EDEFAULT;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__IS_INITIALIZED:
				return isInitialized != IS_INITIALIZED_EDEFAULT;
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_KIND:
				return INITIALIZATION_KIND_EDEFAULT == null ? initializationKind != null : !INITIALIZATION_KIND_EDEFAULT.equals(initializationKind);
			case AcceleowizardmodelPackage.ACCELEO_MODULE__INITIALIZATION_PATH:
				return INITIALIZATION_PATH_EDEFAULT == null ? initializationPath != null : !INITIALIZATION_PATH_EDEFAULT.equals(initializationPath);
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
		result.append(", parentFolder: "); //$NON-NLS-1$
		result.append(parentFolder);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", metamodelURIs: "); //$NON-NLS-1$
		result.append(metamodelURIs);
		result.append(", generateDocumentation: "); //$NON-NLS-1$
		result.append(generateDocumentation);
		result.append(", isInitialized: "); //$NON-NLS-1$
		result.append(isInitialized);
		result.append(", initializationKind: "); //$NON-NLS-1$
		result.append(initializationKind);
		result.append(", initializationPath: "); //$NON-NLS-1$
		result.append(initializationPath);
		result.append(')');
		return result.toString();
	}

} //AcceleoModuleImpl
