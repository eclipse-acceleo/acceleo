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
package org.eclipse.acceleo.compatibility.model.mt.expressions.impl;

import java.util.Collection;

import org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Call</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl#getPrefix <em>Prefix</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl#getArguments <em>Arguments
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.expressions.impl.CallImpl#getFilter <em>Filter</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CallImpl extends ASTNodeImpl implements Call {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrefix() <em>Prefix</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrefix() <em>Prefix</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getPrefix()
	 * @generated
	 * @ordered
	 */
	protected String prefix = PREFIX_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> arguments;

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected Expression filter;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionsPackage.Literals.CALL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionsPackage.CALL__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPrefix(String newPrefix) {
		String oldPrefix = prefix;
		prefix = newPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionsPackage.CALL__PREFIX, oldPrefix,
					prefix));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expression> getArguments() {
		if (arguments == null) {
			arguments = new EObjectContainmentEList<Expression>(Expression.class, this,
					ExpressionsPackage.CALL__ARGUMENTS);
		}
		return arguments;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFilter(Expression newFilter, NotificationChain msgs) {
		Expression oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ExpressionsPackage.CALL__FILTER, oldFilter, newFilter);
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
	public void setFilter(Expression newFilter) {
		if (newFilter != filter) {
			NotificationChain msgs = null;
			if (filter != null)
				msgs = ((InternalEObject)filter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- ExpressionsPackage.CALL__FILTER, null, msgs);
			if (newFilter != null)
				msgs = ((InternalEObject)newFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- ExpressionsPackage.CALL__FILTER, null, msgs);
			msgs = basicSetFilter(newFilter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionsPackage.CALL__FILTER, newFilter,
					newFilter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionsPackage.CALL__ARGUMENTS:
				return ((InternalEList<?>)getArguments()).basicRemove(otherEnd, msgs);
			case ExpressionsPackage.CALL__FILTER:
				return basicSetFilter(null, msgs);
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
			case ExpressionsPackage.CALL__NAME:
				return getName();
			case ExpressionsPackage.CALL__PREFIX:
				return getPrefix();
			case ExpressionsPackage.CALL__ARGUMENTS:
				return getArguments();
			case ExpressionsPackage.CALL__FILTER:
				return getFilter();
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
			case ExpressionsPackage.CALL__NAME:
				setName((String)newValue);
				return;
			case ExpressionsPackage.CALL__PREFIX:
				setPrefix((String)newValue);
				return;
			case ExpressionsPackage.CALL__ARGUMENTS:
				getArguments().clear();
				getArguments().addAll((Collection<? extends Expression>)newValue);
				return;
			case ExpressionsPackage.CALL__FILTER:
				setFilter((Expression)newValue);
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
			case ExpressionsPackage.CALL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ExpressionsPackage.CALL__PREFIX:
				setPrefix(PREFIX_EDEFAULT);
				return;
			case ExpressionsPackage.CALL__ARGUMENTS:
				getArguments().clear();
				return;
			case ExpressionsPackage.CALL__FILTER:
				setFilter((Expression)null);
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
			case ExpressionsPackage.CALL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ExpressionsPackage.CALL__PREFIX:
				return PREFIX_EDEFAULT == null ? prefix != null : !PREFIX_EDEFAULT.equals(prefix);
			case ExpressionsPackage.CALL__ARGUMENTS:
				return arguments != null && !arguments.isEmpty();
			case ExpressionsPackage.CALL__FILTER:
				return filter != null;
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", prefix: "); //$NON-NLS-1$ 
		result.append(prefix);
		result.append(')');
		return result.toString();
	}

} // CallImpl
