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
package org.eclipse.acceleo.query.runtime;

import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
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
	 * @since 4.0.0
	 */
	public static IQueryEnvironment newEnvironmentWithDefaultServices(CrossReferenceProvider xRefProvider,
			IRootEObjectProvider rootProvider) {
		final IQueryEnvironment env = newEnvironment();

		Set<IService> services = ServiceUtils.getServices(env, new AnyServices(env));
		ServiceUtils.registerServices(env, services);
		env.registerEPackage(EcorePackage.eINSTANCE);
		env.registerCustomClassMapping(EcorePackage.eINSTANCE.getEStringToStringMapEntry(),
				EStringToStringMapEntryImpl.class);
		services = ServiceUtils.getServices(env, new EObjectServices(env, xRefProvider, rootProvider));
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, new XPathServices(env));
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, ComparableServices.class);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, NumberServices.class);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, StringServices.class);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, BooleanServices.class);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, CollectionServices.class);
		ServiceUtils.registerServices(env, services);
		services = ServiceUtils.getServices(env, ResourceServices.class);
		ServiceUtils.registerServices(env, services);

		return env;
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

}
