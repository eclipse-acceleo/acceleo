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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage
 * @generated
 */
public interface MtlFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	MtlFactory eINSTANCE = org.eclipse.acceleo.model.mtl.impl.MtlFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Module</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Module</em>'.
	 * @generated
	 */
	Module createModule();

	/**
	 * Returns a new object of class '<em>Template Expression</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Template Expression</em>'.
	 * @generated
	 */
	TemplateExpression createTemplateExpression();

	/**
	 * Returns a new object of class '<em>Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block</em>'.
	 * @generated
	 */
	Block createBlock();

	/**
	 * Returns a new object of class '<em>Init Section</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Init Section</em>'.
	 * @generated
	 */
	InitSection createInitSection();

	/**
	 * Returns a new object of class '<em>Template</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Template</em>'.
	 * @generated
	 */
	Template createTemplate();

	/**
	 * Returns a new object of class '<em>Template Invocation</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Template Invocation</em>'.
	 * @generated
	 */
	TemplateInvocation createTemplateInvocation();

	/**
	 * Returns a new object of class '<em>Query</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Query</em>'.
	 * @generated
	 */
	Query createQuery();

	/**
	 * Returns a new object of class '<em>Query Invocation</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Query Invocation</em>'.
	 * @generated
	 */
	QueryInvocation createQueryInvocation();

	/**
	 * Returns a new object of class '<em>Protected Area Block</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Protected Area Block</em>'.
	 * @generated
	 */
	ProtectedAreaBlock createProtectedAreaBlock();

	/**
	 * Returns a new object of class '<em>For Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>For Block</em>'.
	 * @generated
	 */
	ForBlock createForBlock();

	/**
	 * Returns a new object of class '<em>If Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>If Block</em>'.
	 * @generated
	 */
	IfBlock createIfBlock();

	/**
	 * Returns a new object of class '<em>Let Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Let Block</em>'.
	 * @generated
	 */
	LetBlock createLetBlock();

	/**
	 * Returns a new object of class '<em>File Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>File Block</em>'.
	 * @generated
	 */
	FileBlock createFileBlock();

	/**
	 * Returns a new object of class '<em>Trace Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Trace Block</em>'.
	 * @generated
	 */
	TraceBlock createTraceBlock();

	/**
	 * Returns a new object of class '<em>Macro</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Macro</em>'.
	 * @generated
	 */
	Macro createMacro();

	/**
	 * Returns a new object of class '<em>Macro Invocation</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Macro Invocation</em>'.
	 * @generated
	 */
	MacroInvocation createMacroInvocation();

	/**
	 * Returns a new object of class '<em>Typed Model</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Typed Model</em>'.
	 * @generated
	 */
	TypedModel createTypedModel();

	/**
	 * Returns a new object of class '<em>Comment</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 * @since 3.1
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>Documentation</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Documentation</em>'.
	 * @generated
	 * @since 3.1
	 */
	Documentation createDocumentation();

	/**
	 * Returns a new object of class '<em>Comment Body</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Comment Body</em>'.
	 * @generated
	 * @since 3.1
	 */
	CommentBody createCommentBody();

	/**
	 * Returns a new object of class '<em>Module Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Module Documentation</em>'.
	 * @generated
	 * @since 3.1
	 */
	ModuleDocumentation createModuleDocumentation();

	/**
	 * Returns a new object of class '<em>Module Element Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Module Element Documentation</em>'.
	 * @generated
	 * @since 3.1
	 */
	ModuleElementDocumentation createModuleElementDocumentation();

	/**
	 * Returns a new object of class '<em>Parameter Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Parameter Documentation</em>'.
	 * @generated
	 * @since 3.1
	 */
	ParameterDocumentation createParameterDocumentation();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	MtlPackage getMtlPackage();

} // MtlFactory
