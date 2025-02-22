/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

/**
 * Implementation of the elementary language services like variable access and service call.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class EvaluationServices extends AbstractLanguageServices {

	/**
	 * Log message used when an internal evaluation error is encountered.
	 */
	private static final String INTERNAL_ERROR_MSG = "An internal error occurred during evaluation of a query";

	/**
	 * Creates a new {@link EvaluationServices} instance given a lookupEngine and logging flag.
	 * 
	 * @param queryEnv
	 *            the {@link IReadOnlyQueryEnvironment} to use during evaluation
	 */
	public EvaluationServices(IReadOnlyQueryEnvironment queryEnv) {
		super(queryEnv);
	}

	/**
	 * Returns the value of the specified variable in the specified map. Returns NOTHING when the variable is
	 * not found.
	 * 
	 * @param variableDefinitions
	 *            the set of variable definition in which to lookup the specified variable.
	 * @param variableName
	 *            the name of the variable to lookup in the specified map.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return Returns the value of the specified variable in the specified map or nothing.
	 */
	public Object getVariableValue(Map<String, Object> variableDefinitions, String variableName,
			Diagnostic diagnostic) {
		try {
			Object result = variableDefinitions.get(variableName);
			if (result == null && !variableDefinitions.containsKey(variableName)) {
				Nothing placeHolder = nothing(VARIABLE_NOT_FOUND, variableName);
				addDiagnosticFor(diagnostic, Diagnostic.ERROR, placeHolder);
				result = placeHolder;
			}
			return result;
		} catch (NullPointerException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * returns the nothing value and logs the specified error message.
	 * 
	 * @param message
	 *            the message to log.
	 * @param msgArgs
	 *            the object arguments used to format the log message.
	 * @return an instance of nothing initialized with the formatted error message.
	 */
	private Nothing nothing(String message, Object... msgArgs) {
		String formatedMessage = String.format(message, msgArgs);
		return new Nothing(formatedMessage);
	}

	/**
	 * Do a service call.
	 * 
	 * @param call
	 *            the initial {@link Call}
	 * @param service
	 *            the specification of the service to call.
	 * @param arguments
	 *            the arguments of the service call.
	 * @param argumentTypes
	 *            the array of {@link IType}
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value produced by the service execution.
	 */
	private Object internalCallService(Call call, IService<?> service, Object[] arguments,
			IType[] argumentTypes, Diagnostic diagnostic) {
		try {
			final Object result;
			final Object value = service.invoke(arguments);
			if (value instanceof Nothing) {
				addDiagnosticFor(diagnostic, Diagnostic.WARNING, (Nothing)value);
				result = value;
			} else if (value == null && !isLambda(call)) {
				final List<IType> types = Arrays.asList(argumentTypes);
				final Set<IType> serviceTypes = service.getType(call, new ValidationServices(
						queryEnvironment), null, queryEnvironment, types);
				result = new NullValue(serviceTypes);
			} else {
				result = value;
			}
			return result;
		} catch (AcceleoQueryEvaluationException e) {
			Nothing placeHolder = new Nothing(e.getMessage(), e);
			if (e.getCause() instanceof AcceleoQueryEvaluationException) {
				addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			} else {
				addDiagnosticFor(diagnostic, Diagnostic.ERROR, placeHolder);
			}
			return placeHolder;
		}
	}

	/**
	 * @param call
	 * @return
	 */
	private boolean isLambda(Call call) {
		return call.getArguments().size() == 2 && call.getArguments().get(1) instanceof Lambda;
	}

	/**
	 * Evaluate a service call.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param arguments
	 *            the arguments to pass to the service evaluation.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value resulting from evaluating the service given the specified arguments.
	 */
	public Object call(Call call, Object[] arguments, Diagnostic diagnostic) {
		final Object result;

		switch (call.getType()) {
			case CALLSERVICE:
				result = internalCall(call, arguments, diagnostic);
				break;
			case CALLORAPPLY:
				result = internalCallOrApply(call, arguments, diagnostic);
				break;
			case COLLECTIONCALL:
				result = internalCollectionServiceCall(call, arguments, diagnostic);
				break;
			default:
				throw new UnsupportedOperationException("should never happen");
		}

		return result;
	}

	/**
	 * Evaluate a service call.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param arguments
	 *            the arguments to pass to the service evaluation.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value resulting from evaluating the service given the specified arguments.
	 */
	private Object internalCall(Call call, Object[] arguments, Diagnostic diagnostic) {
		final Object result;

		final String serviceName = call.getServiceName();
		if (arguments.length == 0) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG
					+ " : at least one argument must be specified for service " + serviceName + ".");
		}
		try {
			final IType[] argumentTypes = new IType[arguments.length];
			final Object[] localArguments = new Object[arguments.length];
			for (int i = 0; i < arguments.length; i++) {
				if (arguments[i] == null) {
					argumentTypes[i] = new ClassType(queryEnvironment, null);
					localArguments[i] = null;
				} else if (arguments[i].getClass() == NullValue.class) {
					argumentTypes[i] = ((NullValue)arguments[i]).getType();
					localArguments[i] = null;
				} else if (arguments[i] instanceof EObject) {
					argumentTypes[i] = new EClassifierType(queryEnvironment, ((EObject)arguments[i])
							.eClass());
					localArguments[i] = arguments[i];
				} else {
					argumentTypes[i] = new ClassType(queryEnvironment, arguments[i].getClass());
					localArguments[i] = arguments[i];
				}
			}

			IService<?> service;
			if (call.isSuperCall()) {
				service = ((IQualifiedNameLookupEngine)queryEnvironment.getLookupEngine()).superServiceLookup(
						serviceName, argumentTypes);
			} else {
				service = queryEnvironment.getLookupEngine().lookup(serviceName, argumentTypes);
			}
			if (service == null) {
				Nothing placeHolder = nothing(SERVICE_NOT_FOUND, serviceSignature(serviceName,
						argumentTypes));
				addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
				result = placeHolder;
			} else {
				result = internalCallService(call, service, localArguments, argumentTypes, diagnostic);
			}
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}

		return result;
	}

	/**
	 * The callOrApply method evaluates an expression of the form "<exp>.<service name>(<exp>*)" The first
	 * argument in the arguments array is considered the receiver of the call. If the receiver is a collection
	 * then callOrApply is applied recursively to all elements of the collection thus returning a collection
	 * of the result of this application(nothing values not being added).
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param arguments
	 *            the arguments to pass to the called service.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the result of evaluating the specified service on the specified arguments.
	 */
	@SuppressWarnings("unchecked")
	private Object internalCallOrApply(Call call, Object[] arguments, Diagnostic diagnostic) {
		try {
			Object result;
			if (arguments[0] instanceof List) {
				List<Object> list = (List<Object>)arguments[0];
				result = applyCallOnSequence(call, list, arguments, diagnostic);
			} else if (arguments[0] instanceof Set) {
				Set<Object> set = (Set<Object>)arguments[0];
				result = applyCallOnSet(call, set, arguments, diagnostic);
			} else {
				result = internalCall(call, arguments, diagnostic);
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Calls a collection's service.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param arguments
	 *            the service's arguments.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the result of evaluating the specified service on the specified arguments.
	 */
	private Object internalCollectionServiceCall(Call call, Object[] arguments, Diagnostic diagnostic) {
		Object[] newArguments;
		try {
			Object receiver = arguments[0];
			if (!(receiver instanceof Collection) && !(receiver instanceof Nothing)) {
				// implicit set conversion.
				Set<Object> newReceiver = new LinkedHashSet<Object>();
				// treat "null" as a non existing value. The auto-boxed set will then be empty.
				if (receiver != null) {
					newReceiver.add(receiver);
				}
				receiver = newReceiver;
				newArguments = arguments.clone();
				newArguments[0] = newReceiver;
			} else {
				newArguments = arguments;
			}
			return internalCall(call, newArguments, diagnostic);
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Applies a service call on a sequence of objects.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param origin
	 *            the sequence on which elements to apply the service
	 * @param arguments
	 *            the arguments to pass to the service.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return a sequence made of the result of calling the specified service on the specified arguments on
	 *         the origin list elements.
	 */
	private Object applyCallOnSequence(Call call, List<Object> origin, Object[] arguments,
			Diagnostic diagnostic) {
		try {
			List<Object> result = new ArrayList<Object>(origin.size());
			Object[] innerArguments = arguments.clone();
			for (Object obj : origin) {
				innerArguments[0] = obj;
				final Object newResult = internalCallOrApply(call, innerArguments, diagnostic);
				flattenList(result, newResult);
			}

			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException("empty argument array passed to callOrApply " + call
					.getServiceName(), e);
		}
	}

	/**
	 * Flatten the given {@link Object} into the given {@link List}.
	 * 
	 * @param list
	 *            the {@link List}
	 * @param object
	 *            the {@link Object}
	 */
	protected void flattenList(List<Object> list, Object object) {
		if (!(object instanceof Nothing)) {
			if (object instanceof Collection) {
				list.addAll((Collection<?>)object);
			} else if (object != null && object.getClass() != NullValue.class) {
				list.add(object);
			}
		}
	}

	/**
	 * Applies a service call on a set of objects.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param origin
	 *            the sequence on which elements to apply the service
	 * @param arguments
	 *            the arguments to pass to the service.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return a sequence made of the result of calling the specified service on the specified arguments on
	 *         the origin set elements.
	 */
	private Object applyCallOnSet(Call call, Set<Object> origin, Object[] arguments, Diagnostic diagnostic) {
		try {
			Set<Object> result = new LinkedHashSet<Object>(origin.size());
			Object[] innerArguments = arguments.clone();
			for (Object obj : origin) {
				innerArguments[0] = obj;
				final Object newResult = internalCallOrApply(call, innerArguments, diagnostic);
				flattenSet(result, newResult);
			}

			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}

	}

	/**
	 * Flatten the given {@link Object} into the given {@link Set}.
	 * 
	 * @param set
	 *            the {@link Set}
	 * @param object
	 *            the {@link Object}
	 */
	protected void flattenSet(Set<Object> set, Object object) {
		if (!(object instanceof Nothing)) {
			if (object instanceof Collection) {
				set.addAll((Collection<?>)object);
			} else if (object != null && object.getClass() != NullValue.class) {
				set.add(object);
			}
		}
	}

	/**
	 * Build up the specified service's signature for reporting. Only use this when the service is not
	 * available directly, otherwise use {@link IService#getShortSignature() short signature}.
	 * 
	 * @param serviceName
	 *            the name of the service
	 * @param argumentTypes
	 *            the service's call argument types
	 * @return the specified service's signature.
	 */
	protected String serviceSignature(String serviceName, IType[] argumentTypes) {
		StringBuilder builder = new StringBuilder();
		builder.append(serviceName).append('(');
		boolean first = true;
		for (IType argType : argumentTypes) {
			if (!first) {
				builder.append(',');
			} else {
				first = false;
			}
			if (argType == null) {
				builder.append("Object=null");
			} else {
				builder.append(argType.toString());
			}
		}
		return builder.append(')').toString();
	}

	/**
	 * Adds a child to the given diagnostic chain with the information from that Nothing instance.
	 * 
	 * @param chain
	 *            The parent chain.
	 * @param severity
	 *            Severity to give that issue.
	 * @param nothing
	 *            The nothing we've obtained as a result of an evaluation failure.
	 */
	private void addDiagnosticFor(Diagnostic chain, int severity, Nothing nothing) {
		if (chain instanceof DiagnosticChain) {
			Diagnostic child = new BasicDiagnostic(severity, AstBuilderListener.PLUGIN_ID, 0, nothing
					.getMessage(), new Object[] {nothing.getCause(), });
			((DiagnosticChain)chain).add(child);
		}
	}

	/**
	 * Gets the {@link EClassifier} for the given {@link EClassifierTypeLiteral}.
	 * 
	 * @param eClassifierTypeLiteral
	 *            the {@link EClassifierTypeLiteral}
	 * @return the {@link EClassifier} for the given {@link EClassifierTypeLiteral} if any, <code>null</code>
	 *         otherwise
	 */
	public EClassifier getEClassifier(EClassifierTypeLiteral eClassifierTypeLiteral) {
		final EClassifier result;

		final Collection<EClassifier> eClassifiers = queryEnvironment.getEPackageProvider().getTypes(
				eClassifierTypeLiteral.getEPackageName(), eClassifierTypeLiteral.getEClassifierName());
		if (eClassifiers.isEmpty()) {
			result = null;
		} else {
			result = eClassifiers.iterator().next();
		}

		return result;
	}

	/**
	 * Gets the {@link EEnumLiteral} for the given {@link EnumLiteral}.
	 * 
	 * @param enumLiteral
	 *            the {@link EnumLiteral}
	 * @return the {@link EEnumLiteral} for the given {@link EnumLiteral} if any, <code>null</code> otherwise
	 */
	public EEnumLiteral getEEnumLiteral(EnumLiteral enumLiteral) {
		final EEnumLiteral result;

		final Collection<EEnumLiteral> literals = queryEnvironment.getEPackageProvider().getEnumLiterals(
				enumLiteral.getEPackageName(), enumLiteral.getEEnumName(), enumLiteral.getEEnumLiteralName());
		if (literals.isEmpty()) {
			result = null;
		} else {
			result = literals.iterator().next();
		}

		return result;
	}
}
