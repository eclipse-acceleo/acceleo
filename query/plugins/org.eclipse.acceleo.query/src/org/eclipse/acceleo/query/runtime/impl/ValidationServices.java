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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
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
	 *            the {@link IQueryEnvironment} to use during evaluation
	 * @param doLog
	 *            when <code>true</code> the resulting instance will log error and warning messages.
	 */
	public ValidationServices(IQueryEnvironment queryEnv, boolean doLog) {
		super(queryEnv, doLog);
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
							result.add(new SequenceType(getFeatureBasicType(feature)));
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
		return new EClassifierType(feature.getEType());
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
			result = new SetType(((ICollectionType)featureAccessType).getCollectionType());
		} else {
			result = new SetType(featureAccessType);
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
			result = new SequenceType(((ICollectionType)featureAccessType).getCollectionType());
		} else {
			result = new SequenceType(featureAccessType);
		}

		return result;
	}

	/**
	 * Gets the {@link IType} for the given service name and {@link IType} of parameters.
	 * 
	 * @param serviceName
	 *            the service name
	 * @param argTypes
	 *            the {@link IType} of parameters
	 * @return the {@link IType} for the given service name and {@link IType} of parameters
	 */
	public Set<IType> callType(String serviceName, List<Set<IType>> argTypes) {
		if (argTypes.size() == 0) {
			throw new AcceleoQueryValidationException(
					"An internal error occured during validation of a query : at least one argument must be specified for service "
							+ serviceName + ".");
		}
		try {
			final Set<IType> result = new LinkedHashSet<IType>();
			CombineIterator<IType> it = new CombineIterator<IType>(argTypes);
			while (it.hasNext()) {
				List<IType> currentArgTypes = it.next();
				Class<?>[] argumentTypes = getArgumentTypes(currentArgTypes);
				IService service = this.lookupEngine.lookup(serviceName, argumentTypes);
				if (service == null) {
					if (currentArgTypes.get(0).getType() instanceof EClass) {
						final List<EParameter> eParameters = getEParameters(currentArgTypes);
						final EClass reveiverType = (EClass)eParameters.remove(0).getEType();
						final EOperation eOperation = ePackageProvider.lookupEOperation(reveiverType,
								serviceName, eParameters);
						if (eOperation != null) {
							result.add(getEOperationType(eOperation));
						} else {
							result.add(nothing(SERVICE_EOPERATION_NOT_FOUND, serviceSignature(serviceName,
									currentArgTypes)));
						}
					} else {
						result.add(nothing(SERVICE_NOT_FOUND, serviceSignature(serviceName, currentArgTypes)));
					}
				} else {
					result.addAll(getServiceType(service, currentArgTypes));
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
	 * @return the {@link EParameter} for given {@link IType}
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
		} else if (type instanceof ClassType && type.getType() == null) {
			result = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
			result.setEType(null);
		} else {
			throw new IllegalStateException("EParameter with no EClassifier type.");
		}

		return result;
	}

	/**
	 * Gets the return {@link IType} of a {@link IService service}.
	 * 
	 * @param service
	 *            the {@link IService}
	 * @param argTypes
	 *            the {@link IType} of parameters
	 * @return the return {@link IType} of a {@link IService service}
	 */
	private Set<IType> getServiceType(IService service, List<IType> argTypes) {
		return service.getType(this, ePackageProvider, argTypes);
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

		final IType eClassifierType = new EClassifierType(eOperation.getEType());
		if (eOperation.isMany()) {
			result = new SequenceType(eClassifierType);
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
	 * @param serviceName
	 *            the name of the service to call.
	 * @param argTypes
	 *            the arguments to pass to the called service.
	 * @return the result of validating the specified service on the specified arguments.
	 */
	public Set<IType> callOrApplyTypes(String serviceName, List<Set<IType>> argTypes) {
		try {
			Set<IType> result = new LinkedHashSet<IType>();
			final List<Set<IType>> argTypesNoReceiver = new ArrayList<Set<IType>>(argTypes);
			final Set<IType> receiverTypes = argTypesNoReceiver.remove(0);
			for (IType receiverType : receiverTypes) {
				if (receiverType instanceof SequenceType) {
					result.addAll(validateCallOnSequence(serviceName, (SequenceType)receiverType,
							argTypesNoReceiver));
				} else if (receiverType instanceof SetType) {
					result.addAll(validateCallOnSet(serviceName, (SetType)receiverType, argTypesNoReceiver));
				} else {
					final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
					final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
					newReceiverTypes.add(receiverType);
					newArgTypes.add(0, newReceiverTypes);
					result.addAll(callType(serviceName, newArgTypes));
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
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param receiverType
	 *            the receiver type on which elements to validate the service
	 * @param argTypesNoReceiver
	 *            the argument types to pass to the service
	 * @return types resulting from the validation
	 */
	private Set<IType> validateCallOnSequence(String serviceName, SequenceType receiverType,
			List<Set<IType>> argTypesNoReceiver) {
		final Set<IType> result = new LinkedHashSet<IType>();

		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			final Set<IType> rawResultTypes = callOrApplyTypes(serviceName, newArgTypes);
			for (IType rawResultType : rawResultTypes) {
				if (!(rawResultType instanceof NothingType)) {
					// flatten
					if (rawResultType instanceof ICollectionType) {
						result.add(new SequenceType(((ICollectionType)rawResultType).getCollectionType()));
					} else {
						result.add(new SequenceType(rawResultType));
					}
				}
			}
			if (result.size() == 0) {
				// TODO check the message... and check if needed this problem should already be reported.
				result.add(new NothingType(serviceName + " service call on "
						+ receiverType.getCollectionType() + " produce nothing."));
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply", e);
		}
	}

	/**
	 * Validates a service call on a set of objects.
	 * 
	 * @param serviceName
	 *            the name of the service to be called.
	 * @param receiverType
	 *            the receiver type on which elements to validate the service
	 * @param argTypesNoReceiver
	 *            the argument types to pass to the service
	 * @return types resulting from the validation
	 */
	private Set<IType> validateCallOnSet(String serviceName, SetType receiverType,
			List<Set<IType>> argTypesNoReceiver) {
		final Set<IType> result = new LinkedHashSet<IType>();

		try {
			final List<Set<IType>> newArgTypes = new ArrayList<Set<IType>>(argTypesNoReceiver);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			newReceiverTypes.add(receiverType.getCollectionType());
			newArgTypes.add(0, newReceiverTypes);
			final Set<IType> rawResultTypes = callOrApplyTypes(serviceName, newArgTypes);
			for (IType rawResultType : rawResultTypes) {
				if (!(rawResultType instanceof NothingType)) {
					// flatten
					if (rawResultType instanceof ICollectionType) {
						result.add(new SetType(((ICollectionType)rawResultType).getCollectionType()));
					} else {
						result.add(new SetType(rawResultType));
					}
				}
			}
			if (result.size() == 0) {
				// TODO check the message... and check if needed this problem should already be reported.
				result.add(new NothingType(serviceName + " service call on "
						+ receiverType.getCollectionType() + " produce nothing."));
			}
			return result;
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryValidationException("empty argument array passed to callOrApply", e);
		}
	}

	/**
	 * Calls a collection's service.
	 * 
	 * @param serviceName
	 *            the name of the service.
	 * @param argTypes
	 *            the service's arguments.
	 * @return the result of validating the specified service on the specified arguments.
	 */
	public Set<IType> collectionServiceCallTypes(String serviceName, List<Set<IType>> argTypes) {
		List<Set<IType>> newArguments = new ArrayList<Set<IType>>(argTypes);
		try {
			final Set<IType> receiverTypes = newArguments.remove(0);
			final Set<IType> newReceiverTypes = new LinkedHashSet<IType>();
			for (IType receiverType : receiverTypes) {
				if (!(receiverType instanceof ICollectionType) && !(receiverTypes instanceof NothingType)) {
					// implicit set conversion.
					newReceiverTypes.add(new SetType(receiverType));
				} else {
					newReceiverTypes.add(receiverType);
				}
			}
			newArguments.add(0, newReceiverTypes);
			return callType(serviceName, newArguments);
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
					result.add(new SequenceType(t));
				}
			} else if (Set.class.isAssignableFrom(cls)) {
				for (IType t : getIType(((ParameterizedType)type).getActualTypeArguments()[0])) {
					result.add(new SetType(t));
				}
			} else {
				result.add(new SetType(new ClassType(cls)));
			}
		} else if (type instanceof Class<?>) {
			final Class<?> cls = (Class<?>)type;
			result.addAll(getIType(cls));
		} else {
			result.add(new ClassType(Object.class));
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
	public Set<IType> getIType(final Class<?> cls) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final Set<EClassifier> classifiers = this.ePackageProvider.getEClass(cls);
		if (List.class.isAssignableFrom(cls)) {
			result.add(new SequenceType(new ClassType(Object.class)));
		} else if (Set.class.isAssignableFrom(cls)) {
			result.add(new SetType(new ClassType(Object.class)));
		} else if (classifiers != null) {
			for (EClassifier eCls : classifiers) {
				result.add(new EClassifierType(eCls));
			}
		} else {
			result.add(new ClassType(cls));
		}

		return result;
	}

	/**
	 * Gets the {@link Error} types.
	 * 
	 * @param error
	 *            the {@link Error}
	 * @return the {@link Error} types
	 */
	public Set<IType> getErrorTypes(Error error) {
		final Set<IType> result = new HashSet<IType>();

		/*
		 * TODO when there are parsing errors, provide a better message than just the error EClass
		 */
		final String message = error.eClass().getName();
		result.add(new NothingType(message));

		return result;
	}

}