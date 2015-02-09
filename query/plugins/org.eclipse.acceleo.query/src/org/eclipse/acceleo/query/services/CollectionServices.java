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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

/**
 * Implementation of the collection services of the Acceleo Query language.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class CollectionServices extends AbstractServiceProvider {

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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(lambdaExpressionType)) {
				result.addAll(super.getType(services, provider, argTypes));
			} else {
				result.add(new NothingType("expression in " + getServiceMethod().getName()
						+ " must return a boolean"));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(lambdaExpressionType)) {
				IType lambdaEvaluatorType = lambdaType.getLambdaEvaluatorType();
				if (lambdaEvaluatorType instanceof EClassifierLiteralType) {
					lambdaEvaluatorType = new EClassifierType(((EClassifierLiteralType)lambdaEvaluatorType)
							.getType());
				}
				result.add(lambdaEvaluatorType);
			} else {
				result.add(new NothingType("expression in an any must return a boolean"));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(((ICollectionType)argTypes.get(0)).getCollectionType()));
				result.add(new SequenceType(argTypes.get(1)));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(((ICollectionType)argTypes.get(0)).getCollectionType()));
				result.add(new SetType(argTypes.get(1)));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(lambdaType.getLambdaExpressionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(lambdaType.getLambdaExpressionType()));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(lambdaExpressionType)) {
				IType lambdaEvaluatorType = lambdaType.getLambdaEvaluatorType();
				if (lambdaEvaluatorType instanceof EClassifierLiteralType) {
					lambdaEvaluatorType = new EClassifierType(((EClassifierLiteralType)lambdaEvaluatorType)
							.getType());
				}
				if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SequenceType(lambdaEvaluatorType));
				} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SetType(lambdaEvaluatorType));
				}
			} else {
				result.add(new NothingType("expression in a select must return a boolean"));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
			if (isBooleanType(lambdaExpressionType)) {
				if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SequenceType(((ICollectionType)argTypes.get(0)).getCollectionType()));
				} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
					result.add(new SetType(((ICollectionType)argTypes.get(0)).getCollectionType()));
				}
			} else {
				result.add(new NothingType("expression in a reject must return a boolean"));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
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

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#getType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      java.util.List)
		 */
		@Override
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(((ICollectionType)argTypes.get(0)).getCollectionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(((ICollectionType)argTypes.get(0)).getCollectionType()));
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

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#getType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
		 *      java.util.List)
		 */
		@Override
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(((ICollectionType)argTypes.get(0)).getCollectionType()));
				result.add(new SequenceType(((ICollectionType)argTypes.get(1)).getCollectionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(((ICollectionType)argTypes.get(0)).getCollectionType()));
				result.add(new SetType(((ICollectionType)argTypes.get(1)).getCollectionType()));
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
	private static final class SecondArgumentTypeInFirstArgumentCollectionType extends Service {

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
		public java.util.Set<IType> getType(ValidationServices services, EPackageProvider provider,
				java.util.List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();

			final EClassifierType rawType = new EClassifierType(((EClassifierLiteralType)argTypes.get(1))
					.getType());
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(rawType));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(rawType));
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
		public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
			final Set<IType> result = new LinkedHashSet<IType>();
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(((ICollectionType)argTypes.get(0)).getCollectionType()));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(((ICollectionType)argTypes.get(0)).getCollectionType()));
			}
			return result;
		}

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider#getService(java.lang.reflect.Method)
	 */
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
		} else if ("first".equals(publicMethod.getName()) || "at".equals(publicMethod.getName())
				|| "last".equals(publicMethod.getName())) {
			result = new FirstArgumentRawCollectionType(publicMethod, this);
		} else if ("excluding".equals(publicMethod.getName()) || "sub".equals(publicMethod.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this);
		} else if ("reject".equals(publicMethod.getName())) {
			result = new RejectService(publicMethod, this);
		} else if ("select".equals(publicMethod.getName())) {
			result = new SelectService(publicMethod, this);
		} else if ("collect".equals(publicMethod.getName())) {
			result = new CollectService(publicMethod, this);
		} else if ("including".equals(publicMethod.getName())) {
			result = new IncludingService(publicMethod, this);
		} else if ("sep".equals(publicMethod.getName())) {
			if (publicMethod.getParameterTypes().length == 2) {
				result = new Service(publicMethod, this) {

					@Override
					public Set<IType> getType(ValidationServices services, EPackageProvider provider,
							List<IType> argTypes) {
						final Set<IType> result = new LinkedHashSet<IType>();

						result.add(argTypes.get(0));
						result.add(new SequenceType(argTypes.get(1)));

						return result;
					}
				};
			} else if (publicMethod.getParameterTypes().length == 4) {
				result = new Service(publicMethod, this) {

					@Override
					public Set<IType> getType(ValidationServices services, EPackageProvider provider,
							List<IType> argTypes) {
						final Set<IType> result = new LinkedHashSet<IType>();

						result.add(argTypes.get(0));
						result.add(new SequenceType(argTypes.get(1)));
						result.add(new SequenceType(argTypes.get(2)));
						result.add(new SequenceType(argTypes.get(3)));

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
		} else {
			result = new Service(publicMethod, this);
		}
		return result;
	}

	/**
	 * Tells if the given {@link Object} is a is a boolean {@link org.eclipse.emf.ecore.EDataType EDataType}.
	 * 
	 * @param type
	 *            the {@link org.eclipse.emf.ecore.EDataType EDataType}
	 * @return <code>true</code> if the given {@link Object} is a is a boolean
	 *         {@link org.eclipse.emf.ecore.EDataType EDataType}, <code>false</code> otherwise
	 */
	private static boolean isBooleanType(Object type) {
		return type instanceof EClassifier
				&& (((EClassifier)type).getInstanceClass() == Boolean.class || ((EClassifier)type)
						.getInstanceClass() == boolean.class);
	}

	/**
	 * Returns the concatenation of the two specified lists.
	 * 
	 * @param c1
	 *            the first operand
	 * @param c2
	 *            the second operand.
	 * @return the concatenation of the two specified operands.
	 */
	public List<Object> concat(List<Object> c1, List<Object> c2) {
		// TODO use lazy collection
		final List<Object> result;

		if (c1.isEmpty()) {
			result = c2;
		} else if (c2.isEmpty()) {
			result = c1;
		} else {
			result = Lists.newArrayList(c1);
			result.addAll(c2);
		}

		return result;
	}

	/**
	 * Returns the concatenation of the two specified lists.
	 * 
	 * @param l1
	 *            the first operand
	 * @param l2
	 *            the second operand.
	 * @return the concatenation of the two specified operands.
	 */

	public List<Object> add(List<Object> l1, List<Object> l2) {
		return concat(l1, l2);
	}

	/**
	 * Returns the difference of the two specified lists.
	 * 
	 * @param l1
	 *            the first operand
	 * @param l2
	 *            the second operand.
	 * @return the sequence that contains elements from l1 that are not in l2.
	 */

	public List<Object> sub(List<Object> l1, List<Object> l2) {
		// TODO use lazy collection predicates + !contains
		if (l2.isEmpty()) {
			return l1;
		} else {
			List<Object> result = Lists.newArrayList(l1);
			for (Object obj : l2) {
				result.remove(obj);
			}
			return result;
		}
	}

	/**
	 * Returns the difference of the two specified lists.
	 * 
	 * @param l1
	 *            the first operand
	 * @param l2
	 *            the second operand.
	 * @return the sequence that contains elements from l1 that are not in l2.
	 */

	public Set<Object> sub(Set<Object> l1, Set<Object> l2) {
		// TODO use lazy collection predicates + !contains
		if (l2.isEmpty()) {
			return l1;
		} else {
			Set<Object> result = Sets.newLinkedHashSet(l1);
			for (Object obj : l2) {
				result.remove(obj);
			}
			return result;
		}
	}

	/**
	 * Returns the concatenation of the two specified lists.
	 * 
	 * @param l1
	 *            the first operand
	 * @param l2
	 *            the second operand.
	 * @return the concatenation of the two specified operands.
	 */

	public Set<Object> add(Set<Object> l1, Set<Object> l2) {
		// TODO use lazy collection ? see union java doc
		return Sets.union(l1, l2);
	}

	/**
	 * Select returns a filtered version of the specified {@link List}.
	 * 
	 * @param l1
	 *            the original {@link List}.
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified {@link List}.
	 */
	public List<Object> select(List<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final List<Object> newList;

		if (lambda == null) {
			newList = Collections.emptyList();
		} else {
			newList = Lists.newArrayList();
			for (Object elt : l1) {
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

	/**
	 * Select returns a filtered version of the specified {@link Set}.
	 * 
	 * @param l1
	 *            the original {@link Set}.
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified {@link Set}.
	 */
	public Set<Object> select(Set<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final Set<Object> newSet;

		if (lambda == null) {
			newSet = Collections.emptySet();
		} else {
			newSet = Sets.newLinkedHashSet();
			for (Object elt : l1) {
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

	/**
	 * Select returns a filtered version of the specified {@link Set}.
	 * 
	 * @param l1
	 *            the original {@link Set}
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified {@link Set}.
	 */
	public Set<Object> reject(Set<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final Set<Object> newSet;

		if (lambda == null) {
			newSet = Collections.emptySet();
		} else {
			newSet = Sets.newLinkedHashSet();
			for (Object elt : l1) {
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

	/**
	 * Select returns a filtered version of the specified {@link List}.
	 * 
	 * @param l1
	 *            the original {@link List}
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified {@link List}.
	 */
	public List<Object> reject(List<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final List<Object> newList;

		if (lambda == null) {
			newList = Collections.emptyList();
		} else {
			newList = Lists.newArrayList();
			for (Object elt : l1) {
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

	/**
	 * Collects elements from the given {@link Set} using the given navigation {@link Lambda}.
	 * 
	 * @param set
	 *            the original {@link Set}
	 * @param lambda
	 *            the predicate expression
	 * @return a navigated version of the specified {@link Set}.
	 */
	public Set<Object> collect(Set<Object> set, Lambda lambda) {
		// TODO use lazy collection
		final Set<Object> result;

		if (lambda == null) {
			result = Collections.emptySet();
		} else {
			result = Sets.newLinkedHashSet();
			for (Object elt : set) {
				try {
					result.add(lambda.eval(new Object[] {elt }));
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
			}
		}

		return result;
	}

	/**
	 * Collects elements from the given {@link List} using the given navigation {@link Lambda}.
	 * 
	 * @param list
	 *            the original {@link List}
	 * @param lambda
	 *            the predicate expression
	 * @return a navigated version of the specified {@link List}.
	 */
	public List<Object> collect(List<Object> list, Lambda lambda) {
		// TODO use lazy collection
		final List<Object> result;

		if (lambda == null) {
			result = Collections.emptyList();
		} else {
			result = Lists.newArrayList();
			for (Object elt : list) {
				try {
					result.add(lambda.eval(new Object[] {elt }));
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// TODO: log the exception.
				}
				// CHECKSTYLE:ON
			}
		}

		return result;
	}

	/**
	 * Returns the size of the specified collection.
	 * 
	 * @param collection
	 *            the input collection
	 * @return the size of the specified collection.
	 */
	public Integer size(Collection<Object> collection) {
		return collection.size();
	}

	/**
	 * Returns the {@link Set} containing all elements of source {@link Set} plus the given {@link Object}.
	 * 
	 * @param source
	 *            the source {@link Set}
	 * @param object
	 *            the {@link Object} to add
	 * @return the {@link Set} containing all elements of source {@link Set} plus the given {@link Object}.
	 */
	public Set<Object> including(Set<Object> source, Object object) {
		// TODO use lazy collection
		if (source.contains(object)) {
			return source;
		} else {
			Set<Object> result = Sets.newLinkedHashSet(source);
			result.add(object);
			return result;
		}
	}

	/**
	 * Returns the {@link Set} containing all elements of source {@link Set} without the given {@link Object}.
	 * 
	 * @param source
	 *            the source {@link Set}
	 * @param object
	 *            the {@link Object} to add
	 * @return the {@link Set} containing all elements of source {@link Set} without the given {@link Object}.
	 */
	public Set<Object> excluding(Set<Object> source, Object object) {
		// TODO use lazy collection
		if (!source.contains(object)) {
			return source;
		} else {
			Set<Object> result = Sets.newLinkedHashSet(source);
			result.remove(object);
			return result;
		}
	}

	/**
	 * Returns the {@link List} containing all elements of source {@link List} plus the given {@link Object}.
	 * 
	 * @param source
	 *            the source {@link List}
	 * @param object
	 *            the {@link Object} to add
	 * @return the set containing all elements of source {@link List} plus the given {@link Object}.
	 */
	public List<Object> including(List<Object> source, Object object) {
		// TODO use lazy collection
		if (source.contains(object)) {
			return source;
		} else {
			List<Object> result = Lists.newArrayList(source);
			result.add(object);
			return result;
		}
	}

	/**
	 * Returns the {@link List} containing all elements of source {@link List} without the given
	 * {@link Object}.
	 * 
	 * @param source
	 *            the source {@link List}
	 * @param object
	 *            the {@link Object} to add
	 * @return the {@link List} containing all elements of source {@link List} without the given
	 *         {@link Object}.
	 */
	public List<Object> excluding(List<Object> source, Object object) {
		// TODO use lazy collection
		if (!source.contains(object)) {
			return source;
		} else {
			List<Object> result = Lists.newArrayList(source);
			result.remove(object);
			return result;
		}
	}

	/**
	 * Returns a {@link List} representation of the specified {@link Collection}. Returns the same object if
	 * it is a {@link List} already.
	 * 
	 * @param collection
	 *            the input collection
	 * @return a list with all the elements in collection.
	 */
	public List<Object> asSequence(Collection<Object> collection) {
		// TODO use lazy collection
		if (collection instanceof List) {
			return (List<Object>)collection;
		} else {
			return Lists.newArrayList(collection);
		}
	}

	/**
	 * Returns a {@link Set} representation of the specified {@link Collection}. Returns the same object if it
	 * is a {@link Set} already.
	 * 
	 * @param collection
	 *            the input {@link Collection}
	 * @return a {@link Set} with all the elements in {@link Collection}.
	 */
	public Set<Object> asSet(Collection<Object> collection) {
		// TODO use lazy collection
		if (collection instanceof Set) {
			return (Set<Object>)collection;
		} else {
			return Sets.newLinkedHashSet(collection);
		}
	}

	/**
	 * Same as {@link CollectionServices#asSet(Collection) asSet}.
	 * 
	 * @param collection
	 *            the input {@link Collection}
	 * @return {@link CollectionServices#asSet(Collection) asSet}
	 */
	public Set<Object> asOrderedSet(Collection<Object> collection) {
		return asSet(collection);
	}

	/**
	 * Returns the first element of the specified list.
	 * 
	 * @param list
	 *            the input list.
	 * @return the first element of the list.
	 */
	public Object first(List<Object> list) {
		return list.get(0);
	}

	/**
	 * Returns <code>true</code> when the specified list is empty.
	 * 
	 * @param collection
	 *            the tested collection
	 * @return <code>true</code> when the specified list is empty.
	 */
	public Boolean isEmpty(Collection<Object> collection) {
		return collection.isEmpty();
	}

	/**
	 * Returns <code>true</code> when the specified list is empty.
	 * 
	 * @param collection
	 *            the tested collection
	 * @return <code>true</code> when the specified list is empty.
	 */
	public Boolean notEmpty(Collection<Object> collection) {
		return !collection.isEmpty();
	}

	/**
	 * Returns the element at the indicated position in the list.
	 * 
	 * @param list
	 *            the input list.
	 * @param position
	 *            the position of the element to return [1..n].
	 * @return the element at the indicated position in the list.
	 */
	public Object at(List<Object> list, Integer position) {
		return list.get(position - 1);
	}

	/**
	 * Keeps instances of the given {@link EClassifier} from the given {@link Set}.
	 * 
	 * @param set
	 *            the {@link Set} to filter
	 * @param eClassifier
	 *            the {@link EClassifier} to keep
	 * @return a new {@link Set} containing instances of the given {@link EClassifier} from the given
	 *         {@link Set}, or <code>null</code> if the given {@link Set} is <code>null</code>
	 */
	public Set<Object> filter(Set<Object> set, final EClass eClassifier) {
		// TODO use lazy collection
		final Set<Object> result;

		if (set == null) {
			result = null;
		} else if (eClassifier == null) {
			result = Sets.newLinkedHashSet();
		} else {
			result = Sets.newLinkedHashSet();
			for (Object object : set) {
				if (eClassifier.isInstance(object)) {
					result.add(object);
				}
			}
		}

		return result;
	}

	/**
	 * Keeps instances of the given {@link EClassifier} from the given {@link List}.
	 * 
	 * @param list
	 *            the {@link List} to filter
	 * @param eClassifier
	 *            the {@link EClassifier} to keep
	 * @return a new {@link List} containing instances of the given {@link EClassifier} from the given
	 *         {@link List}, or <code>null</code> if the given {@link List} is <code>null</code>
	 */
	public List<Object> filter(List<Object> list, final EClassifier eClassifier) {
		// TODO use lazy collection
		final List<Object> result;

		if (list == null) {
			result = null;
		} else if (eClassifier == null) {
			result = Lists.newArrayList();
		} else {
			result = Lists.newArrayList();
			for (Object object : list) {
				if (eClassifier.isInstance(object)) {
					result.add(object);
				}
			}
		}

		return result;
	}

	/**
	 * Inserts the given separator between each elements of the given {@link Collection}.
	 * 
	 * @param collection
	 *            the {@link Collection}
	 * @param separator
	 *            the separator to insert
	 * @return a new {@link List}, or <code>null</code> if the given {@link Collection} is <code>null</code>
	 */
	public List<Object> sep(Collection<Object> collection, Object separator) {
		// TODO use lazy collection
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

	/**
	 * Inserts the given separator between each elements of the given {@link Collection}, the given prefix
	 * before the first element, and the given suffix after the last element.
	 * 
	 * @param collection
	 *            the {@link Collection}
	 * @param prefix
	 *            the prefix to add
	 * @param suffix
	 *            the suffix to add
	 * @param separator
	 *            the separator to insert
	 * @return a new {@link List}
	 */
	public List<Object> sep(Collection<Object> collection, Object prefix, Object separator, Object suffix) {
		// TODO use lazy collection
		final List<Object> result = Lists.newArrayList();

		result.add(prefix);
		if (collection != null) {
			result.addAll(sep(collection, separator));
		}
		result.add(suffix);

		return result;
	}

	/**
	 * Gets the last element of the given {@link List}.
	 * 
	 * @param list
	 *            the {@link List}
	 * @return the last element of the given {@link List}, or <code>null</code> if the given {@link List} is
	 *         <code>null</code>
	 */
	public Object last(List<Object> list) {
		return list.get(list.size() - 1);
	}

	/**
	 * Tells if the given {@link Collection} doesn't {@link Collection#contains(Object) contains} the given
	 * {@link Object}.
	 * 
	 * @param collection
	 *            the {@link Collection}
	 * @param object
	 *            the {@link Object}
	 * @return <code>true</code> if the given {@link Collection} doesn't {@link Collection#contains(Object)
	 *         contains} the given {@link Object}, <code>false</code> otherwise
	 */
	public Boolean excludes(Collection<Object> collection, Object object) {
		return Boolean.valueOf(!collection.contains(object));
	}

	/**
	 * Tells if the given {@link Collection} {@link Collection#contains(Object) contains} the given
	 * {@link Object}.
	 * 
	 * @param collection
	 *            the {@link Collection}
	 * @param object
	 *            the {@link Object}
	 * @return <code>true</code> if the given {@link Collection} {@link Collection#contains(Object) contains}
	 *         the given {@link Object}, <code>false</code> otherwise
	 */
	public Boolean includes(Collection<Object> collection, Object object) {
		return Boolean.valueOf(collection.contains(object));
	}

	/**
	 * Makes the union of the first {@link Set} with the second {@link Set}.
	 * 
	 * @param c1
	 *            the first {@link Set}
	 * @param c2
	 *            the second {@link Set}
	 * @return the union of the first {@link Set} with the second {@link Set}
	 */
	public Set<Object> union(Set<Object> c1, Set<Object> c2) {
		return add(c1, c2);
	}

	/**
	 * Makes the union of the first {@link List} with the second {@link List}.
	 * 
	 * @param c1
	 *            the first {@link List}
	 * @param c2
	 *            the second {@link List}
	 * @return the union of the first {@link List} with the second {@link List}
	 */
	public List<Object> union(List<Object> c1, List<Object> c2) {
		return concat(c1, c2);
	}

	/**
	 * Gets the first element in the given {@link Collection} for which the {@link Lambda} is
	 * {@link Lambda#eval(Object[]) evaluated} to <code>true</code>.
	 * 
	 * @param self
	 *            the {@link Collection}
	 * @param lambda
	 *            the {@link Lambda}
	 * @return the first element in the given {@link Collection} for which the {@link Lambda} is
	 *         {@link Lambda#eval(Object[]) evaluated} to <code>true</code> if any, <code>null</code>
	 *         otherwise
	 */
	public Object any(Collection<Object> self, Lambda lambda) {
		Object result = null;

		if (self != null && lambda == null) {
			result = null;
		} else {
			for (Object input : self) {
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

	/**
	 * Counts the number of times the given object is present in the given {@link Set}.
	 * 
	 * @param set
	 *            the {@link Set}
	 * @param object
	 *            the {@link Object}
	 * @return the number of times the given object is present in the given {@link Set}
	 */
	public Integer count(Set<Object> set, Object object) {
		final Integer result;

		if (set.contains(object)) {
			result = Integer.valueOf(1);
		} else {
			result = Integer.valueOf(0);
		}

		return result;
	}

	/**
	 * Counts the number of times the given object is present in the given {@link List}.
	 * 
	 * @param list
	 *            the {@link List}
	 * @param object
	 *            the {@link Object}
	 * @return the number of times the given object is present in the given {@link List}
	 */
	public Integer count(List<Object> list, Object object) {
		int result = 0;

		if (object == null) {
			for (Object input : list) {
				if (input == null) {
					++result;
				}
			}
		} else {
			for (Object input : list) {
				if (object.equals(input)) {
					++result;
				}
			}
		}

		return Integer.valueOf(result);
	}

	/**
	 * Tells if it exists an {@link Object} from the given {@link Collection} that validate the given
	 * {@link Lambda}.
	 * 
	 * @param collection
	 *            the {@link Collection}
	 * @param lambda
	 *            the {@link Lambda}
	 * @return <code>true</code> if it exists an {@link Object} from the given {@link Collection} that
	 *         validate the given {@link Lambda}, <code>false</code> otherwise
	 */
	public Boolean exists(Collection<Object> collection, Lambda lambda) {
		Boolean result = Boolean.FALSE;

		if (collection != null && lambda == null) {
			result = Boolean.FALSE;
		} else {
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

	/**
	 * Tells if all {@link Object} form the given {@link Collection} validates the given {@link Lambda}.
	 * 
	 * @param collection
	 *            the {@link Collection}
	 * @param lambda
	 *            the {@link Lambda}
	 * @return <code>true</code> if all {@link Object} form the given {@link Collection} validates the given
	 *         {@link Lambda}, <code>false</code> otherwise
	 */
	public Boolean forAll(Collection<Object> collection, Lambda lambda) {
		Boolean result = Boolean.TRUE;

		if (collection != null && lambda == null) {
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

	/**
	 * Tells if no elements of c2 are {@link Collection#contains(Object) contained} in self.
	 * 
	 * @param self
	 *            the current {@link Collection}
	 * @param c2
	 *            the other {@link Collection}
	 * @return <code>true</code> if no elements of c2 are {@link Collection#contains(Object) contained} in
	 *         self, <code>false</code> otherwise
	 */
	public Boolean excludesAll(Collection<Object> self, Collection<Object> c2) {
		boolean result = true;

		for (Object input : c2) {
			if (self.contains(input)) {
				result = false;
				break;
			}
		}

		return Boolean.valueOf(result);
	}

	/**
	 * Tells if all elements of c2 are {@link Collection#contains(Object) contained} in self.
	 * 
	 * @param self
	 *            the current {@link Collection}
	 * @param c2
	 *            the other {@link Collection}
	 * @return <code>true</code> if all elements of c2 are {@link Collection#contains(Object) contained} in
	 *         self, <code>false</code> otherwise
	 */
	public Boolean includesAll(Collection<Object> self, Collection<Object> c2) {
		return Boolean.valueOf(self.containsAll(c2));
	}

	/**
	 * Tells if {@link Lambda#eval(Object[]) evaluation} of the given lambda gives a different value for all
	 * element of the given {@link Collection}.
	 * 
	 * @param self
	 *            the {@link Collection}
	 * @param lambda
	 *            the {@link Lambda}
	 * @return <code>true</code> if {@link Lambda#eval(Object[]) evaluation} of the given lambda gives a
	 *         different value for all element of the given {@link Collection}, <code>false</code> otherwise
	 */
	public Boolean isUnique(Collection<Object> self, Lambda lambda) {
		boolean result = true;
		final Set<Object> evaluated = Sets.newHashSet();

		if (self != null && lambda == null) {
			result = false;
		} else {
			for (Object input : self) {
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

	/**
	 * Tells if one and only one element of the given {@link Collection} validates the given {@link Lambda}.
	 * 
	 * @param self
	 *            the current {@link Collection}
	 * @param lambda
	 *            the {@link Lambda}
	 * @return <code>true</code> if one and only one element of the given {@link Collection} validates the
	 *         given {@link Lambda}, <code>false</code> otherwise
	 */
	public Boolean one(Collection<Object> self, Lambda lambda) {
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

	/**
	 * Sums elements of the given {@link Collection} if possible.
	 * 
	 * @param self
	 *            the current {@link Collection}
	 * @return the sum of elements of the given {@link Collection} if possible, throw an exception otherwise
	 */
	public Double sum(Collection<Object> self) {
		double result = 0;

		for (Object input : self) {
			if (input instanceof Number) {
				result += ((Number)input).doubleValue();
			} else {
				throw new IllegalArgumentException("Can only sum numbers.");
			}
		}

		return Double.valueOf(result);
	}

	/**
	 * Gets the index of the given {@link Object} in the given {@link List}.
	 * 
	 * @param self
	 *            the {@link List}
	 * @param object
	 *            the {@link Object}
	 * @return the index of the given {@link Object} in the given {@link List} [1..n]
	 */
	public Integer indexOf(List<Object> self, Object object) {
		return Integer.valueOf(self.indexOf(object) + 1);
	}

	/**
	 * Inserts the given {@link Object} in a copy of the given {@link List} at the given position ([1..n]).
	 * 
	 * @param list
	 *            the {@link List}
	 * @param position
	 *            the position
	 * @param object
	 *            the {@link Object}
	 * @return a copy of the given {@link List}
	 */
	public List<Object> insertAt(List<Object> list, Integer position, Object object) {
		final int initialSize = list.size();
		if (position < 1 || position > initialSize) {
			throw new IndexOutOfBoundsException();
		}
		// TODO use lazy collection
		final List<Object> result = new ArrayList<Object>(initialSize + 1);

		result.addAll(list.subList(0, position - 1));
		result.add(object);
		result.addAll(list.subList(position - 1, initialSize));

		return result;
	}

	/**
	 * Inserts the given {@link Object} in a copy of the given {@link List} at position 1.
	 * 
	 * @param list
	 *            the {@link List}
	 * @param object
	 *            the {@link Object}
	 * @return a copy of the given {@link List}
	 */
	public List<Object> prepend(List<Object> list, Object object) {
		// TODO use lazy collection
		final List<Object> result = new ArrayList<Object>(list.size() + 1);

		result.add(object);
		result.addAll(list);

		return result;
	}

	/**
	 * Creates a {@link Set} with elements from the given {@link Set} that are also present in the given
	 * {@link Set}.
	 * 
	 * @param set1
	 *            the {@link Set}
	 * @param set2
	 *            the {@link Set}
	 * @return the created {@link Set} with elements from the given {@link Set} that are also present in the
	 *         given {@link Set}
	 */
	public Set<Object> intersection(Set<Object> set1, Set<Object> set2) {
		// TODO use lazy collection
		final Set<Object> result = Sets.newHashSet();

		for (Object input : set1) {
			if (set2.contains(input)) {
				result.add(input);
			}
		}

		return result;
	}

	/**
	 * Gets a subset of the given {@link Set}.
	 * 
	 * @param set
	 *            the {@link Set}
	 * @param startIndex
	 *            low end point (inclusive) of the sub-set
	 * @param endIndex
	 *            high end point (inclusive) of the sub-set
	 * @return a subset of the given {@link Set}
	 * @throws IndexOutOfBoundsException
	 *             for an illegal end point value (
	 *             <code>startIndex &lt; 1 || endIndex > set.size() || startIndex > endIndex</code>)
	 */
	public Set<Object> subOrderedSet(Set<Object> set, Integer startIndex, Integer endIndex) {
		if (startIndex < 1 || endIndex > set.size() || startIndex > endIndex) {
			throw new IndexOutOfBoundsException();
		}
		// TODO use lazy collection
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

}
