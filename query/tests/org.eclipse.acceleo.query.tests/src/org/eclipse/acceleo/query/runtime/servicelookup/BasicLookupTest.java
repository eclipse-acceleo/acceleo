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
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;

public class BasicLookupTest extends AbtractServiceLookupTest {

	@Override
	BasicLookupEngine getEngine() {
		IQueryEnvironment queryEnvironment = new QueryEnvironment(null);
		BasicLookupEngine engine = queryEnvironment.getLookupEngine();
		try {
			engine.addServices(ServicesClass.class);
		} catch (InvalidAcceleoPackageException e) {
			throw new UnsupportedOperationException("shouldn't happen.", e);
		}
		return engine;
	}

}
