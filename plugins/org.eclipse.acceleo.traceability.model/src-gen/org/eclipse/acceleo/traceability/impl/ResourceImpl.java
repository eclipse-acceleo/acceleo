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
package org.eclipse.acceleo.traceability.impl;

import org.eclipse.acceleo.traceability.Resource;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.acceleo.traceability.minimal.MinimalEObjectImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Resource</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.traceability.impl.ResourceImpl#getPath <em>Path</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.impl.ResourceImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.impl.ResourceImpl#getCharset <em>Charset</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ResourceImpl extends MinimalEObjectImpl.Container implements Resource {
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getCharset() <em>Charset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharset()
	 * @generated
	 * @ordered
	 */
	protected static final String CHARSET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCharset() <em>Charset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharset()
	 * @generated
	 * @ordered
	 */
	protected String charset = CHARSET_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.RESOURCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.RESOURCE__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.RESOURCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharset(String newCharset) {
		String oldCharset = charset;
		charset = newCharset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.RESOURCE__CHARSET, oldCharset, charset));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.RESOURCE__PATH:
				return getPath();
			case TraceabilityPackage.RESOURCE__NAME:
				return getName();
			case TraceabilityPackage.RESOURCE__CHARSET:
				return getCharset();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.RESOURCE__PATH:
				setPath((String)newValue);
				return;
			case TraceabilityPackage.RESOURCE__NAME:
				setName((String)newValue);
				return;
			case TraceabilityPackage.RESOURCE__CHARSET:
				setCharset((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.RESOURCE__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case TraceabilityPackage.RESOURCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TraceabilityPackage.RESOURCE__CHARSET:
				setCharset(CHARSET_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.RESOURCE__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case TraceabilityPackage.RESOURCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TraceabilityPackage.RESOURCE__CHARSET:
				return CHARSET_EDEFAULT == null ? charset != null : !CHARSET_EDEFAULT.equals(charset);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (path: "); //$NON-NLS-1$
		result.append(path);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", charset: "); //$NON-NLS-1$
		result.append(charset);
		result.append(')');
		return result.toString();
	}

} // ResourceImpl
