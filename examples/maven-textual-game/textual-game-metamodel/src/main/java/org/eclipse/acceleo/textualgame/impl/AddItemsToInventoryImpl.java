/**
 */
package org.eclipse.acceleo.textualgame.impl;

import java.util.Collection;

import org.eclipse.acceleo.textualgame.AddItemsToInventory;
import org.eclipse.acceleo.textualgame.Condition;
import org.eclipse.acceleo.textualgame.ConditionalElement;
import org.eclipse.acceleo.textualgame.Item;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Add Items To Inventory</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl#getDescription
 * <em>Description</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl#isIsOneTime <em>Is One
 * Time</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl#getIsVisible <em>Is
 * Visible</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl#getIsEnabled <em>Is
 * Enabled</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.AddItemsToInventoryImpl#getItems <em>Items</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AddItemsToInventoryImpl extends MinimalEObjectImpl.Container implements AddItemsToInventory {
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
	 * The cached value of the '{@link #getItems() <em>Items</em>}' reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	protected AddItemsToInventoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextualgamePackage.Literals.ADD_ITEMS_TO_INVENTORY;
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME, oldIsOneTime, isOneTime));
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
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE, oldIsVisible, newIsVisible);
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
						- TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE, null, msgs);
			}
			if (newIsVisible != null) {
				msgs = ((InternalEObject)newIsVisible).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE, null, msgs);
			}
			msgs = basicSetIsVisible(newIsVisible, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE, newIsVisible, newIsVisible));
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
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED, oldIsEnabled, newIsEnabled);
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
						- TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED, null, msgs);
			}
			if (newIsEnabled != null) {
				msgs = ((InternalEObject)newIsEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED, null, msgs);
			}
			msgs = basicSetIsEnabled(newIsEnabled, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED, newIsEnabled, newIsEnabled));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Item> getItems() {
		if (items == null) {
			items = new EObjectResolvingEList<>(Item.class, this,
					TextualgamePackage.ADD_ITEMS_TO_INVENTORY__ITEMS);
		}
		return items;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE:
				return basicSetIsVisible(null, msgs);
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED:
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
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__NAME:
				return getName();
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__DESCRIPTION:
				return getDescription();
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME:
				return isIsOneTime();
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE:
				return getIsVisible();
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED:
				return getIsEnabled();
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__ITEMS:
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
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__NAME:
				setName((String)newValue);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME:
				setIsOneTime((Boolean)newValue);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE:
				setIsVisible((Condition)newValue);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED:
				setIsEnabled((Condition)newValue);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__ITEMS:
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
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME:
				setIsOneTime(IS_ONE_TIME_EDEFAULT);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE:
				setIsVisible((Condition)null);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED:
				setIsEnabled((Condition)null);
				return;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__ITEMS:
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
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null
						: !DESCRIPTION_EDEFAULT.equals(description);
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME:
				return isOneTime != IS_ONE_TIME_EDEFAULT;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE:
				return isVisible != null;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED:
				return isEnabled != null;
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__ITEMS:
				return items != null && !items.isEmpty();
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
				case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME:
					return TextualgamePackage.CONDITIONAL_ELEMENT__IS_ONE_TIME;
				case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE:
					return TextualgamePackage.CONDITIONAL_ELEMENT__IS_VISIBLE;
				case TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED:
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
					return TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ONE_TIME;
				case TextualgamePackage.CONDITIONAL_ELEMENT__IS_VISIBLE:
					return TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_VISIBLE;
				case TextualgamePackage.CONDITIONAL_ELEMENT__IS_ENABLED:
					return TextualgamePackage.ADD_ITEMS_TO_INVENTORY__IS_ENABLED;
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

} // AddItemsToInventoryImpl
