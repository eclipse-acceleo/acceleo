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

import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Template</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl#getOverrides <em>Overrides</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl#getParameter <em>Parameter</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.TemplateImpl#getGuard <em>Guard</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TemplateImpl extends BlockImpl implements Template {
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
	 * The cached value of the '{@link #getOverrides() <em>Overrides</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<TemplateOverridesValue> overrides;

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
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression guard;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression post;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.TEMPLATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TEMPLATE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TEMPLATE__VISIBILITY,
					oldVisibility, visibility));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TemplateOverridesValue> getOverrides() {
		if (overrides == null) {
			overrides = new EObjectContainmentEList<TemplateOverridesValue>(TemplateOverridesValue.class,
					this, CstPackage.TEMPLATE__OVERRIDES);
		}
		return overrides;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Variable> getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<Variable>(Variable.class, this,
					CstPackage.TEMPLATE__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGuard(ModelExpression newGuard, NotificationChain msgs) {
		ModelExpression oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.TEMPLATE__GUARD, oldGuard, newGuard);
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
	public void setGuard(ModelExpression newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.TEMPLATE__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.TEMPLATE__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TEMPLATE__GUARD, newGuard,
					newGuard));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPost(ModelExpression newPost, NotificationChain msgs) {
		ModelExpression oldPost = post;
		post = newPost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.TEMPLATE__POST, oldPost, newPost);
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
	public void setPost(ModelExpression newPost) {
		if (newPost != post) {
			NotificationChain msgs = null;
			if (post != null)
				msgs = ((InternalEObject)post).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.TEMPLATE__POST, null, msgs);
			if (newPost != null)
				msgs = ((InternalEObject)newPost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.TEMPLATE__POST, null, msgs);
			msgs = basicSetPost(newPost, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.TEMPLATE__POST, newPost, newPost));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.TEMPLATE__OVERRIDES:
				return ((InternalEList<?>)getOverrides()).basicRemove(otherEnd, msgs);
			case CstPackage.TEMPLATE__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
			case CstPackage.TEMPLATE__GUARD:
				return basicSetGuard(null, msgs);
			case CstPackage.TEMPLATE__POST:
				return basicSetPost(null, msgs);
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
			case CstPackage.TEMPLATE__NAME:
				return getName();
			case CstPackage.TEMPLATE__VISIBILITY:
				return getVisibility();
			case CstPackage.TEMPLATE__OVERRIDES:
				return getOverrides();
			case CstPackage.TEMPLATE__PARAMETER:
				return getParameter();
			case CstPackage.TEMPLATE__GUARD:
				return getGuard();
			case CstPackage.TEMPLATE__POST:
				return getPost();
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
			case CstPackage.TEMPLATE__NAME:
				setName((String)newValue);
				return;
			case CstPackage.TEMPLATE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case CstPackage.TEMPLATE__OVERRIDES:
				getOverrides().clear();
				getOverrides().addAll((Collection<? extends TemplateOverridesValue>)newValue);
				return;
			case CstPackage.TEMPLATE__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends Variable>)newValue);
				return;
			case CstPackage.TEMPLATE__GUARD:
				setGuard((ModelExpression)newValue);
				return;
			case CstPackage.TEMPLATE__POST:
				setPost((ModelExpression)newValue);
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
			case CstPackage.TEMPLATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CstPackage.TEMPLATE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case CstPackage.TEMPLATE__OVERRIDES:
				getOverrides().clear();
				return;
			case CstPackage.TEMPLATE__PARAMETER:
				getParameter().clear();
				return;
			case CstPackage.TEMPLATE__GUARD:
				setGuard((ModelExpression)null);
				return;
			case CstPackage.TEMPLATE__POST:
				setPost((ModelExpression)null);
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
			case CstPackage.TEMPLATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CstPackage.TEMPLATE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case CstPackage.TEMPLATE__OVERRIDES:
				return overrides != null && !overrides.isEmpty();
			case CstPackage.TEMPLATE__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case CstPackage.TEMPLATE__GUARD:
				return guard != null;
			case CstPackage.TEMPLATE__POST:
				return post != null;
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
				case CstPackage.TEMPLATE__NAME:
					return CstPackage.MODULE_ELEMENT__NAME;
				case CstPackage.TEMPLATE__VISIBILITY:
					return CstPackage.MODULE_ELEMENT__VISIBILITY;
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
				case CstPackage.MODULE_ELEMENT__NAME:
					return CstPackage.TEMPLATE__NAME;
				case CstPackage.MODULE_ELEMENT__VISIBILITY:
					return CstPackage.TEMPLATE__VISIBILITY;
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", visibility: "); //$NON-NLS-1$
		result.append(visibility);
		result.append(')');
		return result.toString();
	}

} // TemplateImpl
