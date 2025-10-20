/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Conditional Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.ConditionalElement#isIsOneTime <em>Is One Time</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.ConditionalElement#getIsVisible <em>Is Visible</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.ConditionalElement#getIsEnabled <em>Is Enabled</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getConditionalElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ConditionalElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Is One Time</b></em>' attribute. The default value is
	 * <code>"false"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is One Time</em>' attribute.
	 * @see #setIsOneTime(boolean)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getConditionalElement_IsOneTime()
	 * @model default="false"
	 * @generated
	 */
	boolean isIsOneTime();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.ConditionalElement#isIsOneTime <em>Is One
	 * Time</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is One Time</em>' attribute.
	 * @see #isIsOneTime()
	 * @generated
	 */
	void setIsOneTime(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Visible</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Visible</em>' containment reference.
	 * @see #setIsVisible(Condition)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getConditionalElement_IsVisible()
	 * @model containment="true"
	 * @generated
	 */
	Condition getIsVisible();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.ConditionalElement#getIsVisible <em>Is
	 * Visible</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Visible</em>' containment reference.
	 * @see #getIsVisible()
	 * @generated
	 */
	void setIsVisible(Condition value);

	/**
	 * Returns the value of the '<em><b>Is Enabled</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Enabled</em>' containment reference.
	 * @see #setIsEnabled(Condition)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getConditionalElement_IsEnabled()
	 * @model containment="true"
	 * @generated
	 */
	Condition getIsEnabled();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.ConditionalElement#getIsEnabled <em>Is
	 * Enabled</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Enabled</em>' containment reference.
	 * @see #getIsEnabled()
	 * @generated
	 */
	void setIsEnabled(Condition value);

} // ConditionalElement
