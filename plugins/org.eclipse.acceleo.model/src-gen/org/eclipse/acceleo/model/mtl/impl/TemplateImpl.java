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
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Template</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#isDeprecated <em>Deprecated</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#getOverrides <em>Overrides</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#getParameter <em>Parameter</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#isMain <em>Main</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateImpl#getPost <em>Post</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TemplateImpl extends BlockImpl implements Template {
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
	 * The cached value of the '{@link #getOverrides() <em>Overrides</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOverrides()
	 * @generated
	 * @ordered
	 */
	protected EList<Template> overrides;

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
	protected OCLExpression guard;

	/**
	 * The default value of the '{@link #isMain() <em>Main</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isMain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMain() <em>Main</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isMain()
	 * @generated
	 * @ordered
	 */
	protected boolean main = MAIN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression post;

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
		return MtlPackage.Literals.TEMPLATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE__VISIBILITY,
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
					MtlPackage.TEMPLATE__DOCUMENTATION, oldDocumentation, newDocumentation);
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE__DOCUMENTATION,
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
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE__DEPRECATED,
					oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Template> getOverrides() {
		if (overrides == null) {
			overrides = new EObjectResolvingEList<Template>(Template.class, this,
					MtlPackage.TEMPLATE__OVERRIDES);
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
					MtlPackage.TEMPLATE__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGuard(OCLExpression newGuard, NotificationChain msgs) {
		OCLExpression oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.TEMPLATE__GUARD, oldGuard, newGuard);
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
	public void setGuard(OCLExpression newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE__GUARD, newGuard,
					newGuard));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isMain() {
		return main;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMain(boolean newMain) {
		boolean oldMain = main;
		main = newMain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE__MAIN, oldMain, main));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPost(OCLExpression newPost, NotificationChain msgs) {
		OCLExpression oldPost = post;
		post = newPost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.TEMPLATE__POST, oldPost, newPost);
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
	public void setPost(OCLExpression newPost) {
		if (newPost != post) {
			NotificationChain msgs = null;
			if (post != null)
				msgs = ((InternalEObject)post).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE__POST, null, msgs);
			if (newPost != null)
				msgs = ((InternalEObject)newPost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE__POST, null, msgs);
			msgs = basicSetPost(newPost, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE__POST, newPost, newPost));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.TEMPLATE__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
							- MtlPackage.TEMPLATE__DOCUMENTATION, null, msgs);
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
			case MtlPackage.TEMPLATE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case MtlPackage.TEMPLATE__PARAMETER:
				return ((InternalEList<?>)getParameter()).basicRemove(otherEnd, msgs);
			case MtlPackage.TEMPLATE__GUARD:
				return basicSetGuard(null, msgs);
			case MtlPackage.TEMPLATE__POST:
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
			case MtlPackage.TEMPLATE__VISIBILITY:
				return getVisibility();
			case MtlPackage.TEMPLATE__DOCUMENTATION:
				return getDocumentation();
			case MtlPackage.TEMPLATE__DEPRECATED:
				return isDeprecated();
			case MtlPackage.TEMPLATE__OVERRIDES:
				return getOverrides();
			case MtlPackage.TEMPLATE__PARAMETER:
				return getParameter();
			case MtlPackage.TEMPLATE__GUARD:
				return getGuard();
			case MtlPackage.TEMPLATE__MAIN:
				return isMain() ? Boolean.TRUE : Boolean.FALSE;
			case MtlPackage.TEMPLATE__POST:
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
			case MtlPackage.TEMPLATE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case MtlPackage.TEMPLATE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case MtlPackage.TEMPLATE__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case MtlPackage.TEMPLATE__OVERRIDES:
				getOverrides().clear();
				getOverrides().addAll((Collection<? extends Template>)newValue);
				return;
			case MtlPackage.TEMPLATE__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection<? extends Variable>)newValue);
				return;
			case MtlPackage.TEMPLATE__GUARD:
				setGuard((OCLExpression)newValue);
				return;
			case MtlPackage.TEMPLATE__MAIN:
				setMain(((Boolean)newValue).booleanValue());
				return;
			case MtlPackage.TEMPLATE__POST:
				setPost((OCLExpression)newValue);
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
			case MtlPackage.TEMPLATE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case MtlPackage.TEMPLATE__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case MtlPackage.TEMPLATE__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case MtlPackage.TEMPLATE__OVERRIDES:
				getOverrides().clear();
				return;
			case MtlPackage.TEMPLATE__PARAMETER:
				getParameter().clear();
				return;
			case MtlPackage.TEMPLATE__GUARD:
				setGuard((OCLExpression)null);
				return;
			case MtlPackage.TEMPLATE__MAIN:
				setMain(MAIN_EDEFAULT);
				return;
			case MtlPackage.TEMPLATE__POST:
				setPost((OCLExpression)null);
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
			case MtlPackage.TEMPLATE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case MtlPackage.TEMPLATE__DOCUMENTATION:
				return documentation != null;
			case MtlPackage.TEMPLATE__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case MtlPackage.TEMPLATE__OVERRIDES:
				return overrides != null && !overrides.isEmpty();
			case MtlPackage.TEMPLATE__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case MtlPackage.TEMPLATE__GUARD:
				return guard != null;
			case MtlPackage.TEMPLATE__MAIN:
				return main != MAIN_EDEFAULT;
			case MtlPackage.TEMPLATE__POST:
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
				case MtlPackage.TEMPLATE__VISIBILITY:
					return MtlPackage.MODULE_ELEMENT__VISIBILITY;
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (derivedFeatureID) {
				case MtlPackage.TEMPLATE__DOCUMENTATION:
					return MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case MtlPackage.TEMPLATE__DEPRECATED:
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
					return MtlPackage.TEMPLATE__VISIBILITY;
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (baseFeatureID) {
				case MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return MtlPackage.TEMPLATE__DOCUMENTATION;
				case MtlPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return MtlPackage.TEMPLATE__DEPRECATED;
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
		result.append(", main: "); //$NON-NLS-1$
		result.append(main);
		result.append(')');
		return result.toString();
	}

} // TemplateImpl
