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
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module Element</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.ModuleElement#getModuleElement <em>Module Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getModuleElement()
 * @model
 * @generated
 */
public interface ModuleElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Module Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Element</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Element</em>' reference.
	 * @see #setModuleElement(EObject)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getModuleElement_ModuleElement()
	 * @model
	 * @generated
	 */
	EObject getModuleElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.ModuleElement#getModuleElement <em>Module Element</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Element</em>' reference.
	 * @see #getModuleElement()
	 * @generated
	 */
	void setModuleElement(EObject value);

} // ModuleElement
