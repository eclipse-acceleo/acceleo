/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Enum Literal</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.ErrorEnumLiteral#getSegments <em>Segments</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getErrorEnumLiteral()
 * @model
 * @generated
 */
public interface ErrorEnumLiteral extends org.eclipse.acceleo.query.ast.Error, EnumLiteral {
	/**
	 * Returns the value of the '<em><b>Segments</b></em>' attribute list. The list contents are of type
	 * {@link java.lang.String}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Segments</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Segments</em>' attribute list.
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getErrorEnumLiteral_Segments()
	 * @model
	 * @generated
	 */
	EList<String> getSegments();

} // ErrorEnumLiteral
