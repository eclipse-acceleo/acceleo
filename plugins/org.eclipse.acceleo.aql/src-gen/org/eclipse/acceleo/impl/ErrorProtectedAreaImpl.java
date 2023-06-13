/**
 * Copyright (c) 2008, 2023 Obeo.
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
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Protected Area</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#isMultiLines <em>Multi Lines</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getStartTagPrefix <em>Start Tag
 * Prefix</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getEndTagPrefix <em>End Tag Prefix</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getMissingStartTagPrefixCloseParenthesis
 * <em>Missing Start Tag Prefix Close Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getMissingEndTagPrefixCloseParenthesis
 * <em>Missing End Tag Prefix Close Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorProtectedAreaImpl#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorProtectedAreaImpl extends MinimalEObjectImpl.Container implements ErrorProtectedArea {
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
	 * The cached value of the '{@link #getId() <em>Id</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected Expression id;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

	/**
	 * The cached value of the '{@link #getStartTagPrefix() <em>Start Tag Prefix</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartTagPrefix()
	 * @generated
	 * @ordered
	 */
	protected Expression startTagPrefix;

	/**
	 * The cached value of the '{@link #getEndTagPrefix() <em>End Tag Prefix</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndTagPrefix()
	 * @generated
	 * @ordered
	 */
	protected Expression endTagPrefix;

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
	 * The default value of the '{@link #getMissingStartTagPrefixCloseParenthesis() <em>Missing Start Tag
	 * Prefix Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingStartTagPrefixCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingStartTagPrefixCloseParenthesis() <em>Missing Start Tag
	 * Prefix Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingStartTagPrefixCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingStartTagPrefixCloseParenthesis = MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEndTagPrefixCloseParenthesis() <em>Missing End Tag Prefix
	 * Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndTagPrefixCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEndTagPrefixCloseParenthesis() <em>Missing End Tag Prefix
	 * Close Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndTagPrefixCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingEndTagPrefixCloseParenthesis = MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT;

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
	protected ErrorProtectedAreaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_PROTECTED_AREA;
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES, oldMultiLines, multiLines));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Block getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getStartTagPrefix() {
		return startTagPrefix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetStartTagPrefix(Expression newStartTagPrefix, NotificationChain msgs) {
		Expression oldStartTagPrefix = startTagPrefix;
		startTagPrefix = newStartTagPrefix;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX, oldStartTagPrefix,
					newStartTagPrefix);
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
	public void setStartTagPrefix(Expression newStartTagPrefix) {
		if (newStartTagPrefix != startTagPrefix) {
			NotificationChain msgs = null;
			if (startTagPrefix != null)
				msgs = ((InternalEObject)startTagPrefix).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX, null, msgs);
			if (newStartTagPrefix != null)
				msgs = ((InternalEObject)newStartTagPrefix).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX, null, msgs);
			msgs = basicSetStartTagPrefix(newStartTagPrefix, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX, newStartTagPrefix,
					newStartTagPrefix));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getEndTagPrefix() {
		return endTagPrefix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetEndTagPrefix(Expression newEndTagPrefix, NotificationChain msgs) {
		Expression oldEndTagPrefix = endTagPrefix;
		endTagPrefix = newEndTagPrefix;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX, oldEndTagPrefix, newEndTagPrefix);
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
	public void setEndTagPrefix(Expression newEndTagPrefix) {
		if (newEndTagPrefix != endTagPrefix) {
			NotificationChain msgs = null;
			if (endTagPrefix != null)
				msgs = ((InternalEObject)endTagPrefix).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX, null, msgs);
			if (newEndTagPrefix != null)
				msgs = ((InternalEObject)newEndTagPrefix).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX, null, msgs);
			msgs = basicSetEndTagPrefix(newEndTagPrefix, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX, newEndTagPrefix, newEndTagPrefix));
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS,
					oldMissingCloseParenthesis, missingCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingStartTagPrefixCloseParenthesis() {
		return missingStartTagPrefixCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingStartTagPrefixCloseParenthesis(int newMissingStartTagPrefixCloseParenthesis) {
		int oldMissingStartTagPrefixCloseParenthesis = missingStartTagPrefixCloseParenthesis;
		missingStartTagPrefixCloseParenthesis = newMissingStartTagPrefixCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS,
					oldMissingStartTagPrefixCloseParenthesis, missingStartTagPrefixCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEndTagPrefixCloseParenthesis() {
		return missingEndTagPrefixCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEndTagPrefixCloseParenthesis(int newMissingEndTagPrefixCloseParenthesis) {
		int oldMissingEndTagPrefixCloseParenthesis = missingEndTagPrefixCloseParenthesis;
		missingEndTagPrefixCloseParenthesis = newMissingEndTagPrefixCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS,
					oldMissingEndTagPrefixCloseParenthesis, missingEndTagPrefixCloseParenthesis));
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER, oldMissingEndHeader,
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
					AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END, oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				return basicSetId(null, msgs);
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				return basicSetBody(null, msgs);
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX:
				return basicSetStartTagPrefix(null, msgs);
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX:
				return basicSetEndTagPrefix(null, msgs);
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES:
				return isMultiLines();
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				return getId();
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				return getBody();
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX:
				return getStartTagPrefix();
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX:
				return getEndTagPrefix();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				return getMissingOpenParenthesis();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				return getMissingCloseParenthesis();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS:
				return getMissingStartTagPrefixCloseParenthesis();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS:
				return getMissingEndTagPrefixCloseParenthesis();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				return getMissingEndHeader();
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES:
				setMultiLines((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				setId((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				setBody((Block)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX:
				setStartTagPrefix((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX:
				setEndTagPrefix((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS:
				setMissingStartTagPrefixCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS:
				setMissingEndTagPrefixCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
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
			case AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES:
				setMultiLines(MULTI_LINES_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				setId((Expression)null);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				setBody((Block)null);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX:
				setStartTagPrefix((Expression)null);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX:
				setEndTagPrefix((Expression)null);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS:
				setMissingStartTagPrefixCloseParenthesis(MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS:
				setMissingEndTagPrefixCloseParenthesis(MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES:
				return multiLines != MULTI_LINES_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__ID:
				return id != null;
			case AcceleoPackage.ERROR_PROTECTED_AREA__BODY:
				return body != null;
			case AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX:
				return startTagPrefix != null;
			case AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX:
				return endTagPrefix != null;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS:
				return missingStartTagPrefixCloseParenthesis != MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS:
				return missingEndTagPrefixCloseParenthesis != MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
			case AcceleoPackage.ERROR_PROTECTED_AREA__MISSING_END:
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
				case AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES:
					return AcceleoPackage.STATEMENT__MULTI_LINES;
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
				case AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX:
					return AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX;
				case AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX:
					return AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX;
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
					return AcceleoPackage.ERROR_PROTECTED_AREA__MULTI_LINES;
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
				case AcceleoPackage.PROTECTED_AREA__START_TAG_PREFIX:
					return AcceleoPackage.ERROR_PROTECTED_AREA__START_TAG_PREFIX;
				case AcceleoPackage.PROTECTED_AREA__END_TAG_PREFIX:
					return AcceleoPackage.ERROR_PROTECTED_AREA__END_TAG_PREFIX;
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
		result.append(", missingOpenParenthesis: "); //$NON-NLS-1$
		result.append(missingOpenParenthesis);
		result.append(", missingCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingCloseParenthesis);
		result.append(", missingStartTagPrefixCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingStartTagPrefixCloseParenthesis);
		result.append(", missingEndTagPrefixCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingEndTagPrefixCloseParenthesis);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(", missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} // ErrorProtectedAreaImpl
