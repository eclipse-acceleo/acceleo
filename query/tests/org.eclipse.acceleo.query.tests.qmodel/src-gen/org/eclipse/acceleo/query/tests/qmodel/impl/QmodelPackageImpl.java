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
import org.eclipse.acceleo.query.tests.qmodel.Expectation;
import org.eclipse.acceleo.query.tests.qmodel.IntegerResult;
import org.eclipse.acceleo.query.tests.qmodel.InvalidResult;
import org.eclipse.acceleo.query.tests.qmodel.ListResult;
import org.eclipse.acceleo.query.tests.qmodel.ModelElement;
import org.eclipse.acceleo.query.tests.qmodel.QmodelFactory;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Queries;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation;
import org.eclipse.acceleo.query.tests.qmodel.SerializableResult;
import org.eclipse.acceleo.query.tests.qmodel.SetResult;
import org.eclipse.acceleo.query.tests.qmodel.Severity;
import org.eclipse.acceleo.query.tests.qmodel.StringResult;
import org.eclipse.acceleo.query.tests.qmodel.ValidationMessage;
import org.eclipse.acceleo.query.tests.qmodel.Variable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class QmodelPackageImpl extends EPackageImpl implements QmodelPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eObjectVariableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryEvaluationResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass listResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass setResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queriesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass modelElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass expectationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryEvaluationResultExpectationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass serializableResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass enumeratorResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass booleanResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass emptyResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass integerResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryValidationResultExpectationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryValidationResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass validationMessageEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass invalidResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eObjectResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum severityEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType anySerializableEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private QmodelPackageImpl() {
		super(eNS_URI, QmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link QmodelPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static QmodelPackage init() {
		if (isInited)
			return (QmodelPackage)EPackage.Registry.INSTANCE.getEPackage(QmodelPackage.eNS_URI);

		// Obtain or create and register package
		QmodelPackageImpl theQmodelPackage = (QmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof QmodelPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI)
				: new QmodelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theQmodelPackage.createPackageContents();

		// Initialize created meta-data
		theQmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theQmodelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(QmodelPackage.eNS_URI, theQmodelPackage);
		return theQmodelPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQuery() {
		return queryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQuery_Expression() {
		return (EAttribute)queryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_StartingPoint() {
		return (EReference)queryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_CurrentResults() {
		return (EReference)queryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_Expectations() {
		return (EReference)queryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQuery_ClassesToImport() {
		return (EAttribute)queryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_Variables() {
		return (EReference)queryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQuery_PluginsInClassPath() {
		return (EAttribute)queryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getEObjectVariable() {
		return eObjectVariableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEObjectVariable_Value() {
		return (EReference)eObjectVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQueryEvaluationResult() {
		return queryEvaluationResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQueryEvaluationResult_Interpreter() {
		return (EAttribute)queryEvaluationResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getListResult() {
		return listResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getListResult_Values() {
		return (EReference)listResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSetResult() {
		return setResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSetResult_Values() {
		return (EReference)setResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQueries() {
		return queriesEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQueries_Name() {
		return (EAttribute)queriesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueries_Queries() {
		return (EReference)queriesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueries_ModelElements() {
		return (EReference)queriesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModelElement() {
		return modelElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModelElement_Target() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModelElement_Name() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getExpectation() {
		return expectationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQueryEvaluationResultExpectation() {
		return queryEvaluationResultExpectationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueryEvaluationResultExpectation_ExpectedResult() {
		return (EReference)queryEvaluationResultExpectationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getErrorResult() {
		return errorResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getErrorResult_Message() {
		return (EAttribute)errorResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSerializableResult() {
		return serializableResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSerializableResult_Value() {
		return (EAttribute)serializableResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getEnumeratorResult() {
		return enumeratorResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getEnumeratorResult_Value() {
		return (EAttribute)enumeratorResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBooleanResult() {
		return booleanResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBooleanResult_Value() {
		return (EAttribute)booleanResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStringResult() {
		return stringResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getStringResult_Value() {
		return (EAttribute)stringResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getEmptyResult() {
		return emptyResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIntegerResult() {
		return integerResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getIntegerResult_Value() {
		return (EAttribute)integerResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQueryValidationResultExpectation() {
		return queryValidationResultExpectationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueryValidationResultExpectation_ExpectedResult() {
		return (EReference)queryValidationResultExpectationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQueryValidationResult() {
		return queryValidationResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQueryValidationResult_Interpreter() {
		return (EAttribute)queryValidationResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getQueryValidationResult_PossibleTypes() {
		return (EAttribute)queryValidationResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueryValidationResult_ValidationMessages() {
		return (EReference)queryValidationResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getValidationMessage() {
		return validationMessageEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getValidationMessage_Severity() {
		return (EAttribute)validationMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getValidationMessage_Message() {
		return (EAttribute)validationMessageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getValidationMessage_StartPosition() {
		return (EAttribute)validationMessageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getValidationMessage_EndPosition() {
		return (EAttribute)validationMessageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInvalidResult() {
		return invalidResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getEObjectResult() {
		return eObjectResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEObjectResult_Value() {
		return (EReference)eObjectResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getSeverity() {
		return severityEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getAnySerializable() {
		return anySerializableEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QmodelFactory getQmodelFactory() {
		return (QmodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		queryEClass = createEClass(QUERY);
		createEAttribute(queryEClass, QUERY__EXPRESSION);
		createEReference(queryEClass, QUERY__STARTING_POINT);
		createEReference(queryEClass, QUERY__CURRENT_RESULTS);
		createEReference(queryEClass, QUERY__EXPECTATIONS);
		createEAttribute(queryEClass, QUERY__CLASSES_TO_IMPORT);
		createEReference(queryEClass, QUERY__VARIABLES);
		createEAttribute(queryEClass, QUERY__PLUGINS_IN_CLASS_PATH);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);

		eObjectVariableEClass = createEClass(EOBJECT_VARIABLE);
		createEReference(eObjectVariableEClass, EOBJECT_VARIABLE__VALUE);

		queryEvaluationResultEClass = createEClass(QUERY_EVALUATION_RESULT);
		createEAttribute(queryEvaluationResultEClass, QUERY_EVALUATION_RESULT__INTERPRETER);

		listResultEClass = createEClass(LIST_RESULT);
		createEReference(listResultEClass, LIST_RESULT__VALUES);

		setResultEClass = createEClass(SET_RESULT);
		createEReference(setResultEClass, SET_RESULT__VALUES);

		queriesEClass = createEClass(QUERIES);
		createEAttribute(queriesEClass, QUERIES__NAME);
		createEReference(queriesEClass, QUERIES__QUERIES);
		createEReference(queriesEClass, QUERIES__MODEL_ELEMENTS);

		modelElementEClass = createEClass(MODEL_ELEMENT);
		createEReference(modelElementEClass, MODEL_ELEMENT__TARGET);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__NAME);

		expectationEClass = createEClass(EXPECTATION);

		queryEvaluationResultExpectationEClass = createEClass(QUERY_EVALUATION_RESULT_EXPECTATION);
		createEReference(queryEvaluationResultExpectationEClass,
				QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT);

		errorResultEClass = createEClass(ERROR_RESULT);
		createEAttribute(errorResultEClass, ERROR_RESULT__MESSAGE);

		serializableResultEClass = createEClass(SERIALIZABLE_RESULT);
		createEAttribute(serializableResultEClass, SERIALIZABLE_RESULT__VALUE);

		enumeratorResultEClass = createEClass(ENUMERATOR_RESULT);
		createEAttribute(enumeratorResultEClass, ENUMERATOR_RESULT__VALUE);

		booleanResultEClass = createEClass(BOOLEAN_RESULT);
		createEAttribute(booleanResultEClass, BOOLEAN_RESULT__VALUE);

		stringResultEClass = createEClass(STRING_RESULT);
		createEAttribute(stringResultEClass, STRING_RESULT__VALUE);

		emptyResultEClass = createEClass(EMPTY_RESULT);

		integerResultEClass = createEClass(INTEGER_RESULT);
		createEAttribute(integerResultEClass, INTEGER_RESULT__VALUE);

		queryValidationResultExpectationEClass = createEClass(QUERY_VALIDATION_RESULT_EXPECTATION);
		createEReference(queryValidationResultExpectationEClass,
				QUERY_VALIDATION_RESULT_EXPECTATION__EXPECTED_RESULT);

		queryValidationResultEClass = createEClass(QUERY_VALIDATION_RESULT);
		createEAttribute(queryValidationResultEClass, QUERY_VALIDATION_RESULT__INTERPRETER);
		createEAttribute(queryValidationResultEClass, QUERY_VALIDATION_RESULT__POSSIBLE_TYPES);
		createEReference(queryValidationResultEClass, QUERY_VALIDATION_RESULT__VALIDATION_MESSAGES);

		validationMessageEClass = createEClass(VALIDATION_MESSAGE);
		createEAttribute(validationMessageEClass, VALIDATION_MESSAGE__SEVERITY);
		createEAttribute(validationMessageEClass, VALIDATION_MESSAGE__MESSAGE);
		createEAttribute(validationMessageEClass, VALIDATION_MESSAGE__START_POSITION);
		createEAttribute(validationMessageEClass, VALIDATION_MESSAGE__END_POSITION);

		invalidResultEClass = createEClass(INVALID_RESULT);

		eObjectResultEClass = createEClass(EOBJECT_RESULT);
		createEReference(eObjectResultEClass, EOBJECT_RESULT__VALUE);

		// Create enums
		severityEEnum = createEEnum(SEVERITY);

		// Create data types
		anySerializableEDataType = createEDataType(ANY_SERIALIZABLE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		eObjectVariableEClass.getESuperTypes().add(this.getVariable());
		listResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		setResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		queryEvaluationResultExpectationEClass.getESuperTypes().add(this.getExpectation());
		errorResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		serializableResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		enumeratorResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		booleanResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		stringResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		emptyResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		integerResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		queryValidationResultExpectationEClass.getESuperTypes().add(this.getExpectation());
		invalidResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());
		eObjectResultEClass.getESuperTypes().add(this.getQueryEvaluationResult());

		// Initialize classes, features, and operations; add parameters
		initEClass(queryEClass, Query.class, "Query", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQuery_Expression(), ecorePackage.getEString(), "expression", null, 1, 1,
				Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getQuery_StartingPoint(), this.getModelElement(), null, "startingPoint", null, 1, 1,
				Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuery_CurrentResults(), this.getQueryEvaluationResult(), null, "currentResults",
				null, 0, -1, Query.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getQuery_Expectations(), this.getExpectation(), null, "expectations", null, 0, -1,
				Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQuery_ClassesToImport(), ecorePackage.getEString(), "classesToImport", null, 0, -1,
				Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getQuery_Variables(), this.getVariable(), null, "variables", null, 0, -1, Query.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQuery_PluginsInClassPath(), ecorePackage.getEString(), "pluginsInClassPath", null,
				0, -1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Name(), ecorePackage.getEString(), "name", null, 1, 1, Variable.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(eObjectVariableEClass, EObjectVariable.class, "EObjectVariable", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectVariable_Value(), this.getModelElement(), null, "value", null, 1, 1,
				EObjectVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queryEvaluationResultEClass, QueryEvaluationResult.class, "QueryEvaluationResult",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueryEvaluationResult_Interpreter(), ecorePackage.getEString(), "interpreter",
				null, 0, 1, QueryEvaluationResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(listResultEClass, ListResult.class, "ListResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getListResult_Values(), this.getQueryEvaluationResult(), null, "values", null, 0, -1,
				ListResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(setResultEClass, SetResult.class, "SetResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSetResult_Values(), this.getQueryEvaluationResult(), null, "values", null, 0, -1,
				SetResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queriesEClass, Queries.class, "Queries", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueries_Name(), ecorePackage.getEString(), "name", null, 1, 1, Queries.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getQueries_Queries(), this.getQuery(), null, "queries", null, 0, -1, Queries.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueries_ModelElements(), this.getModelElement(), null, "modelElements", null, 0,
				-1, Queries.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelElementEClass, ModelElement.class, "ModelElement", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelElement_Target(), ecorePackage.getEObject(), null, "target", null, 0, 1,
				ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelElement_Name(), ecorePackage.getEString(), "name", null, 1, 1,
				ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expectationEClass, Expectation.class, "Expectation", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(queryEvaluationResultExpectationEClass, QueryEvaluationResultExpectation.class,
				"QueryEvaluationResultExpectation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQueryEvaluationResultExpectation_ExpectedResult(), this.getQueryEvaluationResult(),
				null, "expectedResult", null, 1, 1, QueryEvaluationResultExpectation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(errorResultEClass, ErrorResult.class, "ErrorResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorResult_Message(), ecorePackage.getEString(), "message", null, 0, 1,
				ErrorResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serializableResultEClass, SerializableResult.class, "SerializableResult", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSerializableResult_Value(), this.getAnySerializable(), "value", null, 1, 1,
				SerializableResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumeratorResultEClass, EnumeratorResult.class, "EnumeratorResult", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumeratorResult_Value(), theEcorePackage.getEString(), "value", null, 1, 1,
				EnumeratorResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanResultEClass, BooleanResult.class, "BooleanResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanResult_Value(), ecorePackage.getEBoolean(), "value", null, 1, 1,
				BooleanResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringResultEClass, StringResult.class, "StringResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringResult_Value(), ecorePackage.getEString(), "value", null, 1, 1,
				StringResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(emptyResultEClass, EmptyResult.class, "EmptyResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerResultEClass, IntegerResult.class, "IntegerResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerResult_Value(), ecorePackage.getEInt(), "value", null, 1, 1,
				IntegerResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queryValidationResultExpectationEClass, QueryValidationResultExpectation.class,
				"QueryValidationResultExpectation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQueryValidationResultExpectation_ExpectedResult(), this.getQueryValidationResult(),
				null, "expectedResult", null, 1, 1, QueryValidationResultExpectation.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(queryValidationResultEClass, QueryValidationResult.class, "QueryValidationResult",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQueryValidationResult_Interpreter(), ecorePackage.getEString(), "interpreter",
				null, 0, 1, QueryValidationResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQueryValidationResult_PossibleTypes(), ecorePackage.getEString(), "possibleTypes",
				null, 0, -1, QueryValidationResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQueryValidationResult_ValidationMessages(), this.getValidationMessage(), null,
				"validationMessages", null, 0, -1, QueryValidationResult.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(validationMessageEClass, ValidationMessage.class, "ValidationMessage", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getValidationMessage_Severity(), this.getSeverity(), "severity", null, 1, 1,
				ValidationMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationMessage_Message(), ecorePackage.getEString(), "message", null, 1, 1,
				ValidationMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationMessage_StartPosition(), ecorePackage.getEInt(), "startPosition", null,
				1, 1, ValidationMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValidationMessage_EndPosition(), ecorePackage.getEInt(), "endPosition", null, 1, 1,
				ValidationMessage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(invalidResultEClass, InvalidResult.class, "InvalidResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(eObjectResultEClass, EObjectResult.class, "EObjectResult", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectResult_Value(), theEcorePackage.getEObject(), null, "value", null, 1, 1,
				EObjectResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(severityEEnum, Severity.class, "Severity");
		addEEnumLiteral(severityEEnum, Severity.INFO);
		addEEnumLiteral(severityEEnum, Severity.WARNING);
		addEEnumLiteral(severityEEnum, Severity.ERROR);

		// Initialize data types
		initEDataType(anySerializableEDataType, Serializable.class, "AnySerializable", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // QmodelPackageImpl
