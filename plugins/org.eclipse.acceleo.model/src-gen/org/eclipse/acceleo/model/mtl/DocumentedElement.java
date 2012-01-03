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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Documented Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.DocumentedElement#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.DocumentedElement#isDeprecated <em>Deprecated</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getDocumentedElement()
 * @model interface="true" abstract="true"
 * @generated
 * @since 3.1
 */
public interface DocumentedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' containment reference. It is bidirectional and
	 * its opposite is '{@link org.eclipse.acceleo.model.mtl.Documentation#getDocumentedElement
	 * <em>Documented Element</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Documentation</em>' containment reference.
	 * @see #setDocumentation(Documentation)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getDocumentedElement_Documentation()
	 * @see org.eclipse.acceleo.model.mtl.Documentation#getDocumentedElement
	 * @model opposite="documentedElement" containment="true"
	 * @generated
	 */
	Documentation getDocumentation();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.DocumentedElement#getDocumentation
	 * <em>Documentation</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Documentation</em>' containment reference.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(Documentation value);

	/**
	 * Returns the value of the '<em><b>Deprecated</b></em>' attribute. The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deprecated</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Deprecated</em>' attribute.
	 * @see #setDeprecated(boolean)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getDocumentedElement_Deprecated()
	 * @model default="" required="true"
	 * @generated
	 */
	boolean isDeprecated();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.DocumentedElement#isDeprecated
	 * <em>Deprecated</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Deprecated</em>' attribute.
	 * @see #isDeprecated()
	 * @generated
	 */
	void setDeprecated(boolean value);

} // DocumentedElement
