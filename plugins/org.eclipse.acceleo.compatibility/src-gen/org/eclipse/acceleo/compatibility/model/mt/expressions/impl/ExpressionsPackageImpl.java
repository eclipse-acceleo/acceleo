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
package org.eclipse.acceleo.compatibility.model.mt.expressions.impl;

import org.eclipse.acceleo.compatibility.model.mt.MtPackage;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Literal;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Not;
import org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage;
import org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ExpressionsPackageImpl extends EPackageImpl implements ExpressionsPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass callSetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass callEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass notEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass operatorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass parenthesisEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass literalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass integerLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass doubleLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass booleanLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass nullLiteralEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ExpressionsPackageImpl() {
		super(eNS_URI, ExpressionsFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link ExpressionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the
	 * package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ExpressionsPackage init() {
		if (isInited)
			return (ExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI);

		// Obtain or create and register package
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl)(EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ExpressionsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new ExpressionsPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		MtPackageImpl theMtPackage = (MtPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(MtPackage.eNS_URI) instanceof MtPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(MtPackage.eNS_URI) : MtPackage.eINSTANCE);
		CorePackageImpl theCorePackage = (CorePackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(CorePackage.eNS_URI) instanceof CorePackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(CorePackage.eNS_URI) : CorePackage.eINSTANCE);
		StatementsPackageImpl theStatementsPackage = (StatementsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI) instanceof StatementsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI)
				: StatementsPackage.eINSTANCE);

		// Create package meta-data objects
		theExpressionsPackage.createPackageContents();
		theMtPackage.createPackageContents();
		theCorePackage.createPackageContents();
		theStatementsPackage.createPackageContents();

		// Initialize created meta-data
		theExpressionsPackage.initializePackageContents();
		theMtPackage.initializePackageContents();
		theCorePackage.initializePackageContents();
		theStatementsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theExpressionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ExpressionsPackage.eNS_URI, theExpressionsPackage);
		return theExpressionsPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCallSet() {
		return callSetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCallSet_Calls() {
		return (EReference)callSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCall() {
		return callEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCall_Name() {
		return (EAttribute)callEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCall_Prefix() {
		return (EAttribute)callEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCall_Arguments() {
		return (EReference)callEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCall_Filter() {
		return (EReference)callEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNot() {
		return notEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNot_Expression() {
		return (EReference)notEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getOperator() {
		return operatorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getOperator_Operator() {
		return (EAttribute)operatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getOperator_Operands() {
		return (EReference)operatorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getParenthesis() {
		return parenthesisEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getParenthesis_Expression() {
		return (EReference)parenthesisEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getLiteral() {
		return literalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStringLiteral() {
		return stringLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getStringLiteral_Value() {
		return (EAttribute)stringLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIntegerLiteral() {
		return integerLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getIntegerLiteral_Value() {
		return (EAttribute)integerLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDoubleLiteral() {
		return doubleLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDoubleLiteral_Value() {
		return (EAttribute)doubleLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBooleanLiteral() {
		return booleanLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBooleanLiteral_Value() {
		return (EAttribute)booleanLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNullLiteral() {
		return nullLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExpressionsFactory getExpressionsFactory() {
		return (ExpressionsFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		expressionEClass = createEClass(EXPRESSION);

		callSetEClass = createEClass(CALL_SET);
		createEReference(callSetEClass, CALL_SET__CALLS);

		callEClass = createEClass(CALL);
		createEAttribute(callEClass, CALL__NAME);
		createEAttribute(callEClass, CALL__PREFIX);
		createEReference(callEClass, CALL__ARGUMENTS);
		createEReference(callEClass, CALL__FILTER);

		notEClass = createEClass(NOT);
		createEReference(notEClass, NOT__EXPRESSION);

		operatorEClass = createEClass(OPERATOR);
		createEAttribute(operatorEClass, OPERATOR__OPERATOR);
		createEReference(operatorEClass, OPERATOR__OPERANDS);

		parenthesisEClass = createEClass(PARENTHESIS);
		createEReference(parenthesisEClass, PARENTHESIS__EXPRESSION);

		literalEClass = createEClass(LITERAL);

		stringLiteralEClass = createEClass(STRING_LITERAL);
		createEAttribute(stringLiteralEClass, STRING_LITERAL__VALUE);

		integerLiteralEClass = createEClass(INTEGER_LITERAL);
		createEAttribute(integerLiteralEClass, INTEGER_LITERAL__VALUE);

		doubleLiteralEClass = createEClass(DOUBLE_LITERAL);
		createEAttribute(doubleLiteralEClass, DOUBLE_LITERAL__VALUE);

		booleanLiteralEClass = createEClass(BOOLEAN_LITERAL);
		createEAttribute(booleanLiteralEClass, BOOLEAN_LITERAL__VALUE);

		nullLiteralEClass = createEClass(NULL_LITERAL);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CorePackage theCorePackage = (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		expressionEClass.getESuperTypes().add(theCorePackage.getASTNode());
		callSetEClass.getESuperTypes().add(this.getExpression());
		callEClass.getESuperTypes().add(theCorePackage.getASTNode());
		notEClass.getESuperTypes().add(this.getExpression());
		operatorEClass.getESuperTypes().add(this.getExpression());
		parenthesisEClass.getESuperTypes().add(this.getExpression());
		literalEClass.getESuperTypes().add(this.getExpression());
		stringLiteralEClass.getESuperTypes().add(this.getLiteral());
		integerLiteralEClass.getESuperTypes().add(this.getLiteral());
		doubleLiteralEClass.getESuperTypes().add(this.getLiteral());
		booleanLiteralEClass.getESuperTypes().add(this.getLiteral());
		nullLiteralEClass.getESuperTypes().add(this.getLiteral());

		// Initialize classes and features; add operations and parameters
		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(callSetEClass, CallSet.class, "CallSet", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCallSet_Calls(), this.getCall(), null, "calls", null, 0, -1, CallSet.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(callEClass, Call.class, "Call", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCall_Name(), ecorePackage.getEString(), "name", null, 1, 1, Call.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getCall_Prefix(), ecorePackage.getEString(), "prefix", null, 0, 1, Call.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getCall_Arguments(), this.getExpression(), null, "arguments", null, 0, -1, Call.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCall_Filter(), this.getExpression(), null, "filter", null, 0, 1, Call.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notEClass, Not.class, "Not", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getNot_Expression(), this.getExpression(), null, "expression", null, 1, 1, Not.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(operatorEClass, Operator.class, "Operator", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperator_Operator(), ecorePackage.getEString(), "operator", null, 1, 1, //$NON-NLS-1$
				Operator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOperator_Operands(), this.getExpression(), null, "operands", null, 2, -1, //$NON-NLS-1$
				Operator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parenthesisEClass, Parenthesis.class, "Parenthesis", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getParenthesis_Expression(), this.getExpression(), null, "expression", null, 1, 1, //$NON-NLS-1$
				Parenthesis.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(literalEClass, Literal.class, "Literal", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(stringLiteralEClass, StringLiteral.class, "StringLiteral", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringLiteral_Value(), ecorePackage.getEString(), "value", null, 1, 1, //$NON-NLS-1$
				StringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerLiteralEClass, IntegerLiteral.class, "IntegerLiteral", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerLiteral_Value(), ecorePackage.getEInt(), "value", null, 1, 1, //$NON-NLS-1$
				IntegerLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doubleLiteralEClass, DoubleLiteral.class, "DoubleLiteral", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleLiteral_Value(), ecorePackage.getEDouble(), "value", null, 1, 1, //$NON-NLS-1$
				DoubleLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanLiteralEClass, BooleanLiteral.class, "BooleanLiteral", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanLiteral_Value(), ecorePackage.getEBoolean(), "value", null, 1, 1, //$NON-NLS-1$
				BooleanLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nullLiteralEClass, NullLiteral.class, "NullLiteral", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
	}

} // ExpressionsPackageImpl
