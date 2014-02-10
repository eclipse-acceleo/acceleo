/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.util;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage
 * @generated
 */
public class EvaluationResultSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EvaluationResultPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResultSwitch() {
		if (modelPackage == null) {
			modelPackage = EvaluationResultPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case EvaluationResultPackage.OCL_ELEMENT: {
				OCLElement oclElement = (OCLElement)theEObject;
				T result = caseOCLElement(oclElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case EvaluationResultPackage.CONSTRAINT_ELEMENT: {
				ConstraintElement constraintElement = (ConstraintElement)theEObject;
				T result = caseConstraintElement(constraintElement);
				if (result == null)
					result = caseOCLElement(constraintElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case EvaluationResultPackage.OPERATION_ELEMENT: {
				OperationElement operationElement = (OperationElement)theEObject;
				T result = caseOperationElement(operationElement);
				if (result == null)
					result = caseOCLElement(operationElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case EvaluationResultPackage.OCL_RESULT: {
				OCLResult oclResult = (OCLResult)theEObject;
				T result = caseOCLResult(oclResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case EvaluationResultPackage.CONSTRAINT_RESULT: {
				ConstraintResult constraintResult = (ConstraintResult)theEObject;
				T result = caseConstraintResult(constraintResult);
				if (result == null)
					result = caseOCLResult(constraintResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>OCL Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>OCL Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseOCLElement(OCLElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constraint Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constraint Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseConstraintElement(ConstraintElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseOperationElement(OperationElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>OCL Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>OCL Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseOCLResult(OCLResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constraint Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constraint Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseConstraintResult(ConstraintResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //EvaluationResultSwitch
