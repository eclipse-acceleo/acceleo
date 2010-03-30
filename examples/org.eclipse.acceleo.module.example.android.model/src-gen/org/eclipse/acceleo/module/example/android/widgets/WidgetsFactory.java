/**
 * <copyright>
 * </copyright>
 *
 * $Id: WidgetsFactory.java,v 1.1 2010/03/30 12:57:34 jmusset Exp $
 */
package org.eclipse.acceleo.module.example.android.widgets;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.module.example.android.widgets.WidgetsPackage
 * @generated
 */
public interface WidgetsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WidgetsFactory eINSTANCE = org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text</em>'.
	 * @generated
	 */
	Text createText();

	/**
	 * Returns a new object of class '<em>Button</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Button</em>'.
	 * @generated
	 */
	Button createButton();

	/**
	 * Returns a new object of class '<em>Spinner</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Spinner</em>'.
	 * @generated
	 */
	Spinner createSpinner();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	WidgetsPackage getWidgetsPackage();

} //WidgetsFactory
