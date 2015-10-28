/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Implementation of the elementary language services like variable access and service call.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class EvaluationServices extends AbstractLanguageServices {

	/**
	 * Log message used when a called service returned null.
	 */
	private static final String SERVICE_RETURNED_NULL = "Service %s returned a null value";

	/**
	 * Log message used when an internal evaluation error is encountered.
	 */
	private static final String INTERNAL_ERROR_MSG = "An internal error occured during evaluation of a query";

	/**
	 * Creates a new {@link EvaluationServices} instance given a lookupEngine and logging flag.
	 * 
	 * @param queryEnv
	 *            the {@link IQueryEnvironment} to use during evaluation
	 */
	public EvaluationServices(IQueryEnvironment queryEnv) {
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
	public Object getVariableValue(ScopedEnvironment variableDefinitions, String variableName,
			Diagnostic diagnostic) {
		try {
			Object result = variableDefinitions.getVariableValue(variableName);
			if (result == null) {
				Nothing placeHolder = nothing(VARIABLE_NOT_FOUND, variableName);
				addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
				result = placeHolder;
			}
			return result;
		} catch (NullPointerException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Returns the value of the specified feature on the specified object. The object must be an
	 * {@link EObject} instance otherwise the method returns nothing.
	 * 
	 * @param context
	 *            the object in which to read the feature.
	 * @param featureName
	 *            the name of the feature to read.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value of the specified feature in the specified object.
	 */
	@SuppressWarnings("unchecked")
	public Object featureAccess(Object context, String featureName, Diagnostic diagnostic) {
		Object result;
		if (context instanceof EObject) {
			EClass eClass = ((EObject)context).eClass();
			EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
			if (feature == null) {
				final Nothing placeHolder = nothing(UNKNOWN_FEATURE, featureName, eClass.getName());
				addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
				result = placeHolder;
			} else {
				result = ((EObject)context).eGet(feature);
			}
		} else if (context instanceof List) {
			result = applyGetFeatureOnSequence((List<Object>)context, featureName, diagnostic);
		} else if (context instanceof Set) {
			result = applyGetFeatureOnSet((Set<Object>)context, featureName, diagnostic);
		} else if (context != null) {
			final Nothing placeHolder = nothing(NON_EOBJECT_FEATURE_ACCESS, featureName, context.getClass()
					.getCanonicalName());
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			result = placeHolder;
		} else {
			final Nothing placeHolder = nothing(NON_EOBJECT_FEATURE_ACCESS, featureName, "null");
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			result = placeHolder;
		}
		return result;
	}

	/**
	 * Applies the 'getFeature' operation on sets.
	 * 
	 * @param context
	 *            the set on which to apply the getFeature operation
	 * @param featureName
	 *            the name of the feature to retrieve
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return a set made of the value of the features of context's elements.
	 */
	private Object applyGetFeatureOnSet(Set<Object> context, String featureName, Diagnostic diagnostic) {
		Set<Object> result = new LinkedHashSet<Object>(context.size());
		for (Object element : context) {
			Object newElt = featureAccess(element, featureName, diagnostic);
			// flatten
			if (!(newElt instanceof Nothing)) {
				if (newElt instanceof Collection) {
					result.addAll((Collection<?>)newElt);
				} else {
					result.add(newElt);
				}
			}
		}

		return result;
	}

	/**
	 * Applies the 'getFeature' operation on lists.
	 * 
	 * @param context
	 *            the set on which to apply the getFeature operation
	 * @param featureName
	 *            the name of the feature to retrieve
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return a set made of the value of the features of context's elements.
	 */
	private Object applyGetFeatureOnSequence(List<Object> context, String featureName, Diagnostic diagnostic) {
		List<Object> result = new ArrayList<Object>(context.size());
		for (Object element : context) {
			Object newElt = featureAccess(element, featureName, diagnostic);
			// flatten
			if (!(newElt instanceof Nothing)) {
				if (newElt instanceof Collection) {
					result.addAll((Collection<?>)newElt);
				} else {
					result.add(newElt);
				}
			}
		}

		return result;
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
	 * returns the nothing value and logs a message reporting that an exception has been thrown during the
	 * service evaluation.
	 * 
	 * @param serviceName
	 *            the name of the service.
	 * @param parameterTypes
	 *            the parameter type of the service arguments.
	 * @param e
	 *            the exception thrown during the service execution.
	 * @return an instance of nothing initialized with the formatted error message and given exception as
	 *         cause.
	 */
	private Nothing nothing(String serviceName, Class<?>[] parameterTypes, Exception e) {
		Throwable cause;
		if (e instanceof InvocationTargetException && e.getCause() != null) {
			cause = e.getCause();
		} else {
			cause = e;
		}
		String message = "Exception while calling " + serviceSignature(serviceName, parameterTypes);
		return new Nothing(message, cause);
	}

	/**
	 * Build the argument type array that corresponds to the specified arguments array.
	 * 
	 * @param arguments
	 *            the argument from which types are required.
	 * @return an array of the classes of the arguments.
	 */
	private Class<?>[] getArgumentTypes(Object[] arguments) {
		Class<?>[] argumentTypes = new Class<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			if (arguments[i] == null) {
				argumentTypes[i] = null;
			} else {
				argumentTypes[i] = arguments[i].getClass();
			}
		}
		return argumentTypes;
	}

	/**
	 * Do a service call.
	 * 
	 * @param service
	 *            the specification of the service to call.
	 * @param arguments
	 *            the arguments of the service call.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value produced by the service execution.
	 */
	private Object callService(IService service, Object[] arguments, Diagnostic diagnostic) {
		Method method = service.getServiceMethod();
		Object result;
		try {
			result = method.invoke(service.getServiceInstance(), arguments);
			if (result == null) {
				Class<?>[] argumentTypes = getArgumentTypes(arguments);
				Nothing placeHolder = nothing(SERVICE_RETURNED_NULL, serviceSignature(method.getName(),
						argumentTypes));
				addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
				// TODO why are we not returning that one?
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			Class<?>[] argumentTypes = getArgumentTypes(arguments);
			Nothing placeHolder = nothing(service.getServiceMethod().getName(), argumentTypes, e);
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			return placeHolder;
		}
	}

	/**
	 * Evaluate a service call.
	 * 
	 * @param serviceName
	 *            the name of the {@link IService} or {@link EOperation} that must be evaluated.
	 * @param arguments
	 *            the arguments to pass to the service evaluation.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the value resulting from evaluating the service given the specified arguments.
	 */
	public Object call(String serviceName, Object[] arguments, Diagnostic diagnostic) {
		final Object result;

		if (arguments.length == 0) {
			throw new AcceleoQueryEvaluationException(
					"An internal error occured during evaluation of a query : at least one argument must be specified for service "
							+ serviceName + ".");
		}
		try {
			Class<?>[] argumentTypes = getArgumentTypes(arguments);
			IService service = queryEnvironment.getLookupEngine().lookup(serviceName, argumentTypes);
			if (service == null) {
				if (arguments[0] instanceof EObject) {
					result = callEOperation(serviceName, arguments, diagnostic);
				} else {
					Nothing placeHolder = nothing(SERVICE_NOT_FOUND, serviceSignature(serviceName,
							argumentTypes));
					addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
					result = placeHolder;
				}
			} else {
				result = callService(service, arguments, diagnostic);
			}
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}

		return result;
	}

	/**
	 * Calls an {@link EOperation} with the given name and given arguments.
	 * 
	 * @param operationName
	 *            the {@link EOperation#getName() name}
	 * @param parameters
	 *            the {@link EOperation#getEParameters() parameters}
	 * @param diagnostic
	 *            the {@link Diagnostic}
	 * @return the {@link EOperation} result if any, {@link Nothing} otherwise
	 * @throws InvocationTargetException
	 *             if the {@link EOperation} invocation fails
	 */
	private Object callEOperation(String operationName, Object[] parameters, Diagnostic diagnostic)
			throws InvocationTargetException {
		final Object result;
		final Object[] arguments = Arrays.copyOfRange(parameters, 1, parameters.length);
		final List<Set<EParameter>> eClassifiers = getEParameters(arguments);
		EOperation eOperation = null;
		final Object receiver = parameters[0];
		if (parameters.length > 1) {
			final Iterator<List<EParameter>> it = new CombineIterator<EParameter>(eClassifiers);
			while (eOperation == null && it.hasNext()) {
				eOperation = queryEnvironment.getEPackageProvider().lookupEOperation(
						((EObject)receiver).eClass(), operationName, it.next());
			}
		} else {
			eOperation = queryEnvironment.getEPackageProvider().lookupEOperation(
					((EObject)receiver).eClass(), operationName, new ArrayList<EParameter>());
		}
		if (eOperation != null) {
			final EList<Object> eArguments = new BasicEList<Object>();
			for (int i = 1; i < parameters.length; ++i) {
				eArguments.add(parameters[i]);
			}
			if (!eOperation.getEContainingClass().isSuperTypeOf(((EObject)receiver).eClass())) {
				// This will be the case for EObject contained eOperations : eContents, eContainer,
				// eCrossReferences... are available to be called, but "EObject" is not a modeled super-type
				// of the receiver; this "eInvoke" will fail. Fall back to plain reflection.
				result = eOperationJavaInvoke(operationName, receiver, arguments, diagnostic);
			} else if (hasEInvoke(receiver)) {
				result = ((EObject)receiver).eInvoke(eOperation, eArguments);
			} else {
				result = eOperationJavaInvoke(operationName, receiver, arguments, diagnostic);
			}
		} else {
			final Class<?>[] argumentTypes = getArgumentTypes(parameters);
			Nothing placeHolder = nothing(SERVICE_EOPERATION_NOT_FOUND, serviceSignature(operationName,
					argumentTypes));
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			result = placeHolder;
		}
		return result;
	}

	/**
	 * Call the {@link EOperation} thru a Java invoke.
	 * 
	 * @param operationName
	 *            the {@link EOperation#getName() name}
	 * @param receiver
	 *            the receiver
	 * @param arguments
	 *            arguments
	 * @param diagnostic
	 *            the {@link Diagnostic}
	 * @return the {@link EOperation} result if any, {@link Nothing} otherwise
	 * @throws InvocationTargetException
	 *             if the invoked {@link EOperation} fail
	 */
	private Object eOperationJavaInvoke(String operationName, final Object receiver,
			final Object[] arguments, Diagnostic diagnostic) throws InvocationTargetException {
		final Object result;
		final Class<?>[] argumentTypes = getArgumentTypes(arguments);
		Object invokeResult = null;
		try {
			final Method method = receiver.getClass().getMethod(operationName, argumentTypes);
			invokeResult = method.invoke(receiver, arguments);
		} catch (NoSuchMethodException e) {
			Nothing placeHolder = nothing(COULDN_T_INVOKE_EOPERATION, serviceSignature(operationName,
					argumentTypes), e.getMessage());
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			invokeResult = placeHolder;
		} catch (SecurityException e) {
			Nothing placeHolder = nothing(COULDN_T_INVOKE_EOPERATION, serviceSignature(operationName,
					argumentTypes), e.getMessage());
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			invokeResult = placeHolder;
		} catch (IllegalAccessException e) {
			Nothing placeHolder = nothing(COULDN_T_INVOKE_EOPERATION, serviceSignature(operationName,
					argumentTypes), e.getMessage());
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			invokeResult = placeHolder;
		} catch (IllegalArgumentException e) {
			Nothing placeHolder = nothing(COULDN_T_INVOKE_EOPERATION, serviceSignature(operationName,
					argumentTypes), e.getMessage());
			addDiagnosticFor(diagnostic, Diagnostic.WARNING, placeHolder);
			invokeResult = placeHolder;
		} finally {
			result = invokeResult;
		}
		return result;
	}

	/**
	 * Try to find out if the Operation reflection is enable for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object} to test.
	 * @return <code>true</code> if the Operation reflection is enable for the given {@link Object},
	 *         <code>false</code> otherwise
	 */
	private boolean hasEInvoke(Object object) {
		Method method = null;

		try {
			method = object.getClass().getDeclaredMethod("eInvoke", int.class, EList.class);
		} catch (NoSuchMethodException e) {
			// nothing to do here
		} catch (SecurityException e) {
			// nothing to do here
		}

		return method != null;
	}

	/**
	 * Gets the {@link Set} of {@link EParameter} for each given {@link Object}.
	 * 
	 * @param objects
	 *            the {@link List} of {@link Object}
	 * @return the {@link Set} of {@link EClassifier} for each given {@link Object}
	 */
	private List<Set<EParameter>> getEParameters(Object[] objects) {
		final List<Set<EParameter>> result = new ArrayList<Set<EParameter>>();

		for (Object object : objects) {
			final Set<EParameter> eParamters = new LinkedHashSet<EParameter>();
			if (object instanceof List) {
				final EParameter parameter = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
				parameter.setUpperBound(-1);
				parameter.setEType(null);
				eParamters.add(parameter);
			} else if (object instanceof EObject) {
				final EParameter parameter = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
				parameter.setEType(((EObject)object).eClass());
				eParamters.add(parameter);
			} else if (object != null) {
				for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getEClass(
						object.getClass())) {
					final EParameter parameter = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
					parameter.setEType(eClassifier);
					eParamters.add(parameter);
				}
			} else {
				final EParameter parameter = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
				parameter.setEType(null);
				eParamters.add(parameter);
			}
			result.add(eParamters);
		}

		return result;
	}

	/**
	 * The callOrApply method evaluates an expression of the form "<exp>.<service name>(<exp>*)" The first
	 * argument in the arguments array is considered the receiver of the call. If the receiver is a collection
	 * then callOrApply is applied recursively to all elements of the collection thus returning a collection
	 * of the result of this application(nothing values not being added).
	 * 
	 * @param serviceName
	 *            the name of the service to call.
	 * @param arguments
	 *            the arguments to pass to the called service.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the result of evaluating the specified service on the specified arguments.
	 */
	@SuppressWarnings("unchecked")
	public Object callOrApply(String serviceName, Object[] arguments, Diagnostic diagnostic) {
		try {
			Object result;
			if (arguments[0] instanceof List) {
				List<Object> list = (List<Object>)arguments[0];
				result = applyCallOnSequence(serviceName, list, arguments, diagnostic);
			} else if (arguments[0] instanceof Set) {
				Set<Object> set = (Set<Object>)arguments[0];
				result = applyCallOnSet(serviceName, set, arguments, diagnostic);
			} else {
				result = call(serviceName, arguments, diagnostic);
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
	 * @param serviceName
	 *            the name of the service.
	 * @param arguments
	 *            the service's arguments.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return the result of evaluating the specified service on the specified arguments.
	 */
	public Object collectionServiceCall(String serviceName, Object[] arguments, Diagnostic diagnostic) {
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
			return call(serviceName, newArguments, diagnostic);
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Applies a service call on a sequence of objects.
	 * 
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param origin
	 *            the sequence on which elements to apply the service
	 * @param arguments
	 *            the arguments to pass to the service.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return a sequence made of the result of calling the specified service on the specified arguments on
	 *         the origin list elements.
	 */
	private Object applyCallOnSequence(String serviceName, List<Object> origin, Object[] arguments,
			Diagnostic diagnostic) {
		try {
			List<Object> result = new ArrayList<Object>(origin.size());
			Object[] innerArguments = arguments.clone();
			for (Object obj : origin) {
				innerArguments[0] = obj;
				Object newResult = callOrApply(serviceName, innerArguments, diagnostic);
				// flatten
				if (!(newResult instanceof Nothing)) {
					if (newResult instanceof Collection) {
						result.addAll((Collection<?>)newResult);
					} else if (newResult != null) {
						result.add(newResult);
					}
				}
			}

			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException("empty argument array passed to callOrApply", e);
		}
	}

	/**
	 * Applies a service call on a set of objects.
	 * 
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param origin
	 *            the sequence on which elements to apply the service
	 * @param arguments
	 *            the arguments to pass to the service.
	 * @param diagnostic
	 *            The status to update in case of warnings or errors during this call.
	 * @return a sequence made of the result of calling the specified service on the specified arguments on
	 *         the origin set elements.
	 */
	private Object applyCallOnSet(String serviceName, Set<Object> origin, Object[] arguments,
			Diagnostic diagnostic) {
		try {
			Set<Object> result = new LinkedHashSet<Object>(origin.size());
			Object[] innerArguments = arguments.clone();
			for (Object obj : origin) {
				innerArguments[0] = obj;
				Object newResult = callOrApply(serviceName, innerArguments, diagnostic);
				// flatten
				if (!(newResult instanceof Nothing)) {
					if (newResult instanceof Collection) {
						result.addAll((Collection<?>)newResult);
					} else if (newResult != null) {
						result.add(newResult);
					}
				}
			}

			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		}

	}

	/**
	 * build up the specified service's signature for reporting.
	 * 
	 * @param serviceName
	 *            the name of the service.
	 * @param argumentTypes
	 *            the service's call argument types.
	 * @return the specified service's signature.
	 */
	protected String serviceSignature(String serviceName, Object[] argumentTypes) {
		StringBuilder builder = new StringBuilder();
		builder.append(serviceName).append('(');
		boolean first = true;
		for (Object argType : argumentTypes) {
			if (!first) {
				builder.append(',');
			} else {
				first = false;
			}
			if (argType instanceof Class<?>) {
				builder.append(((Class<?>)argType).getCanonicalName());
			} else if (argType instanceof EClass) {
				builder.append("EClass=" + ((EClass)argType).getName());
			} else if (argType == null) {
				builder.append("Object=null");
			} else {
				// should not happen
				builder.append("Object=" + argType.toString());
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
}
