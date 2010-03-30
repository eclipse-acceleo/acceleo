/**
 * <copyright>
 * </copyright>
 *
 * $Id: Activity.java,v 1.1 2010/03/30 12:57:32 jmusset Exp $
 */
package org.eclipse.acceleo.module.example.android;

import org.eclipse.acceleo.module.example.android.widgets.Widget;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.module.example.android.Activity#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.module.example.android.Activity#getWidgets <em>Widgets</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.module.example.android.AndroidPackage#getActivity()
 * @model
 * @generated
 */
public interface Activity extends EObject {
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
	 * @see org.eclipse.acceleo.module.example.android.AndroidPackage#getActivity_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.module.example.android.Activity#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Widgets</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.module.example.android.widgets.Widget}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Widgets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Widgets</em>' containment reference list.
	 * @see org.eclipse.acceleo.module.example.android.AndroidPackage#getActivity_Widgets()
	 * @model containment="true"
	 * @generated
	 */
	EList<Widget> getWidgets();

} // Activity
