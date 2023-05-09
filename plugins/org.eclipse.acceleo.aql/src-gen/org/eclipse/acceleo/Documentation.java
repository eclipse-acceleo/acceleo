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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Documentation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Documentation#getDocumentedElement <em>Documented Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getDocumentation()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Documentation extends Comment {
	/**
	 * Returns the value of the '<em><b>Documented Element</b></em>' reference. It is bidirectional and its
	 * opposite is '{@link org.eclipse.acceleo.DocumentedElement#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documented Element</em>' container reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Documented Element</em>' reference.
	 * @see #setDocumentedElement(DocumentedElement)
	 * @see org.eclipse.acceleo.AcceleoPackage#getDocumentation_DocumentedElement()
	 * @see org.eclipse.acceleo.DocumentedElement#getDocumentation
	 * @model opposite="documentation"
	 * @generated
	 */
	DocumentedElement getDocumentedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Documentation#getDocumentedElement <em>Documented
	 * Element</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Documented Element</em>' reference.
	 * @see #getDocumentedElement()
	 * @generated
	 */
	void setDocumentedElement(DocumentedElement value);

} // Documentation
