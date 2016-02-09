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
package org.eclipse.acceleo.query.services;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Other;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.annotations.api.documentation.Throw;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClassifier;

//@formatter:off
@ServiceProvider(
	value = "Services available for Collections"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class CollectionServices extends AbstractServiceProvider {

	/**
	 * Message separator.
	 */
	private static final String MESSAGE_SEPARATOR = "\n ";

	/** Externalized here to avoid multiple uses. */
	private static final String SUM_ONLY_NUMERIC_ERROR = "Sum can only be used on a collection of numbers.";

	/**
	 * Exists {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class BooleanLambdaService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private BooleanLambdaService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(queryEnvironment, lambdaExpressionType)) {
				result.addAll(super.getType(call, services, validationResult, queryEnvironment, argTypes));
			} else {
				result.add(services.nothing("expression in %s must return a boolean", getName()));
			}
			return result;
		}
	}

	/**
	 * Any {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class AnyService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private AnyService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(queryEnvironment, lambdaExpressionType)) {
				final Expression expression;
				if (call != null) {
					expression = ((Lambda)call.getArguments().get(1)).getExpression();
				} else {
					expression = null;
				}
				final Set<IType> inferredTypes;
				if (validationResult != null) {
					inferredTypes = validationResult.getInferredVariableTypes(expression, Boolean.TRUE).get(
							lambdaType.getLambdaEvaluatorName());
				} else {
					inferredTypes = null;
				}
				if (inferredTypes == null) {
					result.add(((ICollectionType)argTypes.get(0)).getCollectionType());
				} else {
					result.addAll(inferredTypes);
				}
			} else {
				result.add(services.nothing("expression in an any must return a boolean"));
			}
			return result;
		}
	}

	/**
	 * Including {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class IncludingService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private IncludingService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes.get(0))
					.getCollectionType()));
			result.add(createReturnCollectionWithType(queryEnvironment, argTypes.get(1)));
			return result;
		}

		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final StringBuilder builder = new StringBuilder();

			for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				for (IType type : entry.getValue()) {
					if (((ICollectionType)type).getCollectionType() instanceof NothingType) {
						builder.append(MESSAGE_SEPARATOR);
						builder.append(((NothingType)((ICollectionType)type).getCollectionType())
								.getMessage());
					} else {
						result.add(type);
					}
				}
			}
			if (result.isEmpty()) {
				result.add(createReturnCollectionWithType(queryEnvironment, services.nothing(
						"Nothing left after %s:" + builder.toString(), getName())));
			}

			return result;
		}
	}

	/**
	 * Collect {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class CollectService extends JavaMethodService {
		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private CollectService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final IType receiverType = argTypes.get(0);
			if (receiverType instanceof NothingType) {
				result.add(createReturnCollectionWithType(queryEnvironment, receiverType));
			} else if (receiverType instanceof ICollectionType
					&& ((ICollectionType)receiverType).getCollectionType() instanceof NothingType) {
				result.add(receiverType);
			} else {
				final LambdaType lambdaType = (LambdaType)argTypes.get(1);
				// flatten if needed
				result.add(createReturnCollectionWithType(queryEnvironment, flatten(lambdaType
						.getLambdaExpressionType())));
			}
			return result;
		}

		private IType flatten(IType type) {
			if (type instanceof ICollectionType) {
				return flatten(((ICollectionType)type).getCollectionType());
			}
			return type;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.Map)
		 */
		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			for (Map.Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				for (IType type : entry.getValue()) {
					final IType collectionType = ((ICollectionType)type).getCollectionType();
					if (collectionType instanceof ClassType && ((ClassType)collectionType).getType() == null) {
						// This is the null literal, which we don't want in our result
						// and will be stripped at runtime.
					} else {
						result.add(type);
					}
				}
			}
			return result;
		}
	}

	/**
	 * Closure {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class ClosureService extends JavaMethodService {
		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private ClosureService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.JavaMethodService#getType(org.eclipse.acceleo.query.ast.Call,
		 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      org.eclipse.acceleo.query.runtime.IValidationResult,
		 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
		 */
		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final IType lambdaExpressionType = ((LambdaType)argTypes.get(1)).getLambdaExpressionType();

			// FIXME need to make the closure on type as well... it's not possible for the moment because we
			// need variable types...
			final IType receiverType = argTypes.get(0);
			if (receiverType instanceof NothingType) {
				result.add(createReturnCollectionWithType(queryEnvironment, receiverType));
			} else if (receiverType instanceof ICollectionType
					&& ((ICollectionType)receiverType).getCollectionType() instanceof NothingType) {
				result.add(receiverType);
			} else if (lambdaExpressionType instanceof ICollectionType) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)lambdaExpressionType)
						.getCollectionType()));
			} else {
				result.add(new SetType(queryEnvironment, lambdaExpressionType));
			}

			return result;
		}
	}

	/**
	 * Sum {@link IService}.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private static final class SumService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private SumService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			if (!argTypes.isEmpty() && argTypes.get(0) instanceof ICollectionType) {
				IType argType = ((ICollectionType)argTypes.get(0)).getCollectionType();

				IType intType = new ClassType(queryEnvironment, Integer.class);
				IType longType = new ClassType(queryEnvironment, Long.class);
				IType numberType = new ClassType(queryEnvironment, Number.class);

				if (intType.isAssignableFrom(argType) || longType.isAssignableFrom(argType)) {
					result.add(longType);
				} else if (numberType.isAssignableFrom(argType)) {
					// any number that is not an int or long is widened to Double
					result.add(new ClassType(queryEnvironment, Double.class));
				} else {
					result.add(services.nothing(SUM_ONLY_NUMERIC_ERROR));
				}
			}
			return result;
		}

		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			IType currentResult = null;

			for (Map.Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				IType longType = new ClassType(queryEnvironment, Long.class);
				IType doubleType = new ClassType(queryEnvironment, Double.class);

				IType returnType = entry.getValue().iterator().next();
				if (currentResult == null) {
					currentResult = returnType;
				} else if (currentResult.equals(doubleType) || returnType.equals(doubleType)) {
					currentResult = doubleType;
				} else if (returnType.equals(longType)) {
					currentResult = longType;
				} else {
					currentResult = services.nothing(SUM_ONLY_NUMERIC_ERROR);
					break;
				}
			}

			Set<IType> result = new LinkedHashSet<IType>();
			result.add(currentResult);
			return result;
		}
	}

	/**
	 * Select {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class SelectService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private SelectService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(queryEnvironment, lambdaExpressionType)) {
				final Expression expression;
				if (call != null) {
					expression = ((Lambda)call.getArguments().get(1)).getExpression();
				} else {
					expression = null;
				}
				final Set<IType> inferredTypes;
				if (validationResult != null) {
					inferredTypes = validationResult.getInferredVariableTypes(expression, Boolean.TRUE).get(
							lambdaType.getLambdaEvaluatorName());
				} else {
					inferredTypes = null;
				}
				if (inferredTypes == null) {
					result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes
							.get(0)).getCollectionType()));
				} else {
					for (IType inferredType : inferredTypes) {
						result.add(createReturnCollectionWithType(queryEnvironment, inferredType));
					}
				}
			} else {
				result.add(createReturnCollectionWithType(queryEnvironment, services
						.nothing("expression in a select must return a boolean")));
			}
			return result;
		}
	}

	/**
	 * Reject {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class RejectService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private RejectService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(queryEnvironment, lambdaExpressionType)) {
				final Expression expression;
				if (call != null) {
					expression = ((Lambda)call.getArguments().get(1)).getExpression();
				} else {
					expression = null;
				}
				final Set<IType> inferredTypes;
				if (validationResult != null) {
					inferredTypes = validationResult.getInferredVariableTypes(expression, Boolean.FALSE).get(
							lambdaType.getLambdaEvaluatorName());
				} else {
					inferredTypes = null;
				}
				if (inferredTypes == null) {
					result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes
							.get(0)).getCollectionType()));
				} else {
					for (IType inferredType : inferredTypes) {
						result.add(createReturnCollectionWithType(queryEnvironment, inferredType));
					}
				}
			} else {
				result.add(createReturnCollectionWithType(queryEnvironment, services
						.nothing("expression in a reject must return a boolean")));
			}

			return result;
		}
	}

	/**
	 * A {@link Service} returning the raw collection type of the first argument.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class FirstArgumentRawCollectionType extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private FirstArgumentRawCollectionType(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			result.add(((ICollectionType)argTypes.get(0)).getCollectionType());

			return result;
		}
	}

	/**
	 * A {@link Service} returning the same collection type as the method with the raw collection type of
	 * first argument.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class ReturnCollectionTypeWithFirstArgumentRawCollectionType extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private ReturnCollectionTypeWithFirstArgumentRawCollectionType(Method serviceMethod,
				Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes.get(0))
					.getCollectionType()));

			return result;
		}
	}

	/**
	 * A {@link Service} returning the same collection type as the method with the raw collection type of
	 * first and second arguments.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType(Method serviceMethod,
				Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final IType arg1Type;
			if (argTypes.get(0) instanceof ICollectionType) {
				arg1Type = ((ICollectionType)argTypes.get(0)).getCollectionType();
			} else if (argTypes.get(0) instanceof NothingType) {
				arg1Type = argTypes.get(0);
			} else {
				arg1Type = services.nothing(
						"%s can only be called on collections, but %s was used as its receiver.", getName(),
						argTypes.get(0));
			}
			final IType arg2Type;
			if (argTypes.get(1) instanceof ICollectionType) {
				arg2Type = ((ICollectionType)argTypes.get(1)).getCollectionType();
			} else if (argTypes.get(1) instanceof NothingType) {
				arg2Type = argTypes.get(1);
			} else {
				arg2Type = services.nothing(
						"%s can only be called on collections, but %s was used as its argument.", getName(),
						argTypes.get(1));
			}

			result.add(createReturnCollectionWithType(queryEnvironment, arg1Type));
			result.add(createReturnCollectionWithType(queryEnvironment, arg2Type));

			return result;
		}

		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final StringBuilder builder = new StringBuilder();

			for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				for (IType type : entry.getValue()) {
					if (((ICollectionType)type).getCollectionType() instanceof NothingType) {
						builder.append(MESSAGE_SEPARATOR);
						builder.append(((NothingType)((ICollectionType)type).getCollectionType())
								.getMessage());
					} else {
						result.add(type);
					}
				}
			}

			if (result.isEmpty()) {
				IType nothing = services.nothing("Nothing left after %s:" + builder.toString(), getName());
				result.add(createReturnCollectionWithType(queryEnvironment, nothing));
			}

			return result;
		}

	}

	/**
	 * A {@link Service} returning the
	 * {@link org.eclipse.acceleo.query.validation.type.EClassifierLiteralType#getType() classifier literal
	 * type} of the second argument in the {@link org.eclipse.acceleo.query.validation.type.ICollectionType
	 * ICollectionType} of the first argument.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class SecondArgumentTypeInFirstArgumentCollectionType extends FilterService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		SecondArgumentTypeInFirstArgumentCollectionType(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public java.util.Set<IType> getType(Call call, ValidationServices services,
				IValidationResult validationResult, IReadOnlyQueryEnvironment queryEnvironment,
				java.util.List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final Set<EClassifierType> rawTypes = Sets.newLinkedHashSet();

			if (argTypes.get(1) instanceof EClassifierType) {
				rawTypes.add(new EClassifierType(queryEnvironment, ((EClassifierType)argTypes.get(1))
						.getType()));
			} else if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
				for (EClassifier eCls : ((EClassifierSetLiteralType)argTypes.get(1)).getEClassifiers()) {
					rawTypes.add(new EClassifierType(queryEnvironment, eCls));
				}
			}
			for (EClassifierType rawType : rawTypes) {
				result.add(createReturnCollectionWithType(queryEnvironment, rawType));
			}

			return result;

		}
	}

	/**
	 * A {@link Service} returning the raw type of the first argument {@link ICollectionType}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class FirstCollectionTypeService extends JavaMethodService {

		/**
		 * Creates a new service instance given a method and an instance.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		public FirstCollectionTypeService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes.get(0))
					.getCollectionType()));
			return result;
		}

	}

	/**
	 * InsertAt {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class InsertAtService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private InsertAtService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes.get(0))
					.getCollectionType()));
			result.add(createReturnCollectionWithType(queryEnvironment, argTypes.get(2)));
			return result;
		}
	}

	/**
	 * Intersection {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class IntersectionService extends JavaMethodService {

		/**
		 * Constructor.
		 * 
		 * @param serviceMethod
		 *            the method that realizes the service
		 * @param serviceInstance
		 *            the instance on which the service must be called
		 */
		private IntersectionService(Method serviceMethod, Object serviceInstance) {
			super(serviceMethod, serviceInstance);
		}

		@Override
		public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
				IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			IType selfRawType = ((ICollectionType)argTypes.get(0)).getCollectionType();
			IType otherRawType = ((ICollectionType)argTypes.get(1)).getCollectionType();
			final Set<IType> resultRawTypes = services.intersection(selfRawType, otherRawType);
			if (resultRawTypes.isEmpty()) {
				resultRawTypes.add(services.nothing("Nothing left after intersection of %s and %s", argTypes
						.get(0), argTypes.get(1)));
			}
			for (IType resultRawType : resultRawTypes) {
				result.add(createReturnCollectionWithType(queryEnvironment, resultRawType));
			}

			return result;
		}

		@Override
		public Set<IType> validateAllType(ValidationServices services,
				IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final StringBuilder builder = new StringBuilder();

			for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
				for (IType type : entry.getValue()) {
					if (((ICollectionType)type).getCollectionType() instanceof NothingType) {
						builder.append(MESSAGE_SEPARATOR);
						builder.append(((NothingType)((ICollectionType)type).getCollectionType())
								.getMessage());
					} else {
						result.add(type);
					}
				}
			}

			if (result.isEmpty()) {
				result.add(createReturnCollectionWithType(queryEnvironment, services
						.nothing("Nothing left after intersection:" + builder.toString())));
			}

			return result;
		}
	}

	@Override
	protected IService getService(Method publicMethod) {
		final IService result;

		if ("filter".equals(publicMethod.getName())) {
			result = new SecondArgumentTypeInFirstArgumentCollectionType(publicMethod, this);
		} else if ("add".equals(publicMethod.getName()) || "concat".equals(publicMethod.getName())
				|| "union".equals(publicMethod.getName())) {
			result = new ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType(publicMethod, this);
		} else if ("asSequence".equals(publicMethod.getName()) || "asSet".equals(publicMethod.getName())
				|| "asOrderedSet".equals(publicMethod.getName())) {
			result = new ReturnCollectionTypeWithFirstArgumentRawCollectionType(publicMethod, this);
		} else if ("subOrderedSet".equals(publicMethod.getName())
				|| "subSequence".equals(publicMethod.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this);
		} else if ("first".equals(publicMethod.getName()) || "at".equals(publicMethod.getName())
				|| "last".equals(publicMethod.getName())) {
			result = new FirstArgumentRawCollectionType(publicMethod, this);
		} else if ("excluding".equals(publicMethod.getName()) || "sub".equals(publicMethod.getName())
				|| "reverse".equals(publicMethod.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this);
		} else if ("sortedBy".equals(publicMethod.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this);
		} else if ("reject".equals(publicMethod.getName())) {
			result = new RejectService(publicMethod, this);
		} else if ("select".equals(publicMethod.getName())) {
			result = new SelectService(publicMethod, this);
		} else if ("collect".equals(publicMethod.getName())) {
			result = new CollectService(publicMethod, this);
		} else if ("closure".equals(publicMethod.getName())) {
			result = new ClosureService(publicMethod, this);
		} else if ("including".equals(publicMethod.getName()) || "prepend".equals(publicMethod.getName())) {
			result = new IncludingService(publicMethod, this);
		} else if ("add".equals(publicMethod.getName())) {
			result = new IncludingService(publicMethod, this);
		} else if ("sep".equals(publicMethod.getName())) {
			if (publicMethod.getParameterTypes().length == 2) {
				result = new JavaMethodService(publicMethod, this) {

					@Override
					public Set<IType> getType(Call call, ValidationServices services,
							IValidationResult validationResult, IReadOnlyQueryEnvironment queryEnvironment,
							List<IType> argTypes) {
						final Set<IType> result = new LinkedHashSet<IType>();

						result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
								.getCollectionType()));
						result.add(new SequenceType(queryEnvironment, argTypes.get(1)));

						return result;
					}
				};
			} else if (publicMethod.getParameterTypes().length == 4) {
				result = new JavaMethodService(publicMethod, this) {

					@Override
					public Set<IType> getType(Call call, ValidationServices services,
							IValidationResult validationResult, IReadOnlyQueryEnvironment queryEnvironment,
							List<IType> argTypes) {
						final Set<IType> result = new LinkedHashSet<IType>();

						result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
								.getCollectionType()));
						result.add(new SequenceType(queryEnvironment, argTypes.get(1)));
						result.add(new SequenceType(queryEnvironment, argTypes.get(2)));
						result.add(new SequenceType(queryEnvironment, argTypes.get(3)));

						return result;
					}
				};
			} else {
				result = new JavaMethodService(publicMethod, this);
			}
		} else if ("any".equals(publicMethod.getName())) {
			result = new AnyService(publicMethod, this);
		} else if ("exists".equals(publicMethod.getName()) || "forAll".equals(publicMethod.getName())
				|| "one".equals(publicMethod.getName())) {
			result = new BooleanLambdaService(publicMethod, this);
		} else if ("insertAt".equals(publicMethod.getName())) {
			result = new InsertAtService(publicMethod, this);
		} else if ("intersection".equals(publicMethod.getName())) {
			result = new IntersectionService(publicMethod, this);
		} else if ("sum".equals(publicMethod.getName())) {
			result = new SumService(publicMethod, this);
		} else {
			result = new JavaMethodService(publicMethod, this);
		}
		return result;
	}

	/**
	 * Tells if the given {@link Object} is a is a boolean {@link org.eclipse.emf.ecore.EDataType EDataType}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link org.eclipse.emf.ecore.EDataType EDataType}
	 * @return <code>true</code> if the given {@link Object} is a is a boolean
	 *         {@link org.eclipse.emf.ecore.EDataType EDataType}, <code>false</code> otherwise
	 */
	private static boolean isBooleanType(IReadOnlyQueryEnvironment queryEnvironment, Object type) {
		final Class<?> typeClass;
		if (type instanceof EClassifier) {
			typeClass = queryEnvironment.getEPackageProvider().getClass((EClassifier)type);
		} else if (type instanceof ClassType) {
			typeClass = ((ClassType)type).getType();
		} else if (type instanceof Class<?>) {
			typeClass = (Class<?>)type;
		} else {
			return false;
		}

		return typeClass == Boolean.class || typeClass == boolean.class;
	}

	// @formatter:off
	@SuppressWarnings("unchecked")
	@Documentation(
		value = "Returns the concatenation of the current sequence with the given collection.",
		params = {
			@Param(name = "sequence", value = "The first operand"),
			@Param(name = "collection", value = "The second operand")
		},
		result = "The concatenation of the two specified collections.",
		examples = {
			@Example(
				expression = "Sequence{'a', 'b', 'c'}.concat(Sequence{'d', 'e'})", result = "Sequence{'a', 'b', 'c', 'd', 'e'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "Sequence{'a', 'b', 'c'}.addAll(Sequence{'d', 'e'})",
						result = "Sequence{'a', 'b', 'c', 'd', 'e'}"
					)
				}
			)
		}
	)
	// @formatter:on
	public <T> List<T> concat(List<? extends T> sequence, Collection<? extends T> collection) {
		checkNotNull(sequence);
		final List<T> result;

		if (collection.isEmpty()) {
			result = (List<T>)sequence;
		} else {
			result = Lists.newArrayList(Iterables.concat(sequence, collection));
		}

		return result;
	}

	// @formatter:off
	@SuppressWarnings("unchecked")
	@Documentation(
		value = "Returns the concatenation of the current set with the given collection.",
		params = {
			@Param(name = "set", value = "The first operand"),
			@Param(name = "collection", value = "The second operand")
		},
		result = "The concatenation of the two specified collections.",
		examples = {
			@Example(
				expression = "OrderedSet{'a', 'b', 'c'}.concat(Sequence{'d', 'e'})", result = "OrderedSet{'a', 'b', 'c', 'd', 'e'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "OrderedSet{'a', 'b', 'c'}.addAll(Sequence{'d', 'e'})",
						result = "OrderedSet{'a', 'b', 'c', 'd', 'e'}"
					)
				}
			)
		}
	)
	// @formatter:on
	public <T> Set<T> concat(Set<? extends T> set, Collection<? extends T> collection) {
		checkNotNull(set);
		final Set<T> result;

		if (collection.isEmpty()) {
			result = (Set<T>)set;
		} else {
			result = Sets.newLinkedHashSet(Iterables.concat(set, collection));
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the concatenation of the given collection into the given sequence.",
		params = {
			@Param(name = "sequence", value = "The first operand"),
			@Param(name = "collection", value = "The second operand")
		},
		result = "The current sequence including the elements of the given collection.",
		examples = {
			@Example(
				expression = "Sequence{'a', 'b', 'c'}.add(Sequence{'d', 'e'})", result = "Sequence{'a', 'b', 'c', 'd', 'e'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "Sequence{'a', 'b', 'c'}.addAll(Sequence{'d', 'e'})",
						result = "Sequence{'a', 'b', 'c', 'd', 'e'}"
					)
				}
			),
			@Example(
				expression = "Sequence{'a', 'b', 'c'}.add(OrderedSet{'c', 'e'})", result = "Sequence{'a', 'b', 'c', 'c', 'e'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "Sequence{'a', 'b', 'c'}.addAll(OrderedSet{'c', 'e'})",
						result = "Sequence{'a', 'b', 'c', 'c', 'e'}"
					)
				}
			)
		},
		comment = "The service addAll has been replaced by \"add\" in order to have access to the operator \"+\" between to sequences"
	)
	// @formatter:on
	public <T> List<T> add(List<? extends T> sequence, Collection<? extends T> collection) {
		return concat(sequence, collection);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the concatenation of the given collection into the current set.",
		params = {
			@Param(name = "set", value = "The first operand"),
			@Param(name = "collection", value = "The second operand")
		},
		result = "The current set including the elements of the given collection.",
		examples = {
			@Example(
				expression = "OrderedSet{'a', 'b', 'c'}.add(OrderedSet{'c', 'b', 'f'})", result = "OrderedSet{'a', 'b', 'c', 'c', 'b', 'f'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "OrderedSet{'a', 'b', 'c'}.addAll(OrderedSet{'c', 'b', 'f'})",
						result = "OrderedSet{'a', 'b', 'c', 'c', 'b', 'f'}"
					)
				}
			)
		},
		comment = "The service addAll has been replaced by \"add\" in order to have access to the operator \"+\" between to sets"
	)
	// @formatter:on
	public <T> Set<T> add(Set<? extends T> set, Collection<? extends T> collection) {
		return concat(set, collection);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the difference of the current sequence and the given collection.",
		params = {
			@Param(name = "sequence", value = "The first operand"),
			@Param(name = "collection", value = "The second operand")
		},
		result = "The sequence that contains elements from sequence1 that are not in collection2.",
		examples = {
			@Example(
				expression = "Sequence{'a', 'b', 'c'}.sub(Sequence{'c', 'b', 'f'})", result = "Sequence{'a'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "Sequence{'a', 'b', 'c'}.removeAll(Sequence{'c', 'b', 'f'})",
						result = "Sequence{'a'}"
					)
				}
			),
			@Example(
				expression = "Sequence{'a', 'b', 'c'}.sub(OrderedSet{'c', 'b', 'f'})", result = "Sequence{'a'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "Sequence{'a', 'b', 'c'}.removeAll(OrderedSet{'c', 'b', 'f'})",
						result = "Sequence{'a'}"
					)
				}
			)
		},
		comment = "The service removeAll has been replaced by \"sub\" in order to have access to the operator \"-\" between to sequences"
	)
	// @formatter:on
	public <T> List<T> sub(List<T> sequence, Collection<?> collection) {
		checkNotNull(sequence);
		if (collection.isEmpty()) {
			return sequence;
		} else {
			List<T> result = Lists.newArrayList(sequence);
			result.removeAll(collection);
			return result;
		}
	}

	// @formatter:off
	@Documentation(
		value = "Returns the difference of the current set and the given collection.",
		params = {
			@Param(name = "set", value = "The first operand"),
			@Param(name = "collection", value = "The second operand")
		},
		result = "The set that contains elements from set1 that are not in collection2.",
		examples = {
			@Example(
				expression = "OrderedSet{'a', 'b', 'c'}.sub(OrderedSet{'c', 'b', 'f'})", result = "OrderedSet{'a'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "OrderedSet{'a', 'b', 'c'}.removeAll(OrderedSet{'c', 'b', 'f'})",
						result = "OrderedSet{'a'}"
					)
				}
			)
		},
		comment = "The service removeAll has been replaced by \"sub\" in order to have access to the operator \"-\" between to sets"
	)
	// @formatter:on
	public <T> Set<T> sub(Set<T> set, Collection<?> collection) {
		checkNotNull(set);
		if (collection.isEmpty()) {
			return set;
		} else {
			Set<T> result = Sets.newLinkedHashSet(set);
			result.removeAll(collection);
			return result;
		}
	}

	// @formatter:off
	@Documentation(
		value = "Select returns a filtered version of the specified sequence. Only elements for which the given \"lambda\" evaluates " +
				"to true will be present in the returned sequence.",
		params = {
			@Param(name = "sequence", value = "The original sequence"),
			@Param(name = "lambda", value = "The filtering expression")
		},
		result = "A filtered version of the specified sequence",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->select(str | str.equals('a'))", result = "Sequence{'a'}")
		}
	)
	// @formatter:on
	public <T> List<T> select(List<T> sequence, LambdaValue lambda) {
		final List<T> newList;

		if (lambda == null) {
			newList = Lists.newArrayList();
		} else {
			newList = Lists.newArrayList();
			for (T elt : sequence) {
				Object value = lambda.eval(new Object[] {elt });
				if (Boolean.TRUE.equals(value)) {
					newList.add(elt);
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException(
							"Expression within a select must return a boolean value.");
				}
			}
		}

		return newList;
	}

	// @formatter:off
	@Documentation(
		value = "Select returns a filtered version of the specified set. Only elements for which the given \"lambda\" evaluates " +
				"to true will be present in the returned set.",
		params = {
			@Param(name = "set", value = "The original set"),
			@Param(name = "lambda", value = "The filtering expression")
		},
		result = "A filtered version of the specified set",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->select(str | str.equals('a'))", result = "OrderedSet{'a'}")
		}
	)
	// @formatter:on
	public <T> Set<T> select(Set<T> set, LambdaValue lambda) {
		final Set<T> newSet;

		if (lambda == null) {
			newSet = Sets.newLinkedHashSet();
		} else {
			newSet = Sets.newLinkedHashSet();
			for (T elt : set) {
				Object value = lambda.eval(new Object[] {elt });
				if (Boolean.TRUE.equals(value)) {
					newSet.add(elt);
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException(
							"Expression within a select must return a boolean value.");
				}
			}
		}

		return newSet;
	}

	// @formatter:off
	@Documentation(
		value = "Reject returns a filtered version of the specified set. Only elements for which the given \"lambda\" evaluates " +
				"to false will be present in the returned set",
		params = {
			@Param(name = "set", value = "The original set"),
			@Param(name = "lambda", value = "The filtering expression")
		},
		result = "A filtered version of the specified set",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->reject(str | str.equals('a'))", result = "OrderedSet{'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> Set<T> reject(Set<T> set, LambdaValue lambda) {
		final Set<T> newSet;

		if (lambda == null) {
			newSet = Sets.newLinkedHashSet();
		} else {
			newSet = Sets.newLinkedHashSet();
			for (T elt : set) {
				Object value = lambda.eval(new Object[] {elt });
				if (Boolean.FALSE.equals(value)) {
					newSet.add(elt);
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException(
							"Expression within a reject must return a boolean value.");
				}
			}
		}

		return newSet;
	}

	// @formatter:off
	@Documentation(
		value = "Reject returns a filtered version of the specified sequence. Only elements for which the given \"lambda\" evaluates " +
				"to false will be present in the returned sequence",
		params = {
			@Param(name = "sequence", value = "The original sequence"),
			@Param(name = "lambda", value = "The filtering expression")
		},
		result = "A filtered version of the specified sequence",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->reject(str | str.equals('a'))", result = "Sequence{'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> List<T> reject(List<T> sequence, LambdaValue lambda) {
		final List<T> newList;

		if (lambda == null) {
			newList = Lists.newArrayList();
		} else {
			newList = Lists.newArrayList();
			for (T elt : sequence) {
				Object value = lambda.eval(new Object[] {elt });
				if (Boolean.FALSE.equals(value)) {
					newList.add(elt);
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException(
							"Expression within a reject must return a boolean value.");
				}
			}
		}

		return newList;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set containing the result of applying \"lambda\" on all elements contained in the current set, " +
				"maintaining order.",
		params = {
			@Param(name = "set", value = "The original set"),
			@Param(name = "lambda", value = "The lambda expression")
		},
		result = "A transformed version of the specified set using the given lamba",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->collect(str | str.toUpper())", result = "OrderedSet{'A', 'B', 'C'}")
		}
	)
	// @formatter:on
	public Set<Object> collect(Set<?> set, LambdaValue lambda) {
		final Set<Object> result;

		if (lambda == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (Object elt : set) {
				try {
					Object lambdaResult = lambda.eval(new Object[] {elt });
					if (!(lambdaResult instanceof Nothing)) {
						if (lambdaResult instanceof Collection) {
							result.addAll((Collection<?>)lambdaResult);
						} else if (lambdaResult != null) {
							result.add(lambdaResult);
						}
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence containing the result of applying \"lambda\" on all elements contained in the current sequence, " +
				"maintaining order.",
		params = {
			@Param(name = "sequence", value = "The original sequence"),
			@Param(name = "lambda", value = "The lambda expression")
		},
		result = "A transformed version of the specified sequence using the given lamba",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->collect(str | str.toUpper())", result = "Sequence{'A', 'B', 'C'}")
		}
	)
	// @formatter:on
	public List<Object> collect(List<?> sequence, LambdaValue lambda) {
		final List<Object> result;

		if (lambda == null) {
			result = Collections.emptyList();
		} else {
			result = Lists.newArrayList();
			for (Object elt : sequence) {
				try {
					Object lambdaResult = lambda.eval(new Object[] {elt });
					if (!(lambdaResult instanceof Nothing)) {
						if (lambdaResult instanceof Collection) {
							result.addAll((Collection<?>)lambdaResult);
						} else if (lambdaResult != null) {
							result.add(lambdaResult);
						}
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
			}
		}

		return result;
	}

	public Set<Object> closure(Collection<?> collection, LambdaValue lambda) {
		final Set<Object> result;

		if (lambda == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet(collection);
			Set<Object> currentSet = Sets.newLinkedHashSet(collection);
			Set<Object> added;
			do {
				added = Sets.newLinkedHashSet();
				for (Object current : currentSet) {
					final Object lambdaResult = lambda.eval(new Object[] {current });
					if (lambdaResult instanceof Collection) {
						for (Object child : (Collection<?>)lambdaResult) {
							if (result.add(child)) {
								added.add(child);
							}
						}
					} else if (lambdaResult != null && !(lambdaResult instanceof Nothing)) {
						if (result.add(lambdaResult)) {
							added.add(lambdaResult);
						}
					}
				}
				currentSet = added;
			} while (!added.isEmpty());
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence containing the elements of the original sequence ordered by the result of the given lamba",
		params = {
			@Param(name = "sequence", value = "The original sequence"),
			@Param(name = "lambda", value = "The lambda expression")
		},
		result = "An ordered version of the given sequence",
		examples = {
			@Example(expression = "Sequence{'aa', 'bbb', 'c'}->sortedBy(str | str.size())", result = "Sequence{'c', 'aa', 'bbb'}")
		}
	)
	// @formatter:on
	public <T> List<T> sortedBy(List<T> sequence, final LambdaValue lambda) {
		final List<T> result;

		if (sequence == null) {
			result = null;
		} else if (lambda == null) {
			return sequence;
		} else {
			result = Lists.newArrayList(sequence);
			Collections.sort(result, new LambdaComparator<T>(lambda));
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set containing the elements of the original set ordered by the result of the given lamba",
		params = {
			@Param(name = "set", value = "The original set"),
			@Param(name = "lambda", value = "The lambda expression")
		},
		result = "An ordered version of the given set",
		examples = {
			@Example(expression = "OrderedSet{'aa', 'bbb', 'c'}->sortedBy(str | str.size())", result = "OrderedSet{'c', 'aa', 'bbb'}")
		}
	)
	// @formatter:on
	public <T> Set<T> sortedBy(Set<T> set, final LambdaValue lambda) {
		final Set<T> result;

		if (set == null) {
			result = null;
		} else if (lambda == null) {
			return set;
		} else {
			List<T> sorted = Lists.newArrayList(set);
			Collections.sort(sorted, new LambdaComparator<T>(lambda));
			result = Sets.newLinkedHashSet(sorted);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the size of the specified collection",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "The size of the specified collection",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->size()", result = "3"),
			@Example(expression = "OrderedSet{'a', 'b', 'c', 'd'}->size()", result = "4")
		}
	)
	// @formatter:on
	public Integer size(Collection<?> collection) {
		return collection.size();
	}

	// @formatter:off
	@Documentation(
		value = "Adds the given object to the current set.",
		params = {
			@Param(name = "set", value = "The source set"),
			@Param(name = "object", value = "The object to add")
		},
		result = "A set containing all elements of source set plus the given object",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->including('d')", result = "OrderedSet{'a', 'b', 'c', 'd'}")
		}
	)
	// @formatter:on
	public <T> Set<T> including(Set<T> set, T object) {
		if (set.contains(object)) {
			return set;
		} else {
			Set<T> result = Sets.newLinkedHashSet(set);
			result.add(object);
			return result;
		}
	}

	// @formatter:off
	@Documentation(
		value = "Removes the given object from the current set.",
		params = {
			@Param(name = "set", value = "The source set"),
			@Param(name = "object", value = "The object to remove")
		},
		result = "A set containing all elements of source set minus the given object",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->excluding('c')", result = "OrderedSet{'a', 'b'}")
		}
	)
	// @formatter:on
	public <T> Set<T> excluding(Set<T> set, Object object) {
		if (!set.contains(object)) {
			return set;
		} else {
			Set<T> result = Sets.newLinkedHashSet(set);
			result.remove(object);
			return result;
		}
	}

	// @formatter:off
	@Documentation(
		value = "Adds the given object to the current sequence.",
		params = {
			@Param(name = "sequence", value = "The source sequence"),
			@Param(name = "object", value = "The object to add")
		},
		result = "A sequence containing all elements of the source sequence plus the given object",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->including('d')", result = "Sequence{'a', 'b', 'c', 'd'}")
		}
	)
	// @formatter:on
	public <T> List<T> including(List<T> sequence, T object) {
		List<T> result = Lists.newArrayList(sequence);
		result.add(object);
		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Removes the given object from the current sequence.",
		params = {
			@Param(name = "sequence", value = "The source sequence"),
			@Param(name = "object", value = "The object to remove")
		},
		result = "A sequence containing all elements of source sequence minus the given object",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->excluding('c')", result = "Sequence{'a', 'b'}")
		}
	)
	// @formatter:on
	public <T> List<T> excluding(List<T> sequence, Object object) {
		List<T> result = Lists.newArrayList(sequence);
		result.removeAll(Collections.singleton(object));
		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence representation of the specified collection. Returns the same object if " +
				"it is already a sequence.",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "A sequence with all the elements of the input collection",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->asSequence()", result = "Sequence{'a', 'b', 'c'}"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->asSequence()", result = "Sequence{'a', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> List<T> asSequence(Collection<T> collection) {
		if (collection instanceof List) {
			return (List<T>)collection;
		} else {
			return Lists.newArrayList(collection);
		}
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set representation of the specified collection. Returns the same object if " +
				"it is already a set.",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "A set with all the elements of the input collection",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->asSet()", result = "OrderedSet{'a', 'b', 'c'}"),
			@Example(expression = "Sequence{'a', 'b', 'c', 'c', 'a'}->asSet()", result = "OrderedSet{'a', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> Set<T> asSet(Collection<T> collection) {
		if (collection instanceof Set) {
			return (Set<T>)collection;
		} else {
			return Sets.newLinkedHashSet(collection);
		}
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set representation of the specified collection. Returns the same object if " +
				"it is a set already. This operation has the same behavior as \"asSet()\"",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "A set with all the elements of the input collection",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->asOrderedSet()", result = "OrderedSet{'a', 'b', 'c'}"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->asOrderedSet()", result = "OrderedSet{'a', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> Set<T> asOrderedSet(Collection<T> collection) {
		return asSet(collection);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the first element of the specified Collection.",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "The first element of the collection",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->first()", result = "'a'")
		}
	)
	// @formatter:on
	public <T> T first(Collection<T> collection) {
		return Iterators.getNext(collection.iterator(), null);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the given sequence in reversed order.",
		params = {
			@Param(name = "sequence", value = "The input sequence")
		},
		result = "The sequence in reserved order",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->reverse()", result = "Sequence{'c', 'b', 'a'}")
		}
	)
	// @formatter:on
	public <T> List<T> reverse(List<T> sequence) {
		return Lists.newArrayList(Lists.reverse(sequence));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the given set in reversed order.",
		params = {
			@Param(name = "set", value = "The input set")
		},
		result = "The set in reserved order",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->reverse()", result = "OrderedSet{'c', 'b', 'a'}")
		}
	)
	// @formatter:on
	public <T> Set<T> reverse(Set<T> set) {
		return Sets.newLinkedHashSet(ImmutableList.copyOf(set).reverse());
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" when the input collection is empty.",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "\"true\" when the input collection is empty.",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->isEmpty()", result = "false"),
			@Example(expression = "Sequence{}->isEmpty()", result = "true"),
		}
	)
	// @formatter:on
	public Boolean isEmpty(Collection<?> collection) {
		return collection.isEmpty();
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" when the input collection is not empty.",
		params = {
			@Param(name = "collection", value = "The input collection")
		},
		result = "\"true\" when the input collection is not empty.",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->notEmpty()", result = "true"),
			@Example(expression = "Sequence{}->notEmpty()", result = "false"),
		}
	)
	// @formatter:on
	public Boolean notEmpty(Collection<?> collection) {
		return !collection.isEmpty();
	}

	// @formatter:off
	@Documentation(
		value = "Returns the element at the specified position in the sequence.",
		params = {
			@Param(name = "sequence", value = "The input sequence"),
			@Param(name = "position", value = "The position of the element to return ([1..size])")
		},
		result = "The element at the specified position in the list",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->at(1)", result = "'a'"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->at(2)", result = "'b'")
		}
	)
	// @formatter:on
	public <T> T at(List<T> sequence, Integer position) {
		return sequence.get(position - 1);
	}

	// @formatter:off
	@Documentation(
		value = "Keeps only instances of the given EClassifier from the given set.",
		params = {
			@Param(name = "set", value = "The input set to filter"),
			@Param(name = "eClassifier", value = "The type used to filters element in the set")
		},
		result = "A new set containing instances of the given EClassifier or null if the given set is null",
		examples = {
			@Example(expression = "OrderedSet{anEClass, anEAttribute, anEReference}->filter(ecore::EClass)", result = "OrederedSet{anEClass}"),
			@Example(expression = "OrderedSet{anEClass, anEAttribute}->filter(ecore::EStructuralFeature)", result = "OrederedSet{anEAttribute}"),
		}
	)
	public <T> Set<T> filter(Set<T> set, final EClassifier eClassifier) {
		final Set<T> result;

		if (set == null) {
			result = null;
		} else if (eClassifier != null) {
			final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
			eClassifiers.add(eClassifier);
			result = filter(set, eClassifiers);
		} else {
			result = Sets.newLinkedHashSet();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Keeps only instances of the given set of EClassifier from the given set.",
		params = {
			@Param(name = "set", value = "The input set to filter"),
			@Param(name = "eClassifiers", value = "The set of type used to filters element in the set")
		},
		result = "A new set containing instances of the given set of EClassifiers or null if the given set is null",
		examples = {
				@Example(expression = "OrderedSet{anEClass, anEAttribute, anEReference}->filter({ecore::EClass | ecore::EReference})", result = "OrderedSet{anEClass, anEReference}"),
				@Example(expression = "OrderedSet{anEClass, anEAttribute, anEPackage}->filter({ecore::EStructuralFeature | ecore::EPacakge})", result = "OrderedSet{anEAttribute, anEPackage}"),
		}
	)
	// @formatter:on
	public <T> Set<T> filter(Set<T> set, final Set<EClassifier> eClassifiers) {
		final Set<T> result;

		if (set == null) {
			result = null;
		} else if (eClassifiers == null || eClassifiers.isEmpty()) {
			result = Sets.newLinkedHashSet();
		} else {
			result = Sets.newLinkedHashSet();
			for (T object : set) {
				for (EClassifier eClassifier : eClassifiers) {
					if (eClassifier.isInstance(object)) {
						result.add(object);
						break;
					}
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Keeps only instances of the given EClassifier in the given sequence.",
		params = {
			@Param(name = "sequence", value = "The input sequence to filter"),
			@Param(name = "eClassifier", value = "The type used to filters element in the sequence")
		},
		result = "A new sequence containing instances of the given EClassifier or null if the given sequence is null",
		examples = {
			@Example(expression = "Sequence{anEClass, anEAttribute, anEReference}->filter(ecore::EClass)", result = "Sequence{anEClass}"),
			@Example(expression = "Sequence{anEClass, anEAttribute}->filter(ecore::EStructuralFeature)", result = "Sequence{anEAttribute}"),
		}
	)
	// @formatter:on
	public <T> List<T> filter(List<T> sequence, final EClassifier eClassifier) {
		final List<T> result;

		if (sequence == null) {
			result = null;
		} else if (eClassifier != null) {
			final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
			eClassifiers.add(eClassifier);
			result = filter(sequence, eClassifiers);
		} else {
			result = Lists.newArrayList();
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Keeps only instances of the given EClassifier in the given sequence.",
		params = {
			@Param(name = "sequence", value = "The input sequence to filter"),
			@Param(name = "eClassifiers", value = "The set of types used to filters element in the sequence")
		},
		result = "A new sequence containing instances of the given EClassifiers or null if the given sequence is null",
		examples = {
			@Example(expression = "Sequence{anEClass, anEAttribute, anEReference}->filter({ecore::EClass | ecore::EReference})", result = "Sequence{anEClass, anEReference}"),
			@Example(expression = "Sequence{anEClass, anEAttribute, anEPackage}->filter({ecore::EStructuralFeature | ecore::EPacakge})", result = "Sequence{anEAttribute, anEPackage}"),
		}
	)
	// @formatter:on
	public <T> List<T> filter(List<T> sequence, final Set<EClassifier> eClassifiers) {
		final List<T> result;

		if (sequence == null) {
			result = null;
		} else if (eClassifiers == null || eClassifiers.isEmpty()) {
			result = Lists.newArrayList();
		} else {
			result = Lists.newArrayList();
			for (T object : sequence) {
				for (EClassifier eClassifier : eClassifiers) {
					if (eClassifier.isInstance(object)) {
						result.add(object);
						break;
					}
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given separator between each elements of the given collection.",
		params = {
			@Param(name = "collection", value = "The input collection"),
			@Param(name = "separator", value = "The separator to insert")
		},
		result = "A new sequence, or null if the given collection is null",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->sep('-')", result = "Sequence{'a', '-', 'b', '-', 'c'}"),
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->sep('-')", result = "Sequence{'a', '-', 'b', '-', 'c'}")
		}
	)
	// @formatter:on
	public List<Object> sep(Collection<?> collection, Object separator) {
		final List<Object> result;

		if (collection == null) {
			result = null;
		} else {
			result = Lists.newArrayList();

			final Iterator<?> it = collection.iterator();
			if (it.hasNext()) {
				result.add(it.next());
				while (it.hasNext()) {
					result.add(separator);
					result.add(it.next());
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given separator between each elements of the given collection, the given prefix " +
				"before the first element, and the given suffix after the last element.",
		params = {
			@Param(name = "collection", value = "The input collection"),
			@Param(name = "prefix", value = "The prefix"),
			@Param(name = "separator", value = "The separator to insert"),
			@Param(name = "suffix", value = "The suffix")
		},
		result = "A new sequence, or null if the given collection is null",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->sep('[', '-', ']')", result = "Sequence{'[', 'a', '-', 'b', '-', 'c', ']'}")
		}
	)
	// @formatter:on
	public List<Object> sep(Collection<?> collection, Object prefix, Object separator, Object suffix) {
		final List<Object> result = Lists.newArrayList();

		result.add(prefix);
		if (collection != null) {
			result.addAll(sep(collection, separator));
		}
		result.add(suffix);

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the last element of the given sequence.",
		params = {
			@Param(name = "sequence", value = "The sequence")
		},
		result = "The last element of the given sequence.",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->last()", result = "'c'")
		}
	)
	// @formatter:on
	public <T> T last(List<T> sequence) {
		ListIterator<T> it = sequence.listIterator(sequence.size());
		if (it.hasPrevious()) {
			return it.previous();
		}
		return null;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the last element of the given set.",
		params = {
			@Param(name = "set", value = "The set")
		},
		result = "The last element of the given set.",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->last()", result = "'c'")
		}
	)
	// @formatter:on
	public <T> T last(Set<T> set) {
		return Iterators.getLast(set.iterator(), null);
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if the given collection doesn't contain the given object.",
		params = {
			@Param(name = "collection", value = "The input collection"),
			@Param(name = "object", value = "The object"),
		},
		result = "\"true\" if the given collection doesn't contain the given object, \"false\" otherwise",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->excludes('a')", result = "false"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->excludes('d')", result = "true")
		}
	)
	// @formatter:on
	public Boolean excludes(Collection<?> collection, Object object) {
		return Boolean.valueOf(!collection.contains(object));
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if the given collection contains the given object.",
		params = {
			@Param(name = "collection", value = "The input collection"),
			@Param(name = "object", value = "The object"),
		},
		result = "\"true\" if the given collection contains the given object, \"false\" otherwise",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->includes('a')", result = "true"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->includes('d')", result = "false")
		}
	)
	// @formatter:on
	public Boolean includes(Collection<?> collection, Object object) {
		return Boolean.valueOf(collection.contains(object));
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set containing all the elements of the first and second sets",
		params = {
			@Param(name = "set1", value = "The first set"),
			@Param(name = "set2", value = "The second set"),
		},
		result = "A set containing all the elements of the first and second sets",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->union(OrderedSet{'d', 'c'})", result = "OrderedSet{'a', 'b', 'c', 'd'}")
		}
	)
	// @formatter:on
	public <T> Set<T> union(Set<? extends T> set1, Set<? extends T> set2) {
		return concat(set1, set2);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence containing all the elements of the first and second sequences",
		params = {
			@Param(name = "sequence1", value = "The first sequence"),
			@Param(name = "sequence2", value = "The second sequence"),
		},
		result = "A sequence containing all the elements of the first and second sequences",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->union(Sequence{'d', 'c'})", result = "Sequence{'a', 'b', 'c', 'd'}")
		}
	)
	// @formatter:on
	public <T> List<T> union(List<? extends T> sequence1, List<? extends T> sequence2) {
		return concat(sequence1, sequence2);
	}

	// @formatter:off
	@Documentation(
		value = "Gets the first element in the current collection for which the value returned by the lambda evaluates " +
				"to \"true\".",
		params = {
			@Param(name = "collection", value = "The input collection"),
			@Param(name = "lambda", value = "The lamba"),
		},
		result = "The first element in the given collection for which the value returned by the lambda is \"true\"",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->any(str | str.size() = 1)", result = "'a'")
		}
	)
	// @formatter:on
	public <T> T any(Collection<T> collection, LambdaValue lambda) {
		T result = null;

		if (collection != null && lambda == null) {
			result = null;
		} else {
			for (T input : collection) {
				Object value = lambda.eval(new Object[] {input });
				if (Boolean.TRUE.equals(value)) {
					result = input;
					break;
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException("Expression within any must return a boolean value.");
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"1\" if the current set contains the given object, \"0\" otherwise.",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "object", value = "The object"),
		},
		result = "\"1\" if the current set contains the given object, \"0\" otherwise.",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->count('d')", result = "0"),
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->count('a')", result = "1")
		}
	)
	// @formatter:on
	public Integer count(Set<?> set, Object object) {
		final Integer result;

		if (set.contains(object)) {
			result = Integer.valueOf(1);
		} else {
			result = Integer.valueOf(0);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Counts the number of occurrences of the given object in the given sequence",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "object", value = "The object"),
		},
		result = "The number of times the given object is present in the given sequence",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->count('d')", result = "0"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->count('a')", result = "1")
		}
	)
	// @formatter:on
	public Integer count(List<Object> sequence, Object object) {
		return Integer.valueOf(Collections.frequency(sequence, object));
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if it exists an object from the given collection for which the given lambda evaluates " +
				"to \"true\"",
		params = {
			@Param(name = "collection", value = "The collection"),
			@Param(name = "lambda", value = "The lambda"),
		},
		result = "\"true\" if it exists an object from the given collection that validate the given lamba " +
				 "\"false\" otherwise.",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->exists(str | str.size() > 5)", result = "false")
		}
	)
	// @formatter:on
	public Boolean exists(Collection<?> collection, LambdaValue lambda) {
		Boolean result = Boolean.FALSE;

		if (collection != null && lambda != null) {
			for (Object input : collection) {
				Object value = lambda.eval(new Object[] {input });
				if (Boolean.TRUE.equals(value)) {
					result = Boolean.TRUE;
					break;
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException(
							"Expression within exists must return a boolean value.");
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if all the objects from the given collection validate the given lamba",
		params = {
			@Param(name = "collection", value = "The collection"),
			@Param(name = "lambda", value = "The lambda"),
		},
		result = "\"true\" if all the objects from the given collection validate the given lamba " +
				 "\"false\" otherwise.",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'ccc'}->forAll(str | str.size() = 1)", result = "false"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->forAll(str | str.size() = 1)", result = "false")
		}
	)
	// @formatter:on
	public Boolean forAll(Collection<?> collection, LambdaValue lambda) {
		Boolean result = Boolean.TRUE;

		if (collection == null || lambda == null) {
			result = Boolean.FALSE;
		} else {
			for (Object input : collection) {
				Object value = lambda.eval(new Object[] {input });
				if (value instanceof Boolean && !Boolean.TRUE.equals(value)) {
					result = Boolean.FALSE;
					break;
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException(
							"Expression within exists must return a boolean value.");
				}
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if no elements from the second collection are contained in the first collection",
		params = {
			@Param(name = "collection1", value = "The first collection"),
			@Param(name = "collection2", value = "The second collection")
		},
		result = "\"true\" if no elements of the second collection are contained in the first one " +
				 "\"false\" otherwise.",
		examples = {
			@Example(expression = "Sequence{'a', 'b'}->excludesAll(OrderedSet{'f'})", result = "true"),
			@Example(expression = "Sequence{'a', 'b'}->excludesAll(OrderedSet{'a', 'f'})", result = "false")
		}
	)
	// @formatter:on
	public Boolean excludesAll(Collection<?> collection1, Collection<?> collection2) {
		return Boolean.valueOf(Collections.disjoint(collection1, collection2));
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if all elements from the second collection are contained in the first collection",
		params = {
			@Param(name = "collection1", value = "The first collection"),
			@Param(name = "collection2", value = "The second collection")
		},
		result = "\"true\" if all elements of the second collection are contained in the first one " +
				 "\"false\" otherwise.",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->includesAll(OrderedSet{'a'})", result = "true"),
			@Example(expression = "Sequence{'a', 'b', 'c'}->includesAll(OrderedSet{'a', 'f'})", result = "false"),
		}
	)
	// @formatter:on
	public Boolean includesAll(Collection<?> collection1, Collection<?> collection2) {
		return Boolean.valueOf(collection1.containsAll(collection2));
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if the evaluation of the given lambda gives a different value for all elements of the " +
				"given collection.",
		params = {
			@Param(name = "collection", value = "The collection"),
			@Param(name = "lambda", value = "The lambda")
		},
		result = "\"true\" if the evaluation of the lamba gives a different value for all elements of the " +
				 "given collection, \"false\" otherwise.",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->isUnique(str | str.size())", result = "false"),
			@Example(expression = "Sequence{'a', 'bb', 'ccc'}->isUnique(str | str.size())", result = "true")
		}
	)
	// @formatter:on
	public Boolean isUnique(Collection<?> collection, LambdaValue lambda) {
		boolean result = true;
		final Set<Object> evaluated = Sets.newHashSet();

		if (collection != null && lambda == null) {
			result = false;
		} else {
			for (Object input : collection) {
				try {
					if (!evaluated.add(lambda.eval(new Object[] {input }))) {
						result = false;
						break;
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
			}
		}

		return Boolean.valueOf(result);
	}

	// @formatter:off
	@Documentation(
		value = "Indicates if one and only one element of the given collection validates the given lambda.",
		params = {
			@Param(name = "collection", value = "The collection"),
			@Param(name = "lambda", value = "The lambda")
		},
		result = "\"true\" if one and only one element of the given collection validates the given lambda, " +
				 "\"false\" otherwise.",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->one(str | str.equals('a'))", result = "true"),
			@Example(expression = "Sequence{'a', 'a', 'c'}->one(str | str.equals('a'))", result = "false")
		}
	)
	// @formatter:on
	public Boolean one(Collection<?> self, LambdaValue lambda) {
		boolean result = false;

		if (self != null && lambda == null) {
			result = false;
		} else {
			for (Object input : self) {
				Object value = lambda.eval(new Object[] {input });
				if (Boolean.TRUE.equals(value)) {
					result = !result;
					if (!result) {
						break;
					}
				} else if (!(value instanceof Boolean)) {
					throw new IllegalArgumentException("Expression in one must return a boolean value.");
				}
			}
		}

		return Boolean.valueOf(result);
	}

	// @formatter:off
	@Documentation(
		value = "Sums elements of the given collection if possible.",
		params = {
			@Param(name = "collection", value = "The collection")
		},
		result = "The sum of elements of the given collection if possible",
		exceptions = {
			@Throw(type = IllegalArgumentException.class, value = "If an element of the collection cannot be processed")
		},
		examples = {
			@Example(expression = "Sequence{1, 2, 3, 4}->sum()", result = "10")
		}
	)
	// @formatter:on
	public Number sum(Collection<?> collection) {
		Number result = Long.valueOf(0);

		for (Object input : collection) {
			if (!(input instanceof Number)) {
				throw new IllegalArgumentException(SUM_ONLY_NUMERIC_ERROR);
			}

			if (result instanceof Long && (input instanceof Long || input instanceof Integer)) {
				result = result.longValue() + ((Number)input).longValue();
			} else {
				// widen anything that is not a long or int to a double
				result = result.doubleValue() + ((Number)input).doubleValue();
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the given object in the given sequence ([1..size]).",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "The index of the given object",
		examples = {
			@Example(expression = "Sequence{1, 2, 3, 4}->indexOf(3)", result = "3")
		}
	)
	// @formatter:on
	public Integer indexOf(List<?> sequence, Object object) {
		return Integer.valueOf(sequence.indexOf(object) + 1);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the given object in the given set ([1..size]).",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "object", value = "The object")
		},
		result = "The index of the given object",
		examples = {
			@Example(expression = "OrderedSet{1, 2, 3, 4}->indexOf(3)", result = "3")
		}
	)
	// @formatter:on
	public Integer indexOf(Set<?> set, Object object) {
		int index = 1;
		for (Object o : set) {
			if (o == object || (o != null && o.equals(object))) {
				return index;
			}
			index++;
		}
		return 0;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given object in a copy of the given sequence at the given position ([1..size]).",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "position", value = "The position"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given sequence including the object at the given position",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->insertAt(2, 'f')", result = "Sequence{'a', 'f', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> List<T> insertAt(List<T> sequence, Integer position, T object) {
		final int initialSize = sequence.size();
		if (position < 1 || position > initialSize + 1) {
			throw new IndexOutOfBoundsException();
		}
		final List<T> result = new ArrayList<T>(initialSize + 1);

		result.addAll(sequence.subList(0, position - 1));
		result.add(object);
		result.addAll(sequence.subList(position - 1, initialSize));

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given object in a copy of the given set at the given position ([1..size]). "
				+ "If the given set already contains this object, it will be moved to the accurate position.",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "position", value = "The position"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given set including the object at the given position if it didn't already contain that object.",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->insertAt(2, 'f')", result = "Sequence{'a', 'f', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> Set<T> insertAt(Set<T> set, Integer position, T object) {
		final int initialSize = set.size();
		if (position < 1 || position > initialSize + 1) {
			throw new IndexOutOfBoundsException();
		}
		final Set<T> result = new LinkedHashSet<T>(initialSize + 1);

		int current = 1;
		Iterator<T> iterator = set.iterator();
		while (iterator.hasNext()) {
			if (current == position.intValue()) {
				result.add(object);
				current++;
			} else {
				T value = iterator.next();
				if (object == value || (object != null && object.equals(value))) {
					// Do not add our target object here, wait until we reach its demanded position
				} else {
					result.add(value);
					current++;
				}
			}
		}
		if (current <= position.intValue()) {
			result.add(object);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given object in a copy of the given sequence at the first position.",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given sequence including the object at the first position",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->prepend('f')", result = "Sequence{'f', 'a', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> List<T> prepend(List<T> sequence, T object) {
		final List<T> result = new ArrayList<T>(sequence.size() + 1);

		result.add(object);
		result.addAll(sequence);

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given object in a copy of the given set at the first position. "
				+ "If the set already contained the given object, it is moved to the first position.",
		params = {
			@Param(name = "set", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given set including the object at the first position",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->prepend('f')", result = "OrderedSet{'f', 'a', 'b', 'c'}")
		}
	)
	// @formatter:on
	public <T> Set<T> prepend(Set<T> set, T object) {
		final Set<T> result = new LinkedHashSet<T>(set.size() + 1);

		result.add(object);
		result.addAll(set);

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Creates a set with the elements from the given set that are also present in the given collection.",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "collection", value = "The collection")
		},
		result = "The created set with elements from the given set that are also present in the given collection",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->intersection(OrderedSet{'a', 'f'})", result = "OrderedSet{'a'}")
		}
	)
	// @formatter:on
	public <T> Set<T> intersection(Set<T> set, Collection<?> collection) {
		if (collection instanceof Set<?>) {
			return Sets.intersection(set, (Set<?>)collection);
		}
		final Set<T> result = Sets.newLinkedHashSet(set);
		result.retainAll(collection);
		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Creates a sequence with elements from the given sequence that are present in both the current sequence " +
				"and the given other {@code Collection}. Iteration order will match that of the current sequence. Duplicates " +
				"from the first list will all be kept in the result if they also are in the second one, but duplicates " +
				"from the second list will be dumped even if they are present in the first.",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "collection", value = "The collection")
		},
		result = "The intersection of both collections",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->intersection(OrderedSet{'a', 'f'})", result = "Sequence{'a'}")
		}
	)
	// @formatter:on
	public <T> List<T> intersection(List<T> sequence, Collection<?> collection) {
		final List<T> result = Lists.newArrayList(sequence);
		result.retainAll(collection);
		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a subset of the given set",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "startIndex", value = "The low end point (inclusive) of the subset. Must not be less than 1."),
			@Param(name = "startIndex", value = "The high end point (inclusive) of the subset. Must not be greater than the current set's size.")
		},
		result = "A subset of the given set",
		exceptions = {
			@Throw(type = IndexOutOfBoundsException.class, value = "If we have an illegal end point value (startIndex < 1 || " +
																   "endIndex > set.size() || startIndex > endIndex).")
		},
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->subOrderedSet(1, 2)", result = "OrderedSet{'a', 'b'}")
		}
	)
	// @formatter:on
	public <T> Set<T> subOrderedSet(Set<T> set, Integer startIndex, Integer endIndex) {
		if (startIndex < 1 || endIndex > set.size() || startIndex > endIndex) {
			throw new IndexOutOfBoundsException();
		}
		final Set<T> result = new LinkedHashSet<T>(endIndex - startIndex + 1);

		int index = 1;
		for (T input : set) {
			if (index >= startIndex) {
				if (index <= endIndex) {
					result.add(input);
				} else {
					break;
				}
			}
			++index;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a subset of the given sequence",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "startIndex", value = "The low end point (inclusive) of the subsequence"),
			@Param(name = "startIndex", value = "The high end point (inclusive) of the subsequence")
		},
		result = "A subset of the given sequence",
		exceptions = {
			@Throw(type = IndexOutOfBoundsException.class, value = "If we have an illegal end point value (startIndex < 1 || " +
																   "endIndex > set.size() || startIndex > endIndex).")
			},
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->subSequence(1, 2)", result = "Sequence{'a', 'b'}")
		}
	)
	// @formatter:on
	public <T> List<T> subSequence(List<T> sequence, Integer startIndex, Integer endIndex) {
		if (startIndex < 1 || endIndex > sequence.size() || startIndex > endIndex) {
			throw new IndexOutOfBoundsException();
		}
		return Lists.newArrayList(sequence.subList(startIndex - 1, endIndex));
	}

	/**
	 * Evaluates a lambda then uses the result as comparables.
	 */
	private static final class LambdaComparator<T> implements Comparator<T> {
		/** The lambda providing our comparables. */
		private final LambdaValue lambda;

		/**
		 * Constructs a comparator given its lambda.
		 * 
		 * @param lambda
		 *            the lambda providing our comparables.
		 */
		public LambdaComparator(LambdaValue lambda) {
			this.lambda = lambda;
		}

		@Override
		public int compare(T o1, T o2) {
			final int result;

			Object o1Result = lambda.eval(new Object[] {o1 });
			Object o2Result = lambda.eval(new Object[] {o2 });
			try {
				if (o1Result instanceof Comparable<?>) {
					@SuppressWarnings("unchecked")
					Comparable<Object> c1 = (Comparable<Object>)o1Result;
					result = c1.compareTo(o2Result);
				} else if (o2Result instanceof Comparable<?>) {
					@SuppressWarnings("unchecked")
					Comparable<Object> c2 = (Comparable<Object>)o2Result;
					result = -c2.compareTo(o1Result);
				} else {
					result = 0;
				}
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				throw new IllegalArgumentException("Cannot compare " + o1 + " with " + o2, e);
			}

			return result;
		}
	}
}
