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

import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.OpenModeKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>File Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.FileBlockImpl#getOpenMode <em>Open Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.FileBlockImpl#getFileUrl <em>File Url</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.FileBlockImpl#getUniqId <em>Uniq Id</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.FileBlockImpl#getCharset <em>Charset</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FileBlockImpl extends BlockImpl implements FileBlock {
	/**
	 * The default value of the '{@link #getOpenMode() <em>Open Mode</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOpenMode()
	 * @generated
	 * @ordered
	 */
	protected static final OpenModeKind OPEN_MODE_EDEFAULT = OpenModeKind.APPEND;

	/**
	 * The cached value of the '{@link #getOpenMode() <em>Open Mode</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOpenMode()
	 * @generated
	 * @ordered
	 */
	protected OpenModeKind openMode = OPEN_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFileUrl() <em>File Url</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFileUrl()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression fileUrl;

	/**
	 * The cached value of the '{@link #getUniqId() <em>Uniq Id</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUniqId()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression uniqId;

	/**
	 * The cached value of the '{@link #getCharset() <em>Charset</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCharset()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression charset;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FileBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.FILE_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OpenModeKind getOpenMode() {
		return openMode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOpenMode(OpenModeKind newOpenMode) {
		OpenModeKind oldOpenMode = openMode;
		openMode = newOpenMode == null ? OPEN_MODE_EDEFAULT : newOpenMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FILE_BLOCK__OPEN_MODE,
					oldOpenMode, openMode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getFileUrl() {
		return fileUrl;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFileUrl(ModelExpression newFileUrl, NotificationChain msgs) {
		ModelExpression oldFileUrl = fileUrl;
		fileUrl = newFileUrl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FILE_BLOCK__FILE_URL, oldFileUrl, newFileUrl);
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
	public void setFileUrl(ModelExpression newFileUrl) {
		if (newFileUrl != fileUrl) {
			NotificationChain msgs = null;
			if (fileUrl != null)
				msgs = ((InternalEObject)fileUrl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FILE_BLOCK__FILE_URL, null, msgs);
			if (newFileUrl != null)
				msgs = ((InternalEObject)newFileUrl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FILE_BLOCK__FILE_URL, null, msgs);
			msgs = basicSetFileUrl(newFileUrl, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FILE_BLOCK__FILE_URL,
					newFileUrl, newFileUrl));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getUniqId() {
		return uniqId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetUniqId(ModelExpression newUniqId, NotificationChain msgs) {
		ModelExpression oldUniqId = uniqId;
		uniqId = newUniqId;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FILE_BLOCK__UNIQ_ID, oldUniqId, newUniqId);
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
	public void setUniqId(ModelExpression newUniqId) {
		if (newUniqId != uniqId) {
			NotificationChain msgs = null;
			if (uniqId != null)
				msgs = ((InternalEObject)uniqId).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FILE_BLOCK__UNIQ_ID, null, msgs);
			if (newUniqId != null)
				msgs = ((InternalEObject)newUniqId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FILE_BLOCK__UNIQ_ID, null, msgs);
			msgs = basicSetUniqId(newUniqId, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FILE_BLOCK__UNIQ_ID, newUniqId,
					newUniqId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getCharset() {
		return charset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCharset(ModelExpression newCharset, NotificationChain msgs) {
		ModelExpression oldCharset = charset;
		charset = newCharset;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.FILE_BLOCK__CHARSET, oldCharset, newCharset);
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
	public void setCharset(ModelExpression newCharset) {
		if (newCharset != charset) {
			NotificationChain msgs = null;
			if (charset != null)
				msgs = ((InternalEObject)charset).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FILE_BLOCK__CHARSET, null, msgs);
			if (newCharset != null)
				msgs = ((InternalEObject)newCharset).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.FILE_BLOCK__CHARSET, null, msgs);
			msgs = basicSetCharset(newCharset, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.FILE_BLOCK__CHARSET, newCharset,
					newCharset));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.FILE_BLOCK__FILE_URL:
				return basicSetFileUrl(null, msgs);
			case CstPackage.FILE_BLOCK__UNIQ_ID:
				return basicSetUniqId(null, msgs);
			case CstPackage.FILE_BLOCK__CHARSET:
				return basicSetCharset(null, msgs);
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
			case CstPackage.FILE_BLOCK__OPEN_MODE:
				return getOpenMode();
			case CstPackage.FILE_BLOCK__FILE_URL:
				return getFileUrl();
			case CstPackage.FILE_BLOCK__UNIQ_ID:
				return getUniqId();
			case CstPackage.FILE_BLOCK__CHARSET:
				return getCharset();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CstPackage.FILE_BLOCK__OPEN_MODE:
				setOpenMode((OpenModeKind)newValue);
				return;
			case CstPackage.FILE_BLOCK__FILE_URL:
				setFileUrl((ModelExpression)newValue);
				return;
			case CstPackage.FILE_BLOCK__UNIQ_ID:
				setUniqId((ModelExpression)newValue);
				return;
			case CstPackage.FILE_BLOCK__CHARSET:
				setCharset((ModelExpression)newValue);
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
			case CstPackage.FILE_BLOCK__OPEN_MODE:
				setOpenMode(OPEN_MODE_EDEFAULT);
				return;
			case CstPackage.FILE_BLOCK__FILE_URL:
				setFileUrl((ModelExpression)null);
				return;
			case CstPackage.FILE_BLOCK__UNIQ_ID:
				setUniqId((ModelExpression)null);
				return;
			case CstPackage.FILE_BLOCK__CHARSET:
				setCharset((ModelExpression)null);
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
			case CstPackage.FILE_BLOCK__OPEN_MODE:
				return openMode != OPEN_MODE_EDEFAULT;
			case CstPackage.FILE_BLOCK__FILE_URL:
				return fileUrl != null;
			case CstPackage.FILE_BLOCK__UNIQ_ID:
				return uniqId != null;
			case CstPackage.FILE_BLOCK__CHARSET:
				return charset != null;
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
		result.append(" (openMode: "); //$NON-NLS-1$
		result.append(openMode);
		result.append(')');
		return result.toString();
	}

} // FileBlockImpl
