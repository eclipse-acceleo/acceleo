/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement#getEvaluationResults <em>Evaluation Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOperationElement()
 * @model
 * @generated
 */
public interface OperationElement extends OCLElement {
	/**
	 * Returns the value of the '<em><b>Evaluation Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Evaluation Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Evaluation Results</em>' containment reference list.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOperationElement_EvaluationResults()
	 * @model containment="true"
	 * @generated
	 */
	EList<OCLResult> getEvaluationResults();

} // OperationElement
