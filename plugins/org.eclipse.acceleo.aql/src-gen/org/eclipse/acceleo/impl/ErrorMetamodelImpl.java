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
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Metamodel</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorMetamodelImpl#getReferencedPackage <em>Referenced
 * Package</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorMetamodelImpl#getFragment <em>Fragment</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorMetamodelImpl#getMissingEndQuote <em>Missing End Quote</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorMetamodelImpl extends MinimalEObjectImpl.Container implements ErrorMetamodel {
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
	 * The default value of the '{@link #getFragment() <em>Fragment</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFragment()
	 * @generated
	 * @ordered
	 */
	protected static final String FRAGMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFragment() <em>Fragment</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFragment()
	 * @generated
	 * @ordered
	 */
	protected String fragment = FRAGMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEndQuote() <em>Missing End Quote</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndQuote()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_QUOTE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEndQuote() <em>Missing End Quote</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndQuote()
	 * @generated
	 * @ordered
	 */
	protected int missingEndQuote = MISSING_END_QUOTE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorMetamodelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_METAMODEL;
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
							AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE, oldReferencedPackage,
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
					AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE, oldReferencedPackage,
					referencedPackage));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getFragment() {
		return fragment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFragment(String newFragment) {
		String oldFragment = fragment;
		fragment = newFragment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_METAMODEL__FRAGMENT,
					oldFragment, fragment));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEndQuote() {
		return missingEndQuote;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEndQuote(int newMissingEndQuote) {
		int oldMissingEndQuote = missingEndQuote;
		missingEndQuote = newMissingEndQuote;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_METAMODEL__MISSING_END_QUOTE, oldMissingEndQuote, missingEndQuote));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE:
				if (resolve)
					return getReferencedPackage();
				return basicGetReferencedPackage();
			case AcceleoPackage.ERROR_METAMODEL__FRAGMENT:
				return getFragment();
			case AcceleoPackage.ERROR_METAMODEL__MISSING_END_QUOTE:
				return getMissingEndQuote();
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
			case AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE:
				setReferencedPackage((EPackage)newValue);
				return;
			case AcceleoPackage.ERROR_METAMODEL__FRAGMENT:
				setFragment((String)newValue);
				return;
			case AcceleoPackage.ERROR_METAMODEL__MISSING_END_QUOTE:
				setMissingEndQuote((Integer)newValue);
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
			case AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE:
				setReferencedPackage((EPackage)null);
				return;
			case AcceleoPackage.ERROR_METAMODEL__FRAGMENT:
				setFragment(FRAGMENT_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_METAMODEL__MISSING_END_QUOTE:
				setMissingEndQuote(MISSING_END_QUOTE_EDEFAULT);
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
			case AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE:
				return referencedPackage != null;
			case AcceleoPackage.ERROR_METAMODEL__FRAGMENT:
				return FRAGMENT_EDEFAULT == null ? fragment != null : !FRAGMENT_EDEFAULT.equals(fragment);
			case AcceleoPackage.ERROR_METAMODEL__MISSING_END_QUOTE:
				return missingEndQuote != MISSING_END_QUOTE_EDEFAULT;
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
		if (baseClass == Metamodel.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE:
					return AcceleoPackage.METAMODEL__REFERENCED_PACKAGE;
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
		if (baseClass == Metamodel.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.METAMODEL__REFERENCED_PACKAGE:
					return AcceleoPackage.ERROR_METAMODEL__REFERENCED_PACKAGE;
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
		result.append(" (fragment: "); //$NON-NLS-1$
		result.append(fragment);
		result.append(", missingEndQuote: "); //$NON-NLS-1$
		result.append(missingEndQuote);
		result.append(')');
		return result.toString();
	}

} // ErrorMetamodelImpl
