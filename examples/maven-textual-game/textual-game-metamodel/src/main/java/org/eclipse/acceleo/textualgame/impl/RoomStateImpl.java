/**
 */
package org.eclipse.acceleo.textualgame.impl;

import org.eclipse.acceleo.textualgame.Condition;
import org.eclipse.acceleo.textualgame.IllustratedElement;
import org.eclipse.acceleo.textualgame.RoomState;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Room State</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomStateImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomStateImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomStateImpl#getImage <em>Image</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomStateImpl#getIsActive <em>Is Active</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RoomStateImpl extends MinimalEObjectImpl.Container implements RoomState {
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
	 * The default value of the '{@link #getImage() <em>Image</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImage() <em>Image</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected String image = IMAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIsActive() <em>Is Active</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getIsActive()
	 * @generated
	 * @ordered
	 */
	protected Condition isActive;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RoomStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextualgamePackage.Literals.ROOM_STATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM_STATE__NAME,
					oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM_STATE__DESCRIPTION,
					oldDescription, description));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getImage() {
		return image;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setImage(String newImage) {
		String oldImage = image;
		image = newImage;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM_STATE__IMAGE,
					oldImage, image));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Condition getIsActive() {
		return isActive;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetIsActive(Condition newIsActive, NotificationChain msgs) {
		Condition oldIsActive = isActive;
		isActive = newIsActive;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.ROOM_STATE__IS_ACTIVE, oldIsActive, newIsActive);
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
	public void setIsActive(Condition newIsActive) {
		if (newIsActive != isActive) {
			NotificationChain msgs = null;
			if (isActive != null) {
				msgs = ((InternalEObject)isActive).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.ROOM_STATE__IS_ACTIVE, null, msgs);
			}
			if (newIsActive != null) {
				msgs = ((InternalEObject)newIsActive).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.ROOM_STATE__IS_ACTIVE, null, msgs);
			}
			msgs = basicSetIsActive(newIsActive, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM_STATE__IS_ACTIVE,
					newIsActive, newIsActive));
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
			case TextualgamePackage.ROOM_STATE__IS_ACTIVE:
				return basicSetIsActive(null, msgs);
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
			case TextualgamePackage.ROOM_STATE__NAME:
				return getName();
			case TextualgamePackage.ROOM_STATE__DESCRIPTION:
				return getDescription();
			case TextualgamePackage.ROOM_STATE__IMAGE:
				return getImage();
			case TextualgamePackage.ROOM_STATE__IS_ACTIVE:
				return getIsActive();
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
			case TextualgamePackage.ROOM_STATE__NAME:
				setName((String)newValue);
				return;
			case TextualgamePackage.ROOM_STATE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TextualgamePackage.ROOM_STATE__IMAGE:
				setImage((String)newValue);
				return;
			case TextualgamePackage.ROOM_STATE__IS_ACTIVE:
				setIsActive((Condition)newValue);
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
			case TextualgamePackage.ROOM_STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TextualgamePackage.ROOM_STATE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TextualgamePackage.ROOM_STATE__IMAGE:
				setImage(IMAGE_EDEFAULT);
				return;
			case TextualgamePackage.ROOM_STATE__IS_ACTIVE:
				setIsActive((Condition)null);
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
			case TextualgamePackage.ROOM_STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TextualgamePackage.ROOM_STATE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null
						: !DESCRIPTION_EDEFAULT.equals(description);
			case TextualgamePackage.ROOM_STATE__IMAGE:
				return IMAGE_EDEFAULT == null ? image != null : !IMAGE_EDEFAULT.equals(image);
			case TextualgamePackage.ROOM_STATE__IS_ACTIVE:
				return isActive != null;
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
		if (baseClass == IllustratedElement.class) {
			switch (derivedFeatureID) {
				case TextualgamePackage.ROOM_STATE__IMAGE:
					return TextualgamePackage.ILLUSTRATED_ELEMENT__IMAGE;
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
		if (baseClass == IllustratedElement.class) {
			switch (baseFeatureID) {
				case TextualgamePackage.ILLUSTRATED_ELEMENT__IMAGE:
					return TextualgamePackage.ROOM_STATE__IMAGE;
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
		result.append(", image: ");
		result.append(image);
		result.append(')');
		return result.toString();
	}

} // RoomStateImpl
