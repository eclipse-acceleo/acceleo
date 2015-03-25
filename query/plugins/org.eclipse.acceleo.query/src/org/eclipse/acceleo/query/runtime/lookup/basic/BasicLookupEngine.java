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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;

/**
 * Lookup engine are used to retrieve services from a name and a set of arguments.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class BasicLookupEngine implements ILookupEngine {

	/**
	 * The method name a query service that needs to use a registered cross referencer must have so it can be
	 * pass to the services.
	 */
	private static final String SET_CROSS_REFERENCER_METHOD_NAME = "setCrossReferencer";

	/**
	 * Message used when a service doesn't have a zero-argument constructor.
	 */
	private static final String PACKAGE_PROBLEM_MSG = "No zero argument constructor found in class ";

	/**
	 * Maps of multimethods : maps the arity to maps that maps service names to their IService list.
	 */
	private final Map<Integer, Map<String, List<IService>>> services = new HashMap<Integer, Map<String, List<IService>>>();

	/**
	 * {@link List} of known {@link IService} and their addition order.
	 */
	private final List<IService> servicesList = new ArrayList<IService>();

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	private IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * The {@link CrossReferencer} that will be used to resolve eReference requests in EObject service.
	 */
	private CrossReferenceProvider crossReferencer;

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
		this.queryEnvironment = queryEnvironment;
		this.crossReferencer = crossReferencer;
	}

	@Override
	public CrossReferenceProvider getCrossReferencer() {
		return crossReferencer;
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
	 *            method2 to compare.
	 * @return <code>true</code> if method1 < method2 in terms of parameter types.
	 */
	private boolean lower(Method method1, Method method2) {
		Class<?>[] params1 = method1.getParameterTypes();
		Class<?>[] params2 = method2.getParameterTypes();
		int size = params1.length;
		for (int i = 0; i < size; i++) {
			Class<?> param1 = params1[i];
			Class<?> param2 = params2[i];
			if (param2.isAssignableFrom(param1) && param1 != param2) {
				return true;
			}
		}
		return false;
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
						&& (result == null || lower(method, result.getServiceMethod()))) {
					result = service;
				}
			}
			return result;
		}
	}

	@Override
	public boolean isServiceMethod(Method method) {
		boolean objectMethod = method.getDeclaringClass() != Object.class;
		return objectMethod;
	}

	@Override
	public boolean isCrossReferencerMethod(Method method) {
		// We do not register java.lang.Object method as
		// having an expression calling the 'wait' or the notify service
		// could yield problems that are difficult to track down.
		boolean crossRefSet = SET_CROSS_REFERENCER_METHOD_NAME.equals(method.getName())
				&& method.getParameterTypes().length > 0
				&& CrossReferenceProvider.class.isAssignableFrom(method.getParameterTypes()[0]);
		return crossRefSet;
	}

	/**
	 * Registers a new set of services provided by the given {@link IServiceProvider}.
	 * 
	 * @param provider
	 *            the {@link IServiceProvider} to register
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class doesn't follow the acceleo package rules.
	 */
	public void addServices(IServiceProvider provider) throws InvalidAcceleoPackageException {
		for (IService service : provider.getServices(queryEnvironment)) {
			final List<IService> multiMethod = getOrCreateMultimethod(service.getServiceMethod().getName(),
					service.getServiceMethod().getParameterTypes().length);
			multiMethod.add(service);
			servicesList.add(service);
		}
	}

	/**
	 * Registers a new set of services.
	 * 
	 * @param newServices
	 *            the class containing the methods to register.
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class doesn't follow the acceleo package rules.
	 */
	public void addServices(Class<?> newServices) throws InvalidAcceleoPackageException {
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
					throw new InvalidAcceleoPackageException(PACKAGE_PROBLEM_MSG
							+ newServices.getCanonicalName(), e);
				}
			}
			if (instance instanceof IServiceProvider) {
				addServices((IServiceProvider)instance);
			} else {
				Method[] methods = newServices.getMethods();
				getServicesFromInstance(instance, methods);
			}
		} catch (SecurityException e) {
			throw new InvalidAcceleoPackageException(PACKAGE_PROBLEM_MSG + newServices.getCanonicalName(), e);
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

	/**
	 * Gets {@link IService} from the given instance and {@link Method} array.
	 * 
	 * @param instance
	 *            the instance
	 * @param methods
	 *            the array of {@link Method}
	 * @throws IllegalAccessException
	 *             if invocation to set the cross referencer fails
	 * @throws InvocationTargetException
	 *             if invocation to set the cross referencer fails
	 */
	private void getServicesFromInstance(Object instance, Method[] methods) throws IllegalAccessException,
			InvocationTargetException {
		for (Method method : methods) {
			if (isCrossReferencerMethod(method)) {
				method.invoke(instance, crossReferencer);
			} else if (isServiceMethod(method)) {
				List<IService> multiMethod = getOrCreateMultimethod(method.getName(), method
						.getParameterTypes().length);
				final IService service = new Service(method, instance);
				multiMethod.add(service);
				servicesList.add(service);
			}
		}
	}

	@Override
	public Set<IService> getServices(Set<Class<?>> receiverTypes) {
		final Set<IService> result = new LinkedHashSet<IService>();

		for (Class<?> cls : receiverTypes) {
			for (IService service : servicesList) {
				if (service.getServiceMethod().getParameterTypes()[0].isAssignableFrom(cls)) {
					result.add(service);
				}
			}
		}

		return result;
	}

}
