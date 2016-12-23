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

import java.util.Collection;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.ErrorLetStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Statement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Let Statement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl#isMissingEndHeader <em>Missing End Header</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorLetStatementImpl#isMissingEnd <em>Missing End</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorLetStatementImpl extends MinimalEObjectImpl.Container implements ErrorLetStatement {
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
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Binding> variables;

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
	 * The default value of the '{@link #isMissingEndHeader() <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_END_HEADER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingEndHeader() <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected boolean missingEndHeader = MISSING_END_HEADER_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingEnd() <em>Missing End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEnd()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_END_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingEnd() <em>Missing End</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEnd()
	 * @generated
	 * @ordered
	 */
	protected boolean missingEnd = MISSING_END_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorLetStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_LET_STATEMENT;
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
					AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION, oldStartPosition, startPosition));
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
					AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION, oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Binding> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Binding>(Binding.class, this,
					AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES);
		}
		return variables;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_LET_STATEMENT__BODY, oldBody, newBody);
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
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_LET_STATEMENT__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_LET_STATEMENT__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_LET_STATEMENT__BODY,
					newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingEndHeader() {
		return missingEndHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingEndHeader(boolean newMissingEndHeader) {
		boolean oldMissingEndHeader = missingEndHeader;
		missingEndHeader = newMissingEndHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END_HEADER, oldMissingEndHeader,
					missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingEnd() {
		return missingEnd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingEnd(boolean newMissingEnd) {
		boolean oldMissingEnd = missingEnd;
		missingEnd = newMissingEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END, oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.ERROR_LET_STATEMENT__BODY:
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
			case AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION:
				return getStartPosition();
			case AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION:
				return getEndPosition();
			case AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES:
				return getVariables();
			case AcceleoPackage.ERROR_LET_STATEMENT__BODY:
				return getBody();
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END_HEADER:
				return isMissingEndHeader();
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END:
				return isMissingEnd();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION:
				setEndPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Binding>)newValue);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__BODY:
				setBody((Block)newValue);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END:
				setMissingEnd((Boolean)newValue);
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
			case AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES:
				getVariables().clear();
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__BODY:
				setBody((Block)null);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END:
				setMissingEnd(MISSING_END_EDEFAULT);
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
			case AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES:
				return variables != null && !variables.isEmpty();
			case AcceleoPackage.ERROR_LET_STATEMENT__BODY:
				return body != null;
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
			case AcceleoPackage.ERROR_LET_STATEMENT__MISSING_END:
				return missingEnd != MISSING_END_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ASTNode.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION:
					return AcceleoPackage.AST_NODE__START_POSITION;
				case AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION:
					return AcceleoPackage.AST_NODE__END_POSITION;
				default:
					return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == LetStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES:
					return AcceleoPackage.LET_STATEMENT__VARIABLES;
				case AcceleoPackage.ERROR_LET_STATEMENT__BODY:
					return AcceleoPackage.LET_STATEMENT__BODY;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ASTNode.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.AST_NODE__START_POSITION:
					return AcceleoPackage.ERROR_LET_STATEMENT__START_POSITION;
				case AcceleoPackage.AST_NODE__END_POSITION:
					return AcceleoPackage.ERROR_LET_STATEMENT__END_POSITION;
				default:
					return -1;
			}
		}
		if (baseClass == Statement.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == LetStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.LET_STATEMENT__VARIABLES:
					return AcceleoPackage.ERROR_LET_STATEMENT__VARIABLES;
				case AcceleoPackage.LET_STATEMENT__BODY:
					return AcceleoPackage.ERROR_LET_STATEMENT__BODY;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(", missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} //ErrorLetStatementImpl
