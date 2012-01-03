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
package org.eclipse.acceleo.parser.cst.impl;

import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.Documentation;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.InitSection;
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.OpenModeKind;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateExpression;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TraceBlock;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CstPackageImpl extends EPackageImpl implements CstPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cstNodeEClass = null;

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
	private EClass moduleExtendsValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleImportsValueEClass = null;

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
	private EClass moduleElementEClass = null;

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
	private EClass templateEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass templateOverridesValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass variableEClass = null;

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
	private EClass modelExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass textExpressionEClass = null;

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
	private EClass queryEClass = null;

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
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CstPackageImpl() {
		super(eNS_URI, CstFactory.eINSTANCE);
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
	public static CstPackage init() {
		if (isInited)
			return (CstPackage)EPackage.Registry.INSTANCE.getEPackage(CstPackage.eNS_URI);

		// Obtain or create and register package
		CstPackageImpl theCstPackage = (CstPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof CstPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(eNS_URI)
				: new CstPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCstPackage.createPackageContents();

		// Initialize created meta-data
		theCstPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCstPackage.freeze();

		return theCstPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCSTNode() {
		return cstNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCSTNode_StartPosition() {
		return (EAttribute)cstNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCSTNode_EndPosition() {
		return (EAttribute)cstNodeEClass.getEStructuralFeatures().get(1);
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
	public EReference getModule_OwnedModuleElement() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_Extends() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_Imports() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModule_Documentation() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModuleExtendsValue() {
		return moduleExtendsValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleExtendsValue_Name() {
		return (EAttribute)moduleExtendsValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModuleImportsValue() {
		return moduleImportsValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleImportsValue_Name() {
		return (EAttribute)moduleImportsValueEClass.getEStructuralFeatures().get(0);
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
	public EClass getModuleElement() {
		return moduleElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleElement_Name() {
		return (EAttribute)moduleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModuleElement_Visibility() {
		return (EAttribute)moduleElementEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getComment_Body() {
		return (EAttribute)commentEClass.getEStructuralFeatures().get(0);
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
	public EReference getTemplate_Post() {
		return (EReference)templateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTemplateOverridesValue() {
		return templateOverridesValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTemplateOverridesValue_Name() {
		return (EAttribute)templateOverridesValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_Name() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_Type() {
		return (EAttribute)variableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVariable_InitExpression() {
		return (EReference)variableEClass.getEStructuralFeatures().get(2);
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
	public EClass getModelExpression() {
		return modelExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getModelExpression_Body() {
		return (EAttribute)modelExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModelExpression_Before() {
		return (EReference)modelExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModelExpression_Each() {
		return (EReference)modelExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModelExpression_After() {
		return (EReference)modelExpressionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTextExpression() {
		return textExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTextExpression_Value() {
		return (EAttribute)textExpressionEClass.getEStructuralFeatures().get(0);
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
	public EAttribute getMacro_Type() {
		return (EAttribute)macroEClass.getEStructuralFeatures().get(1);
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
	public EAttribute getQuery_Type() {
		return (EAttribute)queryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getQuery_Expression() {
		return (EReference)queryEClass.getEStructuralFeatures().get(2);
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
	public CstFactory getCstFactory() {
		return (CstFactory)getEFactoryInstance();
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
		cstNodeEClass = createEClass(CST_NODE);
		createEAttribute(cstNodeEClass, CST_NODE__START_POSITION);
		createEAttribute(cstNodeEClass, CST_NODE__END_POSITION);

		moduleEClass = createEClass(MODULE);
		createEReference(moduleEClass, MODULE__INPUT);
		createEReference(moduleEClass, MODULE__OWNED_MODULE_ELEMENT);
		createEReference(moduleEClass, MODULE__EXTENDS);
		createEReference(moduleEClass, MODULE__IMPORTS);
		createEReference(moduleEClass, MODULE__DOCUMENTATION);

		moduleExtendsValueEClass = createEClass(MODULE_EXTENDS_VALUE);
		createEAttribute(moduleExtendsValueEClass, MODULE_EXTENDS_VALUE__NAME);

		moduleImportsValueEClass = createEClass(MODULE_IMPORTS_VALUE);
		createEAttribute(moduleImportsValueEClass, MODULE_IMPORTS_VALUE__NAME);

		typedModelEClass = createEClass(TYPED_MODEL);
		createEReference(typedModelEClass, TYPED_MODEL__TAKES_TYPES_FROM);

		moduleElementEClass = createEClass(MODULE_ELEMENT);
		createEAttribute(moduleElementEClass, MODULE_ELEMENT__NAME);
		createEAttribute(moduleElementEClass, MODULE_ELEMENT__VISIBILITY);

		commentEClass = createEClass(COMMENT);
		createEAttribute(commentEClass, COMMENT__BODY);

		templateEClass = createEClass(TEMPLATE);
		createEReference(templateEClass, TEMPLATE__OVERRIDES);
		createEReference(templateEClass, TEMPLATE__PARAMETER);
		createEReference(templateEClass, TEMPLATE__GUARD);
		createEReference(templateEClass, TEMPLATE__POST);

		templateOverridesValueEClass = createEClass(TEMPLATE_OVERRIDES_VALUE);
		createEAttribute(templateOverridesValueEClass, TEMPLATE_OVERRIDES_VALUE__NAME);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__NAME);
		createEAttribute(variableEClass, VARIABLE__TYPE);
		createEReference(variableEClass, VARIABLE__INIT_EXPRESSION);

		templateExpressionEClass = createEClass(TEMPLATE_EXPRESSION);

		modelExpressionEClass = createEClass(MODEL_EXPRESSION);
		createEAttribute(modelExpressionEClass, MODEL_EXPRESSION__BODY);
		createEReference(modelExpressionEClass, MODEL_EXPRESSION__BEFORE);
		createEReference(modelExpressionEClass, MODEL_EXPRESSION__EACH);
		createEReference(modelExpressionEClass, MODEL_EXPRESSION__AFTER);

		textExpressionEClass = createEClass(TEXT_EXPRESSION);
		createEAttribute(textExpressionEClass, TEXT_EXPRESSION__VALUE);

		blockEClass = createEClass(BLOCK);
		createEReference(blockEClass, BLOCK__INIT);
		createEReference(blockEClass, BLOCK__BODY);

		initSectionEClass = createEClass(INIT_SECTION);
		createEReference(initSectionEClass, INIT_SECTION__VARIABLE);

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
		createEAttribute(macroEClass, MACRO__TYPE);

		queryEClass = createEClass(QUERY);
		createEReference(queryEClass, QUERY__PARAMETER);
		createEAttribute(queryEClass, QUERY__TYPE);
		createEReference(queryEClass, QUERY__EXPRESSION);

		documentationEClass = createEClass(DOCUMENTATION);

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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		moduleEClass.getESuperTypes().add(theEcorePackage.getEPackage());
		moduleEClass.getESuperTypes().add(this.getCSTNode());
		moduleExtendsValueEClass.getESuperTypes().add(this.getCSTNode());
		moduleImportsValueEClass.getESuperTypes().add(this.getCSTNode());
		typedModelEClass.getESuperTypes().add(this.getCSTNode());
		moduleElementEClass.getESuperTypes().add(this.getCSTNode());
		commentEClass.getESuperTypes().add(this.getModuleElement());
		commentEClass.getESuperTypes().add(this.getTemplateExpression());
		templateEClass.getESuperTypes().add(this.getBlock());
		templateEClass.getESuperTypes().add(this.getModuleElement());
		templateOverridesValueEClass.getESuperTypes().add(this.getCSTNode());
		variableEClass.getESuperTypes().add(this.getCSTNode());
		templateExpressionEClass.getESuperTypes().add(this.getCSTNode());
		modelExpressionEClass.getESuperTypes().add(this.getTemplateExpression());
		textExpressionEClass.getESuperTypes().add(this.getTemplateExpression());
		blockEClass.getESuperTypes().add(this.getTemplateExpression());
		initSectionEClass.getESuperTypes().add(this.getCSTNode());
		protectedAreaBlockEClass.getESuperTypes().add(this.getBlock());
		forBlockEClass.getESuperTypes().add(this.getBlock());
		ifBlockEClass.getESuperTypes().add(this.getBlock());
		letBlockEClass.getESuperTypes().add(this.getBlock());
		fileBlockEClass.getESuperTypes().add(this.getBlock());
		traceBlockEClass.getESuperTypes().add(this.getBlock());
		macroEClass.getESuperTypes().add(this.getBlock());
		macroEClass.getESuperTypes().add(this.getModuleElement());
		queryEClass.getESuperTypes().add(this.getModuleElement());
		documentationEClass.getESuperTypes().add(this.getComment());

		// Initialize classes and features; add operations and parameters
		initEClass(cstNodeEClass, CSTNode.class,
				"CSTNode", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getCSTNode_StartPosition(),
				theEcorePackage.getEInt(),
				"startPosition", null, 0, 1, CSTNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getCSTNode_EndPosition(),
				theEcorePackage.getEInt(),
				"endPosition", null, 0, 1, CSTNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleEClass, Module.class,
				"Module", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getModule_Input(),
				this.getTypedModel(),
				null,
				"input", null, 1, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_OwnedModuleElement(),
				this.getModuleElement(),
				null,
				"ownedModuleElement", null, 1, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_Extends(),
				this.getModuleExtendsValue(),
				null,
				"extends", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_Imports(),
				this.getModuleImportsValue(),
				null,
				"imports", null, 0, -1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModule_Documentation(),
				this.getDocumentation(),
				null,
				"documentation", null, 0, 1, Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleExtendsValueEClass, ModuleExtendsValue.class,
				"ModuleExtendsValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getModuleExtendsValue_Name(),
				theEcorePackage.getEString(),
				"name", null, 1, 1, ModuleExtendsValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleImportsValueEClass, ModuleImportsValue.class,
				"ModuleImportsValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getModuleImportsValue_Name(),
				theEcorePackage.getEString(),
				"name", null, 1, 1, ModuleImportsValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(typedModelEClass, TypedModel.class,
				"TypedModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTypedModel_TakesTypesFrom(),
				theEcorePackage.getEPackage(),
				null,
				"takesTypesFrom", null, 1, -1, TypedModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(moduleElementEClass, ModuleElement.class,
				"ModuleElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getModuleElement_Name(),
				theEcorePackage.getEString(),
				"name", null, 1, 1, ModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getModuleElement_Visibility(),
				this.getVisibilityKind(),
				"visibility", null, 1, 1, ModuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(commentEClass, Comment.class,
				"Comment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getComment_Body(),
				theEcorePackage.getEString(),
				"body", null, 1, 1, Comment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateEClass, Template.class,
				"Template", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTemplate_Overrides(),
				this.getTemplateOverridesValue(),
				null,
				"overrides", null, 0, -1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplate_Parameter(),
				this.getVariable(),
				null,
				"parameter", null, 0, -1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplate_Guard(),
				this.getModelExpression(),
				null,
				"guard", null, 0, 1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getTemplate_Post(),
				this.getModelExpression(),
				null,
				"post", null, 0, 1, Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateOverridesValueEClass, TemplateOverridesValue.class,
				"TemplateOverridesValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getTemplateOverridesValue_Name(),
				theEcorePackage.getEString(),
				"name", null, 1, 1, TemplateOverridesValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(variableEClass, Variable.class,
				"Variable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getVariable_Name(),
				theEcorePackage.getEString(),
				"name", null, 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getVariable_Type(),
				theEcorePackage.getEString(),
				"type", null, 1, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getVariable_InitExpression(),
				this.getModelExpression(),
				null,
				"initExpression", null, 0, 1, Variable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(templateExpressionEClass, TemplateExpression.class,
				"TemplateExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(modelExpressionEClass, ModelExpression.class,
				"ModelExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getModelExpression_Body(),
				theEcorePackage.getEString(),
				"body", null, 1, 1, ModelExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModelExpression_Before(),
				this.getModelExpression(),
				null,
				"before", null, 0, 1, ModelExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModelExpression_Each(),
				this.getModelExpression(),
				null,
				"each", null, 0, 1, ModelExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getModelExpression_After(),
				this.getModelExpression(),
				null,
				"after", null, 0, 1, ModelExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(textExpressionEClass, TextExpression.class,
				"TextExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getTextExpression_Value(),
				theEcorePackage.getEString(),
				"value", null, 1, 1, TextExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(blockEClass, Block.class,
				"Block", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getBlock_Init(),
				this.getInitSection(),
				null,
				"init", null, 0, 1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getBlock_Body(),
				this.getTemplateExpression(),
				null,
				"body", null, 0, -1, Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(initSectionEClass, InitSection.class,
				"InitSection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getInitSection_Variable(),
				this.getVariable(),
				null,
				"variable", null, 1, -1, InitSection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(protectedAreaBlockEClass, ProtectedAreaBlock.class,
				"ProtectedAreaBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getProtectedAreaBlock_Marker(),
				this.getModelExpression(),
				null,
				"marker", null, 1, 1, ProtectedAreaBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(forBlockEClass, ForBlock.class,
				"ForBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getForBlock_LoopVariable(),
				this.getVariable(),
				null,
				"loopVariable", null, 1, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_IterSet(),
				this.getModelExpression(),
				null,
				"iterSet", null, 1, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_Before(),
				this.getModelExpression(),
				null,
				"before", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_Each(),
				this.getModelExpression(),
				null,
				"each", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_After(),
				this.getModelExpression(),
				null,
				"after", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getForBlock_Guard(),
				this.getModelExpression(),
				null,
				"guard", null, 0, 1, ForBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(ifBlockEClass, IfBlock.class,
				"IfBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getIfBlock_IfExpr(),
				this.getModelExpression(),
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
				this.getVariable(),
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
				this.getModelExpression(),
				null,
				"fileUrl", null, 1, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getFileBlock_UniqId(),
				this.getModelExpression(),
				null,
				"uniqId", null, 0, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getFileBlock_Charset(),
				this.getModelExpression(),
				null,
				"charset", null, 0, 1, FileBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(traceBlockEClass, TraceBlock.class,
				"TraceBlock", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getTraceBlock_ModelElement(),
				this.getModelExpression(),
				null,
				"modelElement", null, 1, 1, TraceBlock.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(macroEClass, Macro.class,
				"Macro", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getMacro_Parameter(),
				this.getVariable(),
				null,
				"parameter", null, 0, -1, Macro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getMacro_Type(),
				theEcorePackage.getEString(),
				"type", null, 1, 1, Macro.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(queryEClass, Query.class,
				"Query", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getQuery_Parameter(),
				this.getVariable(),
				null,
				"parameter", null, 0, -1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(
				getQuery_Type(),
				theEcorePackage.getEString(),
				"type", null, 1, 1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getQuery_Expression(),
				this.getModelExpression(),
				null,
				"expression", null, 0, 1, Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(documentationEClass, Documentation.class,
				"Documentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

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

} // CstPackageImpl
