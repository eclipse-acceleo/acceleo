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
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleDocumentation;
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
import org.eclipse.acceleo.model.mtl.impl.spec.CommentBodySpec;
import org.eclipse.acceleo.model.mtl.impl.spec.CommentSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.DocumentationSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.FileBlockSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.ForBlockSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.IfBlockSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.InitSectionSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.LetBlockSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.ModuleSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.ProtectedAreaBlockSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.QueryInvocationSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.QuerySpec;
import org.eclipse.acceleo.model.mtl.impl.spec.TemplateInvocationSpec;
import org.eclipse.acceleo.model.mtl.impl.spec.TemplateSpec;
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
public class MtlFactoryImpl extends EFactoryImpl implements MtlFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static MtlFactory init() {
		try {
			MtlFactory theMtlFactory = (MtlFactory)EPackage.Registry.INSTANCE
					.getEFactory("http://www.eclipse.org/acceleo/mtl/3.0"); //$NON-NLS-1$ 
			if (theMtlFactory != null) {
				return theMtlFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MtlFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtlFactoryImpl() {
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
			case MtlPackage.MODULE:
				return createModule();
			case MtlPackage.TEMPLATE_EXPRESSION:
				return createTemplateExpression();
			case MtlPackage.BLOCK:
				return createBlock();
			case MtlPackage.INIT_SECTION:
				return createInitSection();
			case MtlPackage.TEMPLATE:
				return createTemplate();
			case MtlPackage.TEMPLATE_INVOCATION:
				return createTemplateInvocation();
			case MtlPackage.QUERY:
				return createQuery();
			case MtlPackage.QUERY_INVOCATION:
				return createQueryInvocation();
			case MtlPackage.PROTECTED_AREA_BLOCK:
				return createProtectedAreaBlock();
			case MtlPackage.FOR_BLOCK:
				return createForBlock();
			case MtlPackage.IF_BLOCK:
				return createIfBlock();
			case MtlPackage.LET_BLOCK:
				return createLetBlock();
			case MtlPackage.FILE_BLOCK:
				return createFileBlock();
			case MtlPackage.TRACE_BLOCK:
				return createTraceBlock();
			case MtlPackage.MACRO:
				return createMacro();
			case MtlPackage.MACRO_INVOCATION:
				return createMacroInvocation();
			case MtlPackage.TYPED_MODEL:
				return createTypedModel();
			case MtlPackage.COMMENT:
				return createComment();
			case MtlPackage.DOCUMENTATION:
				return createDocumentation();
			case MtlPackage.COMMENT_BODY:
				return createCommentBody();
			case MtlPackage.MODULE_DOCUMENTATION:
				return createModuleDocumentation();
			case MtlPackage.MODULE_ELEMENT_DOCUMENTATION:
				return createModuleElementDocumentation();
			case MtlPackage.PARAMETER_DOCUMENTATION:
				return createParameterDocumentation();
			default:
				throw new IllegalArgumentException(
						"The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
			case MtlPackage.VISIBILITY_KIND:
				return createVisibilityKindFromString(eDataType, initialValue);
			case MtlPackage.OPEN_MODE_KIND:
				return createOpenModeKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException(
						"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
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
			case MtlPackage.VISIBILITY_KIND:
				return convertVisibilityKindToString(eDataType, instanceValue);
			case MtlPackage.OPEN_MODE_KIND:
				return convertOpenModeKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException(
						"The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Module createModule() {
		ModuleImpl module = new ModuleSpec();
		return module;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TemplateExpression createTemplateExpression() {
		TemplateExpressionImpl templateExpression = new TemplateExpressionImpl();
		return templateExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Block createBlock() {
		BlockImpl block = new BlockImpl();
		return block;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public InitSection createInitSection() {
		InitSectionImpl initSection = new InitSectionSpec();
		return initSection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Template createTemplate() {
		TemplateImpl template = new TemplateSpec();
		return template;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public TemplateInvocation createTemplateInvocation() {
		TemplateInvocationImpl templateInvocation = new TemplateInvocationSpec();
		return templateInvocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Query createQuery() {
		QueryImpl query = new QuerySpec();
		return query;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public QueryInvocation createQueryInvocation() {
		QueryInvocationImpl queryInvocation = new QueryInvocationSpec();
		return queryInvocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ProtectedAreaBlock createProtectedAreaBlock() {
		ProtectedAreaBlockImpl protectedAreaBlock = new ProtectedAreaBlockSpec();
		return protectedAreaBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public ForBlock createForBlock() {
		ForBlockImpl forBlock = new ForBlockSpec();
		return forBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public IfBlock createIfBlock() {
		IfBlockImpl ifBlock = new IfBlockSpec();
		return ifBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public LetBlock createLetBlock() {
		LetBlockImpl letBlock = new LetBlockSpec();
		return letBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public FileBlock createFileBlock() {
		FileBlockImpl fileBlock = new FileBlockSpec();
		return fileBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TraceBlock createTraceBlock() {
		TraceBlockImpl traceBlock = new TraceBlockImpl();
		return traceBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Macro createMacro() {
		MacroImpl macro = new MacroImpl();
		return macro;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MacroInvocation createMacroInvocation() {
		MacroInvocationImpl macroInvocation = new MacroInvocationImpl();
		return macroInvocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypedModel createTypedModel() {
		TypedModelImpl typedModel = new TypedModelImpl();
		return typedModel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentSpec();
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Documentation createDocumentation() {
		DocumentationImpl documentation = new DocumentationSpec();
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public CommentBody createCommentBody() {
		CommentBodyImpl commentBody = new CommentBodySpec();
		return commentBody;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleDocumentation createModuleDocumentation() {
		ModuleDocumentationImpl moduleDocumentation = new ModuleDocumentationImpl();
		return moduleDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleElementDocumentation createModuleElementDocumentation() {
		ModuleElementDocumentationImpl moduleElementDocumentation = new ModuleElementDocumentationImpl();
		return moduleElementDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ParameterDocumentation createParameterDocumentation() {
		ParameterDocumentationImpl parameterDocumentation = new ParameterDocumentationImpl();
		return parameterDocumentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VisibilityKind createVisibilityKindFromString(EDataType eDataType, String initialValue) {
		VisibilityKind result = VisibilityKind.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
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
		if (result == null) {
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	public String convertOpenModeKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtlPackage getMtlPackage() {
		return (MtlPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MtlPackage getPackage() {
		return MtlPackage.eINSTANCE;
	}

} // MtlFactoryImpl
