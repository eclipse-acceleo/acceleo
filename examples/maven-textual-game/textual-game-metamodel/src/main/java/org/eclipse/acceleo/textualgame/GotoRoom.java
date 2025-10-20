/**
 */
package org.eclipse.acceleo.textualgame;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Goto Room</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.GotoRoom#getRoom <em>Room</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGotoRoom()
 * @model
 * @generated
 */
public interface GotoRoom extends Action {
	/**
	 * Returns the value of the '<em><b>Room</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the value of the '<em>Room</em>' reference.
	 * @see #setRoom(Room)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGotoRoom_Room()
	 * @model required="true"
	 * @generated
	 */
	Room getRoom();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.GotoRoom#getRoom <em>Room</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Room</em>' reference.
	 * @see #getRoom()
	 * @generated
	 */
	void setRoom(Room value);

} // GotoRoom
