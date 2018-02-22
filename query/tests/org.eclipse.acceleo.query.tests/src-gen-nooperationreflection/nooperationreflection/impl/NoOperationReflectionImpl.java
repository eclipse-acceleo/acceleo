/**
 */
package nooperationreflection.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import nooperationreflection.NoOperationReflection;
import nooperationreflection.NooperationreflectionPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>No Operation Reflection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class NoOperationReflectionImpl extends MinimalEObjectImpl.Container implements NoOperationReflection {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NoOperationReflectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NooperationreflectionPackage.Literals.NO_OPERATION_REFLECTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String eOperationNoReflection(String message) {
		return message;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String eOperationNoReflectionSubParameterType(EClassifier classifier) {
		return classifier.getName();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String eOperationNoReflectionListParameter(EList<EClassifier> classifiers) {
		final StringBuilder result = new StringBuilder();

		for (EClassifier classifer : classifiers) {
			result.append(classifer.getName());
		}

		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EObject identity(EObject eObject) {
		return eObject;
	}
} // NoOperationReflectionImpl
