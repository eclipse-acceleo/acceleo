/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Game</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.Game#getInitialRoom <em>Initial Room</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.Game#getInitialItems <em>Initial Items</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.Game#getRooms <em>Rooms</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.Game#getItems <em>Items</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGame()
 * @model
 * @generated
 */
public interface Game extends EObject {
	/**
	 * Returns the value of the '<em><b>Initial Room</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Initial Room</em>' reference.
	 * @see #setInitialRoom(Room)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGame_InitialRoom()
	 * @model required="true"
	 * @generated
	 */
	Room getInitialRoom();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.Game#getInitialRoom <em>Initial
	 * Room</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Initial Room</em>' reference.
	 * @see #getInitialRoom()
	 * @generated
	 */
	void setInitialRoom(Room value);

	/**
	 * Returns the value of the '<em><b>Initial Items</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.textualgame.Item}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Initial Items</em>' reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGame_InitialItems()
	 * @model
	 * @generated
	 */
	EList<Item> getInitialItems();

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.textualgame.Item}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGame_Items()
	 * @model containment="true"
	 * @generated
	 */
	EList<Item> getItems();

	/**
	 * Returns the value of the '<em><b>Rooms</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.textualgame.Room}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Rooms</em>' containment reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getGame_Rooms()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Room> getRooms();

} // Game
