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

import com.google.common.collect.ImmutableList;
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
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
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

	/**
	 * Exists {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class BooleanLambdaService extends Service {

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
				result.add(services.nothing("expression in %s must return a boolean", getServiceMethod()
						.getName()));
			}
			return result;
		}
	}

	/**
	 * Any {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class AnyService extends Service {

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
					IType lambdaEvaluatorType = lambdaType.getLambdaEvaluatorType();
					if (lambdaEvaluatorType instanceof EClassifierLiteralType) {
						lambdaEvaluatorType = new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)lambdaEvaluatorType).getType());
					}
					result.add(lambdaEvaluatorType);
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
	private static final class IncludingService extends Service {

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
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
				result.add(new SequenceType(queryEnvironment, argTypes.get(1)));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
				result.add(new SetType(queryEnvironment, argTypes.get(1)));
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
				result.add(services.nothing("Nothing left after %s:" + builder.toString(), getServiceMethod()
						.getName()));
			}

			return result;
		}
	}

	/**
	 * Collect {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class CollectService extends Service {
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
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, lambdaType.getLambdaExpressionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, lambdaType.getLambdaExpressionType()));
			}
			return result;
		}
	}

	/**
	 * Select {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class SelectService extends Service {

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
					IType lambdaEvaluatorType = lambdaType.getLambdaEvaluatorType();
					if (lambdaEvaluatorType instanceof EClassifierLiteralType) {
						lambdaEvaluatorType = new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)lambdaEvaluatorType).getType());
					}
					if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
						result.add(new SequenceType(queryEnvironment, lambdaEvaluatorType));
					} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
						result.add(new SetType(queryEnvironment, lambdaEvaluatorType));
					}
				} else {
					for (IType inferredType : inferredTypes) {
						if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
							result.add(new SequenceType(queryEnvironment, inferredType));
						} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
							result.add(new SetType(queryEnvironment, inferredType));
						}
					}
				}
			} else {
				result.add(services.nothing("expression in a select must return a boolean"));
			}
			return result;
		}
	}

	/**
	 * Reject {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class RejectService extends Service {

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
					if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
						result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
								.getCollectionType()));
					} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
						result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(0))
								.getCollectionType()));
					}
				} else {
					for (IType inferredType : inferredTypes) {
						if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
							result.add(new SequenceType(queryEnvironment, inferredType));
						} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
							result.add(new SetType(queryEnvironment, inferredType));
						}
					}
				}
			} else {
				result.add(services.nothing("expression in a reject must return a boolean"));
			}

			return result;
		}
	}

	/**
	 * A {@link Service} returning the raw collection type of the first argument.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class FirstArgumentRawCollectionType extends Service {

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
	private static final class ReturnCollectionTypeWithFirstArgumentRawCollectionType extends Service {

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

			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
			}

			return result;
		}
	}

	/**
	 * A {@link Service} returning the same collection type as the method with the raw collection type of
	 * first and second arguments.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType extends Service {

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

			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
				result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(1))
						.getCollectionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
				result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(1))
						.getCollectionType()));
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
				result.add(services.nothing("Nothing left after %s:" + builder.toString(), getServiceMethod()
						.getName()));
			}

			return result;
		}

	}

	/**
	 * A {@link Service} returning the {@link EClassifierLiteralType#getType() classifier literal type} of the
	 * second argument in the {@link org.eclipse.acceleo.query.validation.type.ICollectionType
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
				if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SequenceType(queryEnvironment, rawType));
				} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SetType(queryEnvironment, rawType));
				}
			}

			return result;

		}
	}

	/**
	 * A {@link Service} returning the raw type of the first argument {@link ICollectionType}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class FirstCollectionTypeService extends Service {

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
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
			}
			return result;
		}

	}

	/**
	 * InsertAt {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class InsertAtService extends Service {

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
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
				result.add(new SequenceType(queryEnvironment, argTypes.get(2)));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)argTypes.get(0))
						.getCollectionType()));
				result.add(new SetType(queryEnvironment, argTypes.get(2)));
			}
			return result;
		}
	}

	/**
	 * Intersection {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class IntersectionService extends Service {

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
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				for (IType resultRawType : resultRawTypes) {
					result.add(new SequenceType(queryEnvironment, resultRawType));
				}
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				for (IType resultRawType : resultRawTypes) {
					result.add(new SetType(queryEnvironment, resultRawType));
				}
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
				if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SequenceType(queryEnvironment, services
							.nothing("Nothing left after intersection:" + builder.toString())));
				} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SetType(queryEnvironment, services
							.nothing("Nothing left after intersection:" + builder.toString())));
				}
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
		} else if ("including".equals(publicMethod.getName()) || "prepend".equals(publicMethod.getName())) {
			result = new IncludingService(publicMethod, this);
		} else if ("sep".equals(publicMethod.getName())) {
			if (publicMethod.getParameterTypes().length == 2) {
				result = new Service(publicMethod, this) {

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
				result = new Service(publicMethod, this) {

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
				result = new Service(publicMethod, this);
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
		} else {
			result = new Service(publicMethod, this);
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
		final boolean result;

		if (type instanceof EClassifier) {
			final Class<?> typeClass = queryEnvironment.getEPackageProvider().getClass((EClassifier)type);
			result = typeClass == Boolean.class || typeClass == boolean.class;
		} else {
			result = false;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the concatenation of the two specified sequences.",
		params = {
			@Param(name = "sequence1", value = "The first operand"),
			@Param(name = "sequence2", value = "The second operand")
		},
		result = "The concatenation of the two specified sequences.",
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
	public List<Object> concat(List<Object> sequence1, Collection<Object> sequence2) {
		final List<Object> result;

		if (sequence1.isEmpty()) {
			result = Lists.newArrayList(sequence2);
		} else if (sequence2.isEmpty()) {
			result = sequence1;
		} else {
			result = Lists.newArrayList(sequence1);
			result.addAll(sequence2);
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the concatenation of the given collection into the given sequence.",
		params = {
			@Param(name = "sequence1", value = "The first operand"),
			@Param(name = "collection2", value = "The second operand")
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
	public List<Object> add(List<Object> sequence1, Collection<Object> collection2) {
		return concat(sequence1, collection2);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the difference of the current sequence and the given collection.",
		params = {
			@Param(name = "sequence1", value = "The first operand"),
			@Param(name = "collection2", value = "The second operand")
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
	public List<Object> sub(List<Object> sequence1, Collection<Object> collection2) {
		if (collection2.isEmpty()) {
			return sequence1;
		} else {
			List<Object> result = Lists.newArrayList(sequence1);
			for (Object obj : collection2) {
				result.remove(obj);
			}
			return result;
		}
	}

	// @formatter:off
	@Documentation(
		value = "Returns the difference of the current set and the given collection.",
		params = {
			@Param(name = "set1", value = "The first operand"),
			@Param(name = "collection2", value = "The second operand")
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
	public Set<Object> sub(Set<Object> set1, Collection<Object> collection2) {
		if (collection2.isEmpty()) {
			return set1;
		} else {
			Set<Object> result = Sets.newLinkedHashSet(set1);
			for (Object obj : collection2) {
				result.remove(obj);
			}
			return result;
		}
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
	public Set<Object> add(Set<Object> set, Collection<Object> collection) {
		final Set<Object> result;

		if (set.isEmpty()) {
			result = Sets.newLinkedHashSet(collection);
		} else if (collection.isEmpty()) {
			result = set;
		} else {
			result = Sets.newLinkedHashSet(set);
			result.addAll(collection);
		}

		return result;
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
	public List<Object> select(List<Object> sequence, LambdaValue lambda) {
		final List<Object> newList;

		if (lambda == null) {
			newList = Collections.emptyList();
		} else {
			newList = Lists.newArrayList();
			for (Object elt : sequence) {
				try {
					if (Boolean.TRUE.equals(lambda.eval(new Object[] {elt }))) {
						newList.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
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
	public Set<Object> select(Set<Object> set, LambdaValue lambda) {
		final Set<Object> newSet;

		if (lambda == null) {
			newSet = Collections.emptySet();
		} else {
			newSet = Sets.newLinkedHashSet();
			for (Object elt : set) {
				try {
					if (Boolean.TRUE.equals(lambda.eval(new Object[] {elt }))) {
						newSet.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
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
	public Set<Object> reject(Set<Object> set, LambdaValue lambda) {
		final Set<Object> newSet;

		if (lambda == null) {
			newSet = Collections.emptySet();
		} else {
			newSet = Sets.newLinkedHashSet();
			for (Object elt : set) {
				try {
					if (Boolean.FALSE.equals(lambda.eval(new Object[] {elt }))) {
						newSet.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
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
	public List<Object> reject(List<Object> sequence, LambdaValue lambda) {
		final List<Object> newList;

		if (lambda == null) {
			newList = Collections.emptyList();
		} else {
			newList = Lists.newArrayList();
			for (Object elt : sequence) {
				try {
					if (Boolean.FALSE.equals(lambda.eval(new Object[] {elt }))) {
						newList.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
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
	public Set<Object> collect(Set<Object> set, LambdaValue lambda) {
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
	public List<Object> collect(List<Object> sequence, LambdaValue lambda) {
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

	// @formatter:off
	@Documentation(
		value = "Returns a sequence containing the elements of the original collection ordered by the result of the given lamba",
		params = {
			@Param(name = "collection", value = "The original collection"),
			@Param(name = "lambda", value = "The lambda expression")
		},
		result = "An ordered version of the given collection",
		examples = {
			@Example(expression = "Sequence{'aa', 'bbb', 'c'}->sortedBy(str | str.size())", result = "Sequence{'c', 'aa', 'bbb'}")
		}
	)
	// @formatter:on
	public List<Object> sortedBy(Collection<Object> collection, final LambdaValue lambda) {
		final List<Object> result;

		if (collection == null) {
			result = null;
		} else {
			result = new ArrayList<Object>();
			result.addAll(collection);
			Collections.sort(result, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					final int result;

					Object o1Result = lambda.eval(new Object[] {o1 });
					Object o2Result = lambda.eval(new Object[] {o2 });
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

					return result;
				}
			});
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
	public Integer size(Collection<Object> collection) {
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
	public Set<Object> including(Set<Object> set, Object object) {
		if (set.contains(object)) {
			return set;
		} else {
			Set<Object> result = Sets.newLinkedHashSet(set);
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
	public Set<Object> excluding(Set<Object> set, Object object) {
		if (!set.contains(object)) {
			return set;
		} else {
			Set<Object> result = Sets.newLinkedHashSet(set);
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
		result = "A sequence containing all elements of source sequence plus the given object",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->including('d')", result = "Sequence{'a', 'b', 'c', 'd'}")
		}
	)
	// @formatter:on
	public List<Object> including(List<Object> sequence, Object object) {
		List<Object> result = Lists.newArrayList(sequence);
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
	public List<Object> excluding(List<Object> sequence, Object object) {
		if (!sequence.contains(object)) {
			return sequence;
		} else {
			List<Object> result = Lists.newArrayList(sequence);
			result.removeAll(Collections.singleton(object));
			return result;
		}
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
	public List<Object> asSequence(Collection<Object> collection) {
		if (collection instanceof List) {
			return (List<Object>)collection;
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
	public Set<Object> asSet(Collection<Object> collection) {
		if (collection instanceof Set) {
			return (Set<Object>)collection;
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
	public Set<Object> asOrderedSet(Collection<Object> collection) {
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
	public Object first(Collection<Object> collection) {
		Iterator<Object> iterator = collection.iterator();
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
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
	public List<Object> reverse(List<Object> sequence) {
		return Lists.reverse(sequence);
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
	public Set<Object> reverse(Set<Object> set) {
		return Sets.newLinkedHashSet(Lists.reverse(ImmutableList.copyOf(set)));
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
	public Boolean isEmpty(Collection<Object> collection) {
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
	public Boolean notEmpty(Collection<Object> collection) {
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
	public Object at(List<Object> sequence, Integer position) {
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
	public Set<Object> filter(Set<Object> set, final EClassifier eClassifier) {
		final Set<Object> result;

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
	public Set<Object> filter(Set<Object> set, final Set<EClassifier> eClassifiers) {
		final Set<Object> result;

		if (set == null) {
			result = null;
		} else if (eClassifiers == null || eClassifiers.isEmpty()) {
			result = Sets.newLinkedHashSet();
		} else {
			result = Sets.newLinkedHashSet();
			for (Object object : set) {
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
	public List<Object> filter(List<Object> sequence, final EClassifier eClassifier) {
		final List<Object> result;

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
	public List<Object> filter(List<Object> sequence, final Set<EClassifier> eClassifiers) {
		final List<Object> result;

		if (sequence == null) {
			result = null;
		} else if (eClassifiers == null || eClassifiers.isEmpty()) {
			result = Lists.newArrayList();
		} else {
			result = Lists.newArrayList();
			for (Object object : sequence) {
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
	public List<Object> sep(Collection<Object> collection, Object separator) {
		final List<Object> result;

		if (collection == null) {
			result = null;
		} else {
			result = Lists.newArrayList();

			final Iterator<Object> it = collection.iterator();
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
	public List<Object> sep(Collection<Object> collection, Object prefix, Object separator, Object suffix) {
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
		result = "The last element of the given sequence or null if the given sequence is null",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->last()", result = "'c'")
		}
	)
	// @formatter:on
	public Object last(List<Object> sequence) {
		ListIterator<Object> it = sequence.listIterator(sequence.size());
		if (it.hasPrevious()) {
			return it.previous();
		}
		return null;
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
	public Boolean excludes(Collection<Object> collection, Object object) {
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
	public Boolean includes(Collection<Object> collection, Object object) {
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
	public Set<Object> union(Set<Object> set1, Set<Object> set2) {
		return add(set1, set2);
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
	public List<Object> union(List<Object> sequence1, List<Object> sequence2) {
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
	public Object any(Collection<Object> collection, LambdaValue lambda) {
		Object result = null;

		if (collection != null && lambda == null) {
			result = null;
		} else {
			for (Object input : collection) {
				try {
					if (Boolean.TRUE.equals(lambda.eval(new Object[] {input }))) {
						result = input;
						break;
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
	public Integer count(Set<Object> set, Object object) {
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
	public Boolean exists(Collection<Object> collection, LambdaValue lambda) {
		Boolean result = Boolean.FALSE;

		if (collection != null && lambda != null) {
			for (Object input : collection) {
				try {
					if (Boolean.TRUE.equals(lambda.eval(new Object[] {input }))) {
						result = Boolean.TRUE;
						break;
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
	public Boolean forAll(Collection<Object> collection, LambdaValue lambda) {
		Boolean result = Boolean.TRUE;

		if (collection == null || lambda == null) {
			result = Boolean.FALSE;
		} else {
			for (Object input : collection) {
				try {
					if (!Boolean.TRUE.equals(lambda.eval(new Object[] {input }))) {
						result = Boolean.FALSE;
						break;
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
					result = Boolean.FALSE;
					break;
				}
				// CHECKSTYLE:ON
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
	public Boolean excludesAll(Collection<Object> collection1, Collection<Object> collection2) {
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
	public Boolean includesAll(Collection<Object> collection1, Collection<Object> collection2) {
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
	public Boolean isUnique(Collection<Object> collection, LambdaValue lambda) {
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
	public Boolean one(Collection<Object> self, LambdaValue lambda) {
		boolean result = false;

		if (self != null && lambda == null) {
			result = false;
		} else {
			for (Object input : self) {
				try {
					if (Boolean.TRUE.equals(lambda.eval(new Object[] {input }))) {
						result = !result;
						if (!result) {
							break;
						}
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
	public Double sum(Collection<Object> collection) {
		double result = 0;

		for (Object input : collection) {
			if (input instanceof Number) {
				result += ((Number)input).doubleValue();
			} else {
				throw new IllegalArgumentException("Can only sum numbers.");
			}
		}

		return Double.valueOf(result);
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
	public Integer indexOf(List<Object> sequence, Object object) {
		return Integer.valueOf(sequence.indexOf(object) + 1);
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
	public List<Object> insertAt(List<Object> sequence, Integer position, Object object) {
		final int initialSize = sequence.size();
		if (position < 1 || position > initialSize) {
			throw new IndexOutOfBoundsException();
		}
		final List<Object> result = new ArrayList<Object>(initialSize + 1);

		result.addAll(sequence.subList(0, position - 1));
		result.add(object);
		result.addAll(sequence.subList(position - 1, initialSize));

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given object in a copy of the given sequence at the first position.",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given sequence including the object at the given position",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->prepend('f')", result = "Sequence{'f', 'a', 'b', 'c'}")
		}
	)
	// @formatter:on
	public List<Object> prepend(List<Object> sequence, Object object) {
		final List<Object> result = new ArrayList<Object>(sequence.size() + 1);

		result.add(object);
		result.addAll(sequence);

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
	public Set<Object> subOrderedSet(Set<Object> set, Integer startIndex, Integer endIndex) {
		if (startIndex < 1 || endIndex > set.size() || startIndex > endIndex) {
			throw new IndexOutOfBoundsException();
		}
		final Set<Object> result = new LinkedHashSet<Object>(endIndex - startIndex + 1);

		int index = 1;
		for (Object input : set) {
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
	public List<Object> subSequence(List<Object> sequence, Integer startIndex, Integer endIndex) {
		if (startIndex < 1 || endIndex > sequence.size() || startIndex > endIndex) {
			throw new IndexOutOfBoundsException();
		}
		return Lists.newArrayList(sequence.subList(startIndex - 1, endIndex));
	}

}
