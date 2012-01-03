/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Query Invocation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.QueryInvocation#getDefinition <em>Definition</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.QueryInvocation#getArgument <em>Argument</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQueryInvocation()
 * @model
 * @generated
 */
public interface QueryInvocation extends TemplateExpression {
	/**
	 * Returns the value of the '<em><b>Definition</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Definition</em>' reference.
	 * @see #setDefinition(Query)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQueryInvocation_Definition()
	 * @model required="true"
	 * @generated
	 */
	Query getDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.QueryInvocation#getDefinition
	 * <em>Definition</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Definition</em>' reference.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(Query value);

	/**
	 * Returns the value of the '<em><b>Argument</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.ocl.ecore.OCLExpression}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Argument</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Argument</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getQueryInvocation_Argument()
	 * @model containment="true"
	 * @generated
	 */
	EList<OCLExpression> getArgument();

} // QueryInvocation
