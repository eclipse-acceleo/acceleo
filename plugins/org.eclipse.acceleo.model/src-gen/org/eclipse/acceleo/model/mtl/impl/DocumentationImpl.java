/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.impl;

import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Documentation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.DocumentationImpl#getDocumentedElement <em>Documented Element
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DocumentationImpl extends CommentImpl implements Documentation {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DocumentationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.DOCUMENTATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DocumentedElement getDocumentedElement() {
		if (eContainerFeatureID() != MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT)
			return null;
		return (DocumentedElement)eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetDocumentedElement(DocumentedElement newDocumentedElement,
			NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDocumentedElement,
				MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDocumentedElement(DocumentedElement newDocumentedElement) {
		if (newDocumentedElement != eInternalContainer()
				|| (eContainerFeatureID() != MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT && newDocumentedElement != null)) {
			if (EcoreUtil.isAncestor(this, newDocumentedElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDocumentedElement != null)
				msgs = ((InternalEObject)newDocumentedElement).eInverseAdd(this,
						MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION, DocumentedElement.class, msgs);
			msgs = basicSetDocumentedElement(newDocumentedElement, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, newDocumentedElement, newDocumentedElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
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
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
				return eInternalContainer().eInverseRemove(this,
						MtlPackage.DOCUMENTED_ELEMENT__DOCUMENTATION, DocumentedElement.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
				return getDocumentedElement();
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
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)newValue);
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
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
				setDocumentedElement((DocumentedElement)null);
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
			case MtlPackage.DOCUMENTATION__DOCUMENTED_ELEMENT:
				return getDocumentedElement() != null;
		}
		return super.eIsSet(featureID);
	}

} // DocumentationImpl
