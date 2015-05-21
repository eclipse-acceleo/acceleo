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

/**
 * Static utility methods pertaining to Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class Query {

	private Query() {

	}

	/**
	 * Create a new {@link IQueryEnvironment} configured with the services provided by default with Acceleo
	 * Query.
	 * 
	 * @param xRefProvider
	 *            : an instance to inspect cross references at evaluation time.
	 * @return a new {@link IQueryEnvironment} configured with the services provided by default with Acceleo
	 *         Query.
	 */
	public static IQueryEnvironment newEnvironmentWithDefaultServices(CrossReferenceProvider xRefProvider) {

		QueryEnvironment env = new QueryEnvironment(xRefProvider);
		try {
			env.registerServicePackage(AnyServices.class);
			env.registerServicePackage(EObjectServices.class);
			env.registerServicePackage(XPathServices.class);
			env.registerServicePackage(ComparableServices.class);
			env.registerServicePackage(NumberServices.class);
			env.registerServicePackage(StringServices.class);
			env.registerServicePackage(BooleanServices.class);
			env.registerServicePackage(CollectionServices.class);
			env.registerServicePackage(ResourceServices.class);
		} catch (InvalidAcceleoPackageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return env;
	}

	/**
	 * Create a new {@link IQueryEnvironment} with no services configured.
	 * 
	 * @param xRefProvider
	 *            : an instance to inspect cross references at evaluation time.
	 * @return a new {@link IQueryEnvironment} with no services configured.
	 */
	public static IQueryEnvironment newEnvironment(CrossReferenceProvider xRefProvider) {
		return new QueryEnvironment(xRefProvider);
	}

}
