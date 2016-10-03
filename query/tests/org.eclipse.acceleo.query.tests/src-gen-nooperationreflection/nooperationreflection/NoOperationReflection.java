/**
 */
package nooperationreflection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>No Operation Reflection</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see nooperationreflection.NooperationreflectionPackage#getNoOperationReflection()
 * @model
 * @generated
 */
public interface NoOperationReflection extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" messageRequired="true"
	 * @generated
	 */
	String eOperationNoReflection(String message);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model classifierRequired="true"
	 * @generated
	 */
	String eOperationNoReflectionSubParameterType(EClassifier classifier);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model classifiersRequired="true" classifiersMany="true"
	 * @generated
	 */
	String eOperationNoReflectionListParameter(EList<EClassifier> classifiers);

} // NoOperationReflection
