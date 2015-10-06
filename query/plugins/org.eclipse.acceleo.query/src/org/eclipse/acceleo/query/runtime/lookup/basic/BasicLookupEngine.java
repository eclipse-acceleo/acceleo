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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;

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
	 * The method name a query service that needs to use a registered cross referencer must have so it can be
	 * pass to the services.
	 */
	private static final String SET_CROSS_REFERENCER_METHOD_NAME = "setCrossReferencer";

	/**
	 * The method name a query service that needs to use a registered cross referencer must have so it can be
	 * pass to the services.
	 */
	private static final String SET_ROOT_PROVIDER_METHOD_NAME = "setRootProvider";

	/**
	 * Message used when a service doesn't have a zero-argument constructor.
	 */
	private static final String PACKAGE_PROBLEM_MSG = "No zero argument constructor found in class ";

	/**
	 * Maps of multimethods : maps the arity to maps that maps service names to their IService list.
	 */
	private final Map<Integer, Map<String, List<IService>>> services = new HashMap<Integer, Map<String, List<IService>>>();

	/**
	 * Mapping from a {@link Class} to its {@link IService}.
	 */
	private final Map<Class<?>, Set<IService>> classToServices = new LinkedHashMap<Class<?>, Set<IService>>();

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * The {@link CrossReferencer} that will be used to resolve eReference requests in EObject service.
	 */
	private CrossReferenceProvider crossReferencer;

	/**
	 * The {@link IRootEObjectProvider} that will be used to search all instances requests in EObject service.
	 */
	private IRootEObjectProvider rootProvider;

	/**
	 * Constructor. Initializes the lookup engine with a cross referencer.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param crossReferencer
	 *            The {@link CrossReferencer} that will be used to resolve eReference requests in EObject
	 *            service.
	 */
	public BasicLookupEngine(IReadOnlyQueryEnvironment queryEnvironment,
			CrossReferenceProvider crossReferencer) {
		this(queryEnvironment, crossReferencer, null);
	}

	/**
	 * Constructor. Initializes the lookup engine with a cross referencer.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param crossReferencer
	 *            the {@link CrossReferencer} that will be used to resolve eReference requests in EObject
	 *            service
	 * @param rootProvider
	 *            the {@link IRootEObjectProvider} that will be used to search all instances in EObject
	 *            service
	 * @since 4.0.0
	 */
	public BasicLookupEngine(IReadOnlyQueryEnvironment queryEnvironment,
			CrossReferenceProvider crossReferencer, IRootEObjectProvider rootProvider) {
		this.queryEnvironment = queryEnvironment;
		this.crossReferencer = crossReferencer;
		this.rootProvider = rootProvider;
	}

	@Override
	public CrossReferenceProvider getCrossReferencer() {
		return crossReferencer;
	}

	@Override
	public IRootEObjectProvider getRootEObjectProvider() {
		return rootProvider;
	}

	/**
	 * Gets Maps of multimethods : maps the arity to maps that maps service names to their IService list.
	 * 
	 * @return Maps of multimethods : maps the arity to maps that maps service names to their IService list
	 */
	protected Map<Integer, Map<String, List<IService>>> getServices() {
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

	/**
	 * retrieve or create a multimethod from argcount and the service name.
	 * 
	 * @param methodName
	 *            the name of the multimethod to retrieve.
	 * @param argc
	 *            the arg count of the multimethod to retrieve.
	 * @return the list of {@link IService} instances that make up the retrieve multimethod.
	 */
	private List<IService> getOrCreateMultimethod(String methodName, int argc) {
		Map<String, List<IService>> argcServices = services.get(argc);
		if (argcServices == null) {
			argcServices = new HashMap<String, List<IService>>();
			services.put(argc, argcServices);
		}
		List<IService> result = argcServices.get(methodName);
		if (result == null) {
			result = new ArrayList<IService>();
			argcServices.put(methodName, result);
		}
		return result;
	}

	/**
	 * retrieve a multimethod from argcount and the service name.
	 * 
	 * @param methodName
	 *            the name of the multimethod to retrieve.
	 * @param argc
	 *            the arg count of the multimethod to retrieve.
	 * @return the list of {@link IService} instances that make up the retrieve multimethod.
	 */

	private List<IService> getMultimethod(String methodName, int argc) {
		Map<String, List<IService>> argcServices = services.get(argc);
		if (argcServices == null) {
			return null;
		} else {
			return argcServices.get(methodName);
		}
	}

	/**
	 * Predicates that is <code>true</code> when the specified argument types match the specified method's
	 * parameter types. An argument's type matches a parameter's type if the latter is assignable from the
	 * former.
	 * 
	 * @param method
	 *            the method to match.
	 * @param argumentTypes
	 *            the argument to match agains the method's parameters' type.
	 * @return <code>true</code> when the specified method matches the specified set of types.
	 */
	private boolean matches(Method method, Class<?>[] argumentTypes) {
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length != argumentTypes.length) {
			throw new IllegalArgumentException("the specified method doesn't have the right parameter number");
		}
		for (int i = 0; i < parameterTypes.length; i++) {
			if (argumentTypes[i] != null && !parameterTypes[i].isAssignableFrom(argumentTypes[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Predicates that is <code>true</code> if and only if all method1's parameter types are assignable to
	 * method2's parameter types at the same index.
	 * 
	 * @param method1
	 *            method1 to compare
	 * @param method2
	 *            method2 to compare
	 * @return <code>true</code> if method1 <= method2 in terms of parameter types, <code>false</code>
	 *         otherwise
	 */
	private boolean isLowerOrEqualParameterTypes(Method method1, Method method2) {
		final Class<?>[] params1 = method1.getParameterTypes();
		final Class<?>[] params2 = method2.getParameterTypes();
		boolean result = params1.length == params2.length;

		if (result) {
			final int size = params1.length;
			for (int i = 0; i < size; i++) {
				Class<?> param1 = params1[i];
				Class<?> param2 = params2[i];
				if (!param2.isAssignableFrom(param1)) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Predicates that is <code>true</code> if and only if all method1's parameter types are assignable to
	 * method2's parameter types at the same index and not all parameter types are the same at the same index.
	 * 
	 * @param method1
	 *            method1 to compare
	 * @param method2
	 *            method2 to compare
	 * @return <code>true</code> if method1 < method2 in terms of parameter types, <code>false</code>
	 *         otherwise
	 */
	private boolean isLowerParameterTypes(Method method1, Method method2) {
		return isLowerOrEqualParameterTypes(method1, method2) && !isEqualParameterTypes(method1, method2);
	}

	/**
	 * Predicates that is <code>true</code> if and only if all method1's parameter types are the same as
	 * method2's parameter types at the same index.
	 * 
	 * @param method1
	 *            method1 to compare
	 * @param method2
	 *            method2 to compare
	 * @return <code>true</code> if method1 == method2 in terms of parameter types, <code>false</code>
	 *         otherwise
	 */
	private boolean isEqualParameterTypes(Method method1, Method method2) {
		final Class<?>[] params1 = method1.getParameterTypes();
		final Class<?>[] params2 = method2.getParameterTypes();
		boolean result = params1.length == params2.length;

		if (result) {
			final int size = params1.length;
			for (int i = 0; i < size; i++) {
				Class<?> param1 = params1[i];
				Class<?> param2 = params2[i];
				if (param1 != param2) {
					result = false;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * Tells if given methods are equals (same name and same argument types).
	 * 
	 * @param method1
	 *            method1 to compare
	 * @param method2
	 *            method2 to compare
	 * @return <code>true</code> if given methods are equals (same name and same argument types),
	 *         <code>false</code> otherwise
	 */
	private boolean isEqual(Method method1, Method method2) {
		return method1.getName().equals(method2.getName()) && isEqualParameterTypes(method1, method2);
	}

	/**
	 * Tells if given methods have the same name and
	 * {@link BasicLookupEngine#isLowerParameterTypes(Method, Method) lower parameter types}.
	 * 
	 * @param method1
	 *            method1 to compare
	 * @param method2
	 *            method2 to compare
	 * @return <code>true</code> if given methods have the same name and
	 *         {@link BasicLookupEngine#isLowerParameterTypes(Method, Method) lower parameter types},
	 *         <code>false</code> otherwise
	 */
	private boolean isLower(Method method1, Method method2) {
		return method1.getName().equals(method2.getName()) && isLowerParameterTypes(method1, method2);
	}

	@Override
	public IService lookup(String name, Class<?>[] argumentTypes) {
		List<IService> multiMethod = getMultimethod(name, argumentTypes.length);
		if (multiMethod == null) {
			return null;
		} else {
			IService result = null;
			for (IService service : multiMethod) {
				Method method = service.getServiceMethod();
				if (matches(method, argumentTypes)
						&& (result == null || isLowerOrEqualParameterTypes(method, result.getServiceMethod()))) {
					result = service;
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
				&& (instance != null || Modifier.isStatic(method.getModifiers()));
	}

	@Override
	public boolean isCrossReferencerMethod(Method method) {
		final boolean crossRefSet = SET_CROSS_REFERENCER_METHOD_NAME.equals(method.getName())
				&& method.getParameterTypes().length > 0
				&& CrossReferenceProvider.class.isAssignableFrom(method.getParameterTypes()[0]);

		return crossRefSet;
	}

	@Override
	public boolean isRootProviderMethod(Method method) {
		final boolean result = SET_ROOT_PROVIDER_METHOD_NAME.equals(method.getName())
				&& method.getParameterTypes().length > 0
				&& IRootEObjectProvider.class.isAssignableFrom(method.getParameterTypes()[0]);

		return result;
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
	 *            the {@link Class} containing the methods to register
	 * @return the {@link ServiceRegistrationResult}
	 * @throws InvalidAcceleoPackageException
	 *             if the specified {@link Class} doesn't follow the acceleo package rules
	 */
	public ServiceRegistrationResult registerServices(Class<?> newServices)
			throws InvalidAcceleoPackageException {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		if (!isRegisteredService(newServices)) {
			try {
				Constructor<?> cstr = null;
				Object instance = null;
				try {
					cstr = newServices.getConstructor(new Class[] {});
					instance = cstr.newInstance(new Object[] {});
				} catch (NoSuchMethodException e) {
					try {
						cstr = newServices.getConstructor(new Class[] {IReadOnlyQueryEnvironment.class });
						instance = cstr.newInstance(new Object[] {queryEnvironment });
					} catch (NoSuchMethodException e1) {
						// we will go without instance and register only static methods
					}
				}
				if (instance instanceof IServiceProvider) {
					result.merge(registerServices((IServiceProvider)instance));
				} else {
					Method[] methods = newServices.getMethods();
					result.merge(getServicesFromInstance(newServices, instance, methods));
				}
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

	@Override
	public boolean isRegisteredService(Class<?> cls) {
		return classToServices.containsKey(cls);
	}

	/**
	 * Removes {@link IService} related to the given {@link Class}.
	 * 
	 * @param servicesClass
	 *            the {@link Class} to unregister.
	 */
	public void removeServices(Class<?> servicesClass) {
		Set<IService> servicesSet = classToServices.remove(servicesClass);
		if (servicesSet != null) {
			for (IService service : servicesSet) {
				final int argc = service.getServiceMethod().getParameterTypes().length;
				final Map<String, List<IService>> argcServices = services.get(argc);
				final String serviceName = service.getServiceMethod().getName();
				final List<IService> servicesList = argcServices.get(serviceName);
				servicesList.remove(service);
				if (servicesList.isEmpty()) {
					argcServices.remove(serviceName);
					if (argcServices.isEmpty()) {
						services.remove(argc);
					}
				}
			}
		}
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
	 * @throws IllegalAccessException
	 *             if invocation to set the cross referencer fails
	 * @throws InvocationTargetException
	 *             if invocation to set the cross referencer fails
	 */
	private ServiceRegistrationResult getServicesFromInstance(Class<?> newServices, Object instance,
			Method[] methods) throws IllegalAccessException, InvocationTargetException {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		for (Method method : methods) {
			if (isCrossReferencerMethod(method)) {
				method.invoke(instance, crossReferencer);
			} else if (isServiceMethod(instance, method)) {
				final IService service = new Service(method, instance);
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
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		final List<IService> multiMethod = getOrCreateMultimethod(service.getServiceMethod().getName(),
				service.getServiceMethod().getParameterTypes().length);
		for (IService existingService : multiMethod) {
			if (isEqual(service.getServiceMethod(), existingService.getServiceMethod())) {
				result.addDuplicated(service.getServiceMethod(), existingService.getServiceMethod());
			} else if (isLower(service.getServiceMethod(), existingService.getServiceMethod())) {
				result.addMasked(service.getServiceMethod(), existingService.getServiceMethod());
			} else if (isLower(existingService.getServiceMethod(), service.getServiceMethod())) {
				result.addIsMaskedBy(service.getServiceMethod(), existingService.getServiceMethod());
			}
		}
		result.getRegistered().add(service.getServiceMethod());
		multiMethod.add(service);
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
						if (service.getServiceMethod().getParameterTypes()[0].isAssignableFrom(cls)) {
							result.add(service);
						}
					}
				}
			}
		}

		return result;
	}

}
