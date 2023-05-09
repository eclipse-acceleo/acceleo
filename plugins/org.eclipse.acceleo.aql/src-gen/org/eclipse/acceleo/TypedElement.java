/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Typed Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.TypedElement#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.TypedElement#getTypeAql <em>Type Aql</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getTypedElement()
 * @model
 * @generated
 */
public interface TypedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(AstResult)
	 * @see org.eclipse.acceleo.AcceleoPackage#getTypedElement_Type()
	 * @model dataType="org.eclipse.acceleo.ASTResult" required="true"
	 * @generated
	 */
	AstResult getType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.TypedElement#getType <em>Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(AstResult value);

	/**
	 * Returns the value of the '<em><b>Type Aql</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Type Aql</em>' containment reference.
	 * @see #setTypeAql(Expression)
	 * @see org.eclipse.acceleo.AcceleoPackage#getTypedElement_TypeAql()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getTypeAql();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.TypedElement#getTypeAql <em>Type Aql</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type Aql</em>' containment reference.
	 * @see #getTypeAql()
	 * @generated
	 */
	void setTypeAql(Expression value);

} // TypedElement
