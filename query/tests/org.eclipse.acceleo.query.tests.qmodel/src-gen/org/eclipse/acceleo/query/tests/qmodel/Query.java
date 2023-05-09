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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Query</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getStartingPoint <em>Starting Point</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getCurrentResults <em>Current Results</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getExpectations <em>Expectations</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getClassesToImport <em>Classes To Import</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getVariables <em>Variables</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.Query#getPluginsInClassPath <em>Plugins In Class Path
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery()
 * @model
 * @generated
 */
public interface Query extends EObject {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' attribute.
	 * @see #setExpression(String)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_Expression()
	 * @model required="true"
	 * @generated
	 */
	String getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.Query#getExpression
	 * <em>Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Expression</em>' attribute.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(String value);

	/**
	 * Returns the value of the '<em><b>Starting Point</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Starting Point</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Starting Point</em>' reference.
	 * @see #setStartingPoint(ModelElement)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_StartingPoint()
	 * @model required="true"
	 * @generated
	 */
	ModelElement getStartingPoint();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.qmodel.Query#getStartingPoint
	 * <em>Starting Point</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Starting Point</em>' reference.
	 * @see #getStartingPoint()
	 * @generated
	 */
	void setStartingPoint(ModelElement value);

	/**
	 * Returns the value of the '<em><b>Current Results</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current Results</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Current Results</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_CurrentResults()
	 * @model containment="true" transient="true" derived="true"
	 * @generated
	 */
	EList<QueryEvaluationResult> getCurrentResults();

	/**
	 * Returns the value of the '<em><b>Expectations</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.acceleo.query.tests.qmodel.Expectation}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expectations</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expectations</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_Expectations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expectation> getExpectations();

	/**
	 * Returns the value of the '<em><b>Classes To Import</b></em>' attribute list. The list contents are of
	 * type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classes To Import</em>' attribute list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Classes To Import</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_ClassesToImport()
	 * @model
	 * @generated
	 */
	EList<String> getClassesToImport();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.query.tests.qmodel.Variable}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	/**
	 * Returns the value of the '<em><b>Plugins In Class Path</b></em>' attribute list. The list contents are
	 * of type {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugins In Class Path</em>' attribute list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Plugins In Class Path</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQuery_PluginsInClassPath()
	 * @model
	 * @generated
	 */
	EList<String> getPluginsInClassPath();

} // Query
