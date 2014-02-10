/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>OCL Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getEvaluationTarget <em>Evaluation Target</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getInterpreterResult <em>Interpreter Result</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOCLResult()
 * @model
 * @generated
 */
public interface OCLResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Evaluation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evaluation Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evaluation Target</em>' reference.
	 * @see #setEvaluationTarget(EObject)
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOCLResult_EvaluationTarget()
	 * @model required="true"
	 * @generated
	 */
	EObject getEvaluationTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getEvaluationTarget <em>Evaluation Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Evaluation Target</em>' reference.
	 * @see #getEvaluationTarget()
	 * @generated
	 */
	void setEvaluationTarget(EObject value);

	/**
	 * Returns the value of the '<em><b>Interpreter Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interpreter Result</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interpreter Result</em>' attribute.
	 * @see #setInterpreterResult(EvaluationResult)
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOCLResult_InterpreterResult()
	 * @model dataType="org.eclipse.acceleo.ide.ui.interpreter.completeocl.evaluationresult.EvaluationResult" required="true"
	 * @generated
	 */
	EvaluationResult getInterpreterResult();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getInterpreterResult <em>Interpreter Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interpreter Result</em>' attribute.
	 * @see #getInterpreterResult()
	 * @generated
	 */
	void setInterpreterResult(EvaluationResult value);

} // OCLResult
