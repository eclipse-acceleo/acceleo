/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
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

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.BundleURLConverter;
import org.eclipse.acceleo.common.utils.AcceleoCollections;
import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.common.utils.Deque;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.service.AcceleoDynamicTemplatesRegistry;
import org.eclipse.acceleo.engine.service.AcceleoModulePropertiesAdapter;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.ecore.AnyType;
import org.eclipse.ocl.ecore.BagType;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.EcoreEvaluationEnvironment;
import org.eclipse.ocl.ecore.OrderedSetType;
import org.eclipse.ocl.ecore.SequenceType;
import org.eclipse.ocl.ecore.SetType;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.options.EvaluationOptions;
import org.eclipse.ocl.util.Bag;

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
	private final SetMultimap<Template, Template> templatesDynamicOverrides = AcceleoCollections
			.newCompactLinkedHashSetMultimap();

	/** Maps all overriding templates to their <code>super</code>. */
	private final SetMultimap<Template, Template> overridingTemplates = AcceleoCollections
			.newCompactLinkedHashSetMultimap();

	/** This will hold a reference to the class allowing for properties lookup. */
	private AcceleoPropertiesLookup propertiesLookup;

	/** This will allow us to map all accessible templates to their name. */
	private final SetMultimap<String, Template> templates = AcceleoCollections
			.newCompactLinkedHashSetMultimap();

	/**
	 * Allows us to totally get rid of the inherited map. This will mainly serve the purpose of allowing
	 * multiple bindings against the same variable name.
	 */
	private final Deque<ListMultimap<String, Object>> scopedVariableMap = new CircularArrayDeque<ListMultimap<String, Object>>();

	/**
	 * This will contain variables that are global to a generation module.
	 */
	private final ListMultimap<String, Object> globalVariableMap = AcceleoCollections
			.newCircularArrayDequeMultimap();

	private SetMultimap<String, Query> queries = AcceleoCollections.newCompactLinkedHashSetMultimap();

	private SetMultimap<Query, Query> queriesDynamicOverrides = AcceleoCollections
			.newCompactLinkedHashSetMultimap();

	private SetMultimap<Query, Query> overridingQueries = AcceleoCollections
			.newCompactLinkedHashSetMultimap();

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
		scopedVariableMap.add(AcceleoCollections.<String, Object> newCircularArrayDequeMultimap());
		mapAllTemplates(module);

		AcceleoModulePropertiesAdapter adapter = (AcceleoModulePropertiesAdapter)EcoreUtil.getAdapter(module
				.eAdapters(), AcceleoModulePropertiesAdapter.class);
		if (adapter == null || !adapter.getProperties().contains(IAcceleoConstants.DISABLE_DYNAMIC_MODULES)) {
			mapDynamicOverrides();
		}

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
		scopedVariableMap.add(AcceleoCollections.<String, Object> newCircularArrayDequeMultimap());
		mapAllTemplates(module);

		AcceleoModulePropertiesAdapter adapter = (AcceleoModulePropertiesAdapter)EcoreUtil.getAdapter(module
				.eAdapters(), AcceleoModulePropertiesAdapter.class);
		if (adapter == null || !adapter.getProperties().contains(IAcceleoConstants.DISABLE_DYNAMIC_MODULES)) {
			mapDynamicOverrides();
		}

		setOption(EvaluationOptions.LAX_NULL_HANDLING, Boolean.FALSE);
		propertiesLookup = properties;
	}

	/**
	 * Returns the last value of the given list.
	 * <p>
	 * Makes no effort to try and check whether the argument is valid.
	 * </p>
	 * 
	 * @param values
	 *            List we need the last value from.
	 * @param <V>
	 *            Type of the list's values.
	 * @return The last value of the given list.
	 */
	private static <V> V getLast(List<V> values) {
		final ListIterator<V> iterator = values.listIterator(values.size());
		return iterator.previous();
	}

	/**
	 * Removes the last value of the given list.
	 * <p>
	 * Makes no effort to try and check whether the argument is valid.
	 * </p>
	 * 
	 * @param values
	 *            List we need the last value from.
	 * @param <V>
	 *            Type of the list's values.
	 * @return The last value of the given list.
	 */
	private static <V> V removeLast(List<V> values) {
		final ListIterator<V> iterator = values.listIterator(values.size());
		final V last = iterator.previous();
		iterator.remove();
		return last;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#add(java.lang.String, java.lang.Object)
	 */
	@Override
	public void add(String name, Object value) {
		ListMultimap<String, Object> variableMap;
		if (name.startsWith(TEMPORARY_CONTEXT_VAR_PREFIX) || name.startsWith(TEMPORARY_INVOCATION_ARG_PREFIX)) {
			variableMap = globalVariableMap;
		} else {
			variableMap = scopedVariableMap.getLast();
		}
		variableMap.put(name, value);
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
		/*
		 * Shortcut OCL for operations returning "EJavaObject" : these could be collections, but OCL would
		 * discard any value other than the first in such cases. See bug 287052.
		 */
		if (operation.getEType() == EcorePackage.eINSTANCE.getEJavaObject()) {
			result = callOperationWorkaround287052(operation, opcode, source, args);
		} else {
			result = super.callOperation(operation, opcode, source, args);
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
		scopedVariableMap.add(AcceleoCollections.<String, Object> newCircularArrayDequeMultimap());
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
	public List<Template> getAllCandidates(Module origin, Template call, Object[] arguments) {
		final List<Object> argumentTypes = new ArrayList<Object>(arguments.length);
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] instanceof EObject) {
				argumentTypes.add(((EObject)arguments[i]).eClass());
			} else if (arguments[i] != null) {
				argumentTypes.add(arguments[i].getClass());
			} else {
				argumentTypes.add(NULL_ARGUMENT);
			}
		}

		/*
		 * NOTE : we depend on the ordering offered by List types. Do not change implementation without
		 * testing.
		 */
		final List<Template> orderedNamesakes = reorderCandidatesPriority(origin, getAllCandidateNamesakes(
				templates, origin, call, argumentTypes));
		final List<Template> dynamicOverriding = reorderDynamicOverrides(getAllDynamicCandidateOverriding(
				templatesDynamicOverrides, orderedNamesakes, argumentTypes));
		final List<Template> overriding = getAllCandidateOverriding(overridingTemplates, origin,
				orderedNamesakes, argumentTypes);

		// overriding templates come first, then namesakes
		return Lists.newArrayList(Iterables.concat(dynamicOverriding, overriding, orderedNamesakes));
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
	public List<Query> getAllCandidates(Module origin, Query call, Object[] arguments) {
		final List<Object> argumentTypes = new ArrayList<Object>(arguments.length);
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] instanceof EObject) {
				argumentTypes.add(((EObject)arguments[i]).eClass());
			} else if (arguments[i] != null) {
				argumentTypes.add(arguments[i].getClass());
			} else {
				argumentTypes.add(NULL_ARGUMENT);
			}
		}

		/*
		 * NOTE : we depend on the ordering offered by List types. Do not change implementation without
		 * testing.
		 */
		final List<Query> orderedNamesakes = reorderCandidatesPriority(origin, getAllCandidateNamesakes(
				queries, origin, call, argumentTypes));
		final List<Query> dynamicOverriding = new ArrayList<Query>();
		final List<Query> overriding = getAllCandidateOverriding(overridingQueries, origin, orderedNamesakes,
				argumentTypes);

		// overriding templates come first, then namesakes
		return Lists.newArrayList(Iterables.concat(dynamicOverriding, overriding, orderedNamesakes));
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
	public <T> T getMostSpecific(Iterable<T> candidates, Object[] arguments) {
		final Iterator<T> candidateIterator = candidates.iterator();
		T mostSpecific = candidateIterator.next();
		if (!candidateIterator.hasNext()) {
			return mostSpecific;
		}

		while (candidateIterator.hasNext()) {
			mostSpecific = mostSpecific(mostSpecific, candidateIterator.next(), arguments);
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
		ListMultimap<String, Object> variableMap = scopedVariableMap.getLast();
		Map<String, Object> availableVariables = new HashMap<String, Object>();
		for (String key : variableMap.keys()) {
			List<Object> values = variableMap.get(key);
			availableVariables.put(key, getLast(values));
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
		ListMultimap<String, Object> variableMap = scopedVariableMap.getLast();
		if (variableMap.containsKey(name)) {
			value = getLast(variableMap.get(name));
		} else if (globalVariableMap.containsKey(name)) {
			value = getLast(globalVariableMap.get(name));
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#remove(java.lang.String)
	 */
	@Override
	public Object remove(String name) {
		ListMultimap<String, Object> variableMap;
		if (scopedVariableMap.getLast().containsKey(name)) {
			variableMap = scopedVariableMap.getLast();
		} else if (globalVariableMap.containsKey(name)) {
			variableMap = globalVariableMap;
		} else {
			return null;
		}

		return removeLast(variableMap.get(name));
	}

	/**
	 * This will remove and return the last variable scope.
	 * 
	 * @return Removes and return the last variable scope.
	 */
	public Map<String, Collection<Object>> removeVariableScope() {
		return scopedVariableMap.removeLast().asMap();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#replace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void replace(String name, Object value) {
		ListMultimap<String, Object> variableMap;
		if (name.startsWith(TEMPORARY_CONTEXT_VAR_PREFIX) || name.startsWith(TEMPORARY_INVOCATION_ARG_PREFIX)) {
			variableMap = globalVariableMap;
		} else {
			variableMap = scopedVariableMap.getLast();
		}

		if (variableMap.containsKey(name)) {
			removeLast(variableMap.get(name));
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
		ListMultimap<String, Object> variableMap = scopedVariableMap.getLast();
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
	private <T> Set<T> applicableTemplates(Set<T> candidates, final List<Object> argumentTypes) {
		Predicate<T> argumentSizeMatch = new Predicate<T>() {
			public boolean apply(T input) {
				final List<Variable> parameters;
				if (input instanceof Template) {
					parameters = ((Template)input).getParameter();
				} else if (input instanceof Query) {
					parameters = ((Query)input).getParameter();
				} else {
					parameters = new ArrayList<Variable>();
				}

				return parameters.size() == argumentTypes.size() || parameters.isEmpty() && argumentTypes
						.size() == 1;
			}
		};
		Predicate<T> argumentsMatch = new Predicate<T>() {
			public boolean apply(T input) {
				final List<Variable> parameters;
				if (input instanceof Template) {
					parameters = ((Template)input).getParameter();
				} else if (input instanceof Query) {
					parameters = ((Query)input).getParameter();
				} else {
					parameters = new ArrayList<Variable>();
				}

				if (parameters.isEmpty() && argumentTypes.size() == 1) {
					return true;
				}
				boolean argumentMatch = true;
				for (int i = 0; i < argumentTypes.size() && argumentMatch; i++) {
					final Variable param = parameters.get(i);
					final Object parameterType = param.getType();
					argumentMatch = isApplicableArgument(parameterType, argumentTypes.get(i));
				}
				return argumentMatch;
			}
		};

		return Sets.filter(candidates, Predicates.and(argumentSizeMatch, argumentsMatch));
	}

	/**
	 * This will return the list of all namesakes of the template <code>call</code> applicable for
	 * <code>arguments</code>.
	 * 
	 * @param <T>
	 * @param origin
	 *            Origin of the template call.
	 * @param call
	 *            The called element.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates of this name in the current context.
	 */
	private <T> Set<T> getAllCandidateNamesakes(SetMultimap<String, T> templates, Module origin,
			ModuleElement call, List<Object> argumentTypes) {
		final Set<T> namesakes = new CompactLinkedHashSet<T>();
		final Set<T> candidates = templates.get(call.getName());
		final int candidateSize = candidates.size();
		if (candidateSize == 0) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationEnvironment.ModuleResolutionError")); //$NON-NLS-1$
		}
		Set<Module> scope = Sets.union(Collections.singleton(origin), getScopeOf(origin));
		for (T candidate : candidates) {
			if (scope.contains(((EObject)candidate).eContainer())) {
				namesakes.add(candidate);
			}
		}
		if (namesakes.size() == 1) {
			return namesakes;
		}
		namesakes.retainAll(applicableTemplates(namesakes, argumentTypes));
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
			scope = Sets.union(scope, getExtendedScope(extended));
		}

		List<Module> imports = module.getImports();
		for (int i = 0; i < imports.size(); i++) {
			final Module importedModule = imports.get(i);
			scope = Sets.union(scope, Collections.singleton(importedModule));
			scope = Sets.union(scope, getExtendedScope(importedModule));
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
			scope = Sets.union(scope, getExtendedScope(extended));
		}

		return scope;
	}

	/**
	 * This will return the list of all templates overriding one of <code>overridenTemplates</code> that are
	 * applicable for <code>arguments</code>. These will be ordered as specified on
	 * {@link #reorderCandidatesPriority(Module, Set)}.
	 * 
	 * @param overriding
	 *            List of templates candidate to overriding.
	 * @param origin
	 *            Origin of the template call.
	 * @param overriden
	 *            List of templates we seek overriding templates of.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates overriding one of <code>overridenTemplates</code> in the
	 *         current context.
	 */
	private <T> List<T> getAllCandidateOverriding(SetMultimap<T, T> overriding, Module origin,
			List<T> overriden, List<Object> argumentTypes) {
		final List<T> candidateOverriding = new ArrayList<T>();
		for (int i = 0; i < overriden.size(); i++) {
			final Set<T> candidates = overriding.get(overriden.get(i));
			if (candidates != null) {
				final Set<T> applicableCandidates = applicableTemplates(candidates, argumentTypes);
				for (T template : applicableCandidates) {
					EObject eContainer = ((EObject)template).eContainer();
					if (eContainer instanceof Module && (getScopeOf(origin).contains(eContainer) || eContainer
							.equals(origin))) {
						candidateOverriding.add(template);
					}
				}
				// no need to order this, it'll be ordered later on
				candidateOverriding.addAll(getAllCandidateOverriding(overriding, origin, new ArrayList<T>(
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
	 * @param dynamicOverrides
	 *            List of dynamic overriding candidates.
	 * @param overriden
	 *            List of templates we seek overriding templates of.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates dynamically overriding one of <code>overridenTemplates</code>.
	 */
	private <T> Set<T> getAllDynamicCandidateOverriding(SetMultimap<T, T> dynamicOverrides, List<T> overriden,
			List<Object> argumentTypes) {
		final Set<T> dynamicOverriding = new CompactLinkedHashSet<T>();
		for (int i = 0; i < overriden.size(); i++) {
			final Set<T> candidates = dynamicOverrides.get(overriden.get(i));
			if (candidates != null && !candidates.isEmpty()) {
				final Set<T> applicableCandidates = applicableTemplates(candidates, argumentTypes);
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
		} else if (expectedType == argumentType) {
			isApplicable = true;
		} else if (expectedType instanceof EClass && argumentType instanceof EClass) {
			isApplicable = expectedType == argumentType || isSubTypeOf(expectedType, argumentType);
		} else if (expectedType instanceof Class<?> && argumentType instanceof Class<?>) {
			isApplicable = ((Class<?>)expectedType).isAssignableFrom((Class<?>)argumentType);
		} else if (expectedType instanceof EDataType && argumentType instanceof Class<?>) {
			if (expectedType instanceof BagType && argumentType instanceof Class<?>) {
				Class<?> clazz = (Class<?>)argumentType;
				isApplicable = Bag.class.isAssignableFrom(clazz);
			} else if (expectedType instanceof OrderedSetType && argumentType instanceof Class<?>) {
				Class<?> clazz = (Class<?>)argumentType;
				isApplicable = Set.class.isAssignableFrom(clazz);
			} else if (expectedType instanceof SetType && argumentType instanceof Class<?>) {
				Class<?> clazz = (Class<?>)argumentType;
				isApplicable = Set.class.isAssignableFrom(clazz);
			} else if (expectedType instanceof SequenceType && argumentType instanceof Class<?>) {
				Class<?> clazz = (Class<?>)argumentType;
				isApplicable = List.class.isAssignableFrom(clazz);
			} else if (expectedType instanceof CollectionType && argumentType instanceof Class<?>) {
				Class<?> clazz = (Class<?>)argumentType;
				isApplicable = Collection.class.isAssignableFrom(clazz);
			} else {
				isApplicable = ((EDataType)expectedType).getInstanceClass() == argumentType;
			}
		} else if (expectedType instanceof EEnum && argumentType instanceof EClass) {
			isApplicable = argumentType.equals(EcorePackage.eINSTANCE.getEEnumLiteral());
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
	private boolean isSubTypeOf(Object superType, Object eClass) {
		// if both types are EClass(es) then do the usual stuff
		boolean result = false;
		if (superType instanceof EClass && eClass instanceof EClass) {
			for (final EClass candidate : ((EClass)eClass).getEAllSuperTypes()) {
				if (candidate == superType) {
					result = true;
					break;
				}
			}
		} else if (superType instanceof AnyType) {
			result = true;
		}

		return result;
	}

	/**
	 * This will load all dynamic modules in the first {@link ResourceSet} found by iterating over the
	 * {@link #currentModules}.
	 * 
	 * @return The set of loaded modules.
	 */
	private Set<Module> loadDynamicModules() {
		final Set<URL> dynamicModuleFiles = new CompactLinkedHashSet<URL>();
		final Set<Module> dynamicModules = new CompactLinkedHashSet<Module>();
		// shortcut
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
			resourceSet.setURIConverter(new DynamicModulesURIConverter(resourceSet.getURIConverter(), this));
		}
		// We have a resource set, let's find out where its module are coming from
		List<Resource> resources = resourceSet.getResources();
		Iterator<Resource> iterator = resources.iterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			URI uri = resource.getURI();
			String generatorID = uri.toString();

			// Chicken sacrifice done right! /!\ Warning voodoo magic /!\
			if (uri.isPlatformPlugin() && uri.segments().length > 2) {
				generatorID = uri.segment(1);
			} else if (uri.isPlatformResource() && uri.segments().length > 2) {
				// Not supposed to happen since extension point works only when deployed in eclipse
				generatorID = uri.segment(1);
			} else if (uri.isPlatform() && uri.segments().length > 2) {
				// Not supposed to happen since extension point works only when deployed in eclipse
				generatorID = uri.segment(1);
			} else if (uri.isFile() || generatorID.startsWith("jar:file:")) { //$NON-NLS-1$
				// lokup as osgi bundle
				BundleURLConverter converter = new BundleURLConverter(generatorID);
				generatorID = converter.resolveAsPlatformPlugin();
				// lookup as plugin
				if (generatorID == null) {
					Iterator it = uri.segmentsList().iterator();
					while (it.hasNext()) {

						generatorID = it.next().toString();
						if (EcorePlugin.getPlatformResourceMap().get(generatorID) != null) {
							break;
						}
					}
				}
				// generatorID = AcceleoWorkspaceUtil.resolveAsPlatformPlugin(generatorID);
				if (generatorID != null && generatorID.startsWith("platform:/plugin/") && URI.createURI( //$NON-NLS-1$
						generatorID).segments().length > 2) {
					URI tmpURI = URI.createURI(generatorID);
					generatorID = tmpURI.segment(1);
				}
			}
			final Set<URL> dynamicAcceleoModulesFiles = AcceleoDynamicTemplatesRegistry.INSTANCE
					.getRegisteredModules(generatorID);
			dynamicModuleFiles.addAll(dynamicAcceleoModulesFiles);
		}

		// We add the non-imported dynamic overridiing modules
		dynamicModuleFiles.addAll(AcceleoDynamicTemplatesRegistry.INSTANCE.getRegisteredModules());

		for (URL moduleFile : dynamicModuleFiles) {
			try {
				Resource res = ModelUtils.load(URI.createURI(moduleFile.toString()), resourceSet).eResource();
				for (EObject root : res.getContents()) {
					if (root instanceof Module) {
						dynamicModules.add((Module)root);
					}
				}
			} catch (IOException e) {
				AcceleoEnginePlugin.log(e, false);
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
				templates.put(elem.getName(), (Template)elem);
				mapOverridingTemplate((Template)elem);
			} else if (elem instanceof Query) {
				queries.put(elem.getName(), (Query)elem);
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
				map = true;
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
				for (final Template overriden : templates.get(ownedTemplate.getName())) {
					// Allows dynamic polyformism overriding that cannot be set because cant override another
					// method that does not exist.
					// choose from the accessible templates that override in static way or via dynamic
					// overriding extension point without the need of the "overrides" declaration.
					Template match = null;
					final Iterator<Template> templateIterator = templates.get(overriden.getName()).iterator();
					while (match == null && templateIterator.hasNext()) {
						final Template template = templateIterator.next();
						if (EcoreUtil.equals(template, overriden)) {
							match = template;
						}
					}
					if (match != null) {
						templatesDynamicOverrides.put(match, ownedTemplate);
					}
				}
				templates.put(ownedTemplate.getName(), ownedTemplate);
			} else if (elem instanceof Query) {
				final Query ownedQuery = (Query)elem;
				for (final Query overriden : queries.get(ownedQuery.getName())) {
					// Allows dynamic polyformism overriding that cannot be set because cant override another
					// method that does not exist.
					// choose from the accessible templates that override in static way or via dynamic
					// overriding extension point without the need of the "overrides" declaration.
					Query match = null;
					final Iterator<Query> queryIterator = queries.get(overriden.getName()).iterator();
					while (match == null && queryIterator.hasNext()) {
						final Query query = queryIterator.next();
						if (EcoreUtil.equals(query, overriden)) {
							match = query;
						}
					}
					if (match != null) {
						queriesDynamicOverrides.put(match, ownedQuery);
					}
				}
				queries.put(ownedQuery.getName(), ownedQuery);
			}
		}
		currentModules.add(module);
	}

	/**
	 * Maps dynamic overriding templates for smoother polymorphic resolution.
	 */
	private void mapDynamicOverrides() {
		if (AcceleoDynamicTemplatesRegistry.INSTANCE.getRegisteredModules().isEmpty()) {
			return;
		}

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
			overridingTemplates.put(overriden, elem);
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
	private <T> T mostSpecific(T template1, T template2, Object[] actualArgumentTypes) {
		T mostSpecific;
		// number of arguments which are more specific on template1 as compared to template2
		int template1SpecificArgumentCount = 0;
		// ...
		int template2SpecificArgumentCount = 0;
		for (int i = 0; i < actualArgumentTypes.length; i++) {
			final Object actualArgumentType = actualArgumentTypes[i];

			final List<Variable> parameters1;
			if (template1 instanceof Template) {
				parameters1 = ((Template)template1).getParameter();
			} else if (template1 instanceof Query) {
				parameters1 = ((Query)template1).getParameter();
			} else {
				parameters1 = new ArrayList<Variable>();
			}

			final List<Variable> parameters2;
			if (template2 instanceof Template) {
				parameters2 = ((Template)template2).getParameter();
			} else if (template2 instanceof Query) {
				parameters2 = ((Query)template2).getParameter();
			} else {
				parameters2 = new ArrayList<Variable>();
			}

			if (parameters1.size() == 0 && parameters2.size() == 0) {
				continue;
			}
			final EClassifier template1Type = parameters1.get(i).getType();
			final EClassifier template2Type = parameters2.get(i).getType();
			if (template1Type == template2Type) {
				continue;
			}
			if (actualArgumentType instanceof EObject) {
				if (isSubTypeOf(template1Type, template2Type)) {
					template2SpecificArgumentCount++;
				} else {
					template1SpecificArgumentCount++;
				}
			} else if (actualArgumentType instanceof Collection<?>) {
				template1SpecificArgumentCount++;
			} else {
				// are there any chance the argument would not be an EObject?
				throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
						"AcceleoEvaluationVisitor.ArgumentNotEObjectOrCollection", actualArgumentType)); //$NON-NLS-1$
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
	private <T> List<T> reorderCandidatesPriority(Module origin, Set<T> candidates) {
		final List<T> reorderedList = new ArrayList<T>(candidates.size());

		// We only support single inheritance. get(0) comes from that.
		Iterator<T> candidateIterator = candidates.iterator();
		while (candidateIterator.hasNext()) {
			final T candidate = candidateIterator.next();
			boolean isOverridingCandidate = false;
			Module module = (Module)((EObject)candidate).eContainer();
			while (!isOverridingCandidate && module != null && module.getExtends().size() > 0) {
				if (module.getExtends().get(0) == origin) {
					reorderedList.add(candidate);
					candidateIterator.remove();
					isOverridingCandidate = true;
				}
				module = module.getExtends().get(0);
			}
		}

		candidateIterator = candidates.iterator();
		while (candidateIterator.hasNext()) {
			final T candidate = candidateIterator.next();
			if (((EObject)candidate).eContainer() == origin) {
				reorderedList.add(candidate);
				candidateIterator.remove();
			}
		}

		candidateIterator = candidates.iterator();
		while (candidateIterator.hasNext()) {
			final T candidate = candidateIterator.next();
			for (final Module extended : origin.getExtends()) {
				if (((EObject)candidate).eContainer() == extended) {
					reorderedList.add(candidate);
					candidateIterator.remove();
				}
			}
		}

		candidateIterator = candidates.iterator();
		while (candidateIterator.hasNext()) {
			final T candidate = candidateIterator.next();
			for (final Module imported : origin.getImports()) {
				if (((EObject)candidate).eContainer() == imported) {
					reorderedList.add(candidate);
					candidateIterator.remove();
				}
			}
		}

		candidateIterator = candidates.iterator();
		while (candidateIterator.hasNext()) {
			final T candidate = candidateIterator.next();
			for (final Module imported : origin.getImports()) {
				Module myImportedModule = imported;

				boolean shouldBreak = false;
				while (myImportedModule.getExtends().size() > 0) {
					if (myImportedModule.getExtends().get(0) == ((EObject)candidate).eContainer()) {
						reorderedList.add(candidate);
						candidateIterator.remove();
						shouldBreak = true;
					}
					myImportedModule = myImportedModule.getExtends().get(0);
				}

				if (shouldBreak) {
					break;
				}
			}
		}

		return reorderedList;
	}

	/**
	 * Reorders the given list of candidates by order of overriding. For example if the set contains T11
	 * overriding T1, T21 overriding T11, T31 overriding T11 and T12 overriding T1, The returned list will
	 * contain in this order : {T31, T21, T12, T11}.
	 * 
	 * @param candidates
	 *            Set of candidates that are to be reordered.
	 * @return The reordered list of candidates.
	 */
	private List<Template> reorderDynamicOverrides(Set<Template> candidates) {
		Template[] array = candidates.toArray(new Template[candidates.size()]);
		Arrays.sort(array, new TemplateComparator());
		return Arrays.asList(array);
	}

	/**
	 * This comparator will allow us to reorder Templates according to their overrides.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class TemplateComparator implements Comparator<Template> {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Template o1, Template o2) {
			// Tries and find which template is "deeper" in the overriding tree.
			// We only consider simple overrides, hence the "get(0)" in the following code.
			Template currentLevel1 = o1;
			Template currentLevel2 = o2;
			boolean no1Override = currentLevel1.getOverrides().isEmpty();
			boolean no2Override = currentLevel2.getOverrides().isEmpty();
			while (!no1Override && !no2Override) {
				currentLevel1 = currentLevel1.getOverrides().get(0);
				currentLevel2 = currentLevel2.getOverrides().get(0);
				no1Override = currentLevel1.getOverrides().isEmpty();
				no2Override = currentLevel2.getOverrides().isEmpty();
			}

			int result = 0;
			if (no1Override && no2Override) {
				// same "level". Use the name's ordering
				result = o1.getName().compareTo(o2.getName());
			} else if (no1Override) {
				result = 1;
			} else if (no2Override) {
				result = -1;
			}
			return result;
		}
	}
}
