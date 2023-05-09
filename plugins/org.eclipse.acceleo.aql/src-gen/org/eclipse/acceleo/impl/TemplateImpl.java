/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import java.util.Collection;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
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
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#isDeprecated <em>Deprecated</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getPost <em>Post</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#isMain <em>Main</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.TemplateImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TemplateImpl extends ModuleElementImpl implements Template {
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
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> parameters;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Expression guard;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected Expression post;

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
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

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
		return AcceleoPackage.Literals.TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Documentation getDocumentation() {
		if (documentation != null && documentation.eIsProxy()) {
			InternalEObject oldDocumentation = (InternalEObject)documentation;
			documentation = (Documentation)eResolveProxy(oldDocumentation);
			if (documentation != oldDocumentation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AcceleoPackage.TEMPLATE__DOCUMENTATION, oldDocumentation, documentation));
			}
		}
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Documentation basicGetDocumentation() {
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
					AcceleoPackage.TEMPLATE__DOCUMENTATION, oldDocumentation, newDocumentation);
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
	@Override
	public void setDocumentation(Documentation newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this,
						AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this,
						AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__DOCUMENTATION,
					newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDeprecated() {
		return deprecated;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDeprecated(boolean newDeprecated) {
		boolean oldDeprecated = deprecated;
		deprecated = newDeprecated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__DEPRECATED,
					oldDeprecated, deprecated));
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
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Variable> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Variable>(Variable.class, this,
					AcceleoPackage.TEMPLATE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGuard(Expression newGuard, NotificationChain msgs) {
		Expression oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.TEMPLATE__GUARD, oldGuard, newGuard);
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
	@Override
	public void setGuard(Expression newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TEMPLATE__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TEMPLATE__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__GUARD, newGuard,
					newGuard));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPost(Expression newPost, NotificationChain msgs) {
		Expression oldPost = post;
		post = newPost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.TEMPLATE__POST, oldPost, newPost);
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
	@Override
	public void setPost(Expression newPost) {
		if (newPost != post) {
			NotificationChain msgs = null;
			if (post != null)
				msgs = ((InternalEObject)post).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TEMPLATE__POST, null, msgs);
			if (newPost != null)
				msgs = ((InternalEObject)newPost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TEMPLATE__POST, null, msgs);
			msgs = basicSetPost(newPost, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__POST, newPost,
					newPost));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isMain() {
		return main;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMain(boolean newMain) {
		boolean oldMain = main;
		main = newMain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__MAIN, oldMain,
					main));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VisibilityKind getVisibility() {
		return visibility;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVisibility(VisibilityKind newVisibility) {
		VisibilityKind oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__VISIBILITY,
					oldVisibility, visibility));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Block getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBody(Block newBody, NotificationChain msgs) {
		Block oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.TEMPLATE__BODY, oldBody, newBody);
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
	@Override
	public void setBody(Block newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TEMPLATE__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.TEMPLATE__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.TEMPLATE__BODY, newBody,
					newBody));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.TEMPLATE__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this,
							AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
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
			case AcceleoPackage.TEMPLATE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case AcceleoPackage.TEMPLATE__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.TEMPLATE__GUARD:
				return basicSetGuard(null, msgs);
			case AcceleoPackage.TEMPLATE__POST:
				return basicSetPost(null, msgs);
			case AcceleoPackage.TEMPLATE__BODY:
				return basicSetBody(null, msgs);
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
			case AcceleoPackage.TEMPLATE__DOCUMENTATION:
				if (resolve)
					return getDocumentation();
				return basicGetDocumentation();
			case AcceleoPackage.TEMPLATE__DEPRECATED:
				return isDeprecated();
			case AcceleoPackage.TEMPLATE__NAME:
				return getName();
			case AcceleoPackage.TEMPLATE__PARAMETERS:
				return getParameters();
			case AcceleoPackage.TEMPLATE__GUARD:
				return getGuard();
			case AcceleoPackage.TEMPLATE__POST:
				return getPost();
			case AcceleoPackage.TEMPLATE__MAIN:
				return isMain();
			case AcceleoPackage.TEMPLATE__VISIBILITY:
				return getVisibility();
			case AcceleoPackage.TEMPLATE__BODY:
				return getBody();
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
			case AcceleoPackage.TEMPLATE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case AcceleoPackage.TEMPLATE__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case AcceleoPackage.TEMPLATE__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.TEMPLATE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Variable>)newValue);
				return;
			case AcceleoPackage.TEMPLATE__GUARD:
				setGuard((Expression)newValue);
				return;
			case AcceleoPackage.TEMPLATE__POST:
				setPost((Expression)newValue);
				return;
			case AcceleoPackage.TEMPLATE__MAIN:
				setMain((Boolean)newValue);
				return;
			case AcceleoPackage.TEMPLATE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case AcceleoPackage.TEMPLATE__BODY:
				setBody((Block)newValue);
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
			case AcceleoPackage.TEMPLATE__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case AcceleoPackage.TEMPLATE__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case AcceleoPackage.TEMPLATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.TEMPLATE__PARAMETERS:
				getParameters().clear();
				return;
			case AcceleoPackage.TEMPLATE__GUARD:
				setGuard((Expression)null);
				return;
			case AcceleoPackage.TEMPLATE__POST:
				setPost((Expression)null);
				return;
			case AcceleoPackage.TEMPLATE__MAIN:
				setMain(MAIN_EDEFAULT);
				return;
			case AcceleoPackage.TEMPLATE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case AcceleoPackage.TEMPLATE__BODY:
				setBody((Block)null);
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
			case AcceleoPackage.TEMPLATE__DOCUMENTATION:
				return documentation != null;
			case AcceleoPackage.TEMPLATE__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case AcceleoPackage.TEMPLATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.TEMPLATE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case AcceleoPackage.TEMPLATE__GUARD:
				return guard != null;
			case AcceleoPackage.TEMPLATE__POST:
				return post != null;
			case AcceleoPackage.TEMPLATE__MAIN:
				return main != MAIN_EDEFAULT;
			case AcceleoPackage.TEMPLATE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case AcceleoPackage.TEMPLATE__BODY:
				return body != null;
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
				case AcceleoPackage.TEMPLATE__DOCUMENTATION:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case AcceleoPackage.TEMPLATE__DEPRECATED:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.TEMPLATE__NAME:
					return AcceleoPackage.NAMED_ELEMENT__NAME;
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
				case AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return AcceleoPackage.TEMPLATE__DOCUMENTATION;
				case AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return AcceleoPackage.TEMPLATE__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.TEMPLATE__NAME;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (deprecated: "); //$NON-NLS-1$
		result.append(deprecated);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", main: "); //$NON-NLS-1$
		result.append(main);
		result.append(", visibility: "); //$NON-NLS-1$
		result.append(visibility);
		result.append(')');
		return result.toString();
	}

} // TemplateImpl
