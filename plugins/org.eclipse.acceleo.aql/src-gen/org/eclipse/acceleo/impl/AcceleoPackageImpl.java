/**
 * Copyright (c) 2008, 2024 Obeo.
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

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.AcceleoFactory;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.ErrorBlockComment;
import org.eclipse.acceleo.ErrorComment;
import org.eclipse.acceleo.ErrorExpression;
import org.eclipse.acceleo.ErrorExpressionStatement;
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.ErrorForStatement;
import org.eclipse.acceleo.ErrorIfStatement;
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.ErrorLetStatement;
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.ErrorModule;
import org.eclipse.acceleo.ErrorModuleDocumentation;
import org.eclipse.acceleo.ErrorModuleElementDocumentation;
import org.eclipse.acceleo.ErrorModuleReference;
import org.eclipse.acceleo.ErrorProtectedArea;
import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.ErrorTemplate;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LeafStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.impl.AstPackageImpl;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class AcceleoPackageImpl extends EPackageImpl implements AcceleoPackage {
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
	private EClass errorModuleEClass = null;

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
	private EClass errorMetamodelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass importEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorImportEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass moduleReferenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorModuleReferenceEClass = null;

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
	private EClass blockCommentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorBlockCommentEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorCommentEClass = null;

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
	private EClass documentationEClass = null;

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
	private EClass errorModuleDocumentationEClass = null;

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
	private EClass errorModuleElementDocumentationEClass = null;

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
	private EClass documentedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass acceleoASTNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorEClass = null;

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
	private EClass typedElementEClass = null;

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
	private EClass errorTemplateEClass = null;

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
	private EClass errorQueryEClass = null;

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
	private EClass errorExpressionEClass = null;

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
	private EClass errorVariableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass bindingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorBindingEClass = null;

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
	private EClass leafStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass expressionStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorExpressionStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass protectedAreaEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorProtectedAreaEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass forStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorForStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ifStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorIfStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass letStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorLetStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass fileStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorFileStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass textStatementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass newLineStatementEClass = null;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType astResultEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType moduleQualifiedNameEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType acceleoAstResultEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.AcceleoPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AcceleoPackageImpl() {
		super(eNS_URI, AcceleoFactory.eINSTANCE);
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
	 * This method is used to initialize {@link AcceleoPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AcceleoPackage init() {
		if (isInited)
			return (AcceleoPackage)EPackage.Registry.INSTANCE.getEPackage(AcceleoPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAcceleoPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AcceleoPackageImpl theAcceleoPackage = registeredAcceleoPackage instanceof AcceleoPackageImpl
				? (AcceleoPackageImpl)registeredAcceleoPackage
				: new AcceleoPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(AstPackage.eNS_URI);
		AstPackageImpl theAstPackage = (AstPackageImpl)(registeredPackage instanceof AstPackageImpl
				? registeredPackage
				: AstPackage.eINSTANCE);

		// Create package meta-data objects
		theAcceleoPackage.createPackageContents();
		theAstPackage.createPackageContents();

		// Initialize created meta-data
		theAcceleoPackage.initializePackageContents();
		theAstPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAcceleoPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AcceleoPackage.eNS_URI, theAcceleoPackage);
		return theAcceleoPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModule() {
		return moduleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModule_Metamodels() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModule_Extends() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModule_Imports() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModule_ModuleElements() {
		return (EReference)moduleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModule_StartHeaderPosition() {
		return (EAttribute)moduleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModule_EndHeaderPosition() {
		return (EAttribute)moduleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorModule() {
		return errorModuleEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorModule_MissingOpenParenthesis() {
		return (EAttribute)errorModuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorModule_MissingEPackage() {
		return (EAttribute)errorModuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorModule_MissingCloseParenthesis() {
		return (EAttribute)errorModuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorModule_MissingEndHeader() {
		return (EAttribute)errorModuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getMetamodel() {
		return metamodelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getMetamodel_ReferencedPackage() {
		return (EReference)metamodelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorMetamodel() {
		return errorMetamodelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorMetamodel_Fragment() {
		return (EAttribute)errorMetamodelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorMetamodel_MissingEndQuote() {
		return (EAttribute)errorMetamodelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getImport() {
		return importEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getImport_Module() {
		return (EReference)importEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorImport() {
		return errorImportEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorImport_MissingEnd() {
		return (EAttribute)errorImportEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModuleReference() {
		return moduleReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleReference_QualifiedName() {
		return (EAttribute)moduleReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorModuleReference() {
		return errorModuleReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModuleElement() {
		return moduleElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getComment() {
		return commentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getComment_Body() {
		return (EReference)commentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBlockComment() {
		return blockCommentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorBlockComment() {
		return errorBlockCommentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorComment() {
		return errorCommentEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorComment_MissingEndHeader() {
		return (EAttribute)errorCommentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCommentBody() {
		return commentBodyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCommentBody_Value() {
		return (EAttribute)commentBodyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDocumentation() {
		return documentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getDocumentation_DocumentedElement() {
		return (EReference)documentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModuleDocumentation() {
		return moduleDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleDocumentation_Author() {
		return (EAttribute)moduleDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleDocumentation_Version() {
		return (EAttribute)moduleDocumentationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModuleDocumentation_Since() {
		return (EAttribute)moduleDocumentationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorModuleDocumentation() {
		return errorModuleDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorModuleDocumentation_MissingEndHeader() {
		return (EAttribute)errorModuleDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getModuleElementDocumentation() {
		return moduleElementDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getModuleElementDocumentation_ParameterDocumentation() {
		return (EReference)moduleElementDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorModuleElementDocumentation() {
		return errorModuleElementDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorModuleElementDocumentation_MissingEndHeader() {
		return (EAttribute)errorModuleElementDocumentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getParameterDocumentation() {
		return parameterDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDocumentedElement() {
		return documentedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getDocumentedElement_Documentation() {
		return (EReference)documentedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDocumentedElement_Deprecated() {
		return (EAttribute)documentedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getAcceleoASTNode() {
		return acceleoASTNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getError() {
		return errorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBlock() {
		return blockEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBlock_Statements() {
		return (EReference)blockEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getBlock_Inlined() {
		return (EAttribute)blockEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypedElement() {
		return typedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTypedElement_Type() {
		return (EAttribute)typedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTypedElement_TypeAql() {
		return (EReference)typedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTemplate() {
		return templateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTemplate_Parameters() {
		return (EReference)templateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTemplate_Guard() {
		return (EReference)templateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTemplate_Post() {
		return (EReference)templateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTemplate_Main() {
		return (EAttribute)templateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTemplate_Visibility() {
		return (EAttribute)templateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTemplate_Body() {
		return (EReference)templateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorTemplate() {
		return errorTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingVisibility() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingName() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingOpenParenthesis() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingParameters() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingCloseParenthesis() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingGuardOpenParenthesis() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingGuardCloseParenthesis() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingPostCloseParenthesis() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingEndHeader() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorTemplate_MissingEnd() {
		return (EAttribute)errorTemplateEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getQuery() {
		return queryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getQuery_Parameters() {
		return (EReference)queryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getQuery_Visibility() {
		return (EAttribute)queryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getQuery_Body() {
		return (EReference)queryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorQuery() {
		return errorQueryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingVisibility() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingName() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingOpenParenthesis() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingParameters() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingCloseParenthesis() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingColon() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingType() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingEqual() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorQuery_MissingEnd() {
		return (EAttribute)errorQueryEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getExpression_Ast() {
		return (EAttribute)expressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExpression_Aql() {
		return (EReference)expressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorExpression() {
		return errorExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorVariable() {
		return errorVariableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorVariable_MissingName() {
		return (EAttribute)errorVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorVariable_MissingColon() {
		return (EAttribute)errorVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorVariable_MissingType() {
		return (EAttribute)errorVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBinding() {
		return bindingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBinding_InitExpression() {
		return (EReference)bindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorBinding() {
		return errorBindingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorBinding_MissingName() {
		return (EAttribute)errorBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorBinding_MissingColon() {
		return (EAttribute)errorBindingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorBinding_MissingType() {
		return (EAttribute)errorBindingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorBinding_MissingAffectationSymbole() {
		return (EAttribute)errorBindingEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorBinding_MissingAffectationSymbolePosition() {
		return (EAttribute)errorBindingEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStatement() {
		return statementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStatement_MultiLines() {
		return (EAttribute)statementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLeafStatement() {
		return leafStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getLeafStatement_NewLineNeeded() {
		return (EAttribute)leafStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExpressionStatement() {
		return expressionStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getExpressionStatement_Expression() {
		return (EReference)expressionStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorExpressionStatement() {
		return errorExpressionStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorExpressionStatement_MissingEndHeader() {
		return (EAttribute)errorExpressionStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getProtectedArea() {
		return protectedAreaEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProtectedArea_Id() {
		return (EReference)protectedAreaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProtectedArea_Body() {
		return (EReference)protectedAreaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProtectedArea_StartTagPrefix() {
		return (EReference)protectedAreaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getProtectedArea_EndTagPrefix() {
		return (EReference)protectedAreaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorProtectedArea() {
		return errorProtectedAreaEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorProtectedArea_MissingOpenParenthesis() {
		return (EAttribute)errorProtectedAreaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorProtectedArea_MissingCloseParenthesis() {
		return (EAttribute)errorProtectedAreaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorProtectedArea_MissingStartTagPrefixCloseParenthesis() {
		return (EAttribute)errorProtectedAreaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorProtectedArea_MissingEndTagPrefixCloseParenthesis() {
		return (EAttribute)errorProtectedAreaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorProtectedArea_MissingEndHeader() {
		return (EAttribute)errorProtectedAreaEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorProtectedArea_MissingEnd() {
		return (EAttribute)errorProtectedAreaEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getForStatement() {
		return forStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getForStatement_Binding() {
		return (EReference)forStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getForStatement_Separator() {
		return (EReference)forStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getForStatement_Body() {
		return (EReference)forStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorForStatement() {
		return errorForStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorForStatement_MissingOpenParenthesis() {
		return (EAttribute)errorForStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorForStatement_MissingBinding() {
		return (EAttribute)errorForStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorForStatement_MissingCloseParenthesis() {
		return (EAttribute)errorForStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorForStatement_MissingSeparatorCloseParenthesis() {
		return (EAttribute)errorForStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorForStatement_MissingEndHeader() {
		return (EAttribute)errorForStatementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorForStatement_MissingEnd() {
		return (EAttribute)errorForStatementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getIfStatement() {
		return ifStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getIfStatement_Condition() {
		return (EReference)ifStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getIfStatement_Then() {
		return (EReference)ifStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getIfStatement_Else() {
		return (EReference)ifStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorIfStatement() {
		return errorIfStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorIfStatement_MissingOpenParenthesis() {
		return (EAttribute)errorIfStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorIfStatement_MissingCloseParenthesis() {
		return (EAttribute)errorIfStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorIfStatement_MissingEndHeader() {
		return (EAttribute)errorIfStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorIfStatement_MissingEnd() {
		return (EAttribute)errorIfStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLetStatement() {
		return letStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLetStatement_Variables() {
		return (EReference)letStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLetStatement_Body() {
		return (EReference)letStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorLetStatement() {
		return errorLetStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorLetStatement_MissingBindings() {
		return (EAttribute)errorLetStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorLetStatement_MissingEndHeader() {
		return (EAttribute)errorLetStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorLetStatement_MissingEnd() {
		return (EAttribute)errorLetStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getFileStatement() {
		return fileStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getFileStatement_Mode() {
		return (EAttribute)fileStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getFileStatement_Url() {
		return (EReference)fileStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getFileStatement_Charset() {
		return (EReference)fileStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getFileStatement_Body() {
		return (EReference)fileStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorFileStatement() {
		return errorFileStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorFileStatement_MissingOpenParenthesis() {
		return (EAttribute)errorFileStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorFileStatement_MissingComma() {
		return (EAttribute)errorFileStatementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorFileStatement_MissingOpenMode() {
		return (EAttribute)errorFileStatementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorFileStatement_MissingCloseParenthesis() {
		return (EAttribute)errorFileStatementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorFileStatement_MissingEndHeader() {
		return (EAttribute)errorFileStatementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorFileStatement_MissingEnd() {
		return (EAttribute)errorFileStatementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTextStatement() {
		return textStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getTextStatement_Value() {
		return (EAttribute)textStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getNewLineStatement() {
		return newLineStatementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getNewLineStatement_IndentationNeeded() {
		return (EAttribute)newLineStatementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getVisibilityKind() {
		return visibilityKindEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getOpenModeKind() {
		return openModeKindEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getASTResult() {
		return astResultEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getModuleQualifiedName() {
		return moduleQualifiedNameEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AcceleoFactory getAcceleoFactory() {
		return (AcceleoFactory)getEFactoryInstance();
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
		createEReference(moduleEClass, MODULE__METAMODELS);
		createEReference(moduleEClass, MODULE__EXTENDS);
		createEReference(moduleEClass, MODULE__IMPORTS);
		createEReference(moduleEClass, MODULE__MODULE_ELEMENTS);
		createEAttribute(moduleEClass, MODULE__START_HEADER_POSITION);
		createEAttribute(moduleEClass, MODULE__END_HEADER_POSITION);
		createEAttribute(moduleEClass, MODULE__AST);
		createEAttribute(moduleEClass, MODULE__ENCODING);

		errorModuleEClass = createEClass(ERROR_MODULE);
		createEAttribute(errorModuleEClass, ERROR_MODULE__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorModuleEClass, ERROR_MODULE__MISSING_EPACKAGE);
		createEAttribute(errorModuleEClass, ERROR_MODULE__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorModuleEClass, ERROR_MODULE__MISSING_END_HEADER);

		metamodelEClass = createEClass(METAMODEL);
		createEReference(metamodelEClass, METAMODEL__REFERENCED_PACKAGE);

		errorMetamodelEClass = createEClass(ERROR_METAMODEL);
		createEAttribute(errorMetamodelEClass, ERROR_METAMODEL__FRAGMENT);
		createEAttribute(errorMetamodelEClass, ERROR_METAMODEL__MISSING_END_QUOTE);

		importEClass = createEClass(IMPORT);
		createEReference(importEClass, IMPORT__MODULE);

		errorImportEClass = createEClass(ERROR_IMPORT);
		createEAttribute(errorImportEClass, ERROR_IMPORT__MISSING_END);

		moduleReferenceEClass = createEClass(MODULE_REFERENCE);
		createEAttribute(moduleReferenceEClass, MODULE_REFERENCE__QUALIFIED_NAME);

		errorModuleReferenceEClass = createEClass(ERROR_MODULE_REFERENCE);

		moduleElementEClass = createEClass(MODULE_ELEMENT);

		blockCommentEClass = createEClass(BLOCK_COMMENT);

		errorBlockCommentEClass = createEClass(ERROR_BLOCK_COMMENT);

		commentEClass = createEClass(COMMENT);
		createEReference(commentEClass, COMMENT__BODY);

		errorCommentEClass = createEClass(ERROR_COMMENT);
		createEAttribute(errorCommentEClass, ERROR_COMMENT__MISSING_END_HEADER);

		commentBodyEClass = createEClass(COMMENT_BODY);
		createEAttribute(commentBodyEClass, COMMENT_BODY__VALUE);

		documentationEClass = createEClass(DOCUMENTATION);
		createEReference(documentationEClass, DOCUMENTATION__DOCUMENTED_ELEMENT);

		moduleDocumentationEClass = createEClass(MODULE_DOCUMENTATION);
		createEAttribute(moduleDocumentationEClass, MODULE_DOCUMENTATION__AUTHOR);
		createEAttribute(moduleDocumentationEClass, MODULE_DOCUMENTATION__VERSION);
		createEAttribute(moduleDocumentationEClass, MODULE_DOCUMENTATION__SINCE);

		errorModuleDocumentationEClass = createEClass(ERROR_MODULE_DOCUMENTATION);
		createEAttribute(errorModuleDocumentationEClass, ERROR_MODULE_DOCUMENTATION__MISSING_END_HEADER);

		moduleElementDocumentationEClass = createEClass(MODULE_ELEMENT_DOCUMENTATION);
		createEReference(moduleElementDocumentationEClass,
				MODULE_ELEMENT_DOCUMENTATION__PARAMETER_DOCUMENTATION);

		errorModuleElementDocumentationEClass = createEClass(ERROR_MODULE_ELEMENT_DOCUMENTATION);
		createEAttribute(errorModuleElementDocumentationEClass,
				ERROR_MODULE_ELEMENT_DOCUMENTATION__MISSING_END_HEADER);

		parameterDocumentationEClass = createEClass(PARAMETER_DOCUMENTATION);

		documentedElementEClass = createEClass(DOCUMENTED_ELEMENT);
		createEReference(documentedElementEClass, DOCUMENTED_ELEMENT__DOCUMENTATION);
		createEAttribute(documentedElementEClass, DOCUMENTED_ELEMENT__DEPRECATED);

		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

		acceleoASTNodeEClass = createEClass(ACCELEO_AST_NODE);

		errorEClass = createEClass(ERROR);

		blockEClass = createEClass(BLOCK);
		createEReference(blockEClass, BLOCK__STATEMENTS);
		createEAttribute(blockEClass, BLOCK__INLINED);

		typedElementEClass = createEClass(TYPED_ELEMENT);
		createEAttribute(typedElementEClass, TYPED_ELEMENT__TYPE);
		createEReference(typedElementEClass, TYPED_ELEMENT__TYPE_AQL);

		templateEClass = createEClass(TEMPLATE);
		createEReference(templateEClass, TEMPLATE__PARAMETERS);
		createEReference(templateEClass, TEMPLATE__GUARD);
		createEReference(templateEClass, TEMPLATE__POST);
		createEAttribute(templateEClass, TEMPLATE__MAIN);
		createEAttribute(templateEClass, TEMPLATE__VISIBILITY);
		createEReference(templateEClass, TEMPLATE__BODY);

		errorTemplateEClass = createEClass(ERROR_TEMPLATE);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_VISIBILITY);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_NAME);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_PARAMETERS);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_END_HEADER);
		createEAttribute(errorTemplateEClass, ERROR_TEMPLATE__MISSING_END);

		queryEClass = createEClass(QUERY);
		createEReference(queryEClass, QUERY__PARAMETERS);
		createEAttribute(queryEClass, QUERY__VISIBILITY);
		createEReference(queryEClass, QUERY__BODY);

		errorQueryEClass = createEClass(ERROR_QUERY);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_VISIBILITY);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_NAME);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_PARAMETERS);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_COLON);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_TYPE);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_EQUAL);
		createEAttribute(errorQueryEClass, ERROR_QUERY__MISSING_END);

		expressionEClass = createEClass(EXPRESSION);
		createEAttribute(expressionEClass, EXPRESSION__AST);
		createEReference(expressionEClass, EXPRESSION__AQL);

		errorExpressionEClass = createEClass(ERROR_EXPRESSION);

		variableEClass = createEClass(VARIABLE);

		errorVariableEClass = createEClass(ERROR_VARIABLE);
		createEAttribute(errorVariableEClass, ERROR_VARIABLE__MISSING_NAME);
		createEAttribute(errorVariableEClass, ERROR_VARIABLE__MISSING_COLON);
		createEAttribute(errorVariableEClass, ERROR_VARIABLE__MISSING_TYPE);

		bindingEClass = createEClass(BINDING);
		createEReference(bindingEClass, BINDING__INIT_EXPRESSION);

		errorBindingEClass = createEClass(ERROR_BINDING);
		createEAttribute(errorBindingEClass, ERROR_BINDING__MISSING_NAME);
		createEAttribute(errorBindingEClass, ERROR_BINDING__MISSING_COLON);
		createEAttribute(errorBindingEClass, ERROR_BINDING__MISSING_TYPE);
		createEAttribute(errorBindingEClass, ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE);
		createEAttribute(errorBindingEClass, ERROR_BINDING__MISSING_AFFECTATION_SYMBOLE_POSITION);

		statementEClass = createEClass(STATEMENT);
		createEAttribute(statementEClass, STATEMENT__MULTI_LINES);

		leafStatementEClass = createEClass(LEAF_STATEMENT);
		createEAttribute(leafStatementEClass, LEAF_STATEMENT__NEW_LINE_NEEDED);

		expressionStatementEClass = createEClass(EXPRESSION_STATEMENT);
		createEReference(expressionStatementEClass, EXPRESSION_STATEMENT__EXPRESSION);

		errorExpressionStatementEClass = createEClass(ERROR_EXPRESSION_STATEMENT);
		createEAttribute(errorExpressionStatementEClass, ERROR_EXPRESSION_STATEMENT__MISSING_END_HEADER);

		protectedAreaEClass = createEClass(PROTECTED_AREA);
		createEReference(protectedAreaEClass, PROTECTED_AREA__ID);
		createEReference(protectedAreaEClass, PROTECTED_AREA__BODY);
		createEReference(protectedAreaEClass, PROTECTED_AREA__START_TAG_PREFIX);
		createEReference(protectedAreaEClass, PROTECTED_AREA__END_TAG_PREFIX);

		errorProtectedAreaEClass = createEClass(ERROR_PROTECTED_AREA);
		createEAttribute(errorProtectedAreaEClass, ERROR_PROTECTED_AREA__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorProtectedAreaEClass, ERROR_PROTECTED_AREA__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorProtectedAreaEClass,
				ERROR_PROTECTED_AREA__MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS);
		createEAttribute(errorProtectedAreaEClass,
				ERROR_PROTECTED_AREA__MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS);
		createEAttribute(errorProtectedAreaEClass, ERROR_PROTECTED_AREA__MISSING_END_HEADER);
		createEAttribute(errorProtectedAreaEClass, ERROR_PROTECTED_AREA__MISSING_END);

		forStatementEClass = createEClass(FOR_STATEMENT);
		createEReference(forStatementEClass, FOR_STATEMENT__BINDING);
		createEReference(forStatementEClass, FOR_STATEMENT__SEPARATOR);
		createEReference(forStatementEClass, FOR_STATEMENT__BODY);

		errorForStatementEClass = createEClass(ERROR_FOR_STATEMENT);
		createEAttribute(errorForStatementEClass, ERROR_FOR_STATEMENT__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorForStatementEClass, ERROR_FOR_STATEMENT__MISSING_BINDING);
		createEAttribute(errorForStatementEClass, ERROR_FOR_STATEMENT__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorForStatementEClass, ERROR_FOR_STATEMENT__MISSING_SEPARATOR_CLOSE_PARENTHESIS);
		createEAttribute(errorForStatementEClass, ERROR_FOR_STATEMENT__MISSING_END_HEADER);
		createEAttribute(errorForStatementEClass, ERROR_FOR_STATEMENT__MISSING_END);

		ifStatementEClass = createEClass(IF_STATEMENT);
		createEReference(ifStatementEClass, IF_STATEMENT__CONDITION);
		createEReference(ifStatementEClass, IF_STATEMENT__THEN);
		createEReference(ifStatementEClass, IF_STATEMENT__ELSE);

		errorIfStatementEClass = createEClass(ERROR_IF_STATEMENT);
		createEAttribute(errorIfStatementEClass, ERROR_IF_STATEMENT__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorIfStatementEClass, ERROR_IF_STATEMENT__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorIfStatementEClass, ERROR_IF_STATEMENT__MISSING_END_HEADER);
		createEAttribute(errorIfStatementEClass, ERROR_IF_STATEMENT__MISSING_END);

		letStatementEClass = createEClass(LET_STATEMENT);
		createEReference(letStatementEClass, LET_STATEMENT__VARIABLES);
		createEReference(letStatementEClass, LET_STATEMENT__BODY);

		errorLetStatementEClass = createEClass(ERROR_LET_STATEMENT);
		createEAttribute(errorLetStatementEClass, ERROR_LET_STATEMENT__MISSING_BINDINGS);
		createEAttribute(errorLetStatementEClass, ERROR_LET_STATEMENT__MISSING_END_HEADER);
		createEAttribute(errorLetStatementEClass, ERROR_LET_STATEMENT__MISSING_END);

		fileStatementEClass = createEClass(FILE_STATEMENT);
		createEAttribute(fileStatementEClass, FILE_STATEMENT__MODE);
		createEReference(fileStatementEClass, FILE_STATEMENT__URL);
		createEReference(fileStatementEClass, FILE_STATEMENT__CHARSET);
		createEReference(fileStatementEClass, FILE_STATEMENT__BODY);

		errorFileStatementEClass = createEClass(ERROR_FILE_STATEMENT);
		createEAttribute(errorFileStatementEClass, ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS);
		createEAttribute(errorFileStatementEClass, ERROR_FILE_STATEMENT__MISSING_COMMA);
		createEAttribute(errorFileStatementEClass, ERROR_FILE_STATEMENT__MISSING_OPEN_MODE);
		createEAttribute(errorFileStatementEClass, ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS);
		createEAttribute(errorFileStatementEClass, ERROR_FILE_STATEMENT__MISSING_END_HEADER);
		createEAttribute(errorFileStatementEClass, ERROR_FILE_STATEMENT__MISSING_END);

		textStatementEClass = createEClass(TEXT_STATEMENT);
		createEAttribute(textStatementEClass, TEXT_STATEMENT__VALUE);

		newLineStatementEClass = createEClass(NEW_LINE_STATEMENT);
		createEAttribute(newLineStatementEClass, NEW_LINE_STATEMENT__INDENTATION_NEEDED);

		// Create enums
		visibilityKindEEnum = createEEnum(VISIBILITY_KIND);
		openModeKindEEnum = createEEnum(OPEN_MODE_KIND);

		// Create data types
		astResultEDataType = createEDataType(AST_RESULT);
		moduleQualifiedNameEDataType = createEDataType(MODULE_QUALIFIED_NAME);
		acceleoAstResultEDataType = createEDataType(ACCELEO_AST_RESULT);
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
		AstPackage theAstPackage = (AstPackage)EPackage.Registry.INSTANCE.getEPackage(AstPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		moduleEClass.getESuperTypes().add(this.getNamedElement());
		moduleEClass.getESuperTypes().add(this.getDocumentedElement());
		moduleEClass.getESuperTypes().add(this.getAcceleoASTNode());
		errorModuleEClass.getESuperTypes().add(this.getError());
		errorModuleEClass.getESuperTypes().add(this.getModule());
		metamodelEClass.getESuperTypes().add(this.getAcceleoASTNode());
		errorMetamodelEClass.getESuperTypes().add(this.getError());
		errorMetamodelEClass.getESuperTypes().add(this.getMetamodel());
		importEClass.getESuperTypes().add(this.getAcceleoASTNode());
		errorImportEClass.getESuperTypes().add(this.getError());
		errorImportEClass.getESuperTypes().add(this.getImport());
		moduleReferenceEClass.getESuperTypes().add(this.getAcceleoASTNode());
		errorModuleReferenceEClass.getESuperTypes().add(this.getError());
		errorModuleReferenceEClass.getESuperTypes().add(this.getModuleReference());
		moduleElementEClass.getESuperTypes().add(this.getAcceleoASTNode());
		blockCommentEClass.getESuperTypes().add(this.getComment());
		errorBlockCommentEClass.getESuperTypes().add(this.getErrorComment());
		errorBlockCommentEClass.getESuperTypes().add(this.getBlockComment());
		commentEClass.getESuperTypes().add(this.getModuleElement());
		commentEClass.getESuperTypes().add(this.getStatement());
		errorCommentEClass.getESuperTypes().add(this.getError());
		errorCommentEClass.getESuperTypes().add(this.getComment());
		commentBodyEClass.getESuperTypes().add(this.getAcceleoASTNode());
		documentationEClass.getESuperTypes().add(this.getComment());
		moduleDocumentationEClass.getESuperTypes().add(this.getDocumentation());
		errorModuleDocumentationEClass.getESuperTypes().add(this.getError());
		errorModuleDocumentationEClass.getESuperTypes().add(this.getModuleDocumentation());
		moduleElementDocumentationEClass.getESuperTypes().add(this.getDocumentation());
		errorModuleElementDocumentationEClass.getESuperTypes().add(this.getError());
		errorModuleElementDocumentationEClass.getESuperTypes().add(this.getModuleElementDocumentation());
		parameterDocumentationEClass.getESuperTypes().add(this.getComment());
		documentedElementEClass.getESuperTypes().add(this.getAcceleoASTNode());
		acceleoASTNodeEClass.getESuperTypes().add(theAstPackage.getASTNode());
		errorEClass.getESuperTypes().add(this.getAcceleoASTNode());
		blockEClass.getESuperTypes().add(this.getAcceleoASTNode());
		templateEClass.getESuperTypes().add(this.getModuleElement());
		templateEClass.getESuperTypes().add(this.getDocumentedElement());
		templateEClass.getESuperTypes().add(this.getNamedElement());
		errorTemplateEClass.getESuperTypes().add(this.getError());
		errorTemplateEClass.getESuperTypes().add(this.getTemplate());
		queryEClass.getESuperTypes().add(this.getModuleElement());
		queryEClass.getESuperTypes().add(this.getDocumentedElement());
		queryEClass.getESuperTypes().add(this.getNamedElement());
		queryEClass.getESuperTypes().add(this.getTypedElement());
		errorQueryEClass.getESuperTypes().add(this.getError());
		errorQueryEClass.getESuperTypes().add(this.getQuery());
		expressionEClass.getESuperTypes().add(this.getAcceleoASTNode());
		errorExpressionEClass.getESuperTypes().add(this.getError());
		errorExpressionEClass.getESuperTypes().add(this.getExpression());
		variableEClass.getESuperTypes().add(this.getTypedElement());
		variableEClass.getESuperTypes().add(this.getNamedElement());
		variableEClass.getESuperTypes().add(this.getAcceleoASTNode());
		errorVariableEClass.getESuperTypes().add(this.getError());
		errorVariableEClass.getESuperTypes().add(this.getVariable());
		bindingEClass.getESuperTypes().add(this.getVariable());
		errorBindingEClass.getESuperTypes().add(this.getError());
		errorBindingEClass.getESuperTypes().add(this.getBinding());
		statementEClass.getESuperTypes().add(this.getAcceleoASTNode());
		leafStatementEClass.getESuperTypes().add(this.getStatement());
		expressionStatementEClass.getESuperTypes().add(this.getLeafStatement());
		errorExpressionStatementEClass.getESuperTypes().add(this.getError());
		errorExpressionStatementEClass.getESuperTypes().add(this.getExpressionStatement());
		protectedAreaEClass.getESuperTypes().add(this.getStatement());
		errorProtectedAreaEClass.getESuperTypes().add(this.getError());
		errorProtectedAreaEClass.getESuperTypes().add(this.getProtectedArea());
		forStatementEClass.getESuperTypes().add(this.getStatement());
		errorForStatementEClass.getESuperTypes().add(this.getError());
		errorForStatementEClass.getESuperTypes().add(this.getForStatement());
		ifStatementEClass.getESuperTypes().add(this.getStatement());
		errorIfStatementEClass.getESuperTypes().add(this.getError());
		errorIfStatementEClass.getESuperTypes().add(this.getIfStatement());
		letStatementEClass.getESuperTypes().add(this.getStatement());
		errorLetStatementEClass.getESuperTypes().add(this.getError());
		errorLetStatementEClass.getESuperTypes().add(this.getLetStatement());
		fileStatementEClass.getESuperTypes().add(this.getStatement());
		errorFileStatementEClass.getESuperTypes().add(this.getError());
		errorFileStatementEClass.getESuperTypes().add(this.getFileStatement());
		textStatementEClass.getESuperTypes().add(this.getLeafStatement());
		newLineStatementEClass.getESuperTypes().add(this.getTextStatement());

		// Initialize classes, features, and operations; add parameters
		initEClass(moduleEClass, org.eclipse.acceleo.Module.class, "Module", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModule_Metamodels(), this.getMetamodel(), null, "metamodels", null, 1, -1, //$NON-NLS-1$
				org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModule_Extends(), this.getModuleReference(), null, "extends", null, 0, 1, //$NON-NLS-1$
				org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModule_Imports(), this.getImport(), null, "imports", null, 0, -1, //$NON-NLS-1$
				org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModule_ModuleElements(), this.getModuleElement(), null, "moduleElements", null, 1, //$NON-NLS-1$
				-1, org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModule_StartHeaderPosition(), ecorePackage.getEInt(), "startHeaderPosition", null, //$NON-NLS-1$
				1, 1, org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModule_EndHeaderPosition(), ecorePackage.getEInt(), "endHeaderPosition", null, 1, 1, //$NON-NLS-1$
				org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModule_Ast(), this.getAcceleoAstResult(), "ast", null, 1, 1, //$NON-NLS-1$
				org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModule_Encoding(), ecorePackage.getEString(), "encoding", null, 0, 1, //$NON-NLS-1$
				org.eclipse.acceleo.Module.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorModuleEClass, ErrorModule.class, "ErrorModule", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorModule_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorModule.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorModule_MissingEPackage(), ecorePackage.getEInt(), "missingEPackage", "-1", 1, //$NON-NLS-1$//$NON-NLS-2$
				1, ErrorModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorModule_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorModule.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorModule_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", "-1", 1, //$NON-NLS-1$//$NON-NLS-2$
				1, ErrorModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metamodelEClass, Metamodel.class, "Metamodel", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetamodel_ReferencedPackage(), ecorePackage.getEPackage(), null,
				"referencedPackage", null, 1, 1, Metamodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorMetamodelEClass, ErrorMetamodel.class, "ErrorMetamodel", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorMetamodel_Fragment(), ecorePackage.getEString(), "fragment", null, 0, 1, //$NON-NLS-1$
				ErrorMetamodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorMetamodel_MissingEndQuote(), ecorePackage.getEInt(), "missingEndQuote", "-1", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, ErrorMetamodel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importEClass, Import.class, "Import", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getImport_Module(), this.getModuleReference(), null, "module", null, 1, 1, //$NON-NLS-1$
				Import.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorImportEClass, ErrorImport.class, "ErrorImport", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorImport_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorImport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(moduleReferenceEClass, ModuleReference.class, "ModuleReference", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModuleReference_QualifiedName(), this.getModuleQualifiedName(), "qualifiedName", //$NON-NLS-1$
				null, 0, 1, ModuleReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorModuleReferenceEClass, ErrorModuleReference.class, "ErrorModuleReference", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(moduleElementEClass, ModuleElement.class, "ModuleElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(blockCommentEClass, BlockComment.class, "BlockComment", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorBlockCommentEClass, ErrorBlockComment.class, "ErrorBlockComment", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(commentEClass, Comment.class, "Comment", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComment_Body(), this.getCommentBody(), null, "body", null, 0, 1, Comment.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorCommentEClass, ErrorComment.class, "ErrorComment", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorComment_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", "-1", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, ErrorComment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(commentBodyEClass, CommentBody.class, "CommentBody", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommentBody_Value(), ecorePackage.getEString(), "value", null, 0, 1, //$NON-NLS-1$
				CommentBody.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentationEClass, Documentation.class, "Documentation", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDocumentation_DocumentedElement(), this.getDocumentedElement(), this
				.getDocumentedElement_Documentation(), "documentedElement", null, 0, 1, Documentation.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(moduleDocumentationEClass, ModuleDocumentation.class, "ModuleDocumentation", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModuleDocumentation_Author(), ecorePackage.getEString(), "author", null, 0, 1, //$NON-NLS-1$
				ModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModuleDocumentation_Version(), ecorePackage.getEString(), "version", null, 0, 1, //$NON-NLS-1$
				ModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModuleDocumentation_Since(), ecorePackage.getEString(), "since", null, 0, 1, //$NON-NLS-1$
				ModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorModuleDocumentationEClass, ErrorModuleDocumentation.class, "ErrorModuleDocumentation", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorModuleDocumentation_MissingEndHeader(), ecorePackage.getEInt(),
				"missingEndHeader", "-1", 1, 1, ErrorModuleDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(moduleElementDocumentationEClass, ModuleElementDocumentation.class,
				"ModuleElementDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getModuleElementDocumentation_ParameterDocumentation(), this
				.getParameterDocumentation(), null, "parameterDocumentation", null, 0, -1, //$NON-NLS-1$
				ModuleElementDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorModuleElementDocumentationEClass, ErrorModuleElementDocumentation.class,
				"ErrorModuleElementDocumentation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getErrorModuleElementDocumentation_MissingEndHeader(), ecorePackage.getEInt(),
				"missingEndHeader", "-1", 1, 1, ErrorModuleElementDocumentation.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterDocumentationEClass, ParameterDocumentation.class, "ParameterDocumentation", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(documentedElementEClass, DocumentedElement.class, "DocumentedElement", IS_ABSTRACT, //$NON-NLS-1$
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDocumentedElement_Documentation(), this.getDocumentation(), this
				.getDocumentation_DocumentedElement(), "documentation", null, 0, 1, DocumentedElement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentedElement_Deprecated(), ecorePackage.getEBoolean(), "deprecated", "false", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, DocumentedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, //$NON-NLS-1$
				NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(acceleoASTNodeEClass, AcceleoASTNode.class, "AcceleoASTNode", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorEClass, org.eclipse.acceleo.Error.class, "Error", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(blockEClass, Block.class, "Block", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBlock_Statements(), this.getStatement(), null, "statements", null, 0, -1, //$NON-NLS-1$
				Block.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBlock_Inlined(), ecorePackage.getEBoolean(), "inlined", null, 1, 1, Block.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(typedElementEClass, TypedElement.class, "TypedElement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypedElement_Type(), this.getASTResult(), "type", null, 1, 1, TypedElement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTypedElement_TypeAql(), theAstPackage.getExpression(), null, "typeAql", null, 1, 1, //$NON-NLS-1$
				TypedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(templateEClass, Template.class, "Template", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTemplate_Parameters(), this.getVariable(), null, "parameters", null, 1, -1, //$NON-NLS-1$
				Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemplate_Guard(), this.getExpression(), null, "guard", null, 0, 1, Template.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTemplate_Post(), this.getExpression(), null, "post", null, 0, 1, Template.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTemplate_Main(), ecorePackage.getEBoolean(), "main", "false", 0, 1, Template.class, //$NON-NLS-1$//$NON-NLS-2$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getTemplate_Visibility(), this.getVisibilityKind(), "visibility", null, 1, 1, //$NON-NLS-1$
				Template.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTemplate_Body(), this.getBlock(), null, "body", null, 1, 1, Template.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorTemplateEClass, ErrorTemplate.class, "ErrorTemplate", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorTemplate_MissingVisibility(), ecorePackage.getEInt(), "missingVisibility", //$NON-NLS-1$
				"-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingName(), ecorePackage.getEInt(), "missingName", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingParameters(), ecorePackage.getEInt(), "missingParameters", //$NON-NLS-1$
				"-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, //$NON-NLS-1$
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingGuardOpenParenthesis(), ecorePackage.getEInt(),
				"missingGuardOpenParenthesis", "-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingGuardCloseParenthesis(), ecorePackage.getEInt(),
				"missingGuardCloseParenthesis", "-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingPostCloseParenthesis(), ecorePackage.getEInt(),
				"missingPostCloseParenthesis", "-1", 1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", "-1", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorTemplate_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(queryEClass, Query.class, "Query", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getQuery_Parameters(), this.getVariable(), null, "parameters", null, 1, -1, //$NON-NLS-1$
				Query.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getQuery_Visibility(), this.getVisibilityKind(), "visibility", null, 1, 1, Query.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getQuery_Body(), this.getExpression(), null, "body", null, 1, 1, Query.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorQueryEClass, ErrorQuery.class, "ErrorQuery", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorQuery_MissingVisibility(), ecorePackage.getEInt(), "missingVisibility", "-1", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingName(), ecorePackage.getEInt(), "missingName", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingParameters(), ecorePackage.getEInt(), "missingParameters", "-1", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingColon(), ecorePackage.getEInt(), "missingColon", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingType(), ecorePackage.getEInt(), "missingType", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingEqual(), ecorePackage.getEInt(), "missingEqual", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorQuery_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExpression_Ast(), this.getASTResult(), "ast", null, 1, 1, Expression.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getExpression_Aql(), theAstPackage.getExpression(), null, "aql", null, 1, 1, //$NON-NLS-1$
				Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorExpressionEClass, ErrorExpression.class, "ErrorExpression", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorVariableEClass, ErrorVariable.class, "ErrorVariable", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorVariable_MissingName(), ecorePackage.getEInt(), "missingName", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorVariable_MissingColon(), ecorePackage.getEInt(), "missingColon", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorVariable_MissingType(), ecorePackage.getEInt(), "missingType", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bindingEClass, Binding.class, "Binding", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinding_InitExpression(), this.getExpression(), null, "initExpression", null, 1, 1, //$NON-NLS-1$
				Binding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorBindingEClass, ErrorBinding.class, "ErrorBinding", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorBinding_MissingName(), ecorePackage.getEInt(), "missingName", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorBinding_MissingColon(), ecorePackage.getEInt(), "missingColon", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorBinding_MissingType(), ecorePackage.getEInt(), "missingType", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorBinding_MissingAffectationSymbole(), ecorePackage.getEString(),
				"missingAffectationSymbole", null, 0, 1, ErrorBinding.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorBinding_MissingAffectationSymbolePosition(), ecorePackage.getEInt(),
				"missingAffectationSymbolePosition", "-1", 0, 1, ErrorBinding.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(statementEClass, Statement.class, "Statement", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStatement_MultiLines(), ecorePackage.getEBoolean(), "multiLines", null, 1, 1, //$NON-NLS-1$
				Statement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(leafStatementEClass, LeafStatement.class, "LeafStatement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLeafStatement_NewLineNeeded(), ecorePackage.getEBoolean(), "newLineNeeded", null, 1, //$NON-NLS-1$
				1, LeafStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionStatementEClass, ExpressionStatement.class, "ExpressionStatement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpressionStatement_Expression(), this.getExpression(), null, "expression", null, 1, //$NON-NLS-1$
				1, ExpressionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorExpressionStatementEClass, ErrorExpressionStatement.class, "ErrorExpressionStatement", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorExpressionStatement_MissingEndHeader(), ecorePackage.getEInt(),
				"missingEndHeader", "-1", 1, 1, ErrorExpressionStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(protectedAreaEClass, ProtectedArea.class, "ProtectedArea", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProtectedArea_Id(), this.getExpression(), null, "id", null, 1, 1, //$NON-NLS-1$
				ProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProtectedArea_Body(), this.getBlock(), null, "body", null, 1, 1, //$NON-NLS-1$
				ProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProtectedArea_StartTagPrefix(), this.getExpression(), null, "startTagPrefix", null, //$NON-NLS-1$
				0, 1, ProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProtectedArea_EndTagPrefix(), this.getExpression(), null, "endTagPrefix", null, 0, //$NON-NLS-1$
				1, ProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorProtectedAreaEClass, ErrorProtectedArea.class, "ErrorProtectedArea", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorProtectedArea_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorProtectedArea_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorProtectedArea_MissingStartTagPrefixCloseParenthesis(), ecorePackage.getEInt(),
				"missingStartTagPrefixCloseParenthesis", "-1", 1, 1, ErrorProtectedArea.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorProtectedArea_MissingEndTagPrefixCloseParenthesis(), ecorePackage.getEInt(),
				"missingEndTagPrefixCloseParenthesis", "-1", 1, 1, ErrorProtectedArea.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorProtectedArea_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", //$NON-NLS-1$
				"-1", 1, 1, ErrorProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorProtectedArea_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorProtectedArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(forStatementEClass, ForStatement.class, "ForStatement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getForStatement_Binding(), this.getBinding(), null, "binding", null, 1, 1, //$NON-NLS-1$
				ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getForStatement_Separator(), this.getExpression(), null, "separator", null, 0, 1, //$NON-NLS-1$
				ForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getForStatement_Body(), this.getBlock(), null, "body", null, 1, 1, ForStatement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorForStatementEClass, ErrorForStatement.class, "ErrorForStatement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorForStatement_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorForStatement_MissingBinding(), ecorePackage.getEInt(), "missingBinding", "-1", //$NON-NLS-1$//$NON-NLS-2$
				1, 1, ErrorForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorForStatement_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorForStatement_MissingSeparatorCloseParenthesis(), ecorePackage.getEInt(),
				"missingSeparatorCloseParenthesis", "-1", 1, 1, ErrorForStatement.class, !IS_TRANSIENT, //$NON-NLS-1$//$NON-NLS-2$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorForStatement_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", //$NON-NLS-1$
				"-1", 1, 1, ErrorForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorForStatement_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorForStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ifStatementEClass, IfStatement.class, "IfStatement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIfStatement_Condition(), this.getExpression(), null, "condition", null, 1, 1, //$NON-NLS-1$
				IfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfStatement_Then(), this.getBlock(), null, "then", null, 1, 1, IfStatement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfStatement_Else(), this.getBlock(), null, "else", null, 0, 1, IfStatement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorIfStatementEClass, ErrorIfStatement.class, "ErrorIfStatement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorIfStatement_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorIfStatement_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorIfStatement_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", //$NON-NLS-1$
				"-1", 1, 1, ErrorIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorIfStatement_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorIfStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(letStatementEClass, LetStatement.class, "LetStatement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLetStatement_Variables(), this.getBinding(), null, "variables", null, 1, -1, //$NON-NLS-1$
				LetStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLetStatement_Body(), this.getBlock(), null, "body", null, 1, 1, LetStatement.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorLetStatementEClass, ErrorLetStatement.class, "ErrorLetStatement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorLetStatement_MissingBindings(), ecorePackage.getEInt(), "missingBindings", //$NON-NLS-1$
				"-1", 1, 1, ErrorLetStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorLetStatement_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", //$NON-NLS-1$
				"-1", 1, 1, ErrorLetStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorLetStatement_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorLetStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fileStatementEClass, FileStatement.class, "FileStatement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFileStatement_Mode(), this.getOpenModeKind(), "mode", null, 1, 1, //$NON-NLS-1$
				FileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileStatement_Url(), this.getExpression(), null, "url", null, 1, 1, //$NON-NLS-1$
				FileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileStatement_Charset(), this.getExpression(), null, "charset", null, 0, 1, //$NON-NLS-1$
				FileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFileStatement_Body(), this.getBlock(), null, "body", null, 1, 1, //$NON-NLS-1$
				FileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorFileStatementEClass, ErrorFileStatement.class, "ErrorFileStatement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorFileStatement_MissingOpenParenthesis(), ecorePackage.getEInt(),
				"missingOpenParenthesis", "-1", 1, 1, ErrorFileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorFileStatement_MissingComma(), ecorePackage.getEInt(), "missingComma", "-1", 1, //$NON-NLS-1$//$NON-NLS-2$
				1, ErrorFileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorFileStatement_MissingOpenMode(), ecorePackage.getEInt(), "missingOpenMode", //$NON-NLS-1$
				"-1", 1, 1, ErrorFileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorFileStatement_MissingCloseParenthesis(), ecorePackage.getEInt(),
				"missingCloseParenthesis", "-1", 1, 1, ErrorFileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$//$NON-NLS-2$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorFileStatement_MissingEndHeader(), ecorePackage.getEInt(), "missingEndHeader", //$NON-NLS-1$
				"-1", 1, 1, ErrorFileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErrorFileStatement_MissingEnd(), ecorePackage.getEInt(), "missingEnd", "-1", 1, 1, //$NON-NLS-1$//$NON-NLS-2$
				ErrorFileStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(textStatementEClass, TextStatement.class, "TextStatement", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTextStatement_Value(), ecorePackage.getEString(), "value", null, 1, 1, //$NON-NLS-1$
				TextStatement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(newLineStatementEClass, NewLineStatement.class, "NewLineStatement", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNewLineStatement_IndentationNeeded(), ecorePackage.getEBoolean(),
				"indentationNeeded", null, 1, 1, NewLineStatement.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(visibilityKindEEnum, VisibilityKind.class, "VisibilityKind"); //$NON-NLS-1$
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PRIVATE);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PROTECTED);
		addEEnumLiteral(visibilityKindEEnum, VisibilityKind.PUBLIC);

		initEEnum(openModeKindEEnum, OpenModeKind.class, "OpenModeKind"); //$NON-NLS-1$
		addEEnumLiteral(openModeKindEEnum, OpenModeKind.OVERWRITE);
		addEEnumLiteral(openModeKindEEnum, OpenModeKind.APPEND);
		addEEnumLiteral(openModeKindEEnum, OpenModeKind.CREATE);

		// Initialize data types
		initEDataType(astResultEDataType, AstResult.class, "ASTResult", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(moduleQualifiedNameEDataType, String.class, "ModuleQualifiedName", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(acceleoAstResultEDataType, AcceleoAstResult.class, "AcceleoAstResult", IS_SERIALIZABLE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

	@Override
	public EAttribute getModule_Ast() {
		return (EAttribute)moduleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getModule_Encoding() {
		return (EAttribute)moduleEClass.getEStructuralFeatures().get(7);
	}

	@Override
	public EDataType getAcceleoAstResult() {
		return acceleoAstResultEDataType;
	}

} // AcceleoPackageImpl
