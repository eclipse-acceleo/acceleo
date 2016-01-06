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
package org.eclipse.acceleo.query.runtime.servicelookup;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;

public class BasicLookupTest extends AbtractServiceLookupTest {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.servicelookup.AbtractServiceLookupTest#getQueryEnvironment()
	 */
	@Override
	IQueryEnvironment getQueryEnvironment() {
		final IQueryEnvironment queryEnvironment = Query.newEnvironmentWithDefaultServices(null);

		try {
			queryEnvironment.registerServicePackage(ServicesClass.class);
		} catch (InvalidAcceleoPackageException e) {
			throw new UnsupportedOperationException("shouldn't happen.", e);
		}

		return queryEnvironment;
	}

}
