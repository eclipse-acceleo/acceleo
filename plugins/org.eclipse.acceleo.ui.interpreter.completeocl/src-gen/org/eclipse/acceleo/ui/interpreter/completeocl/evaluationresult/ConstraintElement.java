/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement#getConstraintResults <em>Constraint Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getConstraintElement()
 * @model
 * @generated
 */
public interface ConstraintElement extends OCLElement {
	/**
	 * Returns the value of the '<em><b>Constraint Results</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Results</em>' containment reference list.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getConstraintElement_ConstraintResults()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConstraintResult> getConstraintResults();

} // ConstraintElement
