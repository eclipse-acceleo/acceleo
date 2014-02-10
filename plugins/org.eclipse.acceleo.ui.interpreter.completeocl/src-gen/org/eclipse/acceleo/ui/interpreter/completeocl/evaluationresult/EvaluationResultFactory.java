/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage
 * @generated
 */
public interface EvaluationResultFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EvaluationResultFactory eINSTANCE = org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>OCL Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>OCL Element</em>'.
	 * @generated
	 */
	OCLElement createOCLElement();

	/**
	 * Returns a new object of class '<em>Constraint Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraint Element</em>'.
	 * @generated
	 */
	ConstraintElement createConstraintElement();

	/**
	 * Returns a new object of class '<em>Operation Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Element</em>'.
	 * @generated
	 */
	OperationElement createOperationElement();

	/**
	 * Returns a new object of class '<em>OCL Result</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>OCL Result</em>'.
	 * @generated
	 */
	OCLResult createOCLResult();

	/**
	 * Returns a new object of class '<em>Constraint Result</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraint Result</em>'.
	 * @generated
	 */
	ConstraintResult createConstraintResult();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EvaluationResultPackage getEvaluationResultPackage();

} //EvaluationResultFactory
