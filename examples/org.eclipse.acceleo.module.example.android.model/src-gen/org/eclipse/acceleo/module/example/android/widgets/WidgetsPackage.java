/**
 * <copyright>
 * </copyright>
 *
 * $Id: WidgetsPackage.java,v 1.1 2010/03/30 12:57:35 jmusset Exp $
 */
package org.eclipse.acceleo.module.example.android.widgets;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.module.example.android.widgets.WidgetsFactory
 * @model kind="package"
 * @generated
 */
public interface WidgetsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "widgets";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/example/android/widgets";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "widgets";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WidgetsPackage eINSTANCE = org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.module.example.android.widgets.Widget <em>Widget</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.module.example.android.widgets.Widget
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getWidget()
	 * @generated
	 */
	int WIDGET = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET__NAME = 0;

	/**
	 * The number of structural features of the '<em>Widget</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIDGET_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.module.example.android.widgets.impl.TextImpl <em>Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.TextImpl
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getText()
	 * @generated
	 */
	int TEXT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.module.example.android.widgets.impl.ButtonImpl <em>Button</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.ButtonImpl
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getButton()
	 * @generated
	 */
	int BUTTON = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Button</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUTTON_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.module.example.android.widgets.impl.SpinnerImpl <em>Spinner</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.SpinnerImpl
	 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getSpinner()
	 * @generated
	 */
	int SPINNER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPINNER__NAME = WIDGET__NAME;

	/**
	 * The number of structural features of the '<em>Spinner</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPINNER_FEATURE_COUNT = WIDGET_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.module.example.android.widgets.Widget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Widget</em>'.
	 * @see org.eclipse.acceleo.module.example.android.widgets.Widget
	 * @generated
	 */
	EClass getWidget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.module.example.android.widgets.Widget#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.module.example.android.widgets.Widget#getName()
	 * @see #getWidget()
	 * @generated
	 */
	EAttribute getWidget_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.module.example.android.widgets.Text <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text</em>'.
	 * @see org.eclipse.acceleo.module.example.android.widgets.Text
	 * @generated
	 */
	EClass getText();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.module.example.android.widgets.Button <em>Button</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Button</em>'.
	 * @see org.eclipse.acceleo.module.example.android.widgets.Button
	 * @generated
	 */
	EClass getButton();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.module.example.android.widgets.Spinner <em>Spinner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Spinner</em>'.
	 * @see org.eclipse.acceleo.module.example.android.widgets.Spinner
	 * @generated
	 */
	EClass getSpinner();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WidgetsFactory getWidgetsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.module.example.android.widgets.Widget <em>Widget</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.module.example.android.widgets.Widget
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getWidget()
		 * @generated
		 */
		EClass WIDGET = eINSTANCE.getWidget();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIDGET__NAME = eINSTANCE.getWidget_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.module.example.android.widgets.impl.TextImpl <em>Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.TextImpl
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getText()
		 * @generated
		 */
		EClass TEXT = eINSTANCE.getText();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.module.example.android.widgets.impl.ButtonImpl <em>Button</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.ButtonImpl
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getButton()
		 * @generated
		 */
		EClass BUTTON = eINSTANCE.getButton();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.module.example.android.widgets.impl.SpinnerImpl <em>Spinner</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.SpinnerImpl
		 * @see org.eclipse.acceleo.module.example.android.widgets.impl.WidgetsPackageImpl#getSpinner()
		 * @generated
		 */
		EClass SPINNER = eINSTANCE.getSpinner();

	}

} //WidgetsPackage
