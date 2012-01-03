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
package org.eclipse.acceleo.compatibility.model.mt.statements.impl;

import org.eclipse.acceleo.compatibility.model.mt.MtPackage;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.impl.MtPackageImpl;
import org.eclipse.acceleo.compatibility.model.mt.statements.Comment;
import org.eclipse.acceleo.compatibility.model.mt.statements.Feature;
import org.eclipse.acceleo.compatibility.model.mt.statements.For;
import org.eclipse.acceleo.compatibility.model.mt.statements.If;
import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage;
import org.eclipse.acceleo.compatibility.model.mt.statements.Text;
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
public class StatementsPackageImpl extends EPackageImpl implements StatementsPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass statementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass commentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ifEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass forEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass featureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass textEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StatementsPackageImpl() {
		super(eNS_URI, StatementsFactory.eINSTANCE);
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
	 * This method is used to initialize {@link StatementsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the
	 * package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StatementsPackage init() {
		if (isInited)
			return (StatementsPackage)EPackage.Registry.INSTANCE.getEPackage(StatementsPackage.eNS_URI);

		// Obtain or create and register package
		StatementsPackageImpl theStatementsPackage = (StatementsPackageImpl)(EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof StatementsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new StatementsPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		MtPackageImpl theMtPackage = (MtPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(MtPackage.eNS_URI) instanceof MtPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(MtPackage.eNS_URI) : MtPackage.eINSTANCE);
		CorePackageImpl theCorePackage = (CorePackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(CorePackage.eNS_URI) instanceof CorePackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(CorePackage.eNS_URI) : CorePackage.eINSTANCE);
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI) instanceof ExpressionsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI)
				: ExpressionsPackage.eINSTANCE);

		// Create package meta-data objects
		theStatementsPackage.createPackageContents();
		theMtPackage.createPackageContents();
		theCorePackage.createPackageContents();
		theExpressionsPackage.createPackageContents();

		// Initialize created meta-data
		theStatementsPackage.initializePackageContents();
		theMtPackage.initializePackageContents();
		theCorePackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStatementsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StatementsPackage.eNS_URI, theStatementsPackage);
		return theStatementsPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStatement() {
		return statementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getComment() {
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getComment_Value() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIf() {
		return ifEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIf_Condition() {
		return (EReference)ifEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIf_ThenStatements() {
		return (EReference)ifEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIf_ElseStatements() {
		return (EReference)ifEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIf_ElseIf() {
		return (EReference)ifEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFor() {
		return forEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFor_Iterator() {
		return (EReference)forEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFor_Statements() {
		return (EReference)forEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFeature() {
		return featureEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFeature_Expression() {
		return (EReference)featureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getText() {
		return textEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getText_Value() {
		return (EAttribute)textEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StatementsFactory getStatementsFactory() {
		return (StatementsFactory)getEFactoryInstance();
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
		statementEClass = createEClass(STATEMENT);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__VALUE);

		ifEClass = createEClass(IF);
		createEReference(ifEClass, IF__CONDITION);
		createEReference(ifEClass, IF__THEN_STATEMENTS);
		createEReference(ifEClass, IF__ELSE_STATEMENTS);
		createEReference(ifEClass, IF__ELSE_IF);

		forEClass = createEClass(FOR);
		createEReference(forEClass, FOR__ITERATOR);
		createEReference(forEClass, FOR__STATEMENTS);

		featureEClass = createEClass(FEATURE);
		createEReference(featureEClass, FEATURE__EXPRESSION);

		textEClass = createEClass(TEXT);
		createEAttribute(textEClass, TEXT__VALUE);
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
		ExpressionsPackage theExpressionsPackage = (ExpressionsPackage)EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		statementEClass.getESuperTypes().add(theCorePackage.getASTNode());
		commentEClass.getESuperTypes().add(this.getStatement());
		ifEClass.getESuperTypes().add(this.getStatement());
		forEClass.getESuperTypes().add(this.getStatement());
		featureEClass.getESuperTypes().add(this.getStatement());
		textEClass.getESuperTypes().add(this.getStatement());

		// Initialize classes and features; add operations and parameters
		initEClass(statementEClass, Statement.class, "Statement", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getComment_Value(), ecorePackage.getEString(), "value", null, 1, 1, Comment.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(ifEClass, If.class, "If", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getIf_Condition(), theExpressionsPackage.getExpression(), null, "condition", null, 1, //$NON-NLS-1$
				1, If.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIf_ThenStatements(), this.getStatement(), null, "thenStatements", null, 0, -1, //$NON-NLS-1$
				If.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIf_ElseStatements(), this.getStatement(), null, "elseStatements", null, 0, -1, //$NON-NLS-1$
				If.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIf_ElseIf(), this.getIf(), null, "elseIf", null, 0, -1, If.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(forEClass, For.class, "For", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getFor_Iterator(), theExpressionsPackage.getExpression(), null, "iterator", null, 1, //$NON-NLS-1$
				1, For.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFor_Statements(), this.getStatement(), null, "statements", null, 0, -1, For.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureEClass, Feature.class, "Feature", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeature_Expression(), theExpressionsPackage.getExpression(), null,
				"expression", //$NON-NLS-1$
				null, 1, 1, Feature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textEClass, Text.class, "Text", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getText_Value(), ecorePackage.getEString(), "value", null, 1, 1, Text.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
	}

} // StatementsPackageImpl
