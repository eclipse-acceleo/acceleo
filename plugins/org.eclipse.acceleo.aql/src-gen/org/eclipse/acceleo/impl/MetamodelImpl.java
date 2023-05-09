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
import org.eclipse.acceleo.Metamodel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Metamodel</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.MetamodelImpl#getReferencedPackage <em>Referenced Package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MetamodelImpl extends MinimalEObjectImpl.Container implements Metamodel {
	/**
	 * The cached value of the '{@link #getReferencedPackage() <em>Referenced Package</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReferencedPackage()
	 * @generated
	 * @ordered
	 */
	protected EPackage referencedPackage;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MetamodelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.METAMODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EPackage getReferencedPackage() {
		if (referencedPackage != null && referencedPackage.eIsProxy()) {
			InternalEObject oldReferencedPackage = (InternalEObject)referencedPackage;
			referencedPackage = (EPackage)eResolveProxy(oldReferencedPackage);
			if (referencedPackage != oldReferencedPackage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AcceleoPackage.METAMODEL__REFERENCED_PACKAGE, oldReferencedPackage,
							referencedPackage));
			}
		}
		return referencedPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EPackage basicGetReferencedPackage() {
		return referencedPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setReferencedPackage(EPackage newReferencedPackage) {
		EPackage oldReferencedPackage = referencedPackage;
		referencedPackage = newReferencedPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.METAMODEL__REFERENCED_PACKAGE, oldReferencedPackage, referencedPackage));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.METAMODEL__REFERENCED_PACKAGE:
				if (resolve)
					return getReferencedPackage();
				return basicGetReferencedPackage();
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
			case AcceleoPackage.METAMODEL__REFERENCED_PACKAGE:
				setReferencedPackage((EPackage)newValue);
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
			case AcceleoPackage.METAMODEL__REFERENCED_PACKAGE:
				setReferencedPackage((EPackage)null);
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
			case AcceleoPackage.METAMODEL__REFERENCED_PACKAGE:
				return referencedPackage != null;
		}
		return super.eIsSet(featureID);
	}

} // MetamodelImpl
