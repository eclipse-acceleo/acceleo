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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>File Path</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.FilePath#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getFilePath()
 * @model
 * @generated
 */
public interface FilePath extends ASTNode {
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
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#getFilePath_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Statement> getStatements();

} // FilePath
