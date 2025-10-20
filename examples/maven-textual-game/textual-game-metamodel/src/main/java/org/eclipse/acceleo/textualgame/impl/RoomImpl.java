/**
 */
package org.eclipse.acceleo.textualgame.impl;

import java.util.Collection;

import org.eclipse.acceleo.textualgame.Action;
import org.eclipse.acceleo.textualgame.Room;
import org.eclipse.acceleo.textualgame.RoomState;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Room</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomImpl#getStates <em>States</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomImpl#getDefaultState <em>Default State</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.impl.RoomImpl#getActions <em>Actions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RoomImpl extends MinimalEObjectImpl.Container implements Room {
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
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<RoomState> states;

	/**
	 * The cached value of the '{@link #getDefaultState() <em>Default State</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getDefaultState()
	 * @generated
	 * @ordered
	 */
	protected RoomState defaultState;

	/**
	 * The cached value of the '{@link #getActions() <em>Actions</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> actions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected RoomImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextualgamePackage.Literals.ROOM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM__NAME, oldName,
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
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM__DESCRIPTION,
					oldDescription, description));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<RoomState> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<>(RoomState.class, this,
					TextualgamePackage.ROOM__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RoomState getDefaultState() {
		if (defaultState != null && defaultState.eIsProxy()) {
			InternalEObject oldDefaultState = (InternalEObject)defaultState;
			defaultState = (RoomState)eResolveProxy(oldDefaultState);
			if (defaultState != oldDefaultState) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TextualgamePackage.ROOM__DEFAULT_STATE, oldDefaultState, defaultState));
				}
			}
		}
		return defaultState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public RoomState basicGetDefaultState() {
		return defaultState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setDefaultState(RoomState newDefaultState) {
		RoomState oldDefaultState = defaultState;
		defaultState = newDefaultState;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextualgamePackage.ROOM__DEFAULT_STATE,
					oldDefaultState, defaultState));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Action> getActions() {
		if (actions == null) {
			actions = new EObjectContainmentEList<>(Action.class, this,
					TextualgamePackage.ROOM__ACTIONS);
		}
		return actions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TextualgamePackage.ROOM__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
			case TextualgamePackage.ROOM__ACTIONS:
				return ((InternalEList<?>)getActions()).basicRemove(otherEnd, msgs);
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
			case TextualgamePackage.ROOM__NAME:
				return getName();
			case TextualgamePackage.ROOM__DESCRIPTION:
				return getDescription();
			case TextualgamePackage.ROOM__STATES:
				return getStates();
			case TextualgamePackage.ROOM__DEFAULT_STATE:
				if (resolve) {
					return getDefaultState();
				}
				return basicGetDefaultState();
			case TextualgamePackage.ROOM__ACTIONS:
				return getActions();
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
			case TextualgamePackage.ROOM__NAME:
				setName((String)newValue);
				return;
			case TextualgamePackage.ROOM__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TextualgamePackage.ROOM__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends RoomState>)newValue);
				return;
			case TextualgamePackage.ROOM__DEFAULT_STATE:
				setDefaultState((RoomState)newValue);
				return;
			case TextualgamePackage.ROOM__ACTIONS:
				getActions().clear();
				getActions().addAll((Collection<? extends Action>)newValue);
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
			case TextualgamePackage.ROOM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TextualgamePackage.ROOM__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TextualgamePackage.ROOM__STATES:
				getStates().clear();
				return;
			case TextualgamePackage.ROOM__DEFAULT_STATE:
				setDefaultState((RoomState)null);
				return;
			case TextualgamePackage.ROOM__ACTIONS:
				getActions().clear();
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
			case TextualgamePackage.ROOM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TextualgamePackage.ROOM__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null
						: !DESCRIPTION_EDEFAULT.equals(description);
			case TextualgamePackage.ROOM__STATES:
				return states != null && !states.isEmpty();
			case TextualgamePackage.ROOM__DEFAULT_STATE:
				return defaultState != null;
			case TextualgamePackage.ROOM__ACTIONS:
				return actions != null && !actions.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(')');
		return result.toString();
	}

} // RoomImpl
