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
package org.eclipse.acceleo.query.runtime.lookup.basic;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Lookup engine are used to retrieve services from a name and a set of arguments.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class BasicLookupEngine implements ILookupEngine {

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	protected final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * The {@link ServiceStore}.
	 */
	private final ServiceStore services;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @since 4.0.0
	 */
	public BasicLookupEngine(IReadOnlyQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
		this.services = new ServiceStore(queryEnvironment);
	}

	/**
	 * Gets the {@link ServiceStore}.
	 * 
	 * @return the {@link ServiceStore}
	 */
	protected ServiceStore getServices() {
		return services;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ILookupEngine#getRegisteredServices()
	 */
	public Set<IService<?>> getRegisteredServices() {
		Set<IService<?>> result = services.getServices();

		return result;
	}

	@Override
	public IService<?> lookup(String name, IType[] argumentTypes) {
		List<IService<?>> multiMethod = services.getMultiService(name, argumentTypes.length);
		if (multiMethod == null) {
			return null;
		} else {
			IService<?> result = null;
			for (IService<?> service : multiMethod) {
				if (service.matches(queryEnvironment, argumentTypes)) {
					if (result == null || service.getPriority() > result.getPriority() || (service
							.getPriority() == result.getPriority() && service.isLowerOrEqualParameterTypes(
									queryEnvironment, result))) {
						result = service;
					}
				}
			}
			return result;
		}
	}

	/**
	 * Registers the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService} to register
	 * @return the {@link ServiceRegistrationResult}
	 */
	public ServiceRegistrationResult registerService(IService<?> service) {
		final ServiceRegistrationResult result = services.add(service);

		return result;
	}

	/**
	 * Removes the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService} to remove.
	 * @return the removed {@link IService} if any, <code>null</code> otherwise
	 */
	public IService<?> removeService(IService<?> service) {
		final IService<?> result = services.remove(service);

		return result;
	}

	@Override
	public Set<IService<?>> getServices(Set<IType> receiverTypes) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		final Set<IService<?>> storedServices = services.getServices();
		for (IType type : receiverTypes) {
			if (type != null) {
				for (IService<?> service : storedServices) {
					for (IType parameterType : service.getParameterTypes(queryEnvironment).get(0)) {
						if (parameterType.isAssignableFrom(type)) {
							result.add(service);
							break;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ILookupEngine#isRegisteredService(org.eclipse.acceleo.query.runtime.IService)
	 */
	@Override
	public boolean isRegisteredService(IService<?> service) {
		return services.isRegistered(service);
	}

}
