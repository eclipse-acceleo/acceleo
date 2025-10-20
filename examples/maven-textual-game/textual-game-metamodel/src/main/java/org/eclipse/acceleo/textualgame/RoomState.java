/**
 */
package org.eclipse.acceleo.textualgame;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Room State</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.RoomState#getIsActive <em>Is Active</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getRoomState()
 * @model
 * @generated
 */
public interface RoomState extends NamedElement, IllustratedElement {

	/**
	 * Returns the value of the '<em><b>Is Active</b></em>' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Is Active</em>' containment reference.
	 * @see #setIsActive(Condition)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getRoomState_IsActive()
	 * @model containment="true"
	 * @generated
	 */
	Condition getIsActive();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.RoomState#getIsActive <em>Is
	 * Active</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Is Active</em>' containment reference.
	 * @see #getIsActive()
	 * @generated
	 */
	void setIsActive(Condition value);
} // RoomState
