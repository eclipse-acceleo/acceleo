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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Query Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult#getInterpreter <em>Interpreter
 * </em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryEvaluationResult()
 * @model abstract="true"
 * @generated
 */
public interface QueryEvaluationResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Interpreter</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interpreter</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Interpreter</em>' attribute.
	 * @see #setInterpreter(String)
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getQueryEvaluationResult_Interpreter()
	 * @model
	 * @generated
	 */
	String getInterpreter();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult#getInterpreter
	 * <em>Interpreter</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Interpreter</em>' attribute.
	 * @see #getInterpreter()
	 * @generated
	 */
	void setInterpreter(String value);

} // QueryResult
