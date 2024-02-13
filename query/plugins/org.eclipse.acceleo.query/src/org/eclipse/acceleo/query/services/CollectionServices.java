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
package org.eclipse.acceleo.query.services;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Other;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.acceleo.annotations.api.documentation.Throw;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.services.collection.AbstractCollectionService;
import org.eclipse.acceleo.query.services.collection.AnyService;
import org.eclipse.acceleo.query.services.collection.BooleanLambdaService;
import org.eclipse.acceleo.query.services.collection.ClosureService;
import org.eclipse.acceleo.query.services.collection.CollectService;
import org.eclipse.acceleo.query.services.collection.FirstArgumentRawCollectionType;
import org.eclipse.acceleo.query.services.collection.FirstCollectionTypeService;
import org.eclipse.acceleo.query.services.collection.IncludingService;
import org.eclipse.acceleo.query.services.collection.InsertAtService;
import org.eclipse.acceleo.query.services.collection.IntersectionService;
import org.eclipse.acceleo.query.services.collection.NumberService;
import org.eclipse.acceleo.query.services.collection.RejectService;
import org.eclipse.acceleo.query.services.collection.ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType;
import org.eclipse.acceleo.query.services.collection.ReturnCollectionTypeWithFirstArgumentRawCollectionType;
import org.eclipse.acceleo.query.services.collection.SecondArgumentTypeInFirstArgumentCollectionType;
import org.eclipse.acceleo.query.services.collection.SelectService;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;

