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
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ErrorModuleElementDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.Statement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Module Element
 * Documentation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl#getDocumentedElement <em>Documented
 * Element</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl#getParameterDocumentation
 * <em>Parameter Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleElementDocumentationImpl#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorModuleElementDocumentationImpl extends MinimalEObjectImpl.Container implements ErrorModuleElementDocumentation {
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
	 * The cached value of the '{@link #getParameterDocumentation() <em>Parameter Documentation</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameterDocumentation()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterDocumentation> parameterDocumentation;

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
	protected ErrorModuleElementDocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_MODULE_ELEMENT_DOCUMENTATION;
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
					AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY, oldBody, newBody);
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
						- AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY, newBody, newBody));
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
							AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT,
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
					AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT,
					oldDocumentedElement, newDocumentedElement);
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
					AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT,
					newDocumentedElement, newDocumentedElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ParameterDocumentation> getParameterDocumentation() {
		if (parameterDocumentation == null) {
			parameterDocumentation = new EObjectContainmentEList<ParameterDocumentation>(
					ParameterDocumentation.class, this,
					AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION);
		}
		return parameterDocumentation;
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
					AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER,
					oldMissingEndHeader, missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
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
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY:
				return basicSetBody(null, msgs);
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return basicSetDocumentedElement(null, msgs);
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
				return ((InternalEList<?>)getParameterDocumentation()).basicRemove(otherEnd, msgs);
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
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY:
				return getBody();
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
				if (resolve)
					return getDocumentedElement();
				return basicGetDocumentedElement();
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
				return getParameterDocumentation();
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER:
				return getMissingEndHeader();
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
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY:
				setBody((CommentBody)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
				getParameterDocumentation().clear();
				getParameterDocumentation().addAll((Collection<? extends ParameterDocumentation>)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER:
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
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY:
				setBody((CommentBody)null);
				return;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)null);
				return;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
				getParameterDocumentation().clear();
				return;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER:
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
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY:
				return body != null;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
				return documentedElement != null;
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
				return parameterDocumentation != null && !parameterDocumentation.isEmpty();
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER:
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
				default:
					return -1;
			}
		}
		if (baseClass == Comment.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY:
					return AcceleoPackage.COMMENT__BODY;
				default:
					return -1;
			}
		}
		if (baseClass == Documentation.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT:
					return AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT;
				default:
					return -1;
			}
		}
		if (baseClass == ModuleElementDocumentation.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
					return AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION;
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
				default:
					return -1;
			}
		}
		if (baseClass == Comment.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.COMMENT__BODY:
					return AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__BODY;
				default:
					return -1;
			}
		}
		if (baseClass == Documentation.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
					return AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__DOCUMENTED_ELEMENT;
				default:
					return -1;
			}
		}
		if (baseClass == ModuleElementDocumentation.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION:
					return AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION;
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
		result.append(" (missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(')');
		return result.toString();
	}

} // ErrorModuleElementDocumentationImpl
