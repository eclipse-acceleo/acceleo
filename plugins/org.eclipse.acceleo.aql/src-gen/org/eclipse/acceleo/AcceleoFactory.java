/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.AcceleoPackage
 * @generated
 */
public interface AcceleoFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	AcceleoFactory eINSTANCE = org.eclipse.acceleo.impl.AcceleoFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Module</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Module</em>'.
	 * @generated
	 */
	Module createModule();

	/**
	 * Returns a new object of class '<em>Error Module</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Module</em>'.
	 * @generated
	 */
	ErrorModule createErrorModule();

	/**
	 * Returns a new object of class '<em>Metamodel</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Metamodel</em>'.
	 * @generated
	 */
	Metamodel createMetamodel();

	/**
	 * Returns a new object of class '<em>Error Metamodel</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Metamodel</em>'.
	 * @generated
	 */
	ErrorMetamodel createErrorMetamodel();

	/**
	 * Returns a new object of class '<em>Import</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Import</em>'.
	 * @generated
	 */
	Import createImport();

	/**
	 * Returns a new object of class '<em>Error Import</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Import</em>'.
	 * @generated
	 */
	ErrorImport createErrorImport();

	/**
	 * Returns a new object of class '<em>Module Reference</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Module Reference</em>'.
	 * @generated
	 */
	ModuleReference createModuleReference();

	/**
	 * Returns a new object of class '<em>Error Module Reference</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Module Reference</em>'.
	 * @generated
	 */
	ErrorModuleReference createErrorModuleReference();

	/**
	 * Returns a new object of class '<em>Comment</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>Block Comment</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block Comment</em>'.
	 * @generated
	 */
	BlockComment createBlockComment();

	/**
	 * Returns a new object of class '<em>Error Block Comment</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Block Comment</em>'.
	 * @generated
	 */
	ErrorBlockComment createErrorBlockComment();

	/**
	 * Returns a new object of class '<em>Error Comment</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Comment</em>'.
	 * @generated
	 */
	ErrorComment createErrorComment();

	/**
	 * Returns a new object of class '<em>Comment Body</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Comment Body</em>'.
	 * @generated
	 */
	CommentBody createCommentBody();

	/**
	 * Returns a new object of class '<em>Module Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Module Documentation</em>'.
	 * @generated
	 */
	ModuleDocumentation createModuleDocumentation();

	/**
	 * Returns a new object of class '<em>Error Module Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Module Documentation</em>'.
	 * @generated
	 */
	ErrorModuleDocumentation createErrorModuleDocumentation();

	/**
	 * Returns a new object of class '<em>Module Element Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Module Element Documentation</em>'.
	 * @generated
	 */
	ModuleElementDocumentation createModuleElementDocumentation();

	/**
	 * Returns a new object of class '<em>Error Module Element Documentation</em>'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Module Element Documentation</em>'.
	 * @generated
	 */
	ErrorModuleElementDocumentation createErrorModuleElementDocumentation();

	/**
	 * Returns a new object of class '<em>Parameter Documentation</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Parameter Documentation</em>'.
	 * @generated
	 */
	ParameterDocumentation createParameterDocumentation();

	/**
	 * Returns a new object of class '<em>Block</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Block</em>'.
	 * @generated
	 */
	Block createBlock();

	/**
	 * Returns a new object of class '<em>Typed Element</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Typed Element</em>'.
	 * @generated
	 */
	TypedElement createTypedElement();

	/**
	 * Returns a new object of class '<em>Template</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Template</em>'.
	 * @generated
	 */
	Template createTemplate();

	/**
	 * Returns a new object of class '<em>Error Template</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Template</em>'.
	 * @generated
	 */
	ErrorTemplate createErrorTemplate();

	/**
	 * Returns a new object of class '<em>Query</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Query</em>'.
	 * @generated
	 */
	Query createQuery();

	/**
	 * Returns a new object of class '<em>Error Query</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Query</em>'.
	 * @generated
	 */
	ErrorQuery createErrorQuery();

	/**
	 * Returns a new object of class '<em>Expression</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expression</em>'.
	 * @generated
	 */
	Expression createExpression();

	/**
	 * Returns a new object of class '<em>Error Expression</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Expression</em>'.
	 * @generated
	 */
	ErrorExpression createErrorExpression();

	/**
	 * Returns a new object of class '<em>Variable</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Variable</em>'.
	 * @generated
	 */
	Variable createVariable();

	/**
	 * Returns a new object of class '<em>Error Variable</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Variable</em>'.
	 * @generated
	 */
	ErrorVariable createErrorVariable();

	/**
	 * Returns a new object of class '<em>Binding</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Binding</em>'.
	 * @generated
	 */
	Binding createBinding();

	/**
	 * Returns a new object of class '<em>Error Binding</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Binding</em>'.
	 * @generated
	 */
	ErrorBinding createErrorBinding();

	/**
	 * Returns a new object of class '<em>Leaf Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Leaf Statement</em>'.
	 * @generated
	 */
	LeafStatement createLeafStatement();

	/**
	 * Returns a new object of class '<em>Expression Statement</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Expression Statement</em>'.
	 * @generated
	 */
	ExpressionStatement createExpressionStatement();

	/**
	 * Returns a new object of class '<em>Error Expression Statement</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Expression Statement</em>'.
	 * @generated
	 */
	ErrorExpressionStatement createErrorExpressionStatement();

	/**
	 * Returns a new object of class '<em>Protected Area</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Protected Area</em>'.
	 * @generated
	 */
	ProtectedArea createProtectedArea();

	/**
	 * Returns a new object of class '<em>Error Protected Area</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error Protected Area</em>'.
	 * @generated
	 */
	ErrorProtectedArea createErrorProtectedArea();

	/**
	 * Returns a new object of class '<em>For Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>For Statement</em>'.
	 * @generated
	 */
	ForStatement createForStatement();

	/**
	 * Returns a new object of class '<em>Error For Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error For Statement</em>'.
	 * @generated
	 */
	ErrorForStatement createErrorForStatement();

	/**
	 * Returns a new object of class '<em>If Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>If Statement</em>'.
	 * @generated
	 */
	IfStatement createIfStatement();

	/**
	 * Returns a new object of class '<em>Error If Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error If Statement</em>'.
	 * @generated
	 */
	ErrorIfStatement createErrorIfStatement();

	/**
	 * Returns a new object of class '<em>Let Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Let Statement</em>'.
	 * @generated
	 */
	LetStatement createLetStatement();

	/**
	 * Returns a new object of class '<em>Error Let Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>Error Let Statement</em>'.
	 * @generated
	 */
	ErrorLetStatement createErrorLetStatement();

	/**
	 * Returns a new object of class '<em>File Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>File Statement</em>'.
	 * @generated
	 */
	FileStatement createFileStatement();

	/**
	 * Returns a new object of class '<em>Error File Statement</em>'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return a new object of class '<em>Error File Statement</em>'.
	 * @generated
	 */
	ErrorFileStatement createErrorFileStatement();

	/**
	 * Returns a new object of class '<em>Text Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Text Statement</em>'.
	 * @generated
	 */
	TextStatement createTextStatement();

	/**
	 * Returns a new object of class '<em>New Line Statement</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>New Line Statement</em>'.
	 * @generated
	 */
	NewLineStatement createNewLineStatement();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	AcceleoPackage getAcceleoPackage();

} // AcceleoFactory
