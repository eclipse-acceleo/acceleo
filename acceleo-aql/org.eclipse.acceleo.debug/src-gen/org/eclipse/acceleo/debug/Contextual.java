/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Contextual</b></em>'. <!--
 * end-user-doc --> <!-- begin-model-doc --> A contextual element. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.Contextual#getContext <em>Context</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getContextual()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Contextual extends EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Context</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> The {@link EObject} representing the context. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Context</em>' reference.
	 * @see #setContext(EObject)
	 * @see org.eclipse.acceleo.debug.DebugPackage#getContextual_Context()
	 * @model required="true"
	 * @generated
	 */
	EObject getContext();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.debug.Contextual#getContext <em>Context</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Context</em>' reference.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(EObject value);

} // Contextual
