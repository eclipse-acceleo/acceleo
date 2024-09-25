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
package org.eclipse.acceleo.util;

import org.eclipse.acceleo.AcceleoASTNode;
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
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.AcceleoPackage
 * @generated
 */
public class AcceleoAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static AcceleoPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AcceleoPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected AcceleoSwitch<Adapter> modelSwitch = new AcceleoSwitch<Adapter>() {
		@Override
		public Adapter caseModule(org.eclipse.acceleo.Module object) {
			return createModuleAdapter();
		}

		@Override
		public Adapter caseErrorModule(ErrorModule object) {
			return createErrorModuleAdapter();
		}

		@Override
		public Adapter caseMetamodel(Metamodel object) {
			return createMetamodelAdapter();
		}

		@Override
		public Adapter caseErrorMetamodel(ErrorMetamodel object) {
			return createErrorMetamodelAdapter();
		}

		@Override
		public Adapter caseImport(Import object) {
			return createImportAdapter();
		}

		@Override
		public Adapter caseErrorImport(ErrorImport object) {
			return createErrorImportAdapter();
		}

		@Override
		public Adapter caseModuleReference(ModuleReference object) {
			return createModuleReferenceAdapter();
		}

		@Override
		public Adapter caseErrorModuleReference(ErrorModuleReference object) {
			return createErrorModuleReferenceAdapter();
		}

		@Override
		public Adapter caseModuleElement(ModuleElement object) {
			return createModuleElementAdapter();
		}

		@Override
		public Adapter caseBlockComment(BlockComment object) {
			return createBlockCommentAdapter();
		}

		@Override
		public Adapter caseErrorBlockComment(ErrorBlockComment object) {
			return createErrorBlockCommentAdapter();
		}

		@Override
		public Adapter caseComment(Comment object) {
			return createCommentAdapter();
		}

		@Override
		public Adapter caseErrorComment(ErrorComment object) {
			return createErrorCommentAdapter();
		}

		@Override
		public Adapter caseCommentBody(CommentBody object) {
			return createCommentBodyAdapter();
		}

		@Override
		public Adapter caseDocumentation(Documentation object) {
			return createDocumentationAdapter();
		}

		@Override
		public Adapter caseModuleDocumentation(ModuleDocumentation object) {
			return createModuleDocumentationAdapter();
		}

		@Override
		public Adapter caseErrorModuleDocumentation(ErrorModuleDocumentation object) {
			return createErrorModuleDocumentationAdapter();
		}

		@Override
		public Adapter caseModuleElementDocumentation(ModuleElementDocumentation object) {
			return createModuleElementDocumentationAdapter();
		}

		@Override
		public Adapter caseErrorModuleElementDocumentation(ErrorModuleElementDocumentation object) {
			return createErrorModuleElementDocumentationAdapter();
		}

		@Override
		public Adapter caseParameterDocumentation(ParameterDocumentation object) {
			return createParameterDocumentationAdapter();
		}

		@Override
		public Adapter caseDocumentedElement(DocumentedElement object) {
			return createDocumentedElementAdapter();
		}

		@Override
		public Adapter caseNamedElement(NamedElement object) {
			return createNamedElementAdapter();
		}

		@Override
		public Adapter caseAcceleoASTNode(AcceleoASTNode object) {
			return createAcceleoASTNodeAdapter();
		}

		@Override
		public Adapter caseError(org.eclipse.acceleo.Error object) {
			return createErrorAdapter();
		}

		@Override
		public Adapter caseBlock(Block object) {
			return createBlockAdapter();
		}

		@Override
		public Adapter caseTypedElement(TypedElement object) {
			return createTypedElementAdapter();
		}

		@Override
		public Adapter caseTemplate(Template object) {
			return createTemplateAdapter();
		}

		@Override
		public Adapter caseErrorTemplate(ErrorTemplate object) {
			return createErrorTemplateAdapter();
		}

		@Override
		public Adapter caseQuery(Query object) {
			return createQueryAdapter();
		}

		@Override
		public Adapter caseErrorQuery(ErrorQuery object) {
			return createErrorQueryAdapter();
		}

		@Override
		public Adapter caseExpression(Expression object) {
			return createExpressionAdapter();
		}

		@Override
		public Adapter caseErrorExpression(ErrorExpression object) {
			return createErrorExpressionAdapter();
		}

		@Override
		public Adapter caseVariable(Variable object) {
			return createVariableAdapter();
		}

