/**
 * <copyright>
 * </copyright>
 *
 * $Id: Widget.java,v 1.1 2010/03/30 12:57:35 jmusset Exp $
 */
package org.eclipse.acceleo.module.example.android.widgets;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Widget</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.module.example.android.widgets.Widget#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.module.example.android.widgets.WidgetsPackage#getWidget()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Widget extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.module.example.android.widgets.WidgetsPackage#getWidget_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.module.example.android.widgets.Widget#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Widget
