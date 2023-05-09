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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage
 * @generated
 */
public class QmodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static QmodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QmodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = QmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * 
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
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected QmodelSwitch<Adapter> modelSwitch = new QmodelSwitch<Adapter>() {
		@Override
		public Adapter caseQuery(Query object) {
			return createQueryAdapter();
		}

		@Override
		public Adapter caseVariable(Variable object) {
			return createVariableAdapter();
		}

		@Override
		public Adapter caseEObjectVariable(EObjectVariable object) {
			return createEObjectVariableAdapter();
		}

		@Override
		public Adapter caseQueryEvaluationResult(QueryEvaluationResult object) {
			return createQueryEvaluationResultAdapter();
		}

		@Override
		public Adapter caseListResult(ListResult object) {
			return createListResultAdapter();
		}

		@Override
		public Adapter caseSetResult(SetResult object) {
			return createSetResultAdapter();
		}

		@Override
		public Adapter caseQueries(Queries object) {
			return createQueriesAdapter();
		}

		@Override
		public Adapter caseModelElement(ModelElement object) {
			return createModelElementAdapter();
		}

		@Override
		public Adapter caseExpectation(Expectation object) {
			return createExpectationAdapter();
		}

		@Override
		public Adapter caseQueryEvaluationResultExpectation(QueryEvaluationResultExpectation object) {
			return createQueryEvaluationResultExpectationAdapter();
		}

		@Override
		public Adapter caseErrorResult(ErrorResult object) {
			return createErrorResultAdapter();
		}

		@Override
		public Adapter caseSerializableResult(SerializableResult object) {
			return createSerializableResultAdapter();
		}

		@Override
		public Adapter caseEnumeratorResult(EnumeratorResult object) {
			return createEnumeratorResultAdapter();
		}

		@Override
		public Adapter caseBooleanResult(BooleanResult object) {
			return createBooleanResultAdapter();
		}

		@Override
		public Adapter caseStringResult(StringResult object) {
			return createStringResultAdapter();
		}

		@Override
		public Adapter caseEmptyResult(EmptyResult object) {
			return createEmptyResultAdapter();
		}

		@Override
		public Adapter caseIntegerResult(IntegerResult object) {
			return createIntegerResultAdapter();
		}

		@Override
		public Adapter caseQueryValidationResultExpectation(QueryValidationResultExpectation object) {
			return createQueryValidationResultExpectationAdapter();
		}

		@Override
		public Adapter caseQueryValidationResult(QueryValidationResult object) {
			return createQueryValidationResultAdapter();
		}

		@Override
		public Adapter caseValidationMessage(ValidationMessage object) {
			return createValidationMessageAdapter();
		}

		@Override
		public Adapter caseInvalidResult(InvalidResult object) {
			return createInvalidResultAdapter();
		}

		@Override
		public Adapter caseEObjectResult(EObjectResult object) {
			return createEObjectResultAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.qmodel.Query
	 * <em>Query</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query
	 * @generated
	 */
	public Adapter createQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.qmodel.Variable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EObjectVariable <em>EObject Variable</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EObjectVariable
	 * @generated
	 */
	public Adapter createEObjectVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult <em>Query Evaluation Result</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult
	 * @generated
	 */
	public Adapter createQueryEvaluationResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.qmodel.ListResult
	 * <em>List Result</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ListResult
	 * @generated
	 */
	public Adapter createListResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.qmodel.SetResult
	 * <em>Set Result</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.SetResult
	 * @generated
	 */
	public Adapter createSetResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.qmodel.Queries
	 * <em>Queries</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Queries
	 * @generated
	 */
	public Adapter createQueriesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ModelElement <em>Model Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ModelElement
	 * @generated
	 */
	public Adapter createModelElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Expectation <em>Expectation</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Expectation
	 * @generated
	 */
	public Adapter createExpectationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation
	 * <em>Query Evaluation Result Expectation</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation
	 * @generated
	 */
	public Adapter createQueryEvaluationResultExpectationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ErrorResult <em>Error Result</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ErrorResult
	 * @generated
	 */
	public Adapter createErrorResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.SerializableResult <em>Serializable Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.SerializableResult
	 * @generated
	 */
	public Adapter createSerializableResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult <em>Enumerator Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult
	 * @generated
	 */
	public Adapter createEnumeratorResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.BooleanResult <em>Boolean Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.BooleanResult
	 * @generated
	 */
	public Adapter createBooleanResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.StringResult <em>String Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.StringResult
	 * @generated
	 */
	public Adapter createStringResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EmptyResult <em>Empty Result</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EmptyResult
	 * @generated
	 */
	public Adapter createEmptyResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.IntegerResult <em>Integer Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.IntegerResult
	 * @generated
	 */
	public Adapter createIntegerResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation
	 * <em>Query Validation Result Expectation</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation
	 * @generated
	 */
	public Adapter createQueryValidationResultExpectationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult <em>Query Validation Result</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult
	 * @generated
	 */
	public Adapter createQueryValidationResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage <em>Validation Message</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ValidationMessage
	 * @generated
	 */
	public Adapter createValidationMessageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.InvalidResult <em>Invalid Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.InvalidResult
	 * @generated
	 */
	public Adapter createInvalidResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EObjectResult <em>EObject Result</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EObjectResult
	 * @generated
	 */
	public Adapter createEObjectResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // QmodelAdapterFactory
