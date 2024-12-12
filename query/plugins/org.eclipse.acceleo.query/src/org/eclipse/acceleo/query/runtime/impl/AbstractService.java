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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;

/**
 * Abstract implementation of {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractService<O> implements IService<O> {

	/**
	 * The {@link O origin} of this service.
	 */
	private final O origin;

	/**
	 * Known {@link IReadOnlyQueryEnvironment} to invalidate cached {@link #returnTypes} and
	 * {@link #parameterTypes}.
	 */
	private IReadOnlyQueryEnvironment knwonEnvironment;

	/**
	 * Return {@link IType} cache.
	 */
	private Set<IType> returnTypes;

	/**
	 * Parameters {@link IType} cache.
	 */
	private List<Set<IType>> parameterTypes;

	/**
	 * Constructor with an {@link Object origin}.
	 * 
	 * @param serviceOrigin
	 *            the (maybe {@code null}) {@link Object origin} of this service.
	 */
	protected AbstractService(O serviceOrigin) {
		this.origin = serviceOrigin;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getOrigin()
	 */
	@Override
	public O getOrigin() {
		return this.origin;
	}

	@Override
	public List<Set<IType>> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		if (knwonEnvironment != queryEnvironment || returnTypes == null) {
			knwonEnvironment = queryEnvironment;
			parameterTypes = computeParameterTypes(queryEnvironment);
		}

		return parameterTypes;
	}

	/**
	 * Computes the {@link #getParameterTypes(IReadOnlyQueryEnvironment)}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return the {@link #getParameterTypes(IReadOnlyQueryEnvironment)}
	 */
	protected abstract List<Set<IType>> computeParameterTypes(IReadOnlyQueryEnvironment queryEnvironment);

	@Override
	public Set<IType> getType(IReadOnlyQueryEnvironment queryEnvironment) {
		if (knwonEnvironment != queryEnvironment || returnTypes == null) {
			knwonEnvironment = queryEnvironment;
			returnTypes = computeType(queryEnvironment);
		}

		return new LinkedHashSet<>(returnTypes);
	}

	/**
	 * Computes the {@link #getType(IReadOnlyQueryEnvironment)}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return the {@link #getType(IReadOnlyQueryEnvironment)}
	 */
	protected abstract Set<IType> computeType(IReadOnlyQueryEnvironment queryEnvironment);

	@Override
	public boolean isEqualParameterTypes(IReadOnlyQueryEnvironment queryEnvironment, IService<?> service) {
		final List<Set<IType>> paramTypes1 = getParameterTypes(queryEnvironment);
		final List<Set<IType>> paramTypes2 = service.getParameterTypes(queryEnvironment);
		boolean result;

		if (paramTypes1.size() == paramTypes2.size()) {
			final Iterator<Set<IType>> it1 = paramTypes1.iterator();
			final Iterator<Set<IType>> it2 = paramTypes2.iterator();
			result = true;
			while (it1.hasNext()) {
				final Set<IType> types1 = it1.next();
				final Set<IType> types2 = it2.next();
				for (IType type1 : types1) {
					boolean isEquals = false;
					for (IType type2 : types2) {
						if (type2.equals(type1)) {
							isEquals = true;
							break;
						}
					}
					if (!isEquals) {
						result = false;
						break;
					}
				}
			}
		} else {
			result = false;
		}

		return result;
	}

	public boolean isLowerOrEqualParameterTypes(IReadOnlyQueryEnvironment queryEnvironment,
			IService<?> service) {
		final List<Set<IType>> paramTypes1 = getParameterTypes(queryEnvironment);
		final List<Set<IType>> paramTypes2 = service.getParameterTypes(queryEnvironment);
		boolean result;

		if (paramTypes1.size() == paramTypes2.size()) {
			final Iterator<Set<IType>> it1 = paramTypes1.iterator();
			final Iterator<Set<IType>> it2 = paramTypes2.iterator();
			result = true;
			while (it1.hasNext()) {
				final Set<IType> types1 = it1.next();
				final Set<IType> types2 = it2.next();
				for (IType paramType1 : types1) {
					boolean isAssignable = false;
					for (IType paramType2 : types2) {
						if (paramType2.isAssignableFrom(paramType1)) {
							isAssignable = true;
							break;
						}
					}
					if (!isAssignable) {
						result = false;
						break;
					}
				}
			}
		} else {
			result = false;
		}

		return result;
	}

	public boolean matches(IReadOnlyQueryEnvironment queryEnvironment, IType[] argumentTypes) {
		assert getNumberOfParameters() == argumentTypes.length;

		boolean result = true;

		final List<Set<IType>> parameterTypes = getParameterTypes(queryEnvironment);
		for (int i = 0; i < parameterTypes.size() && result; i++) {
			if (argumentTypes[i].getType() != null) {
				boolean oneAssignable = false;
				for (IType parameterType : parameterTypes.get(i)) {
					if (parameterType.isAssignableFrom(argumentTypes[i])) {
						oneAssignable = true;
						break;
					}
				}
				if (!oneAssignable) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public Set<IType> validateAllType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
			Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			// FIXME check for "empty" and put a message instead.
			result.addAll(entry.getValue());
		}

		return result;
	}

	@Override
	public Object invoke(Object... arguments) throws AcceleoQueryEvaluationException {
		final Object result;

		try {
			result = internalInvoke(arguments);
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			final Throwable cause;
			final String message;
			if (e.getCause() != null) {
				cause = e.getCause();
				if (e instanceof InvocationTargetException) {
					final StringWriter sw = new StringWriter();
					final PrintWriter pw = new PrintWriter(sw);
					cause.printStackTrace(pw);
					message = sw.toString().replaceAll("\n", "\n\t");
				} else {
					message = cause.getMessage();
				}
			} else {
				cause = e;
				message = cause.getMessage();
			}
			throw new AcceleoQueryEvaluationException(getShortSignature() + " with arguments " + Arrays
					.deepToString(arguments) + " failed:\n\t" + message, cause);
		}

		return result;
	}

	/**
	 * Does the internal invocation.
	 * 
	 * @param arguments
	 *            arguments
	 * @return the result of the invocation
	 * @throws Exception
	 *             when the invocation goes wrong
	 */
	protected abstract Object internalInvoke(Object[] arguments) throws Exception;

	/**
	 * Build up the specified service's signature for reporting.
	 * 
	 * @param argumentTypes
	 *            the service's call argument types
	 * @return the specified service's signature
	 */
	protected String serviceShortSignature(Object[] argumentTypes) {
		StringBuilder builder = new StringBuilder();
		builder.append(getName()).append('(');
		boolean first = true;
		for (Object argType : argumentTypes) {
			if (!first) {
				builder.append(',');
			} else {
				first = false;
			}
			builder.append(getTypeString(argType));
		}
		return builder.append(')').toString();
	}

	/**
	 * Gets the {@link String} representation of the given type {@link Object}.
	 * 
	 * @param argType
	 *            the type {@link Object}
	 * @return the {@link String} representation of the given type {@link Object}
	 */
	private String getTypeString(Object argType) {
		final String res;

		if (argType instanceof Set<?>) {
			final Set<?> types = (Set<?>)argType;
			if (types.size() > 1) {
				final StringJoiner joiner = new StringJoiner(" | ");
				for (Object type : types) {
					joiner.add(getTypeString(type));
				}
				res = joiner.toString();
			} else if (types.size() == 1) {
				res = getTypeString(types.iterator().next());
			} else {
				res = "???";
			}
		} else if (argType instanceof Class<?>) {
			res = ((Class<?>)argType).getCanonicalName();
		} else if (argType instanceof EClass) {
			res = "EClass=" + ((EClass)argType).getName();
		} else if (argType == null) {
			res = "Object=null";
		} else {
			// should not happen
			res = "Object=" + argType.toString();
		}

		return res;
	}

	@Override
	public String toString() {
		return getLongSignature();
	}

	@Override
	public List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes) {
		return Collections.emptyList();
	}

	@Override
	public Visibility getVisibility() {
		return Visibility.PUBLIC;
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		return getType(queryEnvironment);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof IService<?> && (getOrigin() == null && ((IService<?>)obj).getOrigin() == null
				|| getOrigin().equals(((IService<?>)obj).getOrigin()));
	}

	@Override
	public int hashCode() {
		final int res;

		if (getOrigin() != null) {
			res = getOrigin().hashCode();
		} else {
			res = super.hashCode();
		}

		return res;
	}

}
