/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleowizardmodelFactory.java,v 1.3 2011/04/19 13:28:36 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage
 * @generated
 */
public interface AcceleowizardmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AcceleowizardmodelFactory eINSTANCE = org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.impl.AcceleowizardmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Acceleo Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Project</em>'.
	 * @generated
	 */
	AcceleoProject createAcceleoProject();

	/**
	 * Returns a new object of class '<em>Acceleo UI Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo UI Project</em>'.
	 * @generated
	 */
	AcceleoUIProject createAcceleoUIProject();

	/**
	 * Returns a new object of class '<em>Acceleo Module</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Module</em>'.
	 * @generated
	 */
	AcceleoModule createAcceleoModule();

	/**
	 * Returns a new object of class '<em>Acceleo Module Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Module Element</em>'.
	 * @generated
	 */
	AcceleoModuleElement createAcceleoModuleElement();

	/**
	 * Returns a new object of class '<em>Acceleo Main Class</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Main Class</em>'.
	 * @generated
	 */
	AcceleoMainClass createAcceleoMainClass();

	/**
	 * Returns a new object of class '<em>Acceleo Package</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Package</em>'.
	 * @generated
	 */
	AcceleoPackage createAcceleoPackage();

	/**
	 * Returns a new object of class '<em>Acceleo Pom</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Pom</em>'.
	 * @generated
	 */
	AcceleoPom createAcceleoPom();

	/**
	 * Returns a new object of class '<em>Acceleo Pom Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Acceleo Pom Dependency</em>'.
	 * @generated
	 */
	AcceleoPomDependency createAcceleoPomDependency();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AcceleowizardmodelPackage getAcceleowizardmodelPackage();

} //AcceleowizardmodelFactory
