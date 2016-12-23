/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Error Module Element Documentation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ErrorModuleElementDocumentation#isMissingEndHeader <em>Missing End Header</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModuleElementDocumentation()
 * @model
 * @generated
 */
public interface ErrorModuleElementDocumentation extends org.eclipse.acceleo.Error, ModuleElementDocumentation {
	/**
	 * Returns the value of the '<em><b>Missing End Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End Header</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missing End Header</em>' attribute.
	 * @see #setMissingEndHeader(boolean)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorModuleElementDocumentation_MissingEndHeader()
	 * @model required="true"
	 * @generated
	 */
	boolean isMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorModuleElementDocumentation#isMissingEndHeader <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #isMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(boolean value);

} // ErrorModuleElementDocumentation