		@Override
		public Adapter caseErrorVariable(ErrorVariable object) {
			return createErrorVariableAdapter();
		}

		@Override
		public Adapter caseBinding(Binding object) {
			return createBindingAdapter();
		}

		@Override
		public Adapter caseErrorBinding(ErrorBinding object) {
			return createErrorBindingAdapter();
		}

		@Override
		public Adapter caseStatement(Statement object) {
			return createStatementAdapter();
		}

		@Override
		public Adapter caseLeafStatement(LeafStatement object) {
			return createLeafStatementAdapter();
		}

		@Override
		public Adapter caseExpressionStatement(ExpressionStatement object) {
			return createExpressionStatementAdapter();
		}

		@Override
		public Adapter caseErrorExpressionStatement(ErrorExpressionStatement object) {
			return createErrorExpressionStatementAdapter();
		}

		@Override
		public Adapter caseProtectedArea(ProtectedArea object) {
			return createProtectedAreaAdapter();
		}

		@Override
		public Adapter caseErrorProtectedArea(ErrorProtectedArea object) {
			return createErrorProtectedAreaAdapter();
		}

		@Override
		public Adapter caseForStatement(ForStatement object) {
			return createForStatementAdapter();
		}

		@Override
		public Adapter caseErrorForStatement(ErrorForStatement object) {
			return createErrorForStatementAdapter();
		}

		@Override
		public Adapter caseIfStatement(IfStatement object) {
			return createIfStatementAdapter();
		}

		@Override
		public Adapter caseErrorIfStatement(ErrorIfStatement object) {
			return createErrorIfStatementAdapter();
		}

		@Override
		public Adapter caseLetStatement(LetStatement object) {
			return createLetStatementAdapter();
		}

		@Override
		public Adapter caseErrorLetStatement(ErrorLetStatement object) {
			return createErrorLetStatementAdapter();
		}

		@Override
		public Adapter caseFileStatement(FileStatement object) {
			return createFileStatementAdapter();
		}

		@Override
		public Adapter caseErrorFileStatement(ErrorFileStatement object) {
			return createErrorFileStatementAdapter();
		}

		@Override
		public Adapter caseTextStatement(TextStatement object) {
			return createTextStatementAdapter();
		}

		@Override
		public Adapter caseNewLineStatement(NewLineStatement object) {
			return createNewLineStatementAdapter();
		}

		@Override
		public Adapter caseErrorMargin(ErrorMargin object) {
			return createErrorMarginAdapter();
		}

