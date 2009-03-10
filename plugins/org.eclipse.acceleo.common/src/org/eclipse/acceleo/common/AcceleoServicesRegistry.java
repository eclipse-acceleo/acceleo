/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.AcceleoServicesEclipseUtil;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This will allow Acceleo to know which java services are to be added to the evaluation context.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public final class AcceleoServicesRegistry {
	/** Singleton instance of the registry. */
	public static final AcceleoServicesRegistry INSTANCE = new AcceleoServicesRegistry();

	/** This will contain the services registered for Acceleo evaluations. */
	private final Set<Object> registeredServices = new LinkedHashSet<Object>();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private AcceleoServicesRegistry() {
		// Hides default constructor
	}

	/**
	 * Adds a service to the registry.
	 * 
	 * @param service
	 *            Service that is to be registered for Acceleo evaluations.
	 * @return <code>true</code> if the set didn't already contain <code>service</code>.
	 */
	public boolean addService(Object service) {
		if (service instanceof Class) {
			try {
				registeredServices.add(((Class<?>)service).newInstance());
			} catch (InstantiationException e) {
				AcceleoCommonPlugin
						.log(
								AcceleoCommonMessages
										.getString(
												"AcceleoServicesRegistry.ServiceInstantiationFailure", ((Class<?>)service).getName(), e //$NON-NLS-1$
														.getMessage()), false);
			} catch (IllegalAccessException e) {
				AcceleoCommonPlugin
						.log(
								AcceleoCommonMessages
										.getString(
												"AcceleoServicesRegistry.ServiceInstantiationFailure", ((Class<?>)service).getName(), e //$NON-NLS-1$
														.getMessage()), false);
			}
		}
		return registeredServices.add(service);
	}

	/**
	 * Returns all registered services classes. <b>Note</b> that workspace services are refreshed each time
	 * this is called if Eclipse is running.
	 * 
	 * @return All registered services classes.
	 */
	public Set<Object> getAllRegisteredServiceInstances() {
		final Set<Object> compound = new LinkedHashSet<Object>();
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			compound.addAll(AcceleoServicesEclipseUtil.getRegisteredServices());
		}
		compound.addAll(registeredServices);
		return compound;
	}

	/**
	 * Returns all registered services methods. <b>Note</b> that workspace services are refreshed each time
	 * this is called if Eclipse is running.
	 * 
	 * @return All applicable registered services methods.
	 */
	public Set<Method> getAllRegisteredServices() {
		final Set<Object> serviceInstances = getAllRegisteredServiceInstances();
		final Set<Method> services = new LinkedHashSet<Method>(serviceInstances.size());
		for (Object serviceInstance : serviceInstances) {
			for (Method method : serviceInstance.getClass().getMethods()) {
				services.add(method);
			}
		}
		return services;
	}

	/**
	 * Returns all registered services methods applicable for the given type. <b>Note</b> that workspace
	 * services are refreshed each time this is called if Eclipse is running.
	 * 
	 * @param receiverType
	 *            Type of the receiver we seek applicable services for.
	 * @return All applicable registered services methods.
	 */
	public Set<Method> getRegisteredServices(Class<?> receiverType) {
		final Set<Method> allServices = getAllRegisteredServices();
		final Set<Method> applicableServices = new LinkedHashSet<Method>(allServices.size());
		for (Method method : allServices) {
			final Class<?>[] parameters = method.getParameterTypes();
			if (parameters.length > 0 && parameters[0].equals(receiverType)) {
				applicableServices.add(method);
			}
		}
		return applicableServices;
	}

	/**
	 * Removes a service from the registry.
	 * 
	 * @param service
	 *            Service that is to be removed from Acceleo evaluations contexts.
	 * @return <code>true</code> if the set contained <code>service</code>.
	 */
	public boolean removeService(Object service) {
		return registeredServices.remove(service);
	}

	/**
	 * Clears all registered services out of the registry.
	 */
	public void clearRegistry() {
		registeredServices.clear();
	}
}
