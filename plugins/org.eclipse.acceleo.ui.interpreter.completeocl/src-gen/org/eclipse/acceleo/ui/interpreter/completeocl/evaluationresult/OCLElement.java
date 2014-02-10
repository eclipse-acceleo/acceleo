/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>OCL Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOCLElement()
 * @model
 * @generated
 */
public interface OCLElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' reference.
	 * @see #setElement(EObject)
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOCLElement_Element()
	 * @model required="true"
	 * @generated
	 */
	EObject getElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getElement <em>Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element</em>' reference.
	 * @see #getElement()
	 * @generated
	 */
	void setElement(EObject value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage#getOCLElement_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<OCLElement> getChildren();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Severity getWorstSeverity();

} // OCLElement
