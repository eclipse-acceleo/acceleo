/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleowizardmodelAdapterFactory.java,v 1.3 2011/04/19 13:28:35 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.util;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage
 * @generated
 */
public class AcceleowizardmodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AcceleowizardmodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleowizardmodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AcceleowizardmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AcceleowizardmodelSwitch<Adapter> modelSwitch =
		new AcceleowizardmodelSwitch<Adapter>() {
			@Override
			public Adapter caseAcceleoProject(AcceleoProject object) {
				return createAcceleoProjectAdapter();
			}
			@Override
			public Adapter caseAcceleoUIProject(AcceleoUIProject object) {
				return createAcceleoUIProjectAdapter();
			}
			@Override
			public Adapter caseAcceleoModule(AcceleoModule object) {
				return createAcceleoModuleAdapter();
			}
			@Override
			public Adapter caseAcceleoModuleElement(AcceleoModuleElement object) {
				return createAcceleoModuleElementAdapter();
			}
			@Override
			public Adapter caseAcceleoMainClass(AcceleoMainClass object) {
				return createAcceleoMainClassAdapter();
			}
			@Override
			public Adapter caseAcceleoPackage(AcceleoPackage object) {
				return createAcceleoPackageAdapter();
			}
			@Override
			public Adapter caseAcceleoPom(AcceleoPom object) {
				return createAcceleoPomAdapter();
			}
			@Override
			public Adapter caseAcceleoPomDependency(AcceleoPomDependency object) {
				return createAcceleoPomDependencyAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject <em>Acceleo Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject
	 * @generated
	 */
	public Adapter createAcceleoProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject <em>Acceleo UI Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject
	 * @generated
	 */
	public Adapter createAcceleoUIProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule <em>Acceleo Module</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule
	 * @generated
	 */
	public Adapter createAcceleoModuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement <em>Acceleo Module Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement
	 * @generated
	 */
	public Adapter createAcceleoModuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass <em>Acceleo Main Class</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass
	 * @generated
	 */
	public Adapter createAcceleoMainClassAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage <em>Acceleo Package</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPackage
	 * @generated
	 */
	public Adapter createAcceleoPackageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom <em>Acceleo Pom</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom
	 * @generated
	 */
	public Adapter createAcceleoPomAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency <em>Acceleo Pom Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency
	 * @generated
	 */
	public Adapter createAcceleoPomDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //AcceleowizardmodelAdapterFactory
