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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Documented Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.DocumentedElement#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.DocumentedElement#isDeprecated <em>Deprecated</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getDocumentedElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface DocumentedElement extends AcceleoASTNode {
	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' reference. It is bidirectional and its
	 * opposite is '{@link org.eclipse.acceleo.Documentation#getDocumentedElement <em>Documented
	 * Element</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Documentation</em>' reference.
	 * @see #setDocumentation(Documentation)
	 * @see org.eclipse.acceleo.AcceleoPackage#getDocumentedElement_Documentation()
	 * @see org.eclipse.acceleo.Documentation#getDocumentedElement
	 * @model opposite="documentedElement"
	 * @generated
	 */
	Documentation getDocumentation();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.DocumentedElement#getDocumentation
	 * <em>Documentation</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Documentation</em>' reference.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(Documentation value);

	/**
	 * Returns the value of the '<em><b>Deprecated</b></em>' attribute. The default value is
	 * <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deprecated</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Deprecated</em>' attribute.
	 * @see #setDeprecated(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getDocumentedElement_Deprecated()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isDeprecated();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.DocumentedElement#isDeprecated <em>Deprecated</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Deprecated</em>' attribute.
	 * @see #isDeprecated()
	 * @generated
	 */
	void setDeprecated(boolean value);

} // DocumentedElement
