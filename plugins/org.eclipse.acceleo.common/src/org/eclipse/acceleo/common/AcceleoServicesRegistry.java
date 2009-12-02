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
import org.eclipse.emf.common.util.URI;

/**
 * This will allow Acceleo to know which java services are to be added to the evaluation context.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public final class AcceleoServicesRegistry {
	/** Singleton instance of the registry. */
	public static final AcceleoServicesRegistry INSTANCE = new AcceleoServicesRegistry();

	/** Key of the error message displayed in case of instantiation exceptions. */
	private static final String INSTANTIATION_FAILURE_KEY = "AcceleoServicesRegistry.ClassInstantiationFailure"; //$NON-NLS-1$

	/** Key of the error message displayed in case of exceptions in service constructors. */
	private static final String CONSTRUCTOR_FAILURE_KEY = "AcceleoServicesRegistry.ClassConstructorFailure"; //$NON-NLS-1$

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
		boolean registered = false;
		if (service instanceof Class<?>) {
			try {
				registered = registeredServices.add(((Class<?>)service).newInstance());
			} catch (InstantiationException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(INSTANTIATION_FAILURE_KEY,
						((Class<?>)service).getName()), e, false);
			} catch (IllegalAccessException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(CONSTRUCTOR_FAILURE_KEY,
						((Class<?>)service).getName()), e, false);
			}
		} else {
			registered = registeredServices.add(service);
		}
		return registered;
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
	 *This will attempt to register the service corresponding to the given information within the registry.
	 * <p>
	 * If Eclipse is currently running, it will try and find a workspace plugin which symbolic name is equal
	 * to <code>bundleName</code> and install it, then load the class <code>qualifiedName</code> in this
	 * workspace defined bundle. When no workspace plugin can be found with this symbolic name, the registry
	 * will try and find an installed bundle with this name.
	 * </p>
	 * <p>
	 * Outside of Eclispe, this will simply try to load a class named <code>qualifiedName</code> from the
	 * current classloader.
	 * </p>
	 * 
	 * @param bundleName
	 *            Symbolic name of the bundle containing the acceleo generator currently being evaluated.
	 * @param qualifiedName
	 *            Qualified name of the service class we need an instance of.
	 * @return <code>true</code> if we could instantiate the service and add it for evaluation,
	 *         <code>false</code> otherwise.
	 * @since 0.8
	 */
	public boolean addService(String bundleName, String qualifiedName) {
		Object serviceInstance = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			serviceInstance = AcceleoServicesEclipseUtil.registerService(bundleName, qualifiedName);
		} else {
			try {
				final Class<?> clazz = Class.forName(qualifiedName);
				serviceInstance = clazz.newInstance();
				if (serviceInstance != null) {
					registeredServices.add(serviceInstance);
				}
			} catch (ClassNotFoundException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
						"AcceleoServicesRegistry.ClassLookupFailure", qualifiedName), e, true); //$NON-NLS-1$
			} catch (IllegalAccessException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(CONSTRUCTOR_FAILURE_KEY,
						qualifiedName), e, false);
			} catch (InstantiationException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(INSTANTIATION_FAILURE_KEY,
						qualifiedName), e, false);
			}
		}
		return serviceInstance != null;
	}

	/**
	 *This will attempt to register the service corresponding to the given information within the registry.
	 * <p>
	 * If Eclipse is currently running, it will try and find a workspace plugin corresponding to the given URI
	 * and install it, then load the class <code>qualifiedName</code> in this workspace defined bundle. When
	 * no corrsponding workspace plugin can be found, the registry will try and find it in the installed
	 * bundles.
	 * </p>
	 * <p>
	 * Outside of Eclispe, this will simply try to load a class named <code>qualifiedName</code> from the
	 * current classloader.
	 * </p>
	 * 
	 * @param uri
	 *            URI of the module currently being evaluated. This will be used as a source to find the
	 *            required service by looking through its dependencies.
	 * @param qualifiedName
	 *            Qualified name of the service class we need an instance of.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 * @since 0.8
	 */
	public Object addService(URI uri, String qualifiedName) {
		Object serviceInstance = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			serviceInstance = AcceleoServicesEclipseUtil.registerService(uri, qualifiedName);
		} else {
			try {
				final Class<?> clazz = Class.forName(qualifiedName);
				serviceInstance = clazz.newInstance();
				if (serviceInstance != null) {
					registeredServices.add(serviceInstance);
				}
			} catch (ClassNotFoundException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
						"AcceleoServicesRegistry.ClassLookupFailure", qualifiedName), e, true); //$NON-NLS-1$
			} catch (IllegalAccessException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(CONSTRUCTOR_FAILURE_KEY,
						qualifiedName), e, false);
			} catch (InstantiationException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(INSTANTIATION_FAILURE_KEY,
						qualifiedName), e, false);
			}
		}
		return serviceInstance;
	}

	/**
	 * Removes a service from the registry. <b>Note</b> that this cannot be used to remove a service
	 * registered dynamically registered from the workspace.
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
