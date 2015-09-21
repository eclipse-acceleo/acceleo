/**
 */
package nooperationreflection.impl;

import nooperationreflection.*;

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
public class NooperationreflectionFactoryImpl extends EFactoryImpl implements NooperationreflectionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NooperationreflectionFactory init() {
		try {
			NooperationreflectionFactory theNooperationreflectionFactory = (NooperationreflectionFactory)EPackage.Registry.INSTANCE.getEFactory(NooperationreflectionPackage.eNS_URI);
			if (theNooperationreflectionFactory != null) {
				return theNooperationreflectionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NooperationreflectionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NooperationreflectionFactoryImpl() {
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
			case NooperationreflectionPackage.NO_OPERATION_REFLECTION: return createNoOperationReflection();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoOperationReflection createNoOperationReflection() {
		NoOperationReflectionImpl noOperationReflection = new NoOperationReflectionImpl();
		return noOperationReflection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NooperationreflectionPackage getNooperationreflectionPackage() {
		return (NooperationreflectionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NooperationreflectionPackage getPackage() {
		return NooperationreflectionPackage.eINSTANCE;
	}

} //NooperationreflectionFactoryImpl
