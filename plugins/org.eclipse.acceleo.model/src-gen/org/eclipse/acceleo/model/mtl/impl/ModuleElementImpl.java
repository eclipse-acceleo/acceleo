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

import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENamedElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ocl.utilities.UtilitiesPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Module Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl#getStartPosition <em>Start Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl#getEndPosition <em>End Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.impl.ModuleElementImpl#getVisibility <em>Visibility</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class ModuleElementImpl extends ENamedElementImpl implements ModuleElement {
	/**
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndPosition() <em>End Position</em>} ' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_POSITION_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getEndPosition() <em>End Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndPosition()
	 * @generated
	 * @ordered
	 */
	protected int endPosition = END_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getVisibility() <em>Visibility</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final VisibilityKind VISIBILITY_EDEFAULT = VisibilityKind.PRIVATE;

	/**
	 * The cached value of the '{@link #getVisibility() <em>Visibility</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getVisibility()
	 * @generated
	 * @ordered
	 */
	protected VisibilityKind visibility = VISIBILITY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MtlPackage.Literals.MODULE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VisibilityKind getVisibility() {
		return visibility;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVisibility(VisibilityKind newVisibility) {
		VisibilityKind oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE_ELEMENT__VISIBILITY,
					oldVisibility, visibility));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE_ELEMENT__START_POSITION,
					oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setEndPosition(int newEndPosition) {
		int oldEndPosition = endPosition;
		endPosition = newEndPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MtlPackage.MODULE_ELEMENT__END_POSITION,
					oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MtlPackage.MODULE_ELEMENT__START_POSITION:
				return new Integer(getStartPosition());
			case MtlPackage.MODULE_ELEMENT__END_POSITION:
				return new Integer(getEndPosition());
			case MtlPackage.MODULE_ELEMENT__VISIBILITY:
				return getVisibility();
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
			case MtlPackage.MODULE_ELEMENT__START_POSITION:
				setStartPosition(((Integer)newValue).intValue());
				return;
			case MtlPackage.MODULE_ELEMENT__END_POSITION:
				setEndPosition(((Integer)newValue).intValue());
				return;
			case MtlPackage.MODULE_ELEMENT__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
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
			case MtlPackage.MODULE_ELEMENT__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case MtlPackage.MODULE_ELEMENT__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case MtlPackage.MODULE_ELEMENT__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
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
			case MtlPackage.MODULE_ELEMENT__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case MtlPackage.MODULE_ELEMENT__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case MtlPackage.MODULE_ELEMENT__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
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
		if (baseClass == ASTNode.class) {
			switch (derivedFeatureID) {
				case MtlPackage.MODULE_ELEMENT__START_POSITION:
					return UtilitiesPackage.AST_NODE__START_POSITION;
				case MtlPackage.MODULE_ELEMENT__END_POSITION:
					return UtilitiesPackage.AST_NODE__END_POSITION;
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
		if (baseClass == ASTNode.class) {
			switch (baseFeatureID) {
				case UtilitiesPackage.AST_NODE__START_POSITION:
					return MtlPackage.MODULE_ELEMENT__START_POSITION;
				case UtilitiesPackage.AST_NODE__END_POSITION:
					return MtlPackage.MODULE_ELEMENT__END_POSITION;
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startPosition: "); //$NON-NLS-1$
		result.append(startPosition);
		result.append(", endPosition: "); //$NON-NLS-1$
		result.append(endPosition);
		result.append(", visibility: "); //$NON-NLS-1$
		result.append(visibility);
		result.append(')');
		return result.toString();
	}

} // ModuleElementImpl
