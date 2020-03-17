/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast.util;

import org.eclipse.acceleo.query.ast.And;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Implies;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.Or;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.ast.AstPackage
 * @generated
 */
public class AstSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static AstPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AstSwitch() {
		if (modelPackage == null) {
			modelPackage = AstPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @parameter ePackage the package in question.
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
			case AstPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.VAR_REF: {
				VarRef varRef = (VarRef)theEObject;
				T result = caseVarRef(varRef);
				if (result == null)
					result = caseExpression(varRef);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.CALL: {
				Call call = (Call)theEObject;
				T result = caseCall(call);
				if (result == null)
					result = caseExpression(call);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.LITERAL: {
				Literal literal = (Literal)theEObject;
				T result = caseLiteral(literal);
				if (result == null)
					result = caseExpression(literal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.INTEGER_LITERAL: {
				IntegerLiteral integerLiteral = (IntegerLiteral)theEObject;
				T result = caseIntegerLiteral(integerLiteral);
				if (result == null)
					result = caseLiteral(integerLiteral);
				if (result == null)
					result = caseExpression(integerLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.REAL_LITERAL: {
				RealLiteral realLiteral = (RealLiteral)theEObject;
				T result = caseRealLiteral(realLiteral);
				if (result == null)
					result = caseLiteral(realLiteral);
				if (result == null)
					result = caseExpression(realLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.STRING_LITERAL: {
				StringLiteral stringLiteral = (StringLiteral)theEObject;
				T result = caseStringLiteral(stringLiteral);
				if (result == null)
					result = caseLiteral(stringLiteral);
				if (result == null)
					result = caseExpression(stringLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.BOOLEAN_LITERAL: {
				BooleanLiteral booleanLiteral = (BooleanLiteral)theEObject;
				T result = caseBooleanLiteral(booleanLiteral);
				if (result == null)
					result = caseLiteral(booleanLiteral);
				if (result == null)
					result = caseExpression(booleanLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ENUM_LITERAL: {
				EnumLiteral enumLiteral = (EnumLiteral)theEObject;
				T result = caseEnumLiteral(enumLiteral);
				if (result == null)
					result = caseLiteral(enumLiteral);
				if (result == null)
					result = caseExpression(enumLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.TYPE_LITERAL: {
				TypeLiteral typeLiteral = (TypeLiteral)theEObject;
				T result = caseTypeLiteral(typeLiteral);
				if (result == null)
					result = caseLiteral(typeLiteral);
				if (result == null)
					result = caseExpression(typeLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.TYPE_SET_LITERAL: {
				TypeSetLiteral typeSetLiteral = (TypeSetLiteral)theEObject;
				T result = caseTypeSetLiteral(typeSetLiteral);
				if (result == null)
					result = caseTypeLiteral(typeSetLiteral);
				if (result == null)
					result = caseLiteral(typeSetLiteral);
				if (result == null)
					result = caseExpression(typeSetLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.COLLECTION_TYPE_LITERAL: {
				CollectionTypeLiteral collectionTypeLiteral = (CollectionTypeLiteral)theEObject;
				T result = caseCollectionTypeLiteral(collectionTypeLiteral);
				if (result == null)
					result = caseTypeLiteral(collectionTypeLiteral);
				if (result == null)
					result = caseLiteral(collectionTypeLiteral);
				if (result == null)
					result = caseExpression(collectionTypeLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.LAMBDA: {
				Lambda lambda = (Lambda)theEObject;
				T result = caseLambda(lambda);
				if (result == null)
					result = caseLiteral(lambda);
				if (result == null)
					result = caseExpression(lambda);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.NULL_LITERAL: {
				NullLiteral nullLiteral = (NullLiteral)theEObject;
				T result = caseNullLiteral(nullLiteral);
				if (result == null)
					result = caseLiteral(nullLiteral);
				if (result == null)
					result = caseExpression(nullLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.SET_IN_EXTENSION_LITERAL: {
				SetInExtensionLiteral setInExtensionLiteral = (SetInExtensionLiteral)theEObject;
				T result = caseSetInExtensionLiteral(setInExtensionLiteral);
				if (result == null)
					result = caseLiteral(setInExtensionLiteral);
				if (result == null)
					result = caseExpression(setInExtensionLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.SEQUENCE_IN_EXTENSION_LITERAL: {
				SequenceInExtensionLiteral sequenceInExtensionLiteral = (SequenceInExtensionLiteral)theEObject;
				T result = caseSequenceInExtensionLiteral(sequenceInExtensionLiteral);
				if (result == null)
					result = caseLiteral(sequenceInExtensionLiteral);
				if (result == null)
					result = caseExpression(sequenceInExtensionLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.VARIABLE_DECLARATION: {
				VariableDeclaration variableDeclaration = (VariableDeclaration)theEObject;
				T result = caseVariableDeclaration(variableDeclaration);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR: {
				org.eclipse.acceleo.query.ast.Error error = (org.eclipse.acceleo.query.ast.Error)theEObject;
				T result = caseError(error);
				if (result == null)
					result = caseExpression(error);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_EXPRESSION: {
				ErrorExpression errorExpression = (ErrorExpression)theEObject;
				T result = caseErrorExpression(errorExpression);
				if (result == null)
					result = caseError(errorExpression);
				if (result == null)
					result = caseExpression(errorExpression);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_TYPE_LITERAL: {
				ErrorTypeLiteral errorTypeLiteral = (ErrorTypeLiteral)theEObject;
				T result = caseErrorTypeLiteral(errorTypeLiteral);
				if (result == null)
					result = caseError(errorTypeLiteral);
				if (result == null)
					result = caseTypeLiteral(errorTypeLiteral);
				if (result == null)
					result = caseLiteral(errorTypeLiteral);
				if (result == null)
					result = caseExpression(errorTypeLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL: {
				ErrorEClassifierTypeLiteral errorEClassifierTypeLiteral = (ErrorEClassifierTypeLiteral)theEObject;
				T result = caseErrorEClassifierTypeLiteral(errorEClassifierTypeLiteral);
				if (result == null)
					result = caseErrorTypeLiteral(errorEClassifierTypeLiteral);
				if (result == null)
					result = caseError(errorEClassifierTypeLiteral);
				if (result == null)
					result = caseTypeLiteral(errorEClassifierTypeLiteral);
				if (result == null)
					result = caseLiteral(errorEClassifierTypeLiteral);
				if (result == null)
					result = caseExpression(errorEClassifierTypeLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_ENUM_LITERAL: {
				ErrorEnumLiteral errorEnumLiteral = (ErrorEnumLiteral)theEObject;
				T result = caseErrorEnumLiteral(errorEnumLiteral);
				if (result == null)
					result = caseError(errorEnumLiteral);
				if (result == null)
					result = caseEnumLiteral(errorEnumLiteral);
				if (result == null)
					result = caseLiteral(errorEnumLiteral);
				if (result == null)
					result = caseExpression(errorEnumLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_CALL: {
				ErrorCall errorCall = (ErrorCall)theEObject;
				T result = caseErrorCall(errorCall);
				if (result == null)
					result = caseError(errorCall);
				if (result == null)
					result = caseCall(errorCall);
				if (result == null)
					result = caseExpression(errorCall);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_VARIABLE_DECLARATION: {
				ErrorVariableDeclaration errorVariableDeclaration = (ErrorVariableDeclaration)theEObject;
				T result = caseErrorVariableDeclaration(errorVariableDeclaration);
				if (result == null)
					result = caseError(errorVariableDeclaration);
				if (result == null)
					result = caseVariableDeclaration(errorVariableDeclaration);
				if (result == null)
					result = caseExpression(errorVariableDeclaration);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_STRING_LITERAL: {
				ErrorStringLiteral errorStringLiteral = (ErrorStringLiteral)theEObject;
				T result = caseErrorStringLiteral(errorStringLiteral);
				if (result == null)
					result = caseError(errorStringLiteral);
				if (result == null)
					result = caseStringLiteral(errorStringLiteral);
				if (result == null)
					result = caseLiteral(errorStringLiteral);
				if (result == null)
					result = caseExpression(errorStringLiteral);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_CONDITIONAL: {
				ErrorConditional errorConditional = (ErrorConditional)theEObject;
				T result = caseErrorConditional(errorConditional);
				if (result == null)
					result = caseError(errorConditional);
				if (result == null)
					result = caseConditional(errorConditional);
				if (result == null)
					result = caseExpression(errorConditional);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.BINDING: {
				Binding binding = (Binding)theEObject;
				T result = caseBinding(binding);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.ERROR_BINDING: {
				ErrorBinding errorBinding = (ErrorBinding)theEObject;
				T result = caseErrorBinding(errorBinding);
				if (result == null)
					result = caseError(errorBinding);
				if (result == null)
					result = caseBinding(errorBinding);
				if (result == null)
					result = caseExpression(errorBinding);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.LET: {
				Let let = (Let)theEObject;
				T result = caseLet(let);
				if (result == null)
					result = caseExpression(let);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.CONDITIONAL: {
				Conditional conditional = (Conditional)theEObject;
				T result = caseConditional(conditional);
				if (result == null)
					result = caseExpression(conditional);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.OR: {
				Or or = (Or)theEObject;
				T result = caseOr(or);
				if (result == null)
					result = caseCall(or);
				if (result == null)
					result = caseExpression(or);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.AND: {
				And and = (And)theEObject;
				T result = caseAnd(and);
				if (result == null)
					result = caseCall(and);
				if (result == null)
					result = caseExpression(and);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AstPackage.IMPLIES: {
				Implies implies = (Implies)theEObject;
				T result = caseImplies(implies);
				if (result == null)
					result = caseCall(implies);
				if (result == null)
					result = caseExpression(implies);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>Var Ref</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Var Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVarRef(VarRef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCall(Call object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteral(Literal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerLiteral(IntegerLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Real Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Real Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRealLiteral(RealLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>String Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>String Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringLiteral(StringLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanLiteral(BooleanLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enum Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumLiteral(EnumLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeLiteral(TypeLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Set Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Set Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeSetLiteral(TypeSetLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Collection Type Literal</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Collection Type Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCollectionTypeLiteral(CollectionTypeLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Lambda</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lambda</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLambda(Lambda object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullLiteral(NullLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set In Extension Literal</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set In Extension Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetInExtensionLiteral(SetInExtensionLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence In Extension Literal</em>
	 * '. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence In Extension Literal</em>
	 *         '.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSequenceInExtensionLiteral(SequenceInExtensionLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Declaration</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableDeclaration(VariableDeclaration object) {
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
	public T caseError(org.eclipse.acceleo.query.ast.Error object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Error Type Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Type Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorTypeLiteral(ErrorTypeLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Error EClassifier Type Literal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Error EClassifier Type Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorEClassifierTypeLiteral(ErrorEClassifierTypeLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Enum Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Enum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorEnumLiteral(ErrorEnumLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Call</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Call</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorCall(ErrorCall object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Variable Declaration</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Variable Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorVariableDeclaration(ErrorVariableDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error String Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error String Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorStringLiteral(ErrorStringLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Error Conditional</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Error Conditional</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseErrorConditional(ErrorConditional object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Let</em>'. <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Let</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLet(Let object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditional(Conditional object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Or</em>'. <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Or</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOr(Or object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>And</em>'. <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>And</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnd(And object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Implies</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Implies</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImplies(Implies object) {
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

} // AstSwitch
