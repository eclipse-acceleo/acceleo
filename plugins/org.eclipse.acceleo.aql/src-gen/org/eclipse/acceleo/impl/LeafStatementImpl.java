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
import org.eclipse.acceleo.LeafStatement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Leaf Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.LeafStatementImpl#isNewLineNeeded <em>New Line Needed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LeafStatementImpl extends MinimalEObjectImpl.Container implements LeafStatement {
	/**
	 * The default value of the '{@link #isNewLineNeeded() <em>New Line Needed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isNewLineNeeded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEW_LINE_NEEDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNewLineNeeded() <em>New Line Needed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isNewLineNeeded()
	 * @generated
	 * @ordered
	 */
	protected boolean newLineNeeded = NEW_LINE_NEEDED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LeafStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.LEAF_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isNewLineNeeded() {
		return newLineNeeded;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNewLineNeeded(boolean newNewLineNeeded) {
		boolean oldNewLineNeeded = newLineNeeded;
		newLineNeeded = newNewLineNeeded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED, oldNewLineNeeded, newLineNeeded));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED:
				return isNewLineNeeded();
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
			case AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED:
				setNewLineNeeded((Boolean)newValue);
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
			case AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED:
				setNewLineNeeded(NEW_LINE_NEEDED_EDEFAULT);
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
			case AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED:
				return newLineNeeded != NEW_LINE_NEEDED_EDEFAULT;
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
		result.append(" (newLineNeeded: "); //$NON-NLS-1$
		result.append(newLineNeeded);
		result.append(')');
		return result.toString();
	}

} // LeafStatementImpl
