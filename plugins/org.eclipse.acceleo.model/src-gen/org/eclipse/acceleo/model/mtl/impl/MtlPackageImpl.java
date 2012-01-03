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
package org.eclipse.acceleo.model.mtl.impl;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.Comment;
import org.eclipse.acceleo.model.mtl.CommentBody;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleDocumentation;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.ModuleElementDocumentation;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.acceleo.model.mtl.ParameterDocumentation;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.ocl.ecore.EcorePackage;
import org.eclipse.ocl.utilities.UtilitiesPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class MtlPackageImpl extends EPackageImpl implements MtlPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass templateExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass blockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass initSectionEClass = null;

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
	private EClass templateInvocationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass queryInvocationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass protectedAreaBlockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass forBlockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ifBlockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass letBlockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass fileBlockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass traceBlockEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass macroEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass macroInvocationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typedModelEClass = null;

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
	private EClass documentationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass documentedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass commentBodyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleDocumentationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleElementDocumentationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass parameterDocumentationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum visibilityKindEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum openModeKindEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MtlPackageImpl() {
		super(eNS_URI, MtlFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends. Simple dependencies are satisfied by calling this method on all dependent packages before
	 * doing anything else. This method drives initialization for interdependent packages directly, in
	 * parallel with this package, itself.
	 * <p>
	 * Of this package and its interdependencies, all packages which have not yet been registered by their URI
	 * values are first created and registered. The packages are then initialized in two steps: meta-model
	 * objects for all of the packages are created before any are initialized, since one package's meta-model
	 * objects may refer to those of another.
	 * <p>
	 * Invocation of this method will not affect any packages that have already been initialized. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MtlPackage init() {
		if (isInited)
			return (MtlPackage)EPackage.Registry.INSTANCE.getEPackage(MtlPackage.eNS_URI);

		// Obtain or create and register package
		MtlPackageImpl theMtlPackage = (MtlPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof MtlPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI)
				: new MtlPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMtlPackage.createPackageContents();

		// Initialize created meta-data
		theMtlPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMtlPackage.freeze();

		return theMtlPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModule() {
		return moduleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_Input() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_Extends() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_Imports() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_OwnedModuleElement() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModule_StartHeaderPosition() {
		return (EAttribute)moduleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModule_EndHeaderPosition() {
		return (EAttribute)moduleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModuleElement() {
		return moduleElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleElement_Visibility() {
		return (EAttribute)moduleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTemplateExpression() {
		return templateExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBlock() {
		return blockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBlock_Init() {
		return (EReference)blockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBlock_Body() {
		return (EReference)blockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInitSection() {
		return initSectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInitSection_Variable() {
		return (EReference)initSectionEClass.getEStructuralFeatures().get(0);
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
	public EReference getTemplate_Overrides() {
		return (EReference)templateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplate_Parameter() {
		return (EReference)templateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplate_Guard() {
		return (EReference)templateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTemplate_Main() {
		return (EAttribute)templateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplate_Post() {
		return (EReference)templateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTemplateInvocation() {
		return templateInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplateInvocation_Definition() {
		return (EReference)templateInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplateInvocation_Argument() {
		return (EReference)templateInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplateInvocation_Before() {
		return (EReference)templateInvocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplateInvocation_After() {
		return (EReference)templateInvocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTemplateInvocation_Each() {
		return (EReference)templateInvocationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTemplateInvocation_Super() {
		return (EAttribute)templateInvocationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQuery() {
		return queryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_Parameter() {
		return (EReference)queryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_Expression() {
		return (EReference)queryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_Type() {
		return (EReference)queryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getQueryInvocation() {
		return queryInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueryInvocation_Definition() {
		return (EReference)queryInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQueryInvocation_Argument() {
		return (EReference)queryInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProtectedAreaBlock() {
		return protectedAreaBlockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProtectedAreaBlock_Marker() {
		return (EReference)protectedAreaBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getForBlock() {
		return forBlockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getForBlock_LoopVariable() {
		return (EReference)forBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getForBlock_IterSet() {
		return (EReference)forBlockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getForBlock_Before() {
		return (EReference)forBlockEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getForBlock_Each() {
		return (EReference)forBlockEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getForBlock_After() {
		return (EReference)forBlockEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getForBlock_Guard() {
		return (EReference)forBlockEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIfBlock() {
		return ifBlockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIfBlock_IfExpr() {
		return (EReference)ifBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIfBlock_Else() {
		return (EReference)ifBlockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIfBlock_ElseIf() {
		return (EReference)ifBlockEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getLetBlock() {
		return letBlockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getLetBlock_ElseLet() {
		return (EReference)letBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getLetBlock_Else() {
		return (EReference)letBlockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getLetBlock_LetVariable() {
		return (EReference)letBlockEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFileBlock() {
		return fileBlockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getFileBlock_OpenMode() {
		return (EAttribute)fileBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFileBlock_FileUrl() {
		return (EReference)fileBlockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFileBlock_UniqId() {
		return (EReference)fileBlockEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFileBlock_Charset() {
		return (EReference)fileBlockEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTraceBlock() {
		return traceBlockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTraceBlock_ModelElement() {
		return (EReference)traceBlockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMacro() {
		return macroEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMacro_Parameter() {
		return (EReference)macroEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMacro_Type() {
		return (EReference)macroEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMacroInvocation() {
		return macroInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMacroInvocation_Definition() {
		return (EReference)macroInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMacroInvocation_Argument() {
		return (EReference)macroInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypedModel() {
		return typedModelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTypedModel_TakesTypesFrom() {
		return (EReference)typedModelEClass.getEStructuralFeatures().get(0);
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
	public EReference getComment_Body() {
		return (EReference)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDocumentation() {
		return documentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDocumentation_DocumentedElement() {
		return (EReference)documentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDocumentedElement() {
		return documentedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getDocumentedElement_Documentation() {
		return (EReference)documentedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDocumentedElement_Deprecated() {
		return (EAttribute)documentedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCommentBody() {
		return commentBodyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCommentBody_StartPosition() {
		return (EAttribute)commentBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCommentBody_EndPosition() {
		return (EAttribute)commentBodyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCommentBody_Value() {
		return (EAttribute)commentBodyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModuleDocumentation() {
		return moduleDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleDocumentation_Author() {
		return (EAttribute)moduleDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleDocumentation_Version() {
		return (EAttribute)moduleDocumentationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleDocumentation_Since() {
		return (EAttribute)moduleDocumentationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModuleElementDocumentation() {
		return moduleElementDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModuleElementDocumentation_ParametersDocumentation() {
		return (EReference)moduleElementDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getParameterDocumentation() {
		return parameterDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getVisibilityKind() {
		return visibilityKindEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getOpenModeKind() {
		return openModeKindEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtlFactory getMtlFactory() {
		return (MtlFactory)getEFactoryInstance();
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
		moduleEClass = createEClass(MODULE);
		createEReference(moduleEClass, MODULE__INPUT);
		createEReference(moduleEClass, MODULE__EXTENDS);
		createEReference(moduleEClass, MODULE__IMPORTS);
		createEReference(moduleEClass, MODULE__OWNED_MODULE_ELEMENT);
		createEAttribute(moduleEClass, MODULE__START_HEADER_POSITION);
		createEAttribute(moduleEClass, MODULE__END_HEADER_POSITION);

		moduleElementEClass = createEClass(MODULE_ELEMENT);
		createEAttribute(moduleElementEClass, MODULE_ELEMENT__VISIBILITY);

		templateExpressionEClass = createEClass(TEMPLATE_EXPRESSION);

		blockEClass = createEClass(BLOCK);
		createEReference(blockEClass, BLOCK__INIT);
		createEReference(blockEClass, BLOCK__BODY);

		initSectionEClass = createEClass(INIT_SECTION);
		createEReference(initSectionEClass, INIT_SECTION__VARIABLE);

		templateEClass = createEClass(TEMPLATE);
		createEReference(templateEClass, TEMPLATE__OVERRIDES);
		createEReference(templateEClass, TEMPLATE__PARAMETER);
		createEReference(templateEClass, TEMPLATE__GUARD);
		createEAttribute(templateEClass, TEMPLATE__MAIN);
		createEReference(templateEClass, TEMPLATE__POST);

		templateInvocationEClass = createEClass(TEMPLATE_INVOCATION);
		createEReference(templateInvocationEClass, TEMPLATE_INVOCATION__DEFINITION);
		createEReference(templateInvocationEClass, TEMPLATE_INVOCATION__ARGUMENT);
		createEReference(templateInvocationEClass, TEMPLATE_INVOCATION__BEFORE);
		createEReference(templateInvocationEClass, TEMPLATE_INVOCATION__AFTER);
		createEReference(templateInvocationEClass, TEMPLATE_INVOCATION__EACH);
		createEAttribute(templateInvocationEClass, TEMPLATE_INVOCATION__SUPER);

		queryEClass = createEClass(QUERY);
		createEReference(queryEClass, QUERY__PARAMETER);
		createEReference(queryEClass, QUERY__EXPRESSION);
		createEReference(queryEClass, QUERY__TYPE);

		queryInvocationEClass = createEClass(QUERY_INVOCATION);
		createEReference(queryInvocationEClass, QUERY_INVOCATION__DEFINITION);
		createEReference(queryInvocationEClass, QUERY_INVOCATION__ARGUMENT);

		protectedAreaBlockEClass = createEClass(PROTECTED_AREA_BLOCK);
		createEReference(protectedAreaBlockEClass, PROTECTED_AREA_BLOCK__MARKER);

		forBlockEClass = createEClass(FOR_BLOCK);
		createEReference(forBlockEClass, FOR_BLOCK__LOOP_VARIABLE);
		createEReference(forBlockEClass, FOR_BLOCK__ITER_SET);
		createEReference(forBlockEClass, FOR_BLOCK__BEFORE);
		createEReference(forBlockEClass, FOR_BLOCK__EACH);
		createEReference(forBlockEClass, FOR_BLOCK__AFTER);
		createEReference(forBlockEClass, FOR_BLOCK__GUARD);

		ifBlockEClass = createEClass(IF_BLOCK);
		createEReference(ifBlockEClass, IF_BLOCK__IF_EXPR);
		createEReference(ifBlockEClass, IF_BLOCK__ELSE);
		createEReference(ifBlockEClass, IF_BLOCK__ELSE_IF);

		letBlockEClass = createEClass(LET_BLOCK);
		createEReference(letBlockEClass, LET_BLOCK__ELSE_LET);
		createEReference(letBlockEClass, LET_BLOCK__ELSE);
		createEReference(letBlockEClass, LET_BLOCK__LET_VARIABLE);

		fileBlockEClass = createEClass(FILE_BLOCK);
		createEAttribute(fileBlockEClass, FILE_BLOCK__OPEN_MODE);
		createEReference(fileBlockEClass, FILE_BLOCK__FILE_URL);
		createEReference(fileBlockEClass, FILE_BLOCK__UNIQ_ID);
		createEReference(fileBlockEClass, FILE_BLOCK__CHARSET);

		traceBlockEClass = createEClass(TRACE_BLOCK);
		createEReference(traceBlockEClass, TRACE_BLOCK__MODEL_ELEMENT);

		macroEClass = createEClass(MACRO);
		createEReference(macroEClass, MACRO__PARAMETER);
		createEReference(macroEClass, MACRO__TYPE);

		macroInvocationEClass = createEClass(MACRO_INVOCATION);
		createEReference(macroInvocationEClass, MACRO_INVOCATION__DEFINITION);
		createEReference(macroInvocationEClass, MACRO_INVOCATION__ARGUMENT);

		typedModelEClass = createEClass(TYPED_MODEL);
		createEReference(typedModelEClass, TYPED_MODEL__TAKES_TYPES_FROM);

		commentEClass = createEClass(COMMENT);
		createEReference(commentEClass, COMMENT__BODY);

		documentationEClass = createEClass(DOCUMENTATION);
		createEReference(documentationEClass, DOCUMENTATION__DOCUMENTED_ELEMENT);

		documentedElementEClass = createEClass(DOCUMENTED_ELEMENT);
		createEReference(documentedElementEClass, DOCUMENTED_ELEMENT__DOCUMENTATION);
		createEAttribute(documentedElementEClass, DOCUMENTED_ELEMENT__DEPRECATED);

		commentBodyEClass = createEClass(COMMENT_BODY);
		createEAttribute(commentBodyEClass, COMMENT_BODY__START_POSITION);
		createEAttribute(commentBodyEClass, COMMENT_BODY__END_POSITION);
		createEAttribute(commentBodyEClass, COMMENT_BODY__VALUE);

		moduleDocumentationEClass = createEClass(MODULE_DOCUMENTATION);
		createEAttribute(moduleDocumentationEClass, MODULE_DOCUMENTATION__AUTHOR);
		createEAttribute(moduleDocumentationEClass, MODULE_DOCUMENTATION__VERSION);
		createEAttribute(moduleDocumentationEClass, MODULE_DOCUMENTATION__SINCE);

		moduleElementDocumentationEClass = createEClass(MODULE_ELEMENT_DOCUMENTATION);
		createEReference(moduleElementDocumentationEClass,
				MODULE_ELEMENT_DOCUMENTATION__PARAMETERS_DOCUMENTATION);

		parameterDocumentationEClass = createEClass(PARAMETER_DOCUMENTATION);

		// Create enums
		visibilityKindEEnum = createEEnum(VISIBILITY_KIND);
		openModeKindEEnum = createEEnum(OPEN_MODE_KIND);
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
		org.eclipse.emf.ecore.EcorePackage theEcorePackage_1 = (org.eclipse.emf.ecore.EcorePackage)EPackage.Registry.INSTANCE
				.getEPackage(org.eclipse.emf.ecore.EcorePackage.eNS_URI);
		UtilitiesPackage theUtilitiesPackage = (UtilitiesPackage)EPackage.Registry.INSTANCE
				.getEPackage(UtilitiesPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		moduleEClass.getESuperTypes().add(theEcorePackage_1.getEPackage());
		moduleEClass.getESuperTypes().add(this.getDocumentedElement());
		moduleElementEClass.getESuperTypes().add(theEcorePackage_1.getENamedElement());
		moduleElementEClass.getESuperTypes().add(theUtilitiesPackage.getASTNode());
		templateExpressionEClass.getESuperTypes().add(theEcorePackage.getOCLExpression());
		blockEClass.getESuperTypes().add(this.getTemplateExpression());
		initSectionEClass.getESuperTypes().add(theUtilitiesPackage.getASTNode());
		templateEClass.getESuperTypes().add(this.getBlock());
		templateEClass.getESuperTypes().add(this.getModuleElement());
		templateEClass.getESuperTypes().add(this.getDocumentedElement());
		templateInvocationEClass.getESuperTypes().add(this.getTemplateExpression());
		queryEClass.getESuperTypes().add(this.getModuleElement());
		queryEClass.getESuperTypes().add(this.getDocumentedElement());
		queryInvocationEClass.getESuperTypes().add(this.getTemplateExpression());
		protectedAreaBlockEClass.getESuperTypes().add(this.getBlock());
		forBlockEClass.getESuperTypes().add(this.getBlock());
		ifBlockEClass.getESuperTypes().add(this.getBlock());
		letBlockEClass.getESuperTypes().add(this.getBlock());
		fileBlockEClass.getESuperTypes().add(this.getBlock());
		traceBlockEClass.getESuperTypes().add(this.getBlock());
		macroEClass.getESuperTypes().add(this.getBlock());
		macroEClass.getESuperTypes().add(this.getModuleElement());
		macroEClass.getESuperTypes().add(this.getDocumentedElement());
		macroInvocationEClass.getESuperTypes().add(this.getTemplateExpression());
		commentEClass.getESuperTypes().add(this.getModuleElement());
		documentationEClass.getESuperTypes().add(this.getComment());
		moduleDocumentationEClass.getESuperTypes().add(this.getDocumentation());
		moduleElementDocumentationEClass.getESuperTypes().add(this.getDocumentation());
		parameterDocumentationEClass.getESuperTypes().add(this.getComment());

		// Initialize classes and features; add operations and parameters
		initEClass(moduleEClass, Module.class,
				"Module", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getModule_Input(),
				this.getTypedModel(),
				null,
				"input", null, 1, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_Extends(),
				this.getModule(),
				null,
				"extends", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_Imports(),
				this.getModule(),
				null,
				"imports", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_OwnedModuleElement(),
				this.getModuleElement(),
				null,
				"ownedModuleElement", null, 1, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getModule_StartHeaderPosition(),
				theEcorePackage_1.getEInt(),
				"startHeaderPosition", null, 1, 1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getModule_EndHeaderPosition(),
				theEcorePackage_1.getEInt(),
				"endHeaderPosition", null, 1, 1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleElementEClass, ModuleElement.class,
				"ModuleElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getModuleElement_Visibility(),
				this.getVisibilityKind(),
				"visibility", null, 1, 1, ModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateExpressionEClass, TemplateExpression.class,
				"TemplateExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(blockEClass, Block.class,
				"Block", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getBlock_Init(),
				this.getInitSection(),
				null,
				"init", null, 0, 1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getBlock_Body(),
				theEcorePackage.getOCLExpression(),
				null,
				"body", null, 0, -1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(initSectionEClass, InitSection.class,
				"InitSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getInitSection_Variable(),
				theEcorePackage.getVariable(),
				null,
				"variable", null, 1, -1, InitSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateEClass, Template.class,
				"Template", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTemplate_Overrides(),
				this.getTemplate(),
				null,
				"overrides", null, 0, -1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplate_Parameter(),
				theEcorePackage.getVariable(),
				null,
				"parameter", null, 0, -1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplate_Guard(),
				theEcorePackage.getOCLExpression(),
				null,
				"guard", null, 0, 1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getTemplate_Main(),
				ecorePackage.getEBoolean(),
				"main", "false", 0, 1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(
				getTemplate_Post(),
				theEcorePackage.getOCLExpression(),
				null,
				"post", null, 0, 1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateInvocationEClass, TemplateInvocation.class,
				"TemplateInvocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTemplateInvocation_Definition(),
				this.getTemplate(),
				null,
				"definition", null, 1, 1, TemplateInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplateInvocation_Argument(),
				theEcorePackage.getOCLExpression(),
				null,
				"argument", null, 0, -1, TemplateInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplateInvocation_Before(),
				theEcorePackage.getOCLExpression(),
				null,
				"before", null, 0, 1, TemplateInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplateInvocation_After(),
				theEcorePackage.getOCLExpression(),
				null,
				"after", null, 0, 1, TemplateInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplateInvocation_Each(),
				theEcorePackage.getOCLExpression(),
				null,
				"each", null, 0, 1, TemplateInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getTemplateInvocation_Super(),
				theEcorePackage_1.getEBoolean(),
				"super", "false", 0, 1, TemplateInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(queryEClass, Query.class,
				"Query", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getQuery_Parameter(),
				theEcorePackage.getVariable(),
				null,
				"parameter", null, 0, -1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getQuery_Expression(),
				theEcorePackage.getOCLExpression(),
				null,
				"expression", null, 1, 1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getQuery_Type(),
				theEcorePackage_1.getEClassifier(),
				null,
				"type", null, 1, 1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(queryInvocationEClass, QueryInvocation.class,
				"QueryInvocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getQueryInvocation_Definition(),
				this.getQuery(),
				null,
				"definition", null, 1, 1, QueryInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getQueryInvocation_Argument(),
				theEcorePackage.getOCLExpression(),
				null,
				"argument", null, 0, -1, QueryInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(protectedAreaBlockEClass, ProtectedAreaBlock.class,
				"ProtectedAreaBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getProtectedAreaBlock_Marker(),
				theEcorePackage.getOCLExpression(),
				null,
				"marker", null, 1, 1, ProtectedAreaBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(forBlockEClass, ForBlock.class,
				"ForBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getForBlock_LoopVariable(),
				theEcorePackage.getVariable(),
				null,
				"loopVariable", null, 1, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_IterSet(),
				theEcorePackage.getOCLExpression(),
				null,
				"iterSet", null, 1, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_Before(),
				theEcorePackage.getOCLExpression(),
				null,
				"before", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_Each(),
				theEcorePackage.getOCLExpression(),
				null,
				"each", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_After(),
				theEcorePackage.getOCLExpression(),
				null,
				"after", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_Guard(),
				theEcorePackage.getOCLExpression(),
				null,
				"guard", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(ifBlockEClass, IfBlock.class,
				"IfBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getIfBlock_IfExpr(),
				theEcorePackage.getOCLExpression(),
				null,
				"ifExpr", null, 1, 1, IfBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getIfBlock_Else(),
				this.getBlock(),
				null,
				"else", null, 0, 1, IfBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getIfBlock_ElseIf(),
				this.getIfBlock(),
				null,
				"elseIf", null, 0, -1, IfBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(letBlockEClass, LetBlock.class,
				"LetBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getLetBlock_ElseLet(),
				this.getLetBlock(),
				null,
				"elseLet", null, 0, -1, LetBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getLetBlock_Else(),
				this.getBlock(),
				null,
				"else", null, 0, 1, LetBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getLetBlock_LetVariable(),
				theEcorePackage.getVariable(),
				null,
				"letVariable", null, 1, 1, LetBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(fileBlockEClass, FileBlock.class,
				"FileBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getFileBlock_OpenMode(),
				this.getOpenModeKind(),
				"openMode", null, 1, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getFileBlock_FileUrl(),
				theEcorePackage.getOCLExpression(),
				null,
				"fileUrl", null, 1, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getFileBlock_UniqId(),
				theEcorePackage.getOCLExpression(),
				null,
				"uniqId", null, 0, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getFileBlock_Charset(),
				theEcorePackage.getOCLExpression(),
				null,
				"charset", null, 0, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(traceBlockEClass, TraceBlock.class,
				"TraceBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTraceBlock_ModelElement(),
				theEcorePackage.getOCLExpression(),
				null,
				"modelElement", null, 1, 1, TraceBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(macroEClass, Macro.class,
				"Macro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getMacro_Parameter(),
				theEcorePackage.getVariable(),
				null,
				"parameter", null, 0, -1, Macro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getMacro_Type(),
				theEcorePackage_1.getEClassifier(),
				null,
				"type", null, 1, 1, Macro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(macroInvocationEClass, MacroInvocation.class,
				"MacroInvocation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getMacroInvocation_Definition(),
				this.getMacro(),
				null,
				"definition", null, 1, 1, MacroInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getMacroInvocation_Argument(),
				theEcorePackage.getOCLExpression(),
				null,
				"argument", null, 0, -1, MacroInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(typedModelEClass, TypedModel.class,
				"TypedModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTypedModel_TakesTypesFrom(),
				theEcorePackage_1.getEPackage(),
				null,
				"takesTypesFrom", null, 1, -1, TypedModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(commentEClass, Comment.class,
				"Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getComment_Body(),
				this.getCommentBody(),
				null,
				"body", null, 0, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(documentationEClass, Documentation.class,
				"Documentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getDocumentation_DocumentedElement(),
				this.getDocumentedElement(),
				this.getDocumentedElement_Documentation(),
				"documentedElement", null, 0, 1, Documentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(documentedElementEClass, DocumentedElement.class,
				"DocumentedElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getDocumentedElement_Documentation(),
				this.getDocumentation(),
				this.getDocumentation_DocumentedElement(),
				"documentation", null, 0, 1, DocumentedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getDocumentedElement_Deprecated(),
				theEcorePackage_1.getEBoolean(),
				"deprecated", "", 1, 1, DocumentedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(commentBodyEClass, CommentBody.class,
				"CommentBody", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getCommentBody_StartPosition(),
				theEcorePackage_1.getEInt(),
				"startPosition", null, 0, 1, CommentBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getCommentBody_EndPosition(),
				theEcorePackage_1.getEInt(),
				"endPosition", null, 0, 1, CommentBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getCommentBody_Value(),
				theEcorePackage_1.getEString(),
				"value", null, 0, 1, CommentBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleDocumentationEClass, ModuleDocumentation.class,
				"ModuleDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getModuleDocumentation_Author(),
				theEcorePackage_1.getEString(),
				"author", null, 0, 1, ModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getModuleDocumentation_Version(),
				theEcorePackage_1.getEString(),
				"version", null, 0, 1, ModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getModuleDocumentation_Since(),
				theEcorePackage_1.getEString(),
				"since", null, 0, 1, ModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleElementDocumentationEClass, ModuleElementDocumentation.class,
				"ModuleElementDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getModuleElementDocumentation_ParametersDocumentation(),
				this.getParameterDocumentation(),
				null,
				"parametersDocumentation", null, 0, -1, ModuleElementDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(parameterDocumentationEClass, ParameterDocumentation.class,
				"ParameterDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		// Initialize enums and add enum literals
		initEEnum(visibilityKindEEnum, VisibilityKind.class, "VisibilityKind"); //$NON-NLS-1$
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PRIVATE);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PROTECTED);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PUBLIC);

		initEEnum(openModeKindEEnum, OpenModeKind.class, "OpenModeKind"); //$NON-NLS-1$
		addEEnumLiteral(openModeKindEEnum, OpenModeKind.APPEND);
		addEEnumLiteral(openModeKindEEnum, OpenModeKind.OVER_WRITE);

		// Create resource
		createResource(eNS_URI);
	}

} // MtlPackageImpl
