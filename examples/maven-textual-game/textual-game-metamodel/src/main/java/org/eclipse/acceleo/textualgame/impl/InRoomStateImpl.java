/**
 */
package org.eclipse.acceleo.textualgame.impl;

import org.eclipse.acceleo.textualgame.InRoomState;
import org.eclipse.acceleo.textualgame.RoomState;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>In Room State</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.InRoomStateImpl#getRoomState <em>Room State</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InRoomStateImpl extends MinimalEObjectImpl.Container implements InRoomState {
	/**
	 * The cached value of the '{@link #getRoomState() <em>Room State</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #getRoomState()
	 * @generated
	 * @ordered
	 */
	protected RoomState roomState;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected InRoomStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextualgamePackage.Literals.IN_ROOM_STATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RoomState getRoomState() {
		if (roomState != null && roomState.eIsProxy()) {
			InternalEObject oldRoomState = (InternalEObject)roomState;
			roomState = (RoomState)eResolveProxy(oldRoomState);
			if (roomState != oldRoomState) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TextualgamePackage.IN_ROOM_STATE__ROOM_STATE, oldRoomState, roomState));
				}
			}
		}
		return roomState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RoomState basicGetRoomState() {
		return roomState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRoomState(RoomState newRoomState) {
		RoomState oldRoomState = roomState;
		roomState = newRoomState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.IN_ROOM_STATE__ROOM_STATE, oldRoomState, roomState));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TextualgamePackage.IN_ROOM_STATE__ROOM_STATE:
				if (resolve) {
					return getRoomState();
				}
				return basicGetRoomState();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TextualgamePackage.IN_ROOM_STATE__ROOM_STATE:
				setRoomState((RoomState)newValue);
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
			case TextualgamePackage.IN_ROOM_STATE__ROOM_STATE:
				setRoomState((RoomState)null);
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
			case TextualgamePackage.IN_ROOM_STATE__ROOM_STATE:
				return roomState != null;
		}
		return super.eIsSet(featureID);
	}

} // InRoomStateImpl
