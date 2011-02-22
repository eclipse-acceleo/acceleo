/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoModuleElementImpl.java,v 1.2 2011/02/22 09:59:18 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Acceleo Module Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#getName
 * <em>Name</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#getParameterType
 * <em>Parameter Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#getKind
 * <em>Kind</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#isIsInitialized
 * <em>Is Initialized</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#getInitializationKind
 * <em>Initialization Kind</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#getInitializationPath
 * <em>Initialization Path</em>}</li>
 * <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#isIsMain
 * <em>Is Main</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleoModuleElementImpl#isGenerateFile
 * <em>Generate File</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class AcceleoModuleElementImpl extends EObjectImpl implements AcceleoModuleElement {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getParameterType() <em>Parameter Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameterType()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETER_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameterType() <em>Parameter Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameterType()
	 * @generated
	 * @ordered
	 */
	protected String parameterType = PARAMETER_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final ModuleElementKind KIND_EDEFAULT = ModuleElementKind.TEMPLATE;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected ModuleElementKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsInitialized() <em>Is Initialized</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isIsInitialized()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_INITIALIZED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsInitialized() <em>Is Initialized</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isIsInitialized()
	 * @generated
	 * @ordered
	 */
	protected boolean isInitialized = IS_INITIALIZED_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitializationKind() <em>Initialization Kind</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInitializationKind()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIALIZATION_KIND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitializationKind() <em>Initialization Kind</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInitializationKind()
	 * @generated
	 * @ordered
	 */
	protected String initializationKind = INITIALIZATION_KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getInitializationPath() <em>Initialization Path</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInitializationPath()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIALIZATION_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInitializationPath() <em>Initialization Path</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInitializationPath()
	 * @generated
	 * @ordered
	 */
	protected String initializationPath = INITIALIZATION_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsMain() <em>Is Main</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIsMain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_MAIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsMain() <em>Is Main</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isIsMain()
	 * @generated
	 * @ordered
	 */
	protected boolean isMain = IS_MAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateFile() <em>Generate File</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isGenerateFile()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_FILE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateFile() <em>Generate File</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isGenerateFile()
	 * @generated
	 * @ordered
	 */
	protected boolean generateFile = GENERATE_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AcceleoModuleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleowizardmodelPackage.Literals.ACCELEO_MODULE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setParameterType(String newParameterType) {
		String oldParameterType = parameterType;
		parameterType = newParameterType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE, oldParameterType,
					parameterType));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleElementKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setKind(ModuleElementKind newKind) {
		ModuleElementKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__KIND, oldKind, kind));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isIsInitialized() {
		return isInitialized;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIsInitialized(boolean newIsInitialized) {
		boolean oldIsInitialized = isInitialized;
		isInitialized = newIsInitialized;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_INITIALIZED, oldIsInitialized,
					isInitialized));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getInitializationKind() {
		return initializationKind;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInitializationKind(String newInitializationKind) {
		String oldInitializationKind = initializationKind;
		initializationKind = newInitializationKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_KIND,
					oldInitializationKind, initializationKind));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getInitializationPath() {
		return initializationPath;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setInitializationPath(String newInitializationPath) {
		String oldInitializationPath = initializationPath;
		initializationPath = newInitializationPath;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_PATH,
					oldInitializationPath, initializationPath));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isIsMain() {
		return isMain;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIsMain(boolean newIsMain) {
		boolean oldIsMain = isMain;
		isMain = newIsMain;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_MAIN, oldIsMain, isMain));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isGenerateFile() {
		return generateFile;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setGenerateFile(boolean newGenerateFile) {
		boolean oldGenerateFile = generateFile;
		generateFile = newGenerateFile;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__GENERATE_FILE, oldGenerateFile,
					generateFile));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__NAME:
				return getName();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE:
				return getParameterType();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__KIND:
				return getKind();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_INITIALIZED:
				return isIsInitialized();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_KIND:
				return getInitializationKind();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_PATH:
				return getInitializationPath();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_MAIN:
				return isIsMain();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__GENERATE_FILE:
				return isGenerateFile();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE:
				setParameterType((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__KIND:
				setKind((ModuleElementKind)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_INITIALIZED:
				setIsInitialized((Boolean)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_KIND:
				setInitializationKind((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_PATH:
				setInitializationPath((String)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_MAIN:
				setIsMain((Boolean)newValue);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__GENERATE_FILE:
				setGenerateFile((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE:
				setParameterType(PARAMETER_TYPE_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_INITIALIZED:
				setIsInitialized(IS_INITIALIZED_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_KIND:
				setInitializationKind(INITIALIZATION_KIND_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_PATH:
				setInitializationPath(INITIALIZATION_PATH_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_MAIN:
				setIsMain(IS_MAIN_EDEFAULT);
				return;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__GENERATE_FILE:
				setGenerateFile(GENERATE_FILE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__PARAMETER_TYPE:
				return PARAMETER_TYPE_EDEFAULT == null ? parameterType != null : !PARAMETER_TYPE_EDEFAULT
						.equals(parameterType);
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__KIND:
				return kind != KIND_EDEFAULT;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_INITIALIZED:
				return isInitialized != IS_INITIALIZED_EDEFAULT;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_KIND:
				return INITIALIZATION_KIND_EDEFAULT == null ? initializationKind != null
						: !INITIALIZATION_KIND_EDEFAULT.equals(initializationKind);
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__INITIALIZATION_PATH:
				return INITIALIZATION_PATH_EDEFAULT == null ? initializationPath != null
						: !INITIALIZATION_PATH_EDEFAULT.equals(initializationPath);
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__IS_MAIN:
				return isMain != IS_MAIN_EDEFAULT;
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT__GENERATE_FILE:
				return generateFile != GENERATE_FILE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", parameterType: "); //$NON-NLS-1$
		result.append(parameterType);
		result.append(", kind: "); //$NON-NLS-1$
		result.append(kind);
		result.append(", isInitialized: "); //$NON-NLS-1$
		result.append(isInitialized);
		result.append(", initializationKind: "); //$NON-NLS-1$
		result.append(initializationKind);
		result.append(", initializationPath: "); //$NON-NLS-1$
		result.append(initializationPath);
		result.append(", isMain: "); //$NON-NLS-1$
		result.append(isMain);
		result.append(", generateFile: "); //$NON-NLS-1$
		result.append(generateFile);
		result.append(')');
		return result.toString();
	}

} // AcceleoModuleElementImpl
