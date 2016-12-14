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
package org.eclipse.acceleo.util;

import org.eclipse.acceleo.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.AcceleoPackage
 * @generated
 */
public class AcceleoSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AcceleoPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleoSwitch() {
		if (modelPackage == null) {
			modelPackage = AcceleoPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case AcceleoPackage.MODULE: {
			Module module = (Module) theEObject;
			T result = caseModule(module);
			if (result == null)
				result = caseNamedElement(module);
			if (result == null)
				result = caseDocumentedElement(module);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.METAMODEL: {
			Metamodel metamodel = (Metamodel) theEObject;
			T result = caseMetamodel(metamodel);
			if (result == null)
				result = caseASTNode(metamodel);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.MODULE_ELEMENT: {
			ModuleElement moduleElement = (ModuleElement) theEObject;
			T result = caseModuleElement(moduleElement);
			if (result == null)
				result = caseASTNode(moduleElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.COMMENT: {
			Comment comment = (Comment) theEObject;
			T result = caseComment(comment);
			if (result == null)
				result = caseModuleElement(comment);
			if (result == null)
				result = caseASTNode(comment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.COMMENT_BODY: {
			CommentBody commentBody = (CommentBody) theEObject;
			T result = caseCommentBody(commentBody);
			if (result == null)
				result = caseASTNode(commentBody);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.DOCUMENTATION: {
			Documentation documentation = (Documentation) theEObject;
			T result = caseDocumentation(documentation);
			if (result == null)
				result = caseComment(documentation);
			if (result == null)
				result = caseModuleElement(documentation);
			if (result == null)
				result = caseASTNode(documentation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.MODULE_DOCUMENTATION: {
			ModuleDocumentation moduleDocumentation = (ModuleDocumentation) theEObject;
			T result = caseModuleDocumentation(moduleDocumentation);
			if (result == null)
				result = caseDocumentation(moduleDocumentation);
			if (result == null)
				result = caseComment(moduleDocumentation);
			if (result == null)
				result = caseModuleElement(moduleDocumentation);
			if (result == null)
				result = caseASTNode(moduleDocumentation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.MODULE_ELEMENT_DOCUMENTATION: {
			ModuleElementDocumentation moduleElementDocumentation = (ModuleElementDocumentation) theEObject;
			T result = caseModuleElementDocumentation(moduleElementDocumentation);
			if (result == null)
				result = caseDocumentation(moduleElementDocumentation);
			if (result == null)
				result = caseComment(moduleElementDocumentation);
			if (result == null)
				result = caseModuleElement(moduleElementDocumentation);
			if (result == null)
				result = caseASTNode(moduleElementDocumentation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.PARAMETER_DOCUMENTATION: {
			ParameterDocumentation parameterDocumentation = (ParameterDocumentation) theEObject;
			T result = caseParameterDocumentation(parameterDocumentation);
			if (result == null)
				result = caseComment(parameterDocumentation);
			if (result == null)
				result = caseModuleElement(parameterDocumentation);
			if (result == null)
				result = caseASTNode(parameterDocumentation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.DOCUMENTED_ELEMENT: {
			DocumentedElement documentedElement = (DocumentedElement) theEObject;
			T result = caseDocumentedElement(documentedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.NAMED_ELEMENT: {
			NamedElement namedElement = (NamedElement) theEObject;
			T result = caseNamedElement(namedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.AST_NODE: {
			ASTNode astNode = (ASTNode) theEObject;
			T result = caseASTNode(astNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.BLOCK: {
			Block block = (Block) theEObject;
			T result = caseBlock(block);
			if (result == null)
				result = caseASTNode(block);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.TYPED_ELEMENT: {
			TypedElement typedElement = (TypedElement) theEObject;
			T result = caseTypedElement(typedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.TEMPLATE: {
			Template template = (Template) theEObject;
			T result = caseTemplate(template);
			if (result == null)
				result = caseModuleElement(template);
			if (result == null)
				result = caseDocumentedElement(template);
			if (result == null)
				result = caseNamedElement(template);
			if (result == null)
				result = caseASTNode(template);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.QUERY: {
			Query query = (Query) theEObject;
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
				result = caseASTNode(query);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.EXPRESSION: {
			Expression expression = (Expression) theEObject;
			T result = caseExpression(expression);
			if (result == null)
				result = caseASTNode(expression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.VARIABLE: {
			Variable variable = (Variable) theEObject;
			T result = caseVariable(variable);
			if (result == null)
				result = caseTypedElement(variable);
			if (result == null)
				result = caseNamedElement(variable);
			if (result == null)
				result = caseASTNode(variable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.BINDING: {
			Binding binding = (Binding) theEObject;
			T result = caseBinding(binding);
			if (result == null)
				result = caseVariable(binding);
			if (result == null)
				result = caseTypedElement(binding);
			if (result == null)
				result = caseNamedElement(binding);
			if (result == null)
				result = caseASTNode(binding);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.STATEMENT: {
			Statement statement = (Statement) theEObject;
			T result = caseStatement(statement);
			if (result == null)
				result = caseASTNode(statement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.EXPRESSION_STATEMENT: {
			ExpressionStatement expressionStatement = (ExpressionStatement) theEObject;
			T result = caseExpressionStatement(expressionStatement);
			if (result == null)
				result = caseStatement(expressionStatement);
			if (result == null)
				result = caseASTNode(expressionStatement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.PROTECTED_AREA: {
			ProtectedArea protectedArea = (ProtectedArea) theEObject;
			T result = caseProtectedArea(protectedArea);
			if (result == null)
				result = caseStatement(protectedArea);
			if (result == null)
				result = caseASTNode(protectedArea);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.FOR_STATEMENT: {
			ForStatement forStatement = (ForStatement) theEObject;
			T result = caseForStatement(forStatement);
			if (result == null)
				result = caseStatement(forStatement);
			if (result == null)
				result = caseASTNode(forStatement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.IF_STATEMENT: {
			IfStatement ifStatement = (IfStatement) theEObject;
			T result = caseIfStatement(ifStatement);
			if (result == null)
				result = caseStatement(ifStatement);
			if (result == null)
				result = caseASTNode(ifStatement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.LET_STATEMENT: {
			LetStatement letStatement = (LetStatement) theEObject;
			T result = caseLetStatement(letStatement);
			if (result == null)
				result = caseStatement(letStatement);
			if (result == null)
				result = caseASTNode(letStatement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.FILE_STATEMENT: {
			FileStatement fileStatement = (FileStatement) theEObject;
			T result = caseFileStatement(fileStatement);
			if (result == null)
				result = caseStatement(fileStatement);
			if (result == null)
				result = caseASTNode(fileStatement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case AcceleoPackage.TEXT_STATEMENT: {
			TextStatement textStatement = (TextStatement) theEObject;
			T result = caseTextStatement(textStatement);
			if (result == null)
				result = caseStatement(textStatement);
			if (result == null)
				result = caseASTNode(textStatement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModule(Module object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Metamodel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Metamodel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMetamodel(Metamodel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleElement(ModuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComment(Comment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Body</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Body</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentBody(CommentBody object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentation(Documentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleDocumentation(ModuleDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Element Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Element Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleElementDocumentation(ModuleElementDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Documentation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Documentation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterDocumentation(ParameterDocumentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documented Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documented Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentedElement(DocumentedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>AST Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>AST Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseASTNode(ASTNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlock(Block object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedElement(TypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemplate(Template object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuery(Query object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinding(Binding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStatement(Statement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Expression Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Expression Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExpressionStatement(ExpressionStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Protected Area</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Protected Area</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProtectedArea(ProtectedArea object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>For Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>For Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForStatement(ForStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>If Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>If Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfStatement(IfStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Let Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Let Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLetStatement(LetStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileStatement(FileStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextStatement(TextStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //AcceleoSwitch
