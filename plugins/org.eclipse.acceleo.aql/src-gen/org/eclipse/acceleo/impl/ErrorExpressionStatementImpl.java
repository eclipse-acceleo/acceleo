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
import org.eclipse.acceleo.ErrorExpressionStatement;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.LeafStatement;
import org.eclipse.acceleo.Statement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Expression Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorExpressionStatementImpl#isMultiLines <em>Multi Lines</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorExpressionStatementImpl#isNewLineNeeded <em>New Line
 * Needed</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorExpressionStatementImpl#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorExpressionStatementImpl#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorExpressionStatementImpl extends MinimalEObjectImpl.Container implements ErrorExpressionStatement {
	/**
	 * The default value of the '{@link #isMultiLines() <em>Multi Lines</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isMultiLines()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTI_LINES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiLines() <em>Multi Lines</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isMultiLines()
	 * @generated
	 * @ordered
	 */
	protected boolean multiLines = MULTI_LINES_EDEFAULT;

	/**
	 * The default value of the '{@link #isNewLineNeeded() <em>New Line Needed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isNewLineNeeded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEW_LINE_NEEDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNewLineNeeded() <em>New Line Needed</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isNewLineNeeded()
	 * @generated
	 * @ordered
	 */
	protected boolean newLineNeeded = NEW_LINE_NEEDED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorExpressionStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_EXPRESSION_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isMultiLines() {
		return multiLines;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMultiLines(boolean newMultiLines) {
		boolean oldMultiLines = multiLines;
		multiLines = newMultiLines;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES, oldMultiLines, multiLines));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isNewLineNeeded() {
		return newLineNeeded;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNewLineNeeded(boolean newNewLineNeeded) {
		boolean oldNewLineNeeded = newLineNeeded;
		newLineNeeded = newNewLineNeeded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED, oldNewLineNeeded,
					newLineNeeded));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION, newExpression, newExpression));
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
					AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER, oldMissingEndHeader,
					missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION:
				return basicSetExpression(null, msgs);
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
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES:
				return isMultiLines();
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED:
				return isNewLineNeeded();
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION:
				return getExpression();
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER:
				return getMissingEndHeader();
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
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES:
				setMultiLines((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED:
				setNewLineNeeded((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION:
				setExpression((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
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
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES:
				setMultiLines(MULTI_LINES_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED:
				setNewLineNeeded(NEW_LINE_NEEDED_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION:
				setExpression((Expression)null);
				return;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
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
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES:
				return multiLines != MULTI_LINES_EDEFAULT;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED:
				return newLineNeeded != NEW_LINE_NEEDED_EDEFAULT;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION:
				return expression != null;
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
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
				case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES:
					return AcceleoPackage.STATEMENT__MULTI_LINES;
				default:
					return -1;
			}
		}
		if (baseClass == LeafStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED:
					return AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED;
				default:
					return -1;
			}
		}
		if (baseClass == ExpressionStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION:
					return AcceleoPackage.EXPRESSION_STATEMENT__EXPRESSION;
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
				case AcceleoPackage.STATEMENT__MULTI_LINES:
					return AcceleoPackage.ERROR_EXPRESSION_STATEMENT__MULTI_LINES;
				default:
					return -1;
			}
		}
		if (baseClass == LeafStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED:
					return AcceleoPackage.ERROR_EXPRESSION_STATEMENT__NEW_LINE_NEEDED;
				default:
					return -1;
			}
		}
		if (baseClass == ExpressionStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.EXPRESSION_STATEMENT__EXPRESSION:
					return AcceleoPackage.ERROR_EXPRESSION_STATEMENT__EXPRESSION;
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
		result.append(" (multiLines: "); //$NON-NLS-1$
		result.append(multiLines);
		result.append(", newLineNeeded: "); //$NON-NLS-1$
		result.append(newLineNeeded);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(')');
		return result.toString();
	}

} // ErrorExpressionStatementImpl
