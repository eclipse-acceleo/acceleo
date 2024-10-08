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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
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
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 */
	public JavaMethodReceiverService(Method method, boolean forWorkspace) {
		super(method, null, forWorkspace);
	}

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

	@Override
	public String getLongSignature() {
		return super.getLongSignature() + " (receiver as first parameter)";
	}

	@Override
	public List<Set<IType>> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<Set<IType>> result = new ArrayList<>();

		for (Class<?> cls : getParameterTypes()) {
			result.add(Collections.singleton(getClassType(queryEnvironment, cls)));
		}

		return result;
	}

	@Override
	public int getNumberOfParameters() {
		return super.getNumberOfParameters() + 1;
	}

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
