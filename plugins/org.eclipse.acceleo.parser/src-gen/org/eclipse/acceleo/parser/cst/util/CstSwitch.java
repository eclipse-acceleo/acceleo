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
package org.eclipse.acceleo.parser.cst.util;

import java.util.List;

import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
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
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateExpression;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TraceBlock;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage
 * @generated
 */
public class CstSwitch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static CstPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CstSwitch() {
		if (modelPackage == null) {
			modelPackage = CstPackage.eINSTANCE;
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
			case CstPackage.CST_NODE: {
				CSTNode cstNode = (CSTNode)theEObject;
				T result = caseCSTNode(cstNode);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.MODULE: {
				Module module = (Module)theEObject;
				T result = caseModule(module);
				if (result == null) {
					result = caseEPackage(module);
				}
				if (result == null) {
					result = caseCSTNode(module);
				}
				if (result == null) {
					result = caseENamedElement(module);
				}
				if (result == null) {
					result = caseEModelElement(module);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.MODULE_EXTENDS_VALUE: {
				ModuleExtendsValue moduleExtendsValue = (ModuleExtendsValue)theEObject;
				T result = caseModuleExtendsValue(moduleExtendsValue);
				if (result == null) {
					result = caseCSTNode(moduleExtendsValue);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.MODULE_IMPORTS_VALUE: {
				ModuleImportsValue moduleImportsValue = (ModuleImportsValue)theEObject;
				T result = caseModuleImportsValue(moduleImportsValue);
				if (result == null) {
					result = caseCSTNode(moduleImportsValue);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.TYPED_MODEL: {
				TypedModel typedModel = (TypedModel)theEObject;
				T result = caseTypedModel(typedModel);
				if (result == null) {
					result = caseCSTNode(typedModel);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.MODULE_ELEMENT: {
				ModuleElement moduleElement = (ModuleElement)theEObject;
				T result = caseModuleElement(moduleElement);
				if (result == null) {
					result = caseCSTNode(moduleElement);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.COMMENT: {
				Comment comment = (Comment)theEObject;
				T result = caseComment(comment);
				if (result == null) {
					result = caseModuleElement(comment);
				}
				if (result == null) {
					result = caseTemplateExpression(comment);
				}
				if (result == null) {
					result = caseCSTNode(comment);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.TEMPLATE: {
				Template template = (Template)theEObject;
				T result = caseTemplate(template);
				if (result == null) {
					result = caseBlock(template);
				}
				if (result == null) {
					result = caseModuleElement(template);
				}
				if (result == null) {
					result = caseTemplateExpression(template);
				}
				if (result == null) {
					result = caseCSTNode(template);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.TEMPLATE_OVERRIDES_VALUE: {
				TemplateOverridesValue templateOverridesValue = (TemplateOverridesValue)theEObject;
				T result = caseTemplateOverridesValue(templateOverridesValue);
				if (result == null) {
					result = caseCSTNode(templateOverridesValue);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null) {
					result = caseCSTNode(variable);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.TEMPLATE_EXPRESSION: {
				TemplateExpression templateExpression = (TemplateExpression)theEObject;
				T result = caseTemplateExpression(templateExpression);
				if (result == null) {
					result = caseCSTNode(templateExpression);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.MODEL_EXPRESSION: {
				ModelExpression modelExpression = (ModelExpression)theEObject;
				T result = caseModelExpression(modelExpression);
				if (result == null) {
					result = caseTemplateExpression(modelExpression);
				}
				if (result == null) {
					result = caseCSTNode(modelExpression);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.TEXT_EXPRESSION: {
				TextExpression textExpression = (TextExpression)theEObject;
				T result = caseTextExpression(textExpression);
				if (result == null) {
					result = caseTemplateExpression(textExpression);
				}
				if (result == null) {
					result = caseCSTNode(textExpression);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.BLOCK: {
				Block block = (Block)theEObject;
				T result = caseBlock(block);
				if (result == null) {
					result = caseTemplateExpression(block);
				}
				if (result == null) {
					result = caseCSTNode(block);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.INIT_SECTION: {
				InitSection initSection = (InitSection)theEObject;
				T result = caseInitSection(initSection);
				if (result == null) {
					result = caseCSTNode(initSection);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.PROTECTED_AREA_BLOCK: {
				ProtectedAreaBlock protectedAreaBlock = (ProtectedAreaBlock)theEObject;
				T result = caseProtectedAreaBlock(protectedAreaBlock);
				if (result == null) {
					result = caseBlock(protectedAreaBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(protectedAreaBlock);
				}
				if (result == null) {
					result = caseCSTNode(protectedAreaBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.FOR_BLOCK: {
				ForBlock forBlock = (ForBlock)theEObject;
				T result = caseForBlock(forBlock);
				if (result == null) {
					result = caseBlock(forBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(forBlock);
				}
				if (result == null) {
					result = caseCSTNode(forBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.IF_BLOCK: {
				IfBlock ifBlock = (IfBlock)theEObject;
				T result = caseIfBlock(ifBlock);
				if (result == null) {
					result = caseBlock(ifBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(ifBlock);
				}
				if (result == null) {
					result = caseCSTNode(ifBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.LET_BLOCK: {
				LetBlock letBlock = (LetBlock)theEObject;
				T result = caseLetBlock(letBlock);
				if (result == null) {
					result = caseBlock(letBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(letBlock);
				}
				if (result == null) {
					result = caseCSTNode(letBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.FILE_BLOCK: {
				FileBlock fileBlock = (FileBlock)theEObject;
				T result = caseFileBlock(fileBlock);
				if (result == null) {
					result = caseBlock(fileBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(fileBlock);
				}
				if (result == null) {
					result = caseCSTNode(fileBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.TRACE_BLOCK: {
				TraceBlock traceBlock = (TraceBlock)theEObject;
				T result = caseTraceBlock(traceBlock);
				if (result == null) {
					result = caseBlock(traceBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(traceBlock);
				}
				if (result == null) {
					result = caseCSTNode(traceBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.MACRO: {
				Macro macro = (Macro)theEObject;
				T result = caseMacro(macro);
				if (result == null) {
					result = caseBlock(macro);
				}
				if (result == null) {
					result = caseModuleElement(macro);
				}
				if (result == null) {
					result = caseTemplateExpression(macro);
				}
				if (result == null) {
					result = caseCSTNode(macro);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.QUERY: {
				Query query = (Query)theEObject;
				T result = caseQuery(query);
				if (result == null) {
					result = caseModuleElement(query);
				}
				if (result == null) {
					result = caseCSTNode(query);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case CstPackage.DOCUMENTATION: {
				Documentation documentation = (Documentation)theEObject;
				T result = caseDocumentation(documentation);
				if (result == null) {
					result = caseComment(documentation);
				}
				if (result == null) {
					result = caseModuleElement(documentation);
				}
				if (result == null) {
					result = caseTemplateExpression(documentation);
				}
				if (result == null) {
					result = caseCSTNode(documentation);
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
	 * Returns the result of interpreting the object as an instance of '<em>CST Node</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CST Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseCSTNode(CSTNode object) {
		return null;
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
	@SuppressWarnings("unused")
	public T caseModule(Module object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Extends Value</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Extends Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseModuleExtendsValue(ModuleExtendsValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Imports Value</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Imports Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseModuleImportsValue(ModuleImportsValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Typed Model</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Typed Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseTypedModel(TypedModel object) {
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
	@SuppressWarnings("unused")
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
	@SuppressWarnings("unused")
	public T caseComment(Comment object) {
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
	@SuppressWarnings("unused")
	public T caseTemplate(Template object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template Overrides Value</em>'.
	 * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template Overrides Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseTemplateOverridesValue(TemplateOverridesValue object) {
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
	@SuppressWarnings("unused")
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Template Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Template Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseTemplateExpression(TemplateExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseModelExpression(ModelExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseTextExpression(TextExpression object) {
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
	@SuppressWarnings("unused")
	public T caseBlock(Block object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Init Section</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Init Section</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseInitSection(InitSection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Protected Area Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Protected Area Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseProtectedAreaBlock(ProtectedAreaBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>For Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>For Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseForBlock(ForBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>If Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>If Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseIfBlock(IfBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Let Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Let Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseLetBlock(LetBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>File Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>File Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseFileBlock(FileBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trace Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trace Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseTraceBlock(TraceBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Macro</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseMacro(Macro object) {
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
	@SuppressWarnings("unused")
	public T caseQuery(Query object) {
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
	@SuppressWarnings("unused")
	public T caseDocumentation(Documentation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EModel Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseEModelElement(EModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ENamed Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ENamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseENamedElement(ENamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EPackage</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EPackage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T caseEPackage(EPackage object) {
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
	@SuppressWarnings("unused")
	public T defaultCase(EObject object) {
		return null;
	}

} // CstSwitch
