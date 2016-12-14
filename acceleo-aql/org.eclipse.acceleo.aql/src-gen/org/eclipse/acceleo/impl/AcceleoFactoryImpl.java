/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import org.eclipse.acceleo.*;

import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AcceleoFactoryImpl extends EFactoryImpl implements AcceleoFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AcceleoFactory init() {
		try {
			AcceleoFactory theAcceleoFactory = (AcceleoFactory) EPackage.Registry.INSTANCE
					.getEFactory(AcceleoPackage.eNS_URI);
			if (theAcceleoFactory != null) {
				return theAcceleoFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AcceleoFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case AcceleoPackage.MODULE:
			return createModule();
		case AcceleoPackage.METAMODEL:
			return createMetamodel();
		case AcceleoPackage.COMMENT:
			return createComment();
		case AcceleoPackage.COMMENT_BODY:
			return createCommentBody();
		case AcceleoPackage.DOCUMENTATION:
			return createDocumentation();
		case AcceleoPackage.MODULE_DOCUMENTATION:
			return createModuleDocumentation();
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION:
			return createModuleElementDocumentation();
		case AcceleoPackage.PARAMETER_DOCUMENTATION:
			return createParameterDocumentation();
		case AcceleoPackage.BLOCK:
			return createBlock();
		case AcceleoPackage.TYPED_ELEMENT:
			return createTypedElement();
		case AcceleoPackage.TEMPLATE:
			return createTemplate();
		case AcceleoPackage.QUERY:
			return createQuery();
		case AcceleoPackage.EXPRESSION:
			return createExpression();
		case AcceleoPackage.VARIABLE:
			return createVariable();
		case AcceleoPackage.BINDING:
			return createBinding();
		case AcceleoPackage.EXPRESSION_STATEMENT:
			return createExpressionStatement();
		case AcceleoPackage.PROTECTED_AREA:
			return createProtectedArea();
		case AcceleoPackage.FOR_STATEMENT:
			return createForStatement();
		case AcceleoPackage.IF_STATEMENT:
			return createIfStatement();
		case AcceleoPackage.LET_STATEMENT:
			return createLetStatement();
		case AcceleoPackage.FILE_STATEMENT:
			return createFileStatement();
		case AcceleoPackage.TEXT_STATEMENT:
			return createTextStatement();
		default:
			throw new IllegalArgumentException(
					"The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		case AcceleoPackage.TEMPLATE_QUALIFIED_NAME:
			return createTemplateQualifiedNameFromString(eDataType,
					initialValue);
		default:
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		case AcceleoPackage.TEMPLATE_QUALIFIED_NAME:
			return convertTemplateQualifiedNameToString(eDataType,
					instanceValue);
		default:
			throw new IllegalArgumentException(
					"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Module createModule() {
		ModuleImpl module = new ModuleImpl();
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Metamodel createMetamodel() {
		MetamodelImpl metamodel = new MetamodelImpl();
		return metamodel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentBody createCommentBody() {
		CommentBodyImpl commentBody = new CommentBodyImpl();
		return commentBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Documentation createDocumentation() {
		DocumentationImpl documentation = new DocumentationImpl();
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleDocumentation createModuleDocumentation() {
		ModuleDocumentationImpl moduleDocumentation = new ModuleDocumentationImpl();
		return moduleDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElementDocumentation createModuleElementDocumentation() {
		ModuleElementDocumentationImpl moduleElementDocumentation = new ModuleElementDocumentationImpl();
		return moduleElementDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterDocumentation createParameterDocumentation() {
		ParameterDocumentationImpl parameterDocumentation = new ParameterDocumentationImpl();
		return parameterDocumentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Block createBlock() {
		BlockImpl block = new BlockImpl();
		return block;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypedElement createTypedElement() {
		TypedElementImpl typedElement = new TypedElementImpl();
		return typedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Template createTemplate() {
		TemplateImpl template = new TemplateImpl();
		return template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Query createQuery() {
		QueryImpl query = new QueryImpl();
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression createExpression() {
		ExpressionImpl expression = new ExpressionImpl();
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Binding createBinding() {
		BindingImpl binding = new BindingImpl();
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionStatement createExpressionStatement() {
		ExpressionStatementImpl expressionStatement = new ExpressionStatementImpl();
		return expressionStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProtectedArea createProtectedArea() {
		ProtectedAreaImpl protectedArea = new ProtectedAreaImpl();
		return protectedArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForStatement createForStatement() {
		ForStatementImpl forStatement = new ForStatementImpl();
		return forStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfStatement createIfStatement() {
		IfStatementImpl ifStatement = new IfStatementImpl();
		return ifStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LetStatement createLetStatement() {
		LetStatementImpl letStatement = new LetStatementImpl();
		return letStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileStatement createFileStatement() {
		FileStatementImpl fileStatement = new FileStatementImpl();
		return fileStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextStatement createTextStatement() {
		TextStatementImpl textStatement = new TextStatementImpl();
		return textStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VisibilityKind createVisibilityKindFromString(EDataType eDataType,
			String initialValue) {
		VisibilityKind result = VisibilityKind.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVisibilityKindToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpenModeKind createOpenModeKindFromString(EDataType eDataType,
			String initialValue) {
		OpenModeKind result = OpenModeKind.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOpenModeKindToString(EDataType eDataType,
			Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AstResult createASTResultFromString(EDataType eDataType,
			String initialValue) {
		return (AstResult) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertASTResultToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createModuleQualifiedNameFromString(EDataType eDataType,
			String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertModuleQualifiedNameToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createTemplateQualifiedNameFromString(EDataType eDataType,
			String initialValue) {
		return (String) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTemplateQualifiedNameToString(EDataType eDataType,
			Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoPackage getAcceleoPackage() {
		return (AcceleoPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AcceleoPackage getPackage() {
		return AcceleoPackage.eINSTANCE;
	}

} //AcceleoFactoryImpl
