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
import org.eclipse.acceleo.Expression;
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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Query</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#isDeprecated <em>Deprecated</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getType <em>Type</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getTypeAql <em>Type Aql</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getVisibility <em>Visibility</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.QueryImpl#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QueryImpl extends ModuleElementImpl implements Query {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected QueryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.QUERY;
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
							AcceleoPackage.QUERY__DOCUMENTATION, oldDocumentation, documentation));
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
					AcceleoPackage.QUERY__DOCUMENTATION, oldDocumentation, newDocumentation);
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__DOCUMENTATION,
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__DEPRECATED,
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__TYPE, oldType, type));
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
					AcceleoPackage.QUERY__TYPE_AQL, oldTypeAql, newTypeAql);
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
						- AcceleoPackage.QUERY__TYPE_AQL, null, msgs);
			if (newTypeAql != null)
				msgs = ((InternalEObject)newTypeAql).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.QUERY__TYPE_AQL, null, msgs);
			msgs = basicSetTypeAql(newTypeAql, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__TYPE_AQL, newTypeAql,
					newTypeAql));
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
					AcceleoPackage.QUERY__PARAMETERS);
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
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__VISIBILITY,
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
					AcceleoPackage.QUERY__BODY, oldBody, newBody);
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
						- AcceleoPackage.QUERY__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.QUERY__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.QUERY__BODY, newBody,
					newBody));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.QUERY__DOCUMENTATION:
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
			case AcceleoPackage.QUERY__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case AcceleoPackage.QUERY__TYPE_AQL:
				return basicSetTypeAql(null, msgs);
			case AcceleoPackage.QUERY__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.QUERY__BODY:
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
			case AcceleoPackage.QUERY__DOCUMENTATION:
				if (resolve)
					return getDocumentation();
				return basicGetDocumentation();
			case AcceleoPackage.QUERY__DEPRECATED:
				return isDeprecated();
			case AcceleoPackage.QUERY__NAME:
				return getName();
			case AcceleoPackage.QUERY__TYPE:
				return getType();
			case AcceleoPackage.QUERY__TYPE_AQL:
				return getTypeAql();
			case AcceleoPackage.QUERY__PARAMETERS:
				return getParameters();
			case AcceleoPackage.QUERY__VISIBILITY:
				return getVisibility();
			case AcceleoPackage.QUERY__BODY:
				return getBody();
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
			case AcceleoPackage.QUERY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case AcceleoPackage.QUERY__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case AcceleoPackage.QUERY__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.QUERY__TYPE:
				setType((AstResult)newValue);
				return;
			case AcceleoPackage.QUERY__TYPE_AQL:
				setTypeAql((org.eclipse.acceleo.query.ast.Expression)newValue);
				return;
			case AcceleoPackage.QUERY__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Variable>)newValue);
				return;
			case AcceleoPackage.QUERY__VISIBILITY:
				setVisibility((VisibilityKind)newValue);
				return;
			case AcceleoPackage.QUERY__BODY:
				setBody((Expression)newValue);
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
			case AcceleoPackage.QUERY__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case AcceleoPackage.QUERY__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case AcceleoPackage.QUERY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.QUERY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case AcceleoPackage.QUERY__TYPE_AQL:
				setTypeAql((org.eclipse.acceleo.query.ast.Expression)null);
				return;
			case AcceleoPackage.QUERY__PARAMETERS:
				getParameters().clear();
				return;
			case AcceleoPackage.QUERY__VISIBILITY:
				setVisibility(VISIBILITY_EDEFAULT);
				return;
			case AcceleoPackage.QUERY__BODY:
				setBody((Expression)null);
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
			case AcceleoPackage.QUERY__DOCUMENTATION:
				return documentation != null;
			case AcceleoPackage.QUERY__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case AcceleoPackage.QUERY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.QUERY__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case AcceleoPackage.QUERY__TYPE_AQL:
				return typeAql != null;
			case AcceleoPackage.QUERY__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case AcceleoPackage.QUERY__VISIBILITY:
				return visibility != VISIBILITY_EDEFAULT;
			case AcceleoPackage.QUERY__BODY:
				return body != null;
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
		if (baseClass == DocumentedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.QUERY__DOCUMENTATION:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case AcceleoPackage.QUERY__DEPRECATED:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.QUERY__NAME:
					return AcceleoPackage.NAMED_ELEMENT__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.QUERY__TYPE:
					return AcceleoPackage.TYPED_ELEMENT__TYPE;
				case AcceleoPackage.QUERY__TYPE_AQL:
					return AcceleoPackage.TYPED_ELEMENT__TYPE_AQL;
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
		if (baseClass == DocumentedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return AcceleoPackage.QUERY__DOCUMENTATION;
				case AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return AcceleoPackage.QUERY__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.QUERY__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == TypedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.TYPED_ELEMENT__TYPE:
					return AcceleoPackage.QUERY__TYPE;
				case AcceleoPackage.TYPED_ELEMENT__TYPE_AQL:
					return AcceleoPackage.QUERY__TYPE_AQL;
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
		result.append(')');
		return result.toString();
	}

} // QueryImpl
