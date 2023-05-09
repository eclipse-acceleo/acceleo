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
package org.eclipse.acceleo.query.runtime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Result of a service package registration.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ServiceRegistrationResult {

	/**
	 * The {@link List} of registered {@link IService}.
	 */
	private final List<IService<?>> registered = new ArrayList<IService<?>>();

	/**
	 * Mapping from newly registered services and {@link IService} it duplicates.
	 */
	private final Map<IService<?>, List<IService<?>>> duplicated = new LinkedHashMap<IService<?>, List<IService<?>>>();

	/**
	 * Mapping from newly registered services and {@link IService} it masks (at least one call to the masked
	 * {@link IService} can be handled by the new {@link IService}).
	 */
	private final Map<IService<?>, List<IService<?>>> masked = new LinkedHashMap<IService<?>, List<IService<?>>>();

	/**
	 * Mapping from newly registered services and {@link IService} it is masked by (at least one call that can
	 * be handled by the new {@link IService} will be handled by the masked by {@link IService}).
	 */
	private final Map<IService<?>, List<IService<?>>> isMaskedBy = new LinkedHashMap<IService<?>, List<IService<?>>>();

	/**
	 * Gets the {@link List} of registered {@link IService}.
	 * 
	 * @return the {@link List} of registered {@link IService}
	 */
	public List<IService<?>> getRegistered() {
		return registered;
	}

	/**
	 * Gets the mapping from newly registered services and {@link IService} it duplicates.
	 * 
	 * @return the mapping from newly registered services and {@link IService} it duplicates
	 */
	public Map<IService<?>, List<IService<?>>> getDuplicated() {
		return duplicated;
	}

	/**
	 * Gets the mapping from newly registered services and {@link IService} it masks (at least one call to the
	 * masked {@link IService} can be handled by the new {@link IService}).
	 * 
	 * @return the mapping from newly registered services and {@link IService} it masks (at least one call to
	 *         the masked {@link IService} can be handled by the new {@link IService})
	 */
	public Map<IService<?>, List<IService<?>>> getMasked() {
		return masked;
	}

	/**
	 * Gets the mapping from newly registered services and {@link IService} it is masked by (at least one call
	 * that can be handled by the new {@link IService} will be handled by the masked by {@link IService}).
	 * 
	 * @return the mapping from newly registered services and {@link IService} it is masked by (at least one
	 *         call that can be handled by the new {@link IService} will be handled by the masked by
	 *         {@link IService})
	 */
	public Map<IService<?>, List<IService<?>>> getIsMaskedBy() {
		return isMaskedBy;
	}

	/**
	 * Merges the given {@link ServiceRegistrationResult} with this one.
	 * 
	 * @param otherResult
	 *            the {@link ServiceRegistrationResult} to merge
	 */
	public void merge(ServiceRegistrationResult otherResult) {
		registered.addAll(otherResult.registered);
		duplicated.putAll(otherResult.duplicated);
		masked.putAll(otherResult.masked);
		isMaskedBy.putAll(otherResult.isMaskedBy);
	}

	/**
	 * Adds the the given duplicated {@link IService} as a duplicates of the new {@link IService}.
	 * 
	 * @param newService
	 *            the new {@link IService}
	 * @param duplicatedService
	 *            the duplicated {@link IService}
	 * @since 4.1
	 */
	public void addDuplicated(IService<?> newService, IService<?> duplicatedService) {
		List<IService<?>> services = duplicated.get(newService);
		if (services == null) {
			services = new ArrayList<IService<?>>();
			duplicated.put(newService, services);
		}
		services.add(duplicatedService);
	}

	/**
	 * Adds the the given masked {@link IService} as a masks (at least one call to the masked {@link IService}
	 * can be handled by the new {@link IService}) of the new {@link IService}.
	 * 
	 * @param newService
	 *            the new {@link IService}
	 * @param maskedService
	 *            the masked {@link IService}
	 * @since 4.1
	 */
	public void addMasked(IService<?> newService, IService<?> maskedService) {
		List<IService<?>> services = masked.get(newService);
		if (services == null) {
			services = new ArrayList<IService<?>>();
			masked.put(newService, services);
		}
		services.add(maskedService);
	}

	/**
	 * Adds the the given is masked by {@link IService} as a is masked by (at least one call that can be
	 * handled by the new {@link IService} will be handled by the masked by {@link IService}) of the new
	 * {@link IService}.
	 * 
	 * @param newService
	 *            the new {@link IService}
	 * @param isMaskedByService
	 *            the is masked by {@link IService}
	 * @since 4.1
	 */
	public void addIsMaskedBy(IService<?> newService, IService<?> isMaskedByService) {
		List<IService<?>> services = isMaskedBy.get(newService);
		if (services == null) {
			services = new ArrayList<IService<?>>();
			isMaskedBy.put(newService, services);
		}
		services.add(isMaskedByService);
	}

}
