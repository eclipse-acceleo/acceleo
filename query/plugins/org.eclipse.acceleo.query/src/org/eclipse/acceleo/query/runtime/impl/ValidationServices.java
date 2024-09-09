/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.ClassLiteralType;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.CollectionType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Implementation of the elementary language validation services like variable typing and service call typing.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ValidationServices extends AbstractLanguageServices {

	/**
	 * Log message used when an internal validation error is encountered.
	 */
	public static final String INTERNAL_ERROR_MSG = "An internal error occured during validation of a query";

	/**
	 * Log message used when a variable is present in the types map but has no types.
	 */
	private static final String VARIABLE_HAS_NO_TYPES = "The %s variable has no types";

	/**
	 * Should never happen message.
	 */
	private static final String SHOULD_NEVER_HAPPEN = "should never happen";

	/**
	 * Constructor.
	 * 
	 * @param queryEnv
	 *            the {@link IReadOnlyQueryEnvironment} to use during evaluation
	 */
	public ValidationServices(IReadOnlyQueryEnvironment queryEnv) {
		super(queryEnv);
	}

	/**
	 * returns the nothing value and logs the specified error message.
	 * 
	 * @param message
	 *            the message to log.
	 * @param msgArgs
	 *            the object arguments used to format the log message.
	 * @return a nothing {@link NothingValidationStatus}.
	 */
	public NothingType nothing(String message, Object... msgArgs) {
		final String formatedMessage = String.format(message, msgArgs);
		return new NothingType(formatedMessage);
	}

	/**
	 * Returns the type of the specified variable in the specified map. Returns {@link Nothing} class when the
	 * variable is not found.
	 * 
	 * @param variableTypes
	 *            the set of variable types definition in which to lookup the specified variable.
	 * @param variableName
	 *            the name of the variable to lookup in the specified map.
	 * @return Returns types of the specified variable in the specified map or {@link Nothing} class.
	 */
	public Set<IType> getVariableTypes(Map<String, Set<IType>> variableTypes, String variableName) {
		try {
			final Set<IType> res = new LinkedHashSet<IType>();
			final Set<IType> types = variableTypes.get(variableName);
			if (types != null) {
				if (types.size() > 0) {
					res.addAll(types);
				} else {
					res.add(nothing(VARIABLE_HAS_NO_TYPES, variableName));
				}
			} else {
				res.add(nothing(VARIABLE_NOT_FOUND, variableName));
			}
			return res;
		} catch (NullPointerException e) {
			throw new AcceleoQueryValidationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Gets the {@link ServicesValidationResult} for the given {@link Call} and {@link IType} of parameters.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult}
	 * @param argTypes
	 *            the {@link IType} of parameters
	 * @return the {@link ServicesValidationResult} for the given {@link Call} and {@link IType} of parameters
	 */
	public ServicesValidationResult call(Call call, IValidationResult validationResult,
			final List<Set<IType>> argTypes) {
		final ServicesValidationResult servicesValidationResult;
		final String serviceName = call.getServiceName();
		switch (call.getType()) {
			case CALLSERVICE:
				servicesValidationResult = callType(call, validationResult, serviceName, argTypes);
				break;
			case CALLORAPPLY:
				servicesValidationResult = callOrApplyTypes(call, validationResult, serviceName, argTypes);
				break;
			case COLLECTIONCALL:
				servicesValidationResult = collectionServiceCallTypes(call, validationResult, serviceName,
						argTypes);
				break;
			default:
				throw new UnsupportedOperationException(SHOULD_NEVER_HAPPEN);
		}
		return servicesValidationResult;
	}

	/**
	 * Gets the {@link ServicesValidationResult} for the given {@link IService#getName() service name} and
	 * {@link IType} of parameters.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the {@link IService#getName() service name}
	 * @param argTypes
	 *            the {@link IType} of parameters
	 * @return the {@link ServicesValidationResult}
	 */
	public ServicesValidationResult callType(Call call, IValidationResult validationResult,
			String serviceName, List<Set<IType>> argTypes) {
		if (argTypes.size() == 0) {
			throw new AcceleoQueryValidationException(
					"An internal error occured during validation of a query : at least one argument must be specified for service "
							+ serviceName + ".");
		}
		try {
			final ServicesValidationResult result = new ServicesValidationResult(queryEnvironment, this);
			final CombineIterator<IType> it = new CombineIterator<IType>(argTypes);
			final Map<IService<?>, Map<List<IType>, Set<IType>>> typesPerService = new LinkedHashMap<IService<?>, Map<List<IType>, Set<IType>>>();
			boolean serviceFound = false;
			boolean emptyCombination = !it.hasNext();
			final List<String> notFoundSignatures = new ArrayList<String>();
			while (it.hasNext()) {
				List<IType> currentArgTypes = it.next();
				IService<?> service = queryEnvironment.getLookupEngine().lookup(serviceName, currentArgTypes
						.toArray(new IType[currentArgTypes.size()]));
				if (service != null) {
					Map<List<IType>, Set<IType>> typeMapping = typesPerService.get(service);
					if (typeMapping == null) {
						typeMapping = new LinkedHashMap<List<IType>, Set<IType>>();
						typesPerService.put(service, typeMapping);
					}
					Set<IType> serviceTypes = service.getType(call, this, validationResult, queryEnvironment,
							currentArgTypes);
					typeMapping.put(currentArgTypes, serviceTypes);
					serviceFound = true;
				} else {
					notFoundSignatures.add(serviceSignature(serviceName, currentArgTypes));
				}
			}

			if (!emptyCombination) {
				if (serviceFound) {
					for (Entry<IService<?>, Map<List<IType>, Set<IType>>> entry : typesPerService
							.entrySet()) {
						final IService<?> service = entry.getKey();
						final Map<List<IType>, Set<IType>> types = entry.getValue();
						result.addServiceTypes(service, types);
					}
				} else {
					final StringBuilder builder = new StringBuilder();
					for (String signature : notFoundSignatures) {
						builder.append(String.format(SERVICE_NOT_FOUND, signature) + "\n");
					}
					result.addServiceNotFound(nothing(builder.substring(0, builder.length() - 1)));
				}
			}

			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * The callOrApply method validates an expression of the form "<exp>.<service name>(<exp>*)" The first
	 * argument in the arguments array is considered the receiver of the call. If the receiver is a collection
	 * then callOrApply is applied recursively to all elements of the collection thus returning a collection
	 * of the result of this application(nothing values not being added).
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the name of the service to call
	 * @param argTypes
	 *            the arguments to pass to the called service
	 * @return the {@link ServicesValidationResult}
	 */
	public ServicesValidationResult callOrApplyTypes(Call call, IValidationResult validationResult,
			String serviceName, List<Set<IType>> argTypes) {
		try {
			ServicesValidationResult result = new ServicesValidationResult(queryEnvironment, this);
			final List<Set<IType>> argTypesNoReceiver = new ArrayList<Set<IType>>(argTypes);
			final Set<IType> receiverTypes = argTypesNoReceiver.remove(0);
			for (IType receiverType : receiverTypes) {
				if (receiverType instanceof SequenceType) {
					result.merge(validateCallOnSequence(call, validationResult, serviceName,
							(SequenceType)receiverType, argTypesNoReceiver));
				} else if (receiverType instanceof SetType) {
					result.merge(validateCallOnSet(call, validationResult, serviceName, (SetType)receiverType,
							argTypesNoReceiver));
				} else if (receiverType instanceof CollectionType) {
					result.merge(validateCallOnCollection(call, validationResult, serviceName,
							(CollectionType)receiverType, argTypesNoReceiver));
				} else {
					final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
					final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
					newReceiverTypes.add(receiverType);
					newArgTypes.add(0, newReceiverTypes);
					result.merge(callType(call, validationResult, serviceName, newArgTypes));
				}
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Validates a service call on a sequence of objects.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param receiverType
	 *            the receiver type on which elements to validate the service
	 * @param argTypesNoReceiver
	 *            the argument types to pass to the service
	 * @return the {@link ServicesValidationResult}
	 */
	private ServicesValidationResult validateCallOnSequence(Call call, IValidationResult validationResult,
			String serviceName, SequenceType receiverType, List<Set<IType>> argTypesNoReceiver) {
		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			ServicesValidationResult result = callOrApplyTypes(call, validationResult, serviceName,
					newArgTypes);
			flattenSequence(result);
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply "
					+ serviceName, e);
		}
	}

	/**
	 * Flatten {@link List} on the given {@link ServicesValidationResult}.
	 * 
	 * @param result
	 *            the {@link ServicesValidationResult}
	 */
	protected void flattenSequence(ServicesValidationResult result) {
		result.flattenSequence();
	}

	/**
	 * Validates a service call on a set of objects.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param receiverType
	 *            the receiver type on which elements to validate the service
	 * @param argTypesNoReceiver
	 *            the argument types to pass to the service
	 * @return the {@link ServicesValidationResult}
	 */
	private ServicesValidationResult validateCallOnSet(Call call, IValidationResult validationResult,
			String serviceName, SetType receiverType, List<Set<IType>> argTypesNoReceiver) {
		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			ServicesValidationResult result = callOrApplyTypes(call, validationResult, serviceName,
					newArgTypes);
			flattenSet(result);
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply "
					+ serviceName, e);
		}
	}

	/**
	 * Flatten {@link Set} on the given {@link ServicesValidationResult}.
	 * 
	 * @param result
	 *            the {@link ServicesValidationResult} to flatten
	 */
	protected void flattenSet(ServicesValidationResult result) {
		result.flattenSet();
	}

	/**
	 * Validates a service call on a collection of objects.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param receiverType
	 *            the receiver type on which elements to validate the service
	 * @param argTypesNoReceiver
	 *            the argument types to pass to the service
	 * @return the {@link ServicesValidationResult}
	 */
	private ServicesValidationResult validateCallOnCollection(Call call, IValidationResult validationResult,
			String serviceName, CollectionType receiverType, List<Set<IType>> argTypesNoReceiver) {
		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			ServicesValidationResult result = callOrApplyTypes(call, validationResult, serviceName,
					newArgTypes);
			flattenCollection(result);
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply "
					+ serviceName, e);
		}
	}

	/**
	 * Flatten {@link Collection} on the given {@link ServicesValidationResult}.
	 * 
	 * @param result
	 *            the {@link ServicesValidationResult} to flatten
	 */
	protected void flattenCollection(ServicesValidationResult result) {
		result.flattenCollection();
	}

	/**
	 * Calls a collection's service.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the {@link IService#getName() the service name}
	 * @param argTypes
	 *            {@link IService#getParameterTypes(IReadOnlyQueryEnvironment) service parameter types}
	 * @return the {@link ServicesValidationResult}
	 */
	public ServicesValidationResult collectionServiceCallTypes(Call call, IValidationResult validationResult,
			String serviceName, List<Set<IType>> argTypes) {
		List<Set<IType>> newArguments = new ArrayList<Set<IType>>(argTypes);
		try {
			final Set<IType> receiverTypes = newArguments.remove(0);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			for (IType receiverType : receiverTypes) {
				if (receiverType instanceof ClassType && receiverType.getType() == null) {
					// Call on the NullLiteral
					newReceiverTypes.add(new SetType(queryEnvironment, nothing(
							"The Collection was empty due to a null value being wrapped as a Collection.")));
				} else if (!(receiverType instanceof ICollectionType)
						&& !(receiverType instanceof NothingType)) {
					// implicit set conversion.
					newReceiverTypes.add(new SetType(queryEnvironment, receiverType));
				} else {
					newReceiverTypes.add(receiverType);
				}
			}
			newArguments.add(0, newReceiverTypes);
			return callType(call, validationResult, serviceName, newArguments);
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Build up the specified service's signature for reporting.
	 * 
	 * @param serviceName
	 *            the name of the service.
	 * @param argumentTypes
	 *            the service's call argument types.
	 * @return the specified service's signature.
	 */
	protected String serviceSignature(String serviceName, List<IType> argumentTypes) {
		StringBuilder builder = new StringBuilder();
		builder.append(serviceName).append('(');
		boolean first = true;
		for (IType argType : argumentTypes) {
			if (!first) {
				builder.append(',');
			} else {
				first = false;
			}
			builder.append(argType.toString());
		}
		return builder.append(')').toString();
	}

	/**
	 * Gets the {@link Error} types.
	 * 
	 * @param validationResult
	 *            the {@link IValidationResult}
	 * @param error
	 *            the {@link Error}
	 * @return the {@link Error} types
	 */
	public Set<IType> getErrorTypes(IValidationResult validationResult, Error error) {
		final Set<IType> result = new LinkedHashSet<IType>();

		for (Diagnostic diagnostic : validationResult.getAstResult().getDiagnostic().getChildren()) {
			if (diagnostic.getData().contains(error)) {
				result.add(nothing(diagnostic.getMessage()));
			}
		}

		return result;
	}

	/**
	 * Gets the lower {@link IType} from the two given {@link IType} if they are in the same
	 * {@link IType#isAssignableFrom(IType) hierarchy} ({@link EClassifierLiteralType} are converted to
	 * {@link EClassifierType}).
	 * 
	 * @param type1
	 *            the first {@link IType}
	 * @param type2
	 *            the second {@link IType}
	 * @return the lower {@link IType} from the two given {@link IType} if they are in the same
	 *         {@link IType#isAssignableFrom(IType) hierarchy}, <code>null</code> otherwise
	 */
	public IType lower(IType type1, IType type2) {
		final IType result;

		if (type1 == null || type2 == null) {
			result = null;
		} else {
			if (type1.isAssignableFrom(type2) || type1.getType() == EcorePackage.eINSTANCE.getEObject()
					|| type1.getType() == EObject.class) {
				if (type2 instanceof EClassifierLiteralType) {
					result = new EClassifierType(queryEnvironment, ((EClassifierLiteralType)type2).getType());
				} else if (type2 instanceof ClassLiteralType) {
					result = new ClassType(queryEnvironment, ((ClassLiteralType)type2).getType());
				} else {
					result = type2;
				}
			} else if (type2.isAssignableFrom(type1) || type2.getType() == EcorePackage.eINSTANCE.getEObject()
					|| type2.getType() == EObject.class) {
				if (type1 instanceof EClassifierLiteralType) {
					result = new EClassifierType(queryEnvironment, ((EClassifierLiteralType)type1).getType());
				} else if (type1 instanceof ClassLiteralType) {
					result = new ClassType(queryEnvironment, ((ClassLiteralType)type1).getType());
				} else {
					result = type1;
				}
			} else {
				result = null;
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of the higher {@link EClass} in the super types hierarchy inheriting from both
	 * given {@link EClass}.
	 * 
	 * @param eCls1
	 *            the first {@link EClass}
	 * @param eCls2
	 *            the second {@link EClass}
	 * @return the {@link Set} of the higher {@link EClass} in the super types hierarchy inheriting from both
	 *         given {@link EClass}
	 */
	public Set<EClass> getSubTypesTopIntersection(EClass eCls1, EClass eCls2) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		final Set<EClass> subTypes1 = queryEnvironment.getEPackageProvider().getAllSubTypes(eCls1);
		final Set<EClass> subTypes2 = queryEnvironment.getEPackageProvider().getAllSubTypes(eCls2);

		final Set<EClass> intersection = new LinkedHashSet<EClass>(subTypes1);
		intersection.retainAll(subTypes2);

		for (EClass eCls : intersection) {
			final boolean isTopEClass = Collections.disjoint(eCls.getEAllSuperTypes(), intersection);
			if (isTopEClass) {
				result.add(eCls);
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link IType} that are both type1 and type2.
	 * 
	 * @param type1
	 *            the first {@link IType}
	 * @param type2
	 *            the second {@link IType}
	 * @return the {@link Set} of {@link IType} that are both type1 and type2
	 */
	public Set<IType> intersection(IType type1, IType type2) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final IType lowerType = lower(type1, type2);
		if (lowerType != null) {
			result.add(lowerType);
		} else if (type1 != null && type2 != null) {
			Set<EClass> eClasses1 = getEClasses(type1);
			Set<EClass> eClasses2 = getEClasses(type2);
			for (EClass eCls1 : eClasses1) {
				for (EClass eCls2 : eClasses2) {
					for (EClass eCls : getSubTypesTopIntersection(eCls1, eCls2)) {
						result.add(new EClassifierType(getQueryEnvironment(), eCls));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link EClass} form the given {@link IType}.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return the {@link Set} of {@link EClass} form the given {@link IType}
	 */
	public Set<EClass> getEClasses(IType type) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		if (type.getType() instanceof EClass) {
			result.add((EClass)type.getType());
		} else if (type.getType() instanceof Class) {
			final Set<EClassifier> eClassifiers = queryEnvironment.getEPackageProvider().getEClassifiers(
					(Class<?>)type.getType());
			if (eClassifiers != null) {
				for (EClassifier eClassifier : eClassifiers) {
					if (eClassifier instanceof EClass) {
						result.add((EClass)eClassifier);
					}
				}
			}
		}

		return result;
	}

}
