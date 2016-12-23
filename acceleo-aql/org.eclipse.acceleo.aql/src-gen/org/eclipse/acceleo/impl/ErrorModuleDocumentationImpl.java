/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import org.eclipse.acceleo.ASTNode;
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

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Module Documentation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getDocumentedElement <em>Documented Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#getSince <em>Since</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleDocumentationImpl#isMissingEndHeader <em>Missing End Header</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorModuleDocumentationImpl extends MinimalEObjectImpl.Container implements ErrorModuleDocumentation {
	/**
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected int endPosition = END_POSITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected CommentBody body;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSince() <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected static final String SINCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSince() <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected String since = SINCE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingEndHeader() <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_END_HEADER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingEndHeader() <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected boolean missingEndHeader = MISSING_END_HEADER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorModuleDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_MODULE_DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION, oldStartPosition,
					startPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndPosition(int newEndPosition) {
		int oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION, oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentBody getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentedElement getDocumentedElement() {
		if (eContainerFeatureID() != AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT)
			return null;
		return (DocumentedElement)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentedElement(DocumentedElement newDocumentedElement,
			NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDocumentedElement,
				AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentedElement(DocumentedElement newDocumentedElement) {
		if (newDocumentedElement != eInternalContainer()
				|| (eContainerFeatureID() != AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT && newDocumentedElement != null)) {
			if (EcoreUtil.isAncestor(this, newDocumentedElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSince() {
		return since;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSince(String newSince) {
		String oldSince = since;
		since = newSince;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE, oldSince, since));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingEndHeader() {
		return missingEndHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingEndHeader(boolean newMissingEndHeader) {
		boolean oldMissingEndHeader = missingEndHeader;
		missingEndHeader = newMissingEndHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER, oldMissingEndHeader,
					missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDocumentedElement((DocumentedElement)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return eInternalContainer().eInverseRemove(this,
						AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION, DocumentedElement.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION:
				return getStartPosition();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION:
				return getEndPosition();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				return getBody();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return getDocumentedElement();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__AUTHOR:
				return getAuthor();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__VERSION:
				return getVersion();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__SINCE:
				return getSince();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER:
				return isMissingEndHeader();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION:
				setEndPosition((Integer)newValue);
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
				setMissingEndHeader((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__BODY:
				return body != null;
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return getDocumentedElement() != null;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ASTNode.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION:
					return AcceleoPackage.AST_NODE__START_POSITION;
				case AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION:
					return AcceleoPackage.AST_NODE__END_POSITION;
				default:
					return -1;
			}
		}
		if (baseClass == ModuleElement.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (derivedFeatureID) {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ASTNode.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.AST_NODE__START_POSITION:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__START_POSITION;
				case AcceleoPackage.AST_NODE__END_POSITION:
					return AcceleoPackage.ERROR_MODULE_DOCUMENTATION__END_POSITION;
				default:
					return -1;
			}
		}
		if (baseClass == ModuleElement.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseFeatureID) {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startPosition: "); //$NON-NLS-1$
		result.append(startPosition);
		result.append(", endPosition: "); //$NON-NLS-1$
		result.append(endPosition);
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

} //ErrorModuleDocumentationImpl
