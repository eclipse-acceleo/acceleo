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

import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
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
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Template Invocation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl#getDefinition <em>Definition</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl#getArgument <em>Argument</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl#getAfter <em>After</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.TemplateInvocationImpl#isSuper <em>Super</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TemplateInvocationImpl extends TemplateExpressionImpl implements TemplateInvocation {
	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected Template definition;

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
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression before;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression after;

	/**
	 * The cached value of the '{@link #getEach() <em>Each</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getEach()
	 * @generated
	 * @ordered
	 */
	protected OCLExpression each;

	/**
	 * The default value of the '{@link #isSuper() <em>Super</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isSuper()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUPER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSuper() <em>Super</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isSuper()
	 * @generated
	 * @ordered
	 */
	protected boolean super_ = SUPER_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TemplateInvocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.TEMPLATE_INVOCATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Template getDefinition() {
		if (definition != null && definition.eIsProxy()) {
			InternalEObject oldDefinition = (InternalEObject)definition;
			definition = (Template)eResolveProxy(oldDefinition);
			if (definition != oldDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							MtlPackage.TEMPLATE_INVOCATION__DEFINITION, oldDefinition, definition));
			}
		}
		return definition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Template basicGetDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDefinition(Template newDefinition) {
		Template oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE_INVOCATION__DEFINITION,
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
					MtlPackage.TEMPLATE_INVOCATION__ARGUMENT);
		}
		return argument;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getBefore() {
		return before;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBefore(OCLExpression newBefore, NotificationChain msgs) {
		OCLExpression oldBefore = before;
		before = newBefore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.TEMPLATE_INVOCATION__BEFORE, oldBefore, newBefore);
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
	public void setBefore(OCLExpression newBefore) {
		if (newBefore != before) {
			NotificationChain msgs = null;
			if (before != null)
				msgs = ((InternalEObject)before).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE_INVOCATION__BEFORE, null, msgs);
			if (newBefore != null)
				msgs = ((InternalEObject)newBefore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE_INVOCATION__BEFORE, null, msgs);
			msgs = basicSetBefore(newBefore, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE_INVOCATION__BEFORE,
					newBefore, newBefore));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getAfter() {
		return after;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAfter(OCLExpression newAfter, NotificationChain msgs) {
		OCLExpression oldAfter = after;
		after = newAfter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.TEMPLATE_INVOCATION__AFTER, oldAfter, newAfter);
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
	public void setAfter(OCLExpression newAfter) {
		if (newAfter != after) {
			NotificationChain msgs = null;
			if (after != null)
				msgs = ((InternalEObject)after).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE_INVOCATION__AFTER, null, msgs);
			if (newAfter != null)
				msgs = ((InternalEObject)newAfter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE_INVOCATION__AFTER, null, msgs);
			msgs = basicSetAfter(newAfter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE_INVOCATION__AFTER,
					newAfter, newAfter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OCLExpression getEach() {
		return each;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEach(OCLExpression newEach, NotificationChain msgs) {
		OCLExpression oldEach = each;
		each = newEach;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MtlPackage.TEMPLATE_INVOCATION__EACH, oldEach, newEach);
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
	public void setEach(OCLExpression newEach) {
		if (newEach != each) {
			NotificationChain msgs = null;
			if (each != null)
				msgs = ((InternalEObject)each).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE_INVOCATION__EACH, null, msgs);
			if (newEach != null)
				msgs = ((InternalEObject)newEach).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- MtlPackage.TEMPLATE_INVOCATION__EACH, null, msgs);
			msgs = basicSetEach(newEach, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE_INVOCATION__EACH,
					newEach, newEach));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSuper() {
		return super_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSuper(boolean newSuper) {
		boolean oldSuper = super_;
		super_ = newSuper;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.TEMPLATE_INVOCATION__SUPER,
					oldSuper, super_));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MtlPackage.TEMPLATE_INVOCATION__ARGUMENT:
				return ((InternalEList<?>)getArgument()).basicRemove(otherEnd, msgs);
			case MtlPackage.TEMPLATE_INVOCATION__BEFORE:
				return basicSetBefore(null, msgs);
			case MtlPackage.TEMPLATE_INVOCATION__AFTER:
				return basicSetAfter(null, msgs);
			case MtlPackage.TEMPLATE_INVOCATION__EACH:
				return basicSetEach(null, msgs);
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
			case MtlPackage.TEMPLATE_INVOCATION__DEFINITION:
				if (resolve)
					return getDefinition();
				return basicGetDefinition();
			case MtlPackage.TEMPLATE_INVOCATION__ARGUMENT:
				return getArgument();
			case MtlPackage.TEMPLATE_INVOCATION__BEFORE:
				return getBefore();
			case MtlPackage.TEMPLATE_INVOCATION__AFTER:
				return getAfter();
			case MtlPackage.TEMPLATE_INVOCATION__EACH:
				return getEach();
			case MtlPackage.TEMPLATE_INVOCATION__SUPER:
				return isSuper() ? Boolean.TRUE : Boolean.FALSE;
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
			case MtlPackage.TEMPLATE_INVOCATION__DEFINITION:
				setDefinition((Template)newValue);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__ARGUMENT:
				getArgument().clear();
				getArgument().addAll((Collection<? extends OCLExpression>)newValue);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__BEFORE:
				setBefore((OCLExpression)newValue);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__AFTER:
				setAfter((OCLExpression)newValue);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__EACH:
				setEach((OCLExpression)newValue);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__SUPER:
				setSuper(((Boolean)newValue).booleanValue());
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
			case MtlPackage.TEMPLATE_INVOCATION__DEFINITION:
				setDefinition((Template)null);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__ARGUMENT:
				getArgument().clear();
				return;
			case MtlPackage.TEMPLATE_INVOCATION__BEFORE:
				setBefore((OCLExpression)null);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__AFTER:
				setAfter((OCLExpression)null);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__EACH:
				setEach((OCLExpression)null);
				return;
			case MtlPackage.TEMPLATE_INVOCATION__SUPER:
				setSuper(SUPER_EDEFAULT);
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
			case MtlPackage.TEMPLATE_INVOCATION__DEFINITION:
				return definition != null;
			case MtlPackage.TEMPLATE_INVOCATION__ARGUMENT:
				return argument != null && !argument.isEmpty();
			case MtlPackage.TEMPLATE_INVOCATION__BEFORE:
				return before != null;
			case MtlPackage.TEMPLATE_INVOCATION__AFTER:
				return after != null;
			case MtlPackage.TEMPLATE_INVOCATION__EACH:
				return each != null;
			case MtlPackage.TEMPLATE_INVOCATION__SUPER:
				return super_ != SUPER_EDEFAULT;
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
		result.append(" (super: "); //$NON-NLS-1$
		result.append(super_);
		result.append(')');
		return result.toString();
	}

} // TemplateInvocationImpl
