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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Documentation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.Documentation#getDocumentedElement <em>Documented Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getDocumentation()
 * @model
 * @generated
 * @since 3.1
 */
public interface Documentation extends Comment {
	/**
	 * Returns the value of the '<em><b>Documented Element</b></em>' container reference. It is bidirectional
	 * and its opposite is '{@link org.eclipse.acceleo.model.mtl.DocumentedElement#getDocumentation
	 * <em>Documentation</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documented Element</em>' reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Documented Element</em>' container reference.
	 * @see #setDocumentedElement(DocumentedElement)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getDocumentation_DocumentedElement()
	 * @see org.eclipse.acceleo.model.mtl.DocumentedElement#getDocumentation
	 * @model opposite="documentation" transient="false"
	 * @generated
	 */
	DocumentedElement getDocumentedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Documentation#getDocumentedElement
	 * <em>Documented Element</em>}' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Documented Element</em>' container reference.
	 * @see #getDocumentedElement()
	 * @generated
	 */
	void setDocumentedElement(DocumentedElement value);

} // Documentation
