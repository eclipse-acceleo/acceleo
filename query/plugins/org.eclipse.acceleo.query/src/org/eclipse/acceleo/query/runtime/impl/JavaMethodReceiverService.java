/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Implementation of an {@link org.eclipse.acceleo.query.runtime.IService IService} for {@link Method} with
 * receiver as first parameter.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class JavaMethodReceiverService extends JavaMethodService {

	/**
	 * Constructor.
	 * 
	 * @param method
	 *            the method that realizes the service
	 */
	public JavaMethodReceiverService(Method method) {
		super(method, null);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
	 */
	@Override
	public String getShortSignature() {
		final Class<?>[] parameters = getParameterTypes();
		return serviceShortSignature(parameters);
	}

	/**
	 * Gets the parameter types including the receiver type.
	 * 
	 * @return the parameter types including the receiver type
	 */
	private Class<?>[] getParameterTypes() {
		final Class<?>[] parameters = new Class<?>[getOrigin().getParameterTypes().length + 1];
		int i = 0;
		parameters[i++] = getOrigin().getDeclaringClass();
		for (Class<?> cls : getOrigin().getParameterTypes()) {
			parameters[i++] = cls;
		}
		return parameters;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
	 */
	@Override
	public String getLongSignature() {
		return super.getLongSignature() + " (receiver as first parameter)";
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
	 */
	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IType> result = new ArrayList<IType>();

		for (Class<?> cls : getParameterTypes()) {
			result.add(getClassType(queryEnvironment, cls));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getNumberOfParameters()
	 */
	@Override
	public int getNumberOfParameters() {
		return super.getNumberOfParameters() + 1;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
	 */
	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Object receiver = arguments[0];
		final Object[] newArguments = new Object[arguments.length - 1];
		for (int i = 0; i < newArguments.length; i++) {
			newArguments[i] = arguments[i + 1];
		}

		return getOrigin().invoke(receiver, newArguments);
	}

}
