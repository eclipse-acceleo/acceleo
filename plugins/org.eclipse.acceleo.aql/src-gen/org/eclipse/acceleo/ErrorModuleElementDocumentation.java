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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Module Element
 * Documentation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorModuleElementDocumentation#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModuleElementDocumentation()
 * @model
 * @generated
 */
public interface ErrorModuleElementDocumentation extends org.eclipse.acceleo.Error, ModuleElementDocumentation {
	/**
	 * Returns the value of the '<em><b>Missing End Header</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Header</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End Header</em>' attribute.
	 * @see #setMissingEndHeader(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModuleElementDocumentation_MissingEndHeader()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModuleElementDocumentation#getMissingEndHeader
	 * <em>Missing End Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #getMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(int value);

} // ErrorModuleElementDocumentation
