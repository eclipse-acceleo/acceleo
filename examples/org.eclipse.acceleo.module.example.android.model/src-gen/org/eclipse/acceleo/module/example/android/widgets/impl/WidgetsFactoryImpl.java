/**
 * <copyright>
 * </copyright>
 *
 * $Id: WidgetsFactoryImpl.java,v 1.1 2010/03/30 12:57:38 jmusset Exp $
 */
package org.eclipse.acceleo.module.example.android.widgets.impl;

import org.eclipse.acceleo.module.example.android.widgets.Button;
import org.eclipse.acceleo.module.example.android.widgets.Spinner;
import org.eclipse.acceleo.module.example.android.widgets.Text;
import org.eclipse.acceleo.module.example.android.widgets.WidgetsFactory;
import org.eclipse.acceleo.module.example.android.widgets.WidgetsPackage;
import org.eclipse.emf.ecore.EClass;
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
public class WidgetsFactoryImpl extends EFactoryImpl implements WidgetsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WidgetsFactory init() {
		try {
			WidgetsFactory theWidgetsFactory = (WidgetsFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/acceleo/example/android/widgets"); 
			if (theWidgetsFactory != null) {
				return theWidgetsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WidgetsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetsFactoryImpl() {
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
			case WidgetsPackage.TEXT: return createText();
			case WidgetsPackage.BUTTON: return createButton();
			case WidgetsPackage.SPINNER: return createSpinner();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Text createText() {
		TextImpl text = new TextImpl();
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Button createButton() {
		ButtonImpl button = new ButtonImpl();
		return button;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Spinner createSpinner() {
		SpinnerImpl spinner = new SpinnerImpl();
		return spinner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WidgetsPackage getWidgetsPackage() {
		return (WidgetsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WidgetsPackage getPackage() {
		return WidgetsPackage.eINSTANCE;
	}

} //WidgetsFactoryImpl
