/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.acceleo.textualgame.TextualgameFactory
 * @model kind="package"
 * @generated
 */
public interface TextualgamePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "textualgame";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/textualgame";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "textualgame";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	TextualgamePackage eINSTANCE = org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.GameImpl <em>Game</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.GameImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getGame()
	 * @generated
	 */
	int GAME = 0;

	/**
	 * The feature id for the '<em><b>Initial Room</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GAME__INITIAL_ROOM = 0;

	/**
	 * The feature id for the '<em><b>Initial Items</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GAME__INITIAL_ITEMS = 1;

	/**
	 * The feature id for the '<em><b>Rooms</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GAME__ROOMS = 2;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GAME__ITEMS = 3;

	/**
	 * The number of structural features of the '<em>Game</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GAME_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Game</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GAME_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.NamedElement <em>Named
	 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.NamedElement
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.RoomImpl <em>Room</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.RoomImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getRoom()
	 * @generated
	 */
	int ROOM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM__STATES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default State</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM__DEFAULT_STATE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM__ACTIONS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Room</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Room</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.IllustratedElement <em>Illustrated
	 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.IllustratedElement
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getIllustratedElement()
	 * @generated
	 */
	int ILLUSTRATED_ELEMENT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.RoomStateImpl <em>Room
	 * State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.RoomStateImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getRoomState()
	 * @generated
	 */
	int ROOM_STATE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_STATE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_STATE__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Image</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_STATE__IMAGE = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Active</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_STATE__IS_ACTIVE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Room State</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_STATE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Room State</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int ROOM_STATE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.ConditionalElement <em>Conditional
	 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.ConditionalElement
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getConditionalElement()
	 * @generated
	 */
	int CONDITIONAL_ELEMENT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.Action <em>Action</em>}' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.Action
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Is One Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__IS_ONE_TIME = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Visible</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__IS_VISIBLE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Enabled</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION__IS_ENABLED = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Action</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Action</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ACTION_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is One Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ELEMENT__IS_ONE_TIME = 0;

	/**
	 * The feature id for the '<em><b>Is Visible</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ELEMENT__IS_VISIBLE = 1;

	/**
	 * The feature id for the '<em><b>Is Enabled</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ELEMENT__IS_ENABLED = 2;

	/**
	 * The number of structural features of the '<em>Conditional Element</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Conditional Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Image</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ILLUSTRATED_ELEMENT__IMAGE = 0;

	/**
	 * The number of structural features of the '<em>Illustrated Element</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ILLUSTRATED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Illustrated Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ILLUSTRATED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.ItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.ItemImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getItem()
	 * @generated
	 */
	int ITEM = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITEM__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITEM__DESCRIPTION = NAMED_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITEM__ACTIONS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Item</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITEM_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Item</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ITEM_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl <em>Goto
	 * Room</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.GotoRoomImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getGotoRoom()
	 * @generated
	 */
	int GOTO_ROOM = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Is One Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM__IS_ONE_TIME = ACTION__IS_ONE_TIME;

	/**
	 * The feature id for the '<em><b>Is Visible</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM__IS_VISIBLE = ACTION__IS_VISIBLE;

	/**
	 * The feature id for the '<em><b>Is Enabled</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM__IS_ENABLED = ACTION__IS_ENABLED;

	/**
	 * The feature id for the '<em><b>Room</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM__ROOM = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Goto Room</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Goto Room</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int GOTO_ROOM_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl <em>Add
	 * Items To Inventory</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getAddItemsToInventory()
	 * @generated
	 */
	int ADD_ITEMS_TO_INVENTORY = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Is One Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME = ACTION__IS_ONE_TIME;

	/**
	 * The feature id for the '<em><b>Is Visible</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY__IS_VISIBLE = ACTION__IS_VISIBLE;

	/**
	 * The feature id for the '<em><b>Is Enabled</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY__IS_ENABLED = ACTION__IS_ENABLED;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY__ITEMS = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Add Items To Inventory</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Add Items To Inventory</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADD_ITEMS_TO_INVENTORY_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.RestartImpl <em>Restart</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.RestartImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getRestart()
	 * @generated
	 */
	int RESTART = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART__NAME = ACTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART__DESCRIPTION = ACTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Is One Time</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART__IS_ONE_TIME = ACTION__IS_ONE_TIME;

	/**
	 * The feature id for the '<em><b>Is Visible</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART__IS_VISIBLE = ACTION__IS_VISIBLE;

	/**
	 * The feature id for the '<em><b>Is Enabled</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART__IS_ENABLED = ACTION__IS_ENABLED;

	/**
	 * The number of structural features of the '<em>Restart</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART_FEATURE_COUNT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Restart</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int RESTART_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.Condition <em>Condition</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.Condition
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 11;

	/**
	 * The number of structural features of the '<em>Condition</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Condition</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.InRoomImpl <em>In Room</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.InRoomImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getInRoom()
	 * @generated
	 */
	int IN_ROOM = 12;

	/**
	 * The feature id for the '<em><b>Room</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_ROOM__ROOM = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>In Room</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_ROOM_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>In Room</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_ROOM_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.HasItemsImpl <em>Has
	 * Items</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.HasItemsImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getHasItems()
	 * @generated
	 */
	int HAS_ITEMS = 13;

	/**
	 * The feature id for the '<em><b>Items</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HAS_ITEMS__ITEMS = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Has Items</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int HAS_ITEMS_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Has Items</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 * @ordered
	 */
	int HAS_ITEMS_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.textualgame.impl.InRoomStateImpl <em>In Room
	 * State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.acceleo.textualgame.impl.InRoomStateImpl
	 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getInRoomState()
	 * @generated
	 */
	int IN_ROOM_STATE = 14;

	/**
	 * The feature id for the '<em><b>Room State</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_ROOM_STATE__ROOM_STATE = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>In Room State</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_ROOM_STATE_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>In Room State</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int IN_ROOM_STATE_OPERATION_COUNT = CONDITION_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.Game <em>Game</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Game</em>'.
	 * @see org.eclipse.acceleo.textualgame.Game
	 * @generated
	 */
	EClass getGame();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.textualgame.Game#getInitialRoom
	 * <em>Initial Room</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Initial Room</em>'.
	 * @see org.eclipse.acceleo.textualgame.Game#getInitialRoom()
	 * @see #getGame()
	 * @generated
	 */
	EReference getGame_InitialRoom();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.acceleo.textualgame.Game#getInitialItems <em>Initial Items</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Initial Items</em>'.
	 * @see org.eclipse.acceleo.textualgame.Game#getInitialItems()
	 * @see #getGame()
	 * @generated
	 */
	EReference getGame_InitialItems();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.textualgame.Game#getItems <em>Items</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see org.eclipse.acceleo.textualgame.Game#getItems()
	 * @see #getGame()
	 * @generated
	 */
	EReference getGame_Items();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.textualgame.Game#getRooms <em>Rooms</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Rooms</em>'.
	 * @see org.eclipse.acceleo.textualgame.Game#getRooms()
	 * @see #getGame()
	 * @generated
	 */
	EReference getGame_Rooms();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.Room <em>Room</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Room</em>'.
	 * @see org.eclipse.acceleo.textualgame.Room
	 * @generated
	 */
	EClass getRoom();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.textualgame.Room#getStates <em>States</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see org.eclipse.acceleo.textualgame.Room#getStates()
	 * @see #getRoom()
	 * @generated
	 */
	EReference getRoom_States();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.textualgame.Room#getDefaultState
	 * <em>Default State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Default State</em>'.
	 * @see org.eclipse.acceleo.textualgame.Room#getDefaultState()
	 * @see #getRoom()
	 * @generated
	 */
	EReference getRoom_DefaultState();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.textualgame.Room#getActions <em>Actions</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.eclipse.acceleo.textualgame.Room#getActions()
	 * @see #getRoom()
	 * @generated
	 */
	EReference getRoom_Actions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.RoomState <em>Room
	 * State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Room State</em>'.
	 * @see org.eclipse.acceleo.textualgame.RoomState
	 * @generated
	 */
	EClass getRoomState();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.textualgame.RoomState#getIsActive <em>Is Active</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Is Active</em>'.
	 * @see org.eclipse.acceleo.textualgame.RoomState#getIsActive()
	 * @see #getRoomState()
	 * @generated
	 */
	EReference getRoomState_IsActive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.NamedElement <em>Named
	 * Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.acceleo.textualgame.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.textualgame.NamedElement#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.textualgame.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.textualgame.NamedElement#getDescription <em>Description</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.acceleo.textualgame.NamedElement#getDescription()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.Action <em>Action</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Action</em>'.
	 * @see org.eclipse.acceleo.textualgame.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.ConditionalElement
	 * <em>Conditional Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Conditional Element</em>'.
	 * @see org.eclipse.acceleo.textualgame.ConditionalElement
	 * @generated
	 */
	EClass getConditionalElement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.textualgame.ConditionalElement#isIsOneTime <em>Is One Time</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Is One Time</em>'.
	 * @see org.eclipse.acceleo.textualgame.ConditionalElement#isIsOneTime()
	 * @see #getConditionalElement()
	 * @generated
	 */
	EAttribute getConditionalElement_IsOneTime();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.textualgame.ConditionalElement#getIsVisible <em>Is Visible</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Is Visible</em>'.
	 * @see org.eclipse.acceleo.textualgame.ConditionalElement#getIsVisible()
	 * @see #getConditionalElement()
	 * @generated
	 */
	EReference getConditionalElement_IsVisible();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.acceleo.textualgame.ConditionalElement#getIsEnabled <em>Is Enabled</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference '<em>Is Enabled</em>'.
	 * @see org.eclipse.acceleo.textualgame.ConditionalElement#getIsEnabled()
	 * @see #getConditionalElement()
	 * @generated
	 */
	EReference getConditionalElement_IsEnabled();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.IllustratedElement
	 * <em>Illustrated Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Illustrated Element</em>'.
	 * @see org.eclipse.acceleo.textualgame.IllustratedElement
	 * @generated
	 */
	EClass getIllustratedElement();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.acceleo.textualgame.IllustratedElement#getImage <em>Image</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Image</em>'.
	 * @see org.eclipse.acceleo.textualgame.IllustratedElement#getImage()
	 * @see #getIllustratedElement()
	 * @generated
	 */
	EAttribute getIllustratedElement_Image();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.Item <em>Item</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Item</em>'.
	 * @see org.eclipse.acceleo.textualgame.Item
	 * @generated
	 */
	EClass getItem();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.acceleo.textualgame.Item#getActions <em>Actions</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.eclipse.acceleo.textualgame.Item#getActions()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_Actions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.GotoRoom <em>Goto
	 * Room</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Goto Room</em>'.
	 * @see org.eclipse.acceleo.textualgame.GotoRoom
	 * @generated
	 */
	EClass getGotoRoom();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.textualgame.GotoRoom#getRoom
	 * <em>Room</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Room</em>'.
	 * @see org.eclipse.acceleo.textualgame.GotoRoom#getRoom()
	 * @see #getGotoRoom()
	 * @generated
	 */
	EReference getGotoRoom_Room();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.AddItemsToInventory <em>Add
	 * Items To Inventory</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Add Items To Inventory</em>'.
	 * @see org.eclipse.acceleo.textualgame.AddItemsToInventory
	 * @generated
	 */
	EClass getAddItemsToInventory();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.acceleo.textualgame.AddItemsToInventory#getItems <em>Items</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Items</em>'.
	 * @see org.eclipse.acceleo.textualgame.AddItemsToInventory#getItems()
	 * @see #getAddItemsToInventory()
	 * @generated
	 */
	EReference getAddItemsToInventory_Items();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.Restart <em>Restart</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Restart</em>'.
	 * @see org.eclipse.acceleo.textualgame.Restart
	 * @generated
	 */
	EClass getRestart();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.Condition
	 * <em>Condition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see org.eclipse.acceleo.textualgame.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.InRoom <em>In Room</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>In Room</em>'.
	 * @see org.eclipse.acceleo.textualgame.InRoom
	 * @generated
	 */
	EClass getInRoom();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.acceleo.textualgame.InRoom#getRoom
	 * <em>Room</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Room</em>'.
	 * @see org.eclipse.acceleo.textualgame.InRoom#getRoom()
	 * @see #getInRoom()
	 * @generated
	 */
	EReference getInRoom_Room();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.HasItems <em>Has
	 * Items</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Has Items</em>'.
	 * @see org.eclipse.acceleo.textualgame.HasItems
	 * @generated
	 */
	EClass getHasItems();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.eclipse.acceleo.textualgame.HasItems#getItems <em>Items</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference list '<em>Items</em>'.
	 * @see org.eclipse.acceleo.textualgame.HasItems#getItems()
	 * @see #getHasItems()
	 * @generated
	 */
	EReference getHasItems_Items();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.textualgame.InRoomState <em>In Room
	 * State</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>In Room State</em>'.
	 * @see org.eclipse.acceleo.textualgame.InRoomState
	 * @generated
	 */
	EClass getInRoomState();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.acceleo.textualgame.InRoomState#getRoomState <em>Room State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the reference '<em>Room State</em>'.
	 * @see org.eclipse.acceleo.textualgame.InRoomState#getRoomState()
	 * @see #getInRoomState()
	 * @generated
	 */
	EReference getInRoomState_RoomState();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TextualgameFactory getTextualgameFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.GameImpl
		 * <em>Game</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.GameImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getGame()
		 * @generated
		 */
		EClass GAME = eINSTANCE.getGame();

		/**
		 * The meta object literal for the '<em><b>Initial Room</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GAME__INITIAL_ROOM = eINSTANCE.getGame_InitialRoom();

		/**
		 * The meta object literal for the '<em><b>Initial Items</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GAME__INITIAL_ITEMS = eINSTANCE.getGame_InitialItems();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GAME__ITEMS = eINSTANCE.getGame_Items();

		/**
		 * The meta object literal for the '<em><b>Rooms</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GAME__ROOMS = eINSTANCE.getGame_Rooms();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.RoomImpl
		 * <em>Room</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.RoomImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getRoom()
		 * @generated
		 */
		EClass ROOM = eINSTANCE.getRoom();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ROOM__STATES = eINSTANCE.getRoom_States();

		/**
		 * The meta object literal for the '<em><b>Default State</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ROOM__DEFAULT_STATE = eINSTANCE.getRoom_DefaultState();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ROOM__ACTIONS = eINSTANCE.getRoom_Actions();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.RoomStateImpl <em>Room
		 * State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.RoomStateImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getRoomState()
		 * @generated
		 */
		EClass ROOM_STATE = eINSTANCE.getRoomState();

		/**
		 * The meta object literal for the '<em><b>Is Active</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ROOM_STATE__IS_ACTIVE = eINSTANCE.getRoomState_IsActive();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.NamedElement <em>Named
		 * Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.NamedElement
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__DESCRIPTION = eINSTANCE.getNamedElement_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.Action <em>Action</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.Action
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.ConditionalElement
		 * <em>Conditional Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.ConditionalElement
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getConditionalElement()
		 * @generated
		 */
		EClass CONDITIONAL_ELEMENT = eINSTANCE.getConditionalElement();

		/**
		 * The meta object literal for the '<em><b>Is One Time</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute CONDITIONAL_ELEMENT__IS_ONE_TIME = eINSTANCE.getConditionalElement_IsOneTime();

		/**
		 * The meta object literal for the '<em><b>Is Visible</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONDITIONAL_ELEMENT__IS_VISIBLE = eINSTANCE.getConditionalElement_IsVisible();

		/**
		 * The meta object literal for the '<em><b>Is Enabled</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference CONDITIONAL_ELEMENT__IS_ENABLED = eINSTANCE.getConditionalElement_IsEnabled();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.IllustratedElement
		 * <em>Illustrated Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.IllustratedElement
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getIllustratedElement()
		 * @generated
		 */
		EClass ILLUSTRATED_ELEMENT = eINSTANCE.getIllustratedElement();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute ILLUSTRATED_ELEMENT__IMAGE = eINSTANCE.getIllustratedElement_Image();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.ItemImpl
		 * <em>Item</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.ItemImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getItem()
		 * @generated
		 */
		EClass ITEM = eINSTANCE.getItem();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ITEM__ACTIONS = eINSTANCE.getItem_Actions();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl <em>Goto
		 * Room</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.GotoRoomImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getGotoRoom()
		 * @generated
		 */
		EClass GOTO_ROOM = eINSTANCE.getGotoRoom();

		/**
		 * The meta object literal for the '<em><b>Room</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference GOTO_ROOM__ROOM = eINSTANCE.getGotoRoom_Room();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl <em>Add Items To
		 * Inventory</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getAddItemsToInventory()
		 * @generated
		 */
		EClass ADD_ITEMS_TO_INVENTORY = eINSTANCE.getAddItemsToInventory();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' reference list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference ADD_ITEMS_TO_INVENTORY__ITEMS = eINSTANCE.getAddItemsToInventory_Items();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.RestartImpl
		 * <em>Restart</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.RestartImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getRestart()
		 * @generated
		 */
		EClass RESTART = eINSTANCE.getRestart();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.Condition
		 * <em>Condition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.Condition
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.InRoomImpl <em>In
		 * Room</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.InRoomImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getInRoom()
		 * @generated
		 */
		EClass IN_ROOM = eINSTANCE.getInRoom();

		/**
		 * The meta object literal for the '<em><b>Room</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference IN_ROOM__ROOM = eINSTANCE.getInRoom_Room();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.HasItemsImpl <em>Has
		 * Items</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.HasItemsImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getHasItems()
		 * @generated
		 */
		EClass HAS_ITEMS = eINSTANCE.getHasItems();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' reference list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference HAS_ITEMS__ITEMS = eINSTANCE.getHasItems_Items();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.textualgame.impl.InRoomStateImpl <em>In
		 * Room State</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see org.eclipse.acceleo.textualgame.impl.InRoomStateImpl
		 * @see org.eclipse.acceleo.textualgame.impl.TextualgamePackageImpl#getInRoomState()
		 * @generated
		 */
		EClass IN_ROOM_STATE = eINSTANCE.getInRoomState();

		/**
		 * The meta object literal for the '<em><b>Room State</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EReference IN_ROOM_STATE__ROOM_STATE = eINSTANCE.getInRoomState_RoomState();

	}

} // TextualgamePackage
