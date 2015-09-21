/**
 */
package nooperationreflection;

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
 * @see nooperationreflection.NooperationreflectionFactory
 * @model kind="package"
 * @generated
 */
public interface NooperationreflectionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nooperationreflection";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/nooperationreflection";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nooperationreflection";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NooperationreflectionPackage eINSTANCE = nooperationreflection.impl.NooperationreflectionPackageImpl.init();

	/**
	 * The meta object id for the '{@link nooperationreflection.impl.NoOperationReflectionImpl <em>No Operation Reflection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nooperationreflection.impl.NoOperationReflectionImpl
	 * @see nooperationreflection.impl.NooperationreflectionPackageImpl#getNoOperationReflection()
	 * @generated
	 */
	int NO_OPERATION_REFLECTION = 0;

	/**
	 * The number of structural features of the '<em>No Operation Reflection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NO_OPERATION_REFLECTION_FEATURE_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link nooperationreflection.NoOperationReflection <em>No Operation Reflection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>No Operation Reflection</em>'.
	 * @see nooperationreflection.NoOperationReflection
	 * @generated
	 */
	EClass getNoOperationReflection();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NooperationreflectionFactory getNooperationreflectionFactory();

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
		 * The meta object literal for the '{@link nooperationreflection.impl.NoOperationReflectionImpl <em>No Operation Reflection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nooperationreflection.impl.NoOperationReflectionImpl
		 * @see nooperationreflection.impl.NooperationreflectionPackageImpl#getNoOperationReflection()
		 * @generated
		 */
		EClass NO_OPERATION_REFLECTION = eINSTANCE.getNoOperationReflection();

	}

} //NooperationreflectionPackage
