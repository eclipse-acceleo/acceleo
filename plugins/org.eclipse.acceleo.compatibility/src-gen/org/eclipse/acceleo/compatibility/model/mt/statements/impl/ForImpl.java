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
package org.eclipse.acceleo.compatibility.model.mt.statements.impl;

import java.util.Collection;

import org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.statements.For;
import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>For</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.ForImpl#getIterator <em>Iterator
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.ForImpl#getStatements <em>Statements
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ForImpl extends ASTNodeImpl implements For {
	/**
	 * The cached value of the '{@link #getIterator() <em>Iterator</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIterator()
	 * @generated
	 * @ordered
	 */
	protected Expression iterator;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ForImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StatementsPackage.Literals.FOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getIterator() {
		return iterator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetIterator(Expression newIterator, NotificationChain msgs) {
		Expression oldIterator = iterator;
		iterator = newIterator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StatementsPackage.FOR__ITERATOR, oldIterator, newIterator);
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
	public void setIterator(Expression newIterator) {
		if (newIterator != iterator) {
			NotificationChain msgs = null;
			if (iterator != null)
				msgs = ((InternalEObject)iterator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- StatementsPackage.FOR__ITERATOR, null, msgs);
			if (newIterator != null)
				msgs = ((InternalEObject)newIterator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- StatementsPackage.FOR__ITERATOR, null, msgs);
			msgs = basicSetIterator(newIterator, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StatementsPackage.FOR__ITERATOR,
					newIterator, newIterator));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Statement> getStatements() {
		if (statements == null) {
			statements = new EObjectContainmentEList<Statement>(Statement.class, this,
					StatementsPackage.FOR__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StatementsPackage.FOR__ITERATOR:
				return basicSetIterator(null, msgs);
			case StatementsPackage.FOR__STATEMENTS:
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
			case StatementsPackage.FOR__ITERATOR:
				return getIterator();
			case StatementsPackage.FOR__STATEMENTS:
				return getStatements();
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
			case StatementsPackage.FOR__ITERATOR:
				setIterator((Expression)newValue);
				return;
			case StatementsPackage.FOR__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends Statement>)newValue);
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
			case StatementsPackage.FOR__ITERATOR:
				setIterator((Expression)null);
				return;
			case StatementsPackage.FOR__STATEMENTS:
				getStatements().clear();
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
			case StatementsPackage.FOR__ITERATOR:
				return iterator != null;
			case StatementsPackage.FOR__STATEMENTS:
				return statements != null && !statements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ForImpl
