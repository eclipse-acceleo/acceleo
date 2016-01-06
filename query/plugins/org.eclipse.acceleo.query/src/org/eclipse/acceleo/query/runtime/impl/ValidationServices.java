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

import com.google.common.collect.Sets;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
import org.eclipse.acceleo.query.validation.type.ClassType;
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
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
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
	 * Gets the type of a feature access.
	 * 
	 * @param receiverTypes
	 *            the target types to gets the feature from
	 * @param featureName
	 *            the feature name
	 * @return the type of a feature access
	 */
	public Set<IType> featureAccessTypes(Set<IType> receiverTypes, String featureName) {
		try {
			final Set<IType> result = new LinkedHashSet<IType>();
			for (IType receiverType : receiverTypes) {
				if (receiverType.getType() instanceof EClass) {
					EClass eClass = (EClass)receiverType.getType();
					EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
					if (feature == null) {
						result.add(nothing(UNKNOWN_FEATURE, featureName, eClass.getName()));
					} else {
						if (feature.isMany()) {
							result.add(new SequenceType(queryEnvironment, getFeatureBasicType(feature)));
						} else {
							result.add(getFeatureBasicType(feature));
						}
					}
				} else if (receiverType instanceof SequenceType) {
					result.add(getFeatureTypeOnSequence((SequenceType)receiverType, featureName));
				} else if (receiverType instanceof SetType) {
					result.add(getFeatureTypeOnSet((SetType)receiverType, featureName));
				} else {
					result.add(nothing(NON_EOBJECT_FEATURE_ACCESS, featureName, receiverType.getType()
							.toString()));
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
	 * Get the basic type of the given {@link EStructuralFeature}. It doesn't bother with the cardinality.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 * @return the basic type of the given {@link EStructuralFeature}. It doesn't bother with the cardinality.
	 */
	private IType getFeatureBasicType(EStructuralFeature feature) {
		return new EClassifierType(queryEnvironment, feature.getEType());
	}

	/**
	 * Gets the type of a feature applied on a set.
	 * 
	 * @param targetType
	 *            the target type
	 * @param featureName
	 *            the feature name
	 * @return a {@link SetType} for the given feature type
	 */
	private IType getFeatureTypeOnSet(SetType targetType, String featureName) {
		final IType result;

		final IType basicType = targetType.getCollectionType();
		final Set<IType> basicTypes = new LinkedHashSet<IType>();
		basicTypes.add(basicType);
		final IType featureAccessType = featureAccessTypes(basicTypes, featureName).iterator().next();
		// TODO should we extract Nothing from a set of nothing ? probably not

		// flatten
		if (featureAccessType instanceof ICollectionType) {
			result = new SetType(queryEnvironment, ((ICollectionType)featureAccessType).getCollectionType());
		} else {
			result = new SetType(queryEnvironment, featureAccessType);
		}

		return result;
	}

	/**
	 * Gets the type of a feature applied on a sequence.
	 * 
	 * @param targetType
	 *            the target type
	 * @param featureName
	 *            the feature name
	 * @return a {@link SequenceType} for the given feature type
	 */
	private IType getFeatureTypeOnSequence(SequenceType targetType, String featureName) {
		final IType result;

		final IType basicType = targetType.getCollectionType();
		final Set<IType> basicTypes = new LinkedHashSet<IType>();
		basicTypes.add(basicType);
		final IType featureAccessType = featureAccessTypes(basicTypes, featureName).iterator().next();
		// TODO should we extract Nothing from a list of nothing ? probably not

		// flatten
		if (featureAccessType instanceof ICollectionType) {
			result = new SequenceType(queryEnvironment, ((ICollectionType)featureAccessType)
					.getCollectionType());
		} else {
			result = new SequenceType(queryEnvironment, featureAccessType);
		}

		return result;
	}

	/**
	 * Gets the {@link IType} for the given service or {@link EOperation} name and {@link IType} of
	 * parameters.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the service name
	 * @param argTypes
	 *            the {@link IType} of parameters
	 * @return the {@link IType} for the given service or {@link EOperation} name and {@link IType} of
	 *         parameters
	 */
	public Set<IType> callType(Call call, IValidationResult validationResult, String serviceName,
			List<Set<IType>> argTypes) {
		if (argTypes.size() == 0) {
			throw new AcceleoQueryValidationException(
					"An internal error occured during validation of a query : at least one argument must be specified for service "
							+ serviceName + ".");
		}
		try {
			final Set<IType> result = new LinkedHashSet<IType>();
			CombineIterator<IType> it = new CombineIterator<IType>(argTypes);
			Map<IService, Map<List<IType>, Set<IType>>> typesPerService = new LinkedHashMap<IService, Map<List<IType>, Set<IType>>>();
			while (it.hasNext()) {
				List<IType> currentArgTypes = it.next();
				IType[] argumentTypes = getArgumentTypes(currentArgTypes);
				IService service = queryEnvironment.getLookupEngine().lookup(serviceName, argumentTypes);
				if (service == null) {
					result.addAll(callEOperationType(serviceName, currentArgTypes));
				} else {
					Map<List<IType>, Set<IType>> typeMapping = typesPerService.get(service);
					if (typeMapping == null) {
						typeMapping = new LinkedHashMap<List<IType>, Set<IType>>();
						typesPerService.put(service, typeMapping);
					}
					Set<IType> serviceTypes = getServiceTypes(call, validationResult, service,
							currentArgTypes);
					typeMapping.put(currentArgTypes, serviceTypes);
				}
			}
			for (Entry<IService, Map<List<IType>, Set<IType>>> entry : typesPerService.entrySet()) {
				Set<IType> validatedTypes = validateServiceAllTypes(entry.getKey(), entry.getValue());
				result.addAll(validatedTypes);
			}

			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException(INTERNAL_ERROR_MSG, e);
		}
	}

	/**
	 * Gets the {@link IType} for the given {@link EOperation} name and {@link IType} of parameters.
	 * 
	 * @param eOperationName
	 *            the {@link EOperation#getName() name}
	 * @param currentArgTypes
	 *            the {@link IType} of parameters
	 * @return the {@link IType} for the given service or {@link EOperation} name and {@link IType} of
	 *         parameters
	 */
	private Set<IType> callEOperationType(String eOperationName, List<IType> currentArgTypes) {
		final Set<IType> result = Sets.newLinkedHashSet();

		if (currentArgTypes.get(0).getType() instanceof EClass) {
			final List<EParameter> eParameters = getEParameters(currentArgTypes);
			final EOperation eOperation;
			if (!eParameters.contains(null)) {
				final EClass reveiverType = (EClass)eParameters.remove(0).getEType();
				eOperation = queryEnvironment.getEPackageProvider().lookupEOperation(reveiverType,
						eOperationName, eParameters);
				if (eOperation != null) {
					result.add(getEOperationType(eOperation));
				} else {
					result.add(nothing(SERVICE_EOPERATION_NOT_FOUND, serviceSignature(eOperationName,
							currentArgTypes)));
				}
			} else {
				for (int i = 0; i < currentArgTypes.size(); i++) {
					if (eParameters.get(i) == null) {
						result.add(nothing("Couldn't create EClassifier type for %s parameter %s",
								serviceSignature(eOperationName, currentArgTypes), currentArgTypes.get(i)));
					}
				}
			}
		} else {
			result.add(nothing(SERVICE_NOT_FOUND, serviceSignature(eOperationName, currentArgTypes)));
		}

		return result;
	}

	/**
	 * Gets the {@link List} of {@link EParameter} for given {@link List} of {@link IType}.
	 * 
	 * @param types
	 *            the {@link List} of {@link IType}
	 * @return the {@link List} of {@link EParameter} for given {@link List} of {@link IType}
	 */
	private List<EParameter> getEParameters(List<IType> types) {
		final List<EParameter> result = new ArrayList<EParameter>();

		for (IType type : types) {
			result.add(getEParameter(type));
		}

		return result;
	}

	/**
	 * Gets the {@link EParameter} for given {@link IType}.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return the {@link EParameter} for given {@link IType} if any can be created, <code>null</code>
	 *         otherwise
	 */
	private EParameter getEParameter(IType type) {
		final EParameter result;

		if (type instanceof SequenceType) {
			result = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
			result.setUpperBound(-1);
			result.setEType(getEParameter(((SequenceType)type).getCollectionType()).getEType());
		} else if (type instanceof EClassifierType) {
			result = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
			result.setEType(((EClassifierType)type).getType());
		} else if (type instanceof ClassType) {
			if (type.getType() == null) {
				result = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
				result.setEType(null);
			} else {
				result = null;
			}
		} else {
			throw new IllegalStateException("EParameter with no EClassifier type.");
		}

		return result;
	}

	/**
	 * Gets the return {@link IType} of a {@link IService service}.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param service
	 *            the {@link IService}
	 * @param argTypes
	 *            the {@link IType} of parameters
	 * @return the return {@link IType} of a {@link IService service}
	 */
	private Set<IType> getServiceTypes(Call call, IValidationResult validationResult, IService service,
			List<IType> argTypes) {
		return service.getType(call, this, validationResult, queryEnvironment, argTypes);
	}

	/**
	 * Gets the validated return {@link IType} of a {@link IService service}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @param allTypes
	 *            the {@link IType} of parameters to possible service types
	 * @return the validated return {@link IType} of a {@link IService service}
	 */
	private Set<IType> validateServiceAllTypes(IService service, Map<List<IType>, Set<IType>> allTypes) {
		return service.validateAllType(this, queryEnvironment, allTypes);
	}

	/**
	 * Gets the return {@link IType} of an {@link EOperation}.
	 * 
	 * @param eOperation
	 *            the {@link EOperation}
	 * @return the return {@link IType} of a {@link EOperation}
	 */
	private IType getEOperationType(EOperation eOperation) {
		final IType result;

		final IType eClassifierType = new EClassifierType(queryEnvironment, eOperation.getEType());
		if (eOperation.isMany()) {
			result = new SequenceType(queryEnvironment, eClassifierType);
		} else {
			result = eClassifierType;
		}

		return result;
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
	 *            the name of the service to call.
	 * @param argTypes
	 *            the arguments to pass to the called service.
	 * @return the result of validating the specified service on the specified arguments.
	 */
	public Set<IType> callOrApplyTypes(Call call, IValidationResult validationResult, String serviceName,
			List<Set<IType>> argTypes) {
		try {
			Set<IType> result = new LinkedHashSet<IType>();
			final List<Set<IType>> argTypesNoReceiver = new ArrayList<Set<IType>>(argTypes);
			final Set<IType> receiverTypes = argTypesNoReceiver.remove(0);
			for (IType receiverType : receiverTypes) {
				if (receiverType instanceof SequenceType) {
					result.addAll(validateCallOnSequence(call, validationResult, serviceName,
							(SequenceType)receiverType, argTypesNoReceiver));
				} else if (receiverType instanceof SetType) {
					result.addAll(validateCallOnSet(call, validationResult, serviceName,
							(SetType)receiverType, argTypesNoReceiver));
				} else {
					final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
					final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
					newReceiverTypes.add(receiverType);
					newArgTypes.add(0, newReceiverTypes);
					result.addAll(callType(call, validationResult, serviceName, newArgTypes));
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
	 * @return types resulting from the validation
	 */
	private Set<IType> validateCallOnSequence(Call call, IValidationResult validationResult,
			String serviceName, SequenceType receiverType, List<Set<IType>> argTypesNoReceiver) {
		final Set<IType> result = new LinkedHashSet<IType>();

		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			final Set<IType> rawResultTypes = callOrApplyTypes(call, validationResult, serviceName,
					newArgTypes);
			for (IType rawResultType : rawResultTypes) {
				if (!(rawResultType instanceof NothingType)) {
					// flatten
					if (rawResultType instanceof ICollectionType) {
						result.add(new SequenceType(queryEnvironment, ((ICollectionType)rawResultType)
								.getCollectionType()));
					} else {
						result.add(new SequenceType(queryEnvironment, rawResultType));
					}
				}
			}
			if (result.size() == 0) {
				// TODO check the message... and check if needed this problem should already be reported.
				result.add(nothing("%s service call on %s produce nothing.", serviceName, receiverType
						.getCollectionType()));
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply "
					+ serviceName, e);
		}
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
	 * @return types resulting from the validation
	 */
	private Set<IType> validateCallOnSet(Call call, IValidationResult validationResult, String serviceName,
			SetType receiverType, List<Set<IType>> argTypesNoReceiver) {
		final Set<IType> result = new LinkedHashSet<IType>();

		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			final Set<IType> rawResultTypes = callOrApplyTypes(call, validationResult, serviceName,
					newArgTypes);
			for (IType rawResultType : rawResultTypes) {
				if (!(rawResultType instanceof NothingType)) {
					// flatten
					if (rawResultType instanceof ICollectionType) {
						result.add(new SetType(queryEnvironment, ((ICollectionType)rawResultType)
								.getCollectionType()));
					} else {
						result.add(new SetType(queryEnvironment, rawResultType));
					}
				}
			}
			if (result.size() == 0) {
				// TODO check the message... and check if needed this problem should already be reported.
				result.add(nothing("%s service call on %s produce nothing.", serviceName, receiverType
						.getCollectionType()));
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply "
					+ serviceName, e);
		}
	}

	/**
	 * Calls a collection's service.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param serviceName
	 *            the name of the service.
	 * @param argTypes
	 *            the service's arguments.
	 * @return the result of validating the specified service on the specified arguments.
	 */
	public Set<IType> collectionServiceCallTypes(Call call, IValidationResult validationResult,
			String serviceName, List<Set<IType>> argTypes) {
		List<Set<IType>> newArguments = new ArrayList<Set<IType>>(argTypes);
		try {
			final Set<IType> receiverTypes = newArguments.remove(0);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			for (IType receiverType : receiverTypes) {
				if (receiverType instanceof ClassType && receiverType.getType() == null) {
					// Call on the NullLiteral
					newReceiverTypes
							.add(new SetType(
									queryEnvironment,
									nothing("The receiving Collection was empty due to a null value being wrapped as a Collection.")));
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
	 * Gets {@link IType} from a {@link Type}.
	 * 
	 * @param type
	 *            the {@link Type}
	 * @return {@link IType} from a {@link Type}
	 * @see ValidationServices#getIType(Class)
	 */
	public Set<IType> getIType(Type type) {
		final Set<IType> result = new LinkedHashSet<IType>();

		if (type instanceof ParameterizedType) {
			final Class<?> cls = (Class<?>)((ParameterizedType)type).getRawType();
			if (List.class.isAssignableFrom(cls)) {
				for (IType t : getIType(((ParameterizedType)type).getActualTypeArguments()[0])) {
					result.add(new SequenceType(queryEnvironment, t));
				}
			} else if (Set.class.isAssignableFrom(cls)) {
				for (IType t : getIType(((ParameterizedType)type).getActualTypeArguments()[0])) {
					result.add(new SetType(queryEnvironment, t));
				}
			} else {
				result.add(new ClassType(queryEnvironment, cls));
			}
		} else if (type instanceof Class<?>) {
			final Class<?> cls = (Class<?>)type;
			// TODO double check this it seems wrong
			result.addAll(getIType(cls));
		} else {
			result.add(new ClassType(queryEnvironment, Object.class));
		}

		return result;
	}

	/**
	 * Gets {@link IType} from a {@link Class}.
	 * 
	 * @param cls
	 *            the {@link Class}
	 * @return {@link IType} from a {@link Class}
	 * @see ValidationServices#getIType(Type)
	 */
	private Set<IType> getIType(final Class<?> cls) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final Set<EClassifier> classifiers = queryEnvironment.getEPackageProvider().getEClass(cls);
		if (List.class.isAssignableFrom(cls)) {
			result.add(new SequenceType(queryEnvironment, new ClassType(queryEnvironment, Object.class)));
		} else if (Set.class.isAssignableFrom(cls)) {
			result.add(new SetType(queryEnvironment, new ClassType(queryEnvironment, Object.class)));
		} else if (classifiers != null) {
			for (EClassifier eCls : classifiers) {
				result.add(new EClassifierType(queryEnvironment, eCls));
			}
		} else {
			result.add(new ClassType(queryEnvironment, cls));
		}

		return result;
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
			if (type1.isAssignableFrom(type2) || type1.getType() == EcorePackage.eINSTANCE.getEObject()) {
				if (type2 instanceof EClassifierLiteralType) {
					result = new EClassifierType(queryEnvironment, ((EClassifierLiteralType)type2).getType());
				} else {
					result = type2;
				}
			} else if (type2.isAssignableFrom(type1)
					|| type2.getType() == EcorePackage.eINSTANCE.getEObject()) {
				if (type1 instanceof EClassifierLiteralType) {
					result = new EClassifierType(queryEnvironment, ((EClassifierLiteralType)type1).getType());
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

		final Set<EClass> intersection = Sets.intersection(subTypes1, subTypes2);

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
		} else if (type1.getType() instanceof EClass && type2.getType() instanceof EClass) {
			for (EClass eCls : getSubTypesTopIntersection((EClass)type1.getType(), (EClass)type2.getType())) {
				result.add(new EClassifierType(getQueryEnvironment(), eCls));
			}
		}

		return result;
	}

}