//@formatter:off
@ServiceProvider(
	value = "Services available for Collections"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class CollectionServices extends AbstractServiceProvider {

	@Override
	protected IService<Method> getService(Method publicMethod, boolean forWorkspace) {
		final IService<Method> result;

		if ("filter".equals(publicMethod.getName())) {
			result = new SecondArgumentTypeInFirstArgumentCollectionType(publicMethod, this, forWorkspace);
		} else if ("add".equals(publicMethod.getName()) || "concat".equals(publicMethod.getName()) || "union"
				.equals(publicMethod.getName())) {
			result = new ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType(publicMethod, this,
					forWorkspace);
		} else if ("asSequence".equals(publicMethod.getName()) || "asSet".equals(publicMethod.getName())
				|| "asOrderedSet".equals(publicMethod.getName())) {
			result = new ReturnCollectionTypeWithFirstArgumentRawCollectionType(publicMethod, this,
					forWorkspace);
		} else if ("subOrderedSet".equals(publicMethod.getName()) || "subSequence".equals(publicMethod
				.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this, forWorkspace);
		} else if ("drop".equals(publicMethod.getName()) || "dropRight".equals(publicMethod.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this, forWorkspace);
		} else if ("first".equals(publicMethod.getName()) || "at".equals(publicMethod.getName()) || "last"
				.equals(publicMethod.getName())) {
			result = new FirstArgumentRawCollectionType(publicMethod, this, forWorkspace);
		} else if ("excluding".equals(publicMethod.getName()) || "sub".equals(publicMethod.getName())
				|| "reverse".equals(publicMethod.getName())) {
			result = new FirstCollectionTypeService(publicMethod, this, forWorkspace);
		} else if ("sortedBy".equals(publicMethod.getName())) {
			result = new SortedByService(publicMethod, this, forWorkspace);
		} else if ("isUnique".equals(publicMethod.getName())) {
			result = new IsUniqueService(publicMethod, this, forWorkspace);
		} else if ("reject".equals(publicMethod.getName())) {
			result = new RejectService(publicMethod, this, forWorkspace);
		} else if ("select".equals(publicMethod.getName())) {
			result = new SelectService(publicMethod, this, forWorkspace);
		} else if ("collect".equals(publicMethod.getName())) {
			result = new CollectService(publicMethod, this, forWorkspace);
		} else if ("closure".equals(publicMethod.getName())) {
			result = new ClosureService(publicMethod, this, forWorkspace);
		} else if ("including".equals(publicMethod.getName()) || "prepend".equals(publicMethod.getName())
				|| "append".equals(publicMethod.getName())) {
			result = new IncludingService(publicMethod, this, forWorkspace);
		} else if ("sep".equals(publicMethod.getName())) {
			if (publicMethod.getParameterTypes().length == 2) {
				result = new JavaMethodService(publicMethod, this, forWorkspace) {

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
				result = new JavaMethodService(publicMethod, this, forWorkspace) {

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
				result = new JavaMethodService(publicMethod, this, forWorkspace);
			}
		} else if ("any".equals(publicMethod.getName())) {
			result = new AnyService(publicMethod, this, forWorkspace);
		} else if ("exists".equals(publicMethod.getName()) || "forAll".equals(publicMethod.getName()) || "one"
				.equals(publicMethod.getName())) {
			result = new BooleanLambdaService(publicMethod, this, forWorkspace);
		} else if ("insertAt".equals(publicMethod.getName())) {
			result = new InsertAtService(publicMethod, this, forWorkspace);
		} else if ("intersection".equals(publicMethod.getName())) {
			result = new IntersectionService(publicMethod, this, forWorkspace);
		} else if ("sum".equals(publicMethod.getName()) || "min".equals(publicMethod.getName()) || "max"
				.equals(publicMethod.getName())) {
			result = new NumberService(publicMethod, this, forWorkspace);
		} else {
			result = new JavaMethodService(publicMethod, this, forWorkspace);
		}
		return result;
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
		if (sequence == null) {
			throw new NullPointerException();
		}
		final List<T> result;

		if (collection.isEmpty()) {
			result = (List<T>)sequence;
		} else {
			result = new ArrayList<T>(sequence);
			result.addAll(collection);
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
		if (set == null) {
			throw new NullPointerException();
		}
		final Set<T> result;

		if (collection.isEmpty()) {
			result = (Set<T>)set;
		} else {
			result = new LinkedHashSet<T>(set);
			result.addAll(collection);
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
						expression = "Sequence{'a', 'b', 'c'} + Sequence{'d', 'e'}",
						result = "Sequence{'a', 'b', 'c', 'd', 'e'}"
					)
				}
			),
			@Example(
				expression = "Sequence{'a', 'b', 'c'} + OrderedSet{'c', 'e'}", result = "Sequence{'a', 'b', 'c', 'c', 'e'}",
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
				expression = "OrderedSet{'a', 'b', 'c'} + OrderedSet{'c', 'b', 'f'}", result = "OrderedSet{'a', 'b', 'c', 'f'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "OrderedSet{'a', 'b', 'c'}.addAll(OrderedSet{'c', 'b', 'f'})",
						result = "OrderedSet{'a', 'b', 'c', 'f'}"
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
				expression = "Sequence{'a', 'b', 'c'} - Sequence{'c', 'b', 'f'}", result = "Sequence{'a'}",
				others = {
					@Other(
						language = Other.ACCELEO_3,
						expression = "Sequence{'a', 'b', 'c'}.removeAll(Sequence{'c', 'b', 'f'})",
						result = "Sequence{'a'}"
					)
				}
			),
			@Example(
				expression = "Sequence{'a', 'b', 'c'} - OrderedSet{'c', 'b', 'f'}", result = "Sequence{'a'}",
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
		if (sequence == null) {
			throw new NullPointerException();
		}
		if (collection.isEmpty()) {
			return sequence;
		} else {
			List<T> result = new ArrayList<T>(sequence);
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
				expression = "OrderedSet{'a', 'b', 'c'} - OrderedSet{'c', 'b', 'f'}", result = "OrderedSet{'a'}",
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
		if (set == null) {
			throw new NullPointerException();
		}
		if (collection.isEmpty()) {
			return set;
		} else {
			Set<T> result = new LinkedHashSet<T>(set);
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
			newList = new ArrayList<T>();
		} else {
			newList = new ArrayList<T>();
			for (T elt : sequence) {
				try {
					Object value = lambda.eval(new Object[] {elt });
					if (Boolean.TRUE.equals(value)) {
						newList.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
			newSet = new LinkedHashSet<T>();
		} else {
			newSet = new LinkedHashSet<T>();
			for (T elt : set) {
				try {
					Object value = lambda.eval(new Object[] {elt });
					if (Boolean.TRUE.equals(value)) {
						newSet.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
			newSet = new LinkedHashSet<T>();
		} else {
			newSet = new LinkedHashSet<T>();
			for (T elt : set) {
				try {
					Object value = lambda.eval(new Object[] {elt });
					if (Boolean.FALSE.equals(value)) {
						newSet.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
			newList = new ArrayList<T>();
		} else {
			newList = new ArrayList<T>();
			for (T elt : sequence) {
				try {
					Object value = lambda.eval(new Object[] {elt });
					if (Boolean.FALSE.equals(value)) {
						newList.add(elt);
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
			result = new LinkedHashSet<Object>();
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
					lambda.logException(Diagnostic.WARNING, e);
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
			result = new ArrayList<Object>();
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
					lambda.logException(Diagnostic.WARNING, e);
				}
				// CHECKSTYLE:ON
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set containing the result of applying \"lambda\" recursivly.",
		params = {
			@Param(name = "collection", value = "The original collection"),
			@Param(name = "lambda", value = "The lambda expression")
		},
		result = "a set containing the result of applying \"lambda\" recursivly",
		examples = {
			@Example(expression = "Sequence{eCls}->closure(e | e.eContainer())", result = "Sequence{subEPkg, ePkg, rootEPkg}")
		}
	)
	// @formatter:on
	public Set<Object> closure(Collection<?> collection, LambdaValue lambda) {
		final Set<Object> result;

		if (lambda == null) {
			result = Collections.emptySet();
		} else {
			result = new LinkedHashSet<Object>();
			Set<Object> currentSet = new LinkedHashSet<Object>(collection);
			Set<Object> added;
			do {
				added = new LinkedHashSet<Object>();
				for (Object current : currentSet) {
					try {
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
						// CHECKSTYLE:OFF
					} catch (Exception e) {
						// CHECKSTYLE:ON
						lambda.logException(Diagnostic.WARNING, e);
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
			result = new ArrayList<T>(sequence);

			final Map<T, Object> values = new HashMap<T, Object>();
			for (T object : result) {
				try {
					values.put(object, lambda.eval(new Object[] {object }));
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					lambda.logException(Diagnostic.WARNING, e);
				}
				// CHECKSTYLE:ON
			}

			Collections.sort(result, new LambdaComparator<T>(values));
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
			List<T> sorted = new ArrayList<T>(set);

			final Map<T, Object> values = new HashMap<T, Object>();
			for (T object : sorted) {
				try {
					values.put(object, lambda.eval(new Object[] {object }));
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					lambda.logException(Diagnostic.WARNING, e);
				}
				// CHECKSTYLE:ON
			}

			Collections.sort(sorted, new LambdaComparator<T>(values));
			result = new LinkedHashSet<T>(sorted);
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
			Set<T> result = new LinkedHashSet<T>(set);
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
			Set<T> result = new LinkedHashSet<T>(set);
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
		List<T> result = new ArrayList<T>(sequence);
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
		List<T> result = new ArrayList<T>(sequence);
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
			return new ArrayList<T>(collection);
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
			return new LinkedHashSet<T>(collection);
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
		final T res;

		final Iterator<T> it = collection.iterator();
		if (it.hasNext()) {
			res = it.next();
		} else {
			res = null;
		}

		return res;
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
		final ArrayList<T> res = new ArrayList<T>(sequence);

		Collections.reverse(res);

		return res;
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
		final ArrayList<T> res = new ArrayList<T>(set);

		Collections.reverse(res);

		return new LinkedHashSet<T>(res);
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
		value = "Returns the element at the specified position in the set.",
		params = {
			@Param(name = "set", value = "The input set"),
			@Param(name = "position", value = "The position of the element to return ([1..size])")
		},
		result = "The element at the specified position in the set",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->at(1)", result = "'a'"),
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->at(2)", result = "'b'")
		}
	)
	// @formatter:on
	public <T> T at(Set<T> set, Integer position) {
		return new ArrayList<T>(set).get(position - 1);
	}

	// @formatter:off
	@Documentation(
		value = "Keeps only instances of the given primitive type (String, Integer, Real, Boolean) from the given set.",
		params = {
			@Param(name = "set", value = "The input set to filter"),
			@Param(name = "cls", value = "The type used to filters element in the set")
		},
		result = "A new set containing instances of the given primitive type or null if the given set is null",
		examples = {
			@Example(expression = "OrderedSet{'a', 1, 3.14}->filter(String)", result = "OrederedSet{'a'}")
		}
	)
	// @formatter:on
	public <T> Set<T> filter(Set<T> set, Class<?> cls) {
		final Set<T> result;

		if (set == null) {
			result = null;
		} else if (cls != null) {
			result = new LinkedHashSet<T>();
			for (T value : set) {
				if (cls.isInstance(value)) {
					result.add(value);
				}
			}
		} else {
			result = new LinkedHashSet<T>();
		}

		return result;
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
	// @formatter:on
	public <T> Set<T> filter(Set<T> set, EClassifier eClassifier) {
		final Set<T> result;

		if (set == null) {
			result = null;
		} else if (eClassifier != null) {
			final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
			eClassifiers.add(eClassifier);
			result = filter(set, eClassifiers);
		} else {
			result = new LinkedHashSet<T>();
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
	public <T> Set<T> filter(Set<T> set, Set<EClassifier> eClassifiers) {
		final Set<T> result;

		if (set == null) {
			result = null;
		} else if (eClassifiers == null || eClassifiers.isEmpty()) {
			result = new LinkedHashSet<T>();
		} else {
			result = new LinkedHashSet<T>();
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
		value = "Keeps only instances of the given primitive type (String, Integer, Real, Boolean) from the given sequence.",
		params = {
			@Param(name = "sequence", value = "The input sequence to filter"),
			@Param(name = "cls", value = "The type used to filters element in the sequence")
		},
		result = "A new sequence containing instances of the given primitive type or null if the given sequence is null",
		examples = {
			@Example(expression = "Sequence{'a', 1, 3.14}->filter(String)", result = "Sequence{'a'}")
		}
	)
	// @formatter:on
	public <T> List<T> filter(List<T> sequence, Class<?> cls) {
		final List<T> result;

		if (sequence == null) {
			result = null;
		} else if (cls != null) {
			result = new ArrayList<T>();
			for (T value : sequence) {
				if (cls.isInstance(value)) {
					result.add(value);
				}
			}
		} else {
			result = new ArrayList<T>();
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
	public <T> List<T> filter(List<T> sequence, EClassifier eClassifier) {
		final List<T> result;

		if (sequence == null) {
			result = null;
		} else if (eClassifier != null) {
			final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
			eClassifiers.add(eClassifier);
			result = filter(sequence, eClassifiers);
		} else {
			result = new ArrayList<T>();
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
	public <T> List<T> filter(List<T> sequence, Set<EClassifier> eClassifiers) {
		final List<T> result;

		if (sequence == null) {
			result = null;
		} else if (eClassifiers == null || eClassifiers.isEmpty()) {
			result = new ArrayList<T>();
		} else {
			result = new ArrayList<T>();
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
			result = new ArrayList<Object>();

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
		final List<Object> result = new ArrayList<Object>();

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
		T res = null;

		final Iterator<T> it = set.iterator();
		while (it.hasNext()) {
			res = it.next();
		}

		return res;
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
				try {
					Object value = lambda.eval(new Object[] {input });
					if (Boolean.TRUE.equals(value)) {
						result = input;
						break;
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
				try {
					Object value = lambda.eval(new Object[] {input });
					if (Boolean.TRUE.equals(value)) {
						result = Boolean.TRUE;
						break;
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
				try {
					Object value = lambda.eval(new Object[] {input });
					if (!(value instanceof Boolean) || !Boolean.TRUE.equals(value)) {
						result = Boolean.FALSE;
						break;
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
					result = Boolean.FALSE;
					break;
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
		final Set<Object> evaluated = new HashSet<Object>();

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
					lambda.logException(Diagnostic.WARNING, e);
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
				try {
					Object value = lambda.eval(new Object[] {input });
					if (Boolean.TRUE.equals(value)) {
						result = !result;
						if (!result) {
							break;
						}
					}
					// CHECKSTYLE:OFF
				} catch (Exception e) {
					// CHECKSTYLE:ON
					lambda.logException(Diagnostic.WARNING, e);
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
				throw new IllegalArgumentException(String.format(AbstractCollectionService.ONLY_NUMERIC_ERROR,
						"sum"));
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
		value = "Min element of the given collection if possible.",
		params = {
			@Param(name = "collection", value = "The collection")
		},
		result = "The min element of the given collection if possible",
		exceptions = {
			@Throw(type = IllegalArgumentException.class, value = "If an element of the collection cannot be processed")
		},
		examples = {
			@Example(expression = "Sequence{1, 2, 3, 4}->min()", result = "1"),
			@Example(expression = "Sequence{1, 2, 3.14, 4}->min()", result = "1.0")
		}
	)
	// @formatter:on
	public Number min(Collection<?> collection) {
		Number result = Long.valueOf(Long.MAX_VALUE);

		for (Object input : collection) {
			if (!(input instanceof Number)) {
				throw new IllegalArgumentException(String.format(AbstractCollectionService.ONLY_NUMERIC_ERROR,
						"min"));
			}

			if (result instanceof Long && (input instanceof Long || input instanceof Integer)) {
				result = Long.min(result.longValue(), ((Number)input).longValue());
			} else {
				// widen anything that is not a long or int to a double
				result = Double.min(result.doubleValue(), ((Number)input).doubleValue());
			}
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Max element of the given collection if possible.",
		params = {
			@Param(name = "collection", value = "The collection")
		},
		result = "The max element of the given collection if possible",
		exceptions = {
			@Throw(type = IllegalArgumentException.class, value = "If an element of the collection cannot be processed")
		},
		examples = {
			@Example(expression = "Sequence{1, 2, 3, 4}->max()", result = "4"),
			@Example(expression = "Sequence{1, 2, 3.14, 4}->max()", result = "4")
		}
	)
	// @formatter:on
	public Number max(Collection<?> collection) {
		Number result = Long.valueOf(Long.MIN_VALUE);

		for (Object input : collection) {
			if (!(input instanceof Number)) {
				throw new IllegalArgumentException(String.format(AbstractCollectionService.ONLY_NUMERIC_ERROR,
						"max"));
			}

			if (result instanceof Long && (input instanceof Long || input instanceof Integer)) {
				result = Long.max(result.longValue(), ((Number)input).longValue());
			} else {
				// widen anything that is not a long or int to a double
				result = Double.max(result.doubleValue(), ((Number)input).doubleValue());
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
		value = "Returns the last index of the given object in the given sequence ([1..size]).",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "The last index of the given object",
		examples = {
			@Example(expression = "Sequence{1, 2, 3, 4, 3}->lastIndexOf(3)", result = "5")
		}
	)
	// @formatter:on
	public Integer lastIndexOf(List<?> sequence, Object object) {
		return Integer.valueOf(sequence.lastIndexOf(object) + 1);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the last index of the given object in the given set ([1..size]).",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "object", value = "The object")
		},
		result = "The last index of the given object",
		examples = {
			@Example(expression = "OrderedSet{1, 2, 3, 4}->lastIndexOf(3)", result = "3")
		}
	)
	// @formatter:on
	public Integer lastIndexOf(Set<?> set, Object object) {
		return indexOf(set, object);
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
		value = "Inserts the given object in a copy of the given sequence at the last position.",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given sequence including the object at the last position",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->append('f')", result = "Sequence{'a', 'b', 'c', 'f'}")
		}
	)
	// @formatter:on
	public <T> List<T> append(List<T> sequence, T object) {
		final List<T> result = new ArrayList<T>(sequence);

		result.add(object);

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Inserts the given object in a copy of the given set at the last position. "
				+ "If the set already contained the given object, it is moved to the last position.",
		params = {
			@Param(name = "set", value = "The sequence"),
			@Param(name = "object", value = "The object")
		},
		result = "A copy of the given set including the object at the last position",
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->append('f')", result = "OrderedSet{'a', 'b', 'c', 'f'}"),
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->append('b')", result = "OrderedSet{'a', 'c', 'b'}")
		}
	)
	// @formatter:on
	public <T> Set<T> append(Set<T> set, T object) {
		final Set<T> result = new LinkedHashSet<T>(set);

		result.remove(object);
		result.add(object);

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
		final Set<T> result = new LinkedHashSet<T>(set);

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
		final List<T> result = new ArrayList<T>(sequence);
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
																   "endIndex > sequence.size() || startIndex > endIndex).")
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
		return new ArrayList<T>(sequence.subList(startIndex - 1, endIndex));
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence of elements after the given index in the given sequence",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "index", value = "The low start point (exclusive) of the subsequence")
		},
		result = "A subset of the given sequence",
		exceptions = {
			@Throw(type = IndexOutOfBoundsException.class, value = "If we have an illegal start point value (index < 1 || " +
																   "index > sequence.size()).")
			},
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->drop(2)", result = "Sequence{'c'}")
		}
	)
	// @formatter:on
	public <T> List<T> drop(List<T> sequence, Integer index) {
		return subSequence(sequence, index + 1, sequence.size());
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set of elements after the given index in the given set",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "index", value = "The low start point (exclusive) of the subsequence")
		},
		result = "A subset of the given set",
		exceptions = {
			@Throw(type = IndexOutOfBoundsException.class, value = "If we have an illegal start point value (index < 1 || " +
																   "index > set.size()).")
			},
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->drop(2)", result = "OrderedSet{'c'}")
		}
	)
	// @formatter:on
	public <T> Set<T> drop(Set<T> set, Integer index) {
		return subOrderedSet(set, index + 1, set.size());
	}

	// @formatter:off
	@Documentation(
		value = "Returns a sequence of elements before the given index in the given sequence",
		params = {
			@Param(name = "sequence", value = "The sequence"),
			@Param(name = "index", value = "The high end point (exclusive) of the subsequence")
		},
		result = "A subset of the given sequence",
		exceptions = {
			@Throw(type = IndexOutOfBoundsException.class, value = "If we have an illegal end point value (index < 1 || " +
																   "index > sequence.size()).")
			},
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->dropRight(2)", result = "Sequence{'a'}")
		}
	)
	// @formatter:on
	public <T> List<T> dropRight(List<T> sequence, Integer index) {
		return subSequence(sequence, 1, index - 1);
	}

	// @formatter:off
	@Documentation(
		value = "Returns a set of elements before the given index in the given set",
		params = {
			@Param(name = "set", value = "The set"),
			@Param(name = "index", value = "The high end point (exclusive) of the subsequence")
		},
		result = "A subset of the given set",
		exceptions = {
			@Throw(type = IndexOutOfBoundsException.class, value = "If we have an illegal end point value (index < 1 || " +
																   "index > set.size()).")
			},
		examples = {
			@Example(expression = "OrderedSet{'a', 'b', 'c'}->dropRight(2)", result = "OrderedSet{'a'}")
		}
	)
	// @formatter:on
	public <T> Set<T> dropRight(Set<T> set, Integer index) {
		return subOrderedSet(set, 1, index - 1);
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" if the sequence starts with other, \"false\" otherwise",
		params = {
			@Param(name = "sequence", value = "The Sequence or OrderedSet"),
			@Param(name = "other", value = "The other Sequence or OrderedSet")
		},
		result = "\\\"true\\\" if the sequence starts with other, \\\"false\\\" otherwise",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->startsWith(Sequence{'a', 'b'})", result = "true")
		}
	)
	// @formatter:on
	public <T> Boolean startsWith(Collection<T> collection, Collection<T> other) {
		final boolean res;

		if (other.size() > collection.size()) {
			res = false;
		} else {
			final Iterator<T> collectionIt = collection.iterator();
			final Iterator<T> otherIt = other.iterator();
			res = equalsIterator(collectionIt, otherIt);
		}

		return res;
	}

	// @formatter:off
	@Documentation(
		value = "Returns \"true\" if the sequence ends with other, \"false\" otherwise",
		params = {
			@Param(name = "sequence", value = "The Sequence or OrderedSet"),
			@Param(name = "other", value = "The other Sequence or OrderedSet")
		},
		result = "\\\"true\\\" if the sequence ends with other, \\\"false\\\" otherwise",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->endsWith(Sequence{'b', 'c'})", result = "true")
		}
	)
	// @formatter:on
	public <T> Boolean endsWith(Collection<T> collection, Collection<T> other) {
		final boolean res;

		if (other.size() > collection.size()) {
			res = false;
		} else {
			final Iterator<T> collectionIt = collection.iterator();
			final Iterator<T> otherIt = other.iterator();
			for (int i = 0; i < collection.size() - other.size(); i++) {
				collectionIt.next();
			}
			res = equalsIterator(collectionIt, otherIt);
		}

		return res;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the index of the other collection in the given collection",
		params = {
			@Param(name = "sequence", value = "The Sequence or OrderedSet"),
			@Param(name = "other", value = "The other Sequence or OrderedSet")
		},
		result = "the index of of the other collection in the given collection",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c'}->indexOfSlice(Sequence{'b', 'c'})", result = "2")
		}
	)
	// @formatter:on
	public <T> Integer indexOfSlice(Collection<T> collection, Collection<T> other) {
		Integer res = -1;

		boolean found = false;
		if (other.size() <= collection.size()) {
			for (int i = 0; i < collection.size() - other.size() + 1; i++) {
				final Iterator<T> collectionIt = collection.iterator();
				res = i;
				for (int j = 0; j < i; j++) {
					collectionIt.next();
				}
				if (equalsIterator(collectionIt, other.iterator())) {
					found = true;
					break;
				}
			}
		}

		if (!found) {
			res = -1;
		}

		return res + 1;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the last index of the other collection in the given collection",
		params = {
			@Param(name = "sequence", value = "The Sequence or OrderedSet"),
			@Param(name = "other", value = "The other Sequence or OrderedSet")
		},
		result = "the last index of of the other collection in the given collection",
		examples = {
			@Example(expression = "Sequence{'a', 'b', 'c', 'a', 'b', 'c'}->lastIndexOfSlice(Sequence{'b', 'c'})", result = "5")
		}
	)
	// @formatter:on
	public <T> Integer lastIndexOfSlice(Collection<T> collection, Collection<T> other) {
		Integer res = -1;

		boolean found = false;
		if (other.size() <= collection.size()) {
			for (int i = collection.size() - other.size(); i >= 0; i--) {
				final Iterator<T> collectionIt = collection.iterator();
				res = i;
				for (int j = 0; j < i; j++) {
					collectionIt.next();
				}
				if (equalsIterator(collectionIt, other.iterator())) {
					found = true;
					break;
				}
			}
		}

		if (!found) {
			res = -1;
		}

		return res + 1;
	}

	/**
	 * Tells if both given {@link Iterator} contains the same elements.
	 * 
	 * @param <T>
	 *            collections elements type
	 * @param collectionIt
	 *            the first {@link Iterator}
	 * @param otherIt
	 *            the second {@link Iterator}
	 * @return <code>true</code> if both given {@link Iterator} contains the same elements, <code>false</code>
	 *         otherwise
	 */
	private <T> boolean equalsIterator(final Iterator<T> collectionIt, final Iterator<T> otherIt) {
		boolean res = true;

		while (otherIt.hasNext()) {
			final T element = collectionIt.next();
			final T elementOther = otherIt.next();
			if ((element != null && !element.equals(elementOther)) || (element == null
					&& elementOther != null)) {
				res = false;
				break;
			}
		}

		return res;
	}

}
