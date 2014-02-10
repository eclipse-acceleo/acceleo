/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultFactory
 * @model kind="package"
 * @generated
 */
public interface EvaluationResultPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "evaluationresult"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/interpreter/completeocl/result"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "evaluationresult"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EvaluationResultPackage eINSTANCE = org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLElementImpl <em>OCL Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLElementImpl
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getOCLElement()
	 * @generated
	 */
	int OCL_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_ELEMENT__ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_ELEMENT__CHILDREN = 1;

	/**
	 * The number of structural features of the '<em>OCL Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintElementImpl <em>Constraint Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintElementImpl
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getConstraintElement()
	 * @generated
	 */
	int CONSTRAINT_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ELEMENT__ELEMENT = OCL_ELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ELEMENT__CHILDREN = OCL_ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Constraint Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS = OCL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Constraint Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_ELEMENT_FEATURE_COUNT = OCL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OperationElementImpl <em>Operation Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OperationElementImpl
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getOperationElement()
	 * @generated
	 */
	int OPERATION_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_ELEMENT__ELEMENT = OCL_ELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_ELEMENT__CHILDREN = OCL_ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Evaluation Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_ELEMENT__EVALUATION_RESULTS = OCL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Operation Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_ELEMENT_FEATURE_COUNT = OCL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLResultImpl <em>OCL Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLResultImpl
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getOCLResult()
	 * @generated
	 */
	int OCL_RESULT = 3;

	/**
	 * The feature id for the '<em><b>Evaluation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_RESULT__EVALUATION_TARGET = 0;

	/**
	 * The feature id for the '<em><b>Interpreter Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_RESULT__INTERPRETER_RESULT = 1;

	/**
	 * The number of structural features of the '<em>OCL Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_RESULT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '<em>Evaluation Result</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.language.EvaluationResult
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getEvaluationResult()
	 * @generated
	 */
	int EVALUATION_RESULT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintResultImpl <em>Constraint Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintResultImpl
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getConstraintResult()
	 * @generated
	 */
	int CONSTRAINT_RESULT = 4;

	/**
	 * The feature id for the '<em><b>Evaluation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_RESULT__EVALUATION_TARGET = OCL_RESULT__EVALUATION_TARGET;

	/**
	 * The feature id for the '<em><b>Interpreter Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_RESULT__INTERPRETER_RESULT = OCL_RESULT__INTERPRETER_RESULT;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_RESULT__MESSAGE = OCL_RESULT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_RESULT__SEVERITY = OCL_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Constraint Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_RESULT_FEATURE_COUNT = OCL_RESULT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity <em>Severity</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getSeverity()
	 * @generated
	 */
	int SEVERITY = 5;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement <em>OCL Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>OCL Element</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement
	 * @generated
	 */
	EClass getOCLElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getElement()
	 * @see #getOCLElement()
	 * @generated
	 */
	EReference getOCLElement_Element();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement#getChildren()
	 * @see #getOCLElement()
	 * @generated
	 */
	EReference getOCLElement_Children();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement <em>Constraint Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Element</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement
	 * @generated
	 */
	EClass getConstraintElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement#getConstraintResults <em>Constraint Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraint Results</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement#getConstraintResults()
	 * @see #getConstraintElement()
	 * @generated
	 */
	EReference getConstraintElement_ConstraintResults();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement <em>Operation Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Element</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement
	 * @generated
	 */
	EClass getOperationElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement#getEvaluationResults <em>Evaluation Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Evaluation Results</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement#getEvaluationResults()
	 * @see #getOperationElement()
	 * @generated
	 */
	EReference getOperationElement_EvaluationResults();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult <em>OCL Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>OCL Result</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult
	 * @generated
	 */
	EClass getOCLResult();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getEvaluationTarget <em>Evaluation Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Evaluation Target</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getEvaluationTarget()
	 * @see #getOCLResult()
	 * @generated
	 */
	EReference getOCLResult_EvaluationTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getInterpreterResult <em>Interpreter Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Interpreter Result</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult#getInterpreterResult()
	 * @see #getOCLResult()
	 * @generated
	 */
	EAttribute getOCLResult_InterpreterResult();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.acceleo.ui.interpreter.language.EvaluationResult <em>Evaluation Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Evaluation Result</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.language.EvaluationResult
	 * @model instanceClass="org.eclipse.acceleo.ui.interpreter.language.EvaluationResult"
	 * @generated
	 */
	EDataType getEvaluationResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult <em>Constraint Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Result</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult
	 * @generated
	 */
	EClass getConstraintResult();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getMessage()
	 * @see #getConstraintResult()
	 * @generated
	 */
	EAttribute getConstraintResult_Message();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult#getSeverity()
	 * @see #getConstraintResult()
	 * @generated
	 */
	EAttribute getConstraintResult_Severity();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Severity</em>'.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity
	 * @generated
	 */
	EEnum getSeverity();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EvaluationResultFactory getEvaluationResultFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLElementImpl <em>OCL Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLElementImpl
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getOCLElement()
		 * @generated
		 */
		EClass OCL_ELEMENT = eINSTANCE.getOCLElement();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OCL_ELEMENT__ELEMENT = eINSTANCE.getOCLElement_Element();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OCL_ELEMENT__CHILDREN = eINSTANCE.getOCLElement_Children();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintElementImpl <em>Constraint Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintElementImpl
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getConstraintElement()
		 * @generated
		 */
		EClass CONSTRAINT_ELEMENT = eINSTANCE.getConstraintElement();

		/**
		 * The meta object literal for the '<em><b>Constraint Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS = eINSTANCE
				.getConstraintElement_ConstraintResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OperationElementImpl <em>Operation Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OperationElementImpl
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getOperationElement()
		 * @generated
		 */
		EClass OPERATION_ELEMENT = eINSTANCE.getOperationElement();

		/**
		 * The meta object literal for the '<em><b>Evaluation Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_ELEMENT__EVALUATION_RESULTS = eINSTANCE.getOperationElement_EvaluationResults();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLResultImpl <em>OCL Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLResultImpl
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getOCLResult()
		 * @generated
		 */
		EClass OCL_RESULT = eINSTANCE.getOCLResult();

		/**
		 * The meta object literal for the '<em><b>Evaluation Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OCL_RESULT__EVALUATION_TARGET = eINSTANCE.getOCLResult_EvaluationTarget();

		/**
		 * The meta object literal for the '<em><b>Interpreter Result</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OCL_RESULT__INTERPRETER_RESULT = eINSTANCE.getOCLResult_InterpreterResult();

		/**
		 * The meta object literal for the '<em>Evaluation Result</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.language.EvaluationResult
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getEvaluationResult()
		 * @generated
		 */
		EDataType EVALUATION_RESULT = eINSTANCE.getEvaluationResult();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintResultImpl <em>Constraint Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintResultImpl
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getConstraintResult()
		 * @generated
		 */
		EClass CONSTRAINT_RESULT = eINSTANCE.getConstraintResult();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_RESULT__MESSAGE = eINSTANCE.getConstraintResult_Message();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_RESULT__SEVERITY = eINSTANCE.getConstraintResult_Severity();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity <em>Severity</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity
		 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.EvaluationResultPackageImpl#getSeverity()
		 * @generated
		 */
		EEnum SEVERITY = eINSTANCE.getSeverity();

	}

} //EvaluationResultPackage
