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
package org.eclipse.acceleo.model.mtl.impl;

import java.util.Collection;

import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Macro Invocation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.MacroInvocationImpl#getDefinition <em>Definition</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.MacroInvocationImpl#getArgument <em>Argument</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MacroInvocationImpl extends TemplateExpressionImpl implements MacroInvocation {
	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected Macro definition;

	/**
	 * The cached value of the '{@link #getArgument() <em>Argument</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getArgument()
	 * @generated
	 * @ordered
	 */
	protected EList<OCLExpression> argument;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MacroInvocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.MACRO_INVOCATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Macro getDefinition() {
		if (definition != null && definition.eIsProxy()) {
			InternalEObject oldDefinition = (InternalEObject)definition;
			definition = (Macro)eResolveProxy(oldDefinition);
			if (definition != oldDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							MtlPackage.MACRO_INVOCATION__DEFINITION, oldDefinition, definition));
			}
		}
		return definition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Macro basicGetDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDefinition(Macro newDefinition) {
		Macro oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MACRO_INVOCATION__DEFINITION,
					oldDefinition, definition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<OCLExpression> getArgument() {
		if (argument == null) {
			argument = new EObjectContainmentEList<OCLExpression>(OCLExpression.class, this,
					MtlPackage.MACRO_INVOCATION__ARGUMENT);
		}
		return argument;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.MACRO_INVOCATION__ARGUMENT:
				return ((InternalEList<?>)getArgument()).basicRemove(otherEnd, msgs);
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
			case MtlPackage.MACRO_INVOCATION__DEFINITION:
				if (resolve)
					return getDefinition();
				return basicGetDefinition();
			case MtlPackage.MACRO_INVOCATION__ARGUMENT:
				return getArgument();
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
			case MtlPackage.MACRO_INVOCATION__DEFINITION:
				setDefinition((Macro)newValue);
				return;
			case MtlPackage.MACRO_INVOCATION__ARGUMENT:
				getArgument().clear();
				getArgument().addAll((Collection<? extends OCLExpression>)newValue);
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
			case MtlPackage.MACRO_INVOCATION__DEFINITION:
				setDefinition((Macro)null);
				return;
			case MtlPackage.MACRO_INVOCATION__ARGUMENT:
				getArgument().clear();
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
			case MtlPackage.MACRO_INVOCATION__DEFINITION:
				return definition != null;
			case MtlPackage.MACRO_INVOCATION__ARGUMENT:
				return argument != null && !argument.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // MacroInvocationImpl
