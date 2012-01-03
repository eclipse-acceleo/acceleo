/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Element Documentation</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.ModuleElementDocumentation#getParametersDocumentation <em>
 * Parameters Documentation</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleElementDocumentation()
 * @model
 * @generated
 * @since 3.1
 */
public interface ModuleElementDocumentation extends Documentation {
	/**
	 * Returns the value of the '<em><b>Parameters Documentation</b></em>' containment reference list. The
	 * list contents are of type {@link org.eclipse.acceleo.model.mtl.ParameterDocumentation}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters Documentation</em>' containment reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters Documentation</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getModuleElementDocumentation_ParametersDocumentation()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterDocumentation> getParametersDocumentation();

} // ModuleElementDocumentation
