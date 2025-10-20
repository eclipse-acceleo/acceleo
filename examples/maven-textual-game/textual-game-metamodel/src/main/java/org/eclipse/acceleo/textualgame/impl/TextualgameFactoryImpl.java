/**
 */
package org.eclipse.acceleo.textualgame.impl;

import org.eclipse.acceleo.textualgame.AddItemsToInventory;
import org.eclipse.acceleo.textualgame.Game;
import org.eclipse.acceleo.textualgame.GotoRoom;
import org.eclipse.acceleo.textualgame.HasItems;
import org.eclipse.acceleo.textualgame.InRoom;
import org.eclipse.acceleo.textualgame.InRoomState;
import org.eclipse.acceleo.textualgame.Item;
import org.eclipse.acceleo.textualgame.Restart;
import org.eclipse.acceleo.textualgame.Room;
import org.eclipse.acceleo.textualgame.RoomState;
import org.eclipse.acceleo.textualgame.TextualgameFactory;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class TextualgameFactoryImpl extends EFactoryImpl implements TextualgameFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static TextualgameFactory init() {
		try {
			TextualgameFactory theTextualgameFactory = (TextualgameFactory)EPackage.Registry.INSTANCE
					.getEFactory(TextualgamePackage.eNS_URI);
			if (theTextualgameFactory != null) {
				return theTextualgameFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TextualgameFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TextualgameFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TextualgamePackage.GAME:
				return createGame();
			case TextualgamePackage.ROOM:
				return createRoom();
			case TextualgamePackage.ROOM_STATE:
				return createRoomState();
			case TextualgamePackage.ITEM:
				return createItem();
			case TextualgamePackage.GOTO_ROOM:
				return createGotoRoom();
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY:
				return createAddItemsToInventory();
			case TextualgamePackage.RESTART:
				return createRestart();
			case TextualgamePackage.IN_ROOM:
				return createInRoom();
			case TextualgamePackage.HAS_ITEMS:
				return createHasItems();
			case TextualgamePackage.IN_ROOM_STATE:
				return createInRoomState();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Game createGame() {
		GameImpl game = new GameImpl();
		return game;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Room createRoom() {
		RoomImpl room = new RoomImpl();
		return room;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RoomState createRoomState() {
		RoomStateImpl roomState = new RoomStateImpl();
		return roomState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Item createItem() {
		ItemImpl item = new ItemImpl();
		return item;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public GotoRoom createGotoRoom() {
		GotoRoomImpl gotoRoom = new GotoRoomImpl();
		return gotoRoom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AddItemsToInventory createAddItemsToInventory() {
		AddItemsToInventoryImpl addItemsToInventory = new AddItemsToInventoryImpl();
		return addItemsToInventory;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Restart createRestart() {
		RestartImpl restart = new RestartImpl();
		return restart;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public InRoom createInRoom() {
		InRoomImpl inRoom = new InRoomImpl();
		return inRoom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public HasItems createHasItems() {
		HasItemsImpl hasItems = new HasItemsImpl();
		return hasItems;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public InRoomState createInRoomState() {
		InRoomStateImpl inRoomState = new InRoomStateImpl();
		return inRoomState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TextualgamePackage getTextualgamePackage() {
		return (TextualgamePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TextualgamePackage getPackage() {
		return TextualgamePackage.eINSTANCE;
	}

} // TextualgameFactoryImpl
