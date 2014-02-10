/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.*;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EvaluationResultFactoryImpl extends EFactoryImpl implements EvaluationResultFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EvaluationResultFactory init() {
		try {
			EvaluationResultFactory theEvaluationResultFactory = (EvaluationResultFactory)EPackage.Registry.INSTANCE
					.getEFactory(EvaluationResultPackage.eNS_URI);
			if (theEvaluationResultFactory != null) {
				return theEvaluationResultFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EvaluationResultFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResultFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EvaluationResultPackage.OCL_ELEMENT:
				return createOCLElement();
			case EvaluationResultPackage.CONSTRAINT_ELEMENT:
				return createConstraintElement();
			case EvaluationResultPackage.OPERATION_ELEMENT:
				return createOperationElement();
			case EvaluationResultPackage.OCL_RESULT:
				return createOCLResult();
			case EvaluationResultPackage.CONSTRAINT_RESULT:
				return createConstraintResult();
			default:
				throw new IllegalArgumentException(
						"The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case EvaluationResultPackage.SEVERITY:
				return createSeverityFromString(eDataType, initialValue);
			case EvaluationResultPackage.EVALUATION_RESULT:
				return createEvaluationResultFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException(
						"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case EvaluationResultPackage.SEVERITY:
				return convertSeverityToString(eDataType, instanceValue);
			case EvaluationResultPackage.EVALUATION_RESULT:
				return convertEvaluationResultToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException(
						"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public OCLElement createOCLElement() {
		OCLElementImpl oclElement = new OCLElementImpl();
		return oclElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintElement createConstraintElement() {
		ConstraintElementImpl constraintElement = new ConstraintElementImpl();
		return constraintElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationElement createOperationElement() {
		OperationElementImpl operationElement = new OperationElementImpl();
		return operationElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OCLResult createOCLResult() {
		OCLResultImpl oclResult = new OCLResultImpl();
		return oclResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintResult createConstraintResult() {
		ConstraintResultImpl constraintResult = new ConstraintResultImpl();
		return constraintResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Severity createSeverityFromString(EDataType eDataType, String initialValue) {
		Severity result = Severity.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unused")
	public String convertSeverityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResult createEvaluationResultFromString(EDataType eDataType, String initialValue) {
		return (EvaluationResult)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEvaluationResultToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EvaluationResultPackage getEvaluationResultPackage() {
		return (EvaluationResultPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EvaluationResultPackage getPackage() {
		return EvaluationResultPackage.eINSTANCE;
	}

} //EvaluationResultFactoryImpl
