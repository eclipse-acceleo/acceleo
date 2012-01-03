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
package org.eclipse.acceleo.compatibility.model.mt.expressions.util;

import java.util.List;

import org.eclipse.acceleo.compatibility.model.mt.core.ASTNode;
import org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Literal;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Not;
import org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsPackage
 * @generated
 */
public class ExpressionsSwitch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static ExpressionsPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ExpressionsSwitch() {
		if (modelPackage == null) {
			modelPackage = ExpressionsPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		} else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case ExpressionsPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				T result = caseExpression(expression);
				if (result == null) {
					result = caseASTNode(expression);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.CALL_SET: {
				CallSet callSet = (CallSet)theEObject;
				T result = caseCallSet(callSet);
				if (result == null) {
					result = caseExpression(callSet);
				}
				if (result == null) {
					result = caseASTNode(callSet);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.CALL: {
				Call call = (Call)theEObject;
				T result = caseCall(call);
				if (result == null) {
					result = caseASTNode(call);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.NOT: {
				Not not = (Not)theEObject;
				T result = caseNot(not);
				if (result == null) {
					result = caseExpression(not);
				}
				if (result == null) {
					result = caseASTNode(not);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.OPERATOR: {
				Operator operator = (Operator)theEObject;
				T result = caseOperator(operator);
				if (result == null) {
					result = caseExpression(operator);
				}
				if (result == null) {
					result = caseASTNode(operator);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.PARENTHESIS: {
				Parenthesis parenthesis = (Parenthesis)theEObject;
				T result = caseParenthesis(parenthesis);
				if (result == null) {
					result = caseExpression(parenthesis);
				}
				if (result == null) {
					result = caseASTNode(parenthesis);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.LITERAL: {
				Literal literal = (Literal)theEObject;
				T result = caseLiteral(literal);
				if (result == null) {
					result = caseExpression(literal);
				}
				if (result == null) {
					result = caseASTNode(literal);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.STRING_LITERAL: {
				StringLiteral stringLiteral = (StringLiteral)theEObject;
				T result = caseStringLiteral(stringLiteral);
				if (result == null) {
					result = caseLiteral(stringLiteral);
				}
				if (result == null) {
					result = caseExpression(stringLiteral);
				}
				if (result == null) {
					result = caseASTNode(stringLiteral);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.INTEGER_LITERAL: {
				IntegerLiteral integerLiteral = (IntegerLiteral)theEObject;
				T result = caseIntegerLiteral(integerLiteral);
				if (result == null) {
					result = caseLiteral(integerLiteral);
				}
				if (result == null) {
					result = caseExpression(integerLiteral);
				}
				if (result == null) {
					result = caseASTNode(integerLiteral);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.DOUBLE_LITERAL: {
				DoubleLiteral doubleLiteral = (DoubleLiteral)theEObject;
				T result = caseDoubleLiteral(doubleLiteral);
				if (result == null) {
					result = caseLiteral(doubleLiteral);
				}
				if (result == null) {
					result = caseExpression(doubleLiteral);
				}
				if (result == null) {
					result = caseASTNode(doubleLiteral);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.BOOLEAN_LITERAL: {
				BooleanLiteral booleanLiteral = (BooleanLiteral)theEObject;
				T result = caseBooleanLiteral(booleanLiteral);
				if (result == null) {
					result = caseLiteral(booleanLiteral);
				}
				if (result == null) {
					result = caseExpression(booleanLiteral);
				}
				if (result == null) {
					result = caseASTNode(booleanLiteral);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case ExpressionsPackage.NULL_LITERAL: {
				NullLiteral nullLiteral = (NullLiteral)theEObject;
				T result = caseNullLiteral(nullLiteral);
				if (result == null) {
					result = caseLiteral(nullLiteral);
				}
				if (result == null) {
					result = caseExpression(nullLiteral);
				}
				if (result == null) {
					result = caseASTNode(nullLiteral);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
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
	 * Returns the result of interpreting the object as an instance of '<em>Call Set</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallSet(CallSet object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Not</em>'. <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Not</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNot(Not object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operator</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperator(Operator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parenthesis</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parenthesis</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParenthesis(Parenthesis object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Double Literal</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleLiteral(DoubleLiteral object) {
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
	public T defaultCase(EObject object) {
		return null;
	}

} // ExpressionsSwitch
