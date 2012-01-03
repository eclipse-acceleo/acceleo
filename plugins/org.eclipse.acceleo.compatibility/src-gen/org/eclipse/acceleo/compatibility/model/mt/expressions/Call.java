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
package org.eclipse.acceleo.compatibility.model.mt.expressions;

import org.eclipse.acceleo.compatibility.model.mt.core.ASTNode;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Call</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getPrefix <em>Prefix</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getArguments <em>Arguments</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getFilter <em>Filter</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCall()
 * @model
 * @generated
 */
public interface Call extends ASTNode {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCall_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Prefix</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefix</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Prefix</em>' attribute.
	 * @see #setPrefix(String)
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCall_Prefix()
	 * @model
	 * @generated
	 */
	String getPrefix();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getPrefix
	 * <em>Prefix</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Prefix</em>' attribute.
	 * @see #getPrefix()
	 * @generated
	 */
	void setPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Expression}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCall_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getArguments();

	/**
	 * Returns the value of the '<em><b>Filter</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filter</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Filter</em>' containment reference.
	 * @see #setFilter(Expression)
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCall_Filter()
	 * @model containment="true"
	 * @generated
	 */
	Expression getFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call#getFilter
	 * <em>Filter</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Filter</em>' containment reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(Expression value);

} // Call
