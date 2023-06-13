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
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ErrorModuleDocumentation;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Statement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Module Documentation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#isMultiLines <em>Multi Lines</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getDocumentedElement <em>Documented
 * Element</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getAuthor <em>Author</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getSince <em>Since</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorModuleDocumentationImpl extends MinimalEObjectImpl.Container implements ErrorModuleDocumentation {
	/**
	 * The default value of the '{@link #isMultiLines() <em>Multi Lines</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isMultiLines()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTI_LINES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiLines() <em>Multi Lines</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isMultiLines()
	 * @generated
	 * @ordered
	 */
	protected boolean multiLines = MULTI_LINES_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected CommentBody body;

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
	 * The default value of the '{@link #getMissingEndHeader() <em>Missing End Header</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_HEADER_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEndHeader() <em>Missing End Header</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected int missingEndHeader = MISSING_END_HEADER_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorModuleDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_MODULE_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isMultiLines() {
		return multiLines;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMultiLines(boolean newMultiLines) {
		boolean oldMultiLines = multiLines;
		multiLines = newMultiLines;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES, oldMultiLines, multiLines));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CommentBody getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBody(CommentBody newBody, NotificationChain msgs) {
		CommentBody oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY, oldBody, newBody);
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
	public void setBody(CommentBody newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY, newBody, newBody));
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
							AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT,
							oldDocumentedElement, documentedElement));
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
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT, oldDocumentedElement,
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
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT, newDocumentedElement,
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR, oldAuthor, author));
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
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION, oldVersion, version));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE, oldSince, since));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEndHeader() {
		return missingEndHeader;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEndHeader(int newMissingEndHeader) {
		int oldMissingEndHeader = missingEndHeader;
		missingEndHeader = newMissingEndHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER, oldMissingEndHeader,
					missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
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
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				return basicSetBody(null, msgs);
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
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
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES:
				return isMultiLines();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				return getBody();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				if (resolve)
					return getDocumentedElement();
				return basicGetDocumentedElement();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR:
				return getAuthor();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION:
				return getVersion();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE:
				return getSince();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER:
				return getMissingEndHeader();
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
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES:
				setMultiLines((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				setBody((CommentBody)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR:
				setAuthor((String)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION:
				setVersion((String)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE:
				setSince((String)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
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
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES:
				setMultiLines(MULTI_LINES_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				setBody((CommentBody)null);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)null);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE:
				setSince(SINCE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
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
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES:
				return multiLines != MULTI_LINES_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				return body != null;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return documentedElement != null;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE:
				return SINCE_EDEFAULT == null ? since != null : !SINCE_EDEFAULT.equals(since);
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
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
				default:
					return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES:
					return AcceleoPackage.STATEMENT__MULTI_LINES;
				default:
					return -1;
			}
		}
		if (baseClass == Comment.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
					return AcceleoPackage.COMMENT__BODY;
				default:
					return -1;
			}
		}
		if (baseClass == Documentation.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
					return AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT;
				default:
					return -1;
			}
		}
		if (baseClass == ModuleDocumentation.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR:
					return AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR;
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION:
					return AcceleoPackage.MODULE_DOCUMENTATION__VERSION;
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE:
					return AcceleoPackage.MODULE_DOCUMENTATION__SINCE;
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
				default:
					return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.STATEMENT__MULTI_LINES:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MULTI_LINES;
				default:
					return -1;
			}
		}
		if (baseClass == Comment.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.COMMENT__BODY:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY;
				default:
					return -1;
			}
		}
		if (baseClass == Documentation.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT;
				default:
					return -1;
			}
		}
		if (baseClass == ModuleDocumentation.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.MODULE_DOCUMENTATION__AUTHOR:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR;
				case AcceleoPackage.MODULE_DOCUMENTATION__VERSION:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION;
				case AcceleoPackage.MODULE_DOCUMENTATION__SINCE:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE;
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
		result.append(" (multiLines: "); //$NON-NLS-1$
		result.append(multiLines);
		result.append(", author: "); //$NON-NLS-1$
		result.append(author);
		result.append(", version: "); //$NON-NLS-1$
		result.append(version);
		result.append(", since: "); //$NON-NLS-1$
		result.append(since);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(')');
		return result.toString();
	}

} // ErrorModuleDocumentationImpl
