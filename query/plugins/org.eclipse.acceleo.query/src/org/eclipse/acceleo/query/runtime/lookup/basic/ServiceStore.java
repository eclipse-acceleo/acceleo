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
package org.eclipse.acceleo.query.runtime.lookup.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * {@link IService#getName() name} -> {@link IService} ordered by descending
	 * {@link IService#getPriority() priority}.
	 */
	private final Map<Integer, Map<String, List<IService>>> services = new HashMap<Integer, Map<String, List<IService>>>();

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

	public List<IService> getMultiService(String methodName, int argc) {
		Map<String, List<IService>> argcServices = services.get(argc);
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
	private List<IService> getOrCreateMultiService(IService service) {
		final int argc = service.getNumberOfParameters();
		final String serviceName = service.getName();
		Map<String, List<IService>> argcServices = services.get(argc);
		if (argcServices == null) {
			argcServices = new HashMap<String, List<IService>>();
			services.put(argc, argcServices);
		}
		List<IService> result = argcServices.get(serviceName);
		if (result == null) {
			result = new ArrayList<IService>();
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

		for (Map<String, List<IService>> byArgC : services.values()) {
			for (List<IService> byName : byArgC.values()) {
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
	 */
	public void remove(IService service) {
		final int argc = service.getNumberOfParameters();
		final Map<String, List<IService>> argcServices = services.get(argc);
		final String serviceName = service.getName();
		final List<IService> servicesList = argcServices.get(serviceName);
		servicesList.remove(service);
		if (servicesList.isEmpty()) {
			argcServices.remove(serviceName);
			if (argcServices.isEmpty()) {
				services.remove(argc);
			}
		}
	}

	/**
	 * Adds the given {@link IService} to the store.
	 * 
	 * @param service
	 *            the {@link IService} to add
	 * @return the {@link ServiceRegistrationResult}
	 */
	public ServiceRegistrationResult add(IService service) {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		final List<IService> multiService = getOrCreateMultiService(service);
		for (IService existingService : multiService) {
			if (service.getPriority() > existingService.getPriority()) {
				if (service.isLowerOrEqualParameterTypes(queryEnvironment, existingService)
						|| existingService.isLowerOrEqualParameterTypes(queryEnvironment, service)) {
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
				if (service.isLowerOrEqualParameterTypes(queryEnvironment, existingService)
						|| existingService.isLowerOrEqualParameterTypes(queryEnvironment, service)) {
					result.addIsMaskedBy(service, existingService);
				}
			}
		}
		result.getRegistered().add(service);
		multiService.add(service);

		return result;
	}
}
