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
package org.eclipse.acceleo.parser.cst.impl;

import java.util.Collection;

import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>If Block</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.IfBlockImpl#getIfExpr <em>If Expr</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.IfBlockImpl#getElse <em>Else</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.impl.IfBlockImpl#getElseIf <em>Else If</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class IfBlockImpl extends BlockImpl implements IfBlock {
	/**
	 * The cached value of the '{@link #getIfExpr() <em>If Expr</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getIfExpr()
	 * @generated
	 * @ordered
	 */
	protected ModelExpression ifExpr;

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
	 * The cached value of the '{@link #getElseIf() <em>Else If</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getElseIf()
	 * @generated
	 * @ordered
	 */
	protected EList<IfBlock> elseIf;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IfBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CstPackage.Literals.IF_BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelExpression getIfExpr() {
		return ifExpr;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetIfExpr(ModelExpression newIfExpr, NotificationChain msgs) {
		ModelExpression oldIfExpr = ifExpr;
		ifExpr = newIfExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CstPackage.IF_BLOCK__IF_EXPR, oldIfExpr, newIfExpr);
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
	public void setIfExpr(ModelExpression newIfExpr) {
		if (newIfExpr != ifExpr) {
			NotificationChain msgs = null;
			if (ifExpr != null)
				msgs = ((InternalEObject)ifExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.IF_BLOCK__IF_EXPR, null, msgs);
			if (newIfExpr != null)
				msgs = ((InternalEObject)newIfExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.IF_BLOCK__IF_EXPR, null, msgs);
			msgs = basicSetIfExpr(newIfExpr, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.IF_BLOCK__IF_EXPR, newIfExpr,
					newIfExpr));
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
					CstPackage.IF_BLOCK__ELSE, oldElse, newElse);
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
						- CstPackage.IF_BLOCK__ELSE, null, msgs);
			if (newElse != null)
				msgs = ((InternalEObject)newElse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CstPackage.IF_BLOCK__ELSE, null, msgs);
			msgs = basicSetElse(newElse, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CstPackage.IF_BLOCK__ELSE, newElse, newElse));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<IfBlock> getElseIf() {
		if (elseIf == null) {
			elseIf = new EObjectContainmentEList<IfBlock>(IfBlock.class, this, CstPackage.IF_BLOCK__ELSE_IF);
		}
		return elseIf;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CstPackage.IF_BLOCK__IF_EXPR:
				return basicSetIfExpr(null, msgs);
			case CstPackage.IF_BLOCK__ELSE:
				return basicSetElse(null, msgs);
			case CstPackage.IF_BLOCK__ELSE_IF:
				return ((InternalEList<?>)getElseIf()).basicRemove(otherEnd, msgs);
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
			case CstPackage.IF_BLOCK__IF_EXPR:
				return getIfExpr();
			case CstPackage.IF_BLOCK__ELSE:
				return getElse();
			case CstPackage.IF_BLOCK__ELSE_IF:
				return getElseIf();
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
			case CstPackage.IF_BLOCK__IF_EXPR:
				setIfExpr((ModelExpression)newValue);
				return;
			case CstPackage.IF_BLOCK__ELSE:
				setElse((Block)newValue);
				return;
			case CstPackage.IF_BLOCK__ELSE_IF:
				getElseIf().clear();
				getElseIf().addAll((Collection<? extends IfBlock>)newValue);
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
			case CstPackage.IF_BLOCK__IF_EXPR:
				setIfExpr((ModelExpression)null);
				return;
			case CstPackage.IF_BLOCK__ELSE:
				setElse((Block)null);
				return;
			case CstPackage.IF_BLOCK__ELSE_IF:
				getElseIf().clear();
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
			case CstPackage.IF_BLOCK__IF_EXPR:
				return ifExpr != null;
			case CstPackage.IF_BLOCK__ELSE:
				return else_ != null;
			case CstPackage.IF_BLOCK__ELSE_IF:
				return elseIf != null && !elseIf.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // IfBlockImpl
