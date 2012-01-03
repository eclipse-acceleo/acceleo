/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.compatibility.model.mt.core.impl;

import org.eclipse.acceleo.compatibility.model.mt.core.ASTNode;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>AST Node</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl#getBegin <em>Begin</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl#getEnd <em>End</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class ASTNodeImpl extends EObjectImpl implements ASTNode {
	/**
	 * The default value of the '{@link #getBegin() <em>Begin</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBegin()
	 * @generated
	 * @ordered
	 */
	protected static final int BEGIN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getBegin() <em>Begin</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBegin()
	 * @generated
	 * @ordered
	 */
	protected int begin = BEGIN_EDEFAULT;

	/**
	 * The default value of the '{@link #getEnd() <em>End</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected static final int END_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
	protected int end = END_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ASTNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.AST_NODE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBegin(int newBegin) {
		int oldBegin = begin;
		begin = newBegin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.AST_NODE__BEGIN, oldBegin,
					begin));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEnd(int newEnd) {
		int oldEnd = end;
		end = newEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.AST_NODE__END, oldEnd, end));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Template getTemplate() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated not
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CorePackage.AST_NODE__BEGIN:
				return getBegin();
			case CorePackage.AST_NODE__END:
				return getEnd();
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
			case CorePackage.AST_NODE__BEGIN:
				setBegin((Integer)newValue);
				return;
			case CorePackage.AST_NODE__END:
				setEnd((Integer)newValue);
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
			case CorePackage.AST_NODE__BEGIN:
				setBegin(BEGIN_EDEFAULT);
				return;
			case CorePackage.AST_NODE__END:
				setEnd(END_EDEFAULT);
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
			case CorePackage.AST_NODE__BEGIN:
				return begin != BEGIN_EDEFAULT;
			case CorePackage.AST_NODE__END:
				return end != END_EDEFAULT;
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (begin: "); //$NON-NLS-1$
		result.append(begin);
		result.append(", end: "); //$NON-NLS-1$
		result.append(end);
		result.append(')');
		return result.toString();
	}

} // ASTNodeImpl
