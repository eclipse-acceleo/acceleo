/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.qmodel.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.tests.qmodel.Expectation;
import org.eclipse.acceleo.query.tests.qmodel.ModelElement;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Query</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getStartingPoint <em>Starting Point</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getCurrentResults <em>Current Results
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getExpectations <em>Expectations</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getClassesToImport <em>Classes To Import
 * </em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getVariables <em>Variables</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl#getPluginsInClassPath <em>Plugins In Class
 * Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QueryImpl extends MinimalEObjectImpl.Container implements Query {
	/**
	 * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected String expression = EXPRESSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStartingPoint() <em>Starting Point</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartingPoint()
	 * @generated
	 * @ordered
	 */
	protected ModelElement startingPoint;

	/**
	 * The cached value of the '{@link #getCurrentResults() <em>Current Results</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCurrentResults()
	 * @generated
	 * @ordered
	 */
	protected EList<QueryEvaluationResult> currentResults;

	/**
	 * The cached value of the '{@link #getExpectations() <em>Expectations</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpectations()
	 * @generated
	 * @ordered
	 */
	protected EList<Expectation> expectations;

	/**
	 * The cached value of the '{@link #getClassesToImport() <em>Classes To Import</em>}' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getClassesToImport()
	 * @generated
	 * @ordered
	 */
	protected EList<String> classesToImport;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> variables;

	/**
	 * The cached value of the '{@link #getPluginsInClassPath() <em>Plugins In Class Path</em>}' attribute
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPluginsInClassPath()
	 * @generated
	 * @ordered
	 */
	protected EList<String> pluginsInClassPath;

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
		return QmodelPackage.Literals.QUERY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setExpression(String newExpression) {
		String oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QmodelPackage.QUERY__EXPRESSION,
					oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelElement getStartingPoint() {
		if (startingPoint != null && startingPoint.eIsProxy()) {
			InternalEObject oldStartingPoint = (InternalEObject)startingPoint;
			startingPoint = (ModelElement)eResolveProxy(oldStartingPoint);
			if (startingPoint != oldStartingPoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							QmodelPackage.QUERY__STARTING_POINT, oldStartingPoint, startingPoint));
			}
		}
		return startingPoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelElement basicGetStartingPoint() {
		return startingPoint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStartingPoint(ModelElement newStartingPoint) {
		ModelElement oldStartingPoint = startingPoint;
		startingPoint = newStartingPoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, QmodelPackage.QUERY__STARTING_POINT,
					oldStartingPoint, startingPoint));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<QueryEvaluationResult> getCurrentResults() {
		if (currentResults == null) {
			currentResults = new EObjectContainmentEList<QueryEvaluationResult>(QueryEvaluationResult.class,
					this, QmodelPackage.QUERY__CURRENT_RESULTS);
		}
		return currentResults;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expectation> getExpectations() {
		if (expectations == null) {
			expectations = new EObjectContainmentEList<Expectation>(Expectation.class, this,
					QmodelPackage.QUERY__EXPECTATIONS);
		}
		return expectations;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getClassesToImport() {
		if (classesToImport == null) {
			classesToImport = new EDataTypeUniqueEList<String>(String.class, this,
					QmodelPackage.QUERY__CLASSES_TO_IMPORT);
		}
		return classesToImport;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Variable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<Variable>(Variable.class, this,
					QmodelPackage.QUERY__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getPluginsInClassPath() {
		if (pluginsInClassPath == null) {
			pluginsInClassPath = new EDataTypeUniqueEList<String>(String.class, this,
					QmodelPackage.QUERY__PLUGINS_IN_CLASS_PATH);
		}
		return pluginsInClassPath;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case QmodelPackage.QUERY__CURRENT_RESULTS:
				return ((InternalEList<?>)getCurrentResults()).basicRemove(otherEnd, msgs);
			case QmodelPackage.QUERY__EXPECTATIONS:
				return ((InternalEList<?>)getExpectations()).basicRemove(otherEnd, msgs);
			case QmodelPackage.QUERY__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
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
			case QmodelPackage.QUERY__EXPRESSION:
				return getExpression();
			case QmodelPackage.QUERY__STARTING_POINT:
				if (resolve)
					return getStartingPoint();
				return basicGetStartingPoint();
			case QmodelPackage.QUERY__CURRENT_RESULTS:
				return getCurrentResults();
			case QmodelPackage.QUERY__EXPECTATIONS:
				return getExpectations();
			case QmodelPackage.QUERY__CLASSES_TO_IMPORT:
				return getClassesToImport();
			case QmodelPackage.QUERY__VARIABLES:
				return getVariables();
			case QmodelPackage.QUERY__PLUGINS_IN_CLASS_PATH:
				return getPluginsInClassPath();
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
			case QmodelPackage.QUERY__EXPRESSION:
				setExpression((String)newValue);
				return;
			case QmodelPackage.QUERY__STARTING_POINT:
				setStartingPoint((ModelElement)newValue);
				return;
			case QmodelPackage.QUERY__CURRENT_RESULTS:
				getCurrentResults().clear();
				getCurrentResults().addAll((Collection<? extends QueryEvaluationResult>)newValue);
				return;
			case QmodelPackage.QUERY__EXPECTATIONS:
				getExpectations().clear();
				getExpectations().addAll((Collection<? extends Expectation>)newValue);
				return;
			case QmodelPackage.QUERY__CLASSES_TO_IMPORT:
				getClassesToImport().clear();
				getClassesToImport().addAll((Collection<? extends String>)newValue);
				return;
			case QmodelPackage.QUERY__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends Variable>)newValue);
				return;
			case QmodelPackage.QUERY__PLUGINS_IN_CLASS_PATH:
				getPluginsInClassPath().clear();
				getPluginsInClassPath().addAll((Collection<? extends String>)newValue);
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
			case QmodelPackage.QUERY__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
				return;
			case QmodelPackage.QUERY__STARTING_POINT:
				setStartingPoint((ModelElement)null);
				return;
			case QmodelPackage.QUERY__CURRENT_RESULTS:
				getCurrentResults().clear();
				return;
			case QmodelPackage.QUERY__EXPECTATIONS:
				getExpectations().clear();
				return;
			case QmodelPackage.QUERY__CLASSES_TO_IMPORT:
				getClassesToImport().clear();
				return;
			case QmodelPackage.QUERY__VARIABLES:
				getVariables().clear();
				return;
			case QmodelPackage.QUERY__PLUGINS_IN_CLASS_PATH:
				getPluginsInClassPath().clear();
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
			case QmodelPackage.QUERY__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT
						.equals(expression);
			case QmodelPackage.QUERY__STARTING_POINT:
				return startingPoint != null;
			case QmodelPackage.QUERY__CURRENT_RESULTS:
				return currentResults != null && !currentResults.isEmpty();
			case QmodelPackage.QUERY__EXPECTATIONS:
				return expectations != null && !expectations.isEmpty();
			case QmodelPackage.QUERY__CLASSES_TO_IMPORT:
				return classesToImport != null && !classesToImport.isEmpty();
			case QmodelPackage.QUERY__VARIABLES:
				return variables != null && !variables.isEmpty();
			case QmodelPackage.QUERY__PLUGINS_IN_CLASS_PATH:
				return pluginsInClassPath != null && !pluginsInClassPath.isEmpty();
		}
		return super.eIsSet(featureID);
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
		result.append(" (expression: ");
		result.append(expression);
		result.append(", classesToImport: ");
		result.append(classesToImport);
		result.append(", pluginsInClassPath: ");
		result.append(pluginsInClassPath);
		result.append(')');
		return result.toString();
	}

} // QueryImpl
