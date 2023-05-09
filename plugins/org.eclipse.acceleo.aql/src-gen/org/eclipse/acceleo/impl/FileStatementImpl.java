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

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>File Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.FileStatementImpl#getMode <em>Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.FileStatementImpl#getUrl <em>Url</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.FileStatementImpl#getCharset <em>Charset</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.FileStatementImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FileStatementImpl extends MinimalEObjectImpl.Container implements FileStatement {
	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final OpenModeKind MODE_EDEFAULT = OpenModeKind.OVERWRITE;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected OpenModeKind mode = MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected Expression url;

	/**
	 * The cached value of the '{@link #getCharset() <em>Charset</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCharset()
	 * @generated
	 * @ordered
	 */
	protected Expression charset;

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
	protected FileStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.FILE_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public OpenModeKind getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMode(OpenModeKind newMode) {
		OpenModeKind oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.FILE_STATEMENT__MODE,
					oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetUrl(Expression newUrl, NotificationChain msgs) {
		Expression oldUrl = url;
		url = newUrl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FILE_STATEMENT__URL, oldUrl, newUrl);
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
	public void setUrl(Expression newUrl) {
		if (newUrl != url) {
			NotificationChain msgs = null;
			if (url != null)
				msgs = ((InternalEObject)url).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.FILE_STATEMENT__URL, null, msgs);
			if (newUrl != null)
				msgs = ((InternalEObject)newUrl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.FILE_STATEMENT__URL, null, msgs);
			msgs = basicSetUrl(newUrl, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.FILE_STATEMENT__URL, newUrl,
					newUrl));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getCharset() {
		return charset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCharset(Expression newCharset, NotificationChain msgs) {
		Expression oldCharset = charset;
		charset = newCharset;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FILE_STATEMENT__CHARSET, oldCharset, newCharset);
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
	public void setCharset(Expression newCharset) {
		if (newCharset != charset) {
			NotificationChain msgs = null;
			if (charset != null)
				msgs = ((InternalEObject)charset).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.FILE_STATEMENT__CHARSET, null, msgs);
			if (newCharset != null)
				msgs = ((InternalEObject)newCharset).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.FILE_STATEMENT__CHARSET, null, msgs);
			msgs = basicSetCharset(newCharset, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.FILE_STATEMENT__CHARSET,
					newCharset, newCharset));
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
					AcceleoPackage.FILE_STATEMENT__BODY, oldBody, newBody);
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
						- AcceleoPackage.FILE_STATEMENT__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.FILE_STATEMENT__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.FILE_STATEMENT__BODY,
					newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.FILE_STATEMENT__URL:
				return basicSetUrl(null, msgs);
			case AcceleoPackage.FILE_STATEMENT__CHARSET:
				return basicSetCharset(null, msgs);
			case AcceleoPackage.FILE_STATEMENT__BODY:
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
			case AcceleoPackage.FILE_STATEMENT__MODE:
				return getMode();
			case AcceleoPackage.FILE_STATEMENT__URL:
				return getUrl();
			case AcceleoPackage.FILE_STATEMENT__CHARSET:
				return getCharset();
			case AcceleoPackage.FILE_STATEMENT__BODY:
				return getBody();
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
			case AcceleoPackage.FILE_STATEMENT__MODE:
				setMode((OpenModeKind)newValue);
				return;
			case AcceleoPackage.FILE_STATEMENT__URL:
				setUrl((Expression)newValue);
				return;
			case AcceleoPackage.FILE_STATEMENT__CHARSET:
				setCharset((Expression)newValue);
				return;
			case AcceleoPackage.FILE_STATEMENT__BODY:
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
			case AcceleoPackage.FILE_STATEMENT__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case AcceleoPackage.FILE_STATEMENT__URL:
				setUrl((Expression)null);
				return;
			case AcceleoPackage.FILE_STATEMENT__CHARSET:
				setCharset((Expression)null);
				return;
			case AcceleoPackage.FILE_STATEMENT__BODY:
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
			case AcceleoPackage.FILE_STATEMENT__MODE:
				return mode != MODE_EDEFAULT;
			case AcceleoPackage.FILE_STATEMENT__URL:
				return url != null;
			case AcceleoPackage.FILE_STATEMENT__CHARSET:
				return charset != null;
			case AcceleoPackage.FILE_STATEMENT__BODY:
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
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (mode: "); //$NON-NLS-1$
		result.append(mode);
		result.append(')');
		return result.toString();
	}

} // FileStatementImpl
