/**
 */
package org.eclipse.acceleo.textualgame.impl;

import java.util.Collection;

import org.eclipse.acceleo.textualgame.Game;
import org.eclipse.acceleo.textualgame.Item;
import org.eclipse.acceleo.textualgame.Room;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Game</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GameImpl#getInitialRoom <em>Initial Room</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GameImpl#getInitialItems <em>Initial Items</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GameImpl#getRooms <em>Rooms</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GameImpl#getItems <em>Items</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GameImpl extends MinimalEObjectImpl.Container implements Game {
	/**
	 * The cached value of the '{@link #getInitialRoom() <em>Initial Room</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInitialRoom()
	 * @generated
	 * @ordered
	 */
	protected Room initialRoom;

	/**
	 * The cached value of the '{@link #getInitialItems() <em>Initial Items</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInitialItems()
	 * @generated
	 * @ordered
	 */
	protected EList<Item> initialItems;

	/**
	 * The cached value of the '{@link #getRooms() <em>Rooms</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getRooms()
	 * @generated
	 * @ordered
	 */
	protected EList<Room> rooms;

	/**
	 * The cached value of the '{@link #getItems() <em>Items</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getItems()
	 * @generated
	 * @ordered
	 */
	protected EList<Item> items;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected GameImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextualgamePackage.Literals.GAME;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Room getInitialRoom() {
		if (initialRoom != null && initialRoom.eIsProxy()) {
			InternalEObject oldInitialRoom = (InternalEObject)initialRoom;
			initialRoom = (Room)eResolveProxy(oldInitialRoom);
			if (initialRoom != oldInitialRoom) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TextualgamePackage.GAME__INITIAL_ROOM, oldInitialRoom, initialRoom));
				}
			}
		}
		return initialRoom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Room basicGetInitialRoom() {
		return initialRoom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInitialRoom(Room newInitialRoom) {
		Room oldInitialRoom = initialRoom;
		initialRoom = newInitialRoom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GAME__INITIAL_ROOM,
					oldInitialRoom, initialRoom));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Item> getInitialItems() {
		if (initialItems == null) {
			initialItems = new EObjectResolvingEList<>(Item.class, this,
					TextualgamePackage.GAME__INITIAL_ITEMS);
		}
		return initialItems;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Item> getItems() {
		if (items == null) {
			items = new EObjectContainmentEList<>(Item.class, this, TextualgamePackage.GAME__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Room> getRooms() {
		if (rooms == null) {
			rooms = new EObjectContainmentEList<>(Room.class, this, TextualgamePackage.GAME__ROOMS);
		}
		return rooms;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TextualgamePackage.GAME__ROOMS:
				return ((InternalEList<?>)getRooms()).basicRemove(otherEnd, msgs);
			case TextualgamePackage.GAME__ITEMS:
				return ((InternalEList<?>)getItems()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TextualgamePackage.GAME__INITIAL_ROOM:
				if (resolve) {
					return getInitialRoom();
				}
				return basicGetInitialRoom();
			case TextualgamePackage.GAME__INITIAL_ITEMS:
				return getInitialItems();
			case TextualgamePackage.GAME__ROOMS:
				return getRooms();
			case TextualgamePackage.GAME__ITEMS:
				return getItems();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TextualgamePackage.GAME__INITIAL_ROOM:
				setInitialRoom((Room)newValue);
				return;
			case TextualgamePackage.GAME__INITIAL_ITEMS:
				getInitialItems().clear();
				getInitialItems().addAll((Collection<? extends Item>)newValue);
				return;
			case TextualgamePackage.GAME__ROOMS:
				getRooms().clear();
				getRooms().addAll((Collection<? extends Room>)newValue);
				return;
			case TextualgamePackage.GAME__ITEMS:
				getItems().clear();
				getItems().addAll((Collection<? extends Item>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TextualgamePackage.GAME__INITIAL_ROOM:
				setInitialRoom((Room)null);
				return;
			case TextualgamePackage.GAME__INITIAL_ITEMS:
				getInitialItems().clear();
				return;
			case TextualgamePackage.GAME__ROOMS:
				getRooms().clear();
				return;
			case TextualgamePackage.GAME__ITEMS:
				getItems().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TextualgamePackage.GAME__INITIAL_ROOM:
				return initialRoom != null;
			case TextualgamePackage.GAME__INITIAL_ITEMS:
				return initialItems != null && !initialItems.isEmpty();
			case TextualgamePackage.GAME__ROOMS:
				return rooms != null && !rooms.isEmpty();
			case TextualgamePackage.GAME__ITEMS:
				return items != null && !items.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // GameImpl
