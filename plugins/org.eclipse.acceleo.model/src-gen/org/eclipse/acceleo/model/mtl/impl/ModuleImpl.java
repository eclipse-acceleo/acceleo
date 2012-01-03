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
package org.eclipse.acceleo.model.mtl.impl;

import java.util.Collection;

import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getInput <em> Input</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getExtends <em> Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getImports <em> Imports</em>}</li>
 * <li>
 * {@link org.eclipse.acceleo.model.mtl.impl.spec.ModuleImpl#getOwnedModuleElement <em>Owned Module
 * Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleImpl extends EPackageImpl implements Module {
	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected Documentation documentation;

	/**
	 * The default value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPRECATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected boolean deprecated = DEPRECATED_EDEFAULT;

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
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected EList<Module> extends_;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<Module> imports;

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
	 * The default value of the '{@link #getStartHeaderPosition() <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_HEADER_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartHeaderPosition() <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected int startHeaderPosition = START_HEADER_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndHeaderPosition() <em>End Header Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_HEADER_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndHeaderPosition() <em>End Header Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected int endHeaderPosition = END_HEADER_POSITION_EDEFAULT;

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
		return MtlPackage.Literals.MODULE;
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
					MtlPackage.MODULE__DOCUMENTATION, oldDocumentation, newDocumentation);
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
				msgs = ((InternalEObject)documentation).eInverseRemove(this,
						MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this,
						MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE__DOCUMENTATION,
					newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isDeprecated() {
		return deprecated;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDeprecated(boolean newDeprecated) {
		boolean oldDeprecated = deprecated;
		deprecated = newDeprecated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE__DEPRECATED,
					oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TypedModel> getInput() {
		if (input == null) {
			input = new EObjectContainmentEList<TypedModel>(TypedModel.class, this, MtlPackage.MODULE__INPUT);
		}
		return input;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Module> getExtends() {
		if (extends_ == null) {
			extends_ = new EObjectResolvingEList<Module>(Module.class, this, MtlPackage.MODULE__EXTENDS);
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Module> getImports() {
		if (imports == null) {
			imports = new EObjectResolvingEList<Module>(Module.class, this, MtlPackage.MODULE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ModuleElement> getOwnedModuleElement() {
		if (ownedModuleElement == null) {
			ownedModuleElement = new EObjectContainmentEList<ModuleElement>(ModuleElement.class, this,
					MtlPackage.MODULE__OWNED_MODULE_ELEMENT);
		}
		return ownedModuleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getStartHeaderPosition() {
		return startHeaderPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStartHeaderPosition(int newStartHeaderPosition) {
		int oldStartHeaderPosition = startHeaderPosition;
		startHeaderPosition = newStartHeaderPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE__START_HEADER_POSITION,
					oldStartHeaderPosition, startHeaderPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getEndHeaderPosition() {
		return endHeaderPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEndHeaderPosition(int newEndHeaderPosition) {
		int oldEndHeaderPosition = endHeaderPosition;
		endHeaderPosition = newEndHeaderPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE__END_HEADER_POSITION,
					oldEndHeaderPosition, endHeaderPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.MODULE__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
							- MtlPackage.MODULE__DOCUMENTATION, null, msgs);
				return basicSetDocumentation((Documentation)otherEnd, msgs);
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
			case MtlPackage.MODULE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case MtlPackage.MODULE__INPUT:
				return ((InternalEList<?>)getInput()).basicRemove(otherEnd, msgs);
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				return ((InternalEList<?>)getOwnedModuleElement()).basicRemove(otherEnd, msgs);
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
			case MtlPackage.MODULE__DOCUMENTATION:
				return getDocumentation();
			case MtlPackage.MODULE__DEPRECATED:
				return isDeprecated();
			case MtlPackage.MODULE__INPUT:
				return getInput();
			case MtlPackage.MODULE__EXTENDS:
				return getExtends();
			case MtlPackage.MODULE__IMPORTS:
				return getImports();
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				return getOwnedModuleElement();
			case MtlPackage.MODULE__START_HEADER_POSITION:
				return getStartHeaderPosition();
			case MtlPackage.MODULE__END_HEADER_POSITION:
				return getEndHeaderPosition();
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
			case MtlPackage.MODULE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case MtlPackage.MODULE__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case MtlPackage.MODULE__INPUT:
				getInput().clear();
				getInput().addAll((Collection<? extends TypedModel>)newValue);
				return;
			case MtlPackage.MODULE__EXTENDS:
				getExtends().clear();
				getExtends().addAll((Collection<? extends Module>)newValue);
				return;
			case MtlPackage.MODULE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends Module>)newValue);
				return;
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				getOwnedModuleElement().clear();
				getOwnedModuleElement().addAll((Collection<? extends ModuleElement>)newValue);
				return;
			case MtlPackage.MODULE__START_HEADER_POSITION:
				setStartHeaderPosition((Integer)newValue);
				return;
			case MtlPackage.MODULE__END_HEADER_POSITION:
				setEndHeaderPosition((Integer)newValue);
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
			case MtlPackage.MODULE__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case MtlPackage.MODULE__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case MtlPackage.MODULE__INPUT:
				getInput().clear();
				return;
			case MtlPackage.MODULE__EXTENDS:
				getExtends().clear();
				return;
			case MtlPackage.MODULE__IMPORTS:
				getImports().clear();
				return;
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				getOwnedModuleElement().clear();
				return;
			case MtlPackage.MODULE__START_HEADER_POSITION:
				setStartHeaderPosition(START_HEADER_POSITION_EDEFAULT);
				return;
			case MtlPackage.MODULE__END_HEADER_POSITION:
				setEndHeaderPosition(END_HEADER_POSITION_EDEFAULT);
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
			case MtlPackage.MODULE__DOCUMENTATION:
				return documentation != null;
			case MtlPackage.MODULE__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case MtlPackage.MODULE__INPUT:
				return input != null && !input.isEmpty();
			case MtlPackage.MODULE__EXTENDS:
				return extends_ != null && !extends_.isEmpty();
			case MtlPackage.MODULE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
				return ownedModuleElement != null && !ownedModuleElement.isEmpty();
			case MtlPackage.MODULE__START_HEADER_POSITION:
				return startHeaderPosition != START_HEADER_POSITION_EDEFAULT;
			case MtlPackage.MODULE__END_HEADER_POSITION:
				return endHeaderPosition != END_HEADER_POSITION_EDEFAULT;
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
		if (baseClass == DocumentedElement.class) {
			switch (derivedFeatureID) {
				case MtlPackage.MODULE__DOCUMENTATION:
					return MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case MtlPackage.MODULE__DEPRECATED:
					return MtlPackage.DOCUMENTED_ELEMENT__DEPRECATED;
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
		if (baseClass == DocumentedElement.class) {
			switch (baseFeatureID) {
				case MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return MtlPackage.MODULE__DOCUMENTATION;
				case MtlPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return MtlPackage.MODULE__DEPRECATED;
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
		result.append(" (deprecated: "); //$NON-NLS-1$
		result.append(deprecated);
		result.append(", startHeaderPosition: "); //$NON-NLS-1$
		result.append(startHeaderPosition);
		result.append(", endHeaderPosition: "); //$NON-NLS-1$
		result.append(endHeaderPosition);
		result.append(')');
		return result.toString();
	}

} // ModuleImpl
