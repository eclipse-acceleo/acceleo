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
package org.eclipse.acceleo.query.tests.qmodel.util;

import org.eclipse.acceleo.query.tests.qmodel.BooleanResult;
import org.eclipse.acceleo.query.tests.qmodel.EObjectResult;
import org.eclipse.acceleo.query.tests.qmodel.EObjectVariable;
import org.eclipse.acceleo.query.tests.qmodel.EmptyResult;
import org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult;
import org.eclipse.acceleo.query.tests.qmodel.ErrorResult;
import org.eclipse.acceleo.query.tests.qmodel.Expectation;
import org.eclipse.acceleo.query.tests.qmodel.IntegerResult;
import org.eclipse.acceleo.query.tests.qmodel.InvalidResult;
import org.eclipse.acceleo.query.tests.qmodel.ListResult;
import org.eclipse.acceleo.query.tests.qmodel.ModelElement;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Queries;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation;
import org.eclipse.acceleo.query.tests.qmodel.SerializableResult;
import org.eclipse.acceleo.query.tests.qmodel.SetResult;
import org.eclipse.acceleo.query.tests.qmodel.StringResult;
import org.eclipse.acceleo.query.tests.qmodel.ValidationMessage;
import org.eclipse.acceleo.query.tests.qmodel.Variable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage
 * @generated
 */
public class QmodelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static QmodelPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QmodelSwitch() {
		if (modelPackage == null) {
			modelPackage = QmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case QmodelPackage.QUERY: {
				Query query = (Query)theEObject;
				T result = caseQuery(query);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.EOBJECT_VARIABLE: {
				EObjectVariable eObjectVariable = (EObjectVariable)theEObject;
				T result = caseEObjectVariable(eObjectVariable);
				if (result == null)
					result = caseVariable(eObjectVariable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.QUERY_EVALUATION_RESULT: {
				QueryEvaluationResult queryEvaluationResult = (QueryEvaluationResult)theEObject;
				T result = caseQueryEvaluationResult(queryEvaluationResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.LIST_RESULT: {
				ListResult listResult = (ListResult)theEObject;
				T result = caseListResult(listResult);
				if (result == null)
					result = caseQueryEvaluationResult(listResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.SET_RESULT: {
				SetResult setResult = (SetResult)theEObject;
				T result = caseSetResult(setResult);
				if (result == null)
					result = caseQueryEvaluationResult(setResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.QUERIES: {
				Queries queries = (Queries)theEObject;
				T result = caseQueries(queries);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.MODEL_ELEMENT: {
				ModelElement modelElement = (ModelElement)theEObject;
				T result = caseModelElement(modelElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.EXPECTATION: {
				Expectation expectation = (Expectation)theEObject;
				T result = caseExpectation(expectation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION: {
				QueryEvaluationResultExpectation queryEvaluationResultExpectation = (QueryEvaluationResultExpectation)theEObject;
				T result = caseQueryEvaluationResultExpectation(queryEvaluationResultExpectation);
				if (result == null)
					result = caseExpectation(queryEvaluationResultExpectation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.ERROR_RESULT: {
				ErrorResult errorResult = (ErrorResult)theEObject;
				T result = caseErrorResult(errorResult);
				if (result == null)
					result = caseQueryEvaluationResult(errorResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.SERIALIZABLE_RESULT: {
				SerializableResult serializableResult = (SerializableResult)theEObject;
				T result = caseSerializableResult(serializableResult);
				if (result == null)
					result = caseQueryEvaluationResult(serializableResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.ENUMERATOR_RESULT: {
				EnumeratorResult enumeratorResult = (EnumeratorResult)theEObject;
				T result = caseEnumeratorResult(enumeratorResult);
				if (result == null)
					result = caseQueryEvaluationResult(enumeratorResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.BOOLEAN_RESULT: {
				BooleanResult booleanResult = (BooleanResult)theEObject;
				T result = caseBooleanResult(booleanResult);
				if (result == null)
					result = caseQueryEvaluationResult(booleanResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.STRING_RESULT: {
				StringResult stringResult = (StringResult)theEObject;
				T result = caseStringResult(stringResult);
				if (result == null)
					result = caseQueryEvaluationResult(stringResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.EMPTY_RESULT: {
				EmptyResult emptyResult = (EmptyResult)theEObject;
				T result = caseEmptyResult(emptyResult);
				if (result == null)
					result = caseQueryEvaluationResult(emptyResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.INTEGER_RESULT: {
				IntegerResult integerResult = (IntegerResult)theEObject;
				T result = caseIntegerResult(integerResult);
				if (result == null)
					result = caseQueryEvaluationResult(integerResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.QUERY_VALIDATION_RESULT_EXPECTATION: {
				QueryValidationResultExpectation queryValidationResultExpectation = (QueryValidationResultExpectation)theEObject;
				T result = caseQueryValidationResultExpectation(queryValidationResultExpectation);
				if (result == null)
					result = caseExpectation(queryValidationResultExpectation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.QUERY_VALIDATION_RESULT: {
				QueryValidationResult queryValidationResult = (QueryValidationResult)theEObject;
				T result = caseQueryValidationResult(queryValidationResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.VALIDATION_MESSAGE: {
				ValidationMessage validationMessage = (ValidationMessage)theEObject;
				T result = caseValidationMessage(validationMessage);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.INVALID_RESULT: {
				InvalidResult invalidResult = (InvalidResult)theEObject;
				T result = caseInvalidResult(invalidResult);
				if (result == null)
					result = caseQueryEvaluationResult(invalidResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case QmodelPackage.EOBJECT_RESULT: {
				EObjectResult eObjectResult = (EObjectResult)theEObject;
				T result = caseEObjectResult(eObjectResult);
				if (result == null)
					result = caseQueryEvaluationResult(eObjectResult);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuery(Query object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject Variable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEObjectVariable(EObjectVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Evaluation Result</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Evaluation Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryEvaluationResult(QueryEvaluationResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>List Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>List Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseListResult(ListResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetResult(SetResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Queries</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Queries</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueries(Queries object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelElement(ModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expectation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expectation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpectation(Expectation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Query Evaluation Result Expectation</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Query Evaluation Result Expectation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryEvaluationResultExpectation(QueryEvaluationResultExpectation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorResult(ErrorResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Serializable Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Serializable Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSerializableResult(SerializableResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumerator Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumerator Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumeratorResult(EnumeratorResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanResult(BooleanResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringResult(StringResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Empty Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Empty Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEmptyResult(EmptyResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerResult(IntegerResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Query Validation Result Expectation</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Query Validation Result Expectation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryValidationResultExpectation(QueryValidationResultExpectation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query Validation Result</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query Validation Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryValidationResult(QueryValidationResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Validation Message</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Validation Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValidationMessage(ValidationMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Invalid Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Invalid Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInvalidResult(InvalidResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject Result</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEObjectResult(EObjectResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // QmodelSwitch
