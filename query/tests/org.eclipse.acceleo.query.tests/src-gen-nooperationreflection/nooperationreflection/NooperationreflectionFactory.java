/**
 */
package nooperationreflection;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see nooperationreflection.NooperationreflectionPackage
 * @generated
 */
public interface NooperationreflectionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NooperationreflectionFactory eINSTANCE = nooperationreflection.impl.NooperationreflectionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>No Operation Reflection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>No Operation Reflection</em>'.
	 * @generated
	 */
	NoOperationReflection createNoOperationReflection();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NooperationreflectionPackage getNooperationreflectionPackage();

} //NooperationreflectionFactory
