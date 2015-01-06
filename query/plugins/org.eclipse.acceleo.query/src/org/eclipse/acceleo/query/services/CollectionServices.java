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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.collections.LazyList;
import org.eclipse.acceleo.query.collections.LazySet;
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
		} else if ("add".equals(publicMethod.getName()) || "concat".equals(publicMethod.getName())) {
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
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
					final Set<IType> result = new LinkedHashSet<IType>();

					final LambdaType lambdaType = (LambdaType)argTypes.get(1);
					final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
					if (isBooleanType(lambdaExpressionType)) {
						if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
							result.add(new SequenceType(((ICollectionType)argTypes.get(0))
									.getCollectionType()));
						} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
							result.add(new SetType(((ICollectionType)argTypes.get(0)).getCollectionType()));
						}
					} else {
						result.add(new NothingType("expression in a reject must return a boolean"));
					}

					return result;
				}
			};

		} else if ("select".equals(publicMethod.getName())) {
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
					final Set<IType> result = new LinkedHashSet<IType>();
					final LambdaType lambdaType = (LambdaType)argTypes.get(1);
					final Object lambdaExpressionType = lambdaType.getLambdaExpressionType().getType();
					if (isBooleanType(lambdaExpressionType)) {
						IType lambdaEvaluatorType = lambdaType.getLambdaEvaluatorType();
						if (lambdaEvaluatorType instanceof EClassifierLiteralType) {
							lambdaEvaluatorType = new EClassifierType(
									((EClassifierLiteralType)lambdaEvaluatorType).getType());
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
			};
		} else if ("including".equals(publicMethod.getName())) {
			result = new Service(publicMethod, this) {

				@Override
				public Set<IType> getType(ValidationServices services, EPackageProvider provider,
						List<IType> argTypes) {
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
			};
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
		return new LazyList<Object>(Iterables.concat(c1, c2));
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
		return Sets.union(l1, l2);
	}

	/**
	 * Select returns a filtered version of the specified list.
	 * 
	 * @param l1
	 *            the original list.
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified set.
	 */
	public List<Object> select(List<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final List<Object> newList;

		if (lambda == null) {
			newList = Lists.newArrayList();
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
	 * Select returns a filtered version of the specified list.
	 * 
	 * @param l1
	 *            the original list.
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified set.
	 */
	public Set<Object> select(Set<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final Set<Object> newSet;

		if (lambda == null) {
			newSet = Sets.newLinkedHashSet();
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
	 * Select returns a filtered version of the specified set.
	 * 
	 * @param l1
	 *            the original set
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified set.
	 */
	public Set<Object> reject(Set<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final Set<Object> newSet;

		if (lambda == null) {
			newSet = Sets.newLinkedHashSet();
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
	 * Select returns a filtered version of the specified set.
	 * 
	 * @param l1
	 *            the original set
	 * @param lambda
	 *            the predicate expression
	 * @return a filtered version of the specified set.
	 */
	public List<Object> reject(List<Object> l1, Lambda lambda) {
		// TODO use lazy collection
		final List<Object> newList;

		if (lambda == null) {
			newList = Lists.newArrayList();
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
	 * Returns the collection on which all the elements of the collection2 have been removed.
	 * 
	 * @param collection1
	 *            The source collection.
	 * @param collection2
	 *            The collection to remove.
	 * @return The collection on which all the elements of the collection2 have been removed.
	 */
	public Collection<Object> removeAll(Collection<Object> collection1, Collection<Object> collection2) {
		collection1.removeAll(collection2);
		return collection1;
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
			result = new LazySet<Object>(Iterables.filter(set, new Predicate<Object>() {

				@Override
				public boolean apply(Object object) {
					return eClassifier.isInstance(object);
				}

			}));
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
			result = new LazyList<Object>(Iterables.filter(list, new Predicate<Object>() {

				@Override
				public boolean apply(Object object) {
					return eClassifier.isInstance(object);
				}

			}));
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
		final Object result;

		if (list == null || list.size() == 0) {
			result = null;
		} else {
			result = list.get(list.size() - 1);
		}

		return result;
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

}
