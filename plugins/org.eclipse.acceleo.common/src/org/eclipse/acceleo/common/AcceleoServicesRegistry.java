/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.AcceleoServicesEclipseUtil;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
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
	private final Set<Class<?>> registeredServices = new CompactLinkedHashSet<Class<?>>();

	/** This will allow us to only instantiate services once. */
	private final Map<Class<?>, Object> serviceInstances = new HashMap<Class<?>, Object>();

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
	 * @since 3.0
	 */
	public boolean addServiceClass(Class<?> service) {
		return registeredServices.add(service);
	}

	/**
	 * Adds a service to the registry.
	 * 
	 * @param service
	 *            Service that is to be registered for Acceleo evaluations.
	 * @return <code>true</code> if the set didn't already contain <code>service</code>.
	 * @deprecated
	 */
	@Deprecated
	public boolean addService(Object service) {
		boolean registered = false;
		if (service instanceof Class<?>) {
			registered = addServiceClass((Class<?>)service);
		} else {
			registered = addServiceClass(service.getClass());
		}
		return registered;
	}

	/**
	 * This will attempt to register the service corresponding to the given information within the registry.
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
	 * @deprecated
	 */
	@Deprecated
	public boolean addService(String bundleName, String qualifiedName) {
		return addServiceClass(bundleName, qualifiedName) != null;
	}

	/**
	 * This will attempt to register the service corresponding to the given information within the registry.
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
	 * @return The registered Class if any.
	 * @since 3.0
	 */
	public Class<?> addServiceClass(String bundleName, String qualifiedName) {
		Class<?> clazz = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			clazz = AcceleoServicesEclipseUtil.registerService(bundleName, qualifiedName);
		}
		if (clazz == null) {
			try {
				clazz = Class.forName(qualifiedName);
				if (clazz != null) {
					registeredServices.add(clazz);
				}
			} catch (ClassNotFoundException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
						"AcceleoServicesRegistry.ClassLookupFailure", qualifiedName), e, true); //$NON-NLS-1$
			}
		}
		return clazz;
	}

	/**
	 * This will attempt to register the service corresponding to the given information within the registry.
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
	 * @deprecated
	 */
	@Deprecated
	public Object addService(URI uri, String qualifiedName) {
		return addServiceClass(uri, qualifiedName);
	}

	/**
	 * This will attempt to register the service corresponding to the given information within the registry.
	 * <p>
	 * If Eclipse is currently running, it will try and find a workspace plugin corresponding to the given URI
	 * and install it, then load the class <code>qualifiedName</code> in this workspace defined bundle. When
	 * no corresponding workspace plugin can be found, the registry will try and find it in the installed
	 * bundles.
	 * </p>
	 * <p>
	 * Outside of Eclipse, this will simply try to load a class named <code>qualifiedName</code> from the
	 * current classloader.
	 * </p>
	 * 
	 * @param uri
	 *            URI of the module currently being evaluated. This will be used as a source to find the
	 *            required service by looking through its dependencies.
	 * @param qualifiedName
	 *            Qualified name of the service class we need an instance of.
	 * @return The loaded class if any.
	 * @since 3.0
	 */
	public Class<?> addServiceClass(URI uri, String qualifiedName) {
		Class<?> clazz = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			clazz = AcceleoServicesEclipseUtil.registerService(uri, qualifiedName);
		}
		if (clazz == null) {
			try {
				clazz = Class.forName(qualifiedName);
				if (clazz != null) {
					registeredServices.add(clazz);
				}
			} catch (ClassNotFoundException e) {
				if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
					AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
							"AcceleoServicesRegistry.ClassLookupFailure", qualifiedName), e, true); //$NON-NLS-1$
				}
			}
		}
		return clazz;
	}

	/**
	 * This will return the singleton instance of the given class that serves as invocation source.
	 * 
	 * @param serviceClass
	 *            The class we need the service singleton of.
	 * @return The singleton instance of the given service class.
	 * @since 3.0
	 */
	public Object getServiceInstance(Class<?> serviceClass) {
		Object serviceInstance = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			serviceInstance = AcceleoServicesEclipseUtil.getServiceInstance(serviceClass);
		}
		if (serviceInstance == null) {
			serviceInstance = serviceInstances.get(serviceClass);
			if (serviceInstance == null) {
				try {
					serviceInstance = serviceClass.newInstance();
					serviceInstances.put(serviceClass, serviceInstance);
				} catch (InstantiationException e) {
					AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(INSTANTIATION_FAILURE_KEY,
							serviceClass.getName()), e, false);
				} catch (IllegalAccessException e) {
					AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(CONSTRUCTOR_FAILURE_KEY,
							serviceClass.getName()), e, false);
				}
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
		serviceInstances.clear();
	}
}
