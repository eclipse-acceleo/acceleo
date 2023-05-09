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
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.query.parser.AstResult;
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
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Query</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#isDeprecated <em>Deprecated</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getTypeAql <em>Type Aql</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingVisibility <em>Missing Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingName <em>Missing Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingParameters <em>Missing Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingColon <em>Missing Colon</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingType <em>Missing Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingEqual <em>Missing Equal</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorQueryImpl#getMissingEnd <em>Missing End</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorQueryImpl extends MinimalEObjectImpl.Container implements ErrorQuery {
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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final AstResult TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AstResult type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTypeAql() <em>Type Aql</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTypeAql()
	 * @generated
	 * @ordered
	 */
	protected org.eclipse.acceleo.query.ast.Expression typeAql;

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
	protected Expression body;

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
	 * The default value of the '{@link #getMissingColon() <em>Missing Colon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingColon()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_COLON_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingColon() <em>Missing Colon</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingColon()
	 * @generated
	 * @ordered
	 */
	protected int missingColon = MISSING_COLON_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingType() <em>Missing Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingType()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_TYPE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingType() <em>Missing Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingType()
	 * @generated
	 * @ordered
	 */
	protected int missingType = MISSING_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEqual() <em>Missing Equal</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEqual()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_EQUAL_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEqual() <em>Missing Equal</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEqual()
	 * @generated
	 * @ordered
	 */
	protected int missingEqual = MISSING_EQUAL_EDEFAULT;

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
	protected ErrorQueryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_QUERY;
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
							AcceleoPackage.ERROR_QUERY__DOCUMENTATION, oldDocumentation, documentation));
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
					AcceleoPackage.ERROR_QUERY__DOCUMENTATION, oldDocumentation, newDocumentation);
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__DOCUMENTATION,
					newDocumentation, newDocumentation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__DEPRECATED,
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AstResult getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setType(AstResult newType) {
		AstResult oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__TYPE, oldType,
					type));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public org.eclipse.acceleo.query.ast.Expression getTypeAql() {
		return typeAql;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetTypeAql(org.eclipse.acceleo.query.ast.Expression newTypeAql,
			NotificationChain msgs) {
		org.eclipse.acceleo.query.ast.Expression oldTypeAql = typeAql;
		typeAql = newTypeAql;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_QUERY__TYPE_AQL, oldTypeAql, newTypeAql);
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
	public void setTypeAql(org.eclipse.acceleo.query.ast.Expression newTypeAql) {
		if (newTypeAql != typeAql) {
			NotificationChain msgs = null;
			if (typeAql != null)
				msgs = ((InternalEObject)typeAql).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_QUERY__TYPE_AQL, null, msgs);
			if (newTypeAql != null)
				msgs = ((InternalEObject)newTypeAql).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_QUERY__TYPE_AQL, null, msgs);
			msgs = basicSetTypeAql(newTypeAql, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__TYPE_AQL,
					newTypeAql, newTypeAql));
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
					AcceleoPackage.ERROR_QUERY__PARAMETERS);
		}
		return parameters;
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__VISIBILITY,
					oldVisibility, visibility));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBody(Expression newBody, NotificationChain msgs) {
		Expression oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_QUERY__BODY, oldBody, newBody);
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
	public void setBody(Expression newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_QUERY__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_QUERY__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__BODY, newBody,
					newBody));
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
					AcceleoPackage.ERROR_QUERY__MISSING_VISIBILITY, oldMissingVisibility, missingVisibility));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__MISSING_NAME,
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
					AcceleoPackage.ERROR_QUERY__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
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
					AcceleoPackage.ERROR_QUERY__MISSING_PARAMETERS, oldMissingParameters, missingParameters));
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
					AcceleoPackage.ERROR_QUERY__MISSING_CLOSE_PARENTHESIS, oldMissingCloseParenthesis,
					missingCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingColon() {
		return missingColon;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingColon(int newMissingColon) {
		int oldMissingColon = missingColon;
		missingColon = newMissingColon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__MISSING_COLON,
					oldMissingColon, missingColon));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingType() {
		return missingType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingType(int newMissingType) {
		int oldMissingType = missingType;
		missingType = newMissingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__MISSING_TYPE,
					oldMissingType, missingType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEqual() {
		return missingEqual;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEqual(int newMissingEqual) {
		int oldMissingEqual = missingEqual;
		missingEqual = newMissingEqual;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__MISSING_EQUAL,
					oldMissingEqual, missingEqual));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_QUERY__MISSING_END,
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
			case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
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
			case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case AcceleoPackage.ERROR_QUERY__TYPE_AQL:
				return basicSetTypeAql(null, msgs);
			case AcceleoPackage.ERROR_QUERY__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.ERROR_QUERY__BODY:
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
			case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
				if (resolve)
					return getDocumentation();
				return basicGetDocumentation();
			case AcceleoPackage.ERROR_QUERY__DEPRECATED:
				return isDeprecated();
			case AcceleoPackage.ERROR_QUERY__NAME:
				return getName();
			case AcceleoPackage.ERROR_QUERY__TYPE:
				return getType();
			case AcceleoPackage.ERROR_QUERY__TYPE_AQL:
				return getTypeAql();
			case AcceleoPackage.ERROR_QUERY__PARAMETERS:
				return getParameters();
			case AcceleoPackage.ERROR_QUERY__VISIBILITY:
				return getVisibility();
			case AcceleoPackage.ERROR_QUERY__BODY:
				return getBody();
			case AcceleoPackage.ERROR_QUERY__MISSING_VISIBILITY:
				return getMissingVisibility();
			case AcceleoPackage.ERROR_QUERY__MISSING_NAME:
				return getMissingName();
			case AcceleoPackage.ERROR_QUERY__MISSING_OPEN_PARENTHESIS:
				return getMissingOpenParenthesis();
			case AcceleoPackage.ERROR_QUERY__MISSING_PARAMETERS:
				return getMissingParameters();
			case AcceleoPackage.ERROR_QUERY__MISSING_CLOSE_PARENTHESIS:
				return getMissingCloseParenthesis();
			case AcceleoPackage.ERROR_QUERY__MISSING_COLON:
				return getMissingColon();
			case AcceleoPackage.ERROR_QUERY__MISSING_TYPE:
				return getMissingType();
			case AcceleoPackage.ERROR_QUERY__MISSING_EQUAL:
				return getMissingEqual();
			case AcceleoPackage.ERROR_QUERY__MISSING_END:
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
			case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__TYPE:
				setType((AstResult)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__TYPE_AQL:
				setTypeAql((org.eclipse.acceleo.query.ast.Expression)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Variable>)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__BODY:
				setBody((Expression)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_VISIBILITY:
				setMissingVisibility((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_NAME:
				setMissingName((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_PARAMETERS:
				setMissingParameters((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_COLON:
				setMissingColon((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_TYPE:
				setMissingType((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_EQUAL:
				setMissingEqual((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_END:
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
			case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case AcceleoPackage.ERROR_QUERY__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__TYPE_AQL:
				setTypeAql((org.eclipse.acceleo.query.ast.Expression)null);
				return;
			case AcceleoPackage.ERROR_QUERY__PARAMETERS:
				getParameters().clear();
				return;
			case AcceleoPackage.ERROR_QUERY__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__BODY:
				setBody((Expression)null);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_VISIBILITY:
				setMissingVisibility(MISSING_VISIBILITY_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_NAME:
				setMissingName(MISSING_NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_PARAMETERS:
				setMissingParameters(MISSING_PARAMETERS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_COLON:
				setMissingColon(MISSING_COLON_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_TYPE:
				setMissingType(MISSING_TYPE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_EQUAL:
				setMissingEqual(MISSING_EQUAL_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_QUERY__MISSING_END:
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
			case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
				return documentation != null;
			case AcceleoPackage.ERROR_QUERY__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.ERROR_QUERY__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case AcceleoPackage.ERROR_QUERY__TYPE_AQL:
				return typeAql != null;
			case AcceleoPackage.ERROR_QUERY__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case AcceleoPackage.ERROR_QUERY__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__BODY:
				return body != null;
			case AcceleoPackage.ERROR_QUERY__MISSING_VISIBILITY:
				return missingVisibility != MISSING_VISIBILITY_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_NAME:
				return missingName != MISSING_NAME_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_PARAMETERS:
				return missingParameters != MISSING_PARAMETERS_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_COLON:
				return missingColon != MISSING_COLON_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_TYPE:
				return missingType != MISSING_TYPE_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_EQUAL:
				return missingEqual != MISSING_EQUAL_EDEFAULT;
			case AcceleoPackage.ERROR_QUERY__MISSING_END:
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
				case AcceleoPackage.ERROR_QUERY__DOCUMENTATION:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case AcceleoPackage.ERROR_QUERY__DEPRECATED:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_QUERY__NAME:
					return AcceleoPackage.NAMED_ELEMENT__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_QUERY__TYPE:
					return AcceleoPackage.TYPED_ELEMENT__TYPE;
				case AcceleoPackage.ERROR_QUERY__TYPE_AQL:
					return AcceleoPackage.TYPED_ELEMENT__TYPE_AQL;
				default:
					return -1;
			}
		}
		if (baseClass == Query.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_QUERY__PARAMETERS:
					return AcceleoPackage.QUERY__PARAMETERS;
				case AcceleoPackage.ERROR_QUERY__VISIBILITY:
					return AcceleoPackage.QUERY__VISIBILITY;
				case AcceleoPackage.ERROR_QUERY__BODY:
					return AcceleoPackage.QUERY__BODY;
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
					return AcceleoPackage.ERROR_QUERY__DOCUMENTATION;
				case AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return AcceleoPackage.ERROR_QUERY__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.ERROR_QUERY__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.TYPED_ELEMENT__TYPE:
					return AcceleoPackage.ERROR_QUERY__TYPE;
				case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
					return AcceleoPackage.ERROR_QUERY__TYPE_AQL;
				default:
					return -1;
			}
		}
		if (baseClass == Query.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.QUERY__PARAMETERS:
					return AcceleoPackage.ERROR_QUERY__PARAMETERS;
				case AcceleoPackage.QUERY__VISIBILITY:
					return AcceleoPackage.ERROR_QUERY__VISIBILITY;
				case AcceleoPackage.QUERY__BODY:
					return AcceleoPackage.ERROR_QUERY__BODY;
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
		result.append(", type: "); //$NON-NLS-1$
		result.append(type);
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
		result.append(", missingColon: "); //$NON-NLS-1$
		result.append(missingColon);
		result.append(", missingType: "); //$NON-NLS-1$
		result.append(missingType);
		result.append(", missingEqual: "); //$NON-NLS-1$
		result.append(missingEqual);
		result.append(", missingEnd: "); //$NON-NLS-1$
		result.append(missingEnd);
		result.append(')');
		return result.toString();
	}

} // ErrorQueryImpl
