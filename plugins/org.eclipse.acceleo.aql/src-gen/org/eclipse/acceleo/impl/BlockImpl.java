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
import org.eclipse.acceleo.Block;
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
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Block</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.BlockImpl#getStatements <em>Statements</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.BlockImpl#isInlined <em>Inlined</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BlockImpl extends MinimalEObjectImpl.Container implements Block {
	/**
	 * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Statement> statements;

	/**
	 * The default value of the '{@link #isInlined() <em>Inlined</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isInlined()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INLINED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInlined() <em>Inlined</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isInlined()
	 * @generated
	 * @ordered
	 */
	protected boolean inlined = INLINED_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Statement> getStatements() {
		if (statements == null) {
			statements = new EObjectContainmentEList<Statement>(Statement.class, this,
					AcceleoPackage.BLOCK__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isInlined() {
		return inlined;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setInlined(boolean newInlined) {
		boolean oldInlined = inlined;
		inlined = newInlined;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.BLOCK__INLINED, oldInlined,
					inlined));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.BLOCK__STATEMENTS:
				return ((InternalEList<?>)getStatements()).basicRemove(otherEnd, msgs);
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
			case AcceleoPackage.BLOCK__STATEMENTS:
				return getStatements();
			case AcceleoPackage.BLOCK__INLINED:
				return isInlined();
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
			case AcceleoPackage.BLOCK__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends Statement>)newValue);
				return;
			case AcceleoPackage.BLOCK__INLINED:
				setInlined((Boolean)newValue);
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
			case AcceleoPackage.BLOCK__STATEMENTS:
				getStatements().clear();
				return;
			case AcceleoPackage.BLOCK__INLINED:
				setInlined(INLINED_EDEFAULT);
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
			case AcceleoPackage.BLOCK__STATEMENTS:
				return statements != null && !statements.isEmpty();
			case AcceleoPackage.BLOCK__INLINED:
				return inlined != INLINED_EDEFAULT;
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
		result.append(" (inlined: "); //$NON-NLS-1$
		result.append(inlined);
		result.append(')');
		return result.toString();
	}

} // BlockImpl
