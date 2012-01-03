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
package org.eclipse.acceleo.parser.cst;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Model Expression</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.ModelExpression#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ModelExpression#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ModelExpression#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ModelExpression#getAfter <em>After</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModelExpression()
 * @model
 * @generated
 * @since 3.0
 */
public interface ModelExpression extends TemplateExpression {
	/**
	 * Returns the value of the '<em><b>Body</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' attribute.
	 * @see #setBody(String)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModelExpression_Body()
	 * @model required="true"
	 * @generated
	 */
	String getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ModelExpression#getBody <em>Body</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' attribute.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(String value);

	/**
	 * Returns the value of the '<em><b>Before</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Before</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Before</em>' containment reference.
	 * @see #setBefore(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModelExpression_Before()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getBefore();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ModelExpression#getBefore <em>Before</em>}
	 * ' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Before</em>' containment reference.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Each</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Each</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Each</em>' containment reference.
	 * @see #setEach(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModelExpression_Each()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getEach();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ModelExpression#getEach <em>Each</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Each</em>' containment reference.
	 * @see #getEach()
	 * @generated
	 */
	void setEach(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>After</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>After</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>After</em>' containment reference.
	 * @see #setAfter(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getModelExpression_After()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getAfter();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ModelExpression#getAfter <em>After</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>After</em>' containment reference.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(ModelExpression value);

} // ModelExpression
