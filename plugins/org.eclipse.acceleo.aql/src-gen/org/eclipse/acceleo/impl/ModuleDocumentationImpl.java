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
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Module Documentation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ModuleDocumentationImpl#getDocumentedElement <em>Documented
 * Element</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ModuleDocumentationImpl#getAuthor <em>Author</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ModuleDocumentationImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ModuleDocumentationImpl#getSince <em>Since</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModuleDocumentationImpl extends CommentImpl implements ModuleDocumentation {
	/**
	 * The cached value of the '{@link #getDocumentedElement() <em>Documented Element</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDocumentedElement()
	 * @generated
	 * @ordered
	 */
	protected DocumentedElement documentedElement;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSince() <em>Since</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected static final String SINCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSince() <em>Since</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected String since = SINCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.MODULE_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DocumentedElement getDocumentedElement() {
		if (documentedElement != null && documentedElement.eIsProxy()) {
			InternalEObject oldDocumentedElement = (InternalEObject)documentedElement;
			documentedElement = (DocumentedElement)eResolveProxy(oldDocumentedElement);
			if (documentedElement != oldDocumentedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT, oldDocumentedElement,
							documentedElement));
			}
		}
		return documentedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DocumentedElement basicGetDocumentedElement() {
		return documentedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetDocumentedElement(DocumentedElement newDocumentedElement,
			NotificationChain msgs) {
		DocumentedElement oldDocumentedElement = documentedElement;
		documentedElement = newDocumentedElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT, oldDocumentedElement,
					newDocumentedElement);
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
	public void setDocumentedElement(DocumentedElement newDocumentedElement) {
		if (newDocumentedElement != documentedElement) {
			NotificationChain msgs = null;
			if (documentedElement != null)
				msgs = ((InternalEObject)documentedElement).eInverseRemove(this,
						AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION, DocumentedElement.class, msgs);
			if (newDocumentedElement != null)
				msgs = ((InternalEObject)newDocumentedElement).eInverseAdd(this,
						AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION, DocumentedElement.class, msgs);
			msgs = basicSetDocumentedElement(newDocumentedElement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT, newDocumentedElement,
					newDocumentedElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR,
					oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.MODULE_DOCUMENTATION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getSince() {
		return since;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSince(String newSince) {
		String oldSince = since;
		since = newSince;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.MODULE_DOCUMENTATION__SINCE,
					oldSince, since));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				if (documentedElement != null)
					msgs = ((InternalEObject)documentedElement).eInverseRemove(this,
							AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION, DocumentedElement.class, msgs);
				return basicSetDocumentedElement((DocumentedElement)otherEnd, msgs);
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
			case AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return basicSetDocumentedElement(null, msgs);
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
			case AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				if (resolve)
					return getDocumentedElement();
				return basicGetDocumentedElement();
			case AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR:
				return getAuthor();
			case AcceleoPackage.MODULE_DOCUMENTATION__VERSION:
				return getVersion();
			case AcceleoPackage.MODULE_DOCUMENTATION__SINCE:
				return getSince();
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
			case AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)newValue);
				return;
			case AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR:
				setAuthor((String)newValue);
				return;
			case AcceleoPackage.MODULE_DOCUMENTATION__VERSION:
				setVersion((String)newValue);
				return;
			case AcceleoPackage.MODULE_DOCUMENTATION__SINCE:
				setSince((String)newValue);
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
			case AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)null);
				return;
			case AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case AcceleoPackage.MODULE_DOCUMENTATION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case AcceleoPackage.MODULE_DOCUMENTATION__SINCE:
				setSince(SINCE_EDEFAULT);
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
			case AcceleoPackage.MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return documentedElement != null;
			case AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case AcceleoPackage.MODULE_DOCUMENTATION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case AcceleoPackage.MODULE_DOCUMENTATION__SINCE:
				return SINCE_EDEFAULT == null ? since != null : !SINCE_EDEFAULT.equals(since);
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
		result.append(" (author: "); //$NON-NLS-1$
		result.append(author);
		result.append(", version: "); //$NON-NLS-1$
		result.append(version);
		result.append(", since: "); //$NON-NLS-1$
		result.append(since);
		result.append(')');
		return result.toString();
	}

} // ModuleDocumentationImpl
