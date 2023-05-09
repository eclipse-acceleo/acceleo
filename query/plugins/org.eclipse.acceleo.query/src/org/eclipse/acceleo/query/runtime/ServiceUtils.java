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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.EOperationService;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodReceiverService;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Utility for {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 5.0
 */
public final class ServiceUtils {

	/**
	 * Constructor.
	 */
	private ServiceUtils() {
		// nothing to do here
	}

	/**
	 * Gets the {@link Set} of {@link IService} for the given {@link Class}. If the class can't be
	 * instantiated only static {@link Method} will be used to produce {@link IService}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param cls
	 *            the {@link Class}
	 * @return the {@link Set} of {@link IService} for the given {@link Class}
	 */
	public static Set<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment, Class<?> cls) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		Object instance = null;
		try {
			Constructor<?> cstr = null;
			cstr = cls.getConstructor(new Class[] {});
			instance = cstr.newInstance(new Object[] {});
		} catch (NoSuchMethodException e) {
			// we will go without instance and register only static methods
		} catch (SecurityException e) {
			// we will go without instance and register only static methods
		} catch (InstantiationException e) {
			// we will go without instance and register only static methods
		} catch (IllegalAccessException e) {
			// we will go without instance and register only static methods
		} catch (IllegalArgumentException e) {
			// we will go without instance and register only static methods
		} catch (InvocationTargetException e) {
			// we will go without instance and register only static methods
		}
		result.addAll(getServicesFromInstance(queryEnvironment, cls, instance));

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link IService} for the given {@link Class} with receiver as first parameter.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param cls
	 *            the {@link Class}
	 * @return the {@link Set} of {@link IService} for the given {@link Class} with receiver as first
	 *         parameter
	 */
	public static Set<IService<?>> getReceiverServices(IReadOnlyQueryEnvironment queryEnvironment,
			Class<?> cls) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		for (Method method : cls.getMethods()) {
			if (isReveiverServiceMethod(method)) {
				result.add(new JavaMethodReceiverService(method));
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link IService} for the given {@link Object instance}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param instance
	 *            the {@link Object instance}
	 * @return the {@link Set} of {@link IService} for the given {@link Object instance}
	 */
	public static Set<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment, Object instance) {
		return getServicesFromInstance(queryEnvironment, instance.getClass(), instance);
	}

	/**
	 * Gets {@link IService} from the given instance and {@link Method} array.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param cls
	 *            the services {@link Class}
	 * @param instance
	 *            the instance
	 * @return the {@link ServiceRegistrationResult}
	 */
	private static Set<IService<?>> getServicesFromInstance(IReadOnlyQueryEnvironment queryEnvironment,
			Class<?> cls, Object instance) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		if (instance instanceof IServiceProvider) {
			result.addAll(((IServiceProvider)instance).getServices(queryEnvironment));
		} else {
			Method[] methods = cls.getMethods();
			for (Method method : methods) {
				if (isServiceMethod(instance, method)) {
					final IService<?> service = new JavaMethodService(method, instance);
					result.add(service);
				}
			}
		}

		return result;
	}

	/**
	 * Tells if a given {@link Method} is considered as a {@link IService}. {@link Object} methods are not
	 * considered and only <code>static</code> {@link Method} are considered if the given instance if
	 * <code>null</code>.
	 * 
	 * @param instance
	 *            the instance {@link Object} if any, <code>null</code> otherwise
	 * @param method
	 *            the {@link Method} to check
	 * @return <code>true</code> if a given {@link Method} is considered as a {@link IService},
	 *         <code>false</code> otherwise
	 */
	public static boolean isServiceMethod(Object instance, Method method) {
		final boolean result;

		if (method.getDeclaringClass() == Object.class) {
			// We do not register java.lang.Object method as
			// having an expression calling the 'wait' or the notify service
			// could yield problems that are difficult to track down.
			result = false;
		} else if (instance != null || Modifier.isStatic(method.getModifiers())) {
			// If we have no instance, only consider static methods.
			// Otherwise, any method with at least one parameter can be a service
			result = method.getParameterTypes().length > 0;
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Tells if a given {@link Method} is considered as a {@link IService} with receiver as first parameter.
	 * {@link Object} methods are not considered.
	 * 
	 * @param method
	 *            the {@link Method} to check
	 * @return <code>true</code> if a given {@link Method} is considered as a {@link IService} with receiver
	 *         as first parameter, <code>false</code> otherwise
	 */
	public static boolean isReveiverServiceMethod(Method method) {
		// We do not register java.lang.Object method as
		// having an expression calling the 'wait' or the notify service
		// could yield problems that are difficult to track down.
		return method.getDeclaringClass() != Object.class;
	}

	/**
	 * Registers a {@link Set} of {@link IService} to the given {@link IQueryEnvironment}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param services
	 *            the {@link Set} of {@link IService}
	 * @return the {@link ServiceRegistrationResult}
	 */
	public static ServiceRegistrationResult registerServices(IQueryEnvironment queryEnvironment,
			Set<IService<?>> services) {
		final ServiceRegistrationResult result = new ServiceRegistrationResult();

		for (IService<?> service : services) {
			result.merge(queryEnvironment.registerService(service));
		}

		return result;
	}

	/**
	 * Removes a {@link Set} of {@link IService} from the given {@link IQueryEnvironment}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param services
	 *            the {@link Set} of {@link IService}
	 */
	public static void removeServices(IQueryEnvironment queryEnvironment, Set<IService<?>> services) {
		for (IService<?> service : services) {
			queryEnvironment.removeService(service);
		}
	}

	/**
	 * Gets the {@link Set} of {@link IService} for the given {@link EPackage}.
	 * 
	 * @param ePkg
	 *            the {@link EPackage}
	 * @return the {@link Set} of {@link IService} for the given {@link EPackage}
	 */
	public static Set<IService<?>> getServices(EPackage ePkg) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		for (EClassifier eClassifier : ePkg.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				result.addAll(getServices((EClass)eClassifier));
			}
		}
		for (EPackage child : ePkg.getESubpackages()) {
			result.addAll(getServices(child));
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link IService} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link IService} for the given {@link EClass}
	 */
	public static Set<IService<?>> getServices(EClass eCls) {
		final Set<IService<?>> result = new LinkedHashSet<IService<?>>();

		for (EOperation eOperation : eCls.getEAllOperations()) {
			if (isServiceEOperation(eOperation)) {
				result.add(new EOperationService(eOperation));
			}
		}

		return result;
	}

	/**
	 * Tells if the given {@link EOperation} is considered as a {@link IService}.
	 * {@link org.eclipse.emf.ecore.EObject EObject} {@link EOperation} are not considered as {@link IService}
	 * .
	 * 
	 * @param eOperation
	 *            the {@link EOperation} to test
	 * @return <code>true</code> if the given {@link EOperation} is considered as a {@link IService},
	 *         <code>false</code> otherwise
	 */
	public static boolean isServiceEOperation(EOperation eOperation) {
		return eOperation.getEContainingClass() != EcorePackage.eINSTANCE.getEObject();
	}

}
