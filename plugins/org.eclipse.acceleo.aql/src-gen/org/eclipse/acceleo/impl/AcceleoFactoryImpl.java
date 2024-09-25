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

import org.eclipse.acceleo.AcceleoFactory;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
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
import org.eclipse.acceleo.ErrorMargin;
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
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class AcceleoFactoryImpl extends EFactoryImpl implements AcceleoFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static AcceleoFactory init() {
		try {
			AcceleoFactory theAcceleoFactory = (AcceleoFactory)EPackage.Registry.INSTANCE.getEFactory(
					AcceleoPackage.eNS_URI);
			if (theAcceleoFactory != null) {
				return theAcceleoFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AcceleoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AcceleoPackage.MODULE:
				return createModule();
			case AcceleoPackage.ERROR_MODULE:
				return createErrorModule();
			case AcceleoPackage.METAMODEL:
				return createMetamodel();
			case AcceleoPackage.ERROR_METAMODEL:
				return createErrorMetamodel();
			case AcceleoPackage.IMPORT:
				return createImport();
			case AcceleoPackage.ERROR_IMPORT:
				return createErrorImport();
			case AcceleoPackage.MODULE_REFERENCE:
				return createModuleReference();
			case AcceleoPackage.ERROR_MODULE_REFERENCE:
				return createErrorModuleReference();
			case AcceleoPackage.BLOCK_COMMENT:
				return createBlockComment();
			case AcceleoPackage.ERROR_BLOCK_COMMENT:
				return createErrorBlockComment();
			case AcceleoPackage.COMMENT:
				return createComment();
			case AcceleoPackage.ERROR_COMMENT:
				return createErrorComment();
			case AcceleoPackage.COMMENT_BODY:
				return createCommentBody();
			case AcceleoPackage.MODULE_DOCUMENTATION:
				return createModuleDocumentation();
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION:
				return createErrorModuleDocumentation();
			case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION:
				return createModuleElementDocumentation();
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION:
				return createErrorModuleElementDocumentation();
			case AcceleoPackage.PARAMETER_DOCUMENTATION:
				return createParameterDocumentation();
			case AcceleoPackage.BLOCK:
				return createBlock();
			case AcceleoPackage.TYPED_ELEMENT:
				return createTypedElement();
			case AcceleoPackage.TEMPLATE:
				return createTemplate();
			case AcceleoPackage.ERROR_TEMPLATE:
				return createErrorTemplate();
			case AcceleoPackage.QUERY:
				return createQuery();
			case AcceleoPackage.ERROR_QUERY:
				return createErrorQuery();
			case AcceleoPackage.EXPRESSION:
				return createExpression();
			case AcceleoPackage.ERROR_EXPRESSION:
				return createErrorExpression();
			case AcceleoPackage.VARIABLE:
				return createVariable();
			case AcceleoPackage.ERROR_VARIABLE:
				return createErrorVariable();
			case AcceleoPackage.BINDING:
				return createBinding();
			case AcceleoPackage.ERROR_BINDING:
				return createErrorBinding();
			case AcceleoPackage.LEAF_STATEMENT:
				return createLeafStatement();
			case AcceleoPackage.EXPRESSION_STATEMENT:
				return createExpressionStatement();
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT:
				return createErrorExpressionStatement();
			case AcceleoPackage.PROTECTED_AREA:
				return createProtectedArea();
			case AcceleoPackage.ERROR_PROTECTED_AREA:
				return createErrorProtectedArea();
			case AcceleoPackage.FOR_STATEMENT:
				return createForStatement();
			case AcceleoPackage.ERROR_FOR_STATEMENT:
				return createErrorForStatement();
			case AcceleoPackage.IF_STATEMENT:
				return createIfStatement();
			case AcceleoPackage.ERROR_IF_STATEMENT:
				return createErrorIfStatement();
			case AcceleoPackage.LET_STATEMENT:
				return createLetStatement();
			case AcceleoPackage.ERROR_LET_STATEMENT:
				return createErrorLetStatement();
			case AcceleoPackage.FILE_STATEMENT:
				return createFileStatement();
			case AcceleoPackage.ERROR_FILE_STATEMENT:
				return createErrorFileStatement();
			case AcceleoPackage.TEXT_STATEMENT:
				return createTextStatement();
			case AcceleoPackage.NEW_LINE_STATEMENT:
				return createNewLineStatement();
			case AcceleoPackage.ERROR_MARGIN:
				return createErrorMargin();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AcceleoPackage.VISIBILITY_KIND:
				return createVisibilityKindFromString(eDataType, initialValue);
			case AcceleoPackage.OPEN_MODE_KIND:
				return createOpenModeKindFromString(eDataType, initialValue);
			case AcceleoPackage.AST_RESULT:
				return createASTResultFromString(eDataType, initialValue);
			case AcceleoPackage.MODULE_QUALIFIED_NAME:
				return createModuleQualifiedNameFromString(eDataType, initialValue);
			case AcceleoPackage.ACCELEO_AST_RESULT:
				return createAcceleoAstResultFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AcceleoPackage.VISIBILITY_KIND:
				return convertVisibilityKindToString(eDataType, instanceValue);
			case AcceleoPackage.OPEN_MODE_KIND:
				return convertOpenModeKindToString(eDataType, instanceValue);
			case AcceleoPackage.AST_RESULT:
				return convertASTResultToString(eDataType, instanceValue);
			case AcceleoPackage.MODULE_QUALIFIED_NAME:
				return convertModuleQualifiedNameToString(eDataType, instanceValue);
			case AcceleoPackage.ACCELEO_AST_RESULT:
				return convertAcceleoAstResultToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public org.eclipse.acceleo.Module createModule() {
		ModuleImpl module = new ModuleImpl();
		return module;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorModule createErrorModule() {
		ErrorModuleImpl errorModule = new ErrorModuleImpl();
		return errorModule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Metamodel createMetamodel() {
		MetamodelImpl metamodel = new MetamodelImpl();
		return metamodel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorMetamodel createErrorMetamodel() {
		ErrorMetamodelImpl errorMetamodel = new ErrorMetamodelImpl();
		return errorMetamodel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Import createImport() {
		ImportImpl import_ = new ImportImpl();
		return import_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorImport createErrorImport() {
		ErrorImportImpl errorImport = new ErrorImportImpl();
		return errorImport;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModuleReference createModuleReference() {
		ModuleReferenceImpl moduleReference = new ModuleReferenceImpl();
		return moduleReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorModuleReference createErrorModuleReference() {
		ErrorModuleReferenceImpl errorModuleReference = new ErrorModuleReferenceImpl();
		return errorModuleReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public BlockComment createBlockComment() {
		BlockCommentImpl blockComment = new BlockCommentImpl();
		return blockComment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorBlockComment createErrorBlockComment() {
		ErrorBlockCommentImpl errorBlockComment = new ErrorBlockCommentImpl();
		return errorBlockComment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorComment createErrorComment() {
		ErrorCommentImpl errorComment = new ErrorCommentImpl();
		return errorComment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CommentBody createCommentBody() {
		CommentBodyImpl commentBody = new CommentBodyImpl();
		return commentBody;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModuleDocumentation createModuleDocumentation() {
		ModuleDocumentationImpl moduleDocumentation = new ModuleDocumentationImpl();
		return moduleDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorModuleDocumentation createErrorModuleDocumentation() {
		ErrorModuleDocumentationImpl errorModuleDocumentation = new ErrorModuleDocumentationImpl();
		return errorModuleDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModuleElementDocumentation createModuleElementDocumentation() {
		ModuleElementDocumentationImpl moduleElementDocumentation = new ModuleElementDocumentationImpl();
		return moduleElementDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorModuleElementDocumentation createErrorModuleElementDocumentation() {
		ErrorModuleElementDocumentationImpl errorModuleElementDocumentation = new ErrorModuleElementDocumentationImpl();
		return errorModuleElementDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ParameterDocumentation createParameterDocumentation() {
		ParameterDocumentationImpl parameterDocumentation = new ParameterDocumentationImpl();
		return parameterDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Block createBlock() {
		BlockImpl block = new BlockImpl();
		return block;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TypedElement createTypedElement() {
		TypedElementImpl typedElement = new TypedElementImpl();
		return typedElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Template createTemplate() {
		TemplateImpl template = new TemplateImpl();
		return template;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorTemplate createErrorTemplate() {
		ErrorTemplateImpl errorTemplate = new ErrorTemplateImpl();
		return errorTemplate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Query createQuery() {
		QueryImpl query = new QueryImpl();
		return query;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorQuery createErrorQuery() {
		ErrorQueryImpl errorQuery = new ErrorQueryImpl();
		return errorQuery;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Expression createExpression() {
		ExpressionImpl expression = new ExpressionImpl();
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorExpression createErrorExpression() {
		ErrorExpressionImpl errorExpression = new ErrorExpressionImpl();
		return errorExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorVariable createErrorVariable() {
		ErrorVariableImpl errorVariable = new ErrorVariableImpl();
		return errorVariable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Binding createBinding() {
		BindingImpl binding = new BindingImpl();
		return binding;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorBinding createErrorBinding() {
		ErrorBindingImpl errorBinding = new ErrorBindingImpl();
		return errorBinding;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LeafStatement createLeafStatement() {
		LeafStatementImpl leafStatement = new LeafStatementImpl();
		return leafStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ExpressionStatement createExpressionStatement() {
		ExpressionStatementImpl expressionStatement = new ExpressionStatementImpl();
		return expressionStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorExpressionStatement createErrorExpressionStatement() {
		ErrorExpressionStatementImpl errorExpressionStatement = new ErrorExpressionStatementImpl();
		return errorExpressionStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ProtectedArea createProtectedArea() {
		ProtectedAreaImpl protectedArea = new ProtectedAreaImpl();
		return protectedArea;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorProtectedArea createErrorProtectedArea() {
		ErrorProtectedAreaImpl errorProtectedArea = new ErrorProtectedAreaImpl();
		return errorProtectedArea;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ForStatement createForStatement() {
		ForStatementImpl forStatement = new ForStatementImpl();
		return forStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorForStatement createErrorForStatement() {
		ErrorForStatementImpl errorForStatement = new ErrorForStatementImpl();
		return errorForStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IfStatement createIfStatement() {
		IfStatementImpl ifStatement = new IfStatementImpl();
		return ifStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorIfStatement createErrorIfStatement() {
		ErrorIfStatementImpl errorIfStatement = new ErrorIfStatementImpl();
		return errorIfStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public LetStatement createLetStatement() {
		LetStatementImpl letStatement = new LetStatementImpl();
		return letStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorLetStatement createErrorLetStatement() {
		ErrorLetStatementImpl errorLetStatement = new ErrorLetStatementImpl();
		return errorLetStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public FileStatement createFileStatement() {
		FileStatementImpl fileStatement = new FileStatementImpl();
		return fileStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorFileStatement createErrorFileStatement() {
		ErrorFileStatementImpl errorFileStatement = new ErrorFileStatementImpl();
		return errorFileStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TextStatement createTextStatement() {
		TextStatementImpl textStatement = new TextStatementImpl();
		return textStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NewLineStatement createNewLineStatement() {
		NewLineStatementImpl newLineStatement = new NewLineStatementImpl();
		return newLineStatement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorMargin createErrorMargin() {
		ErrorMarginImpl errorMargin = new ErrorMarginImpl();
		return errorMargin;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VisibilityKind createVisibilityKindFromString(EDataType eDataType, String initialValue) {
		VisibilityKind result = VisibilityKind.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue //$NON-NLS-1$
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertVisibilityKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OpenModeKind createOpenModeKindFromString(EDataType eDataType, String initialValue) {
		OpenModeKind result = OpenModeKind.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue //$NON-NLS-1$
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertOpenModeKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AstResult createASTResultFromString(EDataType eDataType, String initialValue) {
		return (AstResult)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertASTResultToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String createModuleQualifiedNameFromString(EDataType eDataType, String initialValue) {
		return (String)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertModuleQualifiedNameToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoAstResult createAcceleoAstResultFromString(EDataType eDataType, String initialValue) {
		return (AcceleoAstResult)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertAcceleoAstResultToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AcceleoPackage getAcceleoPackage() {
		return (AcceleoPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AcceleoPackage getPackage() {
		return AcceleoPackage.eINSTANCE;
	}

} // AcceleoFactoryImpl
