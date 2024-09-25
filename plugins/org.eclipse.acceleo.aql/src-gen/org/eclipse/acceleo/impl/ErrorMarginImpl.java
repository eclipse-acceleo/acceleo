/**
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.acceleo.ErrorMargin;
import org.eclipse.acceleo.LeafStatement;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Margin</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorMarginImpl#isMultiLines <em>Multi Lines</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorMarginImpl#isNewLineNeeded <em>New Line Needed</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorMarginImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorMarginImpl extends MinimalEObjectImpl.Container implements ErrorMargin {
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
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorMarginImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_MARGIN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MARGIN__MULTI_LINES,
					oldMultiLines, multiLines));
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
					AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED, oldNewLineNeeded, newLineNeeded));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MARGIN__VALUE,
					oldValue, value));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MARGIN__MULTI_LINES:
				return isMultiLines();
			case AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED:
				return isNewLineNeeded();
			case AcceleoPackage.ERROR_MARGIN__VALUE:
				return getValue();
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
			case AcceleoPackage.ERROR_MARGIN__MULTI_LINES:
				setMultiLines((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED:
				setNewLineNeeded((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MARGIN__VALUE:
				setValue((String)newValue);
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
			case AcceleoPackage.ERROR_MARGIN__MULTI_LINES:
				setMultiLines(MULTI_LINES_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED:
				setNewLineNeeded(NEW_LINE_NEEDED_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MARGIN__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case AcceleoPackage.ERROR_MARGIN__MULTI_LINES:
				return multiLines != MULTI_LINES_EDEFAULT;
			case AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED:
				return newLineNeeded != NEW_LINE_NEEDED_EDEFAULT;
			case AcceleoPackage.ERROR_MARGIN__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
				case AcceleoPackage.ERROR_MARGIN__MULTI_LINES:
					return AcceleoPackage.STATEMENT__MULTI_LINES;
				default:
					return -1;
			}
		}
		if (baseClass == LeafStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED:
					return AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED;
				default:
					return -1;
			}
		}
		if (baseClass == TextStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MARGIN__VALUE:
					return AcceleoPackage.TEXT_STATEMENT__VALUE;
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
					return AcceleoPackage.ERROR_MARGIN__MULTI_LINES;
				default:
					return -1;
			}
		}
		if (baseClass == LeafStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.LEAF_STATEMENT__NEW_LINE_NEEDED:
					return AcceleoPackage.ERROR_MARGIN__NEW_LINE_NEEDED;
				default:
					return -1;
			}
		}
		if (baseClass == TextStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.TEXT_STATEMENT__VALUE:
					return AcceleoPackage.ERROR_MARGIN__VALUE;
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
		result.append(", value: "); //$NON-NLS-1$
		result.append(value);
		result.append(')');
		return result.toString();
	}

} // ErrorMarginImpl
