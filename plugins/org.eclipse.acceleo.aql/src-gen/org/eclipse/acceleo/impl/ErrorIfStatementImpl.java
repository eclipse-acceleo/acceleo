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
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.ErrorIfStatement;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Statement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error If Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getCondition <em>Condition</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getThen <em>Then</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getElse <em>Else</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorIfStatementImpl#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorIfStatementImpl extends MinimalEObjectImpl.Container implements ErrorIfStatement {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Expression condition;

	/**
	 * The cached value of the '{@link #getThen() <em>Then</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getThen()
	 * @generated
	 * @ordered
	 */
	protected Block then;

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
	 * The default value of the '{@link #getMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_OPEN_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingOpenParenthesis = MISSING_OPEN_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_CLOSE_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingCloseParenthesis = MISSING_CLOSE_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEndHeader() <em>Missing End Header</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_HEADER_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEndHeader() <em>Missing End Header</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected int missingEndHeader = MISSING_END_HEADER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEnd() <em>Missing End</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEnd()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEnd() <em>Missing End</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEnd()
	 * @generated
	 * @ordered
	 */
	protected int missingEnd = MISSING_END_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorIfStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_IF_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCondition(Expression newCondition, NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__CONDITION, oldCondition, newCondition);
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
	@Override
	public void setCondition(Expression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IF_STATEMENT__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IF_STATEMENT__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Block getThen() {
		return then;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetThen(Block newThen, NotificationChain msgs) {
		Block oldThen = then;
		then = newThen;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__THEN, oldThen, newThen);
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
	@Override
	public void setThen(Block newThen) {
		if (newThen != then) {
			NotificationChain msgs = null;
			if (then != null)
				msgs = ((InternalEObject)then).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IF_STATEMENT__THEN, null, msgs);
			if (newThen != null)
				msgs = ((InternalEObject)newThen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IF_STATEMENT__THEN, null, msgs);
			msgs = basicSetThen(newThen, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_IF_STATEMENT__THEN,
					newThen, newThen));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
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
					AcceleoPackage.ERROR_IF_STATEMENT__ELSE, oldElse, newElse);
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
	@Override
	public void setElse(Block newElse) {
		if (newElse != else_) {
			NotificationChain msgs = null;
			if (else_ != null)
				msgs = ((InternalEObject)else_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IF_STATEMENT__ELSE, null, msgs);
			if (newElse != null)
				msgs = ((InternalEObject)newElse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_IF_STATEMENT__ELSE, null, msgs);
			msgs = basicSetElse(newElse, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_IF_STATEMENT__ELSE,
					newElse, newElse));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingOpenParenthesis() {
		return missingOpenParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingOpenParenthesis(int newMissingOpenParenthesis) {
		int oldMissingOpenParenthesis = missingOpenParenthesis;
		missingOpenParenthesis = newMissingOpenParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
					missingOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingCloseParenthesis() {
		return missingCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingCloseParenthesis(int newMissingCloseParenthesis) {
		int oldMissingCloseParenthesis = missingCloseParenthesis;
		missingCloseParenthesis = newMissingCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS, oldMissingCloseParenthesis,
					missingCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEndHeader() {
		return missingEndHeader;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEndHeader(int newMissingEndHeader) {
		int oldMissingEndHeader = missingEndHeader;
		missingEndHeader = newMissingEndHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END_HEADER, oldMissingEndHeader,
					missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEnd() {
		return missingEnd;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEnd(int newMissingEnd) {
		int oldMissingEnd = missingEnd;
		missingEnd = newMissingEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END, oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_IF_STATEMENT__CONDITION:
				return basicSetCondition(null, msgs);
			case AcceleoPackage.ERROR_IF_STATEMENT__THEN:
				return basicSetThen(null, msgs);
			case AcceleoPackage.ERROR_IF_STATEMENT__ELSE:
				return basicSetElse(null, msgs);
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
			case AcceleoPackage.ERROR_IF_STATEMENT__CONDITION:
				return getCondition();
			case AcceleoPackage.ERROR_IF_STATEMENT__THEN:
				return getThen();
			case AcceleoPackage.ERROR_IF_STATEMENT__ELSE:
				return getElse();
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS:
				return getMissingOpenParenthesis();
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				return getMissingCloseParenthesis();
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END_HEADER:
				return getMissingEndHeader();
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END:
				return getMissingEnd();
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
			case AcceleoPackage.ERROR_IF_STATEMENT__CONDITION:
				setCondition((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__THEN:
				setThen((Block)newValue);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__ELSE:
				setElse((Block)newValue);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END:
				setMissingEnd((Integer)newValue);
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
			case AcceleoPackage.ERROR_IF_STATEMENT__CONDITION:
				setCondition((Expression)null);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__THEN:
				setThen((Block)null);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__ELSE:
				setElse((Block)null);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END:
				setMissingEnd(MISSING_END_EDEFAULT);
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
			case AcceleoPackage.ERROR_IF_STATEMENT__CONDITION:
				return condition != null;
			case AcceleoPackage.ERROR_IF_STATEMENT__THEN:
				return then != null;
			case AcceleoPackage.ERROR_IF_STATEMENT__ELSE:
				return else_ != null;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
			case AcceleoPackage.ERROR_IF_STATEMENT__MISSING_END:
				return missingEnd != MISSING_END_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Statement.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == IfStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_IF_STATEMENT__CONDITION:
					return AcceleoPackage.IF_STATEMENT__CONDITION;
				case AcceleoPackage.ERROR_IF_STATEMENT__THEN:
					return AcceleoPackage.IF_STATEMENT__THEN;
				case AcceleoPackage.ERROR_IF_STATEMENT__ELSE:
					return AcceleoPackage.IF_STATEMENT__ELSE;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Statement.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == IfStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.IF_STATEMENT__CONDITION:
					return AcceleoPackage.ERROR_IF_STATEMENT__CONDITION;
				case AcceleoPackage.IF_STATEMENT__THEN:
					return AcceleoPackage.ERROR_IF_STATEMENT__THEN;
				case AcceleoPackage.IF_STATEMENT__ELSE:
					return AcceleoPackage.ERROR_IF_STATEMENT__ELSE;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (missingOpenParenthesis: "); //$NON-NLS-1$
		result.append(missingOpenParenthesis);
		result.append(", missingCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingCloseParenthesis);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(", missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} // ErrorIfStatementImpl
