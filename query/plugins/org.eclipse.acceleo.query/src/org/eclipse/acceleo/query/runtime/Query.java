/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.util.Properties;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.namespace.QualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.services.PromptServices;
import org.eclipse.acceleo.query.services.PropertiesServices;
import org.eclipse.acceleo.query.services.ResourceServices;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

/**
 * Static utility methods pertaining to Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class Query {
	/** Hides the default constructor. */
	private Query() {
		// Shouldn't be instantiated.
	}

	/**
	 * Create a new {@link IQueryEnvironment} configured with the services provided by default with Acceleo
	 * Query.
	 * 
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @return a new {@link IQueryEnvironment} configured with the services provided by default with Acceleo
	 *         Query
	 */
	public static IQueryEnvironment newEnvironmentWithDefaultServices(CrossReferenceProvider xRefProvider) {
		return newEnvironmentWithDefaultServices(xRefProvider, null);
	}

	/**
	 * Create a new {@link IQueryEnvironment} configured with the services provided by default with Acceleo
	 * Query.
	 * 
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @param rootProvider
	 *            an instance to search all instances at evaluation time
	 * @return a new {@link IQueryEnvironment} configured with the services provided by default with Acceleo
	 *         Query
	 * @since 4.0
	 */
	public static IQueryEnvironment newEnvironmentWithDefaultServices(CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider) {
		final IQueryEnvironment env = newEnvironment();

		configureEnvironment(env, xRefProvider, rootProvider);

		return env;
	}

	/**
	 * Configures an environment with all the default services provided by AQL.
	 * 
	 * @param env
	 *            The environment in which to register all default services.
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @param rootProvider
	 *            an instance to search all instances at evaluation time
	 * @since 5.0
	 */
	public static void configureEnvironment(IQueryEnvironment env, CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider) {
		configureEnvironment(env, xRefProvider, rootProvider, false);
	}

	/**
	 * Configures an environment with all the default services provided by AQL.
	 * 
	 * @param env
	 *            The environment in which to register all default services.
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @param rootProvider
	 *            an instance to search all instances at evaluation time
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @since 8.0.2
	 */
	public static void configureEnvironment(IQueryEnvironment env, CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider, boolean forWorkspace) {
		configureEnvironment(env, xRefProvider, rootProvider, new Properties(), forWorkspace);
	}

	/**
	 * Configures an environment with all the default services provided by AQL.
	 * 
	 * @param env
	 *            The environment in which to register all default services.
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @param rootProvider
	 *            an instance to search all instances at evaluation time
	 * @param properties
	 *            the {@link Properties}
	 * @since 7.1
	 */
	public static void configureEnvironment(IQueryEnvironment env, CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider, Properties properties) {
		configureEnvironment(env, xRefProvider, rootProvider, properties, false);
	}

	/**
	 * Configures an environment with all the default services provided by AQL.
	 * 
	 * @param env
	 *            The environment in which to register all default services.
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @param rootProvider
	 *            an instance to search all instances at evaluation time
	 * @param properties
	 *            the {@link Properties}
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @since 8.0.2
	 */
	public static void configureEnvironment(IQueryEnvironment env, CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider, Properties properties, boolean forWorkspace) {
		Set<IService<?>> services = ServiceUtils.getServices(env, new AnyServices(env), forWorkspace);
		ServiceUtils.registerServices(env, services);
		env.registerEPackage(EcorePackage.eINSTANCE);
		env.registerCustomClassMapping(EcorePackage.eINSTANCE.getEStringToStringMapEntry(),
				EStringToStringMapEntryImpl.class);
		services = ServiceUtils.getServices(env, new EObjectServices(env, xRefProvider, rootProvider),
				forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, new XPathServices(env), forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, ComparableServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, NumberServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, StringServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, BooleanServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, CollectionServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, ResourceServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, PromptServices.class, forWorkspace);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, new PropertiesServices(properties));
		ServiceUtils.registerServices(env, services);
	}

	/**
	 * Create a new {@link IQueryEnvironment} with no services configured.
	 * 
	 * @return a new {@link IQueryEnvironment} with no services configured.
	 * @since 5.0
	 */
	public static IQueryEnvironment newEnvironment() {
		return new QueryEnvironment();
	}

	/**
	 * Create a new {@link IQualifiedNameQueryEnvironment} configured with the services provided by default
	 * with Acceleo Query.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @return a new {@link IQualifiedNameQueryEnvironment} configured with the services provided by default
	 *         with Acceleo Query
	 * @since 8.0.2
	 */
	public static IQualifiedNameQueryEnvironment newQualifiedNameEnvironmentWithDefaultServices(
			IQualifiedNameResolver resolver, CrossReferenceProvider xRefProvider) {
		return newQualifiedNameEnvironmentWithDefaultServices(resolver, xRefProvider, null, false);
	}

	/**
	 * Create a new {@link IQualifiedNameQueryEnvironment} configured with the services provided by default
	 * with Acceleo Query.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param xRefProvider
	 *            an instance to inspect cross references at evaluation time
	 * @param rootProvider
	 *            an instance to search all instances at evaluation time
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @return a new {@link IQualifiedNameQueryEnvironment} configured with the services provided by default
	 *         with Acceleo Query
	 * @since 8.0.2
	 */
	public static IQualifiedNameQueryEnvironment newQualifiedNameEnvironmentWithDefaultServices(
			IQualifiedNameResolver resolver, CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider, boolean forWorkspace) {
		final IQualifiedNameQueryEnvironment env = newQualifiedNameEnvironment(resolver);

		configureEnvironment(env, xRefProvider, rootProvider, forWorkspace);

		return env;
	}

	/**
	 * Create a new {@link IQualifiedNameQueryEnvironment} with no services configured.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @return a new {@link IQualifiedNameQueryEnvironment} with no services configured.
	 * @since 8.0.2
	 */
	public static IQualifiedNameQueryEnvironment newQualifiedNameEnvironment(
			IQualifiedNameResolver resolver) {
		return new QualifiedNameQueryEnvironment(resolver);
	}

}
