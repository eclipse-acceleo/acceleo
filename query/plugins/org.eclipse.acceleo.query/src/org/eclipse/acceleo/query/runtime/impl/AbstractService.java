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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;

/**
 * Abstract implementation of {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractService implements IService {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#isEqualParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment,
	 *      org.eclipse.acceleo.query.runtime.IService)
	 */
	@Override
	public boolean isEqualParameterTypes(IReadOnlyQueryEnvironment queryEnvironment, IService service) {
		final List<IType> paramTypes1 = getParameterTypes(queryEnvironment);
		final List<IType> paramTypes2 = service.getParameterTypes(queryEnvironment);
		boolean result = paramTypes1.size() == paramTypes2.size();

		final Iterator<IType> it1 = paramTypes1.iterator();
		final Iterator<IType> it2 = paramTypes2.iterator();
		while (result && it1.hasNext()) {
			IType paramType1 = it1.next();
			IType paramType2 = it2.next();
			if (!paramType2.equals(paramType1)) {
				result = false;
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#isLowerOrEqualParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment,
	 *      org.eclipse.acceleo.query.runtime.IService)
	 */
	public boolean isLowerOrEqualParameterTypes(IReadOnlyQueryEnvironment queryEnvironment, IService service) {
		final List<IType> paramTypes1 = getParameterTypes(queryEnvironment);
		final List<IType> paramTypes2 = service.getParameterTypes(queryEnvironment);
		boolean result = paramTypes1.size() == paramTypes2.size();

		final Iterator<IType> it1 = paramTypes1.iterator();
		final Iterator<IType> it2 = paramTypes2.iterator();
		while (result && it1.hasNext()) {
			IType paramType1 = it1.next();
			IType paramType2 = it2.next();
			if (!paramType2.isAssignableFrom(paramType1)) {
				result = false;
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#matches(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment,
	 *      org.eclipse.acceleo.query.validation.type.IType[])
	 */
	public boolean matches(IReadOnlyQueryEnvironment queryEnvironment, IType[] argumentTypes) {
		assert getNumberOfParameters() == argumentTypes.length;

		boolean result = true;

		final List<IType> parameterTypes = getParameterTypes(queryEnvironment);
		for (int i = 0; i < parameterTypes.size() && result; i++) {
			if (argumentTypes[i].getType() != null
					&& !parameterTypes.get(i).isAssignableFrom(argumentTypes[i])) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.Map)
	 */
	@Override
	public Set<IType> validateAllType(ValidationServices services,
			IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			// FIXME check for "empty" and put a message instead.
			result.addAll(entry.getValue());
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#invoke(java.lang.Object[])
	 */
	@Override
	public Object invoke(Object... arguments) throws AcceleoQueryEvaluationException {
		final Object result;

		try {
			result = internalInvoke(arguments);
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new AcceleoQueryEvaluationException(getShortSignature() + " with arguments "
					+ Arrays.deepToString(arguments) + " failed.", e);
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
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLongSignature();
	}

}
