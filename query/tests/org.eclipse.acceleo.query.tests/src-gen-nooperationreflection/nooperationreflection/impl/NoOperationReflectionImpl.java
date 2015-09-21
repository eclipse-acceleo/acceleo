/**
 */
package nooperationreflection.impl;

import nooperationreflection.NoOperationReflection;
import nooperationreflection.NooperationreflectionPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>No Operation Reflection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class NoOperationReflectionImpl extends MinimalEObjectImpl.Container implements NoOperationReflection {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NoOperationReflectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NooperationreflectionPackage.Literals.NO_OPERATION_REFLECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String eOperationNoReflection(String message) {
		return message;
	}

} //NoOperationReflectionImpl
