/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleowizardmodelFactoryImpl.java,v 1.3 2011/04/19 13:28:36 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AcceleowizardmodelFactoryImpl extends EFactoryImpl implements AcceleowizardmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AcceleowizardmodelFactory init() {
		try {
			AcceleowizardmodelFactory theAcceleowizardmodelFactory = (AcceleowizardmodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/acceleo/ui/acceleowizardmodel/3.1"); //$NON-NLS-1$ 
			if (theAcceleowizardmodelFactory != null) {
				return theAcceleowizardmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AcceleowizardmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleowizardmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AcceleowizardmodelPackage.ACCELEO_PROJECT: return createAcceleoProject();
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT: return createAcceleoUIProject();
			case AcceleowizardmodelPackage.ACCELEO_MODULE: return createAcceleoModule();
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT: return createAcceleoModuleElement();
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS: return createAcceleoMainClass();
			case AcceleowizardmodelPackage.ACCELEO_PACKAGE: return createAcceleoPackage();
			case AcceleowizardmodelPackage.ACCELEO_POM: return createAcceleoPom();
			case AcceleowizardmodelPackage.ACCELEO_POM_DEPENDENCY: return createAcceleoPomDependency();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AcceleowizardmodelPackage.MODULE_ELEMENT_KIND:
				return createModuleElementKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AcceleowizardmodelPackage.MODULE_ELEMENT_KIND:
				return convertModuleElementKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoProject createAcceleoProject() {
		AcceleoProjectImpl acceleoProject = new AcceleoProjectImpl();
		return acceleoProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoUIProject createAcceleoUIProject() {
		AcceleoUIProjectImpl acceleoUIProject = new AcceleoUIProjectImpl();
		return acceleoUIProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoModule createAcceleoModule() {
		AcceleoModuleImpl acceleoModule = new AcceleoModuleImpl();
		return acceleoModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoModuleElement createAcceleoModuleElement() {
		AcceleoModuleElementImpl acceleoModuleElement = new AcceleoModuleElementImpl();
		return acceleoModuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoMainClass createAcceleoMainClass() {
		AcceleoMainClassImpl acceleoMainClass = new AcceleoMainClassImpl();
		return acceleoMainClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoPackage createAcceleoPackage() {
		AcceleoPackageImpl acceleoPackage = new AcceleoPackageImpl();
		return acceleoPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoPom createAcceleoPom() {
		AcceleoPomImpl acceleoPom = new AcceleoPomImpl();
		return acceleoPom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoPomDependency createAcceleoPomDependency() {
		AcceleoPomDependencyImpl acceleoPomDependency = new AcceleoPomDependencyImpl();
		return acceleoPomDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElementKind createModuleElementKindFromString(EDataType eDataType, String initialValue) {
		ModuleElementKind result = ModuleElementKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertModuleElementKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleowizardmodelPackage getAcceleowizardmodelPackage() {
		return (AcceleowizardmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AcceleowizardmodelPackage getPackage() {
		return AcceleowizardmodelPackage.eINSTANCE;
	}

} //AcceleowizardmodelFactoryImpl
