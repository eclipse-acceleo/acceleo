/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sequence In Extension</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.query.ast.AstPackage#getSequenceInExtensionLiteral()
 * @model
 * @generated
 */
public interface SequenceInExtensionLiteral extends Literal {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.query.ast.Expression}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.ast.AstPackage#getSequenceInExtensionLiteral_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getValues();

} // SequenceInExtension
