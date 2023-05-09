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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Error Let Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.ErrorLetStatement#getMissingBindings <em>Missing Bindings</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorLetStatement#getMissingEndHeader <em>Missing End Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.ErrorLetStatement#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getErrorLetStatement()
 * @model
 * @generated
 */
public interface ErrorLetStatement extends org.eclipse.acceleo.Error, LetStatement {
	/**
	 * Returns the value of the '<em><b>Missing Bindings</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing Bindings</em>' attribute isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing Bindings</em>' attribute.
	 * @see #setMissingBindings(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorLetStatement_MissingBindings()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingBindings();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorLetStatement#getMissingBindings <em>Missing
	 * Bindings</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing Bindings</em>' attribute.
	 * @see #getMissingBindings()
	 * @generated
	 */
	void setMissingBindings(int value);

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
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorLetStatement_MissingEndHeader()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEndHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorLetStatement#getMissingEndHeader <em>Missing End
	 * Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End Header</em>' attribute.
	 * @see #getMissingEndHeader()
	 * @generated
	 */
	void setMissingEndHeader(int value);

	/**
	 * Returns the value of the '<em><b>Missing End</b></em>' attribute. The default value is
	 * <code>"-1"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missing End</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Missing End</em>' attribute.
	 * @see #setMissingEnd(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getErrorLetStatement_MissingEnd()
	 * @model default="-1" required="true"
	 * @generated
	 */
	int getMissingEnd();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.ErrorLetStatement#getMissingEnd <em>Missing
	 * End</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Missing End</em>' attribute.
	 * @see #getMissingEnd()
	 * @generated
	 */
	void setMissingEnd(int value);

} // ErrorLetStatement
