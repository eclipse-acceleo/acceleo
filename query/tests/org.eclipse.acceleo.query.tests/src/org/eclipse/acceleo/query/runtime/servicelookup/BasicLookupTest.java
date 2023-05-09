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
package org.eclipse.acceleo.query.runtime.servicelookup;

import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;

public class BasicLookupTest extends AbtractServiceLookupTest {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.servicelookup.AbtractServiceLookupTest#getQueryEnvironment()
	 */
	@Override
	IQueryEnvironment getQueryEnvironment() {
		final IQueryEnvironment queryEnvironment = Query.newEnvironmentWithDefaultServices(null);

		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, ServicesClass.class);
		ServiceUtils.registerServices(queryEnvironment, services);

		return queryEnvironment;
	}

}
