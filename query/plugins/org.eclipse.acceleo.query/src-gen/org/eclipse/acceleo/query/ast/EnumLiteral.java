/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Enum Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.EnumLiteral#getLiteral <em>Literal</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getEnumLiteral()
 * @model
 * @generated
 */
public interface EnumLiteral extends Literal {
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Literal</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Literal</em>' reference.
	 * @see #setLiteral(EEnumLiteral)
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getEnumLiteral_Literal()
	 * @model
	 * @generated
	 */
	EEnumLiteral getLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.ast.EnumLiteral#getLiteral <em>Literal</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Literal</em>' reference.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(EEnumLiteral value);

} // EnumLiteral
