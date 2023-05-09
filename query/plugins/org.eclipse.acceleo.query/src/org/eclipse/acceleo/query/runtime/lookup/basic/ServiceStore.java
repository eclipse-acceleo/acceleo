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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;

/**
 * A store for {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServiceStore {

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Maps of multiservices : {@link IService#getNumberOfParameters() number of parameters} ->
	 * {@link IService#getName() name} -> {@link IService} ordered by descending {@link IService#getPriority()
	 * priority}.
	 */
	private final Map<Integer, Map<String, List<IService<?>>>> services = new HashMap<Integer, Map<String, List<IService<?>>>>();

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 */
	public ServiceStore(IReadOnlyQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	/**
	 * Gets the {@link List} of {@link IService} for the given {@link IService#getName() name} and number of
	 * {@link IService#getNumberOfParameters() parameters}.
	 * 
	 * @param methodName
	 *            the {@link IService#getName() name}
	 * @param argc
	 *            the number of {@link IService#getNumberOfParameters() parameters}
	 * @return the {@link List} of {@link IService} for the given {@link IService#getName() name} and number
	 *         of {@link IService#getNumberOfParameters() parameters}
	 */

	public List<IService<?>> getMultiService(String methodName, int argc) {
		Map<String, List<IService<?>>> argcServices = services.get(argc);
		if (argcServices == null) {
			return null;
		} else {
			return argcServices.get(methodName);
		}
	}

	/**
	 * Gets or creates the {@link List} of {@link IService} to store the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService} to store
	 * @return the {@link List} of {@link IService} to store the given {@link IService}
	 */
	private List<IService<?>> getOrCreateMultiService(IService<?> service) {
		final int argc = service.getNumberOfParameters();
		final String serviceName = service.getName();
		Map<String, List<IService<?>>> argcServices = services.get(argc);
		if (argcServices == null) {
			argcServices = new HashMap<String, List<IService<?>>>();
			services.put(argc, argcServices);
		}
		List<IService<?>> result = argcServices.get(serviceName);
		if (result == null) {
			result = new ArrayList<IService<?>>();
			argcServices.put(serviceName, result);
		}
		return result;
	}

	/**
	 * Gets the number of stored {@link IService}.
	 * 
	 * @return the number of stored {@link IService}
	 */
	public int size() {
		int result = 0;

		for (Map<String, List<IService<?>>> byArgC : services.values()) {
			for (List<IService<?>> byName : byArgC.values()) {
				result += byName.size();
			}
		}

		return result;
	}

	/**
	 * Removes the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService} to remove
	 * @return the removed {@link IService} if any, <code>null</code> otherwise
	 */
	public IService<?> remove(IService<?> service) {
		final IService<?> result;

		if (service == null) {
			return null;
		}

		final int argc = service.getNumberOfParameters();
		final Map<String, List<IService<?>>> argcServices = services.get(argc);
		if (argcServices != null) {
			final String serviceName = service.getName();
			final List<IService<?>> servicesList = argcServices.get(serviceName);
			if (servicesList != null && servicesList.remove(service)) {
				result = service;
				if (servicesList.isEmpty() && argcServices.remove(serviceName) != null && argcServices
						.isEmpty()) {
					services.remove(argc);
				}
			} else {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Adds the given {@link IService} to the store.
	 * 
	 * @param service
	 *            the {@link IService} to add
	 * @return the {@link ServiceRegistrationResult}
	 */
	public ServiceRegistrationResult add(IService<?> service) {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		final List<IService<?>> multiService = getOrCreateMultiService(service);
		for (IService<?> existingService : multiService) {
			if (service.getPriority() > existingService.getPriority()) {
				if (service.isLowerOrEqualParameterTypes(queryEnvironment, existingService) || existingService
						.isLowerOrEqualParameterTypes(queryEnvironment, service)) {
					result.addMasked(service, existingService);
				}
			} else if (service.getPriority() == existingService.getPriority()) {
				if (service.isEqualParameterTypes(queryEnvironment, existingService)) {
					result.addDuplicated(service, existingService);
				} else if (service.isLowerOrEqualParameterTypes(queryEnvironment, existingService)) {
					result.addMasked(service, existingService);
				} else if (existingService.isLowerOrEqualParameterTypes(queryEnvironment, service)) {
					result.addIsMaskedBy(service, existingService);
				}
			} else {
				if (service.isLowerOrEqualParameterTypes(queryEnvironment, existingService) || existingService
						.isLowerOrEqualParameterTypes(queryEnvironment, service)) {
					result.addIsMaskedBy(service, existingService);
				}
			}
		}

		if (!multiService.contains(service)) {
			result.getRegistered().add(service);
			multiService.add(service);
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link ServiceStore#add(IService) stored} {@link IService}.
	 * 
	 * @return the {@link Set} of {@link ServiceStore#add(IService) stored} {@link IService}
	 */
	public Set<IService<?>> getServices() {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		for (Map<String, List<IService<?>>> byArgC : services.values()) {
			for (List<IService<?>> byName : byArgC.values()) {
				result.addAll(byName);
			}
		}

		return result;
	}

	/**
	 * Tells if the given {@link IService} is registered.
	 * 
	 * @param service
	 *            the {@link IService} to check
	 * @return <code>true</code> if the given {@link IService} is registered, <code>false</code> otherwise
	 */
	public boolean isRegistered(IService<?> service) {
		if (service == null) {
			return false;
		}

		final List<IService<?>> multiService = getMultiService(service.getName(), service
				.getNumberOfParameters());

		return multiService != null && multiService.contains(service);
	}
}
