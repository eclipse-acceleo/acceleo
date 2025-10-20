/**
 */
package org.eclipse.acceleo.textualgame;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>In Room State</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.InRoomState#getRoomState <em>Room State</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getInRoomState()
 * @model
 * @generated
 */
public interface InRoomState extends Condition {
	/**
	 * Returns the value of the '<em><b>Room State</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Room State</em>' reference.
	 * @see #setRoomState(RoomState)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getInRoomState_RoomState()
	 * @model required="true"
	 * @generated
	 */
	RoomState getRoomState();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.InRoomState#getRoomState <em>Room
	 * State</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Room State</em>' reference.
	 * @see #getRoomState()
	 * @generated
	 */
	void setRoomState(RoomState value);

} // InRoomState
