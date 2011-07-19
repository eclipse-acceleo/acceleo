/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Jerome Benois - eInverse initial implementation
 *     Goulwen Le Fur - caching of the eInverse cross referencer
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.environment;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.common.utils.Deque;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.internal.utils.AcceleoOverrideAdapter;
import org.eclipse.acceleo.engine.service.AcceleoDynamicTemplatesRegistry;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.ecore.AnyType;
import org.eclipse.ocl.ecore.EcoreEvaluationEnvironment;
import org.eclipse.ocl.options.EvaluationOptions;
import org.eclipse.ocl.utilities.PredefinedType;

/**
 * This will allow us to accurately evaluate custom operations defined in the Acceleo standard library and
 * resolve the right template for each call (guards, overrides, namesakes, ...).
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationEnvironment extends EcoreEvaluationEnvironment {
	/** This will be used as a place holder when trying to call templates with a <code>null</code> argument. */
	private static final Object NULL_ARGUMENT = new Object();

	/** Holds the prefix we'll use for the temporary context variables created to hold context values. */
	private static final String TEMPORARY_CONTEXT_VAR_PREFIX = "context$"; //$NON-NLS-1$

	/** Holds the prefix we'll use for the temporary variables created to hold argument values. */
	private static final String TEMPORARY_INVOCATION_ARG_PREFIX = "temporaryInvocationVariable$"; //$NON-NLS-1$

	/** This will allow the environment to know of the modules currently in the generation context. */
	private final Set<Module> currentModules = new CompactHashSet<Module>();

	/** Maps dynamic overrides as registered in the {@link AcceleoDynamicTemplatesRegistry}. */
	private final Map<Template, Set<Template>> dynamicOverrides = new HashMap<Template, Set<Template>>();

	/** Maps all overriding templates to their <code>super</code>. */
	private final Map<Template, Set<Template>> overridingTemplates = new HashMap<Template, Set<Template>>();

	/** This will hold a reference to the class allowing for properties lookup. */
	private AcceleoPropertiesLookup propertiesLookup;

	/** This will allow us to map all accessible templates to their name. */
	private final Map<String, Set<Template>> templates = new HashMap<String, Set<Template>>();

	/**
	 * Allows us to totally get rid of the inherited map. This will mainly serve the purpose of allowing
	 * multiple bindings against the same variable name.
	 */
	private final Deque<Map<String, Deque<Object>>> scopedVariableMap = new CircularArrayDeque<Map<String, Deque<Object>>>();

	/**
	 * This will contain variables that are global to a generation module.
	 */
	private final Map<String, Deque<Object>> globalVariableMap = new HashMap<String, Deque<Object>>();

	/**
	 * This constructor is needed by the factory.
	 * 
	 * @param parent
	 *            Parent evaluation environment.
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 * @param properties
	 *            The class allowing for properties lookup for this generation.
	 */
	public AcceleoEvaluationEnvironment(
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> parent,
			Module module, AcceleoPropertiesLookup properties) {
		super(parent);
		scopedVariableMap.add(new HashMap<String, Deque<Object>>());
		mapAllTemplates(module);
		mapDynamicOverrides();
		setOption(EvaluationOptions.LAX_NULL_HANDLING, Boolean.FALSE);
		propertiesLookup = properties;
	}

	/**
	 * This constructor will create our environment given the module from which to resolve dependencies.
	 * 
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 * @param properties
	 *            The class allowing for properties lookup for this generation.
	 */
	public AcceleoEvaluationEnvironment(Module module, AcceleoPropertiesLookup properties) {
		super();
		scopedVariableMap.add(new HashMap<String, Deque<Object>>());
		mapAllTemplates(module);
		mapDynamicOverrides();
		setOption(EvaluationOptions.LAX_NULL_HANDLING, Boolean.FALSE);
		propertiesLookup = properties;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#add(java.lang.String, java.lang.Object)
	 */
	@Override
	public void add(String name, Object value) {
		Map<String, Deque<Object>> variableMap;
		if (name.startsWith(TEMPORARY_CONTEXT_VAR_PREFIX) || name.startsWith(TEMPORARY_INVOCATION_ARG_PREFIX)) {
			variableMap = globalVariableMap;
		} else {
			variableMap = scopedVariableMap.getLast();
		}
		Deque<Object> values = variableMap.get(name);
		if (values == null) {
			values = new CircularArrayDeque<Object>();
			variableMap.put(name, values);
		}
		values.add(value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEvaluationEnvironment#callOperation(org.eclipse.emf.ecore.EOperation,
	 *      int, java.lang.Object, java.lang.Object[])
	 */
	@Override
	public Object callOperation(EOperation operation, int opcode, Object source, Object[] args) {
		Object result = null;
		if (operation.getEAnnotation("MTL") != null) { //$NON-NLS-1$
			result = AcceleoLibraryOperationVisitor.callStandardOperation(this, operation, source, args);
		} else if (operation.getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
			result = AcceleoLibraryOperationVisitor.callNonStandardOperation(this, operation, source, args);
		} else {
			/*
			 * Shortcut OCL for operations returning "EJavaObject" : these could be collections, but OCL would
			 * discard any value other than the first in such cases. See bug 287052.
			 */
			if (operation.getEType() == EcorePackage.eINSTANCE.getEJavaObject()) {
				result = callOperationWorkaround287052(operation, opcode, source, args);
			} else {
				result = super.callOperation(operation, opcode, source, args);
			}
		}
		return result;
	}

	/**
	 * Copied from
	 * {@link org.eclipse.ocl.AbstractEvaluationEnvironment#callOperation(Object, int, Object, Object[])} to
	 * bypass the {@link EcoreEvaluationEnvironment} "coerce" behavior as it is the cause of bug 287052.This
	 * will only be called for EOperations which return type is EJavaObject.
	 * 
	 * @param operation
	 *            Operation we are to call.
	 * @param opcode
	 *            OCL code for this operation.
	 * @param source
	 *            Source of the call.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the invocation.
	 * @throws IllegalArgumentException
	 *             Thrown if we couldn't find an appropriate method to call.
	 */
	private Object callOperationWorkaround287052(EOperation operation, int opcode, Object source,
			Object[] args) throws IllegalArgumentException {
		if (getParent() != null) {
			return getParent().callOperation(operation, opcode, source, args);
		}

		Method method = getJavaMethodFor(operation, source);

		Object noResult = new Object();
		Object result = noResult;
		if (method != null) {
			try {
				// coerce any collection arguments to EList as necessary
				Class<?>[] parmTypes = method.getParameterTypes();
				for (int i = 0; i < parmTypes.length; i++) {
					if (EList.class.isAssignableFrom(parmTypes[i])) {
						if (args[i] == null) {
							args[i] = ECollections.EMPTY_ELIST;
						} else if (!(args[i] instanceof Collection<?>)) {
							EList<Object> list = new BasicEList.FastCompare<Object>(1);
							list.add(args[i]);
							args[i] = list;
						} else if (!(args[i] instanceof EList<?>)) {
							args[i] = new BasicEList.FastCompare<Object>((Collection<?>)args[i]);
						}
					}
				}

				result = method.invoke(source, args);
				// CHECKSTYLE:OFF
				// This is mostly copied from AbstractEvaluationEnvironment, which catches Exception.
			} catch (Exception e) {
				// CHECKSTYLE:ON
				AcceleoEnginePlugin.log(e, false);
				result = getInvalidResult();
			}
		}

		if (result == noResult) {
			throw new IllegalArgumentException();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		scopedVariableMap.clear();
		globalVariableMap.clear();
	}

	/**
	 * Creates a new variable scope. This will typically be called when we enter a new TemplateInvocation or
	 * QueryInvocation.
	 */
	public void createVariableScope() {
		scopedVariableMap.add(new HashMap<String, Deque<Object>>());
	}

	/**
	 * This will return the List of all applicable candidates for the given template call with the given
	 * arguments. These will be ordered as described on {@link #reorderCandidatesPriority(Module, Set)}.
	 * 
	 * @param origin
	 *            Origin of the template call.
	 * @param call
	 *            The called element.
	 * @param arguments
	 *            Arguments of the call.
	 * @return The set of all applicable templates for these arguments
	 */
	public List<Template> getAllCandidates(Module origin, Template call, List<Object> arguments) {
		final List<Object> argumentTypes = new ArrayList<Object>(arguments.size());
		for (final Object arg : arguments) {
			if (arg instanceof EObject) {
				argumentTypes.add(((EObject)arg).eClass());
			} else if (arg != null) {
				argumentTypes.add(arg.getClass());
			} else {
				argumentTypes.add(NULL_ARGUMENT);
			}
		}

		/*
		 * NOTE : we depend on the ordering offered by List types. Do not change implementation without
		 * testing.
		 */
		final List<Template> orderedNamesakes = reorderCandidatesPriority(origin, getAllCandidateNamesakes(
				origin, call, argumentTypes));
		final List<Template> dynamicOverriding = reorderDynamicOverrides(getAllDynamicCandidateOverriding(
				orderedNamesakes, argumentTypes));
		final List<Template> overriding = getAllCandidateOverriding(origin, orderedNamesakes, argumentTypes);

		final List<Template> applicableCandidates = new ArrayList<Template>();
		// overriding templates come first, then namesakes
		applicableCandidates.addAll(dynamicOverriding);
		applicableCandidates.addAll(overriding);
		applicableCandidates.addAll(orderedNamesakes);
		return applicableCandidates;
	}

	/**
	 * Returns the most specific template for the given arguments in the given list.
	 * 
	 * @param candidates
	 *            List of templates candidates to be substituted.
	 * @param arguments
	 *            Arguments of the call.
	 * @return The most specific templates for <code>arguments</code>.
	 */
	public Template getMostSpecificTemplate(List<Template> candidates, List<Object> arguments) {
		final Iterator<Template> candidateIterator = candidates.iterator();
		if (candidates.size() == 1) {
			return candidateIterator.next();
		}

		final List<Object> argumentTypes = new ArrayList<Object>(arguments.size());
		for (final Object arg : arguments) {
			if (arg instanceof EObject) {
				argumentTypes.add(((EObject)arg).eClass());
			} else if (arg != null) {
				argumentTypes.add(arg.getClass());
			}
		}

		Template mostSpecific = candidateIterator.next();
		while (candidateIterator.hasNext()) {
			mostSpecific = mostSpecificTemplate(mostSpecific, candidateIterator.next(), arguments);
		}
		return mostSpecific;
	}

	/**
	 * Returns the list of available properties files.
	 * 
	 * @return The list of available properties files.
	 */
	public AcceleoPropertiesLookup getPropertiesLookup() {
		return propertiesLookup;
	}

	/**
	 * This will return the map of currently available variables. Take note that this is not meant to be used
	 * to alter the variables (which won't work since what we return is a copy of the variable map) but more
	 * to query the variable state.
	 * 
	 * @return The map of currently available variables.
	 */
	public Map<String, Object> getCurrentVariables() {
		Map<String, Deque<Object>> variableMap = scopedVariableMap.getLast();
		Map<String, Object> availableVariables = new HashMap<String, Object>();
		for (Map.Entry<String, Deque<Object>> var : variableMap.entrySet()) {
			if (!var.getValue().isEmpty()) {
				availableVariables.put(var.getKey(), var.getValue().getLast());
			}
		}
		return availableVariables;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEvaluationEnvironment#getInvalidResult()
	 */
	@Override
	public Object getInvalidResult() {
		return super.getInvalidResult();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#getValueOf(java.lang.String)
	 */
	@Override
	public Object getValueOf(String name) {
		Object value = null;
		Map<String, Deque<Object>> variableMap = scopedVariableMap.getLast();
		if (variableMap.containsKey(name)) {
			value = variableMap.get(name).getLast();
		} else if (globalVariableMap.containsKey(name)) {
			value = globalVariableMap.get(name).getLast();
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#overrides(org.eclipse.emf.ecore.EOperation, int)
	 */
	@Override
	public boolean overrides(EOperation operation, int opcode) {
		boolean result = false;
		if (operation.getEAnnotation("MTL") != null) { //$NON-NLS-1$
			result = true;
		} else if (operation.getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
			if (opcode == PredefinedType.PLUS) {
				Adapter adapter = EcoreUtil.getAdapter(operation.eAdapters(), AcceleoOverrideAdapter.class);
				result = adapter != null;
				operation.eAdapters().remove(adapter);
			} else {
				result = true;
			}
		} else {
			result = super.overrides(operation, opcode);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#remove(java.lang.String)
	 */
	@Override
	public Object remove(String name) {
		Map<String, Deque<Object>> variableMap;
		if (scopedVariableMap.getLast().containsKey(name)) {
			variableMap = scopedVariableMap.getLast();
		} else if (globalVariableMap.containsKey(name)) {
			variableMap = globalVariableMap;
		} else {
			return null;
		}

		final Object removedValue = variableMap.get(name).removeLast();
		if (variableMap.get(name).size() == 0) {
			variableMap.remove(name);
		}
		return removedValue;
	}

	/**
	 * This will remove and return the last variable scope.
	 * 
	 * @return Removes and return the last variable scope.
	 */
	public Map<String, Deque<Object>> removeVariableScope() {
		return scopedVariableMap.removeLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#replace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void replace(String name, Object value) {
		Map<String, Deque<Object>> variableMap;
		if (name.startsWith(TEMPORARY_CONTEXT_VAR_PREFIX) || name.startsWith(TEMPORARY_INVOCATION_ARG_PREFIX)) {
			variableMap = globalVariableMap;
		} else {
			variableMap = scopedVariableMap.getLast();
		}

		if (variableMap.containsKey(name)) {
			variableMap.get(name).removeLast();
		}
		add(name, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#toString()
	 */
	@Override
	public String toString() {
		Map<String, Deque<Object>> variableMap = scopedVariableMap.getLast();
		return variableMap.toString();
	}

	/**
	 * Filters non-applicable templates out of the candidates list.
	 * 
	 * @param candidates
	 *            List of templates that needs to be filtered out.
	 * @param argumentTypes
	 *            Types of the arguments for the call.
	 * @return The set of applicable templates.
	 */
	private Set<Template> applicableTemplates(Set<Template> candidates, List<Object> argumentTypes) {
		final Set<Template> applicableCandidates = new CompactLinkedHashSet<Template>(candidates);
		for (final Template candidate : candidates) {
			if (candidate.getParameter().size() != argumentTypes.size()) {
				applicableCandidates.remove(candidate);
			}
		}
		for (int i = 0; i < argumentTypes.size(); i++) {
			for (final Template candidate : new CompactLinkedHashSet<Template>(applicableCandidates)) {
				final Object parameterType = candidate.getParameter().get(i).getType();
				if (!isApplicableArgument(parameterType, argumentTypes.get(i))) {
					applicableCandidates.remove(candidate);
				}
			}
		}
		return applicableCandidates;
	}

	/**
	 * This will return the list of all namesakes of the template <code>call</code> applicable for
	 * <code>arguments</code>.
	 * 
	 * @param origin
	 *            Origin of the template call.
	 * @param call
	 *            The called element.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates of this name in the current context.
	 */
	private Set<Template> getAllCandidateNamesakes(Module origin, Template call, List<Object> argumentTypes) {
		final Set<Template> namesakes = new CompactLinkedHashSet<Template>();
		final Set<Template> candidates = templates.get(call.getName());
		if (candidates == null) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationEnvironment.ModuleResolutionError")); //$NON-NLS-1$
		}
		Set<Module> scope = new CompactHashSet<Module>();
		scope.add(origin);
		scope.addAll(getScopeOf(origin));
		for (Template candidate : candidates) {
			if (scope.contains(candidate.eContainer())) {
				namesakes.add(candidate);
			}
		}
		if (namesakes.size() == 1) {
			return namesakes;
		}
		namesakes.retainAll(applicableTemplates(candidates, argumentTypes));
		return namesakes;
	}

	/**
	 * Returns the whole scope of modules visible from <code>module</code>.
	 * 
	 * @param module
	 *            Module of which we need the scope.
	 * @return The whole scope of modules visible from <code>module</code>.
	 */
	private Set<Module> getScopeOf(Module module) {
		Set<Module> scope = new CompactHashSet<Module>();

		if (module.getExtends().size() > 0) {
			// Only supports single inheritance
			Module extended = module.getExtends().get(0);
			scope.add(extended);
			scope.addAll(getExtendedScope(extended));
		}

		List<Module> imports = module.getImports();
		scope.addAll(imports);
		for (Module importedModule : imports) {
			scope.addAll(getExtendedScope(importedModule));
		}

		return scope;
	}

	/**
	 * Returns the whole scope of modules visible thanks to an extends.
	 * 
	 * @param module
	 *            Module of which we need the scope.
	 * @return The whole scope of modules visible thanks to an extends.
	 */
	private Set<Module> getExtendedScope(Module module) {
		Set<Module> scope = new CompactHashSet<Module>();

		if (module.getExtends().size() > 0) {
			// Only supports single inheritance
			Module extended = module.getExtends().get(0);
			scope.add(extended);
			scope.addAll(getExtendedScope(extended));
		}

		return scope;
	}

	/**
	 * This will return the list of all templates overriding one of <code>overridenTemplates</code> that are
	 * applicable for <code>arguments</code>. These will be ordered as specified on
	 * {@link #reorderCandidatesPriority(Module, Set)}.
	 * 
	 * @param origin
	 *            Origin of the template call.
	 * @param overridenTemplates
	 *            List of templates we seek overriding templates of.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates overriding one of <code>overridenTemplates</code> in the
	 *         current context.
	 */
	private List<Template> getAllCandidateOverriding(Module origin, List<Template> overridenTemplates,
			List<Object> argumentTypes) {
		final List<Template> candidateOverriding = new ArrayList<Template>();
		for (final Template overriden : overridenTemplates) {
			final Set<Template> candidates = overridingTemplates.get(overriden);
			if (candidates != null) {
				final Set<Template> applicableCandidates = applicableTemplates(candidates, argumentTypes);
				for (Template template : applicableCandidates) {
					EObject eContainer = template.eContainer();
					if (eContainer instanceof Module
							&& (getScopeOf(origin).contains(eContainer) || eContainer.equals(origin))) {
						candidateOverriding.add(template);
					}
				}
				// no need to order this, it'll be ordered later on
				candidateOverriding.addAll(getAllCandidateOverriding(origin, new ArrayList<Template>(
						applicableCandidates), argumentTypes));
			}
		}
		Collections.reverse(candidateOverriding);
		return candidateOverriding;
	}

	/**
	 * This will return the list of all templates dynamically overriding one of
	 * <code>overridenTemplates</code> that are applicable for <code>arguments</code>.
	 * 
	 * @param overridenTemplates
	 *            List of templates we seek overriding templates of.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates dynamically overriding one of <code>overridenTemplates</code>.
	 */
	private Set<Template> getAllDynamicCandidateOverriding(List<Template> overridenTemplates,
			List<Object> argumentTypes) {
		final Set<Template> dynamicOverriding = new CompactLinkedHashSet<Template>();
		for (final Template overriden : overridenTemplates) {
			final Set<Template> candidates = dynamicOverrides.get(overriden);
			if (candidates != null) {
				final Set<Template> applicableCandidates = applicableTemplates(candidates, argumentTypes);
				dynamicOverriding.addAll(applicableCandidates);
			}
		}
		return dynamicOverriding;
	}

	/**
	 * This is meant to be used in the Dynamic URI converter only.
	 * 
	 * @return The set of all currently accessible modules.
	 */
	Set<Module> getCurrentModules() {
		return new CompactHashSet<Module>(currentModules);
	}

	/**
	 * Returns <code>true</code> if the value is applicable to the given type, <code>false</code> otherwise.
	 * 
	 * @param expectedType
	 *            Expected type of the argument.
	 * @param argumentType
	 *            type of the argument we're trying to use as an argument.
	 * @return <code>true</code> if the value is applicable to the given type, <code>false</code> otherwise.
	 */
	private boolean isApplicableArgument(Object expectedType, Object argumentType) {
		boolean isApplicable = false;
		if (argumentType == NULL_ARGUMENT) {
			isApplicable = true;
		} else if (expectedType instanceof EClass && argumentType instanceof EClass) {
			isApplicable = expectedType == argumentType
					|| isSubTypeOf((EClass)expectedType, (EClass)argumentType);
		} else if (expectedType instanceof Class<?> && argumentType instanceof Class<?>) {
			isApplicable = ((Class<?>)expectedType).isAssignableFrom((Class<?>)argumentType);
		} else if (expectedType instanceof EDataType && argumentType instanceof Class<?>) {
			isApplicable = ((EDataType)expectedType).getInstanceClass() == argumentType;
		} else if (expectedType instanceof AnyType) {
			isApplicable = true;
		} else {
			isApplicable = expectedType.getClass().isInstance(argumentType);
		}
		return isApplicable;
	}

	/**
	 * Returns <code>true</code> if <code>eClass</code> is a sub-type of <code>superType</code>,
	 * <code>false</code> otherwise.
	 * 
	 * @param superType
	 *            Expected super type of <code>eClass</code>.
	 * @param eClass
	 *            EClass to consider.
	 * @return <code>true</code> if <code>eClass</code> is a sub-type of <code>superType</code>,
	 *         <code>false</code> otherwise.
	 */
	private boolean isSubTypeOf(EClass superType, EClass eClass) {
		for (final EClass candidate : eClass.getEAllSuperTypes()) {
			if (candidate == superType) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This will load all dynamic modules in the first {@link ResourceSet} found by iterating over the
	 * {@link #currentModules}.
	 * 
	 * @return The set of loaded modules.
	 */
	private Set<Module> loadDynamicModules() {
		final Set<File> dynamicModuleFiles = AcceleoDynamicTemplatesRegistry.INSTANCE.getRegisteredModules();
		final Set<Module> dynamicModules = new CompactLinkedHashSet<Module>();
		// shortcut
		if (dynamicModuleFiles.size() > 0) {
			ResourceSet resourceSet = null;
			for (Module module : currentModules) {
				if (module.eResource() != null && module.eResource().getResourceSet() != null) {
					resourceSet = module.eResource().getResourceSet();
					break;
				}
			}
			// If we couldn't find a resourceSet, break the loading loop and log an exception
			if (resourceSet == null) {
				// set as a blocker so that it is logged as an error
				AcceleoEnginePlugin.log(AcceleoEngineMessages
						.getString("AcceleoEvaluationEnvironment.DynamicModulesLoadingFailure"), true); //$NON-NLS-1$
				return dynamicModules;
			}
			if (!(resourceSet.getURIConverter() instanceof DynamicModulesURIConverter)) {
				resourceSet.setURIConverter(new DynamicModulesURIConverter(resourceSet.getURIConverter(),
						this));
			}
			for (File moduleFile : dynamicModuleFiles) {
				if (moduleFile.exists() && moduleFile.canRead()) {
					try {
						Resource res = ModelUtils.load(moduleFile, resourceSet).eResource();
						for (EObject root : res.getContents()) {
							if (root instanceof Module) {
								dynamicModules.add((Module)root);
							}
						}
					} catch (IOException e) {
						AcceleoEnginePlugin.log(e, false);
					}
				}
			}
		}
		return dynamicModules;
	}

	/**
	 * This will resolve all dependencies of the given module and keep references to all accessible templates.
	 * 
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 */
	private void mapAllTemplates(Module module) {
		if (currentModules.contains(module)) {
			return;
		}
		currentModules.add(module);

		for (final ModuleElement elem : module.getOwnedModuleElement()) {
			if (elem instanceof Template) {
				Set<Template> namesakes = templates.get(elem.getName());
				if (namesakes == null) {
					namesakes = new CompactLinkedHashSet<Template>();
					templates.put(elem.getName(), namesakes);
				}
				namesakes.add((Template)elem);
				mapOverridingTemplate((Template)elem);
			}
		}
		for (final Module extended : module.getExtends()) {
			mapAllTemplates(extended);
		}
		for (final Module imported : module.getImports()) {
			mapAllTemplates(imported);
		}
	}

	/**
	 * Handles the mapping of a single dynamic module.
	 * 
	 * @param module
	 *            Module that is to be mapped as a dynamic module.
	 * @param dynamicModules
	 *            The set of all dynamic modules as returned by {@link #loadDynamicModules()}.
	 */
	private void mapDynamicModule(Module module, Set<Module> dynamicModules) {
		boolean map = false;

		final Set<Module> unMappedRequiredModules = new CompactLinkedHashSet<Module>();
		for (Module extended : module.getExtends()) {
			if (dynamicModules.contains(extended)) {
				mapDynamicModule(extended, dynamicModules);
			}
			if (currentModules.contains(extended)) {
				map = true;
			} else {
				unMappedRequiredModules.add(extended);
			}
		}
		// This module shouldn't be added to the context. Go to next.
		if (!map) {
			return;
		}

		for (Module imported : module.getImports()) {
			if (!currentModules.contains(imported)) {
				unMappedRequiredModules.add(imported);
			}
		}

		for (Module required : unMappedRequiredModules) {
			mapAllTemplates(required);
		}

		for (final ModuleElement elem : module.getOwnedModuleElement()) {
			if (elem instanceof Template) {
				final Template ownedTemplate = (Template)elem;
				for (final Template overriden : ownedTemplate.getOverrides()) {
					Set<Template> overriding = dynamicOverrides.get(overriden);
					if (overriding == null && templates.containsKey(overriden.getName())) {
						overriding = new CompactLinkedHashSet<Template>();
						Template match = overriden;
						Set<Template> candidates = templates.get(overriden.getName());
						for (Template template : candidates) {
							if (EcoreUtil.equals(template, overriden)) {
								match = template;
								break;
							}
						}
						dynamicOverrides.put(match, overriding);
					}
					if (overriding != null) {
						overriding.add(ownedTemplate);
					}
				}
				if (ownedTemplate.getOverrides().size() == 0) {
					Set<Template> namesakes = templates.get(ownedTemplate.getName());
					if (namesakes == null) {
						namesakes = new CompactLinkedHashSet<Template>();
						templates.put(ownedTemplate.getName(), namesakes);
					}
					namesakes.add(ownedTemplate);
				}
			}
		}
		currentModules.add(module);
	}

	/**
	 * Maps dynamic overriding templates for smoother polymorphic resolution.
	 */
	private void mapDynamicOverrides() {
		Set<Module> dynamicModules = loadDynamicModules();

		for (Module module : dynamicModules) {
			mapDynamicModule(module, dynamicModules);
		}
	}

	/**
	 * This will create entries for the given template in the overriding map as needed.
	 * 
	 * @param elem
	 *            The template which we need to map if overriding.
	 */
	private void mapOverridingTemplate(Template elem) {
		for (final Template overriden : elem.getOverrides()) {
			Set<Template> overriding = overridingTemplates.get(overriden);
			if (overriding == null) {
				overriding = new CompactLinkedHashSet<Template>();
				overridingTemplates.put(overriden, overriding);
			}
			overriding.add(elem);
		}
	}

	/**
	 * Returns the most specific template of the given two for the given arguments.
	 * 
	 * @param template1
	 *            First of the two compared templates.
	 * @param template2
	 *            Second of the compared templates.
	 * @param actualArgumentTypes
	 *            Types of the actual arguments of the call.
	 * @return The most specific templates for <code>actualArgumentTypes</code>.
	 */
	private Template mostSpecificTemplate(Template template1, Template template2,
			List<Object> actualArgumentTypes) {
		Template mostSpecific;
		// number of arguments which are more specific on template1 as compared to template2
		int template1SpecificArgumentCount = 0;
		// ...
		int template2SpecificArgumentCount = 0;
		for (int i = 0; i < actualArgumentTypes.size(); i++) {
			final Object actualArgumentType = actualArgumentTypes.get(i);
			final EClassifier template1Type = template1.getParameter().get(i).getType();
			final EClassifier template2Type = template2.getParameter().get(i).getType();
			if (template1Type == template2Type) {
				continue;
			}
			if (actualArgumentType instanceof EObject) {
				if (isSubTypeOf((EClass)template1Type, (EClass)template2Type)) {
					template2SpecificArgumentCount++;
				} else {
					template1SpecificArgumentCount++;
				}
			} else {
				// TODO are there any chance the argument would not be an EObject?
				assert false;
			}
		}
		if (template1SpecificArgumentCount >= template2SpecificArgumentCount) {
			mostSpecific = template1;
		} else {
			mostSpecific = template2;
		}
		return mostSpecific;
	}

	/**
	 * Reorders the <code>candidates</code> list so that templates overriding <code>origin</code> come first.
	 * Templates in the same module as <code>call</code> come second, then templates in extended modules, and
	 * finally templates in imported modules.
	 * 
	 * @param origin
	 *            The originating module.
	 * @param candidates
	 *            List that is to be reordered.
	 * @return The reordered list.
	 */
	private List<Template> reorderCandidatesPriority(Module origin, Set<Template> candidates) {
		// FIXME Performance bottleneck. How can we order candidates of a template call more effectively?
		final List<Template> reorderedList = new ArrayList<Template>(candidates.size());

		// We only support single inheritance. get(0) comes from that.
		for (final Template candidate : new CompactLinkedHashSet<Template>(candidates)) {
			boolean isOverridingCandidate = false;
			Module module = (Module)candidate.eContainer();
			while (!isOverridingCandidate && module != null && module.getExtends().size() > 0) {
				if (module.getExtends().get(0) == origin) {
					reorderedList.add(candidate);
					candidates.remove(candidate);
					isOverridingCandidate = true;
				}
				module = module.getExtends().get(0);
			}
		}

		for (final Template candidate : new CompactLinkedHashSet<Template>(candidates)) {
			if (candidate.eContainer() == origin) {
				reorderedList.add(candidate);
				candidates.remove(candidate);
			}
		}

		for (final Template candidate : new CompactLinkedHashSet<Template>(candidates)) {
			for (final Module extended : origin.getExtends()) {
				if (candidate.eContainer() == extended) {
					reorderedList.add(candidate);
					candidates.remove(candidate);
				}
			}
		}

		for (final Template candidate : new CompactLinkedHashSet<Template>(candidates)) {
			for (final Module imported : origin.getImports()) {
				if (candidate.eContainer() == imported) {
					reorderedList.add(candidate);
					candidates.remove(candidate);
				}
			}
		}

		for (final Template candidate : new CompactLinkedHashSet<Template>(candidates)) {
			for (final Module imported : origin.getImports()) {
				Module myImportedModule = imported;

				while (myImportedModule.getExtends().size() > 0) {
					if (myImportedModule.getExtends().get(0) == candidate.eContainer()) {
						reorderedList.add(candidate);
						candidates.remove(candidate);
					}
					myImportedModule = myImportedModule.getExtends().get(0);
				}
			}
		}

		return reorderedList;
	}

	/**
	 * Reorders the given list of candidates by order of overriding. For example if the set contains T11
	 * overriding T1, T21 overriding T11, T31 overriding T11 and T12 overriding T1, The returned list will
	 * contain in this order : {T31, T12, T21, T11}.
	 * 
	 * @param candidates
	 *            Set of candidates that are to be reordered.
	 * @return The reordered list of candidates.
	 */
	private List<Template> reorderDynamicOverrides(Set<Template> candidates) {
		final List<Template> reorderedList = new ArrayList<Template>(candidates.size());

		final Set<Template> lowest = new CompactLinkedHashSet<Template>(candidates);
		while (!lowest.isEmpty()) {
			for (final Template candidate : new CompactLinkedHashSet<Template>(lowest)) {
				for (final Template overriden : candidate.getOverrides()) {
					if (lowest.contains(overriden)) {
						lowest.remove(overriden);
					}
				}
			}
			if (lowest.isEmpty()) {
				List<Template> remainingCandidates = new ArrayList<Template>(candidates);
				remainingCandidates.removeAll(reorderedList);
				reorderedList.addAll(remainingCandidates);
			} else {
				reorderedList.addAll(lowest);
				lowest.clear();
				lowest.addAll(candidates);
				lowest.removeAll(reorderedList);
			}
		}

		return reorderedList;
	}
}
