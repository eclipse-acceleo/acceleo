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
package org.eclipse.acceleo.compatibility.model.mt.core;

import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Script</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Script#getDescriptor <em>Descriptor</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.Script#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScript()
 * @model
 * @generated
 */
public interface Script extends ASTNode {
	/**
	 * Returns the value of the '<em><b>Descriptor</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Descriptor</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Descriptor</em>' containment reference.
	 * @see #setDescriptor(ScriptDescriptor)
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScript_Descriptor()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ScriptDescriptor getDescriptor();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.compatibility.model.mt.core.Script#getDescriptor
	 * <em>Descriptor</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Descriptor</em>' containment reference.
	 * @see #getDescriptor()
	 * @generated
	 */
	void setDescriptor(ScriptDescriptor value);

	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Statements</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getScript_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();

} // Script
