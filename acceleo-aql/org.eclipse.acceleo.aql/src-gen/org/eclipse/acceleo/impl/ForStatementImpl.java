/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.Variable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>For Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getBefore <em>Before</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getEach <em>Each</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getAfter <em>After</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ForStatementImpl#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ForStatementImpl extends MinimalEObjectImpl.Container implements
		ForStatement {
	/**
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected int endPosition = END_POSITION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable variable;

	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected Expression values;

	/**
	 * The cached value of the '{@link #getBefore() <em>Before</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBefore()
	 * @generated
	 * @ordered
	 */
	protected Expression before;

	/**
	 * The cached value of the '{@link #getEach() <em>Each</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEach()
	 * @generated
	 * @ordered
	 */
	protected Expression each;

	/**
	 * The cached value of the '{@link #getAfter() <em>After</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAfter()
	 * @generated
	 * @ordered
	 */
	protected Expression after;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ForStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.FOR_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__START_POSITION,
					oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndPosition(int newEndPosition) {
		int oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__END_POSITION, oldEndPosition,
					endPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariable(Variable newVariable,
			NotificationChain msgs) {
		Variable oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, AcceleoPackage.FOR_STATEMENT__VARIABLE,
					oldVariable, newVariable);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(Variable newVariable) {
		if (newVariable != variable) {
			NotificationChain msgs = null;
			if (variable != null)
				msgs = ((InternalEObject) variable).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__VARIABLE, null,
						msgs);
			if (newVariable != null)
				msgs = ((InternalEObject) newVariable).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__VARIABLE, null,
						msgs);
			msgs = basicSetVariable(newVariable, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__VARIABLE, newVariable,
					newVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getValues() {
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValues(Expression newValues,
			NotificationChain msgs) {
		Expression oldValues = values;
		values = newValues;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, AcceleoPackage.FOR_STATEMENT__VALUES,
					oldValues, newValues);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValues(Expression newValues) {
		if (newValues != values) {
			NotificationChain msgs = null;
			if (values != null)
				msgs = ((InternalEObject) values).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__VALUES, null,
						msgs);
			if (newValues != null)
				msgs = ((InternalEObject) newValues).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__VALUES, null,
						msgs);
			msgs = basicSetValues(newValues, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__VALUES, newValues, newValues));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getBefore() {
		return before;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBefore(Expression newBefore,
			NotificationChain msgs) {
		Expression oldBefore = before;
		before = newBefore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, AcceleoPackage.FOR_STATEMENT__BEFORE,
					oldBefore, newBefore);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBefore(Expression newBefore) {
		if (newBefore != before) {
			NotificationChain msgs = null;
			if (before != null)
				msgs = ((InternalEObject) before).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__BEFORE, null,
						msgs);
			if (newBefore != null)
				msgs = ((InternalEObject) newBefore).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__BEFORE, null,
						msgs);
			msgs = basicSetBefore(newBefore, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__BEFORE, newBefore, newBefore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getEach() {
		return each;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEach(Expression newEach,
			NotificationChain msgs) {
		Expression oldEach = each;
		each = newEach;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, AcceleoPackage.FOR_STATEMENT__EACH,
					oldEach, newEach);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEach(Expression newEach) {
		if (newEach != each) {
			NotificationChain msgs = null;
			if (each != null)
				msgs = ((InternalEObject) each).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__EACH, null,
						msgs);
			if (newEach != null)
				msgs = ((InternalEObject) newEach).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__EACH, null,
						msgs);
			msgs = basicSetEach(newEach, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__EACH, newEach, newEach));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getAfter() {
		return after;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAfter(Expression newAfter,
			NotificationChain msgs) {
		Expression oldAfter = after;
		after = newAfter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, AcceleoPackage.FOR_STATEMENT__AFTER,
					oldAfter, newAfter);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAfter(Expression newAfter) {
		if (newAfter != after) {
			NotificationChain msgs = null;
			if (after != null)
				msgs = ((InternalEObject) after).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__AFTER, null,
						msgs);
			if (newAfter != null)
				msgs = ((InternalEObject) newAfter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__AFTER, null,
						msgs);
			msgs = basicSetAfter(newAfter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__AFTER, newAfter, newAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBody(Block newBody, NotificationChain msgs) {
		Block oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, AcceleoPackage.FOR_STATEMENT__BODY,
					oldBody, newBody);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(Block newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject) body).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__BODY, null,
						msgs);
			if (newBody != null)
				msgs = ((InternalEObject) newBody).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- AcceleoPackage.FOR_STATEMENT__BODY, null,
						msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.FOR_STATEMENT__BODY, newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case AcceleoPackage.FOR_STATEMENT__VARIABLE:
			return basicSetVariable(null, msgs);
		case AcceleoPackage.FOR_STATEMENT__VALUES:
			return basicSetValues(null, msgs);
		case AcceleoPackage.FOR_STATEMENT__BEFORE:
			return basicSetBefore(null, msgs);
		case AcceleoPackage.FOR_STATEMENT__EACH:
			return basicSetEach(null, msgs);
		case AcceleoPackage.FOR_STATEMENT__AFTER:
			return basicSetAfter(null, msgs);
		case AcceleoPackage.FOR_STATEMENT__BODY:
			return basicSetBody(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case AcceleoPackage.FOR_STATEMENT__START_POSITION:
			return getStartPosition();
		case AcceleoPackage.FOR_STATEMENT__END_POSITION:
			return getEndPosition();
		case AcceleoPackage.FOR_STATEMENT__VARIABLE:
			return getVariable();
		case AcceleoPackage.FOR_STATEMENT__VALUES:
			return getValues();
		case AcceleoPackage.FOR_STATEMENT__BEFORE:
			return getBefore();
		case AcceleoPackage.FOR_STATEMENT__EACH:
			return getEach();
		case AcceleoPackage.FOR_STATEMENT__AFTER:
			return getAfter();
		case AcceleoPackage.FOR_STATEMENT__BODY:
			return getBody();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case AcceleoPackage.FOR_STATEMENT__START_POSITION:
			setStartPosition((Integer) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__END_POSITION:
			setEndPosition((Integer) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__VARIABLE:
			setVariable((Variable) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__VALUES:
			setValues((Expression) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__BEFORE:
			setBefore((Expression) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__EACH:
			setEach((Expression) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__AFTER:
			setAfter((Expression) newValue);
			return;
		case AcceleoPackage.FOR_STATEMENT__BODY:
			setBody((Block) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case AcceleoPackage.FOR_STATEMENT__START_POSITION:
			setStartPosition(START_POSITION_EDEFAULT);
			return;
		case AcceleoPackage.FOR_STATEMENT__END_POSITION:
			setEndPosition(END_POSITION_EDEFAULT);
			return;
		case AcceleoPackage.FOR_STATEMENT__VARIABLE:
			setVariable((Variable) null);
			return;
		case AcceleoPackage.FOR_STATEMENT__VALUES:
			setValues((Expression) null);
			return;
		case AcceleoPackage.FOR_STATEMENT__BEFORE:
			setBefore((Expression) null);
			return;
		case AcceleoPackage.FOR_STATEMENT__EACH:
			setEach((Expression) null);
			return;
		case AcceleoPackage.FOR_STATEMENT__AFTER:
			setAfter((Expression) null);
			return;
		case AcceleoPackage.FOR_STATEMENT__BODY:
			setBody((Block) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case AcceleoPackage.FOR_STATEMENT__START_POSITION:
			return startPosition != START_POSITION_EDEFAULT;
		case AcceleoPackage.FOR_STATEMENT__END_POSITION:
			return endPosition != END_POSITION_EDEFAULT;
		case AcceleoPackage.FOR_STATEMENT__VARIABLE:
			return variable != null;
		case AcceleoPackage.FOR_STATEMENT__VALUES:
			return values != null;
		case AcceleoPackage.FOR_STATEMENT__BEFORE:
			return before != null;
		case AcceleoPackage.FOR_STATEMENT__EACH:
			return each != null;
		case AcceleoPackage.FOR_STATEMENT__AFTER:
			return after != null;
		case AcceleoPackage.FOR_STATEMENT__BODY:
			return body != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startPosition: "); //$NON-NLS-1$
		result.append(startPosition);
		result.append(", endPosition: "); //$NON-NLS-1$
		result.append(endPosition);
		result.append(')');
		return result.toString();
	}

} //ForStatementImpl
