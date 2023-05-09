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
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>New Line Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.NewLineStatementImpl#isIndentationNeeded <em>Indentation
 * Needed</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NewLineStatementImpl extends TextStatementImpl implements NewLineStatement {
	/**
	 * The default value of the '{@link #isIndentationNeeded() <em>Indentation Needed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isIndentationNeeded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INDENTATION_NEEDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIndentationNeeded() <em>Indentation Needed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isIndentationNeeded()
	 * @generated
	 * @ordered
	 */
	protected boolean indentationNeeded = INDENTATION_NEEDED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NewLineStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.NEW_LINE_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isIndentationNeeded() {
		return indentationNeeded;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setIndentationNeeded(boolean newIndentationNeeded) {
		boolean oldIndentationNeeded = indentationNeeded;
		indentationNeeded = newIndentationNeeded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.NEW_LINE_STATEMENT__INDENTATION_NEEDED, oldIndentationNeeded,
					indentationNeeded));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.NEW_LINE_STATEMENT__INDENTATION_NEEDED:
				return isIndentationNeeded();
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
			case AcceleoPackage.NEW_LINE_STATEMENT__INDENTATION_NEEDED:
				setIndentationNeeded((Boolean)newValue);
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
			case AcceleoPackage.NEW_LINE_STATEMENT__INDENTATION_NEEDED:
				setIndentationNeeded(INDENTATION_NEEDED_EDEFAULT);
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
			case AcceleoPackage.NEW_LINE_STATEMENT__INDENTATION_NEEDED:
				return indentationNeeded != INDENTATION_NEEDED_EDEFAULT;
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
		result.append(" (indentationNeeded: "); //$NON-NLS-1$
		result.append(indentationNeeded);
		result.append(')');
		return result.toString();
	}

} // NewLineStatementImpl
