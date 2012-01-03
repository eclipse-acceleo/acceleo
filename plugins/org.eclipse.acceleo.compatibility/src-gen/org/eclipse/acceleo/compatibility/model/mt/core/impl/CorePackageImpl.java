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
package org.eclipse.acceleo.compatibility.model.mt.core.impl;

import org.eclipse.acceleo.compatibility.model.mt.MtPackage;
import org.eclipse.acceleo.compatibility.model.mt.core.ASTNode;
import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.FilePath;
import org.eclipse.acceleo.compatibility.model.mt.core.Metamodel;
import org.eclipse.acceleo.compatibility.model.mt.core.Method;
import org.eclipse.acceleo.compatibility.model.mt.core.Parameter;
import org.eclipse.acceleo.compatibility.model.mt.core.Script;
import org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor;
import org.eclipse.acceleo.compatibility.model.mt.core.Service;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.impl.ExpressionsPackageImpl;
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
public class CorePackageImpl extends EPackageImpl implements CorePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass astNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass templateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass scriptEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass scriptDescriptorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass filePathEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass metamodelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass serviceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass methodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CorePackageImpl() {
		super(eNS_URI, CoreFactory.eINSTANCE);
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
	 * This method is used to initialize {@link CorePackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CorePackage init() {
		if (isInited)
			return (CorePackage)EPackage.Registry.INSTANCE.getEPackage(CorePackage.eNS_URI);

		// Obtain or create and register package
		CorePackageImpl theCorePackage = (CorePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CorePackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI)
				: new CorePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		MtPackageImpl theMtPackage = (MtPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(MtPackage.eNS_URI) instanceof MtPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(MtPackage.eNS_URI) : MtPackage.eINSTANCE);
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI) instanceof ExpressionsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI)
				: ExpressionsPackage.eINSTANCE);
		StatementsPackageImpl theStatementsPackage = (StatementsPackageImpl)(EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI) instanceof StatementsPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI)
				: StatementsPackage.eINSTANCE);

		// Create package meta-data objects
		theCorePackage.createPackageContents();
		theMtPackage.createPackageContents();
		theExpressionsPackage.createPackageContents();
		theStatementsPackage.createPackageContents();

		// Initialize created meta-data
		theCorePackage.initializePackageContents();
		theMtPackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();
		theStatementsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCorePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CorePackage.eNS_URI, theCorePackage);
		return theCorePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getASTNode() {
		return astNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getASTNode_Begin() {
		return (EAttribute)astNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getASTNode_End() {
		return (EAttribute)astNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTemplate() {
		return templateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplate_Imports() {
		return (EReference)templateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplate_Scripts() {
		return (EReference)templateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTemplate_BeginTag() {
		return (EAttribute)templateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTemplate_EndTag() {
		return (EAttribute)templateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getScript() {
		return scriptEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getScript_Descriptor() {
		return (EReference)scriptEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getScript_Statements() {
		return (EReference)scriptEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getScriptDescriptor() {
		return scriptDescriptorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getScriptDescriptor_Name() {
		return (EAttribute)scriptDescriptorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getScriptDescriptor_Type() {
		return (EAttribute)scriptDescriptorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getScriptDescriptor_Description() {
		return (EAttribute)scriptDescriptorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getScriptDescriptor_File() {
		return (EReference)scriptDescriptorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getScriptDescriptor_Post() {
		return (EReference)scriptDescriptorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFilePath() {
		return filePathEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFilePath_Statements() {
		return (EReference)filePathEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMetamodel() {
		return metamodelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMetamodel_PackageClass() {
		return (EAttribute)metamodelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getService() {
		return serviceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getService_Methods() {
		return (EReference)serviceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMethod() {
		return methodEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMethod_Name() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMethod_Parameters() {
		return (EReference)methodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMethod_Return() {
		return (EAttribute)methodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getParameter_Type() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CoreFactory getCoreFactory() {
		return (CoreFactory)getEFactoryInstance();
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
		astNodeEClass = createEClass(AST_NODE);
		createEAttribute(astNodeEClass, AST_NODE__BEGIN);
		createEAttribute(astNodeEClass, AST_NODE__END);

		templateEClass = createEClass(TEMPLATE);
		createEReference(templateEClass, TEMPLATE__IMPORTS);
		createEReference(templateEClass, TEMPLATE__SCRIPTS);
		createEAttribute(templateEClass, TEMPLATE__BEGIN_TAG);
		createEAttribute(templateEClass, TEMPLATE__END_TAG);

		scriptEClass = createEClass(SCRIPT);
		createEReference(scriptEClass, SCRIPT__DESCRIPTOR);
		createEReference(scriptEClass, SCRIPT__STATEMENTS);

		scriptDescriptorEClass = createEClass(SCRIPT_DESCRIPTOR);
		createEAttribute(scriptDescriptorEClass, SCRIPT_DESCRIPTOR__NAME);
		createEAttribute(scriptDescriptorEClass, SCRIPT_DESCRIPTOR__TYPE);
		createEAttribute(scriptDescriptorEClass, SCRIPT_DESCRIPTOR__DESCRIPTION);
		createEReference(scriptDescriptorEClass, SCRIPT_DESCRIPTOR__FILE);
		createEReference(scriptDescriptorEClass, SCRIPT_DESCRIPTOR__POST);

		filePathEClass = createEClass(FILE_PATH);
		createEReference(filePathEClass, FILE_PATH__STATEMENTS);

		metamodelEClass = createEClass(METAMODEL);
		createEAttribute(metamodelEClass, METAMODEL__PACKAGE_CLASS);

		serviceEClass = createEClass(SERVICE);
		createEReference(serviceEClass, SERVICE__METHODS);

		methodEClass = createEClass(METHOD);
		createEAttribute(methodEClass, METHOD__NAME);
		createEReference(methodEClass, METHOD__PARAMETERS);
		createEAttribute(methodEClass, METHOD__RETURN);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__TYPE);
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
		MtPackage theMtPackage = (MtPackage)EPackage.Registry.INSTANCE.getEPackage(MtPackage.eNS_URI);
		StatementsPackage theStatementsPackage = (StatementsPackage)EPackage.Registry.INSTANCE
				.getEPackage(StatementsPackage.eNS_URI);
		ExpressionsPackage theExpressionsPackage = (ExpressionsPackage)EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		templateEClass.getESuperTypes().add(theMtPackage.getResource());
		scriptEClass.getESuperTypes().add(this.getASTNode());
		scriptDescriptorEClass.getESuperTypes().add(this.getASTNode());
		filePathEClass.getESuperTypes().add(this.getASTNode());
		metamodelEClass.getESuperTypes().add(theMtPackage.getResource());
		serviceEClass.getESuperTypes().add(theMtPackage.getResource());

		// Initialize classes and features; add operations and parameters
		initEClass(astNodeEClass, ASTNode.class, "ASTNode", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getASTNode_Begin(), ecorePackage.getEInt(), "begin", null, 0, 1, ASTNode.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getASTNode_End(), ecorePackage.getEInt(), "end", null, 0, 1, ASTNode.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		addEOperation(astNodeEClass, this.getTemplate(), "getTemplate", 0, 1, IS_UNIQUE, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateEClass, Template.class, "Template", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemplate_Imports(), theMtPackage.getResource(), null, "imports", null, 0, -1, //$NON-NLS-1$
				Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemplate_Scripts(), this.getScript(), null, "scripts", null, 0, -1, Template.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplate_BeginTag(), ecorePackage.getEString(), "beginTag", null, 1, 1, //$NON-NLS-1$
				Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplate_EndTag(), ecorePackage.getEString(), "endTag", null, 1, 1, Template.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(scriptEClass, Script.class, "Script", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScript_Descriptor(), this.getScriptDescriptor(), null, "descriptor", null, 1, 1, //$NON-NLS-1$
				Script.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScript_Statements(), theStatementsPackage.getStatement(), null, "statements", null, //$NON-NLS-1$
				0, -1, Script.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scriptDescriptorEClass, ScriptDescriptor.class, "ScriptDescriptor", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getScriptDescriptor_Name(), ecorePackage.getEString(), "name", null, 1, 1, //$NON-NLS-1$
				ScriptDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScriptDescriptor_Type(), ecorePackage.getEString(), "type", null, 1, 1, //$NON-NLS-1$
				ScriptDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScriptDescriptor_Description(), ecorePackage.getEString(), "description", null, 1, //$NON-NLS-1$
				1, ScriptDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScriptDescriptor_File(), this.getFilePath(), null, "file", null, 0, 1, //$NON-NLS-1$
				ScriptDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScriptDescriptor_Post(), theExpressionsPackage.getExpression(), null, "post", null, //$NON-NLS-1$
				0, 1, ScriptDescriptor.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(filePathEClass, FilePath.class, "FilePath", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFilePath_Statements(), theStatementsPackage.getStatement(), null,
				"statements", //$NON-NLS-1$
				null, 0, -1, FilePath.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metamodelEClass, Metamodel.class, "Metamodel", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMetamodel_PackageClass(), ecorePackage.getEString(), "packageClass", null, 0, 1, //$NON-NLS-1$
				Metamodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceEClass, Service.class, "Service", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getService_Methods(), this.getMethod(), null, "methods", null, 0, -1, Service.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(methodEClass, Method.class, "Method", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMethod_Name(), ecorePackage.getEString(), "name", null, 1, 1, Method.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getMethod_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, //$NON-NLS-1$
				Method.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMethod_Return(), ecorePackage.getEString(), "return", null, 0, 1, Method.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Type(), ecorePackage.getEString(), "type", null, 1, 1, Parameter.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
	}

} // CorePackageImpl
