/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.cst.impl;

import java.util.Collection;

import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.Documentation;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModuleImpl#getInput <em>Input</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModuleImpl#getOwnedModuleElement <em>Owned Module Element
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModuleImpl#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.ModuleImpl#getImports <em>Imports</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleImpl extends EPackageImpl implements Module {
	/**
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndPosition() <em>End Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected int endPosition = END_POSITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInput() <em>Input</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getInput()
	 * @generated
	 * @ordered
	 */
	protected EList<TypedModel> input;

	/**
	 * The cached value of the '{@link #getOwnedModuleElement() <em>Owned Module Element</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOwnedModuleElement()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleElement> ownedModuleElement;

	/**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleExtendsValue> extends_;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleImportsValue> imports;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected Documentation documentation;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.MODULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODULE__START_POSITION,
					oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEndPosition(int newEndPosition) {
		int oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODULE__END_POSITION,
					oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TypedModel> getInput() {
		if (input == null) {
			input = new EObjectContainmentEList<TypedModel>(TypedModel.class, this, CstPackage.MODULE__INPUT);
		}
		return input;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ModuleElement> getOwnedModuleElement() {
		if (ownedModuleElement == null) {
			ownedModuleElement = new EObjectContainmentEList<ModuleElement>(ModuleElement.class, this,
					CstPackage.MODULE__OWNED_MODULE_ELEMENT);
		}
		return ownedModuleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ModuleExtendsValue> getExtends() {
		if (extends_ == null) {
			extends_ = new EObjectContainmentEList<ModuleExtendsValue>(ModuleExtendsValue.class, this,
					CstPackage.MODULE__EXTENDS);
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ModuleImportsValue> getImports() {
		if (imports == null) {
			imports = new EObjectContainmentEList<ModuleImportsValue>(ModuleImportsValue.class, this,
					CstPackage.MODULE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Documentation getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
		Documentation oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.MODULE__DOCUMENTATION, oldDocumentation, newDocumentation);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDocumentation(Documentation newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODULE__DOCUMENTATION, null, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.MODULE__DOCUMENTATION, null, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.MODULE__DOCUMENTATION,
					newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.MODULE__INPUT:
				return ((InternalEList<?>)getInput()).basicRemove(otherEnd, msgs);
			case CstPackage.MODULE__OWNED_MODULE_ELEMENT:
				return ((InternalEList<?>)getOwnedModuleElement()).basicRemove(otherEnd, msgs);
			case CstPackage.MODULE__EXTENDS:
				return ((InternalEList<?>)getExtends()).basicRemove(otherEnd, msgs);
			case CstPackage.MODULE__IMPORTS:
				return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
			case CstPackage.MODULE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
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
			case CstPackage.MODULE__START_POSITION:
				return new Integer(getStartPosition());
			case CstPackage.MODULE__END_POSITION:
				return new Integer(getEndPosition());
			case CstPackage.MODULE__INPUT:
				return getInput();
			case CstPackage.MODULE__OWNED_MODULE_ELEMENT:
				return getOwnedModuleElement();
			case CstPackage.MODULE__EXTENDS:
				return getExtends();
			case CstPackage.MODULE__IMPORTS:
				return getImports();
			case CstPackage.MODULE__DOCUMENTATION:
				return getDocumentation();
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
			case CstPackage.MODULE__START_POSITION:
				setStartPosition(((Integer)newValue).intValue());
				return;
			case CstPackage.MODULE__END_POSITION:
				setEndPosition(((Integer)newValue).intValue());
				return;
			case CstPackage.MODULE__INPUT:
				getInput().clear();
				getInput().addAll((Collection<? extends TypedModel>)newValue);
				return;
			case CstPackage.MODULE__OWNED_MODULE_ELEMENT:
				getOwnedModuleElement().clear();
				getOwnedModuleElement().addAll((Collection<? extends ModuleElement>)newValue);
				return;
			case CstPackage.MODULE__EXTENDS:
				getExtends().clear();
				getExtends().addAll((Collection<? extends ModuleExtendsValue>)newValue);
				return;
			case CstPackage.MODULE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends ModuleImportsValue>)newValue);
				return;
			case CstPackage.MODULE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
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
			case CstPackage.MODULE__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case CstPackage.MODULE__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case CstPackage.MODULE__INPUT:
				getInput().clear();
				return;
			case CstPackage.MODULE__OWNED_MODULE_ELEMENT:
				getOwnedModuleElement().clear();
				return;
			case CstPackage.MODULE__EXTENDS:
				getExtends().clear();
				return;
			case CstPackage.MODULE__IMPORTS:
				getImports().clear();
				return;
			case CstPackage.MODULE__DOCUMENTATION:
				setDocumentation((Documentation)null);
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
			case CstPackage.MODULE__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case CstPackage.MODULE__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case CstPackage.MODULE__INPUT:
				return input != null && !input.isEmpty();
			case CstPackage.MODULE__OWNED_MODULE_ELEMENT:
				return ownedModuleElement != null && !ownedModuleElement.isEmpty();
			case CstPackage.MODULE__EXTENDS:
				return extends_ != null && !extends_.isEmpty();
			case CstPackage.MODULE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case CstPackage.MODULE__DOCUMENTATION:
				return documentation != null;
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
		if (baseClass == CSTNode.class) {
			switch (derivedFeatureID) {
				case CstPackage.MODULE__START_POSITION:
					return CstPackage.CST_NODE__START_POSITION;
				case CstPackage.MODULE__END_POSITION:
					return CstPackage.CST_NODE__END_POSITION;
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
		if (baseClass == CSTNode.class) {
			switch (baseFeatureID) {
				case CstPackage.CST_NODE__START_POSITION:
					return CstPackage.MODULE__START_POSITION;
				case CstPackage.CST_NODE__END_POSITION:
					return CstPackage.MODULE__END_POSITION;
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startPosition: "); //$NON-NLS-1$
		result.append(startPosition);
		result.append(", endPosition: "); //$NON-NLS-1$
		result.append(endPosition);
		result.append(')');
		return result.toString();
	}

} // ModuleImpl
