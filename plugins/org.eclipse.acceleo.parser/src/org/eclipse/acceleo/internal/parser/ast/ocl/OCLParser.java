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
package org.eclipse.acceleo.internal.parser.ast.ocl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoEnvironment;
import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.CallExp;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.ocl.helper.ConstraintKind;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ocl.utilities.CallingASTNode;
import org.eclipse.ocl.utilities.TypedASTNode;

/**
 * To parse an OCL Expression. Make sure to call dispose() once you're done with the parser.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class OCLParser {

	/**
	 * To set the value of the 'Source' attribute for each Acceleo MTL annotation.
	 */
	public static final String ANNOTATION_SOURCE = "MTL"; //$NON-NLS-1$

	/**
	 * To set the value of the 'Source' attribute for each Acceleo MTL non-standard annotation.
	 */
	public static final String ANNOTATION_NON_STANDARD_SOURCE = "MTL non-standard"; //$NON-NLS-1$

	/**
	 * To set the value of the 'type' key for each Acceleo annotation.
	 */
	public static final String ANNOTATION_KEY_TYPE = "type"; //$NON-NLS-1$

	/**
	 * A stack to push and pop the current context of an OCL expression.
	 */
	private Stack<EClassifier> contextStack = new Stack<EClassifier>();

	/**
	 * Helper for OCL expression parsing.
	 */
	private OCL.Helper helper;

	/**
	 * Environment for OCL expression parsing.
	 */
	private AcceleoEnvironment environment;

	/**
	 * To save the container of one variable before to add it in the OCL environment.
	 * <p>
	 * Remark : The following code changes the container of the object 'newVariable' :
	 * <p>
	 * <b>helper.getOCL().getEnvironment()).addElement(newVariable)</b>
	 */
	private Map<Variable, EObject> saveVariableContainer = new HashMap<Variable, EObject>();

	/**
	 * To save the containing feature of one variable before to add it in the OCL environment. Remark : The
	 * following code changes the container of the object 'newVariable' :
	 * <p>
	 * <b>helper.getOCL().getEnvironment()).addElement(newVariable)</b>
	 */
	private Map<Variable, EStructuralFeature> saveVariableContainingFeature = new HashMap<Variable, EStructuralFeature>();

	/**
	 * To save the EOperation element created by the OCL environment for each module element.
	 */
	private Map<EOperation, ModuleElement> eOperation2ModuleElement = new HashMap<EOperation, ModuleElement>();

	/**
	 * Create a new OCL parser for Acceleo.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	public OCLParser(Resource oclEnvironmentResource) {
		init(oclEnvironmentResource);
	}

	/**
	 * Initializes and clears the OCL parser.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	public void init(Resource oclEnvironmentResource) {
		helper = OCL.newInstance(AcceleoEnvironmentFactory.ACCELEO_INSTANCE, oclEnvironmentResource)
				.createOCLHelper();
		environment = (AcceleoEnvironment)helper.getEnvironment();
		helper.setContext(EcorePackage.eINSTANCE.getEObject());
		contextStack.clear();
		saveVariableContainer.clear();
		saveVariableContainingFeature.clear();
		eOperation2ModuleElement.clear();
	}

	/**
	 * Next OCL expressions will have this new context.
	 * 
	 * @param newContext
	 *            is the new context
	 */
	public void pushContext(EClassifier newContext) {
		helper.setContext(newContext);
		contextStack.push(newContext);
	}

	/**
	 * Restores the previous context.
	 */
	public void popContext() {
		contextStack.pop();
		if (contextStack.isEmpty()) {
			helper.setContext(EcorePackage.eINSTANCE.getEObject());
		} else {
			helper.setContext(contextStack.peek());
		}
	}

	/**
	 * Add a new variable in the current parsing scope.
	 * 
	 * @param newVariable
	 *            a new ocl variable to consider while parsing.
	 */
	public void addVariableToScope(Variable newVariable) {
		if (newVariable != null && newVariable.getName() != null && newVariable.getName().length() > 0) {
			if (newVariable.getType() == null) {
				resolveType(newVariable);
			}
			EObject eContainer = newVariable.eContainer();
			EStructuralFeature eContainingFeature = newVariable.eContainingFeature();
			saveVariableContainer.put(newVariable, eContainer);
			saveVariableContainingFeature.put(newVariable, eContainingFeature);
			helper.getOCL().getEnvironment().addElement(newVariable.getName(), newVariable, true);
		}
	}

	/**
	 * Resolves the type of the variable using the current OCL environment, and the "Acceleo.type"
	 * EAnnotation.
	 * 
	 * @param newVariable
	 *            We have to resolve the type of this variable.
	 */
	private void resolveType(Variable newVariable) {
		EAnnotation eAnnotation = newVariable.getEAnnotation(ANNOTATION_SOURCE);
		if (eAnnotation != null) {
			String type = eAnnotation.getDetails().get(ANNOTATION_KEY_TYPE);
			if (type != null) {
				EClassifier eType = lookupClassifier(type);
				if (eType != null && eType != getInvalidType()) {
					newVariable.getEAnnotations().remove(eAnnotation);
				}
				newVariable.setType(eType);
			}
		}
	}

	/**
	 * Add the accessible variables (at the given index) in the current parsing scope. It browses the given
	 * module, and its children that are accessible at the given index. It returns the context classifier. It
	 * is defined by a variable (a loop variable, the first parameter of a template...)
	 * 
	 * @param oModule
	 *            is the current module
	 * @param offset
	 *            is the offset in the text
	 * @return the context classifier, or null if the current block doesn't define a new context
	 */
	public EClassifier addRecursivelyVariablesToScopeAndGetContextClassifierAt(
			org.eclipse.acceleo.model.mtl.Module oModule, int offset) {
		Variable eContext = null;
		Iterator<org.eclipse.acceleo.model.mtl.ModuleElement> ownedModuleElementIt = oModule
				.getOwnedModuleElement().iterator();
		while (ownedModuleElementIt.hasNext()) {
			org.eclipse.acceleo.model.mtl.ModuleElement oModuleElement = ownedModuleElementIt.next();
			if (offset > oModuleElement.getStartPosition() && offset < oModuleElement.getEndPosition()) {
				if (oModuleElement instanceof org.eclipse.acceleo.model.mtl.Template) {
					org.eclipse.acceleo.model.mtl.Template oTemplate = (org.eclipse.acceleo.model.mtl.Template)oModuleElement;
					if (oTemplate.getParameter().size() > 0) {
						eContext = oTemplate.getParameter().get(0);
					}
					addVariablesToScope(oTemplate.getParameter());
				} else if (oModuleElement instanceof org.eclipse.acceleo.model.mtl.Query) {
					org.eclipse.acceleo.model.mtl.Query oQuery = (org.eclipse.acceleo.model.mtl.Query)oModuleElement;
					if (oQuery.getParameter().size() > 0) {
						eContext = oQuery.getParameter().get(0);
					}
					addVariablesToScope(oQuery.getParameter());
				} else if (oModuleElement instanceof org.eclipse.acceleo.model.mtl.Macro) {
					org.eclipse.acceleo.model.mtl.Macro oMacro = (org.eclipse.acceleo.model.mtl.Macro)oModuleElement;
					if (oMacro.getParameter().size() > 0) {
						eContext = oMacro.getParameter().get(0);
					}
					addVariablesToScope(oMacro.getParameter());
				}
				if (oModuleElement instanceof org.eclipse.acceleo.model.mtl.Block) {
					Variable oContext = addVariablesToScope(
							(org.eclipse.acceleo.model.mtl.Block)oModuleElement, offset);
					if (oContext != null) {
						eContext = oContext;
					}
				}
				break;
			}
		}
		if (eContext != null) {
			return eContext.getType();
		}
		return null;
	}

	/**
	 * Add a list of variables in the current parsing scope.
	 * 
	 * @param newVariables
	 *            are new variables to consider
	 */
	private void addVariablesToScope(List<Variable> newVariables) {
		Iterator<Variable> newVariablesIt = new ArrayList<Variable>(newVariables).iterator();
		while (newVariablesIt.hasNext()) {
			addVariableToScope(newVariablesIt.next());
		}
	}

	/**
	 * Add the variables of a 'Block' in the current parsing scope, and return the contextual variable (a loop
	 * variable for example).
	 * 
	 * @param block
	 *            is a block to consider
	 * @param offset
	 *            is the offset in the text
	 * @return the contextual variable, or null if the block doesn't define a new context
	 */
	private Variable addVariablesToScope(org.eclipse.acceleo.model.mtl.Block block, int offset) {
		Variable eContext = null;
		if (offset > block.getStartPosition() && offset < block.getEndPosition()) {
			if (block instanceof org.eclipse.acceleo.model.mtl.ForBlock) {
				// We have to get the variable before
				eContext = ((org.eclipse.acceleo.model.mtl.ForBlock)block).getLoopVariable();
				addVariableToScope(((org.eclipse.acceleo.model.mtl.ForBlock)block).getLoopVariable());
			}
			if (block instanceof org.eclipse.acceleo.model.mtl.LetBlock) {
				// We have to get the variable before
				eContext = ((org.eclipse.acceleo.model.mtl.LetBlock)block).getLetVariable();
				addVariableToScope(((org.eclipse.acceleo.model.mtl.LetBlock)block).getLetVariable());
			}
			if (block.getInit() != null) {
				addVariablesToScope(block.getInit().getVariable());
			}
			Iterator<EObject> eContentsIt = block.eContents().iterator();
			while (eContentsIt.hasNext()) {
				EObject eContent = eContentsIt.next();
				if (eContent instanceof org.eclipse.acceleo.model.mtl.Block) {
					Variable oContext = addVariablesToScope((org.eclipse.acceleo.model.mtl.Block)eContent,
							offset);
					if (oContext != null) {
						eContext = oContext;
						break;
					}
				}
			}
		}
		return eContext;
	}

	/**
	 * Add the behavioral features of a module in the current parsing scope. It browses the given module, its
	 * children, and the imported modules...
	 * 
	 * @param oModule
	 *            is the current module
	 */
	public void addRecursivelyBehavioralFeaturesToScope(org.eclipse.acceleo.model.mtl.Module oModule) {
		addRecursivelyBehavioralFeaturesToScope(oModule, true, true, null);
	}

	/**
	 * Add the behavioral features of a module in the current parsing scope. It browses the given module, its
	 * children, and the imported modules...
	 * 
	 * @param oModule
	 *            is the current module
	 * @param operationWithReceiver
	 *            indicates if we add the operations for which the first parameter is the receiver
	 * @param operationWithoutReceiver
	 *            indicates if we add the operations for which there is a default receiver, the first
	 *            parameter isn't the receiver
	 * @param startsWith
	 *            is a filter for the named elements to keep
	 */
	public void addRecursivelyBehavioralFeaturesToScope(org.eclipse.acceleo.model.mtl.Module oModule,
			boolean operationWithReceiver, boolean operationWithoutReceiver, String startsWith) {
		addBehavioralFeaturesToScope(oModule, VisibilityKind.PRIVATE, operationWithReceiver,
				operationWithoutReceiver, startsWith);
		Iterator<org.eclipse.acceleo.model.mtl.Module> itOtherModules = oModule.getImports().iterator();
		while (itOtherModules.hasNext()) {
			org.eclipse.acceleo.model.mtl.Module oOtherModule = itOtherModules.next();
			addBehavioralFeaturesToScope(oOtherModule, VisibilityKind.PUBLIC, operationWithReceiver,
					operationWithoutReceiver, startsWith);
			// We add the operation from the modules extended by the import.
			this.addRecursivelyBehavioralFeaturesFromImports(oOtherModule, operationWithReceiver,
					operationWithoutReceiver, startsWith);
		}
		List<org.eclipse.acceleo.model.mtl.Module> allExtends = new ArrayList<org.eclipse.acceleo.model.mtl.Module>();
		computeAllExtends(allExtends, oModule);
		itOtherModules = allExtends.iterator();
		while (itOtherModules.hasNext()) {
			org.eclipse.acceleo.model.mtl.Module oOtherModule = itOtherModules.next();
			addBehavioralFeaturesToScope(oOtherModule, VisibilityKind.PROTECTED, operationWithReceiver,
					operationWithoutReceiver, startsWith);
		}
	}

	/**
	 * Add the behavioral features of the extended modules in the current parsing scope.
	 * 
	 * @param oModule
	 *            is the current module
	 * @param operationWithReceiver
	 *            indicates if we add the operations for which the first parameter is the receiver
	 * @param operationWithoutReceiver
	 *            indicates if we add the operations for which there is a default receiver, the first
	 *            parameter isn't the receiver
	 * @param startsWith
	 *            is a filter for the named elements to keep
	 */
	private void addRecursivelyBehavioralFeaturesFromImports(org.eclipse.acceleo.model.mtl.Module oModule,
			boolean operationWithReceiver, boolean operationWithoutReceiver, String startsWith) {
		List<Module> extendsList = oModule.getExtends();
		if (extendsList != null && extendsList.size() > 0) {
			for (Module module : extendsList) {
				addBehavioralFeaturesToScope(module, VisibilityKind.PUBLIC, operationWithReceiver,
						operationWithoutReceiver, startsWith);
			}
		}
	}

	/**
	 * It creates the list of all inherited modules.
	 * 
	 * @param allExtends
	 *            the list to compute
	 * @param oModule
	 *            is the current module
	 */
	private void computeAllExtends(List<org.eclipse.acceleo.model.mtl.Module> allExtends,
			org.eclipse.acceleo.model.mtl.Module oModule) {
		List<org.eclipse.acceleo.model.mtl.Module> toBrowse = new ArrayList<org.eclipse.acceleo.model.mtl.Module>();
		Iterator<org.eclipse.acceleo.model.mtl.Module> itOtherModules = oModule.getExtends().iterator();
		while (itOtherModules.hasNext()) {
			org.eclipse.acceleo.model.mtl.Module oOtherModule = itOtherModules.next();
			if (!allExtends.contains(oOtherModule)) {
				allExtends.add(oOtherModule);
				toBrowse.add(oOtherModule);
			}
		}
		itOtherModules = toBrowse.iterator();
		while (itOtherModules.hasNext()) {
			org.eclipse.acceleo.model.mtl.Module oOtherModule = itOtherModules.next();
			computeAllExtends(allExtends, oOtherModule);
		}
	}

	/**
	 * Add the behavioral features of a module in the current parsing scope. It browses the given module and
	 * its children.
	 * 
	 * @param oModule
	 *            is the current module
	 * @param lowestVisibilityKind
	 *            is the lowest visibility value, VisibilityKind.PRIVATE indicates that private members are
	 *            included
	 * @param operationWithReceiver
	 *            indicates if we add the operations for which the first parameter is the receiver
	 * @param operationWithoutReceiver
	 *            indicates if we add the operations for which there is a default receiver, the first
	 *            parameter isn't the receiver
	 * @param startsWith
	 *            is a filter for the named elements to keep
	 */
	private void addBehavioralFeaturesToScope(org.eclipse.acceleo.model.mtl.Module oModule,
			VisibilityKind lowestVisibilityKind, boolean operationWithReceiver,
			boolean operationWithoutReceiver, String startsWith) {
		Iterator<EObject> itObjects = oModule.eAllContents();
		while (itObjects.hasNext()) {
			EObject eObject = itObjects.next();
			if (eObject instanceof org.eclipse.acceleo.model.mtl.ModuleElement) {
				org.eclipse.acceleo.model.mtl.ModuleElement eModuleElement = (org.eclipse.acceleo.model.mtl.ModuleElement)eObject;
				if (eModuleElement.getVisibility().getValue() >= lowestVisibilityKind.getValue()
						&& (startsWith == null || (eModuleElement.getName() != null && eModuleElement
								.getName().toLowerCase().startsWith(startsWith.toLowerCase())))) {
					if (eObject instanceof org.eclipse.acceleo.model.mtl.Template) {
						org.eclipse.acceleo.model.mtl.Template eTemplate = (org.eclipse.acceleo.model.mtl.Template)eObject;
						addTemplateToScope(eTemplate, operationWithReceiver, operationWithoutReceiver);
					} else if (eObject instanceof org.eclipse.acceleo.model.mtl.Query) {
						org.eclipse.acceleo.model.mtl.Query eQuery = (org.eclipse.acceleo.model.mtl.Query)eObject;
						addQueryToScope(eQuery, operationWithReceiver, operationWithoutReceiver);
					} else if (eObject instanceof org.eclipse.acceleo.model.mtl.Macro) {
						org.eclipse.acceleo.model.mtl.Macro eMacro = (org.eclipse.acceleo.model.mtl.Macro)eObject;
						addMacroToScope(eMacro, operationWithReceiver, operationWithoutReceiver);
					}
				}
			}
		}
	}

	/**
	 * Add a new template in the current parsing scope.
	 * 
	 * @param newTemplate
	 *            a new template to consider while parsing.
	 * @param operationWithReceiver
	 *            indicates if we add the operations for which the first parameter is the receiver
	 * @param operationWithoutReceiver
	 *            indicates if we add the operations for which there is a default receiver, the first
	 *            parameter isn't the receiver
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void addTemplateToScope(Template newTemplate, boolean operationWithReceiver,
			boolean operationWithoutReceiver) {
		EClassifier owner = EcorePackage.eINSTANCE.getEObject();
		if (newTemplate != null && newTemplate.getName() != null && newTemplate.getName().length() > 0) {
			EClassifier type = environment.getOCLStandardLibrary().getString();
			List params = newTemplate.getParameter();
			Iterator<Variable> paramsIt = params.iterator();
			while (paramsIt.hasNext()) {
				resolveType(paramsIt.next());
			}
			if (operationWithoutReceiver) {
				Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
				EOperation eOperation = environment.defineOperation(owner, newTemplate.getName(), type,
						params, constraint);
				if (eOperation != null) {
					eOperation2ModuleElement.put(eOperation, newTemplate);
					EAnnotation eAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
							.createEAnnotation();
					eAnnotation.setSource(ANNOTATION_SOURCE);
					eAnnotation.getReferences().add(newTemplate);
					eOperation.getEAnnotations().add(eAnnotation);
				}
			}
			// We create a new EOperation for the template, but the first parameter is the owner...
			if (operationWithReceiver && params.size() > 0 && ((Variable)params.get(0)).getType() != null) {
				owner = ((Variable)params.get(0)).getType();
				params = new BasicEList<Variable>(params);
				params.remove(0);
				Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
				EOperation eOperation = environment.defineOperation(owner, newTemplate.getName(), type,
						params, constraint);
				if (eOperation != null) {
					eOperation2ModuleElement.put(eOperation, newTemplate);
					EAnnotation eAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
							.createEAnnotation();
					eAnnotation.setSource(ANNOTATION_SOURCE);
					eAnnotation.getReferences().add(newTemplate);
					eOperation.getEAnnotations().add(eAnnotation);
				}
			}
		}

	}

	/**
	 * Add a new query in the current parsing scope.
	 * 
	 * @param newQuery
	 *            a new query to consider while parsing.
	 * @param operationWithReceiver
	 *            indicates if we add the operations for which the first parameter is the receiver
	 * @param operationWithoutReceiver
	 *            indicates if we add the operations for which there is a default receiver, the first
	 *            parameter isn't the receiver
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void addQueryToScope(Query newQuery, boolean operationWithReceiver,
			boolean operationWithoutReceiver) {
		EClassifier owner = EcorePackage.eINSTANCE.getEObject();
		if (newQuery != null && newQuery.getName() != null && newQuery.getName().length() > 0) {
			resolveType(newQuery);
			List params = newQuery.getParameter();
			Iterator<Variable> paramsIt = params.iterator();
			while (paramsIt.hasNext()) {
				resolveType(paramsIt.next());
			}
			if (operationWithoutReceiver) {
				Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
				EOperation eOperation = environment.defineOperation(owner, newQuery.getName(), newQuery
						.getType(), params, constraint);
				if (eOperation != null) {
					eOperation2ModuleElement.put(eOperation, newQuery);
					EAnnotation eAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
							.createEAnnotation();
					eAnnotation.setSource(ANNOTATION_SOURCE);
					eAnnotation.getReferences().add(newQuery);
					eOperation.getEAnnotations().add(eAnnotation);
				}
			}
			// We create a new EOperation for the query, but the first parameter is the owner...
			if (operationWithReceiver && params.size() > 0 && ((Variable)params.get(0)).getType() != null) {
				owner = ((Variable)params.get(0)).getType();
				params = new BasicEList<Variable>(params);
				params.remove(0);
				Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
				EOperation eOperation = environment.defineOperation(owner, newQuery.getName(), newQuery
						.getType(), params, constraint);
				if (eOperation != null) {
					eOperation2ModuleElement.put(eOperation, newQuery);
					EAnnotation eAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
							.createEAnnotation();
					eAnnotation.setSource(ANNOTATION_SOURCE);
					eAnnotation.getReferences().add(newQuery);
					eOperation.getEAnnotations().add(eAnnotation);
				}
			}
		}
	}

	/**
	 * Resolves the type of the query using the current OCL environment, and the "Acceleo.type" EAnnotation.
	 * 
	 * @param newQuery
	 *            We have to resolve the type of this query.
	 */
	private void resolveType(Query newQuery) {
		EAnnotation eAnnotation = newQuery.getEAnnotation(ANNOTATION_SOURCE);
		if (eAnnotation != null) {
			String type = eAnnotation.getDetails().get(ANNOTATION_KEY_TYPE);
			if (type != null) {
				EClassifier eType = lookupClassifier(type);
				if (eType != null && eType != getInvalidType()) {
					newQuery.getEAnnotations().remove(eAnnotation);
				}
				newQuery.setType(eType);
			}
		}
	}

	/**
	 * Add a new macro in the current parsing scope.
	 * 
	 * @param newMacro
	 *            a new macro to consider while parsing.
	 * @param operationWithReceiver
	 *            indicates if we add the operations for which the first parameter is the receiver
	 * @param operationWithoutReceiver
	 *            indicates if we add the operations for which there is a default receiver, the first
	 *            parameter isn't the receiver
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void addMacroToScope(Macro newMacro, boolean operationWithReceiver,
			boolean operationWithoutReceiver) {
		EClassifier owner = EcorePackage.eINSTANCE.getEObject();
		if (newMacro != null && newMacro.getName() != null && newMacro.getName().length() > 0) {
			resolveType(newMacro);
			List params = newMacro.getParameter();
			Iterator<Variable> paramsIt = params.iterator();
			while (paramsIt.hasNext()) {
				resolveType(paramsIt.next());
			}
			if (operationWithoutReceiver) {
				Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
				EOperation eOperation = environment.defineOperation(owner, newMacro.getName(), newMacro
						.getType(), params, constraint);
				if (eOperation != null) {
					eOperation2ModuleElement.put(eOperation, newMacro);
					EAnnotation eAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
							.createEAnnotation();
					eAnnotation.setSource(ANNOTATION_SOURCE);
					eAnnotation.getReferences().add(newMacro);
					eOperation.getEAnnotations().add(eAnnotation);
				}
			}
			// We create a new EOperation for the query, but the first parameter is the owner...
			if (operationWithReceiver && params.size() > 0 && ((Variable)params.get(0)).getType() != null) {
				owner = ((Variable)params.get(0)).getType();
				params = new BasicEList<Variable>(params);
				params.remove(0);
				Constraint constraint = EcoreFactory.eINSTANCE.createConstraint();
				EOperation eOperation = environment.defineOperation(owner, newMacro.getName(), newMacro
						.getType(), params, constraint);
				if (eOperation != null) {
					eOperation2ModuleElement.put(eOperation, newMacro);
					EAnnotation eAnnotation = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
							.createEAnnotation();
					eAnnotation.setSource(ANNOTATION_SOURCE);
					eAnnotation.getReferences().add(newMacro);
					eOperation.getEAnnotations().add(eAnnotation);
				}
			}
		}
	}

	/**
	 * Resolves the type of the macro using the current OCL environment, and the "Acceleo.type" EAnnotation.
	 * 
	 * @param newMacro
	 *            We have to resolve the type of this macro.
	 */
	private void resolveType(Macro newMacro) {
		EAnnotation eAnnotation = newMacro.getEAnnotation(ANNOTATION_SOURCE);
		if (eAnnotation != null) {
			String type = eAnnotation.getDetails().get(ANNOTATION_KEY_TYPE);
			if (type != null) {
				EClassifier eType = lookupClassifier(type);
				if (eType != null && eType != getInvalidType()) {
					newMacro.getEAnnotations().remove(eAnnotation);
				}
				newMacro.setType(eType);
			}
		}
	}

	/**
	 * Remove the behavioral features in the current parsing scope. It browses the given module, its children,
	 * and the imported modules...
	 * 
	 * @param oModule
	 *            is the current module
	 */
	public void removeRecursivelyBehavioralFeaturesToScope(org.eclipse.acceleo.model.mtl.Module oModule) {
		/* Do nothing */
	}

	/**
	 * Add all {@link EPackage} of a module in the list of the metamodels considered during the parsing.
	 * 
	 * @param oModule
	 *            is the current module
	 */
	public void addRecursivelyMetamodelsToScope(org.eclipse.acceleo.model.mtl.Module oModule) {
		Iterator<org.eclipse.acceleo.model.mtl.TypedModel> inputIt = oModule.getInput().iterator();
		while (inputIt.hasNext()) {
			org.eclipse.acceleo.model.mtl.TypedModel typedModel = inputIt.next();
			Iterator<EPackage> takesTypesFromIt = typedModel.getTakesTypesFrom().iterator();
			while (takesTypesFromIt.hasNext()) {
				addMetamodel(takesTypesFromIt.next());
			}
		}
	}

	/**
	 * Add a new {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to add in the current {@link EPackage}'s known by the parser.
	 */
	public void addMetamodel(EPackage metamodel) {
		environment.addMetamodel(metamodel);
	}

	/**
	 * Remove a {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to remove in the current {@link EPackage}'s known by the parser.
	 */
	public void removeMetamodel(EPackage metamodel) {
		environment.removeMetamodel(metamodel);
	}

	/**
	 * Selects the meta-model object for the given name in the current module.
	 * 
	 * @param name
	 *            is the name of the type to search
	 * @return the meta-model object, or null if it doesn't exist
	 */
	public EClassifier lookupClassifier(String name) {
		return environment.lookupClassifier(name);
	}

	/**
	 * Returns the underlying OCL environment.
	 * 
	 * @return The underlying OCL environment.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public Environment<?, EClassifier, EOperation, EStructuralFeature, ?, ?, ?, ?, ?, ?, ?, ?> getOCLEnvironment() {
		return helper.getEnvironment();
	}

	/**
	 * Gets the meta-model types of the current module.
	 * 
	 * @return the meta-model objects, or an empty list
	 */
	public List<EClassifier> getTypes() {
		return environment.getTypes();
	}

	/**
	 * Obtains the single instance of the OCL invalid type.
	 * 
	 * @return the OCL invalid type
	 */
	public EClassifier getInvalidType() {
		return environment.getOCLStandardLibraryReflection().getOCLInvalid();
	}

	/**
	 * Returns the standard library's integer classifier.
	 * 
	 * @return The standard library's integer classifier.
	 */
	public EClassifier getIntegerType() {
		return environment.getOCLStandardLibrary().getInteger();
	}

	/**
	 * Returns the standard library's string classifier.
	 * 
	 * @return The standard library's string classifier.
	 */
	public EClassifier getStringType() {
		return environment.getOCLStandardLibrary().getString();
	}

	/**
	 * Parse an OCL Expression and return the corresponding instance. Make sure to declare all the needed
	 * contextual elements (variables or contexts) before trying to parse.
	 * 
	 * @param text
	 *            is the OCL text to parse, just a part of the file
	 * @param realOffsetInTheBuffer
	 *            beginning index of the given text in the file, this index is used to compute the real offset
	 *            of a problem...
	 * @param iModelExpression
	 *            The model expression (only used to solve a recursivity problem
	 * @return an {@link OCLExpression} instance.
	 * @throws ParserException
	 *             if the given text is not conform to what we expect.
	 */
	public OCLExpression parseOCLExpression(String text, int realOffsetInTheBuffer,
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) throws ParserException {
		if (text.trim().equals(IAcceleoConstants.SUPER)) {
			TemplateInvocation eTemplateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
			eTemplateInvocation.setDefinition(null);
			eTemplateInvocation.setSuper(true);
			eTemplateInvocation.setStartPosition(realOffsetInTheBuffer);
			eTemplateInvocation.setEndPosition(realOffsetInTheBuffer + text.length());
			return eTemplateInvocation;
		}

		OCLExpression eOCLExpression = createQuery(text);
		shiftOCLExpressionPositions(realOffsetInTheBuffer, eOCLExpression);
		Iterator<EObject> eAllContents = eOCLExpression.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eContent = eAllContents.next();
			shiftOCLExpressionPositions(realOffsetInTheBuffer, eContent);
		}
		OCLExpression newOCLExpression = createAcceleoInvocation(eOCLExpression, iModelExpression);
		if (newOCLExpression == null) {
			Iterator<EObject> eAllContentsIt = eOCLExpression.eAllContents();
			while (eAllContentsIt.hasNext()) {
				createAcceleoInvocation(eAllContentsIt.next(), iModelExpression);
			}
			newOCLExpression = eOCLExpression;
		}
		return newOCLExpression;
	}

	/**
	 * Parse an OCL Expression and return the corresponding instance. We try 2 contexts : 'self.type' and
	 * 'EObject'.
	 * 
	 * @param text
	 *            is the OCL text to parse, just a part of the file
	 * @return an {@link OCLExpression} instance.
	 * @throws ParserException
	 *             if the given text is not conform to what we expect.
	 */
	private OCLExpression createQuery(String text) throws ParserException {
		environment.deleteFirstProblemObject();
		try {
			OCLExpression eOCLExpression = helper.createQuery(text);
			return eOCLExpression;
		} catch (ParserException e1) {
			Object firstProblemObject = environment.getFirstProblemObject();
			pushContext(org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getEObject());
			try {
				OCLExpression eOCLExpression = helper.createQuery(text);
				return eOCLExpression;
			} catch (ParserException e2) {
				if (firstProblemObject != null) {
					throw new WrappedOCLException(e1, firstProblemObject);
				}
				throw e1;
			} finally {
				popContext();
			}
		}
	}

	/**
	 * Replaces the current 'OCL' object with an invocation of one Acceleo module element.
	 * 
	 * @param eObject
	 *            is the detected OCL element
	 * @param iModelExpression
	 *            The CST model expression (used only for solving a recusive problem)
	 * @return the new OCL expression, which is an invocation of the Acceleo module element
	 */
	private OCLExpression createAcceleoInvocation(EObject eObject,
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		// Check the source of this object and replace it if needed
		if (eObject instanceof CallExp) {
			createAcceleoInvocation(((CallExp)eObject).getSource(), iModelExpression);
		}
		if (eObject instanceof OperationCallExp) {
			OperationCallExp eCall = (OperationCallExp)eObject;
			if (eCall.getReferredOperation() != null
					&& eCall.getReferredOperation().getEAnnotation(ANNOTATION_SOURCE) != null) {
				Iterator<EObject> referencesIt = eCall.getReferredOperation().getEAnnotation(
						ANNOTATION_SOURCE).getReferences().iterator();
				if (referencesIt.hasNext()) {
					EObject eModuleElement = referencesIt.next();
					OCLExpression acceleoInvocation = createAcceleoInvocation(eCall, eModuleElement,
							iModelExpression);
					return handleArguments(acceleoInvocation, iModelExpression);
				}
			} else if (eCall.getReferredOperation() != null) {
				// try to see if the arguments of the non standard operation contains an Acceleo invocation
				// example: 'a string' + ('another string' + aQueryInvocation())
				// also applicable for OCL standard operations
				// example 'a string'.concat('another string'.concat(aQueryInvocation()))
				List<org.eclipse.ocl.expressions.OCLExpression<EClassifier>> argument = eCall.getArgument();
				for (org.eclipse.ocl.expressions.OCLExpression<EClassifier> oclExpression : argument) {
					createAcceleoInvocation(oclExpression, iModelExpression);
				}
			}
		}
		return null;
	}

	/**
	 * Transform the arguments into Acceleo invocations.
	 * 
	 * @param acceleoInvocation
	 *            The acceleoInvocation
	 * @param iModelExpression
	 *            The CST model expression (used only for solving a recusive problem)
	 * @return The new Acceleo invocation with its new arguments
	 */
	private OCLExpression handleArguments(OCLExpression acceleoInvocation,
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		OCLExpression result = acceleoInvocation;
		if (acceleoInvocation instanceof TemplateInvocation) {
			TemplateInvocation templateInvocation = (TemplateInvocation)acceleoInvocation;
			List<OCLExpression> arguments = templateInvocation.getArgument();
			for (OCLExpression argument : arguments) {
				createAcceleoInvocationArguments(argument, iModelExpression);
			}
		} else if (acceleoInvocation instanceof QueryInvocation) {
			QueryInvocation queryInvocation = (QueryInvocation)acceleoInvocation;
			List<OCLExpression> arguments = queryInvocation.getArgument();
			for (OCLExpression argument : arguments) {
				createAcceleoInvocationArguments(argument, iModelExpression);
			}
		} else if (acceleoInvocation instanceof MacroInvocation) {
			MacroInvocation macroInvocation = (MacroInvocation)acceleoInvocation;
			List<OCLExpression> arguments = macroInvocation.getArgument();
			for (OCLExpression argument : arguments) {
				createAcceleoInvocationArguments(argument, iModelExpression);
			}
		}
		return result;
	}

	/**
	 * Transform the arguments into Acceleo invocations.
	 * 
	 * @param argument
	 *            The arguments of the Acceleo invocation
	 * @param iModelExpression
	 *            The CST model expression (used only for solving a recusive problem)
	 */
	private void createAcceleoInvocationArguments(org.eclipse.ocl.expressions.OCLExpression<?> argument,
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		if (argument instanceof org.eclipse.ocl.expressions.CollectionLiteralExp<?>) {
			org.eclipse.ocl.expressions.CollectionLiteralExp<?> collectionLiteralExp = (org.eclipse.ocl.expressions.CollectionLiteralExp<?>)argument;
			List<?> parts = collectionLiteralExp.getPart();
			for (Object collectionLiteralPart : parts) {
				if (collectionLiteralPart instanceof org.eclipse.ocl.expressions.CollectionItem) {
					org.eclipse.ocl.expressions.CollectionItem<?> collectionItem = (org.eclipse.ocl.expressions.CollectionItem<?>)collectionLiteralPart;
					org.eclipse.ocl.expressions.OCLExpression<?> item = collectionItem.getItem();
					if (item instanceof org.eclipse.ocl.expressions.CollectionLiteralExp<?>) {
						createAcceleoInvocationArguments(item, iModelExpression);
					} else {
						createAcceleoInvocation(item, iModelExpression);
					}
				}
			}
		}
	}

	/**
	 * Replaces the given OperationCallExp object with an invocation of the Acceleo module element.
	 * 
	 * @param eCall
	 *            is the detected OCL operation call
	 * @param eModuleElement
	 *            is the referred module element
	 * @param iModelExpression
	 *            The model expression of the CST (only used to solve a recursivity problem)
	 * @return the new OCL expression, which is an invocation of the Acceleo module element
	 */
	private OCLExpression createAcceleoInvocation(OperationCallExp eCall, EObject eModuleElement,
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		OCLExpression eOCLExpression;
		if (eModuleElement instanceof Template) {
			// We have a template invocation
			Template eTemplate = (Template)eModuleElement;
			TemplateInvocation eTemplateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
			eOCLExpression = eTemplateInvocation;
			eTemplateInvocation.setDefinition(eTemplate);
			eTemplateInvocation.setStartPosition(eCall.getStartPosition());
			eTemplateInvocation.setEndPosition(eCall.getEndPosition());
			if (receiverIsArgument(eCall, eTemplate, eTemplate.getParameter(), iModelExpression)) {
				eTemplateInvocation.getArgument().add((OCLExpression)eCall.getSource());
			}
			eTemplateInvocation.setType(eCall.getType());
			move(eCall.getArgument(), eTemplateInvocation.getArgument());
			checkArgumentInvocations(eTemplateInvocation.getArgument(), iModelExpression);
			EcoreUtil.replace(eCall, eTemplateInvocation);
		} else if (eModuleElement instanceof Query) {
			Query eQuery = (Query)eModuleElement;
			QueryInvocation eQueryInvocation = MtlFactory.eINSTANCE.createQueryInvocation();
			eOCLExpression = eQueryInvocation;
			eQueryInvocation.setDefinition(eQuery);
			eQueryInvocation.setStartPosition(eCall.getStartPosition());
			eQueryInvocation.setEndPosition(eCall.getEndPosition());
			if (receiverIsArgument(eCall, eQuery, eQuery.getParameter(), iModelExpression)) {
				eQueryInvocation.getArgument().add((OCLExpression)eCall.getSource());
			}
			eQueryInvocation.setType(eCall.getType());
			move(eCall.getArgument(), eQueryInvocation.getArgument());
			checkArgumentInvocations(eQueryInvocation.getArgument(), iModelExpression);
			EcoreUtil.replace(eCall, eQueryInvocation);
		} else if (eModuleElement instanceof Macro) {
			Macro eMacro = (Macro)eModuleElement;
			MacroInvocation eMacroInvocation = MtlFactory.eINSTANCE.createMacroInvocation();
			eOCLExpression = eMacroInvocation;
			eMacroInvocation.setDefinition(eMacro);
			eMacroInvocation.setStartPosition(eCall.getStartPosition());
			eMacroInvocation.setEndPosition(eCall.getEndPosition());
			if (receiverIsArgument(eCall, eMacro, eMacro.getParameter(), iModelExpression)) {
				eMacroInvocation.getArgument().add((OCLExpression)eCall.getSource());
			}
			eMacroInvocation.setType(eCall.getType());
			move(eCall.getArgument(), eMacroInvocation.getArgument());
			checkArgumentInvocations(eMacroInvocation.getArgument(), iModelExpression);
			EcoreUtil.replace(eCall, eMacroInvocation);
		} else {
			eOCLExpression = null;
		}
		return eOCLExpression;
	}

	/**
	 * Indicates if the receiver of the given operation call should be added to the arguments.
	 * 
	 * @param eCall
	 *            is an operation call
	 * @param astModuleElement
	 *            The module element from the AST
	 * @param variables
	 *            The list of variables of the template, query or macro called.
	 * @param iModelExpression
	 *            The CST model expression (used only for solving a recusive problem)
	 * @return true if the receiver of the given operation call should be added to the arguments
	 */
	private boolean receiverIsArgument(OperationCallExp eCall, ModuleElement astModuleElement,
			List<Variable> variables, org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		boolean result = false;
		if (eCall.getSource() != null) {
			if (eCall.getSource() instanceof VariableExp
					&& ((VariableExp)eCall.getSource()).getReferredVariable() != null) {

				// No arguments for the call: the source is the argument
				if (eCall.getArgument().size() == 0) {
					result = true;
				} else if (variables.size() == 0 && iModelExpression != null) {
					// We are in a recursive call of a template or a query
					result = handleRecursiveCall(eCall, astModuleElement, variables, iModelExpression);
				} else {
					// We are in a call where the source may be the argument
					result = eCall.getArgument().size() < variables.size();
					result = result && variables.size() != 0;
				}
			} else {
				result = true;
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Handle the recursive call.
	 * 
	 * @param eCall
	 *            is an operation call
	 * @param astModuleElement
	 *            The module element from the AST
	 * @param variables
	 *            The list of variables of the template, query or macro called.
	 * @param iModelExpression
	 *            The CST model expression (used only for solving a recusive problem)
	 * @return true if the receiver of the given operation call should be added to the arguments
	 */
	private boolean handleRecursiveCall(OperationCallExp eCall, ModuleElement astModuleElement,
			List<Variable> variables, org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		boolean result = false;
		EObject eContainer = iModelExpression.eContainer();
		while (eContainer != null && !(eContainer instanceof org.eclipse.acceleo.parser.cst.ModuleElement)) {
			eContainer = eContainer.eContainer();
		}

		if (eContainer instanceof org.eclipse.acceleo.parser.cst.Template) {
			// We have the module of the CST, we need to look for the correct template or query
			org.eclipse.acceleo.parser.cst.Template cstTemplate = (org.eclipse.acceleo.parser.cst.Template)eContainer;
			if (cstTemplate.getStartPosition() == astModuleElement.getStartPosition()
					&& cstTemplate.getEndPosition() == astModuleElement.getEndPosition()) {
				result = eCall.getArgument().size() < cstTemplate.getParameter().size();
				result = result && cstTemplate.getParameter().size() != 0;
			}
		} else if (eContainer instanceof org.eclipse.acceleo.parser.cst.Query) {
			// We have the module of the CST, we need to look for the correct template or query
			org.eclipse.acceleo.parser.cst.Query cstQuery = (org.eclipse.acceleo.parser.cst.Query)eContainer;
			if (cstQuery.getStartPosition() == astModuleElement.getStartPosition()
					&& cstQuery.getEndPosition() == astModuleElement.getEndPosition()) {
				result = eCall.getArgument().size() < cstQuery.getParameter().size();
				result = result && cstQuery.getParameter().size() != 0;
			}
		} else if (eContainer instanceof org.eclipse.acceleo.parser.cst.Macro) {
			// We have the module of the CST, we need to look for the correct template or query
			org.eclipse.acceleo.parser.cst.Macro cstMacro = (org.eclipse.acceleo.parser.cst.Macro)eContainer;
			if (cstMacro.getStartPosition() == astModuleElement.getStartPosition()
					&& cstMacro.getEndPosition() == astModuleElement.getEndPosition()) {
				result = eCall.getArgument().size() < cstMacro.getParameter().size();
				result = result && cstMacro.getParameter().size() != 0;
			}
		}
		return result;
	}

	/**
	 * Moves the elements of the first list in the second list.
	 * 
	 * @param in
	 *            is the first list
	 * @param out
	 *            is the second list
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void move(List in, List out) {
		out.addAll(in);
	}

	/**
	 * Creates the invocations of the arguments list.
	 * 
	 * @param arguments
	 *            is the arguments list
	 * @param iModelExpression
	 *            The CST model expression (used only for solving a recusive problem)
	 */
	private void checkArgumentInvocations(List<OCLExpression> arguments,
			org.eclipse.acceleo.parser.cst.ModelExpression iModelExpression) {
		ListIterator<OCLExpression> it = arguments.listIterator();
		while (it.hasNext()) {
			OCLExpression next = it.next();
			OCLExpression newArgument = createAcceleoInvocation(next, iModelExpression);
			if (newArgument != null) {
				it.set(newArgument);
			}
		}
	}

	/**
	 * Shifts the position of an OCL expression and its children with the given value that corresponds to the
	 * beginning index of the expression in the full buffer.
	 * 
	 * @param posBegin
	 *            is the beginning index of the expression in the full buffer
	 * @param eContent
	 *            is the expression to be updated
	 */
	private void shiftOCLExpressionPositions(int posBegin, EObject eContent) {
		if (eContent instanceof ASTNode && ((ASTNode)eContent).getStartPosition() >= 0) {
			((ASTNode)eContent).setStartPosition(posBegin + ((ASTNode)eContent).getStartPosition());
			((ASTNode)eContent).setEndPosition(posBegin + ((ASTNode)eContent).getEndPosition());
		}
		if (eContent instanceof CallingASTNode && ((CallingASTNode)eContent).getPropertyStartPosition() >= 0) {
			((CallingASTNode)eContent).setPropertyStartPosition(posBegin
					+ ((CallingASTNode)eContent).getPropertyStartPosition());
			((CallingASTNode)eContent).setPropertyEndPosition(posBegin
					+ ((CallingASTNode)eContent).getPropertyEndPosition());
		}
		if (eContent instanceof TypedASTNode && ((TypedASTNode)eContent).getTypeStartPosition() >= 0) {
			((TypedASTNode)eContent).setTypeStartPosition(posBegin
					+ ((TypedASTNode)eContent).getTypeStartPosition());
			((TypedASTNode)eContent).setTypeEndPosition(posBegin
					+ ((TypedASTNode)eContent).getTypeEndPosition());
		}
	}

	/**
	 * Clear all contextual information.
	 */
	public void dispose() {
		saveVariableContainer.clear();
		saveVariableContainingFeature.clear();
		eOperation2ModuleElement.clear();
	}

	/**
	 * Remove an accessible variable from the current parser scope.
	 * 
	 * @param oldVariable
	 *            oldVariable is the variable to remove.
	 */
	@SuppressWarnings("unchecked")
	public void removeVariableFromScope(Variable oldVariable) {
		if (oldVariable == null) {
			return;
		}
		EObject eContainer = saveVariableContainer.get(oldVariable);
		EStructuralFeature eContainingFeature = saveVariableContainingFeature.get(oldVariable);
		if (oldVariable.getName() != null && eContainer != null && eContainingFeature != null) {
			helper.getOCL().getEnvironment().deleteElement(oldVariable.getName());
			if (eContainingFeature.getUpperBound() == 1) {
				eContainer.eSet(eContainingFeature, oldVariable);
			} else {
				Object eGet = eContainer.eGet(eContainingFeature);
				if (eGet instanceof Collection<?>) {
					Collection<Variable> list = (Collection<Variable>)eGet;
					list.add(oldVariable);
				}
			}
		}
		saveVariableContainer.remove(oldVariable);
		saveVariableContainingFeature.remove(oldVariable);
	}

	/**
	 * Obtains syntax completion choices for the specified fragment of an OCL expression.
	 * 
	 * @param text
	 *            a partial OCL expression for which to seek choices that could be appended to it
	 * @return a list of {@link Choice}s, possibly empty. The ordering of the list may or may not indicate
	 *         relative relevance or frequency of a choice
	 */
	public List<Choice> getSyntaxHelp(String text) {
		List<Choice> result = new ArrayList<Choice>();
		try {
			result.addAll(helper.getSyntaxHelp(ConstraintKind.INVARIANT, text));
		} catch (NullPointerException e) {
			// do not log, it can happen during the parsing.
		}

		pushContext(org.eclipse.emf.ecore.EcorePackage.eINSTANCE.getEObject());

		try {
			result.addAll(helper.getSyntaxHelp(ConstraintKind.INVARIANT, text));
		} catch (NullPointerException e) {
			// do not log, it can happen during the parsing.
		} finally {
			popContext();
		}
		return result;
	}

	/**
	 * Gets the module element used to create the given operation, or null if it doen't exist.
	 * 
	 * @param eOperation
	 *            is an EOperation created by the OCL parser
	 * @return the corresponding Acceleo module element
	 */
	public ModuleElement getModuleElement(EOperation eOperation) {
		ModuleElement result = eOperation2ModuleElement.get(eOperation);
		if (result == null && eOperation != null) {
			Iterator<Map.Entry<EOperation, ModuleElement>> entries = eOperation2ModuleElement.entrySet()
					.iterator();
			while (entries.hasNext()) {
				Map.Entry<EOperation, ModuleElement> entry = entries.next();
				EOperation operation = entry.getKey();
				if (equals(eOperation, operation)) {
					return entry.getValue();
				}
			}
		}
		return result;
	}

	/**
	 * Compares these EOperations. The result is true if the operations have the same name and the same
	 * parameters.
	 * 
	 * @param o1
	 *            is the first one
	 * @param o2
	 *            is the second one
	 * @return true if the operations have the same name and the same parameters
	 */
	private boolean equals(EOperation o1, EOperation o2) {
		boolean result;
		if (o1 == o2) {
			result = true;
		} else if (o1 == null || o2 == null) {
			result = false;
		} else {
			if (o1.getName() != null && o1.getName().equals(o2.getName())
					&& o1.getEParameters().size() == o2.getEParameters().size()) {
				Iterator<EParameter> params1 = o1.getEParameters().iterator();
				Iterator<EParameter> params2 = o2.getEParameters().iterator();
				while (params1.hasNext() && params2.hasNext()) {
					EParameter param1 = params1.next();
					EParameter param2 = params2.next();
					if (param1.getEType() != null && param2.getEType() != null
							&& param1.getEType().getName() != null
							&& !param1.getEType().getName().equals(param2.getEType().getName())) {
						return false;
					}
				}
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}
}
