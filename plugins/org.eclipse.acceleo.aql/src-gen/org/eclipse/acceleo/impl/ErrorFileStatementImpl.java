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
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.Statement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error File Statement</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMode <em>Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getUrl <em>Url</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getCharset <em>Charset</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMissingComma <em>Missing Comma</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMissingOpenMode <em>Missing Open
 * Mode</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMissingEndHeader <em>Missing End
 * Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorFileStatementImpl#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorFileStatementImpl extends MinimalEObjectImpl.Container implements ErrorFileStatement {
	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final OpenModeKind MODE_EDEFAULT = OpenModeKind.OVERWRITE;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected OpenModeKind mode = MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected Expression url;

	/**
	 * The cached value of the '{@link #getCharset() <em>Charset</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCharset()
	 * @generated
	 * @ordered
	 */
	protected Expression charset;

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
	 * The default value of the '{@link #getMissingComma() <em>Missing Comma</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingComma()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_COMMA_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingComma() <em>Missing Comma</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingComma()
	 * @generated
	 * @ordered
	 */
	protected int missingComma = MISSING_COMMA_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingOpenMode() <em>Missing Open Mode</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingOpenMode()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_OPEN_MODE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingOpenMode() <em>Missing Open Mode</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingOpenMode()
	 * @generated
	 * @ordered
	 */
	protected int missingOpenMode = MISSING_OPEN_MODE_EDEFAULT;

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
	protected ErrorFileStatementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_FILE_STATEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public OpenModeKind getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMode(OpenModeKind newMode) {
		OpenModeKind oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_FILE_STATEMENT__MODE,
					oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetUrl(Expression newUrl, NotificationChain msgs) {
		Expression oldUrl = url;
		url = newUrl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_FILE_STATEMENT__URL, oldUrl, newUrl);
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
	public void setUrl(Expression newUrl) {
		if (newUrl != url) {
			NotificationChain msgs = null;
			if (url != null)
				msgs = ((InternalEObject)url).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_FILE_STATEMENT__URL, null, msgs);
			if (newUrl != null)
				msgs = ((InternalEObject)newUrl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_FILE_STATEMENT__URL, null, msgs);
			msgs = basicSetUrl(newUrl, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_FILE_STATEMENT__URL,
					newUrl, newUrl));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getCharset() {
		return charset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCharset(Expression newCharset, NotificationChain msgs) {
		Expression oldCharset = charset;
		charset = newCharset;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET, oldCharset, newCharset);
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
	public void setCharset(Expression newCharset) {
		if (newCharset != charset) {
			NotificationChain msgs = null;
			if (charset != null)
				msgs = ((InternalEObject)charset).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET, null, msgs);
			if (newCharset != null)
				msgs = ((InternalEObject)newCharset).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET, null, msgs);
			msgs = basicSetCharset(newCharset, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET, newCharset, newCharset));
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
					AcceleoPackage.ERROR_FILE_STATEMENT__BODY, oldBody, newBody);
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
						- AcceleoPackage.ERROR_FILE_STATEMENT__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_FILE_STATEMENT__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_FILE_STATEMENT__BODY,
					newBody, newBody));
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
					AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
					missingOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingComma() {
		return missingComma;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingComma(int newMissingComma) {
		int oldMissingComma = missingComma;
		missingComma = newMissingComma;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_COMMA, oldMissingComma, missingComma));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingOpenMode() {
		return missingOpenMode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingOpenMode(int newMissingOpenMode) {
		int oldMissingOpenMode = missingOpenMode;
		missingOpenMode = newMissingOpenMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE, oldMissingOpenMode,
					missingOpenMode));
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
					AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS,
					oldMissingCloseParenthesis, missingCloseParenthesis));
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
					AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END_HEADER, oldMissingEndHeader,
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
					AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END, oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
				return basicSetUrl(null, msgs);
			case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
				return basicSetCharset(null, msgs);
			case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
				return basicSetBody(null, msgs);
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
			case AcceleoPackage.ERROR_FILE_STATEMENT__MODE:
				return getMode();
			case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
				return getUrl();
			case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
				return getCharset();
			case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
				return getBody();
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS:
				return getMissingOpenParenthesis();
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_COMMA:
				return getMissingComma();
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE:
				return getMissingOpenMode();
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				return getMissingCloseParenthesis();
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END_HEADER:
				return getMissingEndHeader();
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END:
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
			case AcceleoPackage.ERROR_FILE_STATEMENT__MODE:
				setMode((OpenModeKind)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
				setUrl((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
				setCharset((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
				setBody((Block)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_COMMA:
				setMissingComma((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE:
				setMissingOpenMode((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END:
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
			case AcceleoPackage.ERROR_FILE_STATEMENT__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
				setUrl((Expression)null);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
				setCharset((Expression)null);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
				setBody((Block)null);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_COMMA:
				setMissingComma(MISSING_COMMA_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE:
				setMissingOpenMode(MISSING_OPEN_MODE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END:
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
			case AcceleoPackage.ERROR_FILE_STATEMENT__MODE:
				return mode != MODE_EDEFAULT;
			case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
				return url != null;
			case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
				return charset != null;
			case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
				return body != null;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_COMMA:
				return missingComma != MISSING_COMMA_EDEFAULT;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE:
				return missingOpenMode != MISSING_OPEN_MODE_EDEFAULT;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
			case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END:
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
		if (baseClass == FileStatement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_FILE_STATEMENT__MODE:
					return AcceleoPackage.FILE_STATEMENT__MODE;
				case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
					return AcceleoPackage.FILE_STATEMENT__URL;
				case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
					return AcceleoPackage.FILE_STATEMENT__CHARSET;
				case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
					return AcceleoPackage.FILE_STATEMENT__BODY;
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
		if (baseClass == FileStatement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.FILE_STATEMENT__MODE:
					return AcceleoPackage.ERROR_FILE_STATEMENT__MODE;
				case AcceleoPackage.FILE_STATEMENT__URL:
					return AcceleoPackage.ERROR_FILE_STATEMENT__URL;
				case AcceleoPackage.FILE_STATEMENT__CHARSET:
					return AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET;
				case AcceleoPackage.FILE_STATEMENT__BODY:
					return AcceleoPackage.ERROR_FILE_STATEMENT__BODY;
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
		result.append(" (mode: "); //$NON-NLS-1$
		result.append(mode);
		result.append(", missingOpenParenthesis: "); //$NON-NLS-1$
		result.append(missingOpenParenthesis);
		result.append(", missingComma: "); //$NON-NLS-1$
		result.append(missingComma);
		result.append(", missingOpenMode: "); //$NON-NLS-1$
		result.append(missingOpenMode);
		result.append(", missingCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingCloseParenthesis);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(", missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} // ErrorFileStatementImpl
