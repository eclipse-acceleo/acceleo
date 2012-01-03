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
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Macro</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.MacroImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.spec.MacroImpl#getParameter <em>Parameter</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MacroImpl extends BlockImpl implements Macro {
	/**
	 * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final VisibilityKind VISIBILITY_EDEFAULT = VisibilityKind.PRIVATE;

	/**
	 * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected VisibilityKind visibility = VISIBILITY_EDEFAULT;

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
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> parameter;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClassifier type;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MacroImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.MACRO;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VisibilityKind getVisibility() {
		return visibility;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVisibility(VisibilityKind newVisibility) {
		VisibilityKind oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MACRO__VISIBILITY,
					oldVisibility, visibility));
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
					MtlPackage.MACRO__DOCUMENTATION, oldDocumentation, newDocumentation);
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MACRO__DOCUMENTATION,
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MACRO__DEPRECATED,
					oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Variable> getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<Variable>(Variable.class, this,
					MtlPackage.MACRO__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClassifier getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EClassifier)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MtlPackage.MACRO__TYPE,
							oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClassifier basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(EClassifier newType) {
		EClassifier oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MACRO__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.MACRO__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
							- MtlPackage.MACRO__DOCUMENTATION, null, msgs);
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
			case MtlPackage.MACRO__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case MtlPackage.MACRO__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
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
			case MtlPackage.MACRO__VISIBILITY:
				return getVisibility();
			case MtlPackage.MACRO__DOCUMENTATION:
				return getDocumentation();
			case MtlPackage.MACRO__DEPRECATED:
				return isDeprecated();
			case MtlPackage.MACRO__PARAMETER:
				return getParameter();
			case MtlPackage.MACRO__TYPE:
				if (resolve)
					return getType();
				return basicGetType();
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
			case MtlPackage.MACRO__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case MtlPackage.MACRO__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case MtlPackage.MACRO__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case MtlPackage.MACRO__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends Variable>)newValue);
				return;
			case MtlPackage.MACRO__TYPE:
				setType((EClassifier)newValue);
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
			case MtlPackage.MACRO__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case MtlPackage.MACRO__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case MtlPackage.MACRO__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case MtlPackage.MACRO__PARAMETER:
				getParameter().clear();
				return;
			case MtlPackage.MACRO__TYPE:
				setType((EClassifier)null);
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
			case MtlPackage.MACRO__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case MtlPackage.MACRO__DOCUMENTATION:
				return documentation != null;
			case MtlPackage.MACRO__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case MtlPackage.MACRO__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case MtlPackage.MACRO__TYPE:
				return type != null;
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
		if (baseClass == ModuleElement.class) {
			switch (derivedFeatureID) {
				case MtlPackage.MACRO__VISIBILITY:
					return MtlPackage.MODULE_ELEMENT__VISIBILITY;
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (derivedFeatureID) {
				case MtlPackage.MACRO__DOCUMENTATION:
					return MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case MtlPackage.MACRO__DEPRECATED:
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
		if (baseClass == ModuleElement.class) {
			switch (baseFeatureID) {
				case MtlPackage.MODULE_ELEMENT__VISIBILITY:
					return MtlPackage.MACRO__VISIBILITY;
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (baseFeatureID) {
				case MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return MtlPackage.MACRO__DOCUMENTATION;
				case MtlPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return MtlPackage.MACRO__DEPRECATED;
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
		result.append(" (visibility: "); //$NON-NLS-1$
		result.append(visibility);
		result.append(", deprecated: "); //$NON-NLS-1$
		result.append(deprecated);
		result.append(')');
		return result.toString();
	}

} // MacroImpl
