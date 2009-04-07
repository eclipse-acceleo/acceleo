/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.ecore.EcoreEvaluationEnvironment;

/**
 * This will allow us to accurately evaluate custom operations defined in the Acceleo standard library and
 * resolve the right template for each call (guards, overrides, namesakes, ...).
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationEnvironment extends EcoreEvaluationEnvironment {
	/** This will allow the environment to know of the modules currently in the generation context. */
	final Set<Module> currentModules = new HashSet<Module>();

	/** Maps dynamic overrides as registered in the {@link AcceleoDynamicTemplatesRegistry}. */
	private final Map<Template, Set<Template>> dynamicOverrides = new HashMap<Template, Set<Template>>();

	/** Maps all overriding templates to their <code>super</code>. */
	private final Map<Template, Set<Template>> overridingTemplates = new HashMap<Template, Set<Template>>();

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
	 * This constructor is needed by the factory.
	 * 
	 * @param parent
	 *            Parent evaluation environment.
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 */
	public AcceleoEvaluationEnvironment(
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> parent,
			Module module) {
		super(parent);
		mapAllTemplates(module);
		mapDynamicOverrides();
	}

	/**
	 * This constructor will create our environment given the module from which to resolve dependencies.
	 * 
	 * @param module
	 *            We will resolve dependencies for this module and keep references to all accessible
	 *            templates.
	 */
	public AcceleoEvaluationEnvironment(Module module) {
		super();
		mapAllTemplates(module);
		mapDynamicOverrides();
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
	public Object callNonStandardOperation(EOperation operation, Object source, Object... args) {
		Object result = null;
		final String operationName = operation.getName();
		// Specifications of each non-standard operation can be found as comments of
		// AcceleoNonStandardLibrary#OPERATION_*.
		if (AcceleoNonStandardLibrary.OPERATION_OCLANY_TOSTRING.equals(operationName)) {
			result = source.toString();
		} else if (source instanceof String) {
			final String sourceValue = (String)source;

			if (AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL.equals(operationName)) {
				result = substitute(sourceValue, (String)args[0], (String)args[1], true);
			} else if (AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE.equals(operationName)) {
				result = sourceValue.replaceFirst((String)args[0], (String)args[1]);
			} else if (AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL.equals(operationName)) {
				result = sourceValue.replaceAll((String)args[0], (String)args[1]);
			} else if (AcceleoNonStandardLibrary.OPERATION_STRING_ENDSWITH.equals(operationName)) {
				result = sourceValue.endsWith((String)args[0]);
			} else if (AcceleoNonStandardLibrary.OPERATION_STRING_STARTSWITH.equals(operationName)) {
				result = sourceValue.startsWith((String)args[0]);
			} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TRIM.equals(operationName)) {
				result = sourceValue.trim();
			} else if (AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE.equals(operationName)) {
				result = tokenize(sourceValue, (String)args[0]);
			}
		} else if (source instanceof EObject) {
			final EObject sourceValue = (EObject)source;

			if (AcceleoNonStandardLibrary.OPERATION_OCLANY_EALLCONTENTS.equals(operationName)) {
				if (args.length == 0) {
					result = eAllContents(sourceValue, null);
				} else if (args.length == 1 && args[0] instanceof EClassifier) {
					result = eAllContents(sourceValue, (EClassifier)args[0]);
				}
				// fall through : let else fail in UnsupportedOperationException
			} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_ANCESTORS.equals(operationName)) {
				result = ancestors(sourceValue);
			} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_SIBLINGS.equals(operationName)) {
				result = siblings(sourceValue);
			} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_EINVERSE.equals(operationName)) {
				result = eInverse(sourceValue);
			}
		}

		if (result != null) {
			return result;
		}

		// If we're here, the operation is undefined.
		final StringBuilder argErrorMsg = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			argErrorMsg.append(args[i].getClass().getName());
			if (i < args.length - 1) {
				argErrorMsg.append(", "); //$NON-NLS-1$
			}
		}
		final String sourceLabel;
		if (source == null) {
			sourceLabel = "null"; //$NON-NLS-1$
		} else {
			sourceLabel = source.getClass().getName();
		}
		throw new UnsupportedOperationException(
				AcceleoEngineMessages
						.getString(
								"AcceleoEvaluationEnvironment.UndefinedOperation", operation.getName(), argErrorMsg.toString(), //$NON-NLS-1$
								sourceLabel));
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
		Object result = null;
		// Specifications of each standard operation can be found as comments of
		// AcceleoStandardLibrary#OPERATION_*.
		if (source instanceof String) {
			final String sourceValue = (String)source;

			if (AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE.equals(operation.getName())) {
				result = substitute(sourceValue, (String)args[0], (String)args[1], false);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_INDEX.equals(operation.getName())) {
				result = sourceValue.indexOf((String)args[0]);
			} else if (AcceleoStandardLibrary.OPERATION_STRING_FIRST.equals(operation.getName())) {
				int endIndex = ((Integer)args[0]).intValue();
				if (endIndex < 0 || endIndex > sourceValue.length()) {
					result = sourceValue;
				} else {
					result = sourceValue.substring(0, endIndex);
				}
			} else if (AcceleoStandardLibrary.OPERATION_STRING_LAST.equals(operation.getName())) {
				int charCount = ((Integer)args[0]).intValue();
				if (charCount < 0 || charCount > sourceValue.length()) {
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

		if (result != null) {
			return result;
		}

		// If we're here, the operation is undefined.
		final StringBuilder argErrorMsg = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			argErrorMsg.append(args[i].getClass().getName());
			if (i < args.length - 1) {
				argErrorMsg.append(", "); //$NON-NLS-1$
			}
		}
		final String sourceLabel;
		if (source == null) {
			sourceLabel = "null"; //$NON-NLS-1$
		} else {
			sourceLabel = source.getClass().getName();
		}
		throw new UnsupportedOperationException(AcceleoEngineMessages.getString(
				"AcceleoEvaluationEnvironment.UndefinedOperation", operation.getName(), argErrorMsg //$NON-NLS-1$
						.toString(), sourceLabel));
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
			} else {
				argumentTypes.add(arg.getClass());
			}
		}

		/*
		 * NOTE : we depend on the ordering offered by List types. Do not change implementation without
		 * testing.
		 */
		final List<Template> orderedNamesakes = reorderCandidatesPriority(origin, getAllCandidateNamesakes(
				call, argumentTypes));
		final Set<Template> dynamicOverriding = getAllDynamicCandidateOverriding(orderedNamesakes,
				argumentTypes);
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
	 * Returns a Sequence containing the full set of <code>source</code>'s ancestors.
	 * 
	 * @param source
	 *            The EObject we seek the ancestors of.
	 * @return Sequence containing the full set of the receiver's ancestors.
	 */
	private Set<EObject> ancestors(EObject source) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		EObject container = source.eContainer();
		while (container != null) {
			result.add(container);
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
			for (final Template candidate : new LinkedHashSet<Template>(candidates)) {
				final Object parameterType = candidate.getParameter().get(i).getType();
				if (!isApplicableArgument(parameterType, argumentTypes.get(i))) {
					applicableCandidates.remove(candidate);
				}
			}
		}
		return applicableCandidates;
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
	 * Returns a Sequence containing the full set of the inverse references on the receiver.
	 * 
	 * @param target
	 *            The EObject we seek the inverse references of.
	 * @return Sequence containing the full set of inverse references.
	 */
	private Set<EObject> eInverse(EObject target) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		if (referencer == null) {
			if (target.eResource() != null && target.eResource().getResourceSet() != null) {
				referencer = new CrossReferencer(target.eResource().getResourceSet()) {
					/** Default SUID. */
					private static final long serialVersionUID = 1L;

					// static initializer
					{
						crossReference();
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
		Collection<EStructuralFeature.Setting> settings = referencer.get(target);
		if (settings == null) {
			return Collections.emptySet();
		}
		for (EStructuralFeature.Setting setting : settings) {
			result.add(setting.getEObject());
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
	 * <u>including</u> those references.
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
					result.addAll((Collection)value);
				} else if (value instanceof EObject) {
					result.add((EObject)value);
				}
			}
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
		if (expectedType instanceof EClass && argumentType instanceof EClass) {
			isApplicable = expectedType == argumentType
					|| isSubTypeOf((EClass)expectedType, (EClass)argumentType);
		} else if (expectedType instanceof Class && argumentType instanceof Class) {
			isApplicable = ((Class<?>)expectedType).isAssignableFrom((Class<?>)argumentType);
		} else if (expectedType instanceof EDataType && argumentType instanceof Class) {
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
			for (File moduleFile : dynamicModuleFiles) {
				if (moduleFile.exists() && moduleFile.canRead()) {
					try {
						Resource res = ModelUtils.load(moduleFile, resourceSet).eResource();
						new DynamicModulesReferenceResolver(res);
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
	 * Maps dynamic overriding templates for smoother polymorphic resolution.
	 */
	private void mapDynamicOverrides() {
		for (Module module : loadDynamicModules()) {
			boolean map = false;
			final Set<Module> unMappedRequiredModules = new LinkedHashSet<Module>();
			for (Module extended : module.getExtends()) {
				if (currentModules.contains(extended)) {
					map = true;
				} else {
					unMappedRequiredModules.add(extended);
				}
			}
			// This module shouldn't be added to the context. Go to next.
			if (!map) {
				continue;
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
					for (final Template overriden : ((Template)elem).getOverrides()) {
						Set<Template> overriding = dynamicOverrides.get(overriden);
						if (overriding == null) {
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
						overriding.add((Template)elem);
					}
				}
			}
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
	 * Returns a Sequence containing the full set of <code>source</code>'s siblings.
	 * 
	 * @param source
	 *            The EObject we seek the siblings of.
	 * @return Sequence containing the full set of the receiver's siblings.
	 */
	private Set<EObject> siblings(EObject source) {
		final Set<EObject> result = new LinkedHashSet<EObject>();
		EObject container = source.eContainer();
		for (EObject child : getContents(container)) {
			if (child != source) {
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
	 * This will allow us to handle references to file scheme URIs within the resource set containing the
	 * generation modules and their dynamic overrides. We need this since the dynamic overrides are loaded
	 * with file scheme URIs whereas the generation module can be loaded through platform scheme URIs and we
	 * need references to be resolved anyway.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private final class DynamicModulesReferenceResolver extends EcoreUtil.CrossReferencer {
		/** Generated SUID. */
		private static final long serialVersionUID = -8156535301924238350L;

		/**
		 * Instantiates the reference resolver given the dynamic template which references are to be resolved.
		 * 
		 * @param resource
		 *            The resource containing the dynamic template which references are to be resolved.
		 */
		protected DynamicModulesReferenceResolver(Resource resource) {
			super(resource);
			crossReference();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer#crossReference(org.eclipse.emf.ecore.EObject,
		 *      org.eclipse.emf.ecore.EReference, org.eclipse.emf.ecore.EObject)
		 */
		@Override
		protected boolean crossReference(EObject object, EReference reference, EObject crossReferencedEObject) {
			if (crossReferencedEObject.eIsProxy()) {
				if (resolveProxy(object, reference, crossReferencedEObject)) {
					return false;
				}
			}
			return super.crossReference(object, reference, crossReferencedEObject);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer#resolve()
		 */
		@Override
		protected boolean resolve() {
			return false;
		}

		/**
		 * This will try and resolve the given proxy value for the given reference.
		 * 
		 * @param object
		 *            The object on which a reference value is a proxy.
		 * @param reference
		 *            The reference which value is to be resolved.
		 * @param proxy
		 *            The proxy which is to be resolved.
		 * @return <code>true</code> if <code>proxy</code> could be resolved in the current modules,
		 *         <code>false</code> otherwise.
		 */
		private boolean resolveProxy(EObject object, EReference reference, EObject proxy) {
			final URI proxyURI = ((InternalEObject)proxy).eProxyURI();
			if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(proxyURI.fileExtension())
					&& "file".equals(proxyURI.scheme())) { //$NON-NLS-1$
				String moduleName = proxyURI.lastSegment();
				moduleName = moduleName.substring(0, moduleName.lastIndexOf('.'));
				for (Module module : currentModules) {
					if (moduleName.equals(module.getName())) {
						final EObject crossReferencedTarget = module.eResource().getEObject(
								proxyURI.fragment());
						if (crossReferencedTarget != null) {
							setOrAdd(object, reference, crossReferencedTarget);
							add((InternalEObject)object, reference, crossReferencedTarget);
							return true;
						}
					}
				}
			}
			return false;
		}

		/**
		 * Will behave like either eSet or eAdd according to the given reference upper bound.
		 * 
		 * @param object
		 *            The object on which a reference value is to be modified.
		 * @param reference
		 *            The reference which value is(are) to be modified.
		 * @param value
		 *            The value that is to be set for the reference if it is unique, or added to the reference
		 *            values if it is multi-valued.
		 */
		@SuppressWarnings("unchecked")
		private void setOrAdd(EObject object, EReference reference, EObject value) {
			if (reference.isMany()) {
				if (value instanceof Collection) {
					((Collection)object.eGet(reference)).addAll((Collection)value);
				} else {
					((Collection)object.eGet(reference)).add(value);
				}
			} else {
				object.eSet(reference, value);
			}
		}
	}
}