		@Override
		public Adapter caseASTNode(ASTNode object) {
			return createASTNodeAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Module <em>Module</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Module
	 * @generated
	 */
	public Adapter createModuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorModule <em>Error
	 * Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorModule
	 * @generated
	 */
	public Adapter createErrorModuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Metamodel
	 * <em>Metamodel</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Metamodel
	 * @generated
	 */
	public Adapter createMetamodelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorMetamodel <em>Error
	 * Metamodel</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorMetamodel
	 * @generated
	 */
	public Adapter createErrorMetamodelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Import <em>Import</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Import
	 * @generated
	 */
	public Adapter createImportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorImport <em>Error
	 * Import</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorImport
	 * @generated
	 */
	public Adapter createErrorImportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ModuleReference <em>Module
	 * Reference</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ModuleReference
	 * @generated
	 */
	public Adapter createModuleReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorModuleReference <em>Error
	 * Module Reference</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorModuleReference
	 * @generated
	 */
	public Adapter createErrorModuleReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ModuleElement <em>Module
	 * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ModuleElement
	 * @generated
	 */
	public Adapter createModuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Comment
	 * @generated
	 */
	public Adapter createCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.BlockComment <em>Block
	 * Comment</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.BlockComment
	 * @generated
	 */
	public Adapter createBlockCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorBlockComment <em>Error
	 * Block Comment</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorBlockComment
	 * @generated
	 */
	public Adapter createErrorBlockCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorComment <em>Error
	 * Comment</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorComment
	 * @generated
	 */
	public Adapter createErrorCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.CommentBody <em>Comment
	 * Body</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.CommentBody
	 * @generated
	 */
	public Adapter createCommentBodyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Documentation
	 * <em>Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Documentation
	 * @generated
	 */
	public Adapter createDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ModuleDocumentation <em>Module
	 * Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ModuleDocumentation
	 * @generated
	 */
	public Adapter createModuleDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorModuleDocumentation
	 * <em>Error Module Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null
	 * so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorModuleDocumentation
	 * @generated
	 */
	public Adapter createErrorModuleDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ModuleElementDocumentation
	 * <em>Module Element Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all
	 * the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ModuleElementDocumentation
	 * @generated
	 */
	public Adapter createModuleElementDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.eclipse.acceleo.ErrorModuleElementDocumentation <em>Error Module Element
	 * Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorModuleElementDocumentation
	 * @generated
	 */
	public Adapter createErrorModuleElementDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ParameterDocumentation
	 * <em>Parameter Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ParameterDocumentation
	 * @generated
	 */
	public Adapter createParameterDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.DocumentedElement
	 * <em>Documented Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.DocumentedElement
	 * @generated
	 */
	public Adapter createDocumentedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.NamedElement <em>Named
	 * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.AcceleoASTNode <em>AST
	 * Node</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.AcceleoASTNode
	 * @generated
	 */
	public Adapter createAcceleoASTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.ast.ASTNode <em>AST
	 * Node</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.ast.ASTNode
	 * @generated
	 */
	public Adapter createASTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Error <em>Error</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Error
	 * @generated
	 */
	public Adapter createErrorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Block <em>Block</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Block
	 * @generated
	 */
	public Adapter createBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.TypedElement <em>Typed
	 * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.TypedElement
	 * @generated
	 */
	public Adapter createTypedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Template <em>Template</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Template
	 * @generated
	 */
	public Adapter createTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorTemplate <em>Error
	 * Template</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorTemplate
	 * @generated
	 */
	public Adapter createErrorTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Query <em>Query</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Query
	 * @generated
	 */
	public Adapter createQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorQuery <em>Error
	 * Query</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorQuery
	 * @generated
	 */
	public Adapter createErrorQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Expression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorExpression <em>Error
	 * Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorExpression
	 * @generated
	 */
	public Adapter createErrorExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Variable
	 * @generated
	 */
	public Adapter createVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorVariable <em>Error
	 * Variable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorVariable
	 * @generated
	 */
	public Adapter createErrorVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Binding <em>Binding</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Binding
	 * @generated
	 */
	public Adapter createBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorBinding <em>Error
	 * Binding</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorBinding
	 * @generated
	 */
	public Adapter createErrorBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.Statement
	 * <em>Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.Statement
	 * @generated
	 */
	public Adapter createStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.LeafStatement <em>Leaf
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.LeafStatement
	 * @generated
	 */
	public Adapter createLeafStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ExpressionStatement
	 * <em>Expression Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ExpressionStatement
	 * @generated
	 */
	public Adapter createExpressionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorExpressionStatement
	 * <em>Error Expression Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null
	 * so that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorExpressionStatement
	 * @generated
	 */
	public Adapter createErrorExpressionStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ProtectedArea <em>Protected
	 * Area</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ProtectedArea
	 * @generated
	 */
	public Adapter createProtectedAreaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorProtectedArea <em>Error
	 * Protected Area</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorProtectedArea
	 * @generated
	 */
	public Adapter createErrorProtectedAreaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ForStatement <em>For
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ForStatement
	 * @generated
	 */
	public Adapter createForStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorForStatement <em>Error
	 * For Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorForStatement
	 * @generated
	 */
	public Adapter createErrorForStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.IfStatement <em>If
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.IfStatement
	 * @generated
	 */
	public Adapter createIfStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorIfStatement <em>Error If
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorIfStatement
	 * @generated
	 */
	public Adapter createErrorIfStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.LetStatement <em>Let
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.LetStatement
	 * @generated
	 */
	public Adapter createLetStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorLetStatement <em>Error
	 * Let Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorLetStatement
	 * @generated
	 */
	public Adapter createErrorLetStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.FileStatement <em>File
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.FileStatement
	 * @generated
	 */
	public Adapter createFileStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorFileStatement <em>Error
	 * File Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorFileStatement
	 * @generated
	 */
	public Adapter createErrorFileStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.TextStatement <em>Text
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.TextStatement
	 * @generated
	 */
	public Adapter createTextStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.NewLineStatement <em>New Line
	 * Statement</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.NewLineStatement
	 * @generated
	 */
	public Adapter createNewLineStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.ErrorMargin <em>Error
	 * Margin</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.ErrorMargin
	 * @generated
	 */
	public Adapter createErrorMarginAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // AcceleoAdapterFactory
