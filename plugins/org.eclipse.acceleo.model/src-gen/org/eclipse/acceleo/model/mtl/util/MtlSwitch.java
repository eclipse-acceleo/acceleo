/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.util;

import java.util.List;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.emf.ecore.EClass;
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
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage
 * @generated
 * @since 0.8
 */
public class MtlSwitch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static MtlPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtlSwitch() {
		if (modelPackage == null) {
			modelPackage = MtlPackage.eINSTANCE;
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
			case MtlPackage.MODULE: {
				Module module = (Module)theEObject;
				T result = caseModule(module);
				if (result == null) {
					result = caseEPackage(module);
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
			case MtlPackage.MODULE_ELEMENT: {
				ModuleElement moduleElement = (ModuleElement)theEObject;
				T result = caseModuleElement(moduleElement);
				if (result == null) {
					result = caseENamedElement(moduleElement);
				}
				if (result == null) {
					result = caseASTNode(moduleElement);
				}
				if (result == null) {
					result = caseEModelElement(moduleElement);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.TEMPLATE_EXPRESSION: {
				TemplateExpression templateExpression = (TemplateExpression)theEObject;
				T result = caseTemplateExpression(templateExpression);
				if (result == null) {
					result = caseOCLExpression_1(templateExpression);
				}
				if (result == null) {
					result = caseASTNode(templateExpression);
				}
				if (result == null) {
					result = caseETypedElement(templateExpression);
				}
				if (result == null) {
					result = caseOCLExpression(templateExpression);
				}
				if (result == null) {
					result = caseENamedElement(templateExpression);
				}
				if (result == null) {
					result = caseTypedElement(templateExpression);
				}
				if (result == null) {
					result = caseVisitable(templateExpression);
				}
				if (result == null) {
					result = caseEModelElement(templateExpression);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.BLOCK: {
				Block block = (Block)theEObject;
				T result = caseBlock(block);
				if (result == null) {
					result = caseTemplateExpression(block);
				}
				if (result == null) {
					result = caseOCLExpression_1(block);
				}
				if (result == null) {
					result = caseASTNode(block);
				}
				if (result == null) {
					result = caseETypedElement(block);
				}
				if (result == null) {
					result = caseOCLExpression(block);
				}
				if (result == null) {
					result = caseENamedElement(block);
				}
				if (result == null) {
					result = caseTypedElement(block);
				}
				if (result == null) {
					result = caseVisitable(block);
				}
				if (result == null) {
					result = caseEModelElement(block);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.INIT_SECTION: {
				InitSection initSection = (InitSection)theEObject;
				T result = caseInitSection(initSection);
				if (result == null) {
					result = caseASTNode(initSection);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.TEMPLATE: {
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
					result = caseENamedElement(template);
				}
				if (result == null) {
					result = caseASTNode(template);
				}
				if (result == null) {
					result = caseOCLExpression_1(template);
				}
				if (result == null) {
					result = caseEModelElement(template);
				}
				if (result == null) {
					result = caseETypedElement(template);
				}
				if (result == null) {
					result = caseOCLExpression(template);
				}
				if (result == null) {
					result = caseTypedElement(template);
				}
				if (result == null) {
					result = caseVisitable(template);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.TEMPLATE_INVOCATION: {
				TemplateInvocation templateInvocation = (TemplateInvocation)theEObject;
				T result = caseTemplateInvocation(templateInvocation);
				if (result == null) {
					result = caseTemplateExpression(templateInvocation);
				}
				if (result == null) {
					result = caseOCLExpression_1(templateInvocation);
				}
				if (result == null) {
					result = caseASTNode(templateInvocation);
				}
				if (result == null) {
					result = caseETypedElement(templateInvocation);
				}
				if (result == null) {
					result = caseOCLExpression(templateInvocation);
				}
				if (result == null) {
					result = caseENamedElement(templateInvocation);
				}
				if (result == null) {
					result = caseTypedElement(templateInvocation);
				}
				if (result == null) {
					result = caseVisitable(templateInvocation);
				}
				if (result == null) {
					result = caseEModelElement(templateInvocation);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.QUERY: {
				Query query = (Query)theEObject;
				T result = caseQuery(query);
				if (result == null) {
					result = caseModuleElement(query);
				}
				if (result == null) {
					result = caseENamedElement(query);
				}
				if (result == null) {
					result = caseASTNode(query);
				}
				if (result == null) {
					result = caseEModelElement(query);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.QUERY_INVOCATION: {
				QueryInvocation queryInvocation = (QueryInvocation)theEObject;
				T result = caseQueryInvocation(queryInvocation);
				if (result == null) {
					result = caseTemplateExpression(queryInvocation);
				}
				if (result == null) {
					result = caseOCLExpression_1(queryInvocation);
				}
				if (result == null) {
					result = caseASTNode(queryInvocation);
				}
				if (result == null) {
					result = caseETypedElement(queryInvocation);
				}
				if (result == null) {
					result = caseOCLExpression(queryInvocation);
				}
				if (result == null) {
					result = caseENamedElement(queryInvocation);
				}
				if (result == null) {
					result = caseTypedElement(queryInvocation);
				}
				if (result == null) {
					result = caseVisitable(queryInvocation);
				}
				if (result == null) {
					result = caseEModelElement(queryInvocation);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.PROTECTED_AREA_BLOCK: {
				ProtectedAreaBlock protectedAreaBlock = (ProtectedAreaBlock)theEObject;
				T result = caseProtectedAreaBlock(protectedAreaBlock);
				if (result == null) {
					result = caseBlock(protectedAreaBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(protectedAreaBlock);
				}
				if (result == null) {
					result = caseOCLExpression_1(protectedAreaBlock);
				}
				if (result == null) {
					result = caseASTNode(protectedAreaBlock);
				}
				if (result == null) {
					result = caseETypedElement(protectedAreaBlock);
				}
				if (result == null) {
					result = caseOCLExpression(protectedAreaBlock);
				}
				if (result == null) {
					result = caseENamedElement(protectedAreaBlock);
				}
				if (result == null) {
					result = caseTypedElement(protectedAreaBlock);
				}
				if (result == null) {
					result = caseVisitable(protectedAreaBlock);
				}
				if (result == null) {
					result = caseEModelElement(protectedAreaBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.FOR_BLOCK: {
				ForBlock forBlock = (ForBlock)theEObject;
				T result = caseForBlock(forBlock);
				if (result == null) {
					result = caseBlock(forBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(forBlock);
				}
				if (result == null) {
					result = caseOCLExpression_1(forBlock);
				}
				if (result == null) {
					result = caseASTNode(forBlock);
				}
				if (result == null) {
					result = caseETypedElement(forBlock);
				}
				if (result == null) {
					result = caseOCLExpression(forBlock);
				}
				if (result == null) {
					result = caseENamedElement(forBlock);
				}
				if (result == null) {
					result = caseTypedElement(forBlock);
				}
				if (result == null) {
					result = caseVisitable(forBlock);
				}
				if (result == null) {
					result = caseEModelElement(forBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.IF_BLOCK: {
				IfBlock ifBlock = (IfBlock)theEObject;
				T result = caseIfBlock(ifBlock);
				if (result == null) {
					result = caseBlock(ifBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(ifBlock);
				}
				if (result == null) {
					result = caseOCLExpression_1(ifBlock);
				}
				if (result == null) {
					result = caseASTNode(ifBlock);
				}
				if (result == null) {
					result = caseETypedElement(ifBlock);
				}
				if (result == null) {
					result = caseOCLExpression(ifBlock);
				}
				if (result == null) {
					result = caseENamedElement(ifBlock);
				}
				if (result == null) {
					result = caseTypedElement(ifBlock);
				}
				if (result == null) {
					result = caseVisitable(ifBlock);
				}
				if (result == null) {
					result = caseEModelElement(ifBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.LET_BLOCK: {
				LetBlock letBlock = (LetBlock)theEObject;
				T result = caseLetBlock(letBlock);
				if (result == null) {
					result = caseBlock(letBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(letBlock);
				}
				if (result == null) {
					result = caseOCLExpression_1(letBlock);
				}
				if (result == null) {
					result = caseASTNode(letBlock);
				}
				if (result == null) {
					result = caseETypedElement(letBlock);
				}
				if (result == null) {
					result = caseOCLExpression(letBlock);
				}
				if (result == null) {
					result = caseENamedElement(letBlock);
				}
				if (result == null) {
					result = caseTypedElement(letBlock);
				}
				if (result == null) {
					result = caseVisitable(letBlock);
				}
				if (result == null) {
					result = caseEModelElement(letBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.FILE_BLOCK: {
				FileBlock fileBlock = (FileBlock)theEObject;
				T result = caseFileBlock(fileBlock);
				if (result == null) {
					result = caseBlock(fileBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(fileBlock);
				}
				if (result == null) {
					result = caseOCLExpression_1(fileBlock);
				}
				if (result == null) {
					result = caseASTNode(fileBlock);
				}
				if (result == null) {
					result = caseETypedElement(fileBlock);
				}
				if (result == null) {
					result = caseOCLExpression(fileBlock);
				}
				if (result == null) {
					result = caseENamedElement(fileBlock);
				}
				if (result == null) {
					result = caseTypedElement(fileBlock);
				}
				if (result == null) {
					result = caseVisitable(fileBlock);
				}
				if (result == null) {
					result = caseEModelElement(fileBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.TRACE_BLOCK: {
				TraceBlock traceBlock = (TraceBlock)theEObject;
				T result = caseTraceBlock(traceBlock);
				if (result == null) {
					result = caseBlock(traceBlock);
				}
				if (result == null) {
					result = caseTemplateExpression(traceBlock);
				}
				if (result == null) {
					result = caseOCLExpression_1(traceBlock);
				}
				if (result == null) {
					result = caseASTNode(traceBlock);
				}
				if (result == null) {
					result = caseETypedElement(traceBlock);
				}
				if (result == null) {
					result = caseOCLExpression(traceBlock);
				}
				if (result == null) {
					result = caseENamedElement(traceBlock);
				}
				if (result == null) {
					result = caseTypedElement(traceBlock);
				}
				if (result == null) {
					result = caseVisitable(traceBlock);
				}
				if (result == null) {
					result = caseEModelElement(traceBlock);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.MACRO: {
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
					result = caseENamedElement(macro);
				}
				if (result == null) {
					result = caseASTNode(macro);
				}
				if (result == null) {
					result = caseOCLExpression_1(macro);
				}
				if (result == null) {
					result = caseEModelElement(macro);
				}
				if (result == null) {
					result = caseETypedElement(macro);
				}
				if (result == null) {
					result = caseOCLExpression(macro);
				}
				if (result == null) {
					result = caseTypedElement(macro);
				}
				if (result == null) {
					result = caseVisitable(macro);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.MACRO_INVOCATION: {
				MacroInvocation macroInvocation = (MacroInvocation)theEObject;
				T result = caseMacroInvocation(macroInvocation);
				if (result == null) {
					result = caseTemplateExpression(macroInvocation);
				}
				if (result == null) {
					result = caseOCLExpression_1(macroInvocation);
				}
				if (result == null) {
					result = caseASTNode(macroInvocation);
				}
				if (result == null) {
					result = caseETypedElement(macroInvocation);
				}
				if (result == null) {
					result = caseOCLExpression(macroInvocation);
				}
				if (result == null) {
					result = caseENamedElement(macroInvocation);
				}
				if (result == null) {
					result = caseTypedElement(macroInvocation);
				}
				if (result == null) {
					result = caseVisitable(macroInvocation);
				}
				if (result == null) {
					result = caseEModelElement(macroInvocation);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case MtlPackage.TYPED_MODEL: {
				TypedModel typedModel = (TypedModel)theEObject;
				T result = caseTypedModel(typedModel);
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
	 * Returns the result of interpreting the object as an instance of ' <em>Module</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModule(Module object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Module Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Module Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModuleElement(ModuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Template Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Template Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemplateExpression(TemplateExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Block</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBlock(Block object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Init Section</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Init Section</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInitSection(InitSection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Template</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemplate(Template object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Template Invocation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Template Invocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTemplateInvocation(TemplateInvocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Query</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuery(Query object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Query Invocation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Query Invocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQueryInvocation(QueryInvocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Protected Area Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Protected Area Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProtectedAreaBlock(ProtectedAreaBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>For Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>For Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForBlock(ForBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>If Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>If Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfBlock(IfBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Let Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Let Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLetBlock(LetBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>File Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>File Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFileBlock(FileBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Trace Block</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Trace Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTraceBlock(TraceBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Macro</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Macro</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacro(Macro object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Macro Invocation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Macro Invocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMacroInvocation(MacroInvocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Typed Model</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Typed Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypedModel(TypedModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>EModel Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEModelElement(EModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>ENamed Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>ENamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseENamedElement(ENamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>EPackage</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>EPackage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEPackage(EPackage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>AST Node</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>AST Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseASTNode(ASTNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>ETyped Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>ETyped Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseETypedElement(ETypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Typed Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Typed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <C> T caseTypedElement(TypedElement<C> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>Visitable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>Visitable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVisitable(Visitable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>OCL Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>OCL Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <C> T caseOCLExpression(OCLExpression<C> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>OCL Expression</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>OCL Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOCLExpression_1(org.eclipse.ocl.ecore.OCLExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of ' <em>EObject</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch, but this is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of ' <em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} // MtlSwitch
