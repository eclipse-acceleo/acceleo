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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.AcceleoPackage
 * @generated
 */
public class AcceleoSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static AcceleoPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoSwitch() {
		if (modelPackage == null) {
			modelPackage = AcceleoPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param ePackage
	 *            the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AcceleoPackage.MODULE: {
				org.eclipse.acceleo.Module module = (org.eclipse.acceleo.Module)theEObject;
				T result = caseModule(module);
				if (result == null)
					result = caseNamedElement(module);
				if (result == null)
					result = caseDocumentedElement(module);
				if (result == null)
					result = caseAcceleoASTNode(module);
				if (result == null)
					result = caseASTNode(module);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_MODULE: {
				ErrorModule errorModule = (ErrorModule)theEObject;
				T result = caseErrorModule(errorModule);
				if (result == null)
					result = caseError(errorModule);
				if (result == null)
					result = caseModule(errorModule);
				if (result == null)
					result = caseNamedElement(errorModule);
				if (result == null)
					result = caseDocumentedElement(errorModule);
				if (result == null)
					result = caseAcceleoASTNode(errorModule);
				if (result == null)
					result = caseASTNode(errorModule);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.METAMODEL: {
				Metamodel metamodel = (Metamodel)theEObject;
				T result = caseMetamodel(metamodel);
				if (result == null)
					result = caseAcceleoASTNode(metamodel);
				if (result == null)
					result = caseASTNode(metamodel);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_METAMODEL: {
				ErrorMetamodel errorMetamodel = (ErrorMetamodel)theEObject;
				T result = caseErrorMetamodel(errorMetamodel);
				if (result == null)
					result = caseError(errorMetamodel);
				if (result == null)
					result = caseMetamodel(errorMetamodel);
				if (result == null)
					result = caseAcceleoASTNode(errorMetamodel);
				if (result == null)
					result = caseASTNode(errorMetamodel);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.IMPORT: {
				Import import_ = (Import)theEObject;
				T result = caseImport(import_);
				if (result == null)
					result = caseAcceleoASTNode(import_);
				if (result == null)
					result = caseASTNode(import_);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_IMPORT: {
				ErrorImport errorImport = (ErrorImport)theEObject;
				T result = caseErrorImport(errorImport);
				if (result == null)
					result = caseError(errorImport);
				if (result == null)
					result = caseImport(errorImport);
				if (result == null)
					result = caseAcceleoASTNode(errorImport);
				if (result == null)
					result = caseASTNode(errorImport);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.MODULE_REFERENCE: {
				ModuleReference moduleReference = (ModuleReference)theEObject;
				T result = caseModuleReference(moduleReference);
				if (result == null)
					result = caseAcceleoASTNode(moduleReference);
				if (result == null)
					result = caseASTNode(moduleReference);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_MODULE_REFERENCE: {
				ErrorModuleReference errorModuleReference = (ErrorModuleReference)theEObject;
				T result = caseErrorModuleReference(errorModuleReference);
				if (result == null)
					result = caseError(errorModuleReference);
				if (result == null)
					result = caseModuleReference(errorModuleReference);
				if (result == null)
					result = caseAcceleoASTNode(errorModuleReference);
				if (result == null)
					result = caseASTNode(errorModuleReference);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.MODULE_ELEMENT: {
				ModuleElement moduleElement = (ModuleElement)theEObject;
				T result = caseModuleElement(moduleElement);
				if (result == null)
					result = caseAcceleoASTNode(moduleElement);
				if (result == null)
					result = caseASTNode(moduleElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.BLOCK_COMMENT: {
				BlockComment blockComment = (BlockComment)theEObject;
				T result = caseBlockComment(blockComment);
				if (result == null)
					result = caseComment(blockComment);
				if (result == null)
					result = caseModuleElement(blockComment);
				if (result == null)
					result = caseStatement(blockComment);
				if (result == null)
					result = caseAcceleoASTNode(blockComment);
				if (result == null)
					result = caseASTNode(blockComment);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_BLOCK_COMMENT: {
				ErrorBlockComment errorBlockComment = (ErrorBlockComment)theEObject;
				T result = caseErrorBlockComment(errorBlockComment);
				if (result == null)
					result = caseErrorComment(errorBlockComment);
				if (result == null)
					result = caseBlockComment(errorBlockComment);
				if (result == null)
					result = caseError(errorBlockComment);
				if (result == null)
					result = caseComment(errorBlockComment);
				if (result == null)
					result = caseModuleElement(errorBlockComment);
				if (result == null)
					result = caseStatement(errorBlockComment);
				if (result == null)
					result = caseAcceleoASTNode(errorBlockComment);
				if (result == null)
					result = caseASTNode(errorBlockComment);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.COMMENT: {
				Comment comment = (Comment)theEObject;
				T result = caseComment(comment);
				if (result == null)
					result = caseModuleElement(comment);
				if (result == null)
					result = caseStatement(comment);
				if (result == null)
					result = caseAcceleoASTNode(comment);
				if (result == null)
					result = caseASTNode(comment);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_COMMENT: {
				ErrorComment errorComment = (ErrorComment)theEObject;
				T result = caseErrorComment(errorComment);
				if (result == null)
					result = caseError(errorComment);
				if (result == null)
					result = caseComment(errorComment);
				if (result == null)
					result = caseModuleElement(errorComment);
				if (result == null)
					result = caseStatement(errorComment);
				if (result == null)
					result = caseAcceleoASTNode(errorComment);
				if (result == null)
					result = caseASTNode(errorComment);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.COMMENT_BODY: {
				CommentBody commentBody = (CommentBody)theEObject;
				T result = caseCommentBody(commentBody);
				if (result == null)
					result = caseAcceleoASTNode(commentBody);
				if (result == null)
					result = caseASTNode(commentBody);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.DOCUMENTATION: {
				Documentation documentation = (Documentation)theEObject;
				T result = caseDocumentation(documentation);
				if (result == null)
					result = caseComment(documentation);
				if (result == null)
					result = caseModuleElement(documentation);
				if (result == null)
					result = caseStatement(documentation);
				if (result == null)
					result = caseAcceleoASTNode(documentation);
				if (result == null)
					result = caseASTNode(documentation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.MODULE_DOCUMENTATION: {
				ModuleDocumentation moduleDocumentation = (ModuleDocumentation)theEObject;
				T result = caseModuleDocumentation(moduleDocumentation);
				if (result == null)
					result = caseDocumentation(moduleDocumentation);
				if (result == null)
					result = caseComment(moduleDocumentation);
				if (result == null)
					result = caseModuleElement(moduleDocumentation);
				if (result == null)
					result = caseStatement(moduleDocumentation);
				if (result == null)
					result = caseAcceleoASTNode(moduleDocumentation);
				if (result == null)
					result = caseASTNode(moduleDocumentation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_MODULE_DOCUMENTATION: {
				ErrorModuleDocumentation errorModuleDocumentation = (ErrorModuleDocumentation)theEObject;
				T result = caseErrorModuleDocumentation(errorModuleDocumentation);
				if (result == null)
					result = caseError(errorModuleDocumentation);
				if (result == null)
					result = caseModuleDocumentation(errorModuleDocumentation);
				if (result == null)
					result = caseDocumentation(errorModuleDocumentation);
				if (result == null)
					result = caseASTNode(errorModuleDocumentation);
				if (result == null)
					result = caseComment(errorModuleDocumentation);
				if (result == null)
					result = caseModuleElement(errorModuleDocumentation);
				if (result == null)
					result = caseStatement(errorModuleDocumentation);
				if (result == null)
					result = caseAcceleoASTNode(errorModuleDocumentation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION: {
				ModuleElementDocumentation moduleElementDocumentation = (ModuleElementDocumentation)theEObject;
				T result = caseModuleElementDocumentation(moduleElementDocumentation);
				if (result == null)
					result = caseDocumentation(moduleElementDocumentation);
				if (result == null)
					result = caseComment(moduleElementDocumentation);
				if (result == null)
					result = caseModuleElement(moduleElementDocumentation);
				if (result == null)
					result = caseStatement(moduleElementDocumentation);
				if (result == null)
					result = caseAcceleoASTNode(moduleElementDocumentation);
				if (result == null)
					result = caseASTNode(moduleElementDocumentation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_MODULE_ELEMENT_DOCUMENTATION: {
				ErrorModuleElementDocumentation errorModuleElementDocumentation = (ErrorModuleElementDocumentation)theEObject;
				T result = caseErrorModuleElementDocumentation(errorModuleElementDocumentation);
				if (result == null)
					result = caseError(errorModuleElementDocumentation);
				if (result == null)
					result = caseModuleElementDocumentation(errorModuleElementDocumentation);
				if (result == null)
					result = caseDocumentation(errorModuleElementDocumentation);
				if (result == null)
					result = caseASTNode(errorModuleElementDocumentation);
				if (result == null)
					result = caseComment(errorModuleElementDocumentation);
				if (result == null)
					result = caseModuleElement(errorModuleElementDocumentation);
				if (result == null)
					result = caseStatement(errorModuleElementDocumentation);
				if (result == null)
					result = caseAcceleoASTNode(errorModuleElementDocumentation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.PARAMETER_DOCUMENTATION: {
				ParameterDocumentation parameterDocumentation = (ParameterDocumentation)theEObject;
				T result = caseParameterDocumentation(parameterDocumentation);
				if (result == null)
					result = caseComment(parameterDocumentation);
				if (result == null)
					result = caseModuleElement(parameterDocumentation);
				if (result == null)
					result = caseStatement(parameterDocumentation);
				if (result == null)
					result = caseAcceleoASTNode(parameterDocumentation);
				if (result == null)
					result = caseASTNode(parameterDocumentation);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.DOCUMENTED_ELEMENT: {
				DocumentedElement documentedElement = (DocumentedElement)theEObject;
				T result = caseDocumentedElement(documentedElement);
				if (result == null)
					result = caseAcceleoASTNode(documentedElement);
				if (result == null)
					result = caseASTNode(documentedElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.NAMED_ELEMENT: {
				NamedElement namedElement = (NamedElement)theEObject;
				T result = caseNamedElement(namedElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ACCELEO_AST_NODE: {
				AcceleoASTNode acceleoASTNode = (AcceleoASTNode)theEObject;
				T result = caseAcceleoASTNode(acceleoASTNode);
				if (result == null)
					result = caseASTNode(acceleoASTNode);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR: {
				org.eclipse.acceleo.Error error = (org.eclipse.acceleo.Error)theEObject;
				T result = caseError(error);
				if (result == null)
					result = caseAcceleoASTNode(error);
				if (result == null)
					result = caseASTNode(error);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.BLOCK: {
				Block block = (Block)theEObject;
				T result = caseBlock(block);
				if (result == null)
					result = caseAcceleoASTNode(block);
				if (result == null)
					result = caseASTNode(block);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.TYPED_ELEMENT: {
				TypedElement typedElement = (TypedElement)theEObject;
				T result = caseTypedElement(typedElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.TEMPLATE: {
				Template template = (Template)theEObject;
				T result = caseTemplate(template);
				if (result == null)
					result = caseModuleElement(template);
				if (result == null)
					result = caseDocumentedElement(template);
				if (result == null)
					result = caseNamedElement(template);
				if (result == null)
					result = caseAcceleoASTNode(template);
				if (result == null)
					result = caseASTNode(template);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_TEMPLATE: {
				ErrorTemplate errorTemplate = (ErrorTemplate)theEObject;
				T result = caseErrorTemplate(errorTemplate);
				if (result == null)
					result = caseError(errorTemplate);
				if (result == null)
					result = caseTemplate(errorTemplate);
				if (result == null)
					result = caseModuleElement(errorTemplate);
				if (result == null)
					result = caseDocumentedElement(errorTemplate);
				if (result == null)
					result = caseNamedElement(errorTemplate);
				if (result == null)
					result = caseAcceleoASTNode(errorTemplate);
				if (result == null)
					result = caseASTNode(errorTemplate);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.QUERY: {
				Query query = (Query)theEObject;
				T result = caseQuery(query);
				if (result == null)
					result = caseModuleElement(query);
				if (result == null)
					result = caseDocumentedElement(query);
				if (result == null)
					result = caseNamedElement(query);
				if (result == null)
					result = caseTypedElement(query);
				if (result == null)
					result = caseAcceleoASTNode(query);
				if (result == null)
					result = caseASTNode(query);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_QUERY: {
				ErrorQuery errorQuery = (ErrorQuery)theEObject;
				T result = caseErrorQuery(errorQuery);
				if (result == null)
					result = caseError(errorQuery);
				if (result == null)
					result = caseQuery(errorQuery);
				if (result == null)
					result = caseModuleElement(errorQuery);
				if (result == null)
					result = caseDocumentedElement(errorQuery);
				if (result == null)
					result = caseNamedElement(errorQuery);
				if (result == null)
					result = caseTypedElement(errorQuery);
				if (result == null)
					result = caseAcceleoASTNode(errorQuery);
				if (result == null)
					result = caseASTNode(errorQuery);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null)
					result = caseAcceleoASTNode(expression);
				if (result == null)
					result = caseASTNode(expression);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_EXPRESSION: {
				ErrorExpression errorExpression = (ErrorExpression)theEObject;
				T result = caseErrorExpression(errorExpression);
				if (result == null)
					result = caseError(errorExpression);
				if (result == null)
					result = caseExpression(errorExpression);
				if (result == null)
					result = caseAcceleoASTNode(errorExpression);
				if (result == null)
					result = caseASTNode(errorExpression);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null)
					result = caseTypedElement(variable);
				if (result == null)
					result = caseNamedElement(variable);
				if (result == null)
					result = caseAcceleoASTNode(variable);
				if (result == null)
					result = caseASTNode(variable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_VARIABLE: {
				ErrorVariable errorVariable = (ErrorVariable)theEObject;
				T result = caseErrorVariable(errorVariable);
				if (result == null)
					result = caseError(errorVariable);
				if (result == null)
					result = caseVariable(errorVariable);
				if (result == null)
					result = caseAcceleoASTNode(errorVariable);
				if (result == null)
					result = caseTypedElement(errorVariable);
				if (result == null)
					result = caseNamedElement(errorVariable);
				if (result == null)
					result = caseASTNode(errorVariable);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.BINDING: {
				Binding binding = (Binding)theEObject;
				T result = caseBinding(binding);
				if (result == null)
					result = caseVariable(binding);
				if (result == null)
					result = caseTypedElement(binding);
				if (result == null)
					result = caseNamedElement(binding);
				if (result == null)
					result = caseAcceleoASTNode(binding);
				if (result == null)
					result = caseASTNode(binding);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_BINDING: {
				ErrorBinding errorBinding = (ErrorBinding)theEObject;
				T result = caseErrorBinding(errorBinding);
				if (result == null)
					result = caseError(errorBinding);
				if (result == null)
					result = caseBinding(errorBinding);
				if (result == null)
					result = caseVariable(errorBinding);
				if (result == null)
					result = caseAcceleoASTNode(errorBinding);
				if (result == null)
					result = caseASTNode(errorBinding);
				if (result == null)
					result = caseTypedElement(errorBinding);
				if (result == null)
					result = caseNamedElement(errorBinding);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.STATEMENT: {
				Statement statement = (Statement)theEObject;
				T result = caseStatement(statement);
				if (result == null)
					result = caseAcceleoASTNode(statement);
				if (result == null)
					result = caseASTNode(statement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.LEAF_STATEMENT: {
				LeafStatement leafStatement = (LeafStatement)theEObject;
				T result = caseLeafStatement(leafStatement);
				if (result == null)
					result = caseStatement(leafStatement);
				if (result == null)
					result = caseAcceleoASTNode(leafStatement);
				if (result == null)
					result = caseASTNode(leafStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.EXPRESSION_STATEMENT: {
				ExpressionStatement expressionStatement = (ExpressionStatement)theEObject;
				T result = caseExpressionStatement(expressionStatement);
				if (result == null)
					result = caseLeafStatement(expressionStatement);
				if (result == null)
					result = caseStatement(expressionStatement);
				if (result == null)
					result = caseAcceleoASTNode(expressionStatement);
				if (result == null)
					result = caseASTNode(expressionStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_EXPRESSION_STATEMENT: {
				ErrorExpressionStatement errorExpressionStatement = (ErrorExpressionStatement)theEObject;
				T result = caseErrorExpressionStatement(errorExpressionStatement);
				if (result == null)
					result = caseError(errorExpressionStatement);
				if (result == null)
					result = caseExpressionStatement(errorExpressionStatement);
				if (result == null)
					result = caseLeafStatement(errorExpressionStatement);
				if (result == null)
					result = caseASTNode(errorExpressionStatement);
				if (result == null)
					result = caseStatement(errorExpressionStatement);
				if (result == null)
					result = caseAcceleoASTNode(errorExpressionStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.PROTECTED_AREA: {
				ProtectedArea protectedArea = (ProtectedArea)theEObject;
				T result = caseProtectedArea(protectedArea);
				if (result == null)
					result = caseStatement(protectedArea);
				if (result == null)
					result = caseAcceleoASTNode(protectedArea);
				if (result == null)
					result = caseASTNode(protectedArea);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_PROTECTED_AREA: {
				ErrorProtectedArea errorProtectedArea = (ErrorProtectedArea)theEObject;
				T result = caseErrorProtectedArea(errorProtectedArea);
				if (result == null)
					result = caseError(errorProtectedArea);
				if (result == null)
					result = caseProtectedArea(errorProtectedArea);
				if (result == null)
					result = caseStatement(errorProtectedArea);
				if (result == null)
					result = caseAcceleoASTNode(errorProtectedArea);
				if (result == null)
					result = caseASTNode(errorProtectedArea);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.FOR_STATEMENT: {
				ForStatement forStatement = (ForStatement)theEObject;
				T result = caseForStatement(forStatement);
				if (result == null)
					result = caseStatement(forStatement);
				if (result == null)
					result = caseAcceleoASTNode(forStatement);
				if (result == null)
					result = caseASTNode(forStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_FOR_STATEMENT: {
				ErrorForStatement errorForStatement = (ErrorForStatement)theEObject;
				T result = caseErrorForStatement(errorForStatement);
				if (result == null)
					result = caseError(errorForStatement);
				if (result == null)
					result = caseForStatement(errorForStatement);
				if (result == null)
					result = caseStatement(errorForStatement);
				if (result == null)
					result = caseAcceleoASTNode(errorForStatement);
				if (result == null)
					result = caseASTNode(errorForStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.IF_STATEMENT: {
				IfStatement ifStatement = (IfStatement)theEObject;
				T result = caseIfStatement(ifStatement);
				if (result == null)
					result = caseStatement(ifStatement);
				if (result == null)
					result = caseAcceleoASTNode(ifStatement);
				if (result == null)
					result = caseASTNode(ifStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_IF_STATEMENT: {
				ErrorIfStatement errorIfStatement = (ErrorIfStatement)theEObject;
				T result = caseErrorIfStatement(errorIfStatement);
				if (result == null)
					result = caseError(errorIfStatement);
				if (result == null)
					result = caseIfStatement(errorIfStatement);
				if (result == null)
					result = caseStatement(errorIfStatement);
				if (result == null)
					result = caseAcceleoASTNode(errorIfStatement);
				if (result == null)
					result = caseASTNode(errorIfStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.LET_STATEMENT: {
				LetStatement letStatement = (LetStatement)theEObject;
				T result = caseLetStatement(letStatement);
				if (result == null)
					result = caseStatement(letStatement);
				if (result == null)
					result = caseAcceleoASTNode(letStatement);
				if (result == null)
					result = caseASTNode(letStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_LET_STATEMENT: {
				ErrorLetStatement errorLetStatement = (ErrorLetStatement)theEObject;
				T result = caseErrorLetStatement(errorLetStatement);
				if (result == null)
					result = caseError(errorLetStatement);
				if (result == null)
					result = caseLetStatement(errorLetStatement);
				if (result == null)
					result = caseStatement(errorLetStatement);
				if (result == null)
					result = caseAcceleoASTNode(errorLetStatement);
				if (result == null)
					result = caseASTNode(errorLetStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.FILE_STATEMENT: {
				FileStatement fileStatement = (FileStatement)theEObject;
				T result = caseFileStatement(fileStatement);
				if (result == null)
					result = caseStatement(fileStatement);
				if (result == null)
					result = caseAcceleoASTNode(fileStatement);
				if (result == null)
					result = caseASTNode(fileStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.ERROR_FILE_STATEMENT: {
				ErrorFileStatement errorFileStatement = (ErrorFileStatement)theEObject;
				T result = caseErrorFileStatement(errorFileStatement);
				if (result == null)
					result = caseError(errorFileStatement);
				if (result == null)
					result = caseFileStatement(errorFileStatement);
				if (result == null)
					result = caseStatement(errorFileStatement);
				if (result == null)
					result = caseAcceleoASTNode(errorFileStatement);
				if (result == null)
					result = caseASTNode(errorFileStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.TEXT_STATEMENT: {
				TextStatement textStatement = (TextStatement)theEObject;
				T result = caseTextStatement(textStatement);
				if (result == null)
					result = caseLeafStatement(textStatement);
				if (result == null)
					result = caseStatement(textStatement);
				if (result == null)
					result = caseAcceleoASTNode(textStatement);
				if (result == null)
					result = caseASTNode(textStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AcceleoPackage.NEW_LINE_STATEMENT: {
				NewLineStatement newLineStatement = (NewLineStatement)theEObject;
				T result = caseNewLineStatement(newLineStatement);
				if (result == null)
					result = caseTextStatement(newLineStatement);
				if (result == null)
					result = caseLeafStatement(newLineStatement);
				if (result == null)
					result = caseStatement(newLineStatement);
				if (result == null)
					result = caseAcceleoASTNode(newLineStatement);
				if (result == null)
					result = caseASTNode(newLineStatement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModule(org.eclipse.acceleo.Module object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Module</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorModule(ErrorModule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metamodel</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metamodel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetamodel(Metamodel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Metamodel</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Metamodel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorMetamodel(ErrorMetamodel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImport(Import object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Import</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorImport(ErrorImport object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Reference</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleReference(ModuleReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Module Reference</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Module Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorModuleReference(ErrorModuleReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleElement(ModuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComment(Comment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block Comment</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlockComment(BlockComment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Block Comment</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Block Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorBlockComment(ErrorBlockComment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Comment</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorComment(ErrorComment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Body</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentBody(CommentBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documentation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentation(Documentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Documentation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleDocumentation(ModuleDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Module Documentation</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Module Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorModuleDocumentation(ErrorModuleDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Element
	 * Documentation</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null
	 * result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Element
	 *         Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleElementDocumentation(ModuleElementDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Module Element
	 * Documentation</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null
	 * result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Module Element
	 *         Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorModuleElementDocumentation(ErrorModuleElementDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Documentation</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterDocumentation(ParameterDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documented Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documented Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentedElement(DocumentedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>AST Node</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>AST Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoASTNode(AcceleoASTNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseError(org.eclipse.acceleo.Error object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlock(Block object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedElement(TypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemplate(Template object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Template</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorTemplate(ErrorTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuery(Query object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Query</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorQuery(ErrorQuery object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorExpression(ErrorExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Variable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorVariable(ErrorVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binding</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinding(Binding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Binding</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorBinding(ErrorBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatement(Statement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Leaf Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Leaf Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeafStatement(LeafStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpressionStatement(ExpressionStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Expression Statement</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Expression Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorExpressionStatement(ErrorExpressionStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Protected Area</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Protected Area</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProtectedArea(ProtectedArea object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Protected Area</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Protected Area</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorProtectedArea(ErrorProtectedArea object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>For Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>For Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForStatement(ForStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error For Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error For Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorForStatement(ErrorForStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>If Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>If Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfStatement(IfStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error If Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error If Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorIfStatement(ErrorIfStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Let Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Let Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLetStatement(LetStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Let Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Let Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorLetStatement(ErrorLetStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileStatement(FileStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error File Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error File Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorFileStatement(ErrorFileStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextStatement(TextStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>New Line Statement</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>New Line Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNewLineStatement(NewLineStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>AST Node</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>AST Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseASTNode(ASTNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // AcceleoSwitch
