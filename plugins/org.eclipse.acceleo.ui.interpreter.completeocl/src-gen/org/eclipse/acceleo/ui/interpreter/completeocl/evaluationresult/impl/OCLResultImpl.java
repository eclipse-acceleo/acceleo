/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>OCL Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLResultImpl#getEvaluationTarget <em>Evaluation Target</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLResultImpl#getInterpreterResult <em>Interpreter Result</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OCLResultImpl extends MinimalEObjectImpl.Container implements OCLResult {
	/**
	 * The cached value of the '{@link #getEvaluationTarget() <em>Evaluation Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvaluationTarget()
	 * @generated
	 * @ordered
	 */
	protected EObject evaluationTarget;

	/**
	 * The default value of the '{@link #getInterpreterResult() <em>Interpreter Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterpreterResult()
	 * @generated
	 * @ordered
	 */
	protected static final EvaluationResult INTERPRETER_RESULT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInterpreterResult() <em>Interpreter Result</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterpreterResult()
	 * @generated
	 * @ordered
	 */
	protected EvaluationResult interpreterResult = INTERPRETER_RESULT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OCLResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EvaluationResultPackage.Literals.OCL_RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getEvaluationTarget() {
		if (evaluationTarget != null && evaluationTarget.eIsProxy()) {
			InternalEObject oldEvaluationTarget = (InternalEObject)evaluationTarget;
			evaluationTarget = eResolveProxy(oldEvaluationTarget);
			if (evaluationTarget != oldEvaluationTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EvaluationResultPackage.OCL_RESULT__EVALUATION_TARGET, oldEvaluationTarget,
							evaluationTarget));
			}
		}
		return evaluationTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetEvaluationTarget() {
		return evaluationTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvaluationTarget(EObject newEvaluationTarget) {
		EObject oldEvaluationTarget = evaluationTarget;
		evaluationTarget = newEvaluationTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EvaluationResultPackage.OCL_RESULT__EVALUATION_TARGET, oldEvaluationTarget,
					evaluationTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResult getInterpreterResult() {
		return interpreterResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterpreterResult(EvaluationResult newInterpreterResult) {
		EvaluationResult oldInterpreterResult = interpreterResult;
		interpreterResult = newInterpreterResult;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EvaluationResultPackage.OCL_RESULT__INTERPRETER_RESULT, oldInterpreterResult,
					interpreterResult));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EvaluationResultPackage.OCL_RESULT__EVALUATION_TARGET:
				if (resolve)
					return getEvaluationTarget();
				return basicGetEvaluationTarget();
			case EvaluationResultPackage.OCL_RESULT__INTERPRETER_RESULT:
				return getInterpreterResult();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EvaluationResultPackage.OCL_RESULT__EVALUATION_TARGET:
				setEvaluationTarget((EObject)newValue);
				return;
			case EvaluationResultPackage.OCL_RESULT__INTERPRETER_RESULT:
				setInterpreterResult((EvaluationResult)newValue);
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
			case EvaluationResultPackage.OCL_RESULT__EVALUATION_TARGET:
				setEvaluationTarget((EObject)null);
				return;
			case EvaluationResultPackage.OCL_RESULT__INTERPRETER_RESULT:
				setInterpreterResult(INTERPRETER_RESULT_EDEFAULT);
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
			case EvaluationResultPackage.OCL_RESULT__EVALUATION_TARGET:
				return evaluationTarget != null;
			case EvaluationResultPackage.OCL_RESULT__INTERPRETER_RESULT:
				return INTERPRETER_RESULT_EDEFAULT == null ? interpreterResult != null
						: !INTERPRETER_RESULT_EDEFAULT.equals(interpreterResult);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (interpreterResult: "); //$NON-NLS-1$
		result.append(interpreterResult);
		result.append(')');
		return result.toString();
	}

} //OCLResultImpl
