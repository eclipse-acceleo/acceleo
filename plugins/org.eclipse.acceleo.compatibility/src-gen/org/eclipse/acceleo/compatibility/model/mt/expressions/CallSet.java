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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Call Set</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet#getCalls <em>Calls</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCallSet()
 * @model
 * @generated
 */
public interface CallSet extends Expression {
	/**
	 * Returns the value of the '<em><b>Calls</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.compatibility.model.mt.expressions.Call}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Calls</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Calls</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#getCallSet_Calls()
	 * @model containment="true"
	 * @generated
	 */
	EList<Call> getCalls();

} // CallSet
