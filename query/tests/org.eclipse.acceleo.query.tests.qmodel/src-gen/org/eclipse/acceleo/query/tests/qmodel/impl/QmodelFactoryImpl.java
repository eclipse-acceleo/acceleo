/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.qmodel.impl;

import java.io.Serializable;

import org.eclipse.acceleo.query.tests.qmodel.BooleanResult;
import org.eclipse.acceleo.query.tests.qmodel.EObjectResult;
import org.eclipse.acceleo.query.tests.qmodel.EObjectVariable;
import org.eclipse.acceleo.query.tests.qmodel.EmptyResult;
import org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult;
import org.eclipse.acceleo.query.tests.qmodel.ErrorResult;
import org.eclipse.acceleo.query.tests.qmodel.IntegerResult;
import org.eclipse.acceleo.query.tests.qmodel.InvalidResult;
import org.eclipse.acceleo.query.tests.qmodel.ListResult;
import org.eclipse.acceleo.query.tests.qmodel.ModelElement;
import org.eclipse.acceleo.query.tests.qmodel.QmodelFactory;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Queries;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation;
import org.eclipse.acceleo.query.tests.qmodel.SerializableResult;
import org.eclipse.acceleo.query.tests.qmodel.SetResult;
import org.eclipse.acceleo.query.tests.qmodel.Severity;
import org.eclipse.acceleo.query.tests.qmodel.StringResult;
import org.eclipse.acceleo.query.tests.qmodel.ValidationMessage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class QmodelFactoryImpl extends EFactoryImpl implements QmodelFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static QmodelFactory init() {
		try {
			QmodelFactory theQmodelFactory = (QmodelFactory)EPackage.Registry.INSTANCE
					.getEFactory(QmodelPackage.eNS_URI);
			if (theQmodelFactory != null) {
				return theQmodelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new QmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case QmodelPackage.QUERY:
				return createQuery();
			case QmodelPackage.EOBJECT_VARIABLE:
				return createEObjectVariable();
			case QmodelPackage.LIST_RESULT:
				return createListResult();
			case QmodelPackage.SET_RESULT:
				return createSetResult();
			case QmodelPackage.QUERIES:
				return createQueries();
			case QmodelPackage.MODEL_ELEMENT:
				return createModelElement();
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION:
				return createQueryEvaluationResultExpectation();
			case QmodelPackage.ERROR_RESULT:
				return createErrorResult();
			case QmodelPackage.SERIALIZABLE_RESULT:
				return createSerializableResult();
			case QmodelPackage.ENUMERATOR_RESULT:
				return createEnumeratorResult();
			case QmodelPackage.BOOLEAN_RESULT:
				return createBooleanResult();
			case QmodelPackage.STRING_RESULT:
				return createStringResult();
			case QmodelPackage.EMPTY_RESULT:
				return createEmptyResult();
			case QmodelPackage.INTEGER_RESULT:
				return createIntegerResult();
			case QmodelPackage.QUERY_VALIDATION_RESULT_EXPECTATION:
				return createQueryValidationResultExpectation();
			case QmodelPackage.QUERY_VALIDATION_RESULT:
				return createQueryValidationResult();
			case QmodelPackage.VALIDATION_MESSAGE:
				return createValidationMessage();
			case QmodelPackage.INVALID_RESULT:
				return createInvalidResult();
			case QmodelPackage.EOBJECT_RESULT:
				return createEObjectResult();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case QmodelPackage.SEVERITY:
				return createSeverityFromString(eDataType, initialValue);
			case QmodelPackage.ANY_SERIALIZABLE:
				return createAnySerializableFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case QmodelPackage.SEVERITY:
				return convertSeverityToString(eDataType, instanceValue);
			case QmodelPackage.ANY_SERIALIZABLE:
				return convertAnySerializableToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Query createQuery() {
		QueryImpl query = new QueryImpl();
		return query;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObjectVariable createEObjectVariable() {
		EObjectVariableImpl eObjectVariable = new EObjectVariableImpl();
		return eObjectVariable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ListResult createListResult() {
		ListResultImpl listResult = new ListResultImpl();
		return listResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SetResult createSetResult() {
		SetResultImpl setResult = new SetResultImpl();
		return setResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Queries createQueries() {
		QueriesImpl queries = new QueriesImpl();
		return queries;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelElement createModelElement() {
		ModelElementImpl modelElement = new ModelElementImpl();
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QueryEvaluationResultExpectation createQueryEvaluationResultExpectation() {
		QueryEvaluationResultExpectationImpl queryEvaluationResultExpectation = new QueryEvaluationResultExpectationImpl();
		return queryEvaluationResultExpectation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ErrorResult createErrorResult() {
		ErrorResultImpl errorResult = new ErrorResultImpl();
		return errorResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SerializableResult createSerializableResult() {
		SerializableResultImpl serializableResult = new SerializableResultImpl();
		return serializableResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EnumeratorResult createEnumeratorResult() {
		EnumeratorResultImpl enumeratorResult = new EnumeratorResultImpl();
		return enumeratorResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BooleanResult createBooleanResult() {
		BooleanResultImpl booleanResult = new BooleanResultImpl();
		return booleanResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StringResult createStringResult() {
		StringResultImpl stringResult = new StringResultImpl();
		return stringResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EmptyResult createEmptyResult() {
		EmptyResultImpl emptyResult = new EmptyResultImpl();
		return emptyResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IntegerResult createIntegerResult() {
		IntegerResultImpl integerResult = new IntegerResultImpl();
		return integerResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QueryValidationResultExpectation createQueryValidationResultExpectation() {
		QueryValidationResultExpectationImpl queryValidationResultExpectation = new QueryValidationResultExpectationImpl();
		return queryValidationResultExpectation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QueryValidationResult createQueryValidationResult() {
		QueryValidationResultImpl queryValidationResult = new QueryValidationResultImpl();
		return queryValidationResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ValidationMessage createValidationMessage() {
		ValidationMessageImpl validationMessage = new ValidationMessageImpl();
		return validationMessage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InvalidResult createInvalidResult() {
		InvalidResultImpl invalidResult = new InvalidResultImpl();
		return invalidResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EObjectResult createEObjectResult() {
		EObjectResultImpl eObjectResult = new EObjectResultImpl();
		return eObjectResult;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Severity createSeverityFromString(EDataType eDataType, String initialValue) {
		Severity result = Severity.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertSeverityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Serializable createAnySerializableFromString(EDataType eDataType, String initialValue) {
		return (Serializable)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertAnySerializableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QmodelPackage getQmodelPackage() {
		return (QmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static QmodelPackage getPackage() {
		return QmodelPackage.eINSTANCE;
	}

} // QmodelFactoryImpl
