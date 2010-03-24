/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.AcceleoServicesRegistry;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.service.AcceleoDynamicTemplatesRegistry;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.ContentTreeIterator;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.ecore.EcoreEvaluationEnvironment;
import org.eclipse.ocl.options.EvaluationOptions;

/**
 * This will allow us to accurately evaluate custom operations defined in the Acceleo standard library and
 * resolve the right template for each call (guards, overrides, namesakes, ...).
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationEnvironment extends EcoreEvaluationEnvironment {
	/** This will be used as a place holder when trying to call templates with a <code>null</code> argument. */
	private static final Object NULL_ARGUMENT = new Object();

	/** This will be used as a place holder so that library operations call can return null. */
	private static final Object OPERATION_CALL_FAILED = new Object();

	/** This will allow the environment to know of the modules currently in the generation context. */
	final Set<Module> currentModules = new HashSet<Module>();

	/** Maps dynamic overrides as registered in the {@link AcceleoDynamicTemplatesRegistry}. */
	private final Map<Template, Set<Template>> dynamicOverrides = new HashMap<Template, Set<Template>>();

	/** Maps all overriding templates to their <code>super</code>. */
	private final Map<Template, Set<Template>> overridingTemplates = new HashMap<Template, Set<Template>>();

	/** This will hold the list of properties accessible from this generation. */
	private final List<Properties> properties = new ArrayList<Properties>();

	/**
	 * Keeps track of the cross referencer that's been created for this evaluation, if any. This is used and
	 * will be instantiated by the eInverse() non standard operation.
	 */
	private CrossReferencer referencer;

	/** This will allow us to map all accessible templates to their name. */
	private final Map<String, Set<Template>> templates = new HashMap<String, Set<Template>>();

	/**
	 * Maps a source String to its StringTokenizer. Needed for the implementation of the standard operation
	 * "strtok(String, Integer)" as currently specified.
	 */
	private final Map<String, StringTokenizer> tokenizers = new HashMap<String, StringTokenizer>();

	/**
	 * Allows us to totally get rid of the inherited map. This will mainly serve the purpose of allowing
	 * multiple bindings against the same variable name.
	 */
	private final Map<String, LinkedList<Object>> variableMap = new HashMap<String, LinkedList<Object>>();

	/**
	 * This constructor is needed by the factory.
	 * 
	 * @param parent
	 *            Parent evaluation environment.
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 * @param props
	 *            The list of Properties that can be accessed from this generation.
	 */
	public AcceleoEvaluationEnvironment(
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> parent,
			Module module, List<Properties> props) {
		super(parent);
		mapAllTemplates(module);
		mapDynamicOverrides();
		setOption(EvaluationOptions.LAX_NULL_HANDLING, Boolean.FALSE);
		properties.addAll(props);
	}

	/**
	 * This constructor will create our environment given the module from which to resolve dependencies.
	 * 
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 * @param props
	 *            The list of Properties that can be accessed from this generation.
	 */
	public AcceleoEvaluationEnvironment(Module module, List<Properties> props) {
		super();
		mapAllTemplates(module);
		mapDynamicOverrides();
		setOption(EvaluationOptions.LAX_NULL_HANDLING, Boolean.FALSE);
		properties.addAll(props);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#add(java.lang.String, java.lang.Object)
	 */
	@Override
	public void add(String name, Object value) {
		LinkedList<Object> values = variableMap.get(name);
		if (values == null) {
			values = new LinkedList<Object>();
			variableMap.put(name, values);
		}
		values.add(value);
	}

	/**
	 * The environment will delegate operation calls to this method if it needs to evaluate non-standard
	 * Acceleo operations.
	 * 
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	@SuppressWarnings("unchecked")
	public Object callNonStandardOperation(EOperation operation, Object source, Object... args) {
		Object result = OPERATION_CALL_FAILED;
		final String operationName = operation.getName();
		// Specifications of each non-standard operation can be found as comments of
		// AcceleoNonStandardLibrary#OPERATION_*.
		if (AcceleoNonStandardLibrary.OPERATION_OCLANY_TOSTRING.equals(operationName)) {
			result = toString(source);
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_INVOKE.equals(operationName)) {
			if (args.length == 3) {
				result = invoke(operation.eResource().getURI(), source, args);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_CURRENT.equals(operationName)) {
			if (args.length == 1) {
				result = getContext(args);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_GETPROPERTY.equals(operationName)) {
			if (args.length == 1) {
				result = getProperty((String)args[0]);
			} else if (args.length == 2 && args[1] instanceof String) {
				result = getProperty((String)args[0], (String)args[1]);
			} else if (args.length == 2) {
				result = getProperty((String)args[0], ((List<Object>)args[1]).toArray());
			} else if (args.length == 3) {
				result = getProperty((String)args[0], (String)args[1], ((List<Object>)args[2]).toArray());
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (source instanceof String) {
			result = callNonStandardStringOperation(operation, (String)source, args);
		} else if (source instanceof EObject) {
			result = callNonStandardEObjectOperation(operation, (EObject)source, args);
		} else if (source instanceof Collection<?>) {
			result = callNonStandardCollectionOperation(operation, (Collection<?>)source, args);
		}

		if (result != OPERATION_CALL_FAILED) {
			return result;
		}

		// If we're here, the operation is undefined.
		throw getExceptionOperationCallFailed(operation, source, args);
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
			result = callStandardOperation(operation, source, args);
		} else if (operation.getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
			result = callNonStandardOperation(operation, source, args);
		} else {
			result = super.callOperation(operation, opcode, source, args);
		}
		return result;
	}

	/**
	 * The environment will delegate operation calls to this method if it needs to evaluate a standard Acceleo
	 * operation.
	 * 
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	public Object callStandardOperation(EOperation operation, Object source, Object... args) {
		Object result = OPERATION_CALL_FAILED;
		// Specifications of each standard operation can be found as comments of
		// AcceleoStandardLibrary#OPERATION_*.
		if (source instanceof String) {
			final String sourceValue = (String)source;

			if (AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE.equals(operation.getName())) {
				result = substitute(sourceValue, (String)args[0], (String)args[1], false);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_INDEX.equals(operation.getName())) {
				// Increment java index value by 1 for OCL
				result = sourceValue.indexOf((String)args[0]) + 1;
				if (result == Integer.valueOf(0)) {
					result = Integer.valueOf(-1);
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_FIRST.equals(operation.getName())) {
				int endIndex = ((Integer)args[0]).intValue();
				if (endIndex < 0) {
					result = getInvalidResult();
				} else if (endIndex > sourceValue.length()) {
					result = sourceValue;
				} else {
					result = sourceValue.substring(0, endIndex);
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_LAST.equals(operation.getName())) {
				int charCount = ((Integer)args[0]).intValue();
				if (charCount < 0) {
					result = getInvalidResult();
				} else if (charCount > sourceValue.length()) {
					result = sourceValue;
				} else {
					result = sourceValue.substring(sourceValue.length() - charCount, sourceValue.length());
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_STRSTR.equals(operation.getName())) {
				result = sourceValue.contains((String)args[0]);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_STRTOK.equals(operation.getName())) {
				result = strtok(sourceValue, (String)args[0], (Integer)args[1]);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_STRCMP.equals(operation.getName())) {
				result = sourceValue.compareTo((String)args[0]);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_ISALPHA.equals(operation.getName())) {
				result = isAlpha(sourceValue);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM.equals(operation.getName())) {
				result = isAlphanumeric(sourceValue);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_TOUPPERFIRST.equals(operation.getName())) {
				if (sourceValue.length() == 0) {
					result = sourceValue;
				} else if (sourceValue.length() == 1) {
					result = sourceValue.toUpperCase();
				} else {
					result = Character.toUpperCase(sourceValue.charAt(0)) + sourceValue.substring(1);
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_TOLOWERFIRST.equals(operation.getName())) {
				if (sourceValue.length() == 0) {
					result = sourceValue;
				} else if (sourceValue.length() == 1) {
					result = sourceValue.toLowerCase();
				} else {
					result = Character.toLowerCase(sourceValue.charAt(0)) + sourceValue.substring(1);
				}
			}
		} else if (source instanceof Integer || source instanceof Long) {
			if (AcceleoStandardLibrary.OPERATION_INTEGER_TOSTRING.equals(operation.getName())) {
				result = source.toString();
			}
		} else if (source instanceof Double || source instanceof Float) {
			if (AcceleoStandardLibrary.OPERATION_REAL_TOSTRING.equals(operation.getName())) {
				result = source.toString();
			}
		}

		if (result != OPERATION_CALL_FAILED) {
			return result;
		}

		// If we're here, the operation is undefined.
		throw getExceptionOperationCallFailed(operation, source, args);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		variableMap.clear();
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
				call, argumentTypes));
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
			} else {
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#getValueOf(java.lang.String)
	 */
	@Override
	public Object getValueOf(String name) {
		if (variableMap.containsKey(name)) {
			return variableMap.get(name).getLast();
		}
		return null;
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
			result = true;
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
		if (!variableMap.containsKey(name)) {
			return null;
		}
		final Object removedValue = variableMap.get(name).removeLast();
		if (variableMap.get(name).size() == 0) {
			variableMap.remove(name);
		}
		return removedValue;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationEnvironment#replace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void replace(String name, Object value) {
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
		return variableMap.toString();
	}

	/**
	 * Returns a Sequence containing the full set of <code>source</code>'s ancestors.
	 * 
	 * @param source
	 *            The EObject we seek the ancestors of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of the receiver's ancestors.
	 */
	private Set<EObject> ancestors(EObject source, EClassifier filter) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		EObject container = source.eContainer();
		while (container != null) {
			if (filter == null || filter.isInstance(container)) {
				result.add(container);
			}
			container = container.eContainer();
		}
		return result;
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
		final Set<Template> applicableCandidates = new LinkedHashSet<Template>(candidates);
		for (final Template candidate : candidates) {
			if (candidate.getParameter().size() != argumentTypes.size()) {
				applicableCandidates.remove(candidate);
			}
		}
		for (int i = 0; i < argumentTypes.size(); i++) {
			for (final Template candidate : new LinkedHashSet<Template>(applicableCandidates)) {
				final Object parameterType = candidate.getParameter().get(i).getType();
				if (!isApplicableArgument(parameterType, argumentTypes.get(i))) {
					applicableCandidates.remove(candidate);
				}
			}
		}
		return applicableCandidates;
	}

	/**
	 * The environment will delegate operation calls to this method if it needs to evaluate non-standard
	 * EObject operations.
	 * 
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	private Object callNonStandardCollectionOperation(EOperation operation, Collection<?> source,
			Object... args) {
		Object result = null;
		final String operationName = operation.getName();

		if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_SEP.equals(operationName)) {
			final Collection<Object> temp = new ArrayList<Object>(source.size() << 1);
			final Iterator<?> sourceIterator = source.iterator();
			while (sourceIterator.hasNext()) {
				temp.add(sourceIterator.next());
				if (sourceIterator.hasNext()) {
					temp.add(args[0]);
				}
			}
			result = temp;
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_REVERSE.equals(operationName)) {
			final List<Object> temp = new ArrayList<Object>(source);
			Collections.reverse(temp);
			if (source instanceof LinkedHashSet<?>) {
				final Set<Object> reversedSet = new LinkedHashSet<Object>(temp);
				result = reversedSet;
			} else {
				result = temp;
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_LASTINDEXOF.equals(operationName)) {
			final List<Object> temp = new ArrayList<Object>(source);
			result = Integer.valueOf(temp.lastIndexOf(args[0]) + 1);
			if (result == Integer.valueOf(0)) {
				Integer.valueOf(-1);
			}
		}

		return result;
	}

	/**
	 * The environment will delegate operation calls to this method if it needs to evaluate non-standard
	 * EObject operations.
	 * 
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	private Object callNonStandardEObjectOperation(EOperation operation, EObject source, Object... args) {
		Object result = null;
		final String operationName = operation.getName();

		if (AcceleoNonStandardLibrary.OPERATION_OCLANY_EALLCONTENTS.equals(operationName)) {
			if (args.length == 0) {
				result = eAllContents(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = eAllContents(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_ANCESTORS.equals(operationName)) {
			if (args.length == 0) {
				result = ancestors(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = ancestors(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_SIBLINGS.equals(operationName)) {
			if (args.length == 0) {
				result = siblings(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = siblings(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_EINVERSE.equals(operationName)) {
			if (args.length == 0) {
				result = eInverse(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = eInverse(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_EGET.equals(operationName)) {
			result = eGet(source, (String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_ECONTAINER.equals(operationName)) {
			result = eContainer(source, (EClassifier)args[0]);
		}

		return result;
	}

	/**
	 * The environment will delegate operation calls to this method if it needs to evaluate non-standard
	 * String operations.
	 * 
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	private Object callNonStandardStringOperation(EOperation operation, String source, Object... args) {
		Object result = null;
		final String operationName = operation.getName();

		if (AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL.equals(operationName)) {
			result = substitute(source, (String)args[0], (String)args[1], true);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE.equals(operationName)) {
			result = source.replaceFirst((String)args[0], (String)args[1]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL.equals(operationName)) {
			result = source.replaceAll((String)args[0], (String)args[1]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_ENDSWITH.equals(operationName)) {
			result = source.endsWith((String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_EQUALSIGNORECASE.equals(operationName)) {
			result = source.equalsIgnoreCase((String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_STARTSWITH.equals(operationName)) {
			result = source.startsWith((String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TRIM.equals(operationName)) {
			result = source.trim();
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE.equals(operationName)) {
			result = tokenize(source, (String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_CONTAINS.equals(operationName)) {
			result = source.contains((String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_MATCHES.equals(operationName)) {
			result = source.matches((String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_LASTINDEX.equals(operationName)) {
			// Increment java index value by 1 for OCL
			result = source.lastIndexOf((String)args[0]) + 1;
			if (result == Integer.valueOf(0)) {
				result = Integer.valueOf(-1);
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTRING.equals(operationName)) {
			result = source.substring(((Integer)args[0]).intValue() - 1);
		}

		return result;
	}

	/**
	 * This will create the cross referencer that's to be used by the "eInverse" library. It will attempt to
	 * create the cross referencer on the target's resourceSet. If it is null, we'll then attempt to create
	 * the cross referencer on the target's resource. When the resource too is null, we'll create the cross
	 * referencer on the target's root container.
	 * 
	 * @param target
	 *            Target of the cross referencing.
	 */
	private void createEInverseCrossreferencer(EObject target) {
		if (target.eResource() != null && target.eResource().getResourceSet() != null) {
			final ResourceSet rs = target.eResource().getResourceSet();
			final ContentTreeIterator<Notifier> contentIterator = new ContentTreeIterator<Notifier>(
					Collections.singleton(rs)) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				@Override
				protected Iterator<Resource> getResourceSetChildren(ResourceSet resourceSet) {
					List<Resource> resources = new ArrayList<Resource>();
					for (Resource res : resourceSet.getResources()) {
						if (!IAcceleoConstants.EMTL_FILE_EXTENSION.equals(res.getURI().fileExtension())) {
							resources.add(res);
						}
					}
					resourceSetIterator = new ResourcesIterator(resources);
					return resourceSetIterator;
				}
			};
			referencer = new CrossReferencer(rs) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
				}

				@Override
				protected TreeIterator<Notifier> newContentsIterator() {
					return contentIterator;
				}
			};
		} else if (target.eResource() != null) {
			referencer = new CrossReferencer(target.eResource()) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
				}
			};
		} else {
			referencer = new CrossReferencer(EcoreUtil.getRootContainer(target)) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
				}
			};
		}
	}

	/**
	 * Iterates over the content of the given EObject and returns the elements of type <code>filter</code>
	 * from its content tree as a list.
	 * 
	 * @param source
	 *            The EObject we seek the content tree of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return The given EObject's whole content tree as a list.
	 */
	private List<EObject> eAllContents(EObject source, EClassifier filter) {
		final TreeIterator<EObject> contentIterator = source.eAllContents();
		final List<EObject> result = new ArrayList<EObject>();

		while (contentIterator.hasNext()) {
			final EObject next = contentIterator.next();
			if (filter == null || filter.isInstance(next)) {
				result.add(next);
			}
		}

		return result;
	}

	/**
	 * Handles calls to the non standard operation "eContainer". This will retrieve the very first container
	 * in the hierarchy that is of type <em>filter</em>.
	 * 
	 * @param source
	 *            The EObject we seek to retrieve a feature value of.
	 * @param filter
	 *            Types of the container we seek to retrieve.
	 * @return The first container of type <em>filter</em>.
	 */
	private Object eContainer(EObject source, EClassifier filter) {
		EObject container = source.eContainer();
		while (!filter.isInstance(container)) {
			container = container.eContainer();
		}
		return container;
	}

	/**
	 * Handles calls to the non standard operation "eGet". This will fetch the value of the feature named
	 * <em>featureName</em> on <em>source</em>.
	 * 
	 * @param source
	 *            The EObject we seek to retrieve a feature value of.
	 * @param featureName
	 *            Name of the feature which value we need to retrieve.
	 * @return Value of the given feature on the given object.
	 */
	private Object eGet(EObject source, String featureName) {
		Object result = null;

		for (EStructuralFeature feature : source.eClass().getEAllStructuralFeatures()) {
			if (feature.getName().equals(featureName)) {
				result = source.eGet(feature);
			}
		}

		return result;
	}

	/**
	 * Returns a Sequence containing the full set of the inverse references on the receiver.
	 * 
	 * @param target
	 *            The EObject we seek the inverse references of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of inverse references.
	 */
	private Set<EObject> eInverse(EObject target, EClassifier filter) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		if (referencer == null) {
			createEInverseCrossreferencer(target);
		}
		Collection<EStructuralFeature.Setting> settings = referencer.get(target);
		if (settings == null) {
			return Collections.emptySet();
		}
		for (EStructuralFeature.Setting setting : settings) {
			if (filter == null || filter.isInstance(setting.getEObject())) {
				result.add(setting.getEObject());
			}
		}
		return result;
	}

	/**
	 * This will return the list of all namesakes of the template <code>call</code> applicable for
	 * <code>arguments</code>.
	 * 
	 * @param call
	 *            The called element.
	 * @param argumentTypes
	 *            Types of the arguments of the call.
	 * @return All of the applicable templates of this name in the current context.
	 */
	private Set<Template> getAllCandidateNamesakes(Template call, List<Object> argumentTypes) {
		final Set<Template> namesakes = new LinkedHashSet<Template>();
		final Set<Template> candidates = templates.get(call.getName());
		if (candidates == null) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationEnvironment.ModuleResolutionError")); //$NON-NLS-1$
		}
		namesakes.addAll(candidates);
		if (namesakes.size() == 1) {
			return namesakes;
		}
		namesakes.retainAll(applicableTemplates(candidates, argumentTypes));
		return namesakes;
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
		final Set<Template> candidateOverriding = new LinkedHashSet<Template>();
		for (final Template overriden : overridenTemplates) {
			final Set<Template> candidates = overridingTemplates.get(overriden);
			if (candidates != null) {
				final Set<Template> applicableCandidates = applicableTemplates(candidates, argumentTypes);
				candidateOverriding.addAll(applicableCandidates);
				// no need to order this, it'll be ordered later on
				candidateOverriding.addAll(getAllCandidateOverriding(origin, new ArrayList<Template>(
						applicableCandidates), argumentTypes));
			}
		}
		return reorderCandidatesPriority(origin, candidateOverriding);
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
		final Set<Template> dynamicOverriding = new LinkedHashSet<Template>();
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
	 * Elements held by a reference with containment=true and derived=true are not returned by
	 * {@link EObject#eContents()}. This allows us to return the list of all contents from an EObject
	 * <b>including</b> those references.
	 * 
	 * @param eObject
	 *            The EObject we seek the content of.
	 * @return The list of all the content of a given EObject, derived containmnent references included.
	 */
	@SuppressWarnings("unchecked")
	private List<EObject> getContents(EObject eObject) {
		final List<EObject> result = new ArrayList<EObject>(eObject.eContents());
		for (final EReference reference : eObject.eClass().getEAllReferences()) {
			if (reference.isContainment() && reference.isDerived()) {
				final Object value = eObject.eGet(reference);
				if (value instanceof Collection) {
					for (Object newValue : (Collection)value) {
						if (!result.contains(newValue) && newValue instanceof EObject) {
							result.add((EObject)newValue);
						}
					}
				} else if (!result.contains(value) && value instanceof EObject) {
					result.add((EObject)value);
				}
			}
		}
		return result;
	}

	/**
	 * This will search the first context value corresponding to the given filter or index.
	 * 
	 * @param args
	 *            Arguments of the invocation.
	 * @return Result of the invocation.
	 */
	private Object getContext(Object[] args) {
		final String iteratorPrefix = "context"; //$NON-NLS-1$
		final Object soughtValue;
		final List<Object> allIterators = new ArrayList<Object>();
		int index = 0;
		Object value = getValueOf(iteratorPrefix + index++);
		while (value != null) {
			allIterators.add(value);
			value = getValueOf(iteratorPrefix + index++);
		}

		if (args[0] instanceof Integer) {
			int soughtIndex = ((Integer)args[0]).intValue();

			if (soughtIndex > allIterators.size() - 1) {
				soughtValue = allIterators.get(0);
			} else {
				soughtValue = allIterators.get(allIterators.size() - soughtIndex - 1);
			}
		} else {
			final EClassifier filter = (EClassifier)args[0];

			for (int i = allIterators.size() - 1; i >= 0; i--) {
				if (filter.isInstance(allIterators.get(i))) {
					value = allIterators.get(i);
					break;
				}
			}
			// "value" is null if there were no iterators of the expected type
			soughtValue = value;
		}

		return soughtValue;
	}

	/**
	 * This will be used whenever the environment tried to call a custom EOperation and failed.
	 * 
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return The ready-to-throw exception.
	 */
	private UnsupportedOperationException getExceptionOperationCallFailed(EOperation operation,
			Object source, Object... args) {
		final StringBuilder argErrorMsg = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			argErrorMsg.append(args[i].getClass().getSimpleName());
			if (i < args.length - 1) {
				argErrorMsg.append(", "); //$NON-NLS-1$
			}
		}
		final String sourceName;
		if (source == null) {
			sourceName = "null"; //$NON-NLS-1$
		} else {
			sourceName = source.getClass().getName();
		}
		return new UnsupportedOperationException(AcceleoEngineMessages.getString(
				"AcceleoEvaluationEnvironment.UndefinedOperation", operation.getName(), argErrorMsg //$NON-NLS-1$
						.toString(), sourceName));
	}

	/**
	 * This will return the value of the property corresponding to the given key. Precedence rules for the
	 * properties can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(Properties)}.
	 * 
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @return The value of the property corresponding to the given key.
	 */
	private String getProperty(String key) {
		String propertyValue = null;
		for (Properties propertiesHolder : properties) {
			final String property = propertiesHolder.getProperty(key);
			if (property != null) {
				/*
				 * Pass through MessageFormat so that we're consistent in the handling of special chars such
				 * as the apostrophe.
				 */
				propertyValue = MessageFormat.format(property, new Object[] {});
				break;
			}
		}
		return propertyValue;
	}

	/**
	 * This will return the value of the property corresponding to the given key, with parameters substituted
	 * as needed. Precedence rules for the properties can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(Properties)}.
	 * 
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @param arguments
	 *            Substitution for the property parameters.
	 * @return The value of the property corresponding to the given key.
	 */
	private String getProperty(String key, Object[] arguments) {
		String propertyValue = null;
		for (Properties propertiesHolder : properties) {
			final String property = propertiesHolder.getProperty(key);
			if (property != null) {
				propertyValue = MessageFormat.format(property, arguments);
				break;
			}
		}
		return propertyValue;
	}

	/**
	 * This will return the value of the property corresponding to the given key from the first properties
	 * holder of the given name. Precedence rules for the properties can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(Properties)}.
	 * 
	 * @param propertiesFileName
	 *            Name of the properties file in which we seek the given key.
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @return The value of the property corresponding to the given key.
	 */
	private String getProperty(String propertiesFileName, String key) {
		String propertyValue = null;
		for (Properties propertiesHolder : properties) {
			String soughtPropertiesFile = propertiesFileName;
			String propertiesExtension = ".properties"; //$NON-NLS-1$
			if (!propertiesFileName.endsWith(propertiesExtension)) {
				soughtPropertiesFile += propertiesExtension;
			}
			String fileName = propertiesHolder.getProperty(IAcceleoConstants.PROPERTY_KEY_FILE_NAME);
			if (soughtPropertiesFile.equals(fileName)) {
				final String property = propertiesHolder.getProperty(key);
				if (property != null) {
					/*
					 * Pass through MessageFormat so that we're consistent in the handling of special chars
					 * such as the apostrophe.
					 */
					propertyValue = MessageFormat.format(property, new Object[] {});
					break;
				}
			}
		}
		return propertyValue;
	}

	/**
	 * This will return the value of the property corresponding to the given key from the first properties
	 * holder of the given name, with parameters substituted as needed. Precedence rules for the properties
	 * can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(Properties)}.
	 * 
	 * @param propertiesFileName
	 *            Name of the properties file in which we seek the given key.
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @param arguments
	 *            Substitution for the property parameters.
	 * @return The value of the property corresponding to the given key.
	 */
	private String getProperty(String propertiesFileName, String key, Object[] arguments) {
		String propertyValue = null;
		for (Properties propertiesHolder : properties) {
			String soughtPropertiesFile = propertiesFileName;
			String propertiesExtension = ".properties"; //$NON-NLS-1$
			if (!propertiesFileName.endsWith(propertiesExtension)) {
				soughtPropertiesFile += propertiesExtension;
			}
			String fileName = propertiesHolder.getProperty(IAcceleoConstants.PROPERTY_KEY_FILE_NAME);
			if (soughtPropertiesFile.equals(fileName)) {
				final String property = propertiesHolder.getProperty(key);
				if (property != null) {
					propertyValue = MessageFormat.format(property, arguments);
					break;
				}
			}
		}
		return propertyValue;
	}

	/**
	 * Handles the invocation of a service.
	 * 
	 * @param moduleURI
	 *            URI of the module which is currently being evaluated.
	 * @param source
	 *            Receiver of the invocation. It will be passed as the first argument of the service
	 *            invocation.
	 * @param args
	 *            Arguments of the invocation. May not contain the receiver, in which case it will be set as
	 *            the first argument.
	 * @return Result of the invocation.
	 */
	@SuppressWarnings("unchecked")
	private Object invoke(URI moduleURI, Object source, Object[] args) {
		Object result = null;
		final Object serviceInstance = AcceleoServicesRegistry.INSTANCE
				.addService(moduleURI, (String)args[0]);
		if (serviceInstance == null) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.ClassNotFound", args[0], moduleURI.lastSegment())); //$NON-NLS-1$
		}
		final Class<?> serviceClass = serviceInstance.getClass();
		final String methodSignature = (String)args[1];
		final Method method;
		try {
			final int openParenthesisIndex = methodSignature.indexOf('(');
			if (openParenthesisIndex != -1) {
				final String methodName = methodSignature.substring(0, openParenthesisIndex);
				final int closeParenthesisIndex = methodSignature.indexOf(')');
				if (closeParenthesisIndex - openParenthesisIndex > 1) {
					final String parameterTypesString = methodSignature.substring(openParenthesisIndex + 1,
							closeParenthesisIndex);
					final List<Class<?>> parameterTypes = new ArrayList<Class<?>>();
					int nextCommaIndex = parameterTypesString.indexOf(',');
					int previousComma = 0;
					while (nextCommaIndex != -1) {
						final String parameterType = parameterTypesString.substring(previousComma,
								nextCommaIndex).trim();
						if (serviceInstance.getClass().getClassLoader() != null) {
							parameterTypes.add(serviceInstance.getClass().getClassLoader().loadClass(
									parameterType));
						} else {
							parameterTypes.add(Class.forName(parameterType));
						}
						previousComma = nextCommaIndex + 1;
						nextCommaIndex = parameterTypesString.indexOf(nextCommaIndex, ',');
					}
					/*
					 * The last (or only) parameter type is not followed by a comma and not handled in the
					 * while
					 */
					final String parameterType = parameterTypesString.substring(previousComma,
							parameterTypesString.length()).trim();
					if (serviceInstance.getClass().getClassLoader() != null) {
						parameterTypes.add(serviceInstance.getClass().getClassLoader().loadClass(
								parameterType));
					} else {
						parameterTypes.add(Class.forName(parameterType));
					}
					method = serviceClass.getMethod(methodName, parameterTypes
							.toArray(new Class[parameterTypes.size()]));
				} else {
					method = serviceClass.getMethod(methodName);
				}
			} else {
				method = serviceClass.getMethod(methodSignature);
			}
			// method cannot be null at this point. getMetod has thrown an exception in such cases
			assert method != null;
			final List<Object> invocationArguments = (List<Object>)args[2];
			if (method.getParameterTypes().length == 0) {
				if (invocationArguments.size() == 0) {
					result = method.invoke(source);
				} else {
					result = method.invoke(invocationArguments.get(0));
				}
			} else {
				if (method.getParameterTypes().length - invocationArguments.size() == 1) {
					invocationArguments.add(0, source);
				}
				if (method.getParameterTypes().length - invocationArguments.size() == -1) {
					final Object swappedSource = invocationArguments.remove(0);
					result = method.invoke(swappedSource, invocationArguments
							.toArray(new Object[invocationArguments.size()]));
				} else {
					result = method.invoke(serviceInstance, invocationArguments
							.toArray(new Object[invocationArguments.size()]));
				}
			}
		} catch (NoSuchMethodException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.NoSuchMethod", args[1], args[0]), e); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			throw new AcceleoEvaluationException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			/*
			 * Shouldn't happen. We retrieve methods through Class#getMethod() which only iterates over public
			 * members.
			 */
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.RestrictedMethod", args[1], args[0]), e); //$NON-NLS-1$
		} catch (InvocationTargetException e) {
			throw new AcceleoEvaluationException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.ParameterClassNotFound", args[1], args[0]), e); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * This will return true if all of the given String's characters are considered letters as per
	 * {@link Character#isLetter(char)}.
	 * 
	 * @param s
	 *            The String to consider.
	 * @return <code>true</code> if the String is composed of letters only, <code>false</code> otherwise.
	 */
	private boolean isAlpha(String s) {
		final char[] chars = s.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This will return true if all of the given String's characters are considered letters or digits as per
	 * {@link Character#isLetterOrDigit(char)}.
	 * 
	 * @param s
	 *            The String to consider.
	 * @return <code>true</code> if the String is composed of letters and digits only, <code>false</code>
	 *         otherwise.
	 */
	private boolean isAlphanumeric(String s) {
		final char[] chars = s.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
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
		final Set<Module> dynamicModules = new LinkedHashSet<Module>();
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
				resourceSet.setURIConverter(new DynamicModulesURIConverter());
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
		// Has module already been mapped?
		if (currentModules.contains(module)) {
			return;
		}
		currentModules.add(module);

		for (final ModuleElement elem : module.getOwnedModuleElement()) {
			if (elem instanceof Template) {
				Set<Template> namesakes = templates.get(elem.getName());
				if (namesakes == null) {
					namesakes = new LinkedHashSet<Template>();
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

		final Set<Module> unMappedRequiredModules = new LinkedHashSet<Module>();
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
						overriding = new LinkedHashSet<Template>();
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
						namesakes = new LinkedHashSet<Template>();
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
				overriding = new LinkedHashSet<Template>();
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
	 * Reorders the <code>candidates</code> list so that templates in the same module as <code>call</code>
	 * come first, then templates in extended modules, and finally templates in imported modules.
	 * 
	 * @param origin
	 *            The originating module.
	 * @param candidates
	 *            List that is to be reordered.
	 * @return The reordered list.
	 */
	private List<Template> reorderCandidatesPriority(Module origin, Set<Template> candidates) {
		final List<Template> reorderedList = new ArrayList<Template>(candidates.size());

		for (final Template candidate : new LinkedHashSet<Template>(candidates)) {
			if (candidate.eContainer() == origin) {
				reorderedList.add(candidate);
				candidates.remove(candidate);
			}
		}

		for (final Template candidate : new LinkedHashSet<Template>(candidates)) {
			for (final Module extended : origin.getExtends()) {
				if (candidate.eContainer() == extended) {
					reorderedList.add(candidate);
					candidates.remove(candidate);
				}
			}
		}

		for (final Template candidate : new LinkedHashSet<Template>(candidates)) {
			for (final Module imported : origin.getImports()) {
				if (candidate.eContainer() == imported) {
					reorderedList.add(candidate);
					candidates.remove(candidate);
				}
			}
		}

		return reorderedList;
	}

	/**
	 * Reorders tha given list of candidates by order of overriding. For example if the set contains T11
	 * overriding T1, T21 overriding T11, T31 overriding T11 and T12 overriding T1, The returned list will
	 * contain in this order : {T31, T12, T21, T11}.
	 * 
	 * @param candidates
	 *            Set of candidates that are to be reordered.
	 * @return The reordered list of candidates.
	 */
	private List<Template> reorderDynamicOverrides(Set<Template> candidates) {
		final List<Template> reorderedList = new ArrayList<Template>(candidates.size());

		final Set<Template> lowest = new LinkedHashSet<Template>(candidates);
		while (!lowest.isEmpty()) {
			for (final Template candidate : new LinkedHashSet<Template>(lowest)) {
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

	/**
	 * Returns a Sequence containing the full set of <code>source</code>'s siblings.
	 * 
	 * @param source
	 *            The EObject we seek the siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return Sequence containing the full set of the receiver's siblings.
	 */
	private Set<EObject> siblings(EObject source, EClassifier filter) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		EObject container = source.eContainer();
		for (EObject child : getContents(container)) {
			if (child != source && (filter == null || filter.isInstance(child))) {
				result.add(child);
			}
		}
		return result;
	}

	/**
	 * Implements the Acceleo Standard library "strtok(String, Integer)" operation. This will make use of the
	 * StringTokenizer class and cache the result in order to reuse it as needed.
	 * <p>
	 * <b>Note</b> that this will <b>not</b> fail if called with <code>flag</code> set to <code>1</code> when
	 * no tokens remains in the source String. As the behavior in such a case is not specified by the official
	 * Acceleo specification, we'll return the empty String instead.
	 * </p>
	 * 
	 * @param source
	 *            Source String in which tokenization has to take place.
	 * @param delimiters
	 *            Delimiters around which the <code>source</code> has to be split.
	 * @param flag
	 *            The value of this influences the token that needs be returned. A value of <code>0</code>
	 *            means the very first token will be returned, a value of <code>1</code> means the returned
	 *            token will be the next (as compared to the last one that's been returned).
	 * @return The first of all tokens if <code>flag</code> is <code>0</code>, the next token if
	 *         <code>flag</code> is <code>1</code>. Fails in {@link AcceleoEvaluationException} otherwise.
	 */
	private String strtok(String source, String delimiters, Integer flag) {
		// flag == 0, create a tokenizer, cache it then return its first element.
		/*
		 * flag == 1, create the tokenizer if none exists for this source, retrieve the existing one
		 * otherwise. Then returns the next token in it.
		 */
		if (flag.intValue() == 0) {
			final StringTokenizer tokenizer = new StringTokenizer(source, delimiters);
			tokenizers.put(source, tokenizer);
			return tokenizer.nextToken();
		} else if (flag.intValue() == 1) {
			StringTokenizer tokenizer = tokenizers.get(source);
			if (tokenizer == null) {
				tokenizer = new StringTokenizer(source, delimiters);
				tokenizers.put(source, tokenizer);
			}
			String token = ""; //$NON-NLS-1$
			if (tokenizer.hasMoreTokens()) {
				token = tokenizer.nextToken();
			}
			return token;
		}
		throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
				"AcceleoEvaluationEnvironment.IllegalTokenizerFlag", flag)); //$NON-NLS-1$
	}

	/**
	 * Implements the Acceleo standard library's "substitute(String, String)" and non-standard library's
	 * "substituteAll(String, String)" operation. It will replace either the first or all occurences of a
	 * given <code>substring</code> in the <code>source</code> by the <code>replacement</code>
	 * String.<b>Neither <code>substring</code> nor <code>replacement</code> are considered regular
	 * expressions.</b>
	 * 
	 * @param source
	 *            Source String in which the substitution has to take place.
	 * @param substring
	 *            Substring which is to be replaced.
	 * @param replacement
	 *            String that will be substituted to the sought one.
	 * @param substituteAll
	 *            Indicates wheter we should substitute all occurences of the substring or only the first.
	 * @return <code>source</code> with substitution executed, <code>source</code> itself if the substring
	 *         hasn't been found.
	 */
	private String substitute(String source, String substring, String replacement, boolean substituteAll) {
		if (substring == null || replacement == null) {
			throw new NullPointerException();
		}
		// substitute replaces Strings, not regexes.
		// Surrounding the regex with \Q [...] \E allows just that
		final String regex = "\\Q" + substring + "\\E"; //$NON-NLS-1$ //$NON-NLS-2$
		// We also need to escape backslashes and dollar signs in the replacement (scary!)
		final String replacementValue = replacement.replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"\\\\\\$"); //$NON-NLS-1$

		if (substituteAll) {
			return source.replaceAll(regex, replacementValue);
		}
		return source.replaceFirst(regex, replacementValue);
	}

	/**
	 * Implements the "tokenize" operation on String type. This will return a sequence containing the tokens
	 * of the given string (using <code>delim</code> as delimiter).
	 * 
	 * @param source
	 *            Source String that is to be tokenized.
	 * @param delim
	 *            The delimiters around which the <code>source</code> is to be split.
	 * @return A sequence containing the tokens of the given.
	 */
	private List<String> tokenize(String source, String delim) {
		final StringTokenizer tokenizer = new StringTokenizer(source, delim);
		List<String> result = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		return result;
	}

	/**
	 * Collections need special handling when generated from Acceleo.
	 * 
	 * @param object
	 *            The object we wish the String representation of.
	 * @return String representation of the given Object. For Collections, this will be the concatenation of
	 *         all contained Objects' toString.
	 */
	private String toString(Object object) {
		final StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				buffer.append(toString(childrenIterator.next()));
			}
		} else {
			buffer.append(object.toString());
		}
		return buffer.toString();
	}

	/**
	 * This will allow us to handle references to file scheme URIs within the resource set containing the
	 * generation modules and their dynamic overrides. We need this since the dynamic overrides are loaded
	 * with file scheme URIs whereas the generation module can be loaded through platform scheme URIs and we
	 * need references to be resolved anyway.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private final class DynamicModulesURIConverter extends ExtensibleURIConverterImpl {
		/**
		 * Enhances visibility of the default constructor.
		 */
		public DynamicModulesURIConverter() {
			// Enhances visibility
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see ExtensibleURIConverterImpl#normalize(URI)
		 */
		@Override
		public URI normalize(URI uri) {
			if (!IAcceleoConstants.EMTL_FILE_EXTENSION.equals(uri.fileExtension())
					|| !"file".equals(uri.scheme())) { //$NON-NLS-1$
				return super.normalize(uri);
			}
			URI normalized = getURIMap().get(uri);
			if (normalized == null) {
				String moduleName = uri.lastSegment();
				moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
				Set<URI> candidateURIs = new LinkedHashSet<URI>();

				// Search matching module in the current generation context
				Set<Module> candidateModules = searchCurrentModuleForCandidateMatches(moduleName);
				for (Module candidateModule : candidateModules) {
					candidateURIs.add(candidateModule.eResource().getURI());
				}
				// If there were no matching module, search in their ResourceSet(s)
				if (candidateURIs.size() == 0) {
					candidateURIs.addAll(searchResourceSetForMatches(moduleName));
				}
				if (candidateURIs.size() == 1) {
					normalized = candidateURIs.iterator().next();
				} else if (candidateURIs.size() > 0) {
					normalized = findBestMatchFor(uri, candidateURIs);
				}
				// There is a chance that our match should itself be normalized
				if ((normalized == null || "file".equals(normalized.scheme())) //$NON-NLS-1$
						&& EMFPlugin.IS_ECLIPSE_RUNNING) {
					String uriToString = uri.toString();
					if (uriToString.indexOf('#') > 0) {
						uriToString = uriToString.substring(0, uriToString.indexOf('#'));
					}
					String resolvedPath = AcceleoWorkspaceUtil.INSTANCE
							.resolveAsPlatformPluginResource(uriToString);
					if (resolvedPath != null) {
						normalized = URI.createURI(resolvedPath);
					}
				}
				if (normalized == null) {
					normalized = super.normalize(uri);
				}
				if (!uri.equals(normalized)) {
					getURIMap().put(uri, normalized);
				}
			}
			return normalized;
		}

		/**
		 * Returns the normalized form of the URI, using the given multiple candidates (this means that more
		 * than 2 modules had a matching name).
		 * 
		 * @param uri
		 *            The URI that is to be normalized.
		 * @param candidateURIs
		 *            URIs of the modules that can potentially be a match for <code>uri</code>.
		 * @return the normalized form
		 */
		private URI findBestMatchFor(URI uri, Set<URI> candidateURIs) {
			URI normalized = null;
			final Iterator<URI> candidatesIterator = candidateURIs.iterator();
			final List<String> referenceSegments = Arrays.asList(uri.segments());
			Collections.reverse(referenceSegments);
			int highestEqualFragments = 0;
			while (candidatesIterator.hasNext()) {
				final URI next = candidatesIterator.next();
				int equalFragments = 0;
				final List<String> candidateSegments = Arrays.asList(next.segments());
				Collections.reverse(candidateSegments);
				for (int i = 0; i < Math.min(candidateSegments.size(), referenceSegments.size()); i++) {
					if (candidateSegments.get(i) == referenceSegments.get(i)) {
						equalFragments++;
					} else {
						break;
					}
				}
				if (equalFragments > highestEqualFragments) {
					highestEqualFragments = equalFragments;
					normalized = next;
				}
			}
			return normalized;
		}

		/**
		 * This will search the current generation context for a loaded module matching the given
		 * <code>moduleName</code>.
		 * 
		 * @param moduleName
		 *            Name of the module we seek.
		 * @return The Set of all modules currently loaded for generation going by the name
		 *         <code>moduleName</code>.
		 */
		private Set<Module> searchCurrentModuleForCandidateMatches(String moduleName) {
			Set<Module> candidates = new LinkedHashSet<Module>();
			for (Module module : currentModules) {
				if (moduleName.equals(module.getName())) {
					candidates.add(module);
				}
			}
			return candidates;
		}

		/**
		 * This will search throughout the resourceSet(s) containing the loaded modules for modules going by
		 * name <code>moduleName</code>.
		 * 
		 * @param moduleName
		 *            Name of the module we seek.
		 * @return The Set of all modules loaded within the generation ResourceSet going by the name
		 *         <code>moduleName</code>.
		 */
		private Set<URI> searchResourceSetForMatches(String moduleName) {
			final Set<URI> candidates = new LinkedHashSet<URI>();
			final List<ResourceSet> resourceSets = new ArrayList<ResourceSet>();
			for (Module module : currentModules) {
				final ResourceSet resourceSet = module.eResource().getResourceSet();
				if (!resourceSets.contains(resourceSet)) {
					resourceSets.add(resourceSet);
				}
			}
			for (ResourceSet resourceSet : resourceSets) {
				for (Resource resource : resourceSet.getResources()) {
					if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(resource.getURI().fileExtension())) {
						String candidateName = resource.getURI().lastSegment();
						candidateName = candidateName.substring(0, candidateName.lastIndexOf('.'));
						if (moduleName.equals(candidateName)) {
							candidates.add(resource.getURI());
						}
					}
				}
			}
			return candidates;
		}
	}
}
