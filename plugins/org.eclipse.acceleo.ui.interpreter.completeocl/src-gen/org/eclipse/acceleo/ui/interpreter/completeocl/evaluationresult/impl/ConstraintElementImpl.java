/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl;

import java.util.Collection;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.ConstraintElementImpl#getConstraintResults <em>Constraint Results</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstraintElementImpl extends OCLElementImpl implements ConstraintElement {
	/**
	 * The cached value of the '{@link #getConstraintResults() <em>Constraint Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraintResults()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintResult> constraintResults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EvaluationResultPackage.Literals.CONSTRAINT_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConstraintResult> getConstraintResults() {
		if (constraintResults == null) {
			constraintResults = new EObjectContainmentEList<ConstraintResult>(ConstraintResult.class, this,
					EvaluationResultPackage.CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS);
		}
		return constraintResults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EvaluationResultPackage.CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS:
				return ((InternalEList<?>)getConstraintResults()).basicRemove(otherEnd, msgs);
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
			case EvaluationResultPackage.CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS:
				return getConstraintResults();
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
			case EvaluationResultPackage.CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS:
				getConstraintResults().clear();
				getConstraintResults().addAll((Collection<? extends ConstraintResult>)newValue);
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
			case EvaluationResultPackage.CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS:
				getConstraintResults().clear();
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
			case EvaluationResultPackage.CONSTRAINT_ELEMENT__CONSTRAINT_RESULTS:
				return constraintResults != null && !constraintResults.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ConstraintElementImpl
