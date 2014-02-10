/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.util;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage
 * @generated
 */
public class EvaluationResultAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EvaluationResultPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResultAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EvaluationResultPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EvaluationResultSwitch<Adapter> modelSwitch = new EvaluationResultSwitch<Adapter>() {
		@Override
		public Adapter caseOCLElement(OCLElement object) {
			return createOCLElementAdapter();
		}

		@Override
		public Adapter caseConstraintElement(ConstraintElement object) {
			return createConstraintElementAdapter();
		}

		@Override
		public Adapter caseOperationElement(OperationElement object) {
			return createOperationElementAdapter();
		}

		@Override
		public Adapter caseOCLResult(OCLResult object) {
			return createOCLResultAdapter();
		}

		@Override
		public Adapter caseConstraintResult(ConstraintResult object) {
			return createConstraintResultAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement <em>OCL Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement
	 * @generated
	 */
	public Adapter createOCLElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement <em>Constraint Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement
	 * @generated
	 */
	public Adapter createConstraintElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement <em>Operation Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement
	 * @generated
	 */
	public Adapter createOperationElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult <em>OCL Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult
	 * @generated
	 */
	public Adapter createOCLResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult <em>Constraint Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult
	 * @generated
	 */
	public Adapter createConstraintResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //EvaluationResultAdapterFactory
