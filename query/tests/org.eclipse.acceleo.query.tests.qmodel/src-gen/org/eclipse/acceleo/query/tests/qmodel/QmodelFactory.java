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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage
 * @generated
 */
public interface QmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	QmodelFactory eINSTANCE = org.eclipse.acceleo.query.tests.qmodel.impl.QmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Query</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Query</em>'.
	 * @generated
	 */
	Query createQuery();

	/**
	 * Returns a new object of class '<em>EObject Variable</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>EObject Variable</em>'.
	 * @generated
	 */
	EObjectVariable createEObjectVariable();

	/**
	 * Returns a new object of class '<em>List Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>List Result</em>'.
	 * @generated
	 */
	ListResult createListResult();

	/**
	 * Returns a new object of class '<em>Set Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Set Result</em>'.
	 * @generated
	 */
	SetResult createSetResult();

	/**
	 * Returns a new object of class '<em>Queries</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Queries</em>'.
	 * @generated
	 */
	Queries createQueries();

	/**
	 * Returns a new object of class '<em>Model Element</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Model Element</em>'.
	 * @generated
	 */
	ModelElement createModelElement();

	/**
	 * Returns a new object of class '<em>Query Evaluation Result Expectation</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Query Evaluation Result Expectation</em>'.
	 * @generated
	 */
	QueryEvaluationResultExpectation createQueryEvaluationResultExpectation();

	/**
	 * Returns a new object of class '<em>Error Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Result</em>'.
	 * @generated
	 */
	ErrorResult createErrorResult();

	/**
	 * Returns a new object of class '<em>Serializable Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Serializable Result</em>'.
	 * @generated
	 */
	SerializableResult createSerializableResult();

	/**
	 * Returns a new object of class '<em>Enumerator Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Enumerator Result</em>'.
	 * @generated
	 */
	EnumeratorResult createEnumeratorResult();

	/**
	 * Returns a new object of class '<em>Boolean Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Boolean Result</em>'.
	 * @generated
	 */
	BooleanResult createBooleanResult();

	/**
	 * Returns a new object of class '<em>String Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>String Result</em>'.
	 * @generated
	 */
	StringResult createStringResult();

	/**
	 * Returns a new object of class '<em>Empty Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Empty Result</em>'.
	 * @generated
	 */
	EmptyResult createEmptyResult();

	/**
	 * Returns a new object of class '<em>Integer Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Integer Result</em>'.
	 * @generated
	 */
	IntegerResult createIntegerResult();

	/**
	 * Returns a new object of class '<em>Query Validation Result Expectation</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Query Validation Result Expectation</em>'.
	 * @generated
	 */
	QueryValidationResultExpectation createQueryValidationResultExpectation();

	/**
	 * Returns a new object of class '<em>Query Validation Result</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Query Validation Result</em>'.
	 * @generated
	 */
	QueryValidationResult createQueryValidationResult();

	/**
	 * Returns a new object of class '<em>Validation Message</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Validation Message</em>'.
	 * @generated
	 */
	ValidationMessage createValidationMessage();

	/**
	 * Returns a new object of class '<em>Invalid Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Invalid Result</em>'.
	 * @generated
	 */
	InvalidResult createInvalidResult();

	/**
	 * Returns a new object of class '<em>EObject Result</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>EObject Result</em>'.
	 * @generated
	 */
	EObjectResult createEObjectResult();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	QmodelPackage getQmodelPackage();

} // QmodelFactory
