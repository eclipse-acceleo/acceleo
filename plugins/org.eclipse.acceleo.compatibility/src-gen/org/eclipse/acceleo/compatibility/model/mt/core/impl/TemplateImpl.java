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
package org.eclipse.acceleo.compatibility.model.mt.core.impl;

import java.util.Collection;

import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.Script;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Template</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl#getImports <em>Imports</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl#getScripts <em>Scripts</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl#getBeginTag <em>Begin Tag
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl#getEndTag <em>End Tag</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TemplateImpl extends EObjectImpl implements Template {
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
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<Resource> imports;

	/**
	 * The cached value of the '{@link #getScripts() <em>Scripts</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getScripts()
	 * @generated
	 * @ordered
	 */
	protected EList<Script> scripts;

	/**
	 * The default value of the '{@link #getBeginTag() <em>Begin Tag</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getBeginTag()
	 * @generated
	 * @ordered
	 */
	protected static final String BEGIN_TAG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBeginTag() <em>Begin Tag</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getBeginTag()
	 * @generated
	 * @ordered
	 */
	protected String beginTag = BEGIN_TAG_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndTag() <em>End Tag</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getEndTag()
	 * @generated
	 * @ordered
	 */
	protected static final String END_TAG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEndTag() <em>End Tag</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getEndTag()
	 * @generated
	 * @ordered
	 */
	protected String endTag = END_TAG_EDEFAULT;

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
		return CorePackage.Literals.TEMPLATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.TEMPLATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Resource> getImports() {
		if (imports == null) {
			imports = new EObjectResolvingEList<Resource>(Resource.class, this, CorePackage.TEMPLATE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Script> getScripts() {
		if (scripts == null) {
			scripts = new EObjectContainmentEList<Script>(Script.class, this, CorePackage.TEMPLATE__SCRIPTS);
		}
		return scripts;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getBeginTag() {
		return beginTag;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBeginTag(String newBeginTag) {
		String oldBeginTag = beginTag;
		beginTag = newBeginTag;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.TEMPLATE__BEGIN_TAG,
					oldBeginTag, beginTag));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getEndTag() {
		return endTag;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEndTag(String newEndTag) {
		String oldEndTag = endTag;
		endTag = newEndTag;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.TEMPLATE__END_TAG, oldEndTag,
					endTag));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.TEMPLATE__SCRIPTS:
				return ((InternalEList<?>)getScripts()).basicRemove(otherEnd, msgs);
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
			case CorePackage.TEMPLATE__NAME:
				return getName();
			case CorePackage.TEMPLATE__IMPORTS:
				return getImports();
			case CorePackage.TEMPLATE__SCRIPTS:
				return getScripts();
			case CorePackage.TEMPLATE__BEGIN_TAG:
				return getBeginTag();
			case CorePackage.TEMPLATE__END_TAG:
				return getEndTag();
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
			case CorePackage.TEMPLATE__NAME:
				setName((String)newValue);
				return;
			case CorePackage.TEMPLATE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends Resource>)newValue);
				return;
			case CorePackage.TEMPLATE__SCRIPTS:
				getScripts().clear();
				getScripts().addAll((Collection<? extends Script>)newValue);
				return;
			case CorePackage.TEMPLATE__BEGIN_TAG:
				setBeginTag((String)newValue);
				return;
			case CorePackage.TEMPLATE__END_TAG:
				setEndTag((String)newValue);
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
			case CorePackage.TEMPLATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CorePackage.TEMPLATE__IMPORTS:
				getImports().clear();
				return;
			case CorePackage.TEMPLATE__SCRIPTS:
				getScripts().clear();
				return;
			case CorePackage.TEMPLATE__BEGIN_TAG:
				setBeginTag(BEGIN_TAG_EDEFAULT);
				return;
			case CorePackage.TEMPLATE__END_TAG:
				setEndTag(END_TAG_EDEFAULT);
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
			case CorePackage.TEMPLATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CorePackage.TEMPLATE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case CorePackage.TEMPLATE__SCRIPTS:
				return scripts != null && !scripts.isEmpty();
			case CorePackage.TEMPLATE__BEGIN_TAG:
				return BEGIN_TAG_EDEFAULT == null ? beginTag != null : !BEGIN_TAG_EDEFAULT.equals(beginTag);
			case CorePackage.TEMPLATE__END_TAG:
				return END_TAG_EDEFAULT == null ? endTag != null : !END_TAG_EDEFAULT.equals(endTag);
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", beginTag: "); //$NON-NLS-1$
		result.append(beginTag);
		result.append(", endTag: "); //$NON-NLS-1$
		result.append(endTag);
		result.append(')');
		return result.toString();
	}

} // TemplateImpl
