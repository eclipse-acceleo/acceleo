/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.impl;

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Variable</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.impl.VariableImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.debug.impl.VariableImpl#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VariableImpl extends EObjectImpl implements Variable {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

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
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Object value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isValueChanged() <em>Value Changed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isValueChanged()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VALUE_CHANGED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isValueChanged() <em>Value Changed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isValueChanged()
	 * @generated
	 * @ordered
	 */
	protected boolean valueChanged = VALUE_CHANGED_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeclarationType() <em>Declaration Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDeclarationType()
	 * @generated
	 * @ordered
	 */
	protected static final String DECLARATION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclarationType() <em>Declaration Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDeclarationType()
	 * @generated
	 * @ordered
	 */
	protected String declarationType = DECLARATION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isSupportModifications() <em>Support Modifications</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSupportModifications()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUPPORT_MODIFICATIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSupportModifications() <em>Support Modifications</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isSupportModifications()
	 * @generated
	 * @ordered
	 */
	protected boolean supportModifications = SUPPORT_MODIFICATIONS_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DebugPackage.Literals.VARIABLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.VARIABLE__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValue(Object newValue) {
		Object oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.VARIABLE__VALUE, oldValue,
					value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isValueChanged() {
		return valueChanged;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValueChanged(boolean newValueChanged) {
		boolean oldValueChanged = valueChanged;
		valueChanged = newValueChanged;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.VARIABLE__VALUE_CHANGED,
					oldValueChanged, valueChanged));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StackFrame getFrame() {
		if (eContainerFeatureID() != DebugPackage.VARIABLE__FRAME)
			return null;
		return (StackFrame)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFrame(StackFrame newFrame, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFrame, DebugPackage.VARIABLE__FRAME, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFrame(StackFrame newFrame) {
		if (newFrame != eInternalContainer() || (eContainerFeatureID() != DebugPackage.VARIABLE__FRAME
				&& newFrame != null)) {
			if (EcoreUtil.isAncestor(this, newFrame))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFrame != null)
				msgs = ((InternalEObject)newFrame).eInverseAdd(this, DebugPackage.STACK_FRAME__VARIABLES,
						StackFrame.class, msgs);
			msgs = basicSetFrame(newFrame, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.VARIABLE__FRAME, newFrame,
					newFrame));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getDeclarationType() {
		return declarationType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDeclarationType(String newDeclarationType) {
		String oldDeclarationType = declarationType;
		declarationType = newDeclarationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DebugPackage.VARIABLE__DECLARATION_TYPE,
					oldDeclarationType, declarationType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSupportModifications() {
		return supportModifications;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSupportModifications(boolean newSupportModifications) {
		boolean oldSupportModifications = supportModifications;
		supportModifications = newSupportModifications;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DebugPackage.VARIABLE__SUPPORT_MODIFICATIONS, oldSupportModifications,
					supportModifications));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DebugPackage.VARIABLE__FRAME:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFrame((StackFrame)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DebugPackage.VARIABLE__FRAME:
				return basicSetFrame(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case DebugPackage.VARIABLE__FRAME:
				return eInternalContainer().eInverseRemove(this, DebugPackage.STACK_FRAME__VARIABLES,
						StackFrame.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DebugPackage.VARIABLE__NAME:
				return getName();
			case DebugPackage.VARIABLE__VALUE:
				return getValue();
			case DebugPackage.VARIABLE__VALUE_CHANGED:
				return isValueChanged();
			case DebugPackage.VARIABLE__FRAME:
				return getFrame();
			case DebugPackage.VARIABLE__DECLARATION_TYPE:
				return getDeclarationType();
			case DebugPackage.VARIABLE__SUPPORT_MODIFICATIONS:
				return isSupportModifications();
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
			case DebugPackage.VARIABLE__NAME:
				setName((String)newValue);
				return;
			case DebugPackage.VARIABLE__VALUE:
				setValue(newValue);
				return;
			case DebugPackage.VARIABLE__VALUE_CHANGED:
				setValueChanged((Boolean)newValue);
				return;
			case DebugPackage.VARIABLE__FRAME:
				setFrame((StackFrame)newValue);
				return;
			case DebugPackage.VARIABLE__DECLARATION_TYPE:
				setDeclarationType((String)newValue);
				return;
			case DebugPackage.VARIABLE__SUPPORT_MODIFICATIONS:
				setSupportModifications((Boolean)newValue);
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
			case DebugPackage.VARIABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DebugPackage.VARIABLE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case DebugPackage.VARIABLE__VALUE_CHANGED:
				setValueChanged(VALUE_CHANGED_EDEFAULT);
				return;
			case DebugPackage.VARIABLE__FRAME:
				setFrame((StackFrame)null);
				return;
			case DebugPackage.VARIABLE__DECLARATION_TYPE:
				setDeclarationType(DECLARATION_TYPE_EDEFAULT);
				return;
			case DebugPackage.VARIABLE__SUPPORT_MODIFICATIONS:
				setSupportModifications(SUPPORT_MODIFICATIONS_EDEFAULT);
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
			case DebugPackage.VARIABLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DebugPackage.VARIABLE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case DebugPackage.VARIABLE__VALUE_CHANGED:
				return valueChanged != VALUE_CHANGED_EDEFAULT;
			case DebugPackage.VARIABLE__FRAME:
				return getFrame() != null;
			case DebugPackage.VARIABLE__DECLARATION_TYPE:
				return DECLARATION_TYPE_EDEFAULT == null ? declarationType != null
						: !DECLARATION_TYPE_EDEFAULT.equals(declarationType);
			case DebugPackage.VARIABLE__SUPPORT_MODIFICATIONS:
				return supportModifications != SUPPORT_MODIFICATIONS_EDEFAULT;
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", value: ");
		result.append(value);
		result.append(", valueChanged: ");
		result.append(valueChanged);
		result.append(", declarationType: ");
		result.append(declarationType);
		result.append(", supportModifications: ");
		result.append(supportModifications);
		result.append(')');
		return result.toString();
	}

} // VariableImpl
