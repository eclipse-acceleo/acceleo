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
package org.eclipse.acceleo.model.mtl.util;

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
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ParameterDocumentation;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ocl.utilities.TypedElement;
import org.eclipse.ocl.utilities.Visitable;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage
 * @generated
 * @since 3.0
 */
public class MtlAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static MtlPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtlAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MtlPackage.eINSTANCE;
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
	protected MtlSwitch<Adapter> modelSwitch = new MtlSwitch<Adapter>() {
		@Override
		public Adapter caseModule(Module object) {
			return createModuleAdapter();
		}

		@Override
		public Adapter caseModuleElement(ModuleElement object) {
			return createModuleElementAdapter();
		}

		@Override
		public Adapter caseTemplateExpression(TemplateExpression object) {
			return createTemplateExpressionAdapter();
		}

		@Override
		public Adapter caseBlock(Block object) {
			return createBlockAdapter();
		}

		@Override
		public Adapter caseInitSection(InitSection object) {
			return createInitSectionAdapter();
		}

		@Override
		public Adapter caseTemplate(Template object) {
			return createTemplateAdapter();
		}

		@Override
		public Adapter caseTemplateInvocation(TemplateInvocation object) {
			return createTemplateInvocationAdapter();
		}

		@Override
		public Adapter caseQuery(Query object) {
			return createQueryAdapter();
		}

		@Override
		public Adapter caseQueryInvocation(QueryInvocation object) {
			return createQueryInvocationAdapter();
		}

		@Override
		public Adapter caseProtectedAreaBlock(ProtectedAreaBlock object) {
			return createProtectedAreaBlockAdapter();
		}

		@Override
		public Adapter caseForBlock(ForBlock object) {
			return createForBlockAdapter();
		}

		@Override
		public Adapter caseIfBlock(IfBlock object) {
			return createIfBlockAdapter();
		}

		@Override
		public Adapter caseLetBlock(LetBlock object) {
			return createLetBlockAdapter();
		}

		@Override
		public Adapter caseFileBlock(FileBlock object) {
			return createFileBlockAdapter();
		}

		@Override
		public Adapter caseTraceBlock(TraceBlock object) {
			return createTraceBlockAdapter();
		}

		@Override
		public Adapter caseMacro(Macro object) {
			return createMacroAdapter();
		}

		@Override
		public Adapter caseMacroInvocation(MacroInvocation object) {
			return createMacroInvocationAdapter();
		}

		@Override
		public Adapter caseTypedModel(TypedModel object) {
			return createTypedModelAdapter();
		}

		@Override
		public Adapter caseComment(Comment object) {
			return createCommentAdapter();
		}

		@Override
		public Adapter caseDocumentation(Documentation object) {
			return createDocumentationAdapter();
		}

		@Override
		public Adapter caseDocumentedElement(DocumentedElement object) {
			return createDocumentedElementAdapter();
		}

		@Override
		public Adapter caseCommentBody(CommentBody object) {
			return createCommentBodyAdapter();
		}

		@Override
		public Adapter caseModuleDocumentation(ModuleDocumentation object) {
			return createModuleDocumentationAdapter();
		}

		@Override
		public Adapter caseModuleElementDocumentation(ModuleElementDocumentation object) {
			return createModuleElementDocumentationAdapter();
		}

		@Override
		public Adapter caseParameterDocumentation(ParameterDocumentation object) {
			return createParameterDocumentationAdapter();
		}

		@Override
		public Adapter caseEModelElement(EModelElement object) {
			return createEModelElementAdapter();
		}

		@Override
		public Adapter caseENamedElement(ENamedElement object) {
			return createENamedElementAdapter();
		}

		@Override
		public Adapter caseEPackage(EPackage object) {
			return createEPackageAdapter();
		}

		@Override
		public Adapter caseASTNode(ASTNode object) {
			return createASTNodeAdapter();
		}

		@Override
		public Adapter caseETypedElement(ETypedElement object) {
			return createETypedElementAdapter();
		}

		@Override
		public <C> Adapter caseTypedElement(TypedElement<C> object) {
			return createTypedElementAdapter();
		}

		@Override
		public Adapter caseVisitable(Visitable object) {
			return createVisitableAdapter();
		}

		@Override
		public <C> Adapter caseOCLExpression(OCLExpression<C> object) {
			return createOCLExpressionAdapter();
		}

		@Override
		public Adapter caseEcore_OCLExpression(org.eclipse.ocl.ecore.OCLExpression object) {
			return createEcore_OCLExpressionAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Module
	 * <em>Module</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Module
	 * @generated
	 */
	public Adapter createModuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.ModuleElement
	 * <em>Module Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.ModuleElement
	 * @generated
	 */
	public Adapter createModuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.TemplateExpression
	 * <em>Template Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.TemplateExpression
	 * @generated
	 */
	public Adapter createTemplateExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Block
	 * <em>Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Block
	 * @generated
	 */
	public Adapter createBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.InitSection
	 * <em>Init Section</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.InitSection
	 * @generated
	 */
	public Adapter createInitSectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Template
	 * <em>Template</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Template
	 * @generated
	 */
	public Adapter createTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation
	 * <em>Template Invocation</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.TemplateInvocation
	 * @generated
	 */
	public Adapter createTemplateInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Query
	 * <em>Query</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Query
	 * @generated
	 */
	public Adapter createQueryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.QueryInvocation
	 * <em>Query Invocation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.QueryInvocation
	 * @generated
	 */
	public Adapter createQueryInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.ProtectedAreaBlock
	 * <em>Protected Area Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.ProtectedAreaBlock
	 * @generated
	 */
	public Adapter createProtectedAreaBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.ForBlock
	 * <em>For Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.ForBlock
	 * @generated
	 */
	public Adapter createForBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.IfBlock
	 * <em>If Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.IfBlock
	 * @generated
	 */
	public Adapter createIfBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.LetBlock
	 * <em>Let Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.LetBlock
	 * @generated
	 */
	public Adapter createLetBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.FileBlock
	 * <em>File Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.FileBlock
	 * @generated
	 */
	public Adapter createFileBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.TraceBlock
	 * <em>Trace Block</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.TraceBlock
	 * @generated
	 */
	public Adapter createTraceBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Macro
	 * <em>Macro</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Macro
	 * @generated
	 */
	public Adapter createMacroAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.MacroInvocation
	 * <em>Macro Invocation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.MacroInvocation
	 * @generated
	 */
	public Adapter createMacroInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.TypedModel
	 * <em>Typed Model</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.TypedModel
	 * @generated
	 */
	public Adapter createTypedModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Comment
	 * <em>Comment</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Comment
	 * @generated
	 * @since 3.1
	 */
	public Adapter createCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.Documentation
	 * <em>Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.Documentation
	 * @generated
	 * @since 3.1
	 */
	public Adapter createDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.DocumentedElement
	 * <em>Documented Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.DocumentedElement
	 * @generated
	 * @since 3.1
	 */
	public Adapter createDocumentedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.CommentBody
	 * <em>Comment Body</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.CommentBody
	 * @generated
	 * @since 3.1
	 */
	public Adapter createCommentBodyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.model.mtl.ModuleDocumentation
	 * <em>Module Documentation</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.ModuleDocumentation
	 * @generated
	 * @since 3.1
	 */
	public Adapter createModuleDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.model.mtl.ModuleElementDocumentation <em>Module Element Documentation</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.ModuleElementDocumentation
	 * @generated
	 * @since 3.1
	 */
	public Adapter createModuleElementDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.model.mtl.ParameterDocumentation <em>Parameter Documentation</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.model.mtl.ParameterDocumentation
	 * @generated
	 * @since 3.1
	 */
	public Adapter createParameterDocumentationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecore.EModelElement
	 * <em>EModel Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecore.EModelElement
	 * @generated
	 */
	public Adapter createEModelElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecore.ENamedElement
	 * <em>ENamed Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecore.ENamedElement
	 * @generated
	 */
	public Adapter createENamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecore.EPackage <em>EPackage</em>}
	 * '. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecore.EPackage
	 * @generated
	 */
	public Adapter createEPackageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.utilities.ASTNode
	 * <em>AST Node</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.ocl.utilities.ASTNode
	 * @generated
	 */
	public Adapter createASTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.ecore.ETypedElement
	 * <em>ETyped Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.emf.ecore.ETypedElement
	 * @generated
	 */
	public Adapter createETypedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.utilities.TypedElement
	 * <em>Typed Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.ocl.utilities.TypedElement
	 * @generated
	 */
	public Adapter createTypedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.utilities.Visitable
	 * <em>Visitable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.ocl.utilities.Visitable
	 * @generated
	 */
	public Adapter createVisitableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.expressions.OCLExpression
	 * <em>OCL Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.ocl.expressions.OCLExpression
	 * @generated
	 */
	public Adapter createOCLExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.ocl.ecore.OCLExpression
	 * <em>OCL Expression</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.ocl.ecore.OCLExpression
	 * @generated
	 */
	public Adapter createEcore_OCLExpressionAdapter() {
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

} // MtlAdapterFactory
