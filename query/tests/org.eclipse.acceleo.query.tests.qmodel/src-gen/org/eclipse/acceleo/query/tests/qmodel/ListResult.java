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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>EObject List Result</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.ListResult#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getListResult()
 * @model
 * @generated
 */
public interface ListResult extends QueryEvaluationResult {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.qmodel.QmodelPackage#getListResult_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<QueryEvaluationResult> getValues();

} // EObjectListResult
