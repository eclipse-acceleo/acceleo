/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getSeverity <em>Severity</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getConstraintResult()
 * @model
 * @generated
 */
public interface ConstraintResult extends OCLResult {
	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getConstraintResult_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Severity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity
	 * @see #setSeverity(Severity)
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getConstraintResult_Severity()
	 * @model required="true"
	 * @generated
	 */
	Severity getSeverity();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(Severity value);

} // ConstraintResult
