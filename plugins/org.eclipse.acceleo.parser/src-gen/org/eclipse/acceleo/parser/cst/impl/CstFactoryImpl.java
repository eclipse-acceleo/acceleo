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
public class CstFactoryImpl extends EFactoryImpl implements CstFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static CstFactory init() {
		try {
			CstFactory theCstFactory = (CstFactory)EPackage.Registry.INSTANCE
					.getEFactory("http://www.eclipse.org/acceleo/mtl/cst/3.0"); //$NON-NLS-1$ 
			if (theCstFactory != null) {
				return theCstFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CstFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CstFactoryImpl() {
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
			case CstPackage.MODULE:
				return createModule();
			case CstPackage.MODULE_EXTENDS_VALUE:
				return createModuleExtendsValue();
			case CstPackage.MODULE_IMPORTS_VALUE:
				return createModuleImportsValue();
			case CstPackage.TYPED_MODEL:
				return createTypedModel();
			case CstPackage.COMMENT:
				return createComment();
			case CstPackage.TEMPLATE:
				return createTemplate();
			case CstPackage.TEMPLATE_OVERRIDES_VALUE:
				return createTemplateOverridesValue();
			case CstPackage.VARIABLE:
				return createVariable();
			case CstPackage.TEMPLATE_EXPRESSION:
				return createTemplateExpression();
			case CstPackage.MODEL_EXPRESSION:
				return createModelExpression();
			case CstPackage.TEXT_EXPRESSION:
				return createTextExpression();
			case CstPackage.BLOCK:
				return createBlock();
			case CstPackage.INIT_SECTION:
				return createInitSection();
			case CstPackage.PROTECTED_AREA_BLOCK:
				return createProtectedAreaBlock();
			case CstPackage.FOR_BLOCK:
				return createForBlock();
			case CstPackage.IF_BLOCK:
				return createIfBlock();
			case CstPackage.LET_BLOCK:
				return createLetBlock();
			case CstPackage.FILE_BLOCK:
				return createFileBlock();
			case CstPackage.TRACE_BLOCK:
				return createTraceBlock();
			case CstPackage.MACRO:
				return createMacro();
			case CstPackage.QUERY:
				return createQuery();
			case CstPackage.DOCUMENTATION:
				return createDocumentation();
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
			case CstPackage.VISIBILITY_KIND:
				return createVisibilityKindFromString(eDataType, initialValue);
			case CstPackage.OPEN_MODE_KIND:
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
			case CstPackage.VISIBILITY_KIND:
				return convertVisibilityKindToString(eDataType, instanceValue);
			case CstPackage.OPEN_MODE_KIND:
				return convertOpenModeKindToString(eDataType, instanceValue);
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
	public Module createModule() {
		ModuleImpl module = new ModuleImpl();
		return module;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleExtendsValue createModuleExtendsValue() {
		ModuleExtendsValueImpl moduleExtendsValue = new ModuleExtendsValueImpl();
		return moduleExtendsValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleImportsValue createModuleImportsValue() {
		ModuleImportsValueImpl moduleImportsValue = new ModuleImportsValueImpl();
		return moduleImportsValue;
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
	 * @generated
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Template createTemplate() {
		TemplateImpl template = new TemplateImpl();
		return template;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TemplateOverridesValue createTemplateOverridesValue() {
		TemplateOverridesValueImpl templateOverridesValue = new TemplateOverridesValueImpl();
		return templateOverridesValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
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
	public ModelExpression createModelExpression() {
		ModelExpressionImpl modelExpression = new ModelExpressionImpl();
		return modelExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TextExpression createTextExpression() {
		TextExpressionImpl textExpression = new TextExpressionImpl();
		return textExpression;
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
	 * @generated
	 */
	public InitSection createInitSection() {
		InitSectionImpl initSection = new InitSectionImpl();
		return initSection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProtectedAreaBlock createProtectedAreaBlock() {
		ProtectedAreaBlockImpl protectedAreaBlock = new ProtectedAreaBlockImpl();
		return protectedAreaBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ForBlock createForBlock() {
		ForBlockImpl forBlock = new ForBlockImpl();
		return forBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IfBlock createIfBlock() {
		IfBlockImpl ifBlock = new IfBlockImpl();
		return ifBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LetBlock createLetBlock() {
		LetBlockImpl letBlock = new LetBlockImpl();
		return letBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FileBlock createFileBlock() {
		FileBlockImpl fileBlock = new FileBlockImpl();
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
	public Query createQuery() {
		QueryImpl query = new QueryImpl();
		return query;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Documentation createDocumentation() {
		DocumentationImpl documentation = new DocumentationImpl();
		return documentation;
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
	public CstPackage getCstPackage() {
		return (CstPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CstPackage getPackage() {
		return CstPackage.eINSTANCE;
	}

} // CstFactoryImpl
