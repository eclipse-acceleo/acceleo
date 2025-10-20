/**
 */
package org.eclipse.acceleo.textualgame.impl;

import org.eclipse.acceleo.textualgame.Condition;
import org.eclipse.acceleo.textualgame.ConditionalElement;
import org.eclipse.acceleo.textualgame.GotoRoom;
import org.eclipse.acceleo.textualgame.Room;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Goto Room</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl#isIsOneTime <em>Is One Time</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl#getIsVisible <em>Is Visible</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl#getIsEnabled <em>Is Enabled</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.GotoRoomImpl#getRoom <em>Room</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GotoRoomImpl extends MinimalEObjectImpl.Container implements GotoRoom {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsOneTime() <em>Is One Time</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #isIsOneTime()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_ONE_TIME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsOneTime() <em>Is One Time</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #isIsOneTime()
	 * @generated
	 * @ordered
	 */
	protected boolean isOneTime = IS_ONE_TIME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIsVisible() <em>Is Visible</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getIsVisible()
	 * @generated
	 * @ordered
	 */
	protected Condition isVisible;

	/**
	 * The cached value of the '{@link #getIsEnabled() <em>Is Enabled</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getIsEnabled()
	 * @generated
	 * @ordered
	 */
	protected Condition isEnabled;

	/**
	 * The cached value of the '{@link #getRoom() <em>Room</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getRoom()
	 * @generated
	 * @ordered
	 */
	protected Room room;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected GotoRoomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextualgamePackage.Literals.GOTO_ROOM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GOTO_ROOM__NAME, oldName,
					name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GOTO_ROOM__DESCRIPTION,
					oldDescription, description));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIsOneTime() {
		return isOneTime;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsOneTime(boolean newIsOneTime) {
		boolean oldIsOneTime = isOneTime;
		isOneTime = newIsOneTime;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GOTO_ROOM__IS_ONE_TIME,
					oldIsOneTime, isOneTime));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Condition getIsVisible() {
		return isVisible;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetIsVisible(Condition newIsVisible, NotificationChain msgs) {
		Condition oldIsVisible = isVisible;
		isVisible = newIsVisible;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.GOTO_ROOM__IS_VISIBLE, oldIsVisible, newIsVisible);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsVisible(Condition newIsVisible) {
		if (newIsVisible != isVisible) {
			NotificationChain msgs = null;
			if (isVisible != null) {
				msgs = ((InternalEObject)isVisible).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.GOTO_ROOM__IS_VISIBLE, null, msgs);
			}
			if (newIsVisible != null) {
				msgs = ((InternalEObject)newIsVisible).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.GOTO_ROOM__IS_VISIBLE, null, msgs);
			}
			msgs = basicSetIsVisible(newIsVisible, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GOTO_ROOM__IS_VISIBLE,
					newIsVisible, newIsVisible));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Condition getIsEnabled() {
		return isEnabled;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetIsEnabled(Condition newIsEnabled, NotificationChain msgs) {
		Condition oldIsEnabled = isEnabled;
		isEnabled = newIsEnabled;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.GOTO_ROOM__IS_ENABLED, oldIsEnabled, newIsEnabled);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setIsEnabled(Condition newIsEnabled) {
		if (newIsEnabled != isEnabled) {
			NotificationChain msgs = null;
			if (isEnabled != null) {
				msgs = ((InternalEObject)isEnabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.GOTO_ROOM__IS_ENABLED, null, msgs);
			}
			if (newIsEnabled != null) {
				msgs = ((InternalEObject)newIsEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.GOTO_ROOM__IS_ENABLED, null, msgs);
			}
			msgs = basicSetIsEnabled(newIsEnabled, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GOTO_ROOM__IS_ENABLED,
					newIsEnabled, newIsEnabled));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Room getRoom() {
		if (room != null && room.eIsProxy()) {
			InternalEObject oldRoom = (InternalEObject)room;
			room = (Room)eResolveProxy(oldRoom);
			if (room != oldRoom) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TextualgamePackage.GOTO_ROOM__ROOM, oldRoom, room));
				}
			}
		}
		return room;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Room basicGetRoom() {
		return room;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRoom(Room newRoom) {
		Room oldRoom = room;
		room = newRoom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.GOTO_ROOM__ROOM, oldRoom,
					room));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TextualgamePackage.GOTO_ROOM__IS_VISIBLE:
				return basicSetIsVisible(null, msgs);
			case TextualgamePackage.GOTO_ROOM__IS_ENABLED:
				return basicSetIsEnabled(null, msgs);
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
			case TextualgamePackage.GOTO_ROOM__NAME:
				return getName();
			case TextualgamePackage.GOTO_ROOM__DESCRIPTION:
				return getDescription();
			case TextualgamePackage.GOTO_ROOM__IS_ONE_TIME:
				return isIsOneTime();
			case TextualgamePackage.GOTO_ROOM__IS_VISIBLE:
				return getIsVisible();
			case TextualgamePackage.GOTO_ROOM__IS_ENABLED:
				return getIsEnabled();
			case TextualgamePackage.GOTO_ROOM__ROOM:
				if (resolve) {
					return getRoom();
				}
				return basicGetRoom();
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
			case TextualgamePackage.GOTO_ROOM__NAME:
				setName((String)newValue);
				return;
			case TextualgamePackage.GOTO_ROOM__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TextualgamePackage.GOTO_ROOM__IS_ONE_TIME:
				setIsOneTime((Boolean)newValue);
				return;
			case TextualgamePackage.GOTO_ROOM__IS_VISIBLE:
				setIsVisible((Condition)newValue);
				return;
			case TextualgamePackage.GOTO_ROOM__IS_ENABLED:
				setIsEnabled((Condition)newValue);
				return;
			case TextualgamePackage.GOTO_ROOM__ROOM:
				setRoom((Room)newValue);
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
			case TextualgamePackage.GOTO_ROOM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TextualgamePackage.GOTO_ROOM__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TextualgamePackage.GOTO_ROOM__IS_ONE_TIME:
				setIsOneTime(IS_ONE_TIME_EDEFAULT);
				return;
			case TextualgamePackage.GOTO_ROOM__IS_VISIBLE:
				setIsVisible((Condition)null);
				return;
			case TextualgamePackage.GOTO_ROOM__IS_ENABLED:
				setIsEnabled((Condition)null);
				return;
			case TextualgamePackage.GOTO_ROOM__ROOM:
				setRoom((Room)null);
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
			case TextualgamePackage.GOTO_ROOM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TextualgamePackage.GOTO_ROOM__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null
						: !DESCRIPTION_EDEFAULT.equals(description);
			case TextualgamePackage.GOTO_ROOM__IS_ONE_TIME:
				return isOneTime != IS_ONE_TIME_EDEFAULT;
			case TextualgamePackage.GOTO_ROOM__IS_VISIBLE:
				return isVisible != null;
			case TextualgamePackage.GOTO_ROOM__IS_ENABLED:
				return isEnabled != null;
			case TextualgamePackage.GOTO_ROOM__ROOM:
				return room != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ConditionalElement.class) {
			switch (derivedFeatureID) {
				case TextualgamePackage.GOTO_ROOM__IS_ONE_TIME:
					return TextualgamePackage.CONDITIONAL_ELEMENT__IS_ONE_TIME;
				case TextualgamePackage.GOTO_ROOM__IS_VISIBLE:
					return TextualgamePackage.CONDITIONAL_ELEMENT__IS_VISIBLE;
				case TextualgamePackage.GOTO_ROOM__IS_ENABLED:
					return TextualgamePackage.CONDITIONAL_ELEMENT__IS_ENABLED;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ConditionalElement.class) {
			switch (baseFeatureID) {
				case TextualgamePackage.CONDITIONAL_ELEMENT__IS_ONE_TIME:
					return TextualgamePackage.GOTO_ROOM__IS_ONE_TIME;
				case TextualgamePackage.CONDITIONAL_ELEMENT__IS_VISIBLE:
					return TextualgamePackage.GOTO_ROOM__IS_VISIBLE;
				case TextualgamePackage.CONDITIONAL_ELEMENT__IS_ENABLED:
					return TextualgamePackage.GOTO_ROOM__IS_ENABLED;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", isOneTime: ");
		result.append(isOneTime);
		result.append(')');
		return result.toString();
	}

} // GotoRoomImpl
