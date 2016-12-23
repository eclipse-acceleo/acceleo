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

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.ErrorProtectedArea;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Statement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Protected Area</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getEndPosition <em>End Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#isMissingEndHeader <em>Missing End Header</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#isMissingEnd <em>Missing End</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorProtectedAreaImpl extends MinimalEObjectImpl.Container implements ErrorProtectedArea {
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
	 * The cached value of the '{@link #getId() <em>Id</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Expression id;

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
	 * The default value of the '{@link #isMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_OPEN_PARENTHESIS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected boolean missingOpenParenthesis = MISSING_OPEN_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_CLOSE_PARENTHESIS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected boolean missingCloseParenthesis = MISSING_CLOSE_PARENTHESIS_EDEFAULT;

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
	protected ErrorProtectedAreaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_PROTECTED_AREA;
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
					AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION, oldStartPosition, startPosition));
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
					AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION, oldEndPosition, endPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetId(Expression newId, NotificationChain msgs) {
		Expression oldId = id;
		id = newId;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__ID, oldId, newId);
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
	public void setId(Expression newId) {
		if (newId != id) {
			NotificationChain msgs = null;
			if (id != null)
				msgs = ((InternalEObject)id).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__ID, null, msgs);
			if (newId != null)
				msgs = ((InternalEObject)newId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__ID, null, msgs);
			msgs = basicSetId(newId, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_PROTECTED_AREA__ID,
					newId, newId));
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
					AcceleoPackage.ERROR_PROTECTED_AREA__BODY, oldBody, newBody);
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
						- AcceleoPackage.ERROR_PROTECTED_AREA__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_PROTECTED_AREA__BODY,
					newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingOpenParenthesis() {
		return missingOpenParenthesis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingOpenParenthesis(boolean newMissingOpenParenthesis) {
		boolean oldMissingOpenParenthesis = missingOpenParenthesis;
		missingOpenParenthesis = newMissingOpenParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
					missingOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingCloseParenthesis() {
		return missingCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingCloseParenthesis(boolean newMissingCloseParenthesis) {
		boolean oldMissingCloseParenthesis = missingCloseParenthesis;
		missingCloseParenthesis = newMissingCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS,
					oldMissingCloseParenthesis, missingCloseParenthesis));
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER, oldMissingEndHeader,
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END, oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				return basicSetId(null, msgs);
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION:
				return getStartPosition();
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION:
				return getEndPosition();
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				return getId();
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				return getBody();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				return isMissingOpenParenthesis();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				return isMissingCloseParenthesis();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				return isMissingEndHeader();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
				return isMissingEnd();
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION:
				setEndPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				setId((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				setBody((Block)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				setMissingEndHeader((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION:
				setEndPosition(END_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				setId((Expression)null);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				setBody((Block)null);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION:
				return endPosition != END_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				return id != null;
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				return body != null;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
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
				case AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION:
					return AcceleoPackage.AST_NODE__START_POSITION;
				case AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION:
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
		if (baseClass == ProtectedArea.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
					return AcceleoPackage.PROTECTED_AREA__ID;
				case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
					return AcceleoPackage.PROTECTED_AREA__BODY;
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
					return AcceleoPackage.ERROR_PROTECTED_AREA__START_POSITION;
				case AcceleoPackage.AST_NODE__END_POSITION:
					return AcceleoPackage.ERROR_PROTECTED_AREA__END_POSITION;
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
		if (baseClass == ProtectedArea.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.PROTECTED_AREA__ID:
					return AcceleoPackage.ERROR_PROTECTED_AREA__ID;
				case AcceleoPackage.PROTECTED_AREA__BODY:
					return AcceleoPackage.ERROR_PROTECTED_AREA__BODY;
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
		result.append(", missingOpenParenthesis: "); //$NON-NLS-1$
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

} //ErrorProtectedAreaImpl
