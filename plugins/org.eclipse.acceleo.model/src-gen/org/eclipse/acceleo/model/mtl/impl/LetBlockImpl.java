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

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Let Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.LetBlockImpl#getElseLet <em>Else Let</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.LetBlockImpl#getElse <em>Else</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.LetBlockImpl#getLetVariable <em>Let Variable</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class LetBlockImpl extends BlockImpl implements LetBlock {
	/**
	 * The cached value of the '{@link #getElseLet() <em>Else Let</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElseLet()
	 * @generated
	 * @ordered
	 */
	protected EList<LetBlock> elseLet;

	/**
	 * The cached value of the '{@link #getElse() <em>Else</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getElse()
	 * @generated
	 * @ordered
	 */
	protected Block else_;

	/**
	 * The cached value of the '{@link #getLetVariable() <em>Let Variable</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLetVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable letVariable;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected LetBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.LET_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<LetBlock> getElseLet() {
		if (elseLet == null) {
			elseLet = new EObjectContainmentEList<LetBlock>(LetBlock.class, this,
					MtlPackage.LET_BLOCK__ELSE_LET);
		}
		return elseLet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Block getElse() {
		return else_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetElse(Block newElse, NotificationChain msgs) {
		Block oldElse = else_;
		else_ = newElse;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.LET_BLOCK__ELSE, oldElse, newElse);
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
	public void setElse(Block newElse) {
		if (newElse != else_) {
			NotificationChain msgs = null;
			if (else_ != null)
				msgs = ((InternalEObject)else_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.LET_BLOCK__ELSE, null, msgs);
			if (newElse != null)
				msgs = ((InternalEObject)newElse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.LET_BLOCK__ELSE, null, msgs);
			msgs = basicSetElse(newElse, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.LET_BLOCK__ELSE, newElse,
					newElse));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Variable getLetVariable() {
		return letVariable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLetVariable(Variable newLetVariable, NotificationChain msgs) {
		Variable oldLetVariable = letVariable;
		letVariable = newLetVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.LET_BLOCK__LET_VARIABLE, oldLetVariable, newLetVariable);
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
	public void setLetVariable(Variable newLetVariable) {
		if (newLetVariable != letVariable) {
			NotificationChain msgs = null;
			if (letVariable != null)
				msgs = ((InternalEObject)letVariable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.LET_BLOCK__LET_VARIABLE, null, msgs);
			if (newLetVariable != null)
				msgs = ((InternalEObject)newLetVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.LET_BLOCK__LET_VARIABLE, null, msgs);
			msgs = basicSetLetVariable(newLetVariable, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.LET_BLOCK__LET_VARIABLE,
					newLetVariable, newLetVariable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.LET_BLOCK__ELSE_LET:
				return ((InternalEList<?>)getElseLet()).basicRemove(otherEnd, msgs);
			case MtlPackage.LET_BLOCK__ELSE:
				return basicSetElse(null, msgs);
			case MtlPackage.LET_BLOCK__LET_VARIABLE:
				return basicSetLetVariable(null, msgs);
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
			case MtlPackage.LET_BLOCK__ELSE_LET:
				return getElseLet();
			case MtlPackage.LET_BLOCK__ELSE:
				return getElse();
			case MtlPackage.LET_BLOCK__LET_VARIABLE:
				return getLetVariable();
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
			case MtlPackage.LET_BLOCK__ELSE_LET:
				getElseLet().clear();
				getElseLet().addAll((Collection<? extends LetBlock>)newValue);
				return;
			case MtlPackage.LET_BLOCK__ELSE:
				setElse((Block)newValue);
				return;
			case MtlPackage.LET_BLOCK__LET_VARIABLE:
				setLetVariable((Variable)newValue);
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
			case MtlPackage.LET_BLOCK__ELSE_LET:
				getElseLet().clear();
				return;
			case MtlPackage.LET_BLOCK__ELSE:
				setElse((Block)null);
				return;
			case MtlPackage.LET_BLOCK__LET_VARIABLE:
				setLetVariable((Variable)null);
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
			case MtlPackage.LET_BLOCK__ELSE_LET:
				return elseLet != null && !elseLet.isEmpty();
			case MtlPackage.LET_BLOCK__ELSE:
				return else_ != null;
			case MtlPackage.LET_BLOCK__LET_VARIABLE:
				return letVariable != null;
		}
		return super.eIsSet(featureID);
	}

} // LetBlockImpl
