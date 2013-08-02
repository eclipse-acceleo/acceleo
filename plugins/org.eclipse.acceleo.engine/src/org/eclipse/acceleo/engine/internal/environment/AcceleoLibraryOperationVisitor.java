/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Christian W. Damus - Bug 414214 siblings() of resource roots
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.environment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.common.AcceleoServicesRegistry;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.common.utils.IAcceleoCrossReferenceProvider;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eclipse.ocl.util.Bag;
import org.eclipse.ocl.util.CollectionUtil;

/**
 * The purpose of this Utility class is to allow execution of Standard and non standard Acceleo operations.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public final class AcceleoLibraryOperationVisitor {
	/** This will be used as a place holder so that library operations call can return null. */
	private static final Object OPERATION_CALL_FAILED = new Object();

	/** This will hold mapping from primitive types names to their Class instance. */
	private static final Map<String, Class<?>> PRIMITIVE_TYPES;

	/** Holds the prefix we'll use for the temporary context variables created to hold context values. */
	private static final String TEMPORARY_CONTEXT_VAR_PREFIX = "context$"; //$NON-NLS-1$

	/**
	 * Maps a source String to its StringTokenizer. Needed for the implementation of the standard operation
	 * "strtok(String, Integer)" as currently specified.
	 */
	private static final Map<String, StringTokenizer> TOKENIZERS = new HashMap<String, StringTokenizer>();

	/**
	 * Keeps track of the cross referencer that's been created for this evaluation, if any. This is used and
	 * will be instantiated by the eInverse() non standard operation.
	 */
	private static CrossReferencer referencer;

	static {
		PRIMITIVE_TYPES = new HashMap<String, Class<?>>();
		PRIMITIVE_TYPES.put("boolean", boolean.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("byte", byte.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("char", char.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("short", short.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("int", int.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("long", long.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("float", float.class); //$NON-NLS-1$
		PRIMITIVE_TYPES.put("double", double.class); //$NON-NLS-1$
	}

	/** Utility classes don't need to (and shouldn't be) instantiated. */
	private AcceleoLibraryOperationVisitor() {
		// Hides default constructor
	}

	/**
	 * The environment will delegate operation calls to this method if it needs to evaluate non-standard
	 * Acceleo operations.
	 * 
	 * @param env
	 *            The environment that calls for this evaluation.
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	@SuppressWarnings("unchecked")
	public static Object callNonStandardOperation(AcceleoEvaluationEnvironment env, EOperation operation,
			Object source, Object... args) {
		Object result = OPERATION_CALL_FAILED;
		final String operationName = operation.getName();
		if (AcceleoNonStandardLibrary.OPERATION_OCLANY_PLUS.equals(operationName)) {
			// We'll only be here for two operations : OclAny::+(String) and String::+(OclAny)
			assert source instanceof String || args[0] instanceof String;
			result = toString(source) + toString(args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_TOSTRING.equals(operationName)) {
			result = toString(source);
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_INVOKE.equals(operationName)) {
			if (args.length == 3) {
				URI uri = operation.eResource().getURI();
				uri = URI.createURI(URI.decode(uri.toString()));
				result = invoke(uri, source, args);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_CURRENT.equals(operationName)) {
			if (args.length == 1) {
				result = getContext(env, args);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_GETPROPERTY.equals(operationName)) {
			if (args.length == 1) {
				result = getProperty(env, (String)args[0]);
			} else if (args.length == 2 && args[1] instanceof String) {
				result = getProperty(env, (String)args[0], (String)args[1]);
			} else if (args.length == 2) {
				result = getProperty(env, (String)args[0], ((List<Object>)args[1]).toArray());
			} else if (args.length == 3) {
				result = getProperty(env, (String)args[0], (String)args[1], ((List<Object>)args[2]).toArray());
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_LINE_SEPARATOR.equals(operationName)) {
			if (EMFPlugin.IS_ECLIPSE_RUNNING) {
				result = AcceleoPreferences.getLineSeparator();
			} else {
				result = System.getProperty("line.separator"); //$NON-NLS-1$
			}
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
	 * The environment will delegate operation calls to this method if it needs to evaluate a standard Acceleo
	 * operation.
	 * 
	 * @param env
	 *            The environment that calls for this evaluation.
	 * @param operation
	 *            Operation which is to be evaluated.
	 * @param source
	 *            Source on which the operations is evaluated.
	 * @param args
	 *            Arguments of the call.
	 * @return Result of the operation call.
	 */
	public static Object callStandardOperation(AcceleoEvaluationEnvironment env, EOperation operation,
			Object source, Object... args) {
		Object result = OPERATION_CALL_FAILED;
		// Specifications of each standard operation can be found as comments of
		// AcceleoStandardLibrary#OPERATION_*.
		if (source instanceof String) {
			final String sourceValue = (String)source;

			if (AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE.equals(operation.getName())) {
				result = substitute(sourceValue, (String)args[0], (String)args[1], false);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_INDEX.equals(operation.getName())) {
				// Increment java index value by 1 for OCL
				result = Integer.valueOf(sourceValue.indexOf((String)args[0]) + 1);
				if (result.equals(Integer.valueOf(0))) {
					result = Integer.valueOf(-1);
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_FIRST.equals(operation.getName())) {
				int endIndex = ((Integer)args[0]).intValue();
				if (endIndex < 0) {
					result = env.getInvalidResult();
				} else if (endIndex > sourceValue.length()) {
					result = sourceValue;
				} else {
					result = sourceValue.substring(0, endIndex);
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_LAST.equals(operation.getName())) {
				int charCount = ((Integer)args[0]).intValue();
				if (charCount < 0) {
					result = env.getInvalidResult();
				} else if (charCount > sourceValue.length()) {
					result = sourceValue;
				} else {
					result = sourceValue.substring(sourceValue.length() - charCount, sourceValue.length());
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_STRSTR.equals(operation.getName())) {
				result = Boolean.valueOf(sourceValue.contains((String)args[0]));
			} else if (AcceleoStandardLibrary.OPERATION_STRING_STRTOK.equals(operation.getName())) {
				result = strtok(sourceValue, (String)args[0], (Integer)args[1]);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_STRCMP.equals(operation.getName())) {
				result = Integer.valueOf(sourceValue.compareTo((String)args[0]));
			} else if (AcceleoStandardLibrary.OPERATION_STRING_ISALPHA.equals(operation.getName())) {
				result = Boolean.valueOf(isAlpha(sourceValue));
			} else if (AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM.equals(operation.getName())) {
				result = Boolean.valueOf(isAlphanumeric(sourceValue));
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
	 * Clears all state that could have been memorized by this utility.
	 */
	public static void dispose() {
		TOKENIZERS.clear();
		if (referencer != null) {
			referencer.clear();
			referencer = null;
		}
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
	private static Object callNonStandardCollectionOperation(EOperation operation, Collection<?> source,
			Object... args) {
		Object result = OPERATION_CALL_FAILED;
		final String operationName = operation.getName();

		if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_SEP.equals(operationName)) {
			result = sep(source, args);
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_FILTER.equals(operationName)) {
			final Collection<Object> temp;

			// Determine return type
			if (source instanceof Bag) {
				temp = CollectionUtil.createNewBag();
			} else if (source instanceof HashSet && !(source instanceof LinkedHashSet)) {
				temp = CollectionUtil.createNewSet();
			} else if (source instanceof Set) {
				temp = CollectionUtil.createNewOrderedSet();
			} else {
				temp = CollectionUtil.createNewSequence();
			}

			final Iterator<?> sourceIterator = source.iterator();
			while (sourceIterator.hasNext()) {
				final Object next = sourceIterator.next();
				if (((EClassifier)args[0]).isInstance(next)) {
					temp.add(next);
				}
			}
			result = temp;
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_REVERSE.equals(operationName)) {
			final List<Object> temp = new ArrayList<Object>(source);
			Collections.reverse(temp);
			if (source instanceof LinkedHashSet<?>) {
				final Set<Object> reversedSet = new CompactLinkedHashSet<Object>(temp);
				result = reversedSet;
			} else {
				result = temp;
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_LASTINDEXOF.equals(operationName)) {
			final List<Object> temp = new ArrayList<Object>(source);
			result = Integer.valueOf(temp.lastIndexOf(args[0]) + 1);
			if (result.equals(Integer.valueOf(0))) {
				result = Integer.valueOf(-1);
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_ADD_ALL.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Collection<?>) {
				result = addAll(source, (Collection<?>)args[0]);
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_REMOVE_ALL.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Collection<?>) {
				result = removeAll(source, (Collection<?>)args[0]);
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_DROP.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Integer) {
				final List<Object> temp = new ArrayList<Object>(source);
				int index = ((Integer)args[0]).intValue();
				if (index <= temp.size()) {
					result = temp.subList(index, temp.size());
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_DROP_RIGHT.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Integer) {
				final List<Object> temp = new ArrayList<Object>(source);
				int index = ((Integer)args[0]).intValue();
				if (index <= temp.size()) {
					result = temp.subList(0, temp.size() - index);
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_STARTS_WITH.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Collection<?>) {
				final List<Object> temp = new ArrayList<Object>(source);
				List<Object> arg = new ArrayList<Object>((Collection<?>)args[0]);

				result = Boolean.FALSE;
				if (temp.size() >= arg.size()) {
					List<Object> subTemp = temp.subList(0, arg.size());
					result = Boolean.valueOf(subTemp.equals(arg));
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_ENDS_WITH.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Collection<?>) {
				final List<Object> temp = new ArrayList<Object>(source);
				List<Object> arg = new ArrayList<Object>((Collection<?>)args[0]);

				result = Boolean.FALSE;
				if (temp.size() >= arg.size()) {
					List<Object> subTemp = temp.subList(temp.size() - arg.size(), temp.size());
					result = Boolean.valueOf(subTemp.equals(arg));
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_INDEX_OF_SLICE.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Collection<?>) {
				final List<Object> temp = new ArrayList<Object>(source);
				List<Object> arg = new ArrayList<Object>((Collection<?>)args[0]);
				int indexOfSubList = Collections.indexOfSubList(temp, arg);
				result = Integer.valueOf(indexOfSubList + 1);
				if (result.equals(Integer.valueOf(0))) {
					result = Integer.valueOf(-1);
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_COLLECTION_LAST_INDEX_OF_SLICE.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof Collection<?>) {
				final List<Object> temp = new ArrayList<Object>(source);
				List<Object> arg = new ArrayList<Object>((Collection<?>)args[0]);
				int indexOfSubList = Collections.lastIndexOfSubList(temp, arg);
				result = Integer.valueOf(indexOfSubList + 1);
				if (result.equals(Integer.valueOf(0))) {
					result = Integer.valueOf(-1);
				}
			}
		}

		return result;
	}

	/**
	 * Execute the operation sep('separator') or separator('prefix', 'separator', 'suffix') on the given
	 * source.
	 * 
	 * @param source
	 *            The source collection
	 * @param args
	 *            The arguments [separator] or [prefix, separator, suffix]
	 * @return The source collection separated.
	 */
	private static Object sep(Collection<?> source, Object[] args) {
		Object result = OPERATION_CALL_FAILED;
		if (args.length == 1) {
			final Collection<Object> temp = new ArrayList<Object>(source.size() << 1);
			final Iterator<?> sourceIterator = source.iterator();
			while (sourceIterator.hasNext()) {
				temp.add(sourceIterator.next());
				if (sourceIterator.hasNext()) {
					temp.add(args[0]);
				}
			}
			result = temp;
		} else if (args.length == 3) {
			final Collection<Object> temp = new ArrayList<Object>(source.size() << 1);
			temp.add(args[0]);
			final Iterator<?> sourceIterator = source.iterator();
			while (sourceIterator.hasNext()) {
				temp.add(sourceIterator.next());
				if (sourceIterator.hasNext()) {
					temp.add(args[1]);
				}
			}
			temp.add(args[2]);
			result = temp;
		}
		return result;
	}

	/**
	 * Returns the source collection on which all the elements of the arg collection have been added.
	 * 
	 * @param source
	 *            The source collection
	 * @param arg
	 *            The collection to add
	 * @return The source collection on which all the elements of the arg collection have been added.
	 */
	private static Object addAll(Collection<?> source, Collection<?> arg) {
		Object result = OPERATION_CALL_FAILED;
		if (source instanceof LinkedHashSet) {
			final LinkedHashSet<Object> temp = new LinkedHashSet<Object>(source);
			temp.addAll(arg);
			result = temp;
		} else if (source instanceof Set) {
			final HashSet<Object> temp = new HashSet<Object>(source);
			temp.addAll(arg);
			result = temp;
		} else if (source instanceof List) {
			final List<Object> temp = new ArrayList<Object>(source);
			temp.addAll(arg);
			result = temp;
		} else if (source instanceof Bag) {
			Bag<Object> temp = CollectionUtil.createNewBag(source);
			temp.addAll(arg);
			result = temp;
		}
		return result;
	}

	/**
	 * Returns the source collection on which all the elements of the arg collection have been removed.
	 * 
	 * @param source
	 *            The source collection.
	 * @param arg
	 *            The collection to remove.
	 * @return The source collection on which all the elements of the arg collection have been removed.
	 */
	private static Object removeAll(Collection<?> source, Collection<?> arg) {
		Object result = OPERATION_CALL_FAILED;
		if (source instanceof LinkedHashSet) {
			final LinkedHashSet<Object> temp = new LinkedHashSet<Object>(source);
			temp.removeAll(arg);
			result = temp;
		} else if (source instanceof Set) {
			final HashSet<Object> temp = new HashSet<Object>(source);
			temp.removeAll(arg);
			result = temp;
		} else if (source instanceof List) {
			final List<Object> temp = new ArrayList<Object>(source);
			temp.removeAll(arg);
			result = temp;
		} else if (source instanceof Bag) {
			final List<Object> temp = new ArrayList<Object>(source);
			temp.removeAll(arg);
			Bag<Object> bag = CollectionUtil.createNewBag(temp);
			result = bag;
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
	private static Object callNonStandardEObjectOperation(EOperation operation, EObject source,
			Object... args) {
		Object result = OPERATION_CALL_FAILED;
		final String operationName = operation.getName();

		if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_EALLCONTENTS.equals(operationName)) {
			if (args.length == 0) {
				result = eAllContents(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = eAllContents(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_ANCESTORS.equals(operationName)) {
			if (args.length == 0) {
				result = ancestors(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = ancestors(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_SIBLINGS.equals(operationName)) {
			if (args.length == 0) {
				result = siblings(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = siblings(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_PRECEDINGSIBLINGS.equals(operationName)) {
			if (args.length == 0) {
				result = siblings(source, null, true);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = siblings(source, (EClassifier)args[0], true);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_FOLLOWINGSIBLINGS.equals(operationName)) {
			if (args.length == 0) {
				result = siblings(source, null, false);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = siblings(source, (EClassifier)args[0], false);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_EINVERSE.equals(operationName)) {
			if (args.length == 0) {
				result = eInverse(source, null);
			} else if (args.length == 1 && args[0] instanceof EClassifier) {
				result = eInverse(source, (EClassifier)args[0]);
			}
			// fall through : let else fail in UnsupportedOperationException
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_EGET.equals(operationName)) {
			result = eGet(source, (String)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_ECONTAINER.equals(operationName)) {
			result = eContainer(source, (EClassifier)args[0]);
		} else if (AcceleoNonStandardLibrary.OPERATION_EOBJECT_ECONTENTS.equals(operationName)) {
			result = eContents(source, (EClassifier)args[0]);
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
	private static Object callNonStandardStringOperation(EOperation operation, String source, Object... args) {
		Object result = OPERATION_CALL_FAILED;
		final String operationName = operation.getName();

		/*
		 * Note that because of OCL limitations, String::+(OclAny) will be handled before we even arrive here.
		 * See #callOperation().
		 */
		if (AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL.equals(operationName)) {
			result = substitute(source, (String)args[0], (String)args[1], true);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE.equals(operationName)) {
			result = source.replaceFirst((String)args[0], (String)args[1]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL.equals(operationName)) {
			result = source.replaceAll((String)args[0], (String)args[1]);
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_ENDSWITH.equals(operationName)) {
			result = Boolean.valueOf(source.endsWith((String)args[0]));
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_EQUALSIGNORECASE.equals(operationName)) {
			result = Boolean.valueOf(source.equalsIgnoreCase((String)args[0]));
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_STARTSWITH.equals(operationName)) {
			result = Boolean.valueOf(source.startsWith((String)args[0]));
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TRIM.equals(operationName)) {
			result = source.trim();
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE.equals(operationName)) {
			if (args.length == 1) {
				result = tokenize(source, (String)args[0]);
			} else if (args.length == 0) {
				result = tokenize(source);
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_CONTAINS.equals(operationName)) {
			result = Boolean.valueOf(source.contains((String)args[0]));
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_MATCHES.equals(operationName)) {
			result = Boolean.valueOf(source.matches((String)args[0]));
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_LASTINDEX.equals(operationName)) {
			if (args.length == 1) {
				// Increment java index value by 1 for OCL
				result = Integer.valueOf(source.lastIndexOf((String)args[0]) + 1);
				if (result.equals(Integer.valueOf(0))) {
					result = Integer.valueOf(-1);
				}
			} else if (args.length == 2) {
				// Increment java index value by 1 for OCL
				result = Integer
						.valueOf(source.lastIndexOf((String)args[0], ((Integer)args[1]).intValue()) + 1);
				if (result.equals(Integer.valueOf(0))) {
					result = Integer.valueOf(-1);
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTRING.equals(operationName)) {
			try {
				result = source.substring(((Integer)args[0]).intValue() - 1);
			} catch (IndexOutOfBoundsException e) {
				AcceleoEnginePlugin.log(new AcceleoEvaluationException(AcceleoEngineMessages.getString(
						"AcceleoLibraryOperationVisitor.IndexOutOfBoundsSubstring", source, args[0]), e), //$NON-NLS-1$
						true);
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_INDEX.equals(operationName)) {
			// If we are here, it should be for index(String, Integer) not for index(String)
			if (args.length == 2) {
				// Increment java index value by 1 for OCL
				result = Integer.valueOf(source.indexOf((String)args[0], ((Integer)args[1]).intValue()) + 1);
				if (result.equals(Integer.valueOf(0))) {
					result = Integer.valueOf(-1);
				}
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE_LINE.equals(operationName)) {
			final String dos = "\r\n"; //$NON-NLS-1$
			final String unix = "\n"; //$NON-NLS-1$
			final String macOsClassic = "\r"; //$NON-NLS-1$

			if (source.contains(dos)) {
				result = tokenize(source, dos);
			} else if (source.contains(unix)) {
				result = tokenize(source, unix);
			} else if (source.contains(macOsClassic)) {
				result = tokenize(source, macOsClassic);
			} else {
				// One line only
				List<String> temp = new ArrayList<String>();
				temp.add(source);
				result = temp;
			}
		} else if (AcceleoNonStandardLibrary.OPERATION_STRING_PREFIX.equals(operationName)) {
			if (args.length == 1 && args[0] instanceof String) {
				String prefix = (String)args[0];
				result = prefix + source;
			}
		}

		return result;
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
	private static List<EObject> ancestors(EObject source, EClassifier filter) {
		final List<EObject> result = new ArrayList<EObject>();
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
	 * Iterates over the content of the given EObject and returns the elements of type <code>filter</code>
	 * from its content tree as a list.
	 * 
	 * @param source
	 *            The EObject we seek the content tree of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return The given EObject's whole content tree as a list.
	 */
	private static List<EObject> eAllContents(EObject source, EClassifier filter) {
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
	private static Object eContainer(EObject source, EClassifier filter) {
		EObject container = source.eContainer();
		while (container != null && !filter.isInstance(container)) {
			container = container.eContainer();
		}
		return container;
	}

	/**
	 * Iterates over the direct children of the given EObject and returns the elements of type
	 * <code>filter</code> as a list.
	 * 
	 * @param source
	 *            The EObject we seek the content of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @return The given EObject's children of type <em>filter</em> as a list.
	 */
	private static List<EObject> eContents(EObject source, EClassifier filter) {
		final Iterator<EObject> contentIterator = source.eContents().iterator();
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
	 * Handles calls to the non standard operation "eGet". This will fetch the value of the feature named
	 * <em>featureName</em> on <em>source</em>.
	 * 
	 * @param source
	 *            The EObject we seek to retrieve a feature value of.
	 * @param featureName
	 *            Name of the feature which value we need to retrieve.
	 * @return Value of the given feature on the given object.
	 */
	private static Object eGet(EObject source, String featureName) {
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
	private static Set<EObject> eInverse(EObject target, EClassifier filter) {
		final Set<EObject> result = new CompactLinkedHashSet<EObject>();

		final IAcceleoCrossReferenceProvider crossReferenceProvider = getCrossReferencerAdapter(target);
		if (crossReferenceProvider != null) {
			if (filter != null) {
				result.addAll(crossReferenceProvider.getInverseReferences(target, filter));
			} else {
				result.addAll(crossReferenceProvider.getInverseReferences(target));
			}
		} else {
			if (referencer == null) {
				createEInverseCrossreferencer(target);
			}
			final Collection<EStructuralFeature.Setting> settings = referencer.get(target);
			if (settings == null) {
				return Collections.emptySet();
			}
			for (EStructuralFeature.Setting setting : settings) {
				if (filter == null || filter.isInstance(setting.getEObject())) {
					result.add(setting.getEObject());
				}
			}
		}

		return result;
	}

	/**
	 * This will try and find the given method on the given service Class.
	 * 
	 * @param serviceClass
	 *            The class on which to search for the given method.
	 * @param methodSignature
	 *            The signature of the method we are to search for.
	 * @return The method if any. Will throw exceptions in other cases.
	 * @throws NoSuchMethodException
	 *             Thrown if the method cannot be found.
	 * @throws ClassNotFoundException
	 *             Thrown if we cannot find one of the parameters' class.
	 */
	private static Method findInvokeMethod(Class<?> serviceClass, String methodSignature)
			throws NoSuchMethodException, ClassNotFoundException {
		final int openParenthesisIndex = methodSignature.indexOf('(');
		Method method = null;
		if (openParenthesisIndex == -1) {
			method = serviceClass.getMethod(methodSignature);
		} else {
			final String methodName = methodSignature.substring(0, openParenthesisIndex);
			final int closeParenthesisIndex = methodSignature.indexOf(')');
			if (closeParenthesisIndex - openParenthesisIndex <= 1) {
				method = serviceClass.getMethod(methodName);
			} else {
				final String parameterTypesString = methodSignature.substring(openParenthesisIndex + 1,
						closeParenthesisIndex);
				final List<Class<?>> parameterTypes = new ArrayList<Class<?>>();
				int nextCommaIndex = parameterTypesString.indexOf(',');
				int previousComma = 0;
				while (nextCommaIndex != -1) {
					String parameterType = parameterTypesString.substring(previousComma, nextCommaIndex)
							.trim();
					// Only retain the erasure
					int parameterIndex = parameterType.indexOf('<');
					if (parameterIndex != -1) {
						parameterType = parameterType.substring(parameterIndex + 1, parameterType
								.indexOf('>'));
					}
					if (PRIMITIVE_TYPES.containsKey(parameterType)) {
						parameterTypes.add(PRIMITIVE_TYPES.get(parameterType));
					} else if (serviceClass.getClassLoader() != null) {
						parameterTypes.add(serviceClass.getClassLoader().loadClass(parameterType));
					} else {
						parameterTypes.add(Class.forName(parameterType));
					}
					previousComma = nextCommaIndex + 1;
					nextCommaIndex = parameterTypesString.indexOf(',', previousComma);
				}
				/*
				 * The last (or only) parameter type is not followed by a comma and not handled in the while
				 */
				String parameterType = parameterTypesString.substring(previousComma,
						parameterTypesString.length()).trim();
				// Only retain the erasure
				int parameterIndex = parameterType.indexOf('<');
				if (parameterIndex != -1) {
					parameterType = parameterType.substring(parameterIndex + 1, parameterType.indexOf('>'));
				}
				if (PRIMITIVE_TYPES.containsKey(parameterType)) {
					parameterTypes.add(PRIMITIVE_TYPES.get(parameterType));
				} else if (serviceClass.getClassLoader() != null) {
					parameterTypes.add(serviceClass.getClassLoader().loadClass(parameterType));
				} else {
					parameterTypes.add(Class.forName(parameterType));
				}
				method = serviceClass.getMethod(methodName, parameterTypes.toArray(new Class[parameterTypes
						.size()]));
			}
		}
		return method;
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
	private static void createEInverseCrossreferencer(EObject target) {
		Resource res = null;
		ResourceSet rs = null;
		if (target.eResource() != null) {
			res = target.eResource();
		}
		if (res != null && res.getResourceSet() != null) {
			rs = res.getResourceSet();
		}

		if (rs != null) {
			// Manually add the ecore.ecore resource in the list of cross referenced notifiers
			final Resource ecoreResource = EcorePackage.eINSTANCE.getEClass().eResource();
			final Collection<Notifier> notifiers = new ArrayList<Notifier>();
			for (Resource crossReferenceResource : rs.getResources()) {
				if (!IAcceleoConstants.EMTL_FILE_EXTENSION.equals(crossReferenceResource.getURI()
						.fileExtension())) {
					notifiers.add(crossReferenceResource);
				}
			}
			notifiers.add(ecoreResource);

			referencer = new CrossReferencer(notifiers) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		} else if (res != null) {
			referencer = new CrossReferencer(res) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		} else {
			EObject targetObject = EcoreUtil.getRootContainer(target);
			referencer = new CrossReferencer(targetObject) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		}
	}

	/**
	 * This will search the first context value corresponding to the given filter or index.
	 * 
	 * @param env
	 *            The environment that asked for this evaluation.
	 * @param args
	 *            Arguments of the invocation.
	 * @return Result of the invocation.
	 */
	private static Object getContext(AcceleoEvaluationEnvironment env, Object[] args) {
		final Object soughtValue;
		final List<Object> allIterators = new ArrayList<Object>();
		int index = 0;
		Object value = env.getValueOf(TEMPORARY_CONTEXT_VAR_PREFIX + index++);
		while (value != null) {
			allIterators.add(value);
			value = env.getValueOf(TEMPORARY_CONTEXT_VAR_PREFIX + index++);
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
	 * Tries and find a cross reference provider for the given EObject.
	 * 
	 * @param eObject
	 *            The EObject we need a cross reference provider for.
	 * @return The first cross reference provider we found for the given EObject.
	 */
	private static IAcceleoCrossReferenceProvider getCrossReferencerAdapter(EObject eObject) {
		IAcceleoCrossReferenceProvider provider = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			Object adapted = Platform.getAdapterManager().getAdapter(eObject,
					IAcceleoCrossReferenceProvider.class);
			if (adapted instanceof IAcceleoCrossReferenceProvider) {
				provider = (IAcceleoCrossReferenceProvider)adapted;
			}
		}
		return provider;
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
	private static UnsupportedOperationException getExceptionOperationCallFailed(EOperation operation,
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
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(String)}.
	 * 
	 * @param env
	 *            The environment that asked for this evaluation.
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @return The value of the property corresponding to the given key.
	 */
	private static String getProperty(AcceleoEvaluationEnvironment env, String key) {
		String propertyValue = env.getPropertiesLookup().getProperty(key);
		/*
		 * Pass through MessageFormat so that we're consistent in the handling of special chars such as the
		 * apostrophe.
		 */
		if (propertyValue != null) {
			propertyValue = MessageFormat.format(propertyValue, new Object[] {});
		}
		return propertyValue;
	}

	/**
	 * This will return the value of the property corresponding to the given key, with parameters substituted
	 * as needed. Precedence rules for the properties can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(String)}.
	 * 
	 * @param env
	 *            The environment that asked for this evaluation.
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @param arguments
	 *            Substitution for the property parameters.
	 * @return The value of the property corresponding to the given key.
	 */
	private static String getProperty(AcceleoEvaluationEnvironment env, String key, Object[] arguments) {
		String propertyValue = env.getPropertiesLookup().getProperty(key);
		if (propertyValue != null) {
			propertyValue = MessageFormat.format(propertyValue, arguments);
		}
		return propertyValue;
	}

	/**
	 * This will return the value of the property corresponding to the given key from the first properties
	 * holder of the given name. Precedence rules for the properties can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(String)}.
	 * 
	 * @param env
	 *            The environment that asked for this evaluation.
	 * @param propertiesFileName
	 *            Name of the properties file in which we seek the given key.
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @return The value of the property corresponding to the given key.
	 */
	private static String getProperty(AcceleoEvaluationEnvironment env, String propertiesFileName, String key) {
		String propertyValue = env.getPropertiesLookup().getProperty(propertiesFileName, key);
		/*
		 * Pass through MessageFormat so that we're consistent in the handling of special chars such as the
		 * apostrophe.
		 */
		if (propertyValue != null) {
			propertyValue = MessageFormat.format(propertyValue, new Object[] {});
		}
		return propertyValue;
	}

	/**
	 * This will return the value of the property corresponding to the given key from the first properties
	 * holder of the given name, with parameters substituted as needed. Precedence rules for the properties
	 * can be found in the javadoc of
	 * {@link org.eclipse.acceleo.engine.generation.IAcceleoEngine#addProperties(String)}.
	 * 
	 * @param env
	 *            The environment that asked for this evaluation.
	 * @param propertiesFileName
	 *            Name of the properties file in which we seek the given key.
	 * @param key
	 *            Key of the property which value is to be returned.
	 * @param arguments
	 *            Substitution for the property parameters.
	 * @return The value of the property corresponding to the given key.
	 */
	private static String getProperty(AcceleoEvaluationEnvironment env, String propertiesFileName,
			String key, Object[] arguments) {
		String propertyValue = env.getPropertiesLookup().getProperty(propertiesFileName, key);
		if (propertyValue != null) {
			propertyValue = MessageFormat.format(propertyValue, arguments);
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
	private static Object invoke(URI moduleURI, Object source, Object[] args) {
		Object result = null;
		final Class<?> serviceClass = AcceleoServicesRegistry.INSTANCE.addServiceClass(moduleURI,
				(String)args[0]);
		if (serviceClass == null) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.ClassNotFound", args[0], moduleURI.lastSegment())); //$NON-NLS-1$
		}
		final String methodSignature = (String)args[1];
		try {
			final Method method = findInvokeMethod(serviceClass, methodSignature);
			// method cannot be null at this point. findInvokeMethod has thrown an exception in such cases

			final List<Object> invocationArguments = (List<Object>)args[2];
			if (method.getParameterTypes().length == 0) {
				// If we can use the method from the Java service on the current EObject we do it
				if (Modifier.isStatic(method.getModifiers())) {
					result = method.invoke(null);
				} else if (serviceClass.isInstance(source) && invocationArguments.size() == 0) {
					result = method.invoke(source);
				} else if (invocationArguments.size() == 1
						&& serviceClass.isInstance(invocationArguments.get(0))) {
					result = method.invoke(invocationArguments.get(0));
				} else {
					result = method.invoke(AcceleoServicesRegistry.INSTANCE.getServiceInstance(serviceClass));
				}
			} else {
				if (Modifier.isStatic(method.getModifiers())) {
					result = method.invoke(null, invocationArguments.toArray(new Object[invocationArguments
							.size()]));
				} else if (method.getParameterTypes().length == invocationArguments.size()
						&& serviceClass.isInstance(source)) {
					result = method.invoke(source, invocationArguments.toArray(new Object[invocationArguments
							.size()]));
				} else if (invocationArguments.size() > 0
						&& (invocationArguments.size() - method.getParameterTypes().length > 0)
						&& serviceClass.isInstance(invocationArguments.get(0))) {
					Object newSource = invocationArguments.remove(0);
					result = method.invoke(newSource, invocationArguments
							.toArray(new Object[invocationArguments.size()]));
				} else {
					result = method.invoke(AcceleoServicesRegistry.INSTANCE.getServiceInstance(serviceClass),
							invocationArguments.toArray(new Object[invocationArguments.size()]));
				}
			}
		} catch (NoSuchMethodException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.NoSuchMethod", methodSignature, args[0]), e); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			throw new AcceleoEvaluationException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			/*
			 * Shouldn't happen. We retrieve methods through Class#getMethod() which only iterates over public
			 * members.
			 */
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.RestrictedMethod", methodSignature, args[0]), e); //$NON-NLS-1$
		} catch (InvocationTargetException e) {
			throw new AcceleoEvaluationException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationEnvironment.ParameterClassNotFound", methodSignature, args[0]), e); //$NON-NLS-1$
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
	private static boolean isAlpha(String s) {
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
	private static boolean isAlphanumeric(String s) {
		final char[] chars = s.toCharArray();
		for (final char c : chars) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}
		}
		return true;
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
	private static List<EObject> siblings(EObject source, EClassifier filter) {
		final List<EObject> result = new ArrayList<EObject>();
		Object container = getContainer(source);
		if (container != null) {
			for (EObject child : getContents(container)) {
				if (child != source && (filter == null || filter.isInstance(child))) {
					result.add(child);
				}
			}
		}
		return result;
	}

	/**
	 * Returns a Sequence containing either all preceding siblings of <code>source</code>, or all of its
	 * following siblings.
	 * 
	 * @param source
	 *            The EObject we seek the siblings of.
	 * @param filter
	 *            Types of the EObjects we seek to retrieve.
	 * @param preceding
	 *            If <code>true</code>, we'll return the preceding siblings of <em>source</em>. Otherwise,
	 *            this will return its followingSiblings.
	 * @return Sequence containing the sought set of the receiver's siblings.
	 */
	private static List<EObject> siblings(EObject source, EClassifier filter, boolean preceding) {
		final List<EObject> result = new ArrayList<EObject>();
		final Object container = getContainer(source);
		if (container != null) {
			final List<EObject> siblings = getContents(container);
			int startIndex = 0;
			int endIndex = siblings.size();
			if (preceding) {
				endIndex = siblings.indexOf(source);
			} else {
				startIndex = siblings.indexOf(source) + 1;
			}

			for (int i = startIndex; i < endIndex; i++) {
				EObject child = siblings.get(i);
				if (filter == null || filter.isInstance(child)) {
					result.add(child);
				}
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
	private static String strtok(String source, String delimiters, Integer flag) {
		// flag == 0, create a tokenizer, cache it then return its first element.
		/*
		 * flag == 1, create the tokenizer if none exists for this source, retrieve the existing one
		 * otherwise. Then returns the next token in it.
		 */
		if (flag.intValue() == 0) {
			final StringTokenizer tokenizer = new StringTokenizer(source, delimiters);
			TOKENIZERS.put(source, tokenizer);
			return tokenizer.nextToken();
		} else if (flag.intValue() == 1) {
			StringTokenizer tokenizer = TOKENIZERS.get(source);
			if (tokenizer == null) {
				tokenizer = new StringTokenizer(source, delimiters);
				TOKENIZERS.put(source, tokenizer);
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
	private static String substitute(String source, String substring, String replacement,
			boolean substituteAll) {
		if (substring == null || replacement == null) {
			throw new NullPointerException();
		}

		if (substituteAll) {
			return Pattern.compile(substring, Pattern.LITERAL).matcher(source).replaceAll(
					Matcher.quoteReplacement(replacement));
		}
		return Pattern.compile(substring, Pattern.LITERAL).matcher(source).replaceFirst(
				Matcher.quoteReplacement(replacement));
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
	private static List<String> tokenize(String source, String delim) {
		final StringTokenizer tokenizer = new StringTokenizer(source, delim);
		List<String> result = new ArrayList<String>();
		while (tokenizer.hasMoreTokens()) {
			result.add(tokenizer.nextToken());
		}
		return result;
	}

	/**
	 * Implements the "tokenize" operation on String type. This will return a sequence containing the tokens
	 * of the given string (using line separators and carriage returns as delimiter).
	 * 
	 * @param source
	 *            Source String that is to be tokenized.
	 * @return A sequence containing the tokens of the given.
	 */
	private static List<String> tokenize(String source) {
		final StringTokenizer tokenizer = new StringTokenizer(source);
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
	private static String toString(Object object) {
		final StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				buffer.append(toString(childrenIterator.next()));
			}
		} else if (object != null) {
			buffer.append(object.toString());
		}
		// else return empty String
		return buffer.toString();
	}

	/**
	 * Elements held by a reference with containment=true and derived=true are not returned by
	 * {@link EObject#eContents()}. This allows us to return the list of all contents from an EObject
	 * <b>including</b> those references.
	 * 
	 * @param eObject
	 *            The EObject we seek the content of.
	 * @return The list of all the content of a given EObject, derived containment references included.
	 */
	private static List<EObject> getContents(EObject eObject) {
		final List<EObject> result = new ArrayList<EObject>(eObject.eContents());
		for (final EReference reference : eObject.eClass().getEAllReferences()) {
			if (reference.isContainment() && reference.isDerived()) {
				final Object value = eObject.eGet(reference);
				if (value instanceof Collection<?>) {
					for (Object newValue : (Collection<?>)value) {
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
	 * Obtains the container of an object for the purpose of accessing its siblings. This is often the
	 * {@link EObject#eContainer() eContainer}, but for top-level objects it may be the
	 * {@link EObject#eResource() eResource}.
	 * 
	 * @param object
	 *            an object
	 * @return its logical container
	 */
	private static Object getContainer(EObject object) {
		Object result = object.eContainer();

		if (result == null && object instanceof InternalEObject) {
			// maybe it's a resource root
			result = ((InternalEObject)object).eDirectResource();
		}

		return result;
	}

	/**
	 * Obtains the contents of a container, as determined by {@link #getContainer(EObject)}.
	 * 
	 * @param container
	 *            a container of objects
	 * @return the contained objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code container}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private static List<EObject> getContents(Object container) {
		List<EObject> contents = Collections.<EObject> emptyList();
		if (container instanceof EObject) {
			contents = getContents((EObject)container);
		} else if (container instanceof Resource) {
			contents = getRoots((Resource)container);
		}
		return contents;
	}

	/**
	 * Like the standard {@link Resource#getContents()} method except that we retrieve only objects that do
	 * not have containers (i.e., they are not cross-resource-contained). This is an important distinction
	 * because we want only peers (or "siblings") of an object that is a root (having no container), and those
	 * are defined as the other objects that are also roots.
	 * 
	 * @param resource
	 *            a resource from which to get root objects
	 * @return the root objects. <b>Note</b> that, for efficiency, the resulting list may be the
	 *         {@code resource}'s actual contents list. Callers must treat the result as unmodifiable
	 */
	private static List<EObject> getRoots(Resource resource) {
		// optimize for the vast majority of cases in which none of the objects are cross-resource-contained
		// (i.e., they are all roots)
		List<EObject> result = resource.getContents();

		for (ListIterator<EObject> iter = result.listIterator(); iter.hasNext();) {
			if (iter.next().eContainer() != null) {
				// don't include this in the result

				// need to copy the result so that we don't modify the resource
				int where = iter.previousIndex();
				List<EObject> newResult = new ArrayList<EObject>(result.size() - 1);
				newResult.addAll(result.subList(0, where));
				result = newResult;

				// continue adding roots
				while (iter.hasNext()) {
					EObject next = iter.next();
					if (next.eContainer() == null) {
						result.add(next);
					}
				}
			}
		}

		return result;
	}
}
