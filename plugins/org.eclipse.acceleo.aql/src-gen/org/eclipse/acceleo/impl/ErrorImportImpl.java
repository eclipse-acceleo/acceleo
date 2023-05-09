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
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Import</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorImportImpl#getModule <em>Module</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorImportImpl#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorImportImpl extends MinimalEObjectImpl.Container implements ErrorImport {
	/**
	 * The cached value of the '{@link #getModule() <em>Module</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModule()
	 * @generated
	 * @ordered
	 */
	protected ModuleReference module;

	/**
	 * The default value of the '{@link #getMissingEnd() <em>Missing End</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEnd()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEnd() <em>Missing End</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEnd()
	 * @generated
	 * @ordered
	 */
	protected int missingEnd = MISSING_END_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorImportImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_IMPORT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModuleReference getModule() {
		return module;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetModule(ModuleReference newModule, NotificationChain msgs) {
		ModuleReference oldModule = module;
		module = newModule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IMPORT__MODULE, oldModule, newModule);
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
	public void setModule(ModuleReference newModule) {
		if (newModule != module) {
			NotificationChain msgs = null;
			if (module != null)
				msgs = ((InternalEObject)module).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IMPORT__MODULE, null, msgs);
			if (newModule != null)
				msgs = ((InternalEObject)newModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IMPORT__MODULE, null, msgs);
			msgs = basicSetModule(newModule, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_IMPORT__MODULE,
					newModule, newModule));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEnd() {
		return missingEnd;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEnd(int newMissingEnd) {
		int oldMissingEnd = missingEnd;
		missingEnd = newMissingEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_IMPORT__MISSING_END,
					oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_IMPORT__MODULE:
				return basicSetModule(null, msgs);
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
			case AcceleoPackage.ERROR_IMPORT__MODULE:
				return getModule();
			case AcceleoPackage.ERROR_IMPORT__MISSING_END:
				return getMissingEnd();
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
			case AcceleoPackage.ERROR_IMPORT__MODULE:
				setModule((ModuleReference)newValue);
				return;
			case AcceleoPackage.ERROR_IMPORT__MISSING_END:
				setMissingEnd((Integer)newValue);
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
			case AcceleoPackage.ERROR_IMPORT__MODULE:
				setModule((ModuleReference)null);
				return;
			case AcceleoPackage.ERROR_IMPORT__MISSING_END:
				setMissingEnd(MISSING_END_EDEFAULT);
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
			case AcceleoPackage.ERROR_IMPORT__MODULE:
				return module != null;
			case AcceleoPackage.ERROR_IMPORT__MISSING_END:
				return missingEnd != MISSING_END_EDEFAULT;
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
		if (baseClass == Import.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_IMPORT__MODULE:
					return AcceleoPackage.IMPORT__MODULE;
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
		if (baseClass == Import.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.IMPORT__MODULE:
					return AcceleoPackage.ERROR_IMPORT__MODULE;
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
		result.append(" (missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} // ErrorImportImpl
