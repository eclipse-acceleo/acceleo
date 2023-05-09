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

import java.util.Collection;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ErrorTemplate;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
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
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Template</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#isDeprecated <em>Deprecated</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getGuard <em>Guard</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getPost <em>Post</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#isMain <em>Main</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingVisibility <em>Missing
 * Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingParameters <em>Missing
 * Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingGuardOpenParenthesis <em>Missing Guard Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingGuardCloseParenthesis <em>Missing Guard
 * Close Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingPostCloseParenthesis <em>Missing Post Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingEndHeader <em>Missing End Header</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorTemplateImpl#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorTemplateImpl extends MinimalEObjectImpl.Container implements ErrorTemplate {
	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected Documentation documentation;

	/**
	 * The default value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPRECATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected boolean deprecated = DEPRECATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> parameters;

	/**
	 * The cached value of the '{@link #getGuard() <em>Guard</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getGuard()
	 * @generated
	 * @ordered
	 */
	protected Expression guard;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected Expression post;

	/**
	 * The default value of the '{@link #isMain() <em>Main</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isMain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMain() <em>Main</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #isMain()
	 * @generated
	 * @ordered
	 */
	protected boolean main = MAIN_EDEFAULT;

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
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Block body;

	/**
	 * The default value of the '{@link #getMissingVisibility() <em>Missing Visibility</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingVisibility()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_VISIBILITY_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingVisibility() <em>Missing Visibility</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingVisibility()
	 * @generated
	 * @ordered
	 */
	protected int missingVisibility = MISSING_VISIBILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingName() <em>Missing Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingName()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_NAME_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingName() <em>Missing Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingName()
	 * @generated
	 * @ordered
	 */
	protected int missingName = MISSING_NAME_EDEFAULT;

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
	 * The default value of the '{@link #getMissingParameters() <em>Missing Parameters</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingParameters()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_PARAMETERS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingParameters() <em>Missing Parameters</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingParameters()
	 * @generated
	 * @ordered
	 */
	protected int missingParameters = MISSING_PARAMETERS_EDEFAULT;

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
	 * The default value of the '{@link #getMissingGuardOpenParenthesis() <em>Missing Guard Open
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingGuardOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_GUARD_OPEN_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingGuardOpenParenthesis() <em>Missing Guard Open
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingGuardOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingGuardOpenParenthesis = MISSING_GUARD_OPEN_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingGuardCloseParenthesis() <em>Missing Guard Close
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingGuardCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_GUARD_CLOSE_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingGuardCloseParenthesis() <em>Missing Guard Close
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingGuardCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingGuardCloseParenthesis = MISSING_GUARD_CLOSE_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingPostCloseParenthesis() <em>Missing Post Close
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingPostCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_POST_CLOSE_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingPostCloseParenthesis() <em>Missing Post Close
	 * Parenthesis</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingPostCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingPostCloseParenthesis = MISSING_POST_CLOSE_PARENTHESIS_EDEFAULT;

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
	protected ErrorTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Documentation getDocumentation() {
		if (documentation != null && documentation.eIsProxy()) {
			InternalEObject oldDocumentation = (InternalEObject)documentation;
			documentation = (Documentation)eResolveProxy(oldDocumentation);
			if (documentation != oldDocumentation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION, oldDocumentation, documentation));
			}
		}
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Documentation basicGetDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
		Documentation oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION, oldDocumentation, newDocumentation);
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
	public void setDocumentation(Documentation newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this,
						AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this,
						AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION, newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDeprecated() {
		return deprecated;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDeprecated(boolean newDeprecated) {
		boolean oldDeprecated = deprecated;
		deprecated = newDeprecated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__DEPRECATED,
					oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Variable> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Variable>(Variable.class, this,
					AcceleoPackage.ERROR_TEMPLATE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getGuard() {
		return guard;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetGuard(Expression newGuard, NotificationChain msgs) {
		Expression oldGuard = guard;
		guard = newGuard;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__GUARD, oldGuard, newGuard);
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
	public void setGuard(Expression newGuard) {
		if (newGuard != guard) {
			NotificationChain msgs = null;
			if (guard != null)
				msgs = ((InternalEObject)guard).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_TEMPLATE__GUARD, null, msgs);
			if (newGuard != null)
				msgs = ((InternalEObject)newGuard).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_TEMPLATE__GUARD, null, msgs);
			msgs = basicSetGuard(newGuard, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__GUARD,
					newGuard, newGuard));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPost(Expression newPost, NotificationChain msgs) {
		Expression oldPost = post;
		post = newPost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__POST, oldPost, newPost);
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
	public void setPost(Expression newPost) {
		if (newPost != post) {
			NotificationChain msgs = null;
			if (post != null)
				msgs = ((InternalEObject)post).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_TEMPLATE__POST, null, msgs);
			if (newPost != null)
				msgs = ((InternalEObject)newPost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_TEMPLATE__POST, null, msgs);
			msgs = basicSetPost(newPost, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__POST,
					newPost, newPost));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isMain() {
		return main;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMain(boolean newMain) {
		boolean oldMain = main;
		main = newMain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__MAIN,
					oldMain, main));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VisibilityKind getVisibility() {
		return visibility;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVisibility(VisibilityKind newVisibility) {
		VisibilityKind oldVisibility = visibility;
		visibility = newVisibility == null ? VISIBILITY_EDEFAULT : newVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__VISIBILITY,
					oldVisibility, visibility));
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
					AcceleoPackage.ERROR_TEMPLATE__BODY, oldBody, newBody);
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
						- AcceleoPackage.ERROR_TEMPLATE__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_TEMPLATE__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__BODY,
					newBody, newBody));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingVisibility() {
		return missingVisibility;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingVisibility(int newMissingVisibility) {
		int oldMissingVisibility = missingVisibility;
		missingVisibility = newMissingVisibility;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__MISSING_VISIBILITY, oldMissingVisibility,
					missingVisibility));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingName() {
		return missingName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingName(int newMissingName) {
		int oldMissingName = missingName;
		missingName = newMissingName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__MISSING_NAME,
					oldMissingName, missingName));
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
					AcceleoPackage.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
					missingOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingParameters() {
		return missingParameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingParameters(int newMissingParameters) {
		int oldMissingParameters = missingParameters;
		missingParameters = newMissingParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__MISSING_PARAMETERS, oldMissingParameters,
					missingParameters));
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
					AcceleoPackage.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS, oldMissingCloseParenthesis,
					missingCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingGuardOpenParenthesis() {
		return missingGuardOpenParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingGuardOpenParenthesis(int newMissingGuardOpenParenthesis) {
		int oldMissingGuardOpenParenthesis = missingGuardOpenParenthesis;
		missingGuardOpenParenthesis = newMissingGuardOpenParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS,
					oldMissingGuardOpenParenthesis, missingGuardOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingGuardCloseParenthesis() {
		return missingGuardCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingGuardCloseParenthesis(int newMissingGuardCloseParenthesis) {
		int oldMissingGuardCloseParenthesis = missingGuardCloseParenthesis;
		missingGuardCloseParenthesis = newMissingGuardCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS,
					oldMissingGuardCloseParenthesis, missingGuardCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingPostCloseParenthesis() {
		return missingPostCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingPostCloseParenthesis(int newMissingPostCloseParenthesis) {
		int oldMissingPostCloseParenthesis = missingPostCloseParenthesis;
		missingPostCloseParenthesis = newMissingPostCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS,
					oldMissingPostCloseParenthesis, missingPostCloseParenthesis));
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
					AcceleoPackage.ERROR_TEMPLATE__MISSING_END_HEADER, oldMissingEndHeader,
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_TEMPLATE__MISSING_END,
					oldMissingEnd, missingEnd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this,
							AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
				return basicSetDocumentation((Documentation)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.ERROR_TEMPLATE__GUARD:
				return basicSetGuard(null, msgs);
			case AcceleoPackage.ERROR_TEMPLATE__POST:
				return basicSetPost(null, msgs);
			case AcceleoPackage.ERROR_TEMPLATE__BODY:
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
			case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
				if (resolve)
					return getDocumentation();
				return basicGetDocumentation();
			case AcceleoPackage.ERROR_TEMPLATE__DEPRECATED:
				return isDeprecated();
			case AcceleoPackage.ERROR_TEMPLATE__NAME:
				return getName();
			case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
				return getParameters();
			case AcceleoPackage.ERROR_TEMPLATE__GUARD:
				return getGuard();
			case AcceleoPackage.ERROR_TEMPLATE__POST:
				return getPost();
			case AcceleoPackage.ERROR_TEMPLATE__MAIN:
				return isMain();
			case AcceleoPackage.ERROR_TEMPLATE__VISIBILITY:
				return getVisibility();
			case AcceleoPackage.ERROR_TEMPLATE__BODY:
				return getBody();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_VISIBILITY:
				return getMissingVisibility();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_NAME:
				return getMissingName();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS:
				return getMissingOpenParenthesis();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_PARAMETERS:
				return getMissingParameters();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS:
				return getMissingCloseParenthesis();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS:
				return getMissingGuardOpenParenthesis();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS:
				return getMissingGuardCloseParenthesis();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS:
				return getMissingPostCloseParenthesis();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END_HEADER:
				return getMissingEndHeader();
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END:
				return getMissingEnd();
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
			case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Variable>)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__GUARD:
				setGuard((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__POST:
				setPost((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MAIN:
				setMain((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__BODY:
				setBody((Block)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_VISIBILITY:
				setMissingVisibility((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_NAME:
				setMissingName((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_PARAMETERS:
				setMissingParameters((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS:
				setMissingGuardOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS:
				setMissingGuardCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS:
				setMissingPostCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END:
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
			case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
				getParameters().clear();
				return;
			case AcceleoPackage.ERROR_TEMPLATE__GUARD:
				setGuard((Expression)null);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__POST:
				setPost((Expression)null);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MAIN:
				setMain(MAIN_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__BODY:
				setBody((Block)null);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_VISIBILITY:
				setMissingVisibility(MISSING_VISIBILITY_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_NAME:
				setMissingName(MISSING_NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_PARAMETERS:
				setMissingParameters(MISSING_PARAMETERS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS:
				setMissingGuardOpenParenthesis(MISSING_GUARD_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS:
				setMissingGuardCloseParenthesis(MISSING_GUARD_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS:
				setMissingPostCloseParenthesis(MISSING_POST_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END:
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
			case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
				return documentation != null;
			case AcceleoPackage.ERROR_TEMPLATE__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case AcceleoPackage.ERROR_TEMPLATE__GUARD:
				return guard != null;
			case AcceleoPackage.ERROR_TEMPLATE__POST:
				return post != null;
			case AcceleoPackage.ERROR_TEMPLATE__MAIN:
				return main != MAIN_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__BODY:
				return body != null;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_VISIBILITY:
				return missingVisibility != MISSING_VISIBILITY_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_NAME:
				return missingName != MISSING_NAME_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_PARAMETERS:
				return missingParameters != MISSING_PARAMETERS_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS:
				return missingGuardOpenParenthesis != MISSING_GUARD_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS:
				return missingGuardCloseParenthesis != MISSING_GUARD_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS:
				return missingPostCloseParenthesis != MISSING_POST_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
			case AcceleoPackage.ERROR_TEMPLATE__MISSING_END:
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
		if (baseClass == ModuleElement.class) {
			switch (derivedFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case AcceleoPackage.ERROR_TEMPLATE__DEPRECATED:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_TEMPLATE__NAME:
					return AcceleoPackage.NAMED_ELEMENT__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == Template.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
					return AcceleoPackage.TEMPLATE__PARAMETERS;
				case AcceleoPackage.ERROR_TEMPLATE__GUARD:
					return AcceleoPackage.TEMPLATE__GUARD;
				case AcceleoPackage.ERROR_TEMPLATE__POST:
					return AcceleoPackage.TEMPLATE__POST;
				case AcceleoPackage.ERROR_TEMPLATE__MAIN:
					return AcceleoPackage.TEMPLATE__MAIN;
				case AcceleoPackage.ERROR_TEMPLATE__VISIBILITY:
					return AcceleoPackage.TEMPLATE__VISIBILITY;
				case AcceleoPackage.ERROR_TEMPLATE__BODY:
					return AcceleoPackage.TEMPLATE__BODY;
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
		if (baseClass == ModuleElement.class) {
			switch (baseFeatureID) {
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION;
				case AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return AcceleoPackage.ERROR_TEMPLATE__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.ERROR_TEMPLATE__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == Template.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.TEMPLATE__PARAMETERS:
					return AcceleoPackage.ERROR_TEMPLATE__PARAMETERS;
				case AcceleoPackage.TEMPLATE__GUARD:
					return AcceleoPackage.ERROR_TEMPLATE__GUARD;
				case AcceleoPackage.TEMPLATE__POST:
					return AcceleoPackage.ERROR_TEMPLATE__POST;
				case AcceleoPackage.TEMPLATE__MAIN:
					return AcceleoPackage.ERROR_TEMPLATE__MAIN;
				case AcceleoPackage.TEMPLATE__VISIBILITY:
					return AcceleoPackage.ERROR_TEMPLATE__VISIBILITY;
				case AcceleoPackage.TEMPLATE__BODY:
					return AcceleoPackage.ERROR_TEMPLATE__BODY;
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
		result.append(" (deprecated: "); //$NON-NLS-1$
		result.append(deprecated);
		result.append(", name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", main: "); //$NON-NLS-1$
		result.append(main);
		result.append(", visibility: "); //$NON-NLS-1$
		result.append(visibility);
		result.append(", missingVisibility: "); //$NON-NLS-1$
		result.append(missingVisibility);
		result.append(", missingName: "); //$NON-NLS-1$
		result.append(missingName);
		result.append(", missingOpenParenthesis: "); //$NON-NLS-1$
		result.append(missingOpenParenthesis);
		result.append(", missingParameters: "); //$NON-NLS-1$
		result.append(missingParameters);
		result.append(", missingCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingCloseParenthesis);
		result.append(", missingGuardOpenParenthesis: "); //$NON-NLS-1$
		result.append(missingGuardOpenParenthesis);
		result.append(", missingGuardCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingGuardCloseParenthesis);
		result.append(", missingPostCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingPostCloseParenthesis);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(", missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} // ErrorTemplateImpl
