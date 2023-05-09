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
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.ServiceUtils;

/**
 * {@link IServiceProvider} scanning its own public methods to create {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractServiceProvider implements IServiceProvider {

	/**
	 * Wrong implementation of this {@link Class} message.
	 */
	private static final String WRONG_IMPLEMENTATION = "Wrong implementation of "
			+ AbstractServiceProvider.class.getName();

	/**
	 * {@link List} of {@link IService}.
	 */
	private List<IService<?>> services;

	@Override
	public List<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment) {
		try {
			if (services == null) {
				final Method getServicesMethod = getClass().getMethod("getServices",
						IReadOnlyQueryEnvironment.class);
				services = new ArrayList<IService<?>>();
				final Method[] methods = this.getClass().getMethods();
				for (Method method : methods) {
					if (ServiceUtils.isServiceMethod(this, method) && !getServicesMethod.equals(method)) {
						final IService<Method> service = getService(method);
						if (service != null) {
							services.add(service);
						}
					}
				}
			}
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(WRONG_IMPLEMENTATION, e);
		} catch (SecurityException e) {
			throw new IllegalStateException(WRONG_IMPLEMENTATION, e);
		}

		return services;
	}

	/**
	 * Gets an {@link IService} for the given {@link Method}.
	 * 
	 * @param method
	 *            the {@link Method}
	 * @return the {@link IService} if any, <code>null</code> if no {@link IService} correspond to the given
	 *         {@link Method}
	 */
	protected abstract IService<Method> getService(Method method);

}
