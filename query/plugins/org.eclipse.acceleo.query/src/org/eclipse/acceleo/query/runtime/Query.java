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
		return new QueryEnvironment(xRefProvider);
	}

}
