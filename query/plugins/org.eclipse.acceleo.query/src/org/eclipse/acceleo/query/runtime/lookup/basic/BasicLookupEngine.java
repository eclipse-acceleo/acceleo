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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Lookup engine are used to retrieve services from a name and a set of arguments.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class BasicLookupEngine implements ILookupEngine {

	/**
	 * Message used when a service cannot be instantiated.
	 */
	private static final String INSTANTIATION_PROBLEM_MSG = "Couldn't instantiate class ";

	/**
	 * Message used when a service doesn't have a zero-argument constructor.
	 */
	private static final String PACKAGE_PROBLEM_MSG = "No zero argument constructor found in class ";

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	protected IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Maps of multimethods : maps the arity to maps that maps service names to their IService list.
	 */
	private final ServiceStore services;

	/**
	 * Mapping from a {@link Class} to its {@link IService}.
	 */
	private final Map<Class<?>, Set<IService>> classToServices = new LinkedHashMap<Class<?>, Set<IService>>();

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
	public Map<Class<?>, Set<IService>> getRegisteredServices() {
		final Map<Class<?>, Set<IService>> result = new LinkedHashMap<Class<?>, Set<IService>>();

		for (Entry<Class<?>, Set<IService>> entry : classToServices.entrySet()) {
			result.put(entry.getKey(), new LinkedHashSet<IService>(entry.getValue()));
		}

		return result;
	}

	@Override
	public IService lookup(String name, IType[] argumentTypes) {
		List<IService> multiMethod = services.getMultiService(name, argumentTypes.length);
		if (multiMethod == null) {
			return null;
		} else {
			IService result = null;
			for (IService service : multiMethod) {
				if (service.matches(queryEnvironment, argumentTypes)) {
					if (result == null
							|| service.getPriority() > result.getPriority()
							|| (service.getPriority() == result.getPriority() && service
									.isLowerOrEqualParameterTypes(queryEnvironment, result))) {
						result = service;
					}
				}
			}
			return result;
		}
	}

	@Override
	public boolean isServiceMethod(Object instance, Method method) {
		// We do not register java.lang.Object method as
		// having an expression calling the 'wait' or the notify service
		// could yield problems that are difficult to track down.
		return method.getDeclaringClass() != Object.class
				&& (instance != null || Modifier.isStatic(method.getModifiers()))
				&& method.getParameterTypes().length > 0;
	}

	/**
	 * Registers a new set of services provided by the given {@link IServiceProvider}.
	 * 
	 * @param provider
	 *            the {@link IServiceProvider} to register
	 * @return the {@link ServiceRegistrationResult}
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class doesn't follow the acceleo package rules.
	 */
	private ServiceRegistrationResult registerServices(IServiceProvider provider)
			throws InvalidAcceleoPackageException {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		for (IService service : provider.getServices(queryEnvironment)) {
			result.merge(registerService(provider.getClass(), service));
		}

		return result;
	}

	/**
	 * Registers a new set of services.
	 * 
	 * @param newServices
	 *            the {@link Class} containing the methods to register can't be <code>null</code>
	 * @return the {@link ServiceRegistrationResult}
	 * @throws InvalidAcceleoPackageException
	 *             if the specified {@link Class} doesn't follow the acceleo package rules
	 */
	public ServiceRegistrationResult registerServices(Class<?> newServices)
			throws InvalidAcceleoPackageException {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		if (newServices == null) {
			throw new NullPointerException("the service class can't be null");
		} else if (!isRegisteredService(newServices)) {
			try {
				Constructor<?> cstr = null;
				Object instance = null;
				try {
					cstr = newServices.getConstructor(new Class[] {});
					instance = cstr.newInstance(new Object[] {});
				} catch (NoSuchMethodException e) {
					// we will go without instance and register only static methods
				}
				result.merge(registerServiceInstance(newServices, instance));
			} catch (SecurityException e) {
				throw new InvalidAcceleoPackageException(
						PACKAGE_PROBLEM_MSG + newServices.getCanonicalName(), e);
			} catch (InstantiationException e) {
				throw new InvalidAcceleoPackageException(INSTANTIATION_PROBLEM_MSG
						+ newServices.getCanonicalName(), e);
			} catch (IllegalAccessException e) {
				throw new InvalidAcceleoPackageException(INSTANTIATION_PROBLEM_MSG
						+ newServices.getCanonicalName(), e);
			} catch (IllegalArgumentException e) {
				throw new InvalidAcceleoPackageException(INSTANTIATION_PROBLEM_MSG
						+ newServices.getCanonicalName(), e);
			} catch (InvocationTargetException e) {
				throw new InvalidAcceleoPackageException(INSTANTIATION_PROBLEM_MSG
						+ newServices.getCanonicalName(), e);
			}
		}

		return result;
	}

	/**
	 * Registers a new set of services from a given instance.
	 * 
	 * @param instance
	 *            the instance
	 * @return the {@link ServiceRegistrationResult}
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class doesn't follow the acceleo package rules.
	 * @throws IllegalAccessException
	 *             if invocation to set the cross referencer fails
	 * @throws InvocationTargetException
	 *             if invocation to set the cross referencer fails
	 */
	public ServiceRegistrationResult registerServiceInstance(Object instance)
			throws InvalidAcceleoPackageException {
		return registerServiceInstance(instance.getClass(), instance);
	}

	/**
	 * Registers a new set of services from a given instance.
	 * 
	 * @param newServices
	 *            the service {@link Class}
	 * @param instance
	 *            the service instance
	 * @return the {@link ServiceRegistrationResult}
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class doesn't follow the acceleo package rules.
	 */
	private ServiceRegistrationResult registerServiceInstance(Class<?> newServices, Object instance)
			throws InvalidAcceleoPackageException {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		if (!isRegisteredService(newServices)) {
			if (instance instanceof IServiceProvider) {
				result.merge(registerServices((IServiceProvider)instance));
			} else {
				Method[] methods = newServices.getMethods();
				result.merge(getServicesFromInstance(newServices, instance, methods));
			}
		}

		return result;
	}

	@Override
	public boolean isRegisteredService(Class<?> cls) {
		return classToServices.containsKey(cls);
	}

	/**
	 * Removes {@link IService} related to the given {@link Class}.
	 * 
	 * @param servicesClass
	 *            the {@link Class} to unregister.
	 * @return the removed {@link Class} if any, <code>null</code> otherwise
	 */
	public Class<?> removeServices(Class<?> servicesClass) {
		final Class<?> result;

		final Set<IService> servicesSet = classToServices.remove(servicesClass);
		if (servicesSet != null) {
			result = servicesClass;
			for (IService service : servicesSet) {
				services.remove(service);
			}
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Gets {@link IService} from the given instance and {@link Method} array.
	 * 
	 * @param newServices
	 *            the services {@link Class}
	 * @param instance
	 *            the instance
	 * @param methods
	 *            the array of {@link Method}
	 * @return the {@link ServiceRegistrationResult}
	 */
	private ServiceRegistrationResult getServicesFromInstance(Class<?> newServices, Object instance,
			Method[] methods) {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		for (Method method : methods) {
			if (isServiceMethod(instance, method)) {
				final IService service = new JavaMethodService(method, instance);
				result.merge(registerService(newServices, service));
			}
		}

		return result;
	}

	/**
	 * Registers the given {@link IService}.
	 * 
	 * @param newServices
	 *            the services {@link Class}
	 * @param service
	 *            the {@link IService} to register
	 * @return the {@link ServiceRegistrationResult}
	 */
	private ServiceRegistrationResult registerService(Class<?> newServices, IService service) {
		final ServiceRegistrationResult result = services.add(service);

		Set<IService> servicesSet = classToServices.get(newServices);
		if (servicesSet == null) {
			servicesSet = new LinkedHashSet<IService>();
			classToServices.put(newServices, servicesSet);
		}
		servicesSet.add(service);

		return result;
	}

	@Override
	public Set<IService> getServices(Set<Class<?>> receiverTypes) {
		final Set<IService> result = new LinkedHashSet<IService>();

		for (Class<?> cls : receiverTypes) {
			if (cls != null) {
				for (Set<IService> servicesSet : classToServices.values()) {
					for (IService service : servicesSet) {
						if (service.getParameterTypes(queryEnvironment).get(0).isAssignableFrom(
								new ClassType(queryEnvironment, cls))) {
							result.add(service);
						}
					}
				}
			}
		}

		return result;
	}

}
