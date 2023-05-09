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
package org.eclipse.acceleo.query.tests.qmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelFactory
 * @model kind="package"
 * @generated
 */
public interface QmodelPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "qmodel";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/qmodel";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "qmodel";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	QmodelPackage eINSTANCE = org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl
	 * <em>Query</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQuery()
	 * @generated
	 */
	int QUERY = 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Starting Point</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__STARTING_POINT = 1;

	/**
	 * The feature id for the '<em><b>Current Results</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__CURRENT_RESULTS = 2;

	/**
	 * The feature id for the '<em><b>Expectations</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__EXPECTATIONS = 3;

	/**
	 * The feature id for the '<em><b>Classes To Import</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__CLASSES_TO_IMPORT = 4;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__VARIABLES = 5;

	/**
	 * The feature id for the '<em><b>Plugins In Class Path</b></em>' attribute list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY__PLUGINS_IN_CLASS_PATH = 6;

	/**
	 * The number of structural features of the '<em>Query</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Query</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.VariableImpl
	 * <em>Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.VariableImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Variable</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.EObjectVariableImpl
	 * <em>EObject Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EObjectVariableImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEObjectVariable()
	 * @generated
	 */
	int EOBJECT_VARIABLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VARIABLE__VALUE = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Variable</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>EObject Variable</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_VARIABLE_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultImpl
	 * <em>Query Evaluation Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryEvaluationResult()
	 * @generated
	 */
	int QUERY_EVALUATION_RESULT = 3;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_EVALUATION_RESULT__INTERPRETER = 0;

	/**
	 * The number of structural features of the '<em>Query Evaluation Result</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_EVALUATION_RESULT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Query Evaluation Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_EVALUATION_RESULT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.ListResultImpl
	 * <em>List Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ListResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getListResult()
	 * @generated
	 */
	int LIST_RESULT = 4;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIST_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIST_RESULT__VALUES = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>List Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIST_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>List Result</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIST_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.SetResultImpl
	 * <em>Set Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.SetResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getSetResult()
	 * @generated
	 */
	int SET_RESULT = 5;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_RESULT__VALUES = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Set Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Set Result</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueriesImpl
	 * <em>Queries</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueriesImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueries()
	 * @generated
	 */
	int QUERIES = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERIES__NAME = 0;

	/**
	 * The feature id for the '<em><b>Queries</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERIES__QUERIES = 1;

	/**
	 * The feature id for the '<em><b>Model Elements</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERIES__MODEL_ELEMENTS = 2;

	/**
	 * The number of structural features of the '<em>Queries</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERIES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Queries</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERIES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.ModelElementImpl
	 * <em>Model Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ModelElementImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getModelElement()
	 * @generated
	 */
	int MODEL_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__TARGET = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__NAME = 1;

	/**
	 * The number of structural features of the '<em>Model Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Model Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.ExpectationImpl
	 * <em>Expectation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ExpectationImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getExpectation()
	 * @generated
	 */
	int EXPECTATION = 8;

	/**
	 * The number of structural features of the '<em>Expectation</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPECTATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Expectation</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EXPECTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultExpectationImpl
	 * <em>Query Evaluation Result Expectation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultExpectationImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryEvaluationResultExpectation()
	 * @generated
	 */
	int QUERY_EVALUATION_RESULT_EXPECTATION = 9;

	/**
	 * The feature id for the '<em><b>Expected Result</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT = EXPECTATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Query Evaluation Result Expectation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_EVALUATION_RESULT_EXPECTATION_FEATURE_COUNT = EXPECTATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Query Evaluation Result Expectation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_EVALUATION_RESULT_EXPECTATION_OPERATION_COUNT = EXPECTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.ErrorResultImpl
	 * <em>Error Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ErrorResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getErrorResult()
	 * @generated
	 */
	int ERROR_RESULT = 10;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_RESULT__MESSAGE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Error Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ERROR_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.SerializableResultImpl
	 * <em>Serializable Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.SerializableResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getSerializableResult()
	 * @generated
	 */
	int SERIALIZABLE_RESULT = 11;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERIALIZABLE_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERIALIZABLE_RESULT__VALUE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Serializable Result</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERIALIZABLE_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Serializable Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERIALIZABLE_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.EnumeratorResultImpl
	 * <em>Enumerator Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EnumeratorResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEnumeratorResult()
	 * @generated
	 */
	int ENUMERATOR_RESULT = 12;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUMERATOR_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUMERATOR_RESULT__VALUE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumerator Result</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUMERATOR_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Enumerator Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ENUMERATOR_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.BooleanResultImpl
	 * <em>Boolean Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.BooleanResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getBooleanResult()
	 * @generated
	 */
	int BOOLEAN_RESULT = 13;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_RESULT__VALUE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Boolean Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.StringResultImpl
	 * <em>String Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.StringResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getStringResult()
	 * @generated
	 */
	int STRING_RESULT = 14;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_RESULT__VALUE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STRING_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.EmptyResultImpl
	 * <em>Empty Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EmptyResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEmptyResult()
	 * @generated
	 */
	int EMPTY_RESULT = 15;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EMPTY_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The number of structural features of the '<em>Empty Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EMPTY_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Empty Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EMPTY_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.IntegerResultImpl
	 * <em>Integer Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.IntegerResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getIntegerResult()
	 * @generated
	 */
	int INTEGER_RESULT = 16;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_RESULT__VALUE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultExpectationImpl
	 * <em>Query Validation Result Expectation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultExpectationImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryValidationResultExpectation()
	 * @generated
	 */
	int QUERY_VALIDATION_RESULT_EXPECTATION = 17;

	/**
	 * The feature id for the '<em><b>Expected Result</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT_EXPECTATION__EXPECTED_RESULT = EXPECTATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Query Validation Result Expectation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT_EXPECTATION_FEATURE_COUNT = EXPECTATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Query Validation Result Expectation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT_EXPECTATION_OPERATION_COUNT = EXPECTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultImpl
	 * <em>Query Validation Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryValidationResult()
	 * @generated
	 */
	int QUERY_VALIDATION_RESULT = 18;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT__INTERPRETER = 0;

	/**
	 * The feature id for the '<em><b>Possible Types</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT__POSSIBLE_TYPES = 1;

	/**
	 * The feature id for the '<em><b>Validation Messages</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT__VALIDATION_MESSAGES = 2;

	/**
	 * The number of structural features of the '<em>Query Validation Result</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Query Validation Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int QUERY_VALIDATION_RESULT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl
	 * <em>Validation Message</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getValidationMessage()
	 * @generated
	 */
	int VALIDATION_MESSAGE = 19;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MESSAGE__SEVERITY = 0;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MESSAGE__MESSAGE = 1;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MESSAGE__START_POSITION = 2;

	/**
	 * The feature id for the '<em><b>End Position</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MESSAGE__END_POSITION = 3;

	/**
	 * The number of structural features of the '<em>Validation Message</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MESSAGE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Validation Message</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALIDATION_MESSAGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.InvalidResultImpl
	 * <em>Invalid Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.InvalidResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getInvalidResult()
	 * @generated
	 */
	int INVALID_RESULT = 20;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INVALID_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The number of structural features of the '<em>Invalid Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INVALID_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Invalid Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INVALID_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.EObjectResultImpl
	 * <em>EObject Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EObjectResultImpl
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEObjectResult()
	 * @generated
	 */
	int EOBJECT_RESULT = 21;

	/**
	 * The feature id for the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_RESULT__INTERPRETER = QUERY_EVALUATION_RESULT__INTERPRETER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_RESULT__VALUE = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_RESULT_FEATURE_COUNT = QUERY_EVALUATION_RESULT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>EObject Result</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EOBJECT_RESULT_OPERATION_COUNT = QUERY_EVALUATION_RESULT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.qmodel.Severity <em>Severity</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.qmodel.Severity
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getSeverity()
	 * @generated
	 */
	int SEVERITY = 22;

	/**
	 * The meta object id for the '<em>Any Serializable</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see java.io.Serializable
	 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getAnySerializable()
	 * @generated
	 */
	int ANY_SERIALIZABLE = 23;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.Query <em>Query</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query
	 * @generated
	 */
	EClass getQuery();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getExpression <em>Expression</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getExpression()
	 * @see #getQuery()
	 * @generated
	 */
	EAttribute getQuery_Expression();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getStartingPoint <em>Starting Point</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Starting Point</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getStartingPoint()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_StartingPoint();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getCurrentResults <em>Current Results</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Current Results</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getCurrentResults()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_CurrentResults();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getExpectations <em>Expectations</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Expectations</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getExpectations()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Expectations();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getClassesToImport <em>Classes To Import</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Classes To Import</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getClassesToImport()
	 * @see #getQuery()
	 * @generated
	 */
	EAttribute getQuery_ClassesToImport();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getVariables <em>Variables</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getVariables()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Variables();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Query#getPluginsInClassPath
	 * <em>Plugins In Class Path</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Plugins In Class Path</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Query#getPluginsInClassPath()
	 * @see #getQuery()
	 * @generated
	 */
	EAttribute getQuery_PluginsInClassPath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.Variable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Variable#getName <em>Name</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.EObjectVariable
	 * <em>EObject Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>EObject Variable</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EObjectVariable
	 * @generated
	 */
	EClass getEObjectVariable();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EObjectVariable#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EObjectVariable#getValue()
	 * @see #getEObjectVariable()
	 * @generated
	 */
	EReference getEObjectVariable_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult
	 * <em>Query Evaluation Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query Evaluation Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult
	 * @generated
	 */
	EClass getQueryEvaluationResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult#getInterpreter
	 * <em>Interpreter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Interpreter</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult#getInterpreter()
	 * @see #getQueryEvaluationResult()
	 * @generated
	 */
	EAttribute getQueryEvaluationResult_Interpreter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.ListResult
	 * <em>List Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>List Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ListResult
	 * @generated
	 */
	EClass getListResult();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ListResult#getValues <em>Values</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ListResult#getValues()
	 * @see #getListResult()
	 * @generated
	 */
	EReference getListResult_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.SetResult
	 * <em>Set Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Set Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.SetResult
	 * @generated
	 */
	EClass getSetResult();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.SetResult#getValues <em>Values</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.SetResult#getValues()
	 * @see #getSetResult()
	 * @generated
	 */
	EReference getSetResult_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.Queries
	 * <em>Queries</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Queries</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Queries
	 * @generated
	 */
	EClass getQueries();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Queries#getName <em>Name</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Queries#getName()
	 * @see #getQueries()
	 * @generated
	 */
	EAttribute getQueries_Name();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Queries#getQueries <em>Queries</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Queries</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Queries#getQueries()
	 * @see #getQueries()
	 * @generated
	 */
	EReference getQueries_Queries();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.Queries#getModelElements <em>Model Elements</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Model Elements</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Queries#getModelElements()
	 * @see #getQueries()
	 * @generated
	 */
	EReference getQueries_ModelElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.ModelElement
	 * <em>Model Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ModelElement
	 * @generated
	 */
	EClass getModelElement();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ModelElement#getTarget <em>Target</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ModelElement#getTarget()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_Target();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ModelElement#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ModelElement#getName()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.Expectation
	 * <em>Expectation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Expectation</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Expectation
	 * @generated
	 */
	EClass getExpectation();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation
	 * <em>Query Evaluation Result Expectation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query Evaluation Result Expectation</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation
	 * @generated
	 */
	EClass getQueryEvaluationResultExpectation();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation#getExpectedResult
	 * <em>Expected Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expected Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation#getExpectedResult()
	 * @see #getQueryEvaluationResultExpectation()
	 * @generated
	 */
	EReference getQueryEvaluationResultExpectation_ExpectedResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.ErrorResult
	 * <em>Error Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Error Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ErrorResult
	 * @generated
	 */
	EClass getErrorResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ErrorResult#getMessage <em>Message</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ErrorResult#getMessage()
	 * @see #getErrorResult()
	 * @generated
	 */
	EAttribute getErrorResult_Message();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.SerializableResult
	 * <em>Serializable Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Serializable Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.SerializableResult
	 * @generated
	 */
	EClass getSerializableResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.SerializableResult#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.SerializableResult#getValue()
	 * @see #getSerializableResult()
	 * @generated
	 */
	EAttribute getSerializableResult_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult
	 * <em>Enumerator Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Enumerator Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult
	 * @generated
	 */
	EClass getEnumeratorResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult#getValue()
	 * @see #getEnumeratorResult()
	 * @generated
	 */
	EAttribute getEnumeratorResult_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.BooleanResult
	 * <em>Boolean Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Boolean Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.BooleanResult
	 * @generated
	 */
	EClass getBooleanResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.BooleanResult#isValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.BooleanResult#isValue()
	 * @see #getBooleanResult()
	 * @generated
	 */
	EAttribute getBooleanResult_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.StringResult
	 * <em>String Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>String Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.StringResult
	 * @generated
	 */
	EClass getStringResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.StringResult#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.StringResult#getValue()
	 * @see #getStringResult()
	 * @generated
	 */
	EAttribute getStringResult_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.EmptyResult
	 * <em>Empty Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Empty Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EmptyResult
	 * @generated
	 */
	EClass getEmptyResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.IntegerResult
	 * <em>Integer Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Integer Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.IntegerResult
	 * @generated
	 */
	EClass getIntegerResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.IntegerResult#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.IntegerResult#getValue()
	 * @see #getIntegerResult()
	 * @generated
	 */
	EAttribute getIntegerResult_Value();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation
	 * <em>Query Validation Result Expectation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query Validation Result Expectation</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation
	 * @generated
	 */
	EClass getQueryValidationResultExpectation();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation#getExpectedResult
	 * <em>Expected Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expected Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation#getExpectedResult()
	 * @see #getQueryValidationResultExpectation()
	 * @generated
	 */
	EReference getQueryValidationResultExpectation_ExpectedResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult
	 * <em>Query Validation Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Query Validation Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult
	 * @generated
	 */
	EClass getQueryValidationResult();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getInterpreter
	 * <em>Interpreter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Interpreter</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getInterpreter()
	 * @see #getQueryValidationResult()
	 * @generated
	 */
	EAttribute getQueryValidationResult_Interpreter();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getPossibleTypes
	 * <em>Possible Types</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Possible Types</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getPossibleTypes()
	 * @see #getQueryValidationResult()
	 * @generated
	 */
	EAttribute getQueryValidationResult_PossibleTypes();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getValidationMessages
	 * <em>Validation Messages</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Validation Messages</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult#getValidationMessages()
	 * @see #getQueryValidationResult()
	 * @generated
	 */
	EReference getQueryValidationResult_ValidationMessages();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage
	 * <em>Validation Message</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Validation Message</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ValidationMessage
	 * @generated
	 */
	EClass getValidationMessage();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getSeverity <em>Severity</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getSeverity()
	 * @see #getValidationMessage()
	 * @generated
	 */
	EAttribute getValidationMessage_Severity();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getMessage <em>Message</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getMessage()
	 * @see #getValidationMessage()
	 * @generated
	 */
	EAttribute getValidationMessage_Message();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getStartPosition
	 * <em>Start Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Start Position</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getStartPosition()
	 * @see #getValidationMessage()
	 * @generated
	 */
	EAttribute getValidationMessage_StartPosition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getEndPosition <em>End Position</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Position</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.ValidationMessage#getEndPosition()
	 * @see #getValidationMessage()
	 * @generated
	 */
	EAttribute getValidationMessage_EndPosition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.InvalidResult
	 * <em>Invalid Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Invalid Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.InvalidResult
	 * @generated
	 */
	EClass getInvalidResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.qmodel.EObjectResult
	 * <em>EObject Result</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>EObject Result</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EObjectResult
	 * @generated
	 */
	EClass getEObjectResult();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.EObjectResult#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.EObjectResult#getValue()
	 * @see #getEObjectResult()
	 * @generated
	 */
	EReference getEObjectResult_Value();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.qmodel.Severity
	 * <em>Severity</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Severity</em>'.
	 * @see org.eclipse.acceleo.query.tests.qmodel.Severity
	 * @generated
	 */
	EEnum getSeverity();

	/**
	 * Returns the meta object for data type '{@link java.io.Serializable <em>Any Serializable</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Any Serializable</em>'.
	 * @see java.io.Serializable
	 * @model instanceClass="java.io.Serializable"
	 * @generated
	 */
	EDataType getAnySerializable();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	QmodelFactory getQmodelFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl
		 * <em>Query</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQuery()
		 * @generated
		 */
		EClass QUERY = eINSTANCE.getQuery();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY__EXPRESSION = eINSTANCE.getQuery_Expression();

		/**
		 * The meta object literal for the '<em><b>Starting Point</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__STARTING_POINT = eINSTANCE.getQuery_StartingPoint();

		/**
		 * The meta object literal for the '<em><b>Current Results</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__CURRENT_RESULTS = eINSTANCE.getQuery_CurrentResults();

		/**
		 * The meta object literal for the '<em><b>Expectations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__EXPECTATIONS = eINSTANCE.getQuery_Expectations();

		/**
		 * The meta object literal for the '<em><b>Classes To Import</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY__CLASSES_TO_IMPORT = eINSTANCE.getQuery_ClassesToImport();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY__VARIABLES = eINSTANCE.getQuery_Variables();

		/**
		 * The meta object literal for the '<em><b>Plugins In Class Path</b></em>' attribute list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY__PLUGINS_IN_CLASS_PATH = eINSTANCE.getQuery_PluginsInClassPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.VariableImpl
		 * <em>Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.VariableImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.EObjectVariableImpl <em>EObject Variable</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EObjectVariableImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEObjectVariable()
		 * @generated
		 */
		EClass EOBJECT_VARIABLE = eINSTANCE.getEObjectVariable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EOBJECT_VARIABLE__VALUE = eINSTANCE.getEObjectVariable_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultImpl
		 * <em>Query Evaluation Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryEvaluationResult()
		 * @generated
		 */
		EClass QUERY_EVALUATION_RESULT = eINSTANCE.getQueryEvaluationResult();

		/**
		 * The meta object literal for the '<em><b>Interpreter</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY_EVALUATION_RESULT__INTERPRETER = eINSTANCE.getQueryEvaluationResult_Interpreter();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.ListResultImpl
		 * <em>List Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ListResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getListResult()
		 * @generated
		 */
		EClass LIST_RESULT = eINSTANCE.getListResult();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LIST_RESULT__VALUES = eINSTANCE.getListResult_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.SetResultImpl
		 * <em>Set Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.SetResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getSetResult()
		 * @generated
		 */
		EClass SET_RESULT = eINSTANCE.getSetResult();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SET_RESULT__VALUES = eINSTANCE.getSetResult_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueriesImpl
		 * <em>Queries</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueriesImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueries()
		 * @generated
		 */
		EClass QUERIES = eINSTANCE.getQueries();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERIES__NAME = eINSTANCE.getQueries_Name();

		/**
		 * The meta object literal for the '<em><b>Queries</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERIES__QUERIES = eINSTANCE.getQueries_Queries();

		/**
		 * The meta object literal for the '<em><b>Model Elements</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERIES__MODEL_ELEMENTS = eINSTANCE.getQueries_ModelElements();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.ModelElementImpl <em>Model Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ModelElementImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getModelElement()
		 * @generated
		 */
		EClass MODEL_ELEMENT = eINSTANCE.getModelElement();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MODEL_ELEMENT__TARGET = eINSTANCE.getModelElement_Target();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODEL_ELEMENT__NAME = eINSTANCE.getModelElement_Name();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.ExpectationImpl <em>Expectation</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ExpectationImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getExpectation()
		 * @generated
		 */
		EClass EXPECTATION = eINSTANCE.getExpectation();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultExpectationImpl
		 * <em>Query Evaluation Result Expectation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryEvaluationResultExpectationImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryEvaluationResultExpectation()
		 * @generated
		 */
		EClass QUERY_EVALUATION_RESULT_EXPECTATION = eINSTANCE.getQueryEvaluationResultExpectation();

		/**
		 * The meta object literal for the '<em><b>Expected Result</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT = eINSTANCE
				.getQueryEvaluationResultExpectation_ExpectedResult();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.ErrorResultImpl <em>Error Result</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ErrorResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getErrorResult()
		 * @generated
		 */
		EClass ERROR_RESULT = eINSTANCE.getErrorResult();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ERROR_RESULT__MESSAGE = eINSTANCE.getErrorResult_Message();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.SerializableResultImpl
		 * <em>Serializable Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.SerializableResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getSerializableResult()
		 * @generated
		 */
		EClass SERIALIZABLE_RESULT = eINSTANCE.getSerializableResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SERIALIZABLE_RESULT__VALUE = eINSTANCE.getSerializableResult_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.EnumeratorResultImpl <em>Enumerator Result</em>}
		 * ' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EnumeratorResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEnumeratorResult()
		 * @generated
		 */
		EClass ENUMERATOR_RESULT = eINSTANCE.getEnumeratorResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ENUMERATOR_RESULT__VALUE = eINSTANCE.getEnumeratorResult_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.BooleanResultImpl <em>Boolean Result</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.BooleanResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getBooleanResult()
		 * @generated
		 */
		EClass BOOLEAN_RESULT = eINSTANCE.getBooleanResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOLEAN_RESULT__VALUE = eINSTANCE.getBooleanResult_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.StringResultImpl <em>String Result</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.StringResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getStringResult()
		 * @generated
		 */
		EClass STRING_RESULT = eINSTANCE.getStringResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STRING_RESULT__VALUE = eINSTANCE.getStringResult_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.EmptyResultImpl <em>Empty Result</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EmptyResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEmptyResult()
		 * @generated
		 */
		EClass EMPTY_RESULT = eINSTANCE.getEmptyResult();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.IntegerResultImpl <em>Integer Result</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.IntegerResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getIntegerResult()
		 * @generated
		 */
		EClass INTEGER_RESULT = eINSTANCE.getIntegerResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGER_RESULT__VALUE = eINSTANCE.getIntegerResult_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultExpectationImpl
		 * <em>Query Validation Result Expectation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultExpectationImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryValidationResultExpectation()
		 * @generated
		 */
		EClass QUERY_VALIDATION_RESULT_EXPECTATION = eINSTANCE.getQueryValidationResultExpectation();

		/**
		 * The meta object literal for the '<em><b>Expected Result</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY_VALIDATION_RESULT_EXPECTATION__EXPECTED_RESULT = eINSTANCE
				.getQueryValidationResultExpectation_ExpectedResult();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultImpl
		 * <em>Query Validation Result</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QueryValidationResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getQueryValidationResult()
		 * @generated
		 */
		EClass QUERY_VALIDATION_RESULT = eINSTANCE.getQueryValidationResult();

		/**
		 * The meta object literal for the '<em><b>Interpreter</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY_VALIDATION_RESULT__INTERPRETER = eINSTANCE.getQueryValidationResult_Interpreter();

		/**
		 * The meta object literal for the '<em><b>Possible Types</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute QUERY_VALIDATION_RESULT__POSSIBLE_TYPES = eINSTANCE
				.getQueryValidationResult_PossibleTypes();

		/**
		 * The meta object literal for the '<em><b>Validation Messages</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference QUERY_VALIDATION_RESULT__VALIDATION_MESSAGES = eINSTANCE
				.getQueryValidationResult_ValidationMessages();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl
		 * <em>Validation Message</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.ValidationMessageImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getValidationMessage()
		 * @generated
		 */
		EClass VALIDATION_MESSAGE = eINSTANCE.getValidationMessage();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VALIDATION_MESSAGE__SEVERITY = eINSTANCE.getValidationMessage_Severity();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VALIDATION_MESSAGE__MESSAGE = eINSTANCE.getValidationMessage_Message();

		/**
		 * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VALIDATION_MESSAGE__START_POSITION = eINSTANCE.getValidationMessage_StartPosition();

		/**
		 * The meta object literal for the '<em><b>End Position</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VALIDATION_MESSAGE__END_POSITION = eINSTANCE.getValidationMessage_EndPosition();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.InvalidResultImpl <em>Invalid Result</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.InvalidResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getInvalidResult()
		 * @generated
		 */
		EClass INVALID_RESULT = eINSTANCE.getInvalidResult();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.qmodel.impl.EObjectResultImpl <em>EObject Result</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.EObjectResultImpl
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getEObjectResult()
		 * @generated
		 */
		EClass EOBJECT_RESULT = eINSTANCE.getEObjectResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EOBJECT_RESULT__VALUE = eINSTANCE.getEObjectResult_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.qmodel.Severity
		 * <em>Severity</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.qmodel.Severity
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getSeverity()
		 * @generated
		 */
		EEnum SEVERITY = eINSTANCE.getSeverity();

		/**
		 * The meta object literal for the '<em>Any Serializable</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.io.Serializable
		 * @see org.eclipse.acceleo.query.tests.qmodel.impl.QmodelPackageImpl#getAnySerializable()
		 * @generated
		 */
		EDataType ANY_SERIALIZABLE = eINSTANCE.getAnySerializable();

	}

} // QmodelPackage
