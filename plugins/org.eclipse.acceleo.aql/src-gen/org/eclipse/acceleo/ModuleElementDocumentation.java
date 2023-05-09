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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Element
 * Documentation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ModuleElementDocumentation#getParameterDocumentation <em>Parameter
 * Documentation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getModuleElementDocumentation()
 * @model
 * @generated
 */
public interface ModuleElementDocumentation extends Documentation {
	/**
	 * Returns the value of the '<em><b>Parameter Documentation</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.ParameterDocumentation}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Documentation</em>' containment reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter Documentation</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModuleElementDocumentation_ParameterDocumentation()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterDocumentation> getParameterDocumentation();

} // ModuleElementDocumentation
