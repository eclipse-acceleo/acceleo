/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage
 * @generated
 */
public interface TextualgameFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	TextualgameFactory eINSTANCE = org.eclipse.acceleo.textualgame.impl.TextualgameFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Game</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Game</em>'.
	 * @generated
	 */
	Game createGame();

	/**
	 * Returns a new object of class '<em>Room</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Room</em>'.
	 * @generated
	 */
	Room createRoom();

	/**
	 * Returns a new object of class '<em>Room State</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Room State</em>'.
	 * @generated
	 */
	RoomState createRoomState();

	/**
	 * Returns a new object of class '<em>Item</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Item</em>'.
	 * @generated
	 */
	Item createItem();

	/**
	 * Returns a new object of class '<em>Goto Room</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Goto Room</em>'.
	 * @generated
	 */
	GotoRoom createGotoRoom();

	/**
	 * Returns a new object of class '<em>Add Items To Inventory</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return a new object of class '<em>Add Items To Inventory</em>'.
	 * @generated
	 */
	AddItemsToInventory createAddItemsToInventory();

	/**
	 * Returns a new object of class '<em>Restart</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Restart</em>'.
	 * @generated
	 */
	Restart createRestart();

	/**
	 * Returns a new object of class '<em>In Room</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>In Room</em>'.
	 * @generated
	 */
	InRoom createInRoom();

	/**
	 * Returns a new object of class '<em>Has Items</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>Has Items</em>'.
	 * @generated
	 */
	HasItems createHasItems();

	/**
	 * Returns a new object of class '<em>In Room State</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return a new object of class '<em>In Room State</em>'.
	 * @generated
	 */
	InRoomState createInRoomState();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the package supported by this factory.
	 * @generated
	 */
	TextualgamePackage getTextualgamePackage();

} // TextualgameFactory
