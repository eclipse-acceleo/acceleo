/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl;

import java.util.Collection;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OperationElement;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OperationElementImpl#getEvaluationResults <em>Evaluation Results</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OperationElementImpl extends OCLElementImpl implements OperationElement {
	/**
	 * The cached value of the '{@link #getEvaluationResults() <em>Evaluation Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvaluationResults()
	 * @generated
	 * @ordered
	 */
	protected EList<OCLResult> evaluationResults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EvaluationResultPackage.Literals.OPERATION_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OCLResult> getEvaluationResults() {
		if (evaluationResults == null) {
			evaluationResults = new EObjectContainmentEList<OCLResult>(OCLResult.class, this,
					EvaluationResultPackage.OPERATION_ELEMENT__EVALUATION_RESULTS);
		}
		return evaluationResults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EvaluationResultPackage.OPERATION_ELEMENT__EVALUATION_RESULTS:
				return ((InternalEList<?>)getEvaluationResults()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EvaluationResultPackage.OPERATION_ELEMENT__EVALUATION_RESULTS:
				return getEvaluationResults();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EvaluationResultPackage.OPERATION_ELEMENT__EVALUATION_RESULTS:
				getEvaluationResults().clear();
				getEvaluationResults().addAll((Collection<? extends OCLResult>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case EvaluationResultPackage.OPERATION_ELEMENT__EVALUATION_RESULTS:
				getEvaluationResults().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EvaluationResultPackage.OPERATION_ELEMENT__EVALUATION_RESULTS:
				return evaluationResults != null && !evaluationResults.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OperationElementImpl
